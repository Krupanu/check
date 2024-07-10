package main.java.ru.clevertec.check.Logging;

import main.java.ru.clevertec.check.Abstractions.ILogger;

import java.util.ArrayList;
import java.util.List;

public class ConsolidateLogger implements ILogger {
    private final List<ILogger> loggers = new ArrayList<ILogger>();
    
    public void Register(ILogger logger) {
        loggers.add(logger);
    }

    @Override
    public void logInfo(String message) {
        for (var logger: loggers) {
            logger.logInfo(message);
        }
    }

    @Override
    public void logError(String message) {
        for (var logger: loggers) {
            logger.logError(message);
        }
    }
}
