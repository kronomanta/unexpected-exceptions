package engine;

import java.util.ArrayList;

public class Scene extends DrawableGameComponent {
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();

	protected ArrayList<GameComponent> getComponents() {
		return this.components;
	}

	protected void addComponent(GameComponent component) {
		component.internalInitialize(this.getContentManager(), this.getKeyboardState(), this.getViewport());
		this.components.add(component);
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		for (GameComponent component : this.components) {
			if (!DrawableGameComponent.class.isInstance(component))
				continue;

			DrawableGameComponent drawable = (DrawableGameComponent) component;
			if (drawable.getIsVisible()) {
				renderer.setTransform(new RenderTransform());
				drawable.draw(gameTime, renderer);
			}
		}
	}

	@Override
	public void update(GameTime gameTime) {
		for (GameComponent component : this.components) {
			component.update(gameTime);
		}
	}
}
