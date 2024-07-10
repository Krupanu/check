package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.Discounts.CardDiscount;
import main.java.ru.clevertec.check.Discounts.DiscountCards;
import main.java.ru.clevertec.check.Discounts.WholeSaleDiscount;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {
    public String DiscountCard;
    public Integer Balance = 0;
    public List<OrderItem> Items = new ArrayList<>();
    public Date CreatedDate = new Date();
    private double TotalDiscount = 0;
    private CsvFileDiscountDataProvider _csvFileDiscountDataProvider;

    public Order(CsvFileDiscountDataProvider csvFileDiscountDataProvider) {
        _csvFileDiscountDataProvider = csvFileDiscountDataProvider;
    }

    public void AddItem(OrderItem item) {
        Items.add(item);
    }

    public boolean isDiscountCardValid() {
        return _csvFileDiscountDataProvider.isDiscountCardValid(DiscountCard);
    }

    public double CalculateTotalItemPrice(OrderItem orderItem) {
        double totalPrice = 0.00;
        var wholeSaleDiscount = new WholeSaleDiscount();
        var discountCard = new DiscountCards(DiscountCard);
        var cardDiscount = new CardDiscount(discountCard);

        if (wholeSaleDiscount.CanBeApplied(orderItem)) {
            wholeSaleDiscount.Apply(orderItem);
        } else if (cardDiscount.CanBeApplied(orderItem) && isDiscountCardValid()) {
            cardDiscount.Apply(orderItem);
        }

        totalPrice = orderItem.GetFullPrice();
        return totalPrice;
    }

    public double CalculateTotalItemDiscount(OrderItem orderItem) {
        var wholeSaleDiscount = new WholeSaleDiscount();
        var discountCard = new DiscountCards(DiscountCard);
        var cardDiscount = new CardDiscount(discountCard);

        if (wholeSaleDiscount.CanBeApplied(orderItem)) {
            wholeSaleDiscount.Apply(orderItem);
        } else if (cardDiscount.CanBeApplied(orderItem) && isDiscountCardValid()) {
            cardDiscount.Apply(orderItem);
        }

        return orderItem.DiscountPrice;
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

