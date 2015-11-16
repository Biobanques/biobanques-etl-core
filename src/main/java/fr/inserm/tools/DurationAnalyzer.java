package fr.inserm.tools;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * 
 * @author matthieu sert à calculer la durée d'execution entre l'instantiation de l'objet et l'appel de la methode
 *         endAnalyse
 * 
 *         Possibilité d'ajouter des étapes
 */
public class DurationAnalyzer {
	Logger LOGGER = Logger.getLogger(DurationAnalyzer.class);
	public Date startDate;
	public Date endDate;
	public LinkedHashMap<String, Date> etapes = new LinkedHashMap<String, Date>();

	public DurationAnalyzer() {
		this.startDate = new Date();
	}

	/**
	 * Méthodes d'ajout d'étapes
	 * 
	 * @param name
	 */
	public void addEtape() {
		int i = this.etapes.size();
		addEtape(Integer.toString(i + 1));
	}

	public void addEtape(int i) {
		addEtape(Integer.toString(i));
	}

	public void addEtape(String name) {
		if (this.etapes.containsKey(name))
			name += "_1";
		Date date = new Date();
		this.etapes.put(name, date);
	}

	public void endAnalyse() {
		this.endDate = new Date();
		LOGGER.error("duree totale " + (this.endDate.getTime() - this.startDate.getTime()) + " millisecondes");
		int index = 0;
		String nomEtapePrecedente = "";
		for (Entry<String, Date> etape : etapes.entrySet()) {
			index++;
			String nomEtape = etape.getKey();
			if (index == 1) {
				LOGGER.error("etape " + nomEtape + " : " + (etape.getValue().getTime() - this.startDate.getTime())
						+ " millisecondes");
			} else {
				LOGGER.error("etape " + nomEtape + " : "
						+ (etape.getValue().getTime() - etapes.get(nomEtapePrecedente).getTime()) + " millisecondes");
			}
			nomEtapePrecedente = nomEtape;
		}
	}

}
