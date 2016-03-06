/*
 * ClienteSeguridadRole.java
 *
 * Created on July 23, 2007, 4:08 PM
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
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
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
public class ClienteSeguridadRole extends ContextSessionRequest{
    private ServicioDelegado delegado =new ServicioDelegado();
    
       
    
    private DatosCombo datosCombo = new DatosCombo();
    private HttpSession session = super.getSession();
    private  Tree tree;
    private HtmlSelectOneMenu selectItemUsuario;
    private boolean swIniciar;
    private Usuario user_logueado;
    private List usuariosCombo;
    private boolean heredaSeguridad=true;
    ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
    ClienteSeguridadTab1 clienteSeguridadTab1= new ClienteSeguridadTab1();
    private  int irecursivo=0;
    private Tree padreHeredaReferencia;
    private String heredaDependeDeTipoNodo;
    
    public ClienteSeguridadRole(String constructorVacio) {
    	
    }
    
    /** Creates a new instance of ClienteSeguridadRole */
    public ClienteSeguridadRole() {
    	session = super.getSession();
        clienteSeguridadTab1.inicializa();
        swIniciar=session.getAttribute("swIniciar")!=null?(Boolean)session.getAttribute("swIniciar"):false;
        tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
        if (swIniciar){
            heredaSeguridad=true;
            //session.setAttribute("BanderaSeguridad",Utilidades.getTab2());
            operaciones();
        }
        
        
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
        
    }
    //cuando se pulsa el boton seleccionar usuario
    public String editSeguridad_User(){
        //si viene de la lista del combo, pasa por aca
        if (!swIniciar ){
            swIniciar=true;
            //    session.setAttribute("BanderaSeguridad",Utilidades.getTab2());
            //    session.removeAttribute("mostrarCatalogo");
            session.setAttribute("BanderaSeguridad",Utilidades.getTab2());
            session.setAttribute("swIniciar",swIniciar);
            //   session.removeAttribute("mostrarCatalogo");
            //si yo pulso esta pestaña, me tapa el frame que me indica cuales son los que  he seleccionado
            
            
            
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
            //     session.setAttribute("mostrarCatalogo","true");
            session.removeAttribute("BanderaSeguridad");
            //inicializamos todas las sessiones,dejamos solo las basicas
            super.clearSession(session,confmessages.getString("seguridadSession"));
        }
        return null;
    }
    public String cancelar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("ClienteSeguridadRole","cancelar",e);
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
            datosCombo.llenarRoledVisiblesenNodo(visibleItems,invisibleItems,tree);
            session.setAttribute("visibleItems",visibleItems);
            session.setAttribute("invisibleItems",invisibleItems);
            session.setAttribute("swIniciar",true);
            session.setAttribute("BanderaSeguridad",Utilidades.getTab2());
            setInvisibleItems(invisibleItems);
            setVisibleItems(visibleItems);
            
        }
        
        
    }
    
    public void inicializaSiStanEmbasuradas(){
        invisibleItems =session.getAttribute("invisibleItems")!=null?(List)session.getAttribute("invisibleItems"):null;
        visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
        if (!visibleItems.isEmpty()){
            int size=invisibleItems.size();
            for (int i=0;i<size;i++){
                SelectItem value = (SelectItem)invisibleItems.get(i);
                if (value!=null && value.getValue() instanceof Role){
                }else{
                    super.clearSession(session,confmessages.getString("usuarioSession"));
                }
                break;
            }
        }
        if (!visibleItems.isEmpty()){
            int size=visibleItems.size();
            for (int i=0;i<size;i++){
                SelectItem value = (SelectItem)visibleItems.get(i);
                if (value!=null && value.getValue() instanceof Role){
                }else{
                    super.clearSession(session,confmessages.getString("usuarioSession"));
                }
                break;
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
    public void  heredarRolePermiso(Seguridad_Role_Lineal seguridad_Role_Lineal,boolean swElimina,long tipoNodo) throws EcologicaExcepciones{
        if (swElimina){
            //elimina todos los ro,es de este tree
            delegado.destroy(seguridad_Role_Lineal);
        }else{
        	//BUSCAMOS PARA GUARDAR
            List<Seguridad_Role_Lineal> lista=delegado.findSeguridad_Role_RoleAndTree(seguridad_Role_Lineal);
            //si es nula o vacia la grabamos
            if (lista==null || lista.isEmpty()){
                //para que sea totalmente nuevo y  no traiga el primary key viejo
                Seguridad_Role_Lineal seguridad_Role_Lineal2=new Seguridad_Role_Lineal(seguridad_Role_Lineal);
                
                delegado.create(seguridad_Role_Lineal2);
            }
        }
        
        List<Tree> hijos= llegarHastaHijosTodos(seguridad_Role_Lineal.getTree(),tipoNodo);
        for(Tree hijo:hijos){
            seguridad_Role_Lineal.setTree(hijo);
           heredarRolePermiso(seguridad_Role_Lineal,swElimina,tipoNodo);
        }
    }
    
     
    
    
    public String saveRole() throws RoleMultiples{
        String pagIr="";
        try{
            tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
            if (tree!=null){
                try{
                    
                    
                    if (heredaSeguridad){
                        boolean  swEliminar=true;
                        Seguridad_Role_Lineal seguridad_Role_Lineal= new Seguridad_Role_Lineal();
                        //elimina el propio role del arbol donde se encuentra ubicado
                        
                        seguridad_Role_Lineal.setTree(tree);
                        seguridad_Role_Lineal.setRole(null);
                        
                        delegado.heredarRolePermiso(seguridad_Role_Lineal,swEliminar,tree.getTiponodo());
                        
//                        List<Seguridad_Role_Lineal>borrarLista=delegado.findAllSeguridad_Role_Lineal(seguridad_Role_Lineal.getTree());
//                        if (borrarLista!=null && !borrarLista.isEmpty()){
//                            heredarRolePermiso(seguridad_Role_Lineal,swEliminar,tree.getTiponodo());
//                        }
                    }else{
                        //eliminamos todos las operaciones del ro, anteriormente seleccionados
                        Seguridad_Role_Lineal seguridad_Role_Lineal= new Seguridad_Role_Lineal();
                        seguridad_Role_Lineal.setTree(tree);
                        delegado.destroy(seguridad_Role_Lineal);
                    }
                    
                    visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                    
                    int length = visibleItems.size();
                    for (int i=0; i<length; i++) {
                        SelectItem value = (SelectItem)visibleItems.get(i);
                        Role role= (Role) value.getValue();
                        if (role!=null && role instanceof Role){
                            
                            //damos permiso al papa
                            
                            if (heredaSeguridad){
                                boolean swEliminar=false;
                                
                                Seguridad_Role_Lineal  seguridad_Role_Lineal = new Seguridad_Role_Lineal();
                                seguridad_Role_Lineal.setRole(role);
                                seguridad_Role_Lineal.setTree(tree);
                                //EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU SEGURIDA
                                List<Seguridad_Role_Lineal>seguridad_Role_LinealList =delegado.findAllSeguridad_Role_Identificador(role);
                                if (seguridad_Role_LinealList!=null && !seguridad_Role_LinealList.isEmpty()){
                                    seguridad_Role_Lineal=seguridad_Role_LinealList .get(0);
                                    seguridad_Role_Lineal.setRole(role);
                                    seguridad_Role_Lineal.setTree(tree);
                                }
                                //para que sea totalmente nuevo y  no traiga el primary key viejo
                                Seguridad_Role_Lineal seguridad_Role_Lineal2=new Seguridad_Role_Lineal(seguridad_Role_Lineal);
                                delegado.create(seguridad_Role_Lineal2);
                                
                                delegado.heredarRolePermiso(seguridad_Role_Lineal,swEliminar,tree.getTiponodo());
                          //      super.givePermparaverToGroup(seguridad_Role_Lineal2);
                                
                                
                            }else{
                                Seguridad_Role_Lineal  seguridad_Role_Lineal = new Seguridad_Role_Lineal();
                                seguridad_Role_Lineal.setRole(role);
                                seguridad_Role_Lineal.setTree(tree);
                                //EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU SEGURIDA
                                List<Seguridad_Role_Lineal>seguridad_Role_LinealList =delegado.findAllSeguridad_Role_Identificador(role);
                                if (seguridad_Role_LinealList!=null && !seguridad_Role_LinealList.isEmpty()){
                                    seguridad_Role_Lineal=seguridad_Role_LinealList .get(0);
                                    seguridad_Role_Lineal.setRole(role);
                                    seguridad_Role_Lineal.setTree(tree);
                                }
                                //EXCEPCION CON LA SEGURIDAD EN SESSION DEL USUARIO AUI, POR LO GNERAL
                                //S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
                                //para que sea totalmente nuevo y  no traiga el primary key viejo
                                Seguridad_Role_Lineal seguridad_Role_Lineal2=new Seguridad_Role_Lineal(seguridad_Role_Lineal);
                                clienteSeguridad.grabarRole_Tree(seguridad_Role_Lineal2);
                                //&delegado.create(seguridad_Role_Lineal2);
                                //givePermparaverToGroup(seguridad_Role_Lineal2);
                            }
                            
                            
                            
                        }
                    }
                    //actualizamos la s3gureidad en xession
                    user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
                    clienteSeguridad.ponerSeguridadInSession(user_logueado );
//                	HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//    				hiloClienteSeguridad.start();
                    
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                    session.setAttribute("pagIr", Utilidades.getListarAplicacion());
                    pagIr = Utilidades.getFinexitosorefresca();
                } catch (EcologicaExcepciones e) {
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") +  e ));
        }
        return pagIr;
    }
    
    
    public String saveRoleRichFaces(List<Role> roleLst) throws RoleMultiples{
        String pagIr="";
    

        
            tree=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
            if (tree!=null){
               
                	long tiempoInicio = System.currentTimeMillis();
                    
                    if (heredaSeguridad){
                        boolean  swEliminar=true;
                        Seguridad_Role_Lineal seguridad_Role_Lineal= new Seguridad_Role_Lineal();
                        //elimina el propio role del arbol donde se encuentra ubicado
                        
                        seguridad_Role_Lineal.setTree(tree);
                        seguridad_Role_Lineal.setRole(null);
                       // List<Seguridad_Role_Lineal>borrarLista=delegado.findAllSeguridad_Role_Lineal(seguridad_Role_Lineal.getTree());
                        //if (borrarLista!=null && !borrarLista.isEmpty()){
                              try {
								delegado.heredarRolePermiso(seguridad_Role_Lineal,swEliminar,tree.getTiponodo());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                          //}
                    	long totalTiempo = System.currentTimeMillis() - tiempoInicio;
            			System.out.println("borrando seguridad en todos los tree del mis o tree con diferente " +
            					"El tiempo de demora es :"
            					+ totalTiempo + " miliseg");
                    }else{
                        //eliminamos todos las operaciones del ro, anteriormente seleccionados
                        Seguridad_Role_Lineal seguridad_Role_Lineal= new Seguridad_Role_Lineal();
                        seguridad_Role_Lineal.setTree(tree);
                        delegado.destroy(seguridad_Role_Lineal);
                    }
                    
                  //  visibleItems =session.getAttribute("visibleItems")!=null?(List)session.getAttribute("visibleItems"):null;
                    
                     tiempoInicio = System.currentTimeMillis();
                     delegado.seguridadForRolesByTree(roleLst, tree, heredaSeguridad);
//                    for (Role role:roleLst) {
//                        if (role!=null && role instanceof Role){
//                            
//                            //damos permiso al papa
//                            
//                            if (heredaSeguridad){
//                                boolean swEliminar=false;
//                                
//                                Seguridad_Role_Lineal  seguridad_Role_Lineal = new Seguridad_Role_Lineal();
//                                seguridad_Role_Lineal.setRole(role);
//                                seguridad_Role_Lineal.setTree(tree);
//                                //EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU SEGURIDA
//                                List<Seguridad_Role_Lineal>seguridad_Role_LinealList =delegado.findAllSeguridad_Role_Identificador(role);
//                                if (seguridad_Role_LinealList!=null && !seguridad_Role_LinealList.isEmpty()){
//                                    seguridad_Role_Lineal=seguridad_Role_LinealList .get(0);
//                                    seguridad_Role_Lineal.setRole(role);
//                                    seguridad_Role_Lineal.setTree(tree);
//                                }
//                                //para que sea totalmente nuevo y  no traiga el primary key viejo
//                                Seguridad_Role_Lineal seguridad_Role_Lineal2=new Seguridad_Role_Lineal(seguridad_Role_Lineal);
//                                
//                                delegado.create(seguridad_Role_Lineal2);
//                                
//                                delegado.heredarRolePermiso(seguridad_Role_Lineal,swEliminar,tree.getTiponodo());
//                                super.givePermparaverToGroup(seguridad_Role_Lineal2);
//                               
//                                
//                            }else{
//                                Seguridad_Role_Lineal  seguridad_Role_Lineal = new Seguridad_Role_Lineal();
//                                seguridad_Role_Lineal.setRole(role);
//                                seguridad_Role_Lineal.setTree(tree);
//                                //EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU SEGURIDA
//                                List<Seguridad_Role_Lineal>seguridad_Role_LinealList =delegado.findAllSeguridad_Role_Identificador(role);
//                                if (seguridad_Role_LinealList!=null && !seguridad_Role_LinealList.isEmpty()){
//                                    seguridad_Role_Lineal=seguridad_Role_LinealList .get(0);
//                                    seguridad_Role_Lineal.setRole(role);
//                                    seguridad_Role_Lineal.setTree(tree);
//                                }
//                                //EXCEPCION CON LA SEGURIDAD EN SESSION DEL USUARIO AUI, POR LO GNERAL
//                                //S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
//                                //para que sea totalmente nuevo y  no traiga el primary key viejo
//                                Seguridad_Role_Lineal seguridad_Role_Lineal2=new Seguridad_Role_Lineal(seguridad_Role_Lineal);
//                                clienteSeguridad.grabarRole_Tree(seguridad_Role_Lineal2);
//                                //&delegado.create(seguridad_Role_Lineal2);
//                                //givePermparaverToGroup(seguridad_Role_Lineal2);
//                            }
//                        }
//                    }
                    
                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                    System.out.println("colocando hereda seguridad del mismo tree con diferente " +
        					"El tiempo de demora es :"
        					+ totalTiempo + " miliseg");
                    //actualizamos la s3gureidad en xession
                    tiempoInicio = System.currentTimeMillis();
                    user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
                    clienteSeguridad.ponerSeguridadInSession(user_logueado );
//                	HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//    				hiloClienteSeguridad.start();
                	totalTiempo = System.currentTimeMillis() - tiempoInicio;
                    System.out.println("colocando seguridad en usuario " +
        					"El tiempo de demora es :"
        					+ totalTiempo + " miliseg");

                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                    session.setAttribute(Utilidades.getNoclearsession(), "");
                    session.setAttribute("pagIr", Utilidades.getListarseguridadtree());
                    pagIr = Utilidades.getFinexitosorefresca();
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error") ));
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
            session.setAttribute("BanderaSeguridad",Utilidades.getTab2());
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
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }
    
    public boolean isHeredaSeguridad() {
        return heredaSeguridad;
    }
    
    public void setHeredaSeguridad(boolean heredaSeguridad) {
        this.heredaSeguridad = heredaSeguridad;
    }
    
    public Tree getPadreHeredaReferencia() {
        return padreHeredaReferencia;
    }
    
    public void setPadreHeredaReferencia(Tree padreHeredaReferencia) {
        this.padreHeredaReferencia = padreHeredaReferencia;
    }
    
    public String getHeredaDependeDeTipoNodo() {
        return heredaDependeDeTipoNodo;
    }
    
    public void setHeredaDependeDeTipoNodo(String heredaDependeDeTipoNodo) {
        this.heredaDependeDeTipoNodo = heredaDependeDeTipoNodo;
    }
    
    
    
}
