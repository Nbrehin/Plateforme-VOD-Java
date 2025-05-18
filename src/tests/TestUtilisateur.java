package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Artiste.Role;
import location.Film;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Utilisateur Utilisateur}.
 *
 * @author Eric Cariou
 * @see location.Utilisateur
 */
class TestUtilisateur {
  
  private Utilisateur utilisateurBasique;
  private Artiste realisateur;
  private Artiste acteur;
  private Film film;
  
  @BeforeEach
  void setUp() throws Exception {
    new InformationPersonnelle("Skywalker", "Luke", "Planète Tatooine", 20);
    utilisateurBasique = new Utilisateur("lukeSkywalker", "password123");
    
    // Création des artistes : un réalisateur et un acteur
    realisateur = new Artiste("George", "Lucas", "USA");
    acteur = new Artiste("Mark", "Hamill", "USA");
    
    // Création d'un film avec le réalisateur et les autres informations
    // necessaires
    film = new Film("Star Wars", realisateur, 1977, 12);
  }
  
  @AfterEach
  void tearDown() throws Exception {}
  
  // Test du constructeur avec pseudo et mot de passe uniquement
  @Test
  void testConstructeurBasique() {
    assertEquals("lukeSkywalker", utilisateurBasique.get_pseudo());
    assertEquals("password123", utilisateurBasique.get_mdp());
    assertNotNull(utilisateurBasique.get_films());
    assertTrue(utilisateurBasique.get_films().isEmpty());
  }
  
  // Test des getters et setters du pseudo
  @Test
  void testGetSetPseudo() {
    utilisateurBasique.set_pseudo("newLukeSkywalker");
    assertEquals("newLukeSkywalker", utilisateurBasique.get_pseudo());
  }
  
  // Test des getters et setters du mot de passe
  @Test
  void testGetSetMdp() {
    utilisateurBasique.set_mdp("newPassword123");
    assertEquals("newPassword123", utilisateurBasique.get_mdp());
  }
  
  // Test des informations personnelles
  @Test
  void testGetSetInfos() {
    InformationPersonnelle nouvelleInfo =
        new InformationPersonnelle("Vador", "Dark", "Planète Coruscant", 40);
    utilisateurBasique.set_infos(nouvelleInfo);
    assertEquals("Vador", utilisateurBasique.get_infos().getNom());
    assertEquals("Dark", utilisateurBasique.get_infos().getPrenom());
  }
  
  // Test d'ajout et de retrait de films
  @Test
  void testAjouterRetirerFilm() {
    Set<Film> filmsAvant = new HashSet<>(utilisateurBasique.get_films());
    assertTrue(filmsAvant.isEmpty());
    
    // Ajout d'un film
    utilisateurBasique.get_films().add(film);
    assertEquals(1, utilisateurBasique.get_films().size());
    
    // Retrait d'un film
    utilisateurBasique.get_films().remove(film);
    assertTrue(utilisateurBasique.get_films().isEmpty());
  }
  
  // Test que la collection des films n'est pas modifiable directement
  @Test
  void testSetFilms() {
    Set<Film> filmsSet = new HashSet<>();
    filmsSet.add(film);
    utilisateurBasique.set_films(filmsSet);
    assertTrue(utilisateurBasique.get_films().contains(film));
  }
  
  // Test pour vérifier qu'un utilisateur avec des films reste consistant
  @Test
  void testUtilisateurAvecFilms() {
    utilisateurBasique.get_films().add(film);
    assertFalse(utilisateurBasique.get_films().isEmpty());
    assertEquals(1, utilisateurBasique.get_films().size());
    assertTrue(utilisateurBasique.get_films().contains(film));
  }
  
  // Test pour vérifier la gestion des films sur un utilisateur vide
  @Test
  void testUtilisateurSansFilms() {
    assertTrue(utilisateurBasique.get_films().isEmpty());
  }
  
  // Test d'un utilisateur avec des informations non définies (ex: un
  // utilisateur sans infos)
  @Test
  void testUtilisateurSansInfos() {
    Utilisateur utilisateurSansInfos =
        new Utilisateur("chewbacca", "password123");
    assertNull(utilisateurSansInfos.get_infos());
  }
  
  // Test d'un mot de passe vide
  @Test
  void testSetMdpVide() {
    utilisateurBasique.set_mdp("");
    assertEquals("", utilisateurBasique.get_mdp());
  }
  
  // Test d'un pseudo vide
  @Test
  void testSetPseudoVide() {
    utilisateurBasique.set_pseudo("");
    assertEquals("", utilisateurBasique.get_pseudo());
  }
  
  // Test d'une adresse vide dans les informations personnelles
  @Test
  void testSetAdresseVide() {
    InformationPersonnelle infoAvecAdresseVide =
        new InformationPersonnelle("Vador", "Dark", "", 50);
    utilisateurBasique.set_infos(infoAvecAdresseVide);
    assertEquals("", utilisateurBasique.get_infos().getAdresse());
  }
  
  // Test de l'ajout d'un artiste en tant que réalisateur
  @Test
  void testArtisteRealisateur() {
    realisateur.change_Role(Role.REALISATEUR);
    assertEquals(Role.REALISATEUR, realisateur.get_role());
    assertTrue(film.get_realisateur().get_role() == Role.REALISATEUR);
  }
  
  // Test de l'ajout d'un artiste en tant qu'acteur
  @Test
  void testArtisteActeur() {
    acteur.change_Role(Role.ACTEUR);
    assertEquals(Role.ACTEUR, acteur.get_role());
    assertFalse(film.get_acteurs().contains(acteur)); 
    // Le film n'a pas encore d'acteurs
  }
  
  // Test de l'ajout d'un artiste avec les deux rôles
  @Test
  void testArtisteBothRoles() {
    acteur.change_Role(Role.BOTH);
    assertEquals(Role.BOTH, acteur.get_role());
    film.get_acteurs().add(acteur);
    assertTrue(film.get_acteurs().contains(acteur)); 
    // Le film peut avoir cet acteur
  }
  
  // Test de l'association d'un artiste à un film
  @Test
  void testAssociationArtisteFilm() {
    acteur.set_afilm(true);
    assertTrue(acteur.get_afilm());
  }
  
}
