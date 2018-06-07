/**
 * 
 */
package com.app.logo.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.app.logo.commons.Zoomable;
import com.app.logo.interprete.Parser;

/**
 * @author Rafael
 * @author Jesús
 * 
 */
public class VentanaPrincipal extends JFrame {

    JTextArea areaDeCodigo;
    JScrollPane scrollCodigo;
    PanelDeDibujo panelDeDibujo;
    JButton ejecutar;
    JButton limpiar;
    JSlider zoom;
    Parser parser;
    Zoomable zoomPanel;
    boolean modoDebug;
    
    public VentanaPrincipal(){
        
        super("Proyecto Compiladores Logo");
        
        modoDebug = false;
        
        parser = new Parser();
        parser.insertarInstrucciones();
        
        areaDeCodigo = new JTextArea(20,20);
        areaDeCodigo.setFont(new Font("Courier New", Font.PLAIN, 14));
        areaDeCodigo.setLineWrap(true);
        areaDeCodigo.setWrapStyleWord(true);
        areaDeCodigo.setTabSize(4);
        scrollCodigo = new JScrollPane(areaDeCodigo);
        scrollCodigo.setBounds(10,10,400,550);
        
        panelDeDibujo = new PanelDeDibujo();
        panelDeDibujo.setBounds(scrollCodigo.getX()+scrollCodigo.getWidth()+10,
                10,
                Propiedades.PANEL_DE_DIBUJO_ANCHO,
                Propiedades.PANEL_DE_DIBUJO_LARGO);
        panelDeDibujo.setBackground(new Color(0,41,91));
        zoomPanel = panelDeDibujo;
        
        ejecutar = new JButton("Ejecutar");
        ejecutar.setBounds(30, 570, 170, 40);
        ejecutar.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent ae) {
                parser.limpiar();
                if(parser.compilar(areaDeCodigo.getText()))
                    panelDeDibujo.setConfiguracion(parser.ejecutar());
                else{
                    parser = new Parser();
                    parser.insertarInstrucciones();
                    panelDeDibujo.setConfiguracion(parser.getConfiguracion());
                }
                panelDeDibujo.repaint();
            }
        });
        
        limpiar = new JButton("Limpiar");
        limpiar.setBounds(220, 570, 170, 40);
        
        limpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                areaDeCodigo.setText("");
                panelDeDibujo.getConfiguracion().limpiar();
                panelDeDibujo.getConfiguracion().setPosicion(0.0, 0.0);
                panelDeDibujo.getConfiguracion().setAngulo(0);
                panelDeDibujo.removeAll();
                panelDeDibujo.repaint();
            }
        });
        
        zoom = new JSlider(JSlider.VERTICAL, -10, 10, 0);
        zoom.setMajorTickSpacing(2);
        zoom.setPaintTicks(true);
        zoom.setBounds(scrollCodigo.getX()+scrollCodigo.getWidth()+
                Propiedades.PANEL_DE_DIBUJO_ANCHO+15, 10, 20, 
                Propiedades.PANEL_DE_DIBUJO_LARGO);
        zoom.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                zoomPanel.setFactor( (((JSlider)e.getSource()).getValue() > 0) ? 
                        zoomPanel.getFactor() + 0.1 : 
                        zoomPanel.getFactor() - 0.1 );
                panelDeDibujo.repaint();
            }
        });
        
        add(scrollCodigo);
        add(panelDeDibujo);
        add(ejecutar);
        add(zoom);
        add(limpiar);
        
        setLayout(null);
        setBounds(50,50,
                10+scrollCodigo.getWidth()+panelDeDibujo.getWidth()+
                zoom.getWidth()+30,649);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
    }

}
