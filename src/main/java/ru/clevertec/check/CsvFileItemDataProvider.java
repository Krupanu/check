package main.java.ru.clevertec.check;
import Abstractions.IDataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class CsvFileItemDataProvider implements IDataProvider<Item> {

    private final HashMap<Integer, Item> _items;

    public CsvFileItemDataProvider(String path) {
        _items = new HashMap<>();
        ReadFile(path);
    }

    private void ReadFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String separator = ";";
                String[] values = line.split(separator);

                int id = Integer.parseInt(values[0]);
                String description = values[1];
                double price = Double.parseDouble(values[2]);
                int quantityInStock = Integer.parseInt(values[3]);
                boolean wholesaleProduct = Boolean.parseBoolean(values[4]);

                _items.put(id, new Item(id, description, price, quantityInStock, wholesaleProduct));
            }

        } catch (IOException e) {
            System.out.println("ERROR");
            String exceptionResultFilePath = "C:\\github\\check";
            String fileName = "result.csv.txt";
            String fullPath = Paths.get(exceptionResultFilePath, fileName).toString();
            String textToFile = "BAD REQUEST";
            var exceptionWriter = new ExceptionWriter();
            exceptionWriter.WriteExceptionToCsv("BAD REQUEST",exceptionResultFilePath);
            System.out.println("Data has been written");
            System.exit(0);
        }
    }

    @Override
    public Item Get(Integer id) {
        return _items.get(id);
    }

    @Override
    public List<Item> GetAll() {
        return _items.values().stream().toList();
    }
}
