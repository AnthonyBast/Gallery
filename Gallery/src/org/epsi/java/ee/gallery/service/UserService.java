package org.epsi.java.ee.gallery.service;

import org.epsi.java.ee.gallery.model.User;

public interface UserService {
	/**

	 * Validate data given in parameters, create a user with, hash the password and store the user.

	 *

	 * @param pseudonym: The user's pseudonym

	 * @param email: The user's email

	 * @param password: The user password not hashed

	 * @param isCguValidated: true if the cgu are validated, false otherwise

	 * @return The user created

	 */

	User create(String pseudonym, String email, String password,boolean isCguValidated);

}
