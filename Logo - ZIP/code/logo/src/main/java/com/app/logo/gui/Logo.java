/**
 * 
 */
package com.app.logo.gui;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
import javax.swing.UIManager;

import com.app.logo.gui.components.VentanaPrincipal;

public class Logo {

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			new VentanaPrincipal();
		} catch (Exception e) {
		}
	}
}
