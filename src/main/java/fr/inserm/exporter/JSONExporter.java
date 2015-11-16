package fr.inserm.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.bean.v2.EchantillonBean;
import fr.inserm.bean.v2.FormatDefinition;
import fr.inserm.transformer.format.source.cible.TumorothequesCodification;

/**
 * classe to export beanXML ti file xml.
 * 
 * @author nicolas
 * 
 */
public class JSONExporter {

	private static final Logger LOGGER = Logger.getLogger(JSONExporter.class);

	TumorothequesCodification codif;

	PropertiesBean properties;

	/**
	 * init du mapping de codifications.
	 */
	public JSONExporter(PropertiesBean props) {
		properties = props;
		codif = new TumorothequesCodification();

	}

	private int MAX_ECHANTILLONS_PAR_FICHIER = 1000;

	/**
	 * export bean to a file.
	 * 
	 * @param input
	 */
	@SuppressWarnings("unchecked")
	public int exportFile(FileInputBean input) {
		int result = 0;
		// si le nb d echantillon est grand decoupe du fichier
		if (input == null || input.getEchantillons() == null) {
			LOGGER.error("problem input ou echantillons null");
			return -1;
		} else {
			int nbEchantillons = input.getEchantillons().size();
			int nbFichiers = 1;
			if (nbEchantillons > MAX_ECHANTILLONS_PAR_FICHIER) {
				LOGGER.info("decoupe des fichiers, volume d echantillon trop grand:" + nbEchantillons);
				nbFichiers = 1 + nbEchantillons / MAX_ECHANTILLONS_PAR_FICHIER;
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ddMMyyyy");
			String suffixDate = sdf.format(date);
			try {
				for (int i = 1; i <= nbFichiers; i++) {
					String idSite = properties.getIdSite() + "";
					String fileName = properties.getFolderExport() + "export_inserm_" + idSite + "_" + i + "_sur_"
							+ nbFichiers + "_" + suffixDate + ".json";

					JSONArray list = new JSONArray();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					int maxPage = i * MAX_ECHANTILLONS_PAR_FICHIER;
					if (maxPage > nbEchantillons) {
						maxPage = nbEchantillons;
					}
					for (int j = (i - 1) * MAX_ECHANTILLONS_PAR_FICHIER; j < maxPage; j++) {
						EchantillonBean ech = input.getEchantillons().get(j);
						JSONObject obj = new JSONObject();
						obj.put("date", format.format(date));
						obj.put("formatVersion", "1");
						obj.put("idSite", 1 + "");
						obj.put("nameSite", "sls");
						obj.put("finess", "123456");
						obj.putAll(writeEchantillon(ech));
						JSONObject objNotes = new JSONObject();
						objNotes.putAll(ech.getNotes());
						obj.put("notes", objNotes);
						list.add(obj);
					}
					String res = list.toJSONString();
					// chiffrement du fichier selon choix
					if (properties.crypt) {
						res = Crypter.encrypt(list.toJSONString(), properties.cryptphrase);
						// Décommenter pour suffixer le fichier si cryptage
						fileName = fileName + ".encrypted";
					}
					FileWriter file = new FileWriter(fileName);
					file.write(res);
					file.flush();
					file.close();
					if (properties.compress) {
						File fileToCompress = new File(fileName);
						GZIPCompress.compress(fileToCompress);
						fileToCompress.delete();
					}
				}
			} catch (FileNotFoundException e) {
				LOGGER.error("file not found" + e.getMessage());
				result = -1;
			} catch (IOException e) {
				LOGGER.error("i/o:" + e.getMessage());
				result = -1;
			}
		}
		return result;
	}

	/**
	 * write an echantillon content
	 * 
	 * @param echantillonBean
	 * @return
	 */
	private Map<String, String> writeEchantillon(EchantillonBean ech) {
		Map<String, String> result = new HashMap<String, String>();
		for (FormatDefinition champ : FormatDefinition.values()) {
			if (ech.getMapValues().containsKey(champ)) {
				result.put(champ + "", ech.getValue(champ));
			}
		}

		return result;
	}

}
