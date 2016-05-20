package org.epsi.java.ee.gallery.controller;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.epsi.java.ee.gallery.model.Image;

public class Library extends HttpServlet {
	private static final long serialVersionUID = -5929900245809585946L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("images", filledImages("images"));
		request.getRequestDispatcher("/library.jsp")
			.forward(request, response);
	}

	private List<Image> filledImages(String directory) throws IOException {
		List<Image> images = new ArrayList<Image>();
		Path path = Paths.get(getServletContext().getRealPath(directory));
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
		for (Path filePath : directoryStream) {
			String fileName = filePath.getFileName()
				.toString();
			images.add(new Image(createImageName(fileName), directory + "/" + fileName));
		}

		return images;
	}

	private String createImageName(String fileName) {
		String[] split = fileName.split("\\.");
		if (split.length > 0) {
			return split[0];
		}

		return fileName;
	}

}
