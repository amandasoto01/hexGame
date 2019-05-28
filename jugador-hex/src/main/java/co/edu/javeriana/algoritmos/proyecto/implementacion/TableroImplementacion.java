package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class TableroImplementacion implements Tablero {

	private ColorJugador[][] tableroHex;

	private UtilsImplementacion utilsImplementacion;

	public TableroImplementacion() {
		super();
		this.tableroHex = new ColorJugador[11][11];
		this.utilsImplementacion = new UtilsImplementacion();
	}

	public TableroImplementacion(ColorJugador[][] tableroHex) {
		super();
		this.utilsImplementacion = new UtilsImplementacion();
		this.tableroHex = tableroHex;
	}

	@Override
	public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
		if (jugada.isCambioColores()) {
			/**
			 * Solo se hace un cambio de colores de los jugadores pero el tablero se
			 * mantiene igual
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
		/**
		 * Realizar BFS desde la columna 0 (para todas las que sean blancas) y ver si en
		 * algun momento se llega a la columna 10 en ese caso el ganador es el color
		 * BLANCO
		 * 
		 * Realizar BFS desde la fila 0 (para todas las que sean negras) y ver si en
		 * algun momento se llega a la fila 10 en ese caso el ganador es el color NEGRO
		 **/

		/** COLOR BLANCO **/
		//System.out.println("COLOR BLANCO");
		Queue<Pair> cola = new LinkedList<>();
		boolean visitados[][] = new boolean[tableroHex.length][tableroHex.length];
		for (int i = 0; i < tableroHex.length; i++) {
			if (tableroHex[i][0] != null && tableroHex[i][0].equals(ColorJugador.BLANCO)) {
				cola.add(new Pair(i, 0));
				//System.out.println("Añadir: " + i + ",0");
				visitados[i][0] = true;
			}
		}
		while (!cola.isEmpty()) {
			Pair tope = cola.poll();
			visitados[tope.getFirst()][tope.getSecond()] = true;
			//System.out.println("Tope de la cola: " + tope.getFirst() + "," + tope.getSecond());

			if (tope.getSecond() == 10) {
				return ColorJugador.BLANCO;
			}

			for (int i = 0; i < utilsImplementacion.numMovimientos; i++) {
				if (utilsImplementacion.posicionValida(tope.getFirst() + utilsImplementacion.movimientosX[i],
						tope.getSecond() + utilsImplementacion.movimientosY[i])
						&& tableroHex[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
								+ utilsImplementacion.movimientosY[i]] != null) {
					if (tableroHex[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
							+ utilsImplementacion.movimientosY[i]] == ColorJugador.BLANCO
							&& visitados[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
									+ utilsImplementacion.movimientosY[i]] == false) {
						cola.add(new Pair(tope.getFirst() + utilsImplementacion.movimientosX[i],
								tope.getSecond() + utilsImplementacion.movimientosY[i]));
					}
				}
			}
		}

		/** COLOR NEGRO **/
		//System.out.println("COLOR NEGRO");
		cola = new LinkedList<>();
		visitados = new boolean[tableroHex.length][tableroHex.length];
		for (int i = 0; i < tableroHex.length; i++) {
			if (tableroHex[0][i] != null && tableroHex[0][i].equals(ColorJugador.NEGRO)) {
				cola.add(new Pair(0, i));
				visitados[0][i] = true;
			}
		}
		while (!cola.isEmpty()) {
			Pair tope = cola.poll();
			visitados[tope.getFirst()][tope.getSecond()] = true;
			if (tope.getFirst() == 10) {
				return ColorJugador.NEGRO;
			}

			for (int i = 0; i < utilsImplementacion.numMovimientos; i++) {
				if (utilsImplementacion.posicionValida(tope.getFirst() + utilsImplementacion.movimientosX[i],
						tope.getSecond() + utilsImplementacion.movimientosY[i])
						&& tableroHex[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
								+ utilsImplementacion.movimientosY[i]] != null) {
					if (tableroHex[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
							+ utilsImplementacion.movimientosY[i]] == ColorJugador.BLANCO
							&& visitados[tope.getFirst() + utilsImplementacion.movimientosX[i]][tope.getSecond()
									+ utilsImplementacion.movimientosY[i]] == false) {
						cola.add(new Pair(tope.getFirst() + utilsImplementacion.movimientosX[i],
								tope.getSecond() + utilsImplementacion.movimientosY[i]));
					}
				}
			}
		}

		/** NO HAY GANADOR **/
		return null;
	}

	@Override
	public ColorJugador casilla(int fila, int columna) {
		if (fila < 0 || fila >= 11)
			return null;
		if (columna < 0 || columna >= 11)
			return null;
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
		for (int i = 0; i < tableroHex.length; i++) {
			for (int j = 0; j < tableroHex.length; j++) {
				sb.append((tableroHex[i][j]) + " ");
				if (tableroHex[i][j] == null) {
					sb.append("  ");
				}
				if (tableroHex[i][j] != null && tableroHex[i][j] == ColorJugador.NEGRO) {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		return "TableroImplementacion [tableroHex=\n" + sb.toString() + "]";
	}

}
