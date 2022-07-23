package student;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import operations.VehicleOperations;

public class BV090555_VehicleOperations implements VehicleOperations {

	@Override
	public boolean changeConsumption(String arg0, BigDecimal arg1) {
		ResultSet rs;
		boolean b=false;
		try {
				
				rs = Baza.st.executeQuery("select * from vozilo where regbr= '" + arg0+"'");
				if (rs.next()==true)
				{
							
				PreparedStatement ps1 = Baza.conn.prepareStatement("update vozilo set potrosnja=? where regbr=?  ");
				
				ps1.setBigDecimal(1,arg1.setScale(3,BigDecimal.ROUND_HALF_UP));
				ps1.setString(2, arg0);
				
				
				ps1.executeUpdate();
				ps1.close();	
				b= true;
				}
			
				return b;	
			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changeFuelType(String arg0, int arg1) {
		ResultSet rs;
		boolean b=false;
		try {
				
				rs = Baza.st.executeQuery("select tipGoriva from vozilo where regbr= '" + arg0+"'");
				if (rs.next()==true)
				{
				if (arg1>2 || arg1<0) return false;		
				
				
				PreparedStatement ps1 = Baza.conn.prepareStatement("update vozilo set tipgoriva=? where regbr=?  ");
				
				ps1.setInt(1,arg1);
				ps1.setString(2, arg0);
				
				
				ps1.executeUpdate();
				ps1.close();	
				b= true;
				}
			
				return b;	
			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}


	public boolean deleteVehicle(String... arg0) {
		ResultSet rs;
		boolean b=false;
		try {
		
			
			for (int i = 0; i < arg0.length; i++){
				rs = Baza.st.executeQuery("select idvozilo from vozilo where regbr= '" + arg0[i]+"'");
				if (rs.next()==true)
				{
				int id=	rs.getInt(1);			
				Baza.st.executeUpdate("delete from vozilo where IDvozilo="+id);
				b= true;
				}
			
			}
			
		return b;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<String> getAllVehichles() {
		List<String> lista = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select * from vozilo");
			while (rs.next()) {
				lista.add(rs.getString(2));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return lista;
	}

	@Override
	public boolean insertVehicle(String arg0, int arg1, BigDecimal arg2) {
		ResultSet rs;
		try {

			rs = Baza.st.executeQuery("Select * from vozilo where regbr='" + arg0+"'");
				if (rs.next()==true) return false; 

			else{
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Vozilo (regbr,tipGoriva,potrosnja) values (?,?,?)");
			ps1.setString(1, arg0);
			ps1.setInt(2, arg1);
			ps1.setBigDecimal(3,arg2.setScale(10,BigDecimal.ROUND_HALF_UP));
			
			ps1.executeUpdate();
			ps1.close();			
		
		
			return true;
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public int deleteVehicles(String... arg0) {
		ResultSet rs;
		
		int id=-1,n=0;
		try {
		
			
			for (int i = 0; i < arg0.length; i++){
				rs = Baza.st.executeQuery("select idvozilo from vozilo where regbr= '" + arg0[i]+"'");
				if (rs.next()==true)
				{
				id=	rs.getInt(1);			
				Baza.st.executeUpdate("delete from vozilo where IDvozilo="+id);
				n++;
				}
			
			}
			
		return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

}
