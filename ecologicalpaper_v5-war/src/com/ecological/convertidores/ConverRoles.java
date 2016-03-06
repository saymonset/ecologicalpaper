/*
 * ConverRoles.java
 *
 * Created on July 19, 2007, 6:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.delegados.ServicioDelegado;

import com.ecological.paper.permisologia.role.Role;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverRoles implements Converter {
    private ServicioDelegado delegado =new ServicioDelegado();
    
    private static final long malo=-21;
    
    /** Creates a new instance of ConverRoles */
    public ConverRoles() {
       
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        Role objeto=new Role();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto = delegado.Encontrar_roles(new String(string));
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Role role= (Role) object;
            return String.valueOf(role.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
