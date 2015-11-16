package fr.inserm.extractor;

import junit.framework.TestCase;

import org.junit.Ignore;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * classe de test de la classe extractor.
 * 
 * @author nicolas
 * 
 */
@Ignore
public class ExtractorTest extends TestCase {
	ApplicationLoader loader = new ApplicationLoader();
	PropertiesBean props = new PropertiesBean(loader);

	public void testExtract() {
		Extractor ex = new Extractor(null);
		assertNull(ex.extract());
		assertNull(ex.getAlertes());
		try {
			assertNull(ex
					.getConnection(props.getHost(), props.getPort(),
							props.getDbname(), props.getUsername(),
							props.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ex = new Extractor(props);
		assertNotNull(ex.extract());
		assertNotNull(ex.getAlertes());
		try {
			assertNotNull(ex
					.getConnection(props.getHost(), props.getPort(),
							props.getDbname(), props.getUsername(),
							props.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
