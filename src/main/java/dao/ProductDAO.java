package dao;

import model.Product;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {
    /**
     * Clasa ClientDAO extinde clasa AbstarctDAO si face legatura cu baza de date prin apelarea
     * metodei din ConnecionFactory si se implementeaza query-urile pentru clasa/tabelul Clients. Are
     * o singura variabila instanta, si anume LOGGER. Metodele din aceasta clasa sunt insert, delete,
     * update, findAll si findById. In clasa nu se afla implementarea propriu zisa a metodelor, deoarece
     * se apeleaza cu super metoda respectiva din clasa AbstractDAO
     * @param product
     * @return
     */
    @Override
    public int insert(Product product) {
        return super.insert(product);
    }

    @Override
    public Product delete(Product p) {
        return super.delete(p);
    }

    @Override
    public Product update(Product product) {
        return super.update(product);
    }

    @Override
    public Product findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return super.findAll();
    }
}
