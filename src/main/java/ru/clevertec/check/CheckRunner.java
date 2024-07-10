package main.java.ru.clevertec.check;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class CheckRunner {
    public static void main(String[] args) {
        try {

            String pathToProductsFile = "";
            String pathToDiscountCardsFile = "";
            String pathToResultFile = "";
            for (String arg : args) {
                if (arg.contains("=")) {
                    var parts = arg.split("=");
                    var key = parts[0];
                    var value = parts[1];

                    if (Objects.equals(key, "pathToProductsFile")) {
                        pathToProductsFile = value;
                    }
                    if (Objects.equals(key, "pathToDiscountCardsFile")) {
                        pathToDiscountCardsFile = value;
                    }
                    if (Objects.equals(key, "pathToResultFile")) {
                        pathToResultFile = value;
                    }
            }
            }
            var idQuantityMap = new HashMap<Integer, Integer>();

            var csvFileItemDataProvider = new CsvFileItemDataProvider(pathToProductsFile);
            var csvFileDiscountDataProvider = new CsvFileDiscountDataProvider(pathToDiscountCardsFile);

            var order = new Order(csvFileDiscountDataProvider);

            for (String arg : args) {
                // Parse items and quantity
                if (arg.contains("-")) {
                    var parts = arg.split("-");
                    var key = Integer.parseInt(parts[0]);
                    var value = Integer.parseInt(parts[1]);

                    if (idQuantityMap.containsKey(key)) {
                        idQuantityMap.put(key, idQuantityMap.get(key) + value);
                    } else {
                        idQuantityMap.put(key, value);
                    }
                }

                // Parse discount card
                if (arg.contains("=")) {
                    var parts = arg.split("=");
                    var key = parts[0];
                    var value = parts[1];

                    if (Objects.equals(key, "discountCard")) {
                        order.DiscountCard = value;
                    }

                    if (Objects.equals(key, "balanceDebitCard")) {
                        order.Balance = Integer.parseInt(value);
                    }
                }
            }

            for (var entry : idQuantityMap.entrySet()) {
                order.AddItem(new OrderItem(csvFileItemDataProvider.Get(entry.getKey()), entry.getValue()));
            }

            var detailsWriter = new OrderDetailsToCsvWriter(pathToResultFile, idQuantityMap);
            double totalPrice = 0;
            for (Map.Entry<Integer, Integer> entry : idQuantityMap.entrySet())    {
                int key = entry.getKey();
                int quantity = entry.getValue();
                Item item = order.GetItemById(key);
                OrderItem orderItem = new OrderItem(item, quantity);
                double itemTotal = order.CalculateTotalItemPrice(orderItem);
                totalPrice += itemTotal;
            }
            if (totalPrice > order.Balance) {
                System.out.println("Data has been written");
                detailsWriter.WriteNoBalance();
                System.exit(0);}
            detailsWriter.Write(order);
        }
        catch (NumberFormatException e){
            var idQuantityMap = new HashMap<Integer, Integer>();
            String pathToResultFile = "";
            for (String arg : args) {
                if (arg.contains("=")) {
                    var parts = arg.split("=");
                    var key = parts[0];
                    var value = parts[1];
                    if (Objects.equals(key, "pathToResultFile")) {
                        pathToResultFile = value;
                    }
                }
            }
                var detailsWriter = new OrderDetailsToCsvWriter(pathToResultFile, idQuantityMap);
                detailsWriter.WriteException("BAD REQUEST");
                System.out.println("ERROR");
                System.out.println("Data has been written");
                System.exit(0);

        }

        catch (Exception e) {
            var idQuantityMap = new HashMap<Integer, Integer>();
            String pathToResultFile = "";
            for (String arg : args) {
                if (arg.contains("=")) {
                    var parts = arg.split("=");
                    var key = parts[0];
                    var value = parts[1];
                    if (Objects.equals(key, "pathToResultFile")) {
                        pathToResultFile = value;
                    }
                }
            }
            var detailsWriter = new OrderDetailsToCsvWriter(pathToResultFile, idQuantityMap);
            detailsWriter.WriteException("INTERNAL SERVER ERROR");
            System.out.println("ERROR");
            System.out.println("Data has been written");
            System.exit(0);
        }
        System.out.println("Data has been written!");
    }
}
