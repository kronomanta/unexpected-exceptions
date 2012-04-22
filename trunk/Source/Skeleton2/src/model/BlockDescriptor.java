package model;


import main.SkeletonHelper;

import org.w3c.dom.Element;

//Blokk leírása
public class BlockDescriptor {
	//kezdõ pozíció, szélesség, magasság megadása
	private int x;
	private int y;
	private int width;
	private int height;
	//milyen fal? sima, balra lejt, jobbra lejt
	private BlockType type;
	
	//állapot lekérdezõ függvények
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public BlockType getType() {
		return this.type;
	}
	
	//konst.
	public BlockDescriptor(int x, int y, int width, int height, BlockType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	
	public static BlockDescriptor parse(Element blockElement) {
		SkeletonHelper.enterMethod();
		return new BlockDescriptor(0, 0, 1, 1, BlockType.Normal);
	}
}
