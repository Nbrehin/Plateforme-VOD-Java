package ui;

import java.util.Iterator;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import location.Artiste;
import location.Backend;
import location.Evaluation;
import location.Film;
import location.Frontend;
import location.Genre;
import location.GestionnaireArtistes;
import location.GestionnaireFilms;
import location.GestionnaireUtilisateurs;
import location.InformationPersonnelle;
import location.LocationException;
import location.NonConnecteException;
import location.Utilisateur;
import location.Artiste.Role;

/**
 * Controleur JavaFX de la fenêtre utilisateur.
 *
 * @author Eric Cariou
 *
 */
public class UtilisateurControleur {
  
  @FXML
  private CheckBox checkFilmLouable;
  
  @FXML
  private TextField entreeAdresseUtilisateur;
  
  @FXML
  private TextField entreeAgeLimiteFilm;
  
  @FXML
  private TextField entreeAgeUtilisateur;
  
  @FXML
  private TextField entreeAnneeFilm;
  
  @FXML
  private TextField entreeAuteurEvaluation;
  
  @FXML
  private TextField entreeEvaluationMoyenne;
  
  @FXML
  private TextField entreeGenresFilm;
  
  @FXML
  private TextField entreeMotDePasseUtilisateur;
  
  @FXML
  private TextField entreeNationaliteArtiste;
  
  @FXML
  private TextField entreeNomArtiste;
  
  @FXML
  private TextField entreeNomPrenomRealisateurFilm;
  
  @FXML
  private TextField entreeNomUtilisateur;
  
  @FXML
  private TextField entreePrenomArtiste;
  
  @FXML
  private TextField entreePrenomUtilisateur;
  
  @FXML
  private TextField entreePseudoUtilisateur;
  
  @FXML
  private TextField entreeTitreFilm;
  
  @FXML
  private Label labelListeFilms;
  
  @FXML
  private Label labelListeArtistes;
  
  @FXML
  private ListView<String> listeArtistes;
  
  @FXML
  private ListView<String> listeEvaluations;
  
  @FXML
  private ListView<String> listeFilms;
  
  @FXML
  private ListView<String> listeFilmsEnLocation;
  
  @FXML
  private ListView<String> listeGenresFilm;
  
  @FXML
  private ChoiceBox<Integer> listeNoteEvaluation;
  
  @FXML
  private TextArea texteCommentaire;
  
  @FXML
  private StackPane paneAffiche;
  
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
  void actionBoutonAfficherActeursFilmSelectionne(ActionEvent event) {
    listeArtistes.getItems().clear();
    String f = entreeTitreFilm.getText();
    if (f != "") {
      Film real = frontend.getFilm(f);
      
      Set<Artiste> s = real.get_acteurs();
      if (s == null) {
        listeArtistes.getItems().add("Aucun Acteurs trouvé!");
      } else {
        Iterator<Artiste> it = s.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          labelListeArtistes.setText("Liste de tous les Acteurs du Film :");
          listeArtistes.getItems().add(it.next().toString());
          trouve = true;
        }
        if (!trouve) {
          listeArtistes.getItems().add("Aucun Acteurs trouvé!");
        }
      }
    } else {
      afficherPopupErreur("Film inexistant ou invalide !");
      return;
    }
  }
  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
    
    labelListeArtistes.setText("La liste des acteurs");
    listeArtistes.getItems().clear();
    
    Set<Artiste> a = frontend.ensembleActeurs();
    
    if (!(a == null)) {
      
      for (Artiste art : a) {
        
        listeArtistes.getItems().add(art.toString());
        
      }
      
    } else {
      
      listeArtistes.getItems().add("Pas d'acteurs");
      
      
    }
    
  }
  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    
    labelListeArtistes.setText("La liste des réalisateurs");
    listeArtistes.getItems().clear();
    
    Set<Artiste> a = frontend.ensembleRealisateurs();
    
    if (!(a == null)) {
      
      for (Artiste art : a) {
        
        listeArtistes.getItems().add(art.toString());
        
      }
      
    } else {
      
      listeArtistes.getItems().add("Pas de realisateur");
      
      
    }
    
  }
  
  @FXML
  void actionBoutonAfficherFilmLoue(ActionEvent event) {
    String film = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
    String[] details = film.split(", ");
    entreeTitreFilm.setText(details[0]);
    entreeAnneeFilm.setText(details[3]);
    entreeAgeLimiteFilm.setText(details[4]);
    entreeNomPrenomRealisateurFilm.setText(details[1] + " " + details[2]);
    Film f = frontend.getFilm(details[0]);
    Set<Genre> g = f.get_genres();
    String str = "";
    
    if (g == null) {
      
      entreeGenresFilm.setText("Pas de genre");
      
    } else {
      
      Iterator<Genre> it = g.iterator();
      while (it.hasNext()) {
        
        str = str + it.next().toString() + ", ";
        
      }
      
      entreeGenresFilm.setText(str);
      
    }
    
    checkFilmLouable.setSelected(true);
  }
  
  @FXML
  void actionBoutonAfficherFilmRealisateurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String rea = entreeNomPrenomRealisateurFilm.getText();
    if (rea != "") {
      String[] details = rea.split(" ");
      Artiste real = frontend.getRealisateur(details[0], details[1]);
      
      Set<Film> s = frontend.ensembleFilmsRealisateur(real);
      if (s == null) {
        listeFilms.getItems().add("Aucun film trouvé!");
      } else {
        Iterator<Film> it = s.iterator();
        while (it.hasNext()) {
          labelListeFilms.setText("Liste de tous les films du Réalisateur :");
          listeFilms.getItems().add(it.next().toString());
        }
      }
    } else {
      afficherPopupErreur("Réalisateur inexistant ou invalide !");
      return;
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
      Artiste a =
          frontend.gestionnaireArtistes.getArtiste(details[0], details[1]);
      Set<Film> s = frontend.ensembleFilmsActeur(a);
      if (a.get_role() != Role.ACTEUR && a.get_role() != Role.BOTH) {
        afficherPopupErreur("L'artiste sélectionné n'est pas un acteur !");
        return;
      }
      if (s == null) {
        afficherPopupErreur("Aucun film !");
      } else {
        labelListeFilms.setText("La liste des Films de l'acteur :");
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
  void actionBoutonAfficherFilmsGenre(ActionEvent event) {
    listeFilms.getItems().clear();
    String g = listeGenresFilm.getSelectionModel().getSelectedItem();
    if (g != null) {
      Genre genr = null;
      for (Genre gen : Genre.values()) {
        if (gen.name().equals(g)) {
          genr = gen;
        }
      }
      
      Set<Film> s = frontend.ensembleFilmsGenre(genr);
      if (s == null) {
        listeFilms.getItems().add("Aucun Film trouvé!");
      } else {
        Iterator<Film> it = s.iterator();
        boolean trouve = false;
        while (it.hasNext()) {
          labelListeFilms.setText("Liste de tous les Films de ce genre :");
          listeFilms.getItems().add(it.next().toString());
          trouve = true;
        }
        if (!trouve) {
          listeArtistes.getItems().add("Aucun Film trouvé!");
        }
      }
    } else {
      afficherPopupErreur("Genre inexistant ou invalide !");
      return;
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
      Artiste a =
          frontend.gestionnaireArtistes.getArtiste(details[0], details[1]);
      Set<Film> s = frontend.ensembleFilmsRealisateur(a);
      if (a.get_role() != Role.REALISATEUR && a.get_role() != Role.BOTH) {
        afficherPopupErreur("L'artiste sélectionné n'est pas un Réalisateur !");
        return;
      }
      if (s == null) {
        afficherPopupErreur("Aucun film !");
      } else {
        labelListeFilms.setText("La liste des Films du Réalisateur :");
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
  void actionBoutonAfficherMonEvaluation(ActionEvent event) {
    listeEvaluations.getItems().clear();
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Veuillez sélectionner un film !");
      return;
    }
    String[] details = f.split(", ");
    Film film = frontend.getFilm(details[0]);
    Set<Evaluation> s = film.get_evaluations();
    if (s == null) {
      afficherPopupErreur("Aucune évaluation pour ce film !");
      return;
    }
    Iterator<Evaluation> it = s.iterator();
    boolean trouve = false;
    while (it.hasNext()) {
      Evaluation e = it.next();
      if (e != null) {
        if (e.get_auteur().equals(
            frontend.gestionnaireUtilisateurs.get_utilisateur().get_pseudo())) {
          listeEvaluations.getItems().add(e.toString());
          entreeAuteurEvaluation.setText(e.get_auteur());
          texteCommentaire.setText(e.get_com());
          listeNoteEvaluation.setValue(e.get_note());
          trouve = true;
        }
        
      }
    }
    if (!trouve) {
      listeEvaluations.getItems().add("Pas d'évaluation !");
      return;
    }
  }
  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    
    labelListeArtistes.setText("La liste des artistes");
    listeArtistes.getItems().clear();
    
    Set<Artiste> a = frontend.gestionnaireArtistes.get_artiste();
    
    if (!(a == null)) {
      
      for (Artiste art : a) {
        
        listeArtistes.getItems().add(art.toString());
        
      }
      
    } else {
      
      listeArtistes.getItems().add("Pas d'artiste");
      
      
    }
    
    
    
  }
  
  @FXML
  void actionBoutonAfficherTousFilms(ActionEvent event) {
    
    Set<Film> s = frontend.ensembleFilms();
    if (s == null) {
      listeFilms.getItems().add("Aucun film trouvé!");
    } else {
      Iterator<Film> it = s.iterator();
      while (it.hasNext()) {
        labelListeFilms.setText("Liste de tous les films :");
        listeFilms.getItems().add(it.next().toString());
      }
    }
  }
  
  @FXML
  void actionBoutonChercherActeur(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    
    if (nom != "" && prenom != "") {
      Artiste a = backend.gestionnaireArtistes.getActeur(nom, prenom);
      if (a == null) {
        afficherPopupErreur("Aucun Acteur correspondant !");
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
    if (titre != "") {
      Film f = frontend.getFilm(titre);
      entreeAgeLimiteFilm.setText(String.valueOf(f.get_age_min()));
      entreeNomPrenomRealisateurFilm.setText(f.get_realisateur().get_Nom() + " "
          + f.get_realisateur().get_Prenom());
      entreeAnneeFilm.setText(String.valueOf(f.get_annee()));
      
      Set<Genre> g = f.get_genres();
      String str = "";
      
      if (g == null) {
        
        entreeGenresFilm.setText("Pas de genre");
        
      } else {
        
        Iterator<Genre> it = g.iterator();
        while (it.hasNext()) {
          
          str = str + it.next().toString() + ", ";
          
        }
        
        entreeGenresFilm.setText(str);
        listeFilms.getItems().clear();
        listeFilms.getItems().add(f.toString());
        
        entreeEvaluationMoyenne.clear();
        listeEvaluations.getItems().clear();
        Set<Evaluation> s = f.get_evaluations();
        if (s == null) {
          listeEvaluations.getItems().add("Aucune évaluations !");
          return;
        }
        Iterator<Evaluation> ite = s.iterator();
        boolean trouve = false;
        while (ite.hasNext()) {
          Evaluation e = ite.next();
          if (e != null) {
            listeEvaluations.getItems().add(e.toString());
            trouve = true;
          }
        }
        if (!trouve) {
          listeEvaluations.getItems().add("Aucune évaluations !");
          return;
        }
        entreeEvaluationMoyenne
            .setText(String.valueOf(frontend.evaluationMoyenne(f)));
      }
    } else {
      afficherPopupErreur("Aucun Film correspondant !");
    }
  }
  
  @FXML
  void actionBoutonChercherRealisateur(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    
    if (nom != "" && prenom != "") {
      Artiste a = backend.gestionnaireArtistes.getRealisateur(nom, prenom);
      if (a == null) {
        afficherPopupErreur("Aucun Réalisateur correspondant !");
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
  void actionBoutonConnexion(ActionEvent event) {
    listeFilmsEnLocation.getItems().clear();
    String pseudo = entreePseudoUtilisateur.getText();
    String mdp = entreeMotDePasseUtilisateur.getText();
    
    boolean res = frontend.connexion(pseudo, mdp);
    if (res) {
      entreeNomUtilisateur.setText(frontend.gestionnaireUtilisateurs
          .get_utilisateur().get_infos().getNom());
      entreePrenomUtilisateur.setText(frontend.gestionnaireUtilisateurs
          .get_utilisateur().get_infos().getPrenom());
      entreeAgeUtilisateur
          .setText(String.valueOf(frontend.gestionnaireUtilisateurs
              .get_utilisateur().get_infos().getAge()));
      entreeAdresseUtilisateur.setText(frontend.gestionnaireUtilisateurs
          .get_utilisateur().get_infos().getAdresse());
      
      Set<Film> s;
      try {
        s = frontend.gestionnaireUtilisateurs.filmsEnLocation();
        if (s == null) {
          listeFilmsEnLocation.getItems().add("Aucun film loué");
        } else {
          Iterator<Film> it = s.iterator();
          while (it.hasNext()) {
            listeFilmsEnLocation.getItems().add(it.next().toString());
          }
        }
        
      } catch (NonConnecteException e) {
        listeFilmsEnLocation.getItems().add("Aucun film loué");
      }
      /*
       * if (s == null) {
       * listeFilmsEnLocation.getItems().add("Aucun film loué"); }
       */
      
      /*
       * if (!trouve) { listeFilmsEnLocation.getItems().add("Aucun film loué");
       * }
       */
    } else {
      afficherPopupErreur("La connexion à échouée !");
    }
  }
  
  @FXML
  void actionBoutonCreerMonEvaluation(ActionEvent event) {
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Aucun film valide !");
      return;
    }
    String[] details = f.split(", ");
    if (!frontend.getFilm(details[0]).get_etat()) {
      afficherPopupErreur("Vous ne louez pas ce film !");
      return;
    }
    Set<Evaluation> se = frontend.getFilm(details[0]).get_evaluations();
    if (se != null) {
      Iterator<Evaluation> ite = se.iterator();
      while (ite.hasNext()) {
        Evaluation ev = ite.next();
        if (ev == null) {
          return;
        }
        if (ev.get_auteur().equals(
            frontend.gestionnaireUtilisateurs.get_utilisateur().get_pseudo())) {
          afficherPopupErreur("Vous avez déjà évalué ce film !");
          return;
        }
      }
    }
    try {
      Set<Film> l = frontend.gestionnaireUtilisateurs.filmsEnLocation();
      if (texteCommentaire.getText() == ""
          || listeNoteEvaluation.getValue() == null) {
        afficherPopupErreur("Veuillez remplir les champs !");
        return;
      }
      if (l.contains(frontend.getFilm(details[0]))) {
        Evaluation e = new Evaluation(listeNoteEvaluation.getValue(),
            texteCommentaire.getText(),
            frontend.gestionnaireUtilisateurs.get_utilisateur(),
            frontend.getFilm(details[0]));
        try {
          frontend.ajouterEvaluation(frontend.getFilm(details[0]), e);
          listeEvaluations.getItems().clear();
          listeEvaluations.getItems().add(e.toString());
        } catch (LocationException e1) {
          afficherPopupErreur("echec de l'ajout de l'évaluation !");
          return;
        }
        
      }
    } catch (NonConnecteException e) {
      afficherPopupErreur("Le film n'est pas en location!");
      return;
    }
  }
  
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
    try {
      frontend.deconnexion();
      listeFilmsEnLocation.getItems().clear();
      entreeNomUtilisateur.setText("");
      entreePrenomUtilisateur.setText("");
      entreeAgeUtilisateur.setText("");
      entreeAdresseUtilisateur.setText("");
      entreePseudoUtilisateur.setText("");
      entreeMotDePasseUtilisateur.setText("");
    } catch (NonConnecteException e) {
      afficherPopupErreur("Aucun utilisateur connecté !");
    }
  }
  
  @FXML
  void actionBoutonFinLocation(ActionEvent event) {
    String film = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
    String[] details = film.split(", ");
    listeFilmsEnLocation.getItems().remove(frontend.getFilm(details[0]));
    try {
      frontend.gestionnaireUtilisateurs
          .finLocationFilm(frontend.getFilm(details[0]));
      Set<Film> s;
      listeFilmsEnLocation.getItems().clear();
      
      s = frontend.gestionnaireUtilisateurs.filmsEnLocation();
      
      if (s == null) {
        listeFilmsEnLocation.getItems().add("Aucun film loué");
      } else {
        Iterator<Film> it = s.iterator();
        while (it.hasNext()) {
          listeFilmsEnLocation.getItems().add(it.next().toString());
        }
      }
    } catch (NonConnecteException e) {
      afficherPopupErreur("Aucun utilisateur connecté !");
    } catch (LocationException e) {
      afficherPopupErreur("Fin de Location échoué !");
    }
  }
  
  @FXML
  void actionBoutonInscription(ActionEvent event) {
    String nom = entreeNomUtilisateur.getText();
    String prenom = entreePrenomUtilisateur.getText();
    String adresse = entreeAdresseUtilisateur.getText();
    int age;
    if (entreeAgeUtilisateur.getText() != "") {
      age = Integer.parseInt(entreeAgeUtilisateur.getText());
    } else {
      afficherPopupErreur(
          "Les informations personnelles ne sont pas bien précisées");
      return;
    }
    
    
    String pseudo = entreePseudoUtilisateur.getText();
    String mdp = entreeMotDePasseUtilisateur.getText();
    
    InformationPersonnelle ip =
        new InformationPersonnelle(nom, prenom, adresse, age);
    int res = frontend.inscription(pseudo, mdp, ip);
    
    if (res == 1) {
      afficherPopupErreur("Pseudo déjà utilisé");
    } else {
      if (res == 2) {
        afficherPopupErreur("Pseudo ou mots de passe vide");
      } else {
        if (res == 3) {
          afficherPopupErreur(
              "Les informations personnelles ne sont pas bien précisées");
        } else {
          afficherPopupInformation("Inscription réussie !");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonLouerFilmSelectionne(ActionEvent event) {
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (film != null) {
      String[] details = film.split(", ");
      Film f = frontend.getFilm(details[0]);
      
      boolean x = f.get_etat();
      if (x) {
        try {
          frontend.gestionnaireUtilisateurs.louerFilm(f);
          Set<Film> s;
          listeFilmsEnLocation.getItems().clear();
          
          s = frontend.gestionnaireUtilisateurs.filmsEnLocation();
          
          if (s == null) {
            listeFilmsEnLocation.getItems().add("Aucun film loué");
          } else {
            Iterator<Film> it = s.iterator();
            while (it.hasNext()) {
              listeFilmsEnLocation.getItems().add(it.next().toString());
            }
          }
        } catch (NonConnecteException e) {
          afficherPopupErreur("Veuillez vous connecter !");
        } catch (LocationException e) {
          afficherPopupErreur(
              "Vous n'avez as l'âge suffisant ou vous louez déjà 3 films ou vous louez déjà ce film !");
        }
      } else {
        afficherPopupErreur("Le film n'est pas louable");
        return;
      }
    } else {
      afficherPopupErreur("Le film n'est pas valide");
      return;
    }
  }
  
  
  @FXML
  void actionBoutonModifierMonEvaluation(ActionEvent event) {
    if (frontend.gestionnaireUtilisateurs.get_utilisateur() == null) {
      afficherPopupErreur("Veuillez vous connecter !");
      return;
    }
    Utilisateur user = frontend.gestionnaireUtilisateurs.get_utilisateur();
    String f = listeEvaluations.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Veuillez sélectionner une évaluation à modifier !");
      return;
    }
    String[] details = f.split(", ");
    if (details.length < 2) {
      afficherPopupErreur("Évaluation sélectionnée invalide !");
    }
    Set<Evaluation> s =
        frontend.ensembleEvaluationsFilm(frontend.getFilm(details[3]));
    if (s == null) {
      afficherPopupErreur("Ce film n'a aucune évaluations !");
      return;
    }
    Iterator<Evaluation> it = s.iterator();
    boolean trouve = false;
    Evaluation e = null;
    while (it.hasNext() && !trouve) {
      e = it.next();
      if (e != null) {
        if (e.get_auteur().equals(user.get_pseudo())) {
          trouve = true;
        }
      }
    }
    if (trouve) {
      if (listeNoteEvaluation.getValue() == null
          || texteCommentaire.getText() == "") {
        afficherPopupErreur("Veuillez remplir les champs !");
        return;
      }
      e.set_note(listeNoteEvaluation.getValue());
      e.set_com(texteCommentaire.getText());
      try {
        frontend.modifierEvaluation(frontend.getFilm(details[3]), e);
      } catch (NonConnecteException e1) {
        // TODO Auto-generated catch block
        afficherPopupErreur("Veuillez vous connecter !");
      } catch (LocationException e1) {
        // TODO Auto-generated catch block
        afficherPopupErreur(e1.getMessage());
      }
    }
  }
  
  @FXML
  void actionSelectionArtiste(MouseEvent event) {
    
    String acteur = listeArtistes.getSelectionModel().getSelectedItem();
    
    if (acteur == null) {
      return;
    } else {
      String[] details = acteur.split(", ");
      entreeNationaliteArtiste.setText(details[2]);
      entreeNomArtiste.setText(details[0]);
      entreePrenomArtiste.setText(details[1]);
    }
    
    
  }
  
  @FXML
  void actionSelectionEvaluation(MouseEvent event) {
    String f = listeEvaluations.getSelectionModel().getSelectedItem();
    if (f == null) {
      afficherPopupErreur("Evaluation invalide !");
      return;
    }
    String[] details = f.split(", ");
    if (details.length < 2) {
      afficherPopupErreur("Veuillez sélectionner une évaluation valide !");
      return;
    }
    entreeAuteurEvaluation.setText(details[0]);
    texteCommentaire.setText(details[1]);
    listeNoteEvaluation.setValue(Integer.parseInt(details[2]));
  }
  
  @FXML
  void actionSelectionFilm(MouseEvent event) {
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (film != null) {
      String[] details = film.split(", ");
      entreeTitreFilm.setText(details[0]);
      entreeAnneeFilm.setText(details[3]);
      entreeAgeLimiteFilm.setText(details[4]);
      entreeNomPrenomRealisateurFilm.setText(details[1] + " " + details[2]);
      Film f = frontend.getFilm(details[0]);
      checkFilmLouable.setSelected(f.get_etat());
      
      entreeEvaluationMoyenne.clear();
      listeEvaluations.getItems().clear();
      Set<Evaluation> s = f.get_evaluations();
      if (s == null) {
        listeEvaluations.getItems().add("Aucune évaluations !");
        return;
      }
      Iterator<Evaluation> ite = s.iterator();
      boolean trouve = false;
      while (ite.hasNext()) {
        Evaluation e = ite.next();
        if (e != null) {
          listeEvaluations.getItems().add(e.toString());
          trouve = true;
        }
      }
      if (!trouve) {
        listeEvaluations.getItems().add("Aucune évaluations !");
        return;
      }
      entreeEvaluationMoyenne
          .setText(String.valueOf(frontend.evaluationMoyenne(f)));
      
      Set<Genre> g = f.get_genres();
      String str = "";
      if (g == null) {
        
        entreeGenresFilm.setText("Pas de genre");
        
      } else {
        
        Iterator<Genre> it = g.iterator();
        while (it.hasNext()) {
          
          str = str + it.next().toString() + ", ";
          
        }
        
        entreeGenresFilm.setText(str);
      }
    } else {
      afficherPopupErreur("veuillez séléctionner un film valide !");
      return;
    }
    
  }
  
  @FXML
  void initialize() {
    listeGenresFilm.getItems().add(Genre.Action.toString());
    listeGenresFilm.getItems().add(Genre.Aventure.toString());
    listeGenresFilm.getItems().add(Genre.Comedie.toString());
    listeGenresFilm.getItems().add(Genre.ComedieDramatique.toString());
    listeGenresFilm.getItems().add(Genre.Romance.toString());
    listeGenresFilm.getItems().add(Genre.Drame.toString());
    listeGenresFilm.getItems().add(Genre.Fantastique.toString());
    listeGenresFilm.getItems().add(Genre.Guerre.toString());
    listeGenresFilm.getItems().add(Genre.Policier.toString());
    listeGenresFilm.getItems().add(Genre.Thriller.toString());
    listeGenresFilm.getItems().add(Genre.Horreur.toString());
    listeGenresFilm.getItems().add(Genre.Western.toString());
    listeGenresFilm.getItems().add(Genre.ScienceFiction.toString());
    listeGenresFilm.getItems().add(Genre.Biopic.toString());
    listeGenresFilm.getItems().add(Genre.Animation.toString());
    listeGenresFilm.getItems().add(Genre.Musical.toString());
    listeGenresFilm.getItems().add(Genre.Historique.toString());
    
    listeNoteEvaluation.getItems().add(0);
    listeNoteEvaluation.getItems().add(1);
    listeNoteEvaluation.getItems().add(2);
    listeNoteEvaluation.getItems().add(3);
    listeNoteEvaluation.getItems().add(4);
    listeNoteEvaluation.getItems().add(5);
    listeNoteEvaluation.getItems().add(6);
    listeNoteEvaluation.getItems().add(7);
    listeNoteEvaluation.getItems().add(8);
    listeNoteEvaluation.getItems().add(9);
    listeNoteEvaluation.getItems().add(10);
    
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
    user = u.get_utilisateur();
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
    user = u.get_utilisateur();
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
