/**
 * 
 */
package com.app.logo.interprete;

import java.util.ArrayList;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class Marco {

	private ArrayList parametros;
	private int retorno;
	private String nombre;

	public Marco() {
		parametros = new ArrayList();
		retorno = 0;
		nombre = null;
	}

	public void agregarParametro(Object parametro) {
		parametros.add(parametro);
	}

	public Object getParametro(int integer) {
		return parametros.get(integer);
	}

	public void setParametros(ArrayList parametros) {
		this.parametros = parametros;
	}

	public int getRetorno() {
		return retorno;
	}

	public void setRetorno(int retorno) {
		this.retorno = retorno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
