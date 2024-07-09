package main.java.ru.clevertec.check;

import Abstractions.IDataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

    public class CsvFileDiscountDataProvider implements IDataProvider<Item> {

    private final HashMap<Integer, Item> _discountCards;

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

                _discountCards.put(Integer.parseInt(values[0]), new Item(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), Boolean.parseBoolean(values[3])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item Get(Integer id) {
        return _discountCards.get(id);
    }

    @Override
    public List<Item> GetAll() {
        return _discountCards.values().stream().toList();
    }
}
