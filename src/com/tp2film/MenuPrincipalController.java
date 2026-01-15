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
            // Chargement du fichier FXML depuis le dossier ressources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ressources/" + fxml));
            Parent root = loader.load();

            // Création d'une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Gestion des " + titre);

            // Ajout de l'icône de l'application
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/ressources/clapperboard.png")));

            // Configuration de la scène et affichage
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale (bloque l'accès aux autres fenêtres)
            stage.show();

        } catch (Exception e) {
            // Affichage des erreurs dans la console (pour le débogage)
            e.printStackTrace();
        }
    }
}
