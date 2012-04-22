package main;

import java.io.File;
import java.util.List;

import gameLogic.Level;
import gameLogic.LevelState;
import gameLogic.Player;
import model.LevelDescriptor;

public class Main {
	public static Boolean SilentMode = false;
	public static Boolean Animate = false;
	public static int Resolution = 1;

	public static void main(String[] args) {
		try {
			if (!processArguments(args))
				return;

			String levelPath = readLevelPath();
			LevelDescriptor descriptor = LevelDescriptor.load(levelPath);
			Level level = new Level(descriptor);
			Player player1 = level.getPlayer1();
			Player player2 = level.getPlayer2();
			LevelRenderer.render(level);

			int fps = readFps();
			float timeStep = 1.0f / fps;

			Boolean player1MoveLeft = false;
			Boolean player1MoveRight = false;
			Boolean player2MoveLeft = false;
			Boolean player2MoveRight = false;

			Boolean testDone = false;
			while (!testDone) {
				if (!SilentMode)
					System.out.println("Commands:");
				List<StoryboardCommand> commands = StoryboardReader.readCommands();
				if (commands == null)
					return;

				if (commands.get(0).SlideCommand) {
					level.slide(commands.get(0).SlideDirection);
					LevelRenderer.render(level);
					continue;
				}

				Boolean player1Jump = false;
				Boolean player2Jump = false;

				int frameCount = 0;
				while (commands.size() > 0) {
					StoryboardCommand nextCommand = commands.get(0);

					if (nextCommand.PlayerCommand) {
						if (nextCommand.Jump) {
							if (nextCommand.Player == 1) {
								player1Jump = true;
							} else {
								player2Jump = true;
							}
						} else {
							if (nextCommand.Player == 1) {
								player1MoveLeft = (player1MoveLeft && !nextCommand.Stop || nextCommand.MoveLeft)
										&& !nextCommand.MoveRight;
								player1MoveRight = (player1MoveRight && !nextCommand.Stop || nextCommand.MoveRight)
										&& !nextCommand.MoveLeft;
							} else if (nextCommand.Player == 2) {
								player2MoveLeft = (player2MoveLeft && !nextCommand.Stop || nextCommand.MoveLeft)
										&& !nextCommand.MoveRight;
								player2MoveRight = (player2MoveRight && !nextCommand.Stop || nextCommand.MoveRight)
										&& !nextCommand.MoveLeft;
							}
						}
					} else if (nextCommand.GoCommand) {
						frameCount = nextCommand.Frames;
					}
					commands.remove(nextCommand);
				}

				for (int currentFrame = 0; currentFrame < frameCount && !testDone; currentFrame++) {
					player1.update(timeStep, player1MoveLeft, player1MoveRight, player1Jump);
					player2.update(timeStep, player2MoveLeft, player2MoveRight, player2Jump);
					level.update();

					if (level.getState() == LevelState.Completed)
						testDone = true;

					if (Animate) {
						System.out.println();
						LevelRenderer.render(level);
						Thread.sleep(1000 / fps);
					} else {
						String info = String.format("%d P1[%d, %d][%.2f, %.2f] P2[%d, %d][%.2f, %.2f] {%s}",
								currentFrame, player1.getLevelPart().getX(), player1.getLevelPart().getY(), player1
										.getLogicalX(), player1.getLogicalY(), player2.getLevelPart().getX(), player2
										.getLevelPart().getY(), player2.getLogicalX(), player2.getLogicalY(), level
										.getState());
						System.out.println(info);
					}

					player1Jump = false;
					player2Jump = false;
				}
				if (!Animate)
					LevelRenderer.render(level);
			}
			System.out.println("Level completed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Boolean processArguments(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--resolution") || args[i].equals("-r")) {
				if (args.length < i + 2) {
					System.out.println("Resolution parameter is missing.");
					return false;
				}

				try {
					i++;
					Resolution = Integer.parseInt(args[i]);
					if (Resolution < 1)
						throw new Exception();
				} catch (Exception e) {
					System.out.println("Resolution must be a not zero positive integer.");
					return false;
				}
			} else if (args[i].equals("--animate") || args[i].equals("-a"))
				Animate = true;
			else if (args[i].equals("--silent") || args[i].equals("-s")) {
				SilentMode = true;
			} else if (args[i].equals("--help") || args[i].equals("-h") || args[i].equals("help")) {
				printHelp();
				return false;
			} else {
				System.out.println("Invalid argument: " + args[i] + ".");
				System.out.println("Run this application using the --help switch.");
				return false;
			}
		}
		return true;
	}

	private static String readLevelPath() {
		String levelPath;
		while (true) {
			if (!SilentMode)
				System.out.println("Path to level:");
			levelPath = ConsoleHelper.readLine();
			File tmp = new File(levelPath);
			if (!tmp.exists())
				System.out.println("File doesn't exists.");
			else
				break;
		}
		return levelPath;
	}

	private static int readFps() {
		int fps = 1;
		while (true) {
			try {
				if (!SilentMode)
					System.out.println("FPS:");

				fps = Integer.parseInt(ConsoleHelper.readLine());
			} catch (Exception e) {
				System.out.println("Fps must be a positive integer.");
				continue;
			}
			if (fps > 0)
				break;
			else
				System.out.println("Fps must be greater than zero.");
		}
		return fps;
	}

	private static void printHelp() {
		System.out.println("");
		System.out.println("Arguments:");
		System.out.println("    --resolution <int>      Resolution means the square root of");
		System.out.println("     -r <int>               the number of characters used to");
		System.out.println("                            render a unit space of the game area.");
		System.out.println("                            Default is 1.");
		System.out.println("");
		System.out.println("    --silent                With this switch you can turn off all");
		System.out.println("     -s                     of the outputs besides the rendering.");
		System.out.println("");
		System.out.println("    --animate               With this switch you can change to");
		System.out.println("     -a                     animation mode. In this mode every");
		System.out.println("                            simulated frames will be rendered in");
		System.out.println("                            real time. The fps of this animation");
		System.out.println("                            will match the simulation fps.");
		System.out.println("");
		System.out.println("    --help                  Using this switch you can display the");
		System.out.println("     -h                     help whenever you need.");
		System.out.println("");
		System.out.println("Commands:");
		System.out.println("    px <movetype>           This is the general syntax of the");
		System.out.println("                            player commands. 'px' can be either");
		System.out.println("                            'p1' or 'p2' meaning which player");
		System.out.println("                            you want to move.");
		System.out.println("");
		System.out.println("    px left                 You can instruct the players to go");
		System.out.println("    px right                left or right. Player will try to go");
		System.out.println("    px stop                 in that direction until you instruct");
		System.out.println("                            to go to the opposite direction or to");
		System.out.println("                            stop.");
		System.out.println("");
		System.out.println("    px jump                 Instructs the given player to jump.");
		System.out.println("");
		System.out.println("    slide left              With these command you can slide a");
		System.out.println("    slide up                level part to the empty space. For");
		System.out.println("    slide right             example the up command will move the");
		System.out.println("    slide down              level part beneath the empty space.");
		System.out.println("");
		System.out.println("    go <frames>             Starts the simulation of the next X");
		System.out.println("                            frames.");
		System.out.println("");
	}
}