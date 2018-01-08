package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Bestellung;

public class BestellungDAO implements DAO<Bestellung> {

	
	private DBManager db;
	
	protected BestellungDAO(DBManager db) {
		this.db = db;
	}
	
	
	@Override
	public int create(Bestellung b) {
		
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			
			int day = Calendar.DAY_OF_MONTH + 5;
			cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 5 );
			
			Date d = cal.getTime();
			String date = df.format(d);
			
			String sql = (null+", "+null+", '"+date+"', '"+b.getSumme()+"', '"+b.getAccountId()+"'");
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO bestellung VALUES("+ sql +");");
		    pstmt.executeUpdate();
		    
		    
		    
			pstmt = (PreparedStatement) db.getConnection().prepareStatement("SELECT MAX(bestellung_id) FROM bestellung") ;
		    ResultSet rs = pstmt.executeQuery();
		    
		    int id = -1;
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    
		    rs.close();
		    pstmt.close();

		    return id;


		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Bestellung findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bestellung> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Bestellung object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Bestellung object) {
		// TODO Auto-generated method stub
		
	}

}
