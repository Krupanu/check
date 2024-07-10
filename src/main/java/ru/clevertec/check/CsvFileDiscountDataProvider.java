package main.java.ru.clevertec.check;

import Abstractions.IDataProvider;
import main.java.ru.clevertec.check.Discounts.DiscountCards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CsvFileDiscountDataProvider implements IDataProvider<DiscountCards> {
    private final HashMap<String, DiscountCards> _discountCards;
    public int _discountAmount ;

    public CsvFileDiscountDataProvider(String path) {
        _discountCards = new HashMap<>();
        ReadFile(path);
    }

    private void ReadFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line ;
            while ((line = br.readLine()) != null) {
                String separator = ";";
                String[] values = line.split(separator);

                String DiscountCardNumber = values[0];

                _discountCards.put(DiscountCardNumber, new DiscountCards(DiscountCardNumber));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDiscountCardValid(String cardNumber) {
        return _discountCards.containsKey(cardNumber);
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
