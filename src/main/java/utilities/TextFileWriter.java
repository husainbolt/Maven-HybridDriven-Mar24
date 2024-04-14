package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {
	
	public static void writeInText(String text) {
		File file = new File("retryTestCases.txt");
		try {
			FileWriter writer = new FileWriter(file, true);
			writer.write(text + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
