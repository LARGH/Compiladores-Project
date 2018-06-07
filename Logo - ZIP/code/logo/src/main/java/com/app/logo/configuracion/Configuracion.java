/**
 * 
 */
package com.app.logo.configuracion;

import java.awt.Color;
import java.util.ArrayList;

import com.app.logo.commons.Linea;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class Configuracion {

	ArrayList lineas;
	double x;
	double y;
	int angulo;
	Color color;

	public Configuracion() {
		x = 0.0;
		y = 0.0;
		lineas = new ArrayList();
		color = Color.WHITE;
	}

	public void agregarLinea(Linea linea) {
		lineas.add(linea);
	}

	public void setPosicion(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void limpiar() {
		lineas.clear();
	}

	public ArrayList getLineas() {
		return lineas;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		String valor = "";
		for (int i = 0; i < lineas.size(); i++)
			valor += lineas.get(i) + ", ";
		valor += "x:" + x + " y:" + y + " angulo: " + angulo;
		return valor;
	}

}
