/*
 * CrearEstado.java
 *
 * Created on 20 de febrero de 2008, 03:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.zonas;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simon
 */
public class CrearEstado  extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private HtmlSelectOneMenu selectPais;
    private ServicioDelegado delegado = new ServicioDelegado();
    private Estado estado;
    private Pais pais;
    private List<Estado> estados;
    //private List<Role> roles;
    private HttpSession session = super.getSession();
    private String strBuscar;
    //_____________________________________
    ResourceBundle messages = super.getResourceBundle("com.ecological.resource.ecologicalpaper");
    ResourceBundle confmessages = super.getResourceBundle("com.util.resource.ecological_conf");
    /** Creates a new instance of ClienteRole */
    private Seguridad seguridadMenu=new Seguridad();
    private boolean swMod;
    private boolean swDel;
    private boolean swAdd;
    private Tree treeNodoActual =null;
    private boolean swSuperUsuario;
    /** Creates a new instance of CrearProfesion */
    public CrearEstado() {
    	session = super.getSession();
        //_________________________________________
        //un truquillo para que no me salga null el pais
        pais=(Pais)session.getAttribute("pais");
        if (pais==null){
            List<Pais>paises1=delegado.find_allPaises();
            for(Pais p:paises1){
                pais=p;
                session.setAttribute("pais",p);
                break;
            }
        }
        //_________________________________________
        //verificamos si es super usuario
        if (super.getUser_logueado()!=null && super.getUser_logueado().getLogin()!=null){
            swSuperUsuario= super.getUser_logueado().getLogin().equalsIgnoreCase(Utilidades.getRoot()) ;
        }
        treeNodoActual=session.getAttribute("treeNodoActual")!=null?(Tree)session.getAttribute("treeNodoActual"):null;
        //CONSEGUIMOS LA SEGURIDAD MENU
        Tree treeMenu = new Tree();
        treeMenu.setNodo(Utilidades.getNodoRaiz());
        seguridadMenu=super.getSeguridadTree(treeMenu);
        
        //variable que viene del menu
        boolean crear = session.getAttribute("crear")!=null;
        if (crear){
            setEstado(new Estado());
            estado.setPais(pais);
        }else{
            setEstado((Estado)session.getAttribute("estado"));
            if (estado!=null){
                estado.setPais(pais);
            }
        }
        
        
    }
    
    
    
    
    public String cancelar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("CrearEstado","cancelar",e);
		}
        
        
        return "listarmenu";
    }
    
    
    public String cancelarListar(){
        super.clearSession(session,confmessages.getString("usuarioSession"));
        return "listar";
    }
    
    public String inic_crear() {
        session.setAttribute("crear",true);
        return "crear";
    }
    public String delete(){
        Estado obj =session.getAttribute("estado")!=null?(Estado)session.getAttribute("estado"):null;
        if (obj!=null){
            estado = new Estado();
            estado.setCodigo(obj.getCodigo());
            estado= delegado.findEstado_InCiudad(estado);
            if (estado==null){
                estado = new Estado();
                estado.setCodigo(obj.getCodigo());
                estado=delegado.find(estado);
                if (estado!=null){
                    estado.setStatus(false);
                    delegado.edit(estado);
                }
                
                getEstados();
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("erro_resgistroenuso") ));
            }
        }
        return "";
    }
    public String edit(){
        return "editEstado";
    }
    public void selection(ActionEvent event) throws RoleMultiples{
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId");
        if ((component!=null) && (component.getValue().toString()!=null)){
            long id = Long.parseLong(component.getValue().toString());
            if (estado==null){
                estado=new Estado();
            }
            //buscamos el objeto
            if (id>=0){
                estado.setCodigo(new Long(id));
            }
            //   System.out.println("pasa?? id="+id);
            estado=delegado.find(estado);
            session.setAttribute("estado",estado);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    public String create() {
        String pagIr="";
        try{
            if ( ("".equals(estado.getNombre().toString().trim()))){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
                return "";
            }
            
            delegado.create(estado);
            session.setAttribute("pagIr",Utilidades.getListarEstados());
            pagIr=Utilidades.getFinexitoso();
        
        } catch (Exception e) {
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") + e ));          
        }
        
        return pagIr;
    }
    
    
    public String saveObjeto(){
        String pagIr="";
        if (("".equals(estado.getNombre().toString().trim())) ){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        delegado.edit(estado);
        pagIr = Utilidades.getFinexitoso();
        session.setAttribute("pagIr",Utilidades.getListarEstados());
        
        
        return pagIr;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String getStrBuscar() {
        strBuscar=(String)session.getAttribute("strBuscar");
        return strBuscar;
    }
    public void setStrBuscar(String strBuscar) {
        session.setAttribute("strBuscar",strBuscar);
        this.strBuscar = strBuscar;
    }
    
    
    public void colocarPaisSession(){
        System.out.println("pasa 1");
        System.out.println("pais="+pais.getNombre());
        session.setAttribute("pais",pais);
        
    }
    
    public List<Estado> getEstados() {
        System.out.println("se doipatra ????");
        /*if (getSelectPais()!=null && getSelectPais().getValue()!=null){
            pais= (Pais)getSelectPais().getValue();
            session.setAttribute("pais",pais);
        }
        pais=(Pais)session.getAttribute("pais");
        if (pais!=null){
            //Pais pais= (Pais)getSelectPais().getValue();
            estados=delegado.findAll_EstadoByPais(pais);
         
        }*/
        pais = (Pais)session.getAttribute("pais");
        
        if (pais!=null){
            System.out.println("pais 2 nombre="+pais.getNombre());
            estados=delegado.findAll_EstadoByPais(pais);
            List<Estado> lista = new ArrayList<Estado>();
            for (Estado p:estados){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
                lista.add(p);
            }
            estados=lista;
        }
        
        // System.out.println("etados vacia="+estados.isEmpty());
        
        System.out.println("etados="+estados.isEmpty());
        return estados;
    }
    
    
    public Seguridad getSeguridadMenu() {
        return seguridadMenu;
    }
    
    public void setSeguridadMenu(Seguridad seguridadMenu) {
        this.seguridadMenu = seguridadMenu;
    }
    
    public boolean isSwMod() {
        swMod=seguridadMenu.isToModUsuario();
        if (swSuperUsuario){
            swMod=true;
        }
        return swMod;
    }
    
    public void setSwMod(boolean swMod) {
        this.swMod = swMod;
    }
    
    public boolean isSwDel() {
        swDel=seguridadMenu.isToDelUsuario();
        if (swSuperUsuario){
            swDel=true;
        }
        return swDel;
    }
    
    public void setSwDel(boolean swDel) {
        this.swDel = swDel;
    }
    
    public boolean isSwAdd() {
        swAdd=seguridadMenu.isToAddUsuario();
        if (swSuperUsuario){
            swAdd=true;
        }
        return swAdd;
    }
    
    public void setSwAdd(boolean swAdd) {
        
        this.swAdd = swAdd;
    }
    
    public Tree getTreeNodoActual() {
        return treeNodoActual;
    }
    
    public void setTreeNodoActual(Tree treeNodoActual) {
        this.treeNodoActual = treeNodoActual;
    }
    
    public boolean isSwSuperUsuario() {
        return swSuperUsuario;
    }
    
    public void setSwSuperUsuario(boolean swSuperUsuario) {
        this.swSuperUsuario = swSuperUsuario;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    
    
    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }
    
    public HtmlSelectOneMenu getSelectPais() {
        return selectPais;
    }
    
    public void setSelectPais(HtmlSelectOneMenu selectPais) {
        this.selectPais = selectPais;
    }
    
    public Pais getPais() {
        pais= (Pais)session.getAttribute("pais");
        return pais;
    }
    
    public void setPais(Pais pais) {
        session.setAttribute("pais", pais);
        this.pais = pais;
    }
    
    
}
