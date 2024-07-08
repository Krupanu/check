package main.java.ru.clevertec.check;


import Abstractions.IOrderDetailsWriter;
import main.java.ru.clevertec.check.Discounts.CardDiscount;
import main.java.ru.clevertec.check.Discounts.WholeSaleDiscount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    public String DiscountCard;
    public Integer Balance = 0;
    public List<OrderItem> Items = new ArrayList<>();
    public Date CreatedDate = new Date();
    private double TotalPrice = 0;
    private double TotalDiscount = 0;

    public void AddItem(OrderItem item) {
        Items.add(item);
    }

    public void CalculateOrder() {
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);

        for (OrderItem item : Items) {
            if (wholeSaleDiscount.CanBeApplied(item)) {
                wholeSaleDiscount.Apply(item);
            } else {
                if (cardDiscount.CanBeApplied(item)) {
                    cardDiscount.Apply(item);
                }
            }

            TotalPrice += item.GetFullPrice();
            TotalDiscount += item.DiscountPrice;
        }
//        var detailsWriterTxt = new OrderDetailsToCsvWriter("C:\\github\\check\\src\\main\\resources\\result.csv.txt");
//        if(Balance < (TotalPrice - TotalDiscount)) {
//            detailsWriterTxt.WriteNoBalance();
//        }
    }
}
