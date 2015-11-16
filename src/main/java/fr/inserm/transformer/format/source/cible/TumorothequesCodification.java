package fr.inserm.transformer.format.source.cible;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import fr.inserm.transformer.format.enums.EtatPatientValues;
import fr.inserm.transformer.format.enums.OrganeValues;
import fr.inserm.transformer.format.enums.PrelevementTypeValues;
import fr.inserm.transformer.format.enums.SexeValues;
import fr.inserm.transformer.format.enums.TumoralValues;
import fr.inserm.transformer.format.enums.TypeEvenementValues;

/**
 * Traduit les enums en literal pour le xml<br>
 * Enum -> Literal
 * 
 * @author nicolas
 * 
 */
public class TumorothequesCodification extends AbstractCodification implements ICodification {

	public Hashtable<TypeEvenementValues, String> typeEvenementCodification = new Hashtable<TypeEvenementValues, String>();

	public Hashtable<TumoralValues, String> tumoralCodification = new Hashtable<TumoralValues, String>();

	
	public Hashtable<OrganeValues, String> organeCodification = new Hashtable<OrganeValues, String>();

	public Hashtable<PrelevementTypeValues, String> typePrelevementCodification = new Hashtable<PrelevementTypeValues, String>();

	public Hashtable<EtatPatientValues, String> etatPatientCodification = new Hashtable<EtatPatientValues, String>();

	public Hashtable<SexeValues, String> sexeCodification = new Hashtable<SexeValues, String>();

	public TumorothequesCodification() {
		setCodificatioName("Tumorotheques");
		// remplissage de s types evenements
		typeEvenementCodification.put(TypeEvenementValues.tumeurPrimitive, "1");
		typeEvenementCodification.put(TypeEvenementValues.recidive, "2");
		typeEvenementCodification.put(TypeEvenementValues.metastase, "3");
		typeEvenementCodification.put(TypeEvenementValues.transformation, "4");
		typeEvenementCodification.put(TypeEvenementValues.remission, "5");
		typeEvenementCodification.put(TypeEvenementValues.inconnu, "9");
		// tumoral
		tumoralCodification.put(TumoralValues.inconnu, "I");
		tumoralCodification.put(TumoralValues.oui, "O");
		tumoralCodification.put(TumoralValues.non, "N");
		// organe
		organeCodification.put(OrganeValues.inconnu, "inconnu");
		// type prelevement
		typePrelevementCodification.put(PrelevementTypeValues.biopsie, "B");
		typePrelevementCodification.put(PrelevementTypeValues.pieceOperatoire, "O");
		typePrelevementCodification.put(PrelevementTypeValues.ponction, "P");
		typePrelevementCodification.put(PrelevementTypeValues.liquide, "L");
		typePrelevementCodification.put(PrelevementTypeValues.cytoponction, "C");
		typePrelevementCodification.put(PrelevementTypeValues.inconnu, "I");
		// etat patient
		etatPatientCodification.put(EtatPatientValues.decede, "D");
		etatPatientCodification.put(EtatPatientValues.vivant, "V");
		etatPatientCodification.put(EtatPatientValues.inconnu, "I");
		// sexe
		sexeCodification.put(SexeValues.femelle, "F");
		sexeCodification.put(SexeValues.male, "M");
		sexeCodification.put(SexeValues.inconnu, "I");
		sexeCodification.put(SexeValues.hermaphrodite, "Id");
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public String convertToCode(PrelevementTypeValues value) {
		if (value == null)
			return "";
		return typePrelevementCodification.get(value);
	}

	public String convertToCode(EtatPatientValues value) {
		if (value == null)
			return "";
		return etatPatientCodification.get(value);
	}

	public String convertToCode(OrganeValues value) {
		if (value == null)
			return "";
		return organeCodification.get(value);
	}

	/**
	 * convertit en code tumoral.
	 * 
	 * @param value
	 * @return
	 */
	public String convertToCode(TumoralValues value) {
		if (value == null)
			return "";
		return tumoralCodification.get(value);
	}

	public static String formatDateToTumorotheques(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public String convertToCode(SexeValues sexe) {
		if (sexe == null)
			return "";
		return sexeCodification.get(sexe);
	}

	@SuppressWarnings("rawtypes")
	public void constructTables() {
		listHashtables = new ArrayList<Hashtable>();
		listHashtables.add(typeEvenementCodification);
	}
}
