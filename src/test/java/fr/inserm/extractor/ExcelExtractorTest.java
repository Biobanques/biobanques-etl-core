package fr.inserm.extractor;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class ExcelExtractorTest extends TestCase {
	URL filePath = this.getClass().getResource("/examples/entreprises.xls");
	String testFile = filePath.getFile();

	@Test
	public void testGetExcelColumnsKeyMap() {

		assertNull(ExcelExtractor.getExcelColumnsKeyMap(null, 1, 1));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(null, -1, 1));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(null, 1, -1));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(testFile, -1, 0));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(testFile, 0, -1));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(testFile, -1, -1));
		assertNull(ExcelExtractor.getExcelColumnsKeyMap(testFile, 0, 0));
		assertNotNull(ExcelExtractor.getExcelColumnsKeyMap(testFile, 0, 1));
	}

	@Test
	public void testGetCellsData() throws Exception {
		FileInputStream fis = new FileInputStream(testFile);
		HSSFWorkbook testWorkBook = new HSSFWorkbook(fis);
		HSSFSheet sheet = testWorkBook.getSheetAt(0);
		Row row = sheet.getRow(1);
		assertNull(ExcelExtractor.getCellData(null));
		assertNotNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(0)));
		assertNotNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(1)));
		assertNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(2)));

		row = sheet.getRow(2);
		assertNull(ExcelExtractor.getCellData(null));
		assertNotNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(0)));
		assertNotNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(1)));
		assertNull(ExcelExtractor.getCellData((HSSFCell) row.getCell(2)));

		fis.close();
	}

	@Test
	public void testGetNumericValue() throws Exception {
		assertNull(ExcelExtractor.getNumericValue(null));
	}

	@Test
	public void testGetExcelColumnsValues() {
		HashMap<String, Integer> excelColumnsMap = new HashMap<String, Integer>();
		excelColumnsMap.put("Dates", 0);
		excelColumnsMap.put("nom Entreprise", 1);
		excelColumnsMap.put("testFormule", 2);

		assertNull(ExcelExtractor.getExcelColumnsValues(null, 0, null, 0));
		assertNull(ExcelExtractor.getExcelColumnsValues(testFile, 0, null, 0));
		assertNull(ExcelExtractor.getExcelColumnsValues(null, 0, excelColumnsMap, 0));
		assertNull(ExcelExtractor.getExcelColumnsValues(testFile, -1, excelColumnsMap, 0));
		assertNull(ExcelExtractor.getExcelColumnsValues(testFile, 0, excelColumnsMap, -1));
		assertNotNull(ExcelExtractor.getExcelColumnsValues(testFile, 0, excelColumnsMap, 1));

	}
}
