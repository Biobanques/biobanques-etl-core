package tools;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import fr.inserm.tools.StringFileTools;

public class StringFileToolsTest extends TestCase {
	File fileToTest = new File("/data/test/testFile1.txt");

	@Test
	public void testCopyFileToFolder() {
		String newFolder = "/data/testResult/";
		StringFileTools.copyFileToFolder(fileToTest, newFolder);
		// assertTrue(new File(newFolder.));
	}

	@Test
	public final void testMoveFile() {

	}

}
