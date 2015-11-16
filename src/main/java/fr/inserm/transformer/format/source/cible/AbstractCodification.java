package fr.inserm.transformer.format.source.cible;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * classe abstraite de cofication.
 * 
 * @author nicolas
 * 
 */
public class AbstractCodification {

	String codificationName = "";

	@SuppressWarnings("rawtypes")
	List<Hashtable> listHashtables;

	public void setCodificatioName(String codif) {
		codificationName = codif;
	}

	public String getCodificatioName() {
		return codificationName;
	}

	/**
	 * print des codifications, via la liste de hashtables;
	 */
	@SuppressWarnings("rawtypes")
	public String printCodification() {
		String result = "Codification : " + getCodificatioName() + "\n";
		for (Hashtable table : listHashtables) {
			result += printHashtable(table, "");
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printHashtable( Hashtable hashtable, String nameTable) {
		String result = "";
		result += "TableName : " + nameTable + "\n";
		
		Set<String> set = hashtable.keySet();
		Iterator<String> itr = set.iterator();
		String key;
		while (itr.hasNext()) {
			key = itr.next();
			result += (key + "\t|" + hashtable.get(key) + "\n");
		}
		return result;
	}

}
