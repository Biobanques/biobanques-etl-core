package fr.inserm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class AgeFromDatesToolsTest extends TestCase {

	@Test
	public final void testAgeFromDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date naissance = sdf.parse("01/02/1990");
			Date prel = sdf.parse("05/05/2005");
			assertNull(AgeFromDatesTools.ageFromDates(null, null));
			assertNull(AgeFromDatesTools.ageFromDates(naissance, null));
			assertNull(AgeFromDatesTools.ageFromDates(null, prel));
			assertNull(AgeFromDatesTools.ageFromDates(prel, naissance));
			assertNull(AgeFromDatesTools.ageFromDates(naissance, naissance));
			assertEquals("15", AgeFromDatesTools.ageFromDates(naissance, prel));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
