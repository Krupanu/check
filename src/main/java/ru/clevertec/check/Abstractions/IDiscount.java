package main.java.ru.clevertec.check.Abstractions;
import main.java.ru.clevertec.check.Models.OrderItem;

public interface IDiscount {
    boolean CanBeApplied(OrderItem item);
    void Apply(OrderItem item);
}
