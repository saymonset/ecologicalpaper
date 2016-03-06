package com.ecological.paper.cliente.flows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.ExcepcioFlowImprimirActivo;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowOnlyNotificacionRole;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class ClienteFlowsParalelo extends ClienteSeguridad {
	private AreaDocumentos areaDocumentos;
	private Doc_tipo doc_tipo;
	private Seguridad seguridadTree = new Seguridad();
	private Seguridad seguridadMenu = new Seguridad();
	private Tree treeNodoActual;
	private Tree tree;
	// guardar una plantilla de flujos para un documento tipo plantilla
	private boolean toPlantillaInDocFlowParalelo;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	private DatosCombo datosCombo = new DatosCombo();
	private Flow flow;
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");

	private HttpSession session = super.getSession();
	private Map<Integer, Integer> predecesoresOriginalesMap;
	private List<Integer> predecesoresOriginales;
	private String puedenPredecer;
	private Usuario user_logueado;
	private FlowParalelo flowParalelo;
	private List<SelectItem> visibleUsersFlows = new ArrayList<SelectItem>();;
	private List<SelectItem> visibleRoleFlows = new ArrayList<SelectItem>();
	// private List mandarMailUsuarios;
	private List<FlowParalelo> listarParalelos;
	private boolean swMostrarListaPreceder;
	private String strBuscar;
	private boolean swHayParticipantesFlow;
	private boolean swHayGroupParticipantesFlow;
	private boolean swHayGroupParticipantesFlowNotificacion;
	private Flow flowEditar;

	// private String nuevoNombreGenerar;
	// private String nuevoComentarioGenerar;

	public ClienteFlowsParalelo(String nada) {
	}

	public ClienteFlowsParalelo() {

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		setTree(session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null);
		// CONSEGUIMOS LA SEGURIDAD TREE
		if (getTree() != null) {
			seguridadTree = super.getSeguridadTree(treeNodoActual);
		}
		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		if (seguridadMenu.istoPlantillaInDocFlowParalelo()) {
			toPlantillaInDocFlowParalelo = true;
		}

		// EN CASO DE MODIFICAR TEMPORALMENTE UNA PLANTILLA ANTES DE HACER UN
		// FLUJO DE TRABAJO
		/*
		 * Flow flowModificar = session.getAttribute("flowModificar") != null ?
		 * (Flow) session .getAttribute("flowModificar") : null; if
		 * (flowModificar != null) { nuevoNombreGenerar =
		 * flowModificar.getNombredelflujo(); nuevoComentarioGenerar =
		 * flowModificar.getComentarios(); }
		 */
		// FIN EN CASO DE MODIFICAR TEMPORALMENTE UNA PLANTILLA ANTES DE HACER
		// UN FLUJO DE TRABAJO

		Object objeto = null;

		if (session.getAttribute("edit") != null
				&& session.getAttribute("edit") instanceof Flow) {
			objeto = session.getAttribute("edit") != null ? (Flow) session
					.getAttribute("edit") : null;
		}

		if (objeto != null && objeto instanceof Flow) {
			flow = (Flow) objeto;
		} else {
			flow = null;
		}

		if (flow == null) {
			getListaFlujosParalelos();
		} else {
			// BUSCAMOS LOS PREDECESORES QUE PUEDEN SER PARA EL FLOW
			predecesoresOriginalesMap = new HashMap<Integer, Integer>();
			predecesoresOriginales = new ArrayList<Integer>();

			StringTokenizer numerosValidos = null;
			FlowParalelo flowparalelo = new FlowParalelo();
			flowparalelo.setFlow(flow);
			flowparalelo.setCodigo(flow.getFlowParalelo().getCodigo());
			Flow f = new Flow();
			f.setCodigo(flow.getCodigo());
			// si el f tiene precedencia.. este no es alterada y me rtrae la
			// mismna..
			Flow flowOriginal = precedencia(f, flowparalelo);
			puedenPredecer = flowOriginal.getPrecedencia();
			numerosValidos = new StringTokenizer(flowOriginal.getPrecedencia(),
					",");

			while (numerosValidos.hasMoreTokens()) {
				try {
					String numero = numerosValidos.nextToken();
					predecesoresOriginalesMap.put(new Integer(numero),
							new Integer(numero));
					predecesoresOriginales.add(new Integer(numero));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (isEmptyOrNull(flowparalelo.getFlow().getPrecedencia())) {
				flowparalelo.getFlow().setPrecedencia(puedenPredecer);
			}

		}
	}

	public List<Flow> getListaFlujosParalelos() {
		List<Flow> objetos = null;
		List<Flow> objetos2 = new ArrayList<Flow>();

		try {
			FlowParalelo flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
					.getAttribute("flowParalelo") : null;
			// Map<Long, Object> flowsOfplantilla = new HashMap<Long, Object>();
			// flowsOfplantilla flujos de plantillas q se modifica
			// individualmente en
			// el metodo moficarNombreFlow_action
			// de la pag listarflowsParaleCambiarNomComent2daparte.jsp
			/*
			 * if (session.getAttribute("flowsOfplantilla") != null) {
			 * flowsOfplantilla = (Map) session
			 * .getAttribute("flowsOfplantilla"); }
			 */

			if (flowParalelo != null) {
				// CONSEGUIMOS LA SEGURIDAD MENU
				Tree treeMenu = new Tree();
				treeMenu.setNodo(Utilidades.getNodoRaiz());
				seguridadMenu = super.getSeguridadTree(treeMenu);

				objetos = delegado.findAllFlowParalelosDelSistema(flowParalelo);
				swHayParticipantesFlow = false;
				swHayGroupParticipantesFlow = false;
				swHayGroupParticipantesFlowNotificacion=false;
				for (Flow f : objetos) {
					/*
					 * if (flowsOfplantilla != null) { if
					 * (flowsOfplantilla.containsKey(f.getCodigo())) { f =
					 * (Flow) flowsOfplantilla.get(f.getCodigo()); } }
					 */

					Doc_estado docEstado = new Doc_estado();
					docEstado.setCodigo(new Long(f.getTipoFlujo()));
					docEstado = delegado.findDocEstado(docEstado);
					String tipoFlujo = messages
							.getString(docEstado.getNombre());
					f.setTipoFlujoAux(tipoFlujo);

					if (f.getFlowParalelo().getDoc_tipo() != null) {
						// edita los que son plantilla tipo fomato
						if (seguridadMenu.istoPlantillaInDocFlowParalelo()) {
							f.setSwEdit(true);
						}
					} else {
						// edita los que no son plantilla tipo fomato
						f.setSwEdit(true);
					}

					List<Flow_Participantes> flowParticipantes = delegado
							.findByFlowParticipantes(f);

					// LISTA DE PARTICIPANTES
					List<Usuario> participantesPlantila = new ArrayList<Usuario>();
					for (Flow_Participantes fp : flowParticipantes) {
						participantesPlantila.add(fp.getParticipante());
					}

					// sw que usuaremos en la vista para comprbar que hay
					// participantes en el flow
					if (participantesPlantila != null
							&& !participantesPlantila.isEmpty()
							&& participantesPlantila.size() > 0) {
						swHayParticipantesFlow = true;
					}
					f.setParticipantesPlantila(participantesPlantila);
					// FIN LISTA DE PARTICIPANTES

					// PARA GRUPOS Y LOS USUARIOS DENTRO DEL GRUPO
					List<Flow_referencia_role> frlst = delegado
							.findAllFlow_referencia_role_ByFlow(f);
					List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
					Flow flowGrupoParticipante = null;
					for (Flow_referencia_role fr : frlst) {
						List<Roles_Usuarios> roleUsuarioLst = delegado
								.findRoles_AND_Usuario(fr.getRole());
						for (Roles_Usuarios ru : roleUsuarioLst) {
							flowGrupoParticipante = new Flow();
							flowGrupoParticipante.setGrupo(ru.getRole());
							flowGrupoParticipante.setParticipante(ru
									.getUsuario());
							participantesGruposPlantila
									.add(flowGrupoParticipante);
						}
					}
					// sw que usuaremos en la vista para comprbar que hay
					// participantes en el flow
					swHayGroupParticipantesFlow = false;
					if (participantesGruposPlantila != null
							&& !participantesGruposPlantila.isEmpty()
							&& participantesGruposPlantila.size() > 0) {
						swHayGroupParticipantesFlow = true;
					}
					f.setParticipantesGruposPlantila(participantesGruposPlantila);
					
					// PARA GRUPOS Y LOS USUARIOS DENTRO DEL GRUPO NOTIFICACION
					List<FlowOnlyNotificacionRole> frlst1 = delegado
							.findAllFlowOnlyNotificacionRoleByFlow(f);
					List<Flow> participantesGruposPlantilaNotif = new ArrayList<Flow>();
					flowGrupoParticipante = null;
					for (FlowOnlyNotificacionRole fr : frlst1) {
						List<Roles_Usuarios> roleUsuarioLst = delegado
								.findRoles_AND_Usuario(fr.getRole());
						for (Roles_Usuarios ru : roleUsuarioLst) {
							flowGrupoParticipante = new Flow();
							flowGrupoParticipante.setGrupo(ru.getRole());
							flowGrupoParticipante.setParticipante(ru
									.getUsuario());
							participantesGruposPlantilaNotif
									.add(flowGrupoParticipante);
						}
					}
					// sw que usuaremos en la vista para comprbar que hay
					// participantes en el flow
					swHayGroupParticipantesFlowNotificacion = false;
					if (participantesGruposPlantilaNotif != null
							&& !participantesGruposPlantilaNotif.isEmpty()
							&& participantesGruposPlantilaNotif.size() > 0) {
						swHayGroupParticipantesFlowNotificacion = true;
					}
					f.setParticipantesGruposPlantilaNotificacion(participantesGruposPlantilaNotif);
					
					
				 
					objetos2.add(f);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (objetos2 != null && objetos2.size() > 1) {
			swMostrarListaPreceder = true;
		}
		return objetos2;
	}

	private Flow precedencia(Flow fp, FlowParalelo flowParalelo2) {
		StringBuffer fpStr;
		fpStr = new StringBuffer("");
		/*
		 * if (!isEmptyOrNull(fp.getPrecedencia())) {
		 * fpStr.append(fp.getPrecedencia()); fp.setSwPrecedencia(true); } else
		 * {
		 */
		// asesoramos que precedencia pueda agarrar
		List<Flow> flowPrecedencia = delegado
				.calculandoPrecendenciaFlowParalelos(flowParalelo2);
		boolean swPrimeraVez = false;
		for (Flow fp2 : flowPrecedencia) {
			if (!swPrimeraVez) {
				swPrimeraVez = true;
			} else {
				fpStr.append(",");
			}
			fpStr.append(fp2.getCodigo());
		}
		// }
		fp.setPrecedencia(fpStr.toString());
		return fp;

	}

	public void editFlowParalelo(ActionEvent event) {

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editflowid");

		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editflowiddelimp");
		}

		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editimpid");
		}

		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editflowiddel");
		}
		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editflowiddelplantillaflowdocumento");
		}

		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editflowiddelplantillaflowdocumentoimp");
		}

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				flowParalelo = new FlowParalelo();
				flowParalelo.setCodigo(id);
				try {
					flowParalelo = delegado.find(flowParalelo);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				session.setAttribute("flowParalelo", flowParalelo);
			}

		}
	}

	public String cambiarNombreComentarioDePlantilla2daparte() {
		return "listarflowsParaleCambiarNomComent2daparte";
	}

	public void editFlowParalelo2daparte(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editflowid");
		if (component == null || component.getValue().toString() == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editflowiddel");
		}

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				Flow flowModificar = new Flow();
				flowModificar.setCodigo(id);
				Map<Long, Object> flowsOfplantilla = new HashMap<Long, Object>();
				// flowsOfplantilla flujos de plantillas q se modifica
				// individualmente en
				// el metodo moficarNombreFlow_action
				// de la pag listarflowsParaleCambiarNomComent2daparte.jsp
				if (session.getAttribute("flowsOfplantilla") != null) {
					flowsOfplantilla = (Map) session
							.getAttribute("flowsOfplantilla");
				}
				if (flowsOfplantilla.containsKey(flowModificar.getCodigo())) {
					flowModificar = (Flow) flowsOfplantilla.get(flowModificar
							.getCodigo());
				} else {
					flowModificar = delegado.findFlow(flowModificar);
				}

				session.setAttribute("flowModificar", flowModificar);

			}

		}
	}

	public String cambiarNombreComentarioDePlantilla() {

		session.setAttribute("flowParalelo", flowParalelo);

		return "listarflowsParaleCambiarNomComent";
	}

	public String cancelar() {
		return "listarflowsParalelo";
	}

	public String cancelar2daparte() {
		return "listarflowsParaleCambiarNomComent";
	}

	public String moficarNombreFlow_action() {
		/*
		 * Map<Long, Object> flowsOfplantilla = new HashMap<Long, Object>(); if
		 * (session.getAttribute("flowsOfplantilla") != null) { flowsOfplantilla
		 * = (Map) session.getAttribute("flowsOfplantilla"); }
		 */
		/*
		 * Flow flowModificar = session.getAttribute("flowModificar") != null ?
		 * (Flow) session .getAttribute("flowModificar") : null; if
		 * (flowModificar != null) {
		 * flowModificar.setNombredelflujo(nuevoNombreGenerar);
		 * flowModificar.setComentarios(nuevoComentarioGenerar);
		 * flowsOfplantilla.put(flowModificar.getCodigo(), flowModificar);
		 * session.setAttribute("flowsOfplantilla", flowsOfplantilla);
		 * session.removeAttribute("flowModificar"); }
		 */

		return "listarflowsParaleCambiarNomComent";
	}

	public void editPreceder(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editflowid");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				Flow flow = new Flow();
				flow.setCodigo(id);
				flow = delegado.findFlow(flow);
				session.setAttribute("edit", flow);
			}
		}
	}

	public String listarSolicitudImpresion() {

		return "listarSolicitudImpresion";
	}

	public void editSolicitudImpresion(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editimprimirid");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				FlowParalelo flowParaleloImp = new FlowParalelo();

				flowParaleloImp.setCodigo(id);

				try {
					flowParaleloImp = delegado.find(flowParaleloImp);
					System.out.println("flowParaleloImp.getNombre()="
							+ flowParaleloImp.getNombre());
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (flowParaleloImp != null
						&& flowParaleloImp.getFlow().getDoc_detalle() != null) {
					Doc_detalle doc = flowParaleloImp.getFlow()
							.getDoc_detalle();
					doc.setFlowEditar(flowParaleloImp.getFlow());
					session.setAttribute("doc_detalle", doc);
					System.out.println("doc.getCodigo()==" + doc.getCodigo());
					session.setAttribute("doc_maestro", doc.getDoc_maestro());
					session.setAttribute("flowParalelo", flowParaleloImp);
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_intentar")
									+ " flow is null"));
				}
			}
		}
	}

	public void editFlow(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editfloweditarid");
		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editimprimirid");
		}

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				Flow flow = new Flow();
				flow.setCodigo(id);
				flow = delegado.findFlow(flow);
				if (flow != null && flow.getDoc_detalle() != null) {
					Doc_detalle doc = flow.getDoc_detalle();
					doc.setFlowEditar(flow);
					session.setAttribute("doc_detalle", doc);
					session.setAttribute("doc_maestro", doc.getDoc_maestro());
					session.setAttribute("flowParalelo", flow.getFlowParalelo());
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_intentar")
									+ " flow is null"));
				}
			}
		}
	}

	public String flows_action() {
		session.setAttribute("listarflowsParaleCambiarNomComent",
				Utilidades.getListarflowsparalecambiarnomcoment());

		Doc_detalle doc = flow.getDoc_detalle();
		if (flow != null) {
			// colocamos por defecto que sean flows de plantillas globales
			if (flow.isPlantilla()) {
				flow.getFlowParalelo().setSwTipoPlantilladocumento(true);
			}
		}

		doc.setFlowEditar(flow);
		session.setAttribute("doc_detalle", doc);
		session.setAttribute("doc_maestro", doc.getDoc_maestro());
		session.setAttribute("flowParalelo", flow.getFlowParalelo());

		return Utilidades.getFlows();

	}

	public String flows_actionNew() {
		session.setAttribute("flowEditar", flowEditar);

		session.setAttribute("flows", "flows");
		return Utilidades.getFlows();

	}

	public String delete() {
		// VIENE DE LA LISTA QUE ESCOJIO PARA CONTINUAR EL FLUJO PARALELO
		FlowParalelo flowParaleloPlantilla = session
				.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;
		try {
			flowParaleloPlantilla.setStatus(false);
			delegado.edit(flowParaleloPlantilla);
			session.removeAttribute("flowParalelo");
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String crearFlujoDesdeFlujoParalelo_action() {
		String pagIr = "";
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// REFRTESCAMOS EL ARBOL
		refrescarArbolWorkFlows();
		try {
			// VIENE DE LA LISTA QUE ESCOJIO PARA CONTINUAR EL FLUJO PARALELO
			FlowParalelo flowParaleloPlantilla = session
					.getAttribute("flowParalelo") != null ? (FlowParalelo) session
					.getAttribute("flowParalelo") : null;

			Doc_detalle doc_detalle = null;

			// EN CASO QUE EL FLUJOS ESTE CREADO Y AL FINAL DEL
			// FLUJO, UN PARTICIPANTE DEL
			// FLKUJO DECIDE AGREGAR UN NUEVO FLUJO, ENTONCES LA
			// VARIABLE SESSION flow_Participante VIENE
			// DE CLIENTEFLORESPONSE->versionId(ActionEvent event)
			Flow_Participantes flow_Participantes = session
					.getAttribute("flow_Participantes") != null ? (Flow_Participantes) session
					.getAttribute("flow_Participantes") : null;
			// el padre flujo paralelo a crear de la plantilla .
			if (flow_Participantes == null) {
				flowParalelo = new FlowParalelo();
			} else {
				// AGARRAMOS EL FLUJO PARALELO DEL PARTICIPANTE,PARA QUE SE
				// ENCADENE Y CONTINUAMOS EL FLUJO
				flowParalelo = flow_Participantes.getFlow().getFlowParalelo();
			}


			// flowsOfplantilla flujos de plantillas q se modifica
			// individualmente en
			// el metodo moficarNombreFlow_action
			// de la pag listarflowsParaleCambiarNomComent2daparte.jsp
			List<Flow> subFlujos = (List<Flow>) delegado
					.findByFlowParaleloPlantilla(flowParaleloPlantilla);
			boolean swInicioFlow = true;
			Map<Long, Long> preceder = new HashMap<Long, Long>();
			for (Flow subFlow : subFlujos) {
				List noSeUsa = new ArrayList();
				// llenamos los dartos que se escojio para el flujo en
				// visibleUsersFlows
				visibleUsersFlows.clear();
				datosCombo.llenarUsuariosFlowVisiblesPlantilla(
						visibleUsersFlows, noSeUsa, subFlow);

				List<Usuario> usuarioSeleccionados = new ArrayList<Usuario>();
				for (SelectItem value : visibleUsersFlows) {
					Usuario usuario2 = (Usuario) value.getValue();
					usuarioSeleccionados.add(usuario2);

				}

				Doc_detalle doc_detalleAux = (Doc_detalle) session
						.getAttribute("doc_detalleforflowplantilla");
				// INICILAIZAMOS CON EL TIPO DE FLOW OrigenDocumentoFlow
				doc_detalleAux.setOrigen(Utilidades.getOrigenDocumentoFlow());
				session.setAttribute("doc_detalleforflowplantilla",
						doc_detalleAux);

				// agarramos el documento origibnañl de la playtilla que
				// contiene datos
				if (swInicioFlow) {

					doc_detalle = (Doc_detalle) session
							.getAttribute("doc_detalleforflowplantilla");
					// inicializamos rl tipo de flow, cambio con el tiempo segun
					// las circunstancias
					doc_detalle.setOrigen(Utilidades.getOrigenDocumentoFlow());

					// EN CASO QUE QUIERAS HACER UNA SOLICITUD DE IMPRESION
					if (flowParaleloPlantilla.isSolicituImpresion()) {

						doc_detalle.setOrigen(Utilidades
								.getOrigenflowimpresor());
						session.setAttribute("doc_detalleforflowplantilla",
								doc_detalle);

						SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
						Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
						solicitudimpresion.setDoc_detalle(doc_detalle);
						// EL USUARIO QUE PIDE LA PETICION ES EWL MISMO USUARIO
						// QUE VA A IMPRTIMIR
						solicitudImpParts.setUsuario(user_logueado);
						solicitudImpParts
								.setSolicitudimpresion(solicitudimpresion);

						List<SolicitudImpPart> solicitudImpPartLst = delegado
								.findAllsolicitudImpParts(solicitudImpParts);

						if (solicitudImpPartLst != null
								&& solicitudImpPartLst.size() > 0) {

							throw new ExcepcioFlowImprimirActivo();
						}
					} // FIN EN CASO QUE QUIERAS HACER UNA SOLICITUD DE
						// IMPRESION

				} else {
					// estos sonb documentos derivados de la plantilla .. sin
					// data
					boolean swFlowParalelo = true;

					// de flowParaleloPlantilla , solo se va sacar el no,mbre
					// para el detalle
					doc_detalle = crearHijoDeFlowParaleloVariables(
							user_logueado,
							/* (Doc_detalle) session.getAttribute("doc_detalle") */
							(Doc_detalle) session
									.getAttribute("doc_detalleforflowplantilla"),
							flowParaleloPlantilla, flowParaleloPlantilla
									.getFlow(), swFlowParalelo);
					doc_detalle.setOrigen(Utilidades.getOrigenDocumentoFlow());

				}

				// AQUI GRABAMOS LAS OPERACIONES Y EL FLUJO
				delegadoEcological.saveUsuarioOperacionesFlows(null,
						user_logueado, doc_detalle, usuarioSeleccionados);

				Flow f = buscaFlowDocDetalle(doc_detalle);
				f.setNombredelflujo(subFlow.getNombredelflujo());

				// ----------------
				// SE EDITA POR PRINMERA VEZ EL FLUJO PARALELO,este nace de
				// la
				// plantila
				if (swInicioFlow) {
					if (flow_Participantes == null) {
						// ----------------
						// SE CREA POR PRINMERA VEZ EL FLUJO PARALELO,este nace
						// de
						// la
						// plantila

						flowParalelo.setFlow(f);
						flowParalelo.setNombre(flowParaleloPlantilla
								.getNombre());
						flowParalelo.setUsuario(user_logueado);
						delegado.create(flowParalelo);
					}
				}
				swInicioFlow = false;

				// AQUI EMPOEZAMOS A GRABARE Y EDIUTRAR LOS FLUJO QUE VAN
				// NACIENBDO DEL PAARALELO.-..

				// SI ES NULO O NO.. ME LO GUARDA IGUAL
				f.setFlow_Participantes(flow_Participantes);
				f.setFlowParalelo(flowParalelo);

				// -------------------------

				Flow flowOfParalelo = subFlow;
				preceder.put(subFlow.getCodigo(), f.getCodigo());
				StringBuffer stringPreceder = new StringBuffer("");
				if (!isEmptyOrNull(subFlow.getPrecedencia())) {
					StringTokenizer stkPreceder = new StringTokenizer(
							subFlow.getPrecedencia(), ",");
					boolean swPrimeraVez = true;
					while (stkPreceder.hasMoreTokens()) {
						String p = stkPreceder.nextToken();
						if (preceder.containsKey(new Long(p))) {
							if (swPrimeraVez) {
								stringPreceder
										.append(preceder.get(new Long(p)));
								swPrimeraVez = false;
							} else {
								stringPreceder.append(",").append(
										preceder.get(new Long(p)));
							}

						}

					}
				}
				f.setPrecedencia(stringPreceder.toString());
				f.setCondicional(flowOfParalelo.isCondicional());
				f.setTipoFlujo(flowOfParalelo.getTipoFlujo());
				f.setSecuencial(flowOfParalelo.isSecuencial());
				f.setLectores(flowOfParalelo.isLectores());
				f.setDoc_detalle(doc_detalle);
				f.setDuenio(user_logueado);
				f.setEstado(flowOfParalelo.getEstado());
				Calendar cal = Calendar.getInstance();
				f.setFecha_creado(cal.getTime());
				// NO QUEREMOS QUE ALTERE LA VERSION DEL DOCUMENTO

				f.setOrigen(flowOfParalelo.getOrigen());

				// FIN NO QUEREMOS QUE ALTERE LA VERSION DEL DOCUMENTO

				f.setComentarios(flowOfParalelo.getComentarios());

				f.setNotificacionMail(flowOfParalelo.isNotificacionMail());
				f.setStatus(true);

				// POR ROLES
				descomponerRoleSaveFlowParticipantes(flowOfParalelo, f);
				
//				GRABAMOS LOS ROLES QUE TIENEN LOS USUARIOS PARA NOTIFICAR POR MAILS EN LOS FLOWS
				RoleSaveFlowNotificacion(flowOfParalelo,
						f);
				// grabamos el participante
				f.setFecha_creado(new Date());

				// CASO DE SER UNA SOLICITUD DE IMPRESION
				if (flowParaleloPlantilla.isSolicituImpresion()) {
					f.setOrigen(Utilidades.getOrigenflowimpresor());

					SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
					Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
					List<SolicitudImpPart> solicitudImpPartsLst = new ArrayList<SolicitudImpPart>();
					user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
							.getAttribute("user_logueado") : null;
					solicitudImpParts = new SolicitudImpPart();
					solicitudImpParts.setUsuario(user_logueado);
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getImprimirsin());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudImpParts.setEstado(doc_estado);
					solicitudImpParts.setStatus(true);
					solicitudImpPartsLst.add(solicitudImpParts);

					// SIEMPRE VA HACER UN SOLO FLUJO PARA EL FLUJO DE
					// IMPRESION
					solicitudimpresion = new Solicitudimpresion();
					doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getEnAprobacion());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudimpresion.setEstado(doc_estado);
					solicitudimpresion.setSolicitante(user_logueado);
					solicitudimpresion.setFechadesdeimprimir(new Date());
					solicitudimpresion
							.setFechahastaimprimir(fechaSumarRestarDias(
									new Date(),
									Utilidades.getFechasumarrestardias(), true));
					solicitudimpresion.setFechaSolicitud(new Date());
					solicitudimpresion.setDoc_detalle(doc_detalle);
					solicitudimpresion
							.setsolicitudImpParts(solicitudImpPartsLst);
					solicitudimpresion.setFlow(f);
					delegadoEcological
							.crearSolicitudImpresion(solicitudimpresion);
					// FIN SIEMPRE VA HACER UN SOLO FLUJO PARA EL FLUJO
					// DE IMPRESION

				}
				// FIN CASO DE SER UNA SOLICITUD DE IMPRESION

				delegado.edit(f);

				List<FlowControlByUsuarioBean> flowControlByUsuarioBeans = delegado
						.find_allControlTimeByFlowParticipAndRole(f);
				for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBeans) {
					if (controlTime.getHorasAsignadas() != 0
							|| controlTime.getMinutosAsignados() != 0) {
						// buscamos si el flujo es secuenial
						if (f.isSecuencial()) {
							controlTime.setSwStartHilo(Utilidades.isInactivo());
							delegado.edit(controlTime);
						} else {
							controlTime.setSwStartHilo(Utilidades.isActivo());
							delegado.edit(controlTime);
						}
					}

				}

				if (f.isSecuencial()) {

					List<Flow_Participantes> listaAuxParticipantes = delegado
							.findByFlowParticipantes(f);
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

				// -----------------------------------

				// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
				// MANDARLES UN MAIL

				if (f.isNotificacionMail()) {
					delegadoEcological.MyHiloEnvioMails(f);
				}

				// ____________________________________________________
				// guardamos el histoico
				FlowsHistorico flowsHistorico = new FlowsHistorico();
				flowsHistorico.setFlow(f);
				flowsHistorico.setDoc_detalle(doc_detalle);
				flowsHistorico.setStatus(true);
				delegado.create(flowsHistorico);

				// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
				user_logueado = (Usuario) session.getAttribute("user_logueado");
				boolean verSometerFlow = true;

				super.guardamosHistoricoActivoDelDocumento(user_logueado,
						doc_detalle.getDoc_maestro(), false, false, false,
						false, verSometerFlow, false, false, false, "");
			}

			// VIENE DE CLIENTEFLOWRESPONSE->listaPlantillaFlowParalelo()
			// O DE
			pagIr = (String) session.getAttribute("pagIr");

			session.setAttribute("pagIr", Utilidades.getListarDocumentos());
			session.setAttribute(Utilidades.getNoclearsession(),
					"doc_detalle,doc_maestro,treeNodoActual");
			pagIr = Utilidades.getFinexitosorefresca();

		} catch (ExcepcioFlowImprimirActivo e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("solicitudImpresionpendiente")));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));

		}
		return pagIr;
	}

	public String listaPlantillaFlowParalelo() {
		return Utilidades.getListarflowparalelo();

	}

	public String listar() {

		String pagIr = (String) session.getAttribute("pagIr");

		return pagIr;

	}

	/*
	 * public List<Usuario> mandarMailsUsuarios(Flow flow) { mandarMailUsuarios
	 * = new ArrayList(); List<Flow_Participantes> f_ps = delegado
	 * .findAllFlowParticipantesByFlow(flow); for (Flow_Participantes
	 * mailUsuario : f_ps) {
	 * mailUsuario.getParticipante().setComentarioMailFlujo( "\n" +
	 * messages.getString("enviadoPor") + ":" + flow.getDuenio().getApellido() +
	 * " " + flow.getDuenio().getNombre() + "\n\n      " +
	 * messages.getString("doc_descripciontab") + ":" + flow.getComentarios() +
	 * "\n\n\n        " + messages.getString("doc_documento") + ":" +
	 * flow.getDoc_detalle().getDoc_maestro() .getNombre() + " " +
	 * flow.getDoc_detalle().getDoc_maestro() .getConsecutivo());
	 * mandarMailUsuarios.add(mailUsuario.getParticipante()); } return
	 * mandarMailUsuarios; }
	 */

	public void descomponerRoleSaveFlowParticipantes(Flow flowParalelo,
			Flow flow) {

		List<Flow_referencia_role> f_r_rs = delegado
				.findAllFlow_referencia_role_ByFlow(flowParalelo);
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

				try {
					flow_Participantes = new Flow_Participantes();
					flow_Participantes.setParticipante(user);
					flow_Participantes.setFlow(flow);
					flow_Participantes.setFirma(firma);
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

	}
//	USUARIO PARA NOTIFICAR EN LOS FLOWS
	public void RoleSaveFlowNotificacion(Flow flowParalelo,
			Flow flow) {

		List<FlowOnlyNotificacionRole> f_r_rs = delegado.findByFlowOnlyNotificacionRole(flowParalelo);
		
		Role role = new Role();

		FlowOnlyNotificacionRole flowOnlyNotificacionRole;
		for (FlowOnlyNotificacionRole f_r_r : f_r_rs) {
			role = f_r_r.getRole();
		 	flowOnlyNotificacionRole = new FlowOnlyNotificacionRole();
			flowOnlyNotificacionRole.setRole(role);
			flowOnlyNotificacionRole.setFlow(flow);
			try {
				// GRABAMOS LA REFERENCIA ROLE CON EL FLOW
				delegado.create(flowOnlyNotificacionRole);
			} catch (Exception e) {
				System.out.println("e.toString()="
						+ e.toString());
				e.printStackTrace();
			} finally {
			}
		}

	}

	

	public Flow buscaFlowDocDetalle(Doc_detalle doc_detalle) {
		Flow f = new Flow();
		// /doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (doc_detalle == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		}
		if (doc_detalle != null) {

			if (doc_detalle.getFlowEditar() != null) {
				return doc_detalle.getFlowEditar();
			}

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

	public String actionEdit() {

		session.setAttribute("edit", flow);
		return "edit";
	}

	public String saveObjeto() {
		boolean swOk = true;
		// SI NO QUEREMOS QUE NADA PRECEDA
		if (isEmptyOrNull(flow.getPrecedencia())) {
			session.removeAttribute("edit");
			try {

				delegado.edit(flow);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "flowparalelo";
		}

		// SI HAY PRECESORES
		StringTokenizer numerosAValidar = null;
		numerosAValidar = new StringTokenizer(flow.getPrecedencia(), ",");
		Map<String, Object> unico = new HashMap<String, Object>();
		while (numerosAValidar.hasMoreTokens()) {
			try {
				String number2 = numerosAValidar.nextToken();
				unico.put(number2, number2);
				if (isNumeric(number2)) {
					boolean swAceptado = false;
					for (Integer i : predecesoresOriginales) {
						int num = new Integer(number2);
						// un numero que precede es mayor o igual a l numero
						// introducido..
						// esta bien
						if (predecesoresOriginalesMap.containsKey(num)) {
							swAceptado = true;
							break;
						}
					}
					if (!swAceptado) {
						swOk = false;
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_NoEncontrado")
										+ "->"
										+ number2));
					}
				} else {
					swOk = false;
					FacesContext.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(messages
											.getString("errorNumero")));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (swOk) {
			session.removeAttribute("edit");
			try {
				boolean swPrimeraVez = true;
				Iterator it = unico.keySet().iterator();
				StringBuffer cadena = new StringBuffer("");
				for (; it.hasNext();) {
					String key = (String) it.next();
					if (swPrimeraVez) {
						swPrimeraVez = false;
						cadena.append((String) unico.get(key));

					} else {
						cadena.append(",").append((String) unico.get(key));
					}

				}
				flow.setPrecedencia(cadena.toString());
				delegado.edit(flow);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "flowparalelo";
		} else {
			return "";
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

		doc_detalle = crearHijoDeFlowParaleloVariables(user_logueado,
				doc_detalle, flowParalelo, getFlow(), swFlowParalelo);
		session.setAttribute("doc_detalle", doc_detalle);
		/*
		 * Flow flowReferencia = delegado.findFlow(flowParalelo.getFlow());
		 * String mensaje = flowReferencia.getNombredelflujo() + "(" +
		 * getFlow().getNombredelflujo() + ":" + getFlow().getCodigo() + ")"; if
		 * (doc_detalle != null) { Tree treePadre =
		 * delegado.findTree(doc_detalle.getDoc_maestro()
		 * .getTree().getNodopadre()); doc_detalle =
		 * grabarDocumento(doc_detalle, treePadre, user_logueado,
		 * swFlowParalelo, mensaje); session.setAttribute("doc_detalle",
		 * doc_detalle); }
		 */
		// se crea un documento maestro y detalles copia del original para
		// someterlo a un flow
		return "flows";
	}

	public Doc_detalle crearHijoDeFlowParaleloVariables(Usuario user_logueado,
			Doc_detalle doc_detalle, FlowParalelo flowParalelo, Flow flow,
			boolean swFlowParalelo) {

		Doc_maestro doc_maestro = doc_detalle.getDoc_maestro();

		Flow flowReferencia = delegado.findFlow(flowParalelo.getFlow());
		String mensaje = flowReferencia.getNombredelflujo() + "(copy)";
		if (doc_detalle != null) {
			Tree treePadre = delegado.findTree(doc_detalle.getDoc_maestro()
					.getTree().getNodopadre());
			doc_detalle = grabarDocumento(doc_detalle, treePadre,
					user_logueado, swFlowParalelo, mensaje);
		}
		return doc_detalle;

	}

	public String cancelarListar() {
		session.removeAttribute("edit");
		return "flowparalelo";
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Map<Integer, Integer> getPredecesoresOriginalesMap() {
		return predecesoresOriginalesMap;
	}

	public void setPredecesoresOriginalesMap(
			Map<Integer, Integer> predecesoresOriginalesMap) {
		this.predecesoresOriginalesMap = predecesoresOriginalesMap;
	}

	public List<Integer> getPredecesoresOriginales() {
		return predecesoresOriginales;
	}

	public void setPredecesoresOriginales(List<Integer> predecesoresOriginales) {
		this.predecesoresOriginales = predecesoresOriginales;
	}

	public String getPuedenPredecer() {
		return puedenPredecer;
	}

	public void setPuedenPredecer(String puedenPredecer) {
		this.puedenPredecer = puedenPredecer;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public FlowParalelo getFlowParalelo() {
		return flowParalelo;
	}

	public void setFlowParalelo(FlowParalelo flowParalelo) {
		this.flowParalelo = flowParalelo;
	}

	public List<FlowParalelo> getListarParalelos() {
		List<FlowParalelo> enviar = new ArrayList<FlowParalelo>();
		FlowParalelo flowParaleloImprime = null;
		try {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			// ESTE DOC_DETALLE ES PARA OBTENER EL TIPO DE DOCUMENTO DEL
			// FLUJOPARALELO
			Doc_detalle doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
					.getAttribute("doc_detalle") : null;
			if (doc_detalle == null) {
				treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
				if (treeNodoActual != null) {
					if (treeNodoActual.getTiponodo()
							- Utilidades.getNodoDocumento() == 0) {
						List<Doc_maestro> doc_maestros = new ArrayList<Doc_maestro>();
						doc_maestros = delegado
								.findAllDoc_maestros(treeNodoActual);
						Doc_maestro doc = (Doc_maestro) doc_maestros.get(0);
						List<Doc_detalle> doc_detalles = null;
						doc_detalles = delegado.encontrarDetalles(
								doc.getCodigo(), "");
						doc_detalle = doc_detalles.get(0);
					}
				}
			}
			// FIN ESTE DOC_DETALLE ES PARA OBTENER EL TIPO DE DOCUMENTO DEL
			// FLUJOPARALELO

			// ***************************************************************
			// ***************************************************************
			// ***************************************************************

			user_logueado.setDoc_detall(doc_detalle);

			user_logueado.setStrBuscar(getStrBuscar());

			listarParalelos = delegado.findParalelos(user_logueado);
			// ***************************************************************
			// ***************************************************************
			// ***************************************************************

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listarParalelos == null) {
			listarParalelos = new ArrayList<FlowParalelo>();
		} else {
			// verificamos si puede eliminar floparalelo plantilla para
			// documentos

			for (FlowParalelo flowParalelo : listarParalelos) {

				if (flowParalelo.getDoc_tipo() != null) {
					flowParalelo.setSwTipoPlantilladocumento(true);
					if (toPlantillaInDocFlowParalelo) {
						flowParalelo.setSwTipoPlantilladocumentoDelete(true);
					}
				}
				if (flowParalelo.isSolicituImpresion()) {
					flowParaleloImprime = flowParalelo;
					continue;
				}
				enviar.add(flowParalelo);
			}
		}
		// colocamosel flujo de impresion de primero
		if (flowParaleloImprime != null) {
			enviar.add(0, flowParaleloImprime);
		}

		return enviar;
	}

	public void setListarParalelos(List<FlowParalelo> listarParalelos) {
		this.listarParalelos = listarParalelos;
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

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public boolean istoPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;
	}

	public void settoPlantillaInDocFlowParalelo(
			boolean toPlantillaInDocFlowParalelo) {
		this.toPlantillaInDocFlowParalelo = toPlantillaInDocFlowParalelo;
	}

	public boolean isSwMostrarListaPreceder() {
		return swMostrarListaPreceder;
	}

	public void setSwMostrarListaPreceder(boolean swMostrarListaPreceder) {
		this.swMostrarListaPreceder = swMostrarListaPreceder;
	}

	public AreaDocumentos getAreaDocumentos() {
		areaDocumentos = session.getAttribute("areaDocumentos") != null ? (AreaDocumentos) session
				.getAttribute("areaDocumentos") : null;
		return areaDocumentos;
	}

	public void setAreaDocumentos(AreaDocumentos areaDocumentos) {
		session.setAttribute("areaDocumentos", areaDocumentos);
		this.areaDocumentos = areaDocumentos;
	}

	public Doc_tipo getDoc_tipo() {
		doc_tipo = session.getAttribute("doc_tipo") != null ? (Doc_tipo) session
				.getAttribute("doc_tipo") : null;
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		session.setAttribute("doc_tipo", doc_tipo);
		this.doc_tipo = doc_tipo;
	}

	public String getStrBuscar() {
		strBuscar = session.getAttribute("strBuscar") != null ? (String) session
				.getAttribute("strBuscar") : null;
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {

		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public Flow getFlowEditar() {
		return flowEditar;
	}

	public void setFlowEditar(Flow flowEditar) {
		this.flowEditar = flowEditar;
	}

	public boolean isSwHayParticipantesFlow() {
		return swHayParticipantesFlow;
	}

	public void setSwHayParticipantesFlow(boolean swHayParticipantesFlow) {
		this.swHayParticipantesFlow = swHayParticipantesFlow;
	}

	public boolean isSwHayGroupParticipantesFlow() {
		return swHayGroupParticipantesFlow;
	}

	public void setSwHayGroupParticipantesFlow(
			boolean swHayGroupParticipantesFlow) {
		this.swHayGroupParticipantesFlow = swHayGroupParticipantesFlow;
	}

	public boolean isSwHayGroupParticipantesFlowNotificacion() {
		return swHayGroupParticipantesFlowNotificacion;
	}

	public void setSwHayGroupParticipantesFlowNotificacion(
			boolean swHayGroupParticipantesFlowNotificacion) {
		this.swHayGroupParticipantesFlowNotificacion = swHayGroupParticipantesFlowNotificacion;
	}
}
