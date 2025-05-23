package location;

import java.io.IOException;
import java.util.Set;

/**
 * Création de la classe Backend.
 *
 * @author simon.dumas | noah.brehin | tom.quemeneur
 */

public class Backend implements InterAdministration {
  /**
   * gestionnaire des films.
   */
  public GestionnaireFilms gestionnaireFilms;
  
  /**
   * gestionnaire des artistes.
   */
  public GestionnaireArtistes gestionnaireArtistes;
  
  /**
   * constructeur de backend.
   *
   * @param f gestionnaire de tous les films
   * @param a gestionnaire de tous les artistes
   * @param u gestionnaire de tous les utilisateurs
   */
  
  public Backend(GestionnaireFilms f, GestionnaireArtistes a,
      GestionnaireUtilisateurs u) {
    this.gestionnaireFilms = f;
    this.gestionnaireArtistes = a;
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
  @Override
  public Artiste creerArtiste(String nom, String prenom, String nationalite) {
    // TODO Auto-generated method stub
    return gestionnaireArtistes.creerArtiste(nom, prenom, nationalite);
  }
  
  /**
   * Supprime un artiste de l'ensemble des artistes. Ne peut pas être réalisé si
   * l'artiste est associé à au moins un film en tant qu'acteur ou réalisateur.
   *
   * @param artiste l'artiste à supprimer
   * @return <code>true</code> si la suppression a été réalisée,
   *         <code>false</code> sinon
   */
  @Override
  public boolean supprimerArtiste(Artiste artiste) {
    // TODO Auto-generated method stub
    return gestionnaireArtistes.supprimerArtiste(artiste);
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
  @Override
  public Film creerFilm(String titre, Artiste realisateur, int annee,
      int ageLimite) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.creerFilm(titre, realisateur, annee, ageLimite);
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
  @Override
  public boolean ajouterActeurs(Film film, Artiste... acteurs) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ajouterActeurs(film, acteurs);
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
  @Override
  public boolean ajouterGenres(Film film, Genre... genres) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ajouterGenres(film, genres);
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
  @Override
  public boolean ajouterAffiche(Film film, String file) throws IOException {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ajouterAffiche(film, file);
  }
  
  /**
   * Supprime un film de l'ensemble des films.
   *
   * @param film le film à supprimer
   * @return <code>true</code> si le film a été supprimé ou <code>false</code>
   *         en cas de problème (le film n'existait pas ou le paramètre était
   *         égal à <code>null</code>)
   */
  @Override
  public boolean supprimerFilm(Film film) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.supprimerFilm(film);
  }
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films
   */
  @Override
  public Set<Film> ensembleFilms() {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ensembleFilms();
  }
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs
   */
  @Override
  public Set<Artiste> ensembleActeurs() {
    // TODO Auto-generated method stub
    return gestionnaireArtistes.ensembleActeurs();
  }
  
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs
   */
  @Override
  public Set<Artiste> ensembleRealisateurs() {
    // TODO Auto-generated method stub
    return gestionnaireArtistes.ensembleRealisateurs();
  }
  
  /**
   * Renvoie l'ensemble des films d'un réalisateur.
   *
   * @param realisateur le réalisateur dont on veut les films
   * @return l'ensemble des films du réalisateur ou <code>null</code> s'il n'en
   *         existe pas
   */
  @Override
  public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ensembleFilmsRealisateur(realisateur);
  }
  
  /**
   * Renvoie l'ensemble des films d'un acteur.
   *
   * @param acteur l'acteur dont on veut les films
   * @return l'ensemble des films de l'acteur ou <code>null</code> s'il n'en
   *         existe pas
   */
  @Override
  public Set<Film> ensembleFilmsActeur(Artiste acteur) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ensembleFilmsActeur(acteur);
  }
  
  /**
   * Cherche un artiste à partir de son nom et son prénom.
   *
   * @param nom le nom de l'artiste
   * @param prenom le prénom de l'artiste
   * @return l'artiste s'il a été trouvé ou <code>null</code> sinon
   */
  @Override
  public Artiste getArtiste(String nom, String prenom) {
    // TODO Auto-generated method stub
    return gestionnaireArtistes.getArtiste(nom, prenom);
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  @Override
  public Film getFilm(String titre) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.getFilm(titre);
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
  @Override
  public boolean ouvrirLocation(Film film) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.ouvrirLocation(film);
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
  @Override
  public boolean fermerLocation(Film film) {
    // TODO Auto-generated method stub
    return gestionnaireFilms.fermerLocation(film);
  }
}
