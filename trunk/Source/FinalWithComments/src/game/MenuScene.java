package game;

import game.renderer.Image;
import game.renderer.Point;
import game.renderer.Renderer;

import java.util.EnumSet;

import main.KeyboardState;

public class MenuScene implements IDrawableGameComponent {
	// Private fields
	private Image introImage;
	private Image backgroundImage;
	private Image menuImage;
	private Image helpImage;

	private Point introImagePosition = new Point(0.0f, Constants.CanvasHeight);
	private Point backgroundImagePosition = new Point(0.0f, Constants.CanvasHeight);
	private Point menuImagePosition = new Point(0.0f, Constants.CanvasHeight);
	private Point helpImagePosition = new Point(Constants.CanvasWidth, 0.0f);

	private MenuSceneAction action;
	private EnumSet<MenuSceneState> state;
	private MenuSceneAction menuOutAction;

	private Animator introAnimator = new Animator();
	private Animator backgroundImageAnimator = new Animator();
	private Animator menuImageAnimator = new Animator();
	private Animator helpImageAnimator = new Animator();

	// Getters and setters
	public MenuSceneAction getAction() {
		return this.action;
	}

	// Constructors
	public MenuScene() {
		this.introImage = Image.loadFromFile("data/Intro.jpg");				// load menu images
		this.backgroundImage = Image.loadFromFile("data/MenuBackground.jpg");
		this.menuImage = Image.loadFromFile("data/Menu.png");
		this.helpImage = Image.loadFromFile("data/Help.png");

		this.action = MenuSceneAction.None;						// initialize menu state
		this.state = EnumSet.of(MenuSceneState.Initial);				// call intro screen
	}

	// Public methods
	@Override
	public void update(GameTime gameTime) {
		Boolean running = false;
		float elapsedTime = gameTime.getElapsedTime();
		KeyboardState ks = KeyboardState.getInstance();							// check if key has been pressed

		Boolean in = this.state.contains(MenuSceneState.In);
		Boolean out = this.state.contains(MenuSceneState.Out);

		this.action = MenuSceneAction.None;
		if (this.state.contains(MenuSceneState.Initial)) {						// is intro screen called?
			this.introAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_IntroInInterval, AnimatorMode.EaseIn);	// intro screen starts sliding in
			this.state = EnumSet.of(MenuSceneState.Intro, MenuSceneState.In);			// intro screen state updated
		} else if (this.state.contains(MenuSceneState.Intro)) {
			if (!out) {
				if (ks.isKeyJustPressed("Space") || ks.isKeyJustPressed("Enter") || ks.isKeyJustPressed("Escape")) {	// menu screen called
					this.introAnimator.start(this.introImagePosition, new Point(0.0f, -Constants.CanvasHeight), Constants.MenuScene_IntroOutInterval, AnimatorMode.EaseOut);	// intro screen sliding out
					this.state = EnumSet.of(MenuSceneState.Intro, MenuSceneState.Out);	// intro screen state updated
				}
			}

			if (in) {										// intro screen sliding in
				running = this.introAnimator.update(elapsedTime, this.introImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Intro);
				}
			} else if (out) {									// intro screen sliding out
				running = this.introAnimator.update(elapsedTime, this.introImagePosition);
				if (!running) {
					this.backgroundImageAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_MenuInInterval, AnimatorMode.EaseIn);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.In);
				}
			}
		} else if (this.state.contains(MenuSceneState.Menu)) {						// is main menu called?
			if (in) {										// main menu slides in
				running = this.backgroundImageAnimator.update(elapsedTime, this.backgroundImagePosition);
				this.menuImagePosition = new Point(this.backgroundImagePosition.getX(), this.backgroundImagePosition.getY());
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Menu);				// main menu state updated
				}
			} else if (out) {									// main menu slides out
				running = this.backgroundImageAnimator.update(elapsedTime, this.backgroundImagePosition);
				this.menuImagePosition = new Point(this.backgroundImagePosition.getX(), this.backgroundImagePosition.getY());
				if (!running) {
					this.backgroundImageAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_MenuInInterval, AnimatorMode.EaseIn);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.In);	// main menu slides in
					this.action = this.menuOutAction;
				}
			} else {
				if (ks.isKeyJustPressed("H")) {							// help is called
					this.menuImageAnimator.start(new Point(), new Point(-Constants.CanvasWidth, 0.0f), 0.5f, AnimatorMode.EaseOut);			// menu starts sliding out
					this.helpImageAnimator.start(new Point(Constants.CanvasWidth, 0.0f), new Point(), 0.5f, AnimatorMode.EaseIn);			// help starts sliding in
					this.state = EnumSet.of(MenuSceneState.Help, MenuSceneState.In);	// menu screen state updated
				} else if (ks.isKeyJustPressed("C")) {						// continue previous game is called
					this.backgroundImageAnimator.start(new Point(), new Point(0.0f, Constants.CanvasHeight), 0.5f, AnimatorMode.EaseOut);		// menu starts sliding out
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.Out);	// menu screen state updated
					this.menuOutAction = MenuSceneAction.Continue;
				} else if (ks.isKeyJustPressed("S")) {						// start new game is called
					this.backgroundImageAnimator.start(new Point(), new Point(0.0f, Constants.CanvasHeight), 0.5f, AnimatorMode.EaseOut);		// menu starts sliding out
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.Out);	// menu screen state updated
					this.menuOutAction = MenuSceneAction.StartNew;
				}
			}
		} else if (this.state.contains(MenuSceneState.Help)) {						// is help called?
			if (in) {										// help slides in
				running = this.menuImageAnimator.update(elapsedTime, this.menuImagePosition);
				running = running || this.helpImageAnimator.update(elapsedTime, this.helpImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Help);
				}
			} else if (out) {									// help slides out
				running = this.helpImageAnimator.update(elapsedTime, this.helpImagePosition);
				running = running || this.menuImageAnimator.update(elapsedTime, this.menuImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Menu);
				}
			} else {										// help screen
				if (ks.isKeyJustPressed("Space") || ks.isKeyJustPressed("Enter") || ks.isKeyJustPressed("Escape")) {	// exit help screen
					this.menuImageAnimator.start(this.menuImagePosition, new Point(), 0.5f, AnimatorMode.EaseIn);
					this.helpImageAnimator.start(this.helpImagePosition, new Point(Constants.CanvasWidth, 0.0f), 0.5f, AnimatorMode.EaseOut);
					this.state = EnumSet.of(MenuSceneState.Help, MenuSceneState.Out);
				}
			}
		}
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {						// draw function calls renderers' drawimages
		renderer.drawImage(this.introImagePosition, this.introImage);					// to each static menu image
		renderer.drawImage(this.backgroundImagePosition, this.backgroundImage);
		renderer.drawImage(this.menuImagePosition, this.menuImage);
		renderer.drawImage(this.helpImagePosition, this.helpImage);
	}
}
