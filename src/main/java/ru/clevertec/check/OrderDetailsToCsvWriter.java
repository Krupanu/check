package main.java.ru.clevertec.check;

import Abstractions.IOrderDetailsWriter;
import main.java.ru.clevertec.check.Discounts.DiscountCards;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class OrderDetailsToCsvWriter implements IOrderDetailsWriter {

    public final File _file;

    public OrderDetailsToCsvWriter(String filePath) {
        _file = new File(filePath);
    }

    @Override
    public Integer Write(Order order) {
        try {
            FileWriter outputFile = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(outputFile);

            printWriter.println("Date;Time");
            printWriter.println(order.CreatedDate.getDate() + ";" + order.CreatedDate.getTime());
            printWriter.println("");
            printWriter.println("QTU;DESCRIPTION;PRICE;DISCOUNT;TOTAL");


            printWriter.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");

                        var discount = DiscountCards.Discounts.get(order.DiscountCard) * 100;
                        printWriter.println(order.DiscountCard + ";" + discount + "%");

            printWriter.close();
        }
        catch (Exception e) {
            e.getStackTrace();
            return 0;
        }

        return 1;
    }
//    public void WriteOrder(){
//        for (int i = 1; i < ){
//            var item = new Item();
//        }
//    }
    public void WriteNoBalance() {
        try {
            FileWriter outputFile = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(outputFile);

            printWriter.println("ERROR");

            printWriter.println("NOT ENOUGH MONEY");

            printWriter.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
}
