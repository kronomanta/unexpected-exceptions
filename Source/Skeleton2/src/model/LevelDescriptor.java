package model;

import main.SkeletonHelper;

//Pályaleíró osztály
public class LevelDescriptor {
	//Pálya szélessége, magassága
	private int width;
	private int height;
	//Pályaelem leírók, és object leírók tárolása
	private LevelPartDescriptor[] parts;
	private LevelObjectDescriptor[] objects;
	
	//Állapotlekérdezõk
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
	
	//konst. 
	public LevelDescriptor(int width, int height, LevelPartDescriptor[] parts, LevelObjectDescriptor[] objects) {
		this.width = width;
		this.height = height;
		this.parts = parts;
		this.objects = objects;
	}
	
	//Pályaleíró fájlból való betöltése
	public static LevelDescriptor load(String fileName)  {
		SkeletonHelper.enterMethod();
		LevelPartDescriptor.parse(null);
		LevelObjectDescriptor.parse(null);
		
		return new LevelDescriptor(0, 0, new LevelPartDescriptor[0], new LevelObjectDescriptor[0]);
	}
}
