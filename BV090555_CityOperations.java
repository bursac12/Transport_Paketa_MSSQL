package student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;






import operations.*;

public class BV090555_CityOperations implements CityOperations {

	public void pisi(){
		
		try {
			ResultSet rs =Baza.st.executeQuery("select * from grad");
			
			while (rs.next()) {
				System.out.println(rs.getString("IDGrad") + ", " + rs.getString("Naziv")+ ", " + rs.getString("PostanskiBr"));
			}
			
				
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int deleteCity(String... arg0) {
		
		ResultSet rs;

		
		try {
		int id=-1,n=0;
		for (int i = 0; i < arg0.length; i++){
			rs = Baza.st.executeQuery("select idgrad from Grad where naziv= '" + arg0[i]+"'");
			if (rs.next()==true) {		id=	rs.getInt(1);			
			
		
			
			Baza.st.executeUpdate("delete from grad where IDGrad="+id);
			n++;
			}
			
		}
		
		return n;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	
	}

	@Override
	public boolean deleteCity(int arg0) {
		ResultSet rs;
	
		
		try {
		
			rs = Baza.st.executeQuery("Select * from Grad where IDGrad= " + arg0);
			if (rs.next()==false) return false;
			

			Baza.st.executeUpdate("delete from grad where IDGrad="+arg0);
			return true;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Integer> getAllCities() {
		List<Integer> lista = new ArrayList<Integer>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select idgrad from grad");
			while (rs.next()) {
				lista.add(rs.getInt("IDGrad"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return lista;
	}

	@Override
	public int insertCity(String arg0, String arg1) {
		ResultSet rs;
		try {

			rs = Baza.st.executeQuery("Select * from Grad where PostanskiBr=" + arg1+" or naziv ='"+arg0+"'");
			if (rs.next()==true) return -1;
			else{
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Grad (Naziv, PostanskiBr) values (?,?)");
			ps1.setString(1, arg0);
			ps1.setString(2, arg1);
			ps1.executeUpdate();
			ps1.close();			
		
			rs = Baza.st.executeQuery("select IDGrad from Grad where PostanskiBr="+arg1);
			rs.next();
			int r = rs.getInt("IDGrad");
			return r;
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return -1;	
		}		
	}
	

}
