package student;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import operations.CourierOperations;

public class BV090555_CourierOperations implements CourierOperations {
	
	
	
	@Override
	public boolean deleteCourier(String arg0) {
		ResultSet rs;
	
		
		try {
		
			rs = Baza.st.executeQuery("Select ko.IDKorisnik from korisnik ko, kurir k "
					+ "where ko.username= '" + arg0+"' and k.idkorisnik=ko.idkorisnik");
			if (rs.next()!=true) return false;
			else
			{
			

			
			int id=rs.getInt(1);

			Baza.st.executeUpdate("delete from kurir where IDkorisnik="+id);
			return true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public List<String> getAllCouriers() {
		List<String> lista = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select ko.Username from kurir ku, korisnik ko "
					+ "where ku.Idkorisnik=ko.idkorisnik order by ku.OstvarenProfit desc");
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return lista;
	
	}

	@Override
	public BigDecimal getAverageCourierProfit(int arg0) {
		ResultSet rs;
		BigDecimal bd=BigDecimal.ZERO;
		
		
		try {
			rs = Baza.st.executeQuery("Select avg(OstvarenProfit) from kurir where BrIspPaketa>="+arg0);
			if (rs.next()==true) bd=rs.getBigDecimal(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	}
		if (bd==null) bd=BigDecimal.ZERO;
		return bd;
	}

	@Override
	public List<String> getCouriersWithStatus(int arg0) {
		List<String> lista = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("Select ko.IDKorisnik,ko.Username,ko.ime,ko.Prezime,ku.BrIspPaketa,ku.OstvarenProfit from kurir ku, korisnik ko "
					+ "where ku.Idkorisnik=ko.idkorisnik and status="+arg0+" order by ku.OstvarenProfit desc");
			while (rs.next()) {
				lista.add(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" ");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return lista;
	}

	@Override
	public boolean insertCourier(String arg0, String arg1) {
		// TODO Auto-generated method stub
		ResultSet rs,rs2;
		int idk,idv;
		try {
			Statement st2 = Baza.conn.createStatement();
			
			rs = Baza.st.executeQuery("Select k.idkorisnik,v.idvozilo from korisnik k, vozilo v "
					+ "where k.username='" + arg0+"' and v.Regbr='"+arg1+"'");
			if (rs.next()!=true) return false;
			else{
		
				
			idk=rs.getInt(1);
			idv=rs.getInt(2);
			
			rs2=st2.executeQuery("select * from kurir where idkorisnik="+idk+" or idvozilo="+idv);
			if (rs2.next()==true) return false; else
			{
			
				
				
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Kurir (idkorisnik, idvozilo,BrIspPaketa,ostvarenprofit,status) values (?,?,?,?,?)");
			ps1.setInt(1, idk);
			ps1.setInt(2, idv);
			ps1.setInt(3, 0);
			ps1.setInt(4, 0);
			ps1.setInt(5, 0);
			ps1.executeUpdate();
			ps1.close();			
			return true;
			}
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;	
		}		
	}

	
	
	}


