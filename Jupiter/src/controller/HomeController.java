package controller;

import java.io.IOException;
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
import model.BestellteArtikel;
import model.Hersteller;

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
		
		List<Artikel> artikelList = db.getArtikelDAO().findAll();
		List<Hersteller> herstellerList = db.getHerstellerDAO().findAll();
		List<BestellteArtikel> bestelleArtikelList = db.getBestellteArtikelDAO().findAll();
		
		request.setAttribute("artikelList", artikelList);
		request.setAttribute("herstellerList", herstellerList);
		request.setAttribute("bestelleArtikelList", bestelleArtikelList);
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}

}
