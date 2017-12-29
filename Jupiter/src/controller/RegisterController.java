package controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBManager;
import model.Account;
import model.Person;

/**
 * Servlet implementation class ReagisterController
 */
@WebServlet("/registration")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBManager db = DBManager.getInstance();

		try {
			
			String gebDatum   = request.getParameter("gebdatum");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) df.parse(gebDatum);
						
			Person person = new Person();
			Account account = new Account();
			
			person.setVorname(request.getParameter("vorname"));
			person.setNachname(request.getParameter("nachname"));
			person.setGeburtsdatum(date);
			person.setGeschlecht(request.getParameter("geschlecht"));
			person.setEmail(request.getParameter("email"));
			
			int person_personalnr = db.getPersonDAO().create(person);
			
			account.setUsername(request.getParameter("username"));
			account.setPasswort(request.getParameter("user_passwort"));
			account.setPerson_personalnr(person_personalnr);
			
			db.getAccountDAO().create(account);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		doGet(request, response);
	}

}
