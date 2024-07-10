package main.java.ru.clevertec.check.Models;

public class OrderItem {
    public Item Item;
    public int Amount;
    public double DiscountPrice;
    
    public OrderItem(Item item, int amount) {
        Item = item;
        Amount = amount;
    }
    
    public double GetFullPrice() {
        double fullPrice = Item.getPrice() * Amount;
        return fullPrice;
    }

    public Item getItem() {
        return Item;
    }
}