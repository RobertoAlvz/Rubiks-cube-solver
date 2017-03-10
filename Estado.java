package RCS;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel de estado.
 * 
 * @author roberto
 * 
 */
public class Estado extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton b = new JButton("Resolver");
	JLabel l = new JLabel("En espera");

	Estado() {
		FlowLayout fl = new FlowLayout();
		l = new JLabel("En espera");
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String c = Constructor.getTablero().toString();
				int aux = 6;
				if ((aux = Constructor.verificar(c)) != 0) {
					if (aux == 1)
						l.setText("Sobran o faltan colores.");
					else if (aux == 2)
						l.setText("Sobran o faltan aristas.");
					else if (aux == 3)
						l.setText("Aristas mal orientadas.");
					else if (aux == 4)
						l.setText("Sobran o faltan esquinas.");
					else if (aux == 5)
						l.setText("Esquinas mal rotadas.");
					else
						l.setText("Eror de paridad.");
				} else {
					System.out.print(c);
					l.setText("Solucionable");
					Buscador bs = new Buscador(c);
					Esperador ep = new Esperador(bs, Constructor.getEstado().l, Constructor.getEstado().b);
					ep.start();
				}
			}
		});
		add(b);
		add(l);
	}
	
}
