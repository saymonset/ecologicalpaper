/*
 * ConverDoc_Maestro.java
 *
 * Created on August 27, 2007, 7:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_maestro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverDoc_Maestro implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    
    
    /** Creates a new instance of ConverDoc_Maestro */
    public ConverDoc_Maestro() {
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        Doc_maestro objeto=new Doc_maestro();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.findMaestro(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Doc_maestro doc_maestro= (Doc_maestro) object;
            return String.valueOf(doc_maestro.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
