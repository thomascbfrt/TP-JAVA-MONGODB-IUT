# 🎬 TP2 Film - Résumé Simple

## ✅ Ce qui a été corrigé

### 1. Module-info.java
**Erreur** : Syntaxe incorrecte et mauvais noms de modules MongoDB  
**Correction** : Fichier corrigé avec les bons noms de modules  
📄 Voir : `/src/module-info.java`

### 2. Chemins des ressources
**Erreur** : Le code cherchait `/resources/` mais les fichiers étaient dans `/ressources/`  
**Correction** : Tous les chemins ont été mis à jour dans :
- `Main.java`
- `MenuPrincipalController.java`
- `pom.xml`

### 3. Configuration du projet
**Statut** : ✅ Le projet est maintenant correctement configuré avec :
- Java 17
- JavaFX 21.0.1
- MongoDB Driver 4.11.1

---

## 🚀 Comment lancer l'application

### Étape 1 : Démarrer MongoDB
```bash
# Sur macOS avec Homebrew
brew services start mongodb-community

# Vérifier que MongoDB fonctionne
mongosh
```

Dans mongosh, créer la base de données :
```javascript
use filmographie
db.createCollection("movies")
exit
```

### Étape 2 : Lancer l'application

**Dans IntelliJ IDEA** (recommandé) :
1. Menu : `Build` → `Clean Project`
2. Menu : `Build` → `Rebuild Project`
3. Ouvrir `Main.java`
4. Cliquer sur ▶️ (Run) ou clic droit → `Run 'Main.main()'`

**Avec Maven** :
```bash
cd "/Users/thomasfriquet/Documents/TP2 FILM"
mvn clean javafx:run
```

---

## 📝 Ce qu'il reste à faire

Le projet compile maintenant sans erreurs ! Il reste à implémenter la logique MongoDB dans `FilmsController.java`.

### Code à ajouter (voir FilmsControllerExample.java pour le code complet)

**1. Connexion MongoDB** (30 min)
```java
private MongoClient mongoClient;
private MongoDatabase database;
private MongoCollection<Document> collection;

public void initialize() {
    mongoClient = MongoClients.create("mongodb://localhost:27017");
    database = mongoClient.getDatabase("filmographie");
    collection = database.getCollection("movies");
    chargerFilms();
}
```

**2. Charger les films** (1h)
```java
public void chargerFilms() {
    films.clear();
    FindIterable<Document> documents = collection.find();
    for (Document doc : documents) {
        Film film = new Film();
        film.setId(doc.getObjectId("_id").toString());
        film.setTitle(doc.getString("title"));
        // ... autres champs
        films.add(film);
    }
    filmsTable.setItems(films);
}
```

**3. Ajouter un film** (1h)
```java
public void ajouterFilm() {
    Document filmDoc = new Document();
    filmDoc.append("title", titreField.getText())
           .append("year", Integer.parseInt(anneeField.getText()))
           .append("genre", genreField.getText());
    collection.insertOne(filmDoc);
    chargerFilms();
}
```

**4. Supprimer un film** (30 min)
**5. Modifier un film** (45 min)
**6. Filtrer les films** (1h)

**TOTAL ESTIMÉ : 5-6 heures**

---

## 📁 Fichiers créés pour vous aider

1. **GUIDE_CORRECTION.md** : Guide détaillé avec toutes les explications
2. **FilmsControllerExample.java** : Code complet et commenté ligne par ligne
3. **INSTRUCTIONS_FINALES.md** : Instructions complètes avec commandes

---

## 🗄️ Structure MongoDB recommandée

```json
{
  "_id": ObjectId("..."),
  "title": "Inception",
  "year": 2010,
  "genre": "Science-Fiction",
  "entries": 16300000,
  "length": 148,
  "nationality": "USA",
  "story": "Un voleur qui s'infiltre dans les rêves...",
  "director": {
    "firstname": "Christopher",
    "lastname": "Nolan",
    "role": "Director"
  },
  "actors": [
    {
      "firstname": "Leonardo",
      "lastname": "DiCaprio",
      "role": "Principal"
    }
  ],
  "created_at": ISODate("2024-12-17T10:00:00Z")
}
```

**Pourquoi cette structure ?**
- Simple et claire (bon pour un projet étudiant)
- Tout dans un seul document (pas besoin de jointures)
- Facile à comprendre et à modifier

---

## 🐛 Si vous avez des problèmes

### Erreur "Location is not set"
✅ **Résolu** : Les chemins ont été corrigés

### Erreur avec les modules MongoDB
✅ **Résolu** : `module-info.java` a été corrigé

### L'application ne démarre pas
1. Vérifier que MongoDB est démarré : `brew services list`
2. Nettoyer le projet : `Build → Clean Project`
3. Rebuilder : `Build → Rebuild Project`

### Problèmes de compilation
```bash
# Nettoyer complètement
cd "/Users/thomasfriquet/Documents/TP2 FILM"
rm -rf target
mvn clean compile
```

---

## 💡 Commandes MongoDB utiles

```javascript
// Se connecter
mongosh

// Utiliser la base
use filmographie

// Voir les films
db.movies.find().pretty()

// Compter les films
db.movies.countDocuments()

// Ajouter un film test
db.movies.insertOne({
  title: "Test",
  year: 2024,
  genre: "Test"
})

// Supprimer tous les films
db.movies.deleteMany({})
```

---

## 📦 Pour rendre le TP

1. **Export MongoDB** :
```bash
mongoexport --db=filmographie --collection=movies --out=movies.json
```

2. **Export du projet** :
```bash
cd "/Users/thomasfriquet/Documents/"
zip -r TP2_FILM_VotreNom.zip "TP2 FILM" -x "*/target/*" "*/.idea/*" "*/node_modules/*"
```

3. **Rapport à écrire** :
   - ✅ État d'avancement : Configuration terminée, logique MongoDB à implémenter
   - ✅ Design de la collection : Voir structure JSON ci-dessus
   - 📸 Captures d'écran : À faire une fois l'application fonctionnelle
   - 📝 Difficultés : Chemins de ressources et configuration des modules
   - ⏳ Reste à faire : Implémentation CRUD MongoDB (5-6h estimées)

---

## 🎓 Résumé

**Ce qui fonctionne maintenant :**
✅ Configuration du projet
✅ Modules Java corrects
✅ Chemins de ressources corrects
✅ Dépendances Maven
✅ Le projet compile sans erreurs
✅ L'interface graphique devrait s'afficher

**À faire :**
⏳ Implémenter la connexion MongoDB
⏳ Implémenter les opérations CRUD
⏳ Ajouter les filtres de recherche
⏳ Tester avec des données réelles

**Fichiers d'aide fournis :**
- 📖 GUIDE_CORRECTION.md (guide détaillé)
- 📖 FilmsControllerExample.java (code complet avec commentaires)
- 📖 INSTRUCTIONS_FINALES.md (toutes les instructions)
- 📖 RESUME_SIMPLE.md (ce fichier)

**Pour démarrer l'implémentation :**
1. Ouvrir `FilmsControllerExample.java`
2. Copier le code nécessaire dans `FilmsController.java`
3. Adapter selon vos besoins
4. Tester régulièrement

Bon courage ! 🚀

