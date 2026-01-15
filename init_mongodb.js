// ========================================
// EXERCICE 1 : Création de la base de données filmographie
// ========================================
// Ce script initialise la base MongoDB avec la structure demandée

// Utilisation de la base de données filmographie
use filmographie;

// Suppression de la collection existante (pour repartir à zéro)
db.movies.drop();

// Création de la collection movies
db.createCollection("movies");

// ========================================
// STRUCTURE D'UN DOCUMENT FILM
// ========================================
// Informations minimales stockées :
// - titre : titre du film
// - annee : année de sortie
// - duree : durée en minutes
// - dateAjout : date d'ajout dans la base
// - categorie (genre) : type de film
// - nationalite : pays d'origine
// - entrees : nombre d'entrées
// - resume : résumé du film
// - realisateur : UN réalisateur (objet avec prénom, nom, rôle)
// - acteurs : 0 à N acteurs (tableau d'objets avec prénom, nom, rôle)
//   - rôle peut être "Principal" ou "Secondary"

// ========================================
// INSERTION DE FILMS D'EXEMPLE
// ========================================

// Film 1 : Inception
db.movies.insertOne({
  titre: "Inception",
  annee: 2010,
  duree: 148,
  dateAjout: new Date("2024-12-17"),
  categorie: "Science-Fiction",
  nationalite: "USA",
  entrees: 16300000,
  resume: "Un voleur qui s'infiltre dans les rêves des gens pour voler leurs secrets se voit proposer une mission inverse : implanter une idée dans l'esprit d'un homme d'affaires.",
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
    },
    {
      prenom: "Tom",
      nom: "Hardy",
      role: "Secondary"
    },
    {
      prenom: "Ellen",
      nom: "Page",
      role: "Secondary"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
});

// Film 2 : Le Fabuleux Destin d'Amélie Poulain
db.movies.insertOne({
  titre: "Le Fabuleux Destin d'Amélie Poulain",
  annee: 2001,
  duree: 122,
  dateAjout: new Date("2024-12-17"),
  categorie: "Comédie",
  nationalite: "France",
  entrees: 8900000,
  resume: "Amélie, une jeune serveuse dans un bar de Montmartre, passe son temps à observer les gens et à laisser son imagination divaguer. Elle s'est fixé un but : faire le bien de ceux qui l'entourent.",
  realisateur: {
    prenom: "Jean-Pierre",
    nom: "Jeunet",
    role: "Director"
  },
  acteurs: [
    {
      prenom: "Audrey",
      nom: "Tautou",
      role: "Principal"
    },
    {
      prenom: "Mathieu",
      nom: "Kassovitz",
      role: "Principal"
    },
    {
      prenom: "Rufus",
      nom: "Rufus",
      role: "Secondary"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
});

// Film 3 : Les Intouchables
db.movies.insertOne({
  titre: "Les Intouchables",
  annee: 2011,
  duree: 112,
  dateAjout: new Date("2024-12-17"),
  categorie: "Comédie dramatique",
  nationalite: "France",
  entrees: 19490000,
  resume: "À la suite d'un accident de parapente, Philippe, riche aristocrate, engage comme aide à domicile Driss, un jeune de banlieue tout juste sorti de prison.",
  realisateur: {
    prenom: "Olivier",
    nom: "Nakache",
    role: "Director"
  },
  acteurs: [
    {
      prenom: "François",
      nom: "Cluzet",
      role: "Principal"
    },
    {
      prenom: "Omar",
      nom: "Sy",
      role: "Principal"
    },
    {
      prenom: "Anne",
      nom: "Le Ny",
      role: "Secondary"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
});

// Film 4 : The Dark Knight
db.movies.insertOne({
  titre: "The Dark Knight",
  annee: 2008,
  duree: 152,
  dateAjout: new Date("2024-12-17"),
  categorie: "Action",
  nationalite: "USA",
  entrees: 13500000,
  resume: "Batman, avec l'aide du procureur Harvey Dent et du lieutenant de police Jim Gordon, poursuit sa guerre contre le crime. Mais un nouveau criminel, le Joker, plonge Gotham dans l'anarchie.",
  realisateur: {
    prenom: "Christopher",
    nom: "Nolan",
    role: "Director"
  },
  acteurs: [
    {
      prenom: "Christian",
      nom: "Bale",
      role: "Principal"
    },
    {
      prenom: "Heath",
      nom: "Ledger",
      role: "Principal"
    },
    {
      prenom: "Aaron",
      nom: "Eckhart",
      role: "Secondary"
    },
    {
      prenom: "Michael",
      nom: "Caine",
      role: "Secondary"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
});

// Film 5 : La La Land
db.movies.insertOne({
  titre: "La La Land",
  annee: 2016,
  duree: 128,
  dateAjout: new Date("2024-12-17"),
  categorie: "Comédie musicale",
  nationalite: "USA",
  entrees: 2900000,
  resume: "Mia, une actrice en devenir, sert des cafés entre deux auditions. Sebastian, un pianiste de jazz, joue dans des clubs miteux. Ensemble, ils vont poursuivre leurs rêves.",
  realisateur: {
    prenom: "Damien",
    nom: "Chazelle",
    role: "Director"
  },
  acteurs: [
    {
      prenom: "Ryan",
      nom: "Gosling",
      role: "Principal"
    },
    {
      prenom: "Emma",
      nom: "Stone",
      role: "Principal"
    },
    {
      prenom: "John",
      nom: "Legend",
      role: "Secondary"
    }
  ],
  createdAt: new Date(),
  updatedAt: new Date()
});

// Film 6 : Parasite (film sans acteurs pour tester 0 acteur)
db.movies.insertOne({
  titre: "Parasite",
  annee: 2019,
  duree: 132,
  dateAjout: new Date("2024-12-17"),
  categorie: "Thriller",
  nationalite: "Corée du Sud",
  entrees: 2200000,
  resume: "Toute la famille de Ki-taek est au chômage. Un jour, leur fils réussit à se faire recommander pour donner des cours particuliers d'anglais dans la famille richissime des Park.",
  realisateur: {
    prenom: "Bong",
    nom: "Joon-ho",
    role: "Director"
  },
  acteurs: [],  // 0 acteur dans la base (exemple)
  createdAt: new Date(),
  updatedAt: new Date()
});

// ========================================
// CRÉATION D'INDEX POUR OPTIMISER LES RECHERCHES
// ========================================

// Index sur le titre pour recherche rapide
db.movies.createIndex({ titre: 1 });

// Index sur l'année pour filtrage par période
db.movies.createIndex({ annee: 1 });

// Index sur la catégorie pour filtrage par genre
db.movies.createIndex({ categorie: 1 });

// Index composé pour recherche combinée
db.movies.createIndex({ titre: 1, annee: 1 });

// ========================================
// AFFICHAGE DES RÉSULTATS
// ========================================

print("\n===========================================");
print("  BASE DE DONNÉES FILMOGRAPHIE CRÉÉE");
print("===========================================\n");

print("Nombre de films insérés : " + db.movies.countDocuments());

print("\n--- Liste des films ---\n");
db.movies.find({}, {
  titre: 1,
  annee: 1,
  categorie: 1,
  "realisateur.nom": 1,
  _id: 0
}).forEach(film => {
  print("📽️  " + film.titre + " (" + film.annee + ") - " + film.categorie + " - Réalisateur: " + film.realisateur.nom);
});

print("\n--- Statistiques ---\n");
print("Films par catégorie :");
db.movies.aggregate([
  {
    $group: {
      _id: "$categorie",
      count: { $sum: 1 }
    }
  },
  {
    $sort: { count: -1 }
  }
]).forEach(stat => {
  print("  • " + stat._id + " : " + stat.count + " film(s)");
});

print("\n--- Validation de la structure ---\n");

// Vérification d'un film avec acteurs
var filmAvecActeurs = db.movies.findOne({ acteurs: { $exists: true, $not: { $size: 0 } } });
if (filmAvecActeurs) {
  print("✅ Film avec acteurs trouvé : " + filmAvecActeurs.titre);
  print("   Nombre d'acteurs : " + filmAvecActeurs.acteurs.length);
  print("   Acteur principal : " + filmAvecActeurs.acteurs[0].prenom + " " + filmAvecActeurs.acteurs[0].nom);
}

// Vérification d'un film sans acteurs
var filmSansActeurs = db.movies.findOne({ acteurs: { $size: 0 } });
if (filmSansActeurs) {
  print("✅ Film sans acteurs trouvé : " + filmSansActeurs.titre);
}

// Vérification que tous les films ont un réalisateur
var filmsSansRealisateur = db.movies.countDocuments({ realisateur: { $exists: false } });
print("✅ Films sans réalisateur : " + filmsSansRealisateur + " (doit être 0)");

// Vérification des champs obligatoires
var champsObligatoires = ["titre", "annee", "duree", "dateAjout", "categorie"];
var filmComplet = db.movies.findOne();
var champsManquants = [];
champsObligatoires.forEach(champ => {
  if (!filmComplet[champ]) {
    champsManquants.push(champ);
  }
});
if (champsManquants.length === 0) {
  print("✅ Tous les champs obligatoires sont présents");
} else {
  print("❌ Champs manquants : " + champsManquants.join(", "));
}

print("\n===========================================");
print("  EXERCICE 1 : TERMINÉ ✅");
print("===========================================\n");

print("Pour afficher tous les films :");
print("  db.movies.find().pretty()\n");

print("Pour compter les films :");
print("  db.movies.countDocuments()\n");

print("Pour rechercher un film par titre :");
print("  db.movies.find({ titre: /Inception/i }).pretty()\n");

