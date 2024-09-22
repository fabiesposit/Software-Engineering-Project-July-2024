package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.MyDTO_S;

public class ServizioDAO {
	private String tipo;
	private float costo_giornaliero;
	private int Id;
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public float getCosto_giornaliero() {
		return costo_giornaliero;
	}


	public void setCosto_giornaliero(float costo_giornaliero) {
		this.costo_giornaliero = costo_giornaliero;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		
		Id = id;
	}

	public ServizioDAO() {
		
	}
	
	public ServizioDAO(int id) {
		
		this.Id = id;	
	}
	
	public ServizioDAO(String tipo, float costo_giornaliero, int id) {
		
		this.tipo = tipo;
		this.costo_giornaliero = costo_giornaliero;
		Id = id;
	}

	public ServizioDAO(String tipo, float costo_giornaliero) {
		
		this.tipo = tipo;
		this.costo_giornaliero = costo_giornaliero;
	}
	

	public static ArrayList<ServizioDAO> getServizi(int idBB){
		 
		String query = new String("SELECT * FROM bb_has_service JOIN servizi ON bb_has_service.servizi_idservizi = servizi.idservizi WHERE bb_has_service.bedbreakfast_idbedbreakfast ="+Integer.toString(idBB));
		
		ArrayList<ServizioDAO> serviziBB = new ArrayList<ServizioDAO>();
		//System.out.println("STO PRELEVANDO I SERVIZI RELATIVI AL BB "+ Integer.toString(idBB));
	
		try {
			ResultSet res = DBConnectionManager.selectQuery(query);
			
			while(res.next()) {
				
				ServizioDAO servizio = new ServizioDAO();
				
				servizio.setCosto_giornaliero(res.getFloat("costo_giornaliero"));
				servizio.setTipo(res.getString("tipo"));
				
				serviziBB.add(servizio);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serviziBB;
	}	
	 //metodo che ritorna TUTTI i servizi come classi DAO
	 public static ArrayList<ServizioDAO> getListaServizi() {
	  
	  String query = new String("SELECT * FROM servizi");
	  ArrayList<ServizioDAO> serviziBB = new ArrayList<ServizioDAO>();
	  
	  try {
		  
	   ResultSet res = DBConnectionManager.selectQuery(query);
	   
	   while(res.next()) {
	    
	    
		    
		    ServizioDAO servizio = new ServizioDAO();
		    
		    servizio.setCosto_giornaliero(res.getFloat("costo_giornaliero"));
		    servizio.setTipo(res.getString("tipo"));
		    servizio.setId(res.getInt("idservizi"));
		    
		    
		    serviziBB.add(servizio);
	   }
	  } catch (ClassNotFoundException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  
	  return serviziBB;
	  
	 }

	public boolean salvaSuDB() {
		// TODO Auto-generated method stub
		boolean aggiunto = false;
		
		String query = "INSERT INTO servizi (costo_giornaliero, tipo) VALUES ('" + this.costo_giornaliero +"','"+this.tipo+"')";
		
		try {
			
			DBConnectionManager.insertQuery(query);
			aggiunto = true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			aggiunto = false;
		}
		
		return aggiunto;
	}
	 
	
	 
	 
	 
	 
	 
	

}
