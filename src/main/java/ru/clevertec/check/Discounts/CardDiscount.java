package main.java.ru.clevertec.check.Discounts;

import Abstractions.IDiscount;
import main.java.ru.clevertec.check.Item;
import main.java.ru.clevertec.check.OrderItem;

import java.util.Map;

public class CardDiscount implements IDiscount {
    private final double _defaultDiscount = 0.02;
    private final DiscountCards _discountCard;

    public CardDiscount(DiscountCards discountCard) {
        _discountCard = discountCard;
    }

    public boolean CanBeApplied(OrderItem item) {
        return _discountCard != null;
    }

    public void Apply(OrderItem item) {
        item.DiscountPrice = item.GetFullPrice() * _defaultDiscount;
    }
}



//
