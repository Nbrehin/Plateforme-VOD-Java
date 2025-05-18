package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Evaluation}.
 */
class TestEvaluation {
  
  private Evaluation evaluationAvecCommentaire;
  private Evaluation evaluationSansCommentaire;
  private Utilisateur utilisateur;
  private Film film;
  
  /**
   * Préparation des tests : création d'un utilisateur, d'un film, et
   * d'évaluations.
   */
  @BeforeEach
  void setUp() {
    utilisateur = new Utilisateur("user123", "password");
    Artiste realisateur = new Artiste("Nolan", "Christopher", "Britannique");
    film = new Film("Inception", realisateur, 2010, 13);
    
    evaluationAvecCommentaire =
        new Evaluation(5, "Excellent film !", utilisateur, film);
    evaluationSansCommentaire = new Evaluation(4, utilisateur, film);
  }
  
  /**
   * Test du constructeur avec commentaire.
   */
  @Test
  void testConstructeurAvecCommentaire() {
    assertEquals("user123", evaluationAvecCommentaire.auteur);
    assertEquals(5, evaluationAvecCommentaire.note);
    assertEquals("Excellent film !", evaluationAvecCommentaire.com);
  }
  
  /**
   * Test du constructeur sans commentaire.
   */
  @Test
  void testConstructeurSansCommentaire() {
    assertEquals("user123", evaluationSansCommentaire.auteur);
    assertEquals(4, evaluationSansCommentaire.note);
    assertNull(evaluationSansCommentaire.com);
  }
  
  /**
   * Test de la méthode get_note.
   */
  @Test
  void testGetNote() {
    assertEquals(5, evaluationAvecCommentaire.get_note());
    assertEquals(4, evaluationSansCommentaire.get_note());
  }
  
  /**
   * Test de la méthode get_com.
   */
  @Test
  void testGetCom() {
    assertEquals("Excellent film !", evaluationAvecCommentaire.get_com());
    assertNull(evaluationSansCommentaire.get_com());
  }
}
