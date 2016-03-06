/*
 * Historico.java
 *
 * Created on September 26, 2007, 8:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.historico;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.paper.cliente.flows.ClienteFlowsHistorico;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.cliente.seguridad.ClienteSeguridadRole;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.FlowsHistorico;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;

import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;
import javax.faces.event.AbortProcessingException;
import javax.servlet.http.HttpSession;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.validator.ValidatorException;
import javax.faces.event.ActionEvent;

/**
 * 
 * @author simon
 */
public class Historico extends ContextSessionRequest {

	private HttpSession session = super.getSession();
	private ClienteRole clienteRole = new ClienteRole();
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private Usuario user_logueado;
	private Usuario usuario;
	private String strError;
	// SEGURIDAD
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario usuarioRemplazo;
	private Usuario usuarioEliminar;
	private String cualquierComentario = "";
	private ServicioDelegado delegado = new ServicioDelegado();
	private boolean swSoloVerHistorico;
	private List<com.ecological.paper.historico.Historico> historicos;
	private List<com.ecological.paper.historico.Historico> historicosTreeEliminado;
	private List<com.ecological.paper.historico.Historico> historicosTreeCambio;
	// se hace asi por error
	private List<com.ecological.paper.historico.Historico> listaComentariosTree;
	private Tree toDelTree;
	// TREE
	private static final long serialVersionUID = 1L;
	private Object _treeModel;
	private Object _tree;
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private Tree doc_hist;
	private Tree propiedades;
	private String authorBio;
	private Tree treeBuscarInf;
	private boolean swhayData;
	private Tree treeNodoActual;
	private boolean swSaveDatosBasicos;
	private boolean swSuperUsuario;
	private Tree tree;
	private Seguridad seguridadTree = new Seguridad();
	private Tree moverNodo;
	private List unico;
	private Object treeData = null;
	private List<FlowsHistorico> flowsHistoricoUser;
	private List<FlowsHistorico> flowsHistorico;
	private List<FlowParalelo> flowParaleloUser;
	private List<FlowParalelo> flowParalelo;
	private Date fecha_creadoFin;
	private Date fecha_creado;
	private Date fechaDesde;
	private Date fechaHasta;

	/** Creates a new instance of Historico */
	public Historico() {
		session = super.getSession();
		// inicializamos el tre de tomahawak para que no d error
		treeData = null;

		if (session.getAttribute("usuarioEliminar") != null) {
			usuarioEliminar = session.getAttribute("usuarioEliminar") != null ? (Usuario) session
					.getAttribute("usuarioEliminar") : null;
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
		} else if (session.getAttribute("verHistoricoUsusario") != null) {
			swSoloVerHistorico = true;
		}
		// verificamos si va a eliminar un tree
		if (session.getAttribute("toDelTree") != null) {
			toDelTree = (Tree) session.getAttribute("toDelTree");
		}
		// _______________________________________
		// modifica las propiedades del documento
		if (session.getAttribute("propiedades") != null) {
			propiedades = (Tree) session.getAttribute("propiedades");
		}
		// _______________________________________

		// obtenemos la seguridad del nodo
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// OBTENEMOS SEGURIDAD;
		seguridadTree = super.getSeguridadTree(tree);

		// VENIMOS POARA REALIZAR EL MOVIMIENTO DE NODO, PERO REGISTRAMOS LA
		// RAZON DEL CAMBIO
		if (session.getAttribute("moverNodo") != null) {
			moverNodo = (Tree) session.getAttribute("moverNodo");
		}

	}

	public String cambioNodo() {
		// colocamos la nueva seguridad en session
		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos
		session.setAttribute("swbusqueda", true);
		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos

		session.setAttribute(Utilidades.getTreeprocesando(), Utilidades.getTreeprocesando());
		String pagIr = "";
		if (moverNodo == null) {
			if (session.getAttribute("moverNodo") != null) {
				moverNodo = (Tree) session.getAttribute("moverNodo");
			}
		}
		if (moverNodo != null) {
			// GUARDAMOS SU HISTORICO GENERAL
			com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
			hist.setStatus(true);
			hist.setFecha_accion(super.fechaActual());
			hist.setComentarios(cualquierComentario);
			hist.setMaquina(super.getMaquinaConectada());
			hist.setTipo_accion(Utilidades.getTreeMoverNodo());
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			hist.setUsuario_accion(user_logueado);
			hist.setTree_origen(moverNodo);
			Tree anterior = delegado.obtenerNodo(moverNodo.getNodopadre());
			hist.setTree_anterior(anterior);
			Tree treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			hist.setTree_new(treeNodoActual);

			// AHORA SI MOVEMOS EL NODO
			delegado.create(hist);
			moverNodo.setNodopadre(treeNodoActual.getNodo());
			delegado.edit(moverNodo);

			// ----------------role
			// seguridad---------------------------------------------
			ClienteSeguridadRole clienteSeguridadRole = new ClienteSeguridadRole(
					"vacio constructor");
			boolean swEliminar = true;
			Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
			seguridad_Role_Lineal.setTree(moverNodo);
			seguridad_Role_Lineal.setRole(null);
			List<Seguridad_Role_Lineal> borrarLista = delegado
					.findAllSeguridad_Role_Lineal(seguridad_Role_Lineal
							.getTree());
			if (borrarLista != null && !borrarLista.isEmpty()) {
				try {
					delegado.heredarRolePermiso(
							seguridad_Role_Lineal, swEliminar,
							moverNodo.getTiponodo());
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE

			List<Seguridad_Role_Lineal> lista = delegado
					.findAllSeguridad_Role_Lineal(treeNodoActual);

			for (Seguridad_Role_Lineal s_R_Lineal : lista) {
				Role role = s_R_Lineal.getRole();

				swEliminar = false;
				seguridad_Role_Lineal = new Seguridad_Role_Lineal();
				seguridad_Role_Lineal.setRole(role);
				seguridad_Role_Lineal.setTree(moverNodo);
				// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
				// BUSCAMOS SU SEGURIDA
				List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
						.findAllSeguridad_Role_Identificador(role);
				if (seguridad_Role_LinealList != null
						&& !seguridad_Role_LinealList.isEmpty()) {
					seguridad_Role_Lineal = seguridad_Role_LinealList.get(0);
					seguridad_Role_Lineal.setRole(role);
					seguridad_Role_Lineal.setTree(moverNodo);
				}
				// para que sea totalmente nuevo y no traiga el primary
				// key viejo
				Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
						seguridad_Role_Lineal);
				delegado.create(seguridad_Role_Lineal2);
				try {
					delegado.heredarRolePermiso(
							seguridad_Role_Lineal, swEliminar,
							moverNodo.getTiponodo());
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//super.givePermparaverToGroup(seguridad_Role_Lineal2);
				// FIN AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE
			}

			// -------------fin---role
			// seguridad---------------------------------------------

			// __________________________________________________________________________
			// aqui damos permiso hasta la raiz del usuario logueado
			Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
			seguridad_User_Lineal.setTree(moverNodo);
			seguridad_User_Lineal.setUsuario(user_logueado);
			seguridad_User_Lineal.setStatus(true);
			seguridad_User_Lineal.setToView(true);
			ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
			clienteSeguridad.darViewNodoHastaRaiz(seguridad_User_Lineal);
			seguridad_User_Lineal.setSwHereda(true);
			clienteSeguridad.heredaSeguridadNodo(seguridad_User_Lineal);

			// buscamos el usuario si el nodo es un cargo y le damos permiso de
			// ver hasta la raiz
			if ((Utilidades.getNodoCargo() - moverNodo.getTiponodo()) == 0) {
				Usuario usuario = delegado.findUsuarioByCargo(moverNodo);
				if (usuario != null) {
					seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setTree(moverNodo);
					seguridad_User_Lineal.setUsuario(usuario);
					seguridad_User_Lineal.setStatus(true);
					seguridad_User_Lineal.setToView(true);
					clienteSeguridad = new ClienteSeguridad();
					clienteSeguridad
							.darViewNodoHastaRaiz(seguridad_User_Lineal);
				}
			}
			// __________________________________________________________________________

			// si es doc ., lo borramos de la cache
			// if (moverNodo.getTiponodo()-Utilidades.getNodoDocumento()==0){
			// super.deleteSeguridadTree(toDelTree);
			// }else{
			// Aqui si que hay que refrescar la estructura completa
			Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			clienteSeguridad = new ClienteSeguridad();
			if (user_logueado != null) {
				//HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(
					//	user_logueado);
				//hiloClienteSeguridad.start();
				  clienteSeguridad.ponerSeguridadInSession(user_logueado);
			}

			// }
			// para borrar variable de mover nodo 2008/06/04
			// session.removeAttribute("moverNodo");
			super.clearSession(session,
					confmessages.getString("usuarioSession"));

			session.setAttribute("pagIr", Utilidades.getListarAplicacion());
			pagIr = Utilidades.getFinexitoso();
		}
		return pagIr;
	}

	public String delete2() {
		String pagIr = "";
		usuarioEliminar = session.getAttribute("usuarioEliminar") != null ? (Usuario) session
				.getAttribute("usuarioEliminar") : null;
		if (usuarioEliminar != null) {
			if (usuarioEliminar.equals(usuarioRemplazo)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("escoja_otrousuario")));
				return "";
			}
			if (super.isEmptyOrNull(cualquierComentario)
					|| "#000000".equalsIgnoreCase(cualquierComentario)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				cualquierComentario = "";
				return "";
			}

			try {
				usuarioEliminar = delegado.getUsuario(usuarioEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

			// borramois la seguridad_user del usuario tree operaciones user
			// fisicamente
			delegado.deleteSeguridadIndividual2(usuarioEliminar,
					usuarioRemplazo);
			// borramos Roles_Usuarios y Menus_Usuarios fiicamente
			delegado.destroy_Menu(usuarioEliminar);
			// en flujo se deja porque el fuie el autor
			// falta detalle, maestro
			// cambiamos el flow_participantes y guardamos el historico del
			// flujo si tiene
			delegado.sustituirFlowParticipantesOldByOtro(usuarioEliminar,
					usuarioRemplazo);
			// cambiamos el duenio y guardamos su historico en el flujo
			delegado.deleteTreenewDuenio(usuarioEliminar, usuarioRemplazo);
			// GUARDAMOS SU HISTORICO GENERAL
			com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
			hist.setStatus(true);
			hist.setFecha_accion(super.fechaActual());
			hist.setComentarios(cualquierComentario);
			hist.setMaquina(super.getMaquinaConectada());
			hist.setTipo_accion(Utilidades.getUsuarioEliminado());
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			hist.setUsuario_accion(user_logueado);
			hist.setUsuario_anterior(usuarioEliminar);
			hist.setUsuario_new(usuarioRemplazo);
			delegado.create(hist);
			// __________________________________________________________________
			// damos seguridad hasta abajo del nuevo nodo que se crean
			boolean swEliminar = false;
			// cargamos la operacion de la base de datos
			// Seguridad_Role_Lineal
			Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
			List<Operaciones> operacionViewInBD = delegado
					.findAll_operacionesArbol(usuarioRemplazo.getCargo());
			for (Operaciones op : operacionViewInBD) {
				delegado.llenarSeguridadLineal(op, seguridad_AuxUser_Lineal);
			}
			// truco de convertirlo
			Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal(
					seguridad_AuxUser_Lineal);
			seguridad_User_Lineal.setUsuario(usuarioRemplazo);
			seguridad_User_Lineal.setTree(usuarioRemplazo.getCargo());
			try {
				clienteSeguridad
						.heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
								seguridad_User_Lineal, swEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

			try {
				usuarioEliminar.setStatus(false);
				delegado.edit(usuarioEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
			// __________________________________________________________________
			session.setAttribute("pagIr", Utilidades.getListarUsuarios());
			pagIr = Utilidades.getFinexitoso();
			// getUsuarios();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));

		}
		return pagIr;
	}

	public String cancelar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("Historico", "cancelar", e);
		}

		return Utilidades.getListarAplicacion();
	}

	public String cancelarIrUsuarios() {
		return Utilidades.getListarUsuarios();
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ClienteRole getClienteRole() {
		return clienteRole;
	}

	public void setClienteRole(ClienteRole clienteRole) {
		this.clienteRole = clienteRole;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getConfmessages() {
		return confmessages;
	}

	public void setConfmessages(ResourceBundle confmessages) {
		this.confmessages = confmessages;
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

	public String getStrError() {
		return strError;
	}

	public void setStrError(String strError) {
		this.strError = strError;
	}

	public ClienteSeguridad getClienteSeguridad() {
		return clienteSeguridad;
	}

	public void setClienteSeguridad(ClienteSeguridad clienteSeguridad) {
		this.clienteSeguridad = clienteSeguridad;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public Usuario getUsuarioRemplazo() {
		return usuarioRemplazo;
	}

	public void setUsuarioRemplazo(Usuario usuarioRemplazo) {
		this.usuarioRemplazo = usuarioRemplazo;
	}

	public Usuario getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(Usuario usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
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

	public boolean isSwSoloVerHistorico() {
		return swSoloVerHistorico;
	}

	public void setSwSoloVerHistorico(boolean swSoloVerHistorico) {
		this.swSoloVerHistorico = swSoloVerHistorico;
	}

	public List<com.ecological.paper.historico.Historico> getHistoricos() {
		com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
		hist.setTipo_accion(Utilidades.getUsuarioEliminado());
		Usuario usu = session.getAttribute("verHistoricoUsusario") != null ? (Usuario) session
				.getAttribute("verHistoricoUsusario") : null;
		historicos = delegado.findAllHistorico(hist, usu);

		com.ecological.paper.historico.Historico principal = new com.ecological.paper.historico.Historico();
		boolean swPrincipal = true;
		for (com.ecological.paper.historico.Historico hist2 : historicos) {
			hist2.setFechaAux(hist2.getFecha_accion().toString());
			if (super.isEmptyOrNull(cualquierComentario)) {

				cualquierComentario = hist2.getComentarios();
			}
			if (swPrincipal) {
				swPrincipal = false;
				principal = hist2;
			} else {
				if (principal.getUsuario_new() == null) {
					principal.setUsuario_new(hist2.getUsuario_new());
				}
			}

		}
		// solo uno aceptamos para mostrar
		historicos = new ArrayList();
		historicos.add(principal);
		return historicos;
	}

	public void setHistoricos(
			List<com.ecological.paper.historico.Historico> historicos) {
		this.historicos = historicos;
	}

	public Tree getToDelTree() {
		return toDelTree;
	}

	public void setToDelTree(Tree toDelTree) {
		this.toDelTree = toDelTree;
	}

	/********************************************************/
	public String deleteTree() {
		String pagIr = "";
		if (super.isEmptyOrNull(cualquierComentario)
				|| "#000000".equalsIgnoreCase(cualquierComentario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			cualquierComentario = "";
			return "";
		}

		// verificamos si va a eliminar un tree
		if (session.getAttribute("toDelTree") != null) {
			toDelTree = (Tree) session.getAttribute("toDelTree");
			if (toDelTree.getTiponodo() - Utilidades.getNodoCargo() == 0) {
				Usuario usu = delegado.findUsuarioByCargo(toDelTree);
				if (usu != null) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(messages
											.getString("btn_erase")
											+ " "
											+ messages
													.getString("menu_Usuario")
											+ " "
											+ messages
													.getString("usuario_cargo")));
					return "";
				}

			}
			try {
				// eliminamos
				heredarDeleteTree(toDelTree);
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());
				Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				// eliminamos de la cache de session
				// si es doc ., lo borramos de la cache
				if (toDelTree.getTiponodo() - Utilidades.getNodoDocumento() == 0) {
					super.deleteSeguridadTree(toDelTree);
					 ClienteSeguridad clienteSeguridad = new
					 ClienteSeguridad();
					 clienteSeguridad.ponerSeguridadInSession(user_logueado);
					//HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(
						//	user_logueado);
					//hiloClienteSeguridad.start();
				} else {
					 ClienteSeguridad clienteSeguridad = new
					 ClienteSeguridad();
					 clienteSeguridad.ponerSeguridadInSession(user_logueado);
					//HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(
					//		user_logueado);
				//	hiloClienteSeguridad.start();
				}

				pagIr = Utilidades.getFinexitosorefresca();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}
		return pagIr;
	}

	public void heredarDeleteTree(Tree tree) throws EcologicaExcepciones {

		Tree eliminamosTree = delegado.obtenerNodo(tree.getNodo());
		// SI ES DOCUMENTO, DEBEMOS BUSCARLO DE LA TABLA DOC_MAESTRO
		if (eliminamosTree.getTiponodo() - Utilidades.getNodoDocumento() == 0) {
			List<Doc_maestro> docs = delegado
					.findAllDoc_maestros(eliminamosTree);
			// DEBERIA TRAERME UN SOLO DOC
			for (Doc_maestro d : docs) {
				// QUEDA ELIMINADO LOGICAMENTE
				d.setStatus(false);
				delegado.editMaestro(d);
			}
		}
		// AQUI SI ES UN TIPO DIFERENTE ADOC, LO DESVCISUALIZAMOS DEL ARBOL
		eliminamosTree.setStatus(false);
		delegado.edit(eliminamosTree);
		// GRABAMOS EL HISTORICO.....
		// GUARDAMOS SU HISTORICO GENERAL
		com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
		hist.setStatus(true);
		hist.setTree_origen(tree);
		hist.setFecha_accion(super.fechaActual());
		hist.setComentarios(cualquierComentario);
		hist.setMaquina(super.getMaquinaConectada());
		hist.setTipo_accion(Utilidades.getTreeEliminado());
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		hist.setUsuario_accion(user_logueado);
		hist.setUsuario_anterior(null);
		hist.setUsuario_new(null);
		delegado.create(hist);
		List<Tree> hijos = super.llegarHastaHijosTodos(tree);
		for (Tree hijo : hijos) {
			// System.out.println("hijo="+hijo.getNombre());
			heredarDeleteTree(hijo);
		}

	}

	public String saveDatosBasicos_action() {
		String pagIr = "";
		if (propiedades != null) {
			if (("".equals(propiedades.getNombre().toString()))
					|| (super.isEmptyOrNull(propiedades.getDescripcion()
							.toString()))
					|| ("".equals(propiedades.getDescripcion().toString()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			} else {
				// estoy en el mismo nodo
				Tree treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
						.getAttribute("treeNodoActual") : null;
				if (validaHijosMismoNombreError(treeNodoActual, propiedades)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_tree_existe")));

					return "";
				}
				delegado.edit(propiedades);
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());
				pagIr = Utilidades.getFinexitosorefresca();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			}

		}

		return pagIr;
	}

	public List<FlowsHistorico> getFlowsHistoricoUserCreados() {
		Usuario user = (Usuario) session.getAttribute("usuario");

		boolean swPasar = false;
		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");
		StringBuffer sqlFecha = new StringBuffer();
		if (fecha_creado != null && fecha_creadoFin == null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);

			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		}

		List<FlowsHistorico> lista = new ArrayList<FlowsHistorico>();

		if (swPasar) {

			StringBuffer sql = new StringBuffer(
					" select object(h) from FlowsHistorico as h  ");
			sql.append(" , Flow as f,Flow_Participantes as o ");
			sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
			sql.append(" and ( f.duenio.id=").append(user.getId())
					.append("   ").append(" ) ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append(" and ").append(sqlFecha.toString());
			sql.append(" order by  f.fechaCreadoOnly desc ");
			System.out.println("hist user sql=" + sql.toString());

			flowsHistorico = delegado.queryExecute(sql.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
		} else {
			flowsHistorico = delegado.findAll_FlowsHistoricoUserCreados(user);
		}

		if (flowsHistorico == null) {
			flowsHistorico = new ArrayList();
		}

		HashMap global = new HashMap();
		for (FlowsHistorico fh : flowsHistorico) {
			if (!global.containsKey(fh.getCodigo())) {
				global.put(fh.getCodigo(), fh);
				String result;
				if (fh.getFlow().getFecha_creado() != null) {
					fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow()
							.getFecha_creado()));
				}

				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
				doc_edo = delegado.findDocEstado(doc_edo);
				fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
				fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado()
						.getNombre()));
				lista.add(fh);
			}
		}
		return lista;
	}

	public List<FlowsHistorico> getFlowsHistoricoUserII() {
		Usuario user = (Usuario) session.getAttribute("usuario");
		boolean swPasar = false;
		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");

		StringBuffer sqlFecha = new StringBuffer();
		if (fecha_creado != null && fecha_creadoFin == null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);

			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		}

		List<FlowsHistorico> lista = new ArrayList<FlowsHistorico>();

		if (swPasar) {

			StringBuffer sql = new StringBuffer(
					" select object(h) from FlowsHistorico as h  ");
			sql.append(" , Flow as f,Flow_Participantes as o ");
			sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
			sql.append(" and ( o.participante.id=").append(user.getId())
					.append("   ").append(" ) ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append(" and ").append(sqlFecha.toString());
			sql.append(" order by   f.fechaCreadoOnly  desc ");
			System.out.println("hist user sql=" + sql.toString());
			flowsHistorico = delegado.queryExecute(sql.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
		} else {
			flowsHistorico = delegado.findAll_FlowsHistoricoUser(user);
		}

		if (flowsHistorico == null) {
			flowsHistorico = new ArrayList();
		}

		HashMap global = new HashMap();

		for (FlowsHistorico fh : flowsHistorico) {
			String result;
			if (!global.containsKey(fh.getCodigo())) {
				global.put(fh.getCodigo(), fh);
				if (fh.getFlow().getFecha_creado() != null) {
					fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow()
							.getFecha_creado()));
				}

				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
				doc_edo = delegado.findDocEstado(doc_edo);
				fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
				fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado()
						.getNombre()));
				lista.add(fh);
			}
		}
		global = null;
		return lista;
	}

	public List<FlowParalelo> getFlowParaleloUserCreados() {
		Usuario user = (Usuario) session.getAttribute("usuario");

		boolean swPasar = false;
		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");
		StringBuffer sqlFecha = new StringBuffer();
		if (fecha_creado != null && fecha_creadoFin == null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);

			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		}

		List<FlowParalelo> lista = new ArrayList<FlowParalelo>();

		if (swPasar) {

			StringBuffer sql = new StringBuffer(
					" select object(h) from FlowParalelo as h  ");
			sql.append(" , Flow as f,Flow_Participantes as o ");
			sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
			sql.append(" and ( f.duenio.id=").append(user.getId())
					.append("   ").append(" ) ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append("  and f.status=").append(Utilidades.isActivo());
			sql.append("  and h.status=").append(Utilidades.isActivo());
			sql.append(" and ").append(sqlFecha.toString());
			sql.append(" order by  f.fechaCreadoOnly desc ");
			System.out.println("hist user sql=" + sql.toString());

			flowParalelo = delegado.queryExecute(sql.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
		} else {
			flowParalelo = delegado.findAll_FlowParaleloUserCreados(user);
		}

		if (flowParalelo == null) {
			flowParalelo = new ArrayList();
		}

		HashMap global = new HashMap();
		for (FlowParalelo fh : flowParalelo) {
			if (!global.containsKey(fh.getCodigo())) {
				global.put(fh.getCodigo(), fh);
				String result;
				if (fh.getFlow().getFecha_creado() != null) {
					fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow()
							.getFecha_creado()));
				}

				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
				doc_edo = delegado.findDocEstado(doc_edo);
				fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
				fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado()
						.getNombre()));
				lista.add(fh);
			}
		}
		return lista;
	}

	public List<FlowParalelo> getFlowParaleloSoloFechas() {
		Usuario user = (Usuario) session.getAttribute("usuario");
		boolean swPasar = false;
		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");

		StringBuffer sqlFecha = new StringBuffer();
		if (fecha_creado != null && fecha_creadoFin == null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);

			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		}

		List<FlowParalelo> lista = new ArrayList<FlowParalelo>();

		if (swPasar) {
			StringBuffer sql2 = new StringBuffer(
					" select object(h) from FlowParalelo as h  ");
			sql2.append(" , Flow as f,Flow_Participantes as o ");
			sql2.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
			sql2.append("  and o.status=").append(Utilidades.isActivo());
			sql2.append("  and f.status=").append(Utilidades.isActivo());
			sql2.append("  and h.status=").append(Utilidades.isActivo());
			sql2.append(" and ").append(sqlFecha.toString());
			sql2.append(" order by   f.fechaCreadoOnly  desc ");
			flowParalelo = delegado.queryExecute(sql2.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
		}

		if (flowParalelo == null) {
			flowParalelo = new ArrayList();
		}
		HashMap global = new HashMap();

		for (FlowParalelo fh : flowParalelo) {
			String result;
			if (!global.containsKey(fh.getCodigo())) {
				global.put(fh.getCodigo(), fh);
				if (fh.getFlow().getFecha_creado() != null) {
					fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow()
							.getFecha_creado()));
				}

				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
				doc_edo = delegado.findDocEstado(doc_edo);
				fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
				fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado()
						.getNombre()));
				lista.add(fh);
			}
		}
		global = null;
		return lista;
	}

	public List<FlowParalelo> getFlowParaleloUserII() {
		Usuario user = (Usuario) session.getAttribute("usuario");
		boolean swPasar = false;
		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");

		StringBuffer sqlFecha = new StringBuffer();
		if (fecha_creado != null && fecha_creadoFin == null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			swPasar = true;
			setFechaDesde(fecha_creado);
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", getFechaDesde());
			session.setAttribute("fechaparametro2", fechaHasta);

			sqlFecha.append("  f.fechaCreadoOnly >=:fechaDesde");
			sqlFecha.append(" and f.fechaCreadoOnly <=:fechaHasta ");
		}

		List<FlowParalelo> lista = new ArrayList<FlowParalelo>();

		if (swPasar) {
			StringBuffer sql2 = new StringBuffer(
					" select object(h) from FlowParalelo as h  ");
			sql2.append(" , Flow as f,Flow_Participantes as o ");
			sql2.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
			sql2.append(" and ( o.participante.id=").append(user.getId())
					.append("   ").append(" ) ");
			sql2.append("  and o.status=").append(Utilidades.isActivo());
			sql2.append("  and f.status=").append(Utilidades.isActivo());
			sql2.append("  and h.status=").append(Utilidades.isActivo());
			sql2.append(" and ").append(sqlFecha.toString());
			sql2.append(" order by   f.fechaCreadoOnly  desc ");
			flowParalelo = delegado.queryExecute(sql2.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
		} else {
			flowParalelo = delegado.findAll_FlowParaleloUser(user);
		}

		if (flowParalelo == null) {
			flowParalelo = new ArrayList();
		}
		HashMap global = new HashMap();

		for (FlowParalelo fh : flowParalelo) {
			String result;
			if (!global.containsKey(fh.getCodigo())) {
				global.put(fh.getCodigo(), fh);
				if (fh.getFlow().getFecha_creado() != null) {
					fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow()
							.getFecha_creado()));
				}

				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
				doc_edo = delegado.findDocEstado(doc_edo);
				fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
				fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado()
						.getNombre()));
				lista.add(fh);
			}
		}
		global = null;
		return lista;
	}

	private String _nodePath;

	public void setNodePath(String nodePath) {
		_nodePath = nodePath;
	}

	public String getNodePath() {
		return _nodePath;
	}

	public Tree getDoc_hist() {
		return doc_hist;
	}

	public void setDoc_hist(Tree doc_hist) {
		this.doc_hist = doc_hist;
	}

	public String nodeClicked() {
		return "";
	}

	/*
	 * public void expandAll(ActionEvent event) throws AbortProcessingException
	 * { UIComponent component = (UIComponent)event.getSource(); HtmlTree tree =
	 * getTreeModel().getTree(); tree.expandAll();
	 * 
	 * }
	 */
	public String getAuthorBio() {
		return authorBio;
	}

	public void setAuthorBio(String authorBio) {
		this.authorBio = authorBio;
	}

	public com.ecological.paper.historico.Historico colocarVerFalsolosNullos(
			com.ecological.paper.historico.Historico h) {
		if (!super.isEmptyOrNull(h.getComentarios())) {
			h.setComentariosh(true);
		}
		if (h.getFlow() != null) {
			h.setFlowh(true);
		}
		if (h.getTree_anterior() != null) {
			h.setTree_anteriorh(true);
		}
		if (h.getTree_new() != null) {
			h.setTree_newh(true);
		}

		if (h.getTree_origen() != null) {
			h.setTree_origenh(true);
		}

		if (h.getUsuario_anterior() != null) {
			h.setUsuario_anteriorh(true);
		}
		if (h.getUsuario_new() != null) {
			h.setUsuario_newh(true);
		}

		if (h.getUsuario_accion() != null) {
			h.setUsuario_accionh(true);
		}
		return h;

	}

	public String viewDocumentoPDF() {
		try {

			// dispatcher.include(req, res);
			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			// System.out.println("detalle="+detalle.);

			// ____________________________________
			// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			/*
			 * boolean verDetalles = true;
			 * super.guardamosHistoricoActivoDelDocumento(user_logueado, detalle
			 * .getDoc_maestro(), false, false, verDetalles, false, false,
			 * false, false, false, "");
			 */
			// ____________________________________

			session.setAttribute("doc_detalle", detalle);
			session.setAttribute("onlyView", "onlyView");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return "viewPDF";
	}

	public List<com.ecological.paper.historico.Historico> getHistoricosTree() {
		if (treeBuscarInf != null) {
			com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
			hist.setTipo_accion(Utilidades.getTreeEliminado());

			historicos = delegado.findAllHistoricoTree(null, treeBuscarInf);
			// com.ecological.paper.historico.Historico principal= new
			// com.ecological.paper.historico.Historico();
			boolean swPrincipal = true;
			// solo uno aceptamos para mostrar
			historicosTreeEliminado = new ArrayList();
			historicosTreeCambio = new ArrayList();
			for (com.ecological.paper.historico.Historico hist2 : historicos) {
				swhayData = true;

				hist2.setFechaAux(hist2.getFecha_accion().toString());

				if (Utilidades.getTreeEliminado() - hist2.getTipo_accion() == 0) {
					cualquierComentario = hist2.getComentarios();
					historicosTreeEliminado.add(hist2);

				} else {
					historicosTreeCambio.add(hist2);
				}

			}
		}
		return historicosTreeEliminado;
	}

	public void setHistoricosTreeEliminado(
			List<com.ecological.paper.historico.Historico> historicosTree) {
		this.historicosTreeEliminado = historicosTreeEliminado;
	}

	public Tree getTreeBuscarInf() {
		return treeBuscarInf;
	}

	public void setTreeBuscarInf(Tree treeBuscarInf) {
		this.treeBuscarInf = treeBuscarInf;
	}

	public void setListaComentariosTree(
			List<com.ecological.paper.historico.Historico> listaComentariosTree) {
		this.listaComentariosTree = listaComentariosTree;
	}

	public boolean isSwhayData() {
		return swhayData;
	}

	public void setSwhayData(boolean swhayData) {
		this.swhayData = swhayData;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public Tree getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Tree propiedades) {
		this.propiedades = propiedades;
	}

	public boolean isSwSaveDatosBasicos() {
		swSaveDatosBasicos = seguridadTree.isToMod();
		if (swSuperUsuario) {
			swSaveDatosBasicos = true;
		}
		return swSaveDatosBasicos;
	}

	public void setSwSaveDatosBasicos(boolean swSaveDatosBasicos) {

		this.swSaveDatosBasicos = swSaveDatosBasicos;
	}

	public Tree getMoverNodo() {
		return moverNodo;
	}

	public void setMoverNodo(Tree moverNodo) {
		this.moverNodo = moverNodo;
	}

	public List<com.ecological.paper.historico.Historico> getHistoricosTreeCambio() {
		if (treeBuscarInf != null) {
			historicos = delegado.findAllHistoricoTreeMoverNodo(treeBuscarInf);
			historicosTreeCambio = new ArrayList();
			for (com.ecological.paper.historico.Historico hist2 : historicos) {
				swhayData = true;
				hist2.setFechaAux(hist2.getFecha_accion().toString());
				if (!historicosTreeCambio.contains(hist2)) {
					historicosTreeCambio.add(hist2);
				}

			}
		}

		return historicosTreeCambio;
	}

	public void setHistoricosTreeCambio(
			List<com.ecological.paper.historico.Historico> historicosTreeCambio) {
		this.historicosTreeCambio = historicosTreeCambio;
	}

	public List<com.ecological.paper.historico.Historico> getHistoricosTreeEliminado() {
		if (treeBuscarInf != null) {
			historicos = delegado.findAllHistoricoTreeEliminados(treeBuscarInf);
			historicosTreeEliminado = new ArrayList();
			for (com.ecological.paper.historico.Historico hist2 : historicos) {
				swhayData = true;
				cualquierComentario = hist2.getComentarios();
				hist2.setFechaAux(hist2.getFecha_accion().toString());

				if (!historicosTreeEliminado.contains(hist2)) {
					historicosTreeEliminado.add(hist2);
				}

			}
		}
		return historicosTreeEliminado;
	}

	public List<FlowsHistorico> getFlowsHistorico() {
		return flowsHistorico;
	}

	public void setFlowsHistorico(List<FlowsHistorico> flowsHistorico) {
		this.flowsHistorico = flowsHistorico;
	}

	public void setFlowsHistoricoUser(List<FlowsHistorico> flowsHistoricoUser) {
		this.flowsHistoricoUser = flowsHistoricoUser;
	}

	public Date getFecha_creado() {
		fecha_creado = session.getAttribute("fecha_creado") != null ? (Date) session
				.getAttribute("fecha_creado") : null;
		return fecha_creado;
	}

	public void setFecha_creado(Date fecha_creado) {
		System.out.println("***********creado fecha*********" + fecha_creado);
		session.setAttribute("fecha_creado", fecha_creado);

		this.fecha_creado = fecha_creado;
	}

	public Date getFecha_creadoFin() {
		fecha_creadoFin = session.getAttribute("fecha_creadoFin") != null ? (Date) session
				.getAttribute("fecha_creadoFin") : null;
		return fecha_creadoFin;
	}

	public void setFecha_creadoFin(Date fecha_creadoFin) {
		System.out.println("***********fecha_creadoFin*********"
				+ fecha_creadoFin);
		session.setAttribute("fecha_creadoFin", fecha_creadoFin);
		this.fecha_creadoFin = fecha_creadoFin;
	}

	public List<FlowsHistorico> getFlowsHistoricoUser() {

		// tipoSeleccion viene de processFlowTipoAction(ActionEvent event)
		String tipoSeleccion = session.getAttribute("tipoSeleccion") != null ? (String) session
				.getAttribute("tipoSeleccion") : null;
		if (tipoSeleccion != null) {
			if (tipoSeleccion.equalsIgnoreCase(String.valueOf(Utilidades
					.getAsignadoByDuenioFlow()))) {
				flowsHistoricoUser = getFlowsHistoricoUserII();
			} else if (tipoSeleccion.equalsIgnoreCase(String.valueOf(Utilidades
					.getCreadoByDuenioFlow()))) {
				flowsHistoricoUser = getFlowsHistoricoUserCreados();

			}
		}

		return flowsHistoricoUser;
	}

	public List<FlowParalelo> getFlowParaleloUser() {

		// tipoSeleccion viene de processFlowTipoAction(ActionEvent event)
		String tipoSeleccion = session.getAttribute("tipoSeleccion") != null ? (String) session
				.getAttribute("tipoSeleccion") : null;
		if (tipoSeleccion != null) {
			if (tipoSeleccion.equalsIgnoreCase(String.valueOf(Utilidades
					.getAsignadoByDuenioFlow()))) {
				flowParaleloUser = getFlowParaleloUserII();
			} else if (tipoSeleccion.equalsIgnoreCase(String.valueOf(Utilidades
					.getCreadoByDuenioFlow()))) {
				flowParaleloUser = getFlowParaleloUserCreados();
			}
		} else {
			// me trae por fechas los flujos, sin importar los usuarios
			flowParaleloUser = getFlowParaleloSoloFechas();
		}
		return flowParaleloUser;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public List<FlowParalelo> getFlowParalelo() {
		return flowParalelo;
	}

	public void setFlowParalelo(List<FlowParalelo> flowParalelo) {
		this.flowParalelo = flowParalelo;
	}

	public void setFlowParaleloUser(List<FlowParalelo> flowParaleloUser) {
		this.flowParaleloUser = flowParaleloUser;
	}
}
