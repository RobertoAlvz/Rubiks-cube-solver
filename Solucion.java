package RCS;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;

/**
 * Despliega la solución en una animación interactiva.
 * 
 * @author roberto
 * 
 */
public class Solucion {
	Solucion(String s) {
		final String sol = s;
		final int vs = Constructor.getV(), vd = (int) ((1.5)*vs);
		AnimCube a = new AnimCube();
		final JFrame frame = new JFrame("Solucion");
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
				if (k.equals("move"))
					return sol;
				if (k.equals("initrevmove"))
					return "#";
				if (k.equals("speed"))
					return String.valueOf(vs);
				if (k.equals("doublespeed"))
					return String.valueOf(vd);
				if (k.equals("edit"))
					return "0";
				if (k.equals("colorscheme"))
					return "WYROGB";
				if (k.equals("movetext"))
					return "1";
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
}
