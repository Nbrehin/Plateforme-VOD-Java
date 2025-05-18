package location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import location.Artiste.Role;

/**
 * creation classe de gestion des données des artistes.
 *
 * @author noah.brehin / simon.dumas / tom.quemeneur
 */

public class GestionnaireArtistes {
  
  /**
   * ensembles des artistes.
   */
  private Set<Artiste> artistes;
  
  /**
   * constructeur du gestionnaire des artistes.
   */
  public GestionnaireArtistes() {
    this.artistes = new HashSet<Artiste>();
  }
  
  /**
   * getteur des artistes.
   *
   * @return retourne l'artiste concerné
   */
  public Set<Artiste> get_artiste() {
    return this.artistes;
  }
  
  /**
   * setteur artistes.
   *
   * @param artiste artiste concerné
   */
  public void set_artiste(Set<Artiste> artiste) {
    this.artistes = artiste;
  }
  
  /**
   * Création d'un nouvel artiste. Il ne doit pas déjà exister un artiste avec
   * le même nom et le même prénom.
   *
   * @param nom le nom de l'artiste (chaine non vide)
   * @param prenom le prénom de l'artiste (chaine vide "" si l'artiste n'a qu'un
   *        nom et pas de prénom)
   * @param nationalite la nationalité de l'artiste
   * @return l'artiste créé ou <code>null</code> en cas d'erreur (les paramètres
   *         sont invalides ou il existe déjà un artiste avec les mêmes valeurs)
   */
  public Artiste creerArtiste(String nom, String prenom, String nationalite) {
    if (nom != null && prenom != null && nationalite != null) {
      Artiste a = new Artiste(nom, prenom, nationalite);
      Iterator<Artiste> it = this.get_artiste().iterator();
      while (it.hasNext()) {
        Artiste artiste = it.next();
        if (artiste.get_Nom().equals(nom)
            && artiste.get_Prenom().equals(prenom)) {
          return null;
        }
      }
      if (!this.get_artiste().contains(a)) {
        this.get_artiste().add(a);
        return a;
      }
      return null;
    }
    return null;
    
  }
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs ou <code>null</code> si aucun acteur
   *         n'existe
   */
  public Set<Artiste> ensembleActeurs() {
    Set<Artiste> acteur = new HashSet<Artiste>();
    Iterator<Artiste> it = this.get_artiste().iterator();
    while (it.hasNext()) {
      Artiste a = it.next();
      if ((a.get_role() == Role.ACTEUR || a.get_role() == Role.BOTH)) {
        acteur.add(a);
      }
    }
    return acteur;
  }
  
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs ou <code>null</code> si aucun
   *         réalisateur n'existe
   */
  public Set<Artiste> ensembleRealisateurs() {
    Set<Artiste> real = new HashSet<Artiste>();
    Iterator<Artiste> it = this.get_artiste().iterator();
    while (it.hasNext()) {
      Artiste a = it.next();
      if (a.get_role() == Role.REALISATEUR || a.get_role() == Role.BOTH) {
        real.add(a);
      }
    }
    return real;
  }
  
  /**
   * Renvoie l'ensemble des artistess.
   *
   * @return l'ensemble des artistess ou <code>null</code> si aucun artiste
   *         n'existe
   */
  
  public Set<Artiste> ensembleArtiste() {
    return this.get_artiste();
  }
  
  /**
   * Cherche un acteur à partir de son nom et son prénom.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'acteur s'il a été trouvé ou <code>null</code> sinon
   */
  public Artiste getActeur(String nom, String prenom) {
    if (nom != null && prenom != null) {
      Iterator<Artiste> it = this.get_artiste().iterator();
      while (it.hasNext()) {
        Artiste a = it.next();
        if (a.get_Nom().equals(nom) && a.get_Prenom().equals(prenom)
            && (a.get_role() == Role.ACTEUR || a.get_role() == Role.BOTH)) {
          return a;
        }
      }
    }
    return null;
  }
  
  /**
   * Cherche un réalisateur à partir de son nom et son prénom.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return le réalisateur s'il a été trouvé ou <code>null</code> sinon
   */
  public Artiste getRealisateur(String nom, String prenom) {
    if (nom != null && prenom != null) {
      Iterator<Artiste> it = this.get_artiste().iterator();
      while (it.hasNext()) {
        Artiste a = it.next();
        if (a.get_Nom().equals(nom) && a.get_Prenom().equals(prenom)
            && (a.get_role() == Role.REALISATEUR
                || a.get_role() == Role.BOTH)) {
          return a;
        }
      }
    }
    return null;
  }
  
  /**
   * Cherche un artiste à partir de son nom et son prénom.
   *
   * @param nom le nom de l'artiste
   * @param prenom le prénom de l'artiste
   * @return l'artiste s'il a été trouvé ou <code>null</code> sinon
   */
  public Artiste getArtiste(String nom, String prenom) {
    if (nom != null && prenom != null) {
      Iterator<Artiste> it = this.get_artiste().iterator();
      while (it.hasNext()) {
        Artiste a = it.next();
        if (a.get_Nom().equals(nom) && a.get_Prenom().equals(prenom)) {
          return a;
        }
      }
    }
    return null;
    
  }
  
  /**
   * Supprime un artiste de l'ensemble des artistes. Ne peut pas être réalisé si
   * l'artiste est associé à au moins un film en tant qu'acteur ou réalisateur.
   *
   * @param artiste l'artiste à supprimer
   * @return <code>true</code> si la suppression a été réalisée,
   *         <code>false</code> sinon
   */
  public boolean supprimerArtiste(Artiste artiste) {
    if (artiste != null && !artiste.get_afilm()) {
      Iterator<Artiste> it = this.get_artiste().iterator();
      while (it.hasNext()) {
        Artiste a = it.next();
        if (a.get_Nom().equals(artiste.get_Nom())
            && a.get_Prenom().equals(artiste.get_Prenom())) {
          artistes.remove(a);
          return true;
        }
      }
    }
    return false;
  }
  
  
}
