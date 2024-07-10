package main.java.ru.clevertec.check.Services;

import main.java.ru.clevertec.check.Abstractions.IDataProvider;
import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Constants.ErrorCodes;
import main.java.ru.clevertec.check.Discounts.DiscountCard;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class CsvFileDiscountDataProvider implements IDataProvider<DiscountCard> {
    private final HashMap<String, DiscountCard> _discountCards;
    private final ILogger _logger;

    public CsvFileDiscountDataProvider(String path, ILogger logger) {
        _discountCards = new HashMap<>();
        _logger = logger;
        ReadFile(path);
    }

    private void ReadFile(String path) {
        _logger.logInfo("Reading from a discount file started");
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String separator = ";";
                String[] values = line.split(separator);

                String discountCardNumber = values[0];
                int discountPercentage = Integer.parseInt(values[1]);

                _discountCards.put(discountCardNumber, new DiscountCard(discountCardNumber, discountPercentage));
            }

            _logger.logInfo("Reading from a discount file started");
            _logger.logInfo("");
        } catch (IOException e) {
            _logger.logError(ErrorCodes.InternalServerError);
            System.exit(0);
        }
    }
    
    public DiscountCard GetByCardNumber(String cardNumber) {
        return  _discountCards.get(cardNumber);
    }

    @Override
    public DiscountCard Get(Integer id) {
        return null;
    }

    @Override
    public List<DiscountCard> GetAll() {
        return _discountCards.values().stream().toList();
    }
}
