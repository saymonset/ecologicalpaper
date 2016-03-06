package com.ecological.paper.cliente.view;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.usuarios.ClienteUsuario;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "usuarioView")
@SessionScoped
public class UsuarioView extends ClienteUsuario {
	private Usuario usuarioRemplazo;
	private ServicioDelegado delegado = new ServicioDelegado();
	// editar con richfaces
	private List<Usuario> allObjectItems = null;
	private Usuario objectItem = null;
	private int currentCarIndex;
	private int page = 1;

	// SEGURIDAD
	private Seguridad seguridadMenu = new Seguridad();
	private String cualquierComentario;
	private HttpSession session = super.getSession();
	private Usuario user_logueado;
	private boolean swEditarUsuario;
	private String title;
	private Usuario usuario;
	private String passwordOculta;
	private Usuario usuarioEliminar;
	private boolean swExitoso;
	private String passwordConfirm;
	private Seguridad seguridadTree = new Seguridad();
	private boolean toImprimirAdministrar;
	private List<SelectItem> allPrincipal;
	private Role role = new Role();
	private List<Role> invisibleItems = new ArrayList<Role>();
	private List<Role> visibleItems = new ArrayList<Role>();
	private ClienteRole clienteRole = new ClienteRole();

	private DualListModel<Role> operaciones;

	public UsuarioView() {
	//	super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);
		if (seguridadMenu.isToImprimirAdministrar()) {

			toImprimirAdministrar = seguridadMenu.isToImprimirAdministrar();
		}
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// para que refresque cache en datos combo usuario
		session.setAttribute("swCacheUsuario", false);
		usuario = new Usuario();
	}

	public String editar() {
		//REFRTESCAMOS EL ARBOL
		refrescaraplicacionarbol();
		//para recrear el arbol de seguridad denuevo
		String pagIr = "";
		this.usuario = objectItem;
		try {

			if (swEditarUsuario) {
				super.setUsuario(usuario);
				super.setPasswordOculta(passwordOculta);

			 
				session.setAttribute("visibleItems", operaciones.getTarget());

				pagIr = super.saveEditUser();
				// allObjectItems.set(currentCarIndex, objectItem);
			} else {
				session.setAttribute("visibleItems", operaciones.getTarget());
				super.setUsuario(usuario);
				pagIr = super.create();

			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagIr;
	}

	public String edit() {

		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		clienteRole.findRoles_PorUsuarioRichFaces(visibleItems, invisibleItems,
				objectItem);
		operaciones = new DualListModel<Role>(invisibleItems, visibleItems);

		return "edit";
	}

	public String nuevo() {
		 

		//para recrear el arbol de seguridad denuevo
		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		objectItem = new Usuario();

		// el usuario solo me indica que devo treaer los roles de la empresa
		// domnde el pertenece
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		objectItem.setEmpresa(user_logueado.getEmpresa());

		clienteRole.findRoles_PorUsuarioRichFaces(visibleItems, invisibleItems,
				objectItem);
		operaciones = new DualListModel<Role>(invisibleItems, visibleItems);

		return "edit";
	}

	public String aceptar() {
		String pagIr = "listar";

		return pagIr;
	}

	public List<Usuario> getAllObjectItems() {

		allObjectItems = super.getAllObjectItems();
		// List<Usuario> usu= allObjectItems;
		// for(Usuario u:usu){
		// System.out.println("u.getNombre()="+u.getNombre());
		// }
		//
		// System.out.println("user_logueado.getEmpresa().getNodo()="+user_logueado.getEmpresa().getNodo());
		// System.out.println("user_logueado.getEmpresa().getNombre()="+user_logueado.getEmpresa().getNombre());
		// System.out.println("user_logueado="+user_logueado.toString());
		// System.out.println("allObjectItems.size()="+allObjectItems.size());
		return allObjectItems;
	}

	public String removeUsuario() {
		String pagIr = "";
		super.setUsuarioEliminar(this.getUsuarioEliminar());
		super.setUsuario(this.getUsuario());
		super.setUsuarioRemplazo(this.getUsuarioRemplazo());
		super.setCualquierComentario(this.getCualquierComentario());
		super.setUser_logueado(this.getUser_logueado());
		try {
			pagIr = delete2();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pagIr;
		// allObjectItems.remove(allObjectItems.get(currentCarIndex));
	}

	public Usuario getUsuarioRemplazo() {
		return usuarioRemplazo;
	}

	public void setUsuarioRemplazo(Usuario usuarioRemplazo) {
		this.usuarioRemplazo = usuarioRemplazo;
	}

	public void setAllObjectItems(List<Usuario> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public Usuario getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Usuario objectItem) {
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

	public boolean isSwEditarUsuario() {
		return swEditarUsuario;
	}

	public void setSwEditarUsuario(boolean swEditarUsuario) {
		this.swEditarUsuario = swEditarUsuario;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ServicioDelegado getDelegado() {
		return delegado;
	}

	public void setDelegado(ServicioDelegado delegado) {
		this.delegado = delegado;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPasswordOculta() {
		return passwordOculta;
	}

	public void setPasswordOculta(String passwordOculta) {
		this.passwordOculta = passwordOculta;
	}

	public Usuario getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(Usuario usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}

	public boolean isSwExitoso() {
		return swExitoso;
	}

	public void setSwExitoso(boolean swExitoso) {
		this.swExitoso = swExitoso;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public boolean isToImprimirAdministrar() {
		return toImprimirAdministrar;
	}

	public void setToImprimirAdministrar(boolean toImprimirAdministrar) {
		this.toImprimirAdministrar = toImprimirAdministrar;
	}

	public List<SelectItem> getAllPrincipal() {
		return allPrincipal;
	}

	public void setAllPrincipal(List<SelectItem> allPrincipal) {
		this.allPrincipal = allPrincipal;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ClienteRole getClienteRole() {
		return clienteRole;
	}

	public void setClienteRole(ClienteRole clienteRole) {
		this.clienteRole = clienteRole;
	}

	public List<Role> getInvisibleItems() {
		return invisibleItems;
	}

	public List<Role> getVisibleItems() {
		return visibleItems;
	}

	public DualListModel<Role> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(DualListModel<Role> operaciones) {
		this.operaciones = operaciones;
	}
	
	public void refrescarArbolWorkFlows() {
		if (session != null) {
			if (session.getAttribute("docTaskPendiente") != null) {
				session.removeAttribute("docTaskPendiente");
			}
			session.setAttribute(Utilidades.getRefrescararbolworkflows(), "");
		}
	}

	public void refrescaraplicacionarbol() {
		if (session != null) {
			if (session.getAttribute("obtenArbolSeguridad") != null) {
				session.removeAttribute("obtenArbolSeguridad");
			}
			if (session.getAttribute("docBuscar") != null) {
				session.removeAttribute("docBuscar");
			}
			 
			session.setAttribute(Utilidades.getTreeprocesando(), "");
		}
	}

	public void refrescaraplicaciondetalles() {
		if (session != null) {
			if (session.getAttribute("doc_detallesAux") != null) {
				session.removeAttribute("doc_detallesAux");
			}

			if (session.getAttribute("parametro") != null) {
				session.removeAttribute("parametro");
			}

			if (session.getAttribute("clienteDocumentoMaestrosPanelControl") != null) {
				session.removeAttribute("clienteDocumentoMaestrosPanelControl");
			}
			if (session.getAttribute("clienteDocumentoMaestros") != null) {
				session.removeAttribute("clienteDocumentoMaestros");
			}

			if (session.getAttribute("listReferenciaUnavez") != null) {
				session.removeAttribute("listReferenciaUnavez");
			}
		}
	}
}
