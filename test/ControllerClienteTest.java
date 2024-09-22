package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ControllerCliente;
import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;
import dto.MyDTO_S;

public class ControllerClienteTest {
	
	private ControllerCliente controller;
	
	@Before
	public void setUp() throws Exception {
		
		controller = ControllerCliente.getInstance();
		
	}

	@After
	public void tearDown() throws Exception {
		//TO DO PROVARE A FARE UNA FUNZIONE DI RIMOZIONE DAL DB CHE RIMUOVE GLI UTENTI REGISTRATI
		
	}

//	@Test
//	public void testRegistraUtente() {
//		
//		//Supponiamo che l'username non esista e si provi ad aggiungere un utente
//		boolean ret = controller.registraUtente("Mario", "Rossi", "RSSMRA22M78B953E", "mariucc", "Studente2!");
//		boolean expected = true;
//		assertEquals(expected,ret);
//		
//		//Supponiamo che l'username esista e si provi ad aggiungere un utente
//		ret = controller.registraUtente("Nicola", "Grandioso", "GRNNCL02M29B963E", "nicog", "Studente1!");
//		expected = false;
//		assertEquals(expected,ret);
//	}
// 
//	@Test
//	public void testLoginUtente() {
//		
//		boolean expected;
//		boolean ret;
//		
//		//Supponiamo che l'username esista e la password coincida
//		ret = controller.loginUtente("nicog", "Studente1!");
//		expected = true;
//		assertEquals(expected,ret);
//		
//		//Supponiamo che l'username esista e la password non coincida
//		ret = controller.loginUtente("nicog", "Studente1");
//		expected = false;
//		assertEquals(expected,ret);
//		
//		//Supponiamo che l'username non esista
//		ret = controller.loginUtente("nicogra", "Studente1");
//		expected = false;
//		assertEquals(expected,ret);
//		 
//	}

	@Test
	public void testVisualizzaListaBB() {
		
		//Inseriamo gli indici dei B&B attualmente presenti nel nostro database
		ArrayList<Integer> lista_attesa = new ArrayList<Integer>();
		
		for(int i = 4;i<11;i++) {
			lista_attesa.add(i);
		}
		
		ArrayList<MyDTO_BB> lista_bb_ricevuta = controller.visualizzaListaBB();
		ArrayList<Integer> indici_ricevuti = new ArrayList<Integer>();
		
		//Estraiamo gli indici ricevuti dal metodo richiamato
		for(int i = 0;i<lista_bb_ricevuta.size();i++) {
			
			indici_ricevuti.add(lista_bb_ricevuta.get(i).getId());
		}
		
		//Verifichiamo se gli indici coincidono
		assertEquals(lista_attesa,indici_ricevuti);
	}

	@Test
	public void testVisualizzaListaBBPerLocalita() {
		
		//Inseriamo gli indici dei B&B attualmente presenti nel nostro database
		ArrayList<Integer> lista_attesa = new ArrayList<Integer>();
				
		lista_attesa.add(5); 
		lista_attesa.add(10);
		
		ArrayList<MyDTO_BB> lista_bb_ricevuta = controller.visualizzaListaBBPerLocalita("Napoli");
		ArrayList<Integer> indici_ricevuti = new ArrayList<Integer>();
		
		//Estraiamo gli indici ricevuti dal metodo richiamato
		for(int i = 0;i<lista_bb_ricevuta.size();i++) {
			
			indici_ricevuti.add(lista_bb_ricevuta.get(i).getId());
		}
		
		//Verifichiamo se gli indici coincidono
		assertEquals(lista_attesa,indici_ricevuti);
		
	}

	@Test
	public void testVisualizzaListaBBPerData() {

		//Inseriamo gli indici dei B&B attualmente presenti nel nostro database
		ArrayList<Integer> lista_attesa = new ArrayList<Integer>();
		
		for(int i = 4;i<11;i++) {
			lista_attesa.add(i);
		}
		
		ArrayList<MyDTO_BB> lista_bb_ricevuta = controller.visualizzaListaBBPerData("05/08/2026");
		ArrayList<Integer> indici_ricevuti = new ArrayList<Integer>();
				
		//Estraiamo gli indici ricevuti dal metodo richiamato
		for(int i = 0;i<lista_bb_ricevuta.size();i++) {
					
				indici_ricevuti.add(lista_bb_ricevuta.get(i).getId());
				
		}
				
		//Verifichiamo se gli indici coincidono
		assertEquals(lista_attesa,indici_ricevuti);
	}

	@Test
	public void testVisualizzaBB() {
		
		//RISULTATO ATTESO
		ArrayList<MyDTO_S> lista_servizi = null;

		MyDTO_BB bb_atteso = new MyDTO_BB("Miramare",5,"Rimini",(float)3.0,30,lista_servizi,4);
		
		//RISULTATO OTTENUTO
		MyDTO_BB bb_ricevuto = controller.VisualizzaBB(4);
		
		assertEquals(bb_atteso.getNome(),bb_ricevuto.getNome());
		assertEquals(bb_atteso.getLocalita(),bb_ricevuto.getLocalita());
		assertEquals(bb_atteso.getCosto_giornaliero(),bb_ricevuto.getCosto_giornaliero(),0.0);
		assertEquals(bb_atteso.getId(),bb_ricevuto.getId());
		assertEquals(bb_atteso.getMedia_recensioni(),bb_ricevuto.getMedia_recensioni(),0.0001);
		assertEquals(bb_atteso.getPosti_letto(),bb_ricevuto.getPosti_letto());
		
		
	}

	@Test
	public void testVisualizzaPrenotazioni() {
		
		// Array che contiene gli id delle prenotazioni attese
		
		ArrayList<Integer> id_prenotazioni_attesi = new ArrayList<Integer>();
		
		id_prenotazioni_attesi.add(7);
	
		// Prendiamo gli id delle prenotazioni ricevute
		ArrayList<MyDTO_Prenotazione> prenotazioni = controller.visualizzaPrenotazioni("nicog");
		
		ArrayList<Integer> id_prenotazioni_ricevuti = new ArrayList<Integer>();
		
		for(int i = 0;i<prenotazioni.size();i++) {
			
			id_prenotazioni_ricevuti.add(prenotazioni.get(i).getId_prenotazione());
		}
		
		// Confrontiamo i risultati ottenuti
		assertEquals(id_prenotazioni_attesi,id_prenotazioni_ricevuti);
	
	}

	@Test
	public void testVerificaDate() {
		boolean expected;
		boolean result;
		
		//Supponiamo che il B&B 5 sia occupato nella data 25/07/2024
		expected = false;
		result = controller.verificaDate("25/07/2024", 5);
		
		assertEquals(expected,result);
		
		//Supponiamo che il B&B sia libero nella data 25/08/2024
		expected = true;
		result = controller.verificaDate("25/08/2024", 4);
		
		assertEquals(expected,result);
	}

	@Test
	public void testCalcolaCosto() {
		
		//Testiamo che inserendo un idBB mi dia il costo totale del pernottamento in quel B&B
		
		float costo_expected = (float) 55.0;
		
		float costo_ricevuto = controller.calcolaCosto(6);
		
		assertEquals(costo_expected,costo_ricevuto,0.0);
	}

	/*
	 * 
	 * Per ora fittizio
	@Test
	public void testVerificaMetodoDiPagamento() {
		
	}
	*/
	
	@Test
	public void testConfermaPrenotazione() {
		boolean expected;
		boolean result;
		
		expected = true;
		result = controller.confermaPrenotazione("fabioe", 5, controller.calcolaCosto(5), "25/07/2024");
		
		assertEquals(expected,result);
		
	}

	@Test
	public void testAnnullaPrenotazione() {
		
		//La prenotazione viene annullata entro le 48h
		boolean expected = false;
		
		boolean result;
		
		// Supponiamo di provare a cancellare la prenotazione esistente di un utente
		
		result = controller.annullaPrenotazione("fabioe", 21);
		
		// Confrontiamo i risultati
		
		assertEquals(expected,result);
		
	}

	@Test
	public void testVisualizzaBBPernottati() {
		
		// Lista di id dei BB in cui ha pernottato l'utente nicog
		
		ArrayList<Integer> id_attesi = new ArrayList<Integer>();
		
		id_attesi.add(5);
	
		// Ottengo lista dei B&B in cui l'utente nicog ha pernottato
		
		ArrayList<MyDTO_BB> bb_pernottati = controller.visualizzaBBPernottati("nicog");
		
		
		ArrayList<Integer> id_prenotazioni = new ArrayList<Integer>();
		
		for(int i=0;i<bb_pernottati.size();i++) {
			
			id_prenotazioni.add(bb_pernottati.get(i).getId());
		}
		
		assertEquals(id_attesi,id_prenotazioni);
	}

	@Test
	public void testStrutturaValutata() {
		
		// Supponiamo che l'utente nicog non abbia già valutato il B&B 4, il voto ricevuto sarà -1
		
		int voto_atteso = -1;
		
		int voto_ricevuto;
		
		voto_ricevuto = controller.strutturaValutata("nicog", 4);
		
		assertEquals(voto_atteso,voto_ricevuto);
		
		// Supponiamo nicog  abbia già valutato il B&B 9, il voto ricevuto sarà 4
		
		voto_atteso = 4;
		
		voto_ricevuto = controller.strutturaValutata("nicog", 9);
		
		assertEquals(voto_atteso,voto_ricevuto);
	}

	@Test
	public void testInserisciValutazione() {
		
		boolean expected = true;
		boolean result;
		
		// Supponiamo che l'utente nicog voglia valutare il B&B 7 con il voto 3 supponendo che la valutazione non esista già
		
		result = controller.inserisciValutazione("nicog", 7, 3, false);
		
		assertEquals(expected,result);
		
		// Supponiamo che l'utente nicog voglia valutare il B&B 5 con il voto 2 supponendo che la valutazione esista già
		
		result = controller.inserisciValutazione("nicog", 5, 2, true);
				
		assertEquals(expected,result);
		
	}

}
