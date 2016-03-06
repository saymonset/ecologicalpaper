/*
 * crearFlete.java
 *
 * Created on 21 de febrero de 2008, 09:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sofytware.cliente.euj;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.software.eujovans.clientes.Cliente_EUJ;
import com.software.eujovans.clientes.Factura;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
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
public class crearFlete extends ContextSessionRequest{
    //las variables colocarlas siempre asi
    private HtmlSelectOneMenu selectPais;
    private ServicioDelegado delegado =new ServicioDelegado();
    private Ciudad ciudad;
    private Pais pais;
    private Estado estado;
    
    private Cliente_EUJ cliente_EUJDestinatario;
    private Cliente_EUJ cliente_EUJRemite;
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
    private Estado estadoRemite;
    private Pais paisDestinatario;
    private Pais paisRemite;
    private Estado estadoDestinatario;
    private Ciudad ciudadRemite;
    private Ciudad  ciudadDestinatario;
    private List<SelectItem> listEstadosDestinatario;
    private List<SelectItem> listCiudadDestinatario;
    private List<SelectItem> cliente_EUJ_SDestinatario;
    private List<SelectItem> listEstadosRemite;
    private List<SelectItem> listCiudadRemite;
    private List<SelectItem> cliente_EUJ_SRemite;
    private List<SelectItem> cliente_EUJ_S;
    private List<SelectItem> cliente_EUJ_TODOS;
    private List<SelectItem> cliente_EUJ_SOLO;
    private Factura facturaFlete;
    private List<Factura> factura_S;
    private boolean swAsignaChofer;
    private String txtSaveButton;
    private boolean deshabilitado;
    private boolean swNoPuedeModificar;
    private String download;
    private boolean swDownload;
    private boolean swHistorico;
    private String historicoStr;
    private Usuario buscarPorChofer;
    private Cliente_EUJ buscarDestinatario;
    private Cliente_EUJ buscarRemitente;
    
    private Date buscarFechaCreado;
    private Date buscarFechaCreadoHasta;
    private boolean swFechaCreado;
    
    private Date buscarFechaemitido;
    private Date fechaemitidoHasta;
    
    private boolean swFechaemitido;
    
    private Date buscarFechaconfirmaentrega;
    private Date buscarFechaconfirmaentregaHasta;
    private boolean swFechaconfirmaentrega;
    
    
    
    private Date buscarFechapagado;
    private Date fechapagadoHasta;
    private boolean swFechapagado;
    private String downloadRemesaCobranza;
    private String downloadRemesaCobranza2;
    private boolean swRemesa;
    
    private double calcularFlete_kg_vol;
    private boolean swCalcularFlete_kg_vol;
    
    private double calcularSeguro;
    private boolean swSeguro;
    
    
    private double calcularIva;
    private double valorDeclarado;
    private boolean swIva;
    private boolean swCalcularToTalFletes;
    
    private double calcularTotalFlete;
    
    /** Creates a new instance of CrearProfesion */
    public crearFlete() {
        facturaFlete= new Factura();
        
        
        
        historicoStr=messages.getString("activos");
        
        boolean hist=session.getAttribute("historico")!=null;
        if (hist){
            historicoStr=messages.getString("activos");
        }else{
            historicoStr=messages.getString("doc_hist");
        }
        
        
        //_________________________________________
        //un truquillo para que no me salga null el pais
        pais=(Pais)session.getAttribute("pais");
        estado=(Estado)session.getAttribute("estado");
        ciudad=(Ciudad)session.getAttribute("ciudad");
        
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
            setFacturaFlete(new Factura());
            Factura facturaBuscar= new Factura();
            session.setAttribute("swCalcularFlete_kg_vol",true);
            session.setAttribute("swIva",true);
            session.setAttribute("swSeguro",true);
            facturaBuscar.setStatus(true);
            factura_S=delegado.find_allFactura(facturaBuscar);
            for(Factura f:factura_S){
                facturaFlete.setNumero_entrega(f.getNumero_entrega()+1);
                break;
            }
            
        }else{
            setFacturaFlete((Factura)session.getAttribute("facturaFlete"));
            if (facturaFlete!=null ){
                //puede asignar un chofer si editamos
                if (facturaFlete.getChofer()!=null){
                    swAsignaChofer=true;
                }
                
                
                
                if (facturaFlete.getEstado()==Utilidades.getEstadosinemitir()){
                    txtSaveButton=messages.getString("emitir");
                }else if(facturaFlete.getEstado()==Utilidades.getEstadoemitido()){
                    swNoPuedeModificar=true;
                    txtSaveButton=messages.getString("confirmaentrega");
                }else if(facturaFlete.getEstado()==Utilidades.getEstadoejecutadosinpagar()){
                    swNoPuedeModificar=true;
                    txtSaveButton=messages.getString("pago");
                }else if (facturaFlete.getEstado()==Utilidades.getEstadoejecutadopagado()){
                    swNoPuedeModificar=true;
                    txtSaveButton=messages.getString("estadoejecutadopagado");
                    swDownload=true;
                    deshabilitado=true;
                }
                
            }
        }
        
        
    }
    
    
    
    
    public String cancelar(){
        super.clearSession(session,confmessages.getString("usuarioSession"));
        
        return "listarmenu";
    }
    
    
    public String cancelarListar(){
        super.clearSession(session,confmessages.getString("usuarioSession"));
        return "listar";
    }
    
    public String inic_crear() {
        String pagIr="";
        session.setAttribute("crear",true);
        pagIr="crear";
        
        
        return pagIr;
    }
    public String delete(){
        Factura obj =session.getAttribute("facturaFlete")!=null?(Factura)session.getAttribute("facturaFlete"):null;
        if (obj!=null){
            facturaFlete = new Factura();
            facturaFlete.setCodigo(obj.getCodigo());
            
            facturaFlete=delegado.find(facturaFlete);
            
            if (facturaFlete!=null){
                facturaFlete.setStatus(false);
                if (facturaFlete.getEstado()==Utilidades.getEstadosinemitir()){
                    delegado.destroy(facturaFlete);
                }else{
                    delegado.edit(facturaFlete);
                }
                
            }
            getFactura_S();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("operacion_exitosa") ));
            
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
            if (facturaFlete==null){
                facturaFlete=new Factura();
            }
            //buscamos el objeto
            if (id>=0){
                facturaFlete.setCodigo(new Long(id));
            }
            
            facturaFlete=delegado.find(facturaFlete);
            
            session.setAttribute("facturaFlete",facturaFlete);
            
            
        }
    }
    
/*    public void actionListener(ActionEvent event)throws EcologicaExcepciones,
            RoleMultiples,RoleNoEncontrado{
    }*/
    
    public String create() {
        String pagIr="";
        if ( ("".equals(facturaFlete.getFactura().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        if ( (facturaFlete.getDestinatario()==null)){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        if ( (facturaFlete.getRemitente()==null)){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
        
        facturaFlete.setStatus(Utilidades.isActivo());
        if (!swAsignaChofer){
            facturaFlete.setChofer(null);
            facturaFlete.setEstado(Utilidades.getEstadosinemitir());
        }else{
            facturaFlete.setFechaemitido(new Date());
            facturaFlete.setFechaemitidoonly(new Date());
            facturaFlete.setEstado((int)Utilidades.getEstadoemitido());
        }
        
        swCalcularFlete_kg_vol=session.getAttribute("swCalcularFlete_kg_vol")!=null?(Boolean)session.getAttribute("swCalcularFlete_kg_vol"):false;
        if (swCalcularFlete_kg_vol){
            facturaFlete.setFlete_kg_vol(facturaFlete.getValor_decl()*(facturaFlete.getPorct_flete_kg_vol()/100));
        }
        
        swSeguro=session.getAttribute("swSeguro")!=null?(Boolean)session.getAttribute("swSeguro"):false;
        if (swSeguro){
            facturaFlete.setSeguro(facturaFlete.getValor_decl()*(facturaFlete.getPorct_seguro()/100));
        }
        
        swIva=session.getAttribute("swIva")!=null?(Boolean)session.getAttribute("swIva"):false;
        if (swIva){
            facturaFlete.setIva(facturaFlete.getFlete_kg_vol()*(facturaFlete.getPorct_iva()/100));
        }
        
        facturaFlete.setTotal_fletes(facturaFlete.getFlete_kg_vol()+facturaFlete.getSeguro()+facturaFlete.getIva()) ;
        facturaFlete.setFechaonly(facturaFlete.getFecha());
        
        
        long numNoexiste= facturaFlete.getNumero_entrega();
        boolean sw=true;
        session.removeAttribute("conecutivo1");
        session.removeAttribute("conecutivo12");
        session.setAttribute("conecutivo1",String.valueOf(numNoexiste));
        while (sw){
            Factura f = new Factura();
            f.setNumero_entrega(numNoexiste);
            f = delegado.find_FacturaNumEntrega(f);
            if (f!=null){
                numNoexiste++;
            }else{
                session.setAttribute("conecutivo2",String.valueOf(numNoexiste));
                sw=false;
            }
        }
        facturaFlete.setNumero_entrega(numNoexiste);
        //if (facturaFlete.getFecha()==null){
        facturaFlete.setFecha(new Date());
        //}
        facturaFlete.setFechaonly(new Date());
        
        delegado.create(facturaFlete);
        session.setAttribute("pagIr",Utilidades.getListarFletes_EUJ());
        pagIr=Utilidades.getFinexitoso();
        
        return pagIr;
    }
    
    
    public String saveObjeto(){
        String pagIr="";
        if (!swAsignaChofer){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return pagIr;
        }
        if (facturaFlete.getEstado()==Utilidades.getEstadosinemitir()){
            if (swAsignaChofer){
                //se le asigna chofer y es emitido
                facturaFlete.setEstado((int)Utilidades.getEstadoemitido());
                facturaFlete.setFechaemitido(new Date());
                facturaFlete.setFechaemitidoonly(new Date());
            }
        }else if(facturaFlete.getEstado()==Utilidades.getEstadoemitido()){
            if (swAsignaChofer){
                //si ya es emitido.. y es confirmado.. pasara hacer pagado si es sin credito
                //o por pagar si es con credito
                if (facturaFlete.isCredito()){
                    facturaFlete.setEstado((int)Utilidades.getEstadoejecutadosinpagar());
                    facturaFlete.setFechaconfirmaentrega(new Date());
                    facturaFlete.setFechaconfirmaentregaonly(new Date());
                }else{
                    facturaFlete.setFechaconfirmaentrega(new Date());
                    facturaFlete.setFechapagado(new Date());
                    facturaFlete.setFechaconfirmaentregaonly(new Date());
                    facturaFlete.setFechapagadoonly(new Date());
                    facturaFlete.setEstado((int)Utilidades.getEstadoejecutadopagado());
                }
                
            }
            
            //porque esta pagando
        }else if(facturaFlete.getEstado()==Utilidades.getEstadoejecutadosinpagar()){
            facturaFlete.setFechapagado(new Date());
            facturaFlete.setFechapagadoonly(new Date());
            facturaFlete.setEstado((int)Utilidades.getEstadoejecutadopagado());
        }
        delegado.edit(facturaFlete);
        pagIr = Utilidades.getFinexitoso();
        session.setAttribute("pagIr",Utilidades.getListarFletes_EUJ());
        
        
        return pagIr;
    }
    
    
    
    /*
       String pagIr="";
        if ( ("".equals(facturaFlete.getFactura().toString().trim()))){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("error_camposvacios") ));
            return "";
        }
     
        facturaFlete.setStatus(Utilidades.isActivo());
        if (!swAsignaChofer){
            facturaFlete.setChofer(null);
            facturaFlete.setEstado(Utilidades.getEstadosinemitir());
        }else{
            facturaFlete.setEstado((int)Utilidades.getEstadoemitido());
        }
        delegado.create(facturaFlete);
        session.setAttribute("pagIr",Utilidades.getListarFletes_EUJ());
        pagIr=Utilidades.getFinexitoso();
     
        return pagIr;
     */
    
    
    
    
    
    
    
    
    
    
    
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
    
    public void colocarPaisSessionDestinatario(){
        session.removeAttribute("paisDestinatario");
        session.removeAttribute("estadoDestinatario");
        session.removeAttribute("ciudadDestinatario");
        session.setAttribute("paisDestinatario",paisDestinatario);
        
        listEstadosDestinatario = new ArrayList();
        listEstadosDestinatario.clear();
        listCiudadDestinatario = new ArrayList();
        listCiudadDestinatario.clear();
        cliente_EUJ_SDestinatario = new ArrayList();
        cliente_EUJ_SDestinatario.clear();
        
        
        
    }
    public void colocarEstadoSessionDestinatario(){
        session.setAttribute("estadoDestinatario",estadoDestinatario);
        listEstadosDestinatario = new ArrayList();
        listEstadosDestinatario.clear();
        listCiudadDestinatario = new ArrayList();
        listCiudadDestinatario.clear();
        cliente_EUJ_SDestinatario = new ArrayList();
        cliente_EUJ_SDestinatario.clear();
        
    }
    public void colocarCiudadSessionDestinatario(){
        session.setAttribute("ciudadDestinatario",ciudadDestinatario);
        listCiudadDestinatario = new ArrayList();
        listCiudadDestinatario.clear();
        cliente_EUJ_SDestinatario = new ArrayList();
        cliente_EUJ_SDestinatario.clear();
        
    }
    
    public List<SelectItem> getEstadosDestinatario() {
        paisDestinatario=(Pais)session.getAttribute("paisDestinatario");
        
        
        List<SelectItem> items= new ArrayList<SelectItem>();
        Estado edo = new Estado();
        edo.setNombre("");
        edo.setCodigo(0l);
        SelectItem item = new SelectItem(edo, edo.getNombre());
        items.add(item);
        if (paisDestinatario!=null){
            List<SelectItem> items2= datosCombo.getAllEstados(paisDestinatario);
            items.addAll(items2);
        }
        listEstadosDestinatario = new ArrayList();
        listEstadosDestinatario.clear();
        listEstadosDestinatario.addAll(items);
        return items;
    }
    
    
    public List<SelectItem> getEstadosRemite() {
        paisRemite=(Pais)session.getAttribute("paisRemite");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Estado edo = new Estado();
        edo.setNombre("");
        edo.setCodigo(0l);
        SelectItem item = new SelectItem(edo, edo.getNombre());
        items.add(item);
        if (paisRemite!=null){
            List<SelectItem> items2= datosCombo.getAllEstados(paisRemite);
            items.addAll(items2);
        }
        listEstadosRemite= new ArrayList();
        listEstadosRemite.clear();
        listEstadosRemite.addAll(items);
        return listEstadosRemite;
    }
    
    
    public List<SelectItem> getCiudadesDestinatario() {
        estadoDestinatario=(Estado)session.getAttribute("estadoDestinatario");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Ciudad ciud = new Ciudad();
        ciud.setNombre("");
        ciud.setCodigo(0l);
        SelectItem item = new SelectItem(ciud, ciud.getNombre());
        items.add(item);
        if (estadoDestinatario!=null){
            List<SelectItem> items2= datosCombo.getAllCiudades(estadoDestinatario);
            items.addAll(items2);
            
        }
        listCiudadDestinatario = new ArrayList();
        listCiudadDestinatario.clear();
        listCiudadDestinatario.addAll(items);
        return listCiudadDestinatario;
    }
    public List<SelectItem> getCiudadesRemite() {
        estadoRemite=(Estado)session.getAttribute("estadoRemite");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Ciudad ciud = new Ciudad();
        ciud.setNombre("");
        ciud.setCodigo(0l);
        SelectItem item = new SelectItem(ciud, ciud.getNombre());
        items.add(item);
        if (estadoRemite!=null){
            List<SelectItem> items2= datosCombo.getAllCiudades(estadoRemite);
            items.addAll(items2);
            
        }
        
        listCiudadRemite= new ArrayList();
        listCiudadRemite.clear();
        listCiudadRemite.addAll(items);
        return listCiudadRemite;
        
    }
    public List<SelectItem> getCliente_EUJ_SDestinatario() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ciudadDestinatario= (Ciudad)session.getAttribute("ciudadDestinatario");
        estadoDestinatario= (Estado)session.getAttribute("estadoDestinatario");
        paisDestinatario= (Pais)session.getAttribute("paisDestinatario");
        List<Cliente_EUJ> cliente_EUJ_S= new ArrayList();
        if (ciudadDestinatario!=null || paisDestinatario !=null || estadoDestinatario !=null){
            cliente_EUJ_S=delegado.findAll_CiudadByClienteEUJ(ciudadDestinatario,estadoDestinatario,paisDestinatario,null);
        }
        if(cliente_EUJ_S!=null){
            for (Cliente_EUJ p:cliente_EUJ_S){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
                SelectItem item = new SelectItem(p,p.getNombre());
                items.add(item);
                
            }
        }
        
        return items;
    }
    
    
    public List<SelectItem> getCliente_EUJ_SRemite() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ciudadRemite= (Ciudad)session.getAttribute("ciudadRemite");
        estadoRemite= (Estado)session.getAttribute("estadoRemite");
        paisRemite= (Pais)session.getAttribute("paisRemite");
        List<Cliente_EUJ> cliente_EUJ_S= new ArrayList();
        if (ciudadRemite!=null || paisRemite !=null || estadoRemite!=null){
            cliente_EUJ_S=delegado.findAll_CiudadByClienteEUJ(ciudadRemite,estadoRemite,paisRemite,null);
        }
        if(cliente_EUJ_S!=null){
            for (Cliente_EUJ p:cliente_EUJ_S){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
                SelectItem item = new SelectItem(p,p.getNombre());
                items.add(item);
                
            }
        }
        
        return items;
    }
    
    
    public void colocarPaisSessionRemite(){
        session.removeAttribute("paisRemite");
        session.removeAttribute("estadoRemite");
        session.removeAttribute("ciudadRemite");
        session.setAttribute("paisRemite",paisRemite);
        
        listEstadosRemite= new ArrayList();
        listEstadosRemite.clear();
        listCiudadRemite= new ArrayList();
        listCiudadRemite.clear();
        cliente_EUJ_SRemite= new ArrayList();
        cliente_EUJ_SRemite.clear();
    }
    
    
    
    public void colocarEstadoSessionRemite(){
        session.setAttribute("estadoRemite",estadoRemite);
        listEstadosRemite= new ArrayList();
        listEstadosRemite.clear();
        listCiudadRemite= new ArrayList();
        listCiudadRemite.clear();
        cliente_EUJ_SRemite= new ArrayList();
        cliente_EUJ_SRemite.clear();
    }
    public void colocarCiudadSessionRemite(){
        session.setAttribute("ciudadRemite",ciudadRemite);
        listCiudadRemite= new ArrayList();
        listCiudadRemite.clear();
        cliente_EUJ_SRemite= new ArrayList();
        cliente_EUJ_SRemite.clear();
    }
    
    
    
    public void colocarSearchSession(){
        session.setAttribute("facturaFlete",facturaFlete);
    }
    
    
    
    
    
    
    
    
    public void colocarPaisSession(){
        session.setAttribute("pais",pais);
        colocarEstadoSession();
        getEstados();
        getCiudades();
    }
    public void colocarEstadoSession(){
        session.setAttribute("estado",estado);
        getCiudades();
    }
    public void colocarCiudadSession(){
        session.setAttribute("ciudad",ciudad);
        //   getCiudades();
    }
    
    
    public List<SelectItem> getEstados() {
        pais=(Pais)session.getAttribute("pais");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Estado edo = new Estado();
        edo.setNombre("");
        edo.setCodigo(0l);
        SelectItem item = new SelectItem(edo, edo.getNombre());
        items.add(item);
        if (pais!=null){
            List<SelectItem> items2= datosCombo.getAllEstados(pais);
            items.addAll(items2);
        }
        getCiudades();
        return items;
    }
    
    public List<SelectItem> getCiudades() {
        estado=(Estado)session.getAttribute("estado");
        List<SelectItem> items= new ArrayList<SelectItem>();
        Ciudad ciud = new Ciudad();
        ciud.setNombre("");
        ciud.setCodigo(0l);
        SelectItem item = new SelectItem(ciud, ciud.getNombre());
        items.add(item);
        if (estado!=null){
            List<SelectItem> items2= datosCombo.getAllCiudades(estado);
            items.addAll(items2);
            
        }
        
        return items;
    }
    
    
    
    
    public List<Cliente_EUJ> getCliente_EUJ_S() {
        List<Cliente_EUJ> cliente_EUJ_S= new ArrayList();
        cliente_EUJ_S = new ArrayList<Cliente_EUJ>();
        ciudad= (Ciudad)session.getAttribute("ciudad");
        estado= (Estado)session.getAttribute("estado");
        pais= (Pais)session.getAttribute("pais");
        if (ciudad!=null || pais !=null || estado !=null){
            cliente_EUJ_S=delegado.findAll_CiudadByClienteEUJ(ciudad,estado,pais,null);
        }else{
            cliente_EUJ_S=delegado.find_allCliente_EUJ();
        }
        List<Cliente_EUJ> lista = new ArrayList<Cliente_EUJ>();
        for (Cliente_EUJ p:cliente_EUJ_S){
            p.setDelete(false);
            if (isSwDel()){
                p.setDelete(true);
            }
            lista.add(p);
        }
        cliente_EUJ_S=lista;
        return cliente_EUJ_S;
    }
    
    public List<Factura> getFactura_S() {
        factura_S= new ArrayList();
        factura_S = new ArrayList<Factura>();
        
        boolean hist=session.getAttribute("historico")!=null;
        
        boolean activo=!hist;
        
        //vemos por cual hacemos la busqueda
        strBuscar=(String)session.getAttribute("strBuscar");
        buscarDestinatario = (Cliente_EUJ) session.getAttribute("buscarDestinatario");
        buscarPorChofer = (Usuario) session.getAttribute("buscarPorChofer");
        swAsignaChofer= session.getAttribute("swAsignaChofer")!=null?(Boolean)session.getAttribute("swAsignaChofer"):false;
        buscarRemitente = (Cliente_EUJ) session.getAttribute("buscarRemitente");
        buscarFechaCreado=(Date)session.getAttribute("buscarFechaCreado");
        buscarFechaCreadoHasta=(Date)session.getAttribute("buscarFechaCreadoHasta");
        
        boolean swBuscar=false;
        swFechaCreado= session.getAttribute("swFechaCreado")!=null?(Boolean) session.getAttribute("swFechaCreado"):false;
        buscarFechaemitido= (Date) session.getAttribute("buscarFechaemitido");
        fechaemitidoHasta= (Date) session.getAttribute("fechaemitidoHasta");
        swFechaemitido= session.getAttribute("swFechaemitido")!=null?(Boolean) session.getAttribute("swFechaemitido"):false;
        buscarFechaconfirmaentrega= (Date) session.getAttribute("buscarFechaconfirmaentrega");
        buscarFechaconfirmaentregaHasta= session.getAttribute("buscarFechaconfirmaentregaHasta")!=null?(Date) session.getAttribute("buscarFechaconfirmaentregaHasta"):null;
        swFechaconfirmaentrega= session.getAttribute("swFechaconfirmaentrega")!=null?(Boolean) session.getAttribute("swFechaconfirmaentrega"):false;
        buscarFechapagado= (Date) session.getAttribute("buscarFechapagado");
        fechapagadoHasta= (Date) session.getAttribute("fechapagadoHasta");
        swFechapagado= session.getAttribute("swFechapagado")!=null?(Boolean) session.getAttribute("swFechapagado"):false;
        
        
        Factura facturaBuscar = new Factura();
        facturaBuscar.setStatus(activo);
        
        if (swFechaCreado && buscarFechaCreado!=null){
            facturaBuscar.setFecha(buscarFechaCreado);
            if (buscarFechaCreadoHasta!=null){
                facturaBuscar.setFechaHasta(buscarFechaCreadoHasta);
            }else{
                facturaBuscar.setFechaHasta(null);
            }
            
            
            
        }
        if (swFechaemitido && buscarFechaemitido!=null){
            facturaBuscar.setFechaemitido(buscarFechaemitido);
            if (fechaemitidoHasta!=null){
                facturaBuscar.setFechaemitidoHasta(fechaemitidoHasta);
            }else{
                facturaBuscar.setFechaemitidoHasta(null);
            }
            
        }
        if (swFechaconfirmaentrega && buscarFechaconfirmaentrega!=null){
            facturaBuscar.setFechaconfirmaentrega(buscarFechaconfirmaentrega);
            if (buscarFechaconfirmaentregaHasta!=null){
                facturaBuscar.setFechaconfirmaentregaHasta(buscarFechaconfirmaentregaHasta);
            }else{
                facturaBuscar.setFechaconfirmaentregaHasta(null);
            }
            
        }
        if (swFechapagado && buscarFechapagado!=null){
            facturaBuscar.setFechapagado(buscarFechapagado);
            if (fechapagadoHasta!=null){
                facturaBuscar.setFechapagadoHasta(fechapagadoHasta);
            }else{
                facturaBuscar.setFechapagadoHasta(null);
            }
        }
        if (strBuscar!=null && !super.isEmptyOrNull(strBuscar)){
            long numBuscar=0;
            try {
                numBuscar=Long.parseLong(strBuscar);
                facturaBuscar.setNumero_entrega(numBuscar);
                
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(messages.getString("errorNumero")));
            }
        }
        if (buscarDestinatario!=null){
            swBuscar=true;
            facturaBuscar.setDestinatario(buscarDestinatario);
            
        }
        if (buscarPorChofer!=null && swAsignaChofer){
            swBuscar=true;
            facturaBuscar.setChofer(buscarPorChofer);
        }
        
        if (buscarRemitente!=null){
            swBuscar=true;
            facturaBuscar.setRemitente(buscarRemitente);
            
        }
        
        // se usa para reportes
        session.setAttribute("facturaBuscar",facturaBuscar);
        
        //session para mostrar pr pantall
        facturaBuscar.setReporte(false);
        factura_S=delegado.find_allFactura(facturaBuscar);
        
        List<Factura> lista = new ArrayList<Factura>();
        for (Factura p:factura_S){
            if (p.getFechaonly() == null){
                p.setFechaonly(p.getFecha());
                delegado.edit(p);
            }
            
            if (isSwDel() && p.isStatus()){
                p.setDelete(true);
            }
            if (!p.isStatus()){
                p.setDelete(false);
            }
            
            if (p.getFecha()!=null){
                p.setFechaStr(Utilidades.sdfShow.format(p.getFecha()));
            }
            if (p.getFechaemitido()!=null){
                p.setFechaemitidoStr(Utilidades.sdfShow.format(p.getFechaemitido()));
            }
            
            if (p.getFechaconfirmaentrega()!=null){
                p.setFechaconfirmaentregaStr(Utilidades.sdfShow.format(p.getFechaconfirmaentrega()));
            }
            
            if (p.getFechapagado()!=null){
                p.setFechapagadoStr(Utilidades.sdfShow.format(p.getFechapagado()));
            }
            
            
            
            //obtenemos el status si fue credito o n o fue credito ..
            if (p.isCredito()){
                p.setCreditoStr(messages.getString("concredito"));
            }else{
                p.setCreditoStr(messages.getString("sincredito"));
            }
            
            
            //obtenemos el estatus del flete
            
            if ( (p.getEstado()-Utilidades.getEstadoemitido())==0){
                p.setEstadoStr(messages.getString("estadoemitido"));
            }else if ( (p.getEstado()-Utilidades.getEstadosinemitir())==0){
                p.setEstadoStr(messages.getString("estadosinemitir"));
            }else if ( (p.getEstado()-Utilidades.getEstadoejecutadosinpagar())==0){
                p.setEstadoStr(messages.getString("estadoejecutadosinpagar"));
            }else if ( (p.getEstado()-Utilidades.getEstadoejecutadopagado())==0){
                p.setEstadoStr(messages.getString("estadoejecutadopagado"));
            }
            
            
            
            lista.add(p);
        }
        factura_S=lista;
        
        return factura_S;
    }
    
    
    
    public Estado getEstadoRemite() {
        return estadoRemite;
    }
    
    public void setEstadoRemite(Estado estadoRemite) {
        this.estadoRemite = estadoRemite;
    }
    
    public Pais getPaisDestinatario() {
        return paisDestinatario;
    }
    
    public void setPaisDestinatario(Pais paisDestinatario) {
        this.paisDestinatario = paisDestinatario;
    }
    
    public Pais getPaisRemite() {
        return paisRemite;
    }
    
    public void setPaisRemite(Pais paisRemite) {
        this.paisRemite = paisRemite;
    }
    
    public Estado getEstadoDestinatario() {
        return estadoDestinatario;
    }
    
    public void setEstadoDestinatario(Estado estadoDestinatario) {
        this.estadoDestinatario = estadoDestinatario;
    }
    
    public Ciudad getCiudadRemite() {
        return ciudadRemite;
    }
    
    public void setCiudadRemite(Ciudad ciudadRemite) {
        this.ciudadRemite = ciudadRemite;
    }
    
    public Ciudad getCiudadDestinatario() {
        return ciudadDestinatario;
    }
    
    public void setCiudadDestinatario(Ciudad ciudadDestinatario) {
        this.ciudadDestinatario = ciudadDestinatario;
    }
    
    public Cliente_EUJ getCliente_EUJDestinatario() {
        return cliente_EUJDestinatario;
    }
    
    public void setCliente_EUJDestinatario(Cliente_EUJ cliente_EUJDestinatario) {
        this.cliente_EUJDestinatario = cliente_EUJDestinatario;
    }
    
    public Cliente_EUJ getCliente_EUJRemite() {
        return cliente_EUJRemite;
    }
    
    public void setCliente_EUJRemite(Cliente_EUJ cliente_EUJRemite) {
        this.cliente_EUJRemite = cliente_EUJRemite;
    }
    
    public List<SelectItem> getListEstadosDestinatario() {
        getEstadosDestinatario();
        return listEstadosDestinatario;
    }
    
    public void setListEstadosDestinatario(List<SelectItem> listEstadosDestinatario) {
        this.listEstadosDestinatario = listEstadosDestinatario;
    }
    
    public List<SelectItem> getListCiudadDestinatario() {
        getCiudadesDestinatario();
        return listCiudadDestinatario;
    }
    
    public void setListCiudadDestinatario(List<SelectItem> listCiudadDestinatario) {
        this.listCiudadDestinatario = listCiudadDestinatario;
    }
    
    public void setCliente_EUJ_SDestinatario(List<SelectItem> cliente_EUJ_SDestinatario) {
        this.cliente_EUJ_SDestinatario = cliente_EUJ_SDestinatario;
    }
    
    public List<SelectItem> getListEstadosRemite() {
        getEstadosRemite();
        return listEstadosRemite;
    }
    
    public void setListEstadosRemite(List<SelectItem> listEstadosRemite) {
        this.listEstadosRemite = listEstadosRemite;
    }
    
    public List<SelectItem> getListCiudadRemite() {
        getCiudadesRemite();
        return listCiudadRemite;
    }
    
    
    
    public void setCliente_EUJ_SRemite(List<SelectItem> cliente_EUJ_SRemite) {
        this.cliente_EUJ_SRemite = cliente_EUJ_SRemite;
    }
    
    
    
    public Factura getFacturaFlete() {
        return facturaFlete;
    }
    
    public void setFacturaFlete(Factura facturaFlete) {
        this.facturaFlete = facturaFlete;
    }
    
    
    public void setFactura_S(List<Factura> Factura_S) {
        this.factura_S = Factura_S;
    }
    
    public boolean isSwAsignaChofer() {
        swAsignaChofer= session.getAttribute("swAsignaChofer")!=null?(Boolean)session.getAttribute("swAsignaChofer"):false;
        
        return swAsignaChofer;
    }
    
    public void setSwAsignaChofer(boolean swAsignaChofer) {
        session.setAttribute("swAsignaChofer",swAsignaChofer);
        
        this.swAsignaChofer = swAsignaChofer;
    }
    
    public String getTxtSaveButton() {
        return txtSaveButton;
    }
    
    public void setTxtSaveButton(String txtSaveButton) {
        this.txtSaveButton = txtSaveButton;
    }
    
    public boolean isDeshabilitado() {
        return deshabilitado;
    }
    
    public void setDeshabilitado(boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }
    
    public boolean isSwNoPuedeModificar() {
        return swNoPuedeModificar;
    }
    
    public void setSwNoPuedeModificar(boolean swNoPuedeModificar) {
        this.swNoPuedeModificar = swNoPuedeModificar;
    }
    
    public String getDownload() {
        //setFacturaFlete((Factura)session.getAttribute("facturaFlete"));
        session.setAttribute("facturaFlete",getFacturaFlete());
        return "reporte";
    }
    
    
    
    public void setDownload(String download) {
        this.download = download;
    }
    
    public boolean isSwDownload() {
        return swDownload;
    }
    
    public void setSwDownload(boolean swDownload) {
        this.swDownload = swDownload;
    }
    
    public void setCliente_EUJ_S(List<SelectItem> cliente_EUJ_S) {
        this.cliente_EUJ_S = cliente_EUJ_S;
    }
    
    public List<SelectItem> getCliente_EUJ_SOLO() {
        //cache
        boolean swCacheCliente_EUJ_SOLO= session.getAttribute("swCacheCliente_EUJ_SOLO")!=null?(Boolean)session.getAttribute("swCacheCliente_EUJ_SOLO"):false;
        if (!swCacheCliente_EUJ_SOLO){
            List<Cliente_EUJ> cliente_EUJ_S= new ArrayList();
            cliente_EUJ_S = new ArrayList<Cliente_EUJ>();
            cliente_EUJ_S=delegado.find_allCliente_EUJ();
            cliente_EUJ_TODOS= new ArrayList<SelectItem>();
            List<Cliente_EUJ> lista = new ArrayList<Cliente_EUJ>();
            Cliente_EUJ p1= new Cliente_EUJ();
            p1.setCodigo(0l);
            p1.setNombre("");
            SelectItem item2 = new SelectItem(p1,p1.getNombre());
            cliente_EUJ_TODOS.add(item2);
            for (Cliente_EUJ p:cliente_EUJ_S){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
                
                if (p.isEujovans()){
                    String nombre="";
                    String pais="";
                    String estado="";
                    String ciudad="";
                    if (p.getNombre()!=null && !super.isEmptyOrNull(p.getNombre())){
                        nombre=p.getNombre();
                    }
                    if (p.getPais()!=null && !super.isEmptyOrNull(p.getPais().getNombre())){
                        pais=p.getPais().getNombre();
                    }
                    
                    if (p.getEstado()!=null && !super.isEmptyOrNull(p.getEstado().getNombre())){
                        estado=p.getEstado().getNombre();
                    }
                    
                    if (p.getCiudad()!=null && !super.isEmptyOrNull(p.getCiudad().getNombre())){
                        ciudad=p.getCiudad().getNombre();
                    }
                    SelectItem item = new SelectItem(p,nombre+" "+pais+"->"+estado +"->" +ciudad);
                    
                    cliente_EUJ_TODOS.add(item);
                }
            }
            session.setAttribute("swCacheCliente_EUJ_SOLO",true);
            session.setAttribute("cliente_EUJ_SOLO",cliente_EUJ_TODOS);
        }else{
            
            session.setAttribute("swCacheCliente_EUJ_SOLO",true);
            cliente_EUJ_TODOS=(List<SelectItem>)session.getAttribute("cliente_EUJ_SOLO");
        }
        return cliente_EUJ_TODOS;
    }
    public List<SelectItem> getCliente_EUJ_TODOS() {
        //cache
        boolean swCacheCliente_EUJ_TODOS= session.getAttribute("swCacheCliente_EUJ_TODOS")!=null?(Boolean)session.getAttribute("swCacheCliente_EUJ_TODOS"):false;
        if (!swCacheCliente_EUJ_TODOS){
            List<Cliente_EUJ> cliente_EUJ_S= new ArrayList();
            cliente_EUJ_S = new ArrayList<Cliente_EUJ>();
            cliente_EUJ_S=delegado.find_allCliente_EUJ();
            cliente_EUJ_TODOS= new ArrayList<SelectItem>();
            List<Cliente_EUJ> lista = new ArrayList<Cliente_EUJ>();
            Cliente_EUJ p1= new Cliente_EUJ();
            p1.setCodigo(0l);
            p1.setNombre("");
            SelectItem item2 = new SelectItem(p1,p1.getNombre());
            cliente_EUJ_TODOS.add(item2);
            for (Cliente_EUJ p:cliente_EUJ_S){
                p.setDelete(false);
                if (isSwDel()){
                    p.setDelete(true);
                }
//            lista.add(p);
                String nombre="";
                String pais="";
                String estado="";
                String ciudad="";
                if (p.getNombre()!=null && !super.isEmptyOrNull(p.getNombre())){
                    nombre=p.getNombre();
                }
                if (p.getPais()!=null && !super.isEmptyOrNull(p.getPais().getNombre())){
                    pais=p.getPais().getNombre();
                }
                
                if (p.getEstado()!=null && !super.isEmptyOrNull(p.getEstado().getNombre())){
                    estado=p.getEstado().getNombre();
                }
                
                if (p.getCiudad()!=null && !super.isEmptyOrNull(p.getCiudad().getNombre())){
                    ciudad=p.getCiudad().getNombre();
                }
                
                SelectItem item = new SelectItem(p,nombre+" "+pais+"->"+estado +"->" +ciudad);
                cliente_EUJ_TODOS.add(item);
            }
            //se guarda en cache
            
            session.setAttribute("swCacheCliente_EUJ_TODOS",true);
            session.setAttribute("cliente_EUJ_TODOS",cliente_EUJ_TODOS);
        }else{
            
            session.setAttribute("swCacheCliente_EUJ_TODOS",true);
            cliente_EUJ_TODOS=(List<SelectItem>)session.getAttribute("cliente_EUJ_TODOS");
        }
        
        
        //List<SelectItem>
        return cliente_EUJ_TODOS;
    }
    
    
    public void setCliente_EUJ_TODOS(List<SelectItem> cliente_EUJ_TODOS) {
        this.cliente_EUJ_TODOS = cliente_EUJ_TODOS;
    }
    
    
    public boolean isSwHistorico() {
        return swHistorico;
    }
    
    public void setSwHistorico(boolean swHistorico) {
        this.swHistorico = swHistorico;
    }
    
    public String historico() {
        if (session.getAttribute("historico")==null){
            session.setAttribute("historico",true);
            swHistorico=true;
        }else{
            session.removeAttribute("historico");
            swHistorico=false;
        }
        return "";
    }
    
    public String getHistoricoStr() {
        return historicoStr;
    }
    
    public void setHistoricoStr(String historicoStr) {
        this.historicoStr = historicoStr;
    }
    
    public Usuario getBuscarPorChofer() {
        buscarPorChofer = (Usuario) session.getAttribute("buscarPorChofer");
        return buscarPorChofer;
    }
    
    public void setBuscarPorChofer(Usuario buscarPorChofer) {
        session.setAttribute("buscarPorChofer",buscarPorChofer);
        
        this.buscarPorChofer = buscarPorChofer;
    }
    
    public Cliente_EUJ getBuscarDestinatario() {
        buscarDestinatario = (Cliente_EUJ) session.getAttribute("buscarDestinatario");
        return buscarDestinatario;
    }
    
    public void setBuscarDestinatario(Cliente_EUJ buscarDestinatario) {
        session.setAttribute("buscarDestinatario",buscarDestinatario);
        this.buscarDestinatario = buscarDestinatario;
    }
    
    public Cliente_EUJ getBuscarRemitente() {
        buscarRemitente = (Cliente_EUJ) session.getAttribute("buscarRemitente");
        return buscarRemitente;
    }
    
    public void setBuscarRemitente(Cliente_EUJ buscarRemitente) {
        session.setAttribute("buscarRemitente",buscarRemitente);
        this.buscarRemitente = buscarRemitente;
    }
    
    public Date getBuscarFechaCreado() {
        buscarFechaCreado= (Date) session.getAttribute("buscarFechaCreado");
        return buscarFechaCreado;
    }
    
    public void setBuscarFechaCreado(Date buscarFechaCreado) {
        session.setAttribute("buscarFechaCreado",buscarFechaCreado);
        this.buscarFechaCreado = buscarFechaCreado;
    }
    
    public boolean isSwFechaCreado() {
        swFechaCreado= session.getAttribute("swFechaCreado")!=null?(Boolean) session.getAttribute("swFechaCreado"):false;
        return swFechaCreado;
    }
    
    public void setSwFechaCreado(boolean swFechaCreado) {
        session.setAttribute("swFechaCreado",swFechaCreado);
        this.swFechaCreado = swFechaCreado;
    }
    
    public Date getBuscarFechaemitido() {
        buscarFechaemitido= (Date) session.getAttribute("buscarFechaemitido");
        return buscarFechaemitido;
    }
    
    public void setBuscarFechaemitido(Date buscarFechaemitido) {
        session.setAttribute("buscarFechaemitido",buscarFechaemitido);
        this.buscarFechaemitido = buscarFechaemitido;
    }
    
    public boolean isSwFechaemitido() {
        swFechaemitido= session.getAttribute("swFechaemitido")!=null?(Boolean) session.getAttribute("swFechaemitido"):false;
        return swFechaemitido;
    }
    
    public void setSwFechaemitido(boolean swFechaemitido) {
        session.setAttribute("swFechaemitido",swFechaemitido);
        this.swFechaemitido = swFechaemitido;
    }
    
    public Date getBuscarFechaconfirmaentrega() {
        buscarFechaconfirmaentrega= (Date) session.getAttribute("buscarFechaconfirmaentrega");
        return buscarFechaconfirmaentrega;
    }
    
    public void setBuscarFechaconfirmaentrega(Date buscarFechaconfirmaentrega) {
        session.setAttribute("buscarFechaconfirmaentrega",buscarFechaconfirmaentrega);
        this.buscarFechaconfirmaentrega = buscarFechaconfirmaentrega;
    }
    
    public boolean isSwFechaconfirmaentrega() {
        swFechaconfirmaentrega= session.getAttribute("swFechaconfirmaentrega")!=null?(Boolean) session.getAttribute("swFechaconfirmaentrega"):false;
        return swFechaconfirmaentrega;
    }
    
    public void setSwFechaconfirmaentrega(boolean swFechaconfirmaentrega) {
        session.setAttribute("swFechaconfirmaentrega",swFechaconfirmaentrega);
        this.swFechaconfirmaentrega = swFechaconfirmaentrega;
    }
    
    public Date getBuscarFechapagado() {
        buscarFechapagado= (Date) session.getAttribute("buscarFechapagado");
        return buscarFechapagado;
    }
    
    public void setBuscarFechapagado(Date buscarFechapagado) {
        session.setAttribute("buscarFechapagado",buscarFechapagado);
        this.buscarFechapagado = buscarFechapagado;
    }
    
    public boolean isSwFechapagado() {
        swFechapagado= session.getAttribute("swFechapagado")!=null?(Boolean) session.getAttribute("swFechapagado"):false;
        return swFechapagado;
    }
    
    public void setSwFechapagado(boolean swFechapagado) {
        session.setAttribute("swFechapagado",swFechapagado);
        this.swFechapagado = swFechapagado;
    }
    
    
    
    public void setDownloadRemesaCobranza(String downloadRemesaCobranza) {
        this.downloadRemesaCobranza = downloadRemesaCobranza;
    }
    
    public boolean isSwRemesa() {
        swRemesa= session.getAttribute("swRemesa")!=null?(Boolean) session.getAttribute("swRemesa"):false;
        return swRemesa;
    }
    
    public void setSwRemesa(boolean swRemesa) {
        session.setAttribute("swRemesa",swRemesa);
        this.swRemesa = swRemesa;
    }
    
    public String getDownloadRemesaCobranza2() {
        Factura facturaBuscar=(Factura)session.getAttribute("facturaBuscar");
        facturaBuscar.setReporte(true);
        factura_S=delegado.find_allFactura(facturaBuscar);
        // se usa para reportes
        session.setAttribute("factura_S",factura_S);
        session.setAttribute("swRemesa",true);
        return "ServletReporteREMESA";
    }
    public String getDownloadRemesaCobranza() {
        Factura facturaBuscar=(Factura)session.getAttribute("facturaBuscar");
        facturaBuscar.setReporte(true);
        factura_S=delegado.find_allFactura(facturaBuscar);
        // se usa para reportes
        session.setAttribute("factura_S",factura_S);
        session.setAttribute("swRemesa",false);
        return "ServletReporteREMESA";
    }
    public void setDownloadRemesaCobranza2(String downloadRemesaCobranza2) {
        this.downloadRemesaCobranza2 = downloadRemesaCobranza2;
    }
    
    public double getCalcularFlete_kg_vol() {
        
        if (isSwCalcularFlete_kg_vol()){
            calcularFlete_kg_vol=facturaFlete.getPorct_flete_kg_vol()*facturaFlete.getValor_decl();
        }
        
        
        session.setAttribute("calcularSeguro",calcularSeguro);
        facturaFlete.setFlete_kg_vol(calcularSeguro);
        return calcularFlete_kg_vol;
    }
    
    public void setCalcularFlete_kg_vol(double calcularFlete_kg_vol) {
        
        session.setAttribute("calcularFlete_kg_vol",calcularFlete_kg_vol);
        this.calcularFlete_kg_vol = calcularFlete_kg_vol;
    }
    
    public boolean isSwCalcularFlete_kg_vol() {
        swCalcularFlete_kg_vol=session.getAttribute("swCalcularFlete_kg_vol")!=null?(Boolean)session.getAttribute("swCalcularFlete_kg_vol"):false;
        
        return swCalcularFlete_kg_vol;
    }
    
    public void setSwCalcularFlete_kg_vol(boolean swCalcularFlete_kg_vol) {
        session.setAttribute("swCalcularFlete_kg_vol",swCalcularFlete_kg_vol);
        
        this.swCalcularFlete_kg_vol = swCalcularFlete_kg_vol;
    }
    
    public double getCalcularSeguro() {
        if (isSwSeguro()){
            calcularSeguro=facturaFlete.getPorct_seguro()*facturaFlete.getValor_decl();
        }
        session.setAttribute("calcularSeguro",calcularSeguro);
        facturaFlete.setSeguro(calcularSeguro);
        //facturaFlete.setTotal_fletes(facturaFlete.getFlete_kg_vol()+facturaFlete.getSeguro()+facturaFlete.getIva());
        
        return calcularSeguro;
    }
    
    public void setCalcularSeguro(double calcularSeguro) {
        this.calcularSeguro = calcularSeguro;
    }
    
    public boolean isSwSeguro() {
        swSeguro=session.getAttribute("swSeguro")!=null?(Boolean)session.getAttribute("swSeguro"):false;
        return swSeguro;
    }
    
    public void setSwSeguro(boolean swSeguro) {
        session.setAttribute("swSeguro",swSeguro);
        this.swSeguro = swSeguro;
    }
    
    public double getCalcularIva() {
        
        calcularFlete_kg_vol=session.getAttribute("calcularFlete_kg_vol")!=null?(Double)session.getAttribute("calcularFlete_kg_vol"):0;
        System.out.println("getCalcularIva(=calcularFlete_kg_vol="+calcularFlete_kg_vol);
        System.out.println("facturaFlete.getFlete_kg_vol()="+facturaFlete.getFlete_kg_vol());
        
        if (isSwIva()){
            System.out.println("----------nsi--------------------------");
            calcularIva=facturaFlete.getPorct_iva()*calcularFlete_kg_vol;
        }else{
            
        }
        
        facturaFlete.setIva(calcularIva);
        session.setAttribute("calcularIva",calcularIva);
        //facturaFlete.setTotal_fletes(facturaFlete.getFlete_kg_vol()+facturaFlete.getSeguro()+facturaFlete.getIva());
        return calcularIva;
    }
    
    public void setCalcularIva(double calcularIva) {
        this.calcularIva = calcularIva;
    }
    
    public boolean isSwIva() {
        swIva=session.getAttribute("swIva")!=null?(Boolean)session.getAttribute("swIva"):false;
        return swIva;
    }
    
    public void setSwIva(boolean swIva) {
        session.setAttribute("swIva",swIva);
        this.swIva = swIva;
    }
    
    public double getCalcularTotalFlete() {
        //calcularTotalFlete=facturaFlete.getFlete_kg_vol()+facturaFlete.getSeguro()+facturaFlete.getIva();
        calcularFlete_kg_vol=session.getAttribute("calcularFlete_kg_vol")!=null?(Double)session.getAttribute("calcularFlete_kg_vol"):0;
        calcularSeguro=session.getAttribute("calcularSeguro")!=null?(Double)session.getAttribute("calcularSeguro"):0;
        calcularIva=session.getAttribute("calcularIva")!=null?(Double)session.getAttribute("calcularIva"):0;
        calcularTotalFlete=calcularIva+calcularSeguro+calcularFlete_kg_vol;
        
        System.out.println("------------------------------------");
        if ( isSwCalcularToTalFletes() ){
            
            facturaFlete.setTotal_fletes(calcularTotalFlete);
        }else{
            
        }
        
        return calcularTotalFlete;
    }
    
    public void setCalcularTotalFlete(double calcularTotalFlete) {
        this.calcularTotalFlete = calcularTotalFlete;
    }
    
    public boolean isSwCalcularToTalFletes() {
        swCalcularToTalFletes=session.getAttribute("swCalcularToTalFletes")!=null?(Boolean)session.getAttribute("swCalcularToTalFletes"):false;
        return swCalcularToTalFletes;
    }
    
    public void setSwCalcularToTalFletes(boolean swCalcularToTalFletes) {
        session.setAttribute("swCalcularToTalFletes",swCalcularToTalFletes);
        this.swCalcularToTalFletes = swCalcularToTalFletes;
    }
    
    public double getValorDeclarado() {
        valorDeclarado=session.getAttribute("valorDeclarado")!=null?(Double)session.getAttribute("valorDeclarado"):0;
        facturaFlete.setValor_decl(valorDeclarado);
        return valorDeclarado;
    }
    
    public void setValorDeclarado(double valorDeclarado) {
        session.setAttribute("valorDeclarado",valorDeclarado);
        this.valorDeclarado = valorDeclarado;
    }
    
    public Date getBuscarFechaconfirmaentregaHasta() {
        buscarFechaconfirmaentregaHasta=session.getAttribute("buscarFechaconfirmaentregaHasta")!=null?(Date)session.getAttribute("buscarFechaconfirmaentregaHasta"):null;
        return buscarFechaconfirmaentregaHasta;
    }
    
    public void setBuscarFechaconfirmaentregaHasta(Date buscarFechaconfirmaentregaHasta) {
        session.setAttribute("buscarFechaconfirmaentregaHasta",buscarFechaconfirmaentregaHasta);
        this.buscarFechaconfirmaentregaHasta = buscarFechaconfirmaentregaHasta;
    }
    
    public void setCliente_EUJ_SOLO(List<SelectItem> cliente_EUJ_SOLO) {
        this.cliente_EUJ_SOLO = cliente_EUJ_SOLO;
    }
    
    public Date getBuscarFechaCreadoHasta() {
        buscarFechaCreadoHasta=session.getAttribute("buscarFechaCreadoHasta")!=null?(Date)session.getAttribute("buscarFechaCreadoHasta"):null;
        return buscarFechaCreadoHasta;
    }
    
    public void setBuscarFechaCreadoHasta(Date buscarFechaCreadoHasta) {
        session.setAttribute("buscarFechaCreadoHasta",buscarFechaCreadoHasta);
        this.buscarFechaCreadoHasta = buscarFechaCreadoHasta;
    }
    
    public Date getFechaemitidoHasta() {
        fechaemitidoHasta=session.getAttribute("fechaemitidoHasta")!=null?(Date)session.getAttribute("fechaemitidoHasta"):null;
        
        return fechaemitidoHasta;
    }
    
    public void setFechaemitidoHasta(Date fechaemitidoHasta) {
        session.setAttribute("fechaemitidoHasta",fechaemitidoHasta);
        this.fechaemitidoHasta = fechaemitidoHasta;
    }
    
    public Date getFechapagadoHasta() {
        fechapagadoHasta=session.getAttribute("fechapagadoHasta")!=null?(Date)session.getAttribute("fechapagadoHasta"):null;
        
        return fechapagadoHasta;
    }
    
    public void setFechapagadoHasta(Date fechapagadoHasta) {
        session.setAttribute("fechapagadoHasta",fechapagadoHasta);
        this.fechapagadoHasta = fechapagadoHasta;
    }
    
    
}
