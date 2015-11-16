package fr.inserm.tools;

import java.util.Date;
import java.util.GregorianCalendar;

public class AgeFromDatesTools {

	// TODO améliorer pour gérer le delta dû aux années bissextiles
	public static String ageFromDates(Date datenaissance, Date dateprelevement) {
		if (datenaissance == null || dateprelevement == null || datenaissance.compareTo(dateprelevement) >= 0) {
			return null;
		}
		int age = 0;
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(datenaissance);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(dateprelevement);
		age = (int) ((gc2.getTimeInMillis() - gc1.getTimeInMillis()) / (1000 * 60 * 60 * 24 * 365L));
		return ((Integer) age).toString();

	}

}
