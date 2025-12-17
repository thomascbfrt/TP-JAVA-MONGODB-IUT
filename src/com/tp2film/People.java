package com.tp2film;

public class People {
	private String firstname;
	private String lastname;
	private String role;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
	
	/**
	 */
	public People(String firstname, String lastname, String role) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
	
	public People(String firstname, String lastname) {
		this(firstname, lastname, null);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
