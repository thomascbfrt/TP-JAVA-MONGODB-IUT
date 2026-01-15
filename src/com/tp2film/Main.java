package com.tp2film;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the database with sample data if needed.
        MongoDbInitializer.init();

        // Chargement du fichier FXML pour l'interface du menu principal
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressources/menuprincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Configuration de la fenêtre principale
        stage.setTitle("TP2 - Filmographie");

        // Ajout de l'icône de l'application
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/ressources/clapperboard.png")));

        // Affichage de la fenêtre
        stage.setScene(scene);
        stage.show();
    }

	public void __start_back(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
