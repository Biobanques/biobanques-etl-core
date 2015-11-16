package fr.inserm;

import java.util.List;

import org.apache.log4j.Logger;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.IssueBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.exporter.JSONExporter;
import fr.inserm.exporter.XMLExporter;
import fr.inserm.extractor.Extractor;
import fr.inserm.log.AlertLogExporter;
import fr.inserm.log.TransformAlert;
import fr.inserm.tools.loader.ApplicationLoader;
import fr.inserm.transport.SFTPSender;

/**
 * main class to extract data.
 * 
 * @author nicolas
 * 
 */
public class ApplicationConsoleMode {

	private static final Logger LOGGER = Logger.getLogger(ApplicationConsoleMode.class);

	/**
	 * bean qui contient les proprietes du site, utile dans toute l application.
	 */
	public static PropertiesBean propertiesBean;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// chargement des proprietes
		LOGGER.info("--chargement des proprietes");
		ApplicationLoader loader = new ApplicationLoader();
		propertiesBean = new PropertiesBean(loader);
		Extractor ex = new Extractor(propertiesBean);
		LOGGER.info("--extraction/transformation des informations de la base");
		FileInputBean input = ex.extract();
		LOGGER.info("-- export des informations");
		if (propertiesBean.exportMode == 1) {
			LOGGER.info("-- Format de destination :  fichier JSON");
			JSONExporter jsonExporter = new JSONExporter(propertiesBean);
			jsonExporter.exportFile(input);
		} else {
			LOGGER.info("-- Format de destination :  fichier XML");
			XMLExporter xmlExporter = new XMLExporter(propertiesBean);
			xmlExporter.exportFile(input);
		}
		List<TransformAlert> alertes = ex.getAlertes();
		if (alertes.size() > 0) {
			LOGGER.warn("-- le traitement s'est déroulé avec des anomalies");
			AlertLogExporter.exportInFile(alertes, propertiesBean);
		} else {
			LOGGER.info("-- traitements réalisés avec succès");
		}
		SFTPSender sender = new SFTPSender(propertiesBean);
		List<IssueBean> nbErrors = sender.pushFilesOnSent();
		if (nbErrors.size() > 0) {
			LOGGER.warn("-- l envoi s'est déroulé avec des anomalies");
		}
		//
		// Reporter reporter = new Reporter();
		// reporter.write(input,alertes,nbErrors);
	}
}
