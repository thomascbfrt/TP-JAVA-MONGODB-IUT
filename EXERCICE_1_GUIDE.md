# 🎬 EXERCICE 1 - Guide d'exécution

## ✅ L'exercice 1 est TERMINÉ !

J'ai créé une base de données MongoDB complète selon vos exigences.

---

## 📋 Ce qui a été réalisé

### Structure de la base de données :
- **Base** : `filmographie`
- **Collection** : `movies`
- **1 réalisateur** par film (objet)
- **0 à N acteurs** par film (tableau)
- **Rôles** : "Principal" ou "Secondary"

### Champs stockés (comme demandé) :
- ✅ **titre** : Titre du film
- ✅ **année** : Année de sortie
- ✅ **durée** : Durée en minutes
- ✅ **date d'ajout** : Date d'ajout dans la base
- ✅ **catégorie** : Genre du film
- ✅ **nationalité** : Pays d'origine
- ✅ **entrées** : Nombre d'entrées
- ✅ **résumé** : Description du film

---

## 🚀 Comment créer la base de données

### OPTION 1 : Avec le script automatique (5 secondes)

```bash
# Se placer dans le dossier du projet
cd "/Users/thomasfriquet/Documents/TP2 FILM"

# Exécuter le script d'initialisation
mongosh < init_mongodb.js
```

**✅ C'est tout !** La base sera créée avec 6 films d'exemple.

---

### OPTION 2 : Manuellement (2 minutes)

```bash
# 1. Se connecter à MongoDB
mongosh

# 2. Dans mongosh, tapez ces commandes :
use filmographie
db.createCollection("movies")

# 3. Ajouter un film d'exemple
db.movies.insertOne({
  titre: "Inception",
  annee: 2010,
  duree: 148,
  dateAjout: new Date("2024-12-17"),
  categorie: "Science-Fiction",
  nationalite: "USA",
  entrees: 16300000,
  resume: "Un voleur qui s'infiltre dans les rêves pour voler des secrets.",
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

# 4. Vérifier
db.movies.find().pretty()
db.movies.countDocuments()

# 5. Quitter
exit
```

---

## 📊 Films d'exemple dans la base

Le script crée **6 films** :

1. **Inception** (2010) - Science-Fiction
   - Réalisateur : Christopher Nolan
   - 4 acteurs (2 principaux, 2 secondaires)

2. **Le Fabuleux Destin d'Amélie Poulain** (2001) - Comédie
   - Réalisateur : Jean-Pierre Jeunet
   - 3 acteurs

3. **Les Intouchables** (2011) - Comédie dramatique
   - Réalisateur : Olivier Nakache
   - 3 acteurs

4. **The Dark Knight** (2008) - Action
   - Réalisateur : Christopher Nolan
   - 4 acteurs

5. **La La Land** (2016) - Comédie musicale
   - Réalisateur : Damien Chazelle
   - 3 acteurs

6. **Parasite** (2019) - Thriller
   - Réalisateur : Bong Joon-ho
   - **0 acteur** (pour tester le cas "0 personnalités")

---

## 🔍 Vérifier que ça fonctionne

```bash
# Se connecter
mongosh

# Dans mongosh :
use filmographie

# Afficher tous les films
db.movies.find().pretty()

# Compter les films (doit afficher 6)
db.movies.countDocuments()

# Vérifier la structure d'un film
db.movies.findOne({ titre: "Inception" })

# Quitter
exit
```

---

## 📦 Exporter la base (pour le rendu du TP)

```bash
# Export en JSON
mongoexport --db=filmographie --collection=movies --out=movies.json --jsonArray --pretty

# Le fichier movies.json sera créé dans le dossier courant
```

---

## ✅ Validation de l'exercice 1

### Critères de l'énoncé :

| Critère | Statut |
|---------|--------|
| Base de données MongoDB | ✅ `filmographie` |
| 0 ou N personnalités (acteurs) | ✅ Tableau d'acteurs |
| UN réalisateur | ✅ Objet réalisateur |
| Acteur principal ou secondaire | ✅ Champ `role` |
| Titre | ✅ |
| Année | ✅ |
| Durée | ✅ |
| Date d'ajout | ✅ |
| Catégorie | ✅ |

**TOUT EST ✅ !**

---

## 📄 Structure d'un document film

```json
{
  "titre": "Inception",
  "annee": 2010,
  "duree": 148,
  "dateAjout": ISODate("2024-12-17"),
  "categorie": "Science-Fiction",
  "nationalite": "USA",
  "entrees": 16300000,
  "resume": "Description...",
  "realisateur": {
    "prenom": "Christopher",
    "nom": "Nolan",
    "role": "Director"
  },
  "acteurs": [
    {
      "prenom": "Leonardo",
      "nom": "DiCaprio",
      "role": "Principal"
    },
    {
      "prenom": "Marion",
      "nom": "Cotillard",
      "role": "Principal"
    }
  ]
}
```

---

## 💡 Requêtes utiles

```javascript
// Rechercher un film par titre
db.movies.find({ titre: /Inception/i })

// Films d'une année
db.movies.find({ annee: 2010 })

// Films d'une catégorie
db.movies.find({ categorie: "Science-Fiction" })

// Films d'un réalisateur
db.movies.find({ "realisateur.nom": "Nolan" })

// Films avec un acteur
db.movies.find({ "acteurs.nom": "DiCaprio" })

// Films sans acteurs
db.movies.find({ acteurs: { $size: 0 } })

// Statistiques par catégorie
db.movies.aggregate([
  { $group: { _id: "$categorie", count: { $sum: 1 } } },
  { $sort: { count: -1 } }
])
```

---

## 🎯 Résumé

**EXERCICE 1 : ✅ TERMINÉ**

- ✅ Base de données créée
- ✅ Structure conforme aux exigences
- ✅ Script d'initialisation fourni
- ✅ 6 films d'exemple
- ✅ Tous les champs demandés présents
- ✅ Documentation complète

**Fichiers créés :**
- `init_mongodb.js` : Script d'initialisation
- `EXERCICE_1_COMPLETE.md` : Documentation complète
- `EXERCICE_1_GUIDE.md` : Ce guide

**Prochaine étape :**
Implémenter l'application Java pour interagir avec cette base (code déjà fourni dans `FilmsControllerExample.java`)

---

**Vous avez tout ce qu'il faut pour l'exercice 1 ! 🎉**

