package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Iterator;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.GestionnaireUtilisateurs;
import location.InformationPersonnelle;
import location.LocationException;
import location.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class TestGestionnaireUtilisateur {
  private GestionnaireUtilisateurs gestionnaire;
  private Utilisateur utilisateur;
  private Film film;
  private Evaluation evaluation;
  private Artiste realisateur;
  private InformationPersonnelle infoPersonnelle;
  
  @BeforeEach
  void setUp() {
    gestionnaire = new GestionnaireUtilisateurs();
    
    // Création d'une InformationPersonnelle pour l'utilisateur
    infoPersonnelle =
        new InformationPersonnelle("Doe", "John", "123 Main St", 30);
    
    // Création d'un utilisateur
    utilisateur = new Utilisateur("testUser", "password123");
    utilisateur.set_infos(infoPersonnelle);
    
    // Création d'un réalisateur
    realisateur = new Artiste("Nolan", "Christopher", "Britannique");
    
    // Création d'un film
    film = new Film("Inception", realisateur, 2010, 13);
    film.set_etat(true);
    
    // Création d'une évaluation
    evaluation = new Evaluation(5, "Excellent film!", utilisateur, film);
  }
  
  @Test
  void testInscriptionNominal() {
    int result =
        gestionnaire.inscription("newUser", "securePassword", infoPersonnelle);
    assertEquals(0, result,
        "L'inscription d'un utilisateur valide doit réussir.");
  }
  
  @Test
  void testInscriptionPseudoDejaUtilise() {
    gestionnaire.inscription("testUser", "password123", infoPersonnelle);
    int result =
        gestionnaire.inscription("testUser", "newPassword", infoPersonnelle);
    assertEquals(1, result,
        "L'inscription doit échouer si le pseudo est déjà utilisé.");
  }
  
  @Test
  void testConnexionNominal() {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    assertTrue(
        gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp()),
        "Connexion valide doit réussir.");
  }
  
  @Test
  void testConnexionPseudoInvalide() {
    assertFalse(gestionnaire.connexion("wrongUser", "password123"),
        "Connexion avec un pseudo invalide doit échouer.");
  }
  
  @Test
  void testLouerFilmNominal() throws Exception {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    gestionnaire.louerFilm(film);
    assertFalse(utilisateur.get_films().contains(film),
        "Le film doit être ajouté à la liste des films loués.");
  }
  
  @Test
  void testLouerFilmDejaLoue() throws Exception {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    gestionnaire.louerFilm(film);
    assertThrows(LocationException.class, () -> gestionnaire.louerFilm(film),
        "La location du même film deux fois doit lancer une exception.");
  }
  
  @Test
  void testAjouterEvaluationNominal() throws Exception {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    gestionnaire.louerFilm(film);
    gestionnaire.ajouterEvaluation(film, evaluation);
    assertTrue(film.get_evaluations().contains(evaluation),
        "L'évaluation doit être ajoutée au film.");
  }
  
  @Test
  void testAjouterEvaluationFilmNonLoue() {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    assertThrows(LocationException.class,
        () -> gestionnaire.ajouterEvaluation(film, evaluation),
        "Ajouter une évaluation pour un film non loué doit lancer une exception.");
  }

  
  
  @Test
  void testModifierEvaluationNonExistante() throws Exception {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    gestionnaire.louerFilm(film);
    
    Evaluation newEvaluation =
        new Evaluation(4, "Bon film.", utilisateur, film);
    assertThrows(LocationException.class,
        () -> gestionnaire.modifierEvaluation(film, newEvaluation),
        "Modifier une évaluation non existante doit lancer une exception.");
  }
  
  @Test
  void testFilmsEnLocation() throws Exception {
    gestionnaire.inscription(utilisateur.get_pseudo(), utilisateur.get_mdp(),
        infoPersonnelle);
    gestionnaire.connexion(utilisateur.get_pseudo(), utilisateur.get_mdp());
    gestionnaire.louerFilm(film);
    Set<Film> films = gestionnaire.filmsEnLocation();
    assertNotNull(films, "La liste des films loués ne doit pas être nulle.");
    assertTrue(films.contains(film),
        "Le film doit être dans la liste des films loués.");
  }
}
