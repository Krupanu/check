package main.java.ru.clevertec.check.Models;

import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Discounts.DiscountCard;
import main.java.ru.clevertec.check.Services.CsvFileDiscountDataProvider;
import main.java.ru.clevertec.check.Discounts.CardDiscount;
import main.java.ru.clevertec.check.Discounts.WholeSaleDiscount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private DiscountCard DiscountCard;
    private double Balance = 0;
    public List<OrderItem> Items = new ArrayList<>();
    private final Date CreatedDate = new Date();
    private double TotalDiscount = 0;
    private final CsvFileDiscountDataProvider _csvFileDiscountDataProvider;
    private final ILogger _logger;

    public Order(Settings settings, ILogger logger) {
        _csvFileDiscountDataProvider = new CsvFileDiscountDataProvider(settings.PathToDiscountCardsFile, logger);
        _logger = logger;
    }

    public void AddItem(OrderItem item) {
        Items.add(item);
    }

    public List<OrderItem> GetItems() {
        return Items;
    }
    
    public double CalculateTotalItemPrice(OrderItem orderItem) {
        double totalPrice;
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);

        if (wholeSaleDiscount.CanBeApplied(orderItem)) {
            wholeSaleDiscount.Apply(orderItem);
        } else if (cardDiscount.CanBeApplied(orderItem)) {
            cardDiscount.Apply(orderItem);
        }

        totalPrice = orderItem.GetFullPrice();
        return totalPrice;
    }

    public double CalculateTotalItemDiscount(OrderItem orderItem) {
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);

        if (wholeSaleDiscount.CanBeApplied(orderItem)) {
            wholeSaleDiscount.Apply(orderItem);
        } else if (cardDiscount.CanBeApplied(orderItem)) {
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

    public void SetDiscountCard(String cardNumber) {
        var defaultDiscount = 2;
        var card = _csvFileDiscountDataProvider.GetByCardNumber(cardNumber);
        if(card == null && cardNumber != null && !cardNumber.isBlank()) {
            card = new DiscountCard(cardNumber, defaultDiscount);
            _logger.logInfo("Default discount applied");
        }
        
        this.DiscountCard = card;
    }
    
    public double getBalance() {
        return Balance;
    }

    public void SetBalance(double balance) {
        Balance = balance;
    }
    
    public Date GetCreatedDate() {
        return CreatedDate;
    }
}
