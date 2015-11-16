package fr.inserm.extractor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.inserm.bean.FileInputBean;
import fr.inserm.log.TransformAlert;

/**
 * classe abstraite extractor.
 * 
 * @author nicolas
 * 
 */
public interface IExtractor {

	FileInputBean extract();

	List<TransformAlert> getAlertes();
	
	Connection getConnection(String host, String port, String dbname, String user, String password)throws SQLException;

}
