package fr.inserm;

import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import fr.inserm.bean.LigneBean;

@SuppressWarnings("serial")
public class AdvancedTreeMap extends TreeMap<String, LigneBean> {
	private static final Logger LOGGER = Logger.getLogger(AdvancedTreeMap.class);

	/**
	 * Ajoute un couple clé/valeur, avec fusion des valeurs si clé déjà existante
	 * 
	 * @param key
	 * @param value
	 */
	public void putIn(String key, LigneBean value) {
		if (key == null || key.equals("")) {
			LOGGER.error("Impossible d'ajouter une clé vide ou nulle.");

		} else {
			if (value == null) {
				LOGGER.error("Impossible d'ajouter une valeur nulle");
			} else {
				if (!this.containsKey(key))
					this.put(key, value);
				else
					this.put(key, this.get(key).mergeLigneBean(value));
			}
		}
	}

	/**
	 * fusionne deux objets AdvancedTreeMap
	 * 
	 * @param tm
	 */
	public void putAll(AdvancedTreeMap tm) {
		if (tm != null) {
			for (Entry<String, LigneBean> entree : tm.entrySet()) {
				this.putIn(entree.getKey(), entree.getValue());
			}
		} else {
			LOGGER.error("Aucun objet à ajouter");

		}
	}
}
