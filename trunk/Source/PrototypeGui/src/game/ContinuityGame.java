package game;

import javax.swing.JFrame;

import model.LevelDescriptor;

import java.util.ArrayList;

import engine.GameTime;
import engine.Scene;
import engine.SwingKeyboardState;
import engine.SwingRenderer;
import game.level.LevelScene;
import game.menu.MenuScene;

public class ContinuityGame {

	private ArrayList<Scene> scenes;
	private Scene activeScene;

	private SwingRenderer renderer;
	private SwingKeyboardState keyboardState;
	private JFrame viewport;

	private Boolean firstTick = true;
	private long firstTickTime;
	private long lastTickTime;

	
	public ContinuityGame(SwingRenderer renderer, SwingKeyboardState keyboardState,
			JFrame viewport) {

		this.scenes = new ArrayList<Scene>();
		this.renderer = renderer;
		this.keyboardState = keyboardState;
		this.viewport = viewport;
		
		MenuScene menu = new MenuScene();
		this.addScene(menu);

		LevelScene levelScene = new LevelScene();
		levelScene.internalInitialize(keyboardState, viewport);
		addScene(levelScene);
		try {
			levelScene.setLevel(LevelDescriptor.load("levels/level01.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setActiveScene(levelScene);
	}

	private void setActiveScene(Scene scene) {
		this.activeScene = scene;
	}
	
	protected void addScene(Scene scene) {
		this.scenes.add(scene);
	}

	public void tick() {
		long currentTime = System.currentTimeMillis();

		if (firstTick) {
			this.firstTickTime = currentTime;
			this.lastTickTime = currentTime;

			firstTick = false;
		}

		float elapsedTime = (currentTime - this.lastTickTime) / 1000.0f;
		float totalTime = (currentTime - this.firstTickTime) / 1000.0f;
		this.lastTickTime = currentTime;
		GameTime gameTime = new GameTime(elapsedTime, totalTime);

		this.activeScene.update(gameTime);
		this.activeScene.draw(gameTime, renderer);
		this.keyboardState.tick();
	}	
}