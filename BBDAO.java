package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.MyDTO_BB;
import entity.EntityValutazione;

public class BBDAO {
	private String nome;
	private int posti_letto;
	private String localita;
	private float costo_giornaliero;
	private float media_recensioni;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPosti_letto() {
		return posti_letto;
	}

	public void setPosti_letto(int posti_letto) {
		this.posti_letto = posti_letto;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public float getCosto_giornaliero() {
		return costo_giornaliero;
	}

	public void setCosto_giornaliero(float costo_giornaliero) {
		this.costo_giornaliero = costo_giornaliero;
	}

	public float getMedia_recensioni() {
		return media_recensioni;
	}

	public void setMedia_recensioni(float media_recensioni) {
		this.media_recensioni = media_recensioni;
	}
	//costruttore 
	public BBDAO() {
		
	}
	
	public BBDAO(String nome, int posti_letto, String localita, float costo_giornaliero) {
			  
			  this.nome=nome;
			  this.posti_letto=posti_letto;
			  this.localita=localita;
			  this.costo_giornaliero=costo_giornaliero;
	}
	

	public BBDAO(String nome, int posti_letto, String localita, float costo_giornaliero, float media_recensioni) {
	
		this.nome = nome;
		this.posti_letto = posti_letto;
		this.localita = localita;
		this.costo_giornaliero = costo_giornaliero;
		this.media_recensioni = media_recensioni;	
	}
	
	public BBDAO(String nome, int posti_letto, String localita, float costo_giornaliero, int id) {
		super();
		this.nome = nome;
		this.posti_letto = posti_letto;
		this.localita = localita;
		this.costo_giornaliero = costo_giornaliero;
		this.id = id;
	}
	


	//costruttore con id 
	public BBDAO(int idBB) {
		
		this.id = idBB;
		caricaDaDB();	
	}
	

	public static ArrayList<BBDAO> getListaBB() {
		
		String query = new String("SELECT * FROM bedbreakfast");
		
		ArrayList<BBDAO> bb_list = new ArrayList<BBDAO>();

		try {
			ResultSet res = DBConnectionManager.selectQuery(query);
			
			while(res.next()) {
				
			
				BBDAO bb = new BBDAO();
				
				bb.setNome(res.getString("nome"));
				bb.setPosti_letto(res.getInt("posti_letto"));
				bb.setLocalita(res.getString("località"));
				bb.setMedia_recensioni(res.getFloat("media_recensioni"));
				bb.setCosto_giornaliero(res.getFloat("costo_giornaliero"));
				bb.setId(res.getInt("idbedbreakfast"));

				bb_list.add(bb);			
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bb_list;
	}
	
	
	private void caricaDaDB() {
		
		String query = new String("SELECT * FROM bedbreakfast WHERE idbedbreakfast = "+this.id);
		
		try {
			ResultSet res = DBConnectionManager.selectQuery(query);
			
			if(res.next()) {
				this.setNome(res.getString("nome"));
				this.setPosti_letto(res.getInt("posti_letto"));
				this.setLocalita(res.getString("località"));
				this.setMedia_recensioni(res.getFloat("media_recensioni"));
				this.setCosto_giornaliero(res.getFloat("costo_giornaliero"));
			}		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	
	//TODO
	public boolean salvasuDB(ArrayList<ServizioDAO> list_sd) {
		  
		  int id_bb = 0;
		  int id_servizio=0;

		  String query = new String("INSERT INTO bedbreakfast (nome, posti_letto, località, costo_giornaliero) VALUES ('"+this.nome+"','"+this.posti_letto+"','"+this.localita+"','"+this.costo_giornaliero+"')");
		  String query_id = new String("SELECT idbedbreakfast FROM bedbreakfast ORDER BY idbedbreakfast DESC LIMIT 1");
		  
		  boolean aggiunto = false;
		  
		  try {
		   
			   DBConnectionManager.insertQuery(query);
			   ResultSet rs = DBConnectionManager.selectQuery(query_id);
			   
			   if(rs.next()) {
				   id_bb = rs.getInt("idbedbreakfast");
			   }
			   for(int i=0; i<list_sd.size();i++) {
				    id_servizio = list_sd.get(i).getId();
				    String query_service = new String("INSERT INTO bb_has_service (bedbreakfast_idbedbreakfast, servizi_idservizi) VALUES ('"+id_bb+"','"+id_servizio+"')");
				    
				    DBConnectionManager.insertQuery(query_service);
			   }
 
			   aggiunto = true;
			  		   
		  } catch (ClassNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
			aggiunto = false;

		  } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   
		   aggiunto = false;
		  }
		  
		  return aggiunto; 
		  
		  
		 }
	
	
	//TODO
	public boolean rimuoviDaDB() {
		boolean rimosso = false;
		
		//query per eliminare bb e query per eliminare da bb_has_service
		String query2 = new String("DELETE FROM bb_has_service WHERE bedbreakfast_idbedbreakfast = " + this.id);
		String query1 = new String("DELETE FROM bedbreakfast WHERE idbedbreakfast = " + this.id);
		
		
		  try {
			   
			   DBConnectionManager.deleteQuery(query2);
			   DBConnectionManager.deleteQuery(query1);
			  		   
			   rimosso = true;			   
			   
			  } catch (ClassNotFoundException e) {
				  
			   // TODO Auto-generated catch block
				  	e.printStackTrace();
					rimosso = false;
			  } catch (SQLException e) {
				  
			   // TODO Auto-generated catch block
				  	e.printStackTrace();  
			   		rimosso = false;
			  }
		
		
		return rimosso;
	}


	//METODO PER AGGIORNARE LA MEDIA DELLE RECENSIONI
	public int aggiornaMediaRecensioni(float media) {
		
		String query = new String("UPDATE bedbreakfast SET media_recensioni = "+ Float.toString(media)+ " WHERE idbedbreakfast = '"+Integer.toString(id)+"' ");
		
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
		
		
		return ret;
	}

	//TODO
	public boolean updateBB(ArrayList<ServizioDAO> servizi) {
		
		boolean modificato = false;

		//aggiorna BB table
		String query = new String("UPDATE bedbreakfast SET nome = '" + this.nome + "' , posti_letto = '" + this.posti_letto + "' , località = '" + this.localita + "' , costo_giornaliero = '" + this.costo_giornaliero + "' WHERE idbedbreakfast = '"+this.id+"'");
		
		//aggiorna servizi legati al BB faccio una delete e una nuova insert
		String query1 = new String("DELETE FROM bb_has_service WHERE bedbreakfast_idbedbreakfast = '" +this.id +"'");
		
	    try {
	    	
			DBConnectionManager.updateQuery(query);
			
			DBConnectionManager.deleteQuery(query1);
			
			for (int i=0; i<servizi.size(); i++) {
			 String query2 = new String("INSERT INTO bb_has_service (bedbreakfast_idbedbreakfast, servizi_idservizi) VALUES ('"+this.id+"','"+servizi.get(i).getId()+"')");

			DBConnectionManager.insertQuery(query2);
			}
			
			modificato = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modificato = false;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modificato = false;
	
		}

		return modificato;		
	}
	
}
