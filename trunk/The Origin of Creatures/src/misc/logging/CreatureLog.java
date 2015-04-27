package misc.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreatureLog
{
	static Logger logger;
	Handler fileHandler;
	Formatter plainText;

	public CreatureLog()
	{
		// instance the logger
		logger = Logger.getLogger(CreatureLog.class.getName());

		// instance the file handler
		try
		{
			fileHandler = new FileHandler("CreatureLog.txt", true);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		// instance formatter, set formatting, and handler
		plainText = new CreatureFormatter();
		fileHandler.setFormatter(plainText);
		logger.addHandler(fileHandler);

	}

	private static Logger getLogger()
	{
		if (logger == null)
		{
			new CreatureLog();
		}
		return logger;
	}

	public static void log(Level level, String msg)
	{
		getLogger().log(level, msg);
		System.out.println(msg);
	}

	public static void info(String msg)
	{
		log(Level.INFO, msg);
	}

	public static void warn(String msg)
	{
		log(Level.WARNING, msg);
	}
}