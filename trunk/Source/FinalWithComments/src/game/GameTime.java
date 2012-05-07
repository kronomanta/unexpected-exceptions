package game;

public class GameTime {
	private float elapsedTime;
	private float totalTime;
	
	public float getElapsedTime() {					// get elapsed time
		return this.elapsedTime;
	}
	
	public float getTotalTime() {					// get total play time
		return this.totalTime;
	}
	
	public GameTime(float elapsedTime, float totalTime) {		// create new gametime object
		this.elapsedTime = elapsedTime;
		this.totalTime = totalTime;
	}
}
