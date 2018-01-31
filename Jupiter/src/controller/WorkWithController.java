package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoSQL.DBManager;

/**
 * Servlet implementation class WorkWithController
 */
@WebServlet("/WorkWithController")
public class WorkWithController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public WorkWithController() {
        super();
    }

	DBManager db = DBManager.getInstance();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("WorkWithController called");
		
		String workWith = "";
		Cookie[] cookies = request.getCookies();
		
        for(int i = 0; i < cookies.length; i++) { 
            Cookie c = cookies[i];
            if (c.getName().equals("DB")) {
                workWith = c.getValue();
            }
        }
        
        /*
         * update work_with tabelle auf 1
         * 1 = SQL
         * 0 = NoSQL
         */
        if(workWith.equals("SQL")) {
        		db.getWorkWithDAO().setWorkwith(1);
        }else {
        		db.getWorkWithDAO().setWorkwith(0);
        }
		
        request.getRequestDispatcher("home").forward(request, response);
	}



}
