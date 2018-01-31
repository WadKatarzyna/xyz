package daoSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Artikel;
import model.Warenkorb;

public class WarenkorbDAO implements DAO<Warenkorb> {

	private DBManager db;
	
	public WarenkorbDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(Warenkorb w) {
		try {

			
			String sql = (null+", '"+w.getErstelldatum()+"', '"+w.getAccountId()+"'");
			

			
			
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO warenkorb VALUES("+ sql +");");
		    pstmt.executeUpdate();

		    pstmt.close();

		    pstmt = (PreparedStatement) db.getConnection().prepareStatement("SELECT MAX(warenkorb_id) FROM warenkorb") ;
		    ResultSet rs = pstmt.executeQuery();
		    
		    int id = -1;
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    return id;
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Warenkorb findById(int id) {
		
		return null;
	}
	
	public int findWarenkorbByCustomerId(int id) {
		int warenkorbId = 0;
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select warenkorb_id from warenkorb where warenkorb_account_id="+id);
			if(result.next()) {
				warenkorbId = result.getInt("warenkorb_id");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return warenkorbId;
	}

	@Override
	public List<Warenkorb> findAll() {
		List<Warenkorb> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from warenkorb order by warenkorb_id");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
		
	}

	@Override
	public void update(Warenkorb object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Warenkorb object) {
		// TODO Auto-generated method stub
		
	}
	

	private Warenkorb parse(ResultSet result) throws SQLException {
		Warenkorb w = new Warenkorb();
		w.setId(result.getInt("warenkorb_id"));
		w.setErstelldatum(result.getString("warenkorb_erstelldatum"));
		w.setAccountId(result.getInt("warenkorb_account_id"));
		
		
		return w;
		
	}

}
