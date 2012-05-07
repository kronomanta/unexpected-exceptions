package gameLogic;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

// Class for door type game object. Represents exit point.
public class Door extends GameObject {
	public Door(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		
		if (descriptor.getType() != LevelObjectType.Door)
			throw new IllegalArgumentException("Invalid object descriptor.");
		this.setBounds(new RectangleBounds(descriptor.getX(), descriptor.getY(), 1.0f, 1.0f));
	}
}
