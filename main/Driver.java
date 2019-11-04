/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package main;
import vault.*;
import exceptions.*;

/**
 * Driver class demonstrates functionality and edge cases of PasswordVault
 * and CaesarCipher implementation files. Users can store login credentials
 * for websites into a password protected system.
 *
 * @author mark chesney
 * @version 1.0
 */
public class Driver {

    static Vault vault = new PasswordVault();


	public static void testAddUser(String username, String password) {
        System.out.printf("Adding: user = '%s',\tpassword = '%s'.\n",
            username, password);
	    try {
			vault.addNewUser(username, password);
		} catch (InvalidUsernameException | InvalidPasswordException |
				 DuplicateUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
    }

	public static void main(String[] args) {

        // Strings for password testing
	    // String pw1 = null, pw2 = null, getpw1 = null, getpw2 = null;
	    // String newpw1 = null;
        // String pw1a = null, pw2a = null;

        // add the first new user, setting password
        testAddUser( "firstuser", "asdf!9" );

        // catch InvalidUsernameException
        testAddUser("1234", "asdfasdf0&");

        // catch InvalidPasswordException
       testAddUser("userjoebob", "ok");

        // catch DuplicateUserException
		testAddUser("firstuser", "asdf!9");

/*
        // add new site, returning generated password
	    try {
			pw1 = vault.addNewSite("firstuser", "asdf!9", "amazon");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: try to add same site again
	    try {
			pw1 = vault.addNewSite("firstuser", "asdf!9", "amazon");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: add site for non-existant user
	    try {
			pw1 = vault.addNewSite("userhenryphilip", "qwert123#$%", "rei");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // add a second site, returning generated password
	    try {
			pw2 = vault.addNewSite("firstuser", "asdf!9", "nordstrom");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: wrong password
	    try {
			pw2 = vault.addNewSite("firstuser", "tokyo123!@#", "rakuten");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: invalid site name
	    try {
			pw2 = vault.addNewSite("firstuser", "asdf!9", "abc123");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	    System.out.println(pw1);
	    System.out.println(pw2);

        // retrieve password for first site
        try {
			getpw1 = vault.retrieveSitePassword("firstuser", "asdf!9",
												"amazon");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(getpw1);

        // retrieve password for second site
        try {
			getpw2 = vault.retrieveSitePassword("firstuser", "asdf!9",
												"nordstrom");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(getpw2);

        // fail: this site has not been stored
        try {
			getpw2 = vault.retrieveSitePassword("firstuser", "asdf!9",
												"easterbunny");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(getpw2);

        // fail: user not found
        try {
			getpw2 = vault.retrieveSitePassword("chickenuser", "poiuyt!9",
												"duckduckgo");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(getpw2);

        // update password for second site
	    try {
			newpw1 = vault.updateSitePassword("firstuser", "asdf!9",
											  "nordstrom");
		} catch (SiteNotFoundException | UserNotFoundException | UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(newpw1);

        // update password for second site
	    try {
			newpw1 = vault.updateSitePassword("firstuser", "asdf!9",
											  "nordstrom");
		} catch (SiteNotFoundException | UserNotFoundException | UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	    System.out.println(newpw1);

        // add a second user, setting password
	    try {
			vault.addNewUser("seconduser", "qwerty0#");
		} catch (InvalidUsernameException | InvalidPasswordException |
				 DuplicateUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // add new site to second user
	    try {
			pw1a = vault.addNewSite("seconduser", "qwerty0#", "hotels");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        System.out.println(pw1a);

        // 1st failed attempt at logging in
        try {
            pw2a = vault.retrieveSitePassword("seconduser", "ASDFqwerty0#",
        									  "hotels");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // 2nd failed attempt at logging in
        try {
            pw2a = vault.retrieveSitePassword("seconduser", "ASDFqwerty0#",
        									  "hotels");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // 3rd failed attempt -- user should be locked out
        try {
            pw2a = vault.retrieveSitePassword("seconduser", "ASDFqwerty0#",
        									  "hotels");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: second user locked out from adding new site
	    try {
			pw2a = vault.addNewSite("seconduser", "qwerty0#", "kayak");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: second user locked out from retrieving password
        try {
			pw2a = vault.retrieveSitePassword("seconduser", "qwerty0#",
												"hotels");
		} catch (SiteNotFoundException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: site not found
	    try {
			pw2a = vault.updateSitePassword("seconduser", "qwerty0#",
												"jimmyjohns");
		} catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: user not found
	    try {
			pw2a = vault.updateSitePassword("userjimmyjohn", "qwerty0#",
												"bologna");
		} catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: bad password
	    try {
			pw2a = vault.updateSitePassword("firstuser", "asdf!9", "amazon");
		} catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: user locked out from updating password
	    try {
			pw2a = vault.updateSitePassword("seconduser", "qwerty0#",
												"hotels");
		} catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: bad password
	    try {
			pw2a = vault.updateSitePassword("firstuser", "oogabooga8@",
												"amazon");
		} catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

        // fail: user locked out from adding new site
        try {
            pw2a = vault.addNewSite("seconduser", "qwerty0#", "redrobin");
		} catch (DuplicateSiteException | UserNotFoundException |
				 UserLockedOutException | PasswordMismatchException
				| InvalidSiteException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
*/
	}

}
