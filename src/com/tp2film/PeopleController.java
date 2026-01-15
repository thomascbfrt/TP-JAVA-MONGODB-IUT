package com.tp2film;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PeopleController {

	@FXML
	private TableView<People> peopleTable;
	@FXML
	private TableColumn<People, String> colNom;
	@FXML
	private TableColumn<People, String> colPrénom;
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

	private static final String DB_URL = "mongodb://localhost:27017";
	private static final String DB_SCHEMA = "filmographie";
	private static final String DB_COLLECTION = "movies";

	private final ObservableList<People> people = FXCollections.observableArrayList();
	private final ObservableList<Film> films = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		colNom.setCellValueFactory(new PropertyValueFactory<>("lastname"));
		colPrénom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
		peopleTable.setItems(people);

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colTitre.setCellValueFactory(new PropertyValueFactory<>("title"));
		colAnnee.setCellValueFactory(new PropertyValueFactory<>("year"));
		colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		colGenre.setText("Role");
		filmsTable.setItems(films);

		chargerPeople();
	}

	@FXML
	public void chargerPeople() {
		people.clear();
		films.clear();
		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_SCHEMA);
			MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);
			boolean useFrench = usesFrenchFields(collection);

			Map<String, People> unique = new LinkedHashMap<String, People>();
			for (Document doc : collection.find()) {
				Document directorDoc = doc.get(useFrench ? "realisateur" : "director", Document.class);
				if (directorDoc != null) {
					String firstname = getString(directorDoc, useFrench ? "prenom" : "firstname");
					String lastname = getString(directorDoc, useFrench ? "nom" : "lastname");
					addPerson(unique, firstname, lastname);
				}

				List<Document> actorDocs = doc.getList(useFrench ? "acteurs" : "actors", Document.class);
				if (actorDocs != null) {
					for (Document actorDoc : actorDocs) {
						String firstname = getString(actorDoc, useFrench ? "prenom" : "firstname");
						String lastname = getString(actorDoc, useFrench ? "nom" : "lastname");
						addPerson(unique, firstname, lastname);
					}
				}
			}

			people.addAll(unique.values());
		} catch (MongoException e) {
			showAlert("oups", "chargement rate");
		}
	}

	@FXML
	public void remplirFilms() {
		People selected = peopleTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			return;
		}

		films.clear();
		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_SCHEMA);
			MongoCollection<Document> collection = database.getCollection(DB_COLLECTION);
			boolean useFrench = usesFrenchFields(collection);

			for (Document doc : collection.find()) {
				String role = roleForPerson(doc, selected, useFrench);
				if (role != null) {
					ObjectId objectId = doc.getObjectId("_id");
					String id = objectId == null ? "" : objectId.toHexString();
					String title = getString(doc, useFrench ? "titre" : "title");
					int year = getInt(doc, useFrench ? "annee" : "year");
					Film film = new Film(id, title, year, role, 0, 0, "", "");
					films.add(film);
				}
			}
		} catch (MongoException e) {
			showAlert("oups", "films pas charges");
		}
	}

	private void addPerson(Map<String, People> unique, String firstname, String lastname) {
		String first = firstname == null ? "" : firstname.trim();
		String last = lastname == null ? "" : lastname.trim();
		if (first.isEmpty() && last.isEmpty()) {
			return;
		}
		String key = (first + "|" + last).toLowerCase();
		if (!unique.containsKey(key)) {
			unique.put(key, new People(first, last, ""));
		}
	}

	private String roleForPerson(Document doc, People person, boolean useFrench) {
		String personFirst = person.getFirstname();
		String personLast = person.getLastname();

		Document directorDoc = doc.get(useFrench ? "realisateur" : "director", Document.class);
		if (directorDoc != null) {
			String directorFirst = getString(directorDoc, useFrench ? "prenom" : "firstname");
			String directorLast = getString(directorDoc, useFrench ? "nom" : "lastname");
			if (samePerson(personFirst, personLast, directorFirst, directorLast)) {
				return "Realisateur";
			}
		}

		List<Document> actorDocs = doc.getList(useFrench ? "acteurs" : "actors", Document.class);
		if (actorDocs != null) {
			for (Document actorDoc : actorDocs) {
				String actorFirst = getString(actorDoc, useFrench ? "prenom" : "firstname");
				String actorLast = getString(actorDoc, useFrench ? "nom" : "lastname");
				if (samePerson(personFirst, personLast, actorFirst, actorLast)) {
					String rawRole = getString(actorDoc, "role");
					return formatActorRole(rawRole);
				}
			}
		}

		return null;
	}

	private boolean samePerson(String firstA, String lastA, String firstB, String lastB) {
		String fa = firstA == null ? "" : firstA.trim().toLowerCase();
		String la = lastA == null ? "" : lastA.trim().toLowerCase();
		String fb = firstB == null ? "" : firstB.trim().toLowerCase();
		String lb = lastB == null ? "" : lastB.trim().toLowerCase();
		return fa.equals(fb) && la.equals(lb);
	}

	private String formatActorRole(String role) {
		if (role == null) {
			return "Acteur";
		}
		String normalized = role.trim().toLowerCase();
		if ("principal".equals(normalized)) {
			return "Acteur principal";
		}
		if ("secondaire".equals(normalized) || "secondary".equals(normalized)) {
			return "Acteur secondaire";
		}
		if ("director".equals(normalized) || "realisateur".equals(normalized)) {
			return "Realisateur";
		}
		return "Acteur";
	}

	private boolean usesFrenchFields(MongoCollection<Document> collection) {
		Document sample = collection.find().first();
		if (sample == null) {
			return false;
		}
		return sample.containsKey("titre") || sample.containsKey("annee") || sample.containsKey("categorie");
	}

	private String getString(Document doc, String key) {
		String value = doc.getString(key);
		return value == null ? "" : value;
	}

	private int getInt(Document doc, String key) {
		Integer value = doc.getInteger(key);
		return value == null ? 0 : value.intValue();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
