package entity;

import java.util.ArrayList;

import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;

public interface EntityFacadeCliente {
	//facade cliente singleton
	
	
	public  boolean registraUtente(String nome, String cognome, String cf, String username, String password);
	public  boolean loginUtente(String username, String password);
	public  ArrayList<MyDTO_BB> visualizzaListaBB();
	public  ArrayList<MyDTO_BB> visualizzaListaBBPerLocalita(String localita);
	public  ArrayList<MyDTO_BB> visualizzaListaBBPerData(String data);
	public  MyDTO_BB visualizzaBB(int idBB);
	public  ArrayList<MyDTO_BB> visualizzaBBPernottati(String username);
	public  ArrayList<MyDTO_Prenotazione> visualizzaPrenotazioni(String Username);
	public  boolean verificaDate(String data, int idBB);
	public  float calcolaCosto(int idBB);
	public  boolean CreaPrenotazione(String username,int idBB, float costo_totale, String data);
	public  boolean annullaPrenotazione(String username, int id_prenotazione);
	public  int strutturaValutata(String username,int idBB);
	public  boolean inserisciValutazione(String username,int idBB,int voto, boolean aggiorna);
	
}
