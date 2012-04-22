package main;

import gameLogic.Direction;

public class StoryboardCommand {
	public Boolean PlayerCommand = false;
	public int Player;
	public Boolean MoveLeft = false;
	public Boolean MoveRight = false;
	public Boolean Stop = false;
	public Boolean Jump = false;
	
	public Boolean SlideCommand = false;
	public Direction SlideDirection;
	
	public Boolean GoCommand = false;
	public int Frames;
}
