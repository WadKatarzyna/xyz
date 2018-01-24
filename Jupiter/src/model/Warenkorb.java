package model;

import java.util.Date;

public class Warenkorb {
	
	private int id;
	private String erstelldatum;
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
