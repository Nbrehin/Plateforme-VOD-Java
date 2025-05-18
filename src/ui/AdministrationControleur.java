package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import location.*;
import location.Artiste.Role;

/**
 * Controleur JavaFX de la fenêtre d'administration.
 *
 * @author Eric Cariou
 *
 */
public class AdministrationControleur {
  
  @FXML
  private CheckBox checkBoxLocationFilm;
  
  @FXML
  private TextField entreeAffiche;
  
  @FXML
  private TextField entreeAnneeFilm;
  
  @FXML
  private TextField entreeNationaliteArtiste;
  
  @FXML
  private TextField entreeNomArtiste;
  
  @FXML
  private TextField entreeNomPrenomRealisateur;
  
  @FXML
  private TextField entreePrenomArtiste;
  
  @FXML
  private TextField entreeTitreFilm;
  
  @FXML
  private Label labelListeArtistes;
  
  @FXML
  private Label labelListeFilms;
  
  @FXML
  private ListView<String> listeArtistes;
  
  @FXML
  private ChoiceBox<String> listeChoixAgeLimite;
  
  @FXML
  private ListView<String> listeFilms;
  
  @FXML
  private ListView<String> listeGenresFilm;
  
  @FXML
  private ListView<String> listeTousGenres;
  
  private Backend backend;
  
  private Frontend frontend;
  
  private void afficherPopup(String message, AlertType type) {
    Alert alert = new Alert(type);
    if (type == AlertType.ERROR) {
      alert.setTitle("Erreur");
    } else {
      alert.setTitle("Information");
    }
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.setResizable(true);
    alert.showAndWait();
  }
  
  private void afficherPopupErreur(String message) {
    this.afficherPopup(message, AlertType.ERROR);
  }
  
  private void afficherPopupInformation(String message) {
    this.afficherPopup(message, AlertType.INFORMATION);
  }
  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    Set<Artiste> s = this.backend.ensembleActeurs();
    Iterator<Artiste> it = s.iterator();
    while (it.hasNext()) {
      listeArtistes.getItems().add(it.next().toString());
    }
  }
  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    Set<Artiste> s = this.backend.ensembleRealisateurs();
    Iterator<Artiste> it = s.iterator();
    while (it.hasNext()) {
      listeArtistes.getItems().add(it.next().toString());
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String f = listeArtistes.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Aucun artiste sélectionné !");
    } else {
      String[] details = f.split(", ");
      Artiste a = backend.getArtiste(details[0], details[1]);
      Set<Film> s = backend.ensembleFilmsActeur(a);
      if (a.get_role() != Role.ACTEUR && a.get_role() != Role.BOTH) {
        afficherPopupErreur("L'artiste sélectionné n'est pas un acteur !");
        return;
      }
      if (s == null) {
        afficherPopupErreur("Aucun film !");
      } else {
        Iterator<Film> it = s.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          listeFilms.getItems().add(it.next().toString());
          trouve = true;
        }
        if (!trouve) {
          afficherPopupErreur("Aucun film !");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsDuRealisateur(ActionEvent event) {
    String f = entreeNomPrenomRealisateur.getText();
    if (f == "") {
      afficherPopupErreur(
          "Veuillez entrer un nom et prénom d'un réalisateur !");
      return;
    }
    String[] details = f.split(" ");
    if (details.length < 2) {
      afficherPopupErreur(
          "Veuillez entrer un nom et prénom d'un réalisateur !");
      return;
    }
    Artiste a = backend.getArtiste(details[0], details[1]);
    if (a == null) {
      afficherPopupErreur("Réalisateur inconnu !");
      return;
    }
    Set<Film> s = backend.ensembleFilmsRealisateur(a);
    if (s == null) {
      afficherPopupErreur("Cet artiste n'a réalisé aucun film !");
      return;
    }
    Iterator<Film> it = s.iterator();
    listeFilms.getItems().clear();
    while (it.hasNext()) {
      Film film = it.next();
      if (film != null) {
        listeFilms.getItems().add(film.toString());
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String f = listeArtistes.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Aucun artiste sélectionné !");
    } else {
      String[] details = f.split(", ");
      Artiste a = backend.getArtiste(details[0], details[1]);
      Set<Film> s = backend.ensembleFilms();
      if (a.get_role() != Role.REALISATEUR && a.get_role() != Role.BOTH) {
        afficherPopupErreur("L'artiste sélectionné n'est pas un réalisateur !");
        return;
      }
      if (s == null) {
        afficherPopupErreur("Aucun film !");
      } else {
        Iterator<Film> it = s.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          Film film = it.next();
          if (film.get_realisateur().equals(a)) {
            listeFilms.getItems().add(film.toString());
            trouve = true;
          }
        }
        if (!trouve) {
          afficherPopupErreur("Aucun film !");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousActeursFilm(ActionEvent event) {
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Veuillez sélectionner un film !");
      return;
    }
    String[] details = f.split(", ");
    Film film = backend.getFilm(details[0]);
    Set<Artiste> s = film.get_acteurs();
    listeArtistes.getItems().clear();
    if (s == null) {
      afficherPopupErreur("Ce film n'a pas d'acteurs !");
      listeArtistes.getItems().add(String.valueOf(film.get_realisateur()));
      return;
    }
    s.add(film.get_realisateur());
    Iterator<Artiste> it = s.iterator();
    while (it.hasNext()) {
      Artiste a = it.next();
      if (a != null) {
        listeArtistes.getItems().add(String.valueOf(a));
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    listeArtistes.getItems().clear();
    Set<Artiste> res = new HashSet<>();
    Set<Artiste> s = this.backend.ensembleActeurs();
    Iterator<Artiste> it = s.iterator();
    while (it.hasNext()) {
      Artiste i = it.next();
      if (!res.contains(i)) {
        res.add(i);
      }
    }
    s = this.backend.ensembleRealisateurs();
    it = s.iterator();
    while (it.hasNext()) {
      Artiste i = it.next();
      if (!res.contains(i)) {
        res.add(i);
      }
    }
    it = res.iterator();
    while (it.hasNext()) {
      listeArtistes.getItems().add(it.next().toString());
    }
  }
  
  @FXML
  void actionBoutonAjouterActeurFilm(ActionEvent event) {
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (artiste == null || film == null) {
      afficherPopupErreur("Veuillez sélectionner un artiste et un film !");
      return;
    }
    String[] detailsa = artiste.split(", ");
    String[] detailsf = film.split(", ");
    Film f = backend.getFilm(detailsf[0]);
    Artiste a = backend.getArtiste(detailsa[0], detailsa[1]);
    Set<Artiste> s = f.get_acteurs();
    if (s.add(a)) {
      afficherPopupInformation("Ajout réussi !");
    } else {
      afficherPopupErreur("L'ajout à échoué !");
    }
  }
  
  @FXML
  void actionBoutonAjouterGenreFilm(ActionEvent event) {
    String genre = listeTousGenres.getSelectionModel().getSelectedItem();
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (genre == null || film == null) {
      afficherPopupErreur("Veuillez sélectionner un genre et un film !");
      return;
    }
    Genre genr = null;
    for (Genre g : Genre.values()) {
      if (g.name().equals(genre)) {
        genr = g;
      }
    }
    String[] detailsf = film.split(", ");
    Film f = backend.getFilm(detailsf[0]);
    Set<Genre> s = f.get_genres();
    if (s.add(genr)) {
      afficherPopupInformation("Ajout réussi !");
      return;
    } else {
      afficherPopupErreur("L'ajout à échoué !");
    }
  }
  
  @FXML
  void actionBoutonChercherArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    
    if (nom != "" && prenom != "") {
      Artiste a = backend.getArtiste(nom, prenom);
      if (a == null) {
        afficherPopupErreur(
            "Aucun artiste ne correspond à ces noms et prénoms !");
      } else {
        listeArtistes.getItems().clear();
        listeArtistes.getItems().add(a.toString());
        entreeNomArtiste.setText(a.get_Nom());
        entreePrenomArtiste.setText(a.get_Prenom());
        entreeNationaliteArtiste.setText(a.get_Nationalite());
      }
    } else {
      afficherPopupErreur("Nom ou prénom invalide(s) !");
    }
  }
  
  @FXML
  void actionBoutonChercherFilm(ActionEvent event) {
    String titre = entreeTitreFilm.getText();
    if (titre == "") {
      afficherPopupErreur("Veuillez saisir un titre !");
      return;
    }
    Film f = backend.getFilm(titre);
    if (f == null) {
      afficherPopupErreur("Film introuvable !");
      return;
    }
    listeFilms.getItems().clear();
    listeFilms.getItems().add(f.toString());
    entreeTitreFilm.setText(f.get_titre());
    entreeAnneeFilm.setText(String.valueOf(f.get_annee()));
    Artiste realisateur = f.get_realisateur();
    entreeNomPrenomRealisateur
        .setText(realisateur.get_Nom() + " " + realisateur.get_Prenom());
    listeChoixAgeLimite.setValue(String.valueOf(f.get_age_min()));
    entreeAffiche.setText(f.get_img());
    checkBoxLocationFilm.setSelected(f.get_etat());
  }
  
  @FXML
  void actionBoutonChoisirArtisteSelectionneRealisateur(ActionEvent event) {
    String f = listeArtistes.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Aucun artiste sélectionné !");
    } else {
      String[] details = f.split(", ");
      entreeNomPrenomRealisateur.setText(details[0] + " " + details[1]);
    }
  }
  
  @FXML
  void actionBoutonEnregistrerArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    String nationalite = entreeNationaliteArtiste.getText();
    
    if (nom != "" && prenom != "" && nationalite != "") {
      if (backend.creerArtiste(nom, prenom, nationalite) == null) {
        afficherPopupErreur("Ajout de l'artiste impossible !");
        return;
      }
      afficherPopupInformation("Ajout de l'artiste réussi !");
    } else {
      afficherPopupErreur("Les informations renseignées sont invalides !");
    }
  }
  
  @FXML
  void actionBoutonEnregistrerFilm(ActionEvent event) {
    String titre = entreeTitreFilm.getText();
    String annee = entreeAnneeFilm.getText();
    String age = listeChoixAgeLimite.getValue();
    String realisateur = entreeNomPrenomRealisateur.getText();
    if (annee != "" && age != "" && titre != "" && realisateur != "") {
      int ann = Integer.parseInt(annee);
      int ag = Integer.parseInt(age);
      String[] details = realisateur.split(" ");
      String nom = details[0];
      String prenom = details[1];
      Film f =
          backend.creerFilm(titre, backend.getArtiste(nom, prenom), ann, ag);
      if (f == null) {
        Film film = backend.getFilm(titre);
        if (film == null) {
          afficherPopupErreur("Création du film impossible !");
          return;
        }
        film.set_etat(checkBoxLocationFilm.isSelected());
        afficherPopupInformation("Modification du film réussie !");
        return;
      } else {
        afficherPopupInformation("Création du film réussie !");
        listeFilms.getItems().clear();
        listeFilms.getItems().add(f.toString());
        
      }
    } else {
      afficherPopupErreur("Veuillez remplir les champs ! ");
      return;
    }
  }
  
  @FXML
  void actionBoutonNouveauArtiste(ActionEvent event) {
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
  }
  
  @FXML
  void actionBoutonNouveauFilm(ActionEvent event) {
    entreeTitreFilm.clear();
    entreeAnneeFilm.clear();;
    listeChoixAgeLimite.getSelectionModel().clearSelection();
    entreeNomPrenomRealisateur.clear();
    checkBoxLocationFilm.setSelected(false);
    listeGenresFilm.getItems().clear();
  }
  
  @FXML
  void actionBoutonParcourirAffiche(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
        "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
    fileChooser.getExtensionFilters().add(imageFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      entreeAffiche.setText(file.getName());
      String titre = entreeTitreFilm.getText();
      if (!titre.isBlank()) {
        Film film = backend.getFilm(titre);
        if (film != null) {
          try {
            backend.ajouterAffiche(film, entreeAffiche.getText());
          } catch (IOException e) {
            afficherPopupErreur("Fichier invalide !");
          }
        }
      }
    }
  }
  
  @FXML
  void actionBoutonSupprimerArtiste(ActionEvent event) {
    String f = listeArtistes.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Aucun artiste sélectionné !");
    } else {
      String[] details = f.split(", ");
      Artiste a = backend.getArtiste(details[0], details[1]);
      boolean res = backend.supprimerArtiste(a);
      if (res) {
        afficherPopupInformation("Suppression réussie !");
      } else {
        afficherPopupErreur("La suppression à échouée !");
      }
    }
  }
  
  @FXML
  void actionBoutonSupprimerFilm(ActionEvent event) {
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Veuillez sélectionner un film !");
      return;
    }
    String[] details = f.split(", ");
    Film film = backend.getFilm(details[0]);
    if (backend.supprimerFilm(film)) {
      afficherPopupInformation("Suppression réussie !");
    } else {
      afficherPopupErreur("La suppression à échouée !");
    }
  }
  
  @FXML
  void actionMenuApropos(ActionEvent event) {
    afficherPopupInformation(
        "Application réalisée par Tom Quemeneur, Dumas Simon et Noah Bréhin.");
  }
  
  @FXML
  void actionMenuCharger(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    
  }
  
  @FXML
  void actionListeSelectionArtiste(MouseEvent event) {
    String f = listeArtistes.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Artiste invalide !");
    } else {
      String[] details = f.split(", ");
      entreeNomArtiste.setText(details[0]);
      entreePrenomArtiste.setText(details[1]);
      entreeNationaliteArtiste.setText(details[2]);
    }
  }
  
  @FXML
  void actionListeSelectionFilm(MouseEvent event) {
    listeGenresFilm.getItems().clear();
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Film invalide !");
    } else {
      String[] details = f.split(", ");
      entreeTitreFilm.setText(details[0]);
      entreeAnneeFilm.setText(details[3]);
      listeChoixAgeLimite.setValue(details[4]);
      entreeNomPrenomRealisateur.setText(details[1] + " " + details[2]);
      entreeAffiche.setText(backend.getFilm(details[0]).get_img());
      if (backend.getFilm(details[0]).get_etat()) {
        checkBoxLocationFilm.setSelected(true);
      } else {
        checkBoxLocationFilm.setSelected(false);
      }
      Set<Genre> s = backend.getFilm(details[0]).get_genres();
      if (s != null) {
        Iterator<Genre> it = s.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          Genre g = it.next();
          if (g != null) {
            listeGenresFilm.getItems().add(g.toString());
            trouve = true;
          }
        }
        if (!trouve) {
          listeGenresFilm.getItems().add("Aucun genre !");
        }
      }
    }
  }
  
  @FXML
  void initialize() {
    // Mettre ici le code d'initialisation du contenu de la fenêtre
    listeChoixAgeLimite.getItems().add("0");
    listeChoixAgeLimite.getItems().add("1");
    listeChoixAgeLimite.getItems().add("2");
    listeChoixAgeLimite.getItems().add("3");
    listeChoixAgeLimite.getItems().add("4");
    listeChoixAgeLimite.getItems().add("5");
    listeChoixAgeLimite.getItems().add("6");
    listeChoixAgeLimite.getItems().add("7");
    listeChoixAgeLimite.getItems().add("8");
    listeChoixAgeLimite.getItems().add("9");
    listeChoixAgeLimite.getItems().add("10");
    listeChoixAgeLimite.getItems().add("11");
    listeChoixAgeLimite.getItems().add("12");
    listeChoixAgeLimite.getItems().add("13");
    listeChoixAgeLimite.getItems().add("14");
    listeChoixAgeLimite.getItems().add("15");
    listeChoixAgeLimite.getItems().add("16");
    listeChoixAgeLimite.getItems().add("17");
    listeChoixAgeLimite.getItems().add("18");
    
    listeTousGenres.getItems().add(Genre.Action.toString());
    listeTousGenres.getItems().add(Genre.Aventure.toString());
    listeTousGenres.getItems().add(Genre.Comedie.toString());
    listeTousGenres.getItems().add(Genre.ComedieDramatique.toString());
    listeTousGenres.getItems().add(Genre.Romance.toString());
    listeTousGenres.getItems().add(Genre.Drame.toString());
    listeTousGenres.getItems().add(Genre.Fantastique.toString());
    listeTousGenres.getItems().add(Genre.Guerre.toString());
    listeTousGenres.getItems().add(Genre.Policier.toString());
    listeTousGenres.getItems().add(Genre.Thriller.toString());
    listeTousGenres.getItems().add(Genre.Horreur.toString());
    listeTousGenres.getItems().add(Genre.Western.toString());
    listeTousGenres.getItems().add(Genre.ScienceFiction.toString());
    listeTousGenres.getItems().add(Genre.Biopic.toString());
    listeTousGenres.getItems().add(Genre.Animation.toString());
    listeTousGenres.getItems().add(Genre.Musical.toString());
    listeTousGenres.getItems().add(Genre.Historique.toString());
    
    GestionnaireFilms f = new GestionnaireFilms();
    GestionnaireArtistes a = new GestionnaireArtistes();
    GestionnaireUtilisateurs u = new GestionnaireUtilisateurs();
    backend = new Backend(f, a, u);
    backend.creerArtiste("Spielberg", "Steven", "Américaine");
    Artiste artiste1 = backend.getArtiste("Spielberg", "Steven");
    artiste1.change_Role(Role.REALISATEUR);
    backend.creerArtiste("DiCaprio", "Leonardo", "Américaine");
    artiste1 = backend.getArtiste("DiCaprio", "Leonardo");
    artiste1.change_Role(Role.ACTEUR);
    backend.creerArtiste("Tarantino", "Quentin", "Américaine");
    artiste1 = backend.getArtiste("Tarantino", "Quentin");
    artiste1.change_Role(Role.BOTH);
    backend.creerArtiste("Cotillard", "Marion", "Française");
    artiste1 = backend.getArtiste("Cotillard", "Marion");
    artiste1.change_Role(Role.ACTEUR);
    backend.creerArtiste("Renoir", "Jean", "Française");
    artiste1 = backend.getArtiste("Renoir", "Jean");
    artiste1.change_Role(Role.REALISATEUR);
    backend.creerArtiste("Eastwood", "Clint", "Américaine");
    artiste1 = backend.getArtiste("Eastwood", "Clint");
    artiste1.change_Role(Role.BOTH);
    backend.creerArtiste("Winslet", "Kate", "Britannique");
    artiste1 = backend.getArtiste("Winslet", "Kate");
    artiste1.change_Role(Role.ACTEUR);
    backend.creerArtiste("Nolan", "Christopher", "Britannique");
    artiste1 = backend.getArtiste("Nolan", "Christopher");
    artiste1.change_Role(Role.REALISATEUR);
    backend.creerArtiste("Del Toro", "Guillermo", "Mexicaine");
    artiste1 = backend.getArtiste("Del Toro", "Guillermo");
    artiste1.change_Role(Role.REALISATEUR);
    backend.creerArtiste("Blanchett", "Cate", "Australienne");
    artiste1 = backend.getArtiste("Blanchett", "Cate");
    artiste1.change_Role(Role.ACTEUR);
    
    frontend = new Frontend(f, a, u);
    
    backend.creerFilm("L'Odyssée de l'Espace Profonde",
        backend.getArtiste("Nolan", "Christopher"), 2022, 12);
    backend.ouvrirLocation(frontend.getFilm("L'Odyssée de l'Espace Profonde"));
    backend.ajouterGenres(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        Genre.Drame);
    backend.ajouterGenres(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        Genre.ScienceFiction);
    backend.ajouterGenres(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        Genre.Aventure);
    
    backend.ajouterActeurs(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        backend.getArtiste("Blanchett", "Kate"));
    backend.ajouterActeurs(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    /**
     * 
     */
    
    backend.creerFilm("Le Masque du Temps",
        backend.getArtiste("Del Toro", "Guillermo"), 2019, 16);
    backend.ouvrirLocation(frontend.getFilm("Le Masque du Temps"));
    backend.ajouterGenres(backend.getFilm("Le Masque du Temps"),
        Genre.Fantastique);
    backend.ajouterGenres(backend.getFilm("Le Masque du Temps"),
        Genre.Historique);
    /**
     * 
     */
    backend.creerFilm("Le Chant des Étoiles",
        backend.getArtiste("Renoir", "Jean"), 2020, 0);
    backend.ouvrirLocation(frontend.getFilm("Le Chant des Étoiles"));
    backend.ajouterGenres(backend.getFilm("Le Chant des Étoiles"),
        Genre.Animation);
    backend.ajouterGenres(backend.getFilm("Le Chant des Étoiles"),
        Genre.Comedie);
    
    backend.ajouterActeurs(backend.getFilm("Le Chant des Étoiles"),
        backend.getArtiste("Blanchett", "Kate"));
    backend.ajouterActeurs(backend.getFilm("Le Chant des Étoiles"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    /**
     * 
     */
    backend.creerFilm("L'Ultime Traque",
        backend.getArtiste("Tarantino", "Quentin"), 2021, 18);
    backend.ouvrirLocation(frontend.getFilm("L'Ultime Traque"));
    backend.ajouterGenres(backend.getFilm("L'Ultime Traque"), Genre.Aventure);
    backend.ajouterGenres(backend.getFilm("L'Ultime Traque"), Genre.Drame);
    backend.ajouterGenres(backend.getFilm("L'Ultime Traque"), Genre.Western);
    /**
     * 
     */
    backend.creerFilm("La Danse des Ombres",
        backend.getArtiste("Spielberg", "Steven"), 2018, 12);
    backend.ouvrirLocation(frontend.getFilm("La Danse des Ombres"));
    backend.ajouterGenres(backend.getFilm("La Danse des Ombres"), Genre.Guerre);
    backend.ajouterGenres(backend.getFilm("La Danse des Ombres"),
        Genre.Thriller);
    
    /**
     * 
     */
    backend.creerFilm("Les Rêves d'Ailleurs",
        backend.getArtiste("Renoir", "Jean"), 2023, 0);
    backend.ajouterGenres(backend.getFilm("Les Rêves d'Ailleurs"),
        Genre.Guerre);
    /**
     * 
     */
    backend.ajouterActeurs(backend.getFilm("L'Odyssée de l'Espace Profonde"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    backend.ajouterActeurs(backend.getFilm("Les Rêves d'Ailleurs"),
        backend.getArtiste("Blanchett", "Kate"));
    backend.ajouterActeurs(backend.getFilm("Les Rêves d'Ailleurs"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    /**
     * 
     */
    backend.creerFilm("Horizons Brisés",
        backend.getArtiste("Eastwood", "Clint"), 2024, 16);
    /**
     * 
     */
    backend.creerFilm("Sous le Ciel Rouge",
        backend.getArtiste("Del Toro", "Guillermo"), 2020, 12);
    backend.ajouterActeurs(backend.getFilm("Sous le Ciel Rouge"),
        backend.getArtiste("Blanchett", "Kate"));
    backend.ajouterActeurs(backend.getFilm("Sous le Ciel Rouge"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    backend.ajouterGenres(backend.getFilm("Sous le Ciel Rouge"), Genre.Biopic);
    backend.ajouterGenres(backend.getFilm("Sous le Ciel Rouge"), Genre.Romance);
    /**
     * 
     */
    backend.creerFilm("Les Murmures de la Forêt",
        backend.getArtiste("Nolan", "Christopher"), 2021, 0);
    /**
     * 
     */
    backend.creerFilm("Le Crépuscule des Héros",
        backend.getArtiste("Tarantino", "Quentin"), 2017, 18);
    backend.ajouterActeurs(backend.getFilm("Le Crépuscule des Héros"),
        backend.getArtiste("Blanchett", "Kate"));
    backend.ajouterActeurs(backend.getFilm("Le Crépuscule des Héros"),
        backend.getArtiste("DiCaprio", "Leonardo"));
    /**
     * 
     */
    
    InformationPersonnelle ip = new InformationPersonnelle("Dupont", "Pierre",
        "12 rue des Lilas, 75010 Paris, France", 34);
    frontend.inscription("ShadowHunter", "1234", ip);
    ip = new InformationPersonnelle("Smith", "Emily",
        "45 Park Avenue, New York, NY, USA", 28);
    frontend.inscription("StarGazer99", "Galaxy@2024*", ip);
    ip = new InformationPersonnelle("Müller", "Hans",
        "78 Bahnhofstrasse, 8001 Zurich, Suisse", 42);
    frontend.inscription("FrostyPenguin", "Chill!1234", ip);
    ip = new InformationPersonnelle("Rossi", "Maria",
        "Via Roma 15, 00184 Rome, Italie", 31);
    frontend.inscription("CyberWolf", "Byte$Wolf77", ip);
    ip = new InformationPersonnelle("Takahashi", "Hiroshi",
        "1-2-3 Shibuya, Tokyo, Japon", 37);
    frontend.inscription("MysticRaven", "Raven&2025", ip);
    
    
    
    frontend.connexion("ShadowHunter", "1234");
    Utilisateur user = u.get_utilisateur();
    Evaluation eval = new Evaluation(9,
        "Une expérience visuelle époustouflante avec un scénario captivant.",
        user, frontend.getFilm("L'Odyssée de l'Espace Profonde"));
    
    try {
      frontend.louerFilm(frontend.getFilm("L'Odyssée de l'Espace Profonde"));
      frontend.louerFilm(frontend.getFilm("Le Masque du Temps"));
      frontend.louerFilm(frontend.getFilm("Le Chant des Étoiles"));
      frontend.louerFilm(frontend.getFilm("L'Ultime Traque"));
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    } catch (LocationException e) {
    }
    eval = new Evaluation(8,
        "Un film sombre et poétique mais parfois un peu lent.", user,
        frontend.getFilm("Le Masque du Temps"));
    try {
      frontend.ajouterEvaluation(frontend.getFilm("Le Masque du Temps"), eval);
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    } catch (LocationException e) {
      // TODO Auto-generated catch block
    }
    try {
      frontend.deconnexion();
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    }
    
    frontend.connexion("StarGazer99", "Galaxy@2024*");
    user = u.get_utilisateur();
    eval = new Evaluation(10,
        "Un chef-d'œuvre qui touche l'âme. Parfait pour toute la famille.",
        user, frontend.getFilm("Le Chant des Étoiles"));
    try {
      frontend.ajouterEvaluation(frontend.getFilm("Le Chant des Étoiles"),
          eval);
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    } catch (LocationException e) {
      // TODO Auto-generated catch block
    }
    eval = new Evaluation(7,
        "Action intense mais certains moments sont prévisibles.", user,
        frontend.getFilm("L'Ultime Traque"));
    try {
      frontend.ajouterEvaluation(frontend.getFilm("L'Ultime Traque"), eval);
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    } catch (LocationException e) {
      // TODO Auto-generated catch block
    }
    try {
      frontend.deconnexion();
    } catch (NonConnecteException e) {
      // TODO Auto-generated catch block
    }
    
    
  }
}
