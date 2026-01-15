package com.tp2film;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
			try (MongoClient client = MongoClients.create(DB_URL)) {
				MongoDatabase database = client.getDatabase(DB_SCHEMA);
				MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);
				boolean useFrench = usesFrenchFields(collection);

				Document query = new Document();
				String titleFilter = filtreTitreField.getText().trim();
				String yearFilter = filtreAnneeField.getText().trim();
				String genreFilter = filtreGenreField.getText().trim();

				if (!titleFilter.isEmpty()) {
					query.append(useFrench ? "titre" : "title",
							new Document("$regex", titleFilter).append("$options", "i"));
				}

				if (!yearFilter.isEmpty()) {
					int yearValue;
					try {
						yearValue = Integer.parseInt(yearFilter);
					} catch (NumberFormatException e) {
						showAlert("Erreur", "Annee invalide.");
						return;
					}
					query.append(useFrench ? "annee" : "year", yearValue);
				}

				if (!genreFilter.isEmpty()) {
					query.append(useFrench ? "categorie" : "genre",
							new Document("$regex", genreFilter).append("$options", "i"));
				}

				FindIterable<Document> results = collection.find(query);
				for (Document doc : results) {
					films.add(documentToFilm(doc));
				}
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
		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_SCHEMA);
			MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);
			boolean useFrench = usesFrenchFields(collection);

			Document filmDoc = buildFilmDocument(useFrench, true);
			if (filmDoc == null) {
				return;
			}

			collection.insertOne(filmDoc);
			showInfo("OK", "Film ajoute.");
			chargerFilms();
		} catch (MongoException e) {
			showAlert("Erreur", "Impossible d'ajouter le film: " + e.getMessage());
		}
	}

	@FXML
	public void supprimerFilm() {
		Film film = filmsTable.getSelectionModel().getSelectedItem();
		if (film == null) {
			showInfo("Info", "Selectionnez un film.");
			return;
		}

		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_SCHEMA);
			MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);

			ObjectId id = new ObjectId(film.getId());
			DeleteResult result = collection.deleteOne(new Document("_id", id));
			if (result.getDeletedCount() == 0) {
				showAlert("Erreur", "Suppression impossible.");
			} else {
				showInfo("OK", "Film supprime.");
				chargerFilms();
			}
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", "Identifiant invalide.");
		} catch (MongoException e) {
			showAlert("Erreur", "Impossible de supprimer le film: " + e.getMessage());
		}
	}

	@FXML
	public void modifierFilm() {
		Film film = filmsTable.getSelectionModel().getSelectedItem();
		if (film == null) {
			showInfo("Info", "Selectionnez un film.");
			return;
		}

		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_SCHEMA);
			MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);
			boolean useFrench = usesFrenchFields(collection);

			Document updateDoc = buildFilmDocument(useFrench, false);
			if (updateDoc == null) {
				return;
			}

			ObjectId id = new ObjectId(film.getId());
			Document filter = new Document("_id", id);
			Document update = new Document("$set", updateDoc);
			UpdateResult result = collection.updateOne(filter, update);
			if (result.getMatchedCount() == 0) {
				showAlert("Erreur", "Film non trouve.");
			} else {
				showInfo("OK", "Film modifie.");
				chargerFilms();
			}
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", "Identifiant invalide.");
		} catch (MongoException e) {
			showAlert("Erreur", "Impossible de modifier le film: " + e.getMessage());
		}
	}

	private Film documentToFilm(Document doc) {
		String id = "";
		ObjectId objectId = doc.getObjectId("_id");
		if (objectId != null) {
			id = objectId.toHexString();
		}

		String title = getString(doc, "title", "titre");
		int year = getInt(doc, "year", "annee");
		String genre = getString(doc, "genre", "categorie");
		int entries = getInt(doc, "entries", "entrees");
		int length = getInt(doc, "length", "duree");
		String nationality = getString(doc, "nationality", "nationalite");
		String story = getString(doc, "story", "resume");
		Date createdAt = getDate(doc, "created_at", "createdAt", "dateAjout");

		Film film = new Film(id, title, year, genre, entries, length, nationality, story, createdAt);

		Document directorDoc = doc.get("director", Document.class);
		if (directorDoc == null) {
			directorDoc = doc.get("realisateur", Document.class);
		}
		if (directorDoc != null) {
			String firstname = getString(directorDoc, "firstname", "prenom");
			String lastname = getString(directorDoc, "lastname", "nom");
			String role = getString(directorDoc, "role", "role");
			film.set_director(new People(firstname, lastname, normalizeRole(role)));
		}

		List<Document> actorsDocs = doc.getList("actors", Document.class);
		if (actorsDocs == null) {
			actorsDocs = doc.getList("acteurs", Document.class);
		}
		if (actorsDocs != null) {
			for (Document actorDoc : actorsDocs) {
				String firstname = getString(actorDoc, "firstname", "prenom");
				String lastname = getString(actorDoc, "lastname", "nom");
				String role = getString(actorDoc, "role", "role");
				film.add_actor(new People(firstname, lastname, normalizeRole(role)));
			}
		}

		return film;
	}

	private Document buildFilmDocument(boolean useFrench, boolean includeCreatedAt) {
		String title = titreField.getText().trim();
		if (title.isEmpty()) {
			showAlert("Erreur", "Titre obligatoire.");
			return null;
		}

		String yearText = anneeField.getText().trim();
		if (yearText.isEmpty()) {
			showAlert("Erreur", "Annee obligatoire.");
			return null;
		}
		int yearValue;
		try {
			yearValue = Integer.parseInt(yearText);
		} catch (NumberFormatException e) {
			showAlert("Erreur", "Annee invalide.");
			return null;
		}

		String genre = genreField.getText().trim();
		if (genre.isEmpty()) {
			showAlert("Erreur", "Genre obligatoire.");
			return null;
		}

		Integer entries = parseOptionalInt(entreesField.getText(), "Entrees");
		if (entries == null) {
			return null;
		}

		Integer length = parseOptionalInt(dureeField.getText(), "Duree");
		if (length == null) {
			return null;
		}

		String nationality = nationaliteField.getText().trim();
		String story = resumeField.getText().trim();

		String directorFirstname = realisateurPrenomField.getText().trim();
		String directorLastname = realisateurNomField.getText().trim();
		if (directorFirstname.isEmpty() && directorLastname.isEmpty()) {
			showAlert("Erreur", "Realisateur obligatoire.");
			return null;
		}

		Document director = new Document(useFrench ? "prenom" : "firstname", directorFirstname)
				.append(useFrench ? "nom" : "lastname", directorLastname)
				.append("role", "director");

		List<Document> actors = new ArrayList<Document>();
		addActorFromFields(actors, useFrench, acteur1PrenomField, acteur1NomField, acteur1RoleField);
		addActorFromFields(actors, useFrench, acteur2PrenomField, acteur2NomField, acteur2RoleField);
		addActorFromFields(actors, useFrench, acteur3PrenomField, acteur3NomField, acteur3RoleField);

		Document filmDoc = new Document();
		filmDoc.append(useFrench ? "titre" : "title", title);
		filmDoc.append(useFrench ? "annee" : "year", yearValue);
		filmDoc.append(useFrench ? "categorie" : "genre", genre);
		filmDoc.append(useFrench ? "entrees" : "entries", entries.intValue());
		filmDoc.append(useFrench ? "duree" : "length", length.intValue());
		filmDoc.append(useFrench ? "nationalite" : "nationality", nationality);
		filmDoc.append(useFrench ? "resume" : "story", story);
		filmDoc.append(useFrench ? "realisateur" : "director", director);
		filmDoc.append(useFrench ? "acteurs" : "actors", actors);

		Date now = new Date();
		if (includeCreatedAt) {
			if (useFrench) {
				filmDoc.append("dateAjout", now);
				filmDoc.append("createdAt", now);
				filmDoc.append("updatedAt", now);
			} else {
				filmDoc.append("created_at", now);
				filmDoc.append("updated_at", now);
			}
		} else {
			if (useFrench) {
				filmDoc.append("updatedAt", now);
			} else {
				filmDoc.append("updated_at", now);
			}
		}

		return filmDoc;
	}

	private void addActorFromFields(List<Document> actors, boolean useFrench,
			TextField prenomField, TextField nomField, ChoiceBox<String> roleField) {
		String firstname = prenomField.getText().trim();
		String lastname = nomField.getText().trim();
		if (firstname.isEmpty() && lastname.isEmpty()) {
			return;
		}

		String role = roleField.getValue();
		if (role == null || role.trim().isEmpty()) {
			role = "secondaire";
		} else {
			role = role.trim().toLowerCase();
		}

		Document actor = new Document(useFrench ? "prenom" : "firstname", firstname)
				.append(useFrench ? "nom" : "lastname", lastname)
				.append("role", role);
		actors.add(actor);
	}

	private Integer parseOptionalInt(String text, String label) {
		if (text == null) {
			return Integer.valueOf(0);
		}
		String trimmed = text.trim();
		if (trimmed.isEmpty()) {
			return Integer.valueOf(0);
		}
		try {
			return Integer.valueOf(Integer.parseInt(trimmed));
		} catch (NumberFormatException e) {
			showAlert("Erreur", label + " invalide.");
			return null;
		}
	}

	private boolean usesFrenchFields(MongoCollection<Document> collection) {
		Document sample = collection.find().first();
		if (sample == null) {
			return false;
		}
		return sample.containsKey("titre") || sample.containsKey("annee") || sample.containsKey("categorie");
	}

	private String getString(Document doc, String key1, String key2) {
		String value = doc.getString(key1);
		if (value == null) {
			value = doc.getString(key2);
		}
		return value == null ? "" : value;
	}

	private int getInt(Document doc, String key1, String key2) {
		Integer value = doc.getInteger(key1);
		if (value == null) {
			value = doc.getInteger(key2);
		}
		return value == null ? 0 : value.intValue();
	}

	private Date getDate(Document doc, String key1, String key2, String key3) {
		Date value = doc.getDate(key1);
		if (value == null) {
			value = doc.getDate(key2);
		}
		if (value == null) {
			value = doc.getDate(key3);
		}
		return value;
	}

	private String normalizeRole(String role) {
		if (role == null) {
			return "";
		}
		return role.trim().toLowerCase();
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
