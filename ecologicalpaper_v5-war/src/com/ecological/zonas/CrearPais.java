/*
 * CrearPais.java
 *
 * Created on 19 de febrero de 2008, 07:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.zonas;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.software.zonas.Pais;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simon
 */
public class CrearPais extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private ServicioDelegado delegado =new ServicioDelegado();
    private Pais pais;
    private List<Pais> paises;
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
    public CrearPais() {
    	session = super.getSession();
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
            setPais(new Pais());
        }else{
            setPais((Pais)session.getAttribute("pais"));
        }
        
        
    }
    
    
    
    
      public String cancelar(){
    	  try {
    		  super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			 redirect("CrearPais","cancelar",e);
		}
        
        return "listarmenu";
    } 
    
    
    public String cancelarListar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("CrearPais","cancelarLista",e);
		}
        
        return "listar";
    }
    
    public String inic_crear() {
        session.setAttribute("crear",true);
        return "crear";
    }
    public String delete(){
        Pais obj =session.getAttribute("pais")!=null?(Pais)session.getAttribute("pais"):null;
        if (obj!=null){
            pais = new Pais();
            pais.setCodigo(obj.getCodigo());
            pais= delegado.findPais_InEstado(pais);
            if (pais==null){
                pais = new Pais();
                pais.setCodigo(obj.getCodigo());
                pais=delegado.find(pais);
                if (pais!=null){
                    pais.setStatus(false);
                    delegado.edit(pais);
                }
                
                getPaises();
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("erro_resgistroenuso") ));
            }
        }
        return "";
    }
    public String edit(){
        return "editPais";
    }
    public void selection(ActionEvent event) throws RoleMultiples{
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId");
        if ((component!=null) && (component.getValue().toString()!=null)){
            long id = Long.parseLong(component.getValue().toString());
            if (pais==null){
                pais=new Pais();
            }
            //buscamos el objeto
            if (id>=0){
                pais.setCodigo(new Long(id));
            }
            //   System.out.println("pasa?? id="+id);
            pais=delegado.find(pais);
            session.setAttribute("pais",pais);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    public String create() {
        String pagIr="";
        if ( ("".equals(pais.getNombre().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        delegado.create(pais);
        session.setAttribute("pagIr",Utilidades.getListarPaises());
        pagIr=Utilidades.getFinexitoso();
        
        return pagIr;
    }
    
    
    public String saveObjeto(){
        String pagIr="";
        if (("".equals(pais.getNombre().toString().trim())) ){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        delegado.edit(pais);
        pagIr = Utilidades.getFinexitoso();
        session.setAttribute("pagIr",Utilidades.getListarPaises());
        
        
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
    
    
    
    
    public List<Pais> getPaises() {
        if (!super.isEmptyOrNull(getStrBuscar())){
            paises=delegado.findAll_Paises(getStrBuscar());
        }else{
            paises=delegado.find_allPaises();
        }
        List<Pais> lista = new ArrayList<Pais>();
        for (Pais p:paises){
            p.setDelete(false);
            if (isSwDel()){
                p.setDelete(true);
            }
            lista.add(p);
        }
        
        paises=lista;
        
        return paises;
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
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
}
