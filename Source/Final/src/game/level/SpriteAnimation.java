package game.level;

public class SpriteAnimation {
	private int row;
	private int column;
	private int length;
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public SpriteAnimation(int row, int column, int length) {
		this.row = row;
		this.column = column;
		this.length = length;
	}
}
