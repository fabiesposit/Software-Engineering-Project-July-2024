package entity;

import database.ValutazioneDAO;

public class EntityValutazione {

	private int voto;
	private Client client;
	private EntityBB bb;
	
	
	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public Client getClient() {
		return client;
	}

	public void setBb(EntityBB bb) {
		this.bb = bb;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	// Funzioni per associare il client e il BB alla valutazione
	
	public void associaCliente(Client cliente) {
		
		this.client = cliente;
	}
	
	public void associaBB(EntityBB bb) {
		this.bb = bb;
	}
	
	public EntityBB getBb() {
		return bb;
	}

	public EntityValutazione() {
		
		this.voto = 0;
		this.bb = new EntityBB();
		this.client = new Client();
	
	}
	
	//costruttore che serve a caricare da DB la valutazione
	public EntityValutazione(String username,int idBB) {
		
		ValutazioneDAO vd = new ValutazioneDAO(username,idBB);	
		this.voto = vd.getVoto();		
	}
	
	//funzione per salvare la valutazione nel DB	
	public int salvaInDB() {
		
		ValutazioneDAO vdao = new ValutazioneDAO();
		
		vdao.setUsername(this.client.getUsername());
		vdao.setIdBB(this.bb.getId());
		vdao.setVoto(this.voto);
		
		int ret = vdao.salvaInDB();
		
		this.bb.aggiornaMedia();
		
		return ret;
	}
	
	//funzione per aggiornare il voto di una valutazione esistente
	public int aggiornaValutazioneInDB() {
		
		ValutazioneDAO vdao = new ValutazioneDAO();
		
		vdao.setUsername(this.client.getUsername());
		vdao.setIdBB(this.bb.getId());
		vdao.setVoto(this.voto);
		
		int ret = vdao.aggiornaValutazione();
		
		this.bb.aggiornaMedia();
		
		return ret;
	}
	

}
