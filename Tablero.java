package RCS;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Tablero de botones.
 * 
 * @author roberto
 * 
 */
public class Tablero extends JPanel {
	private JButton bot[], cl[];
	private Esq esquinas[];
	private Ars aristas[];
	private Icon colsI[], g;
	private String colS[] = { "B", "Z", "R", "A", "V", "N" };
	private int[] nColA, nColE;
	private int col;

	/**
	 * Inicializa la matriz de botones.
	 */
	Tablero() {
		nColA = new int[6];
		nColE = new int[6];
		colsI = new ImageIcon[6];
		bot = new JButton[54];
		cl = new JButton[6];
		for (int i = 0; i < 6; i++) {
			nColA[i] = 4;
			nColE[i] = 4;
			colsI[i] = new ImageIcon(getClass().getResource(
					Integer.toString(i + 1) + ".jpg"));
			cl[i] = new JButton();
			cl[i].setIcon(colsI[i]);
			cl[i].setPreferredSize(new Dimension(50, 50));
			cl[i].addActionListener(new changeCol(i));
		}
		g = new ImageIcon(getClass().getResource("0.jpg"));
		for (int i = 0; i < 54; i++) {
			bot[i] = new JButton("");
			bot[i].setIcon(g);
			bot[i].setPreferredSize(new Dimension(50, 50));
		}
		bot[4].setIcon(colsI[0]);
		bot[13].setIcon(colsI[1]);
		bot[22].setIcon(colsI[2]);
		bot[31].setIcon(colsI[3]);
		bot[40].setIcon(colsI[4]);
		bot[49].setIcon(colsI[5]);
		esquinas = new Esq[8];
		aristas = new Ars[12];
		esquinas[0] = new Esq(0, 36, 47);
		esquinas[1] = new Esq(2, 11, 45);
		esquinas[2] = new Esq(6, 18, 38);
		esquinas[3] = new Esq(8, 20, 9);
		esquinas[4] = new Esq(27, 24, 44);
		esquinas[5] = new Esq(29, 26, 15);
		esquinas[6] = new Esq(33, 42, 53);
		esquinas[7] = new Esq(35, 17, 51);
		aristas[0] = new Ars(1, 46);
		aristas[1] = new Ars(3, 37);
		aristas[2] = new Ars(5, 10);
		aristas[3] = new Ars(7, 19);
		aristas[4] = new Ars(12, 23);
		aristas[5] = new Ars(14, 48);
		aristas[6] = new Ars(16, 32);
		aristas[7] = new Ars(21, 41);
		aristas[8] = new Ars(25, 28);
		aristas[9] = new Ars(30, 43);
		aristas[10] = new Ars(34, 52);
		aristas[11] = new Ars(39, 50);
		JPanel colP[] = new JPanel[6], cub, clP;
		clP = new JPanel(new GridLayout(3, 3, 5, 5));
		cub = new JPanel(new GridLayout(3, 4, 5, 5));
		for (int i = 0; i < 6; i++) {
			colP[i] = new JPanel(new GridLayout(3, 3, 5, 5));
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++)
				colP[j].add(bot[i + 9 * j]);
			clP.add(i < 6 ? cl[i] : new JPanel());
		}
		cub.add(new JPanel());
		cub.add(colP[0]);
		cub.add(new JPanel());
		cub.add(clP);
		cub.add(colP[4]);
		cub.add(colP[2]);
		cub.add(colP[1]);
		cub.add(colP[5]);
		cub.add(new JPanel());
		cub.add(colP[3]);
		add(cub);
	}

	/**
	 * Representación del estado actual de los botones como cadena.
	 */
	public String toString() {
		String r = "";
		Icon aux;
		for (int i = 0; i < 54; i++) {
			aux = bot[i].getIcon();
			r += aux == colsI[0] ? "U" : aux == colsI[1] ? "R"
					: aux == colsI[2] ? "F" : aux == colsI[3] ? "D"
							: aux == colsI[4] ? "L" : aux == colsI[5] ? "B"
									: "X";
		}
		System.out.println(r);
		return r;
	}
	
	/**
	 * Representación del estado actual de los botones como cadena codificada.
	 */
	public String toStringCod(){
		String r = "";
		Icon aux;
		for (int i = 0; i < 54; i++) {
			aux = bot[i].getIcon();
			r += aux == colsI[0] ? "B" : aux == colsI[1] ? "Z"
					: aux == colsI[2] ? "R" : aux == colsI[3] ? "A"
							: aux == colsI[4] ? "V" : aux == colsI[5] ? "N"
									: "X";
		}
		System.out.println(r);
		return r;
	}
	
	/**
	 * Representa al cubo en el tablero a partir de una representación como cadena.
	 */
	public void setFromString(String s){
		char[] cs = s.toCharArray();
		for(int i=0; i<54; i++)	bot[i].setIcon(colsI[codC(cs[i])]);
		for(Ars a: aristas) a.update();
		for(Esq e: esquinas) e.update();
	}
	
	private int codC(char c){
		return c=='U'? 0: c=='R'? 1: c=='F'? 2: c=='D'? 3: c=='L'? 4: c=='B'? 5: 6; 
	}
	/**
	 * Representa al cubo en el tablero a partir de una representación como cadena codificada.
	 */
	public void setFromStringCod(String s){
		setFromString(Constructor.codS(s));
	}

	/**
	 * Color seleccionado para el coloreo.
	 * 
	 * @return Código de color (0 blanco, 1 azul, 2 rojo, 3 amarillo, 4 verde, 5
	 *         naranja).
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Implementa el cambio en el color seleccionado
	 * 
	 * @author roberto
	 * 
	 */
	class changeCol implements ActionListener {
		int i;

		changeCol(int i) {
			this.i = i;
		}

		public void actionPerformed(ActionEvent e) {
			col = i;
		}
	}

	/**
	 * Representa pares de botones como una arista.
	 * 
	 * @author roberto
	 * 
	 */
	class Ars {
		String[] c = new String[2];
		int id[] = new int[2], a, b;

		Ars(int a, int b) {
			this.a=a;
			this.b=b;
			bot[a].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c[0] = colS[getCol()];
					((JButton) e.getSource()).setIcon(colsI[getCol()]);
				}
			});
			bot[b].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c[1] = colS[getCol()];
					((JButton) e.getSource()).setIcon(colsI[getCol()]);
				}
			});
			id[0] = a;
			id[1] = b;
			for (int i = 0; i < 2; i++)
				c[i] = "";
		}

		/**
		 * Representación de la arista como cadena.
		 */
		public String toString() {
			return (c[0].equals("") || c[1].equals("")) ? "" : ordenar(c[0],
					c[1]);
		}

		public void update(){
			for(int i=0; i<6; i++){
				if(bot[a].getIcon()==colsI[i]) c[0]=colS[i];
				if(bot[b].getIcon()==colsI[i]) c[1]=colS[i];
			}
		}
	}

	/**
	 * Representa tercias de botones como una esquina.
	 * 
	 * @author roberto
	 * 
	 */
	class Esq {
		int id[] = new int[3], a, b, c;
		String[] cs = new String[3], esqPos = { "BZR", "BZN", "BVN", "BRV",
				"ZRA", "ZAN", "RAV", "AVN" };

		Esq(int a, int b, int c) {
			this.a=a;
			this.b=b;
			this.c=c;
			bot[a].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cs[0] = colS[getCol()];
					((JButton) e.getSource()).setIcon(colsI[getCol()]);
				}
			});
			bot[b].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cs[1] = colS[getCol()];
					((JButton) e.getSource()).setIcon(colsI[getCol()]);
				}
			});
			bot[c].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cs[2] = colS[getCol()];
					((JButton) e.getSource()).setIcon(colsI[getCol()]);
				}
			});
			id[0] = a;
			id[1] = b;
			id[2] = c;
			for (int i = 0; i < 3; i++)
				cs[i] = "X";
		}

		
		/**
		 * Representación de la esquina como cadena.
		 */
		public String toString() {
			return ordenar(cs[0], cs[1], cs[2]);
		}

		public void update(){
			for(int i=0; i<6; i++){
				if(bot[a].getIcon()==colsI[i]) cs[0]=colS[i];
				if(bot[b].getIcon()==colsI[i]) cs[1]=colS[i];
				if(bot[c].getIcon()==colsI[i]) cs[2]=colS[i];
			}
		}
	}

	/**
	 * Ordena según el orden de asignación de colores.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public String ordenar(String a, String b) {
		return min(a, b) + max(a, b);
	}

	/**
	 * Ordena según el orden de asignación de colores.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public String ordenar(String a, String b, String c) {
		return min(a, min(b, c)) + med(a, b, c) + max(a, max(b, c));
	}

	/**
	 * Ordena según el orden de asignación de colores.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public String min(String a, String b) {
		return (a.equals("A")) ? b : a.equals("Z") ? (b.equals("A") ? a : b)
				: a.equals("R") ? ((b.equals("A") || b.equals("Z")) ? a : b)
						: a.equals("A") ? ((b.equals("A") || b.equals("Z") || b
								.equals("R")) ? a : b)
								: a.equals("V") ? ((b.equals("A")
										|| b.equals("Z") || b.equals("R") || b
										.equals("A")) ? a : b)
										: (a.equals("N") ? a : b);
	}

	/**
	 * Ordena según el orden de asignación de colores.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public String max(String a, String b) {
		return a.equals(min(a, b)) ? b : a;
	}

	/**
	 * Ordena según el orden de asignación de colores.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public String med(String a, String b, String c) {
		return a.equals(min(a, min(b, c))) ? min(b, c) : b.equals(min(a,
				min(b, c))) ? min(a, c) : min(a, b);
	}
}
