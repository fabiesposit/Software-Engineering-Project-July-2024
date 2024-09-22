package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

import database.BBDAO;
import database.ClientDAO;
import database.PrenotazioneDAO;
import database.ValutazioneDAO;
import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;
import dto.MyDTO_S;

public class Client {
	private String name;
	private String cognome;
	private String cf;
	private String username;
	private String password;
	private ArrayList<EntityPrenotazione> prenotazioni; 
	private ArrayList<EntityValutazione> valutazioni;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Client() {
		
	}
	
	//costruttore di base per creare un nuovo Client
	public Client(String name, String cognome, String cf, String username, String password) {
		
		this.name = name;
		this.cognome = cognome;
		this.cf = cf;
		this.username = username;
		this.password = password;
		this.prenotazioni = new ArrayList<EntityPrenotazione>();
		this.valutazioni = new ArrayList<EntityValutazione>();
	}
	
	//costruttore che carica i dati del Cliente da DB a partire dall'username (senza prenotazioni e valutazioni)
	public Client(String username) {
	
		ClientDAO cd = new ClientDAO(username);
		
		this.name = cd.getName();
		this.cognome = cd.getCognome();
		this.cf = cd.getCf();
		this.password = cd.getPassword();
		this.username=username;
		this.prenotazioni = new ArrayList<EntityPrenotazione>();
		this.valutazioni = new ArrayList<EntityValutazione>();	
	}
		
	public void addLinkToPrenotazione(EntityPrenotazione prenotazione) {
		
		prenotazioni.add(prenotazione);	
	}
	
	public void addLinkToValutazione(EntityValutazione valutazione) {
		
		valutazioni.add(valutazione);	
	}

	//salva cliente su DB 
	public boolean salva_su_DB() {
		
		boolean result = false;
		
		ClientDAO cd = new ClientDAO(name,cognome,cf,username,password);
		
		result = cd.salvaSuDB();
		
		return result;	
	}
	
	public boolean login_utente(String password) {
		
		boolean check = false;
		
		if(this.password!=null) {
			if(this.password.equals(password) ) {
				
				check = true;
			}
		}
		
		return check;
	}
	
	//restituisce prenotazioni successive alla data corrente
	public ArrayList<MyDTO_Prenotazione> getListaprenotazioni(){
		
		ArrayList<MyDTO_Prenotazione> list_dto_p = new ArrayList<MyDTO_Prenotazione>();
		ArrayList<PrenotazioneDAO> p_list = PrenotazioneDAO.getPrenotazioni_Utente(this.username);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate currentDate = LocalDate.now();
	    
		for(int i=0;i<p_list.size();i++) {
			
			LocalDate dataprenotazione = LocalDate.parse(p_list.get(i).getData_prenotazione(), formatter);
			
			if(!dataprenotazione.isBefore(currentDate)) {
			
				MyDTO_Prenotazione dtop = new MyDTO_Prenotazione(p_list.get(i).getId_prenotazione(),p_list.get(i).getIdBB(),p_list.get(i).getUsername(),p_list.get(i).getCosto_totale(),p_list.get(i).getData_prenotazione());
				list_dto_p.add(dtop);
			}	
		}
		
		return list_dto_p;
	}
	
	//restituisce tutte le prenotazioni che ha effettuato un Cliente 
	public ArrayList<MyDTO_Prenotazione> getListaPrenotazioniTotali(){
		
		ArrayList<MyDTO_Prenotazione> list_dto_p = new ArrayList<MyDTO_Prenotazione>();
		ArrayList<PrenotazioneDAO> p_list = PrenotazioneDAO.getPrenotazioni_Utente(this.username);
		
		for(int i=0;i<p_list.size();i++) {
			
			MyDTO_Prenotazione dtop = new MyDTO_Prenotazione(p_list.get(i).getId_prenotazione(),p_list.get(i).getIdBB(),p_list.get(i).getUsername(),p_list.get(i).getCosto_totale(),p_list.get(i).getData_prenotazione());
			list_dto_p.add(dtop);
		}
		
		return list_dto_p;
	}
	
	public boolean verificaDatePrenotazione(int id_prenotazione) {
		
		boolean value = false;

		PrenotazioneDAO pdao = new PrenotazioneDAO(id_prenotazione);
        
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //  data corrente
        LocalDate currentDate = LocalDate.now();
        LocalDate dataprenotazione = LocalDate.parse(pdao.getData_prenotazione(), formatter);

        // differenza in giorni tra la data corrente e la data di ingresso
        long daysBetween = ChronoUnit.DAYS.between(currentDate, dataprenotazione);

        // Verifica se la differenza è minore o uguale a due giorni
        if (daysBetween <= 2 && daysBetween >= 0) {
            value = true;
        }

		return value;	
	}

	
	//restituisce la lista dei soli B&B in cui l'utente ha pernottato, in modo da poter poi lasciare una valutazione
	public ArrayList<MyDTO_BB> getListaBBPernottati(){
		
		EntityCatena catena = EntityCatena.getInstance();
		
		// Prendo l'intera lista di BB della catena
		ArrayList<MyDTO_BB> bb_totali = catena.getListaBB();
		ArrayList<MyDTO_BB> bb_pernottati = new ArrayList<MyDTO_BB>();
		ArrayList<Integer> idBB_pernottati = new ArrayList<Integer>();
		
		// Prendo le prenotazioni effettuate dal cliente
		ArrayList<MyDTO_Prenotazione> prenotazioni = this.getListaPrenotazioniTotali();

		//Per ogni prenotazione vado a prendere una sola volta il BB presso il quale ho prenotato
		for(int i = 0;i<prenotazioni.size();i++) {
			boolean pernottamento_passato = false;
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate currentDate = LocalDate.now();
			String data = prenotazioni.get(i).getData_prenotazione();
			LocalDate data_prenotazione = LocalDate.parse(data, formatter);
			
			if(data_prenotazione.isBefore(currentDate)) {
				
				pernottamento_passato = true;
			}
			
			if(!idBB_pernottati.contains(prenotazioni.get(i).getIdBB())&&pernottamento_passato) {
				
				idBB_pernottati.add(prenotazioni.get(i).getIdBB());
			}
			
		}	
		// Tra tutti i BB prelevo solo quelli presso cui ho soggiornato e li inserisco in un'ulteriore lista		
		for(int i = 0;i<bb_totali.size();i++) {
			
			if(idBB_pernottati.contains(bb_totali.get(i).getId())) {
				
				bb_pernottati.add(bb_totali.get(i));
			}
		}
		
		// Restituisco una lista di oggetti DTO contenente le informazioni sui BB presso cui ho prenotato	
		return bb_pernottati;	
	}
	
	//metodo per vedere se l'utente ha già valutato quella struttura	
	public int verificaEsistenzaValutazione(int idBB) {
		
		ValutazioneDAO valutazione = new ValutazioneDAO(this.username,idBB);
		
		return valutazione.voto;
	}
	
	//metodo per inserire la valutazione o aggiornarla nel caso già esista
	public int inserisciValutazione(EntityBB bb, int voto, boolean aggiorna) {
		
		EntityValutazione valutazione = new EntityValutazione();
		
		valutazione.associaBB(bb);
		valutazione.associaCliente(this);
		valutazione.setVoto(voto);
		this.addLinkToValutazione(valutazione);
		bb.associaValutazione(valutazione);
		
		int ret;
		
		if(!aggiorna) {
			
			ret = valutazione.salvaInDB();
		}
		else {
			
			ret = valutazione.aggiornaValutazioneInDB();
		}
				
		return ret;
	}
	
}
