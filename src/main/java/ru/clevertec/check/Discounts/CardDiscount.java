package main.java.ru.clevertec.check.Discounts;

import Abstractions.IDiscount;
import main.java.ru.clevertec.check.OrderItem;

public class CardDiscount implements IDiscount {
    private final DiscountCards _discountCard;

    public CardDiscount(DiscountCards discountCard) {
        _discountCard = discountCard;
    }

    public boolean CanBeApplied(OrderItem item) {
        return _discountCard != null;
    }

    public void Apply(OrderItem item) {
        double discountRate = _discountCard.getDiscountPercentage() / 100.0;
        item.DiscountPrice = item.GetFullPrice() * discountRate;
    }
}
