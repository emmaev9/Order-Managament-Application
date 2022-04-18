package model;

/**
 * Clasa Order simuleaza o comanda care are ca variabile instanta id, idClient,
 * idProdus, cantitate. Clasa contine doar gettere si settere si constructori.
 */

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, int quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(){
    }

    public int getId() {
        return id;
    }

    public void setId(int productID) {
        this.id = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return "Product [id = " + id + ", name = " + name + ", price = " + price + ", quantity = " + quantity + "]";
    }
}
