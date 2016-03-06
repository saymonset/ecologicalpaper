package com.ecological.paper.cliente.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.dias.feriados.DiasFeriadosCliente;
import com.dias.habiles.DiasHabilesByHoras;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "viewDiasHabilesByHoras")
@SessionScoped
public class ViewDiasHabilesByHoras extends DiasHabilesByHoras implements
		ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<DiasHabiles> allObjectItems = null;
	private DiasHabiles objeto = null;
	private DiasHabiles objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;
	
    private String diaHabilNombre;;

	public ViewDiasHabilesByHoras() {
	//	super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new DiasHabiles();
	}

	public List<?> getAllObjectItems() {
		allObjectItems = super.getListaDiasHabiles();
		return allObjectItems;
	}

	 
	public String editar() {
		String pagIr = "";
 
		if (swEditar) {
			super.setDiaHabilNombre(this.getDiaHabilNombre());
			super.setDiasHabiles(objectItem);
			pagIr = super.saveObjeto();
			// allObjectItems.set(currentCarIndex, objectItem);
		} else {
			// super.setDiasFeriadosBean(objectItem);
			// pagIr = super.create();

		}
		return pagIr;
	}

	@Override
	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String remove() {
		// TODO Auto-generated method stub
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

	public DiasHabiles getObjeto() {
		return objeto;
	}

	public void setObjeto(DiasHabiles objeto) {
		this.objeto = objeto;
	}

	public DiasHabiles getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(DiasHabiles objectItem) {
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

	public void setAllObjectItems(List<DiasHabiles> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public String getDiaHabilNombre() {
		return diaHabilNombre;
	}

	public void setDiaHabilNombre(String diaHabilNombre) {
		this.diaHabilNombre = diaHabilNombre;
	}

}
