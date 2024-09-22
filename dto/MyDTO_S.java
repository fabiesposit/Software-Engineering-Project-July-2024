package dto;

public class MyDTO_S {
		private String tipo;
		private float costo_giornaliero;
		private int id;

		//aggiornare
		public MyDTO_S(String tipo, float costo_giornaliero) {
			
			this.tipo = tipo;
			this.costo_giornaliero = costo_giornaliero;
		}
		
		public MyDTO_S(String tipo, float costo_giornaliero,int id) {
			
			this.tipo = tipo;
			this.costo_giornaliero = costo_giornaliero;
			this.id = id;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public float getCosto_giornaliero() {
			return costo_giornaliero;
		}

		public void setCosto_giornaliero(float costo_giornaliero) {
			this.costo_giornaliero = costo_giornaliero;
		}
	
		
}
