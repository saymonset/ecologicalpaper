/*
 * listarControlFlowByTime.java
 *
 * Created on August 16, 2008, 4:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole_OperacionesPopup;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;

import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class listarControlFlowByTime  extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private ServicioDelegado delegado =new ServicioDelegado();
    private FlowControlByUsuarioBean flowControlByUsuarioBean;
    private List<FlowControlByUsuarioBean> flowControlByUsuarioBeans;
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
    private List<SelectItem> llenarHoras;
    private List<SelectItem> llenarMinutos;
    private String horaServidior;
    private String fechaActualServidor= Utilidades.sdfShowWithoutHour.format(new Date());
    private String diaDeLaSemanaServidor;
    /** Creates a new instance of CrearProfesion */
    public listarControlFlowByTime() {
    	session = super.getSession();
        setDiaDeLaSemanaServidor(super.diaSemana());
        Date fecha = new Date();
        String fechaActual= Utilidades.sdfShowWithoutHour.format(fecha);
          setHoraServidior(super.horaCompleta());
        
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
            flowControlByUsuarioBean=new FlowControlByUsuarioBean();
        }else{
            flowControlByUsuarioBean=(FlowControlByUsuarioBean)session.getAttribute("flowControlByUsuarioBean");
        }
        
        
    }
    
    
    
    
    
    
    
    public String cancelarListar(){
    	try {
			
		} catch (Exception e) {
			redirect("listarControlFlowByTime","cancelarLista",e);
		}
        return "listar";
    }
    
    public String inic_crear() {
        
        session.setAttribute("crear",true);
        return "crear";
    }
    
    public String edit(){
    	session.setAttribute("flowControlByUsuarioBean",flowControlByUsuarioBean);
        return "edit";
    }
    public void select(ActionEvent event) throws RoleMultiples{
        UIParameter component = (UIParameter) event.getComponent().findComponent("editId");
        if ((component!=null) && (component.getValue().toString()!=null)){
            long id = Long.parseLong(component.getValue().toString());
            if (flowControlByUsuarioBean==null){
                flowControlByUsuarioBean=new FlowControlByUsuarioBean();
            }
            //buscamos el objeto
            if (id>=0){
                flowControlByUsuarioBean.setCodigo(new Long(id));
            }
            //   System.out.println("pasa?? id="+id);
            flowControlByUsuarioBean=delegado.find(flowControlByUsuarioBean);
            //flowControlByUsuarioBean.setEnvMailRemember(Utilidades.isInactivo());
            session.setAttribute("flowControlByUsuarioBean",flowControlByUsuarioBean);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    
    
    
    public String saveObjeto(){
        String pagIr="";
        try {
            delegado.edit(flowControlByUsuarioBean);
            pagIr="listar";
           } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_intentar") + e ));
        }
        
        
        return "listar";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String getStrBuscar() {
        strBuscar=(String)session.getAttribute("strBuscar");
        return strBuscar;
    }
    public void setStrBuscar(String strBuscar) {
        session.setAttribute("strBuscar",strBuscar);
        this.strBuscar = strBuscar;
    }
    
    
    
    
    
    
    
    public Seguridad getSeguridadMenu() {
        return seguridadMenu;
    }
    
    public void setSeguridadMenu(Seguridad seguridadMenu) {
        this.seguridadMenu = seguridadMenu;
    }
    
    public boolean isSwMod() {
        swMod=seguridadMenu.isToModProfesiones();
        if (swSuperUsuario){
            swMod=true;
        }
        return swMod;
    }
    
    public void setSwMod(boolean swMod) {
        this.swMod = swMod;
    }
    
    public boolean isSwDel() {
        swDel=seguridadMenu.isToDelProfesiones();
        if (swSuperUsuario){
            swDel=true;
        }
        return swDel;
    }
    
    public void setSwDel(boolean swDel) {
        this.swDel = swDel;
    }
    
    public boolean isSwAdd() {
        swAdd=seguridadMenu.isToAddProfesiones();
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
    
    public FlowControlByUsuarioBean getFlowControlByUsuarioBean() {
        return flowControlByUsuarioBean;
    }
    
    public void setFlowControlByUsuarioBean(FlowControlByUsuarioBean flowControlByUsuarioBean) {
        this.flowControlByUsuarioBean = flowControlByUsuarioBean;
    }
    
    public List<FlowControlByUsuarioBean> getFlowControlByUsuarioBeans() {
        Flow flow =null;
        
        if (session!=null){
            flow = session.getAttribute("flow")!=null?(Flow)session.getAttribute("flow"):null;
        }
        
        if (flow!=null){
            //  List<FlowControlByUsuarioBean> lista= new ArrayList<FlowControlByUsuarioBean>();
            flowControlByUsuarioBeans=delegado.find_allControlTimeByFlowParticipAndRole(flow);
         /*   for(FlowControlByUsuarioBean f:flowControlByUsuarioBeans){
                f.setEnvMailRemember(Utilidades.isInactivo());
                lista.add(f);
            }
             //inicializamos en falso el envio de mails
            flowControlByUsuarioBeans=lista;*/
            
            
        }else{
            System.out.println("flujo nulo");
        }
        
        
        
        return flowControlByUsuarioBeans;
    }
    
    public void setFlowControlByUsuarioBeans(List<FlowControlByUsuarioBean> flowControlByUsuarioBeans) {
        this.flowControlByUsuarioBeans = flowControlByUsuarioBeans;
    }
    
    
    public List<SelectItem> getLlenarHoras() {
        llenarHoras=new ArrayList<SelectItem>();
        SelectItem item;
        String hora="";
        for(int i=0;i<=Utilidades.getHorasNumeroConfigurar();i++){
            hora=""+i;
            if (hora.length()<2){
                hora="0"+hora;
            }
            item=new SelectItem(new Integer(hora),hora);
            llenarHoras.add(item);
        }
        
        return llenarHoras;
    }
    
    
    public List<SelectItem> getLlenarMinutos() {
        llenarMinutos=new ArrayList<SelectItem>();
        SelectItem item;
        String minto;
        for(int i=0;i<=Utilidades.getMinutosNumeroConfigurar();i++){
            minto = ""+i;
            if(minto.length()<2){
                minto ="0"+minto;
            }
            item=new SelectItem(new Integer(minto),minto);
            llenarMinutos.add(item);
        }
        
        return llenarMinutos;
    }

    public String getHoraServidior() {
        return horaServidior;
    }

    public void setHoraServidior(String horaServidior) {
        this.horaServidior = horaServidior;
    }

    public String getFechaActualServidor() {
        return fechaActualServidor;
    }

    public void setFechaActualServidor(String fechaActualServidor) {
        this.fechaActualServidor = fechaActualServidor;
    }

    public String getDiaDeLaSemanaServidor() {
        return diaDeLaSemanaServidor;
    }

    public void setDiaDeLaSemanaServidor(String diaDeLaSemanaServidor) {
        this.diaDeLaSemanaServidor = diaDeLaSemanaServidor;
    }
    
}
