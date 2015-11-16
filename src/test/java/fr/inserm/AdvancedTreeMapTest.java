package fr.inserm;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.inserm.bean.LigneBean;

public class AdvancedTreeMapTest {
	static HashMap<String, String> hm01;
	static HashMap<String, String> hm02;
	static HashMap<String, String> hm03;
	static LigneBean lb1;
	static LigneBean lb2;
	static LigneBean lb3;
	static LigneBean lb4;

	@BeforeClass
	public static void init() {
		hm01 = new HashMap<String, String>();
		hm01.put("key01", "val01");
		hm01.put("key02", "val02");
		hm01.put("key03", "val03");
		hm01.put("key04", "val04");
		lb1 = new LigneBean();
		lb1.setLigne(hm01);

		hm02 = new HashMap<String, String>();
		hm02.put("key05", "val05");
		hm02.put("key02", "val06");
		hm02.put("key06", "val07");
		hm02.put("key04", "val08");
		lb2 = new LigneBean();
		lb2.setLigne(hm02);

		hm03 = new HashMap<String, String>();
		lb3 = new LigneBean();
		lb3.setLigne(hm03);
		lb4 = new LigneBean();
	}

	@Test
	public final void testPutIn() {
		AdvancedTreeMap tm1 = new AdvancedTreeMap();
		tm1.putIn(null, lb1);
		assertEquals(new AdvancedTreeMap(), tm1);
		tm1.putIn("", lb1);
		assertEquals(new AdvancedTreeMap(), tm1);
		tm1.putIn("adtKey1", null);
		assertEquals(new AdvancedTreeMap(), tm1);
		tm1.putIn("adtKey1", lb1);
		assertEquals(lb1, tm1.get("adtKey1"));
		tm1.putIn(null, lb2);
		assertEquals(lb1, tm1.get("adtKey1"));
		tm1.putIn("adtKey1", null);
		assertEquals(lb1, tm1.get("adtKey1"));
		tm1.putIn("adtKey1", lb2);
		assertEquals(lb1.mergeLigneBean(lb2), tm1.get("adtKey1"));
		tm1.putIn("adtKey2", lb2);
		assertEquals(lb2, tm1.get("adtKey2"));
	}

	@Test
	public final void testPutAllAdvancedTreeMap() {
		AdvancedTreeMap tm1 = new AdvancedTreeMap();
		AdvancedTreeMap tm2 = new AdvancedTreeMap();
		tm1.putIn("adtKey1", lb1);
		tm2.putIn("adtKey1", lb2);
		tm2.putIn("adtKey2", lb3);
		tm2.putIn("adtKey3", lb2);
		tm1.putAll(tm2);
		assertEquals(lb1.mergeLigneBean(lb2), tm1.get("adtKey1"));
		assertEquals(lb3, tm1.get("adtKey2"));
		assertEquals(lb2, tm1.get("adtKey3"));
		tm1.putAll(null);
		assertEquals(lb1.mergeLigneBean(lb2), tm1.get("adtKey1"));
		assertEquals(lb3, tm1.get("adtKey2"));
		assertEquals(lb2, tm1.get("adtKey3"));

	}

}
