package com.ecological.paper.cliente.impresion;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.MySeguridad;

import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.historico.Hist_usuarios;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.Repositorio;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;

import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.software.zonas.Estado;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlInputHidden;
import javax.servlet.http.HttpSession;

public class ListarSolicitudImpresion extends ContextSessionRequest {
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private ServicioDelegado delegado = new ServicioDelegado();
	private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
	private Solicitudimpresion solicitudimpresion;
	private boolean swSolicitudimpresionInCurso;
	private boolean swEstaInFlow;
	private boolean swAllObsoletos;
	private SolicitudImpPart solicitudImpParts;
	private List<SolicitudImpPart> solicitudImpPartsLst;
	private List<Usuario> usuariosImpresion;
	private boolean toSolicitudImpresion;
	private boolean toImprimirAdministrar;
	private Tree treeNodoActual = null;
	private List<Usuario> usuarios;
	private Usuario user_logueado;
	private Seguridad seguridadTree = new Seguridad();
	private Seguridad seguridadMenu = new Seguridad();
	private List<Usuario> usuariosPruebas;
	// private Usuario usu=null;
	private HttpSession session = super.getSession();
	private List<Solicitudimpresion> solicitudimpresiones;
	private List<SolicitudImpPart> solicitudImpPartLst;

	public ListarSolicitudImpresion() {
		session = super.getSession();
		// para que refresque cache en datos combo usuario
		session.setAttribute("swCacheUsuario", false);

		if (session != null) {

			// CONSEGUIMOS LA SEGURIDAD MENU
			Tree treeMenu = new Tree();
			treeMenu.setNodo(Utilidades.getNodoRaiz());
			seguridadMenu = super.getSeguridadTree(treeMenu);

			// para solicitud de impresion
			solicitudimpresion = new Solicitudimpresion();
			Doc_detalle doc_detalle = devolverDoc_detalleTreeNodoActual();
			solicitudimpresion.setDoc_detalle(doc_detalle);

			// VERIFICAMOS SI TODOS ESTAN OBSOLETOS PARA MANDAR UNA NUEVA ORDEN
			// DE IMPRESION
			Solicitudimpresion allObsoletos = delegado
					.findSolicitudimpresionByDocDetalle(solicitudimpresion);
			// si es nulo.. todos son obsoletos..
			if (allObsoletos == null) {
				// si todos son obsoletos.. podemos realizar una nueva
				// solicitud de impresion
				swAllObsoletos = true;
			}

			// vemos si el doc-detalle esta en flows
			List<Flow> flows = delegado.findDocumentoDetalleInFlow(doc_detalle);
			if (flows != null && !flows.isEmpty() && flows.size() > 0) {
				swEstaInFlow = true;
			}

			/*
			 * solicitudimpresion.setSwMostrarAll(true); solicitudimpresion =
			 * delegado .findSolicitudimpresionByDocDetalle(solicitudimpresion);
			 * 
			 * if (solicitudimpresion == null) { solicitudimpresion = new
			 * Solicitudimpresion(); } else { // ACTUALIZAMOS LO QUE TENGAMOS
			 * QUE ACTUALIZAR if (solicitudimpresion != null &&
			 * solicitudimpresion.getFechahastaimprimir() != null &&
			 * solicitudimpresion.getFechahastaimprimir() .compareTo(new Date())
			 * > 0) { Doc_estado doc_estado = new Doc_estado();
			 * doc_estado.setCodigo(Utilidades.getObsoleto()); doc_estado =
			 * delegado.findDocEstado(doc_estado);
			 * solicitudimpresion.setEstado(doc_estado);
			 * delegado.edit(solicitudimpresion); } else if
			 * (solicitudimpresion.getEstado().getCodigo() == Utilidades
			 * .getAprobado()) { swSolicitudimpresionInCurso = true;
			 * 
			 * } }
			 */

			// fin para solicitud de impresion

		}

	}

	public String listarSolicitudImpresion() {

		if (session.getAttribute("doc_detalle") != null) {
			session.setAttribute("numCopias", "1");
			session.setAttribute("solicitudimpresion", "-1");
			return "imprimirDocumento";
		}
		return "";
	}

	public String solicitudImpresion() {

		Doc_detalle doc_detalle = devolverDoc_detalleTreeNodoActual();
		/*
		 * treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
		 * 
		 * List<Doc_maestro> doc_maestros =
		 * delegado.findAllDoc_maestros(treeNodoActual); for (Doc_maestro
		 * d:doc_maestros ){ List<Doc_detalle> Doc_Detallelst=
		 * delegado.findAllDoc_Detalles(d); for (Doc_detalle
		 * dtalle:Doc_Detallelst){ doc_detalle=dtalle; break; } break; }
		 */

		solicitudImpPartsLst = new ArrayList();

		Doc_estado doc_estado = null;
		for (Usuario usu : usuarios) {
			if ((selectedIds != null) && (selectedIds.get(usu.getId()) != null)) {
				if (selectedIds.get(usu.getId()).booleanValue()) {
					SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
					solicitudImpParts.setUsuario(usu);
					doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getImprimirsin());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudImpParts.setEstado(doc_estado);
					solicitudImpParts.setStatus(true);
					solicitudImpPartsLst.add(solicitudImpParts);
					// files.add(ar.getArchivo().trim());
					System.out
							.println("Usuario seleccionado=" + usu.toString());

				}
			}
		}

		if (!isNumeric(solicitudimpresion.getNumcopias() + "")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("numcopiasmessages")
							+ " " + messages.getString("errorNumero")));
		}

		// if(date1.before(date2) || date1.equals(date2)){ // lo anterior se
		// resume: date1 <= date2

		// COMENTARIO
		if (isEmptyOrNull(solicitudimpresion.getComentarios())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("falta_data")));
			// PARTICIPANYTES
		} else if (solicitudImpPartsLst.size() == 0
				|| solicitudImpPartsLst.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("falta_data")));
			// FECHA HASTA NULA
		} else if (solicitudimpresion.getFechahastaimprimir() == null) {
			// #{txt.fecha} #{txt.hasta}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("fecha") + " "
							+ messages.getString("hasta")));
			// FECHA DESDE < FECHA HASTA
		} else if (solicitudimpresion.getFechadesdeimprimir() != null
				&& solicitudimpresion.getFechahastaimprimir() != null
				&& solicitudimpresion.getFechadesdeimprimir().after(
						solicitudimpresion.getFechahastaimprimir())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("error") + "->"
							+ messages.getString("fecha")));
			// FECHA HASTA MENOR QUE DATE
		} else if (solicitudimpresion.getFechadesdeimprimir() == null
				&& solicitudimpresion.getFechahastaimprimir()
						.before(new Date())) {

			// #{txt.fecha} #{txt.hasta}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("fecha") + " "
							+ messages.getString("hasta")));
		} else {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			// perfecto
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getEnAprobacion());
			doc_estado = delegado.findDocEstado(doc_estado);
			solicitudimpresion.setEstado(doc_estado);
			solicitudimpresion.setSolicitante(user_logueado);
			solicitudimpresion.setFechaSolicitud(new Date());
			solicitudimpresion.setDoc_detalle(doc_detalle);
			solicitudimpresion.setsolicitudImpParts(solicitudImpPartsLst);
			session.setAttribute("solicitudimpresion", solicitudimpresion);
			session.setAttribute("doc_detalle", doc_detalle);
			session.setAttribute("doc_maestro", doc_detalle.getDoc_maestro());
			return Utilidades.getFlows();

		}
		return "";

	}

	public String crearSolicitudImpresion() {
		solicitudimpresion = new Solicitudimpresion();
		session.setAttribute("solicitudimpresion", solicitudimpresion);

		return "solicitudImpresion";
	}

	public Doc_detalle devolverDoc_detalleTreeNodoActual() {
		session = super.getSession();
		Doc_detalle doc_detalle = null;
		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");

		List<Doc_maestro> doc_maestros = delegado
				.findAllDoc_maestros(treeNodoActual);
		for (Doc_maestro d : doc_maestros) {
			List<Doc_detalle> Doc_Detallelst = delegado.findAllDoc_Detalles(d);
			for (Doc_detalle dtalle : Doc_Detallelst) {
				doc_detalle = dtalle;
				break;
			}
			break;
		}
		session.setAttribute("objeto", doc_detalle);
		return doc_detalle;
	}

	/*
	 * public List<Usuario> getUsuariosImpresion() { try {
	 * 
	 * // SE INICIALIZA EN EL CONTRUCTOIR DE LA CLASE user_logueado =
	 * session.getAttribute("user_logueado") != null ? (Usuario) session
	 * .getAttribute("user_logueado") : null; treeNodoActual =
	 * session.getAttribute("treeNodoActual") != null ? (Tree) session
	 * .getAttribute("treeNodoActual") : null;
	 * 
	 * List<Usuario> usuariosImpresion = new ArrayList<Usuario>();
	 * usuariosImpresion = new ArrayList<Usuario>();
	 * 
	 * // esta variable solicitudimpresion se inicializa en el constructor if
	 * (solicitudimpresion != null && solicitudimpresion.getDoc_detalle()!=null
	 * 
	 * ) {
	 * 
	 * List<Solicitudimpresion> solicitudimpresiones =
	 * (List<Solicitudimpresion>) delegado
	 * .findAllSolicitudimpresionByDocDetalle(solicitudimpresion);
	 * 
	 * 
	 * Iterator it = solicitudimpresiones.iterator();
	 * 
	 * while (it.hasNext()) { Solicitudimpresion si = (Solicitudimpresion)
	 * it.next();
	 * 
	 * 
	 * for (SolicitudImpPart sip : si.getsolicitudImpParts()) {
	 * 
	 * // si todos son obsoletos.. podemos realizar una nueva // solicitud de
	 * impresion
	 * 
	 * if ((sip.getSolicitudimpresion().getEstado() .getCodigo() -
	 * Utilidades.getObsoleto()) != 0) { swAllObsoletos = false; } if
	 * (sip.getEstado().getCodigo() == Utilidades .getImprimirsin()) {
	 * sip.getUsuario().setCancelar(true); } if ((sip.getUsuario().getId() -
	 * user_logueado.getId()) == 0) { sip.getUsuario().setSwMostrar(true); }
	 * 
	 * try { sip.getUsuario() .getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechadesdeimprimirtxt( Utilidades.sdfShow.format(sip .getUsuario()
	 * .getsolicitudImpParts() .getSolicitudimpresion()
	 * .getFechadesdeimprimir())); } catch (NullPointerException e) {
	 * sip.getUsuario().getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechadesdeimprimirtxt(""); } try { sip.getUsuario()
	 * .getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechahastaimprimirtxt( Utilidades.sdfShow.format(sip .getUsuario()
	 * .getsolicitudImpParts() .getSolicitudimpresion()
	 * .getFechahastaimprimir())); } catch (NullPointerException e) {
	 * sip.getUsuario().getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechahastaimprimirtxt(""); }
	 * 
	 * try { sip.getUsuario() .getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechaSolicitudtxt( Utilidades.sdfShow.format(sip .getUsuario()
	 * .getsolicitudImpParts() .getSolicitudimpresion() .getFechaSolicitud()));
	 * } catch (NullPointerException e) {
	 * sip.getUsuario().getsolicitudImpParts() .getSolicitudimpresion()
	 * .setFechaSolicitudtxt(""); } sip.getUsuario().setsolicitudImpParts(sip);
	 * usuariosImpresion.add(sip.getUsuario());
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return usuariosImpresion; } } catch (Exception e) { e.printStackTrace();
	 * System.out.println(); } return usuariosImpresion;
	 * 
	 * }
	 */

	public String edit() {
		return "solicitudImpresion";
	}

	public void selection(ActionEvent event) throws RoleMultiples {
		Solicitudimpresion solicitudimpresionEdit = new Solicitudimpresion();
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el objeto
			if (id >= 0) {
				solicitudimpresionEdit.setCodigo(new Long(id));
			}

			// System.out.println("pasa?? id="+id);
			solicitudimpresionEdit = delegado.find(solicitudimpresionEdit);
			solicitudimpresionEdit.setsolicitudImpParts(delegado
					.findAllsolicitudImpParts(solicitudimpresionEdit));
			Collection<SolicitudImpPart> s = solicitudimpresionEdit
					.getSolicitudImpParts();
			Iterator i = s.iterator();
			while (i.hasNext()) {
				SolicitudImpPart f = (SolicitudImpPart) i.next();
			}
			session.setAttribute("solicitudimpresion", solicitudimpresionEdit);
		}
	}

	public String regresar() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		session.removeAttribute("solicitudimpresion");

		return "listarflowsParalelo";

	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Map<Long, Boolean> getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(Map<Long, Boolean> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
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

	public ServicioDelegado getDelegado() {
		return delegado;
	}

	public void setDelegado(ServicioDelegado delegado) {
		this.delegado = delegado;
	}

	public Solicitudimpresion getSolicitudimpresion() {
		return solicitudimpresion;
	}

	public void setSolicitudimpresion(Solicitudimpresion solicitudimpresion) {
		this.solicitudimpresion = solicitudimpresion;
	}

	public boolean isSwSolicitudimpresionInCurso() {
		return swSolicitudimpresionInCurso;
	}

	public void setSwSolicitudimpresionInCurso(
			boolean swSolicitudimpresionInCurso) {
		this.swSolicitudimpresionInCurso = swSolicitudimpresionInCurso;
	}

	public boolean isSwEstaInFlow() {
		return swEstaInFlow;
	}

	public void setSwEstaInFlow(boolean swEstaInFlow) {
		this.swEstaInFlow = swEstaInFlow;
	}

	public SolicitudImpPart getSolicitudImpParts() {
		return solicitudImpParts;
	}

	public void setSolicitudImpParts(SolicitudImpPart solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}

	public List<SolicitudImpPart> getSolicitudImpPartsLst() {
		return solicitudImpPartsLst;
	}

	public void setSolicitudImpPartsLst(
			List<SolicitudImpPart> solicitudImpPartsLst) {
		this.solicitudImpPartsLst = solicitudImpPartsLst;
	}

	public boolean isToSolicitudImpresion() {
		return toSolicitudImpresion;
	}

	public void setToSolicitudImpresion(boolean toSolicitudImpresion) {
		this.toSolicitudImpresion = toSolicitudImpresion;
	}

	public boolean isToImprimirAdministrar() {
		return toImprimirAdministrar;
	}

	public void setToImprimirAutorizacion(boolean toImprimirAdministrar) {
		this.toImprimirAdministrar = toImprimirAdministrar;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setUsuariosImpresion(List<Usuario> usuariosImpresion) {
		this.usuariosImpresion = usuariosImpresion;
	}

	public List<Usuario> getUsuariosPruebas() {
		usuariosPruebas = new ArrayList<Usuario>();
		return usuariosPruebas;
	}

	public void setUsuariosPruebas(List<Usuario> usuariosPruebas) {
		this.usuariosPruebas = usuariosPruebas;
	}

	public boolean isSwAllObsoletos() {
		return swAllObsoletos;
	}

	public void setSwAllObsoletos(boolean swAllObsoletos) {
		this.swAllObsoletos = swAllObsoletos;
	}

	public List<Solicitudimpresion> getSolicitudimpresiones() {

		// SE INICIALIZA EN EL CONTRUCTOIR DE LA CLASE
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		List<Usuario> usuariosImpresion = new ArrayList<Usuario>();
		
		Solicitudimpresion solicitudimp= new Solicitudimpresion();
		solicitudimp.setSolicitante(user_logueado);
		
		// esta variable solicitudimpresion se inicializa en el constructor
		if (solicitudimp != null

		) {
			solicitudimpresiones = (List<Solicitudimpresion>) delegado
					.findAllSolicitudimpresionByDocDetalle(solicitudimp);

			if (solicitudimpresiones == null) {
				solicitudimpresiones = new ArrayList<Solicitudimpresion>();
			} else {
				List<Solicitudimpresion> solicitudimpresiones2 = new ArrayList<Solicitudimpresion>();

				for (Solicitudimpresion sip : solicitudimpresiones) {

					try {
						sip.setFechadesdeimprimirtxt(Utilidades.sdfShow
								.format(sip.getFechadesdeimprimir()));
					} catch (NullPointerException e) {
						sip.setFechadesdeimprimirtxt("");
					}
					try {
						sip.setFechahastaimprimirtxt(Utilidades.sdfShow
								.format(sip.getFechahastaimprimir()));
					} catch (NullPointerException e) {
						sip.setFechahastaimprimirtxt("");
					}

					try {
						sip.setFechaSolicitudtxt(Utilidades.sdfShow.format(sip
								.getFechaSolicitud()));
					} catch (NullPointerException e) {
						sip.setFechaSolicitudtxt("");
					}
					
				 
					solicitudimpresiones2.add(sip);
				}
				solicitudimpresiones.clear();
				solicitudimpresiones.addAll(solicitudimpresiones2);
			}
		}

		return solicitudimpresiones;
	}

	public String viewAppletImp() {
		return "imprimirDocumento";
	}

	public void versionId(ActionEvent event) {
		try {

			String attrname1 = (String) event.getComponent().getAttributes()
					.get("attrname1");
			UIParameter numcopiasCmp = null;
			numcopiasCmp = (UIParameter) event.getComponent().findComponent(
					"numcopias");

			UIParameter solicitudimpresionCmp = null;

			solicitudimpresionCmp = (UIParameter) event.getComponent()
					.findComponent("solicitudimpresion");

			long numCopias = Long.parseLong(numcopiasCmp.getValue().toString());
			long solicitudimpresion = Long.parseLong(solicitudimpresionCmp
					.getValue().toString());
			session.setAttribute("numCopias", numCopias + "");
			session.setAttribute("solicitudimpresion", solicitudimpresion + "");

		} catch (Exception e) {
			e.printStackTrace();
			// redirect();
		}
	}

	public void setSolicitudimpresiones(
			List<Solicitudimpresion> solicitudimpresiones) {
		this.solicitudimpresiones = solicitudimpresiones;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public List<SolicitudImpPart> getSolicitudImpPartLst() {
		List<SolicitudImpPart> solicitudImpPartLstAux = new ArrayList<SolicitudImpPart>();
		// SE INICIALIZA EN EL CONTRUCTOIR DE LA CLASE
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		List<Usuario> usuariosImpresion = new ArrayList<Usuario>();
		solicitudimpresion = new Solicitudimpresion();
		Doc_detalle doc_detalle = devolverDoc_detalleTreeNodoActual();
		// solicitudimpresion.setDoc_detalle(doc_detalle);
		SolicitudImpPart solicitudImpPart = new SolicitudImpPart();
		solicitudImpPart.setSolicitudimpresion(solicitudimpresion);
		solicitudImpPart.setUsuario(user_logueado);
		solicitudImpPartLst = (List<SolicitudImpPart>) delegado
				.findAllsolicitudImpPartsEmpresa(solicitudImpPart);

		for (SolicitudImpPart sip : solicitudImpPartLst) {
			System.out.println("sip.getCodigo()=" + sip.getCodigo());
			if (sip.getEstado().getCodigo() == Utilidades.getImprimirsin()) {
				sip.setCancelar(true);
			}
			solicitudImpPartLstAux.add(sip);
		}

		return solicitudImpPartLstAux;
	}

	public void setSolicitudImpPartLst(
			List<SolicitudImpPart> solicitudImpPartLst) {
		this.solicitudImpPartLst = solicitudImpPartLst;
	}

	public String aceptar() {
		String pagIr = "";
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", "listarSolicitudImpresion");
		return "";
	}
	

	public String firmarFlow() {
		String pagIr = "";
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", "listarSolicitudImpresion");
		return "flowsResponse";
	}

	public void cancelarsolicitudImpParts(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("cancelarImpresionId");

		
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				SolicitudImpPart objeto = new SolicitudImpPart();
				objeto.setCodigo(id);
				objeto = delegado.find(objeto);
				if (objeto != null) {
					delegado.solicitudImpPartsCancelar(objeto);
					// obtenbemos los usuarios para que refresqiue la pag
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
				}

			}
		}

	}
	

	public void firmarsolicitudImpParts(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("usuarioImpresionId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				SolicitudImpPart objeto = new SolicitudImpPart();
				objeto.setCodigo(id);
				objeto = delegado.find(objeto);

				if (objeto != null) {
					System.out.println("flow id="+objeto
							.getSolicitudimpresion().getFlow().getCodigo());
					
					// delegado.solicitudImpPartsCancelar(objeto);
					session.setAttribute("flowpuente", objeto
							.getSolicitudimpresion().getFlow().getCodigo()+"");
					session.setAttribute("doc_detalle", objeto
							.getSolicitudimpresion().getDoc_detalle());
					// se uada para ver los tabs en el flowresponse.jsp en
					// rendered
//					session.setAttribute("vertab", true);

					// inicializo el comentario del usuario especifico
					// esto en caso que el usuario valla a firmar, mellevo el
					// ide
					// que va a firmar
//					Flow_Participantes flow_Participante = new Flow_Participantes (); 
//					flow_Participante.setFlow(objeto
//							.getSolicitudimpresion().getFlow());
					
					//LOS PARTICIPANES.. SON LOS QUE VAN EN LA PLANTILLA,ESTANB DEFINIDOS PARA TODO EL MUNDO 
					//NO TIENE NADA QUE VER CON EL SUUARIO QUE VA AIMPRIMIR
					//CADA USUARIO QUE MANDSA UNA SOLICITUD DE IMPRESION.. ES UN ID DE FLUJO
//					flow_Participante.setParticipante(null);
//					List<Flow_Participantes> fps= delegado.findByFlowParticipantes(flow_Participante);
//					if (fps!=null && fps.size()>0){
//						session.setAttribute("flow_Participante", fps.get(0));
//					}
					

				}

			}
		}

	}

}
