package fr.inserm.exporter;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.extractor.siteextractor.TumorotekExtractor;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * classe de test de l export xml.<br>
 * Clase de test d export XML depuis uen base TK. <br>
 * Pré-requis avoir une base tK installée.<br>
 * TODO : travailler plutôt avec des bases dynamiquement allouées pour les tests
 * 
 * @author nmalservet
 * 
 */
@Ignore
public class XMLExporterTest extends TestCase {
	ApplicationLoader loader = new ApplicationLoader();
	PropertiesBean props = new PropertiesBean(loader);

	TumorotekExtractor ex = new TumorotekExtractor(props);
	FileInputBean fib = ex.extract();

	@Test
	public void testExport() {
		XMLExporter exp = new XMLExporter(props);
		assertEquals(-1, exp.exportFile(null));
		assertEquals(0, exp.exportFile(fib));
		assertEquals(-1, exp.exportFile(new FileInputBean()));
		exp.properties.setCompress(false);
		assertEquals(0, exp.exportFile(fib));
		exp.properties.setCrypt(false);
		assertEquals(0, exp.exportFile(fib));
		exp.properties.setCrypt(true);
		assertEquals(0, exp.exportFile(fib));
		exp.properties.setCompress(true);
		assertEquals(0, exp.exportFile(fib));
	}
}
