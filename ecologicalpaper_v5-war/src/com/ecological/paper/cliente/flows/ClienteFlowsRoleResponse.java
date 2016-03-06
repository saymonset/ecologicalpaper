/*
 * ClienteFlowsRoleResponse.java
 *
 * Created on August 7, 2007, 5:56 PM
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
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
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

public class ClienteFlowsRoleResponse  extends ContextSessionRequest{
    private ServicioDelegado delegado =new ServicioDelegado();

    
    private DatosCombo datosCombo = new DatosCombo();
    private HttpSession session = super.getSession();
    private  Tree tree;
    private  Usuario usuario ;
    private  boolean swIniciar;
    private List usuariosCombo;
    private Flow flow;
    ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    /** Creates a new instance of ClienteFlowsRoleResponse */
    public ClienteFlowsRoleResponse() {
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
            session.setAttribute("tabBandera","2");
            operaciones();
        }
    }
    
    //cuando se pulsa el boton seleccionar usuario
    public String editFlows_Role(){
        //si viene de la lista del combo, pasa por aca
        //la primera vez se ejecuta
        if (!swIniciar ){
            //tabulador principal
            //ClienteFlows.setFlowBandera("2");
            swIniciar=true;
            session.setAttribute("tabBandera","2");
            session.setAttribute("swIniciar",swIniciar);
            try {
                
                inicializar(swIniciar);
            } catch (RoleMultiples ex) {
                ex.printStackTrace();
            }
        }else{
            //tabulador principal
            session.setAttribute("tabBandera","");
            //si viene de la lista multiple de los permisos del usario, pasa por aca
            //blanquea las sessiones y regresa para escijer un nuevo usario
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
			redirect("ClienteFlowsRoleResponse","cancelar",e);
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
            List flows=delegado.findByFlow(doc_detalle);
            int size=flows.size();
            Flow f=null;
            int j=0;
            for (int i=0;i<size;i++){
                f = (Flow)flows.get(i);
                j=i;
            }
            if (j>1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_borr_detlle_flowsVariosenBD") ));
            }
            setFlow(f);
            datosCombo.llenarRoleFlowVisibles(visibleItems,invisibleItems,getFlow());
            
            
            session.setAttribute("visibleItems",visibleItems);
            session.setAttribute("invisibleItems",invisibleItems);
            session.setAttribute("usuario",usuario);
            session.setAttribute("swIniciar",true);
            session.setAttribute("tabBandera","2");
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
    
    
    
    
    
    public String saveRole() throws RoleMultiples{
        tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        Usuario user_logueado=session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
        Doc_detalle doc_detalle=null;
        Doc_estado doc_estado= new Doc_estado();
        
        doc_estado.setCodigo(Utilidades.getBorrador());
        doc_estado=delegado.findDocEstado(doc_estado);
        if (doc_estado==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_edobdAprobacion") ));
        }
        
        doc_detalle=(Doc_detalle) session.getAttribute("doc_detalle");
        if (doc_detalle==null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_doc_detalleflow") ));
        }
        //siempre buscamos los flows por borradores, porque son los roles que se van a guardar como borrador
        
        List flows=delegado.findByFlow(doc_detalle);
        int size=flows.size();
        Flow f=null;
        //solamente debe haber un solo flo edo borrador el flujo ese documento
        int j=0;
        for (int i=0;i<size;i++){
            f = (Flow)flows.get(i);
            j=i;
        }
        if (j>1){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_borr_detlle_flowsVariosenBD") ));
        }
        setFlow(f);
        if (getFlow()==null){
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
        
        //    doc_detalle viene de session y doc_estado siempre lo trae Utilidades.class
        if (doc_detalle!=null && doc_estado!=null){
            try{
                //eliminamos todos las operaciones del ro, anteriormente seleccionados
                List listaFlowRoles= delegado.findByFlow_referencia_role(flow);
                Iterator it = listaFlowRoles.iterator();
                Role role=null;
                while(it.hasNext()){
                    Flow_referencia_role flow_Role = (Flow_referencia_role)it.next();
                    
                    if (flow==null){
                        flow=flow_Role.getFlow();
                    }
                    delegado.destroy(flow_Role);
                }
               //List<Flow_Participantes> Flow_ParticipanteSS = new ArrayList<Flow_Participantes>();
                // introducimos las nuevas operaciones al rol
                visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                Flow_referencia_role  flow_referencia_role;
                int length = visibleItems.size();
                for (int i=0; i<length; i++) {
                    SelectItem value = (SelectItem)visibleItems.get(i);
                    role= (Role) value.getValue();
                    if (role!=null && role instanceof Role){
                        flow_referencia_role = new Flow_referencia_role();
                        flow_referencia_role.setRole(role);
                        flow_referencia_role.setFlow(flow);
                        try {
                            delegado.create(flow_referencia_role);
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
            session.setAttribute("tabBandera","2");
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
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    
   
    
    
  
    
    public DatosCombo getDatosCombo() {
        return datosCombo;
    }
    
    public void setDatosCombo(DatosCombo datosCombo) {
        this.datosCombo = datosCombo;
    }
    
    public HttpSession getSession() {
        return session;
    }
    
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }
    
    public ResourceBundle getMessages() {
        return messages;
    }
    
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }
    
    public ResourceBundle getConfmessages() {
        return confmessages;
    }
    
    public void setConfmessages(ResourceBundle confmessages) {
        this.confmessages = confmessages;
    }
    
    
    
    
    
    
}
