package entity;

import database.PrenotazioneDAO;

public class EntityPrenotazione {
	private int id_prenotazione;
	private EntityBB bb;
	private Client client;
	private float costo_totale;
	private String data_prenotazione;
	

	public int getId_prenotazione() {
		return id_prenotazione;
	}

	public void setId_prenotazione(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
	}

	public EntityBB getBb() {
		return bb;
	}

	public void setBb(EntityBB bb) {
		this.bb = bb;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public EntityPrenotazione(float costo_totale,String data_prenotazione) {
		
		this.costo_totale = costo_totale;
		this.data_prenotazione = data_prenotazione;
	}
	
	public EntityPrenotazione(int id_prenotazione) {
		PrenotazioneDAO pd = new PrenotazioneDAO(id_prenotazione);
		
		this.bb= new EntityBB(pd.getIdBB());
		this.client = new Client(pd.getUsername());
		this.costo_totale = pd.getCosto_totale();
		this.data_prenotazione = pd.getData_prenotazione();
		this.id_prenotazione = id_prenotazione;
		
	}
	
	//costruisce EntityPrenotazione a partire da EntityBB e PrenotazioneDAO
	public EntityPrenotazione(EntityBB bb, PrenotazioneDAO pdao) {
		
		this.bb = bb;
		//this.client = new Client(pdao.getUsername());
		this.costo_totale = pdao.getCosto_totale();
		this.data_prenotazione = pdao.getData_prenotazione();
	}
	

	public void associaCliente(Client cliente) {
		
		this.client= cliente;
	}
	
	public void associaBB(EntityBB bb) {
		
		this.bb= bb;
	}
	
	public int salvaSuDB() {
		
		PrenotazioneDAO pd = new PrenotazioneDAO();
		
		pd.setIdBB(this.bb.getId());
		pd.setCosto_totale(this.costo_totale);
		pd.setUsername(this.client.getUsername());
		pd.setData_prenotazione(this.data_prenotazione);
		
		int ret = pd.salvaInDB();
		
		return ret;
	}
	
	public boolean addebitaCosto(String Username) {
			
		return EntitySistemaBancario.addebitaCosto(Username, this.getCosto_totale());
	}
	
	
}
