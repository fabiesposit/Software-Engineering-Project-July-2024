package controller;

import java.util.ArrayList;


import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;
import entity.EntityFacadeCliente;
import entity.EntityFacadeImplCliente;

//singleton
public class ControllerCliente {
	private static ControllerCliente uniqueInstance;
	private EntityFacadeCliente facade;
	
	
	private ControllerCliente(){		
		//ogni volta che viene creato un controller si ottiene un'istanza della classe Facade
		this.facade = EntityFacadeImplCliente.getInstance();
	};
	
	//per ottenere l'unica istanza del controller 
	public static ControllerCliente getInstance() {
		
		if(uniqueInstance == null) {
			
			uniqueInstance = new ControllerCliente();
		}
		
		return uniqueInstance;
		
	} 
	
	
	//restituisce Lista di tutti B&B  
	public ArrayList<MyDTO_BB> visualizzaListaBB() {	
		
		ArrayList<MyDTO_BB> lista_bb = facade.visualizzaListaBB();
		
		return lista_bb;
		
	}
	
	//restituisce Lista di B&B filtrati per Localita
	public ArrayList<MyDTO_BB> visualizzaListaBBPerLocalita(String localita) {	
		
		ArrayList<MyDTO_BB> lista_bb = facade.visualizzaListaBBPerLocalita(localita);
		
		return lista_bb;
		
	}
	
	//restituisce Lista di B&B filtrati per Data
	public ArrayList<MyDTO_BB> visualizzaListaBBPerData(String data) {	
		
		ArrayList<MyDTO_BB> lista_bb = facade.visualizzaListaBBPerData(data);
		
		return lista_bb;
		
	}

	//restituisce un B&B identificato dall'id
	public MyDTO_BB VisualizzaBB(int idbb) {
		
		MyDTO_BB bb = facade.visualizzaBB(idbb);
		
		return bb;
	}
	
	//restituisce tutte le prenotazioni (successive alla data corrente) di uno specifico Utente 
	public ArrayList<MyDTO_Prenotazione> visualizzaPrenotazioni(String Username) {	
		
		ArrayList<MyDTO_Prenotazione> lista_p = facade.visualizzaPrenotazioni(Username);
		
		return lista_p;
		
	}	
	
	//verifica se la data specificata dall'utente è disponibile per uno specifico B&B identificato da un id
	public boolean verificaDate(String data, int idBB) {
		
		boolean date_disponibili = facade.verificaDate(data, idBB);
		
		return date_disponibili;
	}
	

	//calcola Costo totale del pernottamento
	public float calcolaCosto(int idBB) {
		
		return facade.calcolaCosto(idBB);
	}
	
	//metodo fittizio per verificare la correttezza dei dati di pagamento
	public boolean verificaMetodoDiPagamento(String numerocarta, int cvv) {
		boolean value = true;
		//TODO Aggiungere il seervizio bancario che verifichi effettivamente la correttezza dei dati
		
		return value;
	}
	
	//permette di creare una nuova prenotazione
	public boolean confermaPrenotazione(String username,int idBB,float costo_totale, String data_di_prenotazione) {
		
		return facade.CreaPrenotazione(username, idBB, costo_totale, data_di_prenotazione);
		
	}
	
	//permette di annullare una Prenotazione
	public boolean annullaPrenotazione(String username, int id_prenotazione) {
		return facade.annullaPrenotazione(username, id_prenotazione);
	}
	
	//restituisce tutti i B&B dove ha pernottato un utente	
	public ArrayList<MyDTO_BB> visualizzaBBPernottati(String username){
		
		ArrayList<MyDTO_BB> bb_pernottati = facade.visualizzaBBPernottati(username);
		
		return bb_pernottati;
	}
		
	//verifica se un utente ha già valutato una struttura ( restituisce voto valutazione )
	public int strutturaValutata(String username,int idBB) {
	
		int voto = facade.strutturaValutata(username, idBB);
		
		return voto;
	}
	
	//inserisce o aggiorna valutazione
	public boolean inserisciValutazione(String username, int idBB, int voto, boolean aggiorna) {
		
		boolean ret = facade.inserisciValutazione(username, idBB, voto, aggiorna);
		
		return ret;
	}
}
