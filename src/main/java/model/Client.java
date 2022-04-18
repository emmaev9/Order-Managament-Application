package model;

/**
 * Clasa Client simuleaza un client care are ca variabile instanta id, nume, adresa,
 * email, varsta. Clasa contine doar gettere si settere si contructori.
 */
public class Client {
	public int id;
	public String name;
	public String address;
	public String email;
	public int age;

	public Client() {
	}

	public Client(int id, String name, String address, int age, String email) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.age = age;
		this.email = email;
	}

	public Client(String name, String address, String email, int age) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age
				+ "]";
	}

}
