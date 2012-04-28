package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private BufferedImage image = null;

	public int getWidth() {
		return this.image.getWidth();
	}

	public int getHeight() {
		return this.image.getHeight();
	}

	public BufferedImage getBufferedImage() {
		return this.image;
	}
	
	private Image(BufferedImage image) {
		this.image = image;
	}

	public static Image loadFromFile(String path) {
		try {
			File file = new File(path);
			BufferedImage image = ImageIO.read(file);
			return new Image(image);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}