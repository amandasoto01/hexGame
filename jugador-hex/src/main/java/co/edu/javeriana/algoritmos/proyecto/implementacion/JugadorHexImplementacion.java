package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.awt.Color;
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
		
		ColorJugador[][] mat = t.getTableroHex();
		ColorJugador colorOponente;
		List<Pair> fichasMias = BuscarFichasTablero (t.getTableroHex(),color);
		
		if(color == ColorJugador.BLANCO) {
			colorOponente = ColorJugador.NEGRO;
		}else {
			colorOponente = ColorJugador.BLANCO;
		}
	    

		List<Pair> fichasOponente = BuscarFichasTablero (t.getTableroHex(), colorOponente);
		
		int contFichasMias = fichasMias.size();
		int contFichasOponente = fichasOponente.size();
		

		/* -------------------------- PRIMERA RONDA -----------------------*/
		int mitad = t.getTableroHex().length/2;
		if  (contFichasMias == 0 && contFichasOponente == 0) {
			//la primera jugada, se hace la jugada en el centro del tablero
			return new Jugada(false, mitad, mitad-1);
		}else if (contFichasMias == 0 && contFichasOponente == 1) {
			//El Oponente empieza
			if(mat[fichasOponente.get(0).getFirst()][fichasOponente.get(0).getSecond()] 
				== mat[mitad][mitad] && color == ColorJugador.BLANCO) {
				//Hizo la jugada en el centro del tablero, cambio de color // SE ASUME QUE: EL NEGRO SIEMPRE COMIENZA
				return new Jugada(true, mitad, mitad);
			}else {
				//la casilla central esta libre, se hace la jugada
				return new Jugada(false, mitad, mitad);
			} 		
		    /* -------------------------- SEGUNDA RONDA ++ -----------------------*/ 
		}else if(contFichasMias == contFichasOponente) {
			if(contFichasMias > 1  && contFichasOponente >= 1 ) {
				Pair casilla = PuentesInvisiblesPeligrosos(mat, color, colorOponente	, fichasMias);
		        return new Jugada(false, casilla.getFirst(), casilla.getSecond());
			}
			
			
			 if(color == ColorJugador.NEGRO) {
				 if(mitad > fichasOponente.get(contFichasOponente - 1).getSecond()) {
					 int menorColumna = 11;
					 Pair fichaMasIzquierda = new Pair(-1,-1);
					 
					 for(Pair ficha: fichasMias) {
						 if(ficha.getSecond() < menorColumna)
	                        fichaMasIzquierda.setFirst(ficha.getFirst());
						 	fichaMasIzquierda.setSecond(ficha.getSecond());
							menorColumna = ficha.getSecond();
					 }
					 
					 Pair casilla = calcularGrafoTentativo ( fichaMasIzquierda, "Izquierda" , mat, color );
		             return new Jugada(false, casilla.getFirst(), casilla.getSecond());
				 }else {
					 int mayorColumna = -1;
					 Pair fichaMasDerecha = new Pair(-1,-1);    
					 
					 for(Pair ficha: fichasMias) {
							if(ficha.getFirst() > mayorColumna) {
								fichaMasDerecha.setFirst(ficha.getFirst());
								fichaMasDerecha.setSecond(ficha.getSecond());
								mayorColumna = ficha.getSecond();
							}
	                           	
					 }
					 
					//Calculo grafo tentativo de los caminos mas cercanos al borde
					Pair casilla = calcularGrafoTentativo ( fichaMasDerecha, "Derecha", mat, color );
		            return new Jugada(false, casilla.getFirst(), casilla.getSecond());
				 }
			 }
			 
			 
	          if(color == ColorJugador.BLANCO) {
	        	  if(mitad > fichasOponente.get(contFichasOponente - 1).getFirst()) {
	        		int menorFila = 11;
	  			  	Pair fichaMasArriba = new Pair(-1,-1);
	  			  	
		  			  for(Pair ficha: fichasMias) {
		  				if(ficha.getFirst() < menorFila) {
		  			     	fichaMasArriba.setFirst(ficha.getFirst());
		  			     	fichaMasArriba.setSecond(ficha.getSecond());
		  			     	menorFila = ficha.getFirst();
		  				}
	  			  }
	  			  Pair casilla = calcularGrafoTentativo ( fichaMasArriba, "Arriba", mat, color);
                  return new Jugada(false, casilla.getFirst(), casilla.getSecond());
	        	}else {
	        		int mayorFila = 11;
				  	Pair fichaMasAbajo = new Pair(-1,-1);
	        		
				  	for(Pair ficha: fichasMias) {
				  		if(ficha.getFirst() > mayorFila) {
				  			fichaMasAbajo=ficha;
							mayorFila = ficha.getFirst();
				  		}
				  	}
				  	
				  //Calculo grafo tentativo de los caminos mas cercanos al borde
				  	Pair casilla = calcularGrafoTentativo ( fichaMasAbajo, "Abajo", mat, color );
	                return new Jugada(false, casilla.getFirst(), casilla.getSecond());
	        	}
	          }
		}
		
		
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
			
			/**fil 0  (A) no puede calcular el 1,2,6 
		       col 0  (B) no puede calcular el 1,2,3 
		       col 10 (C) no puede calcular el 4,5,6 
		       fil 10 (D) no puede calcular el 3,4,5
		       fil 1  (E) no puede calcular el 1
		       col 1  (F) no puede calcular el 2
		       col 9  (G) no puede calcular el 5
		    	 fil 9  (H) no puede calcular el 4**/

			/*---------------1--------------*/
			if (fichaVoy.getFirst() != 0 || fichaVoy.getSecond() != 0 || fichaVoy.getFirst() != 1) {
				if (mat[fichaVoy.getFirst() - 2][fichaVoy.getSecond() - 1] == color) {
					if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo
							|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 1] == ColorEnemigo) {

						return (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo)
								? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond() - 1)
								: new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond());
					}
				}
			}

			/*---------------2--------------*/
			if (fichaVoy.getFirst() != 0 || fichaVoy.getSecond() != 0 || fichaVoy.getSecond() != 1) {
				if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 2] == color) {
					if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] == ColorEnemigo
							|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 1] == ColorEnemigo) {

						return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] == ColorEnemigo)
								? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond() + 1)
								: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() + 1);
					}
				}
			}

			/*---------------3--------------*/
			if (fichaVoy.getSecond() != 0 || fichaVoy.getFirst() != 10 ) {
				if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] == color) {
					if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] == ColorEnemigo
							|| mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] == ColorEnemigo) {

						return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() - 1] == ColorEnemigo)
								? new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond())
								: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() + 1);
					}
				}
			}

			/*---------------4--------------*/
			if (fichaVoy.getSecond() != 10 || fichaVoy.getFirst() != 10 || fichaVoy.getFirst()!=9) {
				if (fichaVoy.getFirst() != 11) {
					if (mat[fichaVoy.getFirst() + 2][fichaVoy.getSecond() + 1] == color) {
						if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] == ColorEnemigo
								|| mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 1] == ColorEnemigo) {

							return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()] == ColorEnemigo)
									? new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond() - 1)
									: new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond());
						}
					}
				}
			}

			/*---------------5--------------*/
			if (fichaVoy.getSecond() != 11 || fichaVoy.getSecond() != 10 || fichaVoy.getFirst()!=10 || fichaVoy.getSecond()!=9) {
				if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 2] == color) {
					if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 1] == ColorEnemigo
							|| mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] == ColorEnemigo) {

						return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 1] == ColorEnemigo)
								? new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() - 1)
								: new Pair(fichaVoy.getFirst() + 1, fichaVoy.getSecond() - 1);
					}
				}
			}

			/*---------------6--------------*/
			if (fichaVoy.getFirst() != 0 || fichaVoy.getSecond() != 10) {
				if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] == color) {
					if (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] == ColorEnemigo
							|| mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo) {

						return (mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] == ColorEnemigo)
								? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond())
								: new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() - 1);
					}
				}
			}
		}

		return casillaPeligro;
	}

	Pair calcularGrafoTentativo(Pair fichaDerecha, String sentido, ColorJugador[][] mat, ColorJugador color) {
		boolean[][] casillasVisitadas = new boolean[11][11];
		return rutaMasCorta(fichaDerecha, casillasVisitadas, color, mat, sentido);
	}

	Pair rutaMasCorta(Pair ficha, boolean[][] casillasVisitadas, ColorJugador color, ColorJugador[][] tablero,
			String sentido) {
		Pair l = new Pair(-1, -1);

		return l;

	}

	@Override
	public String nombreJugador() {
		return "Amanda, William y Diego";
	}

}
