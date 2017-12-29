package dao;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Account;

public class AccountDAO implements DAO<Account> {

	
	private DBManager db;
	
	protected AccountDAO(DBManager db) {
		this.db = db;
	}
	
	
	@Override
	public int create(Account a) {
		
		try {
		
			String sql = (null+", '"+a.getUsername()+"', "+null+", '"+a.getPasswort()+"', '"+a.getPerson_personalnr()+"'");
			
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO account VALUES("+ sql +");");

		    pstmt.executeUpdate();

		    pstmt.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Account findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Account object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Account object) {
		// TODO Auto-generated method stub
		
	}

}
