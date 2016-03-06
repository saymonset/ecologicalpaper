package com.ecological.paper.cliente.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.ecological.configuracion.Configuracion;
import com.ecological.paper.cliente.configuracion.ConfiguracionCliente;
import com.ecological.paper.cliente.documentos.ClienteDocumentoTipo;
import com.ecological.paper.cliente.viewimpl.ViewImp;
 
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "viewConfiguracion")
@SessionScoped
public class ViewConfiguracion extends ConfiguracionCliente implements
		ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<Configuracion> allObjectItems = null;
	private Configuracion objeto = null;
	private Configuracion objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;
	
private boolean swLdapPasswordAdmin;
private boolean 	swServerEncryp;
private boolean swServerIpEncryp;
private boolean swNumeroUsuariosEncryp;
private boolean swCompradoEncryp;
private String passwordConf;
private String usuarioProbarLdap;
private String passwordProbarLdap;
	
	public ViewConfiguracion() {
		//super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new Configuracion();
	}
	 
	public List<?> getAllObjectItems() {
		allObjectItems= getConfiguraciones();
		return allObjectItems;
	}
	 
	public String editar() {
		String pagIr = "";
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				
				super.setSwLdapPasswordAdmin(swLdapPasswordAdmin);
				super.setSwServerEncryp(swServerEncryp);
				super.setSwServerIpEncryp(swServerIpEncryp);
				super.setSwNumeroUsuariosEncryp(swNumeroUsuariosEncryp);
				super.setSwCompradoEncryp(swCompradoEncryp);
				super.setPasswordConf(passwordConf);
		 
		if (swEditar) {
			session.setAttribute("empresaEscojer", user_logueado.getEmpresa());
			super.setConfiguracion(objectItem);
			pagIr = super.saveObjeto();
			// allObjectItems.set(currentCarIndex, objectItem);
		} else {
			session.setAttribute("empresaEscojer", user_logueado.getEmpresa());
			super.setConfiguracion(objectItem);
			pagIr = super.create();

		}
		return pagIr;
	}
	
	 public String probarLdap(){
		 super.setUsuarioProbarLdap(this.usuarioProbarLdap);
		 super.setPasswordProbarLdap(this.passwordProbarLdap);
		 
		 String pagIr=super.probarLdap();
		 System.out.println("---------------------paso por aca-----------------");
		 return "";
	 }
	 
	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	public String remove() {
		 session.setAttribute("doc_tipo",objectItem);
		 super.delete();
		return null;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}
	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}
	public Usuario getUser_logueado() {
		return user_logueado;
	}
	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}
	public boolean isSwEditar() {
		return swEditar;
	}
	public void setSwEditar(boolean swEditar) {
		this.swEditar = swEditar;
	}
	 
	public int getCurrentCarIndex() {
		return currentCarIndex;
	}
	public void setCurrentCarIndex(int currentCarIndex) {
		this.currentCarIndex = currentCarIndex;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Configuracion getObjeto() {
		return objeto;
	}

	public void setObjeto(Configuracion objeto) {
		this.objeto = objeto;
	}

	public Configuracion getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Configuracion objectItem) {
		this.objectItem = objectItem;
	}

	public void setAllObjectItems(List<Configuracion> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public boolean isSwLdapPasswordAdmin() {
		return swLdapPasswordAdmin;
	}

	public void setSwLdapPasswordAdmin(boolean swLdapPasswordAdmin) {
		this.swLdapPasswordAdmin = swLdapPasswordAdmin;
	}

	public boolean isSwServerEncryp() {
		return swServerEncryp;
	}

	public void setSwServerEncryp(boolean swServerEncryp) {
		this.swServerEncryp = swServerEncryp;
	}

	public boolean isSwServerIpEncryp() {
		return swServerIpEncryp;
	}

	public void setSwServerIpEncryp(boolean swServerIpEncryp) {
		this.swServerIpEncryp = swServerIpEncryp;
	}

	public boolean isSwNumeroUsuariosEncryp() {
		return swNumeroUsuariosEncryp;
	}

	public void setSwNumeroUsuariosEncryp(boolean swNumeroUsuariosEncryp) {
		this.swNumeroUsuariosEncryp = swNumeroUsuariosEncryp;
	}

	public boolean isSwCompradoEncryp() {
		return swCompradoEncryp;
	}

	public void setSwCompradoEncryp(boolean swCompradoEncryp) {
		this.swCompradoEncryp = swCompradoEncryp;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

 

}
