package com.tp2film;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class FilmsController {
	@FXML
	private TableView<Film> filmsTable;
	@FXML
	private TableColumn<Film, String> colId;
	@FXML
	private TableColumn<Film, String> colTitre;
	@FXML
	private TableColumn<Film, String> colAnnee;
	@FXML
	private TableColumn<Film, String> colGenre;
	@FXML
	private TextField filtreTitreField, filtreAnneeField, filtreGenreField;
	@FXML
	private TextField titreField, anneeField, genreField;
	@FXML
	private TextField realisateurNomField, realisateurPrenomField;
	@FXML
	private TextField acteur1PrenomField, acteur1NomField;
	@FXML
	private ChoiceBox<String> acteur1RoleField;
	@FXML
	private TextField acteur2PrenomField, acteur2NomField;
	@FXML
	private ChoiceBox<String> acteur2RoleField;
	@FXML
	private TextField acteur3PrenomField, acteur3NomField;
	@FXML
	private ChoiceBox<String> acteur3RoleField;
	@FXML
	private TextField entreesField, dureeField, nationaliteField;
	@FXML
	private TextArea resumeField;

	private ObservableList<Film> films = FXCollections.observableArrayList();

	private static final String DB_URL = "mongodb://localhost:27017";
	private static final String DB_SCHEMA = "filmographie";
	private static final String DB_COLLECTION = "movies";

	@FXML
	public void initialize() {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colTitre.setCellValueFactory(new PropertyValueFactory<>("title"));
		colAnnee.setCellValueFactory(new PropertyValueFactory<>("year"));
		colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		filmsTable.setItems(films);
		
		ObservableList<String> roles = FXCollections.observableArrayList("principal", "secondaire");
		acteur1RoleField.setItems(roles);
		acteur2RoleField.setItems(roles);
		acteur3RoleField.setItems(roles);
		chargerFilms();
	}

	@FXML
	public void chargerFilms() {
		films.clear();
		try {

			// Filtres
			String title = null ;
			int year = 0 ;
			String genre = null ;
			if (! filtreTitreField.getText().isEmpty()) title = filtreTitreField.getText() ;
			if (! filtreAnneeField.getText().isEmpty()) year = Integer.parseInt(filtreAnneeField.getText()) ;
			if (! filtreGenreField.getText().isEmpty()) genre = filtreGenreField.getText() ;

			for (int i = 0; i < 10; ++i) {
				Film film = new Film("#" + i, "title " + i, 2000 + i, "genre " + i, i, i, "nationality " + i,
						"story " + i + " 🎅🎅🎅 !", new Date());
				film.add_actor(new People("firstname " + i, "lastname " + i, "role " + i));
				films.add(film);
			}

		} catch (Exception e) {
			showAlert("Erreur de chargement", "Unable to load due to an error: " + e);
		}
	}

	@FXML
	public void remplirChamps() {
		
		// Clear all
		anneeField.clear();
		genreField.clear();
		entreesField.clear();
		dureeField.clear();
		nationaliteField.clear();
		resumeField.clear();
		realisateurNomField.clear();
		realisateurPrenomField.clear();
		acteur1PrenomField.clear();
		acteur1NomField.clear();
		acteur1RoleField.setValue("");
		acteur2PrenomField.clear();
		acteur2NomField.clear();
		acteur2RoleField.setValue("");
		acteur3PrenomField.clear();
		acteur3NomField.clear();
		acteur3RoleField.setValue("");
		
		Film film = filmsTable.getSelectionModel().getSelectedItem();
		if (film != null) {
			titreField.setText(film.getTitle());
			if (film.getDirector() != null) {
				realisateurNomField.setText(film.getDirector().getLastname());
				realisateurPrenomField.setText(film.getDirector().getFirstname());
			}
			anneeField.setText("" + film.getYear());
			genreField.setText(film.getGenre());
			entreesField.setText("" + film.getEntries());
			dureeField.setText("" + film.getLength());
			nationaliteField.setText(film.getNationality());
			resumeField.setText(film.getStory());
			
			// @todo : quick and dirty
			ArrayList<People>actors = film.getActors();
			if (actors.size() > 0) {
				acteur1PrenomField.setText(actors.get(0).getFirstname()) ;
				acteur1NomField.setText(actors.get(0).getLastname()) ;
				acteur1RoleField.setValue(actors.get(0).getRole());
			}
			if (actors.size() > 1) {
				acteur2PrenomField.setText(actors.get(1).getFirstname()) ;
				acteur2NomField.setText(actors.get(1).getLastname()) ;
				acteur2RoleField.setValue(actors.get(1).getRole());
			}
			if (actors.size() > 2) {
				acteur3PrenomField.setText(actors.get(2).getFirstname()) ;
				acteur3NomField.setText(actors.get(2).getLastname()) ;
				acteur3RoleField.setValue(actors.get(2).getRole());
			}
		}
	}

	@FXML
	public void ajouterFilm() {
		showInfo("ToDo", "Ajout");
	}

	@FXML
	public void supprimerFilm() {
		showInfo("ToDo", "Suppression");
	}

	@FXML
	public void modifierFilm() {
		showInfo("ToDo", "Modification");
	}

	private void showInfo(String titre, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(titre);
		alert.setContentText(message);
		alert.showAndWait();
	}
	private void showAlert(String titre, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titre);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
