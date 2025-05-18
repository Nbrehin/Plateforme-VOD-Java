package location;

/**
 * Classe Artiste String nom : nom de l'artiste String prenom : prenom de
 * l'artiste String nationalite : nationalité de l'artiste.
 *
 * @author noah.brehin / simon.dumas / tom.quemeneur
 */


public class Artiste {
  
  /**
   * enumerage des options de rôle d'un artiste. type énuméré qui représente le
   * rôle de l'artiste.
   * 
   *
   */
  
  public enum Role {
    /**
     * artiste réalisateur de film.
     */
    REALISATEUR,
    /**
     * artiste acteur.
     */
    ACTEUR,
    /**
     * artiste ayant les 2 rôle précedent.
     */
    BOTH;
    
  }
  
  /**
   * nom de l'artiste.
   */
  private String nom;
  /**
   * prenom de l'artiste.
   */
  private String prenom;
  /**
   * nationalité de l'artiste.
   */
  private String nationalite;
  /**
   * role de l'artiste.
   */
  private Role role;
  /**
   * boolean représentant si l'artiste est associé à un film.
   */
  private boolean afilm;
  
  
  /**
   * decalaration classe artiste.
   *
   * @param nom de l'artiste
   * @param prenom prenom de l'artiste
   * @param nationalite de l'artiste
   */
  
  
  public Artiste(String nom, String prenom, String nationalite) {
    
    this.nom = nom;
    this.prenom = prenom;
    this.nationalite = nationalite;
    this.role = Role.ACTEUR;
    this.afilm = false;
    
    
  }
  
  /**
   * getteur de la nationalité de l'artiste.
   *
   * @return la nationalité de l'artiste
   */
  public String get_Nationalite() {
    
    return this.nationalite;
  }
  
  /**
   * setteur de la nationalité de l'artiste.
   *
   * @param nationalite set la nationalité
   */
  public void set_Nationalite(String nationalite) {
    this.nationalite = nationalite;
  }
  
  
  
  /**
   * getteur du nom de l'artiste.
   *
   * @return le nom de l'artiste
   */
  public String get_Nom() {
    
    return this.nom;
    
    
  }
  
  /**
   * setteur de la nom de l'artiste.
   *
   * @param nom set le nom
   */
  public void set_Nom(String nom) {
    this.nom = nom;
  }
  
  
  /**
   * getteur du prenom l'Artiste.
   *
   * @return le prenom de l'artiste
   */
  public String get_Prenom() {
    
    return this.prenom;
    
  }
  
  /**
   * setteur de la prenom de l'artiste.
   *
   * @param prenom set le prenom
   */
  public void set_preNom(String prenom) {
    this.prenom = prenom;
  }
  
  /**
   * getteur de la role de l'artiste.
   *
   * @return retourne le role de l'artiste
   */
  public Role get_role() {
    return this.role;
  }
  
  /**
   * setteur du boolean représentant si l'artiste est lié à un film ou non.
   *
   * @param afilm set le boolean représentant si l'artiste est lié à un film ou
   *        non.
   */
  public void set_afilm(boolean afilm) {
    this.afilm = afilm;
  }
  
  /**
   * getteur du boolean représentant si l'artiste est lié à un film ou non.
   *
   * @return retourne le boolean représentant si l'artiste est lié à un film ou
   *         non.
   */
  public boolean get_afilm() {
    return this.afilm;
  }
  
  /**
   * Change le rôle de l'artiste en fonction de la chaîne que je rentre en
   * paramètre.
   *
   * @param role prend un role en parametre
   * @return un boolean indiquant si la modification du rôle de l'artiste s'est
   *         bien passée.
   */
  
  public boolean change_Role(Role role) {
    if (role == null) {
      return false;
    }
    if (role.equals(Role.ACTEUR)) {
      
      this.role = Role.ACTEUR;
      return true;
      
    } else if (role.equals(Role.REALISATEUR)) {
      
      this.role = Role.REALISATEUR;
      return true;
      
    } else if (role.equals(Role.BOTH)) {
      
      this.role = Role.BOTH;
      return true;
      
    } else {
      
      return false;
    }
    
  }
  
  /**
   * Redéfinition de la méthode toString pour afficher les informations de
   * l'artiste.
   *
   * @return une chaîne représentant l'identité de l'artiste.
   */
  @Override
  
  public String toString() {
    
    return (this.nom + ", " + this.prenom + ", "
        + this.nationalite + ", " + this.role);
    
  }
  
  
  
}


