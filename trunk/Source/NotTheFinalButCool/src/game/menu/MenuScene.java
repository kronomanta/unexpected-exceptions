package game.menu;

import engine.Scene;
import game.level.FrameRateCounterComponent;

public class MenuScene extends Scene {
	public MenuScene() {
		FrameRateCounterComponent counter = new FrameRateCounterComponent();
		this.addComponent(counter);
	}
}
