/*
 * ClienteFlowsUsers.java
 *
 * Created on August 2, 2007, 8:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class ClienteFlowsUsers extends ContextSessionRequest {
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();

	private DatosCombo datosCombo = new DatosCombo();
	private HttpSession session = super.getSession();
	private Tree tree;
	private Usuario usuario;
	private boolean swIniciar;
	private List usuariosCombo;
	private boolean swSolicitudimpresion;
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");

	/** Creates a new instance of ClienteFlowsUsers */
	public ClienteFlowsUsers() {
		session = super.getSession();

		// REVISAMOS SI ES UNA SOLICITUD DE IMPRESION
		swSolicitudimpresion = session.getAttribute("solicitudimpresion") != null ? true
				: false;

		// FIN REVISAMOS SI ES UNA SOLICITUD DE IMPRESION

		// catalogo pincipal
		session.removeAttribute("mostrarCatalogo");
		swIniciar = session.getAttribute("swIniciar") != null ? (Boolean) session
				.getAttribute("swIniciar") : false;
		usuario = session.getAttribute("usuario") != null ? (Usuario) session
				.getAttribute("usuario") : null;
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// por si las variables viene de la pestaña de al lado, y los
		// convertidores piuedenb dar error, revisamos e inicializamos
		// inicializaSiStanEmbasuradas();
		if (swIniciar) {
			session.setAttribute("tabBandera", Utilidades.getTab1());
			operaciones();
		}
	}

	// cuando se pulsa el boton seleccionar usuario
	public String editFlows_User() {
		// si viene de la lista del combo, pasa por aca
		// la primera vez se ejecuta
		if (!swIniciar) {
			// tabulador principal
			session.setAttribute("tabBandera", Utilidades.getTab1());

			swIniciar = true;
			session.setAttribute("swIniciar", swIniciar);
			try {

				inicializar(swIniciar);
			} catch (RoleMultiples ex) {
				ex.printStackTrace();
			}
		} else {
			// si viene de la lista multiple de los permisos del usario, pasa
			// por aca
			// blanquea las sessiones y regresa para escijer un nuevo usario
			// tabulador principal
			session.setAttribute("tabBandera", Utilidades.getTab0());
			swIniciar = false;
			session.setAttribute("swIniciar", swIniciar);
			// inicializamos todas las sessiones,dejamos solo las basicas
			super.clearSession(session, confmessages.getString("flowSession"));
		}
		return null;
	}

	public String cancelar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteFlowsUsers", "cancelar", e);
		}

		return "menu";
	}

	public void inicializar(boolean sw) throws RoleMultiples {

		if (sw) {
			tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			visibleItems = new ArrayList();
			invisibleItems = new ArrayList();
			customizePageBean = new CustomizePageBean();
			invisibleItems.clear();
			visibleItems.clear();

			Doc_detalle doc_detalle = null;
			doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
			if (doc_detalle == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_doc_detalleflow")));
			}
			// recuperarDoc_Detalle_EDo(doc_estado,doc_detalle,Utilidades.getBorrador());
			datosCombo.llenarUsuariosFlowVisibles(visibleItems,
					invisibleItems, doc_detalle);
		 
			
			session.setAttribute("visibleItems", visibleItems);
			session.setAttribute("invisibleItems", invisibleItems);
			session.setAttribute("usuario", usuario);
			session.setAttribute("swIniciar", true);
			session.setAttribute("tabBandera", Utilidades.getTab1());
			setInvisibleItems(invisibleItems);
			setVisibleItems(visibleItems);
		}

	}

	public void recuperarDoc_Detalle_EDo(Doc_estado edo,
			Doc_detalle doc_detalle, Long tipoEstado) {
		edo = new Doc_estado();
		edo.setCodigo(tipoEstado);
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_edobdAprobacion")));
		}
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (doc_detalle == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		}
	}

	public void operaciones() {
		customizePageBean = new CustomizePageBean();
		invisibleItems = session.getAttribute("invisibleItems") != null ? (List) session
				.getAttribute("invisibleItems") : null;
		visibleItems = session.getAttribute("visibleItems") != null ? (List) session
				.getAttribute("visibleItems") : null;
		setInvisibleItems(invisibleItems);
		setVisibleItems(visibleItems);
	}

	public String saveUsuario_Operaciones() throws RoleMultiples {
		// try{
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		Doc_detalle doc_detalle = null;
		// apenas estamos grabando participantes, sin enviar el flujo, pore lo
		// tanto, temporalmente es borrador
		Doc_estado doc_estado = null;
		doc_estado = new Doc_estado();
		doc_estado.setCodigo(Utilidades.getBorrador());
		doc_estado = delegado.findDocEstado(doc_estado);

		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (doc_detalle == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		} else {
			List<Usuario> usuarioSeleccionados = new ArrayList<Usuario>();
			for (SelectItem value : visibleItems) {
				Usuario usuario2 = (Usuario) value.getValue();
				usuarioSeleccionados.add(usuario2);
			}
			try {
				delegadoEcological.saveUsuarioOperacionesFlows(tree,
						user_logueado, doc_detalle, usuarioSeleccionados);
				// si se creo el flow, doc_detalle tiene un parametro llamadpo
				// flow_reditar que se le fue
				// asignao el flow que se creo

				session.setAttribute("doc_detalle", doc_detalle);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			editFlows_User();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));
		}

		return "";
	}

	/*************** SELECT MULTIPLES *****************************************/
	/********************************************************/
	/********************************************************/
	private CustomizePageBean customizePageBean;
	// List of SelectItem instances
	private List invisibleItems;
	// List of SelectItem instances
	private List<SelectItem> visibleItems;
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

		invisibleItems = customizePageBean.getInvisibleItems();

		return invisibleItems;
	}

	public void setInvisibleItems(List invisibleItems) {
		customizePageBean.setInvisibleItems(invisibleItems);
		this.invisibleItems = invisibleItems;
	}

	public List getVisibleItems() {

		visibleItems = customizePageBean.getVisibleItems();

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

	public boolean isSwIniciar() {
		if (swIniciar) {
			session.setAttribute("tabBandera", Utilidades.getTab1());
		}
		return swIniciar;
	}

	public void setSwIniciar(boolean swIniciar) {
		this.swIniciar = swIniciar;
	}

	public List getUsuariosCombo() {
		return usuariosCombo;
	}

	public void setUsuariosCombo(List usuariosCombo) {
		this.usuariosCombo = usuariosCombo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSwSolicitudimpresion() {
		return swSolicitudimpresion;
	}

	public void setSwSolicitudimpresion(boolean swSolicitudimpresion) {
		this.swSolicitudimpresion = swSolicitudimpresion;
	}

}
