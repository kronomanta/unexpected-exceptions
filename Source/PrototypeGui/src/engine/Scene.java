package engine;

import java.util.ArrayList;
import javax.swing.JFrame;
import model.LevelDescriptor;
import gameLogic.Level;

public class Scene{
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	protected Level level;
	private SwingKeyboardState keyboardState;
	private JFrame viewport;
	
	public JFrame getViewPort(){
		return viewport;
	}
	public SwingKeyboardState getKeyboardState(){
		return keyboardState;
	}
	
	public void setLevel(LevelDescriptor descriptor) throws Exception{
		this.level = new Level(descriptor);	
	}
	
	public ArrayList<GameComponent> getComponents() {
		return this.components;
	}
	
	public void draw(GameTime gameTime, SwingRenderer renderer) {
		for (GameComponent component : this.components) {
			if (component.getIsVisible()) {
				renderer.setTransform(new RenderTransform());
				component.draw(gameTime, renderer);
			}
		}
	}

	public void update(GameTime gameTime) {
		for (GameComponent component : this.components) {
			component.update(gameTime);
		}
	}
	
	public void internalInitialize( SwingKeyboardState keyboardState, JFrame viewport) {
		this.keyboardState = keyboardState;
		this.viewport = viewport;
	}
	
	protected void addComponent(GameComponent component) {
		component.internalInitialize(this.keyboardState, this.viewport);
		this.components.add(component);
	}
}
