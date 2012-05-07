package game;

import game.renderer.Point;

public class Animator {
	// Private fields
	private float startX = 0.0f;
	private float startY = 0.0f;
	private float dx = 0.0f;
	private float dy = 0.0f;
	private float interval = 0.0f;
	private AnimatorMode mode = AnimatorMode.EaseIn;
	private float totalTime = 0.0f;
	private Boolean isRunning = false;

	// Public methods
	public void start(Point start, Point end, float interval, AnimatorMode mode) {
		this.startX = start.getX();
		this.startY = start.getY();
		this.dx = end.getX() - this.startX;
		this.dy = end.getY() - this.startY;
		this.interval = interval;
		this.mode = mode;
		this.totalTime = 0.0f;
		this.isRunning = true;
	}

	// Calculates new metrics according to elapsed time. Returns true if finished. Returns false if called but not running.
	public Boolean update(float elapsedTime, Point out) {
		if (!this.isRunning)
			return false;

		this.totalTime += elapsedTime;
		float progress = this.totalTime / this.interval;
		// breaks with new setX/Y values when analyzed time is smaller than analyzed interval
		if (progress > 1.0f) {
			this.isRunning = false;
			out.setX(this.startX + this.dx);
			out.setY(this.startY + this.dy);
			return false;
		} else {
		// makes calculations according to animator mode and returns with eased values of elapsed times for setX/Y
			float easedProgress = 0.0f;

			switch (this.mode) {
			case EaseIn:
				float p = progress - 1.0f;
				easedProgress = -1 * p * p * p * p * p * p + 1.0f;
				break;
			case EaseOut:
				easedProgress = progress * progress * progress * progress * progress * progress;
				break;
			}

			out.setX(this.startX + easedProgress * this.dx);
			out.setY(this.startY + easedProgress * this.dy);
			return true;
		}
	}
}
