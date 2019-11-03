package exceptions;

public class DuplicateSiteException extends Exception {

    private static final long serialVersionUID = 1L;

    public DuplicateSiteException() {
		super("There is already a site stored for this"
    		+ " user.");
	}
}
