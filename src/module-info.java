module tp2.film {
    // Modules JavaFX nécessaires pour l'interface graphique
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Modules MongoDB pour la gestion de la base de données
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    // Permet à JavaFX d'accéder au package com.tp2film par réflexion (pour FXML)
    opens com.tp2film to javafx.fxml;

    // Exporte le package com.tp2film pour qu'il soit accessible
    exports com.tp2film;
}

