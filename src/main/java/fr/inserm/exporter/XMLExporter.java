package fr.inserm.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.bean.SiteBean;
import fr.inserm.bean.v2.EchantillonBean;
import fr.inserm.bean.v2.FormatDefinition;
import fr.inserm.tools.StringFileTools;
import fr.inserm.transformer.format.source.cible.TumorothequesCodification;

/**
 * classe to export beanXML ti file xml.
 * 
 * @author nicolas
 * 
 */
public class XMLExporter {

	private static final Logger LOGGER = Logger.getLogger(XMLExporter.class);

	TumorothequesCodification codif;

	PropertiesBean properties;
	public int nbFichiers;
	public int nbEchantillons;
	private int MAX_ECHANTILLONS_PAR_FICHIER = 1000;

	/**
	 * init du mapping de codifications.
	 */
	public XMLExporter(PropertiesBean props) {
		properties = props;
		codif = new TumorothequesCodification();

	}

	/**
	 * export bean to a file.
	 * 
	 * @param input
	 * @return 0 if ok, error code<0 if pb
	 */
	public int exportFile(FileInputBean input) {
		int res = 0;
		// si le nb d echantillon est grand decoupe du fichier
		if (input == null || input.getEchantillons() == null) {
			LOGGER.error("problem input ou echantillons null");
			return -1;
		}
		nbEchantillons = input.getEchantillons().size();
		nbFichiers = 1;
		if (nbEchantillons > MAX_ECHANTILLONS_PAR_FICHIER) {
			LOGGER.info("decoupe des fichiers, volume d echantillon trop grand:" + nbEchantillons);
			nbFichiers = 1 + nbEchantillons / MAX_ECHANTILLONS_PAR_FICHIER;
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ddMMyyyy");
		String suffixDate = sdf.format(date);
		try {
			for (int i = 1; i <= nbFichiers; i++) {
				String idSite = properties.getIdSite() + "";
				String fileName = properties.getFolderExport() + "export_inserm_" + idSite + "_" + i + "_sur_"
						+ nbFichiers + "_" + suffixDate + ".xml";
				FileOutputStream fos = new FileOutputStream(fileName);
				OutputFormat of = new OutputFormat("XML", "utf-8", true);
				of.setIndent(1);
				of.setIndenting(true);
				XMLSerializer serializer = new XMLSerializer(fos, of);
				ContentHandler hd = serializer.asContentHandler();
				hd.startDocument();
				AttributesImpl atts = new AttributesImpl();
				hd.startElement("", "", "import", atts);
				// <!-- date au format iso 8601 -->
				// 2011-04-01T13:01:02</date>
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				hd = writeLeafElement(hd, "date", format.format(date));
				hd = writeLeafElement(hd, "formatVersion", "1");
				hd = writeSite(hd, input.getSite());
				hd.startElement("", "", "echantillons", atts);
				int maxPage = i * MAX_ECHANTILLONS_PAR_FICHIER;
				if (maxPage > nbEchantillons) {
					maxPage = nbEchantillons;
				}
				for (int j = (i - 1) * MAX_ECHANTILLONS_PAR_FICHIER; j < maxPage; j++) {
					EchantillonBean ech = input.getEchantillons().get(j);
					hd = writeEchantillon(hd, ech);
				}
				hd.endElement("", "", "echantillons");
				hd.endElement("", "", "import");
				hd.endDocument();
				fos.close();
				File fis = new File(fileName);
				// chiffrement du fichier selon choix
				if (properties.crypt) {

					String fichierXML = StringFileTools.fileToString(fis);
					if (fichierXML != null && !fichierXML.isEmpty()) {
						String resEncrypted = Crypter.encrypt(fichierXML, properties.cryptphrase);
						LOGGER.debug("resEncrypted:" + resEncrypted);
						if (resEncrypted != null && !resEncrypted.isEmpty()) {
							fileName = fileName + ".encrypted";
							FileWriter fileEncrypted = new FileWriter(fileName);
							fileEncrypted.write(resEncrypted);
							fileEncrypted.flush();
							fileEncrypted.close();
							// delete du fichier original, move du nouveau avec
							// bon nom
							fis.delete();
						} else {
							LOGGER.error("resultat encrypté null ou vide");
						}
					} else {
						LOGGER.error("fichier xml null ou vide");
					}
				}

				if (properties.compress) {
					File fileToCompress = new File(fileName);
					GZIPCompress.compress(fileToCompress);
					fileToCompress.delete();
					fis.delete();
				}

			}
		} catch (FileNotFoundException e) {
			LOGGER.error("file not found" + e.getMessage());
			res = -1;
		} catch (IOException e) {
			LOGGER.error("i/o:" + e.getMessage());
			res = -1;
		} catch (SAXException e) {
			LOGGER.error("problem sax:" + e.getMessage());
			res = -1;
		}

		return res;
	}

	/**
	 * write an echantillon content. </echantillon>
	 * 
	 * @param hd
	 * @return
	 */
	private ContentHandler writeEchantillon(ContentHandler hd, EchantillonBean ech) {
		ContentHandler result = hd;
		AttributesImpl atts = new AttributesImpl();
		try {
			hd.startElement("", "", "echantillon", atts);
			// ecriture de chaque information presente au format XML
			for (FormatDefinition champ : FormatDefinition.values()) {
				if (ech.getMapValues().containsKey(champ)) {
					hd = writeLeafElement(hd, champ + "", ech.getValue(champ));
				}
			}
			hd = writeNotes(hd, ech);
			hd.endElement("", "", "echantillon");
		} catch (SAXException e) {
			LOGGER.error("problem sax:" + e.getMessage());
		}
		return result;
	}

	/**
	 * ecrit les notes de l echa.
	 * 
	 * @param hd
	 * @return
	 */
	public ContentHandler writeNotes(ContentHandler hd, EchantillonBean ech) {
		ContentHandler result = hd;
		AttributesImpl atts = new AttributesImpl();
		try {
			result.startElement("", "", "notes", atts);
			for (Entry<String, String> note : ech.getNotes().entrySet()) {
				hd = writeLeafElement(hd, note.getKey(), note.getValue());
			}
			result.endElement("", "", "notes");
		} catch (SAXException e) {
			LOGGER.error("problem sax:" + e.getMessage());
		}
		return result;
	}

	/**
	 * <site> <id>1</id> <name>Hp Saint Louis</name> <finess>750101234</finess> </site>
	 */
	public ContentHandler writeSite(ContentHandler hd, SiteBean site) {
		ContentHandler result = hd;
		AttributesImpl atts = new AttributesImpl();
		try {
			hd.startElement("", "", "site", atts);
			// id
			hd = writeLeafElement(hd, "id", site.getId());
			hd = writeLeafElement(hd, "name", site.getName());
			hd = writeLeafElement(hd, "finess", site.getFiness());
			hd.endElement("", "", "site");
		} catch (SAXException e) {
			LOGGER.error("problem sax:" + e.getMessage());
		}
		return result;
	}

	/**
	 * write a leaf element in xml.
	 * 
	 * @param hd
	 * @param elementName
	 * @param elementValue
	 * @return
	 */
	public ContentHandler writeLeafElement(ContentHandler hd, String elementName, String elementValue) {
		ContentHandler result = hd;
		AttributesImpl atts = new AttributesImpl();
		try {
			hd.startElement("", "", elementName, atts);
			if (elementValue != null && !elementValue.isEmpty()) {
				hd.characters(elementValue.toCharArray(), 0, elementValue.length());
			}
			hd.endElement("", "", elementName);
		} catch (SAXException e) {
			LOGGER.error("problem sax:" + e.getMessage());
		}
		return result;

	}

}
