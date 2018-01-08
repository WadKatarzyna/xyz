package model;

import java.util.Date;

public class Bestellung {

	private int id;
	private String bestellzeitpunkt;
	private Date lieferdatum;
	private double summe;
	private int accountId;
	
	
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
	 * @return the bestellzeitpunkt
	 */
	public String getBestellzeitpunkt() {
		return bestellzeitpunkt;
	}
	/**
	 * @param bestellzeitpunkt the bestellzeitpunkt to set
	 */
	public void setBestellzeitpunkt(String bestellzeitpunkt) {
		this.bestellzeitpunkt = bestellzeitpunkt;
	}
	/**
	 * @return the lieferdatum
	 */
	public Date getLieferdatum() {
		return lieferdatum;
	}
	/**
	 * @param lieferdatum the lieferdatum to set
	 */
	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}
	/**
	 * @return the summe
	 */
	public double getSumme() {
		return summe;
	}
	/**
	 * @param summe the summe to set
	 */
	public void setSumme(double summe) {
		this.summe = summe;
	}
	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	
	
}
