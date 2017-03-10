package RCS;

import static RCS.Esquina.*;
import static RCS.Arista.*;

/**
 * Representación del cubo como piezas.
 * @author roberto
 *
 */
public class Piezas {
	// Permutaciones y orientaciones.
	Esquina[] cp = { URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB };
	byte[] co = { 0, 0, 0, 0, 0, 0, 0, 0 };
	Arista[] ep = { UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR };
	byte[] eo = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	// Movimientos.
	private static Esquina[] cpU = { UBR, URF, UFL, ULB, DFR, DLF, DBL, DRB };
	private static byte[] coU = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private static Arista[] epU = { UB, UR, UF, UL, DR, DF, DL, DB, FR, FL, BL,
			BR };
	private static byte[] eoU = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static Esquina[] cpR = { DFR, UFL, ULB, URF, DRB, DLF, DBL, UBR };
	private static byte[] coR = { 2, 0, 0, 1, 1, 0, 0, 2 };
	private static Arista[] epR = { FR, UF, UL, UB, BR, DF, DL, DB, DR, FL, BL,
			UR };
	private static byte[] eoR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static Esquina[] cpF = { UFL, DLF, ULB, UBR, URF, DFR, DBL, DRB };
	private static byte[] coF = { 1, 2, 0, 0, 2, 1, 0, 0 };
	private static Arista[] epF = { UR, FL, UL, UB, DR, FR, DL, DB, UF, DF, BL,
			BR };
	private static byte[] eoF = { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0 };

	private static Esquina[] cpD = { URF, UFL, ULB, UBR, DLF, DBL, DRB, DFR };
	private static byte[] coD = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private static Arista[] epD = { UR, UF, UL, UB, DF, DL, DB, DR, FR, FL, BL,
			BR };
	private static byte[] eoD = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static Esquina[] cpL = { URF, ULB, DBL, UBR, DFR, UFL, DLF, DRB };
	private static byte[] coL = { 0, 1, 2, 0, 0, 2, 1, 0 };
	private static Arista[] epL = { UR, UF, BL, UB, DR, DF, FL, DB, FR, UL, DL,
			BR };
	private static byte[] eoL = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static Esquina[] cpB = { URF, UFL, UBR, DRB, DFR, DLF, ULB, DBL };
	private static byte[] coB = { 0, 0, 1, 2, 0, 0, 2, 1 };
	private static Arista[] epB = { UR, UF, UL, BR, DR, DF, DL, BL, FR, FL, UB,
			DB };
	private static byte[] eoB = { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1 };

	// Transformaciones de los movimientos.
	static Piezas[] moveCube = new Piezas[6];
	static {
		moveCube[0] = new Piezas();
		moveCube[0].cp = cpU;
		moveCube[0].co = coU;
		moveCube[0].ep = epU;
		moveCube[0].eo = eoU;

		moveCube[1] = new Piezas();
		moveCube[1].cp = cpR;
		moveCube[1].co = coR;
		moveCube[1].ep = epR;
		moveCube[1].eo = eoR;

		moveCube[2] = new Piezas();
		moveCube[2].cp = cpF;
		moveCube[2].co = coF;
		moveCube[2].ep = epF;
		moveCube[2].eo = eoF;

		moveCube[3] = new Piezas();
		moveCube[3].cp = cpD;
		moveCube[3].co = coD;
		moveCube[3].ep = epD;
		moveCube[3].eo = eoD;

		moveCube[4] = new Piezas();
		moveCube[4].cp = cpL;
		moveCube[4].co = coL;
		moveCube[4].ep = epL;
		moveCube[4].eo = eoL;

		moveCube[5] = new Piezas();
		moveCube[5].cp = cpB;
		moveCube[5].co = coB;
		moveCube[5].ep = epB;
		moveCube[5].eo = eoB;
	}

	Piezas() {
	};

	public Piezas(Esquina[] cp, byte[] co, Arista[] ep, byte[] eo) {
		for (int i = 0; i < 8; i++) {
			this.cp[i] = cp[i];
			this.co[i] = co[i];
		}
		for (int i = 0; i < 12; i++) {
			this.ep[i] = ep[i];
			this.eo[i] = eo[i];
		}
	}

	/**
	 * Combinatoria.
	 * 
	 * @param n
	 * @param k
	 * @return
	 */
	static int Cnk(int n, int k) {
		int i, j, s;
		if (n < k)
			return 0;
		if (k > n / 2)
			k = n - k;
		for (s = 1, i = n, j = 1; i != n - k; i--, j++) {
			s *= i;
			s /= j;
		}
		return s;
	}

	/**
	 * Rotación básica.
	 * 
	 * @param arr
	 *            Esquina a rotar.
	 * @param l
	 * @param r
	 */
	static void rotarDer(Esquina[] arr, int l, int r) {
		Esquina temp = arr[r];
		for (int i = r; i > l; i--)
			arr[i] = arr[i - 1];
		arr[l] = temp;
	}

	/**
	 * Rotación básica.
	 * 
	 * @param arr
	 *            Arista a rotar.
	 * @param l
	 * @param r
	 */
	static void rotarIzq(Arista arr[], int l, int r) {
		Arista temp = arr[l];
		for (int i = l; i < r; i++)
			arr[i] = arr[i + 1];
		arr[r] = temp;
	}

	/**
	 * Rotación básica.
	 * 
	 * @param arr
	 *            Arista a rotar.
	 * @param l
	 * @param r
	 */
	static void rotarDer(Arista[] arr, int l, int r) {
		Arista temp = arr[r];
		for (int i = r; i > l; i--)
			arr[i] = arr[i - 1];
		arr[l] = temp;
	}

	/**
	 * Rotación básica.
	 * 
	 * @param arr
	 *            Esquina a rotar.
	 * @param l
	 * @param r
	 */
	static void rotarIzq(Esquina arr[], int l, int r) {
		Esquina temp = arr[l];
		for (int i = l; i < r; i++)
			arr[i] = arr[i + 1];
		arr[r] = temp;
	}

	/**
	 * Representación del cubo como caras.
	 * 
	 * @return
	 */
	Caras aCaras() {
		Caras fcRet = new Caras();
		for (Esquina c : Esquina.values()) {
			int i = c.ordinal();
			int j = cp[i].ordinal();
			int ori = co[i];
			for (int n = 0; n < 3; n++)
				fcRet.f[Caras.esqCars[i][(n + ori) % 3].ordinal()] = Caras.esqColor[j][n];
		}
		for (Arista e : Arista.values()) {
			int i = e.ordinal();
			int j = ep[i].ordinal();
			int ori = eo[i];
			for (int n = 0; n < 2; n++)
				fcRet.f[Caras.arstCars[i][(n + ori) % 2].ordinal()] = Caras.arstColor[j][n];
		}
		return fcRet;
	}

	/**
	 * Define el algebra de las piezas.
	 * 
	 * @param b
	 */
	void esquinaMult(Piezas b) {
		Esquina[] cPerm = new Esquina[8];
		byte[] cOri = new byte[8];
		for (Esquina esq : Esquina.values()) {
			cPerm[esq.ordinal()] = cp[b.cp[esq.ordinal()].ordinal()];
			byte oriA = co[b.cp[esq.ordinal()].ordinal()], oriB = b.co[esq
					.ordinal()], ori = 0;
			if (oriA < 3 && oriB < 3) {
				ori = (byte) (oriA + oriB);
				if (ori >= 3)
					ori -= 3;
			} else if (oriA < 3 && oriB >= 3) {
				ori = (byte) (oriA + oriB);
				if (ori >= 6)
					ori -= 3;
			} else if (oriA >= 3 && oriB < 3) {
				ori = (byte) (oriA - oriB);
				if (ori < 3)
					ori += 3;
			} else if (oriA >= 3 && oriB >= 3) {
				ori = (byte) (oriA - oriB);
				if (ori < 0)
					ori += 3;
			}
			cOri[esq.ordinal()] = ori;
		}
		for (Esquina c : Esquina.values()) {
			cp[c.ordinal()] = cPerm[c.ordinal()];
			co[c.ordinal()] = cOri[c.ordinal()];
		}
	}

	void aristaMult(Piezas b) {
		Arista[] aPerm = new Arista[12];
		byte[] aOri = new byte[12];
		for (Arista a : Arista.values()) {
			aPerm[a.ordinal()] = ep[b.ep[a.ordinal()].ordinal()];
			aOri[a.ordinal()] = (byte) ((b.eo[a.ordinal()] + eo[b.ep[a
					.ordinal()].ordinal()]) % 2);
		}
		for (Arista a : Arista.values()) {
			ep[a.ordinal()] = aPerm[a.ordinal()];
			eo[a.ordinal()] = aOri[a.ordinal()];
		}
	}

	void mult(Piezas b) {
		esquinaMult(b);
		// aristaMult(b);
	}

	/**
	 * Calcula el inverso algebráico.
	 * 
	 * @param c
	 */
	void invPiezas(Piezas c) {
		for (Arista a : Arista.values())
			c.ep[ep[a.ordinal()].ordinal()] = a;
		for (Arista a : Arista.values())
			c.eo[a.ordinal()] = eo[c.ep[a.ordinal()].ordinal()];
		for (Esquina esq : Esquina.values())
			c.cp[cp[esq.ordinal()].ordinal()] = esq;
		for (Esquina esq : Esquina.values()) {
			byte ori = co[c.cp[esq.ordinal()].ordinal()];
			if (ori >= 3)
				c.co[esq.ordinal()] = ori;
			else {
				c.co[esq.ordinal()] = (byte) -ori;
				if (c.co[esq.ordinal()] < 0)
					c.co[esq.ordinal()] += 3;
			}
		}
	}

	// Métodos get y set para rotaciones y orientaciones.
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	short getRot() {
		short ret = 0;
		for (int i = URF.ordinal(); i < DRB.ordinal(); i++)
			ret = (short) (3 * ret + co[i]);
		return ret;
	}

	void setRot(short r) {
		int paridad = 0;
		for (int i = DRB.ordinal() - 1; i >= URF.ordinal(); i--) {
			paridad += co[i] = (byte) (r % 3);
			r /= 3;
		}
		co[DRB.ordinal()] = (byte) ((3 - paridad % 3) % 3);
	}

	short getOri() {
		short ret = 0;
		for (int i = UR.ordinal(); i < BR.ordinal(); i++)
			ret = (short) (2 * ret + eo[i]);
		return ret;
	}

	void setOri(short o) {
		int paridad = 0;
		for (int i = BR.ordinal() - 1; i >= UR.ordinal(); i--) {
			paridad += eo[i] = (byte) (o % 2);
			o /= 2;
		}
		eo[BR.ordinal()] = (byte) ((2 - paridad % 2) % 2);
	}

	// Paridades de permutacion.
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	short esqParidad() {
		int s = 0;
		for (int i = DRB.ordinal(); i >= URF.ordinal() + 1; i--)
			for (int j = i - 1; j >= URF.ordinal(); j--)
				if (cp[j].ordinal() > cp[i].ordinal())
					s++;
		return (short) (s % 2);
	}

	short arstParidad() {
		int s = 0;
		for (int i = BR.ordinal(); i >= UR.ordinal() + 1; i--)
			for (int j = i - 1; j >= UR.ordinal(); j--)
				if (ep[j].ordinal() > ep[i].ordinal())
					s++;
		return (short) (s % 2);
	}

	// Métodos get y set para conjuntos de operaciones.
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	short getFRtoBR() {
		int a = 0, x = 0;
		Arista[] arista4 = new Arista[4];
		for (int j = BR.ordinal(); j >= UR.ordinal(); j--) {
			if (FR.ordinal() <= ep[j].ordinal()
					&& ep[j].ordinal() <= BR.ordinal()) {
				a += Cnk(11 - j, x + 1);
				arista4[3 - x++] = ep[j];
			}
		}
		int b = 0;
		for (int j = 3; j > 0; j--) {
			int k = 0;
			while (arista4[j].ordinal() != j + 8) {
				rotarIzq(arista4, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return (short) (24 * a + b);
	}

	void setFRtoBR(short idx) {
		int x;
		Arista[] capArst = { FR, FL, BL, BR };
		Arista[] otraArst = { UR, UF, UL, UB, DR, DF, DL, DB };
		int b = idx % 24;
		int a = idx / 24;
		for (Arista e : Arista.values())
			ep[e.ordinal()] = DB;
		for (int j = 1, k; j < 4; j++) {
			k = b % (j + 1);
			b /= j + 1;
			while (k-- > 0)
				rotarDer(capArst, 0, j);
		}
		x = 3;
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (a - Cnk(11 - j, x + 1) >= 0) {
				ep[j] = capArst[3 - x];
				a -= Cnk(11 - j, x-- + 1);
			}
		x = 0;
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (ep[j] == DB)
				ep[j] = otraArst[x++];
	}

	short getURFtoDLF() {
		int a = 0, x = 0;
		Esquina[] esq6 = new Esquina[6];
		for (int j = URF.ordinal(); j <= DRB.ordinal(); j++)
			if (cp[j].ordinal() <= DLF.ordinal()) {
				a += Cnk(j, x + 1);
				esq6[x++] = cp[j];
			}
		int b = 0;
		for (int j = 5; j > 0; j--) {
			int k = 0;
			while (esq6[j].ordinal() != j) {
				rotarIzq(esq6, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return (short) (720 * a + b);
	}

	void setURFtoDLF(short idx) {
		int x;
		Esquina[] esq6 = { URF, UFL, ULB, UBR, DFR, DLF };
		Esquina[] otraEsq = { DBL, DRB };
		int b = idx % 720;
		int a = idx / 720;
		for (Esquina c : Esquina.values())
			cp[c.ordinal()] = DRB;
		for (int j = 1, k; j < 6; j++) {
			k = b % (j + 1);
			b /= j + 1;
			while (k-- > 0)
				rotarDer(esq6, 0, j);
		}
		x = 5;
		for (int j = DRB.ordinal(); j >= 0; j--)
			if (a - Cnk(j, x + 1) >= 0) {
				cp[j] = esq6[x];
				a -= Cnk(j, x-- + 1);
			}
		x = 0;
		for (int j = URF.ordinal(); j <= DRB.ordinal(); j++)
			if (cp[j] == DRB)
				cp[j] = otraEsq[x++];
	}

	int getURtoDF() {
		int a = 0, x = 0;
		Arista[] arst6 = new Arista[6];
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (ep[j].ordinal() <= DF.ordinal()) {
				a += Cnk(j, x + 1);
				arst6[x++] = ep[j];
			}
		int b = 0;
		for (int j = 5; j > 0; j--) {
			int k = 0;
			while (arst6[j].ordinal() != j) {
				rotarIzq(arst6, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return 720 * a + b;
	}

	void setURtoDF(int idx) {
		int x;
		Arista[] arst6 = { UR, UF, UL, UB, DR, DF };
		Arista[] otraArst = { DL, DB, FR, FL, BL, BR };
		int b = idx % 720;
		int a = idx / 720;
		for (Arista e : Arista.values())
			ep[e.ordinal()] = BR;
		for (int j = 1, k; j < 6; j++) {
			k = b % (j + 1);
			b /= j + 1;
			while (k-- > 0)
				rotarDer(arst6, 0, j);
		}
		x = 5;
		for (int j = BR.ordinal(); j >= 0; j--)
			if (a - Cnk(j, x + 1) >= 0) {
				ep[j] = arst6[x];
				a -= Cnk(j, x-- + 1);
			}
		x = 0;
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (ep[j] == BR)
				ep[j] = otraArst[x++];
	}

	public static int getURtoDF(short idx1, short idx2) {
		Piezas a = new Piezas();
		Piezas b = new Piezas();
		a.setURtoUL(idx1);
		b.setUBtoDF(idx2);
		for (int i = 0; i < 8; i++) {
			if (a.ep[i] != BR)
				if (b.ep[i] != BR)// collision
					return -1;
				else
					b.ep[i] = a.ep[i];
		}
		return b.getURtoDF();
	}

	short getURtoUL() {
		int a = 0, x = 0;
		Arista[] arst3 = new Arista[3];
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (ep[j].ordinal() <= UL.ordinal()) {
				a += Cnk(j, x + 1);
				arst3[x++] = ep[j];
			}
		int b = 0;
		for (int j = 2; j > 0; j--) {
			int k = 0;
			while (arst3[j].ordinal() != j) {
				rotarIzq(arst3, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return (short) (6 * a + b);
	}

	void setURtoUL(short idx) {
		int x;
		Arista[] arst3 = { UR, UF, UL };
		int b = idx % 6;
		int a = idx / 6;
		for (Arista e : Arista.values())
			ep[e.ordinal()] = BR;
		for (int j = 1, k; j < 3; j++) {
			k = b % (j + 1);
			b /= j + 1;
			while (k-- > 0)
				rotarDer(arst3, 0, j);
		}
		x = 2;
		for (int j = BR.ordinal(); j >= 0; j--)
			if (a - Cnk(j, x + 1) >= 0) {
				ep[j] = arst3[x];
				a -= Cnk(j, x-- + 1);
			}
	}

	short getUBtoDF() {
		int a = 0, x = 0;
		Arista[] arst3 = new Arista[3];
		for (int j = UR.ordinal(); j <= BR.ordinal(); j++)
			if (UB.ordinal() <= ep[j].ordinal()
					&& ep[j].ordinal() <= DF.ordinal()) {
				a += Cnk(j, x + 1);
				arst3[x++] = ep[j];
			}
		int b = 0;
		for (int j = 2; j > 0; j--) {
			int k = 0;
			while (arst3[j].ordinal() != UB.ordinal() + j) {
				rotarIzq(arst3, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return (short) (6 * a + b);
	}

	void setUBtoDF(short idx) {
		int x;
		Arista[] arst3 = { UB, DR, DF };
		int b = idx % 6;
		int a = idx / 6;
		for (Arista e : Arista.values())
			ep[e.ordinal()] = BR;
		for (int j = 1, k; j < 3; j++) {
			k = b % (j + 1);
			b /= j + 1;
			while (k-- > 0)
				rotarDer(arst3, 0, j);
		}
		x = 2;
		for (int j = BR.ordinal(); j >= 0; j--)
			if (a - Cnk(j, x + 1) >= 0) {
				ep[j] = arst3[x];
				a -= Cnk(j, x-- + 1);
			}
	}

	int getURFtoDLB() {
		Esquina[] perm = new Esquina[8];
		int b = 0;
		for (int i = 0; i < 8; i++)
			perm[i] = cp[i];
		for (int j = 7; j > 0; j--) {
			int k = 0;
			while (perm[j].ordinal() != j) {
				rotarIzq(perm, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return b;
	}

	void setURFtoDLB(int idx) {
		Esquina[] perm = { URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB };
		int k;
		for (int j = 1; j < 8; j++) {
			k = idx % (j + 1);
			idx /= j + 1;
			while (k-- > 0)
				rotarDer(perm, 0, j);
		}
		int x = 7;
		for (int j = 7; j >= 0; j--)
			cp[j] = perm[x--];
	}

	int getURtoBR() {
		Arista[] perm = new Arista[12];
		int b = 0;
		for (int i = 0; i < 12; i++)
			perm[i] = ep[i];
		for (int j = 11; j > 0; j--) {
			int k = 0;
			while (perm[j].ordinal() != j) {
				rotarIzq(perm, 0, j);
				k++;
			}
			b = (j + 1) * b + k;
		}
		return b;
	}

	void setURtoBR(int idx) {
		Arista[] perm = { UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR };
		int k;
		for (int j = 1; j < 12; j++) {
			k = idx % (j + 1);
			idx /= j + 1;
			while (k-- > 0)
				rotarDer(perm, 0, j);
		}
		int x = 11;
		for (int j = 11; j >= 0; j--)
			ep[j] = perm[x--];
	}

	/**
	 * Comprueba que el cubo sea solucionable.
	 * 
	 * @return Código de error.
	 */
	int verificar() {
		int sum = 0;
		int[] arstCount = new int[12];
		for (Arista e : Arista.values())
			arstCount[ep[e.ordinal()].ordinal()]++;
		for (int i = 0; i < 12; i++)
			if (arstCount[i] != 1)
				return -2; // Arsiatas repetidas.
		for (int i = 0; i < 12; i++)
			sum += eo[i];
		if (sum % 2 != 0)
			return -3; // Orientacion de las arsitas erronea.
		int[] esqCount = new int[8];
		for (Esquina c : Esquina.values())
			esqCount[cp[c.ordinal()].ordinal()]++;
		for (int i = 0; i < 8; i++)
			if (esqCount[i] != 1)
				return -4; // Esquinas repetidas.
		sum = 0;
		for (int i = 0; i < 8; i++)
			sum += co[i];
		if (sum % 3 != 0)
			return -5; // Orientacion de las esquinas erronea.
		if ((arstParidad() ^ esqParidad()) != 0)
			return -6; // Error de paridad.
		return 0; // Cubo solucionable (hasta ahora).
	}
}
