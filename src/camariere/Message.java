package camariere;

import java.util.Date;

public class Message {

    LoggingLevel loggingLevel = LoggingLevel.INFO;
    String description;
    String timestamp;

    public Message() {
        this.timestamp = new Date().toString();
    }

    public Message(String desc) {
        this();
        this.description = desc;
    }

    public Message(String desc, LoggingLevel level) {
        this(desc);
        this.loggingLevel = level;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public LoggingLevel getLoggingLevel() {
        return this.loggingLevel;
    }

    public void setLoggingLevel(LoggingLevel level) {
        this.loggingLevel = level;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String toString() {
        return this.timestamp + " | " + this.loggingLevel.toString() + " | " + this.description;
    }
}

