package main.java.ru.clevertec.check.Services;
import main.java.ru.clevertec.check.Abstractions.IDataProvider;
import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Constants.ErrorCodes;
import main.java.ru.clevertec.check.Models.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class CsvFileItemDataProvider implements IDataProvider<Item> {

    private final HashMap<Integer, Item> _items;
    private final ILogger _logger;

    public CsvFileItemDataProvider(String path, ILogger logger) {
        _items = new HashMap<>();
        _logger = logger;
        
        ReadFile(path);
    }

    private void ReadFile(String path) {
        _logger.logInfo("Reading from an items file started");
        
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

            _logger.logInfo("Reading from an items file ended");
            _logger.logInfo("");
        } catch (IOException e) {
            _logger.logInfo("Error while reading items file");
            _logger.logError(ErrorCodes.InternalServerError);
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
