/**
 * 
 */
package com.app.logo.interprete;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;

import com.app.logo.commons.Linea;
import com.app.logo.configuracion.Configuracion;

public class MaquinaDePila {

	private int contadorDePrograma;
	private ArrayList memoria;
	private Stack pila;
	private TablaDeSimbolos tabla;
	private boolean stop = false;
	private boolean returning = false;
	private Stack pilaDeMarcos;
	private Configuracion configuracionActual;

	public MaquinaDePila(TablaDeSimbolos tabla) {
		configuracionActual = new Configuracion();
		contadorDePrograma = 0;
		memoria = new ArrayList();
		pila = new Stack();
		this.tabla = tabla;
		pilaDeMarcos = new Stack();
	}

	public int numeroDeElementos() {
		return memoria.size() + 1;
	}

	// Funciones que se escriben en memoria
	public int agregarOperacion(String nombre) {
		int posicion = memoria.size();
		try {
			memoria.add(this.getClass().getDeclaredMethod(nombre, null));
			return posicion;
		} catch (Exception e) {
			System.out.println("Error al agregar operación " + nombre + ". ");
		}
		return -1;
	}

	public int agregar(Object objeto) {
		int posicion = memoria.size();
		memoria.add(objeto);
		return posicion;
	}

	public void agregar(Object objeto, int posicion) {
		memoria.remove(posicion);
		memoria.add(posicion, objeto);
	}

	public int agregarOperacionEn(String nombre, int posicion) {
		try {
			memoria.add(posicion,
					this.getClass().getDeclaredMethod(nombre, null));
		} catch (Exception e) {
			System.out.println("Error al agregar operación " + nombre + ". ");
		}
		return posicion;
	}

	// Funciones que la máquina ejecuta sobre la pila
	private void SUM() {
		Object matriz2 = pila.pop();
		Object matriz1 = pila.pop();
		pila.push(new Double(((Double) matriz1).doubleValue()
				+ ((Double) matriz2).doubleValue()));
	}

	private void RES() {
		Object matriz2 = pila.pop();
		Object matriz1 = pila.pop();
		pila.push(new Double(((Double) matriz1).doubleValue()
				- ((Double) matriz2).doubleValue()));
	}

	private void MUL() {
		Object matriz2 = pila.pop();
		Object matriz1 = pila.pop();
		pila.push(new Double(((Double) matriz1).doubleValue()
				* ((Double) matriz2).doubleValue()));
	}

	private void negativo() {
		Object matriz1 = pila.pop();
		System.out.println(matriz1);
		pila.push(new Double(-((Double) matriz1).doubleValue()));
	}

	private void constPush() {
		pila.push(memoria.get(++contadorDePrograma));
	}

	private void varPush() {
		pila.push(memoria.get(++contadorDePrograma));
	}

	private void varPush_Eval() {
		pila.push(tabla.encontrar((String) memoria.get(++contadorDePrograma)));
	}

	private void asignar() {
		String variable = (String) pila.pop();
		Object objeto = pila.pop();
		tabla.insertar(variable, objeto);
	}

	private void EQ() {
		Object A = pila.pop();
		Object B = pila.pop();
		pila.push(new Boolean(((Double) A).doubleValue() == ((Double) B)
				.doubleValue()));
	}

	private void NE() {
		Object A = pila.pop();
		Object B = pila.pop();
		pila.push(new Boolean(((Double) A).doubleValue() != ((Double) B).doubleValue()));
	}

	private void LE() {
		double a;
		double b;
		Object B = pila.pop();
		Object A = pila.pop(); // Se sacan en orden inverso por la forma de la
								// pila
		a = ((Double) A).doubleValue();
		b = ((Double) B).doubleValue();
		pila.push(new Boolean(a < b));
	}

	private void GR() {
		double a;
		double b;
		Object B = pila.pop();
		Object A = pila.pop(); // Se sacan en orden inverso por la forma de la
								// pila
		a = ((Double) A).doubleValue();
		b = ((Double) B).doubleValue();
		pila.push(new Boolean(a > b));
	}

	private void LQ() {
		double a;
		double b;
		Object B = pila.pop();
		Object A = pila.pop(); // Se sacan en orden inverso por la forma de la
								// pila
		a = ((Double) A).doubleValue();
		b = ((Double) B).doubleValue();
		pila.push(new Boolean(a <= b));
	}

	private void GE() {
		double a;
		double b;
		Object B = pila.pop();
		Object A = pila.pop(); // Se sacan en orden inverso por la forma de la
								// pila
		a = ((Double) A).doubleValue();
		b = ((Double) B).doubleValue();
		pila.push(new Boolean(a >= b));
	}

	private void NOT() {
		pila.push(new Boolean(!((Boolean) pila.pop()).booleanValue()));
	}

	private void AND() {
		pila.push(new Boolean(((Boolean) pila.pop()).booleanValue()
				&& ((Boolean) pila.pop()).booleanValue()));
	}

	private void OR() {
		pila.push(new Boolean(((Boolean) pila.pop()).booleanValue()
				|| ((Boolean) pila.pop()).booleanValue()));
	}

	private void stop() {
		stop = true;
	}

	private void _return() {
		returning = true;
	}

	private void nop() {
	}

	private void WHILE() {
		int condicion = contadorDePrograma;
		boolean continua = true;
		while (continua && !returning) {
			ejecutar(condicion + 3);
			if (((Boolean) pila.pop()).booleanValue()) { // lee el resultado de la condición de
										// la pila
				ejecutar(((Integer) memoria.get(condicion + 1)).intValue());// Ejecuta
																		// el
																		// cuerpo
			} else {
				contadorDePrograma = ((Integer) memoria.get(condicion + 2)).intValue();
				continua = false;
			}
		}
	}

	private void IF_ELSE() {
		int condicion = contadorDePrograma;
		ejecutar(condicion + 4); // Evalúa la condicion
		boolean resultado = true;
		try {
			resultado = ((Boolean) pila.pop()).booleanValue();
		} catch (Exception e) {
		}
		if (resultado) { // lee el resultado de la condición de la pila
			ejecutar(((Integer) memoria.get(condicion + 1)).intValue());// Ejecuta
																		// el
																		// cuerpo
		} else {
			ejecutar(((Integer) memoria.get(condicion + 2)).intValue());
		}
		contadorDePrograma = ((Integer) memoria.get(condicion + 3)).intValue() - 1; // El
																					// -1
																					// es
		// para
		// corregir
		// el
		// aumento
		// del cp al
		// final de
		// cada
		// instrucción
	}

	private void FOR() {
		int condicion = contadorDePrograma;
		ejecutar(condicion + 5); // Ejecutamos la primera parte
		boolean continua = true;
		while (continua && !returning) {
			ejecutar(((Integer) memoria.get(condicion + 1)).intValue()); // evaluamos
																			// la
			// condición
			if (((Boolean) pila.pop()).booleanValue()) { // lee el resultado de
															// la condición de
				// la pila
				ejecutar(((Integer) memoria.get(condicion + 3)).intValue());// Ejecuta
																			// el
																			// cuerpo
				ejecutar(((Integer) memoria.get(condicion + 2)).intValue());// Ejecuta
																			// la
																			// última
				// parte del for
			} else {
				contadorDePrograma = ((Integer) memoria.get(condicion + 4))
						.intValue();
				continua = false;
			}
		}
	}

	private void declaracion() {
		tabla.insertar((String) memoria.get(++contadorDePrograma),
				new Integer(++contadorDePrograma)); // Apuntamos a la primera instrucción de
										// la función
		int invocados = 0;
		while (memoria.get(contadorDePrograma) != null || invocados != 0) { // Llevamos
																			// cp
																			// hasta
																			// la
																			// siguiente
																			// instrucción
																			// después
																			// de
																			// la
																			// declaración
			if (memoria.get(contadorDePrograma) instanceof Method)
				if (((Method) memoria.get(contadorDePrograma)).getName()
						.equals("invocar"))
					invocados++;
			if (memoria.get(contadorDePrograma) instanceof Funcion)
				invocados++;
			if (memoria.get(contadorDePrograma) == null)
				invocados--;
			contadorDePrograma++;
		}
	}

	private void invocar() {
		Marco marco = new Marco();
		String nombre = (String) memoria.get(++contadorDePrograma);
		marco.setNombre(nombre);
		contadorDePrograma++;
		while (memoria.get(contadorDePrograma) != null) { // Aquí también usamos
															// null como
															// delimitador. Aquí
															// se agregan los
															// parámetros al
															// marco
			if (memoria.get(contadorDePrograma) instanceof String) {
				if (((String) (memoria.get(contadorDePrograma)))
						.equals("Limite")) {
					Object parametro = pila.pop();
					marco.agregarParametro(parametro);
					contadorDePrograma++;
				}
			} else {
				ejecutarInstruccion(contadorDePrograma);
			}

		}
		marco.setRetorno(contadorDePrograma);
		pilaDeMarcos.add(marco);
		ejecutarFuncion(((Integer) tabla.encontrar(nombre)).intValue()); // VAMOS
		// AQUI***************************
	}

	private void push_parametro() {
		pila.push(((Marco) pilaDeMarcos.lastElement()).getParametro(
				((Integer) memoria.get(++contadorDePrograma)).intValue() - 1));
	}

	// Métodos para la ejecución
	public void imprimirMemoria() {
		for (int i = 0; i < memoria.size(); i++)
			System.out.println("" + i + ": " + memoria.get(i));
	}

	public void ejecutar() {
		// imprimirMemoria();
		stop = false;
		while (contadorDePrograma < memoria.size())
			ejecutarInstruccion(contadorDePrograma);
	}

	public boolean ejecutarSiguiente() {
		// imprimirMemoria();
		if (contadorDePrograma < memoria.size()) {
			ejecutarInstruccion(contadorDePrograma);
			return true;
		}
		return false;
	}

	public void ejecutar(int indice) {// ejecuta hasta que se encuentra Stop
		contadorDePrograma = indice;
		while (!stop && !returning) {
			ejecutarInstruccion(contadorDePrograma);
		}
		stop = false;
	}

	public void ejecutarFuncion(int indice) {
		contadorDePrograma = indice;
		while (!returning && memoria.get(contadorDePrograma) != null) {
			ejecutarInstruccion(contadorDePrograma);
		}
		returning = false;
		contadorDePrograma = ((Marco) pilaDeMarcos.lastElement()).getRetorno();
		pilaDeMarcos.removeElement(pilaDeMarcos.lastElement());
	}

	public void ejecutarInstruccion(int indice) {
		// System.out.println("Ejecutando: " + indice);
		try {
			Object objetoLeido = memoria.get(indice);
			if (objetoLeido instanceof Method) {
				Method metodo = (Method) objetoLeido;
				metodo.invoke(this, null);
			}
			if (objetoLeido instanceof Funcion) {
				ArrayList parametros = new ArrayList();
				Funcion funcion = (Funcion) objetoLeido;
				contadorDePrograma++;
				while (memoria.get(contadorDePrograma) != null) { // Aquí
																	// también
																	// usamos
																	// null como
																	// delimitador.
																	// Aquí se
																	// agregan
																	// los
																	// parámetros
																	// al marco
					if (memoria.get(contadorDePrograma) instanceof String) {
						if (((String) (memoria.get(contadorDePrograma)))
								.equals("Limite")) {
							Object parametro = pila.pop();
							parametros.add(parametro);
							contadorDePrograma++;
						}
					} else {
						ejecutarInstruccion(contadorDePrograma);
					}

				}
				funcion.ejecutar(configuracionActual, parametros);
			}
			contadorDePrograma++;
		} catch (Exception e) {
		}
	}

	public Configuracion getConfiguracion() {
		return configuracionActual;
	}

	public static class Girar implements Funcion {
		public void ejecutar(Object A, ArrayList parametros) {
			Configuracion configuracion = (Configuracion) A;
			int angulo = (configuracion.getAngulo() + (int) ((Double) parametros
					.get(0)).doubleValue()) % 360;
			configuracion.setAngulo(angulo);
		}
	}

	public static class Avanzar implements Funcion {
		public void ejecutar(Object A,
				ArrayList parametros) {
			Configuracion configuracion = (Configuracion) A;
			int angulo = configuracion.getAngulo();
			double x0 = configuracion.getX();
			double y0 = configuracion.getY();
			double x1 = x0 + Math.cos(Math.toRadians(angulo))
					* ((Double) parametros.get(0)).doubleValue();
			double y1 = y0 + Math.sin(Math.toRadians(angulo))
					* ((Double) parametros.get(0)).doubleValue();
			configuracion.setPosicion(x1, y1);
			configuracion.agregarLinea(new Linea((int) x0, (int) y0, (int) x1,
					(int) y1, configuracion.getColor()));
		}
	}

	public static class CambiarColor implements Funcion {
		public void ejecutar(Object A,
				ArrayList parametros) {
			Configuracion configuracion = (Configuracion) A;
			configuracion.setColor(new Color((int) ((Double) parametros.get(0))
					.doubleValue() % 256, (int) ((Double) parametros.get(1))
					.doubleValue() % 256, (int) ((Double) parametros.get(2))
					.doubleValue() % 256));
		}
	}

}
