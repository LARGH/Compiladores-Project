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
public class TablaDeSimbolos {

    ArrayList simbolos;
    
    public TablaDeSimbolos(){
        simbolos = new ArrayList();
    }
    
    public Object encontrar(String nombre){
        for(int i = 0; i < simbolos.size(); i++)
            if(nombre.equals(((Par)simbolos.get(i)).getNombre()))
                return ((Par)simbolos.get(i)).getObjeto();
        return null;
    }
    
    public boolean insertar(String nombre, Object objeto){
        Par par = new Par(nombre, objeto);
        for(int i = 0; i < simbolos.size(); i++)
            if(nombre.equals(((Par)simbolos.get(i)).getNombre())){
                ((Par)simbolos.get(i)).setObjeto(objeto);
                return true;
            }
        simbolos.add(par);
        return false;
    }
    
    public void imprimir(){
        for(int i = 0; i < simbolos.size(); i++){
            System.out.println(((Par)simbolos.get(i)).getNombre()
            		+ ((Par)simbolos.get(i)).getObjeto().toString());
        }
    }

}
