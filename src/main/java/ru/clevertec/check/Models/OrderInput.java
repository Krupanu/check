package main.java.ru.clevertec.check.Models;

import main.java.ru.clevertec.check.Abstractions.ILogger;
import main.java.ru.clevertec.check.Constants.ErrorCodes;

import java.util.HashMap;

public class OrderInput {
    private final HashMap<Integer, Integer> _idQuantityMap = new HashMap<>();
    private String _discountCardNumber;
    private double _balance;
    private final ILogger _logger;
    
    public OrderInput(ILogger logger) {
        _logger = logger;
    }
    
    public void AddOrderItem(String id, String quantity) {
        if(!IsNumeric(id) || !IsNumeric(quantity)) {
            _logger.logInfo("Wrong item or quantity.");
            _logger.logError(ErrorCodes.BadRequest);
            System.exit(0);
        }

        var idNumber = Integer.parseInt(id);
        var quantityNumber = Integer.parseInt(quantity);
        
        if (_idQuantityMap.containsKey(idNumber)) {
            _idQuantityMap.put(idNumber, _idQuantityMap.get(idNumber) + quantityNumber);

            _logger.logInfo("Requiring item with id " + id + " and quantity " + quantity);
        } else {
            _idQuantityMap.put(idNumber, quantityNumber);
            
            _logger.logInfo("Item with id " + id + " and quantity " + quantity);
        }
    }

    public HashMap<Integer, Integer> GetOrderItems() {
        return _idQuantityMap;
    }

    public void AddDiscountCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 4 || !IsNumeric(cardNumber)) {
            _logger.logInfo("Wrong discount card number");
            _logger.logError(ErrorCodes.BadRequest);
            System.exit(0);
        } else {
            _discountCardNumber = cardNumber;

            _logger.logInfo("Discount card number is " + cardNumber);
        }
    }

    public String GetDiscountCardNumber() {
        return _discountCardNumber;
    }

    public void AddBalance(String balance) {
        if(balance == null || !IsNumeric(balance)) {
            _logger.logInfo("Wrong balance");
            _logger.logError(ErrorCodes.BadRequest);
            System.exit(0);
        }
        _balance = Double.parseDouble(balance);
        
        _logger.logInfo("Balance is " + balance);
    }

    public double GetBalance() {
        return _balance;
    }

    private static boolean IsNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
}
