package main.java.ru.clevertec.check.Models;

import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Constants.ErrorCodes;
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
    private double TotalPrice;
    private double TotalDiscount;
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
    
    public void Calculate() {
        double totalPrice = 0;
        double totalDiscount = 0;
        var wholeSaleDiscount = new WholeSaleDiscount();
        var cardDiscount = new CardDiscount(DiscountCard);
        
        for(var orderItem : Items) {
            if (wholeSaleDiscount.CanBeApplied(orderItem)) {
                wholeSaleDiscount.Apply(orderItem);
            } else if (cardDiscount.CanBeApplied(orderItem)) {
                cardDiscount.Apply(orderItem);
            }

            totalPrice += orderItem.GetFullPrice();
            totalDiscount += orderItem.DiscountPrice;
        }
        
        TotalPrice = totalPrice;
        _logger.logInfo("Total price is " + totalPrice);
        TotalDiscount = totalDiscount;
        _logger.logInfo("Total discount is " + totalDiscount);

        if (totalPrice > Balance) {
            _logger.logInfo("Not enough money");
            _logger.logError(ErrorCodes.NotEnoughMoney);
            System.exit(0);
        }
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

    public double GetTotalPrice() {
        return TotalPrice;
    }

    public double GetTotalDiscount() {
        return TotalDiscount;
    }
}
