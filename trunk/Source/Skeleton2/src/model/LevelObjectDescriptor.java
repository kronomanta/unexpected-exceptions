package model;


import main.SkeletonHelper;

import org.w3c.dom.Element;


//Pályaelemeket alkotó objektumok leírója
public class LevelObjectDescriptor {
	//kezdõpozíció, ajtó, vagy kulcs
	private LevelObjectType type;
	//melyik pályaelemben vagyunk
	private int levelPartIndex;
	//pozíció
	private int x;
	private int y;
	
	//Állapotlekérdezõk.
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
	
	//Konst.
	public LevelObjectDescriptor(LevelObjectType type, int levelPartIndex, int x, int y) {
		this.type = type;
		this.levelPartIndex = levelPartIndex;
		this.x = x;
		this.y = y;
	}
	
	public static LevelObjectDescriptor parse(Element objectElement) {
		SkeletonHelper.enterMethod();
		return new LevelObjectDescriptor(LevelObjectType.Key, 0, 0, 0);
	}
}
