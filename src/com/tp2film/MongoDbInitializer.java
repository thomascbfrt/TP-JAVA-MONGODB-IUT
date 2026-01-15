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
		actors1.add(person("Garrett", "Hedlund", "principal"));
		actors1.add(person("Jeff", "Bridges", "principal"));
		actors1.add(person("Olivia", "Wilde", "secondaire"));

		movies.add(new Document("title", "Tron Legacy")
				.append("year", 2010)
				.append("genre", "Science-fiction")
				.append("entries", 2500000)
				.append("length", 125)
				.append("nationality", "USA")
				.append("story", "A son enters a digital world to find his father.")
				.append("director", person("Joseph", "Kosinski", "director"))
				.append("actors", actors1)
				.append("created_at", now)
				.append("updated_at", now));

		List<Document> actors2 = new ArrayList<Document>();
		actors2.add(person("Tom", "Cruise", "principal"));
		actors2.add(person("Morgan", "Freeman", "secondaire"));
		actors2.add(person("Andrea", "Riseborough", "secondaire"));

		movies.add(new Document("title", "Oblivion")
				.append("year", 2013)
				.append("genre", "Science-fiction")
				.append("entries", 1800000)
				.append("length", 124)
				.append("nationality", "USA")
				.append("story", "A technician on a ruined Earth discovers a hidden truth.")
				.append("director", person("Joseph", "Kosinski", "director"))
				.append("actors", actors2)
				.append("created_at", now)
				.append("updated_at", now));

		List<Document> actors3 = new ArrayList<Document>();
		actors3.add(person("Joaquin", "Phoenix", "principal"));
		actors3.add(person("Scarlett", "Johansson", "principal"));
		actors3.add(person("Amy", "Adams", "secondaire"));

		movies.add(new Document("title", "HER")
				.append("year", 2013)
				.append("genre", "Drama")
				.append("entries", 1200000)
				.append("length", 126)
				.append("nationality", "USA")
				.append("story", "A man falls in love with an AI voice.")
				.append("director", person("Spike", "Jonze", "director"))
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
