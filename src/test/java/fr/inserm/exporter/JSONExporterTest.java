package fr.inserm.exporter;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.extractor.siteextractor.TumorotekExtractor;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * classe de test de l export json depuis une base tk.<br>
 * Pre-requis avoir une base TK installée.
 * 
 * @author nmalservet
 * 
 */
@Ignore
public class JSONExporterTest extends TestCase {
	@Test
	public void testExport() {
		ApplicationLoader loader = new ApplicationLoader();
		PropertiesBean props = new PropertiesBean(loader);
		TumorotekExtractor ex = new TumorotekExtractor(props);
		FileInputBean fib = ex.extract();
		JSONExporter exp = new JSONExporter(props);
		exp.properties.setCrypt(true);
		exp.properties.setCompress(true);
		assertEquals(0, exp.exportFile(fib));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exp.properties.setCrypt(false);
		exp.properties.setCompress(true);
		assertEquals(0, exp.exportFile(fib));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exp.properties.setCrypt(true);
		exp.properties.setCompress(false);
		assertEquals(0, exp.exportFile(fib));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exp.properties.setCrypt(false);
		exp.properties.setCompress(false);
		assertEquals(0, exp.exportFile(fib));
	}
}
