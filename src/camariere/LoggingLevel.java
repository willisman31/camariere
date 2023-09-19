package camariere;

public enum LoggingLevel {
	ALL(7),
	TRACE(6),
	DEBUG(5),
	INFO(4),
	WARN(3),
	ERROR(2),
	FATAL(1),
	OFF(0);
	
	private final int level;
	
	LoggingLevel(int val) {
		this.level = val;
	}
	
	public int level() {
		return this.level;
	}
}
