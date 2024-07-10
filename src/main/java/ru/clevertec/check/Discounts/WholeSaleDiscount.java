package main.java.ru.clevertec.check.Discounts;

import main.java.ru.clevertec.check.Abstractions.IDiscount;
import main.java.ru.clevertec.check.Models.OrderItem;

public class WholeSaleDiscount implements IDiscount {

    private final double _discount = 0.1;

    @Override
    public boolean CanBeApplied(OrderItem item) {
        return item.Item.getWholeSale() && item.Amount >= 5;
    }

    @Override
    public void Apply(OrderItem item) {
        item.DiscountPrice = item.GetFullPrice() * _discount;
    }

    //
}
