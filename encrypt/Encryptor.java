/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package encrypt;

/**
 * Encryptor interface can be used to encrypt and decrypt a string.
 * @author mark chesney
 * @version 1.0
 */
public interface Encryptor {
	
	/**
	 * Encrypts the passed in string
	 * @param s The string to encrypt
	 * @return  The encrypted string
	 */
	String encrypt(String s);
	
	/**
	 * Decrypts the passed in string
	 * @param s The string to decrypt
	 * @return  The (plaintext) decrypted string
	 */
	String decrypt(String s);
	
}