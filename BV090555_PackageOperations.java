package student;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import operations.PackageOperations;

public class BV090555_PackageOperations implements PackageOperations {


	 private double rastojanje(int x1, int y1, int x2, int y2) {
		    double rez,kv;
		    kv=(x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		    rez=Math.sqrt(kv);
		    return rez;
		  }
		  
	 private BigDecimal cena(BigDecimal tezina, int tip, double rastojanje, BigDecimal proc) {
		    double procenat = proc.doubleValue()/100;
		    switch (tip) {
		    case 0: 
		      return new BigDecimal((10*rastojanje)*(procenat+1));
		    case 1: 
		      return new BigDecimal((25+tezina.doubleValue()*1*100)*rastojanje*(procenat+1));
		    case 2: 
		      return new BigDecimal((75+tezina.doubleValue()*2*300)*rastojanje*(procenat+1));
		    }
		    return null;
		  }
	



	@Override
	public boolean changeType(int arg0, int arg1) {
ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select idzahtev from zahtev where idzahtev= " + arg0);
			if (rs.next()==false) return false;
			int id=rs.getInt(1);
			
			if (arg1<0 || arg1>2  ) return false;
		
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("update zahtev set Tip=? where idzahtev=?  ");
			
			ps1.setInt(1, arg1);
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
	public boolean changeWeight(int arg0, BigDecimal arg1) {
ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select idzahtev from zahtev where idzahtev= " + arg0);
			if (rs.next()==false) return false;
			int id=rs.getInt(1);
		
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("update zahtev set tezina=? where idzahtev=?  ");
			
			ps1.setBigDecimal(1, arg1.setScale(10,BigDecimal.ROUND_HALF_UP));
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
	public boolean deletePackage(int arg0) {
		ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select idzahtev from zahtev where idzahtev= " + arg0);
			if (rs.next()==false) return false;
			int id=rs.getInt(1);
			
			
			
			Baza.st.executeUpdate("delete from zahtev where IDzahtev="+id);
			return true;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int driveNextPackage(String arg0) {
ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select ko.idkorisnik, ku. status from korisnik ko, kurir ku where ko.username='" + arg0+"' and ku.idkorisnik=ko.idkorisnik");
			if (rs.next()==false) return -2;
			int id=rs.getInt(1);
			int sta=rs.getInt(2);
	
			rs = Baza.st.executeQuery("Select * from voznja where gotovo=0 and idkorisnik="+id);
			if (rs.next()==false) return -1;
			int idvo=rs.getInt("idvoznja");
			int pid=rs.getInt("idzahtev");
			
			if (sta==0){ //ako kurir nije vozio podesi status
				PreparedStatement psv = Baza.conn.prepareStatement("update kurir set Status=? where idkorisnik=?  ");
				psv.setInt(1, 1);
				psv.setInt(2, id);
				psv.executeUpdate();
				psv.close();	
						}
	
			
			
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("update zahtev set Status=? where idzahtev=?  ");
			ps1.setInt(1, 3);
			ps1.setInt(2, pid);
			ps1.executeUpdate();
			ps1 = Baza.conn.prepareStatement("update voznja set gotovo=? where idvoznja=?  ");
			ps1.setInt(1, 1);
			ps1.setInt(2, idvo);
			ps1.executeUpdate();
			
			ps1.close();	
			
			rs = Baza.st.executeQuery("Select * from voznja where gotovo=0 and idkorisnik="+id);
			if (rs.next()==false) {//sve sam razvezao
				PreparedStatement psv = Baza.conn.prepareStatement("update kurir set Status=? where idkorisnik=?  ");
				psv.setInt(1, 0);
				psv.setInt(2, id);
				psv.executeUpdate();
				psv.close();
				
				
				rs=Baza.st.executeQuery("select idvozilo from kurir where idkorisnik="+id);
				rs.next();
				int idvz=rs.getInt(1);
				
				
				rs=Baza.st.executeQuery("select potrosnja,tipgoriva from vozilo where Idvozilo="+idvz);
				rs.next();
				double pot=rs.getDouble(1);
				int tipg=rs.getInt(2);
				
				int cenag=0;
				
				switch (tipg) {
			    case 0: 
			      cenag=15;
			    case 1: 
			      cenag=32;
			    case 2: 
			      cenag=36;
			    }
				
				
				
				
				
				double profit=0;
				int brisp=0;
				
				rs = Baza.st.executeQuery("Select z.cena,z.duzina from voznja v, zahtev z where "
						+ "v.idzahtev=z.idzahtev and v.gotovo=1 and v.idkorisnik="+id);
				
				while (rs.next()){
				double cena=rs.getDouble(1);
				double duz=rs.getDouble(2);
				brisp++;
				profit=profit+(cena-(duz*pot*cenag));
				//System.out.println(cenag+"/n");
				}
				
				rs = Baza.st.executeQuery("Select brisppaketa,ostvarenprofit from kurir where "
						+ "idkorisnik="+id);
				rs.next();
				int brp=rs.getInt(1);
				double pro=rs.getDouble(2);
				
				profit+=pro;
				brisp+=brp;
				
				PreparedStatement ps2 = Baza.conn.prepareStatement("update kurir set brisppaketa=?,ostvarenprofit=? where idkorisnik=?  ");
				ps2.setInt(1, brisp);
				ps2.setDouble(2, profit);
				ps2.setInt(3, id);
				ps2.executeUpdate();
				ps2.close();
				
				Baza.st.executeUpdate("delete from voznja where IdKorisnik="+id);
				
			} 
			else	
				
			{//vozi dalje
				int pid2=rs.getInt("idzahtev");
				PreparedStatement ps2 = Baza.conn.prepareStatement("update zahtev set Status=? where idzahtev=?  ");
				ps2.setInt(1, 2);
				ps2.setInt(2, pid2);
				ps2.executeUpdate();
				ps2.close();	
			}
			
			
		
			return pid;
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}

	}
	

	@Override
	public Date getAcceptanceTime(int arg0) {
ResultSet rs;
		
		try {
		
			rs = Baza.st.executeQuery("Select vrprihvatanja,status from zahtev where idzahtev= " + arg0);
			if (rs.next()==false) return null;
			Date vr=rs.getDate(1);
			int stat=rs.getInt(2);
			if (stat>0) return vr;
			else return null;
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<Integer> getAllPackages() {
		ResultSet rs;
		List<Integer> lista = new ArrayList<Integer>();
				
				try {
				
					rs = Baza.st.executeQuery("Select idzahtev from zahtev order by idzahtev");
					if (rs.next()==false) return null;
								
					do {
						lista.add(rs.getInt(1));
					} while(rs.next());
			
					return lista;
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public List<Integer> getAllPackagesWithSpecificType(int arg0) {
		ResultSet rs;
		List<Integer> lista = new ArrayList<Integer>();
				
				try {
				
					rs = Baza.st.executeQuery("Select idzahtev from zahtev where tip="+arg0+" order by idzahtev");
					if (rs.next()==false) return null;
								
					do {
						lista.add(rs.getInt(1));
					} while(rs.next());
			
					return lista;
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}





	@Override
	public BigDecimal getPriceOfDelivery(int arg0) {
		ResultSet rs;
		BigDecimal bd=BigDecimal.ZERO;
		
		
		try {
			rs = Baza.st.executeQuery("Select cena,status from zahtev where idzahtev="+arg0);
			if (rs.next()!=true) return null; 
				
			bd=rs.getBigDecimal(1);
			int stat=rs.getInt(2);
			if (stat==0) return null;
			
			
			
				
		} catch (SQLException e) {

			e.printStackTrace();
			
	}
		return bd;
		
		
	}

	@Override
	public int insertPackage(int arg0, int arg1, String arg2, int arg3,
			BigDecimal arg4) {
		ResultSet rs;
		int pk =-1;
		try {
			if (arg3<0 || arg3>2) return -1;
			rs = Baza.st.executeQuery("Select * from opstina where idOpstina="+arg0);
			if (rs.next()!=true) return -1; 
			rs = Baza.st.executeQuery("Select * from opstina where idOpstina="+arg1);
			if (rs.next()!=true) return -1; 
			rs = Baza.st.executeQuery("Select * from korisnik where username='"+arg2+"'");
			if (rs.next()!=true) return -1; 
			
			
			  String columnNames[] = new String[] { "idzahtev" };
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into Zahtev (OpstinaOd,OpstinaDo,username,tip,tezina,status) values (?,?,?,?,?,?)",columnNames);
			ps1.setInt(1, arg0);
			ps1.setInt(2, arg1);
			ps1.setString(3, arg2);
			ps1.setInt(4, arg3);
			ps1.setBigDecimal(5, arg4.setScale(3,BigDecimal.ROUND_HALF_UP));
			ps1.setInt(6, 0);
			
						
		
			if (ps1.executeUpdate() > 0) {
	           
	           rs = ps1.getGeneratedKeys();
	            if ( rs.next() ) {
	                pk = rs.getInt(1);
	            }
	        }
			ps1.close();
			
			return pk;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return -1;	
		}		
	}

	@Override
	public int insertTransportOffer(String arg0, int arg1, BigDecimal arg2) {
		
		ResultSet rs;
		int pk =-1;
		try {
			
			rs = Baza.st.executeQuery("Select * from zahtev where idzahtev="+arg1);
			if (rs.next()!=true) return -1; 
			
			rs = Baza.st.executeQuery("Select ku.idkorisnik, ku.status from korisnik ko,kurir ku where ko.username='"+arg0+"' and ku.idkorisnik=ko.idkorisnik");
			if (rs.next()!=true) return -1; 
			int id=rs.getInt(1);
			int sta=rs.getInt(2);
			if (sta!=0) return -1;
			
			
			String columnNames[] = new String[] { "idponuda" };
			
			PreparedStatement ps1 = Baza.conn.prepareStatement("insert into ponuda (idzahtev,idkorisnik,cena) values (?,?,?)",columnNames);
			ps1.setInt(1, arg1);
			ps1.setInt(2, id);
			
			if (arg2!=null)
			ps1.setBigDecimal(3, arg2.setScale(3,BigDecimal.ROUND_HALF_UP));
			else
			{
				Random rand = new Random(); 
				int cena = rand.nextInt(10);
				ps1.setBigDecimal(3, new BigDecimal(cena).setScale(3,BigDecimal.ROUND_HALF_UP));
			}
			//ps1.executeUpdate();
						
		
			if (ps1.executeUpdate() > 0) {
	           
	           rs = ps1.getGeneratedKeys();
	            if ( rs.next() ) {
	                pk = rs.getInt(1);
	            }
	        }
			ps1.close();
			
			return pk;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return -1;	
		}
	}

	@Override
	public boolean acceptAnOffer(int arg0) {//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
ResultSet rs;
BigDecimal bd=null;		


		try {
		
			rs = Baza.st.executeQuery("Select idzahtev,cena,idkorisnik from ponuda where idponuda= " + arg0);
			if (rs.next()==false) return false;
			int idz=rs.getInt(1);
			BigDecimal cen=rs.getBigDecimal(2);
			int idk=rs.getInt(3);
			
			rs = Baza.st.executeQuery("Select Opstinaod,opstinado,tezina,tip from zahtev where idzahtev= " + idz);
			if (rs.next()==false) return false;
			int ido1=rs.getInt(1);
			int ido2=rs.getInt(2);
			BigDecimal tez=rs.getBigDecimal(3);
			int tip=rs.getInt(4);
			
			rs = Baza.st.executeQuery("Select x,y from opstina where idopstina= " + ido1);
			if (rs.next()==false) return false;
			int x1=rs.getInt(1);
			int y1=rs.getInt(2);

			rs = Baza.st.executeQuery("Select x,y from opstina where idopstina= " + ido2);
			if (rs.next()==false) return false;
			int x2=rs.getInt(1);
			int y2=rs.getInt(2);
			
			
			
			double duzina=rastojanje(x1, y1, x2, y2);
			
			bd=cena(tez,tip,duzina,cen);
			
			
			
			
			//System.out.print(bd.setScale(3,BigDecimal.ROUND_HALF_UP)+"\n");
			
		
			PreparedStatement ps1 = Baza.conn.prepareStatement("update zahtev set idkorisnik=?,cena=?,vrprihvatanja=?,status=?,duzina=? where idzahtev=?  ");
			
			
			ps1.setInt(1, idk);
			ps1.setBigDecimal(2, bd.setScale(3,BigDecimal.ROUND_HALF_UP));
			ps1.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
			ps1.setInt(4,1);
			ps1.setDouble(5, duzina);
			ps1.setInt(6, idz);
			
			
			
			ps1.executeUpdate();
			ps1.close();	
			
			

			PreparedStatement ps2 = Baza.conn.prepareStatement("insert into voznja (idzahtev,idkorisnik,gotovo) values (?,?,?)");
			ps2.setInt(1, idz);
			ps2.setInt(2, idk);
			ps2.setInt(3, 0);
			ps2.executeUpdate();
			ps2.close();
			
		//	Baza.st.executeUpdate("delete from ponuda where IDzahtev="+idz);

			
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Integer> getAllOffers() { ///// HOCE IDPONUDE, PROMENITI U BAZI
		ResultSet rs;
		List<Integer> lista = new ArrayList<Integer>();
				
				try {
				
					rs = Baza.st.executeQuery("Select idponuda from ponuda order by idzahtev");
					if (rs.next()==false) return null;
								
					do {
						lista.add(rs.getInt(1));
					} while(rs.next());
			
					return lista;
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int arg0) {
		ResultSet rs;
		List<Pair<Integer, BigDecimal>> lista = new ArrayList<>();
				
				try {
				
					rs = Baza.st.executeQuery("Select idponuda, cena from ponuda where idzahtev= "+arg0+" order by idzahtev");
					if (rs.next()==false) return null;
					
					do {
						int id=rs.getInt(1);

						BigDecimal cen=rs.getBigDecimal(2);
						
						lista.add(new BV090555_PackageOperationsPair(id, cen));
				
					} while(rs.next());
			
					return lista;
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
		
		
	}

	@Override
	public Integer getDeliveryStatus(int arg0) {
ResultSet rs;
		
		try {		
			rs = Baza.st.executeQuery("Select status from zahtev where idzahtev= " + arg0);
			if (rs.next()==false) return null;
			int stat=rs.getInt(1);
	
			return stat;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Integer> getDrive(String arg0) {
ResultSet rs;
List<Integer> lista = new ArrayList<Integer>();
		
		try {
		
			rs = Baza.st.executeQuery("Select ko.idkorisnik from korisnik ko, kurir ku where ko.username='" + arg0+"' and ku.idkorisnik=ko.idkorisnik");
			if (rs.next()==false) return null;
			int id=rs.getInt(1);
			
			rs = Baza.st.executeQuery("Select v.idzahtev from voznja v,zahtev z where z.idzahtev=v.idzahtev and z.status=2 and v.idkorisnik="+id);
			if (rs.next()==false) return null;

		
			do {
				lista.add(rs.getInt(1));
			} while(rs.next());
	
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
