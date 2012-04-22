package gameLogic;

import main.SkeletonHelper;
import model.LevelObjectDescriptor;

public class KeyHolder extends GameObject {
	private Boolean hasKey;
	
	public Boolean getHasKey() {
		return this.hasKey;
	}
	
	public void setHasKey(Boolean hasKey) {
		SkeletonHelper.enterMethod();
		this.hasKey = hasKey;
	}
	
	public KeyHolder(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		SkeletonHelper.enterMethod();
		setBounds(new RectangleBounds(0.0f, 0.0f, 1.0f, 1.0f));
	}
}
