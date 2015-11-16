package fr.inserm.tools.loader;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.extractor.Extractor;

/**
 * check des dependenaces a l init pour remonter les erreurs.
 * 
 * @author nicolas
 * 
 */
public class DependeciesChecker {

	private static final Logger LOGGER = Logger.getLogger(DependeciesChecker.class);

	/**
	 * check des infos du fichier de conf application. Puis check du fichier de db<br>
	 * 
	 * @return liste of string containings errors.
	 */
	public static List<String> checkDependencies(PropertiesBean appLoader) {
		List<String> error = new ArrayList<String>();
		error.addAll(checkConfigurationApplication(appLoader));
		error.addAll(checkConfigurationDbConnector(appLoader));
		if (error.size() == 0) {
			error.addAll(checkConnexionDb(appLoader));
		}
		return error;
	}

	/**
	 * check de la config de l application.properties.<br>
	 * Check rep d export exist rep d export sent existe numero finess site ok id site fourni nom site fourni
	 */
	public static List<String> checkConfigurationApplication(PropertiesBean appLoader) {
		List<String> errors = new ArrayList<String>();
		String foldExport = appLoader.getFolderExport();
		if (foldExport == null || foldExport.isEmpty()) {
			errors.add("Le dossier d'export n'est pas renseigné");
		} else {
			File file = new File(foldExport);
			if (!file.exists()) {
				errors.add("Le dossier d'export n'existe pas ou n'est pas accessible");
			}
			if (!file.isDirectory()) {
				errors.add("Le dossier d'export n'est pas un dossier");
			}
		}
		String foldexportSent = appLoader.getFolderExportSent();
		if (foldexportSent == null || foldexportSent.isEmpty()) {
			errors.add("Le dossier d'envoi n'est pas renseigné");
		} else {
			File file2 = new File(foldexportSent);
			if (!file2.exists()) {
				errors.add("Le dossier d'envoi n'existe pas ou n'est pas accessible");
			}
			if (!file2.isDirectory()) {
				errors.add("Le dossier d'envoi n'est pas un dossier");
			}
		}

		String finess = appLoader.getFinessSite();
		if (finess == null || finess.isEmpty()) {
			errors.add("Le numéro de finess n'est pas renseigné");
		}
		String name = appLoader.getNameSite();
		if (name == null || name.isEmpty()) {
			errors.add("Le nom du site n'est pas renseigné");
		}
		String id = appLoader.getIdSite();
		if (id == null || id.isEmpty()) {
			errors.add("L'id du site n'est pas renseigné");
		}
		return errors;
	}

	/**
	 * check de l acces db
	 * 
	 * @param loader
	 * @return
	 */
	public static List<String> checkConfigurationDbConnector(PropertiesBean loader) {
		List<String> errors = new ArrayList<String>();
		String db = loader.getDbname();
		if (db == null || db.isEmpty()) {
			errors.add("Le nom de la base n'est pas renseigné");
		}
		String host = loader.getHost();
		if (host == null || host.isEmpty()) {
			errors.add("Le nom d'hôte n'est pas renseigné");
		}
		String login = loader.getLogin();
		if (login == null || login.isEmpty()) {
			errors.add("Le nom d'utilisateur n'est pas renseigné");
		}
		String pass = loader.getPassword();
		if (pass == null || pass.isEmpty()) {
			errors.add("Le mot de passe n'est pas renseigné");
		}
		String port = loader.getPort();
		if (port == null || port.isEmpty()) {
			errors.add("Le port n'est pas renseigné");
		}
		return errors;
	}

	/**
	 * check de la connexion db.<br>
	 * Essayes d etablir une connexion et indique si c est ok.
	 * 
	 * @param loader
	 * @return
	 */
	public static List<String> checkConnexionDb(PropertiesBean loader) {
		List<String> errors = new ArrayList<String>();
		Extractor ex = new Extractor(loader);
		Connection c = null;
		try {
			c = ex.getConnection(loader.getHost(), loader.getPort(), loader.getDbname(), loader.getLogin(),
					loader.getPassword());
		} catch (Exception e) {
			// "La connexion à la base de données est impossible";
			LOGGER.debug("pb db connect:" + e.getMessage());
		}
		// if (c == null) {
		// errors.add("La connexion à la base de données est impossible, revoir les informations:"
		// + loader.getHost()
		// + ","
		// + loader.getPort()
		// + ","
		// + loader.getDbname()
		// + ","
		// + loader.getLogin()
		// + ","
		// + loader.getPassword());
		// } else {
		// try {
		// c.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return errors;
	}
}
