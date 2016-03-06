/*
 * ConverTree.java
 *
 * Created on July 7, 2007, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.convertidores;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author simon
 */
public class ConverTree implements Converter {
    private static final long malo=-21;
     private ServicioDelegado delegado = new ServicioDelegado();
    
    /** Creates a new instance of ConverTree */
    public ConverTree() {
    }
    public Object getAsObject(FacesContext facesContext,
            UIComponent uIComponent, String string) {
        //el nodo exactamente el que tenemos es de tipo cargo,o carpeta o,
        //localidad, dependiendpo del filtro que llene al select
        //el convertidor sera el mismo.
        Tree tree = delegado.obtenerNodo(new Long(Long.parseLong(string)));
        
        return tree;
    }
    
    public String getAsString(FacesContext facesContext,
            UIComponent uIComponent, Object object) {
        if (object != null) {
            Tree tree = (Tree) object;
            return String.valueOf(tree.getNodo());
        } else {
            return String.valueOf(malo);
        }
    }
}
