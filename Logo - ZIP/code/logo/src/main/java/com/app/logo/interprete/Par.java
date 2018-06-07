/**
 * 
 */
package com.app.logo.interprete;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class Par {

	private String nombre;
	private Object objeto;

	public Par(String nombre, Object objeto) {
		this.nombre = nombre;
		this.objeto = objeto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

}
