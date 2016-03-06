/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.examples.listexample;

import com.ecological.paper.tree.Tree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.myfaces.examples.listexample.SortableList;


import com.ecological.delegados.ServicioDelegado;
import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.el.MethodBinding;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.util.Utilidades;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class OpenDataList extends SortableList {
    private DataModel data;
    private DataModel columnHeaders;
    
    private static final int SORT_ASCENDING = 1;
    private static final int SORT_DESCENDING = -1;
    
    private Tree treeNodoActual;
    private HttpSession session = super.getSession();
    private boolean swMostrarListDocumentos=false;
    private List<Doc_maestro> mostrarListDocumentos;
    private ServicioDelegado delegado =new ServicioDelegado();
    private ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    private ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    
    public OpenDataList() {
        super(null);
        swMostrarListDocumentos=false;
        treeNodoActual =session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        //si es una carpeta, mostramos todos sus documentos
        List headerList = new ArrayList();
        List rowList = new ArrayList();
        if(treeNodoActual.getTiponodo()-Utilidades.getNodoDocumento()==0){
            swMostrarListDocumentos=true;
            mostrarListDocumentos= delegado.findAllDoc_maestros(treeNodoActual);
            
            
            
            headerList.add(new ColumnHeader(messages.getString("doc_nombre"),"100",false));
            headerList.add(new ColumnHeader(messages.getString("doc_consecutivotab"),"200",true));
            columnHeaders = new ListDataModel(headerList);
            //headerList.add(new ColumnHeader("Model","300",true));
            // create list of lists (data)
            
            int i=0;
            for(Doc_maestro doc:mostrarListDocumentos){
                ++i;
                List colList = new ArrayList();
                colList.add(new Integer(i));
                colList.add(doc.getNombre());
                colList.add(doc.getConsecutivo());
                rowList.add(colList);
                
            }
          
            
        }
        
         data = new ListDataModel(rowList);
        
        
        
        
    }
    
    //==========================================================================
    // Getters
    //==========================================================================
    
    public DataModel getData() {
        sort(getSort(), isAscending());
        return data;
    }
    
    void setData(DataModel datamodel) {
        System.out.println("preserved datamodel updated");
        // just here to see if the datamodel is updated if preservedatamodel=true
    }
    
    public DataModel getColumnHeaders() {
        return columnHeaders;
    }
    
    //==========================================================================
    // Public Methods
    //==========================================================================
    
    public Object getColumnValue() {
        Object columnValue = null;
        if (data.isRowAvailable() && columnHeaders.isRowAvailable()) {
            columnValue = ((List)data.getRowData()).get(columnHeaders.getRowIndex());
        }
        return columnValue;
    }
    
    public void setColumnValue(Object value) {
        if (data.isRowAvailable() && columnHeaders.isRowAvailable()) {
            ((List)data.getRowData()).set(columnHeaders.getRowIndex(), value);
        }
    }
    
    public String getColumnWidth() {
        String columnWidth = null;
        if (data.isRowAvailable() && columnHeaders.isRowAvailable()) {
            columnWidth = ((ColumnHeader)columnHeaders.getRowData()).getWidth();
        }
        return columnWidth;
    }
    
    public boolean isValueModifiable() {
        boolean valueModifiable = false;
        if (data.isRowAvailable() && columnHeaders.isRowAvailable()) {
            valueModifiable = ((ColumnHeader)columnHeaders.getRowData()).isEditable();
        }
        return valueModifiable;
    }
    
    //==========================================================================
    // Protected Methods
    //==========================================================================
    
    protected boolean isDefaultAscending(String sortColumn) {
        return true;
    }
    
    protected void sort(final String column, final boolean ascending) {
        if (column != null) {
            int columnIndex = getColumnIndex(column);
            int direction = (ascending) ? SORT_ASCENDING : SORT_DESCENDING;
            sort(columnIndex, direction);
        }
    }
    
    protected void sort(final int columnIndex, final int direction) {
        Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                int result = 0;
                Object column1 = ((List)o1).get(columnIndex);
                Object column2 = ((List)o2).get(columnIndex);
                if (column1 == null && column2 != null)
                    result = -1;
                else if (column1 == null && column2 == null)
                    result = 0;
                else if (column1 != null && column2 == null)
                    result = 1;
                else
                    result = ((Comparable)column1).compareTo(column2) * direction;
                return result;
            }
        };
        Collections.sort((List)data.getWrappedData(), comparator);
    }
    
    //==========================================================================
    // Private Methods
    //==========================================================================
    
    private int getColumnIndex(final String columnName) {
        int columnIndex = -1;
        List headers = (List) columnHeaders.getWrappedData();
        for (int i=0;i<headers.size() && columnIndex==-1;i++) {
            ColumnHeader header = (ColumnHeader) headers.get(i);
            if (header.getLabel().equals(columnName)) {
                columnIndex = i;
            }
        }
        return columnIndex;
    }
    
    public Tree getTreeNodoActual() {
        return treeNodoActual;
    }
    
    public void setTreeNodoActual(Tree treeNodoActual) {
        this.treeNodoActual = treeNodoActual;
    }
    
    public HttpSession getSession() {
        return session;
    }
    
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    public boolean isSwMostrarListDocumentos() {
        return swMostrarListDocumentos;
    }
    
    public void setSwMostrarListDocumentos(boolean swMostrarListDocumentos) {
        this.swMostrarListDocumentos = swMostrarListDocumentos;
    }
    
    public List<Doc_maestro> getMostrarListDocumentos() {
        return mostrarListDocumentos;
    }
    
    public void setMostrarListDocumentos(List<Doc_maestro> mostrarListDocumentos) {
        this.mostrarListDocumentos = mostrarListDocumentos;
    }
    
}
