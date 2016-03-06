package com.ecological.paper.cliente.view;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.ecological.paper.crearprofesionpkg.CrearProfesion;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "profesionView")
@SessionScoped
public class ProfesionView extends CrearProfesion {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<Profesion> allObjectItems = null;
	private Profesion objeto = null;
	private Profesion objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;
	

	public ProfesionView() {
		//super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				
				objeto= new Profesion();

	}
	
	public String editar() {
		String pagIr = "";
		
		 
			if (swEditar) {
				super.setProfesion(objectItem);
				pagIr = super.saveObjeto();
				//allObjectItems.set(currentCarIndex, objectItem);
			} else {
				
				super.setProfesion(objectItem);
				pagIr = super.create();

			}
		 
		return pagIr;
	}

	public String aceptar() {
		String pagIr = "";
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", Utilidades.getListarProfesion());
		return pagIr;
	}
	
public List<Profesion> getAllObjectItems() {
		
		allObjectItems = super. getProfesiones();
		
		// System.out.println("user_logueado="+user_logueado.toString());
		// System.out.println("allObjectItems.size()="+allObjectItems.size());
		return allObjectItems;
	}
	
	public String remove() {
		String pagIr = "";
		super.setProfesion(this.objectItem);
	 
		try {
			pagIr = deleteRichfaces();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pagIr;
		//allObjectItems.remove(allObjectItems.get(currentCarIndex));
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

	public boolean isSwEditar() {
		return swEditar;
	}

	public void setSwEditar(boolean swEditar) {
		this.swEditar = swEditar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Profesion getObjeto() {
		return objeto;
	}

	public void setObjeto(Profesion objeto) {
		this.objeto = objeto;
	}

	public Profesion getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Profesion objectItem) {
		this.objectItem = objectItem;
	}

	public void setAllObjectItems(List<Profesion> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

}
