package fr.inserm.transformer.format.source.cible;

import fr.inserm.transformer.format.enums.EtatPatientValues;
import fr.inserm.transformer.format.enums.PrelevementTypeValues;
import fr.inserm.transformer.format.enums.TumoralValues;

/**
 * classe de transformation des valeurs du format TK en enum.<br>
 * TK - > Enums
 * 
 * @author nicolas
 * 
 */
public class TumoroteKCodification {

	private TumoroteKCodification() {

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

	public static PrelevementTypeValues translateTypePrelevement(int value) {
		PrelevementTypeValues result = PrelevementTypeValues.inconnu;
		switch (value) {
		case 1:
			result = PrelevementTypeValues.biopsie;
			break;
		case 2:
			result = PrelevementTypeValues.necropsie;
			break;
		case 3:
			result = PrelevementTypeValues.ponction;
			break;
		case 4:
			result = PrelevementTypeValues.cytoponction;
			break;
		default:
			break;
		}
		return result;
	}

	public static TumoralValues translateTumoral(String tumoral) throws Exception {
		int delaiCongelation = Integer.parseInt(tumoral);
		TumoralValues result = TumoralValues.inconnu;
		if (delaiCongelation == 0) {
			result = TumoralValues.non;
		}
		if (delaiCongelation == 1) {
			result = TumoralValues.oui;
		}
		return result;
	}
}
