package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Account;
import model.Person;

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
		List<Account> output = new ArrayList<>();
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("select * from account order by account_id");

			while (result.next())
				output.add(parse(result));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void update(Account object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Account object) {
		// TODO Auto-generated method stub
		
	}
	
	private Account parse(ResultSet result) throws SQLException {
		Account account = new Account();
		account.setId(result.getInt("account_id"));
		account.setUsername(result.getString("account_username"));
		account.setErstelldatum(result.getString("account_erstelldatum"));
		account.setPasswort(result.getString("account_passwort"));
		account.setPerson_personalnr(result.getInt("account_person_personalnr"));
		
		return account;
		
	}

}
