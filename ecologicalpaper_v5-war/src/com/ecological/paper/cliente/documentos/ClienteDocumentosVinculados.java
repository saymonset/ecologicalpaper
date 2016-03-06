/*
 * ClienteDocumentosVinculados.java
 *
 * Created on August 27, 2007, 3:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.documentos;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole_OperacionesPopup;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_relacionados;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

/**
 * 
 * @author simon
 */
public class ClienteDocumentosVinculados extends ContextSessionRequest {

	private List<Doc_relacionados> docs_relacionados;
	private Doc_relacionados docs_relacionado;
	private Doc_maestro doc_maestro;
	private Doc_detalle doc_detalle;
	private DatosCombo datosCombo = new DatosCombo();
	private Role role;
	private List operaciones;
	private String strBuscar;
	private HttpSession session = super.getSession();
	private ClienteOperaciones clienteOperaciones = new ClienteOperaciones();
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private ServicioDelegado delegado = new ServicioDelegado();
	private String usuarioId;
	private Usuario usuario;
	private String doc_estadoId;
	private String doc_tipoId;
	private Doc_tipo doc_tipo;
	private Usuario user_logueado;
	private DualListModel<Doc_maestro> operacionesDoc_maestro;
	private List<Doc_maestro> invisibleItemsDoc_maestro = new ArrayList<Doc_maestro>();
	private List<Doc_maestro> visibleItemsDoc_maestro = new ArrayList<Doc_maestro>();
	private String consecutivo;
	// _____________________________________
	// mostrar todos los roles estilo popup
	private List _roles_popup = new ArrayList();
	// _____________________________________
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private boolean swSuperUsuario;

	/** Creates a new instance of ClienteDocumentosVinculados */
	public ClienteDocumentosVinculados() {
		session = super.getSession();
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// en caso que valla a editar o abrir documento, este es el mismo
		// backing para crear,editar
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();
		doc_maestro = session.getAttribute("doc_maestro") != null ? (Doc_maestro) session
				.getAttribute("doc_maestro") : new Doc_maestro();

		// variable que viene del menu
		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			role = new Role();
		} else {
			IniOperaciones();
		}
	}

	public String getDoc_tipoId() {
		doc_tipoId = session.getAttribute("doc_tipoId") != null ? (String) session
				.getAttribute("doc_tipoId") : "";
		return doc_tipoId;
	}

	public void setDoc_tipoId(String doc_tipoId) {
		session.setAttribute("doc_tipoId", doc_tipoId);
		this.doc_tipoId = doc_tipoId;
	}

	public String getDoc_estadoId() {
		doc_estadoId = session.getAttribute("doc_estadoId") != null ? (String) session
				.getAttribute("doc_estadoId") : "";
		return doc_estadoId;
	}

	public void setDoc_estadoId(String doc_estadoId) {
		session.setAttribute("doc_estadoId", doc_estadoId);
		this.doc_estadoId = doc_estadoId;
	}

	public String getUsuarioId() {
		usuarioId = session.getAttribute("usuarioId") != null ? (String) session
				.getAttribute("usuarioId") : "";
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		session.setAttribute("usuarioId", usuarioId);
		this.usuarioId = usuarioId;
	}

	/** Creates a new instance of ClienteRole */
	/*************** SELECT MULTIPLES *****************************************/
	/********************************************************/
	/********************************************************/
	private CustomizePageBean customizePageBean;
	// List of SelectItem instances
	private List invisibleItems;
	// List of SelectItem instances
	private List visibleItems;
	// array of values of selected items in "Invisible" list
	private Object[] selectedInvisibleItems;
	// array of values of selected items in the "Visible" list
	private Object[] selectedVisibleItems;

	public void moveSelectedToVisible(ActionEvent actionEvent) {
		customizePageBean.moveSelectedToVisible(actionEvent);
	}

	public void moveAllToVisible(ActionEvent actionEvent) {
		customizePageBean.moveAllToVisible(actionEvent);
	}

	public void moveSelectedToInvisible(ActionEvent actionEvent) {
		customizePageBean.moveSelectedToInvisible(actionEvent);
	}

	public void moveAllToInvisible(ActionEvent actionEvent) {
		customizePageBean.moveAllToInvisible(actionEvent);
	}

	public CustomizePageBean getCustomizePageBean() {
		return customizePageBean;
	}

	public void setCustomizePageBean(CustomizePageBean customizePageBean) {
		this.customizePageBean = customizePageBean;
	}

	public List getInvisibleItems() {
		invisibleItems = filtrarSelectItems(customizePageBean
				.getInvisibleItems());
		// ;
		return invisibleItems;
	}

	public void setInvisibleItems(List invisibleItems) {
		customizePageBean.setInvisibleItems(invisibleItems);
		this.invisibleItems = invisibleItems;
	}

	public List getVisibleItems() {

		visibleItems = filtrarSelectItems(customizePageBean.getVisibleItems());
		// visibleItems = customizePageBean.getVisibleItems();
		return visibleItems;
	}

	public void setVisibleItems(List visibleItems) {
		customizePageBean.setVisibleItems(visibleItems);
		this.visibleItems = visibleItems;
	}

	public Object[] getSelectedInvisibleItems() {
		selectedInvisibleItems = customizePageBean.getSelectedInvisibleItems();
		return selectedInvisibleItems;
	}

	public void setSelectedInvisibleItems(Object[] selectedInvisibleItems) {
		customizePageBean.setSelectedInvisibleItems(selectedInvisibleItems);
		this.selectedInvisibleItems = selectedInvisibleItems;
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public Object[] getSelectedVisibleItems() {
		selectedVisibleItems = customizePageBean.getSelectedVisibleItems();
		return selectedVisibleItems;
	}

	public void setSelectedVisibleItems(Object[] selectedVisibleItems) {
		customizePageBean.setSelectedVisibleItems(selectedVisibleItems);
		this.selectedVisibleItems = selectedVisibleItems;
	}

	/********************************************************/
	/********************************************************/
	/********************************************************/
	public String cancelarEditar() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos

		return "listar";
	}

	public String goDocument() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			
		 

			Tree treeNodoActual = delegado.obtenerNodo(doc_maestro.getTree()
					.getNodo());

			session.setAttribute("treeNodoActual", treeNodoActual);
			//ESTO NO TOC TREENODOACTUAL
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentosVinculados", "goDocument", e);
		}

		return "goDocument";
	}

	public void versionId(ActionEvent event) {
		// inicializamos todas las variables

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_maestro == null) {
				doc_maestro = new Doc_maestro();
			}
			// buscamos el role para editar
			if (id >= 0) {
				doc_maestro.setCodigo(new Long(id));
				doc_maestro = delegado.findMaestro(doc_maestro);
			}

			Tree treeNodoActual = delegado.obtenerNodo(doc_maestro.getTree()
					.getNodo());

			session.setAttribute("treeNodoActual", treeNodoActual);
		}
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public boolean getDocForVincular() {

		invisibleItemsDoc_maestro = new ArrayList<Doc_maestro>();
		visibleItemsDoc_maestro = new ArrayList<Doc_maestro>();

		doc_maestro = (Doc_maestro) session.getAttribute("doc_maestro");

		Doc_detalle doc_detalle = new Doc_detalle();
		doc_detalle.setDoc_maestro(doc_maestro);

		boolean sw = false;
		
		if (!isEmptyOrNull(getConsecutivo())) {
		 

			doc_detalle.getDoc_maestro().setConsecutivo(consecutivo);
			sw = true;

		}
	 
		if (getDoc_tipo() != null && !isEmptyOrNull(getDoc_tipo().getNombre())) {
			doc_detalle.getDoc_maestro().setDoc_tipo(getDoc_tipo());

//			sw = true;
		} else {
			try {
				doc_detalle.getDoc_maestro().setDoc_tipo(null);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

		}

		if (doc_maestro != null && sw) {
			// datosCombo.llenarDocRelacionados(visibleItems,invisibleItems,doc_maestro);
			datosCombo.llenarDocRelacionadosAjaxRichFaces(
					visibleItemsDoc_maestro, invisibleItemsDoc_maestro,
					doc_maestro, doc_detalle);
			operacionesDoc_maestro = new DualListModel<Doc_maestro>(
					invisibleItemsDoc_maestro, visibleItemsDoc_maestro);
			session.setAttribute("operacionesDoc_maestro",
					operacionesDoc_maestro);

		}
		// System.out.println("------------------------------------------");
		return sw;

	}

	public String editVinculado() {
		String pagIr = "";
		if (getDocForVincular()) {
			pagIr = "editVinculo";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("filtra_datos")));
		}
		return pagIr;
	}

	public String cancelarListRol() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentosVinculados", "cancelarListRol", e);
		}

		return "menu";
	}

	public String inic_crear() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public void IniOperaciones() {
		customizePageBean = new CustomizePageBean();
		invisibleItems = session.getAttribute("invisibleItems") != null ? (List) session
				.getAttribute("invisibleItems") : null;
		visibleItems = session.getAttribute("visibleItems") != null ? (List) session
				.getAttribute("visibleItems") : null;

		// session.setAttribute("role",role);
		setInvisibleItems(invisibleItems);
		setVisibleItems(visibleItems);
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */
	public String create() {
		String pagIr = "";
		if (("".equals(role.getNombre().toString().trim()))
				|| ("".equals(role.getDescripcion().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}
		try {

			delegado.create(role);

			session.setAttribute("pagIr", Utilidades.getListarRoles());
			pagIr = Utilidades.getFinexitoso();
		} catch (RoleNoEncontrado ex) {
			ex.printStackTrace();
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
		} catch (RoleMultiples ex) {
			ex.printStackTrace();
		}

		return pagIr;
	}

	public String saveRelacionados() {
		String pagIr = "";
		try {

			List<Doc_relacionados> doc_relOldS = delegado
					.findAllDoc_relacionados();
			// findAll_Doc_Relacionados(doc_maestro);
			for (Doc_relacionados doc_relOld : doc_relOldS) {
				if (doc_relOld.getDoc_rel1().equals(doc_maestro)) {
					delegado.destroy(doc_relOld);
				} else if (doc_relOld.getDoc_rel2().equals(doc_maestro)) {
					delegado.destroy(doc_relOld);
				}

			}

			Doc_relacionados doc_relacionados;

			if (operacionesDoc_maestro != null) {
				List<Doc_maestro> listaDoc_maestro = operacionesDoc_maestro
						.getTarget();
				for (Doc_maestro doc_m : listaDoc_maestro) {
					if (doc_m != null && doc_m instanceof Doc_maestro) {
						doc_relacionados = new Doc_relacionados();
						doc_relacionados.setDoc_rel1(doc_maestro);
						doc_relacionados.setDoc_rel2(doc_m);
						delegado.createDocRel(doc_relacionados);

						doc_relacionados = new Doc_relacionados();
						doc_relacionados.setDoc_rel2(doc_maestro);
						doc_relacionados.setDoc_rel1(doc_m);

						delegado.createDocRel(doc_relacionados);

						// ____________________________________
						// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
						user_logueado = (Usuario) session
								.getAttribute("user_logueado");
						boolean doVinculados = true;
						String comentario = doc_maestro.getNombre() + "<->"
								+ doc_m.getNombre();
						super.guardamosHistoricoActivoDelDocumento(
								user_logueado, doc_maestro, false, false,
								false, doVinculados, false, false, false,
								false, comentario);
						// ____________________________________

					}
				}
			}

			pagIr = Utilidades.getFinexitoso();
			session.setAttribute(Utilidades.getNoclearsession(), "");
			session.setAttribute("pagIr", Utilidades.getDocVinculados());

		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public void destroy() throws EcologicaExcepciones {
		try {
			delegado.destroy(role);
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			e.printStackTrace();
		}
	}

	public void findRole() throws RoleMultiples, RoleNoEncontrado {
		try {
			role = delegado.findRole(role);
		} catch (RoleMultiples e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			e.printStackTrace();
		} catch (RoleNoEncontrado e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			e.printStackTrace();
		}
	}

	public Role getFindRole() throws RoleMultiples, RoleNoEncontrado {
		try {
			role = delegado.getRole(role);
		} catch (EcologicaExcepciones ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			ex.printStackTrace();
		} catch (RoleMultiples ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			ex.printStackTrace();
		} catch (RoleNoEncontrado ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			ex.printStackTrace();
		}
		return role;

	}

	public List<Role> findAll() {
		// return servicioUsuario.findAll();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			return delegado.findAll_Roles(user_logueado);
		} else {
			return null;
		}
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void findRoles_PorUsuario(List seleccionados, List noSeleccionados,
			Usuario usuario) {
		List enBd = new ArrayList();
		int size = 0;
		SelectItem item = null;
		if (usuario != null) {
			enBd = delegado.findRoles_Usuario(usuario);
			size = enBd.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Role role = (Role) enBd.get(i);
				item = new SelectItem(role, role.getNombre());
				seleccionados.add(item);
			}
		}
		List items2 = findAll();
		size = items2.size();
		item = null;
		for (int i = 0; i < size; i++) {
			Role role = (Role) items2.get(i);
			if (!enBd.contains(role)) {
				item = new SelectItem(role, role.getNombre());
				noSeleccionados.add(item);
			}
		}

	}

	public List getRoles_popup() {
		_roles_popup = new ArrayList();
		_roles_popup.clear();
		boolean usadoParaCrearFlujo = false;
		clienteSeguridad.buscarTodosLosRoles(_roles_popup, usadoParaCrearFlujo);
		// buscarTodosLosRoles();
		return _roles_popup;
	}

	public void setRoles_popup(List roles_popup) {
		this._roles_popup = roles_popup;
	}

	public List<Doc_relacionados> getDocs_relacionados() {
		List<Doc_relacionados> doc_rels = new ArrayList<Doc_relacionados>();
		if (!super.isEmptyOrNull(getStrBuscar())) {
			docs_relacionados = delegado.findAll_Doc_Relacionados(doc_maestro,
					getStrBuscar());
		} else {
			docs_relacionados = delegado.findAll_Doc_Relacionados(doc_maestro);
		}
		doc_rels.clear();
		Seguridad seguridadTree;
		for (Doc_relacionados doc_r : docs_relacionados) {
			seguridadTree = new Seguridad();
			// verificamos si puede o tiene permiso de ver el seg docum ento..
			seguridadTree = super.getSeguridadTree(super.getUser_logueado(),
					doc_r.getDoc_rel2().getTree());
			if (seguridadTree.isToView() || swSuperUsuario) {
				doc_rels.add(doc_r);
			}
		}

		// return docs_relacionados;
		return doc_rels;
	}

	public void setDocs_relacionados(List<Doc_relacionados> docs_relacionados) {
		this.docs_relacionados = docs_relacionados;
	}

	public Doc_maestro getDoc_maestro() {
		return doc_maestro;
	}

	public void setDoc_maestro(Doc_maestro doc_maestro) {
		this.doc_maestro = doc_maestro;
	}

	public Doc_detalle getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

	public Doc_relacionados getDocs_relacionado() {
		return docs_relacionado;
	}

	public void setDocs_relacionado(Doc_relacionados docs_relacionado) {
		this.docs_relacionado = docs_relacionado;
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

	public DualListModel<Doc_maestro> getOperacionesDoc_maestro() {
		operacionesDoc_maestro = session.getAttribute("operacionesDoc_maestro") != null ? (DualListModel<Doc_maestro>) session
				.getAttribute("operacionesDoc_maestro") : null;
		if (operacionesDoc_maestro == null) {
			invisibleItemsDoc_maestro = new ArrayList<Doc_maestro>();
			visibleItemsDoc_maestro = new ArrayList<Doc_maestro>();

			operacionesDoc_maestro = new DualListModel<Doc_maestro>(
					invisibleItemsDoc_maestro, visibleItemsDoc_maestro);
		}
		return operacionesDoc_maestro;
	}

	public void setOperacionesDoc_maestro(
			DualListModel<Doc_maestro> operacionesDoc_maestro) {

		this.operacionesDoc_maestro = operacionesDoc_maestro;
	}

	public List<Doc_maestro> getInvisibleItemsDoc_maestro() {
		return invisibleItemsDoc_maestro;
	}

	public void setInvisibleItemsDoc_maestro(
			List<Doc_maestro> invisibleItemsDoc_maestro) {
		this.invisibleItemsDoc_maestro = invisibleItemsDoc_maestro;
	}

	public List<Doc_maestro> getVisibleItemsDoc_maestro() {
		return visibleItemsDoc_maestro;
	}

	public void setVisibleItemsDoc_maestro(
			List<Doc_maestro> visibleItemsDoc_maestro) {
		this.visibleItemsDoc_maestro = visibleItemsDoc_maestro;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

}
