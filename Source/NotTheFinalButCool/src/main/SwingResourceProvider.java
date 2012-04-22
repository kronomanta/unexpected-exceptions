package main;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import engine.IContentProvider;
import engine.Image;

public class SwingResourceProvider implements IContentProvider {

	@Override
	public Image loadImage(String path) {
		return new SwingImage(path);
	}

	@Override
	public String loadText(String path) {
		StringBuilder sb = new StringBuilder(512);
		try {
			FileInputStream fs = new FileInputStream(path);
			InputStreamReader sr = new InputStreamReader(fs, "UTF-8");
			int c = 0;
			while (c != -1) {
				c = sr.read();
				sb.append((char) c);
			}
			sr.close();
			fs.close();
		} catch (Exception e) {
		}

		return sb.toString();
	}

	@Override
	public void saveText(String path, String text) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
			out.write(text);
			out.close();
		} catch (Exception e) {
		}
	}
}
