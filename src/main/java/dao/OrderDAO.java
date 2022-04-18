package dao;

import model.Orders;

import java.util.List;

public class OrderDAO extends AbstractDAO<Orders>{

    /**
     * Clasa ClientDAO extinde clasa AbstarctDAO si face legatura cu baza de date prin apelarea
     * metodei din ConnecionFactory si se implementeaza query-urile pentru clasa/tabelul Clients. Are
     * o singura variabila instanta, si anume LOGGER. Metodele din aceasta clasa sunt insert, delete,
     * update, findAll si findById. In clasa nu se afla implementarea propriu zisa a metodelor, deoarece
     * se apeleaza cu super metoda respectiva din clasa AbstractDAO
     * @param orders
     * @return
     */
    @Override
    public int insert(Orders orders) {
        return super.insert(orders);
    }

    @Override
    public Orders delete(Orders o) {
        return super.delete(o);
    }

    @Override
    public Orders update(Orders orders) {
        return super.update(orders);
    }

    @Override
    public List<Orders> findAll() {
        return super.findAll();
    }

    @Override
    public Orders findById(int id) {
        return super.findById(id);
    }
}
