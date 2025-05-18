package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionnaireFilms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests JUnit de la classe {@link location.GestionnaireFilms
 * GestionnaireFilms}.
 */
class TestGestionnaireFilm {
  
  private GestionnaireFilms gestionnaire;
  private Film film1;
  private Film film2;
  private Artiste realisateur1;
  private Genre genre1;
  
  @BeforeEach
  void setUp() throws Exception {
    gestionnaire = new GestionnaireFilms();
    realisateur1 = new Artiste("Spielberg", "Steven", "Américaine");
    new Artiste("Hanks", "Tom", "Américaine");
    genre1 = Genre.Action;
    film1 = new Film("Film1", realisateur1, 1993, 12);
    film2 = new Film("Film2", realisateur1, 1998);
  }
  
  @AfterEach
  void tearDown() throws Exception {
    gestionnaire = null;
    film1 = null;
    film2 = null;
  }
  
  @Test
  void testCreerFilm() {
    Film nouveauFilm = gestionnaire.creerFilm("Film3", realisateur1, 2000, 16);
    assertNotNull(nouveauFilm);
    assertEquals("Film3", nouveauFilm.get_titre());
    
    // Test duplication
    Film filmDup = gestionnaire.creerFilm("Film3", realisateur1, 2000, 16);
    assertNull(filmDup);
    
    // Test paramètres invalides
    Film filmNull = gestionnaire.creerFilm(null, realisateur1, 2000, 16);
    assertNull(filmNull);
  }
  
  @Test
  void testAjouterGenres() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    assertTrue(gestionnaire.ajouterGenres(film1, genre1));
    assertTrue(film1.get_genres().contains(genre1));
    
    // Test film inexistant
    assertFalse(gestionnaire.ajouterGenres(null, genre1));
  }
  
  @Test
  void testAjouterAffiche() throws IOException {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    
    // Test ajout d'une affiche valide
    assertTrue(gestionnaire.ajouterAffiche(film1,
        new File("src/images/f14.png").getAbsolutePath()));
    assertEquals(new File("src/images/f14.png").getAbsolutePath(), film1.get_img());
    
    // Test film inexistant
    Film filmInexistant = new Film("Inconnu", realisateur1, 2000);
    assertThrows(IOException.class,
        () -> gestionnaire.ajouterAffiche(filmInexistant,
            new File("src/images/f18.png").getAbsolutePath()));
  }
  
  @Test
  void testSupprimerFilm() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    Set<Film> s = gestionnaire.get_films();
    Iterator<Film> it = s.iterator();
    Film film = null;
    while (it.hasNext()) {
      Film f = it.next();
      if (f.get_titre().equals(film1.get_titre())) {
        film = f;
      }
    }
    assertTrue(gestionnaire.supprimerFilm(film));
    
    // Test suppression d'un film inexistant
    assertFalse(gestionnaire.supprimerFilm(film2));
  }
  
  @Test
  void testEnsembleFilms() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    Set<Film> films = gestionnaire.ensembleFilms();
    Set<Film> s = gestionnaire.get_films();
    Iterator<Film> it = s.iterator();
    Film film = null;
    while (it.hasNext()) {
      Film f = it.next();
      if (f.get_titre().equals(film1.get_titre())) {
        film = f;
      }
    }
    assertTrue(films.contains(film));
  }
  
  @Test
  void testEnsembleFilmsRealisateur() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    Set<Film> filmsRealisateur =
        gestionnaire.ensembleFilmsRealisateur(realisateur1);
    assertNotNull(filmsRealisateur);
    Set<Film> s = gestionnaire.get_films();
    Iterator<Film> it = s.iterator();
    Film film = null;
    while (it.hasNext()) {
      Film f = it.next();
      if (f.get_titre().equals(film1.get_titre())) {
        film = f;
      }
    }
    assertTrue(filmsRealisateur.contains(film));
  }
  
  @Test
  void testEvaluationMoyenne() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    // Ajouter des évaluations fictives (si possible)
    // Assurez-vous que la logique d'évaluation est correcte.
    assertEquals(-1.0, gestionnaire.evaluationMoyenne(film1), 0.001);
  }
  
  @Test
  void testOuvrirEtFermerLocation() {
    gestionnaire.creerFilm(film1.get_titre(), film1.get_realisateur(),
        film1.get_annee(), film1.get_age_min());
    assertTrue(gestionnaire.ouvrirLocation(film1));
    assertTrue(film1.get_etat());
    
    assertTrue(gestionnaire.fermerLocation(film1));
    assertFalse(film1.get_etat());
  }
}
