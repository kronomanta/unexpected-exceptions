package gameLogic;

/**
 * A Level objektum állapotai.
 * 
 * @author
 */
public enum LevelState {
	Normal, // A játék alapállapota, még nincs összeszedve minden kulcs.
	CanComplete, // Megvan minden kulcs, az ajtót elérve megoldódik a puzzle.
	Completed, // A puzzle megoldva.
}
