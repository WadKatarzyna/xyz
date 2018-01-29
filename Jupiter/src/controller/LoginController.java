package controller;

import java.io.IOException;
import java.util.ArrayList;
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

/**
 * Servlet implementation class LoginController
 * @param <T>
 */
@WebServlet("/login")
public class LoginController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean found = false;
		
		DBManager db = DBManager.getInstance();
		GenericDAO<T> daoNoSQL = new GenericDAO<>();
		Cookie[] cookies = request.getCookies();
		String workWith = "";

        for(int i = 0; i < cookies.length; i++) { 
            Cookie c = cookies[i];
            if (c.getName().equals("DB")) {
                workWith = c.getValue();
            }
        }
		
		String username = request.getParameter("username");
		String passwort = request.getParameter("user_passwort");
		
		
		try {
			List<Account> accounts = new ArrayList<>();;
			if(workWith.equals("SQL")) {
				accounts = db.getAccountDAO().findAll();
			}
			else if (workWith.equals("NoSQL")) {
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
			}
		}catch (IllegalArgumentException e) {
			request.setAttribute("username", username);
			request.setAttribute("errmsg", e.getMessage());
			doGet(request, response);
			return;
		}
		
		response.sendRedirect(request.getContextPath()+ "/home");
		
		
	}

}
