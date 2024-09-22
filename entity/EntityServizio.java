package entity;

import java.util.ArrayList;

import database.ServizioDAO;

public class EntityServizio {
	private int id;
	private String tipo;
	private float costo_giornaliero;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


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
	
	public EntityServizio(int id) {

		this.id = id;
	}

	public EntityServizio(String tipo, float costo_giornaliero) {
		
		this.tipo = tipo;
		this.costo_giornaliero = costo_giornaliero;
	}
	
	public EntityServizio(int id, String tipo, float costo_giornaliero) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.costo_giornaliero = costo_giornaliero;
	}

	public boolean salvaSuDB() {
		
		boolean aggiunto = false;
		
		ServizioDAO nuovoServizio = new ServizioDAO(this.tipo, this.costo_giornaliero);
		aggiunto = nuovoServizio.salvaSuDB();
		
		return aggiunto;
	}
	
	

}
