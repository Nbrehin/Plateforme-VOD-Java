package location;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente un Utilisateur.
 *
 *
 * @author tom.quemeneur
 */
public class Utilisateur {
  
  // ******************************* ATTRIBUTS
  
  /**
   * pseudo de l'utilisateur.
   */
  public String pseudo;
  
  /**
   * mdp de l'utilisateur.
   */
  private String motdepasse;
  
  /**
   * infos de l'utilisateur.
   */
  private InformationPersonnelle infos;
  
  /**
   * ensemble des films loués par l'utilisateur.
   */
  private Set<Film> films;
  
  // ******************************* CONSTRUCTEUR
  /**
   * Constructeur d'Utilisateur (initialement sans réservation).
   *
   * @param pseudo (chaine de caractere)
   * @param mdp (chaine de caractere)
   */
  public Utilisateur(String pseudo, String mdp) {
    this.pseudo = pseudo;
    this.motdepasse = mdp;
    this.films = new HashSet<Film>();
  }
  // ******************************* METHODES
  
  /**
   * Fournit le Pseudo de l'utilisateur.
   *
   * @return Pseudo
   */
  public String get_pseudo() {
    return this.pseudo;
  }
  
  /**
   * setteur du pseudo.
   *
   * @param pseudo a ajouter
   */
  public void set_pseudo(String pseudo) {
    this.pseudo = pseudo;
  }
  
  /**
   * Fournit le mot de passe de l'utilisateur.
   *
   * @return motdepasse
   */
  public String get_mdp() {
    return this.motdepasse;
  }
  
  /**
   * définie le mdp du compte.
   *
   * @param mdp chaine de caractère correspondante au mot de passe
   */
  public void set_mdp(String mdp) {
    this.motdepasse = mdp;
  }
  
  /**
   * retourne les infos personnelles de l'utilisateur.
   *
   * @return les infos, de type InformationPersonnelle
   */
  
  public InformationPersonnelle get_infos() {
    return this.infos;
  }
  
  /**
   * définie les infos personnelles de l'utilisateur.
   *
   * @param infos type : InformationPersonnelle
   */
  
  public void set_infos(InformationPersonnelle infos) {
    this.infos = infos;
  }
  
  /**
   * retourne la collection des films loués par l'utilisateur.
   *
   * @return une collection de film
   */
  
  public Set<Film> get_films() {
    
    return this.films;
  }
  
  /**
   * définie une collection de film loués par l'utilisateur.
   *
   * @param films collection de film
   */
  
  public void set_films(Set<Film> films) {
    
    this.films = films;
  }
}
