package com.ecological.paper.cliente.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.dias.feriados.DiasFeriadosCliente;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "diasFeriadosView")
@SessionScoped
public class DiasFeriadosView extends DiasFeriadosCliente implements ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<DiasFeriadosBean> allObjectItems = null;
	private DiasFeriadosBean objeto = null;
	private DiasFeriadosBean objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;

	public DiasFeriadosView() {
		//super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new DiasFeriadosBean();
	}

	public List<DiasFeriadosBean> getAllObjectItems() {
		allObjectItems = super.getDiasFeriadosBeans();
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";
		
		 
		if (swEditar) {
			super.setDiasFeriadosBean(objectItem);
			pagIr = super.save();
			//allObjectItems.set(currentCarIndex, objectItem);
		} else {
			
			super.setDiasFeriadosBean(objectItem);
			pagIr = super.create();

		}
	 
	return pagIr;
	}

	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}

	public String remove() {
		String pagIr = "";
		session.setAttribute("diasFeriadosBean",this.objectItem);
		try {
			pagIr = super.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pagIr;
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

	public DiasFeriadosBean getObjeto() {
		return objeto;
	}

	public void setObjeto(DiasFeriadosBean objeto) {
		this.objeto = objeto;
	}

	public DiasFeriadosBean getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(DiasFeriadosBean objectItem) {
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

	public void setAllObjectItems(List<DiasFeriadosBean> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

}
