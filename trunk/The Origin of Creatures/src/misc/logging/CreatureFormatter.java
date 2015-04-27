package misc.logging;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class CreatureFormatter extends SimpleFormatter
{
	@Override
	public String format(LogRecord record) {
		return record.getMessage(); // + "\r\n";
		//return new java.util.Date() + "| " + record.getMessage() + "\r\n";
	}

}
