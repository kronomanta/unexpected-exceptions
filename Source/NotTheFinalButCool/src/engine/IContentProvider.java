package engine;

public interface IContentProvider {
	Image loadImage(String path);
	String loadText(String path);
	void saveText(String path, String text);
}
