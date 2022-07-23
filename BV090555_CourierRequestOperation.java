package student;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import operations.CourierRequestOperation;

public class BV090555_CourierRequestOperation implements
		CourierRequestOperation {

	@Override
	public boolean changeVehicleInCourierRequest(String arg0, String arg1) {
		ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select idkorisnik from korisnik where username= '" + arg0+"'");
			if (rs.next()==false) return false;
			int id=rs.getInt(1);
			
			rs = Baza.st.executeQuery("Select * from kurirrz where idkorisnik= " + id);
			if (rs.next()!=true) return false;
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("update kurirrz set regbr=? where idkorisnik=?  ");
			
			ps1.setString(1, arg1);
			ps1.setInt(2, id);
			ps1.executeUpdate();
			ps1.close();	
			return true;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

		


	@Override
	public boolean deleteCourierRequest(String arg0) {
		ResultSet rs;
	
		try {
		
			rs = Baza.st.executeQuery("Select idkorisnik from korisnik where username= '" + arg0+"'");
			if (rs.next()==false) return false;
			int id=rs.getInt(1);
			
			rs = Baza.st.executeQuery("Select * from kurirrz where idkorisnik= " + id);
			if (rs.next()!=true) return false;
			
			
			Baza.st.executeUpdate("delete from kurirrz where IDKorisnik="+id);
			return true;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<String> getAllCourierRequests() {
	List<String> lista = new ArrayList<String>();
			ResultSet rs;
			try {
				rs = Baza.st.executeQuery("Select * from kurirrz ");
				while (rs.next()) {
					lista.add(rs.getInt("IDKorisnik")+" "+rs.getString("RegBr"));
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			return lista;
		
		}
	

	@Override
	public boolean insertCourierRequest(String arg0, String arg1) {
		ResultSet rs;
		try {

			rs = Baza.st.executeQuery("Select ko.idkorisnik from korisnik ko where "
															+ " ko.username='" + arg0+"'");
			if (rs.next()!=true) return false;
			int id=rs.getInt(1);
			
			rs = Baza.st.executeQuery("Select * from kurir  where "
						+ "idkorisnik="+id);
			if (rs.next()==true) return false;  
			
			rs = Baza.st.executeQuery("Select * from kurirrz  where "
					+ "idkorisnik="+id);
		if (rs.next()==true) return false;  

			rs = Baza.st.executeQuery("Select * from vozilo where "
							+ "regbr='"+arg1+"'");
			if (rs.next()!=true) return false;
			
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into kurirrz (idkorisnik, regbr) values (?,?)");
			ps1.setInt(1, id);
			ps1.setString(2, arg1);
			ps1.executeUpdate();
			ps1.close();			
		
			return true;
					
				
			
			} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;	
		}		
	}




	@Override
	public boolean grantRequest(String arg0) {
		
		
			//ResultSet rs;
		
			try {/*
				//"{?=call fnovi_kurir(?,?)}"
				rs = Baza.st.executeQuery("Select idkorisnik from korisnik where username= '" + arg0+"'");
				if (rs.next()==false) return false;
				int id=rs.getInt(1);
				rs = Baza.st.executeQuery("Select * from kurirrz where idkorisnik= " + id);
				if (rs.next()!=true) return false;
				String reg=rs.getString(2);
				
				rs = Baza.st.executeQuery("Select idvozilo from vozilo where regbr= '" + reg+"'");
				if (rs.next()!=true) return false;
				int idv=rs.getInt(1);
				
				rs = Baza.st.executeQuery("Select * from kurir where idkorisnik= " + id+" or idvozilo='"+idv+"'");
				if (rs.next()==true) return false;
				
				PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Kurir (idkorisnik, idvozilo,BrIspPaketa,ostvarenprofit,status) values (?,?,?,?,?)");
				ps1.setInt(1, id);
				ps1.setInt(2, idv);
				ps1.setInt(3, 0);
				ps1.setInt(4, 0);
				ps1.setInt(5, 0);
				ps1.executeUpdate();
				ps1.close();			
				
				
				
				
				
				Baza.st.executeUpdate("delete from kurirrz where IDKorisnik="+id);
				return true;
				*/
				/*
				String SPsql = "EXEC novi_kurir ?";   // for stored proc taking 2 parameters
				
				PreparedStatement ps = Baza.conn.prepareStatement(SPsql);
			    ps.setEscapeProcessing(true);
			    ps.setQueryTimeout(90);
				
				
				ps.setString(1,arg0);
				ps.execute();
				*/
				CallableStatement cstmt = Baza.conn.prepareCall("{call novi_kurir(?,?)}");  
			      cstmt.setString(1,arg0 );  
			      cstmt.registerOutParameter("ou", java.sql.Types.INTEGER);  
			      cstmt.execute();  
			      int ou=cstmt.getInt("ou");  
			      cstmt.close();  
				
				
				if (ou==-1) return false;
				else return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		
	}
	
	}


