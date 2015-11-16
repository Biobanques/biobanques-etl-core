package fr.inserm.extractor;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.extractor.siteextractor.TumorotekExtractor;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * test de tumorotek extractor.<br>
 * ignore du test car base tk pas obligatoire.
 * 
 * @author nmalservet
 * 
 */
@Ignore
public class TumorotekExtractorTest extends TestCase {
	@Test
	public void testGetConnection() {
		TumorotekExtractor ex = new TumorotekExtractor(null);
		try {
			assertNotNull(ex.getConnection("localhost", "3306", "tkv2", "root",
					"root"));
		} catch (SQLException e) {
			fail("une exception a ete levee alors que normal" + e.getMessage());
		}

		TumorotekExtractor ex2 = new TumorotekExtractor(null);
		Connection c = null;
		try {
			c = ex2.getConnection("localhost", "3306", "tkv3", "root", "root");
			assertNull(c);
			fail("normalement une exception doit etre levee");
		} catch (SQLException e) {
			// ok
		}
		assertNull(c);
	}

	@Test
	public void testExtract() {
		Extractor ex = new Extractor(
				new PropertiesBean(new ApplicationLoader()));
		FileInputBean fib = ex.extract();
		assertNotNull(fib);
	}
}
