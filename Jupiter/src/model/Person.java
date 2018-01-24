package model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;


/**
 * 
 * @author Katarzyna Wadowska
 *
 */
public class Person {
	
	private int personalnr;
	private String vorname;
	private String nachname;
	private String geschlecht;
	
	private String geburtsdatum;
	private String email;
	private int rolle;
	
	
	
	/**
	 * @return the personalnr
	 */
	public int getPersonalnr() {
		return personalnr;
	}
	/**
	 * @param personalnr the personalnr to set
	 */
	public void setPersonalnr(int personalnr) {
		this.personalnr = personalnr;
	}
	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @return the geschlecht
	 */
	public String getGeschlecht() {
		return geschlecht;
	}
	/**
	 * @param geschlecht the geschlecht to set
	 */
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	/**
	 * @return the geburtsdatum
	 */
	public String getGeburtsdatum() {
		return geburtsdatum;
	}
	/**
	 * @param geburtsdatum the geburtsdatum to set
	 */
	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}


	/**
	 * @return the rolle
	 */
	public int getRolle() {
		return rolle;
	}
	/**
	 * @param rolle the rolle to set
	 */
	public void setRolle(int rolle) {
		this.rolle = rolle;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
