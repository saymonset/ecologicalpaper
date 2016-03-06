/*
 * ConverProfesion.java
 *
 * Created on July 9, 2007, 4:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.profesion.Profesion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author simon
 */
public class ConverProfesion implements Converter {
    //ServicioProfesion delegado = ServicioProfesion.getInstance();
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverProfesion */
    public Object getAsObject(FacesContext facesContext,
            UIComponent uIComponent, String string) {
        //Profesion profesion=null;
        //  if (string!=null){
        Profesion profesion=new Profesion();
        try {
            //profesion = delegado.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                profesion = delegado.Encontrar_Profesion(new String(string));
                //System.out.print("profesion.getNombre()="+profesion.getNombre());
            }
            
        } catch (Exception e) {
            profesion=null;
            e.printStackTrace();
        }
        
        // }
        
        //el nodo exactamente el que tenemos es de tipo cargo,o carpeta o,
        //localidad, dependiendpo del filtro que llene al select
        //el convertidor sera el mismo.
        
        return profesion;
    }
    
    public String getAsString(FacesContext facesContext,
            UIComponent uIComponent, Object object) {
        if (object != null) {
            Profesion profesion= (Profesion) object;
            return String.valueOf(profesion.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
}
