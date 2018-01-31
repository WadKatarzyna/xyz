package unitTest.SQL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class LieferdatumTest {

	@Test
	public void testLieferdatum() {
		Date today = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		int day = Calendar.DAY_OF_MONTH + 5;
		cal.set(Calendar.DAY_OF_MONTH, day );
		
		Date d = cal.getTime();
		String date = df.format(d);
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 5);
		dt = c.getTime();
		
		System.out.println(dt);
	}
}
