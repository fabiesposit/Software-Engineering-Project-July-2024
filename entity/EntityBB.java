package entity;

import java.util.ArrayList;

import database.BBDAO;
import database.PrenotazioneDAO;
import database.ServizioDAO;
import database.ValutazioneDAO;
import dto.MyDTO_BB;
import dto.MyDTO_S;

public class EntityBB {
	private int id;
	private String nome;
	private int posti_letto;
	private String localita;
	private float costo_giornaliero;
	private float media_recensioni;
	private ArrayList<EntityServizio> servizi;
	private ArrayList<EntityPrenotazione> prenotazioni;
	private ArrayList<EntityValutazione> valutazioni;
	
	public ArrayList<EntityServizio> getServizi() {
		return servizi;
	}

	public void setServizi(ArrayList<EntityServizio> servizi) {
		this.servizi = servizi;
	}

	public ArrayList<EntityPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<EntityPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntityBB() {
		
	} 
	
	public EntityBB(String nome, int posti_letto, String localita, float costo_giornaliero) {

		this.nome = nome;
		this.posti_letto = posti_letto;
		this.localita = localita;
		this.costo_giornaliero = costo_giornaliero;
		this.servizi = new ArrayList<EntityServizio> ();
		this.prenotazioni = new ArrayList<EntityPrenotazione> ();
		this.valutazioni= new ArrayList<EntityValutazione> ();
	}
	
	public EntityBB(int id, String nome, int posti_letto, String localita, float costo_giornaliero,
			float media_recensioni, ArrayList<EntityServizio> servizi) {
		super();
		this.id = id;
		this.nome = nome;
		this.posti_letto = posti_letto;
		this.localita = localita;
		this.costo_giornaliero = costo_giornaliero;
		this.media_recensioni = media_recensioni;
		this.servizi = servizi;
		this.prenotazioni = new ArrayList<EntityPrenotazione> ();
		this.valutazioni= new ArrayList<EntityValutazione> ();
	}

	//costruttore che carica i dati del B&B da DB a partire dall'username (senza valutazioni, poichè c'è la media delle recensioni)
	public EntityBB(int idBB) {
		
		BBDAO bb = new BBDAO(idBB);
		
		this.nome = bb.getNome();
		this.posti_letto = bb.getPosti_letto();
		this.localita = bb.getLocalita();
		this.costo_giornaliero = bb.getCosto_giornaliero();
		this.media_recensioni = bb.getMedia_recensioni();		
		this.id = bb.getId();
		this.servizi = new  ArrayList<EntityServizio>();
		this.prenotazioni = new  ArrayList<EntityPrenotazione>();
		this.valutazioni = new ArrayList<EntityValutazione> ();
		
		//creo array servizi
		ArrayList<ServizioDAO> servizi_dao = new ArrayList<ServizioDAO>();
		servizi_dao = ServizioDAO.getServizi(this.id);
		
		//per ogni servizio costruisco entity servizi
		for(int i=0;i<servizi_dao.size();i++) {
			
			this.servizi.add(new EntityServizio(servizi_dao.get(i).getTipo(),servizi_dao.get(i).getCosto_giornaliero()));		
		}
		
		//creo array di prenotazioni
		ArrayList<PrenotazioneDAO> prenotazioni_dao = new ArrayList<PrenotazioneDAO>();
		prenotazioni_dao = PrenotazioneDAO.getPrenotazioni(this.id);

		//per ogni prenotazione costruisco oggetto entity con costruttore apposito
		for(int i=0;i<prenotazioni_dao.size();i++) {

			this.prenotazioni.add(new EntityPrenotazione(this,prenotazioni_dao.get(i)));			
		}			
	}
	
	//costruisco un entityBB senza prenotazioni e senza servizi
	public void get_slim_BB(int idbb) {

		BBDAO bb = new BBDAO(idbb);

		this.setNome(bb.getNome());
		this.setPosti_letto(bb.getPosti_letto()); 
		this.setLocalita(bb.getLocalita());
		this.setCosto_giornaliero(bb.getCosto_giornaliero());
		this.setMedia_recensioni(bb.getMedia_recensioni());
		this.setId(bb.getId());
		this.setServizi( new  ArrayList<EntityServizio>());
		this.setPrenotazioni(new  ArrayList<EntityPrenotazione>());
		
	}
	
	public void associaValutazione(EntityValutazione valutazione) {
		
		this.valutazioni.add(valutazione);
	}
	
	public void associaServizio(EntityServizio servizio) {
		
		this.servizi.add(servizio);
	}
	
	public EntityPrenotazione creaPrenotazione(Client client,float costo_totale, String data_prenotazione) {
		
		EntityPrenotazione prenotazione = new EntityPrenotazione(costo_totale,data_prenotazione);
		prenotazione.associaBB(this);
		prenotazione.associaCliente(client);
		this.prenotazioni.add(prenotazione);
		
		int ret = prenotazione.salvaSuDB();

		if(ret == -1) {
			
			prenotazione = null;
		}
		
		return prenotazione;
	}
	
	
	public void rimuoviPrenotazione(int id_prenotazione) {
		
		PrenotazioneDAO pdao = new PrenotazioneDAO(id_prenotazione);
		pdao.rimuoviDaDB();
	}
	
	public boolean verificaDate(String data) {
		
		boolean date_disponibili = true;
		
		ArrayList<String> date_occupate = new ArrayList<String>();
		
		for(int i =0; i< this.prenotazioni.size();i++) {
			
			date_occupate.add(this.prenotazioni.get(i).getData_prenotazione());
		}
		
		if(date_occupate.contains(data)) {
			
			date_disponibili = false;		
		}
		
		return date_disponibili;
	}
	
	public float calcolaCosto(int idBB) {
		
		BBDAO bb = new BBDAO(idBB);
		ArrayList<ServizioDAO> servizi_dao = new ArrayList<ServizioDAO>();
		servizi_dao = ServizioDAO.getServizi(idBB);
		
	
		float costo_totale = bb.getCosto_giornaliero();
						
		for(int i = 0;i<servizi_dao.size();i++) {
			System.out.println(servizi_dao.get(i).getCosto_giornaliero());
			costo_totale += servizi_dao.get(i).getCosto_giornaliero();
		}
		
		return costo_totale;
	}
	

	//ritorna il BB in formato DTO (senza le prenotazioni)
	public MyDTO_BB getBB(){
		
		ArrayList<MyDTO_S> list_dto_servizi = new ArrayList<MyDTO_S>();
		
		//siccome questo metodo lo utilizzo soltanto per get_slim_BB non dovrei aver bisogno dei servizi...
		
		for(int j = 0;j<this.getServizi().size();j++) {

			MyDTO_S dtos = new MyDTO_S(this.getServizi().get(j).getTipo(),this.getServizi().get(j).getCosto_giornaliero());		
			list_dto_servizi.add(dtos);
		}
		
		MyDTO_BB dtobb = new MyDTO_BB(this.getNome(),this.getPosti_letto(),this.getLocalita(),this.getMedia_recensioni(),this.getCosto_giornaliero(),list_dto_servizi,this.getId());
		
		return dtobb;
	}
	
	//salvo su DB il BB e i suoi servizi
	public boolean salva_su_DB() {
		  
		  boolean aggiunto = false;
		  
		  ArrayList<ServizioDAO> list_sd = new ArrayList<ServizioDAO>();
		  
		  for(int i =0; i<servizi.size();i++) {
			  
			   ServizioDAO sd = new ServizioDAO(servizi.get(i).getId());
			   list_sd.add(sd);	   
		  }
		  
		  BBDAO bb = new BBDAO(nome, posti_letto, localita, costo_giornaliero);
		  aggiunto = bb.salvasuDB(list_sd);
		  
		  return aggiunto;
	}
	
	//metodo per aggiornare la media delle recensioni quando viene creata una valutazione
	public void aggiornaMedia() {
		
		BBDAO bbdao = new BBDAO(this.id);
		
		ArrayList<Integer> voti = ValutazioneDAO.getVotiRecensioni(id);
		
		float media = 0;
		int elem = 0;
		
		for(int i = 0;i<voti.size();i++) {
			
			elem++;
			media += voti.get(i);			
		}
		
		media = media/elem;
		
		this.media_recensioni = media;
		
		bbdao.aggiornaMediaRecensioni(media);
	}

	
}
