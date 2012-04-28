//package game.level;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import model.LevelDescriptor;
//import engine.GameTime;
//import engine.KeyboardState;
//import engine.Scene;
//import game.ContinuityGame;
//import gameLogic.Direction;
//import gameLogic.KeyHolder;
//import gameLogic.Level;
//import gameLogic.LevelPart;
//
//public class LevelScene extends Scene {
//	private ContinuityGame game;
//	private Level level;
//	private State state;
//	
//	private HashMap<LevelPart, LevelPartComponent> levelPartComponents = new HashMap<LevelPart, LevelPartComponent>();
//	private PlayerComponent player1;
//	private PlayerComponent player2;
//	private LevelSceneCamera camera;
//	
//	public Map<LevelPart, LevelPartComponent> getLevelPartComponents() {
//		return Collections.unmodifiableMap(this.levelPartComponents);
//	}
//	
//	public Level getLevel() {
//		return this.level;
//	}
//	
//	public State getState() {
//		return this.state;
//	}
//	
//	public PlayerComponent getPlayer1()  {
//		return this.player1;
//	}
//	
//	public PlayerComponent getPlayer2() {
//		return this.player2;
//	}
//	
//	public LevelSceneCamera getCamera() {
//		return this.camera;
//	}
//	
//	public LevelScene(ContinuityGame game) {
//		this.game = game;
//		this.state = State.Playing;
//	}
//	
//	public void update(GameTime gameTime) {
//		super.update(gameTime);
//		
//		KeyboardState keyboardState = this.getKeyboardState();
//		if (this.state == State.Sliding) {
//			if (keyboardState.isKeyJustPressed("Left"))
//				this.level.slide(Direction.Left);
//			else if (keyboardState.isKeyJustPressed("Up"))
//				this.level.slide(Direction.Up);
//			else if (keyboardState.isKeyJustPressed("Right"))
//				this.level.slide(Direction.Right);
//			else if (keyboardState.isKeyJustPressed("Down"))
//				this.level.slide(Direction.Down);
//			else if (keyboardState.isKeyJustPressed("Space")) {
//				this.state = State.Playing;
//			}
//		} else {
//			if (keyboardState.isKeyJustPressed("Space")) {
//				this.state = State.Sliding;
//			}
//		}
//		
//		this.level.update();
//	}
//	
//	public void setLevel(LevelDescriptor descriptor) throws Exception {
//		// Create the new level
//		this.level = new Level(descriptor);
//		
//		// Remove previous components
//		this.getComponents().clear();
//		this.levelPartComponents.clear();
//		
//		// Create LevelPartComponents
//		LevelPart[][] parts = this.level.getParts();
//		for (int i = 0; i < parts.length; i++) {
//			for (int j = 0; j < parts[i].length; j++) {
//				if (parts[i][j] != null) {
//					LevelPartComponent lpc = new LevelPartComponent(parts[i][j], this);
//					this.levelPartComponents.put(parts[i][j], lpc);
//					addComponent(lpc);	
//				}	
//			}
//		}
//		
//		// Create door
//		DoorComponent doorComponent = new DoorComponent(this.level.getDoor(), this);
//		addComponent(doorComponent);
//		
//		// Crate keys
//		KeyHolder[] keyHolders = this.level.getKeyHolders();
//		for (KeyHolder keyHolder : keyHolders) {
//			KeyComponent keyComponent = new KeyComponent(keyHolder, this);
//			addComponent(keyComponent);
//		}
//
//		// Create players
//		this.player1 = new PlayerComponent(this.level.getPlayer1(), false, this);
//		this.player2 = new PlayerComponent(this.level.getPlayer2(), true, this);
//		addComponent(this.player2);
//		addComponent(this.player1);
//		
//		// Create camera
//		this.camera = new LevelSceneCamera(this.player1, this.player2, this, this.getViewport());
//		addComponent(this.camera);
//		
//		// Create LevelPartBorderComponents
//		for (LevelPartComponent lpc : this.levelPartComponents.values()) {
//			addComponent(new LevelPartBorderComponent(lpc, this));
//		}
//		
//		// Create FrameRateCounterComponent
//		addComponent(new FrameRateCounterComponent());
//	}
//	
//	public enum State {
//		Playing,
//		Sliding,
//	}
//}
