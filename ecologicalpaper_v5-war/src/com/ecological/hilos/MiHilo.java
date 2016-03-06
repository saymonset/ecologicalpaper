/*
 * MiHilo.java
 *
 * Created on 6 de octubre de 2007, 05:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.hilos;

/**
 *
 * @author simon
 */
public class MiHilo {
    
    /** Creates a new instance of MiHilo */
    public MiHilo() {
    }
    public static void main(String args[]){
          //"yyyy-MM-dd");
        String    dateCreation = "yyyy-MM-dd";
      
      System.out.println("yyyy="+dateCreation.substring(0,4));
        System.out.println("MONTH="+dateCreation.substring(5,7));
        System.out.println("DD="+dateCreation.substring(8,10));
    }
}
