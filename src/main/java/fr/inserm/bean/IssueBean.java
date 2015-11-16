package fr.inserm.bean;

/**
 * bean de communication pour reporter des problemes, erreurs a travers l application.
 * 
 * @author nicolas
 * 
 */
public class IssueBean {

	public IssueBean(Severity sever, String messge, Categorie cat) {
		message = messge;
		severity = sever;
		categorie = cat;
	}

	public String message;

	public Severity severity;

	public Categorie categorie;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public enum Severity {
		error, warn, major, minor, cosmetic
	}

	public enum Categorie {
		extraction, exportXML, exportJSON, transportFTP, transportSFTP
	}
}
