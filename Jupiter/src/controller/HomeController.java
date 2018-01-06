package controller;

import java.io.IOException;
import java.util.ArrayList;
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
import model.Artikel;
import model.ArtikelKategorie;
import model.BestellteArtikel;
import model.Hersteller;
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
		
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}

}
