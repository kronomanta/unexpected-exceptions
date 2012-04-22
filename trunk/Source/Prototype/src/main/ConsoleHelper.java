package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleHelper {

	private static BufferedReader inputReader = new BufferedReader(
			new InputStreamReader(System.in));

	public static String readLine() {
		String line = "";
		try {
			line = inputReader.readLine();
			inputReader.mark(1);
			if (inputReader.ready()) { // This solves java's strange behavior of reading CRLF.
				if (inputReader.read() != '\n')
					inputReader.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
