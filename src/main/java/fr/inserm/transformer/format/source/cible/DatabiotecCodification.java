package fr.inserm.transformer.format.source.cible;

import fr.inserm.transformer.format.enums.EtatPatientValues;
import fr.inserm.transformer.format.enums.PrelevementTypeValues;
import fr.inserm.transformer.format.enums.TypeEchantillonValues;

/**
 * classe de transformation des valeurs du format Databiotec en enum.<br>
 * Databiotec - > Enums
 * 
 * @author nicolas
 * 
 */
public class DatabiotecCodification {

	private DatabiotecCodification() {

	}

	/**
	 * traduit la donne etat patient dans l enum.
	 * 
	 * @param value
	 * @return
	 */
	public EtatPatientValues translateEtatPatient(String value) {
		EtatPatientValues result = EtatPatientValues.inconnu;
		return result;
	}

	public static TypeEchantillonValues translateTypeEchantillon(String value) {
		TypeEchantillonValues result = TypeEchantillonValues.inconnu;
		if (value.equals("PLA")) {
			result = TypeEchantillonValues.plasma;
		}
		if (value.equals("SG")) {
			result = TypeEchantillonValues.sang;
		}
		if (value.equals("ADN")) {
			result = TypeEchantillonValues.adn;
		}
		return result;
	}

	public static PrelevementTypeValues translateTypePrelevement(String value) {
		PrelevementTypeValues result = PrelevementTypeValues.inconnu;
		if (value != null) {
			if (value.equals("BIO")) {
				result = PrelevementTypeValues.biopsie;
			}
			if (value.equals("PONC")) {
				result = PrelevementTypeValues.ponction;
			}
			if (value.equals("FIBRO")) {
				result = PrelevementTypeValues.fibroscopie;
			}
		}
		return result;
	}

}
