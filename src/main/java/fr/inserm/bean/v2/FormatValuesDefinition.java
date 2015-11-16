package fr.inserm.bean.v2;

import java.util.HashMap;
import java.util.Map;

import fr.inserm.transformer.format.enums.ConsentValues;
import fr.inserm.transformer.format.enums.SexeValues;

/**
 * classe de format des valeurs de champs
 * 
 * @author nicolas
 * 
 */
public class FormatValuesDefinition {
	Map<SexeValues, String> genderValues;
	Map<ConsentValues, String> consentValues;

	public FormatValuesDefinition() {
		genderValues = new HashMap<SexeValues, String>();
		genderValues.put(SexeValues.inconnu, "U");
		genderValues.put(SexeValues.male, "M");
		genderValues.put(SexeValues.male, "F");
		genderValues.put(SexeValues.hermaphrodite, "H");

		consentValues = new HashMap<ConsentValues, String>();
		consentValues.put(ConsentValues.yes, "Y");
		consentValues.put(ConsentValues.no, "N");
		consentValues.put(ConsentValues.unknown, "U");
	}

	public String getGender(SexeValues key) {
		return genderValues.get(key);
	}

	public String getConsent(ConsentValues key) {
		return consentValues.get(key);
	}
}
