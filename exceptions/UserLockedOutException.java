package exceptions;

public class UserLockedOutException extends Exception {

	private static final long serialVersionUID = 1L;

    public UserLockedOutException() {
		super("The user has been locked out due to "
    		+ "too many incorrect password attempts.");
	}
}
