package fr.inserm.reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.log.TransformAlert;

/**
 * classe de generation de fichier de reporting.
 * @author nicolas
 *
 */
public class Reporter {
	
	public Reporter(){
		
	}

	/**
	 * methode  d ecriture du report au format properties clé : valeur
	 */
	@SuppressWarnings("unchecked")
	public void write(FileInputBean input,List<TransformAlert> alertes,int nbErrorsFTP,PropertiesBean properties){
		FileWriter file;
		try {
			file = new FileWriter(properties.getFolderReport() + "last_report.json");
			JSONObject obj = new JSONObject();
			obj.put("extraction.date","\""+input.getDateImport()+"\"");
			obj.put("nb.echantillons",input.getEchantillons().size());
			obj.put("nb.anomalies",alertes.size());
			obj.put("nb.ftp.errors",nbErrorsFTP);
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
