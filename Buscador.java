package RCS;

/**
 * Hilo que hara la búsqueda.
 * 
 * @author roberto
 * 
 */
public class Buscador extends Thread {
	String s;

	Buscador(String s) {
		this.s = s;
	}

	public void run() {
		s = Busqueda.solucion(s);
	}

	public String getSol() {
		return s;
	}
}
