package location;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe Film String titre : tittre unique du film int annee : annee de
 * rélalisation du film Artiste realisateur : realisateur du film acteurs :
 * liste des acteurs du film genres : liste des genres du film int age_min : age
 * minimum requis pour reserver le film String img : image d'affiche du film
 * evaluations : liste des évaluations du film boolean etat : true=film ouvert à
 * la réservation false=film fermé à la reservation.
 *
 * @author noah.brehin / simon.dumas / tom.quemeneur
 */
public class Film {
  /**
   * titre du film.
   */
  private String titre;
  /**
   * anne de sortie du film.
   */
  private int annee;
  /**
   * realisateur du film.
   */
  private Artiste realisateur;
  /**
   * acteurs du film.
   */
  private Set<Artiste> acteurs;
  /**
   * genres du film.
   */
  private Set<Genre> genres;
  /**
   * age minimum pour voir le film.
   */
  private int ageMin;
  /**
   * affiche du film.
   */
  private String img;
  /**
   * Evaluations du film.
   */
  private Set<Evaluation> evaluations;
  /**
   * etat du film.
   */
  private boolean etat;
  
  /**
   * Constructeur de la classe Film.
   *
   * @param titre titre du film
   * @param annee annee du film
   * @param realisateur realisateur du film
   * @param ageMin age minimum requis pour réserver le film
   */
  public Film(String titre, Artiste realisateur, int annee, int ageMin) {
    this.titre = titre;
    this.annee = annee;
    this.realisateur = realisateur;
    this.acteurs = new HashSet<Artiste>();
    this.genres = new HashSet<Genre>();
    this.ageMin = ageMin;
    this.evaluations = new HashSet<Evaluation>();
    this.etat = false;
  }
  
  /**
   * Constructeur de la classe Film.
   *
   * @param titre titre du film
   * @param annee annee du film
   * @param realisateur realisateur du film
   */
  public Film(String titre, Artiste realisateur, int annee) {
    this.titre = titre;
    this.annee = annee;
    this.realisateur = realisateur;
    this.acteurs = new HashSet<Artiste>();
    this.genres = new HashSet<Genre>();
    this.ageMin = 0;
    this.evaluations = new HashSet<Evaluation>();
    this.etat = false;
  }
  
  /**
   * retourne le titre du film.
   *
   * @return titre du film
   */
  public String get_titre() {
    return this.titre;
  }
  
  /**
   * set le titre du film avec la chaîne de caractère fournie en paramètre.
   *
   * @param titre du film
   */
  
  public void set_titre(String titre) {
    this.titre = titre;
  }
  
  /**
   * retourne l'annee du film.
   *
   * @return un int correspondant à l'année de parution de l'année.
   */
  public int get_annee() {
    return this.annee;
  }
  
  /**
   * set l'annee de parution du film avec la valeur int donnée en paramètre.
   *
   * @param annee int qui correspond à l'année
   */
  
  public void set_annee(int annee) {
    this.annee = annee;
  }
  
  /**
   * retourne le réalisateur du film.
   *
   * @return une chaîne de caractère
   */
  
  public Artiste get_realisateur() {
    return this.realisateur;
  }
  
  /**
   * set le réalisateur avec l'object artiste passé en paramètre.
   *
   * @param realisateur un object de type artiste
   */
  
  public void set_realisateur(Artiste realisateur) {
    this.realisateur = realisateur;
  }
  
  /**
   * retourne la collection d'artistes (ici les acteurs) du film.
   *
   * @return une collection d'artistes
   */
  
  public Set<Artiste> get_acteurs() {
    return this.acteurs;
  }
  
  /**
   * définie la collection d'artiste qui correspond aux acteurs.
   *
   * @param acteurs collection d'acteurs passée en paramètre
   */
  
  public void set_acteurs(Set<Artiste> acteurs) {
    this.acteurs = acteurs;
  }
  
  /**
   * retourne la collection de genre du film.
   *
   * @return une collection de genre
   */
  
  public Set<Genre> get_genres() {
    return this.genres;
  }
  
  /**
   * définie la collection de genres correspondants au film.
   *
   * @param genres une collection de genre
   */
  public void set_genres(Set<Genre> genres) {
    this.genres = genres;
  }
  
  /**
   * retourne l'age minimum nécessaire pour le visionnage du film.
   *
   * @return un int
   */
  
  public int get_age_min() {
    return this.ageMin;
  }
  
  /**
   * définie l'age minimum nécessaire pour le visionnage du film.
   *
   * @param ageMin int correspondant à l'age
   */
  
  public void set_age_min(int ageMin) {
    this.ageMin = ageMin;
  }
  
  /**
   * retourne la chaîne correspondante au chemin relatif de l'image.
   *
   * @return une chaine de caractère
   */
  public String get_img() {
    return this.img;
  }
  
  /**
   * définie le chemin relatif de l'image.
   *
   * @param img une chaîne de caractère
   */
  public void set_img(String img) {
    this.img = img;
  }
  
  /**
   * retourne la collection contentant toutes les evaluations du film.
   *
   * @return une collection d'évaluation
   */
  
  public Set<Evaluation> get_evaluations() {
    return this.evaluations;
  }
  
  /**
   * définie la collection d'évaluations du film.
   *
   * @param evaluations une collection d'évaluation
   */
  public void set_evaluations(Set<Evaluation> evaluations) {
    this.evaluations = evaluations;
  }
  
  /**
   * retourne l'état du film.
   *
   * @return un boolean
   */
  
  public boolean get_etat() {
    return this.etat;
  }
  
  /**
   * définie l'état du film.
   *
   * @param etat un boolean
   */
  
  public void set_etat(boolean etat) {
    this.etat = etat;
  }
  
  /**
   * Redéfinition de la méthode toString pour afficher les informations du film.
   *
   * @return une chaîne représentant les informations du film.
   */
  @Override
  
  public String toString() {
    
     
    return (this.titre + ", "
        + this.realisateur.get_Nom() + ", " + this.realisateur.get_Prenom()
        + ", " + this.annee + ", " + this.ageMin);
  
  }
  
}
