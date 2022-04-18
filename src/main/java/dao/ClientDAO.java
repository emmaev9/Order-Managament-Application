package dao;

import model.Client;

import java.util.List;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Clasa ClientDAO extinde clasa AbstarctDAO si face legatura cu baza de date prin apelarea
     * metodei din ConnecionFactory si se implementeaza query-urile pentru clasa/tabelul Clients. Are
     * o singura variabila instanta, si anume LOGGER. Metodele din aceasta clasa sunt insert, delete,
     * update, findAll si findById. In clasa nu se afla implementarea propriu zisa a metodelor, deoarece
     * se apeleaza cu super metoda respectiva din clasa AbstractDAO
     */
	// uses basic CRUD methods from superclass
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    @Override
    public int insert(Client client) {
        return super.insert(client);
    }

    @Override
    public Client delete(Client s) {
        return super.delete(s);
    }

    @Override
    public Client update(Client client) {
        return super.update(client);
    }

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    @Override
    public Client findById(int id) {
        return super.findById(id);
    }


}
