/*
 * MySeguridad.java
 *
 * Created on 6 de octubre de 2007, 06:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.hilos;

import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author simon
 */
public class MySeguridad extends Thread {
    
    
    ProcesarLoginsConectados procesarLoginsConectados=new ProcesarLoginsConectados();
    
    Map application = null;
    Usuario usuario= null;
    Usuario user=null;
    
    private Seguridad_User_Lineal seguridad_User_Lineal;
    public MySeguridad(Seguridad_User_Lineal seguridad_User_Lineal) {
        this.seguridad_User_Lineal=seguridad_User_Lineal;
    }
    public MySeguridad() {
        
    }
    public void run() {
        try {
            
            
            while(true){
                runTask();
                sleep(5000);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private synchronized void runTask() {
        try {
          // System.out.println("procesando hilo");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            
        }
    }
    
    
    
    
}
