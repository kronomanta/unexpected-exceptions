package gameLogic;

import main.SkeletonHelper;
import model.LevelObjectDescriptor;

//Avatár mozgatásáért felelõs osztály
public class Player extends GameObject {
	//pozíció és lekérdezésük.
	private float logicalX;
	private float logicalY;

	public float getLogicalX() {
		return this.logicalX;
	}

	public float getLogicalY() {
		return this.logicalY;
	}

	//konst.
	public Player(LevelPart levelPart, LevelObjectDescriptor spawnPoint) {
		super(levelPart);
		SkeletonHelper.enterMethod();

		resetToSpawnPoint();
		updateBounds();
	}

	//input függvényében frissíti a player helyzetét
	public void update(float time, Boolean goLeft, Boolean goRight, Boolean jump) {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Frissiti a logikai X poziciot jobbra vagy balra.");
		SkeletonHelper.comment("Frissiti a fuggoleges sebesseget a gravitacio alapjan.");
		handleJump(jump);
		SkeletonHelper.comment("A fuggoleges sebesseg alapjan frissiti a logikai Y koordinatat.");
		SkeletonHelper.space();

		SkeletonHelper.comment("Kezeli azt az esetet amikor a LevelPart falanak utkozunk.");
		handleLevelPartBoundaries();
		SkeletonHelper.space();
		SkeletonHelper.comment("Az utkozesvizsgalat elott frissiti a jatekos kiterjedeset.");
		updateBounds();
		SkeletonHelper.space();
		handleCollisions();
	}

	//ApplyPhysics függvények. - Valós fizikai világot megközelítõen igazítja a player mozgását.
	private void handleJump(Boolean jump) {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Frissiti az ugras allapotat (this.jumpState).");
		SkeletonHelper.comment("Ha a jatekos ugorni akar eldonti hogy ugorhat-e.");
		SkeletonHelper.comment("Ha igen akkor ennek megfeleloen beallitja a fuggoleges sebesseget");
	}
	
	//ütközésdetektálásnál frissíti a játékos pozícióját
	private void handleCollisions() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Megvizsgalja az utkozesek merteket az aktualis LevelPart osszes");
		SkeletonHelper.comment("Block-javal. Ha van metszes, akkor a kissebb merteku atfedes");
		SkeletonHelper.comment("iranyaba (X vagy Y tengely menten) visszaigazitja a jatekost");
		SkeletonHelper.comment("es frissiti a jatekos kiterjedeset.");
    	updateBounds();
	}

	private void handleLevelPartBoundaries() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Megvizsgalja falnak utkozott-e a jatekos.");
		SkeletonHelper.comment("Ha igen, megnezi az aktualis LevelPart atjarhatosagat");
		SkeletonHelper.comment("az adott iranyba.");
		LevelPart levelPart = this.getLevelPart();
		levelPart.getPassabilities();

		SkeletonHelper.space();
		SkeletonHelper.comment("Ha nem, akkor frissiti a logikai X koordinatat hogy ne legyen");
		SkeletonHelper.comment("a falban. Ha atjarhato akkor frissiti a poziciojat es hogy melyik");
		SkeletonHelper.comment("levelpartban van.");
		levelPart.getNeighbours();
		this.setLevelPart(null);

		SkeletonHelper.space();
		SkeletonHelper.comment("Ha padloval utkozott akkor meghalt.");
		resetToSpawnPoint();
	}

	//kezdõpozícióra kerülés.
	private void resetToSpawnPoint() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("A logikai koordinatakat es az aktualis LevelPart-ot");
		SkeletonHelper.comment("a spawn point-nak megfeleloen beallitja.");
	}

	private void updateBounds() {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("A logikai koordinatak alapjan frissiti a kiterjedeset.");
		this.setBounds(new RectangleBounds(0.0f, 0.0f, 1.0f, 1.0f));
	}
}
