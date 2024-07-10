package main.java.ru.clevertec.check.Discounts;

public class DiscountCards {
    private final String _discountCardNumber;
    private final int _discountPercentage;

    public DiscountCards(String discountCardNumber, int discountPercentage) {
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
