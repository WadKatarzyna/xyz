package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.Warenkorb;

/**
 * <h1> Database Manager</h1>
 * 
 * Connection for SQL-DB
 * 
 * @author Katarzyna Wadowska
 */
public class DBManager {

	private static DBManager instance = new DBManager();

	private static Connection connection;

	private AccountDAO accountDAO;
	private AdminDAO adminDAO;
	private ArtikelDAO artikelDAO;
	private ArtikelKategorieDAO artikelKategorieDAO;
	private BestellteArtikelDAO bestellteArtikelDAO;
	private HerstellerDAO herstellerDAO;
	private KategorieDAO kategorieDAO;
	private UnterkategorieDAO unterkategorieDAO;
	private PersonDAO personDAO;
	private WarenkorbArtikelDAO warenkorbArtikelDAO;
	private WarenkorbDAO warenkorbDAO;
	
	

	

	private DBManager() {
		adminDAO  			= new AdminDAO(this);
		personDAO 			= new PersonDAO(this);
		accountDAO 			= new AccountDAO(this);
		artikelDAO 			= new ArtikelDAO(this);
		artikelKategorieDAO	= new ArtikelKategorieDAO(this);
		bestellteArtikelDAO 	= new BestellteArtikelDAO(this);
		herstellerDAO 		= new HerstellerDAO(this);
		kategorieDAO			= new KategorieDAO(this);
		warenkorbArtikelDAO	= new WarenkorbArtikelDAO(this);
		warenkorbDAO			= new WarenkorbDAO(this);
		unterkategorieDAO	= new UnterkategorieDAO(this);

	}

	private static void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String host = "jdbc:mysql://localhost:3306/imse_jupiter";
			String user = "root";
			String password = "";
			connection = DriverManager.getConnection(host, user, password);
			
			System.out.println("DB SUCCESS");
		} catch (Exception e) {
			System.out.println("DATABASE ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * return a instance of this class
	 * 
	 * @return instance
	 */
	public static DBManager getInstance() {
		return instance;
	}

	/**
	 * return a database connection
	 * if no database connection exist then open new connection
	 * 
	 * @throws DBException
	 *             bei fehler bei verbindungsaufbau
	 * 
	 * @return connection
	 */
	protected Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				openConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	
	/**
	 * @return the adminDAO
	 */
	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	/**
	 * @return the accountDAO
	 */
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	/**
	 * @return the artikelDAO
	 */
	public ArtikelDAO getArtikelDAO() {
		return artikelDAO;
	}

	/**
	 * @return the artikelKategorieDAO
	 */
	public ArtikelKategorieDAO getArtikelKategorieDAO() {
		return artikelKategorieDAO;
	}

	/**
	 * @return the bestellteArtikelDAO
	 */
	public BestellteArtikelDAO getBestellteArtikelDAO() {
		return bestellteArtikelDAO;
	}

	/**
	 * @return the herstellerDAO
	 */
	public HerstellerDAO getHerstellerDAO() {
		return herstellerDAO;
	}

	/**
	 * @return the kategorieDAO
	 */
	public KategorieDAO getKategorieDAO() {
		return kategorieDAO;
	}
	
	/**
	 * @return the unterkategorieDAO
	 */
	public UnterkategorieDAO getUnterkategorieDAO() {
		return unterkategorieDAO;
	}

	/**
	 * @return the warenkorbArtikelDAO
	 */
	public WarenkorbArtikelDAO getWarenkorbArtikelDAO() {
		return warenkorbArtikelDAO;
	}

	/**
	 * @return the warenkorbDAO
	 */
	public WarenkorbDAO getWarenkorbDAO() {
		return warenkorbDAO;
	}


}
