package com.ecological.convertidores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.subversion.SvnUrlBase;
 

public class ConverSvnUrlBase implements Converter{
     private ServicioDelegado delegado = new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverUsuarios */
    public ConverSvnUrlBase() {
       
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
    	SvnUrlBase objeto=new SvnUrlBase();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
            	try {
            		objeto.setCodigo(new Long(string));	
				} catch (Exception e) {
					objeto.setCodigo(-1L);
				}
            	
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
        	SvnUrlBase svnUrlBase= (SvnUrlBase) object;
            return String.valueOf(svnUrlBase.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
