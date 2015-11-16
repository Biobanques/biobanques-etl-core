package fr.inserm.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateToolsTest {

	@Test
	public final void testMysqlDateToDate() throws ParseException {
		assertNull(DateTools.mysqlDateToDate(null));
		assertNotNull(DateTools.mysqlDateToDate("2012-02-25 12:00:00"));
		assertEquals("2012-02-25 12:00:00", DateTools.mysqlDateToDate("2012-02-25 12:00:00"));
		assertNull(DateTools.mysqlDateToDate("2012-02-25"));

	}

	@Test
	public final void testConvertDate() {
		SimpleDateFormat sdfEntree = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		SimpleDateFormat sdfSortie = new SimpleDateFormat("dd/MM/yyyy");
		String stringDate = "25-12-2013 12:30:00";
		assertNotNull(DateTools.convertDate(stringDate, sdfEntree, sdfSortie));
		assertEquals("25/12/2013", DateTools.convertDate(stringDate, sdfEntree, sdfSortie));
		assertNull(DateTools.convertDate(null, sdfEntree, sdfSortie));
		assertNull(DateTools.convertDate(stringDate, null, sdfSortie));
		assertNull(DateTools.convertDate(stringDate, sdfEntree, null));
		assertNull(DateTools.convertDate("10/02/13", sdfEntree, sdfSortie));
	}

	@Test
	public final void testMysqlStringToDate() {
		String dateString = "2013-12-25 12:30:00";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateString);
			assertEquals(date, DateTools.mysqlStringToDate(dateString));
			assertNull(DateTools.mysqlStringToDate(null));
			DateTools.mysqlStringToDate("25/12/2013");

		} catch (Exception e) {
			fail("Exception anormale");
		}
	}

	@Test
	public final void testMysqlDateShortToDate() {
		String dateString = "2013-12-25";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			assertEquals(date, DateTools.mysqlDateShortToDate(dateString));
			assertNull(DateTools.mysqlDateShortToDate(null));
			DateTools.mysqlDateShortToDate("25/12/2013");

		} catch (Exception e) {
			fail("Exception anormale");
		}
	}

}
