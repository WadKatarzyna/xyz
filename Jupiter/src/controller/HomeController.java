package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoNoSQL.GenericDAO;
import daoSQL.DBManager;
import helper.NextId;
import model.Account;
import model.Artikel;
import model.ArtikelKategorie;
import model.BestellteArtikel;
import model.Bestellung;
import model.Hersteller;
import model.Kategorie;
import model.Unterkategorie;
import model.Warenkorb;
import model.WarenkorbArtikel;

/**
 * Servlet implementation class HomeController
 * @param <T>
 */
@WebServlet("/home")
public class HomeController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//SQL DBManager
	DBManager daoSQL = DBManager.getInstance();
	//NoSQL DBManager
	GenericDAO<T> daoNoSQL = new GenericDAO<>();

	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		/**
		 * hier erhalten wir.
		 * 1 = SQL Datenbank
		 * 0 = NoSQL Datenbank
		 */
		int workWith = daoSQL.getWorkWithDAO().getWorkwith();
	        
		
		Account account = (Account) request.getSession().getAttribute("credentials");
		
		String searchText = "";
		String kategorieIDStr = "";
		String unterkategorieIDStr = "";
		String listedItemsKeyword = "";

		
		searchText = request.getParameter("searchText");
		kategorieIDStr = request.getParameter("kategorie");
		unterkategorieIDStr = request.getParameter("unterkategorie");
		
		boolean categorySet = false;
		int kategorieID = 0;
		int unterkategorieID = 0;

		List<Artikel> artikelList = new ArrayList<>();
		
		List<Kategorie> kategorieList = new ArrayList<>();
		List<Hersteller> herstellerList = new ArrayList<>();
		List<BestellteArtikel> bestelleArtikelList = new ArrayList<>();
		List<Unterkategorie> unterkategorieList = new ArrayList<>();
		List<ArtikelKategorie> artikelKategorieList = new ArrayList<>();
		List<Artikel> warenkorbartikellist = new ArrayList<>();
		Warenkorb warenkorb = null;
		Kategorie kategorie = null;
		
		if(workWith == 1) {
			System.out.println("working with SQL-DB");
			
			kategorieList = daoSQL.getKategorieDAO().findAll();
			herstellerList = daoSQL.getHerstellerDAO().findAll();
			bestelleArtikelList = daoSQL.getBestellteArtikelDAO().findAll();
			unterkategorieList = daoSQL.getUnterkategorieDAO().findAll();
			artikelKategorieList = daoSQL.getArtikelKategorieDAO().findAll();
				

			if (kategorieIDStr != null) {
				kategorieID = Integer.parseInt(kategorieIDStr);
				if (kategorieID != 11)
					categorySet = true;
			}
			
			if (unterkategorieIDStr != null) {
				unterkategorieID = Integer.parseInt(unterkategorieIDStr);
			}
			
			//Search from searchbar
			if ((searchText == null || searchText.isEmpty())) {
				artikelList = daoSQL.getArtikelDAO().findAll();
			} 
			else {
				artikelList = daoSQL.getArtikelDAO().sortWithKeyword(searchText);
			}

			
			
			kategorie = daoSQL.getKategorieDAO().findById(kategorieID);
			
			//list anhand nur kategorie
			if (categorySet && (unterkategorieID == 11 || unterkategorieID == 0)) {
				listedItemsKeyword = "Alle "+kategorie.getName() +"artikel erfolgreich angezeigt!";
				artikelList = new ArrayList<>();
				artikelList = daoSQL.getArtikelDAO().sortWithCategory(kategorieID);
			}
			
			if (unterkategorieID != 11 && unterkategorieID != 0) {
				Unterkategorie unterkategorie = daoSQL.getUnterkategorieDAO().findById(unterkategorieID);
				//list anhand beide unterkategorie und kategorie
				if (categorySet) {
					listedItemsKeyword = unterkategorie.getBezeichnung()+" "+kategorie.getName() +" erfolgreich angezeigt!";
					artikelList = daoSQL.getArtikelDAO().sortWithBothCcategory(kategorieID, unterkategorieID);
				} 
				//list anhand nur unterkategorie
				else {
					listedItemsKeyword = "Alle "+unterkategorie.getName()+" erfolgreich angezeigt!";
					artikelList = daoSQL.getArtikelDAO().sortWithUndercategory(unterkategorieID);
				}

			}
			
			if(account != null) {
				
				request.setAttribute("warenkorbArtikel", daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId()));
				
				for(WarenkorbArtikel w: daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId())){
					for(Artikel a : daoSQL.getArtikelDAO().findAll()) {
						if(w.getArtikelid() == a.getId()) {
							warenkorbartikellist.add(a);
						}
					}
				}
				
				for(Warenkorb w : daoSQL.getWarenkorbDAO().findAll()) {
					if(account.getId() == w.getAccountId()) {
						warenkorb = w;
					}
				}
			}
			
			/***
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
		}else if(workWith == 0) {
			System.out.println("working with NoSQL-DB");
			
			kategorieList = (List<Kategorie>) daoNoSQL.getAll(Kategorie.class);
			herstellerList = (List<Hersteller>) daoNoSQL.getAll(Hersteller.class);
			bestelleArtikelList = (List<BestellteArtikel>) daoNoSQL.getAll(BestellteArtikel.class);
			unterkategorieList = (List<Unterkategorie>) daoNoSQL.getAll(Unterkategorie.class);
			artikelKategorieList = (List<ArtikelKategorie>) daoNoSQL.getAll(ArtikelKategorie.class);
			

			if (kategorieIDStr != null) {
				kategorieID = Integer.parseInt(kategorieIDStr);
				if (kategorieID != 11)
					categorySet = true;
			}
			
			if (unterkategorieIDStr != null) {
				unterkategorieID = Integer.parseInt(unterkategorieIDStr);
			}
			
			//Search from searchbar
			if ((searchText == null || searchText.isEmpty())) {
				artikelList = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
			} 
			else {
				artikelList = (List<Artikel>) daoNoSQL.sortAllWithKeyword(Artikel.class, searchText);
			}

			
			kategorie = (Kategorie) daoNoSQL.getOneById(Kategorie.class, kategorieID);
			
			//list anhand nur kategorie
			if (categorySet && (unterkategorieID == 11 || unterkategorieID == 0)) {
				listedItemsKeyword = "Alle "+kategorie.getName() +"artikel erfolgreich angezeigt!";
				artikelList = new ArrayList<>();
				artikelList = (List<Artikel>) daoNoSQL.sortArtikelWithCategory(Artikel.class, kategorieID);
			}
			
			if (unterkategorieID != 11 && unterkategorieID != 0) {
				Unterkategorie unterkategorie = (Unterkategorie) daoNoSQL.getOneById(Unterkategorie.class, unterkategorieID);

				//list anhand beide unterkategorie und kategorie
				if (categorySet) {
					listedItemsKeyword = unterkategorie.getBezeichnung()+" "+kategorie.getName() +" erfolgreich angezeigt!";
					artikelList = (List<Artikel>) daoNoSQL.sortArtikelWithBothCategory(Artikel.class, unterkategorieID, kategorieID);
				
				} 
				//list anhand nur unterkategorie
				else {
					listedItemsKeyword = "Alle "+unterkategorie.getName()+" erfolgreich angezeigt!";
					artikelList = (List<Artikel>) daoNoSQL.sortArtikelWithUndercategory(Artikel.class, unterkategorieID);
				}

			}
			
			

			
			
			if(account != null) {
				request.setAttribute("warenkorbArtikel", daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, account.getId()));
				System.out.println("getAccountId "+account.getId());

				for(WarenkorbArtikel w: (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, account.getId())){
					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
					for(Artikel a : artikellist) {
						if(w.getArtikelid() == a.getId()) {
							warenkorbartikellist.add(a);
						}
					}
				}
				
				List<Warenkorb> warenkorblist = (List<Warenkorb>) daoNoSQL.getAll(Warenkorb.class);
				for(Warenkorb w : warenkorblist) {
					if(account.getId() == w.getAccountId()) {
						warenkorb = w;
					}
				}
		}
		
	}
		
			request.setAttribute("artikelList", artikelList);
			request.setAttribute("herstellerList", herstellerList);
			request.setAttribute("bestelleArtikelList", bestelleArtikelList);
			request.setAttribute("kategorieList", kategorieList);
			request.setAttribute("listedItemsKeyword", listedItemsKeyword);
			request.setAttribute("unterkategorieList", unterkategorieList);
			request.setAttribute("warenkorbartikellist", warenkorbartikellist);
			
			request.setAttribute("warennkorb",warenkorb);
		
			
		
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
	
	

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * hier erhalten wir.
		 * 1 = SQL Datenbank
		 * 0 = NoSQL Datenbank
		 */
		int workWith = daoSQL.getWorkWithDAO().getWorkwith();
        
		// 1 = SQL
        if(workWith == 1) {
	        	if(request.getParameter("typ").equals("cart")){
	    			//to cart
	    			
	    			boolean oldElement = false;
	    			
	    			String accountId = request.getParameter("accountId");
	    			if (accountId == null || accountId.isEmpty()) {
	    				accountId = "1"; //default
	    			}
	    			
	    			
	    			//finde warenkorb für die account
	    			List<Warenkorb> warenkorbList = daoSQL.getWarenkorbDAO().findAll();
	    			Warenkorb warenkorb = null;
	    			for(Warenkorb w : warenkorbList) {
	    				if(w.getAccountId()== Integer.parseInt(accountId)) {
	    					warenkorb = w;
	    				}
	    			}
	    					
	    			//erstelle warenkorb
	    			if (warenkorb == null) {
	    				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    				String date = df.format(new Date());
	    				
	    				warenkorb = new Warenkorb();
	    				warenkorb.setAccountId(Integer.parseInt(accountId));
	    				warenkorb.setErstelldatum(date);
	    				warenkorb.setId(daoSQL.getWarenkorbDAO().create(warenkorb));
	    				
	    				
	    			}
	    			
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			
	    			
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					oldElement = true;
	    					
	    					for(Artikel artikel : daoSQL.getArtikelDAO().findAll()) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					
	    					
	    					daoSQL.getWarenkorbArtikelDAO().update(a);
	    				}
	    			}
	    			
	    			//wenn keine artikel im warenkorb..
	    			if (!oldElement) {
	    				WarenkorbArtikel warenkorbartikel = new WarenkorbArtikel();
	    				warenkorbartikel.setWarenkorbid(warenkorb.getId());
	    				warenkorbartikel.setArtikelid(artikelId);
	    				warenkorbartikel.setMenge(1);
	    				
	    				for(Artikel a : daoSQL.getArtikelDAO().findAll()) {
	    					if(a.getId()== artikelId) {
	    						warenkorbartikel.setSumme(a.getPreis());
	    						
	    					}
	    				}
	    				
	    				daoSQL.getWarenkorbArtikelDAO().create(warenkorbartikel);
	    			}
	
	    		}else if(request.getParameter("typ").equals("remove")){
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			daoSQL.getWarenkorbArtikelDAO().deleteByarticleId(artikelId);
	    			
	    		}else if(request.getParameter("typ").equals("increase")){
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	
	    			
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					for(Artikel artikel : daoSQL.getArtikelDAO().findAll()) {
	    						if(artikel.getId()== artikelId) {
	    							
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					daoSQL.getWarenkorbArtikelDAO().update(a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("decrease")) {
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	    			
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					for(Artikel artikel : daoSQL.getArtikelDAO().findAll()) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					
	    					daoSQL.getWarenkorbArtikelDAO().updateDecrease(a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("checkout")) {
	    			
	    			System.out.println("in checkout");
	    			
	    			String accountId = request.getParameter("accountId");
	    			Double total = Double.parseDouble(request.getParameter("total"));
	    			
	    			
	    			List<WarenkorbArtikel> warenkorbArtikelList = daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId));
	    			
	    			Bestellung bestellung = new Bestellung();
	    			bestellung.setSumme(total);
	    			
	    			//lieferdatum = heute + 5 tage
	    			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    			Date dt = new Date();
	    			Calendar c = Calendar.getInstance(); 
	    			c.setTime(dt); 
	    			c.add(Calendar.DATE, 5);
	    			dt = c.getTime();
	    			
	    			String date = df.format(dt);
	    			
	    			
	    			bestellung.setLieferdatum(date);
	    			bestellung.setAccountId(Integer.parseInt(accountId));
	    			int bestellungID = daoSQL.getBestellungDAO().create(bestellung);
	    			
	    			
	    			for(WarenkorbArtikel w : warenkorbArtikelList) {
	    				BestellteArtikel ba = new BestellteArtikel();
	    				ba.setBestellungId(bestellungID);
	    				ba.setArtikelId(w.getArtikelid());
	    				ba.setMenge(w.getMenge());
	    				for(Artikel a : daoSQL.getArtikelDAO().findAll()) {
	    					if(a.getId() == w.getArtikelid()) {
	    						ba.setPreis(a.getPreis());
	    					}
	    				}
	    				daoSQL.getBestellteArtikelDAO().create(ba);
	    			}
	    			
	    			//lösche warenkorb nach bestellung
	    			for(WarenkorbArtikel wa : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				daoSQL.getWarenkorbArtikelDAO().deleteByarticleId(wa.getArtikelid());
	    			}
    			
	    		}
	        	//0 = NoSQL
        }else if(workWith == 0) {
        		
	        	if(request.getParameter("typ").equals("cart")){
	    			//to cart
	    			
	    			boolean oldElement = false;
	    			
	    			String accountId = request.getParameter("accountId");
	    			if (accountId == null || accountId.isEmpty()) {
	    				accountId = "1"; //default
	    			}
	    			
	    			
	    			//finde warenkorb für die account
	    			List<Warenkorb> warenkorbList = (List<Warenkorb>) daoNoSQL.getAll(Warenkorb.class);
	    			Warenkorb warenkorb = null;
	    			for(Warenkorb w : warenkorbList) {
	    				if(w.getAccountId()== Integer.parseInt(accountId)) {
	    					warenkorb = w;
	    				}
	    			}
	    					
	    			//erstelle warenkorb
	    			if (warenkorb == null) {
	    				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    				String date = df.format(new Date());
	    				
	    				warenkorb = new Warenkorb();
	    				warenkorb.setAccountId(Integer.parseInt(accountId));
	    				warenkorb.setErstelldatum(date);
	    				warenkorb.setId(daoNoSQL.createWithTwoIDs((T) warenkorb));
	    				
	    			}
	    			
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			
	    			
	    			for( WarenkorbArtikel a : (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, Integer.parseInt(accountId))) {
	    				System.out.println("accountID "+accountId);
	    				if(a.getArtikelid() == artikelId) {
	    					oldElement = true;
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					
	    					daoNoSQL.updateWarenkorbArtikel((T) a, a.getArtikelid(), a);
	    				}
	    			}
	    			
	    			//wenn keine artikel im warenkorb..
	    			if (!oldElement) {
	    				WarenkorbArtikel warenkorbartikel = new WarenkorbArtikel();
	    				warenkorbartikel.setWarenkorbid(warenkorb.getId());
	    				warenkorbartikel.setArtikelid(artikelId);
	    				warenkorbartikel.setMenge(1);
	    				
	    				List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    				for(Artikel a : artikellist) {
	    					if(a.getId()== artikelId) {
	    						warenkorbartikel.setSumme(a.getPreis());
	    						
	    					}
	    				}
	    				daoNoSQL.create((T)warenkorbartikel);
	    			}
	
	    		}else if(request.getParameter("typ").equals("remove")){
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			daoNoSQL.delete(WarenkorbArtikel.class, artikelId);
	    			
	    		}else if(request.getParameter("typ").equals("increase")){
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	
	    			for( WarenkorbArtikel a : (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					daoNoSQL.updateWarenkorbArtikel((T) a, a.getArtikelid(), a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("decrease")) {
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	    			
	    			for( WarenkorbArtikel a : (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					daoNoSQL.updateDecrease((T) a, artikelId, a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("checkout")) {
	    			
	    			System.out.println("in checkout");
	    			
	    			String accountId = request.getParameter("accountId");
	    			Double total = Double.parseDouble(request.getParameter("total"));
	    			
	    			List<WarenkorbArtikel> warenkorbArtikelList = (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, Integer.parseInt(accountId));
	    			
	    			Bestellung bestellung = new Bestellung();
	    			bestellung.setSumme(total);
	    			
	    			//lieferdatum = heute + 5 tage
	    			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    			Date dt = new Date();
	    			Calendar c = Calendar.getInstance(); 
	    			c.setTime(dt); 
	    			c.add(Calendar.DATE, 5);
	    			dt = c.getTime();
	    			String date = df.format(dt);
	    			
	    			bestellung.setId(NextId.getNextId(Bestellung.class.getSimpleName()));
	    			bestellung.setLieferdatum(date);
	    			bestellung.setAccountId(Integer.parseInt(accountId));

	    			int bestellungID = daoNoSQL.create((T) bestellung);
	    			
	    			
	    			for(WarenkorbArtikel w : warenkorbArtikelList) {
	    				BestellteArtikel ba = new BestellteArtikel();
	    				ba.setBestellungId(bestellungID);
	    				ba.setArtikelId(w.getArtikelid());
	    				ba.setMenge(w.getMenge());
	    				
	    				List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    				for(Artikel a : artikellist) {
	    					if(a.getId() == w.getArtikelid()) {
	    						ba.setPreis(a.getPreis());
	    					}
	    				}
	    				daoNoSQL.create((T) ba);
	    			}
	    			
	    			//lösche warenkorb nach bestellung
	    			for(WarenkorbArtikel wa : (List<WarenkorbArtikel>) daoNoSQL.allCartItemsByAccountId(WarenkorbArtikel.class, Integer.parseInt(accountId))) {
	    				daoNoSQL.delete(WarenkorbArtikel.class, wa.getArtikelid());
	    			}
				
	    		}
        }
		
		
		
		
		
		doGet(request, response);
	}

}
