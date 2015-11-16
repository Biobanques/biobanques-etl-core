package fr.inserm;

/**
 * classe de defintiion des types de db/site disponibles
 * 
 * @author nicolas
 * 
 */
public enum DbDefinitionType {
	TUMOROTEK(1), STOCKY(2), LILLE_DATABIOTEC(3), NICE_MSACCESS(4), NANCY_XLS(5), GRENOBLE_TK_PLUS(
			6), GERMETHEQUE(7), CAEN_DATABIOTEC(8), CAEN_MSACCESS(9);

	private int value;

	private DbDefinitionType(int val) {
		this.value = val;
	}

	public int getValue() {
		return value;
	}

	public static DbDefinitionType getName(int val) {
		DbDefinitionType res = null;
		for (DbDefinitionType enval : DbDefinitionType.values()) {
			if (enval.value == val) {
				res = enval;
			}
		}
		return res;
	}
}
