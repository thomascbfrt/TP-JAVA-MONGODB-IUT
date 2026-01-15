package com.tp2film;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDbInitializer {
	private static final String DB_URL = "mongodb://localhost:27017";
	private static final String DB_NAME = "filmographie";
	private static final String COLLECTION_NAME = "movies";

	public static void init() {
		try (MongoClient client = MongoClients.create(DB_URL)) {
			MongoDatabase database = client.getDatabase(DB_NAME);
			MongoCollection<Document> movies = database.getCollection(COLLECTION_NAME);

			// Seed only if the collection is empty to avoid duplicates.
			if (movies.countDocuments() == 0) {
				movies.insertMany(sampleMovies());
				movies.createIndex(new Document("title", 1));
				movies.createIndex(new Document("year", 1));
				movies.createIndex(new Document("genre", 1));
				System.out.println("MongoDB: sample movies inserted.");
			}
		} catch (MongoException e) {
			System.out.println("MongoDB init failed: " + e.getMessage());
		}
	}

	private static List<Document> sampleMovies() {
		Date now = new Date();
		List<Document> movies = new ArrayList<Document>();

		List<Document> actors1 = new ArrayList<Document>();
		actors1.add(person("Leonardo", "DiCaprio", "principal"));
		actors1.add(person("Joseph", "Gordon-Levitt", "secondaire"));
		actors1.add(person("Elliot", "Page", "secondaire"));

		movies.add(new Document("title", "Inception")
				.append("year", 2010)
				.append("genre", "Science-Fiction")
				.append("entries", 16300000)
				.append("length", 148)
				.append("nationality", "USA")
				.append("story", "A thief enters dreams to steal secrets.")
				.append("director", person("Christopher", "Nolan", "director"))
				.append("actors", actors1)
				.append("created_at", now)
				.append("updated_at", now));

		List<Document> actors2 = new ArrayList<Document>();
		actors2.add(person("Francois", "Cluzet", "principal"));
		actors2.add(person("Omar", "Sy", "principal"));
		actors2.add(person("Anne", "Le Ny", "secondaire"));

		movies.add(new Document("title", "Les Intouchables")
				.append("year", 2011)
				.append("genre", "Comedie dramatique")
				.append("entries", 19490000)
				.append("length", 112)
				.append("nationality", "France")
				.append("story", "A rich man hires a caregiver from the suburbs.")
				.append("director", person("Olivier", "Nakache", "director"))
				.append("actors", actors2)
				.append("created_at", now)
				.append("updated_at", now));

		List<Document> actors3 = new ArrayList<Document>();
		movies.add(new Document("title", "Parasite")
				.append("year", 2019)
				.append("genre", "Thriller")
				.append("entries", 2200000)
				.append("length", 132)
				.append("nationality", "Coree du Sud")
				.append("story", "A poor family infiltrates a rich household.")
				.append("director", person("Bong", "Joon-ho", "director"))
				.append("actors", actors3)
				.append("created_at", now)
				.append("updated_at", now));

		return movies;
	}

	private static Document person(String firstname, String lastname, String role) {
		return new Document("firstname", firstname)
				.append("lastname", lastname)
				.append("role", role);
	}
}
