/*
 * Mark Chesney
 * CPSC 5011, Seattle University
 * free and unencumbered software released to public domain.
 */
package encrypt;

import java.util.Random;

/**
 * CaesarCipher method of encrypting and decrypting a string.
 * @author mark chesney
 * @version 1.0
 */
public class CaesarCipher implements Encryptor {

	/**
	 * CaesarCipher constructor
	 */
	public CaesarCipher() {
		shift = getShift();
	}

	@Override
	/**
	 * Encrypts the passed in string
	 * @param s the plaintext in string
	 * @return an encrypted string
	 */
	public String encrypt(String s) {
		return encryptDecrypt(s, true);
	}

	@Override
	/**
	 * Decrypts the passed in string
	 * @param s the encrypted string
	 * @return the decrypted, plaintext string
	 */
	public String decrypt(String s) {
		return encryptDecrypt(s, false);
	}

	/**
	 * Randomly selects encryptor character shift within the offset
	 * @return integer of shifted 
	 */
	private static int getShift() {
		Random r = new Random();
		int low = 1;
		int high = OFFSET_MAX - OFFSET_MIN;
		return r.nextInt(high - low) + low;
	}

	/**
	 * Encrypts or decrypts string
	 * @param s input string
	 * @param encrypt boolean, whether to encrypt or decrypt
	 * @return either encrypted or decrypted string 
	 * @throws IllegalArgumentException
	 */
	private String encryptDecrypt(String s, boolean encrypt) throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			int indx = c, cpos;
			if (!isPositionInRange(indx))
				throw new IllegalArgumentException("String to be encrypted has unrecognized character " + c);

			if (encrypt) {
				cpos = indx + shift;
				if (cpos > OFFSET_MAX)
					cpos = OFFSET_MIN + (cpos - OFFSET_MAX);
			} else {
				cpos = indx - shift;
				if (cpos < OFFSET_MIN)
					cpos = OFFSET_MAX - (OFFSET_MIN - cpos);
			}
			sb.append((char)cpos);
		}
		return sb.toString();
	}

	/**
	 * whether index is in range of offset
	 * @param indx index value
 	 * @return  whether index is in range of offset
	 */
	private boolean isPositionInRange(int indx) {
		return indx >= OFFSET_MIN && indx <= OFFSET_MAX;
	}

	private int shift;
    private static final int OFFSET_MIN = 32;
    private static final int OFFSET_MAX = 126;
}
