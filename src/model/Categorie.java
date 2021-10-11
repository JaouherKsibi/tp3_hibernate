package model;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Categorie {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_categorie;
	@Override
	public String toString() {
		return "Categorie [id_categorie=" + id_categorie + ", nom=" + nom +  "]";
	}
	private String nom;
	
	public Categorie() {
	}
	public Categorie(String nom) {
		super();
		this.nom = nom;
	}
	public Categorie(int id_categorie, String nom) {
		super();
		this.id_categorie = id_categorie;
		this.nom = nom;
	}
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
