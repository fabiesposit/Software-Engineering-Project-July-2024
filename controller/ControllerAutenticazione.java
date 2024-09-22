package controller;

import entity.EntityFacadeCliente;
import entity.EntityFacadeImplCliente;

public class ControllerAutenticazione {
	private static ControllerAutenticazione uniqueInstance;
	private EntityFacadeCliente facade;
	
	
	private ControllerAutenticazione(){
		facade = EntityFacadeImplCliente.getInstance();
	};
	
	//per ottenere l'unica istanza del controller 
	public static ControllerAutenticazione getInstance() {
		
		if(uniqueInstance == null) {
			
			uniqueInstance = new ControllerAutenticazione();
		}
		
		return uniqueInstance;
		
	}
	
	
	
	//permette di registrare un nuovo CLIENTE
	public boolean registraUtente(String nome, String cognome, String cf, String username, String password) {
		
		boolean result = false;
		
		result = facade.registraUtente(nome, cognome, cf, username, password);
		
		return result;
		
	}
	
	
	//permette l'accesso ad un Utente già registrato (AMMINISTRATORE O CLIENTE)
	public boolean loginUtente(String username, String password) {
		
		boolean result = false;
		
		result = facade.loginUtente(username, password);
		
		return result;
		
	}
	
	
	
}