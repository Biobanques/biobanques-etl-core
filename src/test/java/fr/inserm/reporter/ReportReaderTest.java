package fr.inserm.reporter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ReportReaderTest {
	@Test
	public void testWrite() {
		ReportReader rr = new ReportReader(null);
		assertNotNull(rr.getNbEchantillons());
		assertEquals("441",rr.getNbEchantillons());
	}

}
