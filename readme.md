# TP2 - Filmographie

Application JavaFX de gestion de films avec MongoDB

## Prérequis

- Java 17 ou supérieur
- Maven 3.6+ (ou utiliser le Maven intégré d'IntelliJ IDEA)
- MongoDB installé et en cours d'exécution sur localhost:27017

## Dépendances

Le projet utilise les bibliothèques suivantes (configurées dans `pom.xml`):

### JavaFX 21.0.1
- `javafx-controls` - Composants d'interface utilisateur
- `javafx-fxml` - Support pour les fichiers FXML
- `javafx-graphics` - Graphiques et images

### MongoDB 4.11.1
- `mongodb-driver-sync` - Driver MongoDB synchrone
- `bson` - Support pour les documents BSON

## Configuration dans IntelliJ IDEA

### Option 1: Importer comme projet Maven (Recommandé)

1. Fermez le projet actuel dans IntelliJ IDEA
2. File → Open
3. Sélectionnez le dossier `TP2 FILM`
4. IntelliJ détectera automatiquement le `pom.xml`
5. Cliquez sur "Trust Project" si demandé
6. Attendez que Maven télécharge toutes les dépendances (voir la barre de progression en bas)
7. File → Project Structure → Project → Vérifiez que le SDK est Java 17
8. Cliquez sur l'icône Maven dans le panneau de droite
9. Cliquez sur le bouton "Reload All Maven Projects" (icône de refresh)

### Option 2: Ajouter les bibliothèques manuellement

Si Maven ne fonctionne pas, vous devez télécharger et ajouter les JARs manuellement:

#### JavaFX:
1. Téléchargez JavaFX SDK depuis https://openjfx.io/
2. File → Project Structure → Libraries
3. Cliquez sur le + et ajoutez tous les JARs du dossier `lib` de JavaFX

#### MongoDB:
1. Téléchargez le driver MongoDB Java depuis https://mongodb.github.io/mongo-java-driver/
2. File → Project Structure → Libraries  
3. Ajoutez les JARs:
   - `mongodb-driver-sync-4.11.1.jar`
   - `bson-4.11.1.jar`
   - `mongodb-driver-core-4.11.1.jar`

### Configuration VM Options pour JavaFX

Si vous utilisez Java 11+, vous devrez peut-être ajouter des options VM:

1. Run → Edit Configurations
2. Sélectionnez la configuration Main
3. Dans "VM options", ajoutez:
```
--module-path /chemin/vers/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```

## Lancer l'application

### Avec Maven:
```bash
mvn clean javafx:run
```

### Avec IntelliJ:
1. Cliquez droit sur `Main.java`
2. Run 'Main.main()'

## Structure du projet

```
src/
├── Main.java                      - Point d'entrée de l'application
├── MenuPrincipalController.java   - Contrôleur du menu principal
├── FilmsController.java           - Contrôleur de gestion des films
├── PeopleController.java          - Contrôleur de gestion des personnes
├── Film.java                      - Modèle de données Film
├── People.java                    - Modèle de données People
├── module-info.java               - Configuration des modules Java
└── ressources/
    ├── menuprincipal.fxml        - Interface du menu principal
    ├── films-view.fxml           - Interface de gestion des films
    ├── people-view.fxml          - Interface de gestion des personnes
    ├── application.css           - Feuille de style
    └── clapperboard.png          - Icône de l'application
```

## Configuration MongoDB

L'application se connecte à MongoDB avec les paramètres par défaut:
- URL: `mongodb://localhost:27017`
- Base de données: `filmographie`
- Collections: `movies`, `people`

Assurez-vous que MongoDB est démarré avant de lancer l'application.

## Compilation

Pour compiler le projet:
```bash
mvn clean compile
```

Pour créer un JAR exécutable:
```bash
mvn clean package
```

Le JAR sera créé dans le dossier `target/`.

