package gameLogic;

import main.SkeletonHelper;
import model.LevelObjectDescriptor;

//Egy kulcs lehetséges helyét jelöli.
public class KeyHolder extends GameObject {
	//Ott van-e még a kulcs.
	private Boolean hasKey;
	
	//Kulcs ottlétének lekérdezése.
	public Boolean getHasKey() {
		return this.hasKey;
	}
	
	//Kulcs ottlétének beállítása.
	public void setHasKey(Boolean hasKey) {
		SkeletonHelper.enterMethod();
		this.hasKey = hasKey;
	}
	
	//Konst.
	public KeyHolder(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		SkeletonHelper.enterMethod();
		setBounds(new RectangleBounds(0.0f, 0.0f, 1.0f, 1.0f));
	}
}
