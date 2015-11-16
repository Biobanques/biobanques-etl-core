package fr.inserm.extractor;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import fr.inserm.bean.LigneBean;

public class ExcelExtractor {
	private static final Logger LOGGER = Logger.getLogger(ExcelExtractor.class);
	/***
	 * Format de date pour convertir les dates excel
	 */
	private static final String DATE_FORMAT = "ddMMyyyy";

	/**
	 * retourne uen liste de ligneBean associant le nom de la colonne provenant
	 * de l enum avec la valeur
	 * 
	 * @param excelFileName
	 * @param sheetNumber
	 * @param traductionColonnesEnum
	 * @param headerLine
	 *            ligne du header contenant les entets de colonnes. Les valeurs
	 *            demarrent a headerLine +1 La premiere ligne de la feuille est
	 *            0.
	 * 
	 * @return
	 */
	public static ArrayList<LigneBean> getExcelColumnsValues(
			String excelFileName, int sheetNumber,
			HashMap<String, Integer> traductionColonnesEnum, int headerLine) {
		ArrayList<LigneBean> result = new ArrayList<LigneBean>();
		if (headerLine <= 0) {
			LOGGER.warn("La ligne d'entête est obligatoire, impossible d'extraire les données");
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(excelFileName);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(fis));
			fis.close();
			HSSFSheet mySheet = myWorkBook.getSheetAt(sheetNumber);
			Iterator<Row> rowIter = mySheet.rowIterator();
			for (int i = 0; i < headerLine; i++) {
				rowIter.next();
			}
			/**
			 * Parcourt toutes les lignes de la feuille
			 */
			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				boolean notNull = false;
				LigneBean res = generateLigneBean(traductionColonnesEnum, myRow);
				for (String s : res.getValues()) {
					if (s != null) {
						notNull = true;
					}
				}
				if (notNull) {
					result.add(res);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
		return result;
	}

	public static ArrayList<LigneBean> getExcelColumnsValues(
			String excelFileName, String sheetName,
			HashMap<String, Integer> traductionColonnesEnum, int headerLine) {
		try {
			FileInputStream fis = new FileInputStream(excelFileName);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(fis));
			fis.close();
			int sheetNumber = myWorkBook.getSheetIndex(sheetName);
			return getExcelColumnsValues(excelFileName, sheetNumber,
					traductionColonnesEnum, headerLine);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static LigneBean generateLigneBean(
			HashMap<String, Integer> traductionColonnesEnum, HSSFRow myRow) {
		LigneBean res = new LigneBean();

		/**
		 * Parcourt les colonnes données dans la HashMap en paramètre
		 */
		for (Entry<String, Integer> entree : traductionColonnesEnum.entrySet()) {
			String cellValue;
			try {
				cellValue = getCellData(myRow.getCell(entree.getValue()));
				res.put(entree.getKey(), cellValue);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		return res;
	}

	public static HashMap<String, Integer> getExcelColumnsKeyMap(
			String excelFileName, String sheetName, int headerLine) {

		FileInputStream fis;
		try {
			fis = new FileInputStream(excelFileName);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(fis));
			int sheetNumber = myWorkBook.getSheetIndex(sheetName);

			return getExcelColumnsKeyMap(excelFileName, sheetNumber, headerLine);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get de la ligne d entete avec association des noms de colonnes et valeurs
	 * 
	 * @param excelFileName
	 * @param sheetNumber
	 * @param headerLine
	 * @return
	 */
	public static HashMap<String, Integer> getExcelColumnsKeyMap(
			String excelFileName, int sheetNumber, int headerLine) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		try {
			// TODO logguer exception si excelfilename=null
			FileInputStream fis = new FileInputStream(excelFileName);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(fis));
			fis.close();
			HSSFSheet mySheet = myWorkBook.getSheetAt(sheetNumber);
			Iterator<Row> rowIter = mySheet.rowIterator();
			HSSFRow myRow = null;
			for (int i = 0; i < headerLine; i++) {
				myRow = (HSSFRow) rowIter.next();
			}
			if (myRow != null) {
				// on parcours la ligne d entetet poru recuperer les colonnes et
				// leurs numeros
				for (int j = 0; j < myRow.getLastCellNum(); j++) {
					String nom = myRow.getCell(j).getStringCellValue();
					result.put(nom, j);
				}
			} else {
				throw new Exception("la ligne d'entête est incorrecte",
						new Throwable("headerline : " + headerLine));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " " + e.getCause());
			LOGGER.error("file : " + excelFileName + " - " + sheetNumber
					+ " - " + headerLine);
			return null;
		}
		return result;
	}

	/**
	 * Recuperation des valeurs des cellules
	 */

	public static String getCellData(HSSFCell myCell) throws Exception {
		String cellData;
		if (myCell == null) {
			return null;

		} else {
			switch (myCell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				cellData = myCell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				cellData = getNumericValue(myCell);
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				switch (myCell.getCachedFormulaResultType()) {
				case HSSFCell.CELL_TYPE_STRING:
					cellData = myCell.getRichStringCellValue().getString();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					cellData = getNumericValue(myCell);
					break;
				default:
					cellData = null;
				}
				break;
			default:
				cellData = null;
			}
		}
		return cellData;
	}

	// private static String getResult(HSSFCell myCell) {
	//
	// return getCellData(myCell.getCachedFormulaResultType());
	// }

	/**
	 * Récupération des valeurs des cellules numériques vérifie le type de
	 * valeur et traduit les dates en String si nécessaire
	 */
	public static String getNumericValue(HSSFCell myCell) throws Exception {
		if (myCell == null) {
			return null;
		}
		String cellData = "";
		if (HSSFDateUtil.isCellDateFormatted(myCell)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			cellData = sdf.format(myCell.getDateCellValue());
		} else {
			cellData = Double.toString(myCell.getNumericCellValue());
		}
		return cellData;
	}

}
