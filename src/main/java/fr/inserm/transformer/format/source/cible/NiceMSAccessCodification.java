package fr.inserm.transformer.format.source.cible;

import java.util.Hashtable;

import fr.inserm.bean.v2.FormatValuesDefinition;
import fr.inserm.transformer.format.enums.ConsentValues;
import fr.inserm.transformer.format.enums.SexeValues;

public class NiceMSAccessCodification {

	FormatValuesDefinition formatValuesDefinition;
	
	public Hashtable<SexeValues, String> sexeCodification = new Hashtable<SexeValues, String>();
	public Hashtable<ConsentValues, String> consentCodification = new Hashtable<ConsentValues, String>();

	
	/**
	 * correspondance de codification de la base de nice par rapport aux constantes.
	 */
	public NiceMSAccessCodification() {
		sexeCodification.put(SexeValues.inconnu, "0");
		sexeCodification.put(SexeValues.male, "M");
		sexeCodification.put(SexeValues.femelle, "F");
		formatValuesDefinition = new FormatValuesDefinition();
		consentCodification.put(ConsentValues.yes, "1");
		consentCodification.put(ConsentValues.no, "0");
		consentCodification.put(ConsentValues.unknown, "null");
	}

	
	/**
	 * convert the Value of a nice db code to the format code
	 * 
	 * @param code
	 * @return
	 */
	public String convertGenderToValue(String code) {
		String result = formatValuesDefinition.getGender(SexeValues.inconnu);
		if(code!=null){
			if (sexeCodification.containsValue(code)) {
				for (SexeValues sVal : sexeCodification.keySet()) {
					if (sexeCodification.get(sVal).equals(code)) {
						result = formatValuesDefinition.getGender(sVal);
					}
				}
			}
		}
		return result;
	}
	public String convertConsent(String code) {
		String result = formatValuesDefinition.getConsent(ConsentValues.unknown);
		if(code!=null){
			if (consentCodification.containsValue(code)) {
				for (ConsentValues sVal : consentCodification.keySet()) {
					if (consentCodification.get(sVal).equals(code)) {
						result = formatValuesDefinition.getConsent(sVal);
					}
				}
			}
		}
		return result;
	}
}
