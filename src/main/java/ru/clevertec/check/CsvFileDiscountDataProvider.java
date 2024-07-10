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
            String line ;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String separator = ";";
                String[] values = line.split(separator);

                int id = Integer.parseInt(values[0]);
                String description = values[1];
                double price = Double.parseDouble(values[2]);
                int quantityInStock = Integer.parseInt(values[3]);
                boolean wholesaleProduct = Boolean.parseBoolean(values[4]);

                _discountCards.put(id, new Item(id, description, price, quantityInStock, wholesaleProduct));
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
