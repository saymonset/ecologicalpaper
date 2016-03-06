/*
 * ConverDoc_tipo.java
 *
 * Created on July 26, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_tipo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simon
 */
public class ConverDoc_tipo implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    
    /** Creates a new instance of ConverDoc_tipo */
    public ConverDoc_tipo() {
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        Doc_tipo objeto=new Doc_tipo();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.findTipoDoc(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Doc_tipo doc_tipo= (Doc_tipo) object;
            return String.valueOf(doc_tipo.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
