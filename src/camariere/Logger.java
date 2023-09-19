package camariere;

import java.io.File;
import java.time.LocalDate;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class Logger {

	File outFile;
	LoggingLevel loggingLevel;
	boolean logToConsole;
	
	public Logger() {
		LocalDate today = LocalDate.now();
		String outFileName = "camariere-" + today.toString() + ".log";
		this.outFile = new File(outFileName);
		this.loggingLevel = LoggingLevel.ALL;
		this.logToConsole = true;
	}
	
	public int log(String message) {
		try {
			FileOutputStream logFile = new FileOutputStream(this.outFile, true);
			PrintStream appendLogFile = new PrintStream(logFile);
			PrintStream console = System.out;
			System.setOut(appendLogFile);
			System.out.println(message);
			if (this.logToConsole) {
				System.setOut(console);
				System.out.println(message);
			}
		} catch (FileNotFoundException f) {
			System.out.println("Logging to file failed for message: " + message);
			return 0;
		}
		return 1;
	}
	
	public int log(Message message) {
		if (message.getLoggingLevel().level() == 0) return 0;
		try {
			FileOutputStream logFile = new FileOutputStream(this.outFile, true);
			PrintStream appendLogFile = new PrintStream(logFile);
			PrintStream console = System.out;
			if (message.getLoggingLevel().level() <= this.getLoggingLevel().level()) {
				System.setOut(appendLogFile);
				System.out.println(message.toString());
				if (this.logToConsole) {
					System.setOut(console);
					System.out.println(message.toString());
				}
			}
		} catch (FileNotFoundException f) {
			System.out.println("Logging to file failed for message: " + message.toString());
			return 0;
		}
		return 1;
	}
	
	public void setLoggingLevel(LoggingLevel level) {
		this.loggingLevel = level;
	}
	
	public LoggingLevel getLoggingLevel() {
		return this.loggingLevel;
	}
	
	public void toggleLogToConsole() {
		this.logToConsole = !this.logToConsole;
	}
	
	public boolean getLogToConsole() {
		return this.logToConsole;
	}
	
	public String toString() {
		return this.outFile.getName();
	}
	
}
