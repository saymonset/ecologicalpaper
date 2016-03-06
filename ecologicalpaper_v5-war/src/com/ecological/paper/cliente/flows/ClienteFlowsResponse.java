/*
 * ClienteFlowsResponse.java
 *
 * Created on August 7, 2007, 5:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.flows;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.subversion.SvnRutasRelativasFiles;
import com.ecological.paper.subversion.SvnUpload;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.reportes.SubFlowReporte;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import com.util.file.Archivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.primefaces.event.FileUploadEvent;

/**
 * 
 * @author simon
 */
public class ClienteFlowsResponse extends ContextSessionRequest {

	private boolean swAttachment;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	private DatosCombo datosCombo = new DatosCombo();
	private HttpSession session = super.getSession();
	private HttpServletRequest request = super.geRequest();
	private Tree tree;
	// private HtmlInputText inputTextNombre;
	// private HtmlInputText inputTextDescripcion;
	private Usuario usuario;
	private HtmlSelectOneMenu selectItemUsuario;
	private String selectPermisologiaRolUser;
	private Object[] selectedUsuarios;
	private List visibleUsersFlows;
	private List visibleRoleFlows;
	private Object[] selectedRoles;
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	// private static String flowBandera;
	private boolean swPrincipalVisible;
	private boolean swUserVisible;
	private boolean swRoleVisible;
	private boolean swMostrarCatalogo;
	private Flow flow;
	private boolean flowAprobacion;
	private boolean tipoFlow;
	private boolean verTabs;
	private boolean swViewComentario;
	private String nomCompletoFlowDoc;
	private String prefix;
	private String cualquierComentario;
	private String firmarComentario;
	private List flowWithParticipantes;
	private Doc_estado firma;
	private List tipoFirma;
	private Usuario user_logueado;
	private Usuario usuarioFirmaSecuencial;
	private boolean swPuedeBorrarHistTree;
	private String icono;
	private Usuario duenioFlow;
	private boolean swIsDuenio;
	private boolean swFueAprobado;
	// private List mandarMailUsuarios;
	private FileUploadEvent _upFile;
	private ArrayList<FileUploadEvent> filesPrimeFaces = new ArrayList<FileUploadEvent>();
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private Object treeData;
	private boolean swPuedeRealizarSubFlow;
	private List<SubFlowReporte> listaSubFlowReporte = new ArrayList<SubFlowReporte>();
	private File _upFileSVN = null;
	private List<SvnRutasRelativasFiles> rutasRelativaSVN = null;
	private boolean swAttachmentSVN;
	private String automaticoCargaSvn = "1";
	// SEGURIDAD,EMULANDO PARA TREE, PEROI EÑL PERMISO ESTA EN PERMISOLOGIA MENU
	// PARA ESTE CASO
	private Seguridad seguridadTree = new Seguridad();
	// PER,MMISO PARA TREE
	private Seguridad seguridadTreeArbol = new Seguridad();
	private boolean toModFlow;
	private boolean toDoFlow;
	private boolean swSuperUsuario;
	private Tree treeNodoActual;
	private Usuario usuarioRemplazo;
	private String tipoDeCambio = "0";
	private Usuario participanteOLd;
	private Flow_Participantes flow_ParticipantesCambio;
	private List<Flow_Participantes> participantesFlowsDetalle;
	private Flow_Participantes flow_Participante;
	private boolean swPermViewDoc;
	private boolean swAttachmentDetalle;
	private long codigoDetalle;
	private boolean swMostraDocIframe;
	private boolean swMostraDocIframe2;

	public void handleFileUpload(FileUploadEvent event) {

		filesPrimeFaces = session.getAttribute("filesPrimeFaces") != null ? (ArrayList<FileUploadEvent>) session
				.getAttribute("filesPrimeFaces")
				: new ArrayList<FileUploadEvent>();
		filesPrimeFaces.add(event);
		// con este metodo, corremos la lista de mayor A menor para agarra
		// siempre el ultimo achivo que se escojio
		Collections.reverse(filesPrimeFaces);
		session.setAttribute("filesPrimeFaces", filesPrimeFaces);
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	/** Creates a new instance of ClienteFlowsResponse */
	public ClienteFlowsResponse() {

		session = super.getSession();

		// INICLIAZAMOS LOS DOCUMENTOS DETALLES PARA APLICACIONDETALLE
		session.removeAttribute("swPorFirmarFlow");
		session.removeAttribute("reporteBusquedaParticipante");
		session.removeAttribute("doc_detallesAux");
		session.removeAttribute("clienteDocumentoMaestrosPanelControl");
		session.removeAttribute("clienteDocumentoMaestros");
		// FIN INICLIAZAMOS LOS DOCUMENTOS DETALLES PARA APLICACIONDETALLE
		// si hemo agarrado archivos de subversion para guardarlos como
		// atacchment
		_upFileSVN = session.getAttribute("_upFileFile") != null ? (File) session
				.getAttribute("_upFileFile") : null;
		rutasRelativaSVN = session.getAttribute("rutasRelativaSVN") != null ? (List<SvnRutasRelativasFiles>) session
				.getAttribute("rutasRelativaSVN") : null;
		if (_upFileSVN != null && rutasRelativaSVN != null) {
			swAttachmentSVN = true;
		}

		// ebn caso decxida remplazar un participanbte, esto se ejecuta
		flow_ParticipantesCambio = session
				.getAttribute("flow_ParticipantesCambio") != null ? (Flow_Participantes) session
				.getAttribute("flow_ParticipantesCambio") : null;
		if (flow_ParticipantesCambio != null) {
			participanteOLd = flow_ParticipantesCambio.getParticipante();
		}

		// fin si hemo agarrado archivos de subversion para guardarlos como
		// atacchment

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			Configuracion conf = configuraciones.get(0);
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		// si viene por attachment
		swAttachment = session.getAttribute("attachment") != null;
		session.removeAttribute("attachment");
		// --------------------------
		setTree(session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null);
		session.setAttribute("mostrarCatalogo", true);

		// inicializo el comentario principal del flujo

		inicializa();

		isSwPrincipalVisible();
		isSwRoleVisible();
		isSwUserVisible();

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}

		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		Doc_detalle doc_detalle = (Doc_detalle) session
				.getAttribute("doc_detalle");

		// inicio aqui obtenemos la seguridad del menu que es el que me permite cambiar
		// participantges de un flow+
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadTree = super.getSeguridadTree(treeMenu);
		if (seguridadTree == null) {
			seguridadTree = new Seguridad();
		}
		//inicio  verificamos si tienes permisologia para     alterar un workflows.. cambiar sus usuarios por otros MENU
		toModFlow = false;
		if (seguridadTree != null && seguridadTree.isToModFlow()
				|| swSuperUsuario) {
			toModFlow = true;
		}
		// fin  verificamos si tienes permisologia para     alterar un workflows.. cambiar sus usuarios por otros MENU

		
		// participantges de un flow, aqui obtenemos la seguridad de un noodn tree(especificamente del documento)


		if (doc_detalle != null) {
			System.out.println("doc_detalle="+doc_detalle.getCodigo());
			treeMenu.setNodo(doc_detalle.getDoc_maestro().getTree().getNodo());
		}
		seguridadTree = super.getSeguridadTree(treeMenu);
		if (seguridadTree == null) {
			seguridadTree = new Seguridad();
		}
		// verificamos si tienes permisologia para realizar nuevamente un flow
		toDoFlow = false;
		if (seguridadTree != null
				&& (seguridadTree.isToDoFlowRevision() || seguridadTree
						.isToDoFlow()) || swSuperUsuario) {
			toDoFlow = true;
		}
		// fin participantges de un flow, aqui obtenemos la seguridad de un noodn tree(especificamente del documento)

	}

	public ClienteFlowsResponse(String constructorVacio) {

	}

	public ClienteFlowsResponse(Flow flow) {
		this.flow = flow;
		flowParticipantesGetData();
	}

	public String erasedDetalleTreeHistoricoPersonalInFlow() {
		String pagIr = "";
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		pagIr = Utilidades.getFinexitoso();
		return pagIr;
	}

	public String agregarDocumentosvn() {
		String pagIr = "";
		session.setAttribute("flowsResponse", Utilidades.getFlowsResponse());
		pagIr = Utilidades.getAgregardocumentosvn();
		return pagIr;
	}

	public void erasedDetalleTreeHistoricoPersonalInFlow(ActionEvent event) {
		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (flow == null) {
				flow = new Flow();
			}
			// buscamos el role para editar
			if (id >= 0) {
				flow.setCodigo(new Long(id));
				flow = delegado.findFlow(flow);
			}

			if (flow != null) {
				String flowPartSifirmo = (String) session
						.getAttribute("flowPartSifirmo");
				Flow_Participantes f_p2 = new Flow_Participantes();
				if (flowPartSifirmo != null) {
					long id_p = Long.parseLong(flowPartSifirmo);
					f_p2.setCodigo(id_p);
					f_p2 = delegado.findFlow(f_p2);
				}
				if (f_p2 != null) {
					f_p2.setActivo(Utilidades.isInactivo());
					try {
						session.setAttribute("treeNodoActual", f_p2.getFlow()
								.getDoc_detalle().getDoc_maestro().getTree());
						delegado.edit(f_p2);
					} catch (EcologicaExcepciones ex) {
						ex.printStackTrace();
					}
				}

			}
		}

	}

	public Flow buscaFlowDocDetalle(Doc_detalle doc_detalle) {
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");

		if (doc_detalle == null
				&& session.getAttribute("flowHistorico") == null
				&& session.getAttribute("flowPartSifirmo") == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
			return null;
		}

		List flows = null;
		if (session.getAttribute("flowPartSifirmo") != null) {
			swPuedeBorrarHistTree = true;
			flows = delegado
					.findDetalleTreeHistoricoPersonalInFlow(doc_detalle);
		} else {
			if (session.getAttribute("flowHistorico") != null) {
				Flow f = (Flow) session.getAttribute("flowHistorico");
				flows = delegado.findHistoricooDetalleInFlow(f);
			} else {
				flows = delegado.findDocumentoDetalleInFlow(doc_detalle);
			}
		}

		int size = flows.size();
		Flow f = null;
		int j = 0;
		for (int i = 0; i < size; i++) {
			f = (Flow) flows.get(i);
			if (session.getAttribute("flowHistorico") != null) {
				session.setAttribute("doc_detalle", f.getFlowParalelo()
						.getFlow().getDoc_detalle());
			}

			j = i;
		}
		if (j > 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_borr_detlle_flowsVariosenBD")));
		}
		return f;
	}

	public String reporte() {
		return "reporte";
	}

	public void inicializa() {
		Doc_detalle doc_detalle = null;
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");

		if (doc_detalle != null
				&& !super.isEmptyOrNull(doc_detalle.getNameFile())) {
			icono = obtenIconoDoc(doc_detalle.getNameFile());

			if (Utilidades.obtenerXdelIcono(icono)) {
				swAttachmentDetalle = true;
				codigoDetalle = doc_detalle.getCodigo();

			}

		}

		Flow f = new Flow();
		// esdte viene de la lista del arbnol de treas pendientes o firmadas
		String flowpuente = session.getAttribute("flowpuente") != null ? (String) session
				.getAttribute("flowpuente") : null;
		if (flowpuente != null && !isEmptyOrNull(flowpuente)) {
			f.setCodigo(new Long(flowpuente));
			f = delegado.findFlow(f);
		} else {
			f = buscaFlowDocDetalle(doc_detalle);
		}
		if (f != null) {
			// ME ARREGLA UN FLUJO QUE YA FUE APROBADO,
			// RECHAZADO O REVISADO Y HAY GENTE SIN FIRMAR
			delegado.findFlowParticipantesSinFirmar(f);
			// FIN REPARAMOS FLOWS SI ESTA MAL HECHO
			// obtenemois el icono del documento

			if (doc_detalle != null
					&& !super.isEmptyOrNull(doc_detalle.getNameFile())) {

				icono = obtenIconoDoc(doc_detalle.getNameFile());

				if (Utilidades.obtenerXdelIcono(icono)) {
					swAttachmentDetalle = true;
					codigoDetalle = doc_detalle.getCodigo();

				}
				// CONSEGUIMOS LA SEGURIDAD TREE
				if (doc_detalle.getDoc_maestro().getTree() != null) {

					seguridadTree = super.getSeguridadTree(user_logueado,
							doc_detalle.getDoc_maestro().getTree());

					swPermViewDoc = seguridadTree.isToView();
					if (swSuperUsuario && seguridadTreeArbol != null) {
						seguridadTreeArbol.setToView(true);
						swPermViewDoc = true;
					}

				}
			}

			// buscamos el verdadero documento que se origino en el flujo de
			// referencia que esta en la clase flujo paralelo
			// ese documento si tiene data.. los flujos que nacieron a partir de
			// ese documento.. no tiene data...
			Flow flowReferencia = f.getFlowParalelo().getFlow();
			Doc_detalle doc_detalleFlowReferencia = flowReferencia
					.getDoc_detalle();
			session.setAttribute("objeto", doc_detalleFlowReferencia);
			// fin

			setFlow(f);
			// inicializo el comentario principal del flujo
			FlowParalelo flowParalelo = new FlowParalelo();
			flowParalelo = f.getFlowParalelo();

			List noSeUsa = new ArrayList();
			visibleUsersFlows = new ArrayList();
			visibleRoleFlows = new ArrayList();
			datosCombo.llenarUsuariosFlowVisibles(visibleUsersFlows, noSeUsa,
					doc_detalle);
			setVisibleUsersFlows(visibleUsersFlows);
			datosCombo.llenarRoleFlowVisibles(visibleRoleFlows, noSeUsa,
					getFlow());
			setVisibleRoleFlows(visibleRoleFlows);

		}
	}

	public String flows_action() {

		boolean swFlowParalelo = true;
		session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		String mensaje = flow_Participante.getFlow().getFlowParalelo()
				.getFlow().getNombredelflujo()
				+ "("
				+ flow_Participante.getFlow().getNombredelflujo()
				+ ":"
				+ flow_Participante.getFlow().getCodigo() + ")";
		if (flow_Participante.getFlow().getFlowParalelo().getFlow()
				.getDoc_detalle() != null) {
			Tree treePadre = delegado.findTree(flow_Participante.getFlow()
					.getFlowParalelo().getFlow().getDoc_detalle()
					.getDoc_maestro().getTree().getNodopadre());
			Doc_detalle doc_detalle = grabarDocumento(flow_Participante
					.getFlow().getFlowParalelo().getFlow().getDoc_detalle(),
					treePadre, user_logueado, swFlowParalelo, mensaje);

			session.setAttribute("treeNodoActual", getFlow().getFlowParalelo()
					.getFlow().getDoc_detalle().getDoc_maestro().getTree());
			session.setAttribute("doc_detalle", doc_detalle);
			session.setAttribute("flowParalelo", flow_Participante.getFlow()
					.getFlowParalelo());
			session.setAttribute("flow_Participante", flow_Participante);
		}

		return Utilidades.getFlows();
	}

	public void versionId(ActionEvent event) {
		try {

			String attrname1 = (String) event.getComponent().getAttributes()
					.get("attrname1");
			UIParameter component = null;

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editIdSubFlow");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editIdSubFlowPlantilla");
			}

			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				Flow_Participantes flow_Participante = new Flow_Participantes();
				flow_Participante.setCodigo(id);
				flow_Participante = (Flow_Participantes) delegado
						.findFlow(flow_Participante);

				boolean swFlowParalelo = true;
				session = super.getSession();
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;

				String mensaje = flow_Participante.getFlow().getFlowParalelo()
						.getFlow().getNombredelflujo()
						+ "("
						+ flow_Participante.getFlow().getNombredelflujo()
						+ ":" + flow_Participante.getFlow().getCodigo() + ")";
				if (flow_Participante.getFlow().getFlowParalelo().getFlow()
						.getDoc_detalle() != null) {
					Tree treePadre = delegado.findTree(flow_Participante
							.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getTree()
							.getNodopadre());
					Doc_detalle doc_detalle = grabarDocumento(flow_Participante
							.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle(), treePadre, user_logueado,
							swFlowParalelo, mensaje);

					session.setAttribute("treeNodoActual", getFlow()
							.getFlowParalelo().getFlow().getDoc_detalle()
							.getDoc_maestro().getTree());
					session.setAttribute("doc_detalle", doc_detalle);
					session.setAttribute("flowParalelo", flow_Participante
							.getFlow().getFlowParalelo());
					session.setAttribute("flow_Participante", flow_Participante);
				}

			}
		} catch (Exception e) {
			redirect();
		}
	}

	public void versionIdPlantilla(ActionEvent event) {
		try {

			UIParameter component = null;
			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editIdSubFlowPlantilla");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editIdSvn");
			}

			if ((component != null)
					&& (component.getValue().toString() != null)) {

				long id = Long.parseLong(component.getValue().toString());
				Flow_Participantes flow_Participantes = new Flow_Participantes();
				flow_Participantes.setCodigo(id);
				flow_Participantes = (Flow_Participantes) delegado
						.findFlow(flow_Participantes);

				boolean swFlowParalelo = true;
				session = super.getSession();
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;

				String mensaje = flow_Participantes.getFlow().getFlowParalelo()
						.getFlow().getNombredelflujo()
						+ "("
						+ flow_Participantes.getFlow().getNombredelflujo()
						+ ":" + flow_Participantes.getFlow().getCodigo() + ")";
				if (flow_Participantes.getFlow().getFlowParalelo().getFlow()
						.getDoc_detalle() != null) {
					Tree treePadre = delegado.findTree(flow_Participantes
							.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getTree()
							.getNodopadre());

					session.setAttribute("treeNodoActual", getFlow()
							.getFlowParalelo().getFlow().getDoc_detalle()
							.getDoc_maestro().getTree());
					flow_Participantes
							.getFlow()
							.getFlowParalelo()
							.getFlow()
							.getDoc_detalle()
							.setOrigen(
									flow_Participantes.getFlow()
											.getFlowParalelo().getFlow()
											.getOrigen());
					session.setAttribute("doc_detalle", flow_Participantes
							.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle());
					session.setAttribute("doc_detalleforflowplantilla",
							flow_Participantes.getFlow().getFlowParalelo()
									.getFlow().getDoc_detalle());
					session.setAttribute("flowParalelo", flow_Participantes
							.getFlow().getFlowParalelo());
					session.setAttribute("flow_Participantes",
							flow_Participantes);
					// me dice que pagina regresar
					session.setAttribute("flowsResponse",
							Utilidades.getFlowsResponse());
				}

			}
		} catch (Exception e) {
			redirect();
		}
	}

	public String viewDocumentoDetalleFlowReferenciaPDF() {
		try {
			// dispatcher.include(req, res);
			Doc_detalle detalle = (Doc_detalle) session.getAttribute("objeto");
			// System.out.println("detalle="+detalle.);

			// ____________________________________
			// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			/*
			 * boolean verDetalles = true;
			 * super.guardamosHistoricoActivoDelDocumento(user_logueado,
			 * detalle.getDoc_maestro(), false, false, verDetalles, false,
			 * false, false, false, false, "");
			 */
			// ____________________________________

			session.setAttribute("objeto", detalle);
			session.setAttribute("onlyView", "onlyView");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		// return "viewFlowReferenciaPDF";
		return "";
	}

	public String viewDocumentoDetalleFlowReferenciaPDFExplorer() {
		try {
			// dispatcher.include(req, res);
			Doc_detalle detalle = (Doc_detalle) session.getAttribute("objeto");
			// System.out.println("detalle="+detalle.);

			// ____________________________________
			// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			/*
			 * boolean verDetalles = true;
			 * super.guardamosHistoricoActivoDelDocumento(user_logueado,
			 * detalle.getDoc_maestro(), false, false, verDetalles, false,
			 * false, false, false, false, "");
			 */
			// ____________________________________

			session.setAttribute("objeto", detalle);
			session.setAttribute("onlyView", "onlyView");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return "viewFlowReferenciaPDF";

	}

	public String cancelar() {
		// se uada para ver los tabs en el flowresponse.jsp en rendered
		if (session != null) {
			session.removeAttribute("vertab");
		}

		return "flowsResponse";
	}

	public String cancelarRemplazo() {

		return "flowsResponse";
	}

	public String salir() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		super.clearSession(session, confmessages.getString("usuarioSession")
				+ ",doc_detalle");
		return Utilidades.getListarDocumentos();
	}

	/*
	 * public List<Usuario> mandarMailsUsuarios(Flow flow, Flow_Participantes
	 * flow_Participante) { mandarMailUsuarios = new ArrayList();
	 * List<Flow_Participantes> f_ps = delegado
	 * .findAllFlowParticipantesByFlow(flow); String mensBundle = " "; if
	 * (flow_Participante.getFirma().getNombre() != null) { mensBundle =
	 * messages.getString(flow_Participante.getFirma() .getNombre()); }
	 * 
	 * flow_Participante.setFirmaStr(mensBundle.toString()); // SOLO LO HACE UNA
	 * SOLA VEZ, ESTE FOR for (Flow_Participantes mailUsuario : f_ps) {
	 * mailUsuario.getParticipante().setComentarioMailFlujo(
	 * flow_Participante.getParticipante().toString() + "\n" +
	 * flow.getDoc_detalle().getDoc_maestro() .getNombre() + " " +
	 * flow.getDoc_detalle().getDoc_maestro() .getConsecutivo() + " " + "\n" +
	 * flow.getComentarios() + "\n\n" + messages.getString("tambiencomento") +
	 * "\n\n \"" + flow_Participante.getComentario() + "\"\n" +
	 * flow_Participante.getFirmaStr() + "\n\n\n");
	 * 
	 * 
	 * mandarMailUsuarios.add(mailUsuario.getParticipante()); } return
	 * mandarMailUsuarios; }
	 */

	public String saveDatosBasicos_action() {
		String pagIr = "";
		InputStream inputStream = null;
		long tamanio = 0;
		byte[] bytes = null;
		String contentType = "";
		String name = "";

		try {
			
			if (!super.isEmptyOrNull(getFirmarComentario())) {
				Calendar d = Calendar.getInstance();
				Flow_Participantes flow_Participante = (Flow_Participantes) session
						.getAttribute("flow_Participante");

				if (getFirmarComentario().length() > Utilidades
						.getLongCampoGrande()) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages.getString("textobasura")
									+ " " + getFirmarComentario().length()
									+ " " + getFirmarComentario()));
					return "";
				}
				//agregamos fecxha al comentario
				if (!super.isEmptyOrNull(getFirmarComentario())) {
					setFirmarComentario(getFirmarComentario() +"   ["+Utilidades.sdfShow.format(new Date())+"]") ;
				}
				

				flow_Participante.setComentario(getFirmarComentario());
				flow_Participante.setFirma(firma);
				flow_Participante.setFecha_firma(d.getTime());

				try {
					// regresamos al principio del flujo
					if ("devolucionstartflow".equalsIgnoreCase(firma
							.getNombre().trim())) {
						if (flow_Participante != null) {
							delegadoEcological
									.devolucionStartFlow(flow_Participante);

						}
						// System.out.println("------------------2-----------------------");

					} else if ("devolucionfirmaflow".equalsIgnoreCase(firma
							.getNombre().trim())) {
						// System.out.println("-----------------1------------------------");
						if (flow_Participante != null) {
							delegadoEcological
									.devolucionFirmaFlowExecute(flow_Participante);

						}
						// System.out.println("------------------2-----------------------");

					} else {
						// INICIO REVISAMOS SI COLOCO UN ATACHMENT DE ARCHIVO
						// si coloca un attachment de archivo
						Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();

						filesPrimeFaces = session
								.getAttribute("filesPrimeFaces") != null ? (ArrayList<FileUploadEvent>) session
								.getAttribute("filesPrimeFaces")
								: new ArrayList<FileUploadEvent>();

						session.removeAttribute("filesPrimeFaces");

						if (filesPrimeFaces != null
								&& filesPrimeFaces.size() > 0
								&& !filesPrimeFaces.isEmpty()) {
							_upFile = filesPrimeFaces.get(0);

						}

						if (_upFile != null) {
							inputStream = _upFile.getFile().getInputstream();// _upFile.getInputStream();
							tamanio = _upFile.getFile().getSize();// _upFile.getSize();
							bytes = _upFile.getFile().getContents();// _upFile.getBytes();
							contentType = _upFile.getFile().getContentType();// _upFile.getContentType();
							name = _upFile.getFile().getFileName();// _upFile.getName();

						} else if (_upFileSVN != null) {
							flow_ParticipantesAttachment.setSwSVN(true);
							Archivo archivo = new Archivo();
							inputStream = archivo.fileToinputStream(_upFileSVN);

							tamanio = _upFileSVN.length();
							bytes = new byte[(int) tamanio];

							bytes = new byte[(int) _upFileSVN.length()];
							try {
								FileInputStream fileInputStream = new FileInputStream(
										_upFileSVN);
								fileInputStream.read(bytes);
								/*
								 * for (int i = 0; i < bytes.length; i++) {
								 * System.out.print((char) bytes[i]); }
								 */
							} catch (FileNotFoundException e) {
								System.out.println("File Not Found.");
								e.printStackTrace();
							}

							contentType = "application/x-zip-compressed";
							name = _upFileSVN.getName();

						}

						if (_upFileSVN != null) {
							_upFileSVN.delete();
						}
						if (tamanio > 0) {

							// algun dia se arreglara los campos blob en
							// postgres
							Blob blob = Hibernate.createBlob(inputStream);
							flow_ParticipantesAttachment.setAttachmentDoc(blob);

							// algun dia se arreglara los campos blob en
							// postgres
							boolean swPostgres = false;
							if (swConfiguracionHayData) {
								if (swPostgresVieneDeConfiguracion) {
									// si es una base de datos postgres,
									// guardamos
									// la data en otra tabla
									swPostgres = true;
								}
							} else if ("1".equalsIgnoreCase(confmessages
									.getString("bdpostgres"))) {
								// si es una base de datos postgres, guardamos
								// la
								// data en otra tabla
								swPostgres = true;
							}
							boolean swPesoBad = false;
							if (swPostgres) {
								if ((Double.compare(converBytesMbytes(tamanio),
										Utilidades.getPostgresMBmax()) > 0)) {
									// no c ingrewsa.. mayor en peso mb
									// permitido
									swPesoBad = true;
								} else {
									flow_ParticipantesAttachment
											.setAttachmentDocPostgres(bytes);
								}
							}

							flow_ParticipantesAttachment
									.setFlowParticipantes(flow_Participante);
							flow_ParticipantesAttachment
									.setContentType(contentType);
							// si viene de postgres y es peso malo...
							if (swPesoBad) {
								flow_ParticipantesAttachment
										.setNameFile(messages
												.getString("postgmenorochomb"));
							} else {
								flow_ParticipantesAttachment
										.setNameFile(sacarSlashFiltro(name));
							}
							flow_ParticipantesAttachment.setSize_doc(tamanio);
							flow_ParticipantesAttachment.setStatus(Utilidades
									.isActivo());
							// delegado.create(flow_ParticipantesAttachment);
						}

						// FIN------------------------------------------

						delegado.firmarFlows(flow_Participante,
								flow_ParticipantesAttachment);

						if (rutasRelativaSVN != null) {
							// ---llenamos la tabla de mapeo donde van los
							// archoivos de subversion
							for (SvnRutasRelativasFiles svnRutasRelativasFiles : rutasRelativaSVN) {
								svnRutasRelativasFiles
										.setFlow_ParticipantesAttachment(flow_ParticipantesAttachment);
								delegado.create(svnRutasRelativasFiles);
							}
							// -fin--llenamos la tabla de mapeo donde van los
							// archoivos de subversion
						}
					}
					// SI NO ES FIIN DE FLOW Y NOI ES HECHO POR SVN.. NOS VAMOS
					// DE MANERA NORMAL
					// if (session.getAttribute("pagIr") == null) {
					// inicializamos todas las sessiones,dejamos solo las
					// basicas, antes de
					// irnos

					session.setAttribute(Utilidades.getNoclearsession(), "");
					refrescarArbolWorkFlows();

					session.removeAttribute("flowWithParticipantes");
					session.setAttribute("pagIr", Utilidades.getFlowsResponse());

					// }
					pagIr = Utilidades.getFinexitoso();
					// VEMOS SI ES EL UTLIMO USUARIO A FIRMAR Y SI EL DOCUMENTO
					// VIENE DE UN SVN
					// Doc_estado doc_edo = new Doc_estado();
					// doc_edo.setCodigo(Utilidades.getAprobado());
					// doc_edo = delegado.findDocEstado(doc_edo);
					// SI ES EL FIN DEL FLUJO.. O EL EL ULTIMO QUE FIRMO Y SI ES
					// DOCUMENTO SVN
					/*
					 * if
					 * (flow_Participante.getFlow().getFlowParalelo().getFlow(
					 * ).getDoc_detalle().getDoc_maestro().isSwSVN() ){ //si es
					 * el fin de flow paralelo if
					 * (delegado.finFlowParaleloForAlterarDocdetalle
					 * (flow_Participante
					 * .getFlow().getFlowParalelo().getFlow())) { //existe
					 * session de flowparticipante.. no hacemos nada con esa
					 * variable //pero si esta la session flow_Participantes
					 * activa Flow_Participantes flow_Participantes = session
					 * .getAttribute("flow_Participantes") != null ?
					 * (Flow_Participantes) session
					 * .getAttribute("flow_Participantes") : null;
					 * session.setAttribute("flowsResponse",
					 * Utilidades.getFlowsResponse());
					 * session.setAttribute("uploadSVN",true); pagIr =
					 * Utilidades.getAgregardocumentosvnupload(); } }
					 */

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
				} catch (Exception e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages.getString("error")
									+ e.toString()));
				}
				// se uada para ver los tabs en el flowresponse.jsp en rendered,
				// se muestra todos
				// lo comentarios o se muestra donde se va a firmar
				// session.removeAttribute("vertab");
			} else {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(messages
										.getString("error_comentario")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		} finally {

		}
		return pagIr;
	}

	public String canceladoByDuenio() {
		refrescarArbolWorkFlows();
		flow = delegado.findFlow(flow);
		// nos vamos al papa del flow en el paralelo
		flow = flow.getFlowParalelo().getFlow();
		List<FlowParalelo> flowParaleloLst = delegado
				.findAllFlowParalelos(flow);
		for (FlowParalelo fp : flowParaleloLst) {
			List<Flow> flows = delegado.findAllFlowParalelos(fp);
			for (Flow f : flows) {
				// eliminamos el flow de un flow paralelo ciomun
				deleteLogicoFlowPartixipantes(f);
				// REPARAMOS FLOWS SI ESTA MAL HECHO
				// delegado.findFlowParticipantesFirmadosAndFlowIspendiente(f);

			}
		}

		String pagIr = Utilidades.getFinexitoso();
		session.setAttribute(
				Utilidades.getNoclearsession(),
				"doc_detalle,doc_maestro,treeNodoActual,"
						+ Utilidades.getRefrescararbolworkflows());
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(messages.getString("operacion_exitosa")));
		return pagIr.toString();
	}

	public void cancelaCompletoByDuenioFlow(ActionEvent event)
			throws EcologicaExcepciones {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			Flow flow = new Flow();
			flow.setCodigo(new Long(id));
			flow = delegado.findFlow(flow);
			// nos vamos al papa del flow en el paralelo
			flow = flow.getFlowParalelo().getFlow();
			List<FlowParalelo> flowParaleloLst = delegado
					.findAllFlowParalelos(flow);
			for (FlowParalelo fp : flowParaleloLst) {
				List<Flow> flows = delegado.findAllFlowParalelos(fp);
				for (Flow f : flows) {
					// eliminamos el flow de un flow paralelo ciomun
					deleteLogicoFlowPartixipantes(f);
					// REPARAMOS FLOWS SI ESTA MAL HECHO
					// delegado.findFlowParticipantesFirmadosAndFlowIspendiente(f);

				}
			}

		}

	}

	private void deleteLogicoFlowPartixipantes(Flow subFlow) {
		// System.out.println("delete subFlow.getCodigo()=" +
		// subFlow.getCodigo());
		List lst = delegado.findByFlowParticipantes(subFlow);
		Iterator it = lst.iterator();
		Calendar fechacancel = Calendar.getInstance();
		boolean swSoloUnaVez = true;
		while (it.hasNext()) {
			Flow_Participantes flujo_participantes = (Flow_Participantes) it
					.next();
			// System.out.println("flujo_participantes.getCodigo()="
			// + flujo_participantes.getCodigo());
			if (flujo_participantes.getFirma().getCodigo() == Utilidades
					.getSinFirmar()) {
				Doc_estado firma = new Doc_estado();
				firma.setCodigo(Utilidades.getCanceladoByDuenio());
				flujo_participantes.setFirma(firma);
				flujo_participantes.setComentario(messages
						.getString("canceladoByDuenio"));
				flujo_participantes.setFecha_firma(fechacancel.getTime());
				try {
					delegado.edit(flujo_participantes);
					// EL CONTROL DE TIEMPO LO PARAMOS
					// ME DEVUELVE UN SOLO PARTICIPANTE ESTE FOR
					List<FlowControlByUsuarioBean> flowControlByUsuarioBean = delegado
							.find_allControlTimeByFlowParticipAndFlow(flujo_participantes);
					for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
						controlTime.setSwStartHilo(false);
						delegado.edit(controlTime);
					}

					if (swSoloUnaVez) {
						// mandamos mails
						delegadoEcological.MyHiloEnvioMails(
								flujo_participantes.getFlow(),
								flujo_participantes);
						swSoloUnaVez = false;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// }
				// firmamos el flujo como rechazado
				Doc_estado CanceladoByDuenio = new Doc_estado();
				CanceladoByDuenio.setCodigo(Utilidades.getCanceladoByDuenio());
				CanceladoByDuenio = delegado.findDocEstado(CanceladoByDuenio);
				subFlow.setEstado(CanceladoByDuenio);

				try {
					delegado.edit(subFlow);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		// AL FINAL DE TODOS LOS SUBFLOWS COLOCAMOS EL DOCUMENTO
		// BORRADOR----
		//
		try {

			Doc_estado borrador = new Doc_estado();
			borrador.setCodigo(Utilidades.getBorrador());
			borrador = delegado.findDocEstado(borrador);
			Doc_detalle doc_d = flow.getDoc_detalle();
			doc_d = delegado.findUltimolDoc_Detalles(doc_d.getDoc_maestro());
			doc_d.setDoc_estado(borrador);
			delegado.edit(doc_d);

		} catch (Exception e) {
			System.out.println("" + e);
		} finally {
		}
	}

	public void checkFlowToContinuar(Flow_Participantes flow_Participante) {
		try {
			Doc_detalle doc_d = null;
			boolean swCondRechAprob = false;

			if (flow_Participante != null) {
				Flow flow = flow_Participante.getFlow();
				// si es condicional, y es un flujo de aprobacion
				// y ha sido rechazado, se cancela los demas flujos pendientes
				if (flow.isCondicional()
						&& (Long.parseLong(flow.getTipoFlujo()) == Utilidades
								.getEnAprobacion())) {
					if (flow_Participante.getFirma().getCodigo() == Utilidades
							.getRechazado()) {
						swCondRechAprob = true;
						List lst = delegado.findByFlowParticipantes(flow);
						Iterator it = lst.iterator();
						int i = 0;

						while (it.hasNext()) {

							Flow_Participantes flujo_participantes = (Flow_Participantes) it
									.next();

							if (flujo_participantes.getFirma().getCodigo() == Utilidades
									.getSinFirmar()) {

								Doc_estado firma = new Doc_estado();
								firma.setCodigo(Utilidades
										.getRechazadoAutomatico());
								flujo_participantes.setFirma(firma);
								flujo_participantes
										.setComentario(flow_Participante
												.getComentario());
								flujo_participantes
										.setFecha_firma(flow_Participante
												.getFecha_firma());
								try {
									delegado.edit(flujo_participantes);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						// firmamos el flujo como rechazado
						Doc_estado rechazado = new Doc_estado();
						rechazado.setCodigo(Utilidades.getRechazado());
						rechazado = delegado.findDocEstado(rechazado);
						flow.setEstado(rechazado);
						delegado.edit(flow);
						doc_d = flow.getDoc_detalle();
						doc_d = delegado.findDetalle(doc_d);
						doc_d.setDoc_estado(rechazado);
						delegado.editDetalle(doc_d);

					}
				}
			}
			// ________________________________________________________
			// SI ES FIN DEL FLUJO
			// si no fue condicional de aprbacion y no fue rechazado
			if (!swCondRechAprob && delegado.flowIsFinFlowParticipantes(flow)) {
				// BUSCAMOS SI HA SIDO RECHAZADO
				Doc_estado estadoFinDoc = new Doc_estado();
				Doc_estado estadoFinFlow = new Doc_estado();

				if (delegado.flowIsFinEdoRechazadoFlowParticipantes(flow)) {
					// firmamos el flujo como rechazado
					estadoFinDoc.setCodigo(Utilidades.getRechazado());
					estadoFinDoc = delegado.findDocEstado(estadoFinDoc);
					estadoFinFlow = estadoFinDoc;
					flow.setEstado(estadoFinFlow);
				} else {
					if (Long.parseLong(flow.getTipoFlujo()) == Utilidades
							.getEnAprobacion()) {
						// firmamos el flujo como aprobado
						estadoFinFlow.setCodigo(Utilidades.getAprobado());
						estadoFinFlow = delegado.findDocEstado(estadoFinFlow);
						flow.setEstado(estadoFinFlow);

						estadoFinDoc.setCodigo(Utilidades.getVigente());
						estadoFinDoc = delegado.findDocEstado(estadoFinDoc);
						swFueAprobado = true;
					} else {
						// firmamos el flujo como revisado
						estadoFinFlow.setCodigo(Utilidades.getRevisado());
						estadoFinFlow = delegado.findDocEstado(estadoFinFlow);
						estadoFinDoc = estadoFinFlow;
						flow.setEstado(estadoFinFlow);
					}

				}
				// finalizamos la operacion colocando eñl edo a la nueva
				// version
				// y al flujo
				delegado.edit(flow);
				doc_d = flow.getDoc_detalle();
				doc_d = delegado.findDetalle(doc_d);
				if (Utilidades.getVigente() - estadoFinDoc.getCodigo() == 0) {
					doc_d.setMinorVer("0");
					doc_d.setMayorVer(super.incInUnoString(doc_d.getMayorVer()));
				}
				doc_d.setDoc_estado(estadoFinDoc);
				delegado.editDetalle(doc_d);
				// ________________________________________________________

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String attachDocFlowDoc_Go() {
		String pagIr = "";
		session.setAttribute("attachment", true);
		pagIr = "attachment";
		/*
		 * session.setAttribute("edit", true); Doc_detalle detalle =
		 * (Doc_detalle) session.getAttribute("doc_detalle"); if (detalle !=
		 * null) { detalle.setDescripcion("");
		 * session.setAttribute("doc_detalle", detalle); pagIr =
		 * "flowsResponse"; } else {
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage(messages.getString("error_camposvacios"))); }
		 */

		return pagIr;
	}

	public void attachmentFlowId(ActionEvent event) {
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public Object[] getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(Object[] selectedUsuarios) {
		this.selectedUsuarios = selectedUsuarios;
	}

	public Object[] getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(Object[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public List getVisibleUsersFlows() {
		return visibleUsersFlows;
	}

	public void setVisibleUsersFlows(List visibleUsersFlows) {
		this.visibleUsersFlows = visibleUsersFlows;
	}

	public List getVisibleRoleFlows() {
		return visibleRoleFlows;
	}

	public void setVisibleRoleFlows(List visibleRoleFlows) {
		this.visibleRoleFlows = visibleRoleFlows;
	}

	public Flow getFlow() {
		if (flow == null) {
			flow = session.getAttribute("flowHistorico") != null ? (Flow) session
					.getAttribute("flowHistorico") : null;
			if (flow == null) {
				flow = new Flow();
			}
		}
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public boolean isSwUserVisible() {
		String tab = session.getAttribute("tabBandera") != null ? (String) session
				.getAttribute("tabBandera") : "";
		swUserVisible = "1".equals(tab);
		return swUserVisible;
	}

	public void setSwUserVisible(boolean swUserVisible) {
		this.swUserVisible = swUserVisible;
	}

	public boolean isSwRoleVisible() {
		String tab = session.getAttribute("tabBandera") != null ? (String) session
				.getAttribute("tabBandera") : "";
		swRoleVisible = "2".equals(tab);
		return swRoleVisible;
	}

	public void setSwRoleVisible(boolean swRoleVisible) {
		this.swRoleVisible = swRoleVisible;
	}

	public boolean isSwPrincipalVisible() {
		boolean habilitar = (isSwRoleVisible() == false)
				&& (isSwUserVisible() == false);
		swPrincipalVisible = habilitar;
		return swPrincipalVisible;
	}

	public void setSwPrincipalVisible(boolean aSwPrincipalVisible) {
		swPrincipalVisible = aSwPrincipalVisible;
	}

	public boolean isSwMostrarCatalogo() {
		// la principal v entana que te muesytran usuarios y roles que ya se han
		// escojidos
		swMostrarCatalogo = session.getAttribute("mostrarCatalogo") != null;
		return swMostrarCatalogo && isSwPrincipalVisible();
	}

	public void setSwMostrarCatalogo(boolean swMostrarCatalogo) {
		this.swMostrarCatalogo = swMostrarCatalogo;
	}

	public boolean isFlowAprobacion() {
		return flowAprobacion;
	}

	public void setFlowAprobacion(boolean flowAprobacion) {
		this.flowAprobacion = flowAprobacion;
	}

	public boolean isTipoFlow() {
		return tipoFlow;
	}

	public void setTipoFlow(boolean tipoFlow) {
		this.tipoFlow = tipoFlow;
	}

	public String action_verTabs() {

		flow_Participante = (Flow_Participantes) delegado
				.findFlow(flow_Participante);
		// se uada para ver los tabs en el flowresponse.jsp en rendered
		// inicializamos el comentario que va a crear el usuario para
		// firmar
		getFlow().setComentarios("");
		setCualquierComentario("");

		// se uada para ver los tabs en el flowresponse.jsp en rendered
		session.setAttribute("vertab", true);
		// inicializo el comentario del usuario especifico
		// esto en caso que el usuario valla a firmar, mellevo el ide
		// que va a firmar
		session.setAttribute("flow_Participante", flow_Participante);

		return "flowsResponseTab1";
	}

	public void selectFirmar(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdFirmar");
		if ((component != null)
				&& (!super.isEmptyOrNull(component.getValue().toString()))) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdFirmar");
			if ((component != null)
					&& (!super.isEmptyOrNull(component.getValue().toString()))) {
				long id = Long.parseLong(component.getValue().toString());
				Flow_Participantes flow_Participante = new Flow_Participantes();
				flow_Participante.setCodigo(id);
				flow_Participante = (Flow_Participantes) delegado
						.findFlow(flow_Participante);
				// se uada para ver los tabs en el flowresponse.jsp en rendered
				// inicializamos el comentario que va a crear el usuario para
				// firmar
				getFlow().setComentarios("");
				setCualquierComentario("");

				// se uada para ver los tabs en el flowresponse.jsp en rendered
				session.setAttribute("vertab", true);
				// inicializo el comentario del usuario especifico
				// esto en caso que el usuario valla a firmar, mellevo el ide
				// que va a firmar
				session.setAttribute("flow_Participante", flow_Participante);
			}
		}
	}

	public String action_CambiarUsuario() {

		flow_Participante = (Flow_Participantes) delegado
				.findFlow(flow_Participante);
		// se uada para ver los tabs en el flowresponse.jsp en rendered
		// inicializamos el comentario que va a crear el usuario para
		// firmar
		getFlow().setComentarios("");
		setCualquierComentario("");

		session.setAttribute("flow_ParticipantesCambio", flow_Participante);
		String pagIr = Utilidades.getFlowcambiarusuarioparticipante();
		return pagIr;

	}

	public void selectCambiarParticipante(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdCambiar");
		if ((component != null)
				&& (!super.isEmptyOrNull(component.getValue().toString()))) {
			component = (UIParameter) event.getComponent().findComponent(
					"editIdCambiar");
			if ((component != null)
					&& (!super.isEmptyOrNull(component.getValue().toString()))) {
				long id = Long.parseLong(component.getValue().toString());
				Flow_Participantes flow_Participante = new Flow_Participantes();
				flow_Participante.setCodigo(id);
				flow_Participante = (Flow_Participantes) delegado
						.findFlow(flow_Participante);
				// se uada para ver los tabs en el flowresponse.jsp en rendered
				// inicializamos el comentario que va a crear el usuario para
				// firmar
				getFlow().setComentarios("");
				setCualquierComentario("");

				session.setAttribute("flow_ParticipantesCambio",
						flow_Participante);
			}
		}
	}

	public String remplazandoParticipante() {
		String pagIr = "";
		if (participanteOLd != null) {
			if (participanteOLd.equals(usuarioRemplazo)) {
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

				participanteOLd = delegado.getUsuario(participanteOLd);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

			// en flujo se deja porque el fuie el autor
			// falta detalle, maestro
			// cambiamos el flow_participantes y guardamos el historico del
			// flujo si tiene
			if (flow_ParticipantesCambio == null) {
				flow_ParticipantesCambio = session
						.getAttribute("flow_ParticipantesCambio") != null ? (Flow_Participantes) session
						.getAttribute("flow_ParticipantesCambio") : null;
			}
			if (flow_ParticipantesCambio != null) {
				flow_ParticipantesCambio.setTipoDeCambioParticipante(Integer
						.parseInt(tipoDeCambio));
				delegado.sustituirFlowParticipantesOldByOtroFlows(
						flow_ParticipantesCambio, usuarioRemplazo,
						cualquierComentario);
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"participante del flujo es null,error del sistema->clienteFlowResponse.java->remplazandoParticipante"));
			}

			// GUARDAMOS SU HISTORICO GENERAL
			/*
			 * com.ecological.paper.historico.Historico hist = new
			 * com.ecological.paper.historico.Historico(); hist.setStatus(true);
			 * hist.setFecha_accion(super.fechaActual());
			 * hist.setComentarios(cualquierComentario);
			 * hist.setMaquina(super.getMaquinaConectada());
			 * hist.setTipo_accion(Utilidades.getParticipantecambiado());
			 * user_logueado = session.getAttribute("user_logueado") != null ?
			 * (Usuario) session .getAttribute("user_logueado") : null;
			 * hist.setUsuario_accion(user_logueado);
			 * hist.setUsuario_anterior(participanteOLd);
			 * hist.setUsuario_new(usuarioRemplazo); delegado.create(hist);
			 */
			// __________________________________________________________________
			session.setAttribute(Utilidades.getNoclearsession(), "");

			session.removeAttribute("flowWithParticipantes");
			session.setAttribute("pagIr", Utilidades.getFlowsResponse());
			pagIr = Utilidades.getFinexitoso();
			// getUsuarios();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));

		}
		return pagIr;
	}

	public boolean isVerTabs() {
		verTabs = session.getAttribute("vertab") != null;
		return verTabs;
	}

	public void setVerTabs(boolean verTabs) {
		this.verTabs = verTabs;
	}

	public String getNomCompletoFlowDoc() {

		StringBuffer str = new StringBuffer("");
		/*
		 * System.out.println("getFlow()==null=" + (getFlow() == null));
		 * System.out.println("codigo=" + getFlow().getCodigo());
		 * System.out.println("getFlow().getFlowParalelo()=null?" +
		 * (getFlow().getFlowParalelo() == null));
		 */
		if ((getFlow() != null && getFlow().getFlowParalelo() != null && getFlow()
				.getFlowParalelo().getFlow() != null)
				&& (getFlow().getFlowParalelo().getFlow().getDoc_detalle() != null)) {

			str.append(
					getFlow().getFlowParalelo().getFlow().getDoc_detalle()
							.getDoc_maestro().getConsecutivo())
					.append(" ")
					.append(getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getNombre());
			str.append("(")
					.append(getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getMayorVer())
					.append(".")
					.append(getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getMinorVer()).append(")");
			String estado = "";
			try {
				estado = messages.getString(getFlow().getFlowParalelo()
						.getFlow().getEstado().getNombre());
			} catch (Exception e) {
				// TODO: handle exception
			}

			str.append("[").append(estado).append("]");

			boolean nombreCorto = false;
			prefix = "";
			setPrefix(super.getPrefijo(getFlow().getFlowParalelo().getFlow()
					.getDoc_detalle().getDoc_maestro().getTree(), prefix,
					nombreCorto, getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getNombre()));
			// getFlow()

		}
		nomCompletoFlowDoc = str.toString();
		// esta session es usada en el servletReporte.java para el repore
		session.setAttribute("nomCompletoFlowDoc", nomCompletoFlowDoc);

		return nomCompletoFlowDoc;
	}

	public List<Flow> flowParticipantesGetData() {
		List listamostrar = new ArrayList();
		try {
			treeData = null;// new
							// TreeNodeBase(Utilidades.getIdentificaRaizTree(),
			// messages.getString("flow_firmados") + " ", false);
			if (user_logueado == null) {
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
			}
			if (user_logueado != null) {

				// chequeamos si todos los flujos paralelos fueron firmados...
				Flow_Participantes flow_ParticipantesFinFlow = null;
				FlowParalelo flowParalelo = new FlowParalelo();
				if (flow == null || flow.getCodigo() == null) {
					// por si acaso viene del historico de flujos en listar
					// usuarios
					flow = session.getAttribute("flowHistorico") != null ? (Flow) session
							.getAttribute("flowHistorico") : null;
					if (flow == null) {
						// NOS SALIMOS
						return listamostrar;

					}
				}

				flowParalelo.setCodigo(flow.getFlowParalelo().getCodigo());
				List<Flow> subFlujos = (List<Flow>) delegado
						.findByFlowParaleloAllFlows(flowParalelo);
				if (flow.getDoc_detalle() != null) {

					session.setAttribute(
							"nomCompletoFlowDoc",
							flow.getDoc_detalle().getDoc_maestro().getNombre()
									+ " ("
									+ flow.getDoc_detalle().getMayorVer()
									+ "."
									+ flow.getDoc_detalle().getMinorVer()
									+ ")"
									+ "["
									+ flow.getDoc_detalle().getDoc_maestro()
											.getConsecutivo()
									+ "] "
									+ messages.getString(flow.getEstado()
											.getNombre()));

				}

				boolean swFinFlow = true;
				for (Flow subFlow : subFlujos) {

					if (subFlow.isStatus()) {
						swFinFlow = delegado
								.flowIsFinFlowParticipantes(subFlow);
						if (!swFinFlow) {
							break;
						}
					}
				}
				if (swFinFlow) {
					flow_ParticipantesFinFlow = delegado
							.flowUltimoParticipante(flow);
				}

				List detallesParticipantes = new ArrayList();
				StringBuffer comentariosAllPartcipantes = new StringBuffer("");

				/*
				 * <root> <subflow> <nombre></nombre> <usuario></usuario>
				 * <comentario></comentario> <participantes> <usuario></usuario>
				 * <comentario></comentario> </participantes> </subflow> </root>
				 */
				int idSubreporte = 0;
				listaSubFlowReporte = new ArrayList<SubFlowReporte>();
				boolean swUnaVez = true;
				for (Flow subFlow : subFlujos) {

					if (subFlow.isStatus()) {
						comentariosAllPartcipantes
								.append(subFlow.getNombredelflujo())
								.append("->")
								.append(subFlow.getDuenio().getNombre())
								.append(" ")
								.append(subFlow.getDuenio().getApellido())
								.append(" [")
								.append(subFlow.getDuenio().getCargo() != null ? subFlow
										.getDuenio().getCargo().getNombre()
										: "").append("]").append("\n\t")
								.append("[").append(subFlow.getComentarios())
								.append("]\n\n");

						String nombreFlujo = subFlow.getNombredelflujo();
						String nombre = subFlow.getDuenio().getNombre();
						String apellido = subFlow.getDuenio().getApellido();
						String cargo = subFlow.getDuenio().getCargo() != null ? subFlow
								.getDuenio().getCargo().getNombre()
								: "";

						// solamente una vez me trae el titulo del reporte
						SubFlowReporte subFlowReporte = null;
						if (swUnaVez) {
							subFlowReporte = new SubFlowReporte(++idSubreporte,
									nombreFlujo, nombre, apellido, cargo, "["
											+ subFlow.getComentarios() + "]",
									"");
							swUnaVez = false;
						} else {
							subFlowReporte = new SubFlowReporte(++idSubreporte,
									nombreFlujo, "", "", "", "", "");
						}

						listaSubFlowReporte.add(subFlowReporte);

						// SON LOS ParticipantesFlows detallesParticipantes
						procesamientosParticipantes(
								subFlow,
								detallesParticipantes,
								flow_ParticipantesFinFlow,
								(getFlow().getCodigo() - subFlow.getCodigo()) == 0,
								comentariosAllPartcipantes, listaSubFlowReporte);
						// FIN SON LOS ParticipantesFlows detallesParticipantes

						// fin para hacer el reporte

						comentariosAllPartcipantes.append("").append("\n\n");
					}
				}
				session.setAttribute("listaSubFlowReporte", listaSubFlowReporte);
				// si todos los flujos estan en aprobacion... entonces..
				// colocamos el comentario principal de los flujos
				if (isEmptyOrNull(comentariosAllPartcipantes.toString())) {
					comentariosAllPartcipantes.append(flow.getComentarios());

				}
				setCualquierComentario(comentariosAllPartcipantes.toString());

				if (getFlow().getFecha_creado() != null) {
					// fechas asignadas a los comentarios, ya formateads, se
					// utiliza
					// este campo comentario porque es de tipo string
					// en fecha todavia no me sirve
					// getFlow().setComentarios(
					// Utilidades.sdfShow.format(getFlow()
					// .getFecha_creado()));
					// esta sessionb es usada een el reporte servletReporte
					session.setAttribute("fechacreado", Utilidades.sdfShow
							.format(getFlow().getFecha_creado()));
				}
				getFlow().setParticipantesFlows(detallesParticipantes);
				// /paRA xhtml richfaces
				getFlow().setParticipantesFlowsDetalle(detallesParticipantes);
				// /fin paRA xhtml richfaces

				listamostrar.add(getFlow());
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_userNoEncontrado")));
			}
			// arbol de los quew ya firmaron
			session.setAttribute("treeData", treeData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listamostrar;
	}

	private void procesamientosParticipantes(Flow flows,
			List detallesParticipantes,
			Flow_Participantes flow_ParticipantesFinFlow,
			boolean swIsFlowActual, StringBuffer comentariosAllPartcipantes,
			List<SubFlowReporte> listaSubFlowReporte) {
		List<Flow_Participantes> listaAuxParticipantes = delegado
				.findByFlowParticipantesFlowIsActivo(flows);

		boolean swUnaSolaVez = true;
		int idSubreporteParticipante = 0;
		// ESTOS DATOS SON SOLO PARA REPORTES EN BUSQUEDA.. ESTOS VIENEN
		// FALSOS SI
		// NO ES DESDE BUSQUEDA
		boolean swPorFirmarFlow = session.getAttribute("swPorFirmarFlow") != null ? (Boolean) session
				.getAttribute("swPorFirmarFlow") : false;
		Usuario reporteBusquedaParticipante = session
				.getAttribute("reporteBusquedaParticipante") != null ? (Usuario) session
				.getAttribute("reporteBusquedaParticipante") : null;
		boolean swReporteBusquedaPorActor = false;
		// FIN ESTOS DATOS SON SOLO PARA REPORTES EN BUSQUEDA.. ESTOS VIENEN
		// FALSOS SI
		// NO ES DESDE BUSQUEDA

		for (Flow_Participantes flow_Participantes : listaAuxParticipantes) {

			// LOS COMENTARIOS DE TODOS LOS PARTICIPANTES
			if (!isEmptyOrNull(flow_Participantes.getComentario())) {
				String cargo = flow_Participantes.getParticipante().getCargo() != null ? flow_Participantes
						.getParticipante().getCargo().getNombre()
						: "";

				String nomParticpante = flow_Participantes.getParticipante()
						.getNombre()
						+ " "
						+ flow_Participantes.getParticipante().getApellido()
						+ "  [" + cargo + "]";
				comentariosAllPartcipantes.append("\t\t")
						.append(nomParticpante).append("\n\t\t\t");
				comentariosAllPartcipantes.append(
						flow_Participantes.getComentario()).append("\n\n");
			}

			// para hacer el detalle reporte de participanbtes

			if (flow_Participantes != null
					&& flow_Participantes.getParticipante() != null) {
				SubFlowReporte subFlowReporteParticipante = new SubFlowReporte(
						++idSubreporteParticipante,
						"",
						flow_Participantes.getParticipante().getNombre() != null ? flow_Participantes
								.getParticipante().getNombre() : "",
						flow_Participantes.getParticipante().getApellido() != null ? flow_Participantes
								.getParticipante().getApellido() : "",
						flow_Participantes.getParticipante().getCargo() != null ? flow_Participantes
								.getParticipante().getCargo().getNombre()
								: "", flow_Participantes.getComentario(),
						messages.getString(flow_Participantes.getFirma()
								.getNombre()));
				// ESTOS DATOS SON SOLO PARA REPORTES EN BUSQUEDA.. ESTOS VIENEN
				// FALSOS SI NO ES DESDE BUSQUEDA
				//

				if (reporteBusquedaParticipante != null) {
					if ((reporteBusquedaParticipante.getId() - flow_Participantes
							.getParticipante().getId()) == 0) {
						swReporteBusquedaPorActor = true;
					}
				}
				if (swPorFirmarFlow) {
					// SI FILTRAMOS POR USUARIOS QUE FALTAN POR FIRMAR
					if (flow_Participantes.getFirma().getCodigo() == Utilidades
							.getSinFirmar()) {

						// SI TODO LOS USUARIOS QUE NO FIRMA
						listaSubFlowReporte.add(subFlowReporteParticipante);
						// FIN ESTOS DATOS SON SOLO PARA REPORTES EN BUSQUEDA..
						// ESTOS
						// VIENEN
						// FALSOS SI NO ES DESDE BUSQUEDA
						//
					}
				} else {
					listaSubFlowReporte.add(subFlowReporteParticipante);
				}

			}

			// VIENEN DE REPORTE BUSQUEDA
			if (reporteBusquedaParticipante != null
					&& reporteBusquedaParticipante.getId() > 0
					&& !swReporteBusquedaPorActor) {
				listaSubFlowReporte.clear();
			}
			session.setAttribute("listaSubFlowReporte", listaSubFlowReporte);
			// FIN VIENEN DE REPORTE BUSQUEDA
			// fin para hacer el detalle reporte de participanbtes

			// String comentarioAux = "";
			// SI ES SECUENCIAL, SOLO FIRMA UNA SOLA PÈRSONA, LAS DEMAS
			// QUEDAN EN COLA
			if (flow_Participantes.getFirma().getCodigo() == Utilidades
					.getSinFirmar()) {
				// si no es firmado.. se puede cambiar el suuario
				flow_Participantes.setToModFlow(toModFlow);

				// es un ciclo, swUnaSolaVez agarra el primer registro
				if (getFlow().isSecuencial() && swUnaSolaVez && swIsFlowActual) {
					setUsuarioFirmaSecuencial(flow_Participantes
							.getParticipante());
					// firmaPrincipalStr
					swUnaSolaVez = false;
					// solo el usuario logueado es el que firma
					if (flow_Participantes.getParticipante().getId()
							.equals(user_logueado.getId())) {
						flow_Participantes.setAuxdeFirma(true);
					} else {
						flow_Participantes.setAuxdeFirma(false);
					}

				} else if (!getFlow().isSecuencial()) {
					if (swIsFlowActual) {
						// solo el usuario logueado es el que firma
						if (flow_Participantes.getParticipante().getId()
								.equals(user_logueado.getId())) {
							flow_Participantes.setAuxdeFirma(true);
						} else {
							flow_Participantes.setAuxdeFirma(false);
						}
					}
				}
			}

			String mensBundle = messages.getString(flow_Participantes
					.getFirma().getNombre());
			flow_Participantes.setFirmaStr(mensBundle.toString());
			getFlow().setFirmaPrincipalStr(
					messages.getString(getFlow().getEstado().getNombre()));

			// verificamos que venga de un rol, si es asi mostramos es su
			// rol y no su nombre en la vista
			if (flow_Participantes.getRole() != null) {
				flow_Participantes.setSwRole(Utilidades.isActivo());
			}
			// VERIFICAMOS LOS TIEMPOS DE ENTREGA PARA CADA PARTICIPANTE
			// ME DEVUELVE UN SOLO PARTICIPANTE ESTE FOR
			List<FlowControlByUsuarioBean> flowControlByUsuarioBean = delegado
					.find_allControlTimeByFlowParticipAndFlow(flow_Participantes);
			for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
				super.calcularMinutosHorasAcumuladas(controlTime);

				flow_Participantes.setHorasAcumuladas(controlTime
						.getHorasAcumuladas());

				flow_Participantes.setMinutosAcumulados(controlTime
						.getMinutosAcumulados());
				flow_Participantes.setHorasAsignadas(controlTime
						.getHorasAsignadas());
				flow_Participantes.setMinutosAsignados(controlTime
						.getMinutosAsignados());
				flow_Participantes
						.setHorasRestantes(controlTime.getHorasAsignadas()
								- controlTime.getHorasAcumuladas());
				flow_Participantes.setMinutosRestantes(controlTime
						.getMinutosAsignados()
						- controlTime.getMinutosAcumulados());
				// si hay retrazos en minutos
				if (flow_Participantes.getMinutosRestantes() < 0) {
					if (flow_Participantes.getHorasRestantes() >= 1) {
						// convertimos los minutos en horas y restamos
						int minutosInHoras = (flow_Participantes
								.getMinutosRestantes() / 60);
						// agarramos los minutos restantes que son menores a
						// 60 minutos
						int minutosRestantes = (flow_Participantes
								.getMinutosRestantes() % 60);
						// restamos las horas con los minutos en horas que
						// calculamos
						flow_Participantes.setHorasRestantes(flow_Participantes
								.getHorasRestantes() - minutosInHoras);
						// colocamos los minutos
						flow_Participantes
								.setMinutosRestantes(minutosRestantes);
						// si es una hora, la convertimos en minutos y le
						// restamos los minutos restantesz
						if ((flow_Participantes.getHorasRestantes() == 1)
								&& flow_Participantes.getMinutosRestantes() != 0) {
							flow_Participantes.setHorasRestantes(0);
							flow_Participantes.setMinutosRestantes(60 - Math
									.abs(flow_Participantes
											.getMinutosRestantes()));
							// si es mas de una hora, decrem,entamos la hora
							// y restamos
							// 60 MINUTOS - MSNUTOS RESTANTES
						} else if ((flow_Participantes.getHorasRestantes() > 1)
								&& flow_Participantes.getMinutosRestantes() != 0) {
							flow_Participantes
									.setHorasRestantes(flow_Participantes
											.getHorasRestantes() - 1);
							flow_Participantes.setMinutosRestantes(60 - Math
									.abs(flow_Participantes
											.getMinutosRestantes()));
						}
					}
				}
			}
			// verfiicamos si el participnte anexo un archivo para poderlo
			// mostrar en el panel
			// aqui buscamois el archivo que attchment el usuario
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
			flow_ParticipantesAttachment
					.setFlowParticipantes(flow_Participantes);
			flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
			flow_ParticipantesAttachment = delegado
					.find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);

			if (flow_ParticipantesAttachment != null) {
				// mostramos en el panel
				flow_Participantes.setSwFlow_ParticipantesAttachment(true);

			}
			// si el comentario no viene vacio, al presdionar click en el
			// comentario,
			// en el metodo inicializar va el ecomentario
			if (!isEmptyOrNull(flow_Participantes.getComentario())) {
				flow_Participantes.setSwComentario(true);
			}

			// verificamos si el documento es de tipo svn
			// el documento original es de
			// flowparalelo->flow->doxc_detalle-doc_maestro
			flow_Participantes.setSwAgregarDocumentosvnUpload(false);
			if ((flow_ParticipantesFinFlow != null && flow_ParticipantesFinFlow
					.getFlow().getEstado().getCodigo()
					- Utilidades.getAprobado() == 0)) {
				flow_Participantes
						.setSwAgregarDocumentosvnUpload(flow_Participantes
								.getFlow().getFlowParalelo().getFlow()
								.getDoc_detalle().getDoc_maestro().isSwSVN());
			}

			// si no es tipo SVN , verificamos si ha anexado documentos SVN
			List<SvnUpload> svnUploadAttachment = null;
			if (!flow_Participantes.isSwAgregarDocumentosvnUpload()) {
				if (flow_Participantes != null
						&& flow_Participantes.getFlow() != null
						&& flow_Participantes.getFlow().getFlowParalelo() != null
						&& flow_Participantes.getFlow().getFlowParalelo()
								.getFlow() != null) {
					svnUploadAttachment = delegado
							.listUploadAttachmentSvn(flow_Participantes
									.getFlow().getFlowParalelo().getFlow());
				}

				if (svnUploadAttachment != null
						&& !svnUploadAttachment.isEmpty()) {

					if ((flow_ParticipantesFinFlow != null && flow_ParticipantesFinFlow
							.getFlow().getEstado().getCodigo()
							- Utilidades.getAprobado() == 0)) {
						flow_Participantes.setSwAgregarDocumentosvnUpload(true);
					}

				}
			}

			// puede realizar Flujo??.. si es el usuario del fin del flujo y
			// si... es el que esta logueado.. si..
			flow_Participantes.setSwCanRealizarFlow(false);
			if (flow_ParticipantesFinFlow != null
					&& (flow_ParticipantesFinFlow.getCodigo() - flow_Participantes
							.getCodigo()) == 0) {
				if (user_logueado != null
						&& (user_logueado.getId() - flow_Participantes
								.getParticipante().getId()) == 0)
					// si el flujo sta aprobado
					if ((flow_ParticipantesFinFlow.getFlow().getEstado()
							.getCodigo()
							- Utilidades.getAprobado() == 0)
							|| (flow_ParticipantesFinFlow.getFlow().getEstado()
									.getCodigo()
									- Utilidades.getRechazado() == 0)) {

						flow_Participantes.setSwCanRealizarFlow(true);

						// ESTE DOC_DETALLE ES PARA OBTENER EL TIPO DE DOCUMENTO
						// DEL
						// FLUJOPARALELO
						// Doc_detalle doc_detalle = new Doc_detalle();
						// doc_detalle.setDoc_maestro(doc_maestro);

						// user_logueado.setDoc_detall(doc_detalle);

						flow_Participantes.getParticipante().setDoc_detall(
								flows.getDoc_detalle());
						List lista = delegado.findParalelos(flow_Participantes
								.getParticipante());
						if (lista != null && !lista.isEmpty()) {
							flow_Participantes
									.setSwPuedeRealizarFlowPlantilla(true);
						}
					}
			}
			detallesParticipantes.add(flow_Participantes);
		}
	}

	public void setNomCompletoFlowDoc(String nomCompletoFlowDoc) {
		this.nomCompletoFlowDoc = nomCompletoFlowDoc;
	}

	public List<Flow> getFlowWithParticipantes() {
		flowWithParticipantes = session.getAttribute("flowWithParticipantes") != null ? (List<Flow>) session
				.getAttribute("flowWithParticipantes") : null;
		if (flowWithParticipantes == null) {
			flowWithParticipantes = flowParticipantesGetData();
			session.setAttribute("flowWithParticipantes", flowWithParticipantes);
		}

		return flowWithParticipantes;
	}

	public void setFlowWithParticipantes(List flowWithParticipantes) {
		this.flowWithParticipantes = flowWithParticipantes;
	}

	public String viewDocumentoPDF() {
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		session.setAttribute("onlyView", "onlyView");
		return "viewPDF";
	}

	public String viewComentarioFlow() {

		// se elimina de session los flowWithParticipantes para que estos sean
		// refrescados en
		// getFlowWithParticipantes()
		session.removeAttribute("flowWithParticipantes");

		boolean hayVerComentario = session.getAttribute("swViewComentario") != null;
		if (hayVerComentario == false) {
			session.setAttribute("swViewComentario", true);
			setSwViewComentario(true);

		} else {
			hayVerComentario = (Boolean) session
					.getAttribute("swViewComentario");
			boolean negamosActual = !hayVerComentario;
			session.setAttribute("swViewComentario", negamosActual);
			setSwViewComentario(negamosActual);
		}
		return "";
	}

	public void selectCommun(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null)
				&& (!super.isEmptyOrNull(component.getValue().toString()))) {
			long id = Long.parseLong(component.getValue().toString());
			Flow_Participantes flow_Participante = new Flow_Participantes();
			flow_Participante.setCodigo(id);
			flow_Participante = (Flow_Participantes) delegado
					.findFlow(flow_Participante);
			if (flow_Participante.getComentario() != null) {
				setCualquierComentario(flow_Participante.getComentario());
			} else {
				// inicializo el comentario principal del flujo
				setCualquierComentario("");
			}

		} // se va a firmar

	}

	public void selectCommunAttachmentByTree(ActionEvent event) {
		UIComponent component = (UIComponent) event.getSource();
		while (!(component != null)) {
			component = component.getParent();
		}
		if ((component != null)) {
			// HtmlTree tree = (HtmlTree) component;
			// tree.setNodeSelected(event);
			long id = 0;// Long.parseLong(tree.getNode().getIdentifier());
			Flow_Participantes flow_Participante = new Flow_Participantes();
			flow_Participante.setCodigo(id);
			flow_Participante = (Flow_Participantes) delegado
					.findFlow(flow_Participante);

			// aqui buscamois el archivo que attchment el usuario
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
			flow_ParticipantesAttachment
					.setFlowParticipantes(flow_Participante);
			flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
			flow_ParticipantesAttachment = delegado
					.find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);
			if (flow_ParticipantesAttachment != null) {
				session.setAttribute("attachment", flow_ParticipantesAttachment);

			}

		} // se va a firmar

	}

	public void selectCommunAttachment(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdAttach");
		if ((component != null)
				&& (!super.isEmptyOrNull(component.getValue().toString()))) {
			long id = Long.parseLong(component.getValue().toString());
			// System.out.println("------------id=" + id);
			Flow_Participantes flow_Participante = new Flow_Participantes();
			flow_Participante.setCodigo(id);
			flow_Participante = (Flow_Participantes) delegado
					.findFlow(flow_Participante);

			// aqui buscamois el archivo que attchment el usuario
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
			flow_ParticipantesAttachment
					.setFlowParticipantes(flow_Participante);
			flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
			flow_ParticipantesAttachment = delegado
					.find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);
			if (flow_ParticipantesAttachment != null) {
				session.setAttribute("attachment", flow_ParticipantesAttachment);
				// System.out.println("flow_ParticipantesAttachment codigo ="
				// + flow_ParticipantesAttachment.getCodigo());
			}

		} // se va a firmar

	}

	public String viewDocumentoAttachment() {

		// aqui buscamois el archivo que attchment el usuario
		Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
		flow_ParticipantesAttachment.setFlowParticipantes(flow_Participante);
		flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
		flow_ParticipantesAttachment = delegado
				.find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);
		if (flow_ParticipantesAttachment != null) {
			session.setAttribute("attachment", flow_ParticipantesAttachment);
			// System.out.println("flow_ParticipantesAttachment codigo ="
			// + flow_ParticipantesAttachment.getCodigo());
		}

		session.setAttribute("onlyView", "onlyView");
		Flow_ParticipantesAttachment detalle = (Flow_ParticipantesAttachment) session
				.getAttribute("attachment");
		return "viewAttachment";
	}

	public boolean isSwViewComentario() {
		return swViewComentario;
	}

	public void setSwViewComentario(boolean swViewComentario) {
		this.swViewComentario = swViewComentario;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public Doc_estado getFirma() {
		return firma;
	}

	public void setFirma(Doc_estado firma) {
		this.firma = firma;
	}

	public String getFirmarComentario() {
		return firmarComentario;
	}

	public void setFirmarComentario(String firmarComentario) {
		this.firmarComentario = firmarComentario;
	}

	public List getTipoFirma() {

		if (session.getAttribute("flow_Participante") != null) {
			Flow_Participantes flow_Participante = (Flow_Participantes) session
					.getAttribute("flow_Participante");
			Flow_Participantes flow_ParticipanteAnterior = delegado
					.devolucionFirmaFlow(flow_Participante);
			if (flow_ParticipanteAnterior != null) {
				// se le puede deviolver al participante anterior
				tipoFirma = datosCombo
						.getTipoDeFirmaWithParticpanteAnterior(flow_Participante
								.getFlow());
			} else {
				// detectar si antes de el alguien ha firmado...y es secuencial
				tipoFirma = datosCombo.getTipoDeFirma(flow_Participante
						.getFlow());
			}

		} else {
			if (flow != null) {
				tipoFirma = datosCombo.getTipoDeFirma(flow);
			} else {
				tipoFirma = datosCombo.getTipoDeFirma();
			}
		}
		return tipoFirma;
	}

	public void setTipoFirma(List tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public Usuario getUsuarioFirmaSecuencial() {
		return usuarioFirmaSecuencial;
	}

	public void setUsuarioFirmaSecuencial(Usuario usuarioFirmaSecuencial) {
		this.usuarioFirmaSecuencial = usuarioFirmaSecuencial;
	}

	public boolean isSwPuedeBorrarHistTree() {
		return swPuedeBorrarHistTree;
	}

	public void setSwPuedeBorrarHistTree(boolean swPuedeBorrarHistTree) {
		this.swPuedeBorrarHistTree = swPuedeBorrarHistTree;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public Usuario getDuenioFlow() {
		return duenioFlow;
	}

	public void setDuenioFlow(Usuario duenioFlow) {
		this.duenioFlow = duenioFlow;
	}

	public boolean isSwIsDuenio() {
		// dueño del flujo
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		swIsDuenio = false;
		boolean sw = false;
		if (getFlow() != null && getFlow().getEstado() != null) {
			sw = true;/*
					 * getFlow().getEstado().getCodigo()
					 * .equals(Utilidades.getEnAprobacion()) ||
					 * getFlow().getEstado().getCodigo()
					 * .equals(Utilidades.getEnRevision());
					 */
			duenioFlow = getFlow().getDuenio();
			duenioFlow.setSubFlow(getFlow().getNombredelflujo());
		} else {
			duenioFlow = new Usuario();
		}
		if (sw && (user_logueado != null && user_logueado.equals(duenioFlow))) {
			swIsDuenio = true;
		}
		return swIsDuenio;
	}

	public String listaPlantillaFlowParalelo() {

		boolean swFlowParalelo = true;
		session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				
		//EN LISTAR PLANYTILLAS,ESTA VARIABLE DE SESSION ME LA SETEABA EN BUSQUEDA Y POR ESO NO SALIAN LAS PLANTILLAS
				 session.setAttribute("strBuscar",null);

		String mensaje = flow_Participante.getFlow().getFlowParalelo()
				.getFlow().getNombredelflujo()
				+ "("
				+ flow_Participante.getFlow().getNombredelflujo()
				+ ":"
				+ flow_Participante.getFlow().getCodigo() + ")";
	 
		
		
		if (flow_Participante.getFlow().getFlowParalelo().getFlow()
				.getDoc_detalle() != null) {
			Tree treePadre = delegado.findTree(flow_Participante.getFlow()
					.getFlowParalelo().getFlow().getDoc_detalle()
					.getDoc_maestro().getTree().getNodopadre());

			session.setAttribute("treeNodoActual", getFlow().getFlowParalelo()
					.getFlow().getDoc_detalle().getDoc_maestro().getTree());
			flow_Participante
					.getFlow()
					.getFlowParalelo()
					.getFlow()
					.getDoc_detalle()
					.setOrigen(
							flow_Participante.getFlow().getFlowParalelo()
									.getFlow().getOrigen());
			session.setAttribute("doc_detalle", flow_Participante.getFlow()
					.getFlowParalelo().getFlow().getDoc_detalle());
			session.setAttribute("doc_detalleforflowplantilla",
					flow_Participante.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle());
			session.setAttribute("flowParalelo", flow_Participante.getFlow()
					.getFlowParalelo());
			session.setAttribute("flow_Participantes", flow_Participante);
			// me dice que pagina regresar
			session.setAttribute("flowsResponse", Utilidades.getFlowsResponse());
		}

		String pagIr = "";
		session.setAttribute("pagIr", Utilidades.getFlowsResponse());
		Flow_Participantes flow_Participantes = session
				.getAttribute("flow_Participantes") != null ? (Flow_Participantes) session
				.getAttribute("flow_Participantes") : null;

		return Utilidades.getListarflowparalelo();

	}

	public String agregardocumentosvnupload() {

		boolean swFlowParalelo = true;
		session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		String mensaje = flow_Participante.getFlow().getFlowParalelo()
				.getFlow().getNombredelflujo()
				+ "("
				+ flow_Participante.getFlow().getNombredelflujo()
				+ ":"
				+ flow_Participante.getFlow().getCodigo() + ")";
		if (flow_Participante.getFlow().getFlowParalelo().getFlow()
				.getDoc_detalle() != null) {
			Tree treePadre = delegado.findTree(flow_Participante.getFlow()
					.getFlowParalelo().getFlow().getDoc_detalle()
					.getDoc_maestro().getTree().getNodopadre());

			session.setAttribute("treeNodoActual", getFlow().getFlowParalelo()
					.getFlow().getDoc_detalle().getDoc_maestro().getTree());
			flow_Participante
					.getFlow()
					.getFlowParalelo()
					.getFlow()
					.getDoc_detalle()
					.setOrigen(
							flow_Participante.getFlow().getFlowParalelo()
									.getFlow().getOrigen());
			session.setAttribute("doc_detalle", flow_Participante.getFlow()
					.getFlowParalelo().getFlow().getDoc_detalle());
			session.setAttribute("doc_detalleforflowplantilla",
					flow_Participante.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle());
			session.setAttribute("flowParalelo", flow_Participante.getFlow()
					.getFlowParalelo());
			session.setAttribute("flow_Participantes", flow_Participante);
			// me dice que pagina regresar
			session.setAttribute("flowsResponse", Utilidades.getFlowsResponse());
		}

		String pagIr = "";
		// me dice que pagina regresar
		session.setAttribute("pagIr", Utilidades.getFlowsResponse());
		session.setAttribute("flowsResponse", Utilidades.getFlowsResponse());
		session.setAttribute("uploadSVN", true);

		session.removeAttribute("automaticoCargaSvn");
		if ("1".equalsIgnoreCase(automaticoCargaSvn)) {
			session.setAttribute("automaticoCargaSvn", true);
		}
		return Utilidades.getAgregardocumentosvnupload();

	}

	public Object getTreeData() {
		treeData = null;// session.getAttribute("treeData") != null ? (TreeNode)
						// session
		// .getAttribute("treeData") : null;
		/*
		 * TreeNode treeData = null; try {
		 * 
		 * List<Flow_Participantes> listaAuxParticipantes = delegado
		 * .findByFlowParticipantes(getFlow()); System.out.println("codigo=" +
		 * getFlow().getCodigo()); if (listaAuxParticipantes != null) {
		 * System.out.println("listaAuxParticipantestamaño:" +
		 * listaAuxParticipantes.size()); } else {
		 * System.out.println("lista NULA"); }
		 * 
		 * treeData = new TreeNodeBase(Utilidades.getIdentificaRaizTree(),
		 * messages.getString("flow_firmados") + " ", false);
		 * 
		 * for (Flow_Participantes f : listaAuxParticipantes) { if
		 * (!isEmptyOrNull(f.getComentario())){ TreeNode treeDetalle = new
		 * TreeNodeBase("", "", false);
		 * treeDetalle.setType(Utilidades.getIdentifica1Tree());
		 * treeDetalle.setIdentifier(String.valueOf(f.getParticipante()
		 * .getId()));
		 * treeDetalle.setDescription(f.getParticipante().getNombre() + " " +
		 * f.getParticipante().getApellido() + "[" +
		 * f.getParticipante().getCargo().getNombre() + "]");
		 * treeDetalle.setLeaf(false); treeData.getChildren().add(treeDetalle);
		 * 
		 * //colocamos el mensaje TreeNode treeDetalle1 = new TreeNodeBase("",
		 * "", false); treeDetalle1.setType(Utilidades.getIdentifica2Tree());
		 * 
		 * treeDetalle1.setDescription(f.getComentario());
		 * treeDetalle1.setLeaf(false);
		 * treeDetalle.getChildren().add(treeDetalle1); //si hay atachment
		 * //colocamos el mensaje Flow_ParticipantesAttachment
		 * flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
		 * flow_ParticipantesAttachment .setFlowParticipantes(f);
		 * flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
		 * flow_ParticipantesAttachment = delegado
		 * .find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment); if
		 * (flow_ParticipantesAttachment != null) { // mostramos en el panel
		 * TreeNode treeDetalle2 = new TreeNodeBase("", "", false);
		 * treeDetalle2.setType(Utilidades.getIdentifica3Tree());
		 * treeDetalle2.setIdentifier(String.valueOf(f.getParticipante()
		 * .getId())); treeDetalle2.setDescription("file");
		 * treeDetalle2.setLeaf(false);
		 * treeDetalle1.getChildren().add(treeDetalle2); }
		 * 
		 * 
		 * 
		 * } } } catch (Exception e) { e.printStackTrace(); }
		 */
		return treeData;
	}

	public void setSwIsDuenio(boolean swIsDuenio) {
		this.swIsDuenio = swIsDuenio;
	}

	public boolean isSwFueAprobado() {
		return swFueAprobado;
	}

	public void setSwFueAprobado(boolean swFueAprobado) {
		this.swFueAprobado = swFueAprobado;
	}

	public String getPrefix() {
		getNomCompletoFlowDoc();
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isSwAttachment() {
		return swAttachment;
	}

	public void setSwAttachment(boolean swAttachment) {
		this.swAttachment = swAttachment;
	}

	public boolean isSwPuedeRealizarSubFlow() {
		return swPuedeRealizarSubFlow;
	}

	public void setSwPuedeRealizarSubFlow(boolean swPuedeRealizarSubFlow) {
		this.swPuedeRealizarSubFlow = swPuedeRealizarSubFlow;
	}

	public boolean isSwAttachmentSVN() {
		return swAttachmentSVN;
	}

	public void setSwAttachmentSVN(boolean swAttachmentSVN) {
		this.swAttachmentSVN = swAttachmentSVN;
	}

	public File get_upFileSVN() {
		return _upFileSVN;
	}

	public void set_upFileSVN(File _upFileSVN) {
		this._upFileSVN = _upFileSVN;
	}

	public String getAutomaticoCargaSvn() {
		return automaticoCargaSvn;
	}

	public void setAutomaticoCargaSvn(String automaticoCargaSvn) {
		this.automaticoCargaSvn = automaticoCargaSvn;
	}

	public boolean isToModFlow() {
		return toModFlow;
	}

	public void setToModFlow(boolean toModFlow) {
		this.toModFlow = toModFlow;
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public Usuario getUsuarioRemplazo() {
		return usuarioRemplazo;
	}

	public void setUsuarioRemplazo(Usuario usuarioRemplazo) {
		this.usuarioRemplazo = usuarioRemplazo;
	}

	public Usuario getParticipanteOLd() {
		return participanteOLd;
	}

	public void setParticipanteOLd(Usuario participanteOLd) {
		this.participanteOLd = participanteOLd;
	}

	public Flow_Participantes getFlow_ParticipantesCambio() {
		return flow_ParticipantesCambio;
	}

	public void setFlow_ParticipantesCambio(
			Flow_Participantes flow_ParticipantesCambio) {
		this.flow_ParticipantesCambio = flow_ParticipantesCambio;
	}

	public List<SvnRutasRelativasFiles> getRutasRelativaSVN() {
		return rutasRelativaSVN;
	}

	public void setRutasRelativaSVN(
			List<SvnRutasRelativasFiles> rutasRelativaSVN) {
		this.rutasRelativaSVN = rutasRelativaSVN;
	}

	public String getTipoDeCambio() {
		return tipoDeCambio;
	}

	public void setTipoDeCambio(String tipoDeCambio) {
		this.tipoDeCambio = tipoDeCambio;
	}

	public List<Flow_Participantes> getParticipantesFlowsDetalle() {
		List<Flow> flowWithParticipantes = session
				.getAttribute("flowWithParticipantes") != null ? (List<Flow>) session
				.getAttribute("flowWithParticipantes") : null;

		if (flowWithParticipantes != null) {

			if (flowWithParticipantes.size() > 0) {
				participantesFlowsDetalle = flowWithParticipantes.get(0)
						.getParticipantesFlowsDetalle();

			}

		}

		return participantesFlowsDetalle;
	}

	public void setParticipantesFlowsDetalle(
			List<Flow_Participantes> participantesFlowsDetalle) {
		this.participantesFlowsDetalle = participantesFlowsDetalle;
	}

	public Flow_Participantes getFlow_Participante() {
		return flow_Participante;
	}

	public void setFlow_Participante(Flow_Participantes flow_Participante) {
		this.flow_Participante = flow_Participante;
	}

	public ArrayList<FileUploadEvent> getFilesPrimeFaces() {
		return filesPrimeFaces;
	}

	public void setFilesPrimeFaces(ArrayList<FileUploadEvent> filesPrimeFaces) {
		this.filesPrimeFaces = filesPrimeFaces;
	}

	public FileUploadEvent get_upFile() {
		return _upFile;
	}

	public void set_upFile(FileUploadEvent _upFile) {
		this._upFile = _upFile;
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

	public Seguridad getSeguridadTreeArbol() {
		return seguridadTreeArbol;
	}

	public void setSeguridadTreeArbol(Seguridad seguridadTreeArbol) {
		this.seguridadTreeArbol = seguridadTreeArbol;
	}

	public boolean isSwPermViewDoc() {
		return swPermViewDoc;
	}

	public void setSwPermViewDoc(boolean swPermViewDoc) {
		this.swPermViewDoc = swPermViewDoc;
	}

	public boolean isSwAttachmentDetalle() {
		return swAttachmentDetalle;
	}

	public void setSwAttachmentDetalle(boolean swAttachmentDetalle) {
		this.swAttachmentDetalle = swAttachmentDetalle;
	}

	public long getCodigoDetalle() {
		return codigoDetalle;
	}

	public void setCodigoDetalle(long codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}

	public boolean isToDoFlow() {
		return toDoFlow;
	}

	public void setToDoFlow(boolean toDoFlow) {
		this.toDoFlow = toDoFlow;
	}

	
	public String mostrarDocEnIframe(){
		 
		 
		return "";
	}
	public void mostrarDocEnIframeEvent(ActionEvent event) {
		Doc_detalle doc_detalle = (Doc_detalle) session
				.getAttribute("doc_detalle");
		
		if (session.getAttribute("swMostraDocIframe")!=null){
			swMostraDocIframe=(Boolean)session.getAttribute("swMostraDocIframe");
				session.setAttribute("swMostraDocIframe",!swMostraDocIframe);
				swMostraDocIframe=(Boolean)session.getAttribute("swMostraDocIframe");	
			
		}else{
			swMostraDocIframe=true;
			session.setAttribute("swMostraDocIframe",swMostraDocIframe);
		}
		if (swMostraDocIframe){
			session.setAttribute("onlyView", "onlyView");
			session.setAttribute("objeto", doc_detalle);
		}
	}
	public boolean isSwMostraDocIframe() {
		
		return swMostraDocIframe;
	}

	public void setSwMostraDocIframe(boolean swMostraDocIframe) {
		this.swMostraDocIframe = swMostraDocIframe;
	}


	
	public void mostrarDocEnIframeEvent2(ActionEvent event) {
		if (session.getAttribute("swMostraDocIframe2")!=null){
			swMostraDocIframe2=(Boolean)session.getAttribute("swMostraDocIframe2");
				session.setAttribute("swMostraDocIframe2",!swMostraDocIframe2);
				swMostraDocIframe2=(Boolean)session.getAttribute("swMostraDocIframe2");	
			
		}else{
			swMostraDocIframe2=true;
			session.setAttribute("swMostraDocIframe2",swMostraDocIframe2);
		}
//		if (swMostraDocIframe2){
//		}
	}
	public boolean isSwMostraDocIframe2() {
		
		return swMostraDocIframe2;
	}

	public void setSwMostraDocIframe2(boolean swMostraDocIframe2) {
		this.swMostraDocIframe2 = swMostraDocIframe2;
	}

	
}
