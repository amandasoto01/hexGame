package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class JugadorHexImplementacion implements JugadorHex{
	
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
	
	
	void llenarTablero (Tablero tablero) {
		ColorJugador[][] tt = new ColorJugador[11][11];
		for(int i=0; i<11 ; i++) {
			for(int j=0; j<11; j++) {
				tt[i][j] = tablero.casilla(i, j);
			}
		}
		t.setTableroHex(tt);
	}
	
	
	
	List<Pair> BuscarFichasTablero (ColorJugador[][] mat, ColorJugador color){
		List<Pair> fichas = new ArrayList<Pair>();
		
		for(int i=0; i< mat.length; i++) {
			for(int j=0; j<mat.length; j++) {		
				if(mat[i][j] ==  color) {
					fichas.add(new Pair(i,j));
				}
			}
		}
		return fichas;	
	}
	
	Pair PuentesInvisiblesPeligrosos(ColorJugador[][] mat, ColorJugador color, List<Pair> fichasMias) {
		Pair casillaPeligro = new Pair(-1,-1);
		
		for(Pair fichaVoy: fichasMias) {
			
			/*---------------1--------------*/
			if(mat[fichaVoy.getFirst()-2][fichaVoy.getSecond()+1] == color) {
				if(mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()] != null  && mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()] != color 
				  ||  mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()+1] != null && mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()+1] != color) {
					return (mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()]!= null && 
							mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()]!= color) ? 
									new Pair(fichaVoy.getFirst()-1,fichaVoy.getSecond()+1):
									new Pair(fichaVoy.getFirst()-1,fichaVoy.getSecond());		
				}
			}
			
			
			/*---------------2--------------*/
			if(mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()+2] == color) {
				if(mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1] != null  && mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1] != color 
				  ||  mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()+1] != null && mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()+1] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1]!= null && 
							mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1]!= color) ? 
									new Pair(fichaVoy.getFirst()-1,fichaVoy.getSecond()+1):
									new Pair(fichaVoy.getFirst(),fichaVoy.getSecond()+1);
				}
			}
			
			
		    /*---------------3--------------*/
			if(mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()+1] == color) {
				if(mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1] != null  && mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1] != color 
				  ||  mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()] != null && mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1]!= null && 
							mat[fichaVoy.getFirst()][fichaVoy.getSecond()+1]!= color) ? 
									new Pair(fichaVoy.getFirst()+1,fichaVoy.getSecond()):
									new Pair(fichaVoy.getFirst(),fichaVoy.getSecond()+1);
				}
			}
			
			
			/*---------------4--------------*/
			if(mat[fichaVoy.getFirst()+2][fichaVoy.getSecond()-1] == color) {
				if(mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()] != null  && mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()] != color 
				  ||  mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1] != null && mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1] != color) {
					return (mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()]!= null && 
							mat[fichaVoy.getFirst()][fichaVoy.getSecond()]!= color) ? 
									new Pair(fichaVoy.getFirst()+1,fichaVoy.getSecond()-1):
									new Pair(fichaVoy.getFirst()+1,fichaVoy.getSecond());
				}
			}

			
			/*---------------5--------------*/
			if(mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-2] == color) {
				if(mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1] != null  && mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1] != color 
				  ||  mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1] != null && mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1] != color) {
					return (mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1]!= null && 
							mat[fichaVoy.getFirst()+1][fichaVoy.getSecond()-1]!= color) ? 
									new Pair(fichaVoy.getFirst(),fichaVoy.getSecond()-1):
									new Pair(fichaVoy.getFirst()+1,fichaVoy.getSecond()-1);
				}
			}
			
			
			/*---------------6--------------*/
			if(mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()-1] == color) {
				if(mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1] != null  && mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1] != color 
				  ||  mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()] != null && mat[fichaVoy.getFirst()-1][fichaVoy.getSecond()] != color) {
					return (mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1]!= null && 
							mat[fichaVoy.getFirst()][fichaVoy.getSecond()-1]!= color) ? 
									new Pair(fichaVoy.getFirst()-1,fichaVoy.getSecond()):
									new Pair(fichaVoy.getFirst(),fichaVoy.getSecond()-1);
				}
			}
			
		}

		return casillaPeligro;
	}
	
	
	Pair calcularGrafoTentativo(Pair fichaDerecha, String sentido, ColorJugador[][] mat, ColorJugador color) {
		boolean[][] casillasVisitadas= new boolean[11][11];
		return rutaMasCorta(fichaDerecha, casillasVisitadas, color, mat, sentido);
	}
	
	Pair rutaMasCorta(Pair ficha, boolean[][] casillasVisitadas, ColorJugador color, ColorJugador[][] tablero, String sentido) {
		Pair l = new Pair(-1,-1);
		
		return l;
		  
	}
	@Override
	public String nombreJugador() {
		return "Amanda, William y Diego";
	}

}
