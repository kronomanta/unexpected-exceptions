package main;

import java.util.ArrayList;

import model.BlockType;

import gameLogic.Block;
import gameLogic.Door;
import gameLogic.KeyHolder;
import gameLogic.Level;
import gameLogic.LevelPart;
import gameLogic.Player;

public class LevelRenderer {	
	public static void render(Level level) {
		int width = level.getWidth();
		int height = level.getHeight();
		
		String[][][] levelPartTexts = new String[width][height][];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				levelPartTexts[i][j] = renderLevelPart(level, level.getParts()[i][j]);
			}
		}
		
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < height; y++) {
			for (int line = 0; line < 10 * Main.Resolution + 2; line++) {
				for (int x = 0; x < width; x++) {
					buffer.append(levelPartTexts[x][y][line]);
				}
				buffer.append(System.getProperty("line.separator"));
			}
		}
		System.out.print(buffer.toString());
	}
	
	public static String[] renderLevelPart(Level level, LevelPart levelPart) {
		int size = 10 * Main.Resolution + 2;
		
		if (levelPart == null) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < size; i++)
				buffer.append(" ");
			
			String[] text = new String[size];
			for (int i = 0; i < size; i++)
				text[i] = buffer.toString();
			return text;
		}
		
		Player player1 = level.getPlayer1();
		Player player2 = level.getPlayer2();
		if (player1.getLevelPart() != levelPart)
			player1 = null;
		if (player2.getLevelPart() != levelPart)
			player2 = null;
		
		Door door = level.getDoor();
		if (door.getLevelPart() != levelPart)
			door = null;
		
		KeyHolder[] allKeyHolders = level.getKeyHolders();
		ArrayList<KeyHolder> keyHolders = new ArrayList<KeyHolder>();
		for (KeyHolder kh : allKeyHolders) {
			if (kh.getHasKey() && kh.getLevelPart() == levelPart)
				keyHolders.add(kh);
		}
		
		float step = 1.0f / Main.Resolution;
		float start = 1.0f / Main.Resolution / 2.0f;
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++)
			buffer.append("-");
		
		String[] text = new String[size];
		text[0] = buffer.toString();
		text[size - 1] = buffer.toString();
		for (int i = 0; i < 10 * Main.Resolution; i++) {
			buffer = new StringBuffer();
			buffer.append("|");
			for (int j = 0; j < 10 * Main.Resolution; j++) {
				float x = start + j * step;
				float y = start + i * step;
				
				if (player1 != null && player1.getBounds().contains(x, y)) {
					buffer.append("1");
				} else if (player2 != null && player2.getBounds().contains(x, y)) {
					buffer.append("2");
				} else if (door != null && door.getBounds().contains(x, y)) {
					buffer.append("D");
				} else {
					Boolean keyFound = false;
					for (KeyHolder kh : keyHolders) {
						if (kh.getBounds().contains(x, y)) {
							buffer.append("K");
							keyFound = true;
							break;
						}
					}
					
					if (!keyFound) {
						Boolean blockFound = false;
						for (Block block : levelPart.getBlocks()) {
							if (block.getBounds().contains(x, y)) {
								if (block.getType() == BlockType.LeftRamp)
									buffer.append("/");
								else if (block.getType() == BlockType.RightRamp)
									buffer.append("\\");
								else
									buffer.append("X");
								
								blockFound = true;
								break;
							}
						}
						
						if (!blockFound) {
							buffer.append(" ");
						}
					}
				}
			}
			buffer.append("|");
			text[i + 1] = buffer.toString();
		}
		return text;
	}
}
