package dto;

public class MyDTO_Prenotazione {
	private int id_prenotazione;
	private int idBB;
	private String username;
	private float costo_totale;
	private String data_prenotazione;
	
	
	public MyDTO_Prenotazione(int id_prenotazione, int idBB, String username, float costo_totale,String data_prenotazione) {

		this.id_prenotazione = id_prenotazione;
		this.idBB = idBB;
		this.username = username;
		this.costo_totale = costo_totale;
		this.data_prenotazione = data_prenotazione;
	}

	public int getId_prenotazione() {
		return id_prenotazione;
	}
	
	public void setId_prenotazione(int id_prenotazione) {
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
	
}
