package game.level;

import game.GameTime;
import game.renderer.Image;
import game.renderer.Point;
import game.renderer.Rectangle;
import game.renderer.Renderer;

public class SrpitePlayer {
	// Private fields
	private Image image;
	private int frameWidth;
	private int frameHeight;
	private float frameTime;
	
	private SpriteAnimation animation;
	
	private float time;
	private float frameIndex;
	
	// Constructors
	public SrpitePlayer(Image image, int frameWidth, int frameHeight, float frameTime) {
		this.image = image;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTime = frameTime;
		this.animation = new SpriteAnimation(0, 0, 1);					// default animation is a static picture of the first part of the image
	}
	
	// Public methods
	public void play(SpriteAnimation animation) {						// set sprite animation to play
		if (this.animation == animation)
			return;
		
		this.animation = animation;
		this.frameIndex = 0;								// set frame # to 0
		this.time = 0.0f;								// reset animation time to 0 sec
	}
	public void draw(GameTime gameTime, Renderer renderer, Point position) {		// animate
		if (this.animation != null) {							// if a sprite animation is loaded
			this.time += gameTime.getElapsedTime();					// draw the next frames according to elapsed time
			while (this.time > this.frameTime) {
				this.time -= this.frameTime;
				this.frameIndex++;
			}
			this.frameIndex = this.frameIndex % this.animation.getLength();		// restart frameindex when exceeding the lenght of actual animation sprite

			Rectangle target = new Rectangle(					// set target rectangle
					position.getX(),
					position.getY(),
					this.frameWidth,
					this.frameHeight);
			Rectangle source = new Rectangle(					// set source rectangle
					-(this.frameIndex + this.animation.getColumn()) * this.frameWidth,
					-this.animation.getRow() * this.frameHeight,
					this.frameWidth,
					this.frameHeight);
			
			renderer.drawImage(target, source, image);				// call to renderer's drawimage
		}
	}
}
