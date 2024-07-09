package main.java.ru.clevertec.check;

public class OrderItem {
    public OrderItem(Item item, int amount) {
        Item = item;
        Amount = amount;
    }

    public Item Item;
    public int Amount;
    public double GetFullPrice() {
        double fullPrice = Item.getPrice() * Amount;
        return fullPrice;
    }

    public Item getItem() {
        return Item;
    }
    public double DiscountPrice;
}
