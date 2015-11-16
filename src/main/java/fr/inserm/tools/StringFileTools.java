package fr.inserm.tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class StringFileTools {

	private static final Logger LOGGER = Logger.getLogger(StringFileTools.class);

	public static String fileToString(File file) {
		StringBuffer result = new StringBuffer();

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				result.append(strLine);

			}
			// Close the input stream
			br.close();
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			LOGGER.error("pb de conversion en string" + e.getMessage());

		}

		return result.toString();
	}

	/**
	 * deplace le fichier source dans le dossier specifié.
	 * 
	 * @param srcFile
	 * @param targetPath
	 */
	public static void copyFileToFolder(File srcFile, String folderTargetPath) {
		String targetPath = folderTargetPath + srcFile.getName();
		copyFile(srcFile, targetPath);
	}

	/**
	 * deplace le fichier source au chemin specifie.
	 * 
	 * @param srcFile
	 * @param targetPath
	 */
	public static void copyFile(File srcFile, String targetPath) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {

			File bfile = new File(targetPath);
			inStream = new FileInputStream(srcFile);
			outStream = new FileOutputStream(bfile);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			LOGGER.warn("Copie impossible : " + e.getMessage());
		} catch (NullPointerException ex) {
			LOGGER.warn("Problème de paramètre en entrée : " + ex.getMessage());
		}
	}

	/**
	 * add a slash at the end if the dir path doesn t contains it.
	 * 
	 * @param path
	 * @return correct path dir
	 */
	public static String addSlashPathDir(String path) {
		String result = path;
		if (path != null && path.length() > 1) {
			int length = path.length();
			if (path.charAt(length - 1) != '/') {
				result = path + "/";
			}
		}
		return result;
	}

	/**
	 * move des fichiers dans le repo fourni<br>
	 * copie puis DeleteTest.
	 * 
	 * @param file
	 * @param pathTargetFolder
	 */

	public static void moveFile(final File file, final String pathTargetFolder) {

		try {
			if (pathTargetFolder == null || pathTargetFolder == "") {
				throw new NullPointerException();
			}
			File bfile = new File(pathTargetFolder + file.getName());
			InputStream inStream = new FileInputStream(file);
			OutputStream outStream = new FileOutputStream(bfile);

			try {
				byte[] buffer = new byte[1024];
				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {
					outStream.write(buffer, 0, length);
				}
				outStream.flush();
			} finally {

				outStream.close();
				inStream.close();
			}
			// delete the original file
			LOGGER.info(file.getPath());
			file.setWritable(true, false);
			int nbPassage = 0;
			boolean isdeleted = false;
			while (!isdeleted) {

				isdeleted = file.delete();
				nbPassage++;
			}
			LOGGER.debug(nbPassage + " passages avant suppression");
			LOGGER.info("File is copied successful!");
		} catch (IOException e) {
			LOGGER.error("erreur IO pendant le move : " + e.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Erreur pendant le move : " + ex.getMessage());
		}

	}

	/**
	 * get the files from a folder containing the extension name.
	 * 
	 * @param pathFolder
	 * @param extensionName
	 * @return
	 * @throws FolderInexistException
	 */
	public static ArrayList<File> getFilesFromFolder(String pathFolder, String extensionName) {
		LOGGER.debug("pathfolder" + pathFolder);
		File folder = new File(pathFolder);
		if (!folder.exists()) {
			LOGGER.error("dossier  inexist:" + pathFolder);
			return null;

		}
		File[] filesList = folder.listFiles();

		ArrayList<File> newList = new ArrayList<File>();
		for (File f : filesList) {
			if (f.getName().endsWith(extensionName)) {
				newList.add(f);
			}
		}
		return newList;
	}

	/**
	 * 
	 * @param file
	 * @param absolutePathDir
	 * @param prefixName
	 *            is util to add a prefix like ACK_XXX to skip overwriting file.
	 */
	public static void moveFile(File file, String absolutePathDir, String prefixName) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			/*
			 * Si le prefixe est null, mettre un prefixe vide pour éviter de générer une erreur
			 */
			if (prefixName == null) {
				prefixName = "";
			}

			if (file == null || absolutePathDir == null) {
				throw new NullPointerException();
			}

			if (file.getName().endsWith(".encrypted")) {
				File bfile = new File(absolutePathDir + prefixName
						+ file.getName().substring(0, file.getName().length() - 10));
				outStream = new FileOutputStream(bfile);
			} else {
				File bfile = new File(absolutePathDir + prefixName + file.getName());
				outStream = new FileOutputStream(bfile);
			}

			inStream = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			// DeleteTest the original file
			file.delete();
			LOGGER.debug("File is moved successful!");
		} catch (IOException e) {
			LOGGER.error("pb move file : " + e.getMessage());

		} catch (NullPointerException ex) {
			LOGGER.error("pb move file : nullPointerException ");
		} catch (Exception e) {
		} finally {
			try {
				if (inStream != null)
					inStream.close();
				if (outStream != null)
					outStream.close();
			} catch (Exception e) {
				LOGGER.error("Probleme de fermeture des flux : " + e.getMessage());
			}
		}
	}
}
