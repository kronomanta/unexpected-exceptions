package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelDescriptor {
	private int width;
	private int height;
	private LevelPartDescriptor[] parts;
	private LevelObjectDescriptor[] objects;
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public LevelPartDescriptor[] getParts() {
		return this.parts;
	}
	
	public LevelObjectDescriptor[] getObjects() {
		return this.objects;
	}
	
	public LevelDescriptor(int width, int height, LevelPartDescriptor[] parts, LevelObjectDescriptor[] objects) {
		this.width = width;
		this.height = height;
		this.parts = parts;
		this.objects = objects;
	}
	
	public static LevelDescriptor parse(Element levelElement) {
		int width = Integer.parseInt(levelElement.getAttribute("Width"));
		int height = Integer.parseInt(levelElement.getAttribute("Height"));

		Element partsElement = (Element)levelElement.getElementsByTagName("Parts").item(0);
		NodeList partNodes = partsElement.getElementsByTagName("LevelPart");
		LevelPartDescriptor[] parts = new LevelPartDescriptor[partNodes.getLength()];
		for (int i = 0; i < partNodes.getLength(); i++) {
			parts[i] = LevelPartDescriptor.parse((Element)partNodes.item(i));
		}
		
		Element objectsElement = (Element)levelElement.getElementsByTagName("Objects").item(0);
		NodeList objectNodes = objectsElement.getElementsByTagName("Object");
		LevelObjectDescriptor[] objects = new LevelObjectDescriptor[objectNodes.getLength()];
		for (int i = 0; i < objectNodes.getLength(); i++) {
			objects[i] = LevelObjectDescriptor.parse((Element)objectNodes.item(i));
		}
		
		return new LevelDescriptor(width, height, parts, objects);
	}
	
	public static LevelDescriptor load(String fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse (new File(fileName));
        
		return LevelDescriptor.parse(document.getDocumentElement());
	}
}
