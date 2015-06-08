package net.alteiar.view.exception;

public class FXMLException extends Exception {

	private static final long serialVersionUID = 1L;

	public FXMLException() {
		super();
	}

	public FXMLException(String message, Throwable cause) {
		super(message, cause);
	}

	public FXMLException(String message) {
		super(message);
	}

	public FXMLException(Throwable cause) {
		super(cause);
	}
}
