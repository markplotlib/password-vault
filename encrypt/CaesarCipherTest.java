/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package encrypt;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This file unit tests the CaesarCipher constructor, encrypt and decrypt
 * methods.
 * @author mark chesney
 * @version 1.0
 */
public class CaesarCipherTest {

	/**
	 * This tests the CaesarCipher constructor.
	 */
	@Test
	public void testConstructor() {
		CaesarCipher cc = new CaesarCipher();
		assertNotNull(cc);
	}

	/**
	 * This tests the encrypt function.
	 */
	@Test
	public void testEncrypt() {
		CaesarCipher cc = new CaesarCipher();
		String encryptedWord = cc.encrypt("apple");
		assertNotEquals("apple", encryptedWord);
	}

	/**
	 * This tests the decrypt function.
	 */
	@Test
	public void testDecrypt() {
        CaesarCipher cc = new CaesarCipher();
        String encryptedWord = cc.encrypt("fig");
        String decryptedWord = cc.decrypt(encryptedWord);
        assertEquals("fig", decryptedWord);
	}

}
