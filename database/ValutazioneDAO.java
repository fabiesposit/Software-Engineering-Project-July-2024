package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ValutazioneDAO {	
	public int voto;
	public int idBB;
	public String username;

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public int getIdBB() {
		return idBB;
	}

	public void setIdBB(int idBB) {
		this.idBB = idBB;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ValutazioneDAO() {
		
	}
	
	public ValutazioneDAO(String username,int idBB) {
		
		this.username = username;
		this.idBB = idBB;
		
		try {
			ResultSet res = DBConnectionManager.selectQuery("SELECT voto FROM valutazioni WHERE bedbreakfast_idbedbreakfast = '"+ Integer.toString(idBB) + "' AND clienti_username = '" + username+"';");
			
			
			if(res.next()) {
				
				this.voto = res.getInt("voto");
			}
			else {
				voto = -1;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	//METODO PER OTTENERE I VOTI DEI BB
	public static ArrayList<Integer> getVotiRecensioni(int idbb) {
		
		ArrayList<Integer> voti = new ArrayList<Integer>();
		
		String query = new String("SELECT voto FROM valutazioni WHERE bedbreakfast_idbedbreakfast = ' "+Integer.toString(idbb)+ "'");
		
		ResultSet res;
		try {
			res = DBConnectionManager.selectQuery(query);
			
			while(res.next()) {	
				voti.add(res.getInt("voto"));
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voti;
	}
	
	
	public int salvaInDB() {
		
		String query = new String("INSERT INTO Valutazioni(bedbreakfast_idbedbreakfast, clienti_username, voto) VALUES ('"+Integer.toString(this.idBB)+"','"+this.username+"','"+Integer.toString(this.voto)+"');");
		try {
			DBConnectionManager.insertQuery(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
		}
		
		
		return 0;
	}
	
	public int aggiornaValutazione() {
		
		String query = new String("UPDATE valutazioni SET voto = "+ Integer.toString(this.voto)+" WHERE bedbreakfast_idbedbreakfast = '"+Integer.toString(this.idBB)+"' AND clienti_username = '"+this.username+"'");
		int ret = 0;
		try {
			DBConnectionManager.insertQuery(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -1;
		}
		
		return 0;
		
	}
	
	
}
