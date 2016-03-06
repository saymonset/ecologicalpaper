/*
 * ConverCiudad.java
 *
 * Created on 21 de febrero de 2008, 10:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.delegados.ServicioDelegado;
import com.software.zonas.Ciudad;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverCiudad implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverOperaciones */
    public ConverCiudad() {
        
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        Ciudad objeto=new Ciudad();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.find_ciudad(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Ciudad ciudad= (Ciudad) object;
            return String.valueOf(ciudad.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
