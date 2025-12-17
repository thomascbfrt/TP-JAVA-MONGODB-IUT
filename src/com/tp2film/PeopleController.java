package com.tp2film;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PeopleController {

	@FXML
	private TableView<Film> peopleTable;
	@FXML
	private TableColumn<Film, String> colNom;
	@FXML
	private TableColumn<Film, String> colPrénom;
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

	@FXML
	public void chargerPeople() {
	}

	@FXML
	public void remplirFilms() {
	}
}
