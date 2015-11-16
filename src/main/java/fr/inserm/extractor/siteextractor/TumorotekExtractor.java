package fr.inserm.extractor.siteextractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.bean.SiteBean;
import fr.inserm.bean.v2.EchantillonBean;
import fr.inserm.bean.v2.FormatDefinition;
import fr.inserm.extractor.AbstractExtractor;
import fr.inserm.extractor.IExtractor;
import fr.inserm.log.AlertType;
import fr.inserm.log.TransformAlert;

/**
 * classe d extraction des donnes de la base
 * 
 * @author nicolas
 * 
 */
public class TumorotekExtractor extends AbstractExtractor implements IExtractor {

	private static final Logger LOGGER = Logger.getLogger(TumorotekExtractor.class);

	/**
	 * chargement des properties.
	 * 
	 * 
	 */
	public TumorotekExtractor(PropertiesBean appLoader) {
		appProperties = appLoader;
		alertes = new ArrayList<TransformAlert>();
	}

	/**
	 * extraction des infos.
	 * 
	 */
	@Override
	public FileInputBean extract() {
		FileInputBean result = new FileInputBean();
		SiteBean site = new SiteBean();
		site.setFiness(appProperties.getFinessSite());
		site.setId(appProperties.getIdSite());
		site.setName(appProperties.getNameSite());
		result.setSite(site);
		result.setDateImport(new Date());
		result.setFormatVersion(1);
		try {
			Connection conn = getConnection(appProperties.getHost(), appProperties.getPort(),
					appProperties.getDbname(), appProperties.getLogin(), appProperties.getPassword());
			String query = "select ECHANTILLON_ID,prel.DATE_PRELEVEMENT,ech.DELAI_CGL, prel.PATIENT_NDA ,maladie.DATE_DIAGNOSTIC,"
					+ "ech.PRELEVEMENT_ID,prel.DATE_PRELEVEMENT,prel.PRELEVEMENT_TYPE_ID,ech.TUMORAL, ech.CODE, maladie.LIBELLE, maladie.CODE "
					+ "from ECHANTILLON ech "
					+ "left join PRELEVEMENT prel on prel.PRELEVEMENT_ID=ech.PRELEVEMENT_ID "
					+ "left join MALADIE maladie on maladie.MALADIE_ID=prel.MALADIE_ID";
			if (appProperties.getSqlFilter() != null && !appProperties.getSqlFilter().isEmpty()) {
				query += " where " + appProperties.getSqlFilter();
			}
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			List<EchantillonBean> echantillons = new ArrayList<EchantillonBean>();
			// pour chaque ligne ajout d un echantillon.
			while (rs.next()) {
				EchantillonBean ech = new EchantillonBean();
				String id = rs.getString(1);
				ech.addValue(FormatDefinition.id_depositor, id);
				ech.addValue(FormatDefinition.id_sample, id);
				// ech.addValue(FormatDefinition., valeur)
				// try {
				// ech.setDatePrelevement(mysqlDateToDate(rs.getString(2)));
				// } catch (Exception ex) {
				// alertes.add(new TransformAlert(idElement,
				// "PRELEVEMENT.DATE_PRELEVEMENT", "ech.DatePrelevement",
				// AlertType.CONVERSION_PROBLEM, ex.getMessage()));
				// LOGGER.debug("pb de conversion de date de prel:" +
				// ex.getMessage());
				// }
				String delai = rs.getString(3);
				try {
					ech.addValue(FormatDefinition.delay_freezing, "" + Integer.parseInt(delai));
				} catch (Exception ex) {
					alertes.add(new TransformAlert(id, "ECHANTILLON.DELAI_CGL", "echantillon.delaiCongelation",
							AlertType.UNPARSEABLE_FIELD, ex.getMessage()));
					LOGGER.debug("pb parsing delai congelation" + ex.getMessage());
				}
				// String identifiantPatient = rs.getString(4);
				// ech.setIdentifiantPatient(identifiantPatient);
				// // date de diagnostic
				// String dateDiagnostic = rs.getString(5);
				// try {
				// ech.setDateDiagnostic(mysqlDateShortToDate(dateDiagnostic));
				// } catch (Exception e) {
				// alertes.add(new TransformAlert(idElement,
				// "MALADIE.DATE_DIAGNOSTIC", "ECH.DateDiagnostic",
				// AlertType.CONVERSION_PROBLEM, e.getMessage()));
				// }
				// id prelevement
				// String idPrel = rs.getString(6);
				// ech.setIdentifiantPrelevement(idPrel);
				// // date prelevement
				// String datePrelevement = rs.getString(7);
				// try {
				// ech.setDatePrelevement(mysqlDateToDate(datePrelevement));
				// } catch (Exception e) {
				// alertes.add(new TransformAlert(idElement,
				// "PRELEVEMENT.DATE_PRELEVEMENT", "ECH.DATE_PRELEVEMENT",
				// AlertType.CONVERSION_PROBLEM, e.getMessage()));
				// }
				// // type prelevement
				// String prelType = rs.getString(8);
				// try {
				// int tyPre = Integer.parseInt(prelType);
				// ech.setTypePrelevement(TumoroteKCodification.translateTypePrelevement(tyPre));
				// } catch (Exception e) {
				// alertes.add(new TransformAlert(idElement,
				// "PRELEVEMENT.TypePrelevement", "ECH.TypePrelevement",
				// AlertType.CONVERSION_PROBLEM, e.getMessage()));
				// }
				// // organe
				// ech.setOrgane(OrganeValues.inconnu);
				// tumor
				// String tumor = rs.getString(9);
				// try {
				// ech.setIsTumoral(TumoroteKCodification.translateTumoral(tumor));
				// } catch (Exception ex) {
				// alertes.add(new TransformAlert(idElement,
				// "ECHANTILLON.Tumoral", "echantillon.Tumoral",
				// AlertType.UNPARSEABLE_FIELD, ex.getMessage()));
				// LOGGER.debug("pb parsing delai congelation" +
				// ex.getMessage());
				// }
				// // ajout de notes
				// String code = rs.getString(10);
				// try {
				// ech.addNote(makeNote("CODE", code));
				// } catch (Exception ex) {
				// alertes.add(new TransformAlert(idElement, "ECHANTILLON.Code",
				// "echantillon.Code",
				// AlertType.UNPARSEABLE_FIELD, ex.getMessage()));
				// LOGGER.debug("pb parsing delai congelation" +
				// ex.getMessage());
				// }
				// // /maladie libelle
				// String libelle = rs.getString(11);
				// try {
				// ech.addNote(makeNote("MALADIE.LIBELLE", libelle));
				// } catch (Exception ex) {
				// alertes.add(new TransformAlert(idElement, "MALADIE.LIBELLE",
				// "MALADIE.LIBELLE",
				// AlertType.UNPARSEABLE_FIELD, ex.getMessage()));
				// LOGGER.debug("pb parsing delai congelation" +
				// ex.getMessage());
				// }
				// maladie code
				// String maladieCode = rs.getString(12);
				// try {
				// ech.addNote(makeNote("MALADIE.CODE", maladieCode));
				// } catch (Exception ex) {
				// alertes.add(new TransformAlert(idElement, "MALADIE.Code",
				// "Maladie.Code",
				// AlertType.UNPARSEABLE_FIELD, ex.getMessage()));
				// LOGGER.debug("pb parsing delai congelation" +
				// ex.getMessage());
				// }
				echantillons.add(ech);
			}
			result.setEchantillons(echantillons);
		} catch (SQLException ex) {
			LOGGER.error("erreur sql:" + ex.getMessage());
		}
		return result;
	}

	/**
	 * retourne la date a partir d une string issue d une chaine mysql de date.
	 * 
	 * @param chaine
	 * @return
	 * @throws ParseException
	 */
	public Date mysqlDateToDate(String dateString) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = sdf.parse(dateString);
		return date;
	}

	/**
	 * retourne une date a partir d une date sans horodatage.
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public Date mysqlDateShortToDate(String dateString) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(dateString);
		return date;
	}

	@Override
	public Connection getConnection(String host, String port, String dbname, String user, String password)
			throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", password);
		// Load JBBC driver "com.mysql.jdbc.Driver".
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname, connectionProps);
		} catch (InstantiationException e) {
			LOGGER.warn("Erreur connexion jdbc mysql:" + e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.warn("Erreur connexion jdbc mysql:" + e.getMessage());
		} catch (ClassNotFoundException e) {
			LOGGER.warn("Erreur connexion jdbc mysql:" + e.getMessage());
		}

		LOGGER.info("Connected to database");
		return conn;
	}

}
