package gameLogic;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

public class KeyHolder extends GameObject {
	private Boolean hasKey;
	
	public Boolean getHasKey() {
		return this.hasKey;
	}
	
	public void setHasKey(Boolean hasKey) {
		this.hasKey = hasKey;
	}
	
	public KeyHolder(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		
		if (descriptor.getType() != LevelObjectType.Key)
			throw new IllegalArgumentException("Invalid object descriptor.");
		this.hasKey = true;
		this.setBounds(new RectangleBounds(descriptor.getX(), descriptor.getY(), 1.0f, 1.0f));
	}
}
