package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import location.Artiste;
import location.Artiste.Role;
import location.GestionnaireArtistes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestGestionnaireArtistes {
  
  private GestionnaireArtistes gestionnaire;
  
  @BeforeEach
  void setUp() {
    gestionnaire = new GestionnaireArtistes();
  }
  
  @Test
  void testCreerArtiste_nominal() {
    Artiste artiste = gestionnaire.creerArtiste("Doe", "John", "Française");
    assertNotNull(artiste);
    assertEquals("Doe", artiste.get_Nom());
    assertEquals("John", artiste.get_Prenom());
    assertEquals("Française", artiste.get_Nationalite());
  }
  
  @Test
  void testCreerArtiste_nonNominal() {
    // Paramètres invalides
    assertNull(gestionnaire.creerArtiste(null, "John", "Française"));
    assertNull(gestionnaire.creerArtiste("Doe", null, "Française"));
    assertNull(gestionnaire.creerArtiste("Doe", "John", null));
    
    // Duplication
    gestionnaire.creerArtiste("Doe", "John", "Française");
    assertNull(gestionnaire.creerArtiste("Doe", "John", "Française"));
  }
  
  @Test
  void testEnsembleActeurs_nominal() {
    Artiste acteur = gestionnaire.creerArtiste("Doe", "Jane", "Américaine");
    acteur.change_Role(Role.ACTEUR);
    Set<Artiste> acteurs = gestionnaire.ensembleActeurs();
    assertTrue(acteurs.contains(acteur));
  }
  
  @Test
  void testEnsembleActeurs_nonNominal() {
    // Aucun acteur
    Set<Artiste> acteurs = gestionnaire.ensembleActeurs();
    assertTrue(acteurs.isEmpty());
  }
  
  @Test
  void testEnsembleRealisateurs_nominal() {
    Artiste realisateur = gestionnaire.creerArtiste("Doe", "James", "Anglaise");
    realisateur.change_Role(Role.REALISATEUR);
    Set<Artiste> realisateurs = gestionnaire.ensembleRealisateurs();
    assertTrue(realisateurs.contains(realisateur));
  }
  
  @Test
  void testEnsembleRealisateurs_nonNominal() {
    // Aucun réalisateur
    Set<Artiste> realisateurs = gestionnaire.ensembleRealisateurs();
    assertTrue(realisateurs.isEmpty());
  }
  
  @Test
  void testEnsembleArtiste_nominal() {
    gestionnaire.creerArtiste("Smith", "Emma", "Canadienne");
    Set<Artiste> artistes = gestionnaire.ensembleArtiste();
    assertEquals(1, artistes.size());
  }
  
  @Test
  void testEnsembleArtiste_nonNominal() {
    // Aucun artiste
    Set<Artiste> artistes = gestionnaire.ensembleArtiste();
    assertTrue(artistes.isEmpty());
  }
  
  @Test
  void testGetActeur_nominal() {
    Artiste acteur = gestionnaire.creerArtiste("Doe", "Jane", "Française");
    acteur.change_Role(Role.ACTEUR);
    gestionnaire.get_artiste().add(acteur);
    Artiste result = gestionnaire.getActeur("Doe", "Jane");
    assertEquals(acteur, result);
  }
  
  @Test
  void testGetActeur_nonNominal() {
    assertNull(gestionnaire.getActeur(null, "Jane"));
    assertNull(gestionnaire.getActeur("Doe", null));
    assertNull(gestionnaire.getActeur("Inconnu", "Inconnue"));
  }
  
  @Test
  void testGetRealisateur_nominal() {
    Artiste realisateur =
        gestionnaire.creerArtiste("Doe", "James", "Française");
    realisateur.change_Role(Role.REALISATEUR);
    gestionnaire.get_artiste().add(realisateur);
    Artiste result = gestionnaire.getRealisateur("Doe", "James");
    assertEquals(realisateur, result);
  }
  
  @Test
  void testGetRealisateur_nonNominal() {
    assertNull(gestionnaire.getRealisateur(null, "James"));
    assertNull(gestionnaire.getRealisateur("Doe", null));
    assertNull(gestionnaire.getRealisateur("Inconnu", "Inconnue"));
  }
  
  @Test
  void testGetArtiste_nominal() {
    Artiste artiste = gestionnaire.creerArtiste("Doe", "John", "Française");
    gestionnaire.get_artiste().add(artiste);
    Artiste result = gestionnaire.getArtiste("Doe", "John");
    assertEquals(artiste, result);
  }
  
  @Test
  void testGetArtiste_nonNominal() {
    assertNull(gestionnaire.getArtiste(null, "John"));
    assertNull(gestionnaire.getArtiste("Doe", null));
    assertNull(gestionnaire.getArtiste("Inconnu", "Inconnue"));
  }
  
  @Test
  void testSupprimerArtiste_nominal() {
    Artiste artiste = gestionnaire.creerArtiste("Doe", "John", "Française");
    gestionnaire.get_artiste().add(artiste);
    assertTrue(gestionnaire.supprimerArtiste(artiste));
    assertFalse(gestionnaire.get_artiste().contains(artiste));
  }
  
  @Test
  void testSupprimerArtiste_nonNominal() {
    // Artiste non présent
    Artiste nonExistant = new Artiste("Inconnu", "Inconnue", "Inconnue");
    assertFalse(gestionnaire.supprimerArtiste(nonExistant));
    
    // Paramètre nul
    assertFalse(gestionnaire.supprimerArtiste(null));
  }
}
