package RCS;

import javax.swing.JButton;
import javax.swing.JLabel;


/**
 * Hilo que reescribira el estado mientras se realiza la búsqueda.
 * @author roberto
 *
 */
public class Esperador extends Thread{
	Buscador b;
	JLabel l;
	JButton bt;
	
	Esperador(Buscador b, JLabel l, JButton bt){
		this.b=b;
		this.l=l;
		this.bt=bt;
	}
	
	public void run(){
		String auxs = "";
		bt.setEnabled(false);
		b.start();
		while (b.isAlive()) {
			l.setText("Buscando" + auxs);
			l.repaint();
			auxs += ".";
			if (auxs.equals("...."))
				auxs = "";
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
			}
		}
		bt.setEnabled(true);
		l.setText("Solucion encontrada.");
		l.repaint();
		new Solucion(b.getSol());
	}
}
