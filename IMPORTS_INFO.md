# Liste des imports nécessaires pour le projet TP2 FILM

## 📦 Dépendances du projet

### JavaFX 21.0.1
JavaFX est la bibliothèque d'interface graphique utilisée pour créer l'application.

**Modules requis:**
- `javafx-controls` - Boutons, tableaux, champs de texte, etc.
- `javafx-fxml` - Chargement des fichiers FXML (interfaces)
- `javafx-graphics` - Rendu graphique, images, scènes

**Imports utilisés dans le code:**
```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
```

### MongoDB Driver 4.11.1
Le driver MongoDB permet de se connecter à la base de données MongoDB.

**Modules requis:**
- `mongodb-driver-sync` - Client MongoDB synchrone
- `bson` - Support des documents BSON (format MongoDB)

**Imports utilisés dans le code:**
```java
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
```

### Java Standard Library
**Imports Java standard:**
```java
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
```

## 📋 Répartition des imports par fichier

### Main.java
```java
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
```

### MenuPrincipalController.java
```java
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
```

### FilmsController.java
```java
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
```

### PeopleController.java
```java
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
```
*(Ce fichier aura besoin d'imports MongoDB quand l'implémentation sera complète)*

### Film.java
```java
import java.util.ArrayList;
import java.util.Date;
```
*(Pas d'imports de bibliothèques externes)*

### People.java
*(Pas d'imports nécessaires)*

## ⚙️ Configuration Maven (pom.xml)

Le fichier `pom.xml` a été créé avec toutes les dépendances nécessaires:

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-graphics</artifactId>
        <version>21.0.1</version>
    </dependency>

    <!-- MongoDB -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.11.1</version>
    </dependency>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>bson</artifactId>
        <version>4.11.1</version>
    </dependency>
</dependencies>
```

## 🚀 Étapes pour charger les dépendances

### Dans IntelliJ IDEA:

1. **Ouvrir le projet**
   - File → Open
   - Sélectionnez le dossier `TP2 FILM`

2. **IntelliJ détecte Maven**
   - Une notification apparaîtra : "Maven projects need to be imported"
   - Cliquez sur "Import Changes" ou "Enable Auto-Import"

3. **Recharger Maven manuellement**
   - Ouvrez l'onglet Maven (à droite)
   - Cliquez sur l'icône ⟳ "Reload All Maven Projects"

4. **Vérifier les dépendances**
   - Dans l'onglet Maven → Dependencies
   - Vous devriez voir toutes les bibliothèques JavaFX et MongoDB

5. **Attendre le téléchargement**
   - Maven télécharge les JARs (barre de progression en bas)
   - Les erreurs de compilation disparaîtront une fois terminé

### Alternative: Script de setup

Exécutez le script fourni:
```bash
./setup.sh
```

Ce script vous guidera dans la configuration.

## ✅ Vérification

Une fois les dépendances chargées, les imports ne doivent plus afficher d'erreurs en rouge dans IntelliJ.

Pour vérifier que tout fonctionne:
1. Ouvrez `Main.java`
2. Les imports `javafx.*` ne doivent plus être en rouge
3. Ouvrez `FilmsController.java`
4. Les imports `com.mongodb.*` et `org.bson.*` ne doivent plus être en rouge

## 🔧 Dépannage

**Si les erreurs persistent:**

1. File → Invalidate Caches / Restart
2. Supprimez le dossier `.idea` et rouvrez le projet
3. Dans Maven → Reimport
4. Vérifiez que Java 17 est bien configuré (File → Project Structure → Project)

**Si Maven ne fonctionne pas:**

Téléchargez les JARs manuellement:
- JavaFX: https://openjfx.io/
- MongoDB: https://mongodb.github.io/mongo-java-driver/

Puis ajoutez-les dans File → Project Structure → Libraries

