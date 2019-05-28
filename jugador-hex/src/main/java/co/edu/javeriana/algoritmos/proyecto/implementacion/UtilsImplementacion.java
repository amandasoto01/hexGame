package co.edu.javeriana.algoritmos.proyecto.implementacion;

public class UtilsImplementacion {
	
	public boolean posicionValida(int fila, int columna) {
		if(fila<0 || columna >=11) return false;
		if(columna<0 || columna >= 11) return false;
		return true;
	}
	
	/**
	 * Arriba 0,-1
	 * Izquierda -1,0
	 * Diag-Izq-Abajo -1,1
	 * Abajo 0, 1
	 * Derecha 1, 0
	 * Diag-Der-Arriba 1,-1
	 **/
	public final int movimientosX[] = {0,-1,-1,0,1,1};
	public final int movimientosY[] = {-1,0,1,1,0,-1};
	public final int numMovimientos = 6;
}
