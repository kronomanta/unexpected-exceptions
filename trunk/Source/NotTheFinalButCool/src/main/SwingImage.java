package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import engine.Image;

public class SwingImage extends Image {
	private BufferedImage image = null;

	@Override
	public int getWidth() {
		return this.image.getWidth();
	}

	@Override
	public int getHeight() {
		return this.image.getHeight();
	}

	public SwingImage(String path) {

		URL url = ClassLoader.getSystemResource(path);

		try {
			this.image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getBufferedImage() {
		return this.image;
	}
}
