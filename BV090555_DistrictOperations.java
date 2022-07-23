package student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





import operations.DistrictOperations;

public class BV090555_DistrictOperations implements DistrictOperations {
	
	public void pisi(){
			
			try {
				ResultSet rs =Baza.st.executeQuery("select * from opstina");
				
				while (rs.next()) {
					System.out.println(rs.getInt("IDOpstina")+ ", " + rs.getInt("IDGrad") + ", " + rs.getString("Naziv")+ ", " + rs.getInt("X")+ ", " + rs.getInt("Y"));
				}
				
					
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	@Override
	public int deleteAllDistrictsFromCity(String arg0) {
		int id,n=0;
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("select g.idGrad from Opstina o,grad g where g.naziv= '" + arg0+"'");
			if (rs.next()==true) {		id=	rs.getInt(1);	
			
			rs = Baza.st.executeQuery("select count(*) from Opstina where idgrad= "+id);		
			rs.next();
			n=rs.getInt(1);
			
			Baza.st.executeUpdate("delete from opstina where IDGrad="+id);
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return n;
	}

	@Override
	public boolean deleteDistrict(int arg0) {
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("select * from Opstina o where o.idopstina= " + arg0);
			if (rs.next()!=true) return false; else {	
			Baza.st.executeUpdate("delete from opstina where IDOpstina="+arg0);
			return true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		} 
		
	}

	@Override
	public int deleteDistricts(String... arg0) { 
		
		
		ResultSet rs;
		
		try {
		int id,n=0;
		
	
		
		
		for (int i = 0; i < arg0.length; i++){
			rs = Baza.st.executeQuery("select idopstina from Opstina where naziv= '" + arg0[i]+"'");
			if (rs.next()==true) {		id=	rs.getInt(1);			
			
		
				
			Baza.st.executeUpdate("delete from opstina where IDOpstina="+id);
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
	public List<Integer> getAllDistricts() {
		List<Integer> lista = new ArrayList<Integer>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select * from Opstina");
			while (rs.next()) {
				lista.add(rs.getInt("IDOpstina"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return lista;
	
	}

	@Override
	public List<Integer> getAllDistrictsFromCity(int arg0) {
		List<Integer> lista = new ArrayList<Integer>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select * from Opstina where IDGrad="+arg0);
			if (rs.next()==false) return null; else lista.add(rs.getInt("IDOpstina"));
				
				
			while (rs.next()) {
				lista.add(rs.getInt("IDOpstina"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return lista;
	
	}

	@Override
	public int insertDistrict(String arg0, int arg1, int arg2, int arg3) {
		ResultSet rs;
		try {
			
			rs = Baza.st.executeQuery("Select * from grad where Idgrad="+arg1 );
			if (rs.next()!=true) return -1;
			
			rs = Baza.st.executeQuery("Select * from Opstina where Naziv='"+arg0+"' and Idgrad="+arg1+" and X="+arg2+" and Y="+arg3 );
			if (rs.next()==true) return -1;
			else{
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Opstina (Naziv,IDGrad,X,Y) values (?,?,?,?)");
			ps1.setString(1, arg0);
			ps1.setInt(2, arg1);
			ps1.setInt(3, arg2);
			ps1.setInt(4, arg3);
			ps1.executeUpdate();
			ps1.close();			
		
			rs = Baza.st.executeQuery("select Idopstina from Opstina where Naziv='"+arg0+"' and Idgrad="+arg1+" and X="+arg2+" and Y="+arg3);
			rs.next();
			int r = rs.getInt("IDOpstina");
			return r;
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return -1;	
		}		
	}

}
