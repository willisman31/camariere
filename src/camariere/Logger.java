package camariere;

import java.io.File;
import java.time.LocalDate;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class Logger {

	File outFile;
	
	public Logger() {
		LocalDate today = LocalDate.now();
		String outFileName = "camariere-" + today.toString() + ".log";
		this.outFile = new File(outFileName);
	}
	
	public Logger(String fileName) {
		this.outFile = new File(fileName);
	}
	
	public int log(String message) {
		try {
			FileOutputStream logFile = new FileOutputStream(this.outFile, true);
			PrintStream appendLogFile = new PrintStream(logFile);
			PrintStream console = System.out;
			System.setOut(appendLogFile);
			System.out.println(message);
			System.setOut(console);
			System.out.println(message);
		} catch (FileNotFoundException f) {
			System.out.println("Logging to file failed for message: " + message);
			return 0;
		}
		return 1;
	}
	
	public String toString() {
		return this.outFile.getName();
	}
	
}
