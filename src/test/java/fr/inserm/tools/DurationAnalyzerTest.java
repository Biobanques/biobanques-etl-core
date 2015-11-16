package fr.inserm.tools;

import org.junit.Test;

public class DurationAnalyzerTest {

	@Test
	public final void test() {

		DurationAnalyzer duree = new DurationAnalyzer();
		duree.addEtape("début");
		for (int i = 0; i < 10000000; i++) {
			double j = 10000 * Math.random();
			if (j < 0.01 || j > 9999.9)
				duree.addEtape(Integer.toString(i));
			if (j >= 4999.99 && j <= 5000.01)
				duree.addEtape();
		}
		duree.endAnalyse();
	}

}
