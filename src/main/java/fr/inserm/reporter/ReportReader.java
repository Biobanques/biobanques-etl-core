package fr.inserm.reporter;

import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.inserm.bean.PropertiesBean;

public class ReportReader {

	JSONObject object;
	private static final Logger LOGGER = Logger.getLogger(ReportReader.class);
	
	
	public ReportReader(PropertiesBean properties){
		JSONParser parser = new JSONParser();
		File fos;
		try {
			fos = new File(properties.getFolderReport() + "last_report.json");
			FileReader fr = new FileReader(fos);
			object = (JSONObject) parser.parse(fr);
			if(object==null){
				LOGGER.error("le fichier de reporting est null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getNbEchantillons(){
		if(object!=null)
		return object.get("nb.echantillons").toString();
		else
			return "file report access unavailable";
	}
	public String getLastSentDate(){
		if(object!=null)
		return object.get("extraction.date").toString();
		else
			return "file report access unavailable";
	}
	public String getNbAnomalies(){
		if(object!=null)
		return object.get("nb.anomalies").toString();
		else
			return "file report access unavailable";
	}
	public String getNbErrorsFTP(){
		if(object!=null)
		return object.get("nb.ftp.errors").toString();
		else
			return "file report access unavailable";
	}
}
