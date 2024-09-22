package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import database.BBDAO;
import database.PrenotazioneDAO;
import database.ServizioDAO;
import dto.MyDTO_BB;
import dto.MyDTO_S;
import dto.myDTO_report;

public class EntityCatena {
	
	private static EntityCatena uniqueInstance;
	private ArrayList<EntityBB> bb_list ;
	private ArrayList<EntityServizio> servizi_list;
	
	private EntityCatena() {
		//carico una sola volta tutti i BB e tutti i servizi
		bb_list = new ArrayList<EntityBB>();
		servizi_list = new ArrayList<EntityServizio>();
		
		caricaListaBBDaDb();
		caricaListaServiziDaDB();

	}

	public static EntityCatena getInstance() {
	  
	  if(uniqueInstance == null) {
	   
		  uniqueInstance = new EntityCatena();
	  }
	  
	  	return uniqueInstance;
	 }
 
	public ArrayList<EntityBB> getLista_bb() {
		
		return bb_list;
	} 

	public void setLista_bb(ArrayList<EntityBB> lista_bb) {
		
		this.bb_list = lista_bb;
	}
	
	public ArrayList<MyDTO_BB> getListaBB(){
		
		ArrayList<MyDTO_BB> list_dto_bb = new ArrayList<MyDTO_BB>();
		
		
		for(int i=0;i<bb_list.size();i++) {
			
			ArrayList<MyDTO_S> list_dto_servizi = new ArrayList<MyDTO_S>();
			
			for(int j = 0;j<bb_list.get(i).getServizi().size();j++) {
								
				MyDTO_S dtos = new MyDTO_S(bb_list.get(i).getServizi().get(j).getTipo(),bb_list.get(i).getServizi().get(j).getCosto_giornaliero());
				list_dto_servizi.add(dtos);
			} 
			
			MyDTO_BB dtobb = new MyDTO_BB(bb_list.get(i).getNome(),bb_list.get(i).getPosti_letto(),bb_list.get(i).getLocalita(),bb_list.get(i).getMedia_recensioni(),bb_list.get(i).getCosto_giornaliero(),list_dto_servizi,bb_list.get(i).getId());

			list_dto_bb.add(dtobb);
		}
		
		return list_dto_bb;
	}

	//ottengo lista dto BB filtrati per localita
	public ArrayList<MyDTO_BB> getListaBBPerLocalita(String localita){
		
		ArrayList<MyDTO_BB> list_dto_bb = new ArrayList<MyDTO_BB>();
		
		for(int i=0;i<bb_list.size();i++) {
			if(bb_list.get(i).getLocalita().equals(localita)){
			
				ArrayList<MyDTO_S> list_dto_servizi = new ArrayList<MyDTO_S>();
				
				for(int j = 0;j<bb_list.get(i).getServizi().size();j++) {
					
					MyDTO_S dtos = new MyDTO_S(bb_list.get(i).getServizi().get(j).getTipo(),bb_list.get(i).getServizi().get(j).getCosto_giornaliero());	
					list_dto_servizi.add(dtos);
				}
				
				MyDTO_BB dtobb = new MyDTO_BB(bb_list.get(i).getNome(),bb_list.get(i).getPosti_letto(),bb_list.get(i).getLocalita(),bb_list.get(i).getMedia_recensioni(),bb_list.get(i).getCosto_giornaliero(),list_dto_servizi,bb_list.get(i).getId());
				
				list_dto_bb.add(dtobb);
			
			}
		}
		
		return list_dto_bb;
	}
	
	
	//ottengo lista dto BB filtrati per Data
	public ArrayList<MyDTO_BB> getListaBBPerData(String data){
		
		ArrayList<MyDTO_BB> list_dto_bb = new ArrayList<MyDTO_BB>();
		
		for(int i=0;i<bb_list.size();i++) {
			
			EntityBB bb = new EntityBB(bb_list.get(i).getId());
			
			if(bb.verificaDate(data)){
			
				ArrayList<MyDTO_S> list_dto_servizi = new ArrayList<MyDTO_S>();
				
				for(int j = 0;j<bb_list.get(i).getServizi().size();j++) {

					MyDTO_S dtos = new MyDTO_S(bb_list.get(i).getServizi().get(j).getTipo(),bb_list.get(i).getServizi().get(j).getCosto_giornaliero());
					
					list_dto_servizi.add(dtos);
				}
				
				MyDTO_BB dtobb = new MyDTO_BB(bb_list.get(i).getNome(),bb_list.get(i).getPosti_letto(),bb_list.get(i).getLocalita(),bb_list.get(i).getMedia_recensioni(),bb_list.get(i).getCosto_giornaliero(),list_dto_servizi,bb_list.get(i).getId());
				
				list_dto_bb.add(dtobb);
			}
		}
		
		return list_dto_bb;
	}
	
	public ArrayList<MyDTO_S> getListaServizi() {
		  
	  
		 ArrayList<MyDTO_S> list_dto_s = new ArrayList<MyDTO_S>();
		  
		  
		 for(int i=0; i<servizi_list.size(); i++) {
		   
			  MyDTO_S dtos = new MyDTO_S(servizi_list.get(i).getTipo(), servizi_list.get(i).getCosto_giornaliero(),servizi_list.get(i).getId());
			  list_dto_s.add(dtos);
		   
		  }

		  return list_dto_s;
	}
	
	public boolean aggiungiBB(ArrayList<Integer> lista, String nome, String localita, float costo_giornaliero,int posti_letto) {
			  
		 boolean aggiunto = false;
		 
		 EntityBB nuovoBB = new EntityBB(nome,posti_letto, localita, costo_giornaliero);

		  
		 for(int i=0; i<lista.size(); i++) {
			  EntityServizio nuovoServizio = new EntityServizio(lista.get(i));
			  nuovoBB.associaServizio(nuovoServizio);

		 }

		 aggiunto = nuovoBB.salva_su_DB();
		 
		  
		 return aggiunto;
}
	 
	public boolean aggiungiServizio(String tipo_servizio, float costo_servizio) {

		boolean aggiunto = false;
		
		EntityServizio nuovoServizio = new EntityServizio(tipo_servizio, costo_servizio);
		aggiunto = nuovoServizio.salvaSuDB();
		
		
		return aggiunto;
	}
	
	public boolean rimuoviBB(int idBB) {
		
		boolean rimosso = false;
		
		BBDAO bb = new BBDAO(idBB);
		rimosso = bb.rimuoviDaDB();
		
		return rimosso;
	}
	
	public myDTO_report generaReport(String mese) {
		   
		   float incassi_totali=0;
		   int max_prenotazioni=0;
		   String bestBB=null;
		   ArrayList<MyDTO_BB> bb_rece=new ArrayList<MyDTO_BB>();
		   myDTO_report report=new myDTO_report(null, 0, null);
		   
		  //itero su tutti i bb
		   ArrayList<BBDAO> bb_list = BBDAO.getListaBB();
		   
		   for(int i=0; i<bb_list.size(); i++) {
			   int prenotazioniBB=0;
			   ArrayList <PrenotazioneDAO> prenotazioni=PrenotazioneDAO.getPrenotazioni(bb_list.get(i).getId());
			    
			   for(int j=0; j<prenotazioni.size(); j++) {
				    if(prenotazioni.get(j).getData_prenotazione().contains(mese)) {
					     
					     prenotazioniBB++;
					     incassi_totali+=prenotazioni.get(j).getCosto_totale();
					     
					    
				    }
			   }
			   
			   
			   if(prenotazioniBB>0) {
				   
				   MyDTO_BB dto_bb=new MyDTO_BB();
				     dto_bb.setNome(bb_list.get(i).getNome());
				     dto_bb.setMedia_recensioni(bb_list.get(i).getMedia_recensioni());
				     bb_rece.add(dto_bb);
				     
				   if(prenotazioniBB>max_prenotazioni) {
					    max_prenotazioni=prenotazioniBB;
					    bestBB=bb_list.get(i).getNome();
				   }
			     
				     
			   }
			   
		   
		   }
		   report.setBb(bb_rece);
		   report.setBestBB(bestBB);
		   report.setIncassi_tot(incassi_totali);
		   
		   return report;
	}
	
	public boolean modificaBB(MyDTO_BB bb_selezionato) {
		
		boolean modificato = false;
		
		//la lista di DTO_S che contiene bb_selezionato non possiedono id
		ArrayList<ServizioDAO> servizi = new ArrayList<>();
		
		for(int i=0; i<bb_selezionato.getServizi().size();i++) {
			
			ServizioDAO servizio = new ServizioDAO(bb_selezionato.getServizi().get(i).getTipo(), bb_selezionato.getServizi().get(i).getCosto_giornaliero(), bb_selezionato.getServizi().get(i).getId());
			servizi.add(servizio);
		}
		
		BBDAO bb = new BBDAO(bb_selezionato.getNome(),bb_selezionato.getPosti_letto(), bb_selezionato.getLocalita(), bb_selezionato.getCosto_giornaliero(), bb_selezionato.getId()); 
		modificato = bb.updateBB(servizi);
		
		return modificato;
	}
	
	//metodo per caricare da DB la lista di tutti i BB (ENTITY)
	public void caricaListaBBDaDb() {
		
		ArrayList<EntityBB> bb_list_entity = new ArrayList<EntityBB>();
		
		ArrayList<BBDAO> lista_bb_dao = BBDAO.getListaBB();
		
		for(int i=0;i<lista_bb_dao.size();i++) {
			
			ArrayList<ServizioDAO> servizi_dao = new ArrayList<ServizioDAO>();
			servizi_dao = ServizioDAO.getServizi(lista_bb_dao.get(i).getId());
			
			ArrayList<EntityServizio> lista_entity_servizi = new ArrayList<EntityServizio>();
			
			for(int j = 0;j<servizi_dao.size();j++) {
								
				EntityServizio e_s = new EntityServizio(servizi_dao.get(j).getTipo(),servizi_dao.get(j).getCosto_giornaliero());
				lista_entity_servizi.add(e_s);
			} 
			
			EntityBB entity_bb = new EntityBB(lista_bb_dao.get(i).getId(),lista_bb_dao.get(i).getNome(),lista_bb_dao.get(i).getPosti_letto(),lista_bb_dao.get(i).getLocalita(),lista_bb_dao.get(i).getCosto_giornaliero(),lista_bb_dao.get(i).getMedia_recensioni(),lista_entity_servizi);
			bb_list_entity.add(entity_bb);
		}
		this.bb_list = bb_list_entity;
	}
	
	//metodo per caricare da DB la lista di tutti i servizi (ENTITY)
	public void caricaListaServiziDaDB() {
		
		ArrayList<EntityServizio> lista_entity_servizi = new ArrayList<EntityServizio>();
		
		ArrayList<ServizioDAO> lista_servizi = ServizioDAO.getListaServizi();
		
		for(int i=0; i<lista_servizi.size(); i++) {
			   
				EntityServizio e_s =  new EntityServizio(lista_servizi.get(i).getId(),lista_servizi.get(i).getTipo(),lista_servizi.get(i).getCosto_giornaliero());
				lista_entity_servizi.add(e_s);
		   
		  }	
		
		this.servizi_list = lista_entity_servizi;
		
	}
	
	

}
