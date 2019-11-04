/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package main;
import vault.*;
import exceptions.*;

/**
 * Driver class demonstrates functionality and edge cases of PasswordVault.
 * Users can store login credentials
 * for websites into a password protected system.
 *
 * @author mark chesney
 * @version 1.0
 */
public class Driver {

	public static void main(String[] args) {

        // add the first new user, setting password
        addOneUser( "firstuser", "asdf!9" );
        testAddUserFailCases();

        // add a first site, returning generated password
        addOneSite("firstuser", "asdf!9", "amazon");
        // repeat for another
        addOneSite("firstuser", "asdf!9", "nordstrom");
        testAddSiteFailCases();

        // retrieve password for first site
        getOnePassword("firstuser", "asdf!9", "amazon");
        testRetrieveFailCases();
        
/*
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

    private static void testAddUserFailCases() {
        System.out.println("\nExpected-Fail tests of addNewUser...");
        // catch InvalidUsernameException
        addOneUser("1234", "asdfasdf0&");

        // catch InvalidPasswordException
        addOneUser("userjoebob", "ok");

        // catch DuplicateUserException
        addOneUser("firstuser", "asdf!9");
    }

    private static void testAddSiteFailCases() {
        System.out.println("\nExpected-Fail tests of addNewSite...");
        // catch DuplicateSiteException
        addOneSite("firstuser", "asdf!9", "amazon");

        // catch UserNotFoundException
        addOneSite("userhenryphilip", "qwert123#$%", "rei");

        // catch PasswordMismatchException
        addOneUser("seconduser", "ASDFqwerty0#");
        addOneSite("seconduser", "wrongpw0#", "hotels");

        // catch UserLockedOutException
        addOneSite("seconduser", "wrongpw0#", "hotels");
        addOneSite("seconduser", "wrongpw0#", "hotels");
        addOneSite("seconduser", "wrongpw0#", "hotels");

        // catch InvalidSiteException
        addOneSite("firstuser", "asdf!9", "abc123");
    }

    private static void testRetrieveFailCases() {
        System.out.println("\nExpected-Fail tests of retrieveSitePassword ...");
        // catch SiteNotFoundException
        getOnePassword("firstuser", "asdf!9", "boogieman");

        // catch UserNotFoundException
        getOnePassword("userhenryphilip", "qwert123#$%", "rei");

        // catch PasswordMismatchException
        addOneUser("thirduser", "ASDFqwerty0#");
        getOnePassword("thirduser", "wrongpw0#", "hotels");

        // catch UserLockedOutException
        getOnePassword("thirduser", "wrongpw0#", "hotels");
        addOneSite("thirduser", "wrongpw0#", "hotels");
        addOneSite("thirduser", "wrongpw0#", "hotels");
    }

    private static void addOneUser(String username, String password) {
        String caseNum = "case #" + ++testCaseCount;
        System.out.printf(caseNum + ": Adding user = '%s',\tpassword = '%s'.\n",
            username, password);
        try {
            vault.addNewUser(username, password);
        } catch (InvalidUsernameException | InvalidPasswordException |
                 DuplicateUserException e) {
            System.err.println("Caught Exception (" + caseNum + "): "
                 + e.getMessage());
        } finally {
            System.out.println();
        }
    }

    private static void addOneSite(String username, String password,
                                    String sitename) {
        String encryptedPassword;
        String caseNum = "case #" + ++testCaseCount;
        System.out.printf(caseNum + ": Adding site '%s' for user '%s' => ",
                sitename, username);
        try {
            encryptedPassword = vault.addNewSite(username, password, sitename);
            System.out.printf("generating site password: %s.\n",
                encryptedPassword);
        } catch (DuplicateSiteException | UserNotFoundException
                | UserLockedOutException | PasswordMismatchException
                | InvalidSiteException e) {
            System.err.println("Caught Exception (" + caseNum + "): "
                 + e.getMessage());
        } finally {
            System.out.println();
        }
    }

    private static void getOnePassword(String username, String password,
                                        String sitename) {
        String encryptedPassword;
        String caseNum = "case #" + ++testCaseCount;
        System.out.printf(caseNum + ": Retrieving site password for '%s' " +
            "for user '%s' => ", sitename, username);
        try {
            encryptedPassword = vault.retrieveSitePassword(username, password,
                                                            sitename);
            System.out.printf("password: %s.\n", encryptedPassword);
        } catch (SiteNotFoundException | UserNotFoundException |
                 UserLockedOutException | PasswordMismatchException e) {
             System.err.println("Caught Exception (" + caseNum + "): "
                          + e.getMessage());
        } finally {
            System.out.println();
        }
    }

    static Vault vault = new PasswordVault();

    // unique test case ID displayed
    private static int testCaseCount = 100;
}
