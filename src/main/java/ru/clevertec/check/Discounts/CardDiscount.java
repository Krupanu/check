package main.java.ru.clevertec.check.Discounts;

import Abstractions.IDiscount;
import main.java.ru.clevertec.check.OrderItem;

import java.util.Map;

public class CardDiscount implements IDiscount {
    private final double _defaultDiscount = 0.02;
    private final String _cardNumber;

    public CardDiscount(String cardNumber) {
        _cardNumber = cardNumber;
    }

    @Override
    public boolean CanBeApplied(OrderItem item) {
        return _cardNumber != null;
    }

    @Override
    public void Apply(OrderItem item) {
        var discount = DiscountCards.Discounts.get(_cardNumber);
        item.DiscountPrice = item.GetFullPrice() * (discount == null ? _defaultDiscount : discount);
    }
}
