package assignment3;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Recitation we will go over TTD and JUnitifying this
 */

public class SampleTest {


	private static String CLI_INTEGRATION_TESTS_INOUTS = "422_lab4_test/test/data/cli_integration_inouts/";
	private static ByteArrayOutputStream outContent;

	public void ParseCreateLargeCritter(){

		String fileFolder = "create_large_critter";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};

		Main.main(inputs);
		outContent = Main.testOutputString;

		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder +"/expected_output.txt") );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
	}


	String cleanString(String input) {
		String lineSep = System.getProperty("line.separator");
		input = input.replaceAll("critter>", "")
				.replaceAll("\r\n", "\n")
				.replaceAll("critters>", "")
				.replaceAll(lineSep + "\\s+", "\n")
				.replaceAll(lineSep, "\n")
				.replaceAll("(?m)^[ \t]*\r?\n", "") // Remove empty lines
				.replaceAll("(?m)^\\s+", "") // Remove leading spaces/tabs from each line
				.replaceAll("(?m)\\s+$", "") // Remove trailing spaces/tabs from each line
				.trim();
		return input;
	}
}
