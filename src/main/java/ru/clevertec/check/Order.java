package main.java.ru.clevertec.check;


import main.java.ru.clevertec.check.Discounts.CardDiscount;
import main.java.ru.clevertec.check.Discounts.WholeSaleDiscount;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    public String DiscountCard;
    public Integer Balance = 0;
    public List<OrderItem> Items = new ArrayList<>();
    public Date CreatedDate = new Date();

    private double TotalDiscount = 0;

    public void AddItem(OrderItem item) {
        Items.add(item);
    }


    public double CalculateTotalItemPrice(int amountOfLoops) {
        double TotalPrice = 0.00;
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);

            if (wholeSaleDiscount.CanBeApplied(Items.get(amountOfLoops))) {
                wholeSaleDiscount.Apply(Items.get(amountOfLoops));
            } else {
                if (cardDiscount.CanBeApplied(Items.get(amountOfLoops))) {
                    cardDiscount.Apply(Items.get(amountOfLoops));
                }


            TotalPrice = Items.get(amountOfLoops).GetFullPrice();
        }

        return TotalPrice;
    }


    public double CalculateTotalItemDiscount(int amountOfLoops) {
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);


            if (wholeSaleDiscount.CanBeApplied(Items.get(amountOfLoops))) {
                wholeSaleDiscount.Apply(Items.get(amountOfLoops));
            } else {
                if (cardDiscount.CanBeApplied(Items.get(amountOfLoops))) {
                    cardDiscount.Apply(Items.get(amountOfLoops));
                }


            TotalDiscount = Items.get(amountOfLoops).DiscountPrice;
        }

        return TotalDiscount;
    }
    public Item GetItemById(int id) {
        for (OrderItem orderItem : Items) {
            if (orderItem.getItem().getId() == id) {
                return orderItem.getItem();
            }
        }
        return null;
    }
}


//    public void CalculateOrder() {
//        var wholeSaleDiscount = new WholeSaleDiscount();
//        var cardDiscount = new CardDiscount(DiscountCard);
//
//        for (OrderItem item : Items) {
//            if (wholeSaleDiscount.CanBeApplied(item)) {
//                wholeSaleDiscount.Apply(item);
//            } else {
//                if (cardDiscount.CanBeApplied(item)) {
//                    cardDiscount.Apply(item);
//                }
//            }
//
//            TotalPrice += item.GetFullPrice();
//            TotalDiscount += item.DiscountPrice;
//        }
//    }
//public double CalculateTotalPrice() {
//    var wholeSaleDiscount = new WholeSaleDiscount();
//    var cardDiscount = new CardDiscount(DiscountCard);
//    double TotalPrice = 0.00;
//    for (OrderItem item : Items) {
//
//        if (wholeSaleDiscount.CanBeApplied(item)) {
//            wholeSaleDiscount.Apply(item);
//        } else {
//            if (cardDiscount.CanBeApplied(item)) {
//                cardDiscount.Apply(item);
//            }
//        }
//
//        TotalPrice += item.GetFullPrice();
//    }
//
//    return TotalPrice;
//}
//    public double CalculateTotalDiscount() {
//        var wholeSaleDiscount = new WholeSaleDiscount();
//        var cardDiscount = new CardDiscount(DiscountCard);
//
//        for (OrderItem item : Items) {
//            if (wholeSaleDiscount.CanBeApplied(item)) {
//                wholeSaleDiscount.Apply(item);
//            } else {
//                if (cardDiscount.CanBeApplied(item)) {
//                    cardDiscount.Apply(item);
//                }
//            }
//
//            TotalDiscount += item.DiscountPrice;
//        }
//
//        return TotalDiscount;
//    }