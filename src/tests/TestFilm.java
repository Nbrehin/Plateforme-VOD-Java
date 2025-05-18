package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Film}.
 *
 * @author simon.dumas, tom.quemeneur, noah.brehin
 */
class TestFilm {
  
  private Film film;
  private Artiste realisateur;
  private Artiste acteur1;
  private Artiste acteur2;
  
  /**
   * Préparation des tests : création d'un film de base et d'artistes.
   */
  @BeforeEach
  void setUp() throws Exception {
    realisateur = new Artiste("Nolan", "Christopher", "Britannique");
    acteur1 = new Artiste("DiCaprio", "Leonardo", "Américain");
    acteur2 = new Artiste("Page", "Elliot", "Canadien");
    
    film = new Film("Inception", realisateur, 2010, 13);
  }
  
  /**
   * Nettoyage après chaque test.
   */
  @AfterEach
  void tearDown() throws Exception {
    film = null;
    realisateur = null;
    acteur1 = null;
    acteur2 = null;
  }
  
  /**
   * Teste le constructeur et les getters.
   */
  @Test
  void testConstructeurEtGetters() {
    assertEquals("Inception", film.get_titre());
    assertEquals(2010, film.get_annee());
    assertEquals(realisateur, film.get_realisateur());
    assertEquals(13, film.get_age_min());
    assertTrue(film.get_acteurs().isEmpty());
    assertTrue(film.get_genres().isEmpty());
    assertTrue(film.get_evaluations().isEmpty());
    assertFalse(film.get_etat()); // Vérifie que l'état par défaut est false
  }
  
  /**
   * Teste les setters pour les propriétés de base.
   */
  @Test
  void testSetters() {
    film.set_titre("Interstellar");
    film.set_annee(2014);
    film.set_realisateur(new Artiste("Spielberg", "Steven", "Américain"));
    film.set_age_min(10);
    
    assertEquals("Interstellar", film.get_titre());
    assertEquals(2014, film.get_annee());
    assertEquals("Spielberg", film.get_realisateur().get_Nom());
    assertEquals(10, film.get_age_min());
  }
  
  /**
   * Teste l'ajout et la récupération des acteurs.
   */
  @Test
  void testActeurs() {
    Set<Artiste> acteurs = new HashSet<>();
    acteurs.add(acteur1);
    acteurs.add(acteur2);
    
    film.set_acteurs(acteurs);
    
    assertEquals(2, film.get_acteurs().size());
    assertTrue(film.get_acteurs().contains(acteur1));
    assertTrue(film.get_acteurs().contains(acteur2));
  }
  
  /**
   * Teste l'ajout et la récupération des genres.
   */
  @Test
  void testGenres() {
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.Action);
    genres.add(Genre.ScienceFiction);
    
    film.set_genres(genres);
    
    assertEquals(2, film.get_genres().size());
    assertTrue(film.get_genres().contains(Genre.Action));
    assertTrue(film.get_genres().contains(Genre.ScienceFiction));
  }
  
  /**
   * Teste le changement de l'état de réservation.
   */
  @Test
  void testEtat() {
    assertFalse(film.get_etat());
    film.set_etat(true);
    assertTrue(film.get_etat());
  }
  
  /**
   * Teste l'ajout et la récupération des évaluations.
   */
  @Test
  void testEvaluations() {
    Set<Evaluation> evaluations = new HashSet<>();
    evaluations.add(new Evaluation(5, "Excellent",  new Utilisateur("tristan", "ent"), film));
    evaluations.add(new Evaluation(4, "Très bon",  new Utilisateur("luck", "pluck"), film));
    
    film.set_evaluations(evaluations);
    
    assertEquals(2, film.get_evaluations().size());
  }
}
