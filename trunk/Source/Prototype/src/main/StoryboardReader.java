package main;

import gameLogic.Direction;

import java.util.ArrayList;
import java.util.List;

public class StoryboardReader {
	public static List<StoryboardCommand> readCommands() throws Exception {
		List<StoryboardCommand> commands = new ArrayList<StoryboardCommand>();

		Boolean done = false;
		while (!done) {
			try {
				StoryboardCommand command = new StoryboardCommand();

				if (!Main.SilentMode)
					System.out.print("> ");
				String line = ConsoleHelper.readLine();
				if (line == null || line.equals(""))
					return null;

				ArrayList<String> instructionParts = new ArrayList<String>();
				for (String part : line.trim().toLowerCase().split(" ")) {
					part = part.trim();
					if (!part.equals(""))
						instructionParts.add(part);
				}

				String first = instructionParts.get(0);
				if (first.equals("go")) {
					if (instructionParts.size() < 2)
						throw new Exception("Frame count parameter is missing.");

					command.GoCommand = first.equals("go");
					command.Frames = Integer.parseInt(instructionParts.get(1));

					if (command.Frames < 1)
						throw new Exception(
								"Frame count parameter must be greater than 0.");

					done = true;
				} else if (first.equals("p1") || first.equals("p2")) {
					command.PlayerCommand = true;
					command.Player = first.equals("p1") ? 1 : 2;

					if (instructionParts.size() < 2)
						throw new Exception("Missing move type.");

					String second = instructionParts.get(1);
					if (second.equals("jump")) {
						command.Jump = true;
					} else if (second.equals("stop")) {
						command.Stop = true;
					}else if (second.equals("left")) {
						command.MoveLeft = true;
					}else if (second.equals("right")) {
						command.MoveRight = true;
					} else {
						throw new Exception("Unknown move type.");
					}
				} else if (first.equals("slide")) {
					if (commands.size() != 0)
						throw new Exception(
								"Other commands cannot precede slide commands.");

					command.SlideCommand = true;

					if (instructionParts.size() < 2)
						throw new Exception("Missing slide parameter.");

					String second = instructionParts.get(1);
					if (second.equals("left"))
						command.SlideDirection = Direction.Left;
					else if (second.equals("up"))
						command.SlideDirection = Direction.Up;
					else if (second.equals("right"))
						command.SlideDirection = Direction.Right;
					else if (second.equals("down"))
						command.SlideDirection = Direction.Down;
					else
						throw new Exception("Invalid slide direction.");

					done = true;
				} else
					throw new Exception("Unknown instruction: " + first + ".");

				commands.add(command);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		return commands;
	}
}
