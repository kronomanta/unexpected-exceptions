package gameLogic;

/**
 * A Player ugrásállapotai.
 * 
 * @author
 */
public enum PlayerJumpState {
	CanJump, // Akármikor ugorhat, ha épp a földön áll.
	CannotJump, // Még ha földön is áll, akkor se tud még ugorni.
	WaitsForJump, // Ahogy a földre érkezik, azonnal ugrásba kezd.
}
