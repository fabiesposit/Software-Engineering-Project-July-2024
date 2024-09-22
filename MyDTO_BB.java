package dto;

import java.util.ArrayList;

public class MyDTO_BB {
	private String nome;
	private int posti_letto;
	private String localita;
	private float media_recensioni;
	private float costo_giornaliero;
	private int id;
	private ArrayList<MyDTO_S> servizi;
	
	
	public MyDTO_BB(String nome, int posti_letto, String localita, float media_recensioni, float costo_giornaliero,ArrayList<MyDTO_S> servizi, int id) {
	
		this.nome = nome;
		this.posti_letto = posti_letto;
		this.localita = localita;
		this.media_recensioni = media_recensioni;
		this.costo_giornaliero = costo_giornaliero;
		this.servizi = servizi;
		this.id = id;
	}
	
	public MyDTO_BB() {
		
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

	public float getMedia_recensioni() {
		return media_recensioni;
	}

	public void setMedia_recensioni(float media_recensioni) {
		this.media_recensioni = media_recensioni;
	}

	public float getCosto_giornaliero() {
		return costo_giornaliero;
	}

	public void setCosto_giornaliero(float costo_giornaliero) {
		this.costo_giornaliero = costo_giornaliero;
	}

	public ArrayList<MyDTO_S> getServizi() {
		return servizi;
	}

	public void setServizi(ArrayList<MyDTO_S> servizi) {
		this.servizi = servizi;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		

}
