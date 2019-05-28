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
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public ColorJugador ganador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColorJugador casilla(int fila, int columna) {
		// TODO Auto-generated method stub
		return null;
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
