package br.com.caelum.jdbc.test;

import java.util.List;

import br.com.caelum.jdbc.dao.ContactDAO;
import br.com.caelum.jdbc.model.Contact;


public class TestList {

	public static void main(String[] args) throws ClassNotFoundException {
		ContactDAO contactDAO = new ContactDAO();
		List<Contact> contacts = contactDAO.getListContacts();
		
		for(Contact contact : contacts) {
			System.out.println("Name: " + contact.getName());
			System.out.println("Email: " + contact.getEmail());
			System.out.println("Address: " + contact.getAddress());
			System.out.println(
				"Birth date: " + contact.getBirthDate().getTime() + "\n");
		}
	}
}
