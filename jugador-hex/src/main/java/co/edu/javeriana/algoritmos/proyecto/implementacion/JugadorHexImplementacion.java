package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class JugadorHexImplementacion implements JugadorHex {

	private TableroImplementacion t;

	@Override
	public Jugada jugar(Tablero tablero, ColorJugador color) {
		/**
		 * AQUI VA LA LOGICA PARA ESCOGER LA MEJOR JUGADA
		 **/
		t = new TableroImplementacion();
		llenarTablero(tablero);

		return null;
	}

	void llenarTablero(Tablero tablero) {
		ColorJugador[][] tt = new ColorJugador[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tt[i][j] = tablero.casilla(i, j);
			}
		}
		t.setTableroHex(tt);
	}

	List<Pair> BuscarFichasTablero(ColorJugador[][] mat, ColorJugador color) {
		List<Pair> fichas = new ArrayList<Pair>();

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j] == color) {
					fichas.add(new Pair(i, j));
				}
			}
		}
		return fichas;
	}

	Pair PuentesInvisiblesPeligrosos(ColorJugador[][] mat, ColorJugador color, ColorJugador ColorEnemigo,
			List<Pair> fichasMias) {
		Pair casillaPeligro = new Pair(-1, -1);

		for (Pair fichaVoy : fichasMias) {

			/*---------------1--------------*/
			if (mat[fichaVoy.getFirst() - 2][fichaVoy.getSecond() - 1] == color) {
				if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo
						|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 1] == ColorEnemigo) {

					return (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo)
							? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond() - 1)
							: new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond());
				}
			}
			/*---------------2--------------*/
			if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 2] == color) {
				if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != null
						&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != color
						|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] != null
								&& mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != null
							&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != color)
									? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond() + 1)
									: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() + 1);
				}
			}

			/*---------------3--------------*/
			if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 1] == color) {
				if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != null
						&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != color
						|| mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] != null
								&& mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != null
							&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != color)
									? new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond())
									: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() + 1);
				}
			}

			/*---------------4--------------*/
			if (mat[fichaVoy.getFirst() + 2][fichaVoy.getSecond() - 1] == color) {
				if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] != null
						&& mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] != color
						|| mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != null
								&& mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != color) {
					return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] != null
							&& mat[fichaVoy.getFirst()][fichaVoy.getSecond()] != color)
									? new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond() - 1)
									: new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond());
				}
			}

			/*---------------5--------------*/
			if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 2] == color) {
				if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != null
						&& mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != color
						|| mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != null
								&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != color) {
					return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != null
							&& mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != color)
									? new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() - 1)
									: new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond() - 1);
				}
			}

			/*---------------6--------------*/
			if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 1] == color) {
				if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != null
						&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != color
						|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] != null
								&& mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != null
							&& mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] != color)
									? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond())
									: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() - 1);
				}
			}

		}

		return casillaPeligro;
	}

	Pair calcularGrafoTentativo(Pair fichaDerecha, String sentido, ColorJugador[][] mat, ColorJugador color) {
		boolean[][] casillasVisitadas = new boolean[11][11];
		for (int i = 0; i < casillasVisitadas.length; i++) {
			for (int j = 0; j < casillasVisitadas[i].length; j++) {
				casillasVisitadas[i][j] = false;
			}
		}
		Pair casillaRespuesta = new Pair(0, 0);
		rutaMasCorta(fichaDerecha, casillasVisitadas, color, mat, sentido, casillaRespuesta);
		return casillaRespuesta;
	}

	int rutaMasCorta(Pair ficha, boolean[][] casillasVisitadas, ColorJugador color, ColorJugador[][] tablero,
			String sentido, Pair casillaRespuesta) {

		int ruta1 = 0, ruta2 = 0, ruta3 = 0;
		if (ficha.getSecond() == 11) // evalua si la ficha esta en la ultima columna
			return 1;
		// evalua si la ficha esta en la segunda columna y las dos casillas intermedias
		// estan disponibles
		if (ficha.getSecond() == 10 && tablero[ficha.getFirst()][ficha.getSecond() + 1] == null
				&& tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null)
			return 1;
		// evalua que la casilla este dentro de la matriz
		if (ficha.getSecond() > 11 || ficha.getSecond() < 1 || ficha.getFirst() < 1 || ficha.getFirst() > 11)
			return 0;
		// si la casilla ya fue visitada no se tiene en cuenta
		if (casillasVisitadas[ficha.getFirst()][ficha.getSecond()] == true)
			return 0;

		// Se evalua los 3 posibles caminos hacia la derecha de la ficha
		if (sentido == "Derecha") {
			if (tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] != null
					&& tablero[ficha.getFirst() + 1][ficha.getSecond() + 2] != null
					&& tablero[ficha.getFirst() + 2][ficha.getSecond() + 1] != null)
				return 0;
			// ------ Ruta al 6 -------
			if (tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null
					&& (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
							&& tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null)) {
				casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() - 1] = true;
				ruta1 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() - 1), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1; // ATento al +1
			}
			// ------ Ruta al 5 -------
			if (tablero[ficha.getFirst() + 1][ficha.getSecond() + 2] == null
					&& (tablero[ficha.getFirst()][ficha.getSecond() + 1] == null
							&& tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null)) {
				casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() + 2] = true;
				ruta2 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() + 2), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1;
			}
			// ------ Ruta al 4 -------
			if (tablero[ficha.getFirst() + 2][ficha.getSecond() + 1] == null
					&& (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
							&& tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null)) {
				casillasVisitadas[ficha.getFirst() + 2][ficha.getSecond() + 1] = true;
				ruta3 += rutaMasCorta(new Pair(ficha.getFirst() + 2, ficha.getSecond() + 1), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1;
			}
			// Escoger el mas corto 'el que tenga menos saltos'
			if (ruta1 < ruta2 && ruta1 < ruta3)
				casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() + 1);
			if (ruta2 < ruta1 && ruta2 < ruta3)
				casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() + 2);
			if (ruta3 < ruta2 && ruta3 < ruta1)
				casillaRespuesta = new Pair(ficha.getFirst() + 2, ficha.getSecond() + 1);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (sentido == "Izquierda") // Se evalua los 3 posibles caminos hacia la izquierda de la ficha
		{
			if (tablero[ficha.getFirst() - 2][ficha.getSecond() - 1] != null
					&& tablero[ficha.getFirst() - 1][ficha.getSecond() - 2] != null
					&& tablero[ficha.getFirst() + 1][ficha.getSecond() - 1] != null)
				return 0;
			// ------ Ruta al 1 -------
			if (tablero[ficha.getFirst() - 2][ficha.getSecond() - 1] == null
					&& (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
							&& tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null)) {
				casillasVisitadas[ficha.getFirst() - 2][ficha.getSecond() - 1] = true;
				ruta1 += rutaMasCorta(new Pair(ficha.getFirst() - 2, ficha.getSecond() - 1), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1; // ATento al +1
			}
			// ------ Ruta al 2 -------
			if (tablero[ficha.getFirst() - 1][ficha.getSecond() - 2] == null
					&& (tablero[ficha.getFirst()][ficha.getSecond() - 1] == null
							&& tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null)) {
				casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() - 2] = true;
				ruta2 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() - 2), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1;
			}
			// ------ Ruta al 3 -------
			if (tablero[ficha.getFirst() + 1][ficha.getSecond() - 1] == null
					&& (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
							&& tablero[ficha.getFirst()][ficha.getSecond() - 1] == null)) {
				casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() - 1] = true;
				ruta3 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1), casillasVisitadas, color,
						tablero, sentido, casillaRespuesta) + 1;
			}
			// Escoger el mas corto 'el que tenga menos saltos'

			if (ruta1 < ruta2 && ruta1 < ruta3)
				casillaRespuesta = new Pair(ficha.getFirst() - 2, ficha.getSecond() - 1);
			if (ruta2 < ruta1 && ruta2 < ruta3)
				casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() - 2);
			if (ruta3 < ruta2 && ruta3 < ruta1)
				casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1);
		}

	}

	@Override
	public String nombreJugador() {
		return "Amanda, William y Diego";
	}

}
