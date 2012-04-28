package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Levels {
	public static final String[] FileNames = new String[] {
		"levels/Level01.xml", "levels/Level02.xml", "levels/Level03.xml", "levels/Level04.xml" };

	public static int getCurrentLevelIndex() {
		try {
			BufferedReader input = new BufferedReader(new FileReader(new File("save.txt")));
			try {
				String line = input.readLine();
				return Integer.parseInt(line);
			} finally {
				input.close();
			}
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public static void setCurrentLevelIndex(int levelIndex) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(new File("save.txt")));
			output.write(String.valueOf(levelIndex));
			output.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
