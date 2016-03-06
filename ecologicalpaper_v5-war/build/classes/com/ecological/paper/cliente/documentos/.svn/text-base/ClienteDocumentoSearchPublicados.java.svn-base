/*
 * ClienteDocumentoSearchPublicados.java
 *
 * Created on August 30, 2007, 10:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.documentos;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.flows.ClienteFlowsParalelo;
import com.ecological.paper.cliente.flows.ClienteFlowsResponse;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.PublicadosUsuComent;
import com.ecological.paper.documentos.RolesOnlyViewDocPublicados;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.documentos.TablaAuxiliar;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.reportes.SubFlowReporte;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.sun.star.io.UnknownHostException;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.management.relation.Role;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class ClienteDocumentoSearchPublicados extends ContextSessionRequest {
	private HtmlSelectOneMenu selectUsuario;
	private ServicioDelegado delegado = new ServicioDelegado();
	private HtmlSelectOneMenu selectArea;
	private DatosCombo datosCombo = new DatosCombo();
	private String passwordOculta;
	private String passwordConfirm;
	private HtmlSelectOneMenu selectCargo;
	private HtmlOutputText id_str;
	private HtmlSelectOneMenu selectOneMenuProfesion;
	private HtmlSelectOneMenu selectOneMenuCargo;
	private List<Doc_maestro> doc_maestros;
	private List<Doc_detalle> doc_detalles;
	private Doc_maestro doc_maestro;
	private Doc_detalle doc_detalle;
	private Doc_detalle doc_detalle_aux;
	private Context context = null;
	private String maquina;
	private Tree area;
	private List<SelectItem> allCargos;
	private List<SelectItem> AllProfesion;
	private Tree treeNodoActual = null;
	private String strBuscar;
	// private Usuario usu=null;
	private HttpSession session = super.getSession();
	// private HttpServletRequest publicados =
	// (HttpServletRequest)super.geRequest();
	private String parametro = session.getAttribute("parametro") != null ? (String) session
			.getAttribute("parametro") : "";
	private ClienteRole clienteRole = new ClienteRole();
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private Usuario user_logueado;
	private String strError;
	// VARIABLES PARA BUSQUEDAS
	private char buscarPor;
	private char searchNombreDoc;
	private char none;
	private char searchConsecutivoDoc;
	private String searchUsuario;
	private String searchDoc_edo;
	private String searchDoc_tipo;
	private Doc_tipo doc_tipo;
	private Usuario usuario;
	private Doc_estado doc_estado;
	private List usuarios;
	private String usuarioSt;
	private String usuarioId;
	private String usuarioFlowId;
	private List doc_tipos;
	private List doc_tiposSearch;
	private String doc_tiposSearchSTR;
	private String doc_tipoSt;
	private String doc_tipoId;
	private boolean swPorFirmarFlow;
	private boolean swPorFirmarFlowView;
	private List doc_estados;
	private String doc_estadoSt;
	private String doc_estadoId;
	private Date fecha_creado;
	private Date fecha_creadoFin;
	private Seguridad seguridadTree = new Seguridad();
	private boolean swSuperUsuario;
	private int izquierdaBD;
	private int derechaBD;
	private int cuantosResultsetSize;
	private boolean swMostrarDesplazamiento = true;
	private Date fechaDesde;
	private Date fechaHasta;
	// tabla para trabajar con fechas
	private TablaAuxiliar tablaAuxiliar;
	// nuevas busqaueda
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	// estadisticas del docyumento
	private String stadautordeldocumento;
	private String stadestadodeldocumento;
	private String stadtipodedocumento;
	private String buscar;
	private boolean swPublicados;
	private boolean swPublicadosExpirados;

	// SEGURIDAD

	private Seguridad seguridadMenu = new Seguridad();
	private PublicadosUsuComent publicadosUsuComent;
	private boolean swMostraDocIframe;

	/** Creates a new instance of ClienteDocumentoSearchPublicados */
	public ClienteDocumentoSearchPublicados() {
		session = super.getSession();

		try {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			// CONSEGUIMOS LA SEGURIDAD MENU
			Tree treeMenu = new Tree();
			treeMenu.setNodo(Utilidades.getNodoRaiz());
			seguridadMenu = super.getSeguridadTree(treeMenu);

			if (session.getAttribute("buscarPor") != null) {
				buscarPor = session.getAttribute("buscarPor").toString()
						.charAt(0);
			} else {
				buscarPor = getNone();
			}
			session.removeAttribute("debeFiltrar");

			if (session.getAttribute("izquierdaBD") == null) {
				session.setAttribute("izquierdaBD", 0);
			}
			if (session.getAttribute("derechaBD") == null) {
				session.setAttribute("derechaBD",
						Utilidades.getMaxRegisterMostrar());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out
				.println("INICIALIZANDO CONSTRUCTOR clienteDocumentoSearchPublicados ");

	}

	/*************** SELECT ,MULTIIPLES *****************************************/
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
	public String goDownload() {
		return "ReporteExcel";
	}

	public String goStadistica() {
		// agarramos variables que se inicalizan aca para tenerlas en el request

		// ------------finb--------------
		// session.setAttribute("doc_detalleGrafica", doc_detalle);
		// session.setAttribute("doc_maestroGrafica", doc_maestro);
		String pagIr = "";// "DocumentoStadisticasGraficar";
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalleGrafico");
		if (doc_detalle != null) {
			getFacesContext().addMessage(
					null,
					new FacesMessage(messages.getString("graficomostrar") + " "
							+ doc_detalle.getDoc_maestro().getNombre())); // TODO
			// Cambiar
			// mensage
			// por
			// bundle.
		}

		// parametro = session.getAttribute("parametro") != null ? (String)
		// session.getAttribute("parametro") : "";
		return pagIr;
	}

	public String goDocument() {
		String pagIr = "";
		parametro = session.getAttribute("parametro") != null ? (String) session
				.getAttribute("parametro") : "";
		if ("publico".equalsIgnoreCase(parametro)) {
			pagIr = "viewOnly";
			// session.setAttribute("edit", true);
		} else {
			pagIr = "goDocument";
		}

		return pagIr;
	}

	public void versionIdGrafico(ActionEvent event) {
		// inicializamos todas las variables

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdg");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_maestro == null) {
				doc_maestro = new Doc_maestro();
			}
			session.removeAttribute("doc_detalleGrafico");
			// buscamos el role para editar
			if (id >= 0) {
				doc_maestro.setCodigo(new Long(id));
				doc_maestro = delegado.findMaestro(doc_maestro);
				doc_detalle = delegado.findUltimolDoc_Detalles(doc_maestro);
				// SE USA ESTRAS VARIABLES PARA GRAFICAR

				session.setAttribute("doc_detalleGrafico", doc_detalle);
				// _____________________________________________________________________
			}

			// Tree treeNodoActual =
			// delegado.obtenerNodo(doc_maestro.getTree().getNodo());

			// session.setAttribute("treeNodoActual", treeNodoActual);
		}
	}
	
	public void versionIdMejorado(ActionEvent event) {
		// inicializamos todas las variables

	 
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdComentarioAll");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdComentario");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editId2_2");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdImprimir");
		}

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle= new Doc_detalle();
			}
			doc_maestro = new Doc_maestro();
			// buscamos el role para editar
			if (id >= 0) {
				doc_detalle.setCodigo(new Long(id));
				doc_detalle = delegado.findDocDetalle(doc_detalle);
				//el detalle viene de <f:setPropertyActionListener target detalle value maestro
				session.setAttribute("doc_detalle", doc_detalle);
				doc_maestro=doc_detalle.getDoc_maestro();
				session.setAttribute("doc_maestro", doc_maestro);
			 
			}

			// _____________________________________________________________________
			// REVISAMOS SI EL DOC ES PUBLICO,ACCESO DIRECTO A VER ETALLES DEL
			// DOC
			parametro = session.getAttribute("parametro") != null ? (String) session
					.getAttribute("parametro") : "";
			if ("publico".equalsIgnoreCase(parametro)) {
//				Doc_detalle doc_detalle = delegado
//						.findDetalleDocumewntoPublico(doc_maestro);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
				session.setAttribute("listaDepublico", true);
			}
			// _____________________________________________________________________

			Tree treeNodoActual = delegado.obtenerNodo(doc_maestro.getTree()
					.getNodo());

			session.setAttribute("treeNodoActual", treeNodoActual);
		}
	}

	
//
//	public void versionId(ActionEvent event) {
//		// inicializamos todas las variables
//
//	 
//		UIParameter component = (UIParameter) event.getComponent()
//				.findComponent("editId2");
//
//		if (component == null) {
//			component = (UIParameter) event.getComponent().findComponent(
//					"editIdComentarioAll");
//		}
//
//		if (component == null) {
//			component = (UIParameter) event.getComponent().findComponent(
//					"editIdComentario");
//		}
//
//		if (component == null) {
//			component = (UIParameter) event.getComponent().findComponent(
//					"editId2_2");
//		}
//
//		if (component == null) {
//			component = (UIParameter) event.getComponent().findComponent(
//					"editIdImprimir");
//		}
//
//		if ((component != null) && (component.getValue().toString() != null)) {
//			long id = Long.parseLong(component.getValue().toString());
//			if (doc_maestro == null) {
//				doc_maestro = new Doc_maestro();
//		
//			}
//			// buscamos el role para editar
//			if (id >= 0) {
//				doc_maestro.setCodigo(new Long(id));
//				doc_maestro = delegado.findMaestro(doc_maestro);
//				session.setAttribute("doc_maestro", doc_maestro);
//				 doc_detalle = delegado
//						.findDetalleDocumewntoPublico(doc_maestro);
//				session.setAttribute("doc_detalle", doc_detalle);
//			}
//
//			// _____________________________________________________________________
//			// REVISAMOS SI EL DOC ES PUBLICO,ACCESO DIRECTO A VER ETALLES DEL
//			// DOC
//			parametro = session.getAttribute("parametro") != null ? (String) session
//					.getAttribute("parametro") : "";
//			if ("publico".equalsIgnoreCase(parametro)) {
//				
//				session.setAttribute("doc_maestro", doc_maestro);
//				session.setAttribute("listaDepublico", true);
//			}
//			// _____________________________________________________________________
//			Tree treeNodoActual = delegado.obtenerNodo(doc_maestro.getTree()
//					.getNodo());
//
//			session.setAttribute("treeNodoActual", treeNodoActual);
//		}
//	}

	
	public void versionId(ActionEvent event) {
		// inicializamos todas las variables

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdComentarioAll");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdComentario");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editId2_2");
		}

		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdImprimir");
		}

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

			// _____________________________________________________________________
			// REVISAMOS SI EL DOC ES PUBLICO,ACCESO DIRECTO A VER ETALLES DEL
			// DOC
			parametro = session.getAttribute("parametro") != null ? (String) session
					.getAttribute("parametro") : "";
			if ("publico".equalsIgnoreCase(parametro)) {
				Doc_detalle doc_detalle = delegado
						.findDetalleDocumewntoPublico(doc_maestro);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
				session.setAttribute("listaDepublico", true);
			}
			// _____________________________________________________________________

			Tree treeNodoActual = delegado.obtenerNodo(doc_maestro.getTree()
					.getNodo());

			session.setAttribute("treeNodoActual", treeNodoActual);
		}
	}

	
	public boolean habilitadoImprimir(Doc_detalle docImp, Usuario user_logueado) {

		Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
		solicitudimpresion.setDoc_detalle(docImp);

		SolicitudImpPart solicitudImpPart = new SolicitudImpPart();
		solicitudImpPart.setSolicitudimpresion(solicitudimpresion);

		solicitudImpPart.setUsuario(user_logueado);
		List<SolicitudImpPart> lista = delegado
				.findsolicitudImpPartsToSendToImprimir(solicitudImpPart);
		if (lista != null && lista.size() > 0) {
			return true;
		}

		return false;
	}

	public boolean estaInFlowToImprimir(Doc_detalle doc_detalle,
			Usuario user_logueado) {
		doc_detalle.setOrigen(Utilidades.getOrigenflowimpresor());
		SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
		Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
		solicitudimpresion.setDoc_detalle(doc_detalle);
		// EL USUARIO QUE PIDE LA PETICION ES EWL MISMO USUARIO
		// QUE VA A IMPRTIMIR
		solicitudImpParts.setUsuario(user_logueado);
		solicitudImpParts.setSolicitudimpresion(solicitudimpresion);
		List<SolicitudImpPart> solicitudImpPartLst = delegado
				.findAllsolicitudImpParts(solicitudImpParts);
		if (solicitudImpPartLst != null && solicitudImpPartLst.size() > 0) {
			return true;
		}
		return false;
	}

	public String hacerSendSolicitudImpresion() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		Doc_detalle docImp = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : null;

		if (!habilitadoImprimir(docImp, user_logueado)) {
			// mandamos a solicitar un flujo de impresion automatico
			FlowParalelo objeto = new FlowParalelo();
			objeto.setUsuario(user_logueado);
			try {

				// OBTENEMOS EL FLOWPARALELO DE IMPRESION QUE ES UNO SOLO
				FlowParalelo flowParaleloSolicitud = delegado
						.findExistePlantillaImpresionFlowObjeto(objeto);
				if (flowParaleloSolicitud != null) {
					// MANDAMOS HACEREL FLOW AUTOMATICO
					session.setAttribute("flowParalelo", flowParaleloSolicitud);
					ClienteFlowsParalelo clienteFlowsParalelo = new ClienteFlowsParalelo();
					session.setAttribute("doc_detalleforflowplantilla", docImp);
					String pagIr = clienteFlowsParalelo
							.crearFlujoDesdeFlujoParalelo_action();

					// NOS VAMOS A PUBLICAR DOCUMENTO UNAVES CO,MPLEATDO EL
					// EXITOSO
					if (!isEmptyOrNull(pagIr)) {
						session.setAttribute(Utilidades.getNoclearsession(), "");
						session.setAttribute("pagIr",
								"listarDocumentoSearchPublicados");
						pagIr = Utilidades.getFinexitosorefresca();
					}

					return pagIr;
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("flowsolicitudImpresionvoid")));
				}

			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// ESTO ES MANDAR A IMPRIMIR DE MANERA AUTOMATICA
			session.setAttribute("numCopias", "1");
			session.setAttribute("solicitudimpresion", "-1");
			return "imprimirDocumento";
		}

		return "";
	}

	public void IniOperaciones() {
		/*
		 * customizePageBean=new CustomizePageBean(); invisibleItems
		 * =session.getAttribute
		 * ("invisibleItems")!=null?(List)session.getAttribute
		 * ("invisibleItems"):null; visibleItems
		 * =session.getAttribute("visibleItems"
		 * )!=null?(List)session.getAttribute("visibleItems"):null;
		 */
		// session.setAttribute("role",role);
		/*
		 * setInvisibleItems(invisibleItems); setVisibleItems(visibleItems);
		 */
	}

	public Usuario getUsuario() {
		// validamos por ajax a los combos para hacer busquedas
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> findAll() {
		// return servicioUsuario.findAll();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			return delegado.findAll_Usuario(user_logueado);
		} else {
			return null;
		}
	}

	public String derechaGoBD() {

		if (session.getAttribute("izquierdaBD") == null) {
			session.setAttribute("izquierdaBD", 0);
		} else {
			izquierdaBD = (Integer) session.getAttribute("izquierdaBD");
		}
		if (session.getAttribute("derechaBD") == null) {
			session.setAttribute("derechaBD",
					Utilidades.getMaxRegisterMostrar());
		} else {
			derechaBD = (Integer) session.getAttribute("derechaBD");
		}
		izquierdaBD = derechaBD;
		derechaBD += Utilidades.getMaxRegisterMostrar();
		session.setAttribute("izquierdaBD", izquierdaBD);
		session.setAttribute("derechaBD", derechaBD);

		return "";
	}

	public String izquierdaGoBD() {
		if (session.getAttribute("izquierdaBD") == null) {
			session.setAttribute("izquierdaBD", 0);
		} else {
			izquierdaBD = (Integer) session.getAttribute("izquierdaBD");
		}
		if (session.getAttribute("derechaBD") == null) {
			session.setAttribute("derechaBD",
					Utilidades.getMaxRegisterMostrar());
		} else {
			derechaBD = (Integer) session.getAttribute("derechaBD");
		}
		if ((izquierdaBD - Utilidades.getMaxRegisterMostrar()) >= 0) {
			izquierdaBD -= Utilidades.getMaxRegisterMostrar();
			derechaBD -= Utilidades.getMaxRegisterMostrar();
		}
		session.setAttribute("izquierdaBD", izquierdaBD);
		session.setAttribute("derechaBD", derechaBD);

		return "";
	}

	public String cancelarListtUser() {
		try {
			// inicializamos todas las sessiones,dejamos solo las basicas, antes
			// de
			// irnos
			super.clearSession(session,
					confmessages.getString("usuarioSession"));

		} catch (Exception e) {
			redirect("ClienteDocumentoSearchPublicados", "cancelarListtUser", e);
		}
		return "menu";
	}

	public String cancelarEditUser() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentoSearchPublicados", "cancelarEditUser", e);
		}

		return "listarUsuarios";
	}

	public String regresarExitoso() {
		String pagIr = session.getAttribute("pagIr") != null ? (String) session
				.getAttribute("pagIr") : "";
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		super.clearSession(session, confmessages.getString("usuarioSession"));

		return pagIr;
	}

	/*
	 * public List<ClienteDocumentoMaestro> getDoc_maestros() {
	 * List<ClienteDocumentoMaestro> listamostar = new
	 * ArrayList<ClienteDocumentoMaestro>(); listamostar.clear();
	 * ClienteDocumentoMaestro clienteDocumentoMaestro = new
	 * ClienteDocumentoMaestro(); String query =
	 * (String)session.getAttribute("query"); if
	 * (session.getAttribute("fechaparametro")!=null){ String
	 * fechaparametro=(String) session.getAttribute("fechaparametro");
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 * session.removeAttribute("fechaparametro"); try { doc_maestros =
	 * delegado.queryExecute(query.toString(),sdf.parse(fechaparametro)); }
	 * catch (ParseException ex) { ex.printStackTrace(); } }else{ doc_maestros =
	 * delegado.queryExecute(query.toString()); int j=0; int k=0; int size =
	 * doc_maestros.size(); for(Doc_maestro doc:doc_maestros){ k++; // for (int
	 * k = 0; k < size; k++) { System.out.println("k="+k); //Doc_maestro doc =
	 * (Doc_maestro) doc_maestros.get(k); setDoc_maestro(doc); String
	 * prefijo=""; boolean nombreCorto=false; //
	 * prefijo=getPrefijo(doc.getTree(),prefijo,nombreCorto); String parametro =
	 * (String)session.getAttribute("parametro"); List <Doc_detalle>
	 * doc_detalles=
	 * delegado.findDetallesListarDocumewntos(doc,getDoc_estadoId(),
	 * getUsuarioId(),
	 * parametro);//delegado.queryExecute(sqlDocDetalle.toString());
	 * System.out.println("doc.getCodigo()="+doc.getCodigo());
	 * System.out.println("doc="+doc.getNombre()); if (doc_detalles != null &&
	 * !doc_detalles.isEmpty()) { j++; System.out.println("j="+j); Doc_detalle
	 * doc_detalle = doc_detalles.get(0); ClienteDocumentoDetalle
	 * doc_detalle_copy = new ClienteDocumentoDetalle(doc_detalle);
	 * clienteDocumentoMaestro = new
	 * ClienteDocumentoMaestro(getDoc_maestro(),doc_detalle_copy,prefijo);
	 * listamostar.add(clienteDocumentoMaestro); } } }
	 * session.setAttribute("listamostar",listamostar) ; return listamostar; }
	 */
	public List<Doc_maestro> getDoc_maestros() {
		List<Doc_maestro> listamostar = new ArrayList<Doc_maestro>();
		listamostar.clear();
		ClienteDocumentoMaestro clienteDocumentoMaestro = new ClienteDocumentoMaestro();
		String query = (String) session.getAttribute("query");
		long t1 = 0;
		long t2 = 0;
		if ((session.getAttribute("fechaparametro") != null)
				&& (session.getAttribute("fechaparametro2") != null)) {

			doc_maestros = delegado.queryExecute(query.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
			session.removeAttribute("fechaparametro");
			session.removeAttribute("fechaparametro2");

		} else {

			boolean debeFiltrar = session.getAttribute("debeFiltrar") != null;
			// if (debeFiltrar){
			doc_maestros = delegado.queryExecute(query.toString());
			int j = 0;
			int k = 0;
			int size = doc_maestros.size();
			t1 = System.currentTimeMillis();
			t2 = 0;
			System.out.println("System.currentTimeMillis(="
					+ System.currentTimeMillis());
			for (Doc_maestro doc : doc_maestros) {
				String prefijo = "";
				boolean nombreCorto = false;
				listamostar.add(doc);
			}
		}
		// }
		t2 = System.currentTimeMillis();
		/*
		 * System.out.println("---------------------------------------");
		 * System.out.println("TIEMPO EN SEGUNDOS ES:" +(t2-t1)/1000);
		 * System.out.println("---------------------------------------");
		 */
		session.setAttribute("listamostar", listamostar);
		return listamostar;
	}

	public HtmlSelectOneMenu getSelectOneMenuProfesion() {
		return selectOneMenuProfesion;
	}

	public void setSelectOneMenuProfesion(
			HtmlSelectOneMenu selectOneMenuProfesion) {
		this.selectOneMenuProfesion = selectOneMenuProfesion;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public HtmlOutputText getId_str() {
		return id_str;
	}

	public void setId_str(HtmlOutputText id_str) {
		this.id_str = id_str;
	}

	public Tree getArea() {

		// if ( session.getAttribute("areaUsuario")!=null){
		// Tree area = (Tree)session.getAttribute("areaUsuario");
		// }
		return area;
	}

	public void setArea(Tree area) {
		this.area = area;
	}

	public HtmlSelectOneMenu getSelectCargo() {
		return selectCargo;
	}

	public void setSelectCargo(HtmlSelectOneMenu selectCargo) {
		this.selectCargo = selectCargo;
	}

	public HtmlSelectOneMenu getSelectArea() {
		return selectArea;
	}

	public void setSelectArea(HtmlSelectOneMenu selectArea) {
		this.selectArea = selectArea;
	}

	public String getStrError() {
		return strError;
	}

	public void setStrError(String strError) {
		this.strError = strError;
	}

	public String getPasswordOculta() {
		return passwordOculta;
	}

	public void setPasswordOculta(String passwordOculta) {
		this.passwordOculta = passwordOculta;
	}

	public String getPasswordConfirm() {
		/*
		 * if (passwordConfirm!=null && !super.isEmptyOrNull(passwordConfirm)){
		 * session.setAttribute("passwordConfirm",passwordConfirm); }
		 */
		// viene de los selectall de permisoss del multiple select
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {

		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public char getBuscarPor() {

		if (session.getAttribute("query") != null) {
			String buscarPorStr = (String) session.getAttribute("query");
		} else {
			if (session.getAttribute("buscarPor") != null) {
				buscarPor = session.getAttribute("buscarPor").toString()
						.charAt(0);
			} else {
				buscarPor = getNone();
			}

		}

		return buscarPor;
	}

	public void setBuscarPor(char buscarPor) {
		session.setAttribute("buscarPor", buscarPor);
		this.buscarPor = buscarPor;

	}

	public String getBuscar() {

		// BUSCAMOS POR LA PRIMERA LETRA
		if (getDoc_tiposSearchSTR() != null
				&& !super.isEmptyOrNull(getDoc_tiposSearchSTR().toString())) {
			setBuscarPor(getDoc_tiposSearchSTR().toString().charAt(0));
		}

		// SESSION QUE BUSCA OBLIGAR QUE LAS BUSQUEDAS DEBEN SER FILTRADAS
		session.removeAttribute("debeFiltrar");
		StringBuffer sql = new StringBuffer("");
		StringBuffer sqlOrderBy = new StringBuffer("");
		boolean sw1 = false;
		boolean swOrderBy = false;

		// BUSCAMOS EL USUARIO CONECTADO Y SI ES SUPER USUARIO
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}

		// BUSQUEDA POR PALABRAS CLAVES DEL DOCUMENTO
		if (!super.isEmptyOrNull(getStrBuscar())) {
			
				sw1 = true;
				sql.append(" ( ");
				sql.append("  lower(o.busquedakeys) like '%")
						.append(getStrBuscar().trim().toLowerCase().toString())
						.append("%' ");
				if (swOrderBy) {
					sqlOrderBy.append(" , ");
				}
				swOrderBy = true;
				sqlOrderBy.append(" lower(o.nombre) ");
			
			// BUSQUEDA POR NOMBRE DEL DOCUMENTO
			
				if (sw1) {
					sql.append(" or ");
				}
				sw1 = true;
				sql.append("  lower(o.nombre) like '%")
						.append(getStrBuscar().trim().toLowerCase().toString())
						.append("%' ");
				if (swOrderBy) {
					sqlOrderBy.append(" , ");
				}
				swOrderBy = true;
				sqlOrderBy.append(" lower(o.nombre) ");
			
			// BUSQUEDA POR CONSECUTIVO DEL DOCUMENTO

			
				if (sw1) {
					sql.append(" or ");
				}
				if (swOrderBy) {
					sqlOrderBy.append(" , ");
				}
				swOrderBy = true;

				sw1 = true;
				sql.append("  lower(o.consecutivo) like '%")
						.append(getStrBuscar().trim().toLowerCase().toString())
						.append("%' ");
				sqlOrderBy.append(" lower(o.consecutivo)  ");
				
				sql.append(" ) ");
			

		}

		// BUSQUEDA POR TIPO DOCUMENTO DEL DOCUMENTO
		if (!super.isEmptyOrNull(getDoc_tipoId())) {

			if (sw1) {
				sql.append(" and ");

			}
			if (swOrderBy) {
				sqlOrderBy.append(" , ");
			}

			swOrderBy = true;
			sw1 = true;
			sql.append("  o.doc_tipo.codigo =").append(getDoc_tipoId().trim())
					.append("");
			sqlOrderBy.append(" lower(o.doc_tipo.nombre) ");
		}

		// BUSQUEDA POR USUARIO CREADOR DEL DOCUMENTO
		if (!super.isEmptyOrNull(getUsuarioId())) {

			if (sw1) {
				sql.append(" and ");
			}
			sw1 = true;
			if (swOrderBy) {
				sqlOrderBy.append(" , ");
			}
			swOrderBy = true;
			sql.append("  o.usuario_creador.id=").append(getUsuarioId().trim());
			sqlOrderBy.append(" lower(o.usuario_creador.apellido) ");
		}

		// FILTRAMOS POR LA EMPRESA EL DOCUMENTO..--
		if (sw1) {
			sql.append(" and ");
		}
		sw1 = true;
		sql.append("  o.empresa=").append(user_logueado.getEmpresa().getNodo());

		// BUSQUEDA POR ESTADO DEL DOCUMENTO

		boolean swBuscarStadoPorFlujo = false;

		// estos tipos de estados se controla es en el flujo y no en el detalle
		// del documento
		// System.out.println("-------2012 02 05--------------------------------");
		// System.out.println("isSwPorFirmarFlow()=" + isSwPorFirmarFlow());
		// System.out.println("---------------------------------------");
		if (getDoc_estadoId().equals(
				String.valueOf(Utilidades.getEnAprobacion()))
				|| getDoc_estadoId().equals(
						String.valueOf(Utilidades.getRechazado()))
				|| getDoc_estadoId().equals(
						String.valueOf(Utilidades.getRevisado()))
				|| getDoc_estadoId().equals(
						String.valueOf(Utilidades.getCanceladoByDuenio()))
				|| getDoc_estadoId().equals(
						String.valueOf(Utilidades.getEnRevision()))

				|| isSwPorFirmarFlow()) {

			swBuscarStadoPorFlujo = true;
		}

		if (!super.isEmptyOrNull(getDoc_estadoId())) {
			if (sw1) {
				sql.append(" and ");
			}
			sw1 = true;
			if (swOrderBy) {
				sqlOrderBy.append(" , ");
			}
			swOrderBy = true;

			// estos tipos de estados se controla es en el flujo y no en el
			// detalle del documento
			if (swBuscarStadoPorFlujo) {

				sql.append("  f.estado.codigo=").append(getDoc_estadoId());
				sqlOrderBy.append(" lower(f.estado.nombre ) ");
			} else {
				sql.append("  d.doc_estado.codigo=").append(getDoc_estadoId());
				sqlOrderBy.append(" lower(d.doc_estado.nombre ) ");
			}

		}

		// BUSQUEDA FECHA CREADOR DEL DOCUMENTO
		session.removeAttribute("fechaparametro");
		session.removeAttribute("fechaparametro2");
		Calendar actual = Calendar.getInstance();
		// Date fecha = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String fecha_search = null;

		fecha_creado = (Date) session.getAttribute("fecha_creado");
		fecha_creadoFin = (Date) session.getAttribute("fecha_creadoFin");
		if (fecha_creado != null) {
			// fecha_search = sdf.format(fecha_creado);

			if (sw1) {
				sql.append(" and ");
			}
			sw1 = true;
			if (swOrderBy) {
				sqlOrderBy.append(" , ");
			}
			swOrderBy = true;

		}

		// fecha serach viene del parrafo de arriba, inicializada con
		// fecha_creado
		if (fecha_creado != null && fecha_creadoFin == null) {
			fechaDesde = fecha_creado;
			fechaHasta = fecha_creado;
			session.setAttribute("fechaparametro", fechaDesde);
			session.setAttribute("fechaparametro2", fechaHasta);
			// si simula dos, pero es uno solo, se hace porque se introducen dos
			// parametros
			sql.append("  o.fechaCreadoOnly >=:fechaDesde");
			sql.append(" and o.fechaCreadoOnly <=:fechaHasta ");
			sqlOrderBy.append(" o.fechaCreadoOnly ");
		} else if (fecha_creado != null && fecha_creadoFin != null) {
			fechaDesde = fecha_creado;
			fechaHasta = fecha_creadoFin;
			session.setAttribute("fechaparametro", fechaDesde);
			session.setAttribute("fechaparametro2", fechaHasta);

			sql.append("  o.fechaCreadoOnly >=:fechaDesde");
			sql.append(" and o.fechaCreadoOnly <=:fechaHasta ");
			sqlOrderBy.append(" o.fechaCreadoOnly ");
		}

		StringBuffer query = new StringBuffer("");
		query.append("select object(d) from Doc_maestro as o ,Doc_detalle as d ");

		/*
		 * if (fecha_search != null) { query.append(", TablaAuxiliar  as aux");
		 * }
		 */
		// estos tipos de estados se controla ees en el flujo y no en el detalle
		// del documento
		if (swBuscarStadoPorFlujo) {
			query.append(", Flow  as f");
			query.append(" where o.codigo=d.doc_maestro.codigo     ");
			query.append(" and o.status=").append(Utilidades.isActivo());
			query.append(" and f.status=").append(Utilidades.isActivo());
			query.append(" and  o.doc_tipo.codigo <> ").append(
					Utilidades.getFlujoparalelotipodoc());
			query.append(" and f.doc_detalle.codigo=d.codigo");

		} else {
			query.append(" where o.codigo=d.doc_maestro.codigo     ");
			query.append(" and o.status=").append(Utilidades.isActivo());
			query.append(" and  o.doc_tipo.codigo <> ").append(
					Utilidades.getFlujoparalelotipodoc());
		}

		if ("solobuscar".equalsIgnoreCase(parametro)) {
			// sigue con el mismo query y borramos port si acaso el parameytro
			// de session de publicados
			session.removeAttribute("parametro");
		} else {
			if ("publico".equalsIgnoreCase(parametro)) {
				query.append(" and o.publico=" + Utilidades.isActivo()).append(
						" ");
				query.append(" and d.doc_estado.codigo=").append(
						Utilidades.getVigente());
				session.setAttribute("parametro", "publico");
			} else if ("publico".equalsIgnoreCase((String) session
					.getAttribute("parametro"))) {
				query.append(" and o.publico=" + Utilidades.isActivo()).append(
						" ");
				query.append(" and d.doc_estado.codigo=").append(
						Utilidades.getVigente());
			}
		}
		// HttpServletRequest geRequest()
		// ________________________________________________
		if (sw1) {
			query.append(" and ");
			// query.append(sql.toString()).append(" order by ").append(sqlOrderBy.toString());
			query.append(sql.toString()).append("  order by ")
					.append("   d.doc_maestro.codigo  desc , d.codigo desc ");
			session.setAttribute("debeFiltrar", true);
		} else {
			// query.append(" order by   lower(d.doc_estado.nombre),lower(o.doc_tipo.nombre),lower(o.usuario_creador.nombre) ");
			query.append(" order by   d.doc_maestro.codigo  desc, d.codigo desc");

		}
		session.setAttribute("query", query.toString());

		// System.out.println("query.toString()=" + query.toString());
		// System.out.println("stadautordeldocumento=" + stadautordeldocumento);
		// System.out.println("fechaDesde=" + fechaDesde);
		// System.out.println("fechaHasta=" + fechaHasta);
		// System.out.println("stadestadodeldocumento=" +
		// stadestadodeldocumento);
		// System.out.println("stadtipodedocumento=" + stadtipodedocumento);
		return query.toString();
	}

	public List<Doc_detalle> getDoc_detalles() {
		// ____________________________________________________________________________
		// SUPER USUARIO OPERMISOS PARA VER
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}

		// ____________________________________________________________________________

		doc_detalles = new ArrayList<Doc_detalle>();
		List<Doc_detalle> listamostar = new ArrayList<Doc_detalle>();
		listamostar.clear();
		// QUERY QUE SE HACE DINAMICAMENTE
		String query = getBuscar();

		// System.out.println("-----------------simons 20121007--***********************************-----");
//		 System.out.println(query);
//		 System.out.println("------AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA------------------");
		// si lo buscamos por fechas
		if ((session.getAttribute("fechaparametro") != null)
				&& (session.getAttribute("fechaparametro2") != null)) {
			doc_detalles = delegado.queryExecute(query.toString(),
					(Date) session.getAttribute("fechaparametro"),
					(Date) session.getAttribute("fechaparametro2"));
			session.removeAttribute("fechaparametro");
			session.removeAttribute("fechaparametro2");
		} // si no incluimos fechas
		else {
			// SI NO TRAE FECHA.. BUSCAMOS LOS PRIMEROS 15 REGISTROS
			izquierdaBD = session.getAttribute("izquierdaBD") != null ? (Integer) session
					.getAttribute("izquierdaBD") : 0;
			derechaBD = session.getAttribute("derechaBD") != null ? (Integer) session
					.getAttribute("derechaBD") : 0;
			doc_detalles = delegado.queryExecute(query.toString(), derechaBD,
					izquierdaBD);

		}
		boolean debeFiltrar = session.getAttribute("debeFiltrar") != null;

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// **************************************************
		// **** PARA DOCUMENTOS PUBLICOS
		// **************************************************
		String publico = (String) session.getAttribute("parametro");
		if (publico != null && !super.isEmptyOrNull(publico)
				&& "publico".equalsIgnoreCase(publico.toString())) {
			swPublicados = true;

			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados = null;
			String prefijo = "";
			for (Doc_detalle doc : doc_detalles) {
				if (doc.isPublitogrupo()) {
					// HACEMOSUNA REPARACION O PARCH
					// SI EL DOCDETALLE ESTA MARACADO PARA isPublitogrupo, PERO
					// NO HAY GRUPOS CON DETALLE
					// EN RolesOnlyViewDocPublicados, COLOCAMOS EL DOC_DETALLE
					// EN isPublitogrupo FALSO
					RolesOnlyViewDocPublicados rolesOnlyViewDocPublicadosAux = new RolesOnlyViewDocPublicados();
					rolesOnlyViewDocPublicadosAux.setDoc_detalle(doc);
					rolesOnlyViewDocPublicadosAux = delegado
							.findAllRolesOnlyViewDocPublicados(rolesOnlyViewDocPublicadosAux);
					if (rolesOnlyViewDocPublicadosAux == null) {
						doc.setPublitogrupo(false);
					}
					// FIN HACEMOSUNA REPARACION O PARCH
					// SI EL DOCDETALLE ESTA MARACADO PARA isPublitogrupo, PERO
					// NO HAY GRUPOS CON DETALLE
					// EN RolesOnlyViewDocPublicados, COLOCAMOS EL DOC_DETALLE
					// EN isPublitogrupo FALSO

				}

				seguridadTree = super.getSeguridadTree(user_logueado, doc
						.getDoc_maestro().getTree());

				if ((seguridadTree != null && seguridadTree
						.isToViewComentPublic()) || swSuperUsuario) {
					doc.setToViewComentPublic(true);
				}
				boolean nombreCorto = false;
				prefijo = super.getPrefijo(doc.getDoc_maestro().getTree(),
						prefijo, nombreCorto);
				doc.getDoc_maestro().setPrefijo(prefijo);

				// si es el publicador.. puede linkiar..
				if (user_logueado != null
						&& doc.getPublicador() != null
						&& user_logueado.getId() - doc.getPublicador().getId() == 0) {
					doc.setSwIsPublicador(true);
				}

				// si es publicado para un solo grupo
				if (!doc.isSwIsPublicador() && doc.isPublitogrupo()) {
					rolesOnlyViewDocPublicados = new RolesOnlyViewDocPublicados();
					rolesOnlyViewDocPublicados.setDoc_detalle(doc);
					rolesOnlyViewDocPublicados.setUsuario(user_logueado);
					if (!delegado
							.usuarioRolesOnlyViewDocPublicados(rolesOnlyViewDocPublicados)) {
						continue;
					}

				}

				// ENCONTRAMOS EL AREA DEL DOCUMENTO
				Tree area = null;
				area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(doc
						.getDoc_maestro().getTree(), Utilidades.getNodoArea());
				String areaStr = doc.getAreaDocumentos() != null ? doc
						.getAreaDocumentos().getNombre() : "";

				String tipoFormato = doc.getDoc_maestro().getDoc_tipo()
						.getAbreviatura() != null ? doc.getDoc_maestro()
						.getDoc_tipo().getAbreviatura() : doc.getDoc_maestro()
						.getDoc_tipo().getNombre();
				// COLOCAMOS EL AREA DEL DOCUMENTO + EL CONSECUTIVO
				doc.getDoc_maestro().setArea(areaStr + "-" + tipoFormato + "-");
				doc.getDoc_maestro().setConsecutivo(
						doc.getDoc_maestro().getConsecutivo());

				doc.setIcono(super.obtenIconoDoc(doc.getNameFile()));
				if (doc.getIcono() != null) {
					if (Utilidades.obtenerXdelIcono(doc.getIcono())) {
						doc.setSwAttachment(true);
					}
				}

				doc.setSwHabilitadoImprimir(habilitadoImprimir(doc,
						user_logueado));
				doc.setSwEstaInFlowToImprimir(estaInFlowToImprimir(doc,
						user_logueado));

				doc.setSwViewDoc(true);

				// estos tipos de estados se controla es en el flujo y no en el
				// detalle
				// del documento
				if (getDoc_estadoId().equals(
						String.valueOf(Utilidades.getEnAprobacion()))
						|| getDoc_estadoId().equals(
								String.valueOf(Utilidades.getRechazado()))
						|| getDoc_estadoId().equals(
								String.valueOf(Utilidades.getRevisado()))
						|| getDoc_estadoId().equals(
								String.valueOf(Utilidades
										.getCanceladoByDuenio()))
						|| getDoc_estadoId().equals(
								String.valueOf(Utilidades.getEnRevision()))) {
					Doc_estado docEstado = new Doc_estado();
					docEstado.setCodigo(new Long(getDoc_estadoId()));
					docEstado = delegado.findDocEstado(docEstado);
					try {
						docEstado.setNombre(messages.getString(docEstado
								.getNombre()));
					} catch (Exception e) {
						// TODO: handle exception
					}

					doc.setDoc_estado(docEstado);
				}

				if (doc.isSwFechaPublicoExpiro()) {
					// verificamos que se le mando un mensaje al publicador via
					// mail
					// y se marca una casilla como enviado en la base de datos
					// publiMailexpiro
					if (!doc.isPubliMailexpiro()) {
						// mandamos mails y swicheamos el campo
						delegado.notificarPorMailPublicoExpiro(doc);
					}

					// en la vista mandaron a pedir todos los expirados
					if (isSwPublicadosExpirados()) {
						listamostar.add(doc);
					}
				} else if (!isSwPublicadosExpirados()) {
					listamostar.add(doc);

				}

			}
			swMostrarDesplazamiento = true;

		} else {
			// PARA DOCUMENTOS DE BUSQUEDA

			swMostrarDesplazamiento = true;
			long k = 0;
			HashMap nocRepite = new HashMap();

			for (Doc_detalle doc : doc_detalles) {
				// aqui obligamos, que muestre una sola version del doc
				// detalle en docmaestro
				if (nocRepite.containsKey(doc.getDoc_maestro().getCodigo())) {
					continue;
				}
				if (!nocRepite.containsKey(doc.getDoc_maestro().getCodigo())) {
					nocRepite.put(doc.getDoc_maestro().getCodigo(), doc
							.getDoc_maestro().getCodigo());
					k++;
					// inicializamos, mas adelante comprobamos si uno de los
					// participantes del flujo es etste usuario y lo colocamos
					// en
					// true para podre ver el vinculo workflow en busqueda
					user_logueado.setSwEstaInFlow(false);
					seguridadTree = super.getSeguridadTree(user_logueado, doc
							.getDoc_maestro().getTree());

					if ((seguridadTree != null && seguridadTree.isToView())
							|| swSuperUsuario) {
						doc.setSwViewDoc(true);
					} else {
						doc.setSwViewDoc(false);
					}
					String prefijo = "";
					boolean nombreCorto = false;
//					prefijo = super.getPrefijo(doc.getDoc_maestro().getTree(),
//							prefijo, nombreCorto);
					doc.getDoc_maestro().setPrefijo(prefijo);

					// ENCONTRAMOS EL AREA DEL DOCUMENTO
					Tree area = null;
					area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(doc
							.getDoc_maestro().getTree(), Utilidades
							.getNodoArea());
					if (area != null) {
						// COLOCAMOS EL AREA DEL DOCUMENTO + EL CONSECUTIVO
						String areaStr = doc.getAreaDocumentos() != null ? doc
								.getAreaDocumentos().getNombre() : "";
						String tipoFormato = doc.getDoc_maestro().getDoc_tipo()
								.getAbreviatura() != null ? doc
								.getDoc_maestro().getDoc_tipo()
								.getAbreviatura() : doc.getDoc_maestro()
								.getDoc_tipo().getNombre();

						doc.getDoc_maestro().setArea(
								areaStr + "-" + tipoFormato + "-");
						doc.getDoc_maestro().setConsecutivo(
								doc.getDoc_maestro().getConsecutivo());
					}

					// obtenemos el icono---------
					doc.setIcono("");

					if (doc != null && doc.getNameFile() != null) {
						doc.setIcono(super.obtenIconoDoc(doc.getNameFile()));
						if (doc.getIcono() != null) {
							if (Utilidades.obtenerXdelIcono(doc.getIcono())) {
								doc.setSwAttachment(true);
							}
						}

					}

					// colocamos la fecha formateada y la que se debe
					// mostrar
					if (doc != null && doc.getDoc_maestro() != null
							&& doc.getDoc_maestro().getFecha_creado() != null) {
						doc.getDoc_maestro().setFecha_mostrar(
								Utilidades.sdfShow.format(doc.getDoc_maestro()
										.getFecha_creado()));
					}

					// estos tipos de estados se controla es en el flujo y no en
					// el detalle
					// del documento
					// este getDoc_estadoId() vene del parametro buscar
					if (getDoc_estadoId().equals(
							String.valueOf(Utilidades.getEnAprobacion()))
							|| getDoc_estadoId().equals(
									String.valueOf(Utilidades.getRechazado()))
							|| getDoc_estadoId().equals(
									String.valueOf(Utilidades.getRevisado()))
							|| getDoc_estadoId().equals(
									String.valueOf(Utilidades
											.getCanceladoByDuenio()))
							|| getDoc_estadoId().equals(
									String.valueOf(Utilidades.getEnRevision()))

					) {
						Doc_estado docEstado = new Doc_estado();
						docEstado.setCodigo(new Long(getDoc_estadoId()));
						docEstado = delegado.findDocEstado(docEstado);

						try {
							docEstado.setNombre(messages.getString(docEstado
									.getNombre()));

							// colocamos user_logueado como referencia para
							// actualizar su atributo staenflow
							// user_logueado.getSwEstaInFlow() lo obtenemos de
							// aquie, de este metodo
							Flow f = buscaFlowDocDetalle(doc, user_logueado);

							if (f != null && f.getCodigo() > 0) {
								if (user_logueado.getSwEstaInFlow()) {
									doc.setSwHayFlow(true);
								} else {
									doc.setSwHayFlow(false);
								}
							}

							if (isSwPorFirmarFlow()) {
								// BUSQUEDA POR USUARIO EN FLOW
								if (!super.isEmptyOrNull(getUsuarioFlowId())) {

									Usuario user = new Usuario();
									user.setId(new Long(getUsuarioFlowId()));
									user = delegado.find_Usuario(user.getId());
									session.setAttribute(
											"reporteBusquedaParticipante", user);
								}

								// DE AQUI SE REALIZA PARA EL REPORTE EN PDF
								// SI ENCONTRAMOS EN EL CONSTRUCTOR CON
								// PARAMETRO FLOW UN SUBREPORTE EN SESSION
								// listaSubFlowReporte, ES PORQUE EXISTE FLOW
								ClienteFlowsResponse clienteFlowsResponse = new ClienteFlowsResponse(
										f);
								if (session.getAttribute("listaSubFlowReporte") != null) {
									List<SubFlowReporte> listaSubFlowReporte = (List<SubFlowReporte>) session
											.getAttribute("listaSubFlowReporte");
									session.removeAttribute("listaSubFlowReporte");
									if (listaSubFlowReporte != null
											&& !listaSubFlowReporte.isEmpty()
											&& listaSubFlowReporte.size() > 0) {

									} else {
										doc.setSwHayFlow(false);
									}
								}
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

						doc.setDoc_estado(docEstado);

					}
					// el doc_detalle, mantiene un estado diferente a su flujo,
					// pero si en el
					// flujo esta en revision o aprobacion
					// , cambiaremos su estado para mostarrlo en revision o
					// aprobacion del flow y no del estado de doc_detalle que se
					// mantiene inerte
					else {
						Doc_estado enaprobacion = new Doc_estado();
						enaprobacion.setCodigo(Utilidades.getEnAprobacion());
						List<Flow_Participantes> lista = delegado
								.findByFlowParticipantesByRevOrAprob(doc,
										enaprobacion);
						if (lista != null && lista.size() > 0) {
							Flow f = lista.get(0).getFlow();
							// verificamos si este usuario esta en el flujo para
							// prenderlo en el icono de busqueda de serach
							// buscados
							// el sw de usuario lo sacamos como referencia
							if (user_logueado != null && f != null) {
								try {
									if (user_logueado.getId()
											- f.getDuenio().getId() == 0) {
										user_logueado.setSwEstaInFlow(true);

									} else {
										for (Flow_Participantes fp : lista) {
											if (user_logueado.getId()
													- fp.getParticipante()
															.getId() == 0) {
												user_logueado
														.setSwEstaInFlow(true);
												break;

											}
										}
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}

							enaprobacion = delegado.findDocEstado(enaprobacion);
							doc.setDoc_estado(enaprobacion);
							if (user_logueado.getSwEstaInFlow()) {
								doc.setSwHayFlow(true);
							} else {
								doc.setSwHayFlow(false);
							}

						} else {

							// aqui buscamos historicos del flow
							Doc_estado enrevision = new Doc_estado();
							enrevision.setCodigo(Utilidades.getEnRevision());
							lista = delegado
									.findByFlowParticipantesByRevOrAprob(doc,
											enrevision);
							if (lista != null && lista.size() > 0) {
								Flow f = lista.get(0).getFlow();
								// verificamos si este user_logueado esta en el
								// flujo para prenderlo en el icono de busqueda
								// de serach buscados
								// el sw de user_logueado lo sacamos como
								// referencia
								if (user_logueado != null && f != null) {
									try {
										if (user_logueado.getId()
												- f.getDuenio().getId() == 0) {
											user_logueado.setSwEstaInFlow(true);
										} else {
											for (Flow_Participantes fp : lista) {
												if (user_logueado.getId()
														- fp.getParticipante()
																.getId() == 0) {
													user_logueado
															.setSwEstaInFlow(true);
													break;

												}
											}
										}
									} catch (Exception e) {
										// TODO: handle exception
									}
								}

								enrevision = delegado.findDocEstado(enrevision);
								// try {
								// enrevision.setNombre(messages
								// .getString(enrevision.getNombre()));
								// } catch (Exception e) {
								// // TODO: handle exception
								// }
								doc.setDoc_estado(enrevision);
								if (user_logueado.getSwEstaInFlow()) {
									doc.setSwHayFlow(true);
								} else {
									doc.setSwHayFlow(false);
								}

							}
						}

					}

					// //SI ESTA EN FLOW EN LA TABLA DE FLOWS Y FLOWS
					// PARTICIPANTES, CHEQUEAMOS EN DETALLES
					if (doc.isSwHayFlow()) {
						// CHEQUEAMOS DE ULTIMO SI ESTA EN FLOW Y SI SON DE
						// REVISION O APŔOBACION en el doc_detalle, si por
						// casualida el doc esta en flow, pero es un docdetalle
						// con historico, no se muestra..
						// solo se va a mostrar si esta en revision o aprobacion
						// el doc_detalle como tal
						if ((doc.getDoc_estado().getCodigo()
								.equals(Utilidades.getEnAprobacion()) || doc
								.getDoc_estado().getCodigo()
								.equals(Utilidades.getEnRevision()))) {
							doc.setSwHayFlow(true);
						} else {
							doc.setSwHayFlow(false);
						}
					}

					// aqui revisamos si tiene historicos de flujo
					doc.setSwHayHistoricoFlow(false);
					if (doc != null && doc.getDoc_maestro() != null) {
						List flowsHistorico = delegado
								.findAll_FlowsHistorico(doc.getDoc_maestro());
						if (flowsHistorico != null && flowsHistorico.size() > 0
								&& !flowsHistorico.isEmpty()) {
							doc.setSwHayHistoricoFlow(true);
						}
					}
					// el nombre de doc estado lo sacamos de un properties para
					// mostrar en la vista
					try {
						Doc_estado docEstado = doc.getDoc_estado();
						docEstado.setNombre(messages.getString(docEstado
								.getNombre()));
						doc.setDoc_estado(docEstado);
					} catch (Exception e) {
						// TODO: handle exception
					}

					// comprobamos listas en flow paralelo y demas para
					// asegurar completamente que el usuario no sta en flow
					if (!user_logueado.getSwEstaInFlow()) {
						List listaInFlow = delegado
								.findAllFlowParticipantesPendientesDoc_Detalle(
										user_logueado, doc);
						if (listaInFlow != null && listaInFlow.size() > 0
								&& !listaInFlow.isEmpty()) {
							doc.setSwHayFlow(true);
							user_logueado.setSwEstaInFlow(true);
					}

					}

				
					listamostar.add(doc);

				}

			}

		}

		session.setAttribute("listamostar", listamostar);
		return listamostar;
	}

	public void versionIdReporte(ActionEvent event) {
		// inicializamos todas las variables

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("reporteId2");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			Doc_detalle doc = new Doc_detalle();
			doc.setCodigo(id);
			doc = delegado.findDocDetalle(doc);
			Flow f = buscaFlowDocDetalle(doc, null);

			if (f != null && f.getCodigo() > 0) {

				doc.setSwHayFlow(true);
				ClienteFlowsResponse clienteFlowsResponse = new ClienteFlowsResponse(
						f);
			}

		}
	}

	public Flow buscaFlowDocDetalle(Doc_detalle doc_detalle, Usuario usuario) {
		Flow f = null;
		List<Flow_Participantes> flowsParticipantes = delegado
				.findByFlowParticipantes(doc_detalle);
		if (flowsParticipantes != null && !flowsParticipantes.isEmpty()
				&& flowsParticipantes.size() > 0) {
			// obtenemos el flow
			f = flowsParticipantes.get(0).getFlow();
			// verificamos si este usuario esta en el flujo para prenderlo en el
			// icono de busqueda de serach buscados
			// el sw de usuario lo sacamos como referencia
			if (usuario != null && f != null) {
				try {
					if (usuario.getId() - f.getDuenio().getId() == 0) {
						usuario.setSwEstaInFlow(true);
					} else {
						for (Flow_Participantes fp : flowsParticipantes) {
							if (usuario.getId() - fp.getParticipante().getId() == 0) {
								usuario.setSwEstaInFlow(true);
								break;

							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}

		return f;
	}

	public String publicadoComentario() {

		return "publicadoComentario";
	}

	public String verPublicosComentarios() {

		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();

		publicadosUsuComent = new PublicadosUsuComent();
		publicadosUsuComent.setDoc_detalle(doc_detalle);
		List<PublicadosUsuComent> publicadosUsuComentLst = delegado
				.findAllPublicadosVersionesUsuComentLst(publicadosUsuComent);

		List<PublicadosUsuComent> publicadosUsuComentLst2 = new ArrayList<PublicadosUsuComent>();
		for (PublicadosUsuComent p : publicadosUsuComentLst) {
			p.getDoc_detalle().setIcono("");
			if (p.getDoc_detalle() != null
					&& p.getDoc_detalle().getNameFile() != null) {
				p.getDoc_detalle().setIcono(
						super.obtenIconoDoc(p.getDoc_detalle().getNameFile()));
			}

			publicadosUsuComentLst2.add(p);

		}

		session.setAttribute("publicadosUsuComentLst", publicadosUsuComentLst2);

		return "verPublicosComentarios";
	}

	public void setDoc_maestros(List<Doc_maestro> doc_maestros) {
		this.doc_maestros = doc_maestros;
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

	public Doc_detalle getDoc_detalle_aux() {
		return doc_detalle_aux;
	}

	public void setDoc_detalle_aux(Doc_detalle doc_detalle_aux) {
		this.doc_detalle_aux = doc_detalle_aux;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public char getSearchNombreDoc() {

		searchNombreDoc = Utilidades.getSearchNombreDoc();

		setBuscarPor(searchNombreDoc);
		return searchNombreDoc;
	}

	public void setSearchNombreDoc(char searchNombreDoc) {
		searchNombreDoc = Utilidades.getSearchNombreDoc();
		setBuscarPor(searchNombreDoc);

		this.searchNombreDoc = searchNombreDoc;
	}

	public char getSearchConsecutivoDoc() {

		searchConsecutivoDoc = Utilidades.getSearchConsecutivoDoc();

		setBuscarPor(searchConsecutivoDoc);
		return searchConsecutivoDoc;
	}

	public void setSearchConsecutivoDoc(char searchConsecutivoDoc) {
		searchConsecutivoDoc = Utilidades.getSearchConsecutivoDoc();
		setBuscarPor(searchConsecutivoDoc);
		this.searchConsecutivoDoc = searchConsecutivoDoc;
	}

	public Doc_tipo getDoc_tipo() {
		if (doc_tipo == null) {
			doc_tipo = new Doc_tipo();
		}

		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public Doc_estado getDoc_estado() {

		return doc_estado;
	}

	public void setDoc_estado(Doc_estado doc_estado) {
		this.doc_estado = doc_estado;
	}

	public String getSearchUsuario() {
		// validamos por ajax a los combos para hacer busquedas
		if (usuario == null) {
			usuario = new Usuario();
		} else {
			if (usuario.getId() != null && usuario.getId() != -1) {
				searchUsuario = usuario.toString();
			}
		}
		return searchUsuario;
	}

	public void setSearchUsuario(String searchUsuario) {
		this.searchUsuario = searchUsuario;
	}

	public String getSearchDoc_edo() {
		searchDoc_edo = doc_estado.getNombre();

		return searchDoc_edo;
	}

	public void setSearchDoc_edo(String searchDoc_edo) {
		this.searchDoc_edo = searchDoc_edo;
	}

	public String getSearchDoc_tipo() {
		searchDoc_tipo = doc_tipo.getNombre();
		return searchDoc_tipo;
	}

	public void setSearchDoc_tipo(String searchDoc_tipo) {

		this.searchDoc_tipo = searchDoc_tipo;
	}

	public char getNone() {

		none = Utilidades.getNone();

		setBuscarPor(none);
		return none;
	}

	public void setNone(char none) {
		none = Utilidades.getNone();
		setBuscarPor(none);

		this.none = none;
	}

	public List getUsuarios() {

		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		usuarios = new ArrayList();
		usuarios.clear();
		usuarioId = "";
		usuarioSt = "";
		SelectItem items = new SelectItem(usuarioId, usuarioSt);
		usuarios.add(items);

		if (user_logueado != null) {
			List<Usuario> users = delegado.findAll_Usuario(user_logueado);
			for (Usuario user : users) {
				usuarioId = user.getId() + "";
				usuarioSt = user.toString();
				items = new SelectItem(usuarioId, usuarioSt);
				usuarios.add(items);
			}
			usuarioId = "";
			usuarioSt = "";
		}
		return usuarios;

	}

	public List getDoc_tipos() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		doc_tipos = new ArrayList();
		doc_tipos.clear();
		doc_tipoId = "";
		doc_tipoSt = "";
		SelectItem items = new SelectItem(doc_tipoId, doc_tipoSt);
		doc_tipos.add(items);
		if (user_logueado != null) {
			List<Doc_tipo> doc_tps = delegado.findAllDoc_tipos(user_logueado);
			for (Doc_tipo doc_tp : doc_tps) {
				doc_tipoId = doc_tp.getCodigo() + "";
				doc_tipoSt = doc_tp.getNombre();
				items = new SelectItem(doc_tipoId, doc_tipoSt);
				doc_tipos.add(items);
			}
			doc_tipoId = "";
			doc_tipoSt = "";
		}
		return doc_tipos;
	}

	public List getDoc_tiposRichFaces() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		doc_tipos = new ArrayList();
		doc_tipos.clear();

		if (user_logueado != null) {
			List<Doc_tipo> doc_tps = delegado.findAllDoc_tipos(user_logueado);
			for (Doc_tipo doc_tp : doc_tps) {
				doc_tipos.add(doc_tp);
			}
		}
		return doc_tipos;
	}
	
	public List getDoc_estados() {
		List<Doc_estado> doc_es = delegado.findAllDoc_estadosInSearch();
		doc_estados = new ArrayList();
		doc_estados.clear();
		doc_estadoId = "";
		doc_estadoSt = "";
		SelectItem items = new SelectItem(doc_estadoId, doc_estadoSt);
		doc_estados.add(items);
		for (Doc_estado doc_e : doc_es) {
			doc_estadoId = doc_e.getCodigo() + "";
			doc_estadoSt = doc_e.getNombre();
			items = new SelectItem(doc_estadoId,
					messages.getString(doc_estadoSt));
			doc_estados.add(items);
		}
		doc_estadoId = "";
		doc_estadoSt = "";
		return doc_estados;
	}

	public void setUsuarios(List usuarios) {
		this.usuarios = usuarios;
	}

	public String getUsuarioSt() {
		return usuarioSt;
	}

	public void setUsuarioSt(String usuarioSt) {
		this.usuarioSt = usuarioSt;
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

	public void setDoc_tipos(List doc_tipos) {
		this.doc_tipos = doc_tipos;
	}

	public String getDoc_tipoSt() {
		return doc_tipoSt;
	}

	public void setDoc_tipoSt(String doc_tipoSt) {
		this.doc_tipoSt = doc_tipoSt;
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

	public void setDoc_estados(List doc_estados) {
		this.doc_estados = doc_estados;
	}

	public String getDoc_estadoSt() {
		return doc_estadoSt;
	}

	public void setDoc_estadoSt(String doc_estadoSt) {
		this.doc_estadoSt = doc_estadoSt;
	}

	public String getDoc_estadoId() {
		doc_estadoId = session.getAttribute("doc_estadoId") != null ? (String) session
				.getAttribute("doc_estadoId") : "";

		return doc_estadoId;
	}

	public void setDoc_estadoId(String doc_estadoId) {
		session.setAttribute("doc_estadoId", doc_estadoId);
		setSwPorFirmarFlowView(false);
		if (doc_estadoId.equals(String.valueOf(Utilidades.getEnAprobacion()))
				|| doc_estadoId.equals(String.valueOf(Utilidades
						.getEnRevision()))) {
			setSwPorFirmarFlowView(true);
		}

		this.doc_estadoId = doc_estadoId;
	}

	public Date getFecha_creado() {
		fecha_creado = session.getAttribute("fecha_creado") != null ? (Date) session
				.getAttribute("fecha_creado") : null;
		return fecha_creado;
	}

	public void setFecha_creado(Date fecha_creado) {
		session.setAttribute("fecha_creado", fecha_creado);
		this.fecha_creado = fecha_creado;
	}

	public Date getFecha_creadoFin() {
		fecha_creadoFin = session.getAttribute("fecha_creadoFin") != null ? (Date) session
				.getAttribute("fecha_creadoFin") : null;
		return fecha_creadoFin;
	}

	public void setFecha_creadoFin(Date fecha_creadoFin) {
		session.setAttribute("fecha_creadoFin", fecha_creadoFin);
		this.fecha_creadoFin = fecha_creadoFin;
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	public void setDoc_detalles(List<Doc_detalle> doc_detalles) {
		this.doc_detalles = doc_detalles;
	}

	public List getDoc_tiposSearch() {
		doc_tiposSearch = new ArrayList();
		doc_tiposSearch.clear();
//		doc_tipoId = "";
//		doc_tipoSt = "";
		SelectItem items = new SelectItem("c", messages.getString("doc_consecutivotab"));
		doc_tiposSearch.add(items);
		items = new SelectItem("0", messages.getString("doc_keystab0"));
		doc_tiposSearch.add(items);
		items = new SelectItem("n", messages.getString("doc_nombre"));
		doc_tiposSearch.add(items);
//		items = new SelectItem("c", messages.getString("doc_consecutivotab"));
//		doc_tiposSearch.add(items);
		return doc_tiposSearch;

	}

	public void setDoc_tiposSearch(List doc_tiposSearch) {
		this.doc_tiposSearch = doc_tiposSearch;
	}

	public String getDoc_tiposSearchSTR() {
		return doc_tiposSearchSTR;
	}

	public void setDoc_tiposSearchSTR(String doc_tiposSearchSTR) {
		this.doc_tiposSearchSTR = doc_tiposSearchSTR;
	}

	public int getIzquierdaBD() {
		return izquierdaBD;
	}

	public void setIzquierdaBD(int izquierdaBD) {
		this.izquierdaBD = izquierdaBD;
	}

	public int getDerechaBD() {
		return derechaBD;
	}

	public void setDerechaBD(int derechaBD) {
		this.derechaBD = derechaBD;
	}

	public boolean isSwMostrarDesplazamiento() {
		return swMostrarDesplazamiento;
	}

	public void setSwMostrarDesplazamiento(boolean swMostrarDesplazamiento) {
		this.swMostrarDesplazamiento = swMostrarDesplazamiento;
	}

	public int getCuantosResultsetSize() {
		return cuantosResultsetSize;
	}

	public void setCuantosResultsetSize(int cuantosResultsetSize) {
		this.cuantosResultsetSize = cuantosResultsetSize;
	}

	public TablaAuxiliar getTablaAuxiliar() {
		return tablaAuxiliar;
	}

	public void setTablaAuxiliar(TablaAuxiliar tablaAuxiliar) {
		this.tablaAuxiliar = tablaAuxiliar;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getStadautordeldocumento() {
		usuarioId = session.getAttribute("usuarioId") != null ? (String) session
				.getAttribute("usuarioId") : "";
		stadautordeldocumento = usuarioId;
		return stadautordeldocumento;
	}

	public void setStadautordeldocumento(String stadautordeldocumento) {
		this.stadautordeldocumento = stadautordeldocumento;
	}

	public String getStadestadodeldocumento() {
		doc_estadoId = session.getAttribute("doc_estadoId") != null ? (String) session
				.getAttribute("doc_estadoId") : "";
		// para estadistica...
		// se coloco aqui porque en el metodo buscar... no agarraba el valor
		stadestadodeldocumento = doc_estadoId;
		// fin para estadistica...
		return stadestadodeldocumento;
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

	public void verDocId(ActionEvent event) {
		try {

			UIParameter component = null;
			component = (UIParameter) event.getComponent().findComponent(
					"editIdx2_1");

			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				System.out.println("id=" + id);
				if (doc_detalle == null) {
					doc_detalle = new Doc_detalle();
				}
				// buscamos el role para editar
				if (id >= 0) {
					doc_detalle.setCodigo(new Long(id));

					doc_detalle = delegado.findDetalle(doc_detalle);
				}
				if (doc_detalle != null) {
					doc_maestro = doc_detalle.getDoc_maestro();
					session.setAttribute("doc_detalle", doc_detalle);
					session.setAttribute("doc_maestro", doc_maestro);
				}
			}
		} catch (Exception e) {
			redirect();
		}
	}

	public void setStadestadodeldocumento(String stadestadodeldocumento) {
		this.stadestadodeldocumento = stadestadodeldocumento;
	}

	public String getStadtipodedocumento() {
		doc_tipoId = session.getAttribute("doc_tipoId") != null ? (String) session
				.getAttribute("doc_tipoId") : "";
		stadtipodedocumento = doc_tipoId;
		return stadtipodedocumento;
	}

	public void setStadtipodedocumento(String stadtipodedocumento) {
		this.stadtipodedocumento = stadtipodedocumento;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public boolean isSwPublicados() {
		return swPublicados;
	}

	public void setSwPublicados(boolean swPublicados) {
		this.swPublicados = swPublicados;
	}

	public boolean isSwPublicadosExpirados() {
		swPublicadosExpirados = session.getAttribute("swPublicadosExpirados") != null ? (Boolean) session
				.getAttribute("swPublicadosExpirados") : swPublicadosExpirados;
		return swPublicadosExpirados;
	}

	public void setSwPublicadosExpirados(boolean swPublicadosExpirados) {
		session.setAttribute("swPublicadosExpirados", swPublicadosExpirados);
		this.swPublicadosExpirados = swPublicadosExpirados;
	}

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public PublicadosUsuComent getPublicadosUsuComent() {
		return publicadosUsuComent;
	}

	public void setPublicadosUsuComent(PublicadosUsuComent publicadosUsuComent) {
		this.publicadosUsuComent = publicadosUsuComent;
	}

	public boolean isSwPorFirmarFlow() {
		swPorFirmarFlow = session.getAttribute("swPorFirmarFlow") != null ? (Boolean) session
				.getAttribute("swPorFirmarFlow") : false;
		return swPorFirmarFlow;
	}

	public void setSwPorFirmarFlow(boolean swPorFirmarFlow) {
		session.setAttribute("swPorFirmarFlow", swPorFirmarFlow);
		this.swPorFirmarFlow = swPorFirmarFlow;
	}

	/*
	 * 
	 * 
	 * 
	 * public void setDoc_tipoId(String doc_tipoId) {
	 * 
	 * }
	 */

	
	public String mostrarDocEnIframe(){
		 
		 
		return "";
	}
	public void mostrarDocEnIframeEvent(ActionEvent event) {
		
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("docDetalleId");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			doc_detalle= new Doc_detalle();
			doc_detalle.setCodigo(id);
			doc_detalle = delegado.findDocDetalle(doc_detalle);
			if (session.getAttribute("swMostraDocIframe")!=null){
				swMostraDocIframe=(Boolean)session.getAttribute("swMostraDocIframe");
					session.setAttribute("swMostraDocIframe",!swMostraDocIframe);
					swMostraDocIframe=(Boolean)session.getAttribute("swMostraDocIframe");	
				
			}else{
				swMostraDocIframe=true;
				session.setAttribute("swMostraDocIframe",swMostraDocIframe);
			}
		 
			if (doc_detalle!=null && doc_detalle.getCodigo()!=null){
				if (swMostraDocIframe){

				 
					session.setAttribute("onlyView", "onlyView");
					session.setAttribute("objeto", doc_detalle);
				}	
			}else{
				
//				FacesMessage msg = new FacesMessage("Vuelva a intentarlo por favor",  " Es la primera vez.");
//				FacesContext.getCurrentInstance().addMessage(null, msg);
				swMostraDocIframe = false;
				session.setAttribute("swMostraDocIframe", swMostraDocIframe);
			}

		}
		
		 
				
	}
	public boolean isSwMostraDocIframe() {
		
		return swMostraDocIframe;
	}

	public void setSwMostraDocIframe(boolean swMostraDocIframe) {
		this.swMostraDocIframe = swMostraDocIframe;
	}

	
	public String viewHistoricoSearchDocs() {
		try {
			session.setAttribute("parametro", "listarDocumentoSearch");
			session.setAttribute("doc_detalle", doc_detalle);
			session.setAttribute("doc_maestro", doc_detalle.getDoc_maestro());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "listarFlowsHistorico";
	}

	public boolean isSwPorFirmarFlowView() {
		swPorFirmarFlowView = session.getAttribute("swPorFirmarFlowView") != null ? (Boolean) session
				.getAttribute("swPorFirmarFlowView") : false;
		return swPorFirmarFlowView;
	}

	public void setSwPorFirmarFlowView(boolean swPorFirmarFlowView) {
		session.setAttribute("swPorFirmarFlowView", swPorFirmarFlowView);
		this.swPorFirmarFlowView = swPorFirmarFlowView;
	}

	public String getUsuarioFlowId() {
		
		usuarioFlowId = session.getAttribute("usuarioFlowId") != null ? (String) session
				.getAttribute("usuarioFlowId") : "";
		return usuarioFlowId;

	}

	public void setUsuarioFlowId(String usuarioFlowId) {
		session.setAttribute("usuarioFlowId", usuarioFlowId);
		this.usuarioFlowId = usuarioFlowId;
	}

}
