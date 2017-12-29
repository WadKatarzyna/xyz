package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBManager;
import model.Account;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean found = false;
		
		DBManager db = DBManager.getInstance();
		
		String username = request.getParameter("username");
		String passwort = request.getParameter("user_passwort");
		
		
		try {
			
			List<Account> accounts = db.getAccountDAO().findAll();
			
			for(Account a : accounts) {
				if(a.getUsername().equals(username) && a.getPasswort().equals(passwort)) {
					request.getSession().setAttribute("credentials", a.getUsername());
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
