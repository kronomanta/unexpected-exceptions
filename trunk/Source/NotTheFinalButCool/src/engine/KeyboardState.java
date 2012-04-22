package engine;

import java.util.ArrayList;

public abstract class KeyboardState {
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
	
	public void tick() {
		this.previousPressedKeys.clear();
		for (String key : this.pressedKeys)
			this.previousPressedKeys.add(key);
	}
	
	protected void setKeyDown(String key) {
		if (!this.pressedKeys.contains(key))
			this.pressedKeys.add(key);
	}
	
	protected void setKeyUp(String key) {
		if (this.pressedKeys.contains(key))
			this.pressedKeys.remove(key);
	}
}
