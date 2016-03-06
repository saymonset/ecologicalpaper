/*
 * ClienteFlowsHistorico.java
 *
 * Created on August 27, 2007, 10:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.flows;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole_OperacionesPopup;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.usuario.Usuario;
import com.ecological.reportes.Reportes;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;


/**
 *
 * @author simon
 */
public class ClienteFlowsHistorico extends ContextSessionRequest{
    private ServicioDelegado delegado =new ServicioDelegado();
    private HttpSession session = super.getSession();
    private ClienteOperaciones clienteOperaciones =new ClienteOperaciones();
    private  ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
    private List<FlowsHistorico> flowsHistorico;
    private Doc_detalle doc_detalle;
    private Doc_maestro doc_maestro;
    private Flow flow;
    ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    /** Creates a new instance of ClienteRole */
    
    
    /** Creates a new instance of ClienteFlowsHistorico */
    public ClienteFlowsHistorico() {
    	session = super.getSession();
        
    }
    
    
    
    
    
    
    public String cancelarEditar(){
        //inicializamos todas las sessiones,dejamos solo las basicas, antes de irnos
    try {
    	super.clearSession(session,confmessages.getString("usuarioSession"));
	} catch (Exception e) {
		redirect("ClienteFlowsHistorico","cancelarEditar",e);
	}
        
        return "listarRole";
    }
    
    
    public String viewDocDetalleFlowHistorico() throws RoleMultiples{
    	versionId();
        return "flowsResponse";
    }
    public void versionId(){
      	 session.setAttribute("doc_detalle",flow.getFlowParalelo().getFlow().getDoc_detalle());
         session.setAttribute("flowHistorico", flow);
    }
    public void versionId(ActionEvent event) {
        
        String attrname1 = (String) event.getComponent().getAttributes().get("attrname1");
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId2");
        UIParameter componentDetalle = (UIParameter) event.getComponent().findComponent("editId3");
        
        
        if ((component != null) && (component.getValue().toString() != null)) {
            
            long id = Long.parseLong(component.getValue().toString());
         
            if (flow == null) {
                flow= new Flow();
            }
            //buscamos el role para editar
            if (id >= 0) {
                flow.setCodigo(new Long(id));
                
                flow = delegado.findFlow(flow);
            }
            
            if (flow!= null) {
            	 session.setAttribute("doc_detalle",flow.getFlowParalelo().getFlow().getDoc_detalle());
                session.setAttribute("flowHistorico", flow);
            }
        }
    
    }
    public void IniOperaciones(){
        
    }
    
    
    
    
    
    
    public List<Role> findAll() {
        //return servicioUsuario.findAll();
        Usuario user_logueado =session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
        if (user_logueado!=null){
        	return delegado.findAll_Roles(user_logueado);	
        }else{
        	return null;
        }
        
    }
    
    
    
    /***************SELECT MULTIPLES*****************************************/
    /********************************************************/
    /********************************************************/
    private CustomizePageBean  customizePageBean;
    //List of SelectItem instances
    private List invisibleItems;
    //List of SelectItem instances
    private List visibleItems;
    //array of values of selected items in "Invisible" list
    private Object[] selectedInvisibleItems;
    //array of values of selected items in the "Visible" list
    private Object[] selectedVisibleItems;
    
    public void moveSelectedToVisible(ActionEvent actionEvent) {
        customizePageBean.moveSelectedToVisible( actionEvent)  ;
    }
    public void moveAllToVisible(ActionEvent actionEvent) {
        customizePageBean.moveAllToVisible( actionEvent)  ;
    }
    public void moveSelectedToInvisible(ActionEvent actionEvent) {
        customizePageBean.moveSelectedToInvisible( actionEvent)  ;
    }
    public void moveAllToInvisible    (ActionEvent actionEvent) {
        customizePageBean.moveAllToInvisible    ( actionEvent)  ;
    }
    
    public CustomizePageBean getCustomizePageBean() {
        return customizePageBean;
    }
    
    public void setCustomizePageBean(CustomizePageBean customizePageBean) {
        this.customizePageBean = customizePageBean;
    }
    
    public List getInvisibleItems() {
        invisibleItems=customizePageBean.getInvisibleItems();
        return invisibleItems;
    }
    
    public void setInvisibleItems(List invisibleItems) {
        customizePageBean.setInvisibleItems(invisibleItems);
        this.invisibleItems = invisibleItems;
    }
    
    
    public List getVisibleItems() {
        visibleItems=customizePageBean.getVisibleItems();
        return visibleItems;
    }
    
    public void setVisibleItems(List visibleItems) {
        customizePageBean.setVisibleItems(visibleItems);
        this.visibleItems = visibleItems;
    }
    
    public Object[] getSelectedInvisibleItems() {
        selectedInvisibleItems=customizePageBean.getSelectedInvisibleItems();
        return selectedInvisibleItems;
    }
    
    public void setSelectedInvisibleItems(Object[] selectedInvisibleItems) {
        customizePageBean.setSelectedInvisibleItems(selectedInvisibleItems);
        this.selectedInvisibleItems = selectedInvisibleItems;
    }
    
    public Object[] getSelectedVisibleItems() {
        selectedVisibleItems=customizePageBean.getSelectedVisibleItems();
        return selectedVisibleItems;
    }
    
    public void setSelectedVisibleItems(Object[] selectedVisibleItems) {
        customizePageBean.setSelectedVisibleItems(selectedVisibleItems);
        this.selectedVisibleItems = selectedVisibleItems;
    }
    /********************************************************/
    /********************************************************/
    /********************************************************/
    
    
    
    
    
    
    
    
    public List<FlowsHistorico> getFlowsHistorico() {
        doc_detalle = (Doc_detalle)session.getAttribute("doc_detalle");
        doc_maestro=doc_detalle.getDoc_maestro();
        
       
    
        flowsHistorico=delegado.findAll_FlowsHistorico(doc_maestro);
        if (flowsHistorico==null){
            flowsHistorico= new ArrayList();
        }
        
        List<FlowsHistorico> lista = new ArrayList<FlowsHistorico>();
        Map<String,String> unico = new HashMap<String,String>();
        for (FlowsHistorico fh:flowsHistorico){
        	if (unico.containsKey(fh.getFlow().getFlowParalelo().getCodigo()+"")){
        		continue;
        	}else{
        		unico.put(fh.getFlow().getFlowParalelo().getCodigo()+"", fh.getFlow().getFlowParalelo().getCodigo()+"");
            String result;
            /*SimpleDateFormat formatter;
            Locale locale = new Locale("es", "VE");
            formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            result = formatter.format(fh.getFlow().getFecha_creado());*/
            if (fh.getFlow().getFecha_creado()!=null){
                fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow().getFecha_creado()));
            }
            
            Doc_estado doc_edo= new Doc_estado();
            doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
            doc_edo=delegado.findDocEstado(doc_edo);
            fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
            fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado().getNombre()));
            lista.add(fh);
        	}
        }
        return lista;
    }
    
   
    
    public void setFlowsHistorico(List<FlowsHistorico> flowsHistorico) {
        this.flowsHistorico = flowsHistorico;
    }
    
    public Doc_detalle getDoc_detalle() {
        return doc_detalle;
    }
    
    public void setDoc_detalle(Doc_detalle doc_detalle) {
        this.doc_detalle = doc_detalle;
    }
    
    public Doc_maestro getDoc_maestro() {
        return doc_maestro;
    }
    
    public void setDoc_maestro(Doc_maestro doc_maestro) {
        this.doc_maestro = doc_maestro;
    }
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    
    
    
    
    
}
