package org.epsi.java.ee.gallery.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.epsi.java.ee.gallery.dao.UserDAO;
import org.epsi.java.ee.gallery.exception.GalleryException;
import org.epsi.java.ee.gallery.model.User;
import org.epsi.java.ee.gallery.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserDAO userDao;
	
	@Inject
	public UserServiceImpl (UserDAO userDao){
		this.userDao = userDao;
	}

	@Override
	public User create(String pseudonym, String email, String password, boolean isCguValidated) {
		// TODO Auto-generated method stub
		if (pseudonym == null || pseudonym.length() < 1 || pseudonym.length() > 10) {
			throw new GalleryException("Pseudonyme invalide");
		}

		if (email == null) {
			throw new GalleryException("L'email doit être remplit");
		}

		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new GalleryException("Le format de l'email est invalide");
		}

		if (password.length() < 8 || password.length() > 30) {
			throw new GalleryException("Le mot de passe doit contenir entre 8 et 30 caractères");
		}

		if (!isCguValidated) {
			throw new GalleryException("Les CGU doivent être validé");
		}

		return new User(pseudonym, email, password);
	}

}
