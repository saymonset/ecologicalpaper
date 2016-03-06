/*
 * ConverUsuarios.java
 *
 * Created on July 21, 2007, 8:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.usuario.Usuario;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverUsuarios implements Converter{
     private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverUsuarios */
    public ConverUsuarios() {
       
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        Usuario objeto=new Usuario();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto = delegado.Encontrar_Usuario(new String(string));
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Usuario usuario= (Usuario) object;
            return String.valueOf(usuario.getId());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
