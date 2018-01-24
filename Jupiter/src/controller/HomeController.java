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

import dao.DBManager;
import daoNoSQL.GenericDAO;
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
		 * Entscheidungspunkt...
		 * SQL oder NoSQL
		 */
		Cookie[] cookies = request.getCookies();
		String workWith = "";

        for(int i = 0; i < cookies.length; i++) { 
            Cookie c = cookies[i];
            if (c.getName().equals("DB")) {
                workWith = c.getValue();
            }
        }
	        
	        
		
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
		
		if(workWith.equals("SQL")) {
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
				System.out.println(1);
			} 
			else {
				artikelList = daoSQL.getArtikelDAO().sortWithKeyword(searchText);
				System.out.println(2);
			}

			
			
			kategorie = daoSQL.getKategorieDAO().findById(kategorieID);
			
			//list anhand nur kategorie
			if (categorySet && (unterkategorieID == 11 || unterkategorieID == 0)) {
				listedItemsKeyword = "Alle "+kategorie.getName() +"artikel erfolgreich angezeigt!";
				artikelList = new ArrayList<>();
				artikelList = daoSQL.getArtikelDAO().sortWithCategory(kategorieID);
				System.out.println(3);
			}
			
			if (unterkategorieID != 11 && unterkategorieID != 0) {
				Unterkategorie unterkategorie = daoSQL.getUnterkategorieDAO().findById(unterkategorieID);
				System.out.println(4);
				//list anhand beide unterkategorie und kategorie
				if (categorySet) {
					listedItemsKeyword = unterkategorie.getBezeichnung()+" "+kategorie.getName() +" erfolgreich angezeigt!";
					artikelList = daoSQL.getArtikelDAO().sortWithBothCcategory(kategorieID, unterkategorieID);
					System.out.println(5);
				} 
				//list anhand nur unterkategorie
				else {
					listedItemsKeyword = "Alle "+unterkategorie.getName()+" erfolgreich angezeigt!";
					artikelList = daoSQL.getArtikelDAO().sortWithUndercategory(unterkategorieID);
					System.out.println(6);
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
		}else if(workWith.equals("NoSQL")) {
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
				//TODO: create a new nosql method for this
				artikelList = daoSQL.getArtikelDAO().sortWithKeyword(searchText);
			}

			
			kategorie = (Kategorie) daoNoSQL.getOneById(Kategorie.class, kategorieID);
			
			//list anhand nur kategorie
			if (categorySet && (unterkategorieID == 11 || unterkategorieID == 0)) {
				listedItemsKeyword = "Alle "+kategorie.getName() +"artikel erfolgreich angezeigt!";
				artikelList = new ArrayList<>();
				//TODO: change to nosql
				artikelList = daoSQL.getArtikelDAO().sortWithCategory(kategorieID);
			}
			
			if (unterkategorieID != 11 && unterkategorieID != 0) {
				Unterkategorie unterkategorie = (Unterkategorie) daoNoSQL.getOneById(Unterkategorie.class, unterkategorieID);

				//list anhand beide unterkategorie und kategorie
				if (categorySet) {
					listedItemsKeyword = unterkategorie.getBezeichnung()+" "+kategorie.getName() +" erfolgreich angezeigt!";
					//TODO: change to nosql
					artikelList = daoSQL.getArtikelDAO().sortWithBothCcategory(kategorieID, unterkategorieID);
				
				} 
				//list anhand nur unterkategorie
				else {
					listedItemsKeyword = "Alle "+unterkategorie.getName()+" erfolgreich angezeigt!";
					//TODO:change to nosql
					artikelList = daoSQL.getArtikelDAO().sortWithUndercategory(unterkategorieID);
				}

			}
			
			

			
			
			if(account != null) {
				//TODO:change to nosql
				request.setAttribute("warenkorbArtikel", daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId()));
				//TODO:change to nosql
				for(WarenkorbArtikel w: daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId())){
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
		 * Entscheidungspunkt...
		 * SQL oder NoSQL
		 */
		Cookie[] cookies = request.getCookies();
		String workWith = "";

        for(int i = 0; i < cookies.length; i++) { 
            Cookie c = cookies[i];
            if (c.getName().equals("DB")) {
                workWith = c.getValue();
            }
        }
        
        if(workWith.equals("SQL")) {
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
	    			Calendar cal = Calendar.getInstance();
	    			
	    			int day = Calendar.DAY_OF_MONTH + 5;
	    			cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 5 );
	    			
	    			Date d = cal.getTime();
	    			String date = df.format(d);
	    			
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
        }else if(workWith.equals("NoSQL")) {
        		
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
	    				//TODO:
	    				warenkorb.setId(daoSQL.getWarenkorbDAO().create(warenkorb));
	    			
	    				
	    			}
	    			
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			
	    			
	    			//TODO:
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					oldElement = true;
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					
	    					//TODO:
	    					daoSQL.getWarenkorbArtikelDAO().update(a);
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
	    				//TODO:
	    				daoSQL.getWarenkorbArtikelDAO().create(warenkorbartikel);
	    			}
	
	    		}else if(request.getParameter("typ").equals("remove")){
	    			
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			//TODO:
	    			daoSQL.getWarenkorbArtikelDAO().deleteByarticleId(artikelId);
	    			
	    		}else if(request.getParameter("typ").equals("increase")){
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	
	    			//TODO:
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					//TODO:
	    					daoSQL.getWarenkorbArtikelDAO().update(a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("decrease")) {
	    			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
	    			String accountId = request.getParameter("accountId");
	    			
	    			//TODO:
	    			for( WarenkorbArtikel a : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				if(a.getArtikelid() == artikelId) {
	    					
	    					List<Artikel> artikellist = (List<Artikel>) daoNoSQL.getAll(Artikel.class);
	    					for(Artikel artikel : artikellist) {
	    						if(artikel.getId()== artikelId) {
	    							a.setSumme(artikel.getPreis());
	    							
	    						}
	    					}
	    					//TODO:
	    					daoSQL.getWarenkorbArtikelDAO().updateDecrease(a);
	    				}
	    			}
	    			
	    		}else if(request.getParameter("typ").equals("checkout")) {
	    			
	    			System.out.println("in checkout");
	    			
	    			String accountId = request.getParameter("accountId");
	    			Double total = Double.parseDouble(request.getParameter("total"));
	    			
	    			//TODO:
	    			List<WarenkorbArtikel> warenkorbArtikelList = daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId));
	    			
	    			Bestellung bestellung = new Bestellung();
	    			bestellung.setSumme(total);
	    			
	    			//lieferdatum = heute + 5 tage
	    			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    			Calendar cal = Calendar.getInstance();
	    			
	    			int day = Calendar.DAY_OF_MONTH + 5;
	    			cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 5 );
	    			
	    			Date d = cal.getTime();
	    			String date = df.format(d);
	    			
	    			bestellung.setLieferdatum(date);
	    			bestellung.setAccountId(Integer.parseInt(accountId));
	    			//TODO:
	    			int bestellungID = daoSQL.getBestellungDAO().create(bestellung);
	    			
	    			
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
	    			//TODO:
	    			for(WarenkorbArtikel wa : daoSQL.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
	    				//TODO:
	    				daoSQL.getWarenkorbArtikelDAO().deleteByarticleId(wa.getArtikelid());
	    			}
				
	    		}
        }
		
		
		
		
		
		doGet(request, response);
	}

}
