package main.java.ru.clevertec.check.Discounts;

import main.java.ru.clevertec.check.Item;
import main.java.ru.clevertec.check.OrderItem;

public class DiscountCardNumberChecker {
    public DiscountCardNumberChecker(main.java.ru.clevertec.check.Discounts.DiscountCards discountCardNumber) {
        DiscountCardNumber = discountCardNumber;
    }

    public DiscountCards DiscountCardNumber;


    public double DiscountAmount(){
        return 1;
    }
    public DiscountCards getDiscountCardNumber() {
        return DiscountCardNumber;
    }
}
