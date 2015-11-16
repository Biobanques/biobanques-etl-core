package fr.inserm.extractor;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.log.TransformAlert;

/**
 * classe d extraction des donnees.<br>
 * Switch selon db fournie.
 * 
 * @author nicolas
 * 
 */
public class Extractor implements IExtractor {

	private static final Logger LOGGER = Logger.getLogger(Extractor.class);

	IExtractor ex;

	PropertiesBean properties;

	public Extractor(PropertiesBean props) {
		if (props != null) {
			properties = props;
			String className = "";
			try {
				switch (props.getDbChoice()) {
				case TUMOROTEK:
					className = "TumorotekExtractor";
					break;
				case STOCKY:
					className = "StockyExtractor";
					break;
				case LILLE_DATABIOTEC:
					className = "LilleDatabiotecExtractor";
					break;
				case NICE_MSACCESS:
					className = "NiceMSAccessExtractor";
					break;
				case NANCY_XLS:
					className = "NancyExtractor";
					break;
				case GRENOBLE_TK_PLUS:
					className = "GrenobleTumorotekPlusExtractor";
					break;
				case GERMETHEQUE:
					className = "GermethequeExtractor";

					break;
				case CAEN_DATABIOTEC:
					className = "CaenDatabiotecExtractor";
					break;
				case CAEN_MSACCESS:
					className = "CaenMesobankExtractor";
					break;
				default:
					LOGGER.error("pb de choix de db");
					break;
				}
				Class<?> cl = Class
						.forName("fr.inserm.extractor.siteextractor."
								+ className);

				Constructor<?> con = cl.getConstructor(PropertiesBean.class);

				ex = (IExtractor) con.newInstance(props);

			} catch (Exception e) {
				LOGGER.error("pb de construction d'extractor : "
						+ e.getMessage());
			}
		} else {
			LOGGER.error("properties non chargées");
		}
	}

	@Override
	public FileInputBean extract() {
		if (ex == null)
			return null;
		return ex.extract();
	}

	@Override
	public List<TransformAlert> getAlertes() {
		if (ex == null)
			return null;
		return ex.getAlertes();
	}

	@Override
	public Connection getConnection(String host, String port, String dbname,
			String user, String password) throws SQLException {
		if (ex == null)
			return null;
		return ex.getConnection(host, port, dbname, user, password);
	}

}
