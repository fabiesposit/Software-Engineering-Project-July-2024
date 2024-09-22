package test;

import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ControllerAmministratore;
import dto.MyDTO_BB;
import dto.MyDTO_S;

public class ControllerAmministratoreTest {

	private ControllerAmministratore controller;
	
	@Before
	public void setUp() throws Exception {
		
		controller = ControllerAmministratore.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		
		
		controller.rimuoviBB(19);
	
	}

//	@Test
//	public void testLoginAmministratore() {
//		
//		boolean expected = false;
//		boolean result;
//		
//		// Supponiamo che l'username non contenga admin
//		
//		result = controller.loginAmministratore("nicog", "prova");
//		
//		assertEquals(expected,result);
//		
//		// Supponiamo che l'username contenga admin, ma non esista nel database ("admin2")
//		
//		result = controller.loginAmministratore("admin2", "prova");
//		
//		assertEquals(expected,result);
//		
//		// Supponiamo che l'username contenga admin ed esista nel database ("adminNico"), ma la password non coincida
//		
//		result = controller.loginAmministratore("adminNico", "prova");
//				
//		assertEquals(expected,result);
//		
//		// Supponiamo che l'username contenga admin ed esista nel database ("adminNico") e la password coincida
//		
//		expected = true;
//		
//		result = controller.loginAmministratore("adminNico", "admin1!");
//		
//		assertEquals(expected,result);
//		
//	}

	@Test
	public void testVisualizzaListaServizi() {
		
	
		ArrayList<MyDTO_S> lista_servizi = new ArrayList<MyDTO_S> ();
		ArrayList<Integer> id_attesi = new ArrayList<Integer>();
		ArrayList<Integer> id_ricevuti = new ArrayList<Integer>();
		
		// Costruiamo un array con tutti gli id dei servizi della catena
		
		for(int i = 4;i<13;i++) {
			
			id_attesi.add(i);
		}
		
		lista_servizi = controller.visualizzaListaServizi();
		
		for(int i = 0;i<lista_servizi.size();i++) {
			id_ricevuti.add(lista_servizi.get(i).getId());
		}
		
		assertEquals(id_attesi,id_ricevuti);
		
	}

	@Test
	public void testAggiungiBB() {

		// Supponiamo di aggiunger un nuovo BB
		
		boolean expected = true;
		boolean result;
		
		ArrayList<Integer> lista_servizi = new ArrayList<>();
		lista_servizi.add(6);
		result = controller.aggiungiBB(lista_servizi,"nuovB&B", "Roma", 25, 6);
		
		assertEquals(expected,result);
	}

	@Test
	public void testAggiungiServizio() {
		
		boolean expected = true;
		boolean result;
		
		result = controller.aggiungiServizio("prova", 0);
		
		assertEquals(expected,result);
		
	}

	@Test
	public void testRimuoviBB() {
		boolean expected = true;
		boolean result;
		
		result = controller.rimuoviBB(13);
		
		assertEquals(expected,result);
		
	}

	@Test
	public void testModificaBB() {

		boolean expected = true;
		boolean result;
		
		MyDTO_BB bb = new MyDTO_BB();
		
		ArrayList<MyDTO_S> lista_servizi = new ArrayList<MyDTO_S>();
		
		bb.setNome("BBAggiornato");
		bb.setId(11);
		bb.setCosto_giornaliero(25);
		bb.setLocalita("Roma");
		bb.setServizi(lista_servizi);
		
		result = controller.modificaBB(bb);
		
		assertEquals(expected,result);
		
	}

	@Test
	public void testGeneraReport() {
		
		//myDTO_report report = 
	}

}
