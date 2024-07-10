package main.java.ru.clevertec.check;

import Abstractions.IOrderDetailsWriter;


import java.io.*;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Map;

public class OrderDetailsToCsvWriter implements IOrderDetailsWriter {

    public final File _file;
    private Map<Integer, Integer> idQuantityMap;

    public OrderDetailsToCsvWriter(String filePath, Map<Integer, Integer> idQuantityMap) {
        _file = new File(filePath);
        this.idQuantityMap = idQuantityMap;
    }

    @Override
    public Integer Write(Order order) {
        try {
            FileWriter outputFile = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(outputFile);
            DecimalFormat df = new DecimalFormat("0.00");

            printWriter.println("Date;Time");
            printWriter.println(order.CreatedDate.getDate() + "." + (order.CreatedDate.getMonth() + 1) + "." + (order.CreatedDate.getYear() + 1900)
                    + ";" + order.CreatedDate.getHours() + ":" + order.CreatedDate.getMinutes() + ":" + order.CreatedDate.getSeconds());
            printWriter.println("");
            printWriter.println("QTU;DESCRIPTION;PRICE;DISCOUNT;TOTAL");

            double totalDiscount = 0;
            double totalPrice = 0;

            for (Map.Entry<Integer, Integer> entry : idQuantityMap.entrySet()) {
                int key = entry.getKey();
                int quantity = entry.getValue();
                Item item = order.GetItemById(key);
                OrderItem orderItem = new OrderItem(item, quantity);
                double itemDiscount = order.CalculateTotalItemDiscount(orderItem);
                double itemTotal = order.CalculateTotalItemPrice(orderItem);

                totalDiscount += itemDiscount;
                totalPrice += itemTotal;

                printWriter.println(quantity + ";" + item.getDescription() + ";" + df.format(item.getPrice()) + "$;" + df.format(itemDiscount) + "$;" + df.format(itemTotal) + "$");
            }
            printWriter.println("");
            printWriter.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;" );
            printWriter.println(df.format(totalPrice) + "$;" + df.format(totalDiscount) + "$;" + df.format(totalPrice - totalDiscount) + "$");

            printWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR");
            String exceptionResultFilePath = "C:\\github\\check";
            String fileName = "errorResult.csv.txt";
            String fullPath = Paths.get(exceptionResultFilePath, fileName).toString();
            String textToFile = "BAD REQUEST";
            var exceptionWriter = new ExceptionWriter();
            exceptionWriter.WriteExceptionToCsv("BAD REQUEST",exceptionResultFilePath);
            System.out.println("Data has been written");
            System.exit(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    public void WriteNoBalance() {
        try {
            FileWriter outputFile = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(outputFile);

            printWriter.println("ERROR");
            printWriter.println("NOT ENOUGH MONEY");

            printWriter.close();
        } catch (Exception e) {
            System.out.println("ERROR");
            System.exit(0);
        }
    }

    public void WriteException(String exceptionReason) {
        try {

            FileWriter outputFile = new FileWriter("C:\\github\\check\\result.csv.txt");
            PrintWriter printWriter = new PrintWriter(outputFile);

            printWriter.println("ERROR");
            printWriter.println(exceptionReason);

            printWriter.close();
        } catch (Exception e) {
            System.out.println("ERROR");
            String exceptionResultFilePath = "C:\\github\\check";
            String fileName = "errorResult.csv.txt";
            String fullPath = Paths.get(exceptionResultFilePath, fileName).toString();
            String textToFile = "BAD REQUEST";
            var exceptionWriter = new ExceptionWriter();
            exceptionWriter.WriteExceptionToCsv("BAD REQUEST",exceptionResultFilePath);
            System.out.println("Data has been written");
            System.exit(0);
        }
    }
    }

