package fr.inserm.exporter;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

/**
 * classe pour crypter les fichiers.<br>
 * lire la doc pour en savoir plus sur les clés possibles : <url>http://docs.oracle
 * .com/javase/7/docs/technotes/guides/security/SunProviders.html</url>
 * 
 * 
 * @author nmalservet
 * 
 */
public class Crypter {

	private static final Logger LOGGER = Logger.getLogger(Crypter.class);

	public Crypter() {

	}

	/**
	 * crypte le texte fourni en utilisant l algorithme bloawfish et une phrase de mot de passe<br>
	 * Controle de la longueur max autorisée de key car selon jdk peut poser problemes de restrictions.
	 * 
	 * @param text
	 *            texte à encrypter
	 * @param password
	 *            chaine de caractere utile pour le decryptage, maximum size = 128
	 * @return le texte encrypté
	 */
	public static String encrypt(String text, String password) {
		String result = null;
		/**
		 * check de la cle pour limitation 128 bits
		 */
		try {
			int maxKey = Cipher.getMaxAllowedKeyLength("Blowfish");
			if (maxKey <= 128) {
				LOGGER.warn("max keysize:" + maxKey);

			}
		} catch (NoSuchAlgorithmException e1) {
			LOGGER.error(e1.getMessage());
		}
		/**
		 * limitation à 8 caractères pour s'assurer de la compatibilité avec l'ensemble des JRE
		 */
		if (password != null) {
			if (password.length() > 8) {
				LOGGER.error("le password doit etre inferieur a 8 caracteres car limitations de base a 128 bits sur clé de cryptage soit 16 octets");
				return result;
			}
		}
		try {
			byte[] plainText = text.getBytes("UTF8");
			Key secretKey = new SecretKeySpec(Crypter.stringToBytesASCII(password), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cipherText = cipher.doFinal(plainText);
			result = Base64.encodeBase64String(cipherText);
		} catch (Exception e) {
			LOGGER.error("encrypt exception:" + e.getMessage());
		}

		return result;
	}

	public static String decrypt(String encryptedText, String password) {
		String result = null;
		/**
		 * check de la cle pour limitation 128 bits
		 */
		try {
			int maxKey = Cipher.getMaxAllowedKeyLength("Blowfish");
			if (maxKey <= 128) {
				LOGGER.warn("max keysize" + maxKey);
			}
		} catch (NoSuchAlgorithmException e1) {
			LOGGER.error(e1.getMessage());
		}
		/**
		 * check de la limite de password
		 */
		if (password != null) {
			if (password.length() > 8) {
				LOGGER.error("le password doit etre inferieur a 8 cracateres car limitations de base a 128 bits sur clé de cryptage soit 16 octets");
				return result;
			}
		}
		try {
			byte[] plainText = Base64.decodeBase64(encryptedText.getBytes());
			Key secretKey = new SecretKeySpec(Crypter.stringToBytesASCII(password), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] newPlainText = cipher.doFinal(plainText);
			result = new String(newPlainText, "UTF8");
		} catch (Exception e) {
			LOGGER.error("exception:" + e.getMessage());
		}
		return result;
	}

	public static byte[] stringToBytesASCII(String str) {
		char[] buffer = str.toCharArray();
		byte[] b = new byte[buffer.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) buffer[i];
		}
		return b;
	}
}
