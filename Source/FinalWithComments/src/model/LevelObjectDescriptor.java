package model;

import org.w3c.dom.Element;

// descriptor class for objects on a given level
public class LevelObjectDescriptor {
	private LevelObjectType type;		// level object type (can be door, key or player spawn point)
	private int levelPartIndex;		// index of given object
	private int x;				// level object position
	private int y;
	
	// Getters and setters
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
	
	// Constructor
	public LevelObjectDescriptor(LevelObjectType type, int levelPartIndex, int x, int y) {
		this.type = type;
		this.levelPartIndex = levelPartIndex;
		this.x = x;
		this.y = y;
	}
	
	// Parser to read from file
	public static LevelObjectDescriptor parse(Element objectElement) {
		LevelObjectType type = LevelObjectType.valueOf(objectElement.getAttribute("Type"));
		int levelPartIndex = Integer.parseInt(objectElement.getAttribute("LevelPartIndex"));
		int x = Integer.parseInt(objectElement.getAttribute("X"));
		int y = Integer.parseInt(objectElement.getAttribute("Y"));
		
		return new LevelObjectDescriptor(type, levelPartIndex, x, y);
	}
}
