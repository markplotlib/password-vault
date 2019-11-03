package exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
    	super("There is no such user in the vault");
    }
}
