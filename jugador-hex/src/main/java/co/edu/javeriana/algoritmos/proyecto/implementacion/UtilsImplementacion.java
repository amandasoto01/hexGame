package co.edu.javeriana.algoritmos.proyecto.implementacion;

public class UtilsImplementacion {
	
	public boolean posicionValida(int fila, int columna) {
		if(fila<0 || columna >=11) return false;
		if(columna<0 || columna >= 11) return false;
		return true;
	}
	
	/**
	 * DiagonalIzquierdaArriba -1,-1
	 * Izquierda -1,0
	 * Abajo 0,1
	 * DiagonalDerechaAbajo 1,1
	 * Derecha 1,0
	 * Arriba 0,-1 
	 **/
	public final int movimientosX[] = {-1,-1,0,1,1,0};
	public final int movimientosY[] = {-1,0,1,1,0,-1};
	public final int numMovimientos = 6;
}
