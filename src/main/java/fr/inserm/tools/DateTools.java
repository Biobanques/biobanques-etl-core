package fr.inserm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateTools {
	private static final Logger LOGGER = Logger.getLogger(DateTools.class);

	/**
	 * retourne la date a partir d une string issue d une chaine mysql de datetime.
	 * 
	 * @param chaine
	 * @return
	 * @throws ParseException
	 */
	public static String mysqlDateToDate(String dateString) throws ParseException {

		if (dateString != null) {
			try {
				Date date = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				date = sdf.parse(dateString);
				String dateToReturn = sdf.format(date);
				return dateToReturn;
			} catch (Exception e) {
				LOGGER.error("probleme de conversion de date : " + e.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Convertit une date dans le format de sortie, et la retourne sous forme de String
	 * 
	 * @param dateString
	 * @param sdfEntree
	 * @param sdfSortie
	 * @return
	 * @throws ParseException
	 */
	public static String convertDate(String dateString, SimpleDateFormat sdfEntree, SimpleDateFormat sdfSortie) {
		if (dateString != null && sdfEntree != null && sdfSortie != null) {
			try {
				String dateToReturn = sdfSortie.format(sdfEntree.parse(dateString));
				return dateToReturn;
			} catch (Exception e) {
				LOGGER.error("Problème de conversion de date : " + e.getMessage());
				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * retourne une date à partir d'une String au format yyyy-MM-dd hh:mm:ss
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static Date mysqlStringToDate(String dateString) {
		if (dateString != null) {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {

				mysqlDateShortToDate(dateString);

			}
			return date;
		} else {
			return null;
		}
	}

	/**
	 * retourne une date a partir d'une string de date dd une date sans horodatage.
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date mysqlDateShortToDate(String dateString) {
		if (dateString != null) {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				LOGGER.error("problème de parsing / conversion de date : " + dateString);
			}
			return date;
		} else {
			return null;
		}
	}

}
