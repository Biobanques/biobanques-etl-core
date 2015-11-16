package fr.inserm.tools.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import fr.inserm.DbDefinitionType;

/**
 * lodaer de properties de l application.properties
 * 
 * @author nicolas
 * 
 */
public class ApplicationLoader {

	private static final Logger LOGGER = Logger
			.getLogger(ApplicationLoader.class);
	/**
	 * choix du sgbd.
	 */
	public DbDefinitionType dbChoice;

	/**
	 * choix du mode d export 1 = xml, 2 = json
	 */
	public int exportMode;

	public String nameSite;

	public String finessSite;

	/**
	 * id du site fourni par la centrale.
	 */
	public String idSite;

	public String folderExport;

	public String folderExportSent;

	public String folderReport;

	public String versionNumber = "Version ??";

	public String sqlFilter = "";

	public boolean debugMode = false;

	public boolean crypt = true;

	public String exportCron;

	public String cryptphrase = "";

	public String host;

	public String port;

	public String dbname;
	public String dbschema;

	public String login;

	public String password;

	/** l'adresse du serveur. */
	private String server;
	/** login. */
	private String username;
	/** password. */
	private String passwordFTP;
	private int portFTP;
	/** repertoire sur le serveur. */
	private String directory;
	public boolean compress;
	private String proxyHost;
	private int proxyPort;
	private String proxyUserName;
	private String proxyUserPassword;

	/** dossier de stockage des sources excel */
	public String folderDatasourceTableurs;
	private boolean activeDatasourceTableurs;

	/**
	 * load les properties dans le fichier de base déployé
	 * application.properties
	 * 
	 * @param fileName
	 */
	public ApplicationLoader() {
		this("application.properties");
	}

	/**
	 * load le spreperties a partir dun fichier fourni
	 * 
	 * @param fileName
	 */
	public ApplicationLoader(String fileName) {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream(fileName));
			dbChoice = DbDefinitionType.getName(Integer.parseInt(props
					.getProperty("db.choix")));
			nameSite = props.getProperty("site.nom");
			finessSite = props.getProperty("site.finess");
			idSite = props.getProperty("site.id");
			folderExport = props.getProperty("folder.export");
			folderExportSent = props.getProperty("folder.export.sent");
			versionNumber = "Version "
					+ props.getProperty("application.version");
			sqlFilter = props.getProperty("application.sql.filter");
			exportCron = props.getProperty("application.export.cron");
			folderReport = props.getProperty("folder.report");
			cryptphrase = props.getProperty("application.export.cryptphrase");
			host = props.getProperty("db.host");
			port = props.getProperty("db.port");
			dbname = props.getProperty("db.name");

			dbschema = props.getProperty("db.schema");
			login = props.getProperty("db.user.login");
			password = props.getProperty("db.user.password");

			server = props.getProperty("inserm.ftp.server");
			username = props.getProperty("inserm.ftp.login");
			passwordFTP = props.getProperty("inserm.ftp.password");
			portFTP = 22;
			try {
				System.out.println(props.getProperty("inserm.ftp.port"));
				if (!props.getProperty("inserm.ftp.port").equals("null")) {
					portFTP = Integer.parseInt(props
							.getProperty("inserm.ftp.port"));

				}

			} catch (Exception ex) {
				LOGGER.warn("Problem with FTP port : "
						+ ex.getLocalizedMessage()
						+ ". Port is defined to default (22)");
			}
			directory = props.getProperty("inserm.ftp.path");
			if (!props.getProperty("site.proxyHost").equals("null")
					&& !props.getProperty("site.proxyHost").equals("")
					&& !props.getProperty("site.proxyHost").equals(
							"${site.proxyHost}")) {
				proxyHost = props.getProperty("site.proxyHost");
				proxyPort = Integer.parseInt(props
						.getProperty("site.proxyPort"));
				proxyUserName = props.getProperty("site.proxyUserName");
				proxyUserPassword = props.getProperty("site.proxyUserPassword");
			}
			try {
				if (props.getProperty("application.export.crypt").equals("0")) {
					crypt = false;
				} else {
					crypt = true;

				}
			} catch (Exception e) {
				LOGGER.warn("pb value crypt");
			}

			try {
				if (props.getProperty("application.export.compress")
						.equals("0")) {
					compress = false;
				} else {
					compress = true;
				}
			} catch (Exception e) {
				LOGGER.warn("pb value compress");
			}

			try {
				if (props.getProperty("application.version").equals("1")) {
					debugMode = true;
				}
			} catch (Exception exe) {
				LOGGER.error("pb parsing debug mode :" + exe.getMessage());
			}
			try {
				if (props.getProperty("application.export.mode").equals("json")) {
					exportMode = 1;
				}
				if (props.getProperty("application.export.mode").equals("xml")) {
					exportMode = 2;
				}
			} catch (Exception exe) {
				LOGGER.error("pb parsing debug mode :" + exe.getMessage());
			}
		} catch (IOException e) {
			LOGGER.error("properties loading error ! :" + e.getMessage());
		} catch (NumberFormatException e) {
			LOGGER.error("pb de format du choix db! :" + e.getMessage());
			e.printStackTrace();
		} catch (Exception ex) {
			LOGGER.error("pb loading properties:" + ex.getMessage());
			LOGGER.error(ex.getStackTrace());
		}
		folderDatasourceTableurs = props
				.getProperty("folder.datasource.tableurs");
		try {
			if (props.getProperty("active.datasource.tableurs").equals("0")) {
				activeDatasourceTableurs = false;
			} else {
				activeDatasourceTableurs = true;
			}
		} catch (Exception e) {
			LOGGER.warn("pb value activeDatasourceTableurs");
		}
	}

	/*
	 * Charge les propriétés à partir d'un fichier externe TODO : attention code
	 * dupliqué à 99%, factorise rles constructeurs!
	 */
	public ApplicationLoader(File file) {
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
			fis.close();
			dbChoice = DbDefinitionType.getName(Integer.parseInt(props
					.getProperty("db.choix")));
			nameSite = props.getProperty("site.nom");
			finessSite = props.getProperty("site.finess");
			idSite = props.getProperty("site.id");
			folderExport = props.getProperty("folder.export");
			folderExportSent = props.getProperty("folder.export.sent");
			versionNumber = "Version "
					+ props.getProperty("application.version");
			sqlFilter = props.getProperty("application.sql.filter");
			exportCron = props.getProperty("application.export.cron");
			folderReport = props.getProperty("folder.report");
			cryptphrase = props.getProperty("application.export.cryptphrase");
			host = props.getProperty("db.host");
			port = props.getProperty("db.port");
			dbname = props.getProperty("db.name");

			dbschema = props.getProperty("db.schema");
			login = props.getProperty("db.user.login");
			password = props.getProperty("db.user.password");

			server = props.getProperty("inserm.ftp.server");
			username = props.getProperty("inserm.ftp.login");
			passwordFTP = props.getProperty("inserm.ftp.password");
			portFTP = 22;
			try {
				if (!props.getProperty("inserm.ftp.port").equals("null")) {
					portFTP = Integer.parseInt(props
							.getProperty("inserm.ftp.port"));

				}

			} catch (Exception ex) {
				LOGGER.warn("Problem with FTP port : "
						+ ex.getLocalizedMessage()
						+ ". Port is defined to default (22)");
			}
			directory = props.getProperty("inserm.ftp.path");
			if (props.getProperty("site.proxyHost").equals(null)
					&& props.get("site.proxyHost").equals("")) {
				proxyHost = props.getProperty("site.proxyHost");
				proxyPort = Integer.parseInt(props
						.getProperty("site.proxyPort"));
				proxyUserName = props.getProperty("site.proxyUserName");
				proxyUserPassword = props.getProperty("site.proxyUserPassword");
			}
			try {
				if (props.getProperty("active.datasource.tableurs").equals("0")) {
					activeDatasourceTableurs = false;
				} else {
					activeDatasourceTableurs = true;
				}
			} catch (Exception e) {
				LOGGER.warn("pb value activeDatasourceTableurs");
			}

			try {
				if (props.getProperty("application.export.crypt").equals("0")) {
					crypt = false;
				} else {
					crypt = true;
				}
			} catch (Exception e) {
				LOGGER.warn("pb value crypt");
			}
			try {
				if (props.getProperty("application.export.compress")
						.equals("0")) {
					compress = false;
				} else {
					compress = true;
				}
			} catch (Exception e) {
				LOGGER.warn("pb value compress");
			}

			try {
				if (props.getProperty("application.version").equals("1")) {
					debugMode = true;
				}
			} catch (Exception exe) {
				LOGGER.error("pb parsing debug mode :" + exe.getMessage());
			}
			try {
				if (props.getProperty("application.export.mode").equals("json")) {
					exportMode = 1;
				}
				if (props.getProperty("application.export.mode").equals("xml")) {
					exportMode = 2;
				}
			} catch (Exception exe) {
				LOGGER.error("pb parsing debug mode :" + exe.getMessage());
			}
		} catch (IOException e) {
			LOGGER.error("properties loading error ! :" + e.getMessage());
		} catch (NumberFormatException e) {
			LOGGER.error("pb de format du choix db! :" + e.getMessage());
		} catch (Exception ex) {
			LOGGER.error("pb loading properties:" + ex.getMessage());
			ex.printStackTrace();
		}
		folderDatasourceTableurs = props
				.getProperty("folder.datasource.tableurs");
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyUserPassword() {
		return proxyUserPassword;
	}

	public void setProxyUserPassword(String proxyUserPassword) {
		this.proxyUserPassword = proxyUserPassword;
	}

	public String getFolderReport() {
		return folderReport;
	}

	public void setFolderReport(String folderReport) {
		this.folderReport = folderReport;
	}

	public DbDefinitionType getDbChoice() {
		return dbChoice;
	}

	public String getFinessSite() {
		return finessSite;
	}

	public String getIdSite() {
		return idSite;
	}

	public String getNameSite() {
		return nameSite;
	}

	public void setDbChoice(DbDefinitionType dbChoice) {
		this.dbChoice = dbChoice;
	}

	public void setFinessSite(String finessSite) {
		this.finessSite = finessSite;
	}

	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}

	public void setNameSite(String nameSite) {
		this.nameSite = nameSite;
	}

	public String getFolderExport() {
		return folderExport;
	}

	public void setFolderExport(String folderExport) {
		this.folderExport = folderExport;
	}

	public String getFolderExportSent() {
		return folderExportSent;
	}

	public void setFolderExportSent(String folderExportSent) {
		this.folderExportSent = folderExportSent;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public String getSqlFilter() {
		return sqlFilter;
	}

	public void setSqlFilter(String sqlFilter) {
		this.sqlFilter = sqlFilter;
	}

	public int getExportMode() {
		return exportMode;
	}

	public void setExportMode(int exportMode) {
		this.exportMode = exportMode;
	}

	public String getExportCron() {
		return exportCron;
	}

	public void setExportCron(String exportCron) {
		this.exportCron = exportCron;
	}

	public boolean isCrypt() {
		return crypt;
	}

	public void setCrypt(boolean crypt) {
		this.crypt = crypt;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public boolean isCompress() {
		return compress;
	}

	public String getCryptphrase() {
		return cryptphrase;
	}

	public void setCryptphrase(String cryptphrase) {
		this.cryptphrase = cryptphrase;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getPasswordFTP() {
		return passwordFTP;
	}

	public void setPasswordFTP(String passwordFTP) {
		this.passwordFTP = passwordFTP;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFolderDatasourceTableurs() {
		return folderDatasourceTableurs;
	}

	public void setFolderDatasourceTableurs(String folderDatasourceTableurs) {
		this.folderDatasourceTableurs = folderDatasourceTableurs;
	}

	public boolean getActiveDatasourceTableurs() {

		return activeDatasourceTableurs;
	}

	public void setActiveDatasourceTableurs(boolean activeDatasourceTableurs) {
		this.activeDatasourceTableurs = activeDatasourceTableurs;
	}

	public String getDbschema() {
		return dbschema;
	}

	public void setDbschema(String dbschema) {
		this.dbschema = dbschema;
	}

	public int getPortFTP() {

		return this.portFTP;
	}
}
