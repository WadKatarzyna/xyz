package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ArtikelKategorie;

public class ArtikelKategorieDAO implements DAO<ArtikelKategorie> {

	
	DBManager db = DBManager.getInstance();
	
	@Override
	public int create(ArtikelKategorie object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArtikelKategorie findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArtikelKategorie> findAll() {
		List<ArtikelKategorie> output = new ArrayList<>();
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("select * from artikelkategorie order by artikelkategorie_artikel_id");

			while (result.next())
				output.add(parse(result));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void update(ArtikelKategorie object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ArtikelKategorie object) {
		// TODO Auto-generated method stub
		
	}
	
	private ArtikelKategorie parse(ResultSet result) throws SQLException {
		ArtikelKategorie ak = new ArtikelKategorie();
		
		ak.setArtikel_id(result.getInt("artikelkategorie_artikel_id"));
		ak.setKategorie_id(result.getInt("artikelkategorie_kategorie_id"));
		
		return ak;
		
	}

}
