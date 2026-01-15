# ✅ EXERCICE 1 - Base de données filmographie MongoDB

## 📋 Énoncé de l'exercice

**Réalisez la base de données filmographie. La base de données sera au format MongoDB.**

**Contraintes :**
- À un film correspond **0 ou N personnalités** (acteurs)
- Un film a **UN réalisateur**
- Un acteur peut être **acteur principal ou secondaire**
- **Informations minimales à stocker** : titre, année, durée, date d'ajout, catégorie, etc.

---

## ✅ Solution complète

### Structure de la base de données

**Base de données** : `filmographie`  
**Collection** : `movies`

### Structure d'un document film

```json
{
  "_id": ObjectId("..."),
  "titre": "Titre du film",
  "annee": 2024,
  "duree": 120,
  "dateAjout": ISODate("2024-12-17"),
  "categorie": "Genre du film",
  "nationalite": "Pays d'origine",
  "entrees": 1000000,
  "resume": "Résumé du film...",
  "realisateur": {
    "prenom": "Prénom",
    "nom": "Nom",
    "role": "Director"
  },
  "acteurs": [
    {
      "prenom": "Acteur1",
      "nom": "Nom1",
      "role": "Principal"
    },
    {
      "prenom": "Acteur2",
      "nom": "Nom2",
      "role": "Secondary"
    }
  ],
  "createdAt": ISODate("2024-12-17"),
  "updatedAt": ISODate("2024-12-17")
}
```

---

## 📝 Justification du design

### Pourquoi cette structure ?

1. **Un seul document par film** ✅
   - Toutes les informations sont regroupées
   - Pas besoin de jointures (performance)
   - Facile à requêter et à comprendre

2. **Réalisateur = Objet** ✅
   - Un film n'a qu'UN seul réalisateur
   - Structure : `{ prenom, nom, role: "Director" }`

3. **Acteurs = Tableau** ✅
   - Un film peut avoir 0 à N acteurs
   - Chaque acteur a un rôle : "Principal" ou "Secondary"
   - Le tableau peut être vide (0 acteur)

4. **Champs requis** ✅
   - `titre` : Titre du film (String)
   - `annee` : Année de sortie (Number)
   - `duree` : Durée en minutes (Number)
   - `dateAjout` : Date d'ajout dans la base (Date)
   - `categorie` : Genre du film (String)
   - Champs supplémentaires : nationalite, entrees, resume

---

## 🚀 Mise en place de la base de données

### Méthode 1 : Avec le script fourni (RECOMMANDÉ)

J'ai créé un fichier **`init_mongodb.js`** qui contient :
- La création de la base et de la collection
- 6 films d'exemple avec données réalistes
- Des index pour optimiser les recherches
- Des statistiques de validation

**Pour l'exécuter :**
```bash
cd "/Users/thomasfriquet/Documents/TP2 FILM"
mongosh < init_mongodb.js
```

**Résultat attendu :**
```
===========================================
  BASE DE DONNÉES FILMOGRAPHIE CRÉÉE
===========================================

Nombre de films insérés : 6

--- Liste des films ---

📽️  Inception (2010) - Science-Fiction - Réalisateur: Nolan
📽️  Le Fabuleux Destin d'Amélie Poulain (2001) - Comédie - Réalisateur: Jeunet
📽️  Les Intouchables (2011) - Comédie dramatique - Réalisateur: Nakache
📽️  The Dark Knight (2008) - Action - Réalisateur: Nolan
📽️  La La Land (2016) - Comédie musicale - Réalisateur: Chazelle
📽️  Parasite (2019) - Thriller - Réalisateur: Joon-ho

--- Validation de la structure ---

✅ Film avec acteurs trouvé : Inception
   Nombre d'acteurs : 4
✅ Film sans acteurs trouvé : Parasite
✅ Films sans réalisateur : 0
✅ Tous les champs obligatoires sont présents

===========================================
  EXERCICE 1 : TERMINÉ ✅
===========================================
```

### Méthode 2 : Manuellement dans mongosh

```javascript
// 1. Se connecter
mongosh

// 2. Créer la base
use filmographie

// 3. Créer la collection
db.createCollection("movies")

// 4. Insérer un film d'exemple
db.movies.insertOne({
  titre: "Inception",
  annee: 2010,
  duree: 148,
  dateAjout: new Date("2024-12-17"),
  categorie: "Science-Fiction",
  nationalite: "USA",
  entrees: 16300000,
  resume: "Un voleur qui s'infiltre dans les rêves...",
  realisateur: {
    prenom: "Christopher",
    nom: "Nolan",
    role: "Director"
  },
  acteurs: [
    {
      prenom: "Leonardo",
      nom: "DiCaprio",
      role: "Principal"
    },
    {
      prenom: "Marion",
      nom: "Cotillard",
      role: "Principal"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
})

// 5. Vérifier
db.movies.find().pretty()
```

---

## 📊 Exemples de films dans la base

Le script `init_mongodb.js` insère 6 films :

| Film | Année | Catégorie | Réalisateur | Acteurs |
|------|-------|-----------|-------------|---------|
| Inception | 2010 | Science-Fiction | Christopher Nolan | 4 acteurs |
| Le Fabuleux Destin d'Amélie Poulain | 2001 | Comédie | Jean-Pierre Jeunet | 3 acteurs |
| Les Intouchables | 2011 | Comédie dramatique | Olivier Nakache | 3 acteurs |
| The Dark Knight | 2008 | Action | Christopher Nolan | 4 acteurs |
| La La Land | 2016 | Comédie musicale | Damien Chazelle | 3 acteurs |
| Parasite | 2019 | Thriller | Bong Joon-ho | **0 acteur** |

**Note** : Le film "Parasite" a 0 acteur pour tester le cas "0 personnalités".

---

## 🔍 Requêtes utiles

### Afficher tous les films
```javascript
db.movies.find().pretty()
```

### Compter les films
```javascript
db.movies.countDocuments()
```

### Rechercher par titre
```javascript
db.movies.find({ titre: /Inception/i })
```

### Rechercher par année
```javascript
db.movies.find({ annee: { $gte: 2010 } })
```

### Rechercher par catégorie
```javascript
db.movies.find({ categorie: "Science-Fiction" })
```

### Trouver les films d'un réalisateur
```javascript
db.movies.find({ "realisateur.nom": "Nolan" })
```

### Trouver les films avec un acteur
```javascript
db.movies.find({ "acteurs.nom": "DiCaprio" })
```

### Films sans acteurs
```javascript
db.movies.find({ acteurs: { $size: 0 } })
```

### Films avec acteurs principaux
```javascript
db.movies.find({ "acteurs.role": "Principal" })
```

---

## ✅ Validation de l'exercice 1

### Critères remplis :

- ✅ **Base de données MongoDB créée** : `filmographie`
- ✅ **Collection créée** : `movies`
- ✅ **Structure définie** : 1 réalisateur, 0 à N acteurs
- ✅ **Rôles des acteurs** : "Principal" ou "Secondary"
- ✅ **Champs minimaux présents** :
  - titre ✅
  - année ✅
  - durée ✅
  - date d'ajout ✅
  - catégorie ✅
- ✅ **Champs supplémentaires** : nationalite, entrees, resume
- ✅ **Cas limites testés** : Film avec 0 acteur (Parasite)
- ✅ **Données d'exemple** : 6 films avec données réalistes
- ✅ **Index créés** : Pour optimiser les recherches

---

## 📦 Export de la base (pour le rendu)

### Exporter la collection movies
```bash
mongoexport --db=filmographie --collection=movies --out=movies.json --jsonArray --pretty
```

**Résultat** : Un fichier `movies.json` contenant tous les films au format JSON.

### Exporter toute la base
```bash
mongodump --db=filmographie --out=backup_filmographie
```

**Résultat** : Un dossier `backup_filmographie` avec toute la base.

---

## 🎓 Avantages de ce design

### Pour un projet étudiant :

1. **Simplicité** ✅
   - Structure facile à comprendre
   - Pas de relations complexes
   - Tout dans un seul document

2. **Performance** ✅
   - Pas de jointures nécessaires
   - Requêtes rapides
   - Index pour optimiser les recherches

3. **Flexibilité** ✅
   - Facile d'ajouter des champs
   - Tableau d'acteurs extensible
   - Peut gérer 0 acteur

4. **Cohérence** ✅
   - Structure uniforme pour tous les films
   - Validation possible avec des schémas
   - Dates et types bien définis

### Inconvénients (pour information) :

- ⚠️ Duplication si un acteur joue dans plusieurs films
- ⚠️ Difficile de lister tous les films d'un acteur
- ⚠️ Mise à jour des informations d'un acteur nécessite de mettre à jour plusieurs documents

**Note** : Pour un projet avancé, on pourrait avoir une collection séparée `people` et utiliser des références (ObjectId), mais ce n'est **pas nécessaire** pour ce TP.

---

## 📋 Pour le rapport

### Section "Design de la collection MongoDB"

**Base de données** : MongoDB `filmographie`

**Collection** : `movies`

**Justification du design** :
- Structure de document unique par film pour simplifier les requêtes
- Un réalisateur par film (objet imbriqué)
- 0 à N acteurs par film (tableau d'objets)
- Chaque acteur a un rôle (Principal/Secondary)
- Tous les champs requis sont présents
- Structure adaptée à un projet étudiant (simplicité avant performance)

**Exemples de documents** : Voir structure JSON ci-dessus

**Requêtes possibles** :
- Recherche par titre, année, catégorie
- Filtrage par réalisateur
- Recherche par acteur
- Films sans acteurs

---

## 🎯 Conclusion Exercice 1

**Statut** : ✅ **TERMINÉ**

**Ce qui a été fait :**
- ✅ Base de données conçue selon les contraintes
- ✅ Structure documentée et justifiée
- ✅ Script d'initialisation créé (`init_mongodb.js`)
- ✅ 6 films d'exemple insérés
- ✅ Validation de la structure
- ✅ Index créés pour les performances
- ✅ Documentation complète

**Fichiers liés :**
- `init_mongodb.js` : Script d'initialisation
- `EXERCICE_1_COMPLETE.md` : Ce document

**Prochaine étape :** Exercice 2 - Implémenter l'application Java pour interagir avec cette base.

---

**Date de réalisation : 17 décembre 2024**  
**Exercice 1 : Base de données filmographie MongoDB ✅**

