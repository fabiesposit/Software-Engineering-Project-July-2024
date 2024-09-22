package controller;

import java.util.ArrayList;


import dto.MyDTO_BB;
import dto.MyDTO_S;
import dto.myDTO_report;
import entity.EntityFacadeAmministratore;
import entity.EntityFacadeImplAmministratore;

//singleton
public class ControllerAmministratore {
	private static ControllerAmministratore uniqueInstance;
	private EntityFacadeAmministratore facade;
	
	
	private ControllerAmministratore(){
		//ogni volta che viene creato un controller si ottiene un'istanza della classe Facade
		facade = EntityFacadeImplAmministratore.getInstance();
	};
	
	//per ottenere l'unica istanza del controller 
	public static ControllerAmministratore getInstance() {
		
		if(uniqueInstance == null) {
			
			uniqueInstance = new ControllerAmministratore();
		}
		
		return uniqueInstance;
		
	}

	//restituisce la lista di tutti i servizi memorizzati nel sistema
	public ArrayList<MyDTO_S> visualizzaListaServizi() {
		  
		  ArrayList<MyDTO_S> lista_servizi = facade.visualizzaListaServizi();
		  
		  return lista_servizi;
		  
		 }
	
	//restituisce Lista di tutti i B&B
	public ArrayList<MyDTO_BB> visualizzaListaBB() {	
		
		ArrayList<MyDTO_BB> lista_bb = facade.visualizzaListaBB();
		
		return lista_bb;
		
	}
	
	//permette di aggiungere un nuovo B&B
	public boolean aggiungiBB(ArrayList<Integer> lista, String nome, String localita, float costo_giornaliero, int posti_letto){
		  boolean aggiunto = false;
		  
		  //EntityFacadeImpl facade = new EntityFacadeImpl();
		  aggiunto = facade.aggiungiBB(lista, nome, localita, costo_giornaliero, posti_letto);
		  
		  return aggiunto;
	}
	
	//permette di aggiungere un nuovo Servizio nel sistema
	public boolean aggiungiServizio(String tipo_servizio, float costo_servizio) {

		boolean aggiunto = false;
		
		aggiunto = facade.aggiungiServizio(tipo_servizio, costo_servizio);
		 
		
		return aggiunto;
	}
	
	//permette di rimuovere un BB identificato dall'id
	public boolean rimuoviBB(int idBB) {
		
		boolean rimosso = false;
		
		rimosso = facade.rimuoviBB(idBB);
		 
		return rimosso;
	}

	//permette di modificareBB
	public boolean modificaBB(MyDTO_BB bb_selezionato) {
		
		boolean modificato = false;
		
		 modificato=facade.modificaBB(bb_selezionato);
		
		return modificato;
	}
		
	//restituisce report mensile di un determinato periodo (MM/YYYY)
	public myDTO_report generaReport(String periodo) {
		 
		  myDTO_report report=facade.generaReport(periodo);
		  
		  return report;
		  
	}

}
