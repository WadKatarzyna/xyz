package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ArtikelDAO;
import dao.BestellteArtikelDAO;
import dao.DBManager;
import dao.HerstellerDAO;
import model.Account;
import model.Artikel;
import model.ArtikelKategorie;
import model.BestellteArtikel;
import model.Hersteller;
import model.Warenkorb;
import model.WarenkorbArtikel;
import model.Kategorie;
import model.Unterkategorie;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBManager db = DBManager.getInstance();

	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		List<Kategorie> kategorieList = db.getKategorieDAO().findAll();
		List<Artikel> artikelList = new ArrayList<>();
		List<Hersteller> herstellerList = db.getHerstellerDAO().findAll();
		List<BestellteArtikel> bestelleArtikelList = db.getBestellteArtikelDAO().findAll();
		List<Unterkategorie> unterkategorieList = db.getUnterkategorieDAO().findAll();
		List<ArtikelKategorie> artikelKategorieList = db.getArtikelKategorieDAO().findAll();

		if (kategorieIDStr != null) {
			kategorieID = Integer.parseInt(kategorieIDStr);
			if (kategorieID != 11)
				categorySet = true;
		}
		
		if (unterkategorieIDStr != null) {
			unterkategorieID = Integer.parseInt(unterkategorieIDStr);
		}
		
		//Search from searchbar
		if (searchText == null || searchText.isEmpty()) {
			artikelList = db.getArtikelDAO().findAll();
		} 
		else {
			artikelList = db.getArtikelDAO().sortWithKeyword(searchText);
		}
		
		Kategorie kategorie = db.getKategorieDAO().findById(kategorieID);
		
		//list anhand nur kategorie
		if (categorySet && (unterkategorieID == 11 || unterkategorieID == 0)) {
			listedItemsKeyword = "Alle "+kategorie.getName() +"artikel erfolgreich angezeigt!";
			artikelList = new ArrayList<>();
			artikelList = db.getArtikelDAO().sortWithCategory(kategorieID);
		}
		
		if (unterkategorieID != 11 && unterkategorieID != 0) {
			Unterkategorie unterkategorie = db.getUnterkategorieDAO().findById(unterkategorieID);
			
			//list anhand beide unterkategorie und kategorie
			if (categorySet) {
				listedItemsKeyword = unterkategorie.getBezeichnung()+" "+kategorie.getName() +" erfolgreich angezeigt!";
				artikelList = db.getArtikelDAO().sortWithBothCcategory(kategorieID, unterkategorieID);
			} 
			//list anhand nur unterkategorie
			else {
				listedItemsKeyword = "Alle "+unterkategorie.getName()+" erfolgreich angezeigt!";
				artikelList = db.getArtikelDAO().sortWithUndercategory(unterkategorieID);
			}

		}
		
		
		request.setAttribute("artikelList", artikelList);
		request.setAttribute("herstellerList", herstellerList);
		request.setAttribute("bestelleArtikelList", bestelleArtikelList);
		request.setAttribute("kategorieList", kategorieList);
		request.setAttribute("listedItemsKeyword", listedItemsKeyword);
		request.setAttribute("unterkategorieList", unterkategorieList);
		
		
		if(account != null) {
			List<Artikel> warenkorbartikellist = new ArrayList<>();
			request.setAttribute("warenkorbArtikel", db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId()));
			
			for(WarenkorbArtikel w: db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId())){
				for(Artikel a : db.getArtikelDAO().findAll()) {
					if(w.getArtikelid() == a.getId()) {
						warenkorbartikellist.add(a);
					}
				}
			}
			request.setAttribute("warenkorbartikellist", warenkorbartikellist);
			Warenkorb warenkorb = null;
			for(Warenkorb w : db.getWarenkorbDAO().findAll()) {
				if(account.getId() == w.getAccountId()) {
					warenkorb = w;
				}
			}
			
			request.setAttribute("warennkorb",warenkorb);
		}
		
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getParameter("typ").equals("cart")){
			//to cart
			
			boolean oldElement = false;
			
			String accountId = request.getParameter("accountId");
			if (accountId == null || accountId.isEmpty()) {
				accountId = "1"; //default
			}
			
			
			//finde warenkorb für die account
			List<Warenkorb> warenkorbList = db.getWarenkorbDAO().findAll();
			Warenkorb warenkorb = null;
			for(Warenkorb w : warenkorbList) {
				if(w.getAccountId()== Integer.parseInt(accountId)) {
					warenkorb = w;
				}
			}
					
			if (warenkorb == null) {
				warenkorb = new Warenkorb();
				warenkorb.setAccountId(Integer.parseInt(accountId));
				warenkorb.setErstelldatum(new Date());
				warenkorb.setId(db.getWarenkorbDAO().create(warenkorb));
				/*
				WarenkorbArtikel warenkorbArtikel = new WarenkorbArtikel();
				warenkorbArtikel.setWarenkorbid(db.getWarenkorbDAO().findWarenkorbByCustomerId(Integer.parseInt(accountId)));
				warenkorbArtikel.setMenge(0);
				warenkorbArtikel.setSumme(0);
				*/
				
			}
			
			
			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
			
			//List<WarenkorbArtikel> warenkorbartikellist = db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId));
			
		
			

			
			
			
			for( WarenkorbArtikel a : db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
				if(a.getArtikelid() == artikelId) {
					oldElement = true;
					
					db.getWarenkorbArtikelDAO().update(a);
				}
			}
			
			if (!oldElement) {
				WarenkorbArtikel warenkorbartikel = new WarenkorbArtikel();
				warenkorbartikel.setWarenkorbid(warenkorb.getId());
				warenkorbartikel.setArtikelid(artikelId);
				warenkorbartikel.setMenge(1);
				warenkorbartikel.setSumme(8989);
				db.getWarenkorbArtikelDAO().create(warenkorbartikel);
			}
			
			
			
			
			
			

		}else if(request.getParameter("typ").equals("remove")){
			
			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
			
			
			db.getWarenkorbArtikelDAO().deleteByarticleId(artikelId);
			
			
		}else if(request.getParameter("typ").equals("increase")){
			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
			String accountId = request.getParameter("accountId");

			
			for( WarenkorbArtikel a : db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
				if(a.getArtikelid() == artikelId) {

					db.getWarenkorbArtikelDAO().update(a);
				}
			}
			
			
		}else if(request.getParameter("typ").equals("decrease")) {
			int artikelId = Integer.parseInt(request.getParameter("artikelId"));
			String accountId = request.getParameter("accountId");
			
			for( WarenkorbArtikel a : db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(Integer.parseInt(accountId))) {
				if(a.getArtikelid() == artikelId) {

					db.getWarenkorbArtikelDAO().updateDecrease(a);
				}
			}
			
		}
		
		
		
		doGet(request, response);
	}

}
