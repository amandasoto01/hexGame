package co.edu.javeriana.algoritmos.proyecto.implementacion;

public class MainPruebas {

	public static void main(String[] args) {
		TableroImplementacion tableroPruebas = new TableroImplementacion();
		
		System.out.println("Nueva partida");
		System.out.println(tableroPruebas.toString());
		
		System.out.println("El ganador es");
		System.out.println(tableroPruebas.ganador());
	}

}
