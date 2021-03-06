/**
 * 
 */
package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * @author H4303-2014
 */
public class Noeud {
	private int id;
	private int x;
	private int y;
	private List<Troncon> tronconsSortants = new ArrayList<Troncon>();
	private List<Troncon> tronconsEntrants = new ArrayList<Troncon>();
	private int etat=-1;
	
	/**
	 * Constructeur sans parametres de l'Objet Noeud
	 */
	public Noeud(){
		
	}
	
	public Noeud(Noeud other) {
		this.id = Integer.MAX_VALUE;
		this.x = other.x;
		this.y = other.y;
		this.tronconsEntrants = other.tronconsEntrants;
		this.tronconsSortants = other.tronconsSortants;
		this.etat = other.etat;
	}
	
	/**
	 * Constructeur avec parametres de l'Objet Noeud
	 * @param unId : l'ID du Noeud a construire et ses coordonnees X et Y dans le plan
	 * @param unX : l'absisse X du Noeud dans le plan
	 * @param unY : l'oordonnee Y du Noeud dans le plan
	 */
	public Noeud(int unId, int unX, int unY) {
		id = unId;
		x = unX;
		y = unY;
	}
	
	/**
	 * Ajoute un objet troncon a la liste des troncons sortants du Noeud 
	 * @param troncon : L'Objet Troncon a rajouter 
	 */
	public void ajouterTronconSortant(Troncon troncon) {
		this.tronconsSortants.add(troncon);
	}
	
	/**
	 * Ajoute un objet troncon a la liste des troncons entrants du Noeud
	 * @param troncon L'Objet Troncon a rajouter
	 *
	 */
	public void ajouterTronconEntrant(Troncon troncon) {
		this.tronconsEntrants.add(troncon);
	}
	
	/**
	 * Accesseur de l'Attribut tronconsEntrants
	 * @return List<Troncon> Liste des Objets Troncons entrants de Noeud
	 *
	 */
	public List<Troncon> getTronconsEntrant() {
		return this.tronconsEntrants;
	}
	
	/**
	 * Accesseur de l'Attribut tronconsSortants
	 * @return List<Troncon> Liste des Objets Troncons sortants de Noeud
	 *
	 */
	public List<Troncon> getTronconsSortant() {
		return this.tronconsSortants;
	}
	
	
	/**
	 * Accesseur de l'Attribut Id represantant l'Identifiant du Noeud
	 * @return int la valeur de l'Attribut Id
	 *
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Accesseur de l'Attribut X represantant l'absisse du Noeud dans le plan
	 * @return int la valeur de l'Attribut X
	 *
	 */
	public int getX() {
		return x;
	}
	
	/**
	* Accesseur de l'Attribut Y representant l'ordonnee du Noeud dans le plan
	 * @return int la valeur de l'Attribut Y
	 *
	 */
	public int getY() {
		return y;
	}
	
	/**
	* Accesseur de l'Attribut visite du Noeud dans le plan, qui definit si le noeud a ete visite dans la tournee ou pas.
	 * @return bool true si le noeud a ete visite false sinon
	 *
	 */
	public int getEtat(){
		return etat;
	}
	
	/**
	* Mutateur de l'Attribut visite du Noeud dans le plan, qui definit si le noeud a ete visite dans la tournee ou pas.
	 * @param bool visite valeur du boolean a mettre dans l'Attribut visite de l'Objet Noeud
	 *
	 */
	public void setEtat(int etat){
		this.etat=etat;
	}

}
