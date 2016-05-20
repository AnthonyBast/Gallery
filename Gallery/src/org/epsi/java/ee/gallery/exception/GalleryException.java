package org.epsi.java.ee.gallery.exception;

public class GalleryException extends RuntimeException {
	private static final long serialVersionUID = -6717756986647753869L;

	public GalleryException() {
		super();
	}

	public GalleryException(String message, Throwable cause) {
		super(message, cause);
	}

	public GalleryException(String message) {
		super(message);
	}

	public GalleryException(Throwable cause) {
		super(cause);
	}
}
