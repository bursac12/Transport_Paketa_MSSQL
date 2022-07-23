package student;

import java.sql.*;



public class Baza {
	
	public static Connection conn;
	public static Statement st;
	private static String URL = "jdbc:sqlserver://localhost\\sqlexpress:1433;databaseName=domaci";
	
	
	static {
	try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn = DriverManager.getConnection(URL,"sa","123");
		st = conn.createStatement();	
		
		} 
	catch (Exception e) {
			e.printStackTrace();
						};
			}
	
	
	
	
}
