package controleur;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Fonctions.LireCSV;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.ParticiperJDBC;
import ihm.VueImportation;
import modele.Equipe;
import modele.Joueur;
import modele.Participer;
import modele.Pays;

public class ControleurImportation implements ActionListener{
	
	private VueImportation vue;
	private List<String[]> data = null;

	public ControleurImportation(VueImportation vue) {
		this.vue = vue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    if (bouton.getText().equals("Importer")) {
	        try {
	            JFileChooser fc = new JFileChooser();
	            // Affichage de la boite de séléction de fichier
	            if (fc.showOpenDialog(this.vue) == JFileChooser.APPROVE_OPTION) {
	            	// Récupération du chemin absolu vers le fichier
	            	String chemin = fc.getSelectedFile().getAbsolutePath();
	 	            LireCSV read = new LireCSV(chemin);
		            this.data = read.getData();

		            // Récupération modèle de la JTable
		            DefaultTableModel model = (DefaultTableModel) this.vue.getModel();
		            JTable table = (JTable) this.vue.getTable();
		            
		            // Efface les lignes et colonnes
		            model.setRowCount(0);
		            model.setColumnCount(0);

		            // Ajout des colonnes au modèle
		            ajouterColonnes(model);
		            
		            // Récupération des noms des équipes
		            Object[] titreColonne = recupNomEquipes(); 
		            
		            // Ajout du nom des équipes
		            model.addRow(titreColonne);
		            
		            // changer la couleur de fond de la première ligne
		            changerBackgroundColorTableau(table);
		            
		            // Récupération des joueurs dans une liste
		            List<String> joueurs = recupJoueurListe(); 
		            
		            // Récupération et affichage des joueurs par lignes 
		            affichageListeJoueurTableau(model, joueurs);
		            
		            // Mise à jour de l'affichage de la vue
		            vue.setVisible(true);
	             }
	        	
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }
	    if (bouton.getText().equals("Valider") && this.data != null) {
	    	boolean rollback = false;
	    	try {
				ConnectionJDBC.getConnection().setAutoCommit(false);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
	    	EquipeJDBC equipeDB = new EquipeJDBC();
	    	JoueurJDBC joueurDB = new JoueurJDBC();
	    	ParticiperJDBC participerDB = new ParticiperJDBC();
	    	
	    	
	    	for (int ligne = 1; ligne<this.data.size(); ligne += 5) {
	    		try {
	    			String messageErreur = "";
	    			System.out.println(ligne);
	    			Equipe equipe = equipeDB.getByNom(this.data.get(ligne)[4]).orElse(null);
	    			if (equipe == null) {
	    				System.out.println("Equipe pas presente");
	    				// Creer l'equipe et lui attribuer les joueurs
		    			equipe = new Equipe(EquipeJDBC.getNextValueSequence(), this.data.get(ligne)[4], Integer.parseInt(this.data.get(ligne)[5]), Pays.getPays(data.get(ligne)[6]));
		    			for (int j = ligne; j<ligne+5; j++) {
		    				Joueur joueur = new Joueur(JoueurJDBC.getNextValueSequence(), this.data.get(j)[7], equipe);
		    				if (!joueur.presentDansAutreEquipe()) {
		    					equipe.ajouterJoueur(joueur);
		    				} else {
		    					rollback = true;
		    				}
		    			}
		    			if (!rollback) {
		    				equipeDB.add(equipe);
		    			}
		    			System.out.println("--- Equipe ajoutee ---\n" + equipe);
	    			}
	    			System.out.println("Equipe presente");
	    			if (rollback) {
	    				ConnectionJDBC.getConnection().rollback();
	    				this.vue.setColorMessage(new Color(255, 204, 204));
	    				this.vue.setMsgErreur("Un ou plusieurs joueurs appartiennent à plus d'une équipe");
	    			} else {
	    				ConnectionJDBC.getConnection().commit();
	    				this.vue.setColorMessage(new Color(204, 255, 204));
	    				this.vue.setMsgErreur("Les équipes ont bien été importées");
	    				Participer participer = new Participer(equipe, vue.getTournoi());
						participerDB.add(participer);
	    			}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	    	}
	    }
	    if (bouton.getText().equals("Retour")) {
	    	this.vue.dispose();
	    }
	}
}
