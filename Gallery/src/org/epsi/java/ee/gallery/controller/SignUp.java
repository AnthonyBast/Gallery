package org.epsi.java.ee.gallery.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.epsi.java.ee.gallery.exception.GalleryException;
import org.epsi.java.ee.gallery.model.User;

public class SignUp extends HttpServlet {
	private static final long serialVersionUID = -2135238259758756344L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/signUp.jsp")
			.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudonym = request.getParameter("pseudonym");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean isCguValidated = "on".equals(request.getParameter("cgu"));

		try {
			User user = createUser(pseudonym, email, password, isCguValidated);
			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp")
				.forward(request, response);
		} catch (GalleryException galleryException) {
			request.setAttribute("error", galleryException.getMessage());
			request.getRequestDispatcher("/signUp.jsp")
			.forward(request, response);
		}
	}

	private User createUser(String pseudonym, String email, String password, boolean isCguValidated) {
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
