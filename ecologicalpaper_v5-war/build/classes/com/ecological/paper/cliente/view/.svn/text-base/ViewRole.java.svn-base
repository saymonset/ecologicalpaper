package com.ecological.paper.cliente.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@ManagedBean(name = "viewRole")
@SessionScoped
public class ViewRole extends ClienteRole implements ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	// editar con richfaces
	private List<Role> allObjectItems = null;
	private Role objeto = null;
	private Role objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;
	private Tree empresa;
	private ClienteOperaciones clienteOperaciones = new ClienteOperaciones();
	private List<Operaciones> invisibleItems = new ArrayList<Operaciones>();
	private List<Operaciones> visibleItems = new ArrayList<Operaciones>();
	private List<Usuario> invisibleItems2 = new ArrayList<Usuario>();
	private List<Usuario> visibleItems2 = new ArrayList<Usuario>();
	private DualListModel<Operaciones> operaciones;
	private DualListModel<Usuario> operaciones2;
	private boolean usadoParaCrearFlujo;
	private List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
	private boolean swSuperUsuario;

	public ViewRole() {
		//super();
		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			empresa = user_logueado.getEmpresa();
		}

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		objeto = new Role();
	}

	public List<?> getAllObjectItems() {
		this.setUsadoParaCrearFlujo(false);
		super.setUsadoParaCrearFlujo(false);
		session.setAttribute("empresaEscojer", empresa);
		allObjectItems = super.getRoles();
		// esto para que no busque permisos de un nodo especifico
		session.removeAttribute("treeNodoActual");
		// fin esto para que no busque permisos de un nodo especifico
		participantesGruposPlantila = participantesPerfilesPlantila(allObjectItems);

		return allObjectItems;
	}

	public List<?> getAllObjectItemsWorkFlows() {
		this.setUsadoParaCrearFlujo(true);
		super.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
		session.setAttribute("empresaEscojer", empresa);
		allObjectItems = super.getRoles();
		participantesGruposPlantila = participantesPerfilesPlantila(allObjectItems);
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";
	//	System.out.println("swEditar=" + swEditar);
		// me permite saber si en un grupo para workflow o un grupo de
		// privilegios
		super.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
		// fin me permite saber si en un grupo para workflow o un grupo de
		// privilegios

		// refrescar arblol
		refrescaraplicacionarbol();
		super.setRole(objectItem);
		session.setAttribute("visibleItems", operaciones.getTarget());
		session.setAttribute("visibleItems2", operaciones2.getTarget());
		if (swEditar) {

			pagIr = super.saveRoleRichFaces();
			if (usadoParaCrearFlujo) {
				session.setAttribute("pagIr", "listarRoleWorkFlow");
			}

		} else {

			super.setEmpresa(empresa);
			super.setRole(objectItem);
			pagIr = super.create();
			if (usadoParaCrearFlujo) {
				session.setAttribute("pagIr", "listarRoleWorkFlow");
			}

		}

		return pagIr;
	}

	public String edit() {

		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(objectItem);
		visibleItems = new ArrayList();
		visibleItems2 = new ArrayList();
		invisibleItems2 = new ArrayList();
		invisibleItems = new ArrayList();
		clienteOperaciones.llenarOperacionesVisiblesRichFaces(visibleItems,
				invisibleItems, seguridad_Role_Lineal);
		operaciones = new DualListModel<Operaciones>(invisibleItems,
				visibleItems);

		super.findUsuario_PorRolesRichFaces(visibleItems2, invisibleItems2,
				objectItem);

		operaciones2 = new DualListModel<Usuario>(invisibleItems2,
				visibleItems2);

		return "edit";
	}

	public String aceptar() {
		String pagIr = "listarRole";
		if (usadoParaCrearFlujo) {
			pagIr = "listarRoleWorkFlow";
			session.setAttribute("pagIr", "");
		}
		return pagIr;
	}

	public String remove() {
		session.setAttribute("role", objectItem);
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

	public Role getObjeto() {
		return objeto;
	}

	public void setObjeto(Role objeto) {
		this.objeto = objeto;
	}

	public Role getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Role objectItem) {
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

	public void setAllObjectItems(List<Role> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public Tree getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Tree empresa) {
		this.empresa = empresa;
	}

	public ClienteOperaciones getClienteOperaciones() {
		return clienteOperaciones;
	}

	public void setClienteOperaciones(ClienteOperaciones clienteOperaciones) {
		this.clienteOperaciones = clienteOperaciones;
	}

	public List<Operaciones> getInvisibleItems() {
		return invisibleItems;
	}

	public DualListModel<Operaciones> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(DualListModel<Operaciones> operaciones) {
		this.operaciones = operaciones;
	}

	public List<Operaciones> getVisibleItems() {
		return visibleItems;
	}

	public List<Usuario> getInvisibleItems2() {
		return invisibleItems2;
	}

	public List<Usuario> getVisibleItems2() {
		return visibleItems2;
	}

	public DualListModel<Usuario> getOperaciones2() {
		return operaciones2;
	}

	public void setOperaciones2(DualListModel<Usuario> operaciones2) {
		this.operaciones2 = operaciones2;
	}

	public boolean isUsadoParaCrearFlujo() {
		return usadoParaCrearFlujo;
	}

	public void setUsadoParaCrearFlujo(boolean usadoParaCrearFlujo) {
		this.usadoParaCrearFlujo = usadoParaCrearFlujo;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
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

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

}
