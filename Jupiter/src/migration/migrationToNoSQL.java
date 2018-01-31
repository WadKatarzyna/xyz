package migration;

import java.util.ArrayList;
import java.util.List;

import daoNoSQL.GenericDAO;
import daoSQL.DBManager;
import model.Account;
import model.Artikel;
import model.ArtikelKategorie;
import model.BestellteArtikel;
import model.Bestellung;
import model.Hersteller;
import model.Kategorie;
import model.Unterkategorie;
import model.Person;
import model.Warenkorb;
import model.WarenkorbArtikel;

public class migrationToNoSQL {

	
	
	@SuppressWarnings("unchecked")
	public static <T> void main(String[] args) {
		System.out.println("migration started");
		//SQL DBManager
		DBManager daoSQL = DBManager.getInstance();
		//NoSQL DBManager
		GenericDAO<T> daoNoSQL = new GenericDAO<>();
		
		
		/**
		 * Hersteller migration
		 */
		List<Hersteller> herstellerListFromSQL = new ArrayList<>();
		List<Hersteller> herstellerListFromNoSQL = new ArrayList<>();
		herstellerListFromSQL   = daoSQL.getHerstellerDAO().findAll();
		herstellerListFromNoSQL = (List<Hersteller>) daoNoSQL.getAll(Hersteller.class);
		
		for(Hersteller herstellerSQL : herstellerListFromSQL) {
			Boolean found = false;
			for(Hersteller herstellerNoSQL :herstellerListFromNoSQL) {
				if(herstellerSQL.getId() == herstellerNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) herstellerSQL);
			}
		}
		
		
		
		/**
		 *  Person migration
		 */
		List<Person> personListFromSQL = new ArrayList<Person>();
		List<Person> personListFromNoSQL = new ArrayList<Person>();
		personListFromSQL   = daoSQL.getPersonDAO().findAll();
		personListFromNoSQL = (List<Person>) daoNoSQL.getAll(Person.class);
		
		for(Person personSQL : personListFromSQL) {
			Boolean found = false;
			for(Person personNoSQL :personListFromNoSQL) {
				if(personSQL.getPersonalnr() == personNoSQL.getPersonalnr()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) personSQL);
			}
		}
		
		
		
		/**
		 * Account migration
		 */
		List<Account> accountListFromSQL = new ArrayList<Account>();
		List<Account> accountListFromNoSQL = new ArrayList<Account>();
		accountListFromSQL   = daoSQL.getAccountDAO().findAll();
		accountListFromNoSQL = (List<Account>) daoNoSQL.getAll(Account.class);
		
		for(Account accountSQL : accountListFromSQL) {
			Boolean found = false;
			for(Account accountNoSQL :accountListFromNoSQL) {
				if(accountSQL.getId() == accountNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) accountSQL);
			}
		}
		
		
		
		/**
		 * Bestellung migration
		 */
		List<Bestellung> bestellungListFromSQL = new ArrayList<>();
		List<Bestellung> bestellungListFromNoSQL = new ArrayList<>();
		bestellungListFromSQL   = daoSQL.getBestellungDAO().findAll();
		bestellungListFromNoSQL = (List<Bestellung>) daoNoSQL.getAll(Bestellung.class);
		
		for(Bestellung bestellungSQL : bestellungListFromSQL) {
			Boolean found = false;
			for(Bestellung bestellungNoSQL :bestellungListFromNoSQL) {
				if(bestellungSQL.getId() == bestellungNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) bestellungSQL);
			}
		}
		
		
		
		/**
		 * artikel migration
		 */
		List<Artikel> artikelListFromSQL = new ArrayList<>();
		List<Artikel> artikelListFromNoSQL = new ArrayList<>();
		artikelListFromSQL   = daoSQL.getArtikelDAO().findAll();
		artikelListFromNoSQL = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
		
		for(Artikel artikelSQL : artikelListFromSQL) {
			Boolean found = false;
			for(Artikel artikelNoSQL :artikelListFromNoSQL) {
				if(artikelSQL.getId() == artikelNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) artikelSQL);
			}
		}
		
		
		/**
		 * bestellteArtikel migration
		 */
		List<BestellteArtikel> bestellteArtikelListFromSQL = new ArrayList<>();
		List<BestellteArtikel> bestellteArtikelListFromNoSQL = new ArrayList<>();
		bestellteArtikelListFromSQL   = daoSQL.getBestellteArtikelDAO().findAll();
		bestellteArtikelListFromNoSQL = (List<BestellteArtikel>) daoNoSQL.getAll(BestellteArtikel.class);
		
		for(BestellteArtikel bestellteArtikelSQL : bestellteArtikelListFromSQL) {
			Boolean found = false;
			for(BestellteArtikel bestellteArtikelNoSQL :bestellteArtikelListFromNoSQL) {
				if(bestellteArtikelSQL.getBestellungId() == bestellteArtikelNoSQL.getBestellungId()) {
					if(bestellteArtikelSQL.getArtikelId() == bestellteArtikelNoSQL.getArtikelId()) {
						found = true;
					}
					
				}
			}
			if(!found) {
				daoNoSQL.create((T) bestellteArtikelSQL);
			}
		}
		
		
		/**
		 * kategorie migration
		 */
		List<Kategorie> kategorieListFromSQL = new ArrayList<>();
		List<Kategorie> kategorieListFromNoSQL = new ArrayList<>();
		kategorieListFromSQL   = daoSQL.getKategorieDAO().findAll();
		kategorieListFromNoSQL = (List<Kategorie>) daoNoSQL.getAll(Kategorie.class);
		
		for(Kategorie kategorieSQL : kategorieListFromSQL) {
			Boolean found = false;
			for(Kategorie kategorieNoSQL :kategorieListFromNoSQL) {
				if(kategorieSQL.getId() == kategorieNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) kategorieSQL);
			}
		}
		
		/**
		 * unterkategorie migration
		 */
		List<Unterkategorie> unterkategorieListFromSQL = new ArrayList<>();
		List<Unterkategorie> unterkategorieListFromNoSQL = new ArrayList<>();
		unterkategorieListFromSQL   = daoSQL.getUnterkategorieDAO().findAll();
		unterkategorieListFromNoSQL = (List<Unterkategorie>) daoNoSQL.getAll(Unterkategorie.class);
		
		for(Unterkategorie unterkategorieSQL : unterkategorieListFromSQL) {
			Boolean found = false;
			for(Unterkategorie unterkategorieNoSQL :unterkategorieListFromNoSQL) {
				if(unterkategorieSQL.getId() == unterkategorieNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) unterkategorieSQL);
			}
		}
		
		
		/**
		 * artikelkategorie migration
		 */
		List<ArtikelKategorie> artikelKategorieListFromSQL = new ArrayList<>();
		List<ArtikelKategorie> artikelKategorieListFromNoSQL = new ArrayList<>();
		artikelKategorieListFromSQL   = daoSQL.getArtikelKategorieDAO().findAll();
		artikelKategorieListFromNoSQL = (List<ArtikelKategorie>) daoNoSQL.getAll(ArtikelKategorie.class);
		
		for(ArtikelKategorie artikelKategorieSQL : artikelKategorieListFromSQL) {
			Boolean found = false;
			for(ArtikelKategorie artikelKategorieNoSQL :artikelKategorieListFromNoSQL) {
				if(artikelKategorieSQL.getArtikel_id() == artikelKategorieNoSQL.getArtikel_id()) {
					if(artikelKategorieSQL.getKategorie_id() == artikelKategorieNoSQL.getKategorie_id()) {
						found = true;
					}
				}
			}
			if(!found) {
				daoNoSQL.create((T) artikelKategorieSQL);
			}
		}
		
		
		
		/**
		 * warenkorb migration
		 */
		List<Warenkorb> warenkorbListFromSQL = new ArrayList<>();
		List<Warenkorb> warenkorbListFromNoSQL = new ArrayList<>();
		warenkorbListFromSQL   = daoSQL.getWarenkorbDAO().findAll();
		warenkorbListFromNoSQL = (List<Warenkorb>) daoNoSQL.getAll(Warenkorb.class);
		
		for(Warenkorb warenkorbSQL : warenkorbListFromSQL) {
			Boolean found = false;
			for(Warenkorb warenkorbNoSQL :warenkorbListFromNoSQL) {
				if(warenkorbSQL.getId() == warenkorbNoSQL.getId()) {
					found = true;
				}
			}
			if(!found) {
				daoNoSQL.create((T) warenkorbSQL);
			}
		}
		
		
		
		/**
		 * warenkorbArtikel migration
		 */
		List<WarenkorbArtikel> warenkorbArtikelListFromSQL = new ArrayList<>();
		List<WarenkorbArtikel> warenkorbArtikelListFromNoSQL = new ArrayList<>();
		warenkorbArtikelListFromSQL   = daoSQL.getWarenkorbArtikelDAO().findAll();
		warenkorbArtikelListFromNoSQL = (List<WarenkorbArtikel>) daoNoSQL.getAll(WarenkorbArtikel.class);
		
		for(WarenkorbArtikel warenkorbSQL : warenkorbArtikelListFromSQL) {
			Boolean found = false;
			for(WarenkorbArtikel warenkorbNoSQL :warenkorbArtikelListFromNoSQL) {
				if(warenkorbSQL.getWarenkorbid() == warenkorbNoSQL.getWarenkorbid()) {
					if(warenkorbSQL.getArtikelid() == warenkorbNoSQL.getArtikelid()) {
						found = true;
					}
				}
			}
			if(!found) {
				daoNoSQL.create((T) warenkorbSQL);
			}
		}
		System.out.println("migration ended");
		
	}
	
	

}
