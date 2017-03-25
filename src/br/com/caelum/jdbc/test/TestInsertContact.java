package br.com.caelum.jdbc.test;

import java.util.Calendar;

import br.com.caelum.jdbc.dao.ContactDAO;
import br.com.caelum.jdbc.model.Contact;

public class TestInsertContact {

	public static void main(String[] args) throws ClassNotFoundException {
		Contact contact = new Contact();
		
		contact.setName("Caelum");
		contact.setEmail("contato@caelum.com.br");
		contact.setAddress("Rua Vergueiro 3185 cj87");
		contact.setBirthDate(Calendar.getInstance());
		
		ContactDAO contactDAO = new ContactDAO();
		
		contactDAO.addContact(contact);
		
		System.out.println("Contact saved!");
	}

}
