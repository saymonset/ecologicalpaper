/*
 * MyHtmlValueConverter.java
 *
 * Created on September 29, 2007, 8:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;

import com.ecological.util.MyHtmlValue;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author simo
 */
public class MyHtmlValueConverter  implements Converter{
    
    /** Creates a new instance of MyHtmlValueConverter */
    public MyHtmlValueConverter() {
    }
    
    public Object getAsObject(FacesContext context,
            UIComponent component, String value) throws
            ConverterException {
        MyHtmlValue strVal = new MyHtmlValue();
        strVal.setValue(value);
        return strVal;
    }
    
    public String getAsString(FacesContext context,
            UIComponent component, Object value) throws
            ConverterException {
        return value == null ? "" : ((MyHtmlValue)
        value).getValue();
    }
}


