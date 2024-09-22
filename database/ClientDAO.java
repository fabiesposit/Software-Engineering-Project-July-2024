package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {	
	private String name;
	private String cognome;
	private String cf;
	private String username;
	private String password;
	
	
	public ClientDAO(String name, String cognome, String cf, String username, String password) {
		super();
		this.name = name;
		this.cognome = cognome;
		this.cf = cf;
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientDAO(String username) {
		
		this.username = username;
		this.caricaDaDB();		
	}
	
	public boolean salvaSuDB() {
		
		String query = new String("INSERT INTO clienti (nome,cognome,codice_fiscale,username,password) VALUES ('"+this.name+"','"+this.cognome+"','"+this.cf+"','"+this.username+"','"+this.password+"')");
		
		boolean result = false;
		try {
			
			DBConnectionManager.insertQuery(query);			
			result = true;
					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		return result;			
	}

public void caricaDaDB() {
	
		String query = new String("SELECT * FROM clienti WHERE username='"+this.username+"';");
		
		System.out.println(query);
		
		
		try {
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) {
				
				this.setCf(rs.getString("codice_fiscale"));
				this.setName(rs.getString("nome"));
				this.setCognome(rs.getString("cognome"));
				this.setPassword(rs.getString("password"));
			}

			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


}
