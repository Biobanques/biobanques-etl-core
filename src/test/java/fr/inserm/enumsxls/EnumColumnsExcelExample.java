/**
 * Class d'enum ne servant qu'aux cas d'exemples type ExcelExtractorTest
 */
package fr.inserm.enumsxls;

import fr.inserm.ExcelColumns;
import fr.inserm.ISwitchEnum;

public enum EnumColumnsExcelExample implements ISwitchEnum {
	ADRESSE_1ERE_PARTIE(2), ADRESSE_2EME_PARTIE(3), CODE_POSTAL(4), COMMUNE(5), DATES(0), NOM_ENTREPRISE(1), TELEPHONE(
			6);
	private ExcelColumns excelColumns;

	private EnumColumnsExcelExample(int value) {
		this.excelColumns = new ExcelColumns(value);
	}

	@Override
	public ExcelColumns getExcelColumns() {
		return excelColumns;
	}

	@Override
	public EnumColumnsExcelExample getName(int value) {
		EnumColumnsExcelExample res = null;
		for (EnumColumnsExcelExample col : EnumColumnsExcelExample.values()) {
			if (col.getExcelColumns().getNumCol() == value) {
				res = col;
			}
		}
		return res;
	}
}