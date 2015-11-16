package fr.inserm.exporter;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import fr.inserm.tools.StringFileTools;

public class CrypterTest extends TestCase {
	Crypter crypt = new Crypter();

	@Test
	public void testEncryptDecryptWithBadValues() {
		/**
		 * Test des methodes encrypt et decrypt avec des valeurs nulles
		 */
		String texte = "montexte";
		String password = "mypass";
		assertNull(Crypter.encrypt(null, null));
		assertNull(Crypter.encrypt(null, password));
		assertNull(Crypter.encrypt(texte, null));
		String encrypted = Crypter.encrypt(texte, password);
		assertNull(Crypter.decrypt(encrypted, null));
		assertNull(Crypter.decrypt(null, password));
		assertNull(Crypter.decrypt(null, null));

	}

	@Test
	public void testEncryptDecrypt() {
		String texte = "super text";
		String password = "nicolas";
		String encrypted = Crypter.encrypt(texte, password);
		String encryptedwithoutPassword = Crypter.encrypt(texte, "");
		assertNotNull(encrypted);
		assertNull(encryptedwithoutPassword);
		String decrypted = Crypter.decrypt(encrypted, password);
		assertNull(Crypter.decrypt(encryptedwithoutPassword, ""));
		assertEquals(texte, decrypted);
	}

	@Test
	public void testEncryptDecriptNice() {
		/**
		 * teste avec le fichie de nice export full 1000 echas.
		 */
		String password = "nicolas";
		ClassLoader loader = CrypterTest.class.getClassLoader();

		String pathFile = loader.getResource("examples/nice_export_inserm_1_1_sur_3_09_57_24072013.xml").getPath();

		File file = new File(pathFile);
		String fichierXML = StringFileTools.fileToString(file);
		String enc = Crypter.encrypt(fichierXML, password);
		assertNotNull(enc);
		assertEquals(fichierXML, Crypter.decrypt(enc, password));
	}

	@Test
	public void testEcryptDecriptLongKey() {
		/**
		 * test encrypt avec une cle longue (>8 caracteres = 128 bits)
		 */
		String texte = "super text";
		String password = "ma phrase de chiffrement personnelle";
		ClassLoader loader = CrypterTest.class.getClassLoader();
		String pathFile = loader.getResource("examples/nice_export_inserm_1_1_sur_3_09_57_24072013.xml").getPath();
		File file = new File(pathFile);
		String fichierXML = StringFileTools.fileToString(file);
		String enc = Crypter.encrypt(texte, password);
		assertNull(enc);
		assertNull(Crypter.encrypt(fichierXML, password));
		assertNull(Crypter.decrypt(enc, password));
	}

}
