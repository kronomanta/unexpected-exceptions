package model;

import org.w3c.dom.Element;

public class LevelObjectDescriptor {
	private LevelObjectType type;
	private int levelPartIndex;
	private int x;
	private int y;
	
	public LevelObjectType getType() {
		return this.type;
	}
	
	public int getLevelPartIndex() {
		return this.levelPartIndex;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public LevelObjectDescriptor(LevelObjectType type, int levelPartIndex, int x, int y) {
		this.type = type;
		this.levelPartIndex = levelPartIndex;
		this.x = x;
		this.y = y;
	}
	
	public static LevelObjectDescriptor parse(Element objectElement) {
		LevelObjectType type = LevelObjectType.valueOf(objectElement.getAttribute("Type"));
		int levelPartIndex = Integer.parseInt(objectElement.getAttribute("LevelPartIndex"));
		int x = Integer.parseInt(objectElement.getAttribute("X"));
		int y = Integer.parseInt(objectElement.getAttribute("Y"));
		
		return new LevelObjectDescriptor(type, levelPartIndex, x, y);
	}
}
