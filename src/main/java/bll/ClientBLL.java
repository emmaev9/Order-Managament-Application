package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.StudentAgeValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;


public class ClientBLL {

	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
		validators.add(new StudentAgeValidator());

		clientDAO = new ClientDAO();
	}

	public Client findClientById(int id) {
		Client st = clientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + id + " was not found!");
		}
		return st;
	}

	public void insertClient(Client client){
		clientDAO.insert(client);
	}

	public void deleteClient(Client st){

		if(st == null){
			throw new NoSuchElementException("The client with id =" + st.getId() + " was not found!");
		}
		else {
			clientDAO.delete(st);
		}
	}

	public void updateClient(Client st){
		if(st == null){
			throw new NoSuchElementException("The client with id =" + st.getId() + " was not found!");
		}
		else {
			clientDAO.update(st);
		}
	}
	public List<Client> findAllClients(){
		List<Client> students = clientDAO.findAll();
		return students;
	}
}
