package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.KeyboardState;

public class SwingKeyboardState extends KeyboardState implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		this.setKeyDown(KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.setKeyUp(KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
