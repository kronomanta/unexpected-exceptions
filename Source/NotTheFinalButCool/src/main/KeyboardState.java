package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardState implements KeyListener {
	// Private fields
	private ArrayList<String> previousPressedKeys = new ArrayList<String>();
	private ArrayList<String> pressedKeys = new ArrayList<String>(); 
	
	// Constructors
	private KeyboardState() {
	}
	
	// Public methods
	public Boolean isKeyDown(String key) {
		return this.pressedKeys.contains(key);
	}
	public Boolean isKeyJustPressed(String key) {
		if (this.pressedKeys.contains(key))
			return !this.previousPressedKeys.contains(key);
		else
			return false;
	}
	public void tick() {
		this.previousPressedKeys.clear();
		for (String key : this.pressedKeys)
			this.previousPressedKeys.add(key);
	}
	
	// KeyListener members
	@Override
	public void keyPressed(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		if (!this.pressedKeys.contains(key))
			this.pressedKeys.add(key);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		if (this.pressedKeys.contains(key))
			this.pressedKeys.remove(key);
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Singleton
	private static KeyboardState instance = new KeyboardState();
	public static KeyboardState getInstance() {
		return instance;
	}
}
