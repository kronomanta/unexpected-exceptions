package gameLogic;

import main.SkeletonHelper;
import model.BlockDescriptor;

//Nem átjárható, a falat reprezentáló GameObject, aktív szerepe nincs.
//konst.
public class Block extends GameObject {
	public Block(BlockDescriptor descriptor, LevelPart levelPart) {
		super(levelPart);
		SkeletonHelper.enterMethod();
		
		SkeletonHelper.comment("A kapott leiro alapjan vagy rectangle");
		IBounds bounds = new RectangleBounds(0.0f, 0.0f, 1.0f, 1.0f);
		SkeletonHelper.comment("vagy triangle kiterjedese van.");
		bounds = new TriangleBounds(0.0f, 0.0f, 1.0f, 1.0f, 0);
		this.setBounds(bounds);
	}
}
