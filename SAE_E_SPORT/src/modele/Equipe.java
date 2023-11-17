package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Equipe {

	private int idEquipe;
	private String nom;
	private int rang;
	private Pays nationnalite;
	private List<Joueur> joueurs;
	
	public Equipe(int id, String nom, int rang, Pays nationnalite) {
		this.idEquipe = id;
		this.nom = nom;
		this.rang = rang;
		this.nationnalite = nationnalite;
		this.joueurs = new LinkedList<>();
	}

	public int getIdEquipe() {
		return this.idEquipe;
	}

	public String getNom() {
		return this.nom;
	}
	
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}

	public List<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public int getRang() {
		return this.rang;
	}

	public Pays getNationnalité() {
		return this.nationnalite;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Equipe) {
			Equipe e = (Equipe) o;
			return this.idEquipe == e.getIdEquipe();
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.idEquipe);
	}
	
}
