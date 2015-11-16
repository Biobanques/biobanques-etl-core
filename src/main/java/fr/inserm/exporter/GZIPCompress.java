package fr.inserm.exporter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

import fr.inserm.tools.StringFileTools;

public class GZIPCompress {
	private static final Logger LOGGER = Logger.getLogger(GZIPCompress.class);

	/**
	 * Compresse un fichier en format GZIP retourne -1 si fichier en entrée est nul
	 * 
	 * @param fileToCompress
	 * @return
	 */
	public static int compress(File fileToCompress) {
		int res = 0;
		try {
			String XMLEncrypted = StringFileTools.fileToString(fileToCompress);
			FileOutputStream fosc = new FileOutputStream(fileToCompress.getPath() + ".gz");
			GZIPOutputStream gos = new GZIPOutputStream(fosc);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(gos));
			PrintWriter pw = new PrintWriter(bw);
			pw.print(XMLEncrypted);
			pw.close();
			fileToCompress.delete();
		} catch (Exception e) {
			LOGGER.error("Probleme de compression : " + e.getMessage());
			return -1;
		}
		return res;
	}

	/**
	 * Decompresse un fichier compressé au format gzip. Renvoie -1 si le fichier est nul ou pas au format gzip
	 * 
	 * @param fileToUncompress
	 * @return
	 */
	public static int uncompress(File fileToUncompress) {
		int res = 0;
		try {
			FileInputStream fis = new FileInputStream(fileToUncompress.getPath());
			GZIPInputStream gzis = new GZIPInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(gzis, "utf-8"));
			StringBuffer xmlTransported = new StringBuffer();
			String readed;
			while ((readed = br.readLine()) != null) {
				xmlTransported.append(readed + System.getProperty("line.separator"));
			}
			br.close();
			FileWriter fw = new FileWriter(fileToUncompress.getPath().substring(0,
					fileToUncompress.getPath().length() - 3));
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.print(xmlTransported.toString());
			// fw.print(xmlTransported.toString());
			pw.close();
			bw.close();
			fw.close();
			gzis.close();
			fis.close();
		} catch (Exception e) {
			LOGGER.error("erreur de décompression : " + e.getMessage());
			return -1;
		}
		return res;
	}
}
