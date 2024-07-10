package main.java.ru.clevertec.check;

import Abstractions.IDataProvider;
import main.java.ru.clevertec.check.Discounts.DiscountCards;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class CsvFileDiscountDataProvider implements IDataProvider<DiscountCards> {
    private final HashMap<String, DiscountCards> _discountCards;

    public CsvFileDiscountDataProvider(String path) {
        _discountCards = new HashMap<>();
        ReadFile(path);
    }

    private void ReadFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String separator = ";";
                String[] values = line.split(separator);

                String discountCardNumber = values[0];
                int discountPercentage = Integer.parseInt(values[1]);

                _discountCards.put(discountCardNumber, new DiscountCards(discountCardNumber, discountPercentage));
            }
        } catch (IOException e) {
            System.out.println("ERROR");
            String exceptionResultFilePath = "C:\\github\\check\\result.csv.txt";
            String textToFile = "BAD REQUEST";
            var exceptionWriter = new ExceptionWriter();
            exceptionWriter.WriteExceptionToCsv(textToFile, exceptionResultFilePath);
            System.out.println("Data has been written");
            System.exit(0);
        }
    }

    public boolean isDiscountCardValid(String cardNumber) {
        return _discountCards.containsKey(cardNumber);
    }

    public DiscountCards getDiscountCard(String cardNumber) {
        return _discountCards.get(cardNumber);
    }

    @Override
    public DiscountCards Get(Integer id) {
        return null;
    }

    @Override
    public List<DiscountCards> GetAll() {
        return _discountCards.values().stream().toList();
    }
}
