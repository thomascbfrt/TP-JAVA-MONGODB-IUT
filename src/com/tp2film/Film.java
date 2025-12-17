package com.tp2film;

import java.util.ArrayList;
import java.util.Date;

public class Film {
	private String id = "";
	private String title = "";
	private int year = 0;
	private String genre = "";
	private int entries = 0;
	private int length = 0;
	private String nationality = "";
	private String story = "";
	private People director = null;
	private Date created_at = null;
	private Date updated_at = null;
	private ArrayList<People> actors = new ArrayList<People>();

	public Film(String id, String title, int year, String genre, int entries,
			int length, String nationality, String story, Date created_at) {
	super();
	this.id = id;
	this.title = title;
	this.year = year;
	this.genre = genre;
	this.entries = entries;
	this.length = length;
	this.nationality = nationality;
	this.story = story;
	this.created_at = created_at;
}

	public Film(String id, String title, int year, String genre, int entries,
			int length, String nationality, String story) {
	super();
	this.id = id;
	this.title = title;
	this.year = year;
	this.genre = genre;
	this.entries = entries;
	this.length = length;
	this.nationality = nationality;
	this.story = story;
}

	public Film() {
		// TODO Auto-generated constructor stub
	}
	
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public int getEntries() { return entries; }
    public int getLength() { return length; }
    public String getNationality() { return nationality; }
    public String getStory() { return story; }
    public People getDirector() { return director; }
    public ArrayList<People> getActors() { return actors; }
    
	@Override
	public String toString() {
		return "Film [id=" + id + " title=" + title + ", year=" + year + ", genre=" + genre
				+ ", entries=" + entries + "]";
	}
	
	public void set_director(People director) {
		this.director = director;
	}
	
	public void add_actor(People actor) {
		this.actors.add(actor);
	}
	
}
