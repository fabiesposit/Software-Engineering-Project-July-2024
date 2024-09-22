package entity;

import java.util.ArrayList;

import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;

public class EntityFacadeImplCliente implements EntityFacadeCliente{	
	private static EntityFacadeImplCliente uniqueInstance;
	
	private EntityFacadeImplCliente() {
		
	}
	
	//per ottenere l'unica istanza del facadeImplCliente
	public static EntityFacadeImplCliente getInstance() {
		if(uniqueInstance == null) {
			
			uniqueInstance = new EntityFacadeImplCliente();
		}
		
		return uniqueInstance;
	}
	
	@Override
	public  boolean registraUtente(String nome, String cognome, String cf, String username, String password) {
		
		boolean result = false;
		
		Client client = new Client(nome,cognome,cf,username,password);	
		
		result = client.salva_su_DB();
		
		return result;
	}
	
	@Override
	public  boolean loginUtente(String username, String password) {
		
		boolean ret = false;
		
		Client client = new Client(username);
		
		if(client.login_utente(password)) {
			
			ret = true;
		}
		
		return ret;	 
	}
	
	@Override
	public  ArrayList<MyDTO_BB> visualizzaListaBB() {
		
		EntityCatena catena = EntityCatena.getInstance();
		
		catena.caricaListaBBDaDb();
		
		ArrayList<MyDTO_BB> lista_bb = catena.getListaBB();
		
		return lista_bb;
	}
	
	@Override
	public  ArrayList<MyDTO_BB> visualizzaListaBBPerLocalita(String localita){
		
		EntityCatena catena = EntityCatena.getInstance();
		
		catena.caricaListaBBDaDb();
		
		ArrayList<MyDTO_BB> lista_bb = catena.getListaBBPerLocalita(localita);
		
		return lista_bb;
	}
	
	@Override
	public  ArrayList<MyDTO_BB> visualizzaListaBBPerData(String data){
		
		EntityCatena catena =EntityCatena.getInstance();
		
		catena.caricaListaBBDaDb();
						
		ArrayList<MyDTO_BB> lista_bb = catena.getListaBBPerData(data);
		
		return lista_bb;	
	}
		
	@Override
	public  MyDTO_BB visualizzaBB(int idBB) {
		
		EntityBB bb = new EntityBB();
		//carica da DB le informazioni principali del B&B (senza valutazioni, prenotazioni e servizi)
		bb.get_slim_BB(idBB);
		
		return bb.getBB();
	}
	
	@Override
	public  ArrayList<MyDTO_BB> visualizzaBBPernottati(String username) {
		
		Client client = new Client(username);
		
		ArrayList<MyDTO_BB> bb_pernottati = client.getListaBBPernottati();		
		
		return bb_pernottati;
	}
	
	@Override
	public  ArrayList<MyDTO_Prenotazione> visualizzaPrenotazioni(String Username) {

		Client cliente = new Client(Username);

		ArrayList<MyDTO_Prenotazione> lista_prenotazioni = cliente.getListaprenotazioni();
		
		return lista_prenotazioni;						
	}
	
	@Override
	public  boolean verificaDate(String data, int idBB) {

		EntityBB bb = new EntityBB(idBB);
		
		boolean date_disponibili = bb.verificaDate(data);

		return date_disponibili;
	}
	
	@Override
	public  float calcolaCosto(int idBB) {
		
		EntityBB bb = new EntityBB();
		
		float costo_totale = bb.calcolaCosto(idBB);
		
		return costo_totale;
	}
	
	@Override
	public  boolean CreaPrenotazione(String username, int idBB, float costo_totale, String data) {
		
		boolean value = false;
		
		Client client = new Client(username);
		
		EntityBB bb = new EntityBB(idBB);
		
		EntityPrenotazione prenotazione = bb.creaPrenotazione(client, costo_totale, data);

		if(prenotazione!=null) {
		
			client.addLinkToPrenotazione(prenotazione);
			
			value = true;	
		}
		
		return value;		
	}
	
	@Override
	public  boolean annullaPrenotazione(String username, int id_prenotazione) {
		
		//false: prenotazione effettuata entro le 48h correttamente annullata	
		//true: prenotazione effettuata dopo 48h correttamente annullata (con addebito)
		
		boolean value =false;
		
		Client cliente = new Client(username);
		
		EntityPrenotazione ep = new EntityPrenotazione(id_prenotazione);
		
		EntityBB bb = ep.getBb();
		
		if(cliente.verificaDatePrenotazione(id_prenotazione)) {
			ep.addebitaCosto(username);
			value = true;
		}
		
		bb.rimuoviPrenotazione(id_prenotazione);
		
		return value;
	}
	
	//restituisce voto valutazioni
	@Override
	public  int strutturaValutata(String username, int idBB) {
		
		Client client = new Client(username);
		
		
		int voto = client.verificaEsistenzaValutazione(idBB);
		
		return voto;
	}

	@Override
	public  boolean inserisciValutazione(String username, int idBB, int voto, boolean aggiorna) {
		
		Client cliente = new Client(username);
		
		EntityBB bb = new EntityBB(idBB);
		
		
		
		int ret = cliente.inserisciValutazione(bb, voto, aggiorna);
		
		if(ret == -1) {
			return false;
		}
		else {
			
			return true;
		}
	}
	
	
}
