package co.edu.javeriana.algoritmos.proyecto.implementacion;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;

public class MainPruebas {

	public static void main(String[] args) {
		TableroImplementacion tableroPruebas = new TableroImplementacion();
		
		System.out.println("Nueva partida");
		System.out.println(tableroPruebas.toString());
		System.out.println("HOla");
		
		tableroPruebas.aplicarJugada(new Jugada(false, 5, 0), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 5, 1), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 6, 1), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 7, 1), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 7, 2), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 6, 3), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 5, 4), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 4, 5), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 3, 6), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 3, 7), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 3, 8), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 4, 8), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 5, 8), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 6, 8), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 6, 9), ColorJugador.BLANCO);
		tableroPruebas.aplicarJugada(new Jugada(false, 5, 10), ColorJugador.BLANCO);
		
		System.out.println("El tablero actual es : " +  tableroPruebas.toString());
		System.out.println("El ganador es");
		System.out.println(tableroPruebas.ganador());
	}

}
