package game;

import game.renderer.Renderer;

public interface IDrawableGameComponent extends IGameComponent {	// interface for drawable game compontents
	void draw(GameTime gameTime, Renderer renderer);
}
