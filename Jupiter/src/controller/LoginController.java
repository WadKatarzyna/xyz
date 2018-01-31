package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoNoSQL.GenericDAO;
import daoSQL.DBManager;
import model.Account;

/**
 * Servlet implementation class LoginController
 * @param <T>
 */
@WebServlet("/login")
public class LoginController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean found = false;
		
		DBManager db = DBManager.getInstance();
		GenericDAO<T> daoNoSQL = new GenericDAO<>();
	
		/**
		 * hier erhalten wir.
		 * 1 = SQL Datenbank
		 * 0 = NoSQL Datenbank
		 */
		int workWith = db.getWorkWithDAO().getWorkwith();
		
		String username = request.getParameter("username");
		String passwort = request.getParameter("user_passwort");
		
		try {
			List<Account> accounts = new ArrayList<>();;
			// 1 = SQL
			if(workWith == 1) {
				accounts = db.getAccountDAO().findAll();
			}
			// 0 = NoSQL
			else if (workWith == 0) {
				accounts = (List<Account>) daoNoSQL.getAll(Account.class);
			}
			for(Account a : accounts) {
				if(a.getUsername().equals(username) && a.getPasswort().equals(passwort)) {
					request.getSession().setAttribute("credentials", a);
					found = true;
					break;
				}
			}
			if(!found){
				request.setAttribute("username", username);
				request.setAttribute("errmsg", "Username oder Passwort falsch");
				doGet(request, response);
			}else {
				response.sendRedirect(request.getContextPath()+ "/home");
			}
		}catch (IllegalArgumentException e) {
			
			request.setAttribute("username", username);
			request.setAttribute("errmsg", e.getMessage());
		}
		
	}

}
