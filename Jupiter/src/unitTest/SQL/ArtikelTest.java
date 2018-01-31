package unitTest.SQL;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import daoSQL.DBManager;
import model.Artikel;

public class ArtikelTest {

	private DBManager db = DBManager.getInstance();
	
	@Test
	public void testFindArtikelById() {
		int id = 1;
		Artikel artikel = db.getArtikelDAO().findById(id);
		assertEquals("Hemd MDK Blau", artikel.getBeschreibung());
	}
	
	@Test
	public void testSortArtikelWithKeyword() {
		String keyWord = "Hemd MDK Blau";
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = db.getArtikelDAO().sortWithKeyword(keyWord);
		Artikel artikel = artikelList.get(0);
		assertEquals(1, artikel.getId());
	}
	
	@Test
	public void testFindAllArtikel() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = db.getArtikelDAO().findAll();
		assertEquals(100, artikelList.size());
	}
	
	@Test 
	public void testSortArtikelWithCategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = db.getArtikelDAO().sortWithCategory(1); //Suche nach Kategorie == Damen (alle Damenartikel)
		assertEquals(47, artikelList.size());
	}
	
	@Test 
	public void testSortArtikelWithUndercategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = db.getArtikelDAO().sortWithUndercategory(1); // Suche nach Unterkategorie == Oberteile (alle Oberteile)
		assertEquals(19, artikelList.size());
	}
	
	@Test 
	public void testSortArtikelWithBothCategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = db.getArtikelDAO().sortWithBothCcategory(1, 1); // Search for Damenoberteile (alle Damenoberteile)
		assertEquals(4, artikelList.size());
	}
}
