/*
 * CustomizePageBean.java
 *
 * Created on July 14, 2007, 11:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.util;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.component.html.HtmlOutputText;

import java.util.List;
import java.util.ArrayList;

public class CustomizePageBean {
    
    
    //array of values of selected items in "Invisible" list
    private Object[] selectedInvisibleItems;
    
    //array of values of selected items in the "Visible" list
    private Object[] selectedVisibleItems;
    
    //List of SelectItem instances
    private List invisibleItems;
    
    //List of SelectItem instances
    private List visibleItems;
    
    public CustomizePageBean() {
        /*if ((invisibleItems==null) && (visibleItems==null)){
            invisibleItems = new ArrayList();
            visibleItems = new ArrayList();
         
            SelectItem item = new SelectItem("firstName", "First Name");
            invisibleItems.add(item);
            item = new SelectItem("lastName", "Last Name");
            invisibleItems.add(item);
            item = new SelectItem("sex", "Sex");
            invisibleItems.add(item);
            item = new SelectItem("ssn", "SSN");
            invisibleItems.add(item);
        }*/
    }
    
    public void setSelectedInvisibleItems(Object[] items) {
        selectedInvisibleItems = items;
    }
    
    public Object[] getSelectedInvisibleItems() {
        return selectedInvisibleItems;
    }
    
    public void setSelectedVisibleItems(Object[] items) {
        selectedVisibleItems = items;
    }
    
    public Object[] getSelectedVisibleItems() {
        return selectedVisibleItems;
    }
    
    public void setInvisibleItems(List items) {
        invisibleItems = items;
    }
    
    public List getInvisibleItems() {
         
        return invisibleItems;
    }
    
    public void setVisibleItems(List items) {
        visibleItems = items;
    }
    
    public List getVisibleItems() {
        return visibleItems;
    }
    
    public void moveAllToVisible(ActionEvent actionEvent) {
        getVisibleItems().addAll(getInvisibleItems());
        getInvisibleItems().clear();
    }
    
    public void moveAllToInvisible(ActionEvent actionEvent) {
        getInvisibleItems().addAll(getVisibleItems());
        getVisibleItems().clear();
    }
    
    public void moveSelectedToVisible(ActionEvent actionEvent) {
        Object[] values = getSelectedInvisibleItems();
        if (values!=null){
            int length = values.length;
            for (int i=0; i<length; i++) {
                Object value = values[i];
                //remuevo de invisible, y el metodo remoiveItem aparte de remover
                //me devuelve el objeto que me remueve parala otra lista
                getVisibleItems().add(removeItem(value, getInvisibleItems()));
            }
        }
    }
    
    public Object[] saveSelectedTovisible(ActionEvent actionEvent) {
        Object[] values = getSelectedVisibleItems();
        return values;
        
    }
    
    public void moveSelectedToInvisible(ActionEvent actionEvent) {
        Object[] values = getSelectedVisibleItems();
        if (values!=null){
            int length = values.length;
            for (int i=0; i<length; i++) {
                Object value = values[i];
                getInvisibleItems().add(removeItem(value, getVisibleItems()));
            }
        }
    }
    
    private SelectItem removeItem(Object value, List items) {
        SelectItem result = null;
        if (items!=null){
            int size = items.size();
            for (int i=0; i<size; i++) {
                SelectItem item = (SelectItem) items.get(i);
                //if (value.equals(item.getValue())) {
                if (value.equals(item.getValue())) {
                    //lo elimino y mando el objeto
                    result = (SelectItem) items.remove(i);
                    break;
                }
            }
        }
        return result;
    }
}