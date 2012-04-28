package game;

import java.util.EnumSet;

import main.KeyboardState;
import renderer.Image;
import renderer.Point;
import renderer.Renderer;

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
		this.introImage = Image.loadFromFile("data/Intro.jpg");
		this.backgroundImage = Image.loadFromFile("data/MenuBackground.jpg");
		this.menuImage = Image.loadFromFile("data/Menu.png");
		this.helpImage = Image.loadFromFile("data/Help.png");

		this.action = MenuSceneAction.None;
		this.state = EnumSet.of(MenuSceneState.Initial);
	}

	// Public methods
	@Override
	public void update(GameTime gameTime) {
		Boolean running = false;
		float elapsedTime = gameTime.getElapsedTime();
		KeyboardState ks = KeyboardState.getInstance();

		Boolean in = this.state.contains(MenuSceneState.In);
		Boolean out = this.state.contains(MenuSceneState.Out);

		this.action = MenuSceneAction.None;
		if (this.state.contains(MenuSceneState.Initial)) {
			this.introAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_IntroInInterval, AnimatorMode.EaseIn);
			this.state = EnumSet.of(MenuSceneState.Intro, MenuSceneState.In);
		} else if (this.state.contains(MenuSceneState.Intro)) {
			if (!out) {
				if (ks.isKeyJustPressed("Space") || ks.isKeyJustPressed("Enter") || ks.isKeyJustPressed("Escape")) {
					this.introAnimator.start(this.introImagePosition, new Point(0.0f, -Constants.CanvasHeight), Constants.MenuScene_IntroOutInterval, AnimatorMode.EaseOut);
					this.state = EnumSet.of(MenuSceneState.Intro, MenuSceneState.Out);
				}
			}

			if (in) {
				running = this.introAnimator.update(elapsedTime, this.introImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Intro);
				}
			} else if (out) {
				running = this.introAnimator.update(elapsedTime, this.introImagePosition);
				if (!running) {
					this.backgroundImageAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_MenuInInterval, AnimatorMode.EaseIn);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.In);
				}
			}
		} else if (this.state.contains(MenuSceneState.Menu)) {
			if (in) {
				running = this.backgroundImageAnimator.update(elapsedTime, this.backgroundImagePosition);
				this.menuImagePosition = new Point(this.backgroundImagePosition.getX(), this.backgroundImagePosition.getY());
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Menu);
				}
			} else if (out) {
				running = this.backgroundImageAnimator.update(elapsedTime, this.backgroundImagePosition);
				this.menuImagePosition = new Point(this.backgroundImagePosition.getX(), this.backgroundImagePosition.getY());
				if (!running) {
					this.backgroundImageAnimator.start(new Point(0.0f, Constants.CanvasHeight), new Point(), Constants.MenuScene_MenuInInterval, AnimatorMode.EaseIn);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.In);
					this.action = this.menuOutAction;
				}
			} else {
				if (ks.isKeyJustPressed("H")) {
					this.menuImageAnimator.start(new Point(), new Point(-Constants.CanvasWidth, 0.0f), 0.5f, AnimatorMode.EaseOut);
					this.helpImageAnimator.start(new Point(Constants.CanvasWidth, 0.0f), new Point(), 0.5f, AnimatorMode.EaseIn);
					this.state = EnumSet.of(MenuSceneState.Help, MenuSceneState.In);
				} else if (ks.isKeyJustPressed("C")) {
					this.backgroundImageAnimator.start(new Point(), new Point(0.0f, Constants.CanvasHeight), 0.5f, AnimatorMode.EaseOut);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.Out);
					this.menuOutAction = MenuSceneAction.Continue;
				} else if (ks.isKeyJustPressed("S")) {
					this.backgroundImageAnimator.start(new Point(), new Point(0.0f, Constants.CanvasHeight), 0.5f, AnimatorMode.EaseOut);
					this.state = EnumSet.of(MenuSceneState.Menu, MenuSceneState.Out);
					this.menuOutAction = MenuSceneAction.StartNew;
				}
			}
		} else if (this.state.contains(MenuSceneState.Help)) {
			if (in) {
				running = this.menuImageAnimator.update(elapsedTime, this.menuImagePosition);
				running = running || this.helpImageAnimator.update(elapsedTime, this.helpImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Help);
				}
			} else if (out) {
				running = this.helpImageAnimator.update(elapsedTime, this.helpImagePosition);
				running = running || this.menuImageAnimator.update(elapsedTime, this.menuImagePosition);
				if (!running) {
					this.state = EnumSet.of(MenuSceneState.Menu);
				}
			} else {
				if (ks.isKeyJustPressed("Space") || ks.isKeyJustPressed("Enter") || ks.isKeyJustPressed("Escape")) {
					this.menuImageAnimator.start(this.menuImagePosition, new Point(), 0.5f, AnimatorMode.EaseIn);
					this.helpImageAnimator.start(this.helpImagePosition, new Point(Constants.CanvasWidth, 0.0f), 0.5f, AnimatorMode.EaseOut);
					this.state = EnumSet.of(MenuSceneState.Help, MenuSceneState.Out);
				}
			}
		}
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.drawImage(this.introImagePosition, this.introImage);
		renderer.drawImage(this.backgroundImagePosition, this.backgroundImage);
		renderer.drawImage(this.menuImagePosition, this.menuImage);
		renderer.drawImage(this.helpImagePosition, this.helpImage);
	}
}
