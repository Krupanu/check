package main.java.ru.clevertec.check.Abstractions;

import main.java.ru.clevertec.check.Models.Order;

public interface IOrderDetailsWriter {
    Integer Write(Order order);
}

