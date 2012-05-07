package game.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {								// class for reading image from file
	private BufferedImage image = null;

	public int getWidth() {							// returns image's width
		return this.image.getWidth();
	}

	public int getHeight() {						// returns image's height
		return this.image.getHeight();
	}

	public BufferedImage getBufferedImage() {				// returns buffered image
		return this.image;
	}
	
	private Image(BufferedImage image) {
		this.image = image;
	}

	public static Image loadFromFile(String path) {				// gets image file from path
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