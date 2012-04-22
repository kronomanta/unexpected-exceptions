package game.level;

import engine.Color;
import engine.DrawableGameComponent;
import engine.GameTime;
import engine.Point;
import engine.Renderer;

public class FrameRateCounterComponent extends DrawableGameComponent {
	private float elapsedTime;
	private int frames;
	private float fps;
	
	private Point location;
	private Color color;
	
	public FrameRateCounterComponent() {
		this.location = new Point(10, 10);
		this.color = new Color(0, 0, 0);
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.drawText(this.location, "FPS: " + String.valueOf(this.fps), 22, this.color);
	}

	@Override
	public void update(GameTime gameTime) {
	    this.elapsedTime += gameTime.getElapsedTime();
	    this.frames++;
	 
	    if (this.elapsedTime >= 1.0f)
	    {
	        this.fps = this.frames;
	        this.frames = 0;
	        this.elapsedTime = 0;
	    }
	}
}
