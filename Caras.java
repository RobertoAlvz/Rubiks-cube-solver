package RCS;

import static RCS.Cara.*;
import static RCS.Colores.*;
import static RCS.Esquina.*;
import static RCS.Arista.*;

/**
 * Representación del cubo como caras.
 * @author roberto
 *
 */
public class Caras {
	public Colores[] f = new Colores[54];
	// Mapea las posiciones de las esquinas a las de las caras.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	final static Cara[][] esqCars = { { U9, R1, F3 }, { U7, F1, L3 },
			{ U1, L1, B3 }, { U3, B1, R3 }, { D3, F9, R7 }, { D1, L9, F7 },
			{ D7, B9, L7 }, { D9, R9, B7 } };

	// Mapea las posiciones de las aristas a las de las caras.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	final static Cara[][] arstCars = { { U6, R2 }, { U8, F2 }, { U4, L2 },
			{ U2, B2 }, { D6, R8 }, { D2, F8 }, { D4, L8 }, { D8, B8 },
			{ F6, R4 }, { F4, L6 }, { B6, L4 }, { B4, R6 } };

	// Mapea las posiciones de las esquinas a colores.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	final static Colores[][] esqColor = { { U, R, F }, { U, F, L },
			{ U, L, B }, { U, B, R }, { D, F, R }, { D, L, F }, { D, B, L },
			{ D, R, B } };

	// Mapea las posiciones de las arsitas a colores.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	final static Colores[][] arstColor = { { U, R }, { U, F }, { U, L },
			{ U, B }, { D, R }, { D, F }, { D, L }, { D, B }, { F, R },
			{ F, L }, { B, L }, { B, R } };

	Caras() {
		String s = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";
		for (int i = 0; i < 54; i++)
			f[i] = Colores.valueOf(s.substring(i, i + 1));
	}

	Caras(String cubeString) {
		for (int i = 0; i < cubeString.length(); i++)
			f[i] = Colores.valueOf(cubeString.substring(i, i + 1));
	}

	/**
	 * Representacion del cubo como cadena.
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < 54; i++)
			s += f[i].toString();
		return s;
	}

	/**
	 * Representa el cubo como piezas a partir del cubo como caras.
	 * 
	 * @return
	 */
	Piezas aPiezas() {
		byte ori;
		Piezas ccRet = new Piezas();
		// Inicializaciones.
		for (int i = 0; i < 8; i++)
			ccRet.cp[i] = URF;
		for (int i = 0; i < 12; i++)
			ccRet.ep[i] = UR;
		Colores col1, col2;
		// Posiciona las esquinas.
		for (Esquina i : Esquina.values()) {
			for (ori = 0; ori < 3; ori++)
				if (f[esqCars[i.ordinal()][ori].ordinal()] == U
						|| f[esqCars[i.ordinal()][ori].ordinal()] == D)
					break;
			col1 = f[esqCars[i.ordinal()][(ori + 1) % 3].ordinal()];
			col2 = f[esqCars[i.ordinal()][(ori + 2) % 3].ordinal()];
			for (Esquina j : Esquina.values()) {
				if (col1 == esqColor[j.ordinal()][1]
						&& col2 == esqColor[j.ordinal()][2]) {
					ccRet.cp[i.ordinal()] = j;
					ccRet.co[i.ordinal()] = (byte) (ori % 3);
					break;
				}
			}
		}
		// Posiciona las aristas.
		for (Arista i : Arista.values())
			for (Arista j : Arista.values()) {
				if (f[arstCars[i.ordinal()][0].ordinal()] == arstColor[j
						.ordinal()][0]
						&& f[arstCars[i.ordinal()][1].ordinal()] == arstColor[j
								.ordinal()][1]) {
					ccRet.ep[i.ordinal()] = j;
					ccRet.eo[i.ordinal()] = 0;
					break;
				}
				if (f[arstCars[i.ordinal()][0].ordinal()] == arstColor[j
						.ordinal()][1]
						&& f[arstCars[i.ordinal()][1].ordinal()] == arstColor[j
								.ordinal()][0]) {
					ccRet.ep[i.ordinal()] = j;
					ccRet.eo[i.ordinal()] = 1;
					break;
				}
			}
		return ccRet;
	};
}
