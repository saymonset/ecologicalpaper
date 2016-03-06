package com.ecological.paper.cliente.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.extensionesfilecliente.ExtensionFileHijosCliente;
import com.ecological.paper.cliente.viewimpl.ViewImp;

import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "viewExtensionFileHijos")
@SessionScoped
public class ViewExtensionFileHijos extends ExtensionFileHijosCliente implements
		ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<ExtensionFileHijos> allObjectItems = null;
	private ExtensionFileHijos objeto = null;
	private ExtensionFileHijos objectItem = null;
	private ExtensionFile extensionFile;
	private int currentCarIndex;
	private int page = 1;
	private String title;

	public ViewExtensionFileHijos() {
		//super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new ExtensionFileHijos();
		extensionFile= new ExtensionFile();
	}

	public List<?> getAllObjectItems() {
		 
		if (extensionFile != null) {
			System.out.println("extensionFile.getExtension()="
					+ extensionFile.getExtension());
			session.setAttribute("extensionFile", extensionFile);

		}
		allObjectItems = super.getExtensionFileHijosLst();
		if (allObjectItems != null) {
			System.out
					.println("allObjectItems.size()=" + allObjectItems.size());
		} else {
			System.out.println("es nulo");
		}
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";

		if (swEditar) {

			objectItem.setExtensionFile(extensionFile);
			super.setExtensionFileHijos(objectItem);
			pagIr = super.saveObjeto();
			// allObjectItems.set(currentCarIndex, objectItem);
		} else {
			super.setExtensionFileHijos(objectItem);
			pagIr = super.create();

		}
		return pagIr;
	}

	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}

	public String remove() {
		session.setAttribute("extensionFileHijos", objectItem);
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

	public ExtensionFileHijos getObjeto() {
		return objeto;
	}

	public void setObjeto(ExtensionFileHijos objeto) {
		this.objeto = objeto;
	}

	public ExtensionFileHijos getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(ExtensionFileHijos objectItem) {
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

	public void setAllObjectItems(List<ExtensionFileHijos> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public ExtensionFile getExtensionFile() {
		return extensionFile;
	}

	public void setExtensionFile(ExtensionFile extensionFile) {
		this.extensionFile = extensionFile;
	}

}
