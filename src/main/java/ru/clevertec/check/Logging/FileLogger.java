package main.java.ru.clevertec.check.Logging;

import main.java.ru.clevertec.check.Abstractions.ILogger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements ILogger {
    private final String filename;

    public FileLogger(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void logInfo(String message) {
        // Does not require in this case
    }

    @Override
    public void logError(String message) {
        try {
            var fileWriter = new FileWriter(filename, true);
            var writer = new PrintWriter(fileWriter);

            writer.println("ERROR");
            writer.println(message);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
