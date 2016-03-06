package com.ecological.extensionesfilecliente;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;

public class ExtensionFileCliente extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private ServicioDelegado delegado =new ServicioDelegado();
    private ExtensionFile extensionFile;
    private List<ExtensionFile> extensionFiles;
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
    public ExtensionFileCliente() {
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
            setExtensionFile(new ExtensionFile());
        }else{
            setExtensionFile((ExtensionFile)session.getAttribute("extensionFile"));
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
        ExtensionFile obj =session.getAttribute("extensionFile")!=null?(ExtensionFile)session.getAttribute("extensionFile"):null;
        if (obj!=null){
            extensionFile = new ExtensionFile();
            extensionFile.setCodigo(obj.getCodigo());
            extensionFile= delegado.find(extensionFile);
            if (extensionFile==null){
                extensionFile = new ExtensionFile();
                extensionFile.setCodigo(obj.getCodigo());
                extensionFile=delegado.find(extensionFile);
                if (extensionFile!=null){
                    extensionFile.setStatus(false);
                    delegado.edit(extensionFile);
                }
                
                getExtensionFiles();
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("erro_resgistroenuso") ));
            }
        }
        return "";
    }
    public String edit(){
        return "edit";
    }
    public void selection(ActionEvent event) throws RoleMultiples{
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId");
        if ((component!=null) && (component.getValue().toString()!=null)){
            long id = Long.parseLong(component.getValue().toString());
            if (extensionFile==null){
                extensionFile=new ExtensionFile();
            }
            //buscamos el objeto
            if (id>=0){
                extensionFile.setCodigo(new Long(id));
            }
            //   System.out.println("pasa?? id="+id);
            extensionFile=delegado.find(extensionFile);
            session.setAttribute("extensionFile",extensionFile);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    public String create() {
        String pagIr="";
        if ( ("".equals(extensionFile.getExtension().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        if ( ("".equals(extensionFile.getMimeType().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        delegado.create(extensionFile);
        session.setAttribute("pagIr",Utilidades.getListarextensionfile());
        pagIr=Utilidades.getFinexitoso();
        
        return pagIr;
    }
    
    
    public String saveObjeto(){
        String pagIr="";
        if ( ("".equals(extensionFile.getExtension().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        if ( ("".equals(extensionFile.getMimeType().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        delegado.edit(extensionFile);
        pagIr = Utilidades.getFinexitoso();
        session.setAttribute("pagIr",Utilidades.getListarextensionfile());
        
        
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
    
    
    
    
    public List<ExtensionFile> getExtensionFiles() {
        if (!super.isEmptyOrNull(getStrBuscar())){
            extensionFiles=delegado.find_allExtensionFile(getStrBuscar());
        }else{
            extensionFiles=delegado.find_allExtensionFile();
        }
        List<ExtensionFile> lista = new ArrayList<ExtensionFile>();
        for (ExtensionFile p:extensionFiles){
          //  p.setDelete(false);
            if (isSwDel()){
            //    p.setDelete(true);
            }
            lista.add(p);
        }
        
        extensionFiles=lista;
        
        return extensionFiles;
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
    
    public ExtensionFile getExtensionFile() {
        return extensionFile;
    }
    
    public void setExtensionFile(ExtensionFile extensionFile) {
        this.extensionFile = extensionFile;
    }
    
    public void setExtensionFiles(List<ExtensionFile> sxtensionFiles) {
        this.extensionFiles = sxtensionFiles;
    }
}
