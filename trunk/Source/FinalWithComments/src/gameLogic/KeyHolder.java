package gameLogic;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

// class for key type object. Can be picked up.
public class KeyHolder extends GameObject {
	private Boolean hasKey;		// if true, the key hasn't been picked up
	
	// Getters and setters
	public Boolean getHasKey() {
		return this.hasKey;
	}
	
	public void setHasKey(Boolean hasKey) {
		this.hasKey = hasKey;
	}
	
	// Constructor
	public KeyHolder(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		
		if (descriptor.getType() != LevelObjectType.Key)
			throw new IllegalArgumentException("Invalid object descriptor.");
		this.hasKey = true;
		this.setBounds(new RectangleBounds(descriptor.getX(), descriptor.getY(), 1.0f, 1.0f));
	}
}
