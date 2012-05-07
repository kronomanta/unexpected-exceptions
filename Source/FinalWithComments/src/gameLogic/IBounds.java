package gameLogic;

import java.security.InvalidParameterException;

public interface IBounds {

	// returns with the X coordinate of the upper left corner of given rectangle
	public float getLeft();

	// returns with the Y coordinate of the upper left corner of given rectangle
	public float getTop();

	// returns with the width of given rectangle
	public float getWidth();

	// returns with the height of given rectangle
	public float getHeight();

	// returns with the X coordinate of the lower right corner of given rectangle
	public float getRight();

	// returns with the Y coordinate of the lower right corner of given rectangle
	public float getBottom();
	
	public Vector2 getCenter();

	// returns if this rectangle intersects with the given rectangle
	// the InvalidParameterException stands for invalid object implementing the IBound interface this implementation is not compatible with
	public Boolean intersects(IBounds other) throws InvalidParameterException;

	// returns if this rectangle contains the given (X,Y) point
	public Boolean contains(float x, float y);

	// calculates the depth of given intersection between this rectangle and the other
	// the InvalidParameterException stands for invalid object implementing the IBound interface this implementation is not compatible with
	public Vector2 getIntersectionDepthWith(IBounds other)
			throws InvalidParameterException;
}
