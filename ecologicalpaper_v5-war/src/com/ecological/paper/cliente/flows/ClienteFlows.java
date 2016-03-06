/*
} * ClienteFlows.java
 *
 * Created on August 2, 2007, 8:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

/**
 * 
 * @author simon
 */
public class ClienteFlows extends ClienteSeguridad {
	// seguridad
	private Seguridad seguridadTree = new Seguridad();
	private Seguridad seguridadMenu = new Seguridad();
	private Tree treeNodoActual;

	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	private DatosCombo datosCombo = new DatosCombo();

	private HttpSession session = super.getSession();
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

	private boolean tipoFlow;
	private Usuario user_logueado;
	private boolean swDeshabilitarflowAprobacion;
	private boolean swDeshabilitarflowAprobacionParalelo;
	private boolean swTipoFormatoPlantilla;
	// private List mandarMailUsuarios;
	private FlowParalelo flowParalelo;
	// guardar una plantilla de flujos para un documento tipo plantilla
	private boolean toPlantillaInDocFlowParalelo;
	private Solicitudimpresion solicitudimpresion;
	private boolean swSolicitudimpresion;
	private boolean swCanSolicitudimpresion;
	private boolean swDeshabilitarEditaSolicitudimpresion;
	private String tipoDocumento;
	private String areaDocumentos;
	private boolean swCrearHijoDeFlowParalelo;
	private boolean swSi = true;
	private boolean swFlowParaleloSession;
	private boolean swEditando;

	// NUEVA SLISTAS
	private List<Role> invisibleItems = new ArrayList<Role>();
	private List<Role> visibleItems = new ArrayList<Role>();
	private List<Role> invisibleItemsNotificacion = new ArrayList<Role>();
	private List<Role> visibleItemsNotificacion = new ArrayList<Role>();
	private DualListModel<Role> operacionesRole;
	private DualListModel<Role> operacionesRoleNotificacion;
	private DualListModel<Usuario> operacionesUsuario;
	private List<Usuario> invisibleItemsUsuario = new ArrayList<Usuario>();
	private List<Usuario> visibleItemsUsuario = new ArrayList<Usuario>();
	private List<Flow> participantesGruposPlantila = new ArrayList<Flow>();

	public ClienteFlows(String vacio) {

	}

	/** Creates a new instance of ClienteFlows */
	public ClienteFlows() {

		inicializa();
		// PARA HABILITAR LOS TABS...
		isSwRoleVisible();
		isSwUserVisible();
		isSwPrincipalVisible();
	}

	public Flow buscaFlowDocDetalle(Doc_detalle doc_detalle) {
		Flow f = new Flow();
		//COLOCAMOS POR DEFECTO DE SOLO LECTURA EL FLOW SI SE VA HACER POR PRIMERA VEZ
		f.setLectores(true);
		// CUANDO SE EDITA LOS FLUJOS..TRAE ESTA VARIABLE INTERNA
		if (doc_detalle.getFlowEditar() != null) {
			f = doc_detalle.getFlowEditar();
			return f;
		}
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (doc_detalle == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		}
		if (doc_detalle != null) {

			List flows = delegado.findDocumentoDetalleInFlow(doc_detalle);
			if (flows != null) {
				int size = flows.size();
				int j = 0;
				for (int i = 0; i < size; i++) {
					f = (Flow) flows.get(i);
					j = i;
				}
				if (j > 1) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											messages.getString("error_borr_detlle_flowsVariosenBD")));
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_doc_edit")));
		}
		return f;
	}

	public void inicializa() {
		session = super.getSession();
		setTree(session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null);

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		session.setAttribute("mostrarCatalogo", true);
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// AQUI SABEMOS SI ESTA EDITANDO EL FLUJO
		swEditando = session.getAttribute("listarflowsParaleCambiarNomComent") != null;

		// FIN AQUI SABEMOS SI ESTA EDITANDO EL FLUJO

		// cfin olocamos por defecto que los flujos sean de revision
		// CONSEGUIMOS LA SEGURIDAD TREE
		// inbicializamos a revision
		setTipoFlow(false);
		if (treeNodoActual != null) {
			seguridadTree = super.getSeguridadTree(treeNodoActual);

			if (seguridadTree.isToDoFlow()) {
				// aprobacion son true
				setTipoFlow(true);
				// tipoFlow = true;
			} else if (seguridadTree.isToDoFlowRevision()) {
				setTipoFlow(false);
				// tipoFlow = false;
			}
		}

		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		if (seguridadMenu.istoPlantillaInDocFlowParalelo()) {
			toPlantillaInDocFlowParalelo = true;
		}

		// REVISAMOS SI SE PUEDE HACER UNA PLANTILLA PARA LA SOLICITUD DE
		// IMPRESION
		FlowParalelo flowParaleloBusqueda = new FlowParalelo();
		flowParaleloBusqueda.setUsuario(user_logueado);
		try {
			if (seguridadMenu.isToImprimirAdministrar()) {
				swCanSolicitudimpresion = !(delegado
						.findExistePlantillaImpresionFlow(flowParaleloBusqueda));
			}
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// FIN REVISAMOS SI SE PUEDE HACER UNA PLANTILLA PARA LA SOLICITUD DE
		// IMPRESION

		Doc_detalle doc_detalle = null;
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");

		// BUSCAMOS SI ESTE DOC-DETALLE ES PLANTILLA Y SE GUARDA COMO
		// PLANTILLAFLOWS EN LOS FLOWSPARALELOS
		// if (Utilidades.getFormatoTipoDoc()
		// - doc_detalle.getDoc_maestro().getDoc_tipo()
		// .getFormatoTipoDoc() == 0) {
		FlowParalelo flowParaleloPlantillaDoc_Detalle = new FlowParalelo();
		// esto es verificar si se puede hacer en flowparalelo un doctipo
		// que referencie una plantilade documento
		// y en flow paralelo se guarda para tener una plantilla de flujo
		// enlazado con una plantilla o formato de documento
		// LO BUSCAMOS SOLO POR TIPO DE DOCUMENTO Y POR LA EMPRESA QUE
		// PERTENEZCA AL USUARIO
		flowParaleloPlantillaDoc_Detalle.setAreaDocumentos(doc_detalle
				.getAreaDocumentos());
		flowParaleloPlantillaDoc_Detalle.setDoc_tipo(doc_detalle
				.getDoc_maestro().getDoc_tipo());
		flowParaleloPlantillaDoc_Detalle.setUsuario(user_logueado);
		flowParaleloPlantillaDoc_Detalle = delegado
				.findPlantillaDoc_DetallePlantillaInFlowParalelo(flowParaleloPlantillaDoc_Detalle);

		FlowParalelo flowParaleloSession = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;
		// AREA DE LA IMPRESION
		if (flowParaleloSession != null) {
			swDeshabilitarEditaSolicitudimpresion = flowParaleloSession
					.isSolicituImpresion();
		}
		// FIN AREA DE LA IMPRESION

		// SI ES NULO..O NO PUEDE CREAR UNA PLANTILLAFLUJO PARA ESTE DOCUMENTO
		// PLANTILLA
		swTipoFormatoPlantilla = true;
		// FIN SI ES NULO O NO..PUEDE CREAR UNA PLANTILLAFLUJO PARA ESTE
		// DOCUMENTO
		// PLANTILLA

		if (flowParaleloPlantillaDoc_Detalle == null) {
			swTipoFormatoPlantilla = true;

			// SI SELECCIONAMOS ESTE DOCUMENTO PLANTILLA PARA ESTAR EN
			// UJN
			// FLOWSPLANTILLA
			// SI HAY FLUJOS PARALELOS ANIDADOIS.. SE ESCOJIO LA PRIMERA
			// VEZ
			// Y POR ESO VALIDAMOS QUE YA
			// FUERA ESCOJIDO PARA NO SOBREESCRIBIR FLOWSPLANTILLA EN
			// LOS
			// FLUJOS PARALELOS
			// TRATAMIENTO DE FLUJOS DE TIPOD DOCUM,ENTO PLANTILLAS

			if (flowParaleloSession != null
					&& flowParaleloSession.isSwTipoPlantilladocumento()) {

				areaDocumentos = doc_detalle.getAreaDocumentos() != null ? doc_detalle
						.getAreaDocumentos().getNombre() : "";
				tipoDocumento = flowParaleloSession.getFlow().getFlowParalelo()
						.getFlow().getDoc_detalle().getDoc_maestro()
						.getDoc_tipo().getNombre();
				swFlowParaleloSession = true;

			} else {
				areaDocumentos = doc_detalle.getAreaDocumentos() != null ? doc_detalle
						.getAreaDocumentos().getNombre() : "";
				tipoDocumento = doc_detalle.getDoc_maestro().getDoc_tipo()
						.getNombre();
			}

			// FIN TRATAMIENTO DE FLUJOS DE TIPOD DOCUM,ENTO PLANTILLAS

		}

		// }
		
		//colocamos por defecto swFlowParaleloSession = true si puede ser una plantilla global para  un tipo de documento
		//swFlowParaleloSession=true;
		// Este flow se buscan para obtener los rooes seleccionados..
		Flow f = null;

		f = buscaFlowDocDetalle(doc_detalle);

		// colocamos por defecto que los flujos sean de aprobacion
		// este es negado
		flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;

				
		if (flowParalelo != null) {
			swDeshabilitarflowAprobacionParalelo = true;
			f.setPlantilla(flowParalelo.getFlow().isPlantilla());
		}
		setFlow(f);

		// llenamos ROLES
		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		invisibleItemsNotificacion = new ArrayList();
		visibleItemsNotificacion = new ArrayList();
		invisibleItemsNotificacion.clear();
		visibleItemsNotificacion.clear();
		invisibleItems.clear();
		visibleItems.clear();
		
		
		datosCombo.llenarRoleFlowVisiblesRichFaces(visibleItems,
				invisibleItems, f);
		datosCombo.llenarRoleFlowVisiblesRichFacesNotificacion(visibleItemsNotificacion,
				invisibleItemsNotificacion,  flow);

		List<Role> roles = new ArrayList<Role>();
		roles.addAll(invisibleItems);
		roles.addAll(visibleItems);
		//se usa para describir que llevan internamente los grupos
		participantesGruposPlantila = participantesGruposPlantila(roles);

		operacionesRole = new DualListModel<Role>(invisibleItems, visibleItems);
		operacionesRoleNotificacion = new DualListModel<Role>(invisibleItemsNotificacion, visibleItemsNotificacion);
		// llenamos usuarios
		visibleItemsUsuario = new ArrayList();
		invisibleItemsUsuario = new ArrayList();
		datosCombo.llenarUsuariosFlowVisiblesRichFaces(visibleItemsUsuario,
				invisibleItemsUsuario, doc_detalle);
		operacionesUsuario = new DualListModel<Usuario>(invisibleItemsUsuario,
				visibleItemsUsuario);


	}

	public String cancelar() {
		String pagIr = "";
		try {
			// si viene de editar plantillas
			pagIr = session.getAttribute("listarflowsParaleCambiarNomComent") != null ? (String) session
					.getAttribute("listarflowsParaleCambiarNomComent") : "";
			session.removeAttribute("listarflowsParaleCambiarNomComent");
			if (isEmptyOrNull(pagIr)) {
				pagIr = "listar";
				// super.clearSession(session,
				// confmessages.getString("usuarioSession"));

			}

		} catch (Exception e) {
			redirect("ClienteFlows", "cancelar", e);
		}
		return pagIr;
	}

	public String saveDatosBasicos_action() {
		String pagIr = "";
		try {
			// verificanmos con inicializa, que ya hay almacenados participantes
			// y roles participantes
			Doc_detalle doc_detalle = null;
			List noSeUsa = new ArrayList();
			doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
			// llenamos los dartos que se escojio para el flujo
			visibleUsersFlows.clear();
			datosCombo.llenarUsuariosFlowVisibles(visibleUsersFlows, noSeUsa,
					doc_detalle);
			visibleRoleFlows.clear();
			// llenamos los roles que se escojio para el flujo
			datosCombo.llenarRoleFlowVisibles(visibleRoleFlows, noSeUsa,
					getFlow());

			if (!super.isEmptyOrNull(flow.getComentarios())) {
				if ((!visibleUsersFlows.isEmpty())
						|| (!visibleRoleFlows.isEmpty())) {

					// esta F viene si estamos editando un flow, solo es de
					// referencia
					Flow f = buscaFlowDocDetalle(doc_detalle);
					// el optionbutrton me trae este valor si es de aprobacion o
					// revision

					// ASIEMPRE QUE VALLAS HACER FLUJOS PARALELOS.. ESTOS DEBEN
					// SER TIPO APROBACION
					flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
							.getAttribute("flowParalelo") : null;
					if (flowParalelo != null) {

						// si es flowparalelo, entonces es de aprobacion
						// tipoFlow = true;
						setTipoFlow(true);
						f.setPlantilla(flowParalelo.getFlow().isPlantilla());

					} else {
						f.setPlantilla(flow.isPlantilla());
					}
					// si es flowparalelo, entonces es de aprobacion

					if (isTipoFlow()) {
						flow.setTipoFlujo(Utilidades.getEnAprobacion() + "");
					} else {
						flow.setTipoFlujo(Utilidades.getEnRevision() + "");
					}
					// esta F viene si estamos editando un flow, solo es de
					// referencia

					f.setComentarios(flow.getComentarios());
					f.setSecuencial(flow.isSecuencial());
					f.setCondicional(flow.isCondicional());
					f.setLectores(flow.isLectores());

					f.setNotificacionMail(flow.isNotificacionMail());
					f.setStatus(true);
					// buscamos que tipo de estado es segun el tipo de flujo que
					// se selecciono
					// si es en revisiuon o aprobacion
					Doc_estado doc_estado = new Doc_estado();
					if (flow.getTipoFlujo() != null) {
						f.setTipoFlujo(flow.getTipoFlujo());
						doc_estado
								.setCodigo(Long.parseLong(flow.getTipoFlujo()));
						doc_estado = delegado.findDocEstado(doc_estado);
						f.setEstado(doc_estado);
					}

					// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA COMO
					// PLANTILA, COMO APROBACION,LECTOR
					if (swSolicitudimpresion) {
						f.setLectores(true);
						f.setTipoFlujo(Utilidades.getEnAprobacion() + "");
						f.setPlantilla(true);

						flow.setLectores(true);
						flow.setTipoFlujo(Utilidades.getEnAprobacion() + "");
						flow.setPlantilla(true);
						flow.setOrigen(Utilidades.getOrigenflowimpresor());
						flow.setNombredelflujo(messages
								.getString("solicitudImpresion"));
					} else {

						flow.setOrigen(Utilidades.getOrigenDocumentoFlow());

					}

					// FIN, SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA COMO
					// PLANTILA, COMO APROBACION,LECTOR

					flow.setEstado(doc_estado);
					try {

						// -----------------------------------
						List<FlowControlByUsuarioBean> flowControlByUsuarioBeans = delegado
								.find_allControlTimeByFlowParticipAndRole(flow);
						for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBeans) {
							if (controlTime.getHorasAsignadas() != 0
									|| controlTime.getMinutosAsignados() != 0) {
								// buscamos si el flujo es secuenial
								if (flow.isSecuencial()) {
									controlTime.setSwStartHilo(Utilidades
											.isInactivo());
									delegado.edit(controlTime);
								} else {
									controlTime.setSwStartHilo(Utilidades
											.isActivo());
									delegado.edit(controlTime);
								}
							}

						}

						// ya los participante sfueron cargados en la base de
						// datos al escojer en participantes
						if (flow.isSecuencial()) {
							List<Flow_Participantes> listaAuxParticipantes = delegado
									.findByFlowParticipantes(getFlow());
							for (Flow_Participantes fParticipante : listaAuxParticipantes) {

								// BUSCAMOS EL CONTROL DE TIEMPO DE ESE
								// PARTICIPANTE Y LO ACTIVAMOS
								List<FlowControlByUsuarioBean> flowControlByUsuarioBean = delegado
										.find_allControlTimeByFlowParticipAndFlow(fParticipante);
								for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
									// activamos sus hilo para empieze a contar
									// su tiempo
									controlTime.setSwStartHilo(true);
									delegado.edit(controlTime);
									break;// primer for
								}

								break;
							}
						}

						// senial de hacer desde aqui flujos paralelos..
						flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
								.getAttribute("flowParalelo") : null;
						boolean swFlowParaleloPrimeraVez = false;
						if (flowParalelo == null) {
							swFlowParaleloPrimeraVez = true;
							flowParalelo = new FlowParalelo();
							flowParalelo.setFlow(flow);
							flowParalelo.setUsuario(user_logueado);
							if (isEmptyOrNull(flowParalelo.getNombre())) {
								flowParalelo
										.setNombre(flow.getNombredelflujo());
							}

							// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA
							// COMO PLANTILA, COMO APROBACION,LECTOR
							if (swSolicitudimpresion) {
								flowParalelo.setSolicituImpresion(true);
								flowParalelo.setNombre(messages
										.getString("solicitudImpresion"));
							}
							// FIN, SI ES UNA SOLICITUD DDE IMPRESION , SE
							// COLOCA COMO PLANTILA, COMO APROBACION,LECTOR
							delegado.create(flowParalelo);
							session.setAttribute("flowParalelo", flowParalelo);

						}
						// -----------------------------------

						// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
						// MANDARLES UN MAIL
						// EWSTO YA SE HACE CON FLUJOS PARALELOS.. AL CREARLO
						// DESDE UN FLUJO PARALELO
						if (!f.isPlantilla()) {
							// if (flow.isNotificacionMail()) {
							// MyHiloEnvioMails myHiloEnvioMails = new
							// MyHiloEnvioMails(
							// mandarMailsUsuarios(flow));
							// myHiloEnvioMails.start();
							// AL ACEPTAR EN FLOWSPARALELO.JSP.. ESTE COLOCA
							// TODOS LOS FLUJOS EN TRUE SI NO SON PLANTILLAS
							// NO C PUDO POR AHORA.., ASINQUE ENVIAMOS EL
							// FLUJO
							// flow.setStatus(false);
							//
							// }
							if (flow.getTipoFlujo().equalsIgnoreCase(
									Utilidades.getEnAprobacion() + "")) {
								flow.setStatus(false);
							} else {
								// los flujos de impresion o revision son
								// sencillos
								flow.setStatus(true);
							}

						} else if (f.isPlantilla()) {
							// SOLO LAS COPIAS DE ESTOS FLUJOS, A TRAVES DE
							// FLUJOS PARALELOS .. SERAN LOS QUE SE MANDEN AL
							// USUARIO
							// COMO SE TRAMITARA POR FLUJOS PARALELOS..ESTO
							// ESTARAN INACTIVOS

							flow.setStatus(false);
						}

						// EN CASO QUE EL FLUJOS ESTE CREADO Y AL FINAL DEL
						// FLUJO, UN PARTICIPANTE DEL
						// FLKUJO DECIDE AGREGAR UN NUEVO FLUJO, ENTONCES LA
						// VARIABLE SESSION flow_Participante VIENE
						// DE CLIENTEFLORESPONSE->versionId(ActionEvent event)

						// SI ES FLUJO DE REVISION ... DESCOMPONEMOS LOS
						// PARTICIPANTES DEL ROL AQUI MISO
						if (flow.getTipoFlujo().equalsIgnoreCase(
								Utilidades.getEnRevision() + "")) {

							// buscamos los roles y los descomponemos en
							// flows_participantes, ya los usuarios escojidos
							// individualemnte fueron
							// almacenados en flows_participantes
							descomponerRoleSaveFlowParticipantes(flow);
						}
						// FIN SI ES FLUJO DE REVISION ... DESCOMPONEMOS LOS
						// PARTICIPANTES DEL ROL AQUI MISO

						Flow_Participantes flow_Participantes = session
								.getAttribute("flow_Participante") != null ? (Flow_Participantes) session
								.getAttribute("flow_Participante") : null;

						flow.setFlowParalelo(flowParalelo);
						flow.setFlow_Participantes(flow_Participantes);

						delegado.edit(flow);

						// si es editar plantilla.. se va para editar plantillas
						// que es
						// session.setAttribute("listarflowsParaleCambiarNomComent",
						// Utilidades.getListarflowsparalecambiarnomcoment());

						if (isEmptyOrNull(pagIr)) {
							if (!swSolicitudimpresion
									&& flow.getTipoFlujo().compareTo(
											"" + Utilidades.getEnAprobacion()) == 0) {
								pagIr = "flowparalelo";
							} else {
								// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA
								// COMO PLANTILA, COMO APROBACION,LECTOR
								if (swSolicitudimpresion) {
									session.setAttribute(
											"mensaje",
											messages.getString("flowsolicitudImpresion"));
								}
								session.setAttribute("pagIr",
										Utilidades.getListarDocumentos());
								pagIr = Utilidades.getFinexitoso();
							}
						}
						// final senial de hacer desde aqui flujos paralelos..
						// ____________________________________

						// que no sea plantilla y que el flujo no seacreado por
						// un participante..
						// swGuardaHistoricoPrimeraVez significa que es la
						// primera vez que se crea en flujo paralelo, es decir..
						// no sta guardada en session el flujo paralelo para
						// generar nuevos flujos con el
						// mismo flujo paralelo
						if (!f.isPlantilla() && swFlowParaleloPrimeraVez
								&& flow_Participantes == null) {
							// ____________________________________________________
							// guardamos el histoico
							FlowsHistorico flowsHistorico = new FlowsHistorico();
							flowsHistorico.setFlow(flow);
							flowsHistorico.setDoc_detalle(doc_detalle);
							flowsHistorico.setStatus(true);
							delegado.create(flowsHistorico);
							// ____________________________________________________

							// ____________________________________
							// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
							user_logueado = (Usuario) session
									.getAttribute("user_logueado");
							boolean verSometerFlow = true;

							super.guardamosHistoricoActivoDelDocumento(
									user_logueado,
									doc_detalle.getDoc_maestro(), false, false,
									false, false, verSometerFlow, false, false,
									false, "");
						}

					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages.getString("error")
										+ e.toString()));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_flowParticipantes")));
				}
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

		}
		return pagIr;
	}

	public String saveDatosBasicos_actionRichFaces() {

		
		// INICLIAZAMOS LOS DOCUMENTOS DETALLES
		session.removeAttribute("doc_detallesAux");
		session.removeAttribute("clienteDocumentoMaestrosPanelControl");
		session.removeAttribute("clienteDocumentoMaestros");
		// FIN INICLIAZAMOS LOS DOCUMENTOS DETALLES
		String pagIr = "";

		List<Usuario> usuarioSeleccionados = operacionesUsuario.getTarget();
		List<Role> roles = operacionesRole.getTarget();
		List<Role> rolesNotificados = operacionesRoleNotificacion.getTarget();

		try {
			// verificanmos con inicializa, que ya hay almacenados participantes
			// y roles participantes
			Doc_detalle doc_detalle = null;
			List noSeUsa = new ArrayList();
			doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");

			if (!super.isEmptyOrNull(flow.getComentarios())) {
				if ((!usuarioSeleccionados.isEmpty()) || (!roles.isEmpty())) {
					// SE GRABAN LAS OPERACIONES DEL FLUJOS

					if (swSolicitudimpresion) {

						doc_detalle.setOrigen(Utilidades
								.getOrigenflowimpresor());

					} else {

						doc_detalle.setOrigen(Utilidades
								.getOrigenDocumentoFlow());

					}

					// if (!usuarioSeleccionados.isEmpty()) {

					// ESTOS CREAN INTERNAMENTE EL FLOW
					delegadoEcological.saveUsuarioOperacionesFlows(tree,
							user_logueado, doc_detalle, usuarioSeleccionados);
					// si se creo el flow, doc_detalle tiene un parametro
					// llamadpo
					// flow_reditar que se le fue
					// asignao el flow que se creo
					session.setAttribute("doc_detalle", doc_detalle);
					// FIN SE GRABAN LAS OPERACIONES DEL FLUJOS
					// }

					// if (!roles.isEmpty()) {
					// SE GRABAN LOS ROLES
					// ESTOS CREAN INTERNAMENTE EL FLOW
					delegadoEcological.saveRoleOperacionesFlows(tree,
							user_logueado, doc_detalle, roles);
					//SOLO PARA LOS GRUPOS QUE SE ESCOJIERON POARA NOTIFICARLOS SOLAMENTE
					delegadoEcological.saveRoleOperacionesFlowsNotificacion(tree, user_logueado,
							 doc_detalle, rolesNotificados);
					// si se creo el flow, doc_detalle tiene un parametro
					// llamadpo
					// flow_reditar que se le fue
					// asignao el flow que se creo
					session.setAttribute("doc_detalle", doc_detalle);
					// FIN SE GRABAN LOS ROLES
					// }

					// esta F viene si estamos editando un flow, solo es de
					// referencia
					Flow f = buscaFlowDocDetalle(doc_detalle);

					// el optionbutrton me trae este valor si es de aprobacion o
					// revision

					// ASIEMPRE QUE VALLAS HACER FLUJOS PARALELOS.. ESTOS DEBEN
					// SER TIPO APROBACION
					flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
							.getAttribute("flowParalelo") : null;
					if (flowParalelo != null) {

						// si es flowparalelo, entonces es de aprobacion
						// tipoFlow = true;
						setTipoFlow(true);
						f.setPlantilla(flowParalelo.getFlow().isPlantilla());

					} else {
						f.setPlantilla(flow.isPlantilla());
					}
					// si es flowparalelo, entonces es de aprobacion

					if (isTipoFlow()) {
						flow.setTipoFlujo(Utilidades.getEnAprobacion() + "");
						f.setTipoFlujo(Utilidades.getEnAprobacion() + "");

					} else {
						flow.setTipoFlujo(Utilidades.getEnRevision() + "");
						f.setTipoFlujo(Utilidades.getEnRevision() + "");

					}
					// esta F viene si estamos editando un flow, solo es de
					// referencia

					// INICIO CHEQUEMOS PARA FLOW, SI ES REVISION .. NUNCA SERA
					// PLANTILLA
					if (flow.getTipoFlujo().equalsIgnoreCase(
							Utilidades.getEnRevision() + "")) {
						flow.setPlantilla(false);
						f.setPlantilla(false);

					}
					// FIN CHEQUEMOS PARA FLOW, SI ES REVISION .. NUNCA SERA
					// PLANTILLA
					f.setNombredelflujo(flow.getNombredelflujo());
					f.setComentarios(flow.getComentarios());
					f.setSecuencial(flow.isSecuencial());
					f.setCondicional(flow.isCondicional());
					f.setLectores(flow.isLectores());

					f.setNotificacionMail(flow.isNotificacionMail());
					f.setStatus(true);
					// buscamos que tipo de estado es segun el tipo de flujo que
					// se selecciono
					// si es en revisiuon o aprobacion
					Doc_estado doc_estado = new Doc_estado();

					if (flow.getTipoFlujo() != null) {
						f.setTipoFlujo(flow.getTipoFlujo());
						doc_estado
								.setCodigo(Long.parseLong(flow.getTipoFlujo()));
						doc_estado = delegado.findDocEstado(doc_estado);
						f.setEstado(doc_estado);
					}
					// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA COMO
					// PLANTILA, COMO APROBACION,LECTOR
					if (swSolicitudimpresion) {
						f.setLectores(true);
						f.setTipoFlujo(Utilidades.getEnAprobacion() + "");
						f.setPlantilla(true);

						f.setLectores(true);
						f.setTipoFlujo(Utilidades.getEnAprobacion() + "");
						f.setPlantilla(true);
						f.setOrigen(Utilidades.getOrigenflowimpresor());
						f.setNombredelflujo(messages
								.getString("solicitudImpresion"));
					} else {

						f.setOrigen(Utilidades.getOrigenDocumentoFlow());

					}

				 

					// FIN, SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA COMO
					// PLANTILA, COMO APROBACION,LECTOR

					f.setEstado(doc_estado);
					try {

						// -----------------------------------
						List<FlowControlByUsuarioBean> flowControlByUsuarioBeans = delegado
								.find_allControlTimeByFlowParticipAndRole(f);
						for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBeans) {
							if (controlTime.getHorasAsignadas() != 0
									|| controlTime.getMinutosAsignados() != 0) {
								// buscamos si el flujo es secuenial
								if (flow.isSecuencial()) {
									controlTime.setSwStartHilo(Utilidades
											.isInactivo());
									delegado.edit(controlTime);
								} else {
									controlTime.setSwStartHilo(Utilidades
											.isActivo());
									delegado.edit(controlTime);
								}
							}

						}

						// ya los participante sfueron cargados en la base de
						// datos al escojer en participantes
						if (flow.isSecuencial()) {
							List<Flow_Participantes> listaAuxParticipantes = delegado
									.findByFlowParticipantes(getFlow());
							for (Flow_Participantes fParticipante : listaAuxParticipantes) {

								// BUSCAMOS EL CONTROL DE TIEMPO DE ESE
								// PARTICIPANTE Y LO ACTIVAMOS
								List<FlowControlByUsuarioBean> flowControlByUsuarioBean = delegado
										.find_allControlTimeByFlowParticipAndFlow(fParticipante);
								for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
									// activamos sus hilo para empieze a contar
									// su tiempo
									controlTime.setSwStartHilo(true);
									delegado.edit(controlTime);
									break;// primer for
								}

								break;
							}
						}

						// senial de hacer desde aqui flujos paralelos..
						flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
								.getAttribute("flowParalelo") : null;
						boolean swFlowParaleloPrimeraVez = false;
						if (flowParalelo == null) {
							swFlowParaleloPrimeraVez = true;
							flowParalelo = new FlowParalelo();
							// INICIO ESDTE FLUJO ES EL QUE YA VIENEN GUARDADO
							// EN BASE DE DATOS..
							flowParalelo.setFlow(f);
							if (f.isPlantilla()){
								flowParalelo.setSwTipoPlantilladocumento(true);
							}
							// FIN
							flowParalelo.setUsuario(user_logueado);
							if (isEmptyOrNull(flowParalelo.getNombre())) {
								flowParalelo
										.setNombre(flow.getNombredelflujo());
							}
							// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA
							// COMO PLANTILA, COMO APROBACION,LECTOR
							if (swSolicitudimpresion) {
								flowParalelo.setSolicituImpresion(true);
								flowParalelo.setNombre(messages
										.getString("solicitudImpresion"));
							}
							// FIN, SI ES UNA SOLICITUD DDE IMPRESION , SE
							// COLOCA COMO PLANTILA, COMO APROBACION,LECTOR
							delegado.create(flowParalelo);
							session.setAttribute("flowParalelo", flowParalelo);

						}
						// -----------------------------------

						// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
						// MANDARLES UN MAIL
						// EWSTO YA SE HACE CON FLUJOS PARALELOS.. AL CREARLO
						// DESDE UN FLUJO PARALELO

						if (!f.isPlantilla()) {
							// if (flow.isNotificacionMail()) {
							// MyHiloEnvioMails myHiloEnvioMails = new
							// MyHiloEnvioMails(
							// mandarMailsUsuarios(flow));
							// myHiloEnvioMails.start();
							// AL ACEPTAR EN FLOWSPARALELO.JSP.. ESTE COLOCA
							// TODOS LOS FLUJOS EN TRUE SI NO SON PLANTILLAS
							// NO C PUDO POR AHORA.., ASINQUE ENVIAMOS EL
							// FLUJO
							// flow.setStatus(false);
							//
							// }
							if (flow.getTipoFlujo().equalsIgnoreCase(
									Utilidades.getEnAprobacion() + "")) {

								f.setStatus(false);
							} else {

								// los flujos de impresion o revision son
								// sencillos
								f.setStatus(true);
							}

						} else if (f.isPlantilla()) {
							// SOLO LAS COPIAS DE ESTOS FLUJOS, A TRAVES DE
							// FLUJOS PARALELOS .. SERAN LOS QUE SE MANDEN AL
							// USUARIO
							// COMO SE TRAMITARA POR FLUJOS PARALELOS..ESTO
							// ESTARAN INACTIVOS

							f.setStatus(false);
						}

						// EN CASO QUE EL FLUJOS ESTE CREADO Y AL FINAL DEL
						// FLUJO, UN PARTICIPANTE DEL
						// FLKUJO DECIDE AGREGAR UN NUEVO FLUJO, ENTONCES LA
						// VARIABLE SESSION flow_Participante VIENE
						// DE CLIENTEFLORESPONSE->versionId(ActionEvent event)

						// SI ES FLUJO DE REVISION ... DESCOMPONEMOS LOS
						// PARTICIPANTES DEL ROL AQUI MISO
						if (flow.getTipoFlujo().equalsIgnoreCase(
								Utilidades.getEnRevision() + "")) {
							// buscamos los roles y los descomponemos en
							// flows_participantes, ya los usuarios escojidos
							// individualemnte fueron
							// almacenados en flows_participantes
							descomponerRoleSaveFlowParticipantes(f);

							if (flow.isNotificacionMail()) {
								delegadoEcological.MyHiloEnvioMails(f);
							}

						}
						// FIN SI ES FLUJO DE REVISION ... DESCOMPONEMOS LOS
						// PARTICIPANTES DEL ROL AQUI MISO
						Flow_Participantes flow_Participantes = session
								.getAttribute("flow_Participante") != null ? (Flow_Participantes) session
								.getAttribute("flow_Participante") : null;

						f.setFlowParalelo(flowParalelo);
						f.setFlow_Participantes(flow_Participantes);

						Flow fPorSiAcaso = delegado.findFlow(f);
						if (fPorSiAcaso == null) {
							delegado.create(f);
						} else {
							delegado.edit(f);
						}

						// si es editar plantilla.. se va para editar plantillas
						// que es
						// session.setAttribute("listarflowsParaleCambiarNomComent",
						// Utilidades.getListarflowsparalecambiarnomcoment());

						if (isEmptyOrNull(pagIr)) {
							if (!swSolicitudimpresion
									&& flow.getTipoFlujo().compareTo(
											"" + Utilidades.getEnAprobacion()) == 0) {
								pagIr = Utilidades.getFlowparalelo();
							} else {
								// SI ES UNA SOLICITUD DDE IMPRESION , SE COLOCA
								// COMO PLANTILA, COMO APROBACION,LECTOR
								if (swSolicitudimpresion) {
									session.setAttribute(
											"mensaje",
											messages.getString("flowsolicitudImpresion"));
								}
								session.setAttribute("pagIr",
										Utilidades.getListarDocumentos());

								// INICIO agarramos de nuevo el docdetalle
								// oriiginal.. llenamos las sessiones con la
								// original
								// y decimos que no la borremos

								if (doc_detalle == null) {
									doc_detalle = session
											.getAttribute("doc_detalle") != null ? (Doc_detalle) session
											.getAttribute("doc_detalle") : null;
								}
								if (doc_detalle != null) {
									doc_detalle = delegado
											.findDetalle(doc_detalle);
									session.setAttribute("doc_detalle",
											doc_detalle);
									session.setAttribute(
											"doc_detalleforflowplantilla",
											doc_detalle);
									session.setAttribute("doc_maestro",
											doc_detalle.getDoc_maestro());
									session.setAttribute("treeNodoActual",
											doc_detalle.getDoc_maestro()
													.getTree());
									session.setAttribute(
											Utilidades.getNoclearsession(),
											"doc_detalle,"
													+ "doc_detalleforflowplantilla,doc_maestro,treeNodoActual");
								}

								// FIN agarramos de nuevo el docdetalle
								// oriiginal.. llenamos las sessiones con la
								// original
								// y decimos que no la borremos
								pagIr = Utilidades.getFinexitoso();
							}
						}
						// final senial de hacer desde aqui flujos paralelos..
						// ____________________________________

						// que no sea plantilla y que el flujo no seacreado por
						// un participante..
						// swGuardaHistoricoPrimeraVez significa que es la
						// primera vez que se crea en flujo paralelo, es decir..
						// no sta guardada en session el flujo paralelo para
						// generar nuevos flujos con el
						// mismo flujo paralelo
						if (!f.isPlantilla() && swFlowParaleloPrimeraVez
								&& flow_Participantes == null) {
							// ____________________________________________________
							// guardamos el histoico
							FlowsHistorico flowsHistorico = new FlowsHistorico();
							flowsHistorico.setFlow(f);
							flowsHistorico.setDoc_detalle(doc_detalle);
							flowsHistorico.setStatus(true);
							delegado.create(flowsHistorico);
							// ____________________________________________________
							// ____________________________________
							// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
							user_logueado = (Usuario) session
									.getAttribute("user_logueado");
							boolean verSometerFlow = true;

							super.guardamosHistoricoActivoDelDocumento(
									user_logueado,
									doc_detalle.getDoc_maestro(), false, false,
									false, false, verSometerFlow, false, false,
									false, "");
						}

					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages.getString("error")
										+ e.toString()));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_flowParticipantes")));
				}
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

		}

		return pagIr;
	}

	public String cancelarCrearFlows() {
		FlowParalelo flowParaleloSession = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;
		if (flowParaleloSession != null) {
			flowParaleloSession.setStatus(false);
			try {
				delegado.edit(flowParaleloSession);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.clearSession(session, confmessages.getString("usuarioSession"));
		return Utilidades.getListarDocumentos();
	}

	public String aceptar() {
		// INICLIAZAMOS LOS DOCUMENTOS DETALLES
		session.removeAttribute("doc_detallesAux");
		session.removeAttribute("clienteDocumentoMaestrosPanelControl");
		session.removeAttribute("clienteDocumentoMaestros");
		// REFRTESCAMOS EL ARBOL
		refrescarArbolWorkFlows();

		// FIN INICLIAZAMOS LOS DOCUMENTOS DETALLES
		if (this.swCrearHijoDeFlowParalelo) {
			return crearHijoDeFlowParalelo();
		} else {
			// si viene de editar plantillas
			String pagIr = session
					.getAttribute("listarflowsParaleCambiarNomComent") != null ? (String) session
					.getAttribute("listarflowsParaleCambiarNomComent") : "";
			session.removeAttribute("listarflowsParaleCambiarNomComent");
			if (isEmptyOrNull(pagIr)) {
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute(Utilidades.getNoclearsession(), "");
				session.setAttribute("pagIr", Utilidades.getListarDocumentos());
			}

			FlowParalelo flowParaleloSession = session
					.getAttribute("flowParalelo") != null ? (FlowParalelo) session
					.getAttribute("flowParalelo") : null;
			if (!isEmptyOrNull(flowParalelo.getNombre())) {
				flowParaleloSession.setNombre(flowParalelo.getNombre());
				try {

					// SI SELECCIONAMOS ESTE DOCUMENTO PLANTILLA PARA ESTAR EN
					// UJN
					// FLOWSPLANTILLA
					// SI HAY FLUJOS PARALELOS ANIDADOIS.. SE ESCOJIO LA PRIMERA
					// VEZ
					// Y POR ESO VALIDAMOS QUE YA
					// FUERA ESCOJIDO PARA NO SOBREESCRIBIR FLOWSPLANTILLA EN
					// LOS
					// FLUJOS PARALELOS

					if (flowParaleloSession.isSwTipoPlantilladocumento()) {
						if (flowParaleloSession.getDoc_tipo() == null) {

							flowParaleloSession.setDoc_tipo(flowParaleloSession
									.getFlow().getDoc_detalle()
									.getDoc_maestro().getDoc_tipo());
							flowParaleloSession
									.setAreaDocumentos(flowParaleloSession
											.getFlow().getDoc_detalle()
											.getAreaDocumentos());
						}
					}

					delegado.edit(flowParaleloSession);
					if (!flowParaleloSession.getFlow().isPlantilla()) {
						List<Flow> subFlujos = (List<Flow>) delegado
								.activarFlowParaleloAllFlows(flowParaleloSession);
						boolean swFinFlow = true;
						for (Flow subFlow : subFlujos) {

							// grabamos el participante
							flow.setFecha_creado(new Date());
							subFlow.setStatus(true);

							delegado.edit(subFlow);

							// buscamos los roles y los descomponemos en
							// flows_participantes, ya los usuarios escojidos
							// individualemnte fueron
							// almacenados en flows_participantes
							if (!subFlow.isPlantilla()) {
								descomponerRoleSaveFlowParticipantes(subFlow);
							}

							delegadoEcological.MyHiloEnvioMails(subFlow);
						}
					}

				} catch (EcologicaExcepciones e) {
					// si estas editanmdo una solicitud de impresionm
					// no validamos que el nombre se repita, porque esta
					// deshabilitado
					// para cambiar el nombre
					if (swEditando && swDeshabilitarEditaSolicitudimpresion) {
						return pagIr;
					}
					pagIr = "";
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_tree_existe")));
				}

			} else {
				pagIr = "";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("falta_data")));
			}

			return pagIr;
		}

	}

	public String crearHijoDeFlowParalelo() {
		boolean swFlowParalelo = true;
		session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		Doc_detalle doc_detalle = null;
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		Doc_maestro doc_maestro = doc_detalle.getDoc_maestro();
		flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;

		// SI SELECCIONAMOS ESTE DOCUMENTO PLANTILLA PARA ESTAR EN UJN
		// FLOWSPLANTILLA EN LOS FLUJOS PARALELOS
		// --- SI HAY FLUJOS PARALELOS ANIDADOIS.. SE ESCOJIO LA PRIMERA VEZ Y
		// POR
		// ESO VALIDAMOS QUE YA
		// FUERA ESCOJIDO PARANO SOBREESCRIBIR
		if (flowParalelo.isSwTipoPlantilladocumento()) {
			if (flowParalelo.getDoc_tipo() == null) {
				flowParalelo.setDoc_tipo(flowParalelo.getFlow()
						.getDoc_detalle().getDoc_maestro().getDoc_tipo());
				flowParalelo.setAreaDocumentos(flowParalelo.getFlow()
						.getDoc_detalle().getAreaDocumentos());
			}
		}

		Flow flowReferencia = delegado.findFlow(flowParalelo.getFlow());
		String mensaje = flowReferencia.getNombredelflujo() + "("
				+ getFlow().getNombredelflujo() + ":" + getFlow().getCodigo()
				+ ")";
		if (doc_detalle != null) {
			Tree treePadre = delegado.findTree(doc_detalle.getDoc_maestro()
					.getTree().getNodopadre());
			doc_detalle = grabarDocumento(doc_detalle, treePadre,
					user_logueado, swFlowParalelo, mensaje);
			session.setAttribute("doc_detalle", doc_detalle);
		}
		// se crea un documento maestro y detalles copia del original para
		// someterlo a un flow
		return "flows";
	}

	public void descomponerRoleSaveFlowParticipantes(Flow flow) {
		List<Flow_referencia_role> f_r_rs = delegado
				.findAllFlow_referencia_role_ByFlow(flow);
		Role role = new Role();
		for (Flow_referencia_role f_r_r : f_r_rs) {
			role = f_r_r.getRole();

			// descomponemos role en participantes
			Doc_estado firma = new Doc_estado();
			firma.setCodigo(Utilidades.getSinFirmar());
			Flow_Participantes flow_Participantes;
			List<SelectItem> UsuariosLista = usuariosInRole(role);

			HashMap unicoRoleControlTime = new HashMap();
			for (SelectItem item : UsuariosLista) {
				Usuario user = (Usuario) item.getValue();

				flow_Participantes = new Flow_Participantes();
				flow_Participantes.setParticipante(user);
				flow_Participantes.setFlow(flow);
				flow_Participantes.setRole(role);

				try {
					flow_Participantes.setFirma(firma);
					// buscamos si existe individulmente el usuario ya en flujo
					// para que lo sobreescriba en grupo o role
					// Flow_Participantes existIndividual=
					// delegado.flowFlowOfParticipantes(flow,user);
					/*
					 * if (existIndividual!=null){ //identificamos que viene de
					 * un role existIndividual.setRole(role);
					 * existIndividual.setStatus(true);
					 * delegado.edit(existIndividual); }else{
					 */
					// identificamos que viene de un role
					// COLOCA LOS USUARIOS DE LOS ROLES, RESPETANDO LOS USUARIOS
					// UE CXOLOCO INDEPEMÇNDIENTEMENTE
					flow_Participantes.setRole(role);
					flow_Participantes.setStatus(true);
					delegado.create(flow_Participantes);

					// descomponemos los roles de control de tiemñpo en
					// participantes
					List<FlowControlByUsuarioBean> controlTiempos = delegado
							.find_allFlowControlByRoleBean(role, flow);
					// deberia traer un solo objeto, no varios por logica
					for (FlowControlByUsuarioBean t : controlTiempos) {
						if (!unicoRoleControlTime.containsKey(t.getCodigo())) {
							unicoRoleControlTime.put(t.getCodigo(),
									t.getCodigo());
							// uno solo deberia ser...
							FlowControlByUsuarioBean nuevo = new FlowControlByUsuarioBean(
									t);
							nuevo.setFlow_Participantes(flow_Participantes);
							// el participante que antes era null,m ya lo
							// tenemos..

							// creamos este conb su rool y participante del
							// flujo asociadop que pertenece al rol
							delegado.create(nuevo);
						}
					}

					// }

				} catch (Exception e) {
					System.out.println("e.toString()=" + e.toString());
					e.printStackTrace();
				}
			}

		}

		// destruimos los roles de control de referencia que son null los flow
		// participantes
		List<FlowControlByUsuarioBean> controlTiempos = delegado
				.find_allControlTimeByFlowParticipNulos(flow);
		// deberia traer un solo objeto, no varios por logica
		for (FlowControlByUsuarioBean t : controlTiempos) {
			// destruimos el flowparticipante que es null con su rol yy fljuo de
			// conytrtol de tiempò
			delegado.destroy(t);
		}

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
			 
			flow = new Flow();
		}

		 

		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public boolean isSwUserVisible() {
		String tab = session.getAttribute("tabBandera") != null ? (String) session
				.getAttribute("tabBandera") : "";
		swUserVisible = Utilidades.getTab1().equals(tab);

		return swUserVisible;
	}

	public void setSwUserVisible(boolean swUserVisible) {
		this.swUserVisible = swUserVisible;
	}

	public boolean isSwRoleVisible() {
		String tab = session.getAttribute("tabBandera") != null ? (String) session
				.getAttribute("tabBandera") : "";
		swRoleVisible = Utilidades.getTab2().equals(tab);

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

	public boolean isTipoFlow() {
		if (session.getAttribute("tipoFlow") != null) {
			tipoFlow = (Boolean) session.getAttribute("tipoFlow");
		}
		return tipoFlow;
	}

	public void setTipoFlow(boolean tipoFlow) {
		session.setAttribute("tipoFlow", tipoFlow);
		this.tipoFlow = tipoFlow;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public boolean isSwDeshabilitarflowAprobacion() {
		try {

			Doc_detalle doc_detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			if (doc_detalle.getDoc_estado().getCodigo()
					.equals(Utilidades.getVigente())) {
				swDeshabilitarflowAprobacion = true;
			} else {
				swDeshabilitarflowAprobacion = false;
			}
		} catch (Exception e) {
			redirect();
		}
		return swDeshabilitarflowAprobacion;
	}

	public void setSwDeshabilitarflowAprobacion(
			boolean swDeshabilitarflowAprobacion) {
		this.swDeshabilitarflowAprobacion = swDeshabilitarflowAprobacion;
	}

	public String listarControlFlowByUsuario() {

		if (getFlow() != null && getFlow().getCodigo() != null) {

			session.setAttribute("flow", getFlow());

			return "listarControlFlowByTime";
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_flowParticipantes")));
			return "";
		}

	}

	public FlowParalelo getFlowParalelo() {
		return flowParalelo;
	}

	public void setFlowParalelo(FlowParalelo flowParalelo) {
		this.flowParalelo = flowParalelo;
	}

	public boolean isSwDeshabilitarflowAprobacionParalelo() {
		return swDeshabilitarflowAprobacionParalelo;
	}

	public void setSwDeshabilitarflowAprobacionParalelo(
			boolean swDeshabilitarflowAprobacionParalelo) {
		this.swDeshabilitarflowAprobacionParalelo = swDeshabilitarflowAprobacionParalelo;
	}

	public boolean isSwTipoFormatoPlantilla() {
		return swTipoFormatoPlantilla;
	}

	public void setSwTipoFormatoPlantilla(boolean swTipoFormatoPlantilla) {
		this.swTipoFormatoPlantilla = swTipoFormatoPlantilla;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public boolean istoPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;
	}

	public void settoPlantillaInDocFlowParalelo(
			boolean toPlantillaInDocFlowParalelo) {
		this.toPlantillaInDocFlowParalelo = toPlantillaInDocFlowParalelo;
	}

	public Solicitudimpresion getSolicitudimpresion() {
		return solicitudimpresion;
	}

	public void setSolicitudimpresion(Solicitudimpresion solicitudimpresion) {
		this.solicitudimpresion = solicitudimpresion;
	}

	public boolean isSwSolicitudimpresion() {
		return swSolicitudimpresion;
	}

	public void setSwSolicitudimpresion(boolean swSolicitudimpresion) {
		this.swSolicitudimpresion = swSolicitudimpresion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isSwCrearHijoDeFlowParalelo() {
		return swCrearHijoDeFlowParalelo;
	}

	public void setSwCrearHijoDeFlowParalelo(boolean swCrearHijoDeFlowParalelo) {
		this.swCrearHijoDeFlowParalelo = swCrearHijoDeFlowParalelo;
	}

	public boolean isSwSi() {
		return swSi;
	}

	public void setSwSi(boolean swSi) {
		this.swSi = swSi;
	}

	public boolean isSwFlowParaleloSession() {
		return swFlowParaleloSession;
	}

	public void setSwFlowParaleloSession(boolean swFlowParaleloSession) {
		this.swFlowParaleloSession = swFlowParaleloSession;
	}

	public String getAreaDocumentos() {
		return areaDocumentos;
	}

	public void setAreaDocumentos(String areaDocumentos) {
		this.areaDocumentos = areaDocumentos;
	}

	public boolean isSwCanSolicitudimpresion() {
		return swCanSolicitudimpresion;
	}

	public void setSwCanSolicitudimpresion(boolean swCanSolicitudimpresion) {
		this.swCanSolicitudimpresion = swCanSolicitudimpresion;
	}

	public boolean isSwDeshabilitarEditaSolicitudimpresion() {
		return swDeshabilitarEditaSolicitudimpresion;
	}

	public void setSwDeshabilitarEditaSolicitudimpresion(
			boolean swDeshabilitarEditaSolicitudimpresion) {
		this.swDeshabilitarEditaSolicitudimpresion = swDeshabilitarEditaSolicitudimpresion;
	}

	public boolean isSwEditando() {
		return swEditando;
	}

	public void setSwEditando(boolean swEditando) {
		this.swEditando = swEditando;
	}

	public List<Role> getInvisibleItems() {
		return invisibleItems;
	}

	public void setInvisibleItems(List<Role> invisibleItems) {
		this.invisibleItems = invisibleItems;
	}

	public List<Role> getVisibleItems() {
		return visibleItems;
	}

	public void setVisibleItems(List<Role> visibleItems) {
		this.visibleItems = visibleItems;
	}

	public DualListModel<Role> getOperacionesRole() {
		return operacionesRole;
	}

	public void setOperacionesRole(DualListModel<Role> operacionesRole) {
		this.operacionesRole = operacionesRole;
	}

	public DualListModel<Usuario> getOperacionesUsuario() {
		return operacionesUsuario;
	}

	public void setOperacionesUsuario(DualListModel<Usuario> operacionesUsuario) {
		this.operacionesUsuario = operacionesUsuario;
	}

	public List<Usuario> getInvisibleItemsUsuario() {
		return invisibleItemsUsuario;
	}

	public void setInvisibleItemsUsuario(List<Usuario> invisibleItemsUsuario) {
		this.invisibleItemsUsuario = invisibleItemsUsuario;
	}

	public List<Usuario> getVisibleItemsUsuario() {
		return visibleItemsUsuario;
	}

	public void setVisibleItemsUsuario(List<Usuario> visibleItemsUsuario) {
		this.visibleItemsUsuario = visibleItemsUsuario;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
	}

	public DualListModel<Role> getOperacionesRoleNotificacion() {
		return operacionesRoleNotificacion;
	}

	public void setOperacionesRoleNotificacion(
			DualListModel<Role> operacionesRoleNotificacion) {
		this.operacionesRoleNotificacion = operacionesRoleNotificacion;
	}

}
