package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBManager;
import model.Account;
import model.Artikel;

/**
 * Servlet implementation class BestellController
 */
@WebServlet("/bestell")
public class BestellController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBManager db = DBManager.getInstance();
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BestellController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute("credentials");
		
		if(account != null) {
			
			//request.setAttribute("warenkorbArtikel", db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(account.getId()));
			
			request.getRequestDispatcher("/WEB-INF/bestellung.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
