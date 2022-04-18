package bll;

import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }

    public void insertProduct(Product product){
        productDAO.insert(product);
    }

    public void deleteProduct(Product prod){
        if(prod == null){
            throw new NoSuchElementException("The product with id =" + prod.getId() + " was not found!");
        }
        else {
            productDAO.delete(prod);
        }
    }

    public void updateProduct(Product pr){
        if(pr == null){
            throw new NoSuchElementException("The product with id =" + pr.getId() + " was not found!");
        }
        else {
            productDAO.update(pr);
        }
    }
    public List<Product> findAllProducts(){
        List<Product> products = productDAO.findAll();
        return products;
    }
}
