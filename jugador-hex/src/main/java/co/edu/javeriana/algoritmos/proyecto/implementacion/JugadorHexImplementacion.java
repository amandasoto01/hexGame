
package co.edu.javeriana.algoritmos.proyecto.implementacion;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class JugadorHexImplementacion implements JugadorHex {

    private TableroImplementacion t;
    private Boolean Puedocambiar;
    private Pair ultimaJugada;

    private ColorJugador[][] tableroAnterior;

    public JugadorHexImplementacion() {
        super();
        Puedocambiar = true;
        tableroAnterior = new ColorJugador[11][11];
        ultimaJugada = new Pair(-1, -1);
    }

    @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        /**
         * AQUI VA LA LOGICA PARA ESCOGER LA MEJOR JUGADA
         **/
        t = new TableroImplementacion();
        llenarTablero(tablero);

        ColorJugador[][] mat = t.getTableroHex();
        ColorJugador colorOponente;
        List<Pair> fichasMias = BuscarFichasTablero(t.getTableroHex(), color);

        if (color == ColorJugador.BLANCO) {
            colorOponente = ColorJugador.NEGRO;
        } else {
            colorOponente = ColorJugador.BLANCO;
        }

        List<Pair> fichasOponente = BuscarFichasTablero(t.getTableroHex(), colorOponente);
        // Calcular la ultima ficha enemiga
        this.tableroAnterior = mat;

        int contFichasMias = fichasMias.size();
        int contFichasOponente = fichasOponente.size();

        /* -------------------------- INICIO PRIMERA RONDA ----------------------- */
        int mitad = t.getTableroHex().length / 2;

        /** Yo juego primero **/
        if (contFichasMias == 0 && contFichasOponente == 0) {
            // la primera jugada, se hace la jugada en el centro del tablero
            this.Puedocambiar = false;
            return new Jugada(false, mitad, mitad - 1);
        }

        /** Mi oponente jugó primero **/
        if (contFichasMias == 0 && contFichasOponente == 1) {
            // El Oponente empieza
            if (color == ColorJugador.BLANCO && Puedocambiar == true) {
                // Hizo la jugada en el centro del tablero, cambio de color // SE ASUME QUE: EL
                // NEGRO SIEMPRE COMIENZA
                Puedocambiar = false;
                return new Jugada(true, mitad, mitad);
            } else {
                // la casilla central esta libre, se hace la jugada
                if (mat[mitad][mitad] == null) {
                    return new Jugada(false, mitad, mitad);
                } else {
                    return new Jugada(false, mitad, mitad - 1);
                }
            }
        }
        /* -------------------------- FIN PRIMERA RONDA ----------------------- */

        /* -------------------------- INICIO SEGUNDA RONDA O MAS ----------------------- */

        /**
         * Primero reviso que no haya algun puente peligroso que pueda hacer el rival Si el rival
         * tiene un puente peligroso entonces bloquearselo
         **/
        Pair casillaPeligrosa = PuentesInvisiblesPeligrosos(mat, color, colorOponente, fichasMias);
        if (casillaPeligrosa.getFirst() != -1 && casillaPeligrosa.getSecond() != -1) {
            return new Jugada(false, casillaPeligrosa.getFirst(), casillaPeligrosa.getSecond());
        }

        /**
         * Ahora si no hay puentes peligrosos miro dependiendo del color cual es la casilla mas
         * optima en la que puedo realizar una jugada
         **/
        if (color == ColorJugador.NEGRO) {
            if (mitad > fichasOponente.get(contFichasOponente - 1).getSecond()) {
                int menorColumna = 11;
                Pair fichaMasIzquierda = new Pair(-1, -1);

                for (Pair ficha : fichasMias) {
                    if (ficha.getSecond() < menorColumna)
                        fichaMasIzquierda.setFirst(ficha.getFirst());
                    fichaMasIzquierda.setSecond(ficha.getSecond());
                    menorColumna = ficha.getSecond();
                }

                Pair casilla = calcularGrafoTentativo(fichaMasIzquierda, "Izquierda", mat, color);
                return new Jugada(false, casilla.getFirst(), casilla.getSecond());
            } else {
                int mayorColumna = -1;
                Pair fichaMasDerecha = new Pair(-1, -1);

                for (Pair ficha : fichasMias) {
                    if (ficha.getFirst() > mayorColumna) {
                        fichaMasDerecha.setFirst(ficha.getFirst());
                        fichaMasDerecha.setSecond(ficha.getSecond());
                        mayorColumna = ficha.getSecond();
                    }

                }

                // Calculo grafo tentativo de los caminos mas cercanos al borde
                Pair casilla = calcularGrafoTentativo(fichaMasDerecha, "Derecha", mat, color);
                return new Jugada(false, casilla.getFirst(), casilla.getSecond());
            }
        }

        /**
         * Ahora si no hay puentes peligrosos miro dependiendo del color cual es la casilla mas
         * optima en la que puedo realizar una jugada
         **/
        if (color == ColorJugador.BLANCO) {
            if (mitad > fichasOponente.get(contFichasOponente - 1).getFirst()) {
                int menorFila = 11;
                Pair fichaMasArriba = new Pair(-1, -1);

                for (Pair ficha : fichasMias) {
                    if (ficha.getFirst() < menorFila) {
                        fichaMasArriba.setFirst(ficha.getFirst());
                        fichaMasArriba.setSecond(ficha.getSecond());
                        menorFila = ficha.getFirst();
                    }
                }
                Pair casilla = calcularGrafoTentativo(fichaMasArriba, "Arriba", mat, color);
                return new Jugada(false, casilla.getFirst(), casilla.getSecond());
            } else {
                int mayorFila = 11;
                Pair fichaMasAbajo = new Pair(-1, -1);

                for (Pair ficha : fichasMias) {
                    if (ficha.getFirst() > mayorFila) {
                        fichaMasAbajo = ficha;
                        mayorFila = ficha.getFirst();
                    }
                }

                // Calculo grafo tentativo de los caminos mas cercanos al borde
                Pair casilla = calcularGrafoTentativo(fichaMasAbajo, "Abajo", mat, color);
                return new Jugada(false, casilla.getFirst(), casilla.getSecond());
            }
        }
        /* -------------------------- FIN SEGUNDA RONDA O MAS ----------------------- */

        /** En caso de no encontrar una jugada **/
        Pair jugadaFinal = casillaVacia();
        return new Jugada(false, jugadaFinal.getFirst(), jugadaFinal.getSecond());
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

                if (tableroAnterior[i][j] != mat[i][j] && mat[i][j] != null && mat[i][j] != color) {
                    this.ultimaJugada = new Pair(i, j);
                }
            }
        }
        return fichas;
    }

    Pair PuentesInvisiblesPeligrosos(ColorJugador[][] mat, ColorJugador color,
            ColorJugador ColorEnemigo, List<Pair> fichasMias) {
        Pair casillaPeligro = new Pair(-1, -1);

        for (Pair fichaVoy : fichasMias) {

            /*
             * fil 0 (A) no puede calcular el 1,2,6 col 0 (B) no puede calcular el 6,5,4 col 10 (C)
             * no puede calcular el 1,2,3 fil 10 (D) no puede calcular el 3,4,5 fil 1 (E) no puede
             * calcular el 1 col 1 (F) no puede calcular el 5 col 9 (G) no puede calcular el 2 fil 9
             * (H) no puede calcular el 4
             */
            /*---------------1--------------*/
            if (fichaVoy.getFirst() != 0 && fichaVoy.getSecond() != 10
                    && fichaVoy.getFirst() != 1) {
                if (mat[fichaVoy.getFirst() - 2][fichaVoy.getSecond() + 1] == color) {

                    if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] != null
                            && mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == color
                            || mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo
                            || mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()
                                    + 1] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()] == ColorEnemigo)
                                ? new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond() + 1)
                                : new Pair(fichaVoy.getFirst() - 1, fichaVoy.getSecond());
                    }
                }
            }

            /*---------------2--------------*/
            if (fichaVoy.getFirst() != 0 && fichaVoy.getSecond() != 10
                    && fichaVoy.getSecond() != 9) {
                if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 2] == color) {

                    if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] != null
                            && mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 1] == color
                            || mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()
                            + 1] == ColorEnemigo
                            || mat[fichaVoy.getFirst()][fichaVoy.getSecond() + 1] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()
                                + 1] == ColorEnemigo)
                                        ? new Pair(fichaVoy.getFirst(), fichaVoy.getSecond() + 1)
                                        : new Pair(fichaVoy.getFirst() - 1,
                                                fichaVoy.getSecond() + 1);
                    }
                }
            }

            /*---------------3--------------*/
            if (fichaVoy.getSecond() != 10 && fichaVoy.getFirst() != 10) {
                if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 1] == color) {

                    if (mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() + 1] != null
                            && mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 0] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() + 1] == color
                            || mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 0] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond()
                            + 1] == ColorEnemigo
                            || mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                                    + 0] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond()
                                + 1] == ColorEnemigo)
                                        ? new Pair(fichaVoy.getFirst() + 1,
                                                fichaVoy.getSecond() + 0)
                                        : new Pair(fichaVoy.getFirst() + 0,
                                                fichaVoy.getSecond() + 1);
                    }
                }
            }

            /*---------------4--------------*/
            if (fichaVoy.getSecond() != 0 && fichaVoy.getFirst() != 10
                    || fichaVoy.getFirst() != 9) {
                if (mat[fichaVoy.getFirst() + 2][fichaVoy.getSecond() - 1] == color) {

                    if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != null
                            && mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 0] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] == color
                            || mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() + 0] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                            - 1] == ColorEnemigo
                            || mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                                    + 0] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                                - 1] == ColorEnemigo)
                                        ? new Pair(fichaVoy.getFirst() + 1,
                                                fichaVoy.getSecond() + 0)
                                        : new Pair(fichaVoy.getFirst() + 1,
                                                fichaVoy.getSecond() - 1);
                    }
                }
            }

            /*---------------5--------------*/
            if (fichaVoy.getSecond() != 0 && fichaVoy.getSecond() != 1
                    || fichaVoy.getFirst() != 10) {
                if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 2] == color) {

                    if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] != null
                            && mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() - 1] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond() - 1] == color
                            || mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() - 1] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                            - 1] == ColorEnemigo
                            || mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond()
                                    - 1] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() + 1][fichaVoy.getSecond()
                                - 1] == ColorEnemigo)
                                        ? new Pair(fichaVoy.getFirst() + 0,
                                                fichaVoy.getSecond() - 1)
                                        : new Pair(fichaVoy.getFirst() + 1,
                                                fichaVoy.getSecond() - 1);
                    }
                }
            }

            /*---------------6--------------*/
            if (fichaVoy.getFirst() != 0 && fichaVoy.getSecond() != 10) {
                if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() - 1] == color) {

                    if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 0] != null
                            && mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() - 1] != null) {
                        // No hacer nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond() + 0] == color
                            || mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond() - 1] == color) {
                        // No hago nada
                    } else if (mat[fichaVoy.getFirst() - 1][fichaVoy.getSecond()
                            + 0] == ColorEnemigo
                            || mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond()
                                    - 1] == ColorEnemigo) {
                        return (mat[fichaVoy.getFirst() + 0][fichaVoy.getSecond()
                                - 1] == ColorEnemigo)
                                        ? new Pair(fichaVoy.getFirst() - 1,
                                                fichaVoy.getSecond() + 0)
                                        : new Pair(fichaVoy.getFirst() + 0,
                                                fichaVoy.getSecond() - 1);
                    }
                }
            }
        }
        return casillaPeligro;
    }

    Pair calcularGrafoTentativo(Pair fichaDerecha, String sentido, ColorJugador[][] mat,
            ColorJugador color) {
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

    int rutaMasCorta(Pair ficha, boolean[][] casillasVisitadas, ColorJugador color,
            ColorJugador[][] tablero, String sentido, Pair casillaRespuesta) {

        int ruta1 = 0, ruta2 = 0, ruta3 = 0;
        if (ficha.getSecond() == 10) { // evalua si la ficha esta en la ultima columna
            return 1;
        }
        // evalua si la ficha esta en la segunda columna y las dos casillas intermedias
        // estan disponibles
        if (ficha.getSecond() == 9 && tablero[ficha.getFirst()][ficha.getSecond() + 1] == null
                && tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null) {
            return 1;
        }
        // evalua que la casilla este dentro de la matriz
        if (ficha.getSecond() > 10 || ficha.getSecond() < 0 || ficha.getFirst() < 0
                || ficha.getFirst() > 10) {
            return 0;
        }
        // si la casilla ya fue visitada no se tiene en cuenta
        if (casillasVisitadas[ficha.getFirst()][ficha.getSecond()] == true) {
            return 99;
        }
        // Se evalua los 3 posibles caminos hacia la derecha de la ficha
        if (sentido == "Derecha") {
            if (tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] != null
                    && tablero[ficha.getFirst() + 1][ficha.getSecond() - 2] != null
                    && tablero[ficha.getFirst() + 2][ficha.getSecond() - 1] != null) {
                return 99;
            }
            // ------ Ruta al 6 -------
            if (tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null
                    && (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst()][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() - 1] = true;
                ruta1 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() - 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1; // ATento
                // al +1
            }
            // ------ Ruta al 5 -------
            if (tablero[ficha.getFirst() + 1][ficha.getSecond() - 2] == null
                    && (tablero[ficha.getFirst()][ficha.getSecond() - 1] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() - 2] = true;
                ruta2 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() - 2),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // ------ Ruta al 4 -------
            if (tablero[ficha.getFirst() + 2][ficha.getSecond() - 1] == null
                    && (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst() + 1][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 2][ficha.getSecond() - 1] = true;
                ruta3 += rutaMasCorta(new Pair(ficha.getFirst() + 2, ficha.getSecond() - 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // Escoger el mas corto 'el que tenga menos saltos'
            // TO DO: quitar las negativas
            if (ruta1 < ruta2 && ruta1 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() - 1);
            }
            if (ruta2 < ruta1 && ruta2 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() - 2);
            }
            if (ruta3 < ruta2 && ruta3 < ruta1) {
                casillaRespuesta = new Pair(ficha.getFirst() + 2, ficha.getSecond() - 1);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (sentido == "Izquierda") // Se evalua los 3 posibles caminos hacia la izquierda de la
        // ficha
        {
            if (tablero[ficha.getFirst() - 2][ficha.getSecond() + 1] != null
                    && tablero[ficha.getFirst() - 1][ficha.getSecond() + 2] != null
                    && tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] != null) {
                return 99;
            }
            // ------ Ruta al 1 -------
            if (tablero[ficha.getFirst() - 2][ficha.getSecond() + 1] == null
                    && (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 2][ficha.getSecond() + 1] = true;
                ruta1 += rutaMasCorta(new Pair(ficha.getFirst() - 2, ficha.getSecond() + 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1; // ATento
                // al +1
            }
            // ------ Ruta al 2 -------
            if (tablero[ficha.getFirst() - 1][ficha.getSecond() + 2] == null
                    && (tablero[ficha.getFirst()][ficha.getSecond() + 1] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() + 2] = true;
                ruta2 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() + 2),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // ------ Ruta al 3 -------
            if (tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null
                    && (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst()][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() + 1] = true;
                ruta3 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // Escoger el mas corto 'el que tenga menos saltos'

            if (ruta1 < ruta2 && ruta1 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() - 2, ficha.getSecond() + 1);
            }
            if (ruta2 < ruta1 && ruta2 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() + 2);
            }
            if (ruta3 < ruta2 && ruta3 < ruta1) {
                casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1);
            }
        }
        if (sentido == "Arriba") // Se evalua los 3 posibles caminos hacia la arriba de la ficha
        {
            if (tablero[ficha.getFirst() - 2][ficha.getSecond() + 1] != null
                    && tablero[ficha.getFirst() - 1][ficha.getSecond() + 2] != null
                    && tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] != null) {
                return 99;
            }
            // ------ Ruta al 1 -------
            if (tablero[ficha.getFirst() - 2][ficha.getSecond() + 1] == null
                    && (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 2][ficha.getSecond() + 1] = true;
                ruta1 += rutaMasCorta(new Pair(ficha.getFirst() - 2, ficha.getSecond() + 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1; // ATento
                // al +1
            }
            // ------ Ruta al 2 -------
            if (tablero[ficha.getFirst() - 1][ficha.getSecond() + 2] == null
                    && (tablero[ficha.getFirst()][ficha.getSecond() + 1] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() + 2] = true;
                ruta2 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() + 2),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // ------ Ruta al 6 -------
            if (tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null
                    && (tablero[ficha.getFirst() - 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst()][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() - 1][ficha.getSecond() - 1] = true;
                ruta3 += rutaMasCorta(new Pair(ficha.getFirst() - 1, ficha.getSecond() - 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1; // ATento
                // al +1
            }
            // Escoger el mas corto 'el que tenga menos saltos'

            if (ruta1 < ruta2 && ruta1 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() - 2, ficha.getSecond() + 1);
            }
            if (ruta2 < ruta1 && ruta2 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() + 2);
            }
            if (ruta3 < ruta2 && ruta3 < ruta1) {
                casillaRespuesta = new Pair(ficha.getFirst() - 1, ficha.getSecond() - 1);
            }
        }
        if (sentido == "Abajo") // Se evalua los 3 posibles caminos hacia la abajo de la ficha
        {
            if (tablero[ficha.getFirst() + 1][ficha.getSecond() - 2] != null
                    && tablero[ficha.getFirst() + 2][ficha.getSecond() - 1] != null
                    && tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] != null) {
                return 99;
            }
            // ------ Ruta al 5 -------
            if (tablero[ficha.getFirst() + 1][ficha.getSecond() - 2] == null
                    && (tablero[ficha.getFirst()][ficha.getSecond() - 1] == null
                            && tablero[ficha.getFirst() - 1][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() - 2] = true;
                ruta1 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() - 2),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // ------ Ruta al 4 -------
            if (tablero[ficha.getFirst() + 2][ficha.getSecond() - 1] == null
                    && (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst() + 1][ficha.getSecond() - 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 2][ficha.getSecond() - 1] = true;
                ruta2 += rutaMasCorta(new Pair(ficha.getFirst() + 2, ficha.getSecond() - 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // ------ Ruta al 3 -------
            if (tablero[ficha.getFirst() + 1][ficha.getSecond() + 1] == null
                    && (tablero[ficha.getFirst() + 1][ficha.getSecond()] == null
                            && tablero[ficha.getFirst()][ficha.getSecond() + 1] == null)) {
                casillasVisitadas[ficha.getFirst() + 1][ficha.getSecond() + 1] = true;
                ruta3 += rutaMasCorta(new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1),
                        casillasVisitadas, color, tablero, sentido, casillaRespuesta) + 1;
            }
            // Escoger el mas corto 'el que tenga menos saltos'
            if (ruta1 < ruta2 && ruta1 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() - 2);
            }
            if (ruta2 < ruta1 && ruta2 < ruta3) {
                casillaRespuesta = new Pair(ficha.getFirst() + 2, ficha.getSecond() - 1);
            }
            if (ruta3 < ruta2 && ruta3 < ruta1) {
                casillaRespuesta = new Pair(ficha.getFirst() + 1, ficha.getSecond() + 1);
            }
        }
        return ruta3;
    }

    public Pair casillaVacia() {
        for (int i = 0; i < t.getTableroHex().length; i++) {
            for (int j = 0; j < t.getTableroHex().length; j++) {
                if (t.getTableroHex()[i][j] == null) {
                    return new Pair(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public String nombreJugador() {
        return "Amanda, William y Diego";
    }

}
