package main.java.ru.clevertec.check.Logging;

import main.java.ru.clevertec.check.Abstractions.ILogger;

public class ConsoleLogger implements ILogger {
    @Override
    public void logInfo(String message) {
        WriteMessage(message);
    }

    @Override
    public void logError(String message) {
        WriteMessage("ERROR");
        WriteMessage(message);
    }
    
    private void WriteMessage(String message) {
        System.out.println(message);
    }
}
