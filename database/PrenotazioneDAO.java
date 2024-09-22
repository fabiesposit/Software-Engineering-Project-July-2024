package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.EntitySistemaBancario;

public class PrenotazioneDAO {
	private int id_prenotazione;
	private int idBB;
	private String username;
	private float costo_totale;
	private String data_prenotazione;

	public int getId_prenotazione() {
		return id_prenotazione;
	}

	private void setId_prenotazione(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
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

	public float getCosto_totale() {
		return costo_totale;
	}

	public void setCosto_totale(float costo_totale) {
		this.costo_totale = costo_totale;
	}

	public String getData_prenotazione() {
		return data_prenotazione;
	}

	public void setData_prenotazione(String data_prenotazione) {
		this.data_prenotazione = data_prenotazione;
	}

	public PrenotazioneDAO() {
		super();
	}

	public PrenotazioneDAO(int id_prenotazione) {
		
		this.id_prenotazione = id_prenotazione;
		this.getPrenotazione();	
	}
	
	public int salvaInDB() {
		
		int ret = 0;
		
		String query = new String("INSERT INTO prenotazioni(bedbreakfast_idbedbreakfast,clienti_username,costo_totale,data_prenotazione) VALUES ('"+this.idBB+"','"+this.username+"','"+this.costo_totale +"','"+this.data_prenotazione+"')");
		
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
			
		return ret;
	}
	
	//ritorna prenotazioni per specifico BB
	public static ArrayList<PrenotazioneDAO> getPrenotazioni(int idBB){
		
		String query = new String("SELECT * FROM prenotazioni WHERE bedbreakfast_idbedbreakfast ='"+Integer.toString(idBB)+"'");
		
		System.out.println(query);
		ArrayList<PrenotazioneDAO> prenotazioniBB = new ArrayList<PrenotazioneDAO>();
		//System.out.println("STO PRELEVANDO prenotazioni RELATIVI AL BB "+ Integer.toString(idBB));
	
		try {
			
			ResultSet res = DBConnectionManager.selectQuery(query);
			
			System.out.println(res);
			
			while(res.next()) {

				PrenotazioneDAO prenotazione = new PrenotazioneDAO();
				
				prenotazione.setCosto_totale(res.getFloat("costo_totale"));
				prenotazione.setData_prenotazione(res.getString("data_prenotazione"));
				prenotazione.setIdBB(res.getInt("bedbreakfast_idbedbreakfast"));
				prenotazione.setId_prenotazione(res.getInt("id_prenotazione"));
				prenotazione.setUsername(res.getString("clienti_username"));
				
				prenotazioniBB.add(prenotazione); 				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prenotazioniBB;
	}	
	
	
	//ritorna prenotazioni per specifico utente
	public static ArrayList<PrenotazioneDAO> getPrenotazioni_Utente(String username){
		
		String query = new String("SELECT * FROM prenotazioni WHERE clienti_username ='"+username+"';");
		
		System.out.println(query);
		ArrayList<PrenotazioneDAO> prenotazioniBB = new ArrayList<PrenotazioneDAO>();
		//System.out.println("STO PRELEVANDO prenotazioni RELATIVI all'utente "+ username);
	
		try {
			ResultSet res = DBConnectionManager.selectQuery(query);
			
			System.out.println(res);
			
			while(res.next()) {

				PrenotazioneDAO prenotazione = new PrenotazioneDAO();
				
				prenotazione.setCosto_totale(res.getFloat("costo_totale"));
				prenotazione.setData_prenotazione(res.getString("data_prenotazione"));
				prenotazione.setIdBB(res.getInt("bedbreakfast_idbedbreakfast"));
				prenotazione.setId_prenotazione(res.getInt("id_prenotazione"));
				prenotazione.setUsername(res.getString("clienti_username"));
								
				prenotazioniBB.add(prenotazione);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prenotazioniBB;
	}	
	
	private void getPrenotazione() {
		
		String query = new String("SELECT * FROM prenotazioni WHERE id_prenotazione ='"+this.id_prenotazione+"';");
		
		ResultSet res;
		try {
			
			res = DBConnectionManager.selectQuery(query);
			if(res.next()) {
			this.setCosto_totale(res.getFloat("costo_totale"));
			this.setData_prenotazione(res.getString("data_prenotazione"));
			this.setIdBB(res.getInt("bedbreakfast_idbedbreakfast"));
			this.setUsername(res.getString("clienti_username"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	public void rimuoviDaDB() {
		
		String query = new String("DELETE FROM prenotazioni WHERE id_prenotazione = "+this.id_prenotazione+";");
		
		try {
			
			DBConnectionManager.insertQuery(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	
}
