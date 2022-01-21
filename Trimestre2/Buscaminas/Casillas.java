package Trimestre2.Buscaminas;

public class Casillas {

	private String estado;//Estados: vacio, mina, numero, descubierta
	private boolean descubierta;
	private boolean Bandera;
	private int numeroMinas;
	public Casillas() { 
		descubierta = false;
		Bandera = false;
		estado = "vacio";
	}
	
	public String devolverEstado() {
		
		
		return estado;
		
	}
	
	public void actualizarBandera(boolean estado) {
		
		Bandera = estado;
		
	}
	
	public boolean esBandera() {
		
		return Bandera;
	}
	
	public String devolverEmoteSegunEstado() {
		
		switch(estado) {
		
		case "vacio":
			return "⬛";
		case "mina":
			return  "💣";
		case "descubierta":
			return "🔲";
		case "numero":
			return "" + Integer.toString(numeroMinas) + " ";
		default:
			return "Error ocurred";
		}
		
	}
	
	public void ponerNumeroMinas(int numMinas) {
		numeroMinas = numMinas;
	}
	
	public boolean estaDescubierta() {
		return descubierta;
	}
	
	public void ponerEstado(String estado) {
		
		this.estado = estado;
		
	}
	
	public void descubrirCasilla() {
		this.descubierta = true;
		
	}
	
}
