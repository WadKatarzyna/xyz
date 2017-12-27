package model;

import java.util.Date;


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
	private Date geburtsdatum;
	private Long gehalt;
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
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	/**
	 * @param geburtsdatum the geburtsdatum to set
	 */
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	/**
	 * @return the gehalt
	 */
	public Long getGehalt() {
		return gehalt;
	}
	/**
	 * @param gehalt the gehalt to set
	 */
	public void setGehalt(Long gehalt) {
		this.gehalt = gehalt;
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
	
}
