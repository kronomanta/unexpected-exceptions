package game.level;

import java.awt.Color;

import gameLogic.Vector2;

import engine.GameTime;
import engine.SwingRenderer;
import engine.GameComponent;;


public class FrameRateCounterComponent extends GameComponent {
	private float elapsedTime;
	private int frames;
	private float fps;
	
	private Vector2 location;
	private Color color;
	
	public FrameRateCounterComponent() {
		this.location = new Vector2(10, 10);
		this.color = new Color(0, 0, 0);
	}

	@Override
	public void draw(GameTime gameTime, SwingRenderer renderer) {
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
