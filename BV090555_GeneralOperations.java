package student;

import java.sql.SQLException;

import operations.GeneralOperations;

public class BV090555_GeneralOperations implements GeneralOperations{

	@Override
	public void eraseAll() {
		try {
			Baza.st.executeUpdate(""
					+ "delete Ponuda "
					+ "delete zahtev "
					+ "delete kurir "
					+ "delete korisnik "
					+ "delete opstina "
					+ "delete grad "
					+ "delete Vozilo "
					+ "delete Voznja "
					+ "delete kurirrz ");
		
		} catch (SQLException e) {
			
			e.printStackTrace();
				} 
		
		}
	
	
	

}
