module tp2.film {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    opens com.tp2film to javafx.fxml;
    exports com.tp2film;
}

