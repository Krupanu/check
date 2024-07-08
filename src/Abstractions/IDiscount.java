package Abstractions;
import main.java.ru.clevertec.check.OrderItem;

public interface IDiscount {
    boolean CanBeApplied(OrderItem item);
    void Apply(OrderItem item);
}
