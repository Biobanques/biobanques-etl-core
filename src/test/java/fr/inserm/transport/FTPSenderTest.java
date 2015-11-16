package fr.inserm.transport;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Ignore;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * test de la classe ftpsender.
 * 
 * @author nicolas
 * 
 */
@Ignore
public class FTPSenderTest extends TestCase {

	/**
	 * 
	 */
	public void testPushFile() {
		PropertiesBean props = new PropertiesBean(new ApplicationLoader());
		FTPSender ftpSender = new FTPSender(props);
		String fileName = "exemple_export.xml";
		File file = new File(props.getFolderExport() + fileName);
		boolean result = ftpSender.pushFile(file, fileName);
		assertTrue("export file ko", result);
	}
}
