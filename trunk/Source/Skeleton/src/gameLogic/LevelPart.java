package gameLogic;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.SkeletonHelper;
import model.LevelPartDescriptor;


public class LevelPart {
	private int x;
	private int y;
	private Block[] blocks;
	private HashMap<Direction, LevelPart> neighbours;
	private HashMap<Direction, Boolean> passabilities;

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Block[] getBlocks() {
		return this.blocks;
	}

	public Map<Direction, LevelPart> getNeighbours() {
		SkeletonHelper.enterMethod();
		return Collections.unmodifiableMap(this.neighbours);
	}

	public Map<Direction, Boolean> getPassabilities() {
		SkeletonHelper.enterMethod();
		return Collections.unmodifiableMap(this.passabilities);
	}

	public LevelPart(LevelPartDescriptor descriptor, LevelPart[][] levelParts) {
		SkeletonHelper.enterMethod();
		
		this.passabilities = new HashMap<Direction, Boolean>();
		this.neighbours = new HashMap<Direction, LevelPart>();
		new Block(null, this);
	}

	public void update() {
		SkeletonHelper.enterMethod();
		updatePosition();
		SkeletonHelper.space();

		SkeletonHelper.comment("Mind a negy iranyba megvizsgalja a kornyezetet es eltarolja");
		SkeletonHelper.comment("melyik iranyokba kik a szomszedok es hogy arra atjarhato-e.");
		getNeighbour(Direction.Left);
		isPassableTo(Direction.Left);
	}

	private void updatePosition() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Megkeresi sajat magat a LevelPart-ok tombjeben");
		SkeletonHelper.comment("es az alapjan frissiti az X es Y koordinatajat.");
	}

	private Boolean isInBlock(float x, float y) {
		SkeletonHelper.enterMethod();
		
		return false;
	}

	private Boolean[] getSide(Direction direction) {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Keszit egy metszet kepet a LevelPart megadott oldalarol.");

		isInBlock(0.0f, 0.0f);
		return null;
	}

	private Boolean isPassableTo(Direction direction) {
		SkeletonHelper.enterMethod();

		SkeletonHelper.comment("Osszehasonlitja az aktualis LevelPart es az adott iranyban");
		SkeletonHelper.comment("szomszedos LevelPart mefelelo oldalainak metszet kepet");
		SkeletonHelper.comment("es eldonti hogy atjarhato-e az ut.");
		this.getSide(direction);

		return false;
	}

	private LevelPart getNeighbour(Direction direction) {
		SkeletonHelper.enterMethod();
		
		return null;
	}
}
