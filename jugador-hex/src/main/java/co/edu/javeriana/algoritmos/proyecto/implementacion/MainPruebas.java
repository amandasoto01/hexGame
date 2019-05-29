package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.Scanner;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;

public class MainPruebas {

	public static void main(String[] args) {
		TableroImplementacion tableroPruebas = new TableroImplementacion();

		System.out.println("Nueva partida");
		System.out.println(tableroPruebas.toString());
		
		int cual=0;
		Scanner input = new Scanner(System.in);
		while(tableroPruebas.ganador()==null)
		{
			System.out.println(tableroPruebas.toString());
			if (cual%2==0) {
				System.out.println("JUGAROS 1");
				System.out.print("Fila:");
				int fil = input.nextInt();
				System.out.print("Colu:");
				int col = input.nextInt();
				Jugada temp = new Jugada(false, fil, col);
				tableroPruebas.aplicarJugada(temp, ColorJugador.BLANCO);
				
				
			}
			else
			{
				System.out.println("JUGAROS 2");
				System.out.print("Fila:");
				int fil = input.nextInt();
				System.out.print("Colu:");
				int col = input.nextInt();
				Jugada temp = new Jugada(false, fil, col);
				tableroPruebas.aplicarJugada(temp, ColorJugador.NEGRO);
			}
			cual++;
		}
		
		
		/*
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
*/
		System.out.println("El tablero actual es : " + tableroPruebas.toString());
		System.out.println("El ganador es");
		System.out.println(tableroPruebas.ganador());
	}

}
