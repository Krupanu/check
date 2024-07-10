package main.java.ru.clevertec.check.Discounts;

import main.java.ru.clevertec.check.Abstractions.IDiscount;
import main.java.ru.clevertec.check.Models.OrderItem;

public class CardDiscount implements IDiscount {
    private final DiscountCard _discountCard;

    public CardDiscount(DiscountCard discountCard) {
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
