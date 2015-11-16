package fr.inserm.log;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.exporter.XMLExporter;

/**
 * outil d affichage du log d alertes.
 * 
 * @author nicolas
 * 
 */
public class AlertLogExporter {
	private static final Logger LOGGER = Logger.getLogger(AlertLogExporter.class);

	/**
	 * print as a string.
	 * 
	 * @param alertes
	 * @return
	 */
	public static String print(List<TransformAlert> alertes) {
		StringBuffer result = new StringBuffer("SOURCE \t| DEST \t| ID \t| TYPE \t| LITERAL\n");

		for (TransformAlert alert : alertes) {
			result.append(alert.getFieldSourceName() + "\t|" + alert.getFieldDestName() + "\t|" + alert.getIdElement()
					+ "\t|" + alert.getAlertType() + "\t|" + alert.getLiteralAlert() + "\n");
		}
		return result.toString();
	}

	/**
	 * print des anomalies dans un fichier
	 */
	public static void exportInFile(List<TransformAlert> alertes, PropertiesBean properties) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ddMMyyyy");
		String suffixDate = sdf.format(date);
		String NomFichier = properties.getFolderExport() + "anomalies_log" + suffixDate + ".log";
		try {
			PrintWriter out = new PrintWriter(new FileWriter(NomFichier));
			out.println(print(alertes));
			out.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	public static void exportAlertesInXMLFile(String nbFiles, String nbEch, String nomFichier,
			List<TransformAlert> alertes, PropertiesBean properties) {
		XMLExporter xmlExporter = new XMLExporter(properties);

		try {
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(currentDate);
			SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
			String heure = sdf2.format(currentDate);
			FileOutputStream fos = new FileOutputStream(nomFichier);
			OutputFormat of = new OutputFormat("XML", "utf-8", true);
			of.setIndent(1);
			of.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(fos, of);
			ContentHandler hd = serializer.asContentHandler();
			hd.startDocument();
			AttributesImpl atts = new AttributesImpl();
			hd.startElement("", "", "alertes", atts);
			xmlExporter.writeLeafElement(hd, "date", date);
			xmlExporter.writeLeafElement(hd, "heure", heure);
			xmlExporter.writeLeafElement(hd, "nbFichiers", nbFiles);
			xmlExporter.writeLeafElement(hd, "nbEchantillons", nbEch);
			for (TransformAlert alerte : alertes) {
				hd.startElement("", "", "alerte", atts);
				xmlExporter.writeLeafElement(hd, "id", alerte.idElement);
				xmlExporter.writeLeafElement(hd, "source", alerte.fieldSourceName);
				xmlExporter.writeLeafElement(hd, "destination", alerte.fieldDestName);
				xmlExporter.writeLeafElement(hd, "type", alerte.alertType.toString());
				xmlExporter.writeLeafElement(hd, "literal", alerte.literalAlert);
				hd.endElement("", "", "alerte");
			}

			hd.endElement("", "", "alertes");
			hd.endDocument();
			fos.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
