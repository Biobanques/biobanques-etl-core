package fr.inserm.log;

/**
 * objet to store alert on transformation
 * 
 * @author nicolas
 * 
 */
public class TransformAlert {

	public String idElement;
	/**
	 * champs source de l alerte.
	 */
	public String fieldSourceName;

	/**
	 * champs cible de l alerte.
	 */
	public String fieldDestName;

	/**
	 * type de l alerte pour analyse simplifiee.
	 */
	public AlertType alertType;

	/**
	 * alerte literale.(remontee par exception)
	 */
	public String literalAlert;

	public TransformAlert(String id, String sourceField, String destField, AlertType type, String literal) {
		fieldSourceName = sourceField;
		fieldDestName = destField;
		alertType = type;
		literalAlert = literal;
		idElement = id;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public String getFieldDestName() {
		return fieldDestName;
	}

	public String getFieldSourceName() {
		return fieldSourceName;
	}

	public String getIdElement() {
		return idElement;
	}

	public String getLiteralAlert() {
		return literalAlert;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	public void setFieldDestName(String fieldDestName) {
		this.fieldDestName = fieldDestName;
	}

	public void setFieldSourceName(String fieldSourceName) {
		this.fieldSourceName = fieldSourceName;
	}

	public void setIdElement(String idElement) {
		this.idElement = idElement;
	}

	public void setLiteralAlert(String literalAlert) {
		this.literalAlert = literalAlert;
	}

}
