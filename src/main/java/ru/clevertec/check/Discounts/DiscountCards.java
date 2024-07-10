package main.java.ru.clevertec.check.Discounts;

import java.util.Map;

public class DiscountCards {
    public DiscountCards( String discountCardNumber){
        _discountCardNumber = discountCardNumber;
    }
    private Integer Id;
    public double DiscountPrice;
    private  final String _discountCardNumber;
    public Integer getId() {
        return Id;
    }
    public String getDiscountCardNumber() {
        return _discountCardNumber;
    }

}
