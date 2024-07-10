package main.java.ru.clevertec.check.Models;


public class Item {

    public Item(Integer id, String description, Double price,Integer quantityInStock, Boolean isWholeSale) {
        Id = id;
        Description = description;
        Price = price;
        IsWholeSale = isWholeSale;
        QuantityInStock = quantityInStock;
    }

    private Integer Id;

    private String Description;

    private Double Price;
    
    private Integer QuantityInStock;

    private Boolean IsWholeSale;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Boolean getWholeSale() {
        return IsWholeSale;
    }

    public void setWholeSale(Boolean wholeSale) {
        IsWholeSale = wholeSale;
    }

}