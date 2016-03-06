/*
 * ClienteFlowsUsersResponse.java
 *
 * Created on August 7, 2007, 5:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.flows;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;

 

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simon
 */
public class ClienteFlowsUsersResponse  extends ContextSessionRequest{
    private ServicioDelegado delegado = new ServicioDelegado();

    
    
    private DatosCombo datosCombo = new DatosCombo();
    private HttpSession session = super.getSession();
    private  Tree tree;
    private  Usuario usuario ;
    private  boolean  swIniciar;
    private List usuariosCombo;
    ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    
    /** Creates a new instance of ClienteFlowsUsersResponse */
    public ClienteFlowsUsersResponse() {
    	session = super.getSession();
          //catalogo pincipal
        session.removeAttribute("mostrarCatalogo");
        swIniciar=session.getAttribute("swIniciar")!=null?(Boolean)session.getAttribute("swIniciar"):false;
        usuario=session.getAttribute("usuario")!=null?(Usuario)session.getAttribute("usuario"):null;
        tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        //por si las variables viene de la pestaña de al lado, y los
        //convertidores piuedenb dar error, revisamos e inicializamos
        //inicializaSiStanEmbasuradas();
        if (swIniciar){
            session.setAttribute("tabBandera","1");
            operaciones();
        }
    }
       
    
    //cuando se pulsa el boton seleccionar usuario
    public String editFlows_User(){
        //si viene de la lista del combo, pasa por aca
        //la primera vez se ejecuta
        if (!swIniciar ){
            //tabulador principal
            session.setAttribute("tabBandera","1");
            
            swIniciar=true;
            session.setAttribute("swIniciar",swIniciar);
            try {
                
                inicializar(swIniciar);
            } catch (RoleMultiples ex) {
                ex.printStackTrace();
            }
        }else{
            //si viene de la lista multiple de los permisos del usario, pasa por aca
            //blanquea las sessiones y regresa para escijer un nuevo usario
            //tabulador principal
            session.setAttribute("tabBandera","");
            swIniciar=false;
            session.setAttribute("swIniciar",swIniciar);
            //inicializamos todas las sessiones,dejamos solo las basicas
            super.clearSession(session,confmessages.getString("flowSessionResponse"));
        }
        return null;
    }
    public String cancelar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("ClienteFlowsUsersResponse","cancelar",e);
		}
        
        return "menu";
    }
    
    public void inicializar(boolean sw) throws RoleMultiples{
        
        if (sw){
            tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
            visibleItems = new ArrayList();
            invisibleItems = new ArrayList();
            customizePageBean=new CustomizePageBean();
            invisibleItems.clear();
            visibleItems.clear();
            
            Doc_detalle doc_detalle=null;
            doc_detalle=(Doc_detalle) session.getAttribute("doc_detalle");
            if (doc_detalle==null){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_doc_detalleflow") ));
            }
            //  recuperarDoc_Detalle_EDo(doc_estado,doc_detalle,Utilidades.getBorrador());
            datosCombo.llenarUsuariosFlowVisibles(visibleItems,invisibleItems,doc_detalle);
            //  datosCombo.llenarOperacionesVisibles(visibleItems,invisibleItems,tree,usuario);
            
            
            session.setAttribute("visibleItems",visibleItems);
            session.setAttribute("invisibleItems",invisibleItems);
            session.setAttribute("usuario",usuario);
            session.setAttribute("swIniciar",true);
            session.setAttribute("tabBandera","1");
            setInvisibleItems(invisibleItems);
            setVisibleItems(visibleItems);
        }
        
        
    }
    public void recuperarDoc_Detalle_EDo(Doc_estado edo,Doc_detalle doc_detalle,Long tipoEstado){
        edo = new Doc_estado();
        edo.setCodigo(tipoEstado);
        edo=delegado.findDocEstado(edo);
        if (edo==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_edobdAprobacion") ));
        }
        doc_detalle=(Doc_detalle) session.getAttribute("doc_detalle");
        if (doc_detalle==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_doc_detalleflow") ));
        }
    }
    public void operaciones(){
        customizePageBean=new CustomizePageBean();
        invisibleItems =session.getAttribute("invisibleItems")!=null?(List)session.getAttribute("invisibleItems"):null;
        visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
        setInvisibleItems(invisibleItems);
        setVisibleItems(visibleItems);
    }
    
    
    
    
    
    public String saveUsuario_Operaciones() throws RoleMultiples{
        tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        Usuario user_logueado=session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
        Doc_detalle doc_detalle=null;
        //apenas estamos grabando participantes, sin enviar el flujo, pore lo tanto, temporalmente es borrador
        Doc_estado doc_estado=null;
        doc_estado = new Doc_estado();
        doc_estado.setCodigo(Utilidades.getBorrador());
        doc_estado=delegado.findDocEstado(doc_estado);
        
        if (doc_estado==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_edobdAprobacion") ));
        }
        doc_detalle=(Doc_detalle) session.getAttribute("doc_detalle");
        if (doc_detalle==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_doc_detalleflow") ));
        }
        
        
        
        
        //     recuperarDoc_Detalle_EDo(doc_estado,doc_detalle,Utilidades.getBorrador());
        if (doc_detalle!=null && doc_estado!=null){
            try{
                
                //eliminamos todos las operaciones del ro, anteriormente seleccionados
                List listaFlowParticipantes= delegado.findByFlowParticipantes( doc_detalle);
                Iterator it = listaFlowParticipantes.iterator();
                Flow flow =null;
                while(it.hasNext()){
                    Flow_Participantes flow_P = (Flow_Participantes)it.next();
                    if (flow==null){
                        flow=flow_P.getFlow();
                    }
                    delegado.destroy(flow_P);
                }
                
                /*Buscamos el flow por si ya se creo*/
                List flows=delegado.findByFlow(doc_detalle);
                int size=flows.size();
                Flow f=null;
                //solamente debe haber un solo flow edo borrador,en revision o aprobacion el flujo ese documento
                int j=0;
                for (int i=0;i<size;i++){
                    f = (Flow)flows.get(i);
                    j=i;
                }
                if (j>1){
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_borr_detlle_flowsVariosenBD") ));
                }
                flow=f;
                if (flow==null){
                    flow= new Flow();
                    flow.setCondicional(false);
                    flow.setSecuencial(false);
                    flow.setDoc_detalle(doc_detalle);
                    flow.setDuenio(user_logueado);
                    flow.setEstado(doc_estado);
                    Calendar cal = Calendar.getInstance();
                    flow.setFecha_creado(cal.getTime());
                    flow.setOrigen(Utilidades.getOrigenDocumentoFlow());
                    try {
                        delegado.create(flow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }
                
                
                //List<Flow_Participantes> Flow_ParticipanteSS = new ArrayList<Flow_Participantes>();
                // introducimos las nuevas operaciones al rol
                visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                Flow_Participantes  flow_Participantes ;
                int length = visibleItems.size();
                
                for (int i=0; i<length; i++) {
                    SelectItem value = (SelectItem)visibleItems.get(i);
                    Usuario usuario2= (Usuario) value.getValue();
                    if (usuario2!=null && usuario2 instanceof Usuario){
                        flow_Participantes = new Flow_Participantes();
                        flow_Participantes.setParticipante(usuario2);
                        flow_Participantes.setFlow(flow);
                        try {
                            delegado.create(flow_Participantes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //   Flow_ParticipanteSS.add(flow_Participantes);
                    }
                }
                //flow.setParticipantesFlows(Flow_ParticipanteSS);
                
                
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
            } catch (EcologicaExcepciones e) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
        }
        return "";
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
    
    public boolean isSwIniciar() {
        if (swIniciar){
            session.setAttribute("tabBandera","1");
        }
        return swIniciar;
    }
    
    public void setSwIniciar(boolean swIniciar) {
        this.swIniciar = swIniciar;
    }
    
    
    public List getUsuariosCombo() {
        return usuariosCombo;
    }
    
    public void setUsuariosCombo(List usuariosCombo) {
        this.usuariosCombo = usuariosCombo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    
    
    
}
