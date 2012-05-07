package gameLogic;

// enumeration for states of given level
public enum LevelState {
	Normal, // default state, player has yet to collect all the keys
	CanComplete, // player has collected all the keys, the door object is now active as an exit point
	Completed, // exit point reached
}
