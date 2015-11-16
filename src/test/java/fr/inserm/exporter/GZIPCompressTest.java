package fr.inserm.exporter;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

public class GZIPCompressTest extends TestCase {
	ClassLoader loader = GZIPCompressTest.class.getClassLoader();
	String pathFile = loader.getResource("examples/nice_export_inserm_1_1_sur_3_09_57_24072013.xml").getPath();
	File file = new File(pathFile);

	@Test
	public void testCompress() {
		assertEquals(0, GZIPCompress.compress(file));
		assertEquals(-1, GZIPCompress.compress(null));
	}

	@Test
	public void testUncompress() {
		String pathCompressedFile = pathFile + ".gz";
		File filecompressed = new File(pathCompressedFile);
		assertEquals(0, GZIPCompress.uncompress(filecompressed));
		assertEquals(-1, GZIPCompress.uncompress(file));
		assertEquals(-1, GZIPCompress.uncompress(null));

	}

}
