/*
 * UsuarioNoEncontrado.java
 *
 * Created on July 12, 2007, 9:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.ecologicaexcepciones;

/**
 *
 * @author simon
 */
public class UsuarioNoEncontrado extends Exception {
    
    /** Creates a new instance of UsuarioNoEncontrado */
    
     public UsuarioNoEncontrado() {
        this("EcologicaExcepciones");
    }
    public UsuarioNoEncontrado(String msg) {
        super(msg);
    }
    
    public UsuarioNoEncontrado(Throwable t){
        super(t);
    }
    
    public UsuarioNoEncontrado(String msg, Throwable t){
        super(msg,t);
    }
}
