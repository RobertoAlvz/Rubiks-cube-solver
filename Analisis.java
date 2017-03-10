package RCS;

/**
 * Analisis de las transformaciones.
 * 
 * @author roberto
 * 
 */
public class Analisis {
	static final short N_ROT = 2187;// 3^7 rotaciones de esquina
	static final short N_ORI = 2048;// 2^11 orientaciones de arista
	static final short N_CAPA1 = 495;// 12 combinacion 4 posiciones de arista en
										// capa
	static final short N_CAPA2 = 24;// 4! permutaciones de aristaen fase2
	static final short N_PARIDAD = 2; // 2 paridades de esquina
	static final short N_URFtoDLF = 20160;// 8!/(8-6)! permutaciones de esquina
											// correspondientes
	static final short N_FRtoBR = 11880; // 12!/(12-4)! permutaciones de arista
											// correspondientes
	static final short N_URtoUL = 1320; // 12!/(12-3)! permutaciones de arista
										// correspondientes
	static final short N_UBtoDF = 1320; // 12!/(12-3)! permutaciones de arista
										// correspondientes
	static final short N_URtoDF = 20160; // 8!/(8-6)! permutaciones de arista en
											// fase2
	static final int N_URFtoDLB = 40320;
	static final int N_URtoBR = 479001600;
	static final short N_MOVE = 18;

	short rot;
	short ori;
	short paridad;
	short FRtoBR;
	short URFtoDLF;
	short URtoUL;
	short UBtoDF;
	int URtoDF;

	Analisis(Piezas c) {
		rot = c.getRot();
		ori = c.getOri();
		paridad = c.esqParidad();
		FRtoBR = c.getFRtoBR();
		URFtoDLF = c.getURFtoDLF();
		URtoUL = c.getURtoUL();
		UBtoDF = c.getUBtoDF();
		URtoDF = c.getURtoDF();
	}

	/**
	 * Analisis de movimiento.
	 * 
	 * @param m
	 *            Valor numérico del movimiento.
	 */
	void move(int m) {
		rot = rotMov[rot][m];
		ori = oriMov[ori][m];
		paridad = paridadMov[paridad][m];
		FRtoBR = FRtoBR_Move[FRtoBR][m];
		URFtoDLF = URFtoDLF_Move[URFtoDLF][m];
		URtoUL = URtoUL_Move[URtoUL][m];
		UBtoDF = UBtoDF_Move[UBtoDF][m];
		if (URtoUL < 336 && UBtoDF < 336)
			URtoDF = MergeURtoULandUBtoDF[URtoUL][UBtoDF];
	}

	/**
	 * Matriz de movs. para rotaciones de esquinas. rot < 2187 en la primera
	 * fase. rot = 0 en la segunda fase.
	 */
	static short[][] rotMov = new short[N_ROT][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_ROT; i++) {
			a.setRot(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.esquinaMult(Piezas.moveCube[j]);
					rotMov[i][3 * j + k] = a.getRot();
				}
				a.esquinaMult(Piezas.moveCube[j]);
			}
		}
	}

	/**
	 * Matriz de movs. para orientaciones de aristas. ori < 2048 en la primera
	 * fase. ori = 0 en la segunda fase.
	 */
	static short[][] oriMov = new short[N_ORI][N_MOVE]; // Orimov es un villano
														// de James Bond ¿no?
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_ORI; i++) {
			a.setOri(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.aristaMult(Piezas.moveCube[j]);
					oriMov[i][3 * j + k] = a.getOri();
				}
				a.aristaMult(Piezas.moveCube[j]);
			}
		}
	}

	static short[][] paridadMov = {
			{ 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 } };

	// Matriz de movs. para las aristas la capa media.
	// FRtoBRMove < 11880 en fase 1.
	// FRtoBRMove < 24 en fase 2.
	// FRtoBRMove = 0 cubo solucionado.
	static short[][] FRtoBR_Move = new short[N_FRtoBR][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_FRtoBR; i++) {
			a.setFRtoBR(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.aristaMult(Piezas.moveCube[j]);
					FRtoBR_Move[i][3 * j + k] = a.getFRtoBR();
				}
				a.aristaMult(Piezas.moveCube[j]);
			}
		}
	}

	// Matriz de movs. para permutaciones de esquinas.
	// URFtoDLF < 20160 en fase 1
	// URFtoDLF < 20160 en fase 2
	// URFtoDLF = 0 cubo solucionado.
	static short[][] URFtoDLF_Move = new short[N_URFtoDLF][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_URFtoDLF; i++) {
			a.setURFtoDLF(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.esquinaMult(Piezas.moveCube[j]);
					URFtoDLF_Move[i][3 * j + k] = a.getURFtoDLF();
				}
				a.esquinaMult(Piezas.moveCube[j]);
			}
		}
	}

	// Matriz de movs. para permutaciones de esquinas.
	// URtoDF < 665280 en fase 1
	// URtoDF < 20160 en fase 2
	// URtoDF = 0 cubo solucionado.
	static short[][] URtoDF_Move = new short[N_URtoDF][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_URtoDF; i++) {
			a.setURtoDF(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.aristaMult(Piezas.moveCube[j]);
					URtoDF_Move[i][3 * j + k] = (short) a.getURtoDF();
				}
				a.aristaMult(Piezas.moveCube[j]);
			}
		}
	}

	// Auxiliares de matrices de movs. para segunda fase
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	static short[][] URtoUL_Move = new short[N_URtoUL][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_URtoUL; i++) {
			a.setURtoUL(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.aristaMult(Piezas.moveCube[j]);
					URtoUL_Move[i][3 * j + k] = a.getURtoUL();
				}
				a.aristaMult(Piezas.moveCube[j]);
			}
		}
	}

	static short[][] UBtoDF_Move = new short[N_UBtoDF][N_MOVE];
	static {
		Piezas a = new Piezas();
		for (short i = 0; i < N_UBtoDF; i++) {
			a.setUBtoDF(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 3; k++) {
					a.aristaMult(Piezas.moveCube[j]);
					UBtoDF_Move[i][3 * j + k] = a.getUBtoDF();
				}
				a.aristaMult(Piezas.moveCube[j]);
			}
		}
	}

	// Matriz para unificar coordenadas en fase 2.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	static short[][] MergeURtoULandUBtoDF = new short[336][336];
	static {
		for (short uRtoUL = 0; uRtoUL < 336; uRtoUL++)
			for (short uBtoDF = 0; uBtoDF < 336; uBtoDF++)
				MergeURtoULandUBtoDF[uRtoUL][uBtoDF] = (short) Piezas
						.getURtoDF(uRtoUL, uBtoDF);
	}

	// Matrices de poda.
	// Alamacenan la cota inferior del número de movs. para alcanzar el cubo
	// solucionado.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Matriz de poda para las permutaciones de esquina.
	static byte[] CapaURFtoDLFPodaParidad = new byte[N_CAPA2 * N_URFtoDLF
			* N_PARIDAD / 2];
	static {
		for (int i = 0; i < N_CAPA2 * N_URFtoDLF * N_PARIDAD / 2; i++)
			CapaURFtoDLFPodaParidad[i] = -1;
		int depth = 0;
		setPoda(CapaURFtoDLFPodaParidad, 0, (byte) 0);
		int aux = 1;
		while (aux != N_CAPA2 * N_URFtoDLF * N_PARIDAD) {
			for (int i = 0; i < N_CAPA2 * N_URFtoDLF * N_PARIDAD; i++) {
				int paridad = i % 2;
				int URFtoDLF = (i / 2) / N_CAPA2;
				int capa = (i / 2) % N_CAPA2;
				if (getPoda(CapaURFtoDLFPodaParidad, i) == depth) {
					for (int j = 0; j < 18; j++) {
						switch (j) {
						case 3:
						case 5:
						case 6:
						case 8:
						case 12:
						case 14:
						case 15:
						case 17:
							continue;
						default:
							int newCap = FRtoBR_Move[capa][j];
							int newURFtoDLF = URFtoDLF_Move[URFtoDLF][j];
							int nParidad = paridadMov[paridad][j];
							if (getPoda(CapaURFtoDLFPodaParidad, (N_CAPA2
									* newURFtoDLF + newCap)
									* 2 + nParidad) == 0x0f) {
								setPoda(CapaURFtoDLFPodaParidad, (N_CAPA2
										* newURFtoDLF + newCap)
										* 2 + nParidad, (byte) (depth + 1));
								aux++;
							}
						}
					}
				}
			}
			depth++;
		}
	}

	// Matriz de poda para las permutaciones de arista.
	static byte[] CapaURtoDFPodaParidad = new byte[N_CAPA2 * N_URtoDF
			* N_PARIDAD / 2];
	static {
		for (int i = 0; i < N_CAPA2 * N_URtoDF * N_PARIDAD / 2; i++)
			CapaURtoDFPodaParidad[i] = -1;
		int depth = 0;
		setPoda(CapaURtoDFPodaParidad, 0, (byte) 0);
		int aux = 1;
		while (aux != N_CAPA2 * N_URtoDF * N_PARIDAD) {
			for (int i = 0; i < N_CAPA2 * N_URtoDF * N_PARIDAD; i++) {
				int paridad = i % 2;
				int URtoDF = (i / 2) / N_CAPA2;
				int capa = (i / 2) % N_CAPA2;
				if (getPoda(CapaURtoDFPodaParidad, i) == depth) {
					for (int j = 0; j < 18; j++) {
						switch (j) {
						case 3:
						case 5:
						case 6:
						case 8:
						case 12:
						case 14:
						case 15:
						case 17:
							continue;
						default:
							int newCap = FRtoBR_Move[capa][j];
							int newURtoDF = URtoDF_Move[URtoDF][j];
							int newParidad = paridadMov[paridad][j];
							if (getPoda(CapaURtoDFPodaParidad, (N_CAPA2
									* newURtoDF + newCap)
									* 2 + newParidad) == 0x0f) {
								setPoda(CapaURtoDFPodaParidad, (N_CAPA2
										* newURtoDF + newCap)
										* 2 + newParidad, (byte) (depth + 1));
								aux++;
							}
						}
					}
				}
			}
			depth++;
		}
	}

	// Almacenan la cota inferior del número de movs. para alcanzar el subgrupo
	// H.
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Matriz de poda para rotaciones de esquina.
	static byte[] CapaRotPoda = new byte[N_CAPA1 * N_ROT / 2 + 1];
	static {
		for (int i = 0; i < N_CAPA1 * N_ROT / 2 + 1; i++)
			CapaRotPoda[i] = -1;
		int depth = 0;
		setPoda(CapaRotPoda, 0, (byte) 0);
		int aux = 1;
		while (aux != N_CAPA1 * N_ROT) {
			for (int i = 0; i < N_CAPA1 * N_ROT; i++) {
				int rot = i / N_CAPA1, capa = i % N_CAPA1;
				if (getPoda(CapaRotPoda, i) == depth) {
					for (int j = 0; j < 18; j++) {
						int newCap = FRtoBR_Move[capa * 24][j] / 24;
						int newRot = rotMov[rot][j];
						if (getPoda(CapaRotPoda, N_CAPA1 * newRot + newCap) == 0x0f) {
							setPoda(CapaRotPoda, N_CAPA1 * newRot + newCap,
									(byte) (depth + 1));
							aux++;
						}
					}
				}
			}
			depth++;
		}
	}

	// Matriz de poda para orientaciones de aristas.
	static byte[] CapaOriPoda = new byte[N_CAPA1 * N_ORI / 2];
	static {
		for (int i = 0; i < N_CAPA1 * N_ORI / 2; i++)
			CapaOriPoda[i] = -1;
		int depth = 0;
		setPoda(CapaOriPoda, 0, (byte) 0);
		int aux = 1;
		while (aux != N_CAPA1 * N_ORI) {
			for (int i = 0; i < N_CAPA1 * N_ORI; i++) {
				int ori = i / N_CAPA1, capa = i % N_CAPA1;
				if (getPoda(CapaOriPoda, i) == depth) {
					for (int j = 0; j < 18; j++) {
						int newCap = FRtoBR_Move[capa * 24][j] / 24;
						int newOri = oriMov[ori][j];
						if (getPoda(CapaOriPoda, N_CAPA1 * newOri + newCap) == 0x0f) {
							setPoda(CapaOriPoda, N_CAPA1 * newOri + newCap,
									(byte) (depth + 1));
							aux++;
						}
					}
				}
			}
			depth++;
		}
	}

	/**
	 * Establece valores para la matriz de poda
	 * 
	 * @param mat
	 *            Matriz de poda.
	 * @param i
	 *            Indice
	 * @param v
	 *            Valor
	 */
	static void setPoda(byte[] mat, int i, byte v) {
		// OR, AND bit por bit
		if ((i & 1) == 0)
			mat[i / 2] &= 0xf0 | v;
		else
			mat[i / 2] &= 0x0f | (v << 4);
	}

	/**
	 * Obtiene el valor de la matriz de poda.
	 * 
	 * @param mat
	 *            Matriz de poda.
	 * @param i
	 *            Índice
	 * @return Valor de matriz de poda.
	 */
	static byte getPoda(byte[] mat, int i) {
		if ((i & 1) == 0)
			return (byte) (mat[i / 2] & 0x0f);
		else
			return (byte) ((mat[i / 2] & 0xf0) >>> 4);
	}
}
