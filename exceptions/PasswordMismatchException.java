package exceptions;

public class PasswordMismatchException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasswordMismatchException() {
		super("The password supplied does not match this"
    		+ " user's vault password.");
	}
}
