package main.java.ru.clevertec.check;

import Abstractions.IOrderDetailsWriter;
import main.java.ru.clevertec.check.Discounts.DiscountCards;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
            printWriter.println("Date;Time");
            printWriter.println(order.CreatedDate.getDate() + "." + (order.CreatedDate.getMonth() + 1) + "." + (order.CreatedDate.getYear() + 1900)
                    + ";" + order.CreatedDate.getHours() + ":" + order.CreatedDate.getMinutes() + ":" + order.CreatedDate.getSeconds());
            printWriter.println("");
            printWriter.println("QTU;DESCRIPTION;PRICE;DISCOUNT;TOTAL");

            for (Map.Entry<Integer, Integer> entry : idQuantityMap.entrySet()) {
                int key = entry.getKey();
                int quantity = entry.getValue();
                Item item = order.GetItemById(key);
                var discount = DiscountCards.Discounts.get(order.DiscountCard) * 100;
                var df = new DecimalFormat("0.00");
                printWriter.println(quantity + ";" + item.getDescription() + ";" + df.format(item.getPrice()) + "$;" + df.format(order.CalculateTotalItemDiscount()) + "$;" + df.format(order.CalculateTotalItemPrice()) + "$");
            }
            printWriter.println("");
            printWriter.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");


            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
            e.printStackTrace();
        }
    }
}
