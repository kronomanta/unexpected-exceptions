package engine;

public abstract class DrawableGameComponent extends GameComponent {
	private Boolean isVisible = true;
	//private int drawOrder = 0;
	
	public Boolean getIsVisible() {
		return this.isVisible;
	}
	
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	//public int getDrawOrder() {
	//	return this.drawOrder;
	//}
	
	//public void setDrawOrder(int drawOrder) {
	//	this.drawOrder = drawOrder;
	//}
	
	public abstract void draw(GameTime gameTime, Renderer renderer);
}
