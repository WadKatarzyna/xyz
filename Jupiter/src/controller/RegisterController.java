package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoNoSQL.GenericDAO;
import daoSQL.DBManager;
import model.Account;
import model.Person;

/**
 * Servlet implementation class ReagisterController
 * @param <T>
 */
@WebServlet("/registration")
public class RegisterController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
	}


	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBManager db = DBManager.getInstance();
		GenericDAO<T> daoNoSQL = new GenericDAO<>();

		
		/**
		 * hier erhalten wir.
		 * 1 = SQL Datenbank
		 * 0 = NoSQL Datenbank
		 */
		int workWith = db.getWorkWithDAO().getWorkwith();
        
       

		try {
			
			String gebDatum   = request.getParameter("gebdatum");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) df.parse(gebDatum);
						
			Person person = new Person();
			Account account = new Account();
			
			person.setVorname(request.getParameter("vorname"));
			person.setNachname(request.getParameter("nachname"));
			person.setGeburtsdatum(gebDatum);
			person.setGeschlecht(request.getParameter("geschlecht"));
			person.setEmail(request.getParameter("email"));
			
			//1 = SQL
			if(workWith == 1) {
				int person_personalnr = db.getPersonDAO().create(person);
				
				account.setUsername(request.getParameter("username"));
				account.setPasswort(request.getParameter("user_passwort"));
				account.setPerson_personalnr(person_personalnr);
				
				db.getAccountDAO().create(account);
				//0 = NoSQL
			}else if(workWith == 0) {
				int person_personalnr = daoNoSQL.create((T) person);
				
				account.setUsername(request.getParameter("username"));
				account.setPasswort(request.getParameter("user_passwort"));
				account.setPerson_personalnr(person_personalnr);

				daoNoSQL.createWithTwoIDs((T) account);				
		    }
			
			
			
			
		}catch(IllegalArgumentException | ParseException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/registration");
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/login");
		
		
		
	}

}
