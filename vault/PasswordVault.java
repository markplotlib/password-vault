/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package vault;
import encrypt.*;
import exceptions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * PasswordVault implements the Vault interface, adding new users and new
 * sites to the vault; generating, storing and retrieving encrypted passwords.
 * @author mark chesney
 * @version 1.0
 */
public class PasswordVault implements Vault {

	/**
	 * no-argument constructor defaults to the CaesarCipher implementation
	 * of Encryptor
	 */
    public PasswordVault() {
    	this(new CaesarCipher());
    }

	/**
	 * constructor allows client to designate an Encryptor of their choosing
	 * @param encr implementation of Encryptor interface. encrypts a string
	 */
    public PasswordVault(Encryptor encr) {
        encryptor = encr;
        vaultKeyRing = new HashMap<String, HashMap<String, String>>();
        vaultLogin = new HashMap<String, String>();
        failedLogins = new HashMap<String, Integer>();
    }

    @Override
	/**
	 * Add a new user to the vault (with no site passwords). For example, a
	 * username and password is supplied, and the system does no verification
	 * or checking except that the username and password must be in correct
	 * formats, and the username cannot already be in the vault.
	 *
	 * @param username The username to be added
	 * @param password The password to be associated with this user
	 * @throws InvalidUsernameException The supplied username is invalid
	 * @throws InvalidPasswordException The supplied password is invalid
	 * @throws DuplicateUserException   The username is already in the vault
	 */
    public void addNewUser(String username, String password) throws
    InvalidUsernameException, InvalidPasswordException,
    DuplicateUserException {

        // --------------------
        // Exception checking
        // --------------------
        // The supplied username is invalid
        if (!username.matches(VALID_USERNAME))
        	throw new InvalidUsernameException();
        // The supplied password is invalid
        if (!password.matches(VALID_PASSWORD))
      	  	throw new InvalidPasswordException();
        // The username is already in the vault
		if (vaultKeyRing.containsKey(username))
        	throw new DuplicateUserException();

        // add new user to vaultKeyRing
        vaultKeyRing.put(username, null);

        // add new user and ENCRYPTED password to vaultLogin
        vaultLogin.put(username, encryptor.encrypt(password));

        // set counter of login attempts to zero
        failedLogins.put(username, 0);
    }


	@Override
	/**
	 * Adds a new site to the vault for the user, generates, stores, and
	 * returns a password for that site. For example, if a user is creating
	 * an account at the site "amazon" for the first time, he/she calls the
	 * vault with the site name, and the site makes up a password for the
	 * user/site, returns the (plain text password to the user, and stores
	 * the (encrypted) password -- all stored passwords in the vault.
	 *
	 * @param username The username requesting the new site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return 		   A new (plaintext) password for the requested site
	 * @throws DuplicateSiteException    There is already a site stored for this
	 *                                   user
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 * @throws InvalidSiteException      The site name supplied is invalid
	 */
	public String addNewSite(String username, String password, String sitename)
	throws DuplicateSiteException, UserNotFoundException,
	UserLockedOutException, PasswordMismatchException, InvalidSiteException {

        String plaintextPassword, encryptedPassword;
        Random rand = new Random();
		Integer randInt = rand.nextInt(9);
        String randNum = randInt.toString();

	    // --------------------
	    // Exception checking
	    // --------------------
        // There is no such user in the vault
        if (!vaultKeyRing.containsKey(username))
            throw new UserNotFoundException();
        // There is already a site stored for this user
		if (vaultKeyRing.get(username) != null &&
            vaultKeyRing.get(username).containsKey(sitename))
        	throw new DuplicateSiteException();
        // user is locked out due to too many incorrect password attempts
        if (failedLogins.get(username) >= MAX_LOGIN_ATTEMPTS)
    		throw new UserLockedOutException();
        // The password supplied does not match the user's vault password
        if (!encryptor.encrypt(password).equals(vaultLogin.get(username))) {
            failedLogins.put(username, failedLogins.get(username) + 1);
            throw new PasswordMismatchException();
        } // The site name supplied is invalid
        if (!sitename.matches(VALID_SITENAME))
            throw new InvalidSiteException();

        // reset counter of login attempts to zero (in case it were > 0)
        failedLogins.put(username, 0);

        // generate unencrypted password
        plaintextPassword = username.substring(0, 4) +
        		sitename.substring(0, 4) + randNum + "!";

        // encrypt password
        encryptedPassword = encryptor.encrypt(plaintextPassword);

        // store sitename and encrypted password in dictionary
        siteKey.put(sitename, encryptedPassword);

        // store this dictionary within a dictionary
        vaultKeyRing.put(username, siteKey);

        return plaintextPassword;
	}


	@Override
	/**
	 * Retrieve the (plaintext) password for the user for the requested site.
	 * For example, the user supplies the name of a site, and if she has a
	 * stored password for the site, it is returned in plain text.
	 *
	 * @param username The username requesting the site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return The (plaintext) password for the requested site
	 * @throws SiteNotFoundException     The user has no password associated with
	 *                                   this site
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 */
	public String retrieveSitePassword(String username, String password,
	String sitename) throws SiteNotFoundException, UserNotFoundException,
	UserLockedOutException, PasswordMismatchException {

        // There is no such user in the vault
        if (!vaultKeyRing.containsKey(username))
            throw new UserNotFoundException();
        // The user has no password associated with this site
        if (!vaultKeyRing.get(username).containsKey(sitename))
            throw new SiteNotFoundException();
        // user is locked out due to too many incorrect password attempts
        if (failedLogins.get(username) >= MAX_LOGIN_ATTEMPTS)
    		throw new UserLockedOutException();
        // The password supplied does not match the user's vault password
        if (!encryptor.encrypt(password).equals(vaultLogin.get(username))) {
            failedLogins.put(username, failedLogins.get(username) + 1);
            throw new PasswordMismatchException();
        }

        // reset counter of login attempts to zero (in case it were > 0)
        failedLogins.put(username, 0);

        // return plain-text (unencrypted) password
        return encryptor.decrypt(vaultKeyRing.get(username).get(sitename));
	}


	@Override
	/**
	 * Generate, store, and return an updated password for a site associated
	 * with the user. For example, the user wants to change his/her password on
	 * "amazon," and the system generates a new password, stores an encrypted
	 * version for the user, and returns the plaintext version.
	 *
	 * @param username The username requesting the new site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return An updated (plaintext) password for the requested site
	 * @throws SiteNotFoundException     The user has no password associated with
	 *                                   this site
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 */
	public String updateSitePassword(String username, String password,
	String sitename) throws SiteNotFoundException, UserNotFoundException,
	UserLockedOutException, PasswordMismatchException {

        String plaintextPassword, encryptedPassword;

        // There is no such user in the vault
        if (!vaultKeyRing.containsKey(username))
            throw new UserNotFoundException();
        // The user has no password associated with this site
		if (!vaultKeyRing.get(username).containsKey(sitename))
            throw new SiteNotFoundException();
        // user is locked out due to too many incorrect password attempts
        if (failedLogins.get(username) >= MAX_LOGIN_ATTEMPTS)
    		throw new UserLockedOutException();
        // The password supplied does not match the user's vault password
        if (!password.equals(vaultLogin.get(username))) {
            failedLogins.put(username, failedLogins.get(username) + 1);
            throw new PasswordMismatchException();
        }

        // reset counter of login attempts to zero (in case it were > 0)
        failedLogins.put(username, 0);

        // get plain-text (unencrypted) password
        plaintextPassword = encryptor.decrypt(
                                    vaultKeyRing.get(username).get(sitename));
        // shift the first character to become the last
        StringBuilder sb = new StringBuilder(plaintextPassword);
        sb.append(sb.charAt(0));
        sb.deleteCharAt(0);
        plaintextPassword = sb.toString();

        // encrypt password
        encryptedPassword = encryptor.encrypt(plaintextPassword);

        // add sitename and encrypted password to site key pair
        siteKey.put(sitename, encryptedPassword);

        // add site key pair to key ring
        vaultKeyRing.put(username, siteKey);

        return plaintextPassword;
	}


	// double-tiered hash map, designed to store
	// a hash map of site-password pairs for each username
	private Map<String, HashMap<String, String>> vaultKeyRing;

	// stand-alone hash map, storing vault password for each username
	private Map<String, String> vaultLogin = new HashMap<String, String>();

    // stand-alone hash map of username's count of password failed attempts
    private Map<String, Integer> failedLogins = new HashMap<String, Integer>();

    // password encryptor
    private Encryptor encryptor;

    // declare inner hash map, to go inside vaultKeyRing
    private HashMap<String, String> siteKey = new HashMap<String, String>();

    // regular expression patterns
    private final String VALID_USERNAME = "[a-z]{6,12}";
    private final String VALID_SITENAME = "[a-z]{6,12}";
    private final String VALID_PASSWORD =
    		"((?=.*[a-zA-Z])(?=.*[\\d])(?=.*[!@#$%^&]).{6,15})";
    private final int MAX_LOGIN_ATTEMPTS = 3;
}
