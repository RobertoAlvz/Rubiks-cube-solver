package RCS;

/**
 * Implementa la búsqueda con el algoritmo A*.
 * @author roberto
 *
 */
public class Busqueda {
	static int[] ax = new int[31];
	static int[] po = new int[31];
	// Variables para la primera fase.
	static int[] ori = new int[31];
	static int[] rot = new int[31];
	static int[] capa = new int[31];
	// Variables para la segunda fase.
	static int[] paridad = new int[31];
	static int[] URFtoDLF = new int[31];
	static int[] FRtoBR = new int[31];
	static int[] URtoUL = new int[31];
	static int[] UBtoDF = new int[31];
	static int[] URtoDF = new int[31];
	// Cotas para el algoritmo.
	static int[] minDist1 = new int[31];
	static int[] minDist2 = new int[31];

	/**
	 * Genera la cadena solucion a partir de los datos.
	 * 
	 * @param length
	 *            Longitud de la cadena solucion.
	 * @return Representacion de la solucion como cadena.
	 */
	static String solutionToString(int length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			// Qué cara mover.
			switch (ax[i]) {
			case 0:
				s += "U";// U
				break;
			case 1:
				s += "R";// R
				break;
			case 2:
				s += "F";// F
				break;
			case 3:
				s += "D";// D
				break;
			case 4:
				s += "L";// L
				break;
			case 5:
				s += "B";// B
				break;
			}
			// Cuánto moverla.
			switch (po[i]) {
			case 1:
				s += " ";
				break;
			case 2:
				s += "2 ";
				break;
			case 3:
				s += "' ";
				break;
			}
		}
		return s;
	};

	/**
	 * Encuentra la cadena solucion.
	 * 
	 * @param caras
	 *            Representación del cubo como caras.
	 * @return Representación de la solución como cadena, utiliza
	 *         solutionToString(...).
	 */
	public static String solucion(String caras) {
		int s;

		Caras fc = new Caras(caras);
		Piezas cc = fc.aPiezas();
		if ((s = cc.verificar()) != 0)
			return "Error " + Math.abs(s);

		// Inicializaciones.
		Analisis c = new Analisis(cc);
		po[0] = 0;
		ax[0] = 0;
		ori[0] = c.ori;
		rot[0] = c.rot;
		paridad[0] = c.paridad;
		capa[0] = c.FRtoBR / 24;
		URFtoDLF[0] = c.URFtoDLF;
		FRtoBR[0] = c.FRtoBR;
		URtoUL[0] = c.URtoUL;
		UBtoDF[0] = c.UBtoDF;

		minDist1[1] = 1;
		int mv = 0, n = 0;
		boolean aux = false;
		int depth1 = 1;

		// Y empieza la búsqueda.
		do {
			do {
				if ((depth1 - n > minDist1[n + 1]) && !aux) {
					if (ax[n] == 0 || ax[n] == 3)
						ax[++n] = 1;
					else
						ax[++n] = 0;
					po[n] = 1;
				} else if (++po[n] > 3) {
					do {
						if (++ax[n] > 5) {
							if (n == 0) {
								depth1++;
								ax[n] = 0;
								po[n] = 1;
								aux = false;
								break;
							} else {
								n--;
								aux = true;
								break;
							}
						} else {
							po[n] = 1;
							aux = false;
						}
					} while (n != 0
							&& (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
				} else
					aux = false;
			} while (aux);

			// Nuevo analisis.
			// Si minDist1=0, se alcanzó el subgrupo H.
			mv = 3 * ax[n] + po[n] - 1;
			ori[n + 1] = Analisis.oriMov[ori[n]][mv];
			rot[n + 1] = Analisis.rotMov[rot[n]][mv];
			capa[n + 1] = Analisis.FRtoBR_Move[capa[n] * 24][mv] / 24;
			minDist1[n + 1] = Math.max(
					Analisis.getPoda(Analisis.CapaOriPoda, Analisis.N_CAPA1
							* ori[n + 1] + capa[n + 1]),
					Analisis.getPoda(Analisis.CapaRotPoda, Analisis.N_CAPA1
							* rot[n + 1] + capa[n + 1]));
			if (minDist1[n + 1] == 0 && n >= depth1 - 5) {
				minDist1[n + 1] = 10; // Arbitrario!
				if (n == depth1 - 1 && (s = totalDepth(depth1)) >= 0)
					if (s == depth1
							|| (ax[depth1 - 1] != ax[depth1] && ax[depth1 - 1] != ax[depth1] + 3))
						return solutionToString(s);
			}
		} while (true);
	}

	/**
	 * Segunda fase del algoritmo de búsqueda.
	 * 
	 * @param depth1
	 *            Profundidad de búsqueda restante.
	 * @return Longitud total de la solución.
	 */
	static int totalDepth(int depth1) {
		int mv = 0, d1 = 0, d2 = 0;
		int maxDepth2 = Math.min(10, 20 - depth1); // YOLO
		for (int i = 0; i < depth1; i++) {
			mv = 3 * ax[i] + po[i] - 1;
			URFtoDLF[i + 1] = Analisis.URFtoDLF_Move[URFtoDLF[i]][mv];
			FRtoBR[i + 1] = Analisis.FRtoBR_Move[FRtoBR[i]][mv];
			paridad[i + 1] = Analisis.paridadMov[paridad[i]][mv];
		}
		if ((d1 = Analisis.getPoda(Analisis.CapaURFtoDLFPodaParidad,
				(Analisis.N_CAPA2 * URFtoDLF[depth1] + FRtoBR[depth1]) * 2
						+ paridad[depth1])) > maxDepth2)
			return -1;
		for (int i = 0; i < depth1; i++) {
			mv = 3 * ax[i] + po[i] - 1;
			URtoUL[i + 1] = Analisis.URtoUL_Move[URtoUL[i]][mv];
			UBtoDF[i + 1] = Analisis.UBtoDF_Move[UBtoDF[i]][mv];
		}
		URtoDF[depth1] = Analisis.MergeURtoULandUBtoDF[URtoUL[depth1]][UBtoDF[depth1]];
		if ((d2 = Analisis.getPoda(Analisis.CapaURtoDFPodaParidad,
				(Analisis.N_CAPA2 * URtoDF[depth1] + FRtoBR[depth1]) * 2
						+ paridad[depth1])) > maxDepth2)
			return -1;
		if ((minDist2[depth1] = Math.max(d1, d2)) == 0)
			return depth1; // Ya solucionado.

		// Y volvemos a buscar.
		int depth2 = 1;
		int n = depth1;
		boolean aux = false;
		po[depth1] = 0;
		ax[depth1] = 0;
		minDist2[n + 1] = 1;
		do {
			do {
				if ((depth1 + depth2 - n > minDist2[n + 1]) && !aux) {
					if (ax[n] == 0 || ax[n] == 3) {
						ax[++n] = 1;
						po[n] = 2;
					} else {
						ax[++n] = 0;
						po[n] = 1;
					}
				} else if ((ax[n] == 0 || ax[n] == 3) ? (++po[n] > 3)
						: ((po[n] = po[n] + 2) > 3)) {
					do {
						if (++ax[n] > 5) {
							if (n == depth1) {
								if (depth2 >= maxDepth2)
									return -1;
								else {
									depth2++;
									ax[n] = 0;
									po[n] = 1;
									aux = false;
									break;
								}
							} else {
								n--;
								aux = true;
								break;
							}
						} else {
							if (ax[n] == 0 || ax[n] == 3)
								po[n] = 1;
							else
								po[n] = 2;
							aux = false;
						}
					} while (n != depth1
							&& (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
				} else
					aux = false;
			} while (aux);

			// Analisis, otra vez.
			mv = 3 * ax[n] + po[n] - 1;
			URFtoDLF[n + 1] = Analisis.URFtoDLF_Move[URFtoDLF[n]][mv];
			FRtoBR[n + 1] = Analisis.FRtoBR_Move[FRtoBR[n]][mv];
			paridad[n + 1] = Analisis.paridadMov[paridad[n]][mv];
			URtoDF[n + 1] = Analisis.URtoDF_Move[URtoDF[n]][mv];
			minDist2[n + 1] = Math.max(Analisis.getPoda(
					Analisis.CapaURtoDFPodaParidad, (Analisis.N_CAPA2
							* URtoDF[n + 1] + FRtoBR[n + 1])
							* 2 + paridad[n + 1]), Analisis.getPoda(
					Analisis.CapaURFtoDLFPodaParidad, (Analisis.N_CAPA2
							* URFtoDLF[n + 1] + FRtoBR[n + 1])
							* 2 + paridad[n + 1]));
		} while (minDist2[n + 1] != 0);
		return depth1 + depth2;
	}
	// Pan comido.
}
