package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.Arrays;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class TableroImplementacion implements Tablero{
	
	private ColorJugador[][] tableroHex;
	
	public TableroImplementacion() {
		super();
		this.tableroHex = new ColorJugador[11][11];
	}

	public TableroImplementacion(ColorJugador[][] tableroHex) {
		super();
		this.tableroHex = tableroHex;
	}

	@Override
	public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
		if(jugada.isCambioColores()) {
			/**Solo se hace un cambio de colores de los jugadores pero el tablero
			 * se mantiene igual
			 **/
		} else {
			/**
			 * Asignarle el color a la casilla dada por el jugador
			 **/
			this.tableroHex[jugada.getFila()][jugada.getColumna()] = colorJugador;
		}
		return;
	}

	@Override
	public ColorJugador ganador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColorJugador casilla(int fila, int columna) {
		if(fila < 0 || fila >= 11) return null;
		if(columna < 0 || columna >= 11) return null;
		return this.tableroHex[fila][columna];
	}

	public ColorJugador[][] getTableroHex() {
		return tableroHex;
	}

	public void setTableroHex(ColorJugador[][] tableroHex) {
		this.tableroHex = tableroHex;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<tableroHex.length; i++) {
			for(int j = 0; j<tableroHex.length; j++) {
				sb.append((tableroHex[i][j]) + " ");
			}
			sb.append("\n");
		}
		return "TableroImplementacion [tableroHex=\n" +
				sb.toString()
				+ "]";
	}
	
	

}
