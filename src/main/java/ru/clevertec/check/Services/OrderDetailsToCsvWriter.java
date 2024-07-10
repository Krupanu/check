package main.java.ru.clevertec.check.Services;

import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Abstractions.IOrderDetailsWriter;
import main.java.ru.clevertec.check.Constants.ErrorCodes;
import main.java.ru.clevertec.check.Models.Order;


import java.io.*;
import java.text.DecimalFormat;

public class OrderDetailsToCsvWriter implements IOrderDetailsWriter {

    private final File _file;
    private final ILogger _logger;

    public OrderDetailsToCsvWriter(String filePath, ILogger logger) {
        _file = new File(filePath);
        _logger = logger;
    }

    @Override
    public Integer Write(Order order) {
        try {
            _logger.logInfo("Output to a file started");
            
            FileWriter outputFile = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(outputFile);
            DecimalFormat df = new DecimalFormat("0.00");

            printWriter.println("Date;Time");
            printWriter.println(order.GetCreatedDate().getDate() + "." + (order.GetCreatedDate().getMonth() + 1) + "." + (order.GetCreatedDate().getYear() + 1900)
                    + ";" + order.GetCreatedDate().getHours() + ":" + order.GetCreatedDate().getMinutes() + ":" + order.GetCreatedDate().getSeconds());
            printWriter.println();
            printWriter.println("QTU;DESCRIPTION;PRICE;DISCOUNT;TOTAL");

            for (var orderItem : order.GetItems()) {
                printWriter.println(orderItem.Amount + ";" + orderItem.Item.getDescription() + ";" + df.format(orderItem.Item.getPrice()) + "$;" + df.format(orderItem.DiscountPrice) + "$;" + df.format(orderItem.GetFullPrice()) + "$");
            }
            printWriter.println("");
            printWriter.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
            printWriter.println(df.format(order.GetTotalPrice()) + "$;" + df.format(order.GetTotalDiscount()) + "$;" + df.format(order.GetTotalPrice() - order.GetTotalDiscount()) + "$");

            printWriter.close();

            _logger.logInfo("Output to a file ended");
            _logger.logInfo("");
        } catch (Exception e) {
            _logger.logInfo("Smth is wrong with final file writing");
            _logger.logError(ErrorCodes.InternalServerError);
        }

        return 1;
    }
}

