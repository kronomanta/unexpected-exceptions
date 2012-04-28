package game;

import renderer.Renderer;

public interface IDrawableGameComponent extends IGameComponent {
	void draw(GameTime gameTime, Renderer renderer);
}
