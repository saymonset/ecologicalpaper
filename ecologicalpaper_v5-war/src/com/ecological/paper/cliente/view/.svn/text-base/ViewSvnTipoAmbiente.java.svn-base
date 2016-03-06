package com.ecological.paper.cliente.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.ecological.paper.cliente.documentos.SvnTipoAmbienteCliente;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "viewSvnTipoAmbiente")
@SessionScoped
public class ViewSvnTipoAmbiente extends SvnTipoAmbienteCliente implements
		ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<SvnTipoAmbiente> allObjectItems = null;
	private SvnTipoAmbiente objeto = null;
	private SvnTipoAmbiente objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;

	private SvnUrlBase svnUrlBase;
	
	private SvnNombreAplicacion svnNombreAplicacion1;

	public ViewSvnTipoAmbiente() {
	//	super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new SvnTipoAmbiente();

		svnUrlBase = new SvnUrlBase();

		svnNombreAplicacion1 = new SvnNombreAplicacion();
	}

	public List<?> getAllObjectItems() {
		super.setSvnnombreaplicacion(this.svnNombreAplicacion1);
		allObjectItems = super.getListaRichFaces();
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";

		if (swEditar) {
			super.setSvnTipoAmbiente(objectItem);
			pagIr = super.save();
			// allObjectItems.set(currentCarIndex, objectItem);
		} else {
			super.setSvnTipoAmbiente(objectItem);
			pagIr = super.create();

		}
		return pagIr;
	}

	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}

	public String remove() {
		session.setAttribute("svnTipoAmbiente", objectItem);
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

	public SvnTipoAmbiente getObjeto() {
		return objeto;
	}

	public void setObjeto(SvnTipoAmbiente objeto) {
		this.objeto = objeto;
	}

	public SvnTipoAmbiente getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(SvnTipoAmbiente objectItem) {
		this.objectItem = objectItem;
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

	public void setAllObjectItems(List<SvnTipoAmbiente> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public SvnUrlBase getSvnUrlBase() {
		return svnUrlBase;
	}

	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		this.svnUrlBase = svnUrlBase;
	}

	 

 
	public SvnNombreAplicacion getSvnNombreAplicacion1() {
		return svnNombreAplicacion1;
	}

	public void setSvnNombreAplicacion1(SvnNombreAplicacion svnNombreAplicacion1) {
		this.svnNombreAplicacion1 = svnNombreAplicacion1;
	}

}
