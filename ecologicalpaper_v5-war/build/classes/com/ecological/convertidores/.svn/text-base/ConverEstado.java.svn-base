/*
 * ConverEstado.java
 *
 * Created on 21 de febrero de 2008, 10:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.delegados.ServicioDelegado;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverEstado implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverOperaciones */
    public ConverEstado() {
        
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        Estado objeto=new Estado();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.find_estado(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Estado estado= (Estado) object;
            return String.valueOf(estado.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
