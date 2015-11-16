package fr.inserm;

/**
 * classe de defintiion du type oui/non pour classes de properties
 * 
 * @author nicolas
 * 
 */
public enum OuiNonType {
	OUI(1), NON(0);

	private int value;

	private OuiNonType(int val) {
		this.value = val;
	}

	public int getValue() {
		return value;
	}

	public static OuiNonType getName(int val) {
		OuiNonType res = null;
		for (OuiNonType enval : OuiNonType.values()) {
			if (enval.value == val) {
				res = enval;
			}
		}
		return res;
	}
}
