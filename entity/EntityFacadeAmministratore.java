package entity;

import java.util.ArrayList;

import dto.MyDTO_BB;
import dto.MyDTO_S;
import dto.myDTO_report;

public interface EntityFacadeAmministratore {
	//facade amministratore singleton
	
	public  ArrayList<MyDTO_S> visualizzaListaServizi();
	public  ArrayList<MyDTO_BB> visualizzaListaBB();
	public  boolean aggiungiBB(ArrayList<Integer> lista, String nome, String localita, float costo_giornaliero, int posti_letto);
	public  boolean aggiungiServizio(String tipo_servizio, float costo_servizio);
	public  boolean rimuoviBB(int idBB);
	public  boolean modificaBB(MyDTO_BB bb_selezionato);
	public  myDTO_report generaReport(String mese);

}
