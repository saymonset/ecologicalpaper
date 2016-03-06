/*
 * RoleMultiples.java
 *
 * Created on July 16, 2007, 8:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.ecologicaexcepciones.role;

/**
 *
 * @author simon
 */
public class RoleMultiples extends Exception{
    
    /** Creates a new instance of RoleMultiples */
    public RoleMultiples() {
        this("EcologicaRoleExcepciones");
    }
    public RoleMultiples(String msg) {
        super(msg);
    }
    
    public RoleMultiples(Throwable t){
        super(t);
    }
    
    public RoleMultiples(String msg, Throwable t){
        super(msg,t);
    }
}
