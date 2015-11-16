package fr.inserm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * hashmap de clé valeur clé = nom de colonne provenant de l enum et valeur de cellule dans cette colonne a la ligne
 * 
 * @author nicolas
 * 
 */
public class LigneBean {
	HashMap<String, String> ligne = new HashMap<String, String>();

	public HashMap<String, String> getLigne() {
		return ligne;
	}

	public void setLigne(HashMap<String, String> ligne) {
		this.ligne = ligne;
	}

	public LigneBean() {

	}

	public void put(String colName, String colValue) {
		ligne.put(colName, colValue);
	}

	// ne retourne jamais null mais string vide. remplace la methode get(String key) de HashMap<String, String>
	public String getValue(String key) {
		String result = "";
		if (getLigne().get(key) != null) {
			result = getLigne().get(key);

		}
		return result;
	}

	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		for (String value : ligne.values()) {
			values.add(value);
		}
		return values;
	}

	/**
	 * fusionne deux ligneBean si les clés sont identiques et les valeurs differentes.
	 * 
	 * @param value
	 * @return
	 */
	public LigneBean mergeLigneBean(LigneBean value) {
		for (Entry<String, String> entree : value.ligne.entrySet()) {
			String key = entree.getKey();

			if (this.ligne.containsKey(key)) {
				if (!this.getValue(key).equals(value.getValue(key))) {
					this.ligne.put(key, this.getValue(key) + " - " + value.getValue(key));
				}
			} else {

				this.ligne.put(key, value.getValue(key));
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return this.ligne.toString();
	}
}
