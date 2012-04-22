package gameLogic;


import main.SkeletonHelper;
import model.LevelDescriptor;


public class Level {
	private int width;
	private int height;
	private LevelPart[][] parts;
	private KeyHolder[] keyHolders;
	private Door door;
	private Boolean isCompleted;
	private Player player;

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public LevelPart[][] getParts() {
		return this.parts;
	}

	public KeyHolder[] getKeyHolders() {
		return this.keyHolders;
	}

	public Door getDoor() {
		return this.door;
	}

	public Boolean getIsCompleted() {
		return this.isCompleted;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Level(LevelDescriptor descriptor) {
		SkeletonHelper.enterMethod();

		SkeletonHelper.comment("A leiro alapjan letrehozza az osszes LevelPart-ot.");
		this.parts = new LevelPart[1][1];
		LevelPart levelPart = new LevelPart(null, null);
		this.parts[0][0] = levelPart;
		
		SkeletonHelper.space();
		SkeletonHelper.comment("Mindegyik LevelPart-ot frissiti.");
		levelPart.update();

		SkeletonHelper.space();
		SkeletonHelper.comment("Vegul letrehozza a jatekost, a kulcsokat es az ajtot.");
		this.keyHolders = new KeyHolder[1];
		this.keyHolders[0] = new KeyHolder(levelPart, null);
		this.player = new Player(levelPart, null);
		this.door = new Door(levelPart, null);
	}

	public void slide(Direction direction) {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Megkeresi az ures helyet (null erteku LevelPartot).");
		SkeletonHelper.comment("A parameterben kapott iranybol eloveszi a szomszedos LevelPart-ot.");
		SkeletonHelper.comment("Ha van az adott iranyban LevelPart kicsereli azokat a tombben.");
		SkeletonHelper.comment("Vegul frissiti az osszes LevelPart-ot:");
		
		this.parts[0][0].update();
	}

	public void update() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Megvizsgalja utkozik-e a jatekos a kulcsokkal vagy az ajtoval.");
		this.player.collidesWith(this.door);
		SkeletonHelper.comment("Ha utkozik egy kulccsal akkor beallitja hogy ott mar nincs kulcs.");
		this.keyHolders[0].setHasKey(false);
		SkeletonHelper.comment("Ha mar nincs kulcs a palyan es az ajtoval is utkozott akkor kesz.");
	}
}
