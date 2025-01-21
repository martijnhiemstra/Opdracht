package nl.humaninference.person.exception;

/**
 * This exception is thrown when a validation exception takes place for example when saving a 
 * person and the name is blank
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 2622550096593647966L;

	public ValidationException(String message) {
		super(message);
	}

}
