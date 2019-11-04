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
        // test all exceptions on this feature
        testAddUserFailCases();

        // add a first site, returning generated password
        addOneSite("firstuser", "asdf!9", "amazon");
        // repeat for another
        addOneSite("firstuser", "asdf!9", "nordstrom");
        // test all exceptions on this feature
        testAddSiteFailCases();

        // retrieve password for first site
        getOnePassword("firstuser", "asdf!9", "amazon");
        // test all exceptions on this feature
        testRetrieveFailCases();

        // update password for first site
        setOnePassword("firstuser", "asdf!9", "nordstrom");
        // test all exceptions on this feature
        testUpdateFailCases();
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
        addOneSite("thirduser", "ASDFqwerty0#", "hotels");
        getOnePassword("thirduser", "wrongpw0#", "hotels");

        // catch UserLockedOutException
        getOnePassword("thirduser", "wrongpw0#", "hotels");
        getOnePassword("thirduser", "wrongpw0#", "hotels");
        getOnePassword("thirduser", "wrongpw0#", "hotels");
    }

    private static void testUpdateFailCases() {
        System.out.println("\nExpected-Fail tests of updateSitePassword ...");
        // catch SiteNotFoundException
        setOnePassword("firstuser", "asdf!9", "hodor");

        // catch UserNotFoundException
        setOnePassword("userlouisxvi", "qwert123#$%", "buyinganewhead");

        // catch PasswordMismatchException
        addOneUser("fourthuser", "ASDFqwerty0#");
        addOneSite("fourthuser", "ASDFqwerty0#", "hotels");
        setOnePassword("fourthuser", "wrongpw0#", "hotels");

        // catch UserLockedOutException
        setOnePassword("fourthuser", "wrongpw0#", "hotels");
        setOnePassword("fourthuser", "wrongpw0#", "hotels");
        setOnePassword("fourthuser", "wrongpw0#", "hotels");
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

    private static void setOnePassword(String username, String password,
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
