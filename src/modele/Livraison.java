package modele;

public class Livraison {

	private int id;
	private int noeud;
	private int client;
	private String etat;
	private PlageHoraire horaire;
	
	public Livraison(int id, int idClient, int idNoeud, PlageHoraire pl) {
		this.id = id;
		client = idClient;
		noeud = idNoeud;
		etat="en attente";
	}
	
	public Livraison(int idClient, int idNoeud) {
		client = idClient;
		noeud = idNoeud;
		etat="en attente";
	}

	public int getId() {
		return id;
	}

	public int getNoeud() {
		return noeud;
	}

	public int getClient() {
		return client;
	}

	public String getEtat() {
		return etat;
	}
	
	public String display(){
		return "Livraison chez " + client + " entre "+horaire.getHeureDebut() + " et " + horaire.getHeureFin();
	}
	
	
}
