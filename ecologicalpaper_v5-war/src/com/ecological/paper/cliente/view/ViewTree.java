package com.ecological.paper.cliente.view;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.primefaces.event.FileUploadEvent;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.paper.cliente.viewimpl.ViewImp;

import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;
import com.util.file.Archivo;

@ManagedBean(name = "viewTree")
@SessionScoped
public class ViewTree extends ClienteTree implements ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();

	// editar con richfaces
	private List<Tree> allObjectItems = null;
	private Tree objeto = null;
	private Tree objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;
	private Tree treeNodoActual = null;
	private String cualquierComentario;
	private String descripcionTree;
	private ArrayList<FileUploadEvent> filesPrimeFaces = new ArrayList<FileUploadEvent>();
	private File _upFileFile = null;
	public ViewTree() {
		// super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new Tree();
	}

	public List<?> getAllObjectItems() {
		allObjectItems = delegado.findAllEmpresas();
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";
		
		/**
		 * Guardamos la imagen de cada empresa representada por tree
		 */
		for (FileUploadEvent f : filesPrimeFaces) {
			Archivo archivo = new Archivo();
			String ext = f
					.getFile()
					.getFileName()
					.substring(f.getFile().getFileName().lastIndexOf(".") + 1,
							f.getFile().getFileName().length());
			String nom2 = f.getFile().getFileName()
					.substring(0, f.getFile().getFileName().lastIndexOf("."));
			try {
				_upFileFile = archivo.fileDesdeStream(f.getFile()
						.getInputstream(), nom2, ext);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		
		
		super.setDescripcionTree(descripcionTree);

		treeNodoActual = new Tree();
		treeNodoActual.setTiponodo(Utilidades.getNodoRaiz());
		session.setAttribute("treeNodoActual", treeNodoActual);
		session.setAttribute("TipoNodoCrear",
				String.valueOf(Utilidades.getNodoRaiz()));
		
		if (_upFileFile!=null){
			objectItem.setImg(_upFileFile);
		}
		
		super.setTree(objectItem);

		if (swEditar) {

			pagIr = super.saveDatosBasicos_actionEmpresa();

			// allObjectItems.set(currentCarIndex, objectItem);
		} else {

			pagIr = super.ingresarnodoRaiz_actionEmpresa();

		}
		return pagIr;
	}

	public void handleFileUpload(FileUploadEvent event) {

		filesPrimeFaces.add(event);
		 //con este metodo, corremos la lista de mayor  A menor para agarra siempre el ultimo achivo  que se escojio
			Collections.reverse(filesPrimeFaces);
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String aceptar() {
		// TODO Auto-generated method stub
		return null;
	}

	public String remove() {
		session.setAttribute("toDelTree", objectItem);
		super.setCualquierComentario(this.cualquierComentario);
		String pagIr = super.deleteTree();
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

	public Tree getObjeto() {
		return objeto;
	}

	public void setObjeto(Tree objeto) {
		this.objeto = objeto;
	}

	public Tree getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Tree objectItem) {
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

	public void setAllObjectItems(List<Tree> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public String getDescripcionTree() {
		return descripcionTree;
	}

	public void setDescripcionTree(String descripcionTree) {
		this.descripcionTree = descripcionTree;
	}

}
