package main.java.ru.clevertec.check;
import java.util.HashMap;
import java.util.Objects;
import Abstractions.IDataProvider;

public class CheckRunner {
    public static void main(String[] args) {
//        IDataProvider<Item> itemsDataProvider = new DumbStoreDataProvider();
        var idQuantityMap = new HashMap<Integer, Integer>();
        var order = new Order();
        var csvFileItemDataProvider = new CsvFileItemDataProvider("C:\\github\\check\\src\\main\\resources\\products.csv");
//        var csvFileDiscountDataProvider = new CsvFileDiscountDataProvider("C:\\github\\check\\src\\main\\resources\\discountCards.csv");
        //сделать вывод данных, обработчик на колличества запросов, разобраться с скидочными картами,
        //подключить ввод через консоль(удалить DumbStore...), написать записку
        for (String arg : args) {
            //Parse items and quantity
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

            //Parse discount card
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

//        order.CalculateOrder();
        var detailsWriterExcel = new OrderDetailsToCsvWriter("C:\\github\\check\\src\\main\\resources\\productsTest.csv",idQuantityMap);
        var detailsWriterTxt = new OrderDetailsToCsvWriter("C:\\github\\check\\src\\main\\resources\\result.csv.txt",idQuantityMap);

        detailsWriterTxt.Write(order);
        detailsWriterExcel.Write(order);
//        detailsWriter.WriteOrder();
    }
}

