package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import model.WarenkorbArtikel;

public class WarenkorbArtikelDAO implements DAO<WarenkorbArtikel> {

	DBManager db;
	
	public WarenkorbArtikelDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(WarenkorbArtikel w) {
		
		int id = -1;
		try {
			String sql = ("'"+w.getWarenkorbid()+"', '"+w.getArtikelid()+"', '"+w.getMenge()+"', '"+w.getSumme()+"'");
			
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO warenkorbArtikel VALUES("+ sql +");");

		    pstmt.executeUpdate();
		    
			pstmt = (PreparedStatement) db.getConnection().prepareStatement("SELECT MAX(warenkorbArtikel_id) FROM warenkorbArtikel") ;
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
	public WarenkorbArtikel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WarenkorbArtikel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(WarenkorbArtikel o) {
		if ( o == null)
			throw new IllegalArgumentException("object missing");
		
		int menge = 0;
		
		double summe = 0;
		 
		
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select warenkorbArtikel_menge from warenkorbArtikel where warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
			if(result.next()) {
				menge = result.getInt("warenkorbArtikel_menge");
				menge = menge+1;

			}
		
		
		
			ResultSet res = db.getConnection().createStatement().executeQuery("select warenkorbArtikel_summe from warenkorbArtikel where warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
			if(res.next()) {
				summe = res.getDouble("warenkorbArtikel_summe");
				summe = summe+o.getSumme();

			}
			
			
			String sql = ("warenkorbArtikel_menge='"+menge+"'");
			
			String sqlSumme = ("warenkorbArtikel_summe='"+summe+"'");
			
			
			db.getConnection().createStatement().executeUpdate("UPDATE warenkorbArtikel SET "+sql+" WHERE warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
			
			db.getConnection().createStatement().executeUpdate("UPDATE warenkorbArtikel SET "+sqlSumme+" WHERE warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
			
			
		} catch (MySQLIntegrityConstraintViolationException  e) {
			throw new IllegalArgumentException("Product konnte nicht verändert werden");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	
	}
		
	

	@Override
	public void delete(WarenkorbArtikel object) {
		// TODO Auto-generated method stub
		
	}
	
	public List<WarenkorbArtikel> findAllCartItemsByAccountId(int accountId){
		int warenkorbId = 0;
		List<WarenkorbArtikel> warenkorbArtikel = new ArrayList<>();
		
		
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select warenkorb_id from warenkorb where warenkorb_account_id="+accountId);
			if(result.next()) {
				warenkorbId = result.getInt("warenkorb_id");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from warenkorbArtikel where warenkorbArtikel_id="+warenkorbId);
			
			while(result.next()) {
				warenkorbArtikel.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return warenkorbArtikel;
		
	}
	
	public void deleteByarticleId(int artikelId) {
		
		
		try {
			db.getConnection().createStatement().executeUpdate("DELETE FROM warenkorbArtikel WHERE warenkorbArtikel_artikel_id="+ artikelId +";");
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Product not exist");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void updateDecrease(WarenkorbArtikel o) {
		if ( o == null)
			throw new IllegalArgumentException("object missing");
		
		
		int menge = 0;
		

		
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select warenkorbArtikel_menge from warenkorbArtikel where warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
			if(result.next()) {
				menge = result.getInt("warenkorbArtikel_menge");
				menge = menge-1;

			}
			//delete from warenkorb if menge = 0
			if(menge == 0) {
				deleteByarticleId(o.getArtikelid());
			}
			
			
			String sql = ("warenkorbArtikel_menge='"+menge+"'");
			
			
			db.getConnection().createStatement().executeUpdate("UPDATE warenkorbArtikel SET "+sql+" WHERE warenkorbArtikel_artikel_id="+o.getArtikelid()+
					" AND warenkorbArtikel_id="+o.getWarenkorbid());
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Product konnte nicht verändert werden");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private WarenkorbArtikel parse(ResultSet result) throws SQLException {
		WarenkorbArtikel w = new WarenkorbArtikel();
		w.setWarenkorbid(result.getInt("warenkorbArtikel_id"));
		w.setArtikelid(result.getInt("warenkorbArtikel_artikel_id"));
		w.setSumme(result.getDouble("warenkorbArtikel_summe"));
		w.setMenge(result.getInt("warenkorbArtikel_menge"));
		return w;
		
	}

}
