package game;

import java.util.ArrayList;
import java.util.HashMap;

import game.level.DoorComponent;
import game.level.KeyComponent;
import main.KeyboardState;
import game.level.LevelPartComponent;
import game.level.LevelSceneCamera;
import game.level.LevelSceneCameraMode;
import game.level.PlayerComponent;
import gameLogic.Direction;
import gameLogic.KeyHolder;
import gameLogic.Level;
import gameLogic.LevelPart;
import gameLogic.LevelState;
import model.LevelDescriptor;
import renderer.Renderer;

public class GameScene implements IDrawableGameComponent {
	// Private fields
	private GameSceneState state;
	private GameSceneAction action;
	private Boolean isQuitting = false;
	private Boolean isLevelChanging = false;
	private float slideOutStarted;

	private Level level;

	private HashMap<LevelPart, LevelPartComponent> levelPartComponents = new HashMap<LevelPart, LevelPartComponent>();
	private DoorComponent door;
	private ArrayList<KeyComponent> keys = new ArrayList<KeyComponent>();
	private PlayerComponent player1;
	private PlayerComponent player2;
	private LevelSceneCamera camera;

	// Getters and setters
	public GameSceneAction getAction() {
		return this.action;
	}

	// Public methods
	public void initalize(int levelIndex) {
		Levels.setCurrentLevelIndex(levelIndex);
		this.action = GameSceneAction.None;
		this.state = GameSceneState.Sliding;
		this.isQuitting = false;
		this.isLevelChanging = false;

		// Create the new level
		try {
			LevelDescriptor descriptor = LevelDescriptor.load(Levels.FileNames[levelIndex]);
			this.level = new Level(descriptor);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Create LevelPartComponents
		this.levelPartComponents.clear();
		LevelPart[][] parts = this.level.getParts();
		for (int i = 0; i < parts.length; i++) {
			for (int j = 0; j < parts[i].length; j++) {
				if (parts[i][j] != null) {
					LevelPartComponent lpc = new LevelPartComponent(parts[i][j]);
					this.levelPartComponents.put(parts[i][j], lpc);
				}
			}
		}

		// Create door
		this.door = new DoorComponent(this.level.getDoor(), this.levelPartComponents);

		// Crate keys
		this.keys.clear();
		KeyHolder[] keyHolders = this.level.getKeyHolders();
		for (KeyHolder keyHolder : keyHolders) {
			KeyComponent keyComponent = new KeyComponent(keyHolder, this.levelPartComponents);
			this.keys.add(keyComponent);
		}

		// Create players
		this.player1 = new PlayerComponent(this.level.getPlayer1(), false, this.levelPartComponents);
		this.player2 = new PlayerComponent(this.level.getPlayer2(), true, this.levelPartComponents);
		this.player1.setIsEnabled(false);
		this.player2.setIsEnabled(false);

		// Create camera
		this.camera = new LevelSceneCamera(this.player1, this.player2, this.level.getWidth(), this.level.getHeight());
		this.camera.setMode(LevelSceneCameraMode.Distance);
	}

	@Override
	public void update(GameTime gameTime) {
		this.action = GameSceneAction.None;
		
		if (this.isQuitting) {
			if (gameTime.getTotalTime() - this.slideOutStarted > 0.24f) {
				this.action = GameSceneAction.Quit;
			}
		} else if (this.isLevelChanging) {
			if (gameTime.getTotalTime() - this.slideOutStarted > 0.24f) {
				int currentLevelIndex = Levels.getCurrentLevelIndex();
				currentLevelIndex++;
				initalize(currentLevelIndex);
			}
		}else {
			handleInput(gameTime);
			
			this.level.update();
			if (this.level.getState() == LevelState.Completed) {
				int currentLevelIndex = Levels.getCurrentLevelIndex();
				currentLevelIndex++;
				if (currentLevelIndex >= Levels.FileNames.length) {
					this.camera.setMode(LevelSceneCameraMode.SlideUp);
					this.slideOutStarted = gameTime.getTotalTime();
					this.isQuitting = true;
				} else {
					this.camera.setMode(LevelSceneCameraMode.SlideDown);
					this.slideOutStarted = gameTime.getTotalTime();
					this.isLevelChanging = true;
				}
			} else {	
				for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {
					levelPartComponent.update(gameTime);
				}
				this.door.update(gameTime);
				for (KeyComponent keyComponent : this.keys) {
					keyComponent.update(gameTime);
				}
				this.player1.update(gameTime);
				this.player2.update(gameTime);
			}
		}
		this.camera.update(gameTime);
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.setTransform(this.camera.getTransform());
		for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {
			levelPartComponent.draw(gameTime, renderer);
		}
		this.door.draw(gameTime, renderer);
		for (KeyComponent keyComponent : this.keys) {
			keyComponent.draw(gameTime, renderer);
		}
		this.player1.draw(gameTime, renderer);
		this.player2.draw(gameTime, renderer);
		for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {
			levelPartComponent.drawBorder(renderer);
		}
	}

	private void handleInput(GameTime gameTime) {
		KeyboardState keyboardState = KeyboardState.getInstance();
		if (keyboardState.isKeyDown("Escape")) {
			this.camera.setMode(LevelSceneCameraMode.SlideUp);
			this.slideOutStarted = gameTime.getTotalTime();
			this.isQuitting = true;
		}
		
		if (this.state == GameSceneState.Sliding) {
			if (keyboardState.isKeyJustPressed("Left"))
				this.level.slide(Direction.Left);
			else if (keyboardState.isKeyJustPressed("Up"))
				this.level.slide(Direction.Up);
			else if (keyboardState.isKeyJustPressed("Right"))
				this.level.slide(Direction.Right);
			else if (keyboardState.isKeyJustPressed("Down"))
				this.level.slide(Direction.Down);
			else if (keyboardState.isKeyJustPressed("Space")) {
				this.state = GameSceneState.Running;
				this.camera.setMode(LevelSceneCameraMode.Follow);
				this.player1.setIsEnabled(true);
				this.player2.setIsEnabled(true);
			}
		} else {
			if (keyboardState.isKeyJustPressed("Space")) {
				this.state = GameSceneState.Sliding;
				this.camera.setMode(LevelSceneCameraMode.Distance);
				this.player1.setIsEnabled(false);
				this.player2.setIsEnabled(false);
			}
		}
	}
}
