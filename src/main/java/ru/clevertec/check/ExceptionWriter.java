package main.java.ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionWriter {
    public void WriteExceptionToCsv(String textToFile, String exceptionResultFilePath) {
        try (FileWriter fileWriter = new FileWriter(exceptionResultFilePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("ERROR");
            printWriter.println(textToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
