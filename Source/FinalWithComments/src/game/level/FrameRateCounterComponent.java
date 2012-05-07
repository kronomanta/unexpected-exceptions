package game.level;

import java.awt.Color;

import game.GameTime;
import game.IDrawableGameComponent;
import game.renderer.Point;
import game.renderer.Renderer;

public class FrameRateCounterComponent implements IDrawableGameComponent {
	private float elapsedTime;
	private int frames;
	private float fps;
	
	private Point location;
	private Color color;
	
	public FrameRateCounterComponent() {
		this.location = new Point(700, 570);						// set position on screen
		this.color = new Color(0, 0, 0);
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {				// sets text and calls renderer's drawtext function
		renderer.drawText(this.location, "FPS: " + String.valueOf(this.fps), 22, this.color);
	}

	@Override
	public void update(GameTime gameTime) {							// calculates fps by measuring how many times
	    this.elapsedTime += gameTime.getElapsedTime();					// the frames variable is incremented a second
	    this.frames++;
	 
	    if (this.elapsedTime >= 1.0f)
	    {
	        this.fps = this.frames;
	        this.frames = 0;
	        this.elapsedTime = 0;
	    }
	}
}
