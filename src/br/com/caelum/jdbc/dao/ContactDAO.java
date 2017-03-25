package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.model.Contact;
import br.com.caelum.jdbc.util.DateUtil;

public class ContactDAO {
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;
	
	public ContactDAO() throws ClassNotFoundException {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void addContact(Contact contact) {
		String sql = "INSERT INTO contact" + 
			"(name, email, address, birth_date) values (?, ?, ?, ?)";
		
		try {
			prepareStatement(sql);
			populateInsertStatement(contact);
			statement.execute();
		} catch (SQLException error) {
			throw new RuntimeException();
		} finally {
			try {
				dispose();
			} catch (SQLException error) {
				throw new RuntimeException();
			}
		}
	}
	
	private void populateInsertStatement(Contact contact) throws SQLException {
		statement.setString(1, contact.getName());
		statement.setString(2, contact.getEmail());
		statement.setString(3, contact.getAddress());
		statement.setDate(4, DateUtil.toSQLDate(contact.getBirthDate()));
	}
	
	public List<Contact> getListContacts() {
		String sql = "SELECT * FROM contact";
		
		try {
			prepareStatement(sql);
			query();
			return getPopulatedList();
		} catch (SQLException error) {
			throw new RuntimeException(error);
		} finally {
			try {
				dispose();
			} catch (SQLException error) {
				throw new RuntimeException(error);
			}
		}
	}
	
	public List<Contact> getListByName(String name) {
		String sql = "SELECT * FROM contact WHERE LOWER(contact.name) LIKE ?";
		
		try {
			prepareStatement(sql);
			populateGetByNameQueryStatement(name);
			query();
			return getPopulatedList();
		} catch (SQLException error) {
			throw new RuntimeException(error);
		} finally {
			try {
				dispose();
			} catch (SQLException error) {
				throw new RuntimeException(error);
			}
		}
	}
	
	private void populateGetByNameQueryStatement(String name)
			throws SQLException {
		statement.setString(1, "%" + name.toLowerCase() + "%");
	}
	
	private List<Contact> getPopulatedList() throws SQLException {
		List<Contact> contacts = new ArrayList<>();
		
		while (resultSet.next()) {
			contacts.add(getPopulatedObject());
		}
		return contacts;
	}
	
	private Contact getPopulatedObject() throws SQLException {
		Contact contact = new Contact();

		contact.setId(resultSet.getLong("id"));
		contact.setName(resultSet.getString("name"));
		contact.setEmail(resultSet.getString("email"));
		contact.setAddress(resultSet.getString("address"));
		contact.setBirthDate(
			DateUtil.toCalendar(resultSet.getDate("birth_date")));
		
		return contact;
	}
	
	public Contact getById(Long id) {
		String sql = "SELECT * FROM contact WHERE contact.id = ?";
		
		try {
			prepareStatement(sql);
			populateGetByIdQueryStatement(id);
			query();
			return getSingleResult();
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}
	
	private Contact getSingleResult() throws SQLException {
		if (resultSet.next())
			return getPopulatedObject();
		return new Contact();
	}
	
	private void populateGetByIdQueryStatement(Long id) throws SQLException {
		statement.setLong(1, id);
	}
	
	private void prepareStatement(String sql) throws SQLException {
		statement = connection.prepareStatement(sql);
	}
	
	private void query() throws SQLException {
		resultSet = statement.executeQuery();
	}
	
	private void dispose() throws SQLException {
		if (resultSet != null)
			resultSet.close();
		
		if (statement != null)
			statement.close();
		
		if (connection != null)
			connection.close();
	}
}
