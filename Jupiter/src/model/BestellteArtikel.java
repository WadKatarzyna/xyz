package model;

public class BestellteArtikel {

	
	private int bestellungId;
	private int artikelId;
	private int menge;
	private double preis;
	
	
	
	/**
	 * @return the bestellungId
	 */
	public int getBestellungId() {
		return bestellungId;
	}
	/**
	 * @param bestellungId the bestellungId to set
	 */
	public void setBestellungId(int bestellungId) {
		this.bestellungId = bestellungId;
	}
	/**
	 * @return the artikelId
	 */
	public int getArtikelId() {
		return artikelId;
	}
	/**
	 * @param artikelId the artikelId to set
	 */
	public void setArtikelId(int artikelId) {
		this.artikelId = artikelId;
	}
	/**
	 * @return the menge
	 */
	public int getMenge() {
		return menge;
	}
	/**
	 * @param menge the menge to set
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}
	/**
	 * @return the preis
	 */
	public double getPreis() {
		return preis;
	}
	/**
	 * @param preis the preis to set
	 */
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	
}
