package fr.inserm.tools;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

/**
 * classe de test de string file tools.
 * 
 * @author nmalservet <br>
 *         FIXME : tests a revoir, passer en dynamique les chemins d access sinon ça plante partout et revoir l init d
 *         el env de test si ficheir bien present.
 */
@Ignore
public class StringFileToolsTest extends TestCase {
	File fileToTest;
	File fileCopied;
	String targetFolder;

	/**
	 * Initialisation de l'environnement de test
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		URL filePath = this.getClass().getResource("/files/fileToTest.txt");
		String testFile = filePath.getFile();
		System.out.println("file:" + testFile);
		fileToTest = new File(testFile);
		URL filetestPath = this.getClass().getResource("/datatestfiles/");
		targetFolder = filetestPath.getFile();
		PrintWriter pw = new PrintWriter(fileToTest);
		pw.print("Ceci est un fichier de test");
		pw.close();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		if (fileToTest != null)
			fileToTest.delete();
		if (fileCopied != null)
			fileCopied.delete();
	}

	@Test
	public final void testFileToString() {
		assertEquals("Ceci est un fichier de test", StringFileTools.fileToString(fileToTest));
	}

	@Test
	public void testCopyFileToFolder() throws Exception {
		StringFileTools.copyFileToFolder(fileToTest, targetFolder);
		fileCopied = new File(targetFolder + fileToTest.getName());
		assertTrue(StringFileTools.fileToString(fileToTest).equals(StringFileTools.fileToString(fileCopied)));

	}

	@Test
	public final void testCopyFile() {
		StringFileTools.copyFile(fileToTest, targetFolder + fileToTest.getName());
		fileCopied = new File(targetFolder + fileToTest.getName());
		assertTrue(StringFileTools.fileToString(fileToTest).equals(StringFileTools.fileToString(fileCopied)));
		/**
		 * Test si paramètre incohérents. Si les tests suivants passent alors les Exceptions sont bien catchées.
		 */
		StringFileTools.copyFile(null, null);
		StringFileTools.copyFile(null, targetFolder);
		StringFileTools.copyFile(fileToTest, null);
		StringFileTools.copyFile(fileToTest, targetFolder);

	}

	@Test
	public final void testAddSlashPathDir() {
		String testString = "/data/folder";
		assertEquals("/data/folder/", StringFileTools.addSlashPathDir(testString));
		testString = testString + "/";
		assertEquals(testString, StringFileTools.addSlashPathDir(testString));
		assertNull(StringFileTools.addSlashPathDir(null));
		assertEquals("a", StringFileTools.addSlashPathDir("a"));
	}

	@Test
	public final void testMoveFileFileString() {
		String copyContent = StringFileTools.fileToString(fileToTest);
		StringFileTools.moveFile(fileToTest, targetFolder);
		assertFalse(fileToTest.exists());
		fileCopied = new File(targetFolder + fileToTest.getName());
		assertTrue(fileCopied.exists());
		assertEquals(copyContent, StringFileTools.fileToString(fileCopied));
		StringFileTools.moveFile(fileCopied, null);
		StringFileTools.moveFile(null, targetFolder);
		StringFileTools.moveFile(fileCopied, "");
	}

	@Test
	public final void testGetFilesFromFolder() throws Exception {
		String folderToTest = "/data/test/test/";
		assertNull(StringFileTools.getFilesFromFolder(folderToTest, ".txt"));
		folderToTest = "/data/test/";
		assertEquals(1, StringFileTools.getFilesFromFolder(folderToTest, ".txt").size());
		assertEquals(fileToTest.getName(), (StringFileTools.getFilesFromFolder(folderToTest, ".txt")).get(0).getName());
		assertEquals(0, StringFileTools.getFilesFromFolder(folderToTest, ".jpg").size());

	}

	@Test
	public final void testMoveFileFileStringString() {
		String copyContent = StringFileTools.fileToString(fileToTest);
		StringFileTools.moveFile(fileToTest, targetFolder, "test_");
		assertFalse(fileToTest.exists());
		fileCopied = new File(targetFolder + "test_" + fileToTest.getName());
		assertTrue(fileCopied.exists());
		assertEquals(copyContent, StringFileTools.fileToString(fileCopied));
		fileCopied.renameTo(new File(fileCopied.getPath() + ".encrypted"));
		fileCopied = new File(fileCopied.getPath() + ".encrypted");
		StringFileTools.moveFile(fileCopied, "/data/test/", "");
		fileCopied = new File("/data/test/" + fileCopied.getName().substring(0, fileCopied.getName().length() - 10));
		assertTrue(fileCopied.exists());
		assertEquals(copyContent, StringFileTools.fileToString(fileCopied));
		StringFileTools.moveFile(null, targetFolder, "");
		StringFileTools.moveFile(fileCopied, null, "");
		StringFileTools.moveFile(fileCopied, "", null);
		StringFileTools.moveFile(fileCopied, targetFolder, null);
		fileCopied = new File(targetFolder + fileCopied.getName());
		assertTrue(fileCopied.exists());
	}

}
