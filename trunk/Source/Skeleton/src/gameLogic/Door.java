package gameLogic;

import main.SkeletonHelper;
import model.LevelObjectDescriptor;

public class Door extends GameObject {
	public Door(LevelPart levelPart, LevelObjectDescriptor descriptor) {
		super(levelPart);
		SkeletonHelper.enterMethod();
		setBounds(new RectangleBounds(0.0f, 0.0f, 1.0f, 1.0f));
	}
}
