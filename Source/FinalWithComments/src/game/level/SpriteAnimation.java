package game.level;

public class SpriteAnimation {
	private int row;
	private int column;
	private int length;
	
	public int getRow() {						// returns row
		return this.row;
	}
	
	public int getColumn() {					// returns column
		return this.column;
	}
	
	public int getLength() {					// returns lenght of image array
		return this.length;
	}
	
	public SpriteAnimation(int row, int column, int length) {	// set animation from [row][column] to [row][column+lenght]
		this.row = row;
		this.column = column;
		this.length = length;
	}
}
