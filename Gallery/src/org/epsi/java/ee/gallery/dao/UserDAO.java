package org.epsi.java.ee.gallery.dao;

import org.epsi.java.ee.gallery.model.User;

public interface UserDAO {
	/**
	* Insert a User in database
	*
	* @param newUser: The user to insert. Must not be null.
	* @return The user created
	*/
	User create(User newUser);
	/**
	* Retrieve a User from databases
	*
	* @param id: The id of the user to retrieved
	* @return The user with the id given in parameters, null otherwise.
	*/
	User read(long id);
	/**
	* Update user information in the database based on user id.
	*
	* @param user: The user to update. Must not be null.
	*/
	void update(User user);
	/**
	* Remove a user from the database.
	*
	* @param id: The id of the user to remove.
	*/
	void delete(long id);
}
