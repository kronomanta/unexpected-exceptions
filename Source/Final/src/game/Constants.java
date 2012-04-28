package game;

import java.awt.Color;

import gameLogic.LevelPart;

public class Constants {
	public static final int CanvasWidth = 800;
	public static final int CanvasHeight = 600;
	
	public static final float MenuScene_IntroInInterval = 1.5f;
	public static final float MenuScene_IntroOutInterval = 0.5f;
	public static final float MenuScene_MenuInInterval = 0.5f;
	public static final float MenuScene_MenuOutInterval = 0.5f;
	
	public static final int UnitSize = 54;
	
	public static final int LevelPartSize = LevelPart.Size * UnitSize;
	public static final int LevelPartBorderThickness = 36;
	public static final int LevelPartSpacing = 18;
	public static final float LevelPartSlidingDuration = 0.5f;

	public static final Color BlockColor = new Color(127, 127, 127);
	public static final Color LevelPartBorderColor = new Color(127, 64, 64);
	
	public static final int LevelSceneCameraViewportMarginRate = 100;
	public static final float LevelSceneCameraPlayingMaximumScale = 1.0f;
}
