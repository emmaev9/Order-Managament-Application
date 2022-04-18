package bll;

import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Orders;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL{
    private List<Validator<Orders>> validators;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderBLL() {
        validators = new ArrayList<Validator<Orders>>();
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    public Orders findOrderById(int id) {
        Orders o = orderDAO.findById(id);
        if (o == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return o;
    }
    public int insertOrder(Orders order){
        Product product = productDAO.findById(order.getProductId());
        product.setQuantity(product.getQuantity()-order.getQuantity());
        productDAO.update(product);
        return orderDAO.insert(order);
    }

    public void deleteOrder(Orders orders){
        if(orders == null){
            throw new NoSuchElementException("The order with id =" + orders.getOrderId() + " was not found!");
        }
        else {
            orderDAO.delete(orders);
        }
    }

    public void updateOrder(Orders o){
        if(o == null){
            throw new NoSuchElementException("The order with id =" + o.getOrderId() + " was not found!");
        }
        else {
            orderDAO.update(o);
        }
    }
    public List<Orders> findAllOrders(){
        List<Orders> orders = orderDAO.findAll();
        return orders;
    }



}
