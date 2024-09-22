package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.ControllerAutenticazione;

public class ControllerAutenticazioneTest {
	
	private ControllerAutenticazione controller;
	
	@Before
	public void setUp() throws Exception {
		
		controller = ControllerAutenticazione.getInstance();
	}

	@Test
	public void testRegistraUtente() {
		
		//Supponiamo che l'username non esista e si provi ad aggiungere un utente
		boolean ret = controller.registraUtente("Mario", "Rossi", "RSSMRA22M78B953E", "mariucc", "Studente2!");
		boolean expected = true;
		assertEquals(expected,ret);
		
		//Supponiamo che l'username esista e si provi ad aggiungere un utente
		ret = controller.registraUtente("Nicola", "Grandioso", "GRNNCL02M29B963E", "nicog", "Studente1!");
		expected = false;
		assertEquals(expected,ret);
	}
 
	@Test
	public void testLoginUtente() {
		
		boolean expected;
		boolean ret;
		
		//Supponiamo che l'username esista e la password coincida
		ret = controller.loginUtente("nicog", "Studente1!");
		expected = true;
		assertEquals(expected,ret);
		
		//Supponiamo che l'username esista e la password non coincida
		ret = controller.loginUtente("nicog", "Studente1");
		expected = false;
		assertEquals(expected,ret);
		
		//Supponiamo che l'username non esista
		ret = controller.loginUtente("nicogra", "Studente1");
		expected = false;
		assertEquals(expected,ret);
		 
	}
}
