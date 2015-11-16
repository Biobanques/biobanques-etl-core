package fr.inserm.transport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Ignore;

import fr.inserm.bean.IssueBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * classe de test de la classe utilitaire denvoi en sftp.
 * 
 * @author nicolas
 * 
 */
@Ignore
public class SFTPSenderTest extends TestCase {

	/**
	 * test de pushFilesOnSent
	 */
	@Ignore
	public final void testPushFilesOnSent() {
		/**
		 * cas aucun fichier a exporter
		 */
		ApplicationLoader aploader = new ApplicationLoader();
		PropertiesBean props = new PropertiesBean(aploader);
		File[] files = new File(props.folderExport).listFiles();
		if (files.length != 0) {
			for (final File f : files) {
				f.delete();
			}
		}
		SFTPSender sftp = new SFTPSender(props);
		List<IssueBean> issues = sftp.pushFilesOnSent();
		assertNotNull(issues);
		assertEquals("nb problems:" + issues.size(), 1, issues.size());
		/**
		 * set d un faux path d export et assert q une erreur est presente
		 * 
		 */
		props.setFolderExport("/unfolderinexistant/");
		sftp = new SFTPSender(props);
		issues = sftp.pushFilesOnSent();
		assertNotNull(issues);
		assertEquals(1, issues.size());
		/**
		 * set d un faux password
		 */
		props = new PropertiesBean(new ApplicationLoader());
		props.setPasswordFTP("passwordbidon");
		// ajout d un fichier bidon a envoyer
		File file = new File(props.getFolderExport() + "fichier_bidon.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sftp = new SFTPSender(props);
		issues = sftp.pushFilesOnSent();
		assertNotNull(issues);
		assertEquals(1, issues.size());
		/**
		 * envoi correct sans proxy
		 */
		props = new PropertiesBean(new ApplicationLoader());
		// String proxyHostTemp = props.getProxyHost();
		props.setProxyHost("");
		sftp = new SFTPSender(props);
		issues = sftp.pushFilesOnSent();
		// System.out.println("envoi correct sans proxy");
		assertNotNull(issues);
		assertEquals("probleme, il ya une anomalie", 0, issues.size());
		file = new File(props.getFolderExport() + "fichier_bidon.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		props.setProxyHost("localhost");
		sftp = new SFTPSender(props);
		issues = sftp.pushFilesOnSent();
		// System.out.println("envoi correct avec proxy");
		assertNotNull(issues);
		assertEquals("probleme, il ya une anomalie", 0, issues.size());

		/**
		 * delete du fichier bidon
		 */
		File fileToDelete = new File(props.getFolderExportSent() + "fichier_bidon.txt");
		fileToDelete.delete();
	}
}
