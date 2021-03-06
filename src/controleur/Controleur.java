package controleur;

import java.text.ParseException;


import modele.Livraison;

import modele.Noeud;
import modele.ZoneGeographique;
import vue.Accueil;


/**
 * @author H4303 - 2014
 */
public class Controleur {
	

	private Invoker invoker;
	
	 private Accueil viewAccueil;
	 private ZoneGeographique modelZoneGeographique;
	 private Noeud noeudClicked;
	 
     /**
	 * Constructeur de Controleur
	 */ 
	 public Controleur() {
		
		this.invoker = new Invoker();
		this.viewAccueil = new Accueil(this); 
	}

	/**Charge un fichier contenant des livraisons
	 * @param chemin : chemin vers le fichier
	 * @return int
	 */ 
	public void chargerLivraison (String nomFic) {
		int res = this.modelZoneGeographique.chargerLivraison(nomFic);
		System.out.println("Livraison erreur: " + res);
		if (res == 0) {
			this.viewAccueil.CreerVueTournee();
			viewAccueil.afficherMessage("Chargement des livraisons r�ussi. Vous pouvez maintenant calculer un itin�raire.");
		} else if (res == -1) {
			String messageErreur = "Le fichier XML n'est pas valide !";
			viewAccueil.afficherMessageErreur(messageErreur);
		} else if (res == 1) {
			String messageErreur = "Erreur de conversion dans le fichier XML: adresse ou identifiant negatif";
			viewAccueil.afficherMessageErreur(messageErreur);
		} else if (res == 2) {
			String messageErreur = "Erreur dans le fichier XML !";
			viewAccueil.afficherMessageErreur(messageErreur);
		}	
	}
    /**Charger un fichier contenant les noeuds et troncons
	 * @param nomFic : nom du fichier
	 */
	public void ChargerZoneGeo (String nomFic) {
		this.modelZoneGeographique = new ZoneGeographique();
		int res = modelZoneGeographique.lirePlanXML(nomFic);
		if (res == 0) {
			viewAccueil.creerVueZoneGeographique(1100,500, this.modelZoneGeographique);
			viewAccueil.afficherMessage("Chargement du plan r�ussi. Veuillez maintenant charger des livraisons.");
		}
		else if (res == -1) {
			String messageErreur = "Le fichier XML n'est pas valide !";
			viewAccueil.afficherMessageErreur(messageErreur);
		} else if (res == 1) {
			String messageErreur = "Erreur de conversion dans le fichier XML: vitesse, longueur ou identifiant negatif";
			viewAccueil.afficherMessageErreur(messageErreur);
		} else if (res == 2){
			String messageErreur = "Erreur dans le fichier XML !";
			viewAccueil.afficherMessageErreur(messageErreur);
		}
    }
	
	
	
	

	
	public void afficherMessageErreur(String mess){
		viewAccueil.afficherMessageErreur(mess);
	}
	
	/**Ajoute une livraison
	 * @return int : eventuel code d'erreur
	 */
	public int AjouterLivraison (int client, int id) {
		if(noeudClicked==null){
			return -1;
		}
		ConcreteCommandAjouterLivraison command = new ConcreteCommandAjouterLivraison(client, id, noeudClicked);
		command.execute();
		this.invoker.addCommand(command);
		this.viewAccueil.MAJVueZoneGeographique();
		return 0;
	}
	/**Supprime une livraison
	 * @return int : eventuel code d'erreur
	 */
	public int SupprimerLivraison(Livraison l) {
		ConcreteCommandSupprimerLivraison command = new ConcreteCommandSupprimerLivraison(modelZoneGeographique , l);
		command.execute();
		//this.invoker.addCommand(command);
		this.viewAccueil.MAJVueZoneGeographique();
		return 0;
	}
	/**Annule la derni�re commande
	 * @return
	 */
	public int undo(){
		int retour = invoker.undo();
		this.viewAccueil.MAJVueZoneGeographique();
		return retour;
	}
	/**Refais la derni�re commande annule
	 * @return
	 */
	public int redo(){
		return this.invoker.redo();
	}
	/**Calcule l'itineraire
	 * @return int : eventuel code d'erreur
	 */
	public int CalculerItineraire () {
		return this.modelZoneGeographique.calculerItineraire();
	}
	
	/**Affiche les infos de la livraison cliquee
	 * @param n : Noeud clique
	 */
	public void infoLivraison(Noeud n){
		viewAccueil.MAJVueEtape(n);
		noeudClicked = n;
	}
	
	/**Renvoie le modele ZoneGeoGraphique
	 * @return ZoneGeographique
	 */
	public ZoneGeographique getModelZoneGeographique() {
		return this.modelZoneGeographique;
	}
	
	public String exporterTournee(){
		String texte = "Bonjour William\n";
		for(int i=2; i<101; i++){
			texte+="Ceci est la "+i+"eme ligne.\n";
		}
		texte+="Au revoir !";
		return texte;
	}
	
	public static void main(String[] args) throws ParseException{
		 	
		 Controleur controler = new Controleur();
		
		 
			
		}

	
	 


}
