package location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Classe qui s'occupe de la gestion des users.
 *
 * @author noah.brehin / simon.dumas / tom.Quemeneur
 */

public class GestionnaireUtilisateurs {
  /**
   * ensemble des utilisateurs.
   */
  private Set<Utilisateur> utilisateurs;
  /**
   * utilisateur en cours.
   */
  private Utilisateur utilisateur;
  
  /**
   * initialise. utilisateur est une collection qui contient tous les
   * utilisateurs.
   */
  
  
  public GestionnaireUtilisateurs() {
    
    this.utilisateurs = new HashSet<Utilisateur>();
    this.utilisateur = null;
    
  }
  
  /**
   * getteur.
   *
   * @return la collection comprenant tous les utilisateurs
   * 
   */
  public Set<Utilisateur> get_utilisateurs() {
    return this.utilisateurs;
  }
  
  /**
   * setteur utilisateur.
   *
   * @param s utilisateur a ajouter
   */
  public void set_utilisateurs(Set<Utilisateur> s) {
    this.utilisateurs = s;
  }
  
  /**
   * getteur utilisateur.
   *
   * @return retourne l'utilisateur cible
   */
  public Utilisateur get_utilisateur() {
    return this.utilisateur;
  }
  
  /**
   * setteur d'un utilisateur.
   *
   * @param s utilisateur concerné
   */
  public void set_utilisateur(Utilisateur s) {
    this.utilisateur = s;
  }
  
  
  /**
   * Inscription d'un utilisateur. Le pseudo choisi ne doit pas déjà exister
   * parmi les utilisateurs déjà inscrits.
   *
   * @param pseudo le pseudo (unique) de l'utilisateur
   * @param mdp le mot de passe de l'utilisateur (ne pas doit pas être vide ou
   *        <code>null</code>)
   * @param info les informations personnelles sur l'utilisateur
   * @return un code précisant le résultat de l'inscription : 0 si l'inscription
   *         s'est bien déroulée, 1 si le pseudo était déjà utilisé, 2 si le
   *         pseudo ou le mot de passe était vide, 3 si les informations
   *         personnelles ne sont pas bien précisées
   */
  public int inscription(String pseudo, String mdp,
      InformationPersonnelle info) {
    if (info.getAdresse() == "" || info.getNom() == ""
        || info.getPrenom() == "") {
      return 3;
    }
    if ((pseudo != null && mdp != null) && (pseudo != "" && mdp != "")) {
      Set<Utilisateur> s = this.get_utilisateurs();
      Iterator<Utilisateur> it = s.iterator();
      while (it.hasNext()) {
        Utilisateur u = it.next();
        if (u.get_pseudo().equals(pseudo)) {
          return 1;
        }
      }
      Utilisateur u = new Utilisateur(pseudo, mdp);
      u.set_infos(info);
      if (!s.contains(u)) {
        s.add(u);
        return 0;
      }
    }
    return 2;
    
    
  }
  
  /**
   * Connexion de l'utilisateur. Une fois connecté, l'utilisateur pourra accéder
   * aux services de location et déposer des commentaires sur les films qu'il a
   * loués.
   *
   * @param pseudo le pseudo de l'utilisateur
   * @param mdp le mot de passe de l'utilsateur
   * @return <code>true</code> si la connexion s'est bien déroulée,
   *         <code>false</code> en cas de couple pseudo/mot de passe invalide
   */
  public boolean connexion(String pseudo, String mdp) {
    Set<Utilisateur> s = this.get_utilisateurs();
    Iterator<Utilisateur> it = s.iterator();
    while (it.hasNext()) {
      Utilisateur u = it.next();
      if (pseudo == null || mdp == null) {
        return false;
      }
      if (u.get_pseudo().equals(pseudo) && u.get_mdp().equals(mdp)) {
        this.set_utilisateur(u);
        return true;
      }
    }
    return false;
  }
  
  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  public void deconnexion() throws NonConnecteException {
    if (this.get_utilisateur() != null) {
      this.set_utilisateur(null);
    } else {
      throw new NonConnecteException();
    }
  }
  
  /**
   * L'utilisateur connecté loue un film. Il peut le louer s'il a moins de 3
   * films en cours de location et s'il a l'âge suffisant pour voir le film.
   *
   * @param film le film à louer
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de refus de location, l'exception
   *         contiendra un message précisant le problème (déjà 3 films loués,
   *         âge insuffisant ou autre)
   */
  public void louerFilm(Film film)
      throws NonConnecteException, LocationException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      Utilisateur u = this.get_utilisateur();
      Set<Film> s = u.get_films();
      if (s == null) {
        s = new HashSet<Film>();
      }
      if (s.size() == 3) {
        throw new LocationException("Déjà 3 films loués");
      }
      if (film.get_age_min() > u.get_infos().getAge()) {
        throw new LocationException("âge insuffisant");
      }
      if (s.contains(film)) {
        throw new LocationException("Vous le louez déjà");
      }
      s.add(film);
      u.set_films(s);
      
    }
    
  }
  
  /**
   * Termine la location d'un film.
   *
   * @param film le film dont la location est terminée
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de problème, notamment si l'utilisateur
   *         n'avait pas ce film en location, l'exception contiendra un message
   *         précisant le problème
   */
  public void finLocationFilm(Film film)
      throws NonConnecteException, LocationException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      Utilisateur u = this.get_utilisateur();
      Set<Film> s = u.get_films();
      if (s.size() < 1) {
        throw new LocationException("Aucun film loué");
      }
      if (!s.contains(film)) {
        throw new LocationException("Le film donné n'est pas loué");
      }
      if (s.contains(film) && s.size() > 0) {
        s.remove(film);
      }
    }
  }
  
  /**
   * Information sur le fait qu'un film est ouvert à la location.
   *
   * @param film le film dont on veut vérifier la possibilité de location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> sinon
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  boolean estLouable(Film film) throws NonConnecteException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      return film.get_etat();
    }
  }
  
  /**
   * Renvoie l'ensemble des films actuellement en location par l'utilisateur
   * connecté.
   *
   * @return les films en location par l'utilisateur connecté ou
   *         <code>null</code> si aucun film actuellement en location
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  public Set<Film> filmsEnLocation() throws NonConnecteException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      Utilisateur u = this.get_utilisateur();
      Set<Film> s = u.get_films();
      if (s.size() == 0) {
        return null;
      }
      return s;
    }
  }
  
  /**
   * Ajoute à un film une évaluation de la part de l'utilisateur connecté.
   * L'utilisateur doit avoir loué le film pour le commenter (que le film soit
   * actuellement en sa location ou qu'il ait été loué puis retourné
   * préalablement). L'utilisateur ne doit pas déjà avoir déposé une évaluation
   * pour ce film.
   *
   * @param film le film à évaluer
   * @param eval l'évaluation du film
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException en cas d'erreur pour ajouter l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  public void ajouterEvaluation(Film film, Evaluation eval)
      throws NonConnecteException, LocationException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      Utilisateur u = this.get_utilisateur();
      Set<Film> s = u.get_films();
      if (film == null) {
        throw new LocationException("Le film donné n'est pas valide");
      }
      if (eval == null) {
        throw new LocationException("L'évaluation donnée n'est pas valide");
      }
      if (!s.contains(film)) {
        throw new LocationException("Le film donné n'est pas loué");
      }
      if (film != null && eval != null && s.contains(film)) {
        Set<Evaluation> e = film.get_evaluations();
        Iterator<Evaluation> it = e.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          Evaluation eva = it.next();
          if (eva == null) {
            return;
          }
          if (eva.user.equals(u)) {
            trouve = true;
            throw new LocationException("Vous avez déjà évalué ce film");
          }
        }
        if (!trouve) {
          e.add(eval);
        }
      }
    }
  }
  
  /**
   * Modifie l'évaluation que l'utilisateur connecté avait déjà déposée sur un
   * film. Ne peut se faire que si l'utilisateur avait déjà évalué le film.
   *
   * @param film le film dont l'utilisateur modifie l'évaluation
   * @param eval la nouvelle évaluation qui remplace la précédente
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException en cas d'erreur pour modifier l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  public void modifierEvaluation(Film film, Evaluation eval)
      throws NonConnecteException, LocationException {
    if (this.get_utilisateur() == null) {
      throw new NonConnecteException();
    } else {
      if (film != null) {
        if (eval != null) {
          Utilisateur u = this.get_utilisateur();
          Set<Evaluation> s = film.get_evaluations();
          Iterator<Evaluation> it = s.iterator();
          boolean trouve = false;
          while (it.hasNext()) {
            Evaluation e = it.next();
            if (e.user.equals(u)) {
              s.remove(e);
              s.add(eval);
              trouve = true;
            }
          }
          if (!trouve) {
            throw new LocationException("Vous n'avez pas évalué ce film");
          }
        } else {
          throw new LocationException("L'évaluation donnée est invalide");
        }
      } else {
        throw new LocationException("Le film donné est invalide");
      }
    }
  }
  
  
  
}

