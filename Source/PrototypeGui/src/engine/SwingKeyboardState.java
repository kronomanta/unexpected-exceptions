package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SwingKeyboardState implements KeyListener {
	private ArrayList<String> previousPressedKeys = new ArrayList<String>();
	private ArrayList<String> pressedKeys = new ArrayList<String>(); 
	
	public Boolean isKeyDown(String key) {
		return this.pressedKeys.contains(key);
	}
	
	public Boolean isKeyJustPressed(String key) {
		if (this.pressedKeys.contains(key))
			return !this.previousPressedKeys.contains(key);
		else
			return false;
	}
	
	public void keyPressed(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		if (!this.pressedKeys.contains(key))
			this.pressedKeys.add(key);
	}

	public void keyReleased(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		if (this.pressedKeys.contains(key))
			this.pressedKeys.remove(key);
	}

	public void tick() {
		this.previousPressedKeys.clear();
		for (String key : this.pressedKeys)
			this.previousPressedKeys.add(key);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
