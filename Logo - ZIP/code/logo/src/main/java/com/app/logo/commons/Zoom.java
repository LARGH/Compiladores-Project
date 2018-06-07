/**
 * 
 */
package com.app.logo.commons;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class Zoom {
	private double factor = 0.0;

	public Zoom() {
	}

	public Zoom(double factor) {
		this.factor = factor;
	}

	/**
	 * @return the factor
	 */
	public double getFactor() {
		return factor;
	}

	/**
	 * @param factor
	 *            the factor to set
	 */
	public void setFactor(double factor) {
		this.factor = factor;
	}

}
