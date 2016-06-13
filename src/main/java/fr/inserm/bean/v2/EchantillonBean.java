package fr.inserm.bean.v2;

import java.util.HashMap;
import java.util.Map;

/**
 * classe de gestion des informations des échantillons. Format Inserm v2.
 * 
 * @author nicolas
 * 
 */
public class EchantillonBean {

	/**
	 * cle de mapping utile pour faire la correspondance avec les autres datasources. Champ non stocké dans les fichiers
	 * excel Par example, pour Grenoble, nom_prenom_datenaissance
	 */
	private String mappingKey;
	/**
	 * valeurs mappées selon le format de definition.
	 */
	private Map<FormatDefinition, String> mapValues;
	/**
	 * valeurs non mappables par le systeme de format ( champs variables optionnels libres)
	 */
	private Map<String, String> notes;

	public FormatDefinition formatDefinition;

	public EchantillonBean() {
		mapValues = new HashMap<FormatDefinition, String>();
		notes = new HashMap<String, String>();
	}

	public Map<FormatDefinition, String> getMapValues() {
		return mapValues;
	}

	public void setMapValues(Map<FormatDefinition, String> mapValues) {
		this.mapValues = mapValues;
	}

	public Map<String, String> getNotes() {
		return notes;
	}

	public void setNotes(Map<String, String> notes) {
		this.notes = notes;
	}

	/**
	 * fonction d ajout de note pour simplifier le systeme lors de l extraction.
	 * 
	 * @param cle
	 * @param valeur
	 */
	public void addNote(String cle, String valeur) {
		notes.put(cle, valeur);
	}

	/**
	 * ajout d une valeur pour un champs.
	 * 
	 * @param champ
	 * @param valeur
	 */
	public void addValue(FormatDefinition champ, String valeur) {
		mapValues.put(champ, valeur);
	}

	/**
	 * retourne la valeur d un champ
	 * 
	 * @param cle
	 * @return
	 */
	public String getValue(FormatDefinition cle) {
		return mapValues.get(cle);
	}

	public String getMappingKey() {
		return mappingKey;
	}

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

}
