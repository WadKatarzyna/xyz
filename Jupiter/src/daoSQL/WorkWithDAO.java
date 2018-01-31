package daoSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Katarzyna Wadowska
 * 
 * Es wird festgelegt mit welcher Datenbank man arbeiten will.. SQL oder NoSQL
 *
 */
public class WorkWithDAO {

	private DBManager db;
	
	protected WorkWithDAO(DBManager db) {
		this.db = db;
	}
	
	
	/**
	 * 
	 * @param i
	 *  1 = SQL
	 *  0 = NoSQL
	 */
	public void setWorkwith(int i) {
		try {
			db.getConnection().createStatement().executeUpdate("UPDATE work_with SET SQL_="+ i +" WHERE id=1;");

		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * liefert 1 oder 0
	 * 1 = arbeite mit SQL DB
	 * 0 = arbeite mit NOSQL DB
	 */
	public int getWorkwith() {
		//default 1 =(SQL)
		int value = 1;
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("SELECT SQL_ FROM work_with WHERE id=1");

			if (result.next())
				return value = result.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
}
