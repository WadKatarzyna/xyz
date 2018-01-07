package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Artikel;
import model.BestellteArtikel;

public class BestellteArtikelDAO implements DAO<BestellteArtikel> {

	DBManager db = DBManager.getInstance();
	
	public BestellteArtikelDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(BestellteArtikel b) {
		 int id = -1;
		try {
			String sql = ("'"+b.getBestellungId()+"', '"+b.getArtikelId()+"', '"+b.getMenge()+"', '"+b.getPreis()+"'");
			
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO bestellteArtikel VALUES("+ sql +");");

		    pstmt.executeUpdate();
		    
			pstmt = (PreparedStatement) db.getConnection().prepareStatement("SELECT MAX(bestellteArtikel_bestellung_id) FROM bestellteArtikel") ;
		    ResultSet rs = pstmt.executeQuery();
		   
		   
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    
		    rs.close();
		    pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	    

	    return id;
	}

	@Override
	public BestellteArtikel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BestellteArtikel> findAll() {
	List<BestellteArtikel> output = new ArrayList<>();
		
		try {
			
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from bestellteArtikel order by bestellteArtikel_bestellung_id");
			
			
			while(result.next()) {
				
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void update(BestellteArtikel object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(BestellteArtikel object) {
		// TODO Auto-generated method stub
		
	}
	
	
	private BestellteArtikel parse(ResultSet result) throws SQLException {
		BestellteArtikel ba = new BestellteArtikel();
		ba.setBestellungId(result.getInt("bestellteArtikel_bestellung_id"));
		ba.setArtikelId(result.getInt("bestellteArtikel_artikel_id"));
		ba.setMenge(result.getInt("bestellteArtikel_menge"));
		ba.setPreis(result.getDouble("bestellteArtikel_preis"));
		
		return ba;
		
	}

}
