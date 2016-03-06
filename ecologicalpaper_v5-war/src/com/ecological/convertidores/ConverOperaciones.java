/*
 * ConverOperaciones.java
 *
 * Created on July 19, 2007, 5:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverOperaciones implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverOperaciones */
    public ConverOperaciones() {
      
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        Operaciones objeto=new Operaciones();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto = delegado.Encontrar_operaciones(new String(string));
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Operaciones operaciones= (Operaciones) object;
            return String.valueOf(operaciones.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
