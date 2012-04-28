package game.level;

import game.GameTime;
import renderer.Image;
import renderer.Point;
import renderer.Rectangle;
import renderer.Renderer;

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
		this.animation = new SpriteAnimation(0, 0, 1);
	}
	
	// Public methods
	public void play(SpriteAnimation animation) {
		if (this.animation == animation)
			return;
		
		this.animation = animation;
		this.frameIndex = 0;
		this.time = 0.0f;
	}
	public void draw(GameTime gameTime, Renderer renderer, Point position) {
		if (this.animation != null) {
			this.time += gameTime.getElapsedTime();
			while (this.time > this.frameTime) {
				this.time -= this.frameTime;
				this.frameIndex++;
			}
			this.frameIndex = this.frameIndex % this.animation.getLength();

			Rectangle target = new Rectangle(
					position.getX(),
					position.getY(),
					this.frameWidth,
					this.frameHeight);
			Rectangle source = new Rectangle(
					-(this.frameIndex + this.animation.getColumn()) * this.frameWidth,
					-this.animation.getRow() * this.frameHeight,
					this.frameWidth,
					this.frameHeight);
			
			renderer.drawImage(target, source, image);
		}
	}
}
