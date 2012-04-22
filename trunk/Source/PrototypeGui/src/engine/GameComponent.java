package engine;

import javax.swing.JFrame;

public abstract class GameComponent {
	private Boolean isVisible = true;
	
	private SwingKeyboardState keyboardState;
	private JFrame viewport;
	
	
	public Boolean getIsVisible(){
		return isVisible;
	}
	
	public void setIsVisible(Boolean isVisible){
		this.isVisible = isVisible;
	}

	public SwingKeyboardState getKeyboardState() {
		return this.keyboardState;
	}

	public JFrame getViewport() {
		return this.viewport;
	}
	
	public void internalInitialize(SwingKeyboardState keyboardState, JFrame viewport) {
		this.keyboardState = keyboardState;
		this.viewport = viewport;
	}
	
	public void initialize() {
	}

	public abstract void update(GameTime gameTime);
	public abstract void draw(GameTime gameTime, SwingRenderer renderer);
}
