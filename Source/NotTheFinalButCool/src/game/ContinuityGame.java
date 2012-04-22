package game;

import model.LevelDescriptor;
import engine.IContentProvider;
import engine.Game;
import engine.KeyboardState;
import engine.Renderer;
import engine.IViewport;
import game.level.LevelScene;
import game.menu.MenuScene;

public class ContinuityGame extends Game {
	public ContinuityGame(Renderer renderer, IContentProvider resourceProvider, KeyboardState keyboardState,
			IViewport viewport) {
		super(renderer, resourceProvider, keyboardState, viewport);

		MenuScene scene = new MenuScene();
		this.addScene(scene);

		LevelScene levelScene = new LevelScene(this);
		addScene(levelScene);
		try {
			levelScene.setLevel(LevelDescriptor.load("levels/level01.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setActiveScene(levelScene);
	}
}
