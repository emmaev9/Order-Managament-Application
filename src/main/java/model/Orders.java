package model;

import java.util.List;

/**
 * Clasa Product simuleaza un produs care are ca variabile instanta id, nume, pret,
 * cantitate. Clasa contine doar gettere si settere si constructori.
 */
public class Orders {
    private int orderId;
    private int clientId;
    private int productId;
    private int quantity;


    public Orders(){};

    public Orders(int orderId, int clientId, int productId, int quantity) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String toString() {
        return "Order [id= " +orderId+  ", name = " + clientId + ", product = " + productId + ", quantity = " + quantity + "]";
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
