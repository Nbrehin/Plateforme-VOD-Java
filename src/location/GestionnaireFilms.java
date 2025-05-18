package location;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import location.Artiste.Role;

/**
 * creation classe de gestion des données des films.
 *
 * @author noah.brehin / simon.dumas / tom.quemeneur
 */
public class GestionnaireFilms {
  /**
   * ensemble des films.
   */
  private Set<Film> films;
  
  /**
   * constructeur du gestionnaire.
   */
  public GestionnaireFilms() {
    this.films = new HashSet<Film>();
  }
  
  /**
   * getteur d'un film.
   *
   * @return retourne le film voulu
   */
  public Set<Film> get_films() {
    return this.films;
  }
  
  /**
   * setteur des films.
   *
   * @param films film ajouter
   */
  public void set_films(Set<Film> films) {
    this.films = films;
  }
  
  /**
   * Création d'un nouveau film. Il ne doit pas déjà exister un film avec le
   * même titre.
   *
   * @param titre le titre du film (chaine non vide)
   * @param realisateur le réalisateur du film (non <code>null</code>)
   * @param annee l'année de réalisation du film
   * @param ageLimite l'âge minimum pour pouvoir regarder le film (0 si pas
   *        d'âge limite)
   * @return le film créé ou <code>null</code> en cas de problème (il existe
   *         déjà un film au même titre ou des paramètres n'étaient pas valides)
   */
  public Film creerFilm(String titre, Artiste realisateur, int annee,
      int ageLimite) {
    if (titre != null && realisateur != null) {
      realisateur.set_afilm(true);
      Film f = new Film(titre, realisateur, annee, ageLimite);
      if (realisateur.get_role() == Role.ACTEUR) {
        realisateur.change_Role(Role.BOTH);
      }
      Iterator<Film> it = this.get_films().iterator();
      while (it.hasNext()) {
        Film film = it.next();
        if (film.get_titre().equals(titre)) {
          return null;
        }
      }
      if (!this.get_films().contains(f)) {
        Set<Film> s = this.get_films();
        s.add(f);
        this.set_films(s);
        return f;
      }
      return null;
    }
    return null;
  }
  
  /**
   * Ajoute des genres à un film.
   *
   * @param film le film à modifier
   * @param genres la liste des genres à ajouter. Si des genres de la liste sont
   *        déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un genre de la liste a été ajouté aux
   *         genres du film, <code>false</code> sinon
   */
  public boolean ajouterGenres(Film film, Genre... genres) {
    if (film == null) {
      return false;
    }
    Set<Genre> g = film.get_genres();
    boolean ajout = false;
    for (int i = 0; i < genres.length; i++) {
      if (!g.contains(genres[i])) {
        g.add(genres[i]);
        ajout = true;
      }
    }
    return ajout;
  }
  
  /**
   * Ajoute une affiche à un film. Si le film avait déjà une affiche, elle est
   * remplacée par la nouvelle.
   *
   * @param film le film auquel ajouter une affiche
   * @param file le chemin du fichier qui contient l'image de l'affiche (au
   *        format JPG, PNG ...)
   * @return <code>true</code> si l'affiche a été modifiée (le format et la
   *         taille étaient valides)
   * @throws IOException en cas d'erreur de lecture du fichier
   */
  public boolean ajouterAffiche(Film film, String file) throws IOException {
    if (film == null || file == null || file.isEmpty()) {
      return false; // Vérification des paramètres
    }
    
    try {
      // Charger l'image depuis le fichier
      File fichier = new File(file);
      BufferedImage image = ImageIO.read(fichier);
      
      if (image != null) {
        // Associer l'image au film
        film.set_img(file);
        return true;
      }
      return false; // Image invalide ou format non supporté
    } catch (IOException e) {
      throw e; // Propagation de l'exception si une erreur de lecture survient
    }
  }
  
  /**
   * Supprime un film de l'ensemble des films.
   *
   * @param film le film à supprimer
   * @return <code>true</code> si le film a été supprimé ou <code>false</code>
   *         en cas de problème (le film n'existait pas ou le paramètre était
   *         égal à <code>null</code>)
   */
  public boolean supprimerFilm(Film film) {
    if (film != null) {
      return this.films.remove(film);
    }
    return false;
  }
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films
   */
  public Set<Film> ensembleFilms() {
    return this.get_films();
  }
  
  /**
   * Renvoie l'ensemble des films d'un réalisateur.
   *
   * @param realisateur le réalisateur dont on veut les films
   * @return l'ensemble des films du réalisateur ou <code>null</code> s'il n'en
   *         existe pas
   */
  public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
    boolean ajout = false;
    if (realisateur != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        if (f.get_realisateur().equals(realisateur)) {
          s.add(f);
          ajout = true;
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que les paramètres étaient invalides
   */
  Set<Film> ensembleFilmsRealisateur(String nom, String prenom) {
    boolean ajout = false;
    if (nom != null && prenom != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        if (f.get_realisateur().get_Nom().equals(nom)
            && f.get_realisateur().get_Prenom().equals(prenom)) {
          s.add(f);
          ajout = true;
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un acteur.
   *
   * @param acteur l'acteur dont on veut les films
   * @return l'ensemble des films de l'acteur ou <code>null</code> s'il n'en
   *         existe pas
   */
  Set<Film> ensembleFilmsActeur(Artiste acteur) {
    boolean ajout = false;
    if (acteur != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        Set<Artiste> set = f.get_acteurs();
        Iterator<Artiste> i = set.iterator();
        while (i.hasNext()) {
          if (i.next().equals(acteur)) {
            s.add(f);
            ajout = true;
          }
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que les paramètres étaient invalides
   */
  Set<Film> ensembleFilmsActeur(String nom, String prenom) {
    boolean ajout = false;
    if (nom != null && prenom != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        Set<Artiste> set = f.get_acteurs();
        Iterator<Artiste> i = set.iterator();
        while (i.hasNext()) {
          if (i.next().get_Prenom().equals(prenom)
              && i.next().get_Nom().equals(nom)) {
            s.add(f);
            ajout = true;
          }
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé
   */
  Set<Film> ensembleFilmsGenre(Genre genre) {
    boolean ajout = false;
    if (genre != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        Set<Genre> set = f.get_genres();
        Iterator<Genre> i = set.iterator();
        while (i.hasNext()) {
          if (i.next().equals(genre)) {
            s.add(f);
            ajout = true;
          }
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film (doit correspondre à un élément de
   *        l'énumération {@link location.Genre Genre})
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le genre était invalide
   * @see location.Genre
   */
  Set<Film> ensembleFilmsGenre(String genre) {
    boolean ajout = false;
    if (genre != null) {
      Iterator<Film> it = this.get_films().iterator();
      Set<Film> s = new HashSet<Film>();
      while (it.hasNext()) {
        Film f = it.next();
        Set<Genre> set = f.get_genres();
        Iterator<Genre> i = set.iterator();
        while (i.hasNext()) {
          if (i.next().toString().equals(genre)) {
            s.add(f);
            ajout = true;
          }
        }
      }
      if (ajout) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param film le film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour que le film ou que le film était invalide
   *         (valeur <code>null</code> par exemple)
   */
  Set<Evaluation> ensembleEvaluationsFilm(Film film) {
    if (film != null) {
      Set<Evaluation> s = film.get_evaluations();
      if (!s.isEmpty()) {
        return s;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param titre le titre du film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour le film ou que le titre du film était
   *         inconnu ou invalide (valeur <code>null</code> par exemple)
   */
  Set<Evaluation> ensembleEvaluationsFilm(String titre) {
    if (titre != null) {
      Iterator<Film> it = this.get_films().iterator();
      while (it.hasNext()) {
        Film f = it.next();
        if (f.get_titre().equals(titre)) {
          Set<Evaluation> s = f.get_evaluations();
          if (!s.isEmpty()) {
            return s;
          }
        }
      }
    }
    return null;
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  Film getFilm(String titre) {
    if (titre != null) {
      Iterator<Film> it = this.get_films().iterator();
      while (it.hasNext()) {
        Film f = it.next();
        if (f.get_titre().equals(titre)) {
          return f;
        }
      }
    }
    return null;
  }
  
  /**
   * Ouvre un film à la location. Ne fait rien si le film était déjà ouvert à la
   * location.
   *
   * @param film le film à ouvrir à la location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  public boolean ouvrirLocation(Film film) {
    if (film != null) {
      film.set_etat(true);
      return true;
    }
    return false;
  }
  
  /**
   * Ferme la location d'un film. Ne fait rien si le film n'était pas ouvert à
   * la location.
   *
   * @param film le film dont il faut fermer la location
   * @return <code>true</code> si le film est fermé à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  public boolean fermerLocation(Film film) {
    if (film != null) {
      film.set_etat(false);
      return true;
    }
    return false;
  }
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param film le film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de film invalide (n'existant pas ou valeur
   *         <code>null</code>)
   */
  public double evaluationMoyenne(Film film) {
    if (film != null) {
      double somme = 0;
      double cpt = 0;
      Set<Evaluation> s = film.get_evaluations();
      Iterator<Evaluation> it = s.iterator();
      while (it.hasNext()) {
        Evaluation e = it.next();
        somme += e.note;
        cpt += 1;
      }
      if (cpt > 0) {
        return somme / cpt;
      }
      return -1;
    }
    return -2;
  }
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param titre le titre du film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de titre de film invalide (il n'existe pas de film
   *         avec ce titre ou valeur <code>null</code>)
   */
  double evaluationMoyenne(String titre) {
    if (titre != null) {
      double somme = 0;
      double cpt = 0;
      boolean trouve = false;
      Iterator<Film> i = this.get_films().iterator();
      Film f = null;
      while (i.hasNext() && !trouve) {
        f = i.next();
        if (f.get_titre().equals(titre)) {
          trouve = true;
        }
      }
      if (trouve) {
        Set<Evaluation> s = f.get_evaluations();
        Iterator<Evaluation> it = s.iterator();
        while (it.hasNext()) {
          Evaluation e = it.next();
          somme += e.note;
          cpt += 1;
        }
        if (cpt > 0) {
          return somme / cpt;
        }
        return -1;
      }
    }
    return -2;
  }
  
  /**
   * Ajoute des acteurs à un film.
   *
   * @param film le film à modifier
   * @param acteurs la liste des acteurs à ajouter. Si des acteurs de la liste
   *        sont déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un acteur de la liste a été ajouté
   *         aux acteurs du film, <code>false</code> sinon
   */
  boolean ajouterActeurs(Film film, Artiste... acteurs) {
    if (film != null) {
      Set<Artiste> s = film.get_acteurs();
      boolean ajout = false;
      for (int i = 0; i < acteurs.length; i++) {
        if (!s.contains(acteurs[i]) && acteurs[i] != null) {
          ajout = s.add(acteurs[i]);
          if (ajout) {
            acteurs[i].set_afilm(true);
          }
        }
      }
      film.set_acteurs(s);
      return ajout;
    }
    return false;
  }
}
