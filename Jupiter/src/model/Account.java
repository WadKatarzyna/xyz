package model;

public class Account {
	
	private int id;
	private String username;
	private String erstelldatum;
	private String passwort;
	private int person_personalnr;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the erstelldatum
	 */
	public String getErstelldatum() {
		return erstelldatum;
	}
	/**
	 * @param erstelldatum the erstelldatum to set
	 */
	public void setErstelldatum(String erstelldatum) {
		this.erstelldatum = erstelldatum;
	}
	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}
	/**
	 * @param passwort the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	/**
	 * @return the person_personalnr
	 */
	public int getPerson_personalnr() {
		return person_personalnr;
	}
	/**
	 * @param person_personalnr the person_personalnr to set
	 */
	public void setPerson_personalnr(int person_personalnr) {
		this.person_personalnr = person_personalnr;
	}
	
	

}
