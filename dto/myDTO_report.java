
	package dto;

	import java.util.ArrayList;

	public class myDTO_report {
		private String bestBB;
		private float incassi_tot;
		private ArrayList<MyDTO_BB> bb;
		
		//costruttore
		public myDTO_report(String bestBB, float incassi_tot, ArrayList<MyDTO_BB> bb) {
			super();
			this.bestBB = bestBB;
			this.incassi_tot = incassi_tot;
			this.bb = bb;
		}
		
		//getters & setters
		public String getBestBB() {
			return bestBB;
		}
		
		public void setBestBB(String bestBB) {
			this.bestBB = bestBB;
		}
		
		public float getIncassi_tot() {
			return incassi_tot;
		}
		
		public void setIncassi_tot(float incassi_tot) {
			this.incassi_tot = incassi_tot;
		}
		
		public ArrayList<MyDTO_BB> getBb() {
			return bb;
		}
		
		public void setBb(ArrayList<MyDTO_BB> bb) {
			this.bb = bb;
		}
	}


