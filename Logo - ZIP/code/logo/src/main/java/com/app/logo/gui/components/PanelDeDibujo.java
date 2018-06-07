/**
 * 
 */
package com.app.logo.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.app.logo.commons.Linea;
import com.app.logo.commons.Zoomable;
import com.app.logo.configuracion.Configuracion;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class PanelDeDibujo extends JPanel implements Zoomable {

	private Configuracion configuracion;
	private double factor = 1.1;
	private boolean zoomer = false;

	public PanelDeDibujo() {
		configuracion = new Configuracion();
	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	public Configuracion getConfiguracion() {
		return this.configuracion;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		if (zoomer == true) {
			at.scale(this.factor, this.factor);
			zoomer = false;
		}
		g2.transform(at);

		ArrayList lineas = configuracion.getLineas();
		for (int i = 0; i < lineas.size(); i++) {
			int x0 = (((Propiedades.PANEL_DE_DIBUJO_ANCHO / 2) + ((Linea) lineas.get(i))
					.getX0()));
			int y0 = (((Propiedades.PANEL_DE_DIBUJO_LARGO / 2) - ((Linea) lineas.get(i))
					.getY0()));
			int x1 = (((Propiedades.PANEL_DE_DIBUJO_ANCHO / 2) + ((Linea) lineas.get(i))
					.getX1()));
			int y1 = (((Propiedades.PANEL_DE_DIBUJO_LARGO / 2) - ((Linea) lineas.get(i))
					.getY1()));
			g.setColor(((Linea) lineas.get(i)).getColor());
			g.drawLine(x0, y0, x1, y1);
		}
		g.setColor(Color.WHITE);
		Polygon triangulo = triangulo(configuracion.getX(),
				configuracion.getY(), configuracion.getAngulo());
		System.out.println("X: " + configuracion.getX() + "; Y: "
				+ configuracion.getY());
		g.drawPolygon(triangulo);
		g.fillPolygon(triangulo);
	}

	public Polygon triangulo(double x, double y, int angulo) {
		Polygon poligono = null;
		int xs[] = new int[3];
		int ys[] = new int[3];
		xs[0] = ((((Propiedades.PANEL_DE_DIBUJO_ANCHO) / 2) + (int) x));
		ys[0] = ((((Propiedades.PANEL_DE_DIBUJO_LARGO) / 2) - (int) y));
		xs[1] = ((((Propiedades.PANEL_DE_DIBUJO_ANCHO) / 2) + (int) (x - 10 * Math
				.cos(Math.toRadians(angulo + 20)))));
		ys[1] = ((((Propiedades.PANEL_DE_DIBUJO_LARGO) / 2) - (int) (y - +10
				* Math.sin(Math.toRadians(angulo + 20)))));
		xs[2] = ((((Propiedades.PANEL_DE_DIBUJO_ANCHO) / 2) + (int) (x - 10 * Math
				.cos(Math.toRadians(angulo - 20)))));
		ys[2] = ((((Propiedades.PANEL_DE_DIBUJO_LARGO) / 2) - (int) (y - +10
				* Math.sin(Math.toRadians(angulo - 20)))));
		poligono = new Polygon(xs, ys, 3);
		return poligono;
	}

	public void setFactor(double factor) {
		if (factor < this.factor) {
			this.factor = this.factor - 0.1;
		} else {
			this.factor = factor;
		}
		this.zoomer = true;
	}

	public double getFactor() {
		return factor;
	}

}
