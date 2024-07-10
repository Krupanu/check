package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Constants.ErrorCodes;
import main.java.ru.clevertec.check.Constants.FileConstants;
import main.java.ru.clevertec.check.Constants.InputParamsConstants;
import main.java.ru.clevertec.check.Logging.ConsoleLogger;
import main.java.ru.clevertec.check.Logging.ConsolidateLogger;
import main.java.ru.clevertec.check.Logging.FileLogger;
import main.java.ru.clevertec.check.Models.*;
import main.java.ru.clevertec.check.Services.CsvFileItemDataProvider;
import main.java.ru.clevertec.check.Services.OrderDetailsToCsvWriter;

import java.util.Objects;

public class CheckRunner {

    private final static ConsolidateLogger _logger = new ConsolidateLogger();

    public static void main(String[] args) {
        var settings = ReadSettingFromInput(args);
        
        ILogger consoleLogger = new ConsoleLogger();
        ILogger fileLogger = new FileLogger(settings.PathToResultFile.isBlank() ? FileConstants.DefaultResultFilePath : settings.PathToResultFile);
        _logger.Register(consoleLogger);
        _logger.Register(fileLogger);

        try {
            if (settings.PathToProductsFile.isBlank()) {
                _logger.logInfo("Path to a product file was not provided");
                _logger.logError("BAD REQUEST");
                return;
            }

            if (settings.PathToResultFile.isBlank()) {
                _logger.logInfo("Path to a result file was not provided");
                _logger.logError("BAD REQUEST");
                return;
            }

            var orderInput = ReadOrderDataFromInput(args);

            var csvFileItemDataProvider = new CsvFileItemDataProvider(settings.PathToProductsFile, _logger);
            var order = new Order(settings, _logger);
            
            _logger.logInfo("Order calculation started");

            for (var entry : orderInput.GetOrderItems().entrySet()) {
                var itemDetails = csvFileItemDataProvider.Get(entry.getKey());

                if (itemDetails == null) {
                    _logger.logInfo("There is no item with id: " + entry.getKey());
                    _logger.logError(ErrorCodes.BadRequest);
                    System.exit(0);
                }
                
                if(entry.getValue() > itemDetails.getQuantityInStock()) {
                    _logger.logInfo("Not enough items with id : " + entry.getKey());
                    _logger.logError(ErrorCodes.BadRequest);
                    System.exit(0);
                }

                order.AddItem(new OrderItem(itemDetails, entry.getValue()));
                _logger.logInfo("Item was added to an order. Id: " + entry.getKey());
            }
            
            order.SetDiscountCard(orderInput.GetDiscountCardNumber());
            order.SetBalance(orderInput.GetBalance());
            
            order.Calculate();
            
            _logger.logInfo("Order calculation ended");
            _logger.logInfo("");

            var detailsWriter = new OrderDetailsToCsvWriter(settings.PathToResultFile, _logger);
            detailsWriter.Write(order);
            
        } catch (Exception e) {
            _logger.logInfo("Smth is wrong in order calculation");
            _logger.logError(ErrorCodes.InternalServerError);
            System.exit(0);
        }
    }

    private static Settings ReadSettingFromInput(String[] args) {
        var settings = new Settings();

        for (String arg : args) {
            if (arg.contains("=")) {
                var parts = arg.split("=");
                var key = parts[0];
                var value = parts[1];

                if (Objects.equals(key, InputParamsConstants.PathToProductsFile)) {
                    settings.PathToProductsFile = value;
                }
                if (Objects.equals(key, InputParamsConstants.PathToDiscountCardsFile)) {
                    settings.PathToDiscountCardsFile = value;
                }
                if (Objects.equals(key, InputParamsConstants.PathToResultFile)) {
                    settings.PathToResultFile = value;
                }
            }
        }

        return settings;
    }

    private static OrderInput ReadOrderDataFromInput(String[] args) {
        _logger.logInfo("Reading input order params started");
        
        var orderInput = new OrderInput(_logger);
        
        for (String arg : args) {
            // Parse items and quantity
            if (arg.contains("-")) {
                var parts = arg.split("-");
                orderInput.AddOrderItem(parts[0], parts[1]);
            }

            // Parse discount card
            if (arg.contains("=")) {
                var parts = arg.split("=");
                var key = parts[0];
                var value = parts[1];

                if (Objects.equals(key, InputParamsConstants.DiscountCard)) {
                    orderInput.AddDiscountCardNumber(value);
                }

                if (Objects.equals(key, InputParamsConstants.BalanceDebitCard)) {
                    orderInput.AddBalance(value);
                }
            }
        }

        _logger.logInfo("Reading input order params ended");
        _logger.logInfo("");
        
        return orderInput;
    }
}
