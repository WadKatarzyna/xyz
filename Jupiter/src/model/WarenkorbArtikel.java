package model;

public class WarenkorbArtikel {
	
	private int warenkorbid;
	private int artikelid;
	private int menge;
	private double summe;
	
	
	/**
	 * @return the warenkorbid
	 */
	public int getWarenkorbid() {
		return warenkorbid;
	}
	/**
	 * @param warenkorbid the warenkorbid to set
	 */
	public void setWarenkorbid(int warenkorbid) {
		this.warenkorbid = warenkorbid;
	}
	/**
	 * @return the artikelid
	 */
	public int getArtikelid() {
		return artikelid;
	}
	/**
	 * @param artikelid the artikelid to set
	 */
	public void setArtikelid(int artikelid) {
		this.artikelid = artikelid;
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
	
}
