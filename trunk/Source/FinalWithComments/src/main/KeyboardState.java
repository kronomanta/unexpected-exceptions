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
	// Returns which key is pressed
	public Boolean isKeyDown(String key) {
		return this.pressedKeys.contains(key);
	}

	// Returns if key was just pressed
	public Boolean isKeyJustPressed(String key) {
		if (this.pressedKeys.contains(key))
			return !this.previousPressedKeys.contains(key);
		else
			return false;
	}
	// Adds the batch of newly pressed keys to the String typed previousPressedKeys ArrayList
	public void tick() {
		this.previousPressedKeys.clear();
		for (String key : this.pressedKeys)
			this.previousPressedKeys.add(key);
	}
	
	// KeyListener members
	// Gets which key was pressed
	@Override
	public void keyPressed(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		if (!this.pressedKeys.contains(key))
			this.pressedKeys.add(key);
	}
	@Override
	// Gets which key was released
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
