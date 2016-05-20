package org.epsi.java.ee.gallery.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.epsi.java.ee.gallery.dao.*;
import org.epsi.java.ee.gallery.exception.GalleryException;
import org.epsi.java.ee.gallery.model.User;

import com.google.common.base.Converter;
import com.mysql.jdbc.PreparedStatement;

public class UserDaoImpl implements UserDAO{

	private static final String MYSQL_HOST = "localhost";
	private static final String MYSQL_PORT = "3306";
	private static final String MYSQL_DATABASE = "gallery";
	private static final String MYSQL_USER = "root";
	private static final String MYSQL_PWD = "";
	
	@Override
	public User create(User newUser) {
		// TODO Auto-generated method stub
		
		//Connection base de données
		Connection connec = null;
		
		try{
			connec = getConnection();
			Statement stat = connec.createStatement();
			String insert = "INSERT INTO user (pseudonym, email, password) VALUES (";
			
			//On remplit la requete avec les informations envoyés par l'utilisateur
			insert += newUser.getPseudonym() + ", " + newUser.getEmail() + ", " + newUser.getPassword() + ")";
			
			long id = stat.executeUpdate(insert,Statement.RETURN_GENERATED_KEYS);
			
			newUser.setId(id);
			return newUser;
			
		}catch(SQLException e){
			throw new GalleryException("Erreur avec la base de données " + e.getMessage());
		}finally{
			closeConnection(connec);
		}
		
	}

	@Override
	public User read(long id) {
		// TODO Auto-generated method stub
		Connection connec = null;
		
		try{
			connec = getConnection();
			Statement stat = connec.createStatement();
			String select = "SELECT * FROM user where id = " + id;
				
			ResultSet res = stat.executeQuery(select);
			
			if(res.next()){
				User newUser = new User();
				newUser.setId(id);
				newUser.setEmail(res.getString("email"));
				newUser.setPseudonym(res.getString("pseudonym"));
				newUser.setPassword(res.getString("password"));
				return newUser;
			}			
			return null;

		}catch(SQLException e){
			throw new GalleryException("Erreur avec la base de données " + e.getMessage());
		}finally{
			closeConnection(connec);
		}
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		Connection connec = null;
		
		try{
			connec = getConnection();
			Statement stat = connec.createStatement();
			String update = "UPDATE user set pseudonym = ";
			
			//On remplit la requete avec les informations envoyés par l'utilisateur
			update += user.getPseudonym() + ", email = " + user.getEmail() + ", password =" + user.getPassword() +
					"  where id = " + user.getId();
			
			stat.executeUpdate(update);
			
		}catch(SQLException e){
			throw new GalleryException("Erreur avec la base de données " + e.getMessage());
		}finally{
			closeConnection(connec);
		}
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
		Connection connec = null;
		
		try{
			connec = getConnection();
			String delete = "DELETE user where id = ?";

			PreparedStatement stat = (PreparedStatement) connec.prepareStatement(delete);
			stat.setLong(1,id);;
			//On remplit la requete avec les informations envoyés par l'utilisateur
			
			stat.executeUpdate();
			
		}catch(SQLException e){
			throw new GalleryException("Erreur avec la base de données " + e.getMessage());
		}finally{
			closeConnection(connec);
		}
		
		
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://" + MYSQL_HOST + ":" +
		MYSQL_PORT + "/" + MYSQL_DATABASE, MYSQL_USER, MYSQL_PWD);
	}
	
	private void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("Error while closing the connection with the database");
			}
		}
	}
	
	
}
