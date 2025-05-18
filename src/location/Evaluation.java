
package location;

/**
 * Création de la classe d'évaluation.
 *
 * @author Tom Quemeneur
 */

public class Evaluation {
  
  /**
   * auteur de la note / commentaire.
   */
  
  public String auteur;
  
  /**
   * utilisateur, créateur de l'évaluation.
   */
  
  Utilisateur user;
  
  /**
   * note du film.
   */
  
  public int note;
  
  /**
   * commentaire crée sous l'abréviation com.
   */
  
  public String com;
  
  /**
   * nom du film associé a la note.
   */
  public String titrefilm;
  
  /**
   * film associé à la note.
   */
  public Film film;
  
  /**
   * Constructueur de la classe evaluation créé la note a partir du film et de
   * l'utilisateur.
   *
   * @param note la note
   * @param com contenu du commentaire
   * @param user récupération du pseudo de l'auteur
   * @param film film concerné par l'évaluation
   */
  public Evaluation(int note, String com, Utilisateur user, Film film) {
    this.user = user;
    this.auteur = user.get_pseudo();
    this.note = note;
    this.com = com;
    this.titrefilm = film.get_titre();
  }
  
  /**
   * Constructueur de la classe evaluation créé la note a partir du film et de
   * l'utilisateur.
   *
   * @param note la note
   * @param user auteur de l'evaluation
   * @param film récupération du pseudo de l'auteur
   * 
   */
  public Evaluation(int note, Utilisateur user, Film film) {
    this.auteur = user.get_pseudo();
    this.note = note;
    this.com = null;
    this.titrefilm = film.get_titre();
  }
  
  /**
   * retourne la note d'un film donné par son titre.
   *
   * @return note
   */
  public int get_note() {
    return this.note;
  }
  
  /**
   * Fournit le commentaire sur un film d'un utilisateur.
   *
   * @return motdepasse
   */
  public String get_com() {
    return this.com;
  }
  
  /**
   * Fournit l'auteur de l'évaluation concerné.
   *
   * @return l'auteur de l'evaluation
   */
  public String get_auteur() {
    return this.auteur;
  }
  
  /**
   * Setter de la note.
   *
   * @param n note a ajouter
   */
  public void set_note(int n) {
    this.note = n;
  }
  
  /**
   * Setter du commentaire.
   *
   * @param com commentaire a ajouter
   */
  public void set_com(String com) {
    this.com = com;
  }
  
  /**
   * Setter de l'auteur.
   *
   * @param u utilisateur auteur de l'evaluation
   */
  public void set_auteur(Utilisateur u) {
    this.auteur = u.get_pseudo();
  }
  
  /**
   * Redéfinition de la méthode toString pour afficher les informations de
   * l'évaluation.
   *
   * @return une chaîne représentant les informations de l'évaluation.
   */
  @Override
  
  public String toString() {
    
    
    return (this.auteur + ", " + this.com + ", " + this.note + ", "
        + this.titrefilm);
    
  }
  
  
}
