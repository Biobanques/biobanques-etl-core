package fr.inserm.bean;

import fr.inserm.DbDefinitionType;
import fr.inserm.tools.StringFileTools;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * bean d enregistrement des proprietes<br>
 * Permet de stocker les proprietes loadees et les utiliser partout.<bR>
 * Permet d etre modifié dynamiquement au cours de l execution.
 * 
 * @author nicolas
 * 
 */
public class PropertiesBean {

	/**
	 * bean empty
	 */
	public PropertiesBean() {

	}

	public PropertiesBean(ApplicationLoader loader) {
		compress = loader.compress;
		crypt = loader.crypt;
		cryptphrase = loader.cryptphrase;
		dbChoice = loader.dbChoice;
		dbname = loader.dbname;
		debugMode = loader.debugMode;
		directory = StringFileTools.addSlashPathDir(loader.getDirectory());
		exportCron = loader.exportCron;
		exportMode = loader.exportMode;
		finessSite = loader.finessSite;
		folderExport = StringFileTools.addSlashPathDir(loader.folderExport);
		folderExportSent = StringFileTools
				.addSlashPathDir(loader.folderExportSent);
		folderReport = StringFileTools.addSlashPathDir(loader.folderReport);
		host = loader.getHost();
		idSite = loader.idSite;
		login = loader.login;
		nameSite = loader.nameSite;
		password = loader.getPassword();
		passwordFTP = loader.getPasswordFTP();
		port = loader.getPort();
		portFTP = loader.getPortFTP();
		server = loader.getServer();
		sqlFilter = loader.getSqlFilter();
		username = loader.getUsername();
		versionNumber = loader.getVersionNumber();
		// System.out.println("folder datsource:" +
		// loader.getFolderDatasourceTableurs());
		activeDatasourceTableurs = loader.getActiveDatasourceTableurs();
		folderDatasourceTableurs = loader.getFolderDatasourceTableurs();
		proxyHost = loader.getProxyHost();
		proxyPort = loader.getProxyPort();
		proxyUserName = loader.getProxyUserName();
		proxyUserPassword = loader.getProxyUserPassword();
		dbschema = loader.getDbschema();
	}

	public boolean isActiveDatasourceTableurs() {
		return activeDatasourceTableurs;
	}

	public void setActiveDatasourceTableurs(boolean activeDatasourceTableurs) {
		this.activeDatasourceTableurs = activeDatasourceTableurs;
	}

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

	public boolean compress = true;

	public String exportCron;

	public String cryptphrase = "";

	public String host;

	public String port;

	public String dbname;

	public String login;

	public String password;

	/** l'adresse du serveur. */
	public String server;
	/** login. */
	public String username;
	/** password. */
	public String passwordFTP;
	/** repertoire sur le serveur. */
	public String directory;

	/** port utilisé */
	public int portFTP;

	public int getPortFTP() {
		return portFTP;
	}

	public void setPortFTP(int portFTP) {
		this.portFTP = portFTP;
	}

	/* repertoire de stockage des ficheirs excels associes */
	public String folderDatasourceTableurs;
	public boolean activeDatasourceTableurs;
	public String proxyHost = "";
	public int proxyPort;
	public String proxyUserName;
	public String proxyUserPassword;
	public String dbschema;

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public void setProxyUserPassword(String proxyUserPassword) {
		this.proxyUserPassword = proxyUserPassword;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public String getProxyUserPassword() {
		return proxyUserPassword;
	}

	public String getFolderDatasourceTableurs() {
		return folderDatasourceTableurs;
	}

	public void setFolderDatasourceTableurs(String folderDatasourceTableurs) {
		this.folderDatasourceTableurs = folderDatasourceTableurs;
	}

	public DbDefinitionType getDbChoice() {
		return dbChoice;
	}

	public void setDbChoice(DbDefinitionType dbChoice) {
		this.dbChoice = dbChoice;
	}

	public int getExportMode() {
		return exportMode;
	}

	public void setExportMode(int exportMode) {
		this.exportMode = exportMode;
	}

	public String getNameSite() {
		return nameSite;
	}

	public void setNameSite(String nameSite) {
		this.nameSite = nameSite;
	}

	public String getFinessSite() {
		return finessSite;
	}

	public void setFinessSite(String finessSite) {
		this.finessSite = finessSite;
	}

	public String getIdSite() {
		return idSite;
	}

	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}

	public String getFolderExport() {
		return folderExport;
	}

	public void setFolderExport(String folderExport) {
		this.folderExport = StringFileTools.addSlashPathDir(folderExport);
	}

	public String getFolderExportSent() {
		return folderExportSent;
	}

	public void setFolderExportSent(String folderExportSent) {
		this.folderExportSent = StringFileTools
				.addSlashPathDir(folderExportSent);
	}

	public String getFolderReport() {
		return folderReport;
	}

	public void setFolderReport(String folderReport) {
		this.folderReport = StringFileTools.addSlashPathDir(folderReport);
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getSqlFilter() {
		return sqlFilter;
	}

	public void setSqlFilter(String sqlFilter) {
		this.sqlFilter = sqlFilter;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public boolean isCrypt() {
		return crypt;
	}

	public void setCrypt(boolean crypt) {
		this.crypt = crypt;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public String getExportCron() {
		return exportCron;
	}

	public void setExportCron(String exportCron) {
		this.exportCron = exportCron;
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
		this.directory = StringFileTools.addSlashPathDir(directory);
	}

	public String getDbschema() {
		return dbschema;
	}

	public void setDbschema(String dbschema) {
		this.dbschema = dbschema;
	}

}
