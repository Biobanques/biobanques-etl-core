package fr.inserm.transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.tools.StringFileTools;

/**
 * ftp to load xml files on reception server
 * 
 * @author malservet
 * 
 */
public class FTPSender {
	private static final Logger LOGGER = Logger.getLogger(FTPSender.class);

	/** l'adresse du serveur. */
	private String server;
	/** login. */
	private String username;
	/** password. */
	private String password;
	/** repertoire sur le serveur. */
	private String directory;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	PropertiesBean appLoader;

	public FTPSender(PropertiesBean properties) {
		// chargement des properties
		Properties ftpResources = new Properties();
		try {
			ftpResources.load(this.getClass().getClassLoader()
					.getResourceAsStream("inserm_ftp.properties"));
		} catch (IOException e) {
			LOGGER.error("pb loading properties" + e.getMessage());
		}
		server = ftpResources.getProperty("inserm.ftp.server");
		username = ftpResources.getProperty("inserm.ftp.login");
		password = ftpResources.getProperty("inserm.ftp.password");
		directory = ftpResources.getProperty("inserm.ftp.path");
		appLoader = properties;
	}

	/**
	 * verifie si un fichier avec le même nom existe déjà sur le serveur.
	 * 
	 * @param directory
	 *            le repertoire à scanner.
	 * @return true/false
	 */
	private boolean isPresent(FTPClient ftp, String repertoire, String name) {
		FTPFile[] files = null;
		boolean result = false;
		// list files
		try {
			files = ftp.listFiles(repertoire);
			for (FTPFile ftpFile : files) {
				if (ftpFile.getName().equals(name)) {
					result = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * envoi des fichiers dans le repo d export.<br>
	 * Retourne le nb d erreurs en poussant.
	 */
	public int pushFilesOnSent() {
		int nbErrorsPushing = 0;
		// scan du rep d export
		// envoi des fichiers
		String path = appLoader.getFolderExport();
		String fileName;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {

			if (file.isFile()) {
				fileName = file.getName();
				System.out.println(fileName);
				// envoi du fichier
				boolean res = pushFile(file, fileName);
				if (!res) {
					nbErrorsPushing++;
				}
			}
		}
		return nbErrorsPushing;
	}

	/**
	 * on pousse le fichier image dans un repertoire sur le serveur ftp
	 * 
	 * @param stream
	 *            flux du fichier
	 * @param fileName
	 *            nom du fichier
	 */
	public boolean pushFile(File file, String fileName) {
		boolean result = false;
		try {
			FTPClient ftp = new FTPClient();
			ftp.connect(server);
			ftp.login(username, password);
			LOGGER.info("Connected to " + server + ".");
			LOGGER.info(ftp.getReplyString());
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				LOGGER.error("FTP server refused connection.");
			} else {
				// push file
				if (!isPresent(ftp, directory, fileName)) {
					InputStream is = new FileInputStream(file);
					LOGGER.info("push on " + server + " (" + directory
							+ ") => " + fileName);
					ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
					ftp.appendFile(directory + fileName, is);
					// deplacement du fichier vers le repertoire d envoi
					StringFileTools.moveFile(file,
							appLoader.getFolderExportSent());
					result = true;
				}
			}
			ftp.disconnect();
		} catch (Exception ex) {
			LOGGER.error("exception ftp:" + ex.getMessage());
		}
		return result;
	}
}
