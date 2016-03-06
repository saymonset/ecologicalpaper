/*
 * ConverCliente_EUJ.java
 *
 * Created on 22 de febrero de 2008, 08:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.delegados.ServicioDelegado;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import com.software.eujovans.clientes.Cliente_EUJ;
/**
 *
 * @author simon
 */
public class ConverCliente_EUJ implements Converter {
    private ServicioDelegado delegado =new ServicioDelegado();
    private static final long malo=-21;
    /** Creates a new instance of ConverOperaciones */
    public ConverCliente_EUJ() {
        
    }
    
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) throws ConverterException {
        
        Cliente_EUJ objeto=new Cliente_EUJ();
        try {
            //profesion = delegadoProfesion.find(Long.parseLong(string.trim()));
            if (string!=null && string.length()>0){
                objeto.setCodigo(Long.parseLong(string));
                objeto = delegado.find_cliente_EUJ(objeto);
            }
            
        } catch (Exception e) {
            objeto=null;
            e.printStackTrace();
        }
        
        
        return objeto;
        
    }
    
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) throws ConverterException {
        if (object != null) {
            Cliente_EUJ cliente_EUJ= (Cliente_EUJ) object;
            return String.valueOf(cliente_EUJ.getCodigo());
        } else {
            return String.valueOf(malo);
        }
    }
    
}
