package entity;

import java.util.ArrayList;

import dto.MyDTO_BB;
import dto.MyDTO_S;
import dto.myDTO_report;

public class EntityFacadeImplAmministratore implements EntityFacadeAmministratore {
	private static EntityFacadeImplAmministratore uniqueInstance;
	
	private EntityFacadeImplAmministratore () {
		
	}
	
	//per ottenere l'unica istanza del facadeImplAmministratore 
	public static EntityFacadeImplAmministratore getInstance() {
		if(uniqueInstance == null) {
			
			uniqueInstance = new EntityFacadeImplAmministratore();
		}
		
		return uniqueInstance;
	}

	@Override
	public  ArrayList<MyDTO_S> visualizzaListaServizi() {
		 
		EntityCatena catena = EntityCatena.getInstance();
		  
		ArrayList<MyDTO_S> lista_servizi = catena.getListaServizi();
		  
		return lista_servizi;
	}
		 		
	@Override
	public  ArrayList<MyDTO_BB> visualizzaListaBB() {
		
		EntityCatena catena = EntityCatena.getInstance();
		
		//metodo per aggiornare la lista di BB della catena
		catena.caricaListaBBDaDb();
		
		ArrayList<MyDTO_BB> lista_bb = catena.getListaBB();
		
		return lista_bb;	
	}
	
	@Override
	public  boolean aggiungiBB(ArrayList<Integer> lista, String nome, String localita, float costo_giornaliero, int posti_letto) {
		  
		boolean aggiunto = false;
	  
		EntityCatena catena = EntityCatena.getInstance();
		 
		aggiunto = catena.aggiungiBB(lista, nome, localita,costo_giornaliero, posti_letto);
		 
		return aggiunto;
	 }
	
	@Override
 	public  boolean aggiungiServizio(String tipo_servizio, float costo_servizio) {
 		
 		boolean aggiunto = false;
	
 		EntityCatena catena = EntityCatena.getInstance();
 			  
 		aggiunto = catena.aggiungiServizio(tipo_servizio, costo_servizio);
 		
 		//Dopo l'aggiunta, modifica o rimozione di un servizio , bisogna ricaricare la lista aggiornata nella catena
 		
 		catena.caricaListaServiziDaDB();
	
 		return aggiunto;
 	}

	@Override
	public  boolean rimuoviBB(int idBB) {
		
		boolean rimosso = false;
		
		EntityCatena catena = EntityCatena.getInstance();
		
		rimosso = catena.rimuoviBB(idBB);
		
		return rimosso;
	}
	
	
	@Override
	public  boolean modificaBB(MyDTO_BB bb_selezionato) {
		
		boolean modificato = false;
		
		EntityCatena catena=EntityCatena.getInstance();
		
		modificato=catena.modificaBB(bb_selezionato);
		
		return modificato;
	}
	
	@Override
	public  myDTO_report generaReport(String mese) {
		
		EntityCatena catena=EntityCatena.getInstance();
		
		myDTO_report report=catena.generaReport(mese);
		
		return report;
	 }

}
