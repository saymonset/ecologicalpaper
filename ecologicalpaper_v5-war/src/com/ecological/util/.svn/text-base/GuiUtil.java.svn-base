/*
 * GuiUtil.java
 *
 * Created on June 24, 2007, 6:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.util;


import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.text.MessageFormat;
import com.ecological.paper.tree.*;

/**
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 19:46:34 +0000 (Wed, 08 Nov 2006) $
 */
public class GuiUtil
{
//    private static String BUNDLE_NAME = "org.apache.myfaces.examples.resource.example_messages";
      private static String BUNDLE_NAME = "com.ecological.resource.ecologicalpaper";
    

    public static String getMessageResource(String key, Object[] arguments)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String resourceString;
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, context.getViewRoot().getLocale());
            resourceString = bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return key;
        }

        if (arguments == null) return resourceString;

        MessageFormat format = new MessageFormat(resourceString, context.getViewRoot().getLocale());
        return format.format(arguments);
    }

}
