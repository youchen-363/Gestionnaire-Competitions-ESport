package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.VueEquipe;
import ihm.VueListeEquipe;
import ihm.VueTournoi;
import modele.Equipe;

public class ControleurEquipe implements ActionListener {
	private VueEquipe vue;
	private Equipe modele;

	public ControleurEquipe(VueEquipe vue) {
		this.vue = vue;
		this.modele = new Equipe();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getText().equals("Sauvegarder")) {
			try {
				String name = this.vue.getNomEquipe().replace(" ", "");
				// Gestion des erreurs
				if (this.modele.nomEquipeIsVide(name)) {
					this.vue.getPopup().setErreur("Le nom de l'équipe ne peut pas être vide");
				}else if(this.modele.nomEquipeIsTropLong(name)){
					this.vue.getPopup().setErreur("Le nom de l'équipe comporte plus de 40 caractères");
				} else if(this.modele.nomEquipeIsDejaExistant(name)){
					this.vue.getPopup().setErreur("Une équipe portant ce nom existe déjà");
				// Sinon la modification est valide
				}else {
					this.modele.mettreAJourEquipe((new Equipe(this.vue.getIdEquipe(),this.vue.getNomEquipe(),this.vue.getRangEquipe(), this.vue.getPaysEquipe())));
					this.vue.dispose();
					if (this.vue.getPere() == null) {
						VueListeEquipe vue = new VueListeEquipe(this.modele.getToutesLesEquipes());
						vue.setVisible(true);
					} else {
						VueTournoi vue = new VueTournoi(this.vue.getPere());
						vue.setVisible(true);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (bouton.getText().equals("Retour")) {
			if (this.vue.getPere() == null) {
				VueListeEquipe vue = new VueListeEquipe(this.modele.getToutesLesEquipes());
				vue.setVisible(true);
			} else {
				VueTournoi vue = new VueTournoi(this.vue.getPere());
				vue.setVisible(true);
			}
			this.vue.dispose();
		}
				
	}
}
