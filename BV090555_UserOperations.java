package student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.*;

import operations.UserOperations;

public class BV090555_UserOperations implements UserOperations {

	@Override
	public int declareAdmin(String arg0) {
		ResultSet rs;
		int n=2;
		try {
		
		rs = Baza.st.executeQuery("select idkorisnik,isadmin from korisnik where username= '" + arg0+"'");
				if (rs.next()==true) {		
				int id=	rs.getInt(1);
				int is=	rs.getInt(2);
				
				if (is==1) n=1;
				else
				if (is==0){	
				
				PreparedStatement ps1 = Baza.conn.prepareStatement("update korisnik set isadmin=? where idkorisnik=?  ");
				
				ps1.setInt(1, 1);
				ps1.setInt(2, id);
				ps1.executeUpdate();
				ps1.close();	
					
				return n=0;
				}
				
				
				
				}
				else return n=2;
			
				
		
				
			} catch (SQLException e) {
				e.printStackTrace();
				return 2;
			}
		return n;
	}

	@Override
	public int deleteUsers(String... arg0) {
		ResultSet rs;
		


		
		try {
		int id,n=0;
		for (int i = 0; i < arg0.length; i++){
			rs = Baza.st.executeQuery("select idkorisnik from korisnik where username= '" + arg0[i]+"'");
			if (rs.next()==true) {		id=	rs.getInt(1);			
						
			Baza.st.executeUpdate("delete from korisnik where IDkorisnik="+id);
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
	public List<String> getAllUsers() {
		List<String> lista = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = Baza.st.executeQuery("select idkorisnik,username from korisnik");
			while (rs.next()) {
				lista.add(rs.getString(2));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
		}
		return lista;
	}
	

	@Override
	public Integer getSentPackages(String... arg0) {
		ResultSet rs;

		try {
			Integer n=null; 
			int nn=0,nnn=0;
			for (int i = 0; i < arg0.length; i++){
				rs = Baza.st.executeQuery("select brposlpak from korisnik where username= '" + arg0[i]+"'");
				if (rs.next()==true) {
				nnn=5;
				nn+=	rs.getInt(1);			
				} 
			
				
			}
			
			if (nn>=0 && nnn==5) n=nn;
			return n;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		
	}

	@Override
	public boolean insertUser(String arg0, String arg1, String arg2, String arg3) {
		ResultSet rs;
		try {

			rs = Baza.st.executeQuery("Select * from korisnik where username='" + arg0+"'");
				if (rs.next()==true) return false; 
			
				if (Character.isUpperCase(arg1.charAt(0))!=true) return false;
			
				if (Character.isUpperCase(arg2.charAt(0))!=true) return false;
		
				if (  arg3.length()<8) return false;
				 
				Pattern pattern = Pattern.compile("^(?=.*[0-9])^(?=.*[a-zA-Z])");
				Matcher matcher = pattern.matcher(arg3);
				
				
				if (!matcher.lookingAt()) return false;
		
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Korisnik (username,ime,prezime,pass,brposlpak,isadmin) values (?,?,?,?,?,?)");
			ps1.setString(1, arg0);
			ps1.setString(2, arg1);
			ps1.setString(3, arg1);
			ps1.setString(4, arg1);
			ps1.setInt(5, 0);
			ps1.setInt(6, 0);
			ps1.executeUpdate();
			ps1.close();			
		
		
			return true;
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	

}
