package exceptions;

public class SiteNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public SiteNotFoundException() {
		super("The user has no password associated with "
    		+ "this site.");
	}
}
