/*
 * ConverDoc_Estado.java
 *
 * Created on July 26, 2007, 11:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

 
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverDoc_Estado implements Converter {
    private ServicioDelegado delegado= new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverDoc_Estado */
    public ConverDoc_Estado() {
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        
        Doc_estado objeto=new Doc_estado();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.findDocEstado(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Doc_estado doc_estado= (Doc_estado) object;
            return String.valueOf(doc_estado.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
