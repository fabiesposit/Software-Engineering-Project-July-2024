package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionManager {
	
	public static void main(String[]args) {
		
		try {
			
			Connection conn = openConnection();
			
			Statement stm = conn.createStatement();
						
			closeConnection(conn);
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static Connection openConnection() throws SQLException, ClassNotFoundException {
		
		String driver = new String("com.mysql.cj.jdbc.Driver");
		
		Class.forName(driver);
		
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "dbcatenabb";
		String userName = "root";
		String password = "fabiosDB";
			
		Connection conn = DriverManager.getConnection(url+dbName,userName,password);
			
		System.out.println("Connesso");
		return conn;			
	}
	 
	public static void closeConnection(Connection c) throws SQLException {
		
		c.close();		
	}
	
	
	public static void insertQuery(String query) throws ClassNotFoundException, SQLException {
		
		Connection conn = openConnection();
		
		Statement statement = conn.createStatement();
		
		statement.executeUpdate(query);
		
		closeConnection(conn);	
	}
	
	public static ResultSet selectQuery(String query) throws SQLException, ClassNotFoundException {
		
		Connection conn = openConnection();
		
		Statement statement = conn.createStatement();
		
		ResultSet res = statement.executeQuery(query);
		
		return res;	
	}
	
	public static void deleteQuery(String query) throws SQLException, ClassNotFoundException{
		
		Connection conn = openConnection();
		
		Statement statement = conn.createStatement();
		
		statement.executeUpdate(query);
		
		closeConnection(conn);		
	}
	
	public static void updateQuery(String query) throws SQLException, ClassNotFoundException{
		
		Connection conn = openConnection();
		
		Statement statement = conn.createStatement();
		
		statement.executeUpdate(query);
				closeConnection(conn);
		
	}
	
	
	
	
	
	
	
}
