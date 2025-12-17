package com.tp2film;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuPrincipalController {

    public void ouvrirGestionFilms(ActionEvent event) {
        ouvrirFenetre("films-view.fxml", "Films");
    }

    public void ouvrirGestionActeurs(ActionEvent event) {
        ouvrirFenetre("people-view.fxml", "Personnes");
    }

    private void ouvrirFenetre(String fxml, String titre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/" + fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des " + titre);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/clapperboard.png")));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // 👈 rend la fenêtre modale
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
