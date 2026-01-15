# Guide de Correction - TP2 Film

## Résumé des corrections effectuées

### 1. Correction du fichier `module-info.java`

**Problème** : Erreur de syntaxe et mauvais nom de module MongoDB

**Solution appliquée** :
```java
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
```

**Explication** : Les modules MongoDB doivent être déclarés avec leurs noms exacts. L'ordre a été corrigé et des commentaires ont été ajoutés pour la clarté.

---

### 2. Correction des chemins de ressources

**Problème** : Les fichiers FXML et images utilisaient `/resources/` mais les fichiers étaient dans `/ressources/`

**Fichiers modifiés** :
- `Main.java`
- `MenuPrincipalController.java`
- `pom.xml`

**Dans Main.java** :
```java
// AVANT
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/menuprincipal.fxml"));
stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/clapperboard.png")));

// APRÈS
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressources/menuprincipal.fxml"));
stage.getIcons().add(new Image(getClass().getResourceAsStream("/ressources/clapperboard.png")));
```

**Dans MenuPrincipalController.java** :
```java
// AVANT
FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/" + fxml));
stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/clapperboard.png")));

// APRÈS
FXMLLoader loader = new FXMLLoader(getClass().getResource("/ressources/" + fxml));
stage.getIcons().add(new Image(getClass().getResourceAsStream("/ressources/clapperboard.png")));
```

**Dans pom.xml** :
```xml
<!-- AVANT -->
<resource>
    <directory>src/ressources</directory>
    <targetPath>resources</targetPath>
</resource>

<!-- APRÈS -->
<resource>
    <directory>src/ressources</directory>
    <targetPath>ressources</targetPath>
</resource>
```

---

### 3. Configuration Maven

Le `pom.xml` est déjà bien configuré avec :
- Java 17
- JavaFX 21.0.1
- MongoDB Driver 4.11.1

**Dépendances importantes** :
```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>4.11.1</version>
</dependency>
```

---

## Étapes pour compiler et exécuter le projet

### Option 1 : Avec IntelliJ IDEA

1. **Nettoyer le projet** :
   - Menu : Build → Clean Project

2. **Recompiler** :
   - Menu : Build → Rebuild Project

3. **Exécuter** :
   - Ouvrir `Main.java`
   - Clic droit → Run 'Main.main()'

### Option 2 : Avec Maven en ligne de commande

```bash
# Se placer dans le dossier du projet
cd "/Users/thomasfriquet/Documents/TP2 FILM"

# Nettoyer et compiler
mvn clean compile

# Exécuter l'application
mvn javafx:run
```

---

## Structure de la base de données MongoDB

### Collection : `movies` (dans la base `filmographie`)

**Structure d'un document film** :
```json
{
  "_id": ObjectId("..."),
  "title": "Titre du film",
  "year": 2024,
  "genre": "Action",
  "entries": 1000000,
  "length": 120,
  "nationality": "France",
  "story": "Résumé du film...",
  "director": {
    "firstname": "Prénom",
    "lastname": "Nom",
    "role": "Director"
  },
  "actors": [
    {
      "firstname": "Acteur1",
      "lastname": "Nom1",
      "role": "Principal"
    },
    {
      "firstname": "Acteur2",
      "lastname": "Nom2",
      "role": "Secondary"
    }
  ],
  "created_at": ISODate("2024-12-17T10:00:00Z"),
  "updated_at": ISODate("2024-12-17T10:00:00Z")
}
```

**Explication du design** :
- Un film a **un seul réalisateur** (objet `director`)
- Un film a **0 à N acteurs** (tableau `actors`)
- Chaque personne (réalisateur ou acteur) a :
  - `firstname` : prénom
  - `lastname` : nom
  - `role` : rôle ("Director", "Principal", "Secondary")

**Avantages de cette structure** :
- Simple à comprendre
- Pas de doublons de données
- Facile à requêter
- Conforme au modèle document de MongoDB

---

## Démarrer MongoDB

### Sur macOS avec Homebrew :
```bash
# Démarrer MongoDB
brew services start mongodb-community

# Vérifier que MongoDB fonctionne
mongosh
```

### Créer la base de données :
```javascript
// Dans mongosh
use filmographie
db.createCollection("movies")
```

---

## Fonctionnalités à implémenter

### 1. Connexion à MongoDB (FilmsController.java)
```java
// Attributs de classe
private MongoClient mongoClient;
private MongoDatabase database;
private MongoCollection<Document> collection;

// Dans initialize()
public void initialize() {
    // Connexion à MongoDB
    mongoClient = MongoClients.create("mongodb://localhost:27017");
    database = mongoClient.getDatabase("filmographie");
    collection = database.getCollection("movies");
    
    // Configuration des colonnes du TableView
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colTitre.setCellValueFactory(new PropertyValueFactory<>("title"));
    colAnnee.setCellValueFactory(new PropertyValueFactory<>("year"));
    colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
    
    // Charger les films depuis la base de données
    chargerFilms();
}
```

### 2. Charger les films depuis MongoDB
```java
public void chargerFilms() {
    films.clear();
    
    // Récupérer tous les documents de la collection
    FindIterable<Document> documents = collection.find();
    
    // Pour chaque document, créer un objet Film
    for (Document doc : documents) {
        Film film = new Film();
        film.setId(doc.getObjectId("_id").toString());
        film.setTitle(doc.getString("title"));
        film.setYear(doc.getInteger("year", 0));
        film.setGenre(doc.getString("genre"));
        
        // Ajouter le film à la liste observable
        films.add(film);
    }
    
    // Mettre à jour le TableView
    filmsTable.setItems(films);
}
```

### 3. Ajouter un film
```java
public void ajouterFilm() {
    // Récupérer les données des champs
    String titre = titreField.getText();
    int annee = Integer.parseInt(anneeField.getText());
    String genre = genreField.getText();
    
    // Créer un document MongoDB
    Document filmDoc = new Document();
    filmDoc.append("title", titre)
           .append("year", annee)
           .append("genre", genre)
           .append("created_at", new Date());
    
    // Ajouter le réalisateur si renseigné
    if (!realisateurNomField.getText().isEmpty()) {
        Document director = new Document()
            .append("firstname", realisateurPrenomField.getText())
            .append("lastname", realisateurNomField.getText())
            .append("role", "Director");
        filmDoc.append("director", director);
    }
    
    // Ajouter les acteurs
    List<Document> actorsList = new ArrayList<>();
    if (!acteur1NomField.getText().isEmpty()) {
        Document actor1 = new Document()
            .append("firstname", acteur1PrenomField.getText())
            .append("lastname", acteur1NomField.getText())
            .append("role", acteur1RoleField.getValue());
        actorsList.add(actor1);
    }
    filmDoc.append("actors", actorsList);
    
    // Insérer dans MongoDB
    collection.insertOne(filmDoc);
    
    // Recharger la liste
    chargerFilms();
    
    // Vider les champs
    viderChamps();
}
```

---

## Reste à faire

1. ✅ Corriger les chemins des ressources
2. ✅ Corriger le module-info.java
3. ⏳ Implémenter la connexion MongoDB
4. ⏳ Implémenter les méthodes CRUD (Create, Read, Update, Delete)
5. ⏳ Implémenter les filtres de recherche
6. ⏳ Gérer les acteurs et réalisateurs
7. ⏳ Ajouter la validation des champs
8. ⏳ Tester l'application

---

## Estimation de la charge

- Connexion MongoDB : **30 minutes**
- Méthodes CRUD basiques : **2 heures**
- Filtres de recherche : **1 heure**
- Gestion des personnes : **1.5 heures**
- Tests et débogage : **1 heure**

**Total estimé : 5-6 heures**

---

## Pour aller plus loin

Un développeur expert pourrait optimiser ce code en :
- Utilisant un pattern DAO (Data Access Object) pour séparer la logique de base de données
- Ajoutant une couche de service pour la logique métier
- Utilisant des streams Java pour simplifier les boucles
- Ajoutant une gestion d'erreurs robuste avec try-catch
- Créant des classes Repository pour MongoDB
- Utilisant des validateurs JavaFX pour les champs
- Ajoutant des animations et transitions
- Implémentant un système de logs avec Log4j

Mais pour un TP étudiant, le code simple et explicite est préférable !

