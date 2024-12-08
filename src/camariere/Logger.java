package camariere;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class Logger {

    File outFile;
    LoggingLevel loggingLevel;
    boolean logToConsole;

    public Logger() {
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
            return 1;
        }
        return 0;
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
            return 1;
        }
        return 0;
    }

    public int log(Exception exception, LoggingLevel level) {
        try {
            FileOutputStream logFile = new FileOutputStream(this.outFile, true);
            PrintStream appendLogFile = new PrintStream(logFile);
            PrintStream console = System.out;
            if (level.level() <= this.getLoggingLevel().level()) {
                System.setOut(appendLogFile);
                System.out.println(exception.toString());
                if (this.logToConsole) {
                    System.setOut(console);
                    System.out.println(exception.toString());
                }
            }
        } catch (FileNotFoundException f) {
            System.out.println("Logging to file failed for message: " + exception.toString());
            return 1;
        }
        return 0;
    }

    private boolean createLogOutputFile(String outputFilePath)
        throws IOException {

        // Create log file with 644 perms by default
        // TODO: make perms, location configurable from config file.
        String outFileName = "/tmp/camariere.log";
        this.outFile = new File(outFileName);
        if (!outfile.isFile()) {
            outfile.createNewFile();
        }
        outfile.setWritable(true, true);
        outfile.setReadable(true, false);
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
