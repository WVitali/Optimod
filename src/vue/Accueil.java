package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import modele.Tournee;
import modele.ZoneGeographique;
import controleur.Controleur;
import bibliothequesTiers.ExampleFileFilter;



public class Accueil{
	private Controleur ctrl;
	private VueZoneGeo vueZoneGeo;
	private JFrame cadre;
	private JButton chargerLivraison = new JButton("Charger une demande de livraison");
	private JButton chargerPlan = new JButton("Charger un plan");
	private JButton calculerItineraire = new JButton("Calculer l'itin�raire");
	private JPanel south = new JPanel();
	private JPanel center = new JPanel();
	private JPanel west = new JPanel();
	private JPanel east = new JPanel();
	private JPanel north = new JPanel();
	int largeur = 1100;
	int hauteur = 600; 
	
	private JLabel message;
	private JPanel pPlan = new JPanel();
	
	public void message(String mess){
		message.setText(mess);
	}
	
	public Accueil(Controleur controleur){
		ctrl = controleur;
		cadre = new JFrame();
		cadre.setLayout(null);
		cadre.setSize(largeur, hauteur);
		cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cadre.setLocationRelativeTo(null);
		
		cadre.setLayout(new BorderLayout());
		cadre.getContentPane().add(south, BorderLayout.SOUTH);
		cadre.getContentPane().add(north, BorderLayout.NORTH);
		cadre.getContentPane().add(west, BorderLayout.WEST);
		cadre.getContentPane().add(east, BorderLayout.EAST);
		cadre.getContentPane().add(center, BorderLayout.CENTER);
		
		vueZoneGeo = new VueZoneGeo(1100,500);
		pPlan.setLayout(null);
		
		// Impossible d'appuyer sur ces boutons au d�part
		chargerLivraison.setEnabled(false);
		calculerItineraire.setEnabled(false);
		
		/*chargerLivraison.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chemin="";
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Charger une livraison");
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Fichier .xml", "xml", "XML"));
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					ExampleFileFilter filtre = new ExampleFileFilter("xml");
					if (filtre.accept(fc.getSelectedFile())) {
						chemin = fc.getSelectedFile().getAbsolutePath();
						chemin = chemin.replace("\\", "/");
						
						ctrl.chargerLivraison(chemin);
						MAJModele();
						
						Vector<String> livraisons = new Vector<String>();
						ddl = zoneGeo.getTournee();
						for(int i = 0 ; i<ddl.getPlages().size();i++){
							PlageHoraire ph = ddl.getPlages().get(i); 
							for(int j = 0 ; j<ph.getLivraisons().size() ; j++){
								Livraison l = ph.getLivraisons().get(j);
								(listModel).addElement("Livraison n�"+l.getId()+" chez "+l.getClient() + " � l'adresse "+l.getNoeud());
							}
						}
						//						geo.changerCouleur(ddl.getEntrepot());
						//						geo.repaint();
					} else {
						JOptionPane.showMessageDialog(cadre, "Format non pris en compte !", "Erreur !", JOptionPane.ERROR_MESSAGE);
					}
				}
				calculerItineraire.setEnabled(true);
			}
		});*/
		
		chargerPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chemin="";
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Charger un plan");
				FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers .xml", "xml", "XML");
				fc.addChoosableFileFilter(ff);
				fc.setFileFilter(ff);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					ExampleFileFilter filtre = new ExampleFileFilter("xml");
					if (filtre.accept(fc.getSelectedFile())) {
						chemin = fc.getSelectedFile().getAbsolutePath();
						chemin = chemin.replace("\\", "/");
						try {
							int err = ctrl.ChargerZoneGeo(chemin);
							if (err == -1) {
								String messageErreur = "Le fichier XML n'est pas valide !";
								afficherMessageErreur(messageErreur);
							} else if (err == 1) {
								String messageErreur = "Erreur de conversion dans le fichier XML: vitesse, longueur ou identifiant negatif";
								afficherMessageErreur(messageErreur);
							} else if (err == 2){
								String messageErreur = "Erreur dans le fichier XML !";
								afficherMessageErreur(messageErreur);
							} else {
							}
						} catch (IOException | JDOMException | ParserConfigurationException | SAXException | HeadlessException | NumberFormatException e) {
							String messageErreur = "Erreur dans le ficher XML: " + e.getMessage();
							afficherMessageErreur(messageErreur);
						}
					} else {
						String messageErreur = "Format non pris en compte !";
						afficherMessageErreur(messageErreur);
					}
				}		
			}
		});
		
		chargerLivraison.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   String chemin="";
		       JFileChooser fc = new JFileChooser();
		                int retval = fc.showOpenDialog(null);
		                if (retval == JFileChooser.APPROVE_OPTION) {
		                    chemin = fc.getSelectedFile().getAbsolutePath();
		                    chemin = chemin.replace("\\", "/");
		                    
		                }
		                ctrl.chargerLivraison(chemin);
	                    /*
		                ddl = zoneGeographique.getDemandes();
	            		for(int i = 0 ; i<ddl.getPlages().size();i++){
	            			PlageHoraire ph = ddl.getPlages().get(i); 
	            			for(int j = 0 ; j<ph.getLivraisons().size() ; j++){
	            				Livraison l = ph.getLivraisons().get(j);
	            				(listModel).addElement("Livraison n�"+l.getId()+" chez "+l.getClient() + " � l'adresse "+l.getNoeud());
	            			}
	            		}
	            		geo.changerCouleur(ddl.getEntrepot());
	            		geo.repaint();
	            		*/
			  }
		});
		

		//SOUTH ----------------------------
		GridLayout gl = new GridLayout();
		gl.setColumns(3);
		gl.setRows(1);
		gl.setHgap(10); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
		gl.setVgap(10); //Cinq pixels d'espace entre les lignes (V comme Vertical)
		south.setLayout(gl);
		
		
		south.add(calculerItineraire);
		south.add(chargerPlan);
		south.add(chargerLivraison);
		
		//NORTH ----------------------------
		north.setLayout(gl);
		message = new JLabel("Bonjour");
		message.setFont(new Font("Serif", Font.PLAIN, 16));
		
		north.add(message);
		
		//CENTER ----------------------------
//		GridLayout g2 = new GridLayout();
//		g2.setColumns(2);
//		g2.setRows(2);
//		g2.setHgap(10); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
//		g2.setVgap(10); //Cinq pixels d'espace entre les lignes (V comme Vertical)
//		center.setLayout(gl);

		//Liste des points de livraison
//		list = new JList(listModel); //data has type Object[]
//		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//		list.setVisibleRowCount(-1);
//		JScrollPane listScroller = new JScrollPane(list);
//		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		listScroller.setPreferredSize(new Dimension(250, 500));
//		pList.add(listScroller);
//		center.add(pPlan);
//		center.add(pList);
		center.setLayout(null);
		center.add(vueZoneGeo);
				

		cadre.setVisible(true);
		
	}

	
	public void creerVueZoneGeographique(int largeur, int hauteur, ZoneGeographique zoneGeographique) {
		center.remove(vueZoneGeo);
		this.vueZoneGeo = new VueZoneGeo(1100, 500, zoneGeographique, ctrl);
		center.add(vueZoneGeo);
		cadre.repaint();
		chargerLivraison.setEnabled(true);
		System.out.println(zoneGeographique.getNoeuds().size());
	}
	
	public void creerVueTournee (Tournee tournee) {
		this.vueZoneGeo.creerVueTournee();
	}
	
	public void afficherMessageErreur(String erreur){
		JOptionPane.showMessageDialog(cadre, erreur, "Erreur !", JOptionPane.ERROR_MESSAGE);
	}

	public void MAJVueZoneGeographique() {
		vueZoneGeo.MAJVueZoneGeographique();
		
	}



	
	

}
