package RCS;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Interfaz principal.
 * 
 * @author roberto
 * 
 */
public class Constructor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Tablero t = new Tablero();
	static Estado es = new Estado();
	static int v=40;

	Constructor() {
		super("Solucionador del Cubo de Rubik");
		JMenu fileMenu = new JMenu("Archivo"), helpMenu = new JMenu("Ayuda")
				, toolMenu = new JMenu("Herramientas"), prefM  = new JMenu("Preferencias");
		JMenuItem abrir = new JMenuItem("Abrir"), help = new JMenuItem("Ayuda"), 
				about = new JMenuItem("Acerca"), virtual = new JMenuItem("Cubo virtual"),
				vel = new JMenuItem("Velocidad"), exit = new JMenuItem("Salir"),
				save = new JMenuItem("Guardar");
		fileMenu.add(abrir);
		fileMenu.add(save);
		fileMenu.add(exit);
		prefM.add(vel);
		toolMenu.add(virtual);
		helpMenu.add(help);
		helpMenu.add(about);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		vel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				velSlider vS = new velSlider();
				vS.setVisible(true);
				vS.setSize(300, 100);
				vS.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
			class velSlider extends JFrame{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				velSlider(){
					super("Velocidad");
					final JSlider vs =new JSlider(SwingConstants.HORIZONTAL, 1, 100,100-v);
					//JLabel aux = new JLabel("Rápido ... Lento");
			        vs.setMajorTickSpacing(10);
			        vs.setPaintTicks(true);
			        vs.addChangeListener(new ChangeListener(){
			        	public void stateChanged(ChangeEvent e){
			                v=100-vs.getValue();
			            }
			        });
			        //add(aux);
			        add(vs);
				}
			}
		});
		virtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimCube a = new AnimCube();
				final JFrame frame = new JFrame("Cubo virtual");
				frame.setSize(500, 500);
				a.setStub(new AppletStub() {
					public void appletResize(int w, int h) {
					}

					public AppletContext getAppletContext() {
						return null;
					}

					public URL getCodeBase() {
						try {
							return new File(".").toURI().toURL();
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}

					public URL getDocumentBase() {
						return getCodeBase();
					}

					public String getParameter(String k) {
						if (k.equals("speed"))
							return "40";
						if (k.equals("doublespeed"))
							return "60";
						if (k.equals("edit"))
							return "1";
						if (k.equals("colorscheme"))
							return "WYROGB";
						if (k.equals("bgcolor"))
							return "cccccc";
						return null;
					}

					public boolean isActive() {
						return true;
					}
				});
				a.init();
				a.start();
				frame.setContentPane(a);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "No.");
			}
		});
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Autor: Roberto Álvarez Castro");
			}
		});
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser(System
						.getProperty("user.home"));
				fc.setDialogTitle("Escoger archivo.");
				fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES",
						"txt", "text"));
				fc.showOpenDialog(null);
				String c = leerInstancia(fc.getSelectedFile());
				int i = verificar(c);
				if (i != 0) {
					System.out.println("Ocurrio un error.");
					switch (Math.abs(i)) {
					case 1:
						JOptionPane.showMessageDialog(null,
								"Sobran o faltan colores.");
						break;
					case 2:
						JOptionPane.showMessageDialog(null,
								"Sobran o faltan aristas.");
						break;
					case 3:
						JOptionPane.showMessageDialog(null,
								"Una arista mal orientada.");
						break;
					case 4:
						JOptionPane.showMessageDialog(null,
								"Sobran o faltan esquinas.");
						break;
					case 5:
						JOptionPane.showMessageDialog(null,
								"Una esquina mal rotada.");
						break;
					case 6:
						JOptionPane.showMessageDialog(null, "Eror de paridad.");
						break;
					}
				} else t.setFromString(c);
			}
		});
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String c = t.toStringCod();
				JFileChooser fc = new JFileChooser(System
						.getProperty("user.home"));
				fc.setDialogTitle("Guardar archivo.");
				fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES",
						"txt", "text"));
				fc.showSaveDialog(null);
				File f = fc.getSelectedFile();
				try{
					f.createNewFile();
					FileWriter wf = new FileWriter(f,false);
					BufferedWriter bw = new BufferedWriter(wf);
					bw.write(c);
					bw.close();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Eror al escribir archivo.");
				}
			}
		});
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(fileMenu);
		bar.add(prefM);
		bar.add(toolMenu);
		bar.add(helpMenu);
		setLayout(new BorderLayout());
		add(t, BorderLayout.CENTER);
		add(es, BorderLayout.SOUTH);
	}

	/**
	 * Comprueba que una cadena represente un cubo solucionable.
	 * 
	 * @param s
	 *            Representación del cubo como cadena.
	 * @return Código de error.
	 */
	public static int verificar(String s) {
		Caras fc = new Caras(s);
		Piezas cc = fc.aPiezas();
		return cc.verificar();
	}

	/**
	 * Traduce notación de color a notación de orientación.
	 * 
	 * @param c
	 *            Color
	 * @return Orientación.
	 */
	public static String cod(char c) {
		if (c == 'B')
			return "U";
		else if (c == 'R')
			return "F";
		else if (c == 'Z')
			return "R";
		else if (c == 'V')
			return "L";
		else if (c == 'N')
			return "B";
		else if (c == 'A')
			return "D";
		else
			return "X";
	}
	
	/**
	 * Traduce notación de color a notación de orientación.
	 * 
	 * @param c
	 *            Color
	 * @return Orientación.
	 */
	public static String codS(String s){
		String r="";
		for(char c: s.toCharArray()) r+=cod(c);
		return r;
	}

	/**
	 * Lee la instancia desde archivo.
	 * 
	 * @param f
	 *            Archivo a leer.
	 * @return Cadena representativa.
	 */
	public static String leerInstancia(File f) {
		try {
			FileInputStream archivo = new FileInputStream(f);
			int aux;
			char auxs;
			String cubo = "";
			while ((aux = archivo.read()) != -1) {
				auxs = (char) aux;
				if (auxs == 'X') {
					System.out.println("Instancia incompleta.");
					archivo.close();
					return "";
				} else if (auxs == 'A' || auxs == 'B' || auxs == 'N'
						|| auxs == 'R' || auxs == 'V' || auxs == 'Z')
					cubo += cod(auxs);
				else if (auxs != '\n' && auxs != ' ' && auxs != '\r') {
					System.out.println("Caracter no valido." + auxs);
					archivo.close();
					return "";
				}
			}
			archivo.close();
			return cubo;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return "";
		}
	}

	/**
	 * Velocidad actual.
	 * @return Valor del índice de velocidad.
	 */
	public static int getV(){
		return v;
	}
	
	/**
	 * Tablero de botones.
	 * @return Tablero donde se representa el cubo.
	 */
	public static Tablero getTablero(){
		return t;
	}
	
	/**
	 * Panel de estado.
	 * @return Panel de estado actual.
	 */
	public static Estado getEstado(){
		return es;
	}

	

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error con el L&F!\n" + e, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		Constructor c = new Constructor();
		c.setVisible(true);
		c.setSize(700, 600);
		c.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
