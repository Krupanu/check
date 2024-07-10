package main.java.ru.clevertec.check.Discounts;

public class DiscountCard {
    private final String _discountCardNumber;
    private final int _discountPercentage;

    public DiscountCard(String discountCardNumber, int discountPercentage) {
        _discountCardNumber = discountCardNumber;
        _discountPercentage = discountPercentage;
    }

    public String getDiscountCardNumber() {
        return _discountCardNumber;
    }

    public int getDiscountPercentage() {
        return _discountPercentage;
    }
}
