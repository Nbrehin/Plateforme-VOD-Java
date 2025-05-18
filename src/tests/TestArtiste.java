package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import location.Artiste;
import location.Artiste.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Artiste}.
 *
 * @author simon.dumas, tom.quemeneur, noah brehin
 */
class TestArtiste {
  
  private Artiste artiste;
  
  /**
   * Préparation des tests : création d'un artiste de base.
   */
  @BeforeEach
  void setUp() throws Exception {
    artiste = new Artiste("Doe", "John", "Française");
  }
  
  /**
   * Nettoyage après chaque test. (Pas nécessaire ici, à adapter au besoin.)
   */
  @AfterEach
  void tearDown() throws Exception {
    artiste = null;
  }
  
  /**
   * Teste le constructeur et les getters.
   */
  @Test
  void testConstructeurEtGetters() {
    assertEquals("Doe", artiste.get_Nom());
    assertEquals("John", artiste.get_Prenom());
    assertEquals("Française", artiste.get_Nationalite());
    assertEquals(Artiste.Role.ACTEUR, artiste.get_role());
  }
  
  /**
   * Teste les setters pour le nom, le prénom et la nationalité.
   */
  @Test
  void testSetters() {
    artiste.set_Nom("Smith");
    artiste.set_preNom("Jane");
    artiste.set_Nationalite("Anglaise");
    
    assertEquals("Smith", artiste.get_Nom());
    assertEquals("Jane", artiste.get_Prenom());
    assertEquals("Anglaise", artiste.get_Nationalite());
  }
  
  /**
   * Teste le changement de rôle avec des valeurs valides.
   */
  @Test
  void testChangeRoleValide() {
    assertTrue(artiste.change_Role(Role.REALISATEUR));
    assertEquals(Artiste.Role.REALISATEUR, artiste.get_role());
    
    assertTrue(artiste.change_Role(Role.BOTH));
    assertEquals(Artiste.Role.BOTH, artiste.get_role());
    
    assertTrue(artiste.change_Role(Role.ACTEUR));
    assertEquals(Artiste.Role.ACTEUR, artiste.get_role());
  }
  
  /**
   * Teste le changement de rôle avec une valeur invalide.
   */
  @Test
  void testChangeRoleInvalide() {
    assertFalse(artiste.change_Role(null));
    assertEquals(Artiste.Role.ACTEUR, artiste.get_role()); // Le rôle initial
  }
  
  /**
   * Teste la méthode `toString`.
   */
  @Test
  void testToString() {
    String attendu =
        "Doe, John, Française, ACTEUR";
    assertEquals(attendu, artiste.toString());
  }
}
