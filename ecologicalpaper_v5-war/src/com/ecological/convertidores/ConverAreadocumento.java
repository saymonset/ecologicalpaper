package com.ecological.convertidores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.AreaDocumentos;

public class ConverAreadocumento implements Converter {
    private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    
    /** Creates a new instance of ConverDoc_tipo */
    public ConverAreadocumento() {
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        AreaDocumentos objeto=new AreaDocumentos();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.find(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            AreaDocumentos doc_tipo= (AreaDocumentos) object;
            return String.valueOf(doc_tipo.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
