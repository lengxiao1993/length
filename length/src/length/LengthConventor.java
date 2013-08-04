package length;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LengthConventor {
	public static void main(String[] args) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("input.txt"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);

		}
		scanner.close();
	

	}
}
