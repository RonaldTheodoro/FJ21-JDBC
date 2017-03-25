package br.com.caelum.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.caelum.jdbc.ConnectionFactory;

public class TestConnection {

	public static void main(String[] args) 
			throws ClassNotFoundException, SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		System.out.println("Connection open!");
		connection.close();
	}
}
