/*
 * ClienteSeguridadTab2.java
 *
 * Created on September 7, 2007, 2:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.seguridad;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
/**
 *
 * @author simon
 */
public class ClienteSeguridadTab2 extends ContextSessionRequest{
    private ServicioDelegado delegado =new ServicioDelegado();
    private DatosCombo datosCombo = new DatosCombo();
    private HttpSession session = super.getSession();
    private  Tree tree;
    private  Usuario usuario ;
    private HtmlSelectOneMenu selectItemUsuario;
    private boolean swIniciar;
    private List usuariosCombo;
    private ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    private ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    private boolean heredaSeguridad=true;
    private String heredaDependeDeTipoNodo;
    ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
    ClienteSeguridadTab1 clienteSeguridadTab1= new ClienteSeguridadTab1();
    /** Creates a new instance of ClienteSeguridadTab2 */
    
    public ClienteSeguridadTab2(String nada){
    	
    }
    public ClienteSeguridadTab2() {
    	session = super.getSession();
        clienteSeguridadTab1.inicializa();
        swIniciar=session.getAttribute("swIniciar")!=null?(Boolean)session.getAttribute("swIniciar"):false;
        usuario=session.getAttribute("usuario")!=null?(Usuario)session.getAttribute("usuario"):null;
        tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        
        //______________________
        //damos inform,acion  al usuario de como va z hewredar , dependiendo en donde se ubique del tipo de nodo
        if (tree.getTiponodo()-Utilidades.getNodoRaiz()==0) {
            //raiz=hereda solo raiz,principales,areas,cargos,procesos
            heredaDependeDeTipoNodo=messages.getString("heredaDependeDeTipoNodoRaiz");
            
        }else if (tree.getTiponodo()-Utilidades.getNodoPrincipal()==0) {
            heredaDependeDeTipoNodo=messages.getString("heredaDependeDeTipoNodoPrincipal");
        }else{
            
        }
        
        //_______________________
        
        //si yo pulso esta pestaña, me tapa el frame que me indica cuales son los que  he seleccionado
        if (swIniciar){
            heredaSeguridad=true;
            //session.setAttribute("BanderaSeguridad",Utilidades.getTab1());
            operaciones();
        }
    }
    
    //cuando se pulsa el boton seleccionar usuario
    public String editSeguridad_User(){
        //si viene de la lista del combo, pasa por aca
        //la primera vez zse ejecuta
        if (!swIniciar ){
            //   session.removeAttribute("mostrarCatalogo");
            //  session.setAttribute("BanderaSeguridad",Utilidades.getTab1());
            
            swIniciar=true;
            session.setAttribute("swIniciar",swIniciar);
            session.setAttribute("BanderaSeguridad",Utilidades.getTab1());
            
            
            try {
                
                inicializar(swIniciar);
            } catch (RoleMultiples ex) {
                ex.printStackTrace();
            }
        }else{
            //si viene de la lista multiple de los permisos del usario, pasa por aca
            //blanquea las sessiones y regresa para escijer un nuevo usario
            swIniciar=false;
            session.setAttribute("swIniciar",swIniciar);
            // session.setAttribute("mostrarCatalogo","true");
            //no se inicializa, asi no tgiene valores noi tab1 ni tab2
            session.removeAttribute("BanderaSeguridad");
            
            
            //inicializamos todas las sessiones menio las que se necesitan aqui
            super.clearSession(session,confmessages.getString("seguridadSession"));
        }
        return null;
    }
    public String cancelar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("ClienteSeguridadTab2","cancelar",e);
		}
        
        return "menu";
    }
    
    public void inicializar(boolean sw) throws RoleMultiples{
        
        if (sw){
            if (selectItemUsuario!=null){
                usuario=(Usuario)getSelectItemUsuario().getValue();
                if (usuario !=null){
                    tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
                    visibleItems = new ArrayList();
                    invisibleItems = new ArrayList();
                    customizePageBean=new CustomizePageBean();
                    invisibleItems.clear();
                    visibleItems.clear();
                    
                    datosCombo.llenarOperacionesVisibles(visibleItems,invisibleItems,tree,usuario);
                    
                    session.setAttribute("BanderaSeguridad",Utilidades.getTab1());
                    session.setAttribute("visibleItems",visibleItems);
                    session.setAttribute("invisibleItems",invisibleItems);
                    session.setAttribute("usuario",usuario);
                    session.setAttribute("swIniciar",true);
                    setInvisibleItems(invisibleItems);
                    setVisibleItems(visibleItems);
                    
                }else{
                    FacesContext context = FacesContext.getCurrentInstance();
                    String id =(String) context.getExternalContext().getRequestParameterMap().get("id");
                    usuario=delegado.find_Usuario(new Long(id));
                }
            }else{
                session.setAttribute("swIniciar",false);
            }
            
        }
        
        
    }
    
    public void operaciones(){
        customizePageBean=new CustomizePageBean();
        invisibleItems =session.getAttribute("invisibleItems")!=null?(List)session.getAttribute("invisibleItems"):null;
        visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
        setInvisibleItems(invisibleItems);
        setVisibleItems(visibleItems);
    }
    
    
    
   /* public void  heredarOperacionTreeUsuarioPermiso(Tree tree,Usuario usuario,Operaciones oper2
            ,boolean swElimina) throws EcologicaExcepciones{
      //  System.out.println("padre="+tree.getNombre());
        Seguridad_User seguridad_User = new Seguridad_User();
        seguridad_User.setUsuario(usuario);
        seguridad_User.setTree(tree);
        if (swElimina){
            delegado.destroy(seguridad_User);
        }else{
            seguridad_User.setOperaciones(oper2);
            Seguridad_User segGrabada=delegado.findTreeOperUser(seguridad_User);
            //si es nula o vacia la grabamos
            if (segGrabada==null ){
                clienteSeguridad.grabarOperacionesInConfiguracion( usuario, tree , oper2);
            }
        }
        List<Tree> hijos= llegarHastaHijosTodos(tree);
        for(Tree hijo:hijos){
           // System.out.println("hijo="+hijo.getNombre());
            heredarOperacionTreeUsuarioPermiso(hijo,usuario,oper2,swElimina);
        }
    }*/
    
    public String saveUsuario_Operaciones() throws RoleMultiples{
        String pagIr="";
        try{
            tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
            usuario=session.getAttribute("usuario")!=null?(Usuario)session.getAttribute("usuario"):null;
            if (tree!=null && usuario!=null){
                try{
                    Seguridad_User_Lineal seguridad_User_Lineal1=new Seguridad_User_Lineal();
                    seguridad_User_Lineal1.setUsuario(usuario);
                    seguridad_User_Lineal1.setTree(tree);
                    //eliminamos todos las operaciones del usuario, anteriormente seleccionados, respetando
                    //que el rol sea null, sonm opoeraciones asignadas individuales, pero en lotes por usuario y tree
                    if (heredaSeguridad){
                        List<Seguridad_User_Lineal> eliminamos= delegado.findAllSeguridad_User_Lineal(seguridad_User_Lineal1.getTree(),seguridad_User_Lineal1.getUsuario());
                        if (eliminamos!=null && !eliminamos.isEmpty()){
                            boolean swEliminar=true;
                            clienteSeguridad.heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal1,swEliminar);
                        }
                        
                    }else{
                        delegado.destroy(seguridad_User_Lineal1);
                    }
                    
                    //introducimos las nuevas operaciones al rol
                    visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                    
                    int length = visibleItems.size();
                    boolean swEliminar=false;
                    //Seguridad_Role_Lineal
                    Seguridad_Role_Lineal seguridad_AuxUser_Lineal= new Seguridad_Role_Lineal();
                    for (int i=0; i<length; i++) {
                        SelectItem value = (SelectItem)visibleItems.get(i);
                        Operaciones oper2= (Operaciones) value.getValue();
                        if (oper2!=null && oper2 instanceof Operaciones){
                        	
                        	
                            delegado.llenarSeguridadLineal(oper2,seguridad_AuxUser_Lineal);
                            
                            
                        }
                    }
                    
                    //truco de convertirlo
                    Seguridad_User_Lineal seguridad_User_Lineal=new Seguridad_User_Lineal(seguridad_AuxUser_Lineal);
                    seguridad_User_Lineal.setUsuario(usuario);
                    seguridad_User_Lineal.setTree(tree);
                    if (heredaSeguridad){
                        clienteSeguridad.heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal,swEliminar);
                    }else{
                    	clienteSeguridad.grabarOperacionesInConfiguracion(seguridad_User_Lineal);
                    }
                    
                    
                    
                    //refrescamos la seguridad de la vista del usario para que vea que es lko que ha seleccionado hasta el momento
                    clienteSeguridadTab1.inicializa();
                    //EXCEPCION CON LA SEGURIDAD EN SESSION DEL USUARIO AUI, POR LO GNERAL
                    //S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
                    Usuario user_logueado =session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
                    ClienteSeguridad clienteSeguridad=new ClienteSeguridad();
                    clienteSeguridad.ponerSeguridadInSession(user_logueado);
//                	HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//    				hiloClienteSeguridad.start(); 
                    session.setAttribute("pagIr", Utilidades.getListarAplicacion());
                    pagIr = Utilidades.getFinexitosorefresca();
                    
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                } catch (EcologicaExcepciones e) {
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") + e ));
        }
        
        return pagIr;
    }
    
    
    
    public String saveUsuario_OperacionesRichFaces(List<Operaciones> operaciones) throws RoleMultiples{
        String pagIr="";
        try{
            tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
            
            if (tree!=null && usuario!=null){
                try{
                	
                    Seguridad_User_Lineal seguridad_User_Lineal1=new Seguridad_User_Lineal();
                    seguridad_User_Lineal1.setUsuario(usuario);
                    seguridad_User_Lineal1.setTree(tree);
                    //eliminamos todos las operaciones del usuario, anteriormente seleccionados, respetando
                    //que el rol sea null, sonm opoeraciones asignadas individuales, pero en lotes por usuario y tree
                    if (heredaSeguridad){
                        List<Seguridad_User_Lineal> eliminamos= delegado.findAllSeguridad_User_Lineal(seguridad_User_Lineal1.getTree(),seguridad_User_Lineal1.getUsuario());
                        if (eliminamos!=null && !eliminamos.isEmpty()){
                            boolean swEliminar=true;
                            clienteSeguridad.heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal1,swEliminar);
                        }
                        
                    }else{
                        delegado.destroy(seguridad_User_Lineal1);
                    }
                    
                    //introducimos las nuevas operaciones al rol
                    //visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                    
                  //  int length = visibleItems.size();
                    boolean swEliminar=false;
                    //Seguridad_Role_Lineal
                    Seguridad_Role_Lineal seguridad_AuxUser_Lineal= new Seguridad_Role_Lineal();
                    for (Operaciones oper2:operaciones) {
                        
                        if (oper2!=null && oper2 instanceof Operaciones){
                        	
                         
                        	
                            delegado.llenarSeguridadLineal(oper2,seguridad_AuxUser_Lineal);
                        }
                    }
                      //truco de convertirlo
                    Seguridad_User_Lineal seguridad_User_Lineal=new Seguridad_User_Lineal(seguridad_AuxUser_Lineal);
                    seguridad_User_Lineal.setUsuario(usuario);
                    seguridad_User_Lineal.setTree(tree);
                      if (heredaSeguridad){
                        clienteSeguridad.heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal,swEliminar);
                    }else{
                    	clienteSeguridad.grabarOperacionesInConfiguracion(seguridad_User_Lineal);
                    }
                    
                    //refrescamos la seguridad de la vista del usario para que vea que es lko que ha seleccionado hasta el momento
                    clienteSeguridadTab1.inicializa();
                    //EXCEPCION CON LA SEGURIDAD EN SESSION DEL USUARIO AUI, POR LO GNERAL
                    //S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
                   	
                    Usuario user_logueado =session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
                    ClienteSeguridad clienteSeguridad=new ClienteSeguridad();
                    clienteSeguridad.ponerSeguridadInSession(user_logueado);
                    
                    
            			
//                	HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//    				hiloClienteSeguridad.start(); 
                    session.setAttribute(Utilidades.getNoclearsession(),"");
                    session.setAttribute("pagIr", Utilidades.getListarseguridadtree());
                    pagIr = Utilidades.getFinexitosorefresca();
                    
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                } catch (EcologicaExcepciones e) {
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") + e ));
        }
        
        return pagIr;
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
            session.setAttribute("BanderaSeguridad",Utilidades.getTab1());
        }
        return swIniciar;
    }
    
    public void setSwIniciar(boolean swIniciar) {
        this.swIniciar = swIniciar;
    }
    
    public HtmlSelectOneMenu getSelectItemUsuario() {
        return selectItemUsuario;
    }
    
    public void setSelectItemUsuario(HtmlSelectOneMenu selectItemUsuario) {
        this.selectItemUsuario = selectItemUsuario;
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
    
    public boolean isHeredaSeguridad() {
        return heredaSeguridad;
    }
    
    public void setHeredaSeguridad(boolean heredaSeguridad) {
        this.heredaSeguridad = heredaSeguridad;
    }
    
    public String getHeredaDependeDeTipoNodo() {
        return heredaDependeDeTipoNodo;
    }
    
    public void setHeredaDependeDeTipoNodo(String heredaDependeDeTipoNodo) {
        this.heredaDependeDeTipoNodo = heredaDependeDeTipoNodo;
    }
    
}
