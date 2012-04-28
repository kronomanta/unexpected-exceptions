package game;

public class GameTime {
	private float elapsedTime;
	private float totalTime;
	
	public float getElapsedTime() {
		return this.elapsedTime;
	}
	
	public float getTotalTime() {
		return this.totalTime;
	}
	
	public GameTime(float elapsedTime, float totalTime) {
		this.elapsedTime = elapsedTime;
		this.totalTime = totalTime;
	}
}
