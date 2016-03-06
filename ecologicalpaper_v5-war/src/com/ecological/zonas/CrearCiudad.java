/*
 * CrearCiudad.java
 *
 * Created on 21 de febrero de 2008, 10:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.zonas;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.software.zonas.Ciudad;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simon
 */
public class CrearCiudad extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private HtmlSelectOneMenu selectPais;
    private ServicioDelegado delegado = new ServicioDelegado();
    private Ciudad ciudad;
    private Pais pais;
    private Estado estado;
    private List<Ciudad> ciudades;
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
    private DatosCombo datosCombo = new DatosCombo();
    /** Creates a new instance of CrearProfesion */
    public CrearCiudad() {
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
            setCiudad(new Ciudad());
            ciudad.setEstado(estado);
        }else{
            setCiudad((Ciudad)session.getAttribute("ciudad"));
            if (estado!=null){
                ciudad.setEstado(estado);
            }
        }
        
        
    }
    
    
    
    
    public String cancelar(){
    	try {
    		super.clearSession(session,confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("CrearCiudad","cancelar",e);
			
		}
        
        
        return "listarmenu";
    }
    
    
    public String cancelarListar(){
        super.clearSession(session,confmessages.getString("usuarioSession"));
        return "listar";
    }
    
    public String inic_crear() {
        String pagIr="";
        
        if (session.getAttribute("pais")==null ||
                session.getAttribute("estado")==null ){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
        }else{
            session.setAttribute("crear",true);
            pagIr="crear";
        }
        return pagIr;
    }
    public String delete(){
        Ciudad obj =session.getAttribute("ciudad")!=null?(Ciudad)session.getAttribute("ciudad"):null;
        if (obj!=null){
            ciudad = new Ciudad();
            ciudad.setCodigo(obj.getCodigo());
            ciudad=delegado.find(ciudad);
            ciudad=delegado.findCiudad_InFacturaEUJOVANS(ciudad);
            if (ciudad==null){
                ciudad = new Ciudad();
                ciudad.setCodigo(obj.getCodigo());
                ciudad=delegado.find(ciudad);
                ciudad.setStatus(false);
                delegado.edit(ciudad);
                getCiudades();
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("erro_resgistroenuso") ));
            }
            
        }
        return "";
    }
    public String edit(){
        return "editCiudad";
    }
    public void selection(ActionEvent event) throws RoleMultiples{
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId");
        if ((component!=null) && (component.getValue().toString()!=null)){
            long id = Long.parseLong(component.getValue().toString());
            if (ciudad==null){
                ciudad=new Ciudad();
            }
            //buscamos el objeto
            if (id>=0){
                ciudad.setCodigo(new Long(id));
            }
            
            ciudad=delegado.find(ciudad);
            session.setAttribute("ciudad",ciudad);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    public String create() {
        String pagIr="";
        try {
            
            
            if ( ("".equals(ciudad.getNombre().toString().trim()))){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
                return "";
            }
            if (!esValidaCadena(ciudad.getNombre())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
            estado=(Estado)session.getAttribute("estado");
            ciudad.setEstado(estado);
            delegado.create(ciudad);
            session.setAttribute("pagIr",Utilidades.getListarCiudad());
            pagIr=Utilidades.getFinexitoso();
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") + e ));
        }
        return pagIr;
    }
    
    
    public String saveObjeto(){
        String pagIr="";
        if (("".equals(ciudad.getNombre().toString().trim())) ){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        estado=(Estado)session.getAttribute("estado");
        ciudad.setEstado(estado);
        delegado.edit(ciudad);
        pagIr = Utilidades.getFinexitoso();
        session.setAttribute("pagIr",Utilidades.getListarCiudad());
        
        
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
        session.setAttribute("pais",pais);
        colocarEstadoSession();
        getEstados();
    }
    public void colocarEstadoSession(){
        session.setAttribute("estado",estado);
        getCiudades();
    }
    
    
    public List<Ciudad> getCiudades() {
        
        ciudades = new ArrayList<Ciudad>();
        estado= (Estado)session.getAttribute("estado");
        if (estado!=null){
            ciudades=delegado.findAll_CiudadByEstado(estado);
            
            List<Ciudad> lista = new ArrayList<Ciudad>();
            for (Ciudad p:ciudades){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
                lista.add(p);
            }
            ciudades=lista;
        }
        
        return ciudades;
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
    
    public Ciudad getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
   /*
       return pais;
    }
    
    public void setPais(Pais pais) {
    
    */
    public Estado getEstado() {
        estado=(Estado)session.getAttribute("estado");
        return estado;
    }
    
    public void setEstado(Estado estado) {
        session.setAttribute("estado", estado);
        
        this.estado = estado;
    }
    
    
    
    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    public List<SelectItem> getEstados() {
        pais=(Pais)session.getAttribute("pais");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Estado edo = new Estado();
        edo.setNombre("");
        edo.setCodigo(0l);
        SelectItem item = new SelectItem(edo, edo.getNombre());
        items.add(item);
        List<SelectItem> items2= datosCombo.getAllEstados(pais);
        items.addAll(items2);
        getCiudades();
        return items;
    }
    
    
    
    
}
