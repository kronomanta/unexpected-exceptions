package gameLogic;

// Direction helper class for getting opposite directions in a convenient way
public class DirectionHelper {
	public static Direction getOpposite(Direction direction) {
		if (direction == Direction.Left)
			return Direction.Right;
		else if (direction == Direction.Up)
			return Direction.Down;
		else if (direction == Direction.Right)
			return Direction.Left;
		else if (direction == Direction.Down)
			return Direction.Up;
		else
			throw new IllegalArgumentException();	// exception: no such direction allowed
	}
}
