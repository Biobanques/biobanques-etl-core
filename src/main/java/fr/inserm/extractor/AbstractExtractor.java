package fr.inserm.extractor;

import java.sql.Connection;
import java.util.List;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.log.TransformAlert;

/**
 * classe abstraite d extractor.
 * 
 * @author nicolas
 * 
 */
public class AbstractExtractor {

	public PropertiesBean appProperties;

	public List<TransformAlert> alertes;

	public Connection conn;

	public List<TransformAlert> getAlertes() {
		return alertes;
	}

}
