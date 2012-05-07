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
import game.renderer.Renderer;
import gameLogic.Direction;
import gameLogic.KeyHolder;
import gameLogic.Level;
import gameLogic.LevelPart;
import gameLogic.LevelState;
import model.LevelDescriptor;

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
		Levels.setCurrentLevelIndex(levelIndex);		// Set # of level
		this.action = GameSceneAction.None;
		this.state = GameSceneState.Sliding;			// Set gamescene state to puzzle mode
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
			if (gameTime.getTotalTime() - this.slideOutStarted > 0.24f) {		// let current screen slide out for 0.24 seconds
				this.action = GameSceneAction.Quit;				// quit current game
			}
		} else if (this.isLevelChanging) {
			if (gameTime.getTotalTime() - this.slideOutStarted > 0.24f) {		// let current screen slide out for 0.24 seconds
				int currentLevelIndex = Levels.getCurrentLevelIndex();
				currentLevelIndex++;						// set level index to next level
				initalize(currentLevelIndex);					// call initialization of next level
			}
		}else {
			handleInput(gameTime);							// handle the rest of the actions
			
			this.level.update();							// update level
			if (this.level.getState() == LevelState.Completed) {			// if current level is completed
				int currentLevelIndex = Levels.getCurrentLevelIndex();
				currentLevelIndex++;						// step to next level
				if (currentLevelIndex >= Levels.FileNames.length) {		// if no more levels
					this.camera.setMode(LevelSceneCameraMode.SlideUp);	// current screen slides up
					this.slideOutStarted = gameTime.getTotalTime();		// set up time for screen slide
					this.isQuitting = true;					// end current game
				} else {							// otherwise
					this.camera.setMode(LevelSceneCameraMode.SlideDown);	// current screen slides down
					this.slideOutStarted = gameTime.getTotalTime();		// set up time for screen slide
					this.isLevelChanging = true;				// new level begins
				}
			} else {								// if level is not completed
				for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {
					levelPartComponent.update(gameTime);			// reload then update all levelpart components
				}
				this.door.update(gameTime);					// update door
				for (KeyComponent keyComponent : this.keys) {			// update keys
					keyComponent.update(gameTime);
				}
				this.player1.update(gameTime);					// update players
				this.player2.update(gameTime);
			}
		}
		this.camera.update(gameTime);							// update camera
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.setTransform(this.camera.getTransform());				// transform seen image according to camera's attributes
		for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {	// draw levelpart components
			levelPartComponent.draw(gameTime, renderer);
		}
		this.door.draw(gameTime, renderer);							// draw door
		for (KeyComponent keyComponent : this.keys) {						// draw keys
			keyComponent.draw(gameTime, renderer);
		}
		this.player1.draw(gameTime, renderer);							// draw players
		this.player2.draw(gameTime, renderer);
		for (LevelPartComponent levelPartComponent : this.levelPartComponents.values()) {	// draw borders of the levelpart components
			levelPartComponent.drawBorder(renderer);
		}
	}

	private void handleInput(GameTime gameTime) {
		KeyboardState keyboardState = KeyboardState.getInstance();			// get keyboard commands
		if (keyboardState.isKeyDown("Escape")) {
			this.camera.setMode(LevelSceneCameraMode.SlideUp);
			this.slideOutStarted = gameTime.getTotalTime();
			this.isQuitting = true;							// quit from current game
		}
		
		if (this.state == GameSceneState.Sliding) {					// if in puzzle mode, slide components according to key pressed
			if (keyboardState.isKeyJustPressed("Left"))				// handling levelpart movements
				this.level.slide(Direction.Left);
			else if (keyboardState.isKeyJustPressed("Up"))
				this.level.slide(Direction.Up);
			else if (keyboardState.isKeyJustPressed("Right"))
				this.level.slide(Direction.Right);
			else if (keyboardState.isKeyJustPressed("Down"))
				this.level.slide(Direction.Down);
			else if (keyboardState.isKeyJustPressed("Space")) {			// if in puzzle mode and space pressed switch to platform mode
				this.state = GameSceneState.Running;
				this.camera.setMode(LevelSceneCameraMode.Follow);
				this.player1.setIsEnabled(true);
				this.player2.setIsEnabled(true);
			}
		} else {
			if (keyboardState.isKeyJustPressed("Space")) {				// if in platform mode and space pressed switch to puzzle mode
				this.state = GameSceneState.Sliding;
				this.camera.setMode(LevelSceneCameraMode.Distance);
				this.player1.setIsEnabled(false);
				this.player2.setIsEnabled(false);
			}									// note: player movement is being handled in the PlayerComponent class
		}
	}
}
