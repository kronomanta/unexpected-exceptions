package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import gameLogic.Direction;
import gameLogic.Level;
import model.LevelDescriptor;
 
public class Main {
	public static void main(String[] args) {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(isr);
			
			SkeletonHelper.turnOff();
			LevelDescriptor descriptor = LevelDescriptor.load("");
			Level level = new Level(descriptor);
			SkeletonHelper.turnOn();
			
			while (true) {
				System.out.println("A valaszthato lefutasok:");
				System.out.println("1 - Inicializalas");
				System.out.println("2 - Player update");
				System.out.println("3 - Level update");
				System.out.println("4 - Slide");
				System.out.println("------------------");
				System.out.println("0 - Kilepes");
				System.out.println();
				System.out.print("Valasszon egyet: ");
				
				String input = reader.readLine().trim();
				System.out.println();
				if (input.equals("1")) {
					SkeletonHelper.comment("Beolvassa a palyaleirot egy fajlbol.");
					descriptor = LevelDescriptor.load("");
					SkeletonHelper.space();
					SkeletonHelper.comment("Es az alapjan letrehozza a Level objektumot.");
					level = new Level(descriptor);
				} else if (input.equals("2")) {
					level.getPlayer().update(0.1f, false, false, false);
				} else if (input.equals("3")) {
					level.update();
				} else if (input.equals("4")) {
					level.slide(Direction.Left);
				} else if (input.equals("0")) {
					break;
				} else {
					System.out.println("Hibas bemenet.");
				}
				System.out.println();
			}
			System.out.println("Viszlat!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}