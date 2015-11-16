package fr.inserm.transformer.format.source.cible;

import java.util.Hashtable;

import fr.inserm.transformer.format.enums.SexeValues;

public class StockyCodification {

	public StockyCodification() {
		sexeCodification.put(SexeValues.inconnu, "0");
		sexeCodification.put(SexeValues.male, "1");
		sexeCodification.put(SexeValues.femelle, "2");
		sexeCodification.put(SexeValues.hermaphrodite, "3");
	}

	public Hashtable<SexeValues, String> sexeCodification = new Hashtable<SexeValues, String>();

	/**
	 * get the Enum Value of a stocky code.
	 * 
	 * @param code
	 * @return
	 */
	public SexeValues convertToValue(String code) {
		SexeValues result = SexeValues.inconnu;
		if (sexeCodification.containsValue(code)) {
			for (SexeValues sVal : sexeCodification.keySet()) {
				if (sexeCodification.get(sVal).equals(code)) {
					result = sVal;
				}
			}

		}
		return result;
	}
}
