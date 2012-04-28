package game;

import game.renderer.Renderer;

public interface IDrawableGameComponent extends IGameComponent {
	void draw(GameTime gameTime, Renderer renderer);
}
