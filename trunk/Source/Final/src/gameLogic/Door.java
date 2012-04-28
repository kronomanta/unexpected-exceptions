package gameLogic;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

public class Door extends GameObject {
	public Door(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		
		if (descriptor.getType() != LevelObjectType.Door)
			throw new IllegalArgumentException("Invalid object descriptor.");
		this.setBounds(new RectangleBounds(descriptor.getX(), descriptor.getY(), 1.0f, 1.0f));
	}
}
