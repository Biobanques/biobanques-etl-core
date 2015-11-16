package fr.inserm.transformer.format.source.cible;

/**
 * classe interface pour la codification.<br>
 * Propose les methodes a implemenetr.
 * 
 * @author nicolas
 * 
 */
public interface ICodification {

	/**
	 * methode pour imprimer les codifications du format.
	 * 
	 * @return
	 */
	public String printCodification();

	/**
	 * methode pour creer la liste des tables de codification.
	 */
	public void constructTables();

}
