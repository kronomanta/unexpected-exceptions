package engine;

import java.util.HashMap;

public class ContentManager {
	private IContentProvider provider;
	
	private HashMap<String, Image> images;
	private HashMap<String, String> texts;
	
	public ContentManager(IContentProvider provider) {
		this.provider = provider;
		
		this.images = new HashMap<String, Image>();
		this.texts = new HashMap<String, String>();
	}
	
	public Image getImage(String path) {
		if (!this.images.containsKey(path)) {
			Image image = this.provider.loadImage(path);
			this.images.put(path, image);
			return image;
		} else {
			return this.images.get(path);
		}
	}
	
	public String getText(String path) {
		if (!this.images.containsKey(path)) {
			String text = this.provider.loadText(path);
			this.texts.put(path, text);
			return text;
		} else {
			return this.texts.get(path);
		}
	}
	
	public void saveText(String path, String text) {
		this.texts.put(path, text);
		this.provider.saveText(path, text);
	}
}
