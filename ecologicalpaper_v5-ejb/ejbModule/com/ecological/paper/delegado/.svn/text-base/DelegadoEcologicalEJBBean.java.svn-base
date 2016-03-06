package com.ecological.paper.delegado;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Hibernate;

import com.ecological.NegocioEcological;
import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.hilosBackend.MyHiloEnvioMails;
import com.ecological.paper.documentos.Doc_consecutivo;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepcionesContextType;
import com.ecological.paper.ecologicaexcepciones.ExceptionTree;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowOnlyNotificacionRole;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;
import com.util.file.Archivo;

@Stateless
public class DelegadoEcologicalEJBBean extends NegocioEcological implements
		DelegadoEcologicalEJBLocal {

	@EJB
	private DelegadoEJBLocal delegado = null;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;

	public void crearSolicitudImpresion(Solicitudimpresion solicitudimpresion)
			throws ExceptionTree {
		delegado.create(solicitudimpresion);
		for (SolicitudImpPart sip : solicitudimpresion.getsolicitudImpParts()) {
			sip.setSolicitudimpresion(solicitudimpresion);
			delegado.create(sip);
		}

	}

	public Tree crearNuevoTree(Tree tree, Usuario user_logueado,
			boolean swHeredarPermisos) throws ExceptionTree {
		delegado.crearNuevoTree(tree, user_logueado);
		llenarDiasHabiles(tree);
		Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
		seguridad_User_Lineal.setUsuario(user_logueado);
		seguridad_User_Lineal.setTree(tree);
		seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
		if (tree.getTiponodo() == Utilidades.getNodoCarpeta()) {
			try {
				darSeguridadNodoSoloEnCrearNoDamosPublicarDocumento(seguridad_User_Lineal);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			darSeguridadNodo(seguridad_User_Lineal);
		}
		return tree;

	}

	public void saveUsuarioOperacionesFlowsDiferenteDocFlow(Tree noSeUsa,
			Usuario user_logueado, Doc_detalle doc_detalle,
			List<Usuario> usuarioSeleccionados) throws EcologicaExcepciones {

		// apenas estamos grabando participantes, sin enviar el flujo, pore lo
		// tanto, temporalmente es borrador
		Doc_estado doc_estado = null;
		doc_estado = new Doc_estado();
		doc_estado.setCodigo(Utilidades.getBorrador());
		doc_estado = delegado.findDocEstado(doc_estado);
		// eliminamos todos las operaciones anteriormente seleccionados de
		// docdetalle en flow
		Flow flow = null;

		/* Buscamos el flow por si ya se creo */
		Flow f = doc_detalle.getFlowEditar();

		flow = f;
		if (flow == null) {
			flow = new Flow();
			flow.setCondicional(false);
			flow.setSecuencial(false);
			flow.setDoc_detalle(doc_detalle);
			flow.setDuenio(user_logueado);
			flow.setEstado(doc_estado);
			Calendar cal = Calendar.getInstance();
			flow.setStatus(true);
			flow.setFecha_creado(cal.getTime());
			if (doc_detalle.getOrigen() != null && doc_detalle.getOrigen() > 0) {
				flow.setOrigen(doc_detalle.getOrigen());
			} else {
				flow.setOrigen(Utilidades.getOrigenDocumentoFlow());
			}

			try {
				delegado.create(flow);
				doc_detalle.setFlowEditar(flow);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		Flow_Participantes flow_Participantes;
		Doc_estado firma = new Doc_estado();
		firma.setCodigo(Utilidades.getSinFirmar());
		for (Usuario value : usuarioSeleccionados) {

			Usuario usuario2 = (Usuario) value;
			if (usuario2 != null && usuario2 instanceof Usuario) {
				flow_Participantes = new Flow_Participantes();
				flow_Participantes.setParticipante(usuario2);
				flow_Participantes.setFlow(flow);
				try {
					flow_Participantes.setFirma(firma);
					flow_Participantes.setStatus(true);
					delegado.create(flow_Participantes);
					// GRABAMOS EL TIEMPO DEL FLUJOS
					FlowControlByUsuarioBean flowControlByUsuarioBean = new FlowControlByUsuarioBean();
					flowControlByUsuarioBean.setRole(null);
					flowControlByUsuarioBean.setFlow(flow_Participantes
							.getFlow());
					flowControlByUsuarioBean
							.setFlow_Participantes(flow_Participantes);
					flowControlByUsuarioBean.setContEnvMailRemember(0);
					flowControlByUsuarioBean.setEnvMailRemember(Utilidades
							.isActivo());
					flowControlByUsuarioBean.setHrsAntesToEnvMailRemember(0);
					flowControlByUsuarioBean.setHorasAcumuladas(0);
					flowControlByUsuarioBean.setHorasAsignadas(0);

					flowControlByUsuarioBean.setMinutosAcumulados(0);
					flowControlByUsuarioBean.setMinutosAsignados(0);
					flowControlByUsuarioBean.setStatus(Utilidades.isActivo());
					flowControlByUsuarioBean.setSwEsRole(false);
					flowControlByUsuarioBean.setFechaMailRemember(new Date());
					delegado.create(flowControlByUsuarioBean);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			}
		}

	}

	public void saveUsuarioOperacionesFlows(Tree noSeUsa,
			Usuario user_logueado, Doc_detalle doc_detalle,
			List<Usuario> usuarioSeleccionados) throws EcologicaExcepciones {
		boolean swDocFlow = false;

		if (doc_detalle.getOrigen() != null) {

			swDocFlow = (doc_detalle.getOrigen() - Utilidades
					.getOrigenDocumentoFlow()) == 0;
		}

		if (!swDocFlow) {
			// en caso de ser flujos de impresion etc
			saveUsuarioOperacionesFlowsDiferenteDocFlow(noSeUsa, user_logueado,
					doc_detalle, usuarioSeleccionados);
		} else {

			// apenas estamos grabando participantes, sin enviar el flujo, pore
			// lo
			// tanto, temporalmente es borrador
			Doc_estado doc_estado = null;
			doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getBorrador());
			doc_estado = delegado.findDocEstado(doc_estado);
			// eliminamos todos las operaciones anteriormente seleccionados de
			// docdetalle en flow
			Flow flow = null;
			/* Buscamos el flow por si ya se creo */
			Flow f = doc_detalle.getFlowEditar();
			List listaFlowParticipantes = delegado
					.findByFlowParticipantesRoleIsNull(doc_detalle, true);
			Iterator it = listaFlowParticipantes.iterator();

			while (it.hasNext()) {
				Flow_Participantes flow_P = (Flow_Participantes) it.next();
				// if (flow == null) {

				flow = flow_P.getFlow();
				flow.setFlow_P(flow_P);

				// }
				// BORRAMOS PRIMERO EL CONTROL DE TIEMPÒ
				List<FlowControlByUsuarioBean> controlTiempo = delegado
						.find_allFlowControlByFlowBean(flow);

				if (controlTiempo != null && !controlTiempo.isEmpty()) {
					for (FlowControlByUsuarioBean borrar : controlTiempo) {

						delegado.destroy(borrar);
					}
				}

				// LUEGO BORRAMOS LOS PARTICIPANTES
				try {

					delegado.destroy(flow_P);

				} catch (Exception b) {
					b.printStackTrace();
					// TODO: handle exception
				}

			}

			/* Buscamos el flow por si ya se creo */
			f = doc_detalle.getFlowEditar();

			if (f == null) {
				List flows = delegado.findByFlow(doc_detalle);
				int size = flows.size();
				// solamente debe haber un solo flow edo borrador,en revision o
				// aprobacion el flujo ese documento
				int j = 0;
				for (int i = 0; i < size; i++) {
					f = (Flow) flows.get(i);
					j = i;// agarramos el ultimo docDetalle
					break;

				}
			}

			flow = f;
			if (flow == null) {
				flow = new Flow();
				flow.setCondicional(false);
				flow.setSecuencial(false);
				flow.setDoc_detalle(doc_detalle);
				flow.setDuenio(user_logueado);
				flow.setEstado(doc_estado);
				Calendar cal = Calendar.getInstance();
				flow.setStatus(true);
				flow.setFecha_creado(cal.getTime());
				if (doc_detalle.getOrigen() != null
						&& doc_detalle.getOrigen() > 0) {
					flow.setOrigen(doc_detalle.getOrigen());
				} else {
					flow.setOrigen(Utilidades.getOrigenDocumentoFlow());
				}

				try {
					delegado.create(flow);
					doc_detalle.setFlowEditar(flow);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			Flow_Participantes flow_Participantes;
			Doc_estado firma = new Doc_estado();
			firma.setCodigo(Utilidades.getSinFirmar());
			if (usuarioSeleccionados != null && !usuarioSeleccionados.isEmpty()) {
				for (Usuario value : usuarioSeleccionados) {
					Usuario usuario2 = (Usuario) value;
					if (usuario2 != null && usuario2 instanceof Usuario) {
						flow_Participantes = new Flow_Participantes();

						flow_Participantes.setParticipante(usuario2);
						flow_Participantes.setFlow(flow);
						try {
							flow_Participantes.setFirma(firma);
							flow_Participantes.setStatus(true);
							delegado.create(flow_Participantes);
							// GRABAMOS EL TIEMPO DEL FLUJOS
							FlowControlByUsuarioBean flowControlByUsuarioBean = new FlowControlByUsuarioBean();
							flowControlByUsuarioBean.setRole(null);
							flowControlByUsuarioBean.setFlow(flow_Participantes
									.getFlow());
							flowControlByUsuarioBean
									.setFlow_Participantes(flow_Participantes);
							flowControlByUsuarioBean.setContEnvMailRemember(0);
							flowControlByUsuarioBean
									.setEnvMailRemember(Utilidades.isActivo());
							flowControlByUsuarioBean
									.setHrsAntesToEnvMailRemember(0);
							flowControlByUsuarioBean.setHorasAcumuladas(0);
							flowControlByUsuarioBean.setHorasAsignadas(0);

							flowControlByUsuarioBean.setMinutosAcumulados(0);
							flowControlByUsuarioBean.setMinutosAsignados(0);
							flowControlByUsuarioBean.setStatus(Utilidades
									.isActivo());
							flowControlByUsuarioBean.setSwEsRole(false);
							flowControlByUsuarioBean
									.setFechaMailRemember(new Date());
							delegado.create(flowControlByUsuarioBean);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {

						}
					}
				}
			}
		}

	}

	public void saveRoleOperacionesFlows(Tree t, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Role> roleSeleccionados)
			throws EcologicaExcepciones {
		// apenas estamos grabando participantes, sin enviar el flujo, pore lo
		// tanto, temporalmente es borrador
		Doc_estado doc_estado = null;
		doc_estado = new Doc_estado();
		doc_estado.setCodigo(Utilidades.getBorrador());
		doc_estado = delegado.findDocEstado(doc_estado);
		Flow flow = null;

		// BUSCAMOS Y SI NO ENCONTRAMOS, CREAMOS EL NEW FLOW
		// ESTE SE PUDO CREAR EN OPERACIONES Y VICEVERSA, EN ROL Y SU ESTADO ES
		// EN BORRADOR
		/* Buscamos el flow por si ya se creo(borrador en revision o aprobacion) */
		List flows = delegado.findByFlow(doc_detalle);
		int size = flows.size();
		Flow f = null;
		// solamente debe haber un solo flow edo borrador,en revision o
		// aprobacion el flujo ese documento
		int j = 0;
		if (doc_detalle.getFlowEditar() != null) {
			f = doc_detalle.getFlowEditar();
		} else {
			for (int i = 0; i < size; i++) {
				f = (Flow) flows.get(i);
				j = i;
			}
		}
		// si doc detalle me trae un flow p�ra editar, j se mantiene en cero y
		// pasa
		// pero f no es nulo, por lo tanto el flow no es nulo y no c crea
		if (j <= 0) {

			// SI NO ESTA EL FLOW, LO CREAMOS
			flow = f;
			if (flow == null) {
				flow = new Flow();
				flow.setCondicional(false);
				flow.setSecuencial(false);
				flow.setDoc_detalle(doc_detalle);
				flow.setDuenio(user_logueado);
				flow.setEstado(doc_estado);
				Calendar cal = Calendar.getInstance();
				flow.setStatus(true);
				flow.setFecha_creado(cal.getTime());
				flow.setOrigen(Utilidades.getOrigenDocumentoFlow());
				try {
					flow = delegado.createFlow(flow);
					doc_detalle.setFlowEditar(flow);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			try {

				// eliminamos todos las operaciones de referencia role,
				// anteriormente seleccionados
				List listaFlowRoles = delegado.findByFlow_referencia_role(flow);
				Iterator it = listaFlowRoles.iterator();
				// Role role = null;

				while (it.hasNext()) {

					Flow_referencia_role flow_Role = (Flow_referencia_role) it
							.next();

					if (flow == null) {
						flow = flow_Role.getFlow();
//						System.out.println("flow_Role.getFlow().getCodigo()="
//								+ flow_Role.getFlow().getCodigo());
					}
					// BORRAMOS LOS ROLES CON TIEMPO EN EL FLUJO
					// BORRAMOS PRIMERO EL CONTROL DE TIEMPÒ
					List<FlowControlByUsuarioBean> controlTiempo = delegado
							.find_allFlowControlByRoleBean(flow_Role.getRole(),
									flow_Role.getFlow());
					if (controlTiempo != null && !controlTiempo.isEmpty()) {
						for (FlowControlByUsuarioBean borrar : controlTiempo) {
							delegado.destroy(borrar);
						}
					}

					// BORRAMOS LOS ROLES
					delegado.destroy(flow_Role);
				}
				// introducimos las nuevas operaciones al rol

				Flow_referencia_role flow_referencia_role;
				if (roleSeleccionados != null && !roleSeleccionados.isEmpty()) {
					for (Role role : roleSeleccionados) {
						if (role != null && role instanceof Role) {

							flow_referencia_role = new Flow_referencia_role();
							flow_referencia_role.setRole(role);
							flow_referencia_role.setFlow(flow);
							try {
								// GRABAMOS LA REFERENCIA ROLE CON EL FLOW
								delegado.create(flow_referencia_role);

								// GRABAMOS EL TIEMPO DEL FLUJOS
								FlowControlByUsuarioBean flowControlByUsuarioBean = new FlowControlByUsuarioBean();
								flowControlByUsuarioBean
										.setFlow(flow_referencia_role.getFlow());
								flowControlByUsuarioBean
										.setRole(flow_referencia_role.getRole());
								flowControlByUsuarioBean.setSwEsRole(true);
								flowControlByUsuarioBean
										.setFlow_Participantes(null);
								flowControlByUsuarioBean
										.setContEnvMailRemember(0);
								flowControlByUsuarioBean
										.setEnvMailRemember(Utilidades
												.isActivo());
								// flowControlByUsuarioBean.setHrsAntesToEnvMailRemember(Utilidades.getHorasAntesEnviaMails());
								flowControlByUsuarioBean
										.setHrsAntesToEnvMailRemember(0);
								flowControlByUsuarioBean.setHorasAcumuladas(0);
								flowControlByUsuarioBean.setHorasAsignadas(0);
								flowControlByUsuarioBean
										.setMinutosAcumulados(0);
								flowControlByUsuarioBean.setMinutosAsignados(0);
								flowControlByUsuarioBean.setStatus(Utilidades
										.isActivo());
								flowControlByUsuarioBean
										.setFechaMailRemember(new Date());
								delegado.create(flowControlByUsuarioBean);

							} catch (Exception e) {
								System.out.println("e.toString()="
										+ e.toString());
								e.printStackTrace();
							} finally {
							}

						}
					}
				}

			} catch (EcologicaExcepciones e) {
			}

		}
	}
	
	
	
	public void saveRoleOperacionesFlowsNotificacion(Tree t, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Role> roleSeleccionados)
			throws EcologicaExcepciones {
		// apenas estamos grabando participantes, sin enviar el flujo, pore lo
		// tanto, temporalmente es borrador
//		Doc_estado doc_estado = null;
//		doc_estado = new Doc_estado();
//		doc_estado.setCodigo(Utilidades.getBorrador());
//		doc_estado = delegado.findDocEstado(doc_estado);
		Flow flow = null;

		// BUSCAMOS Y SI NO ENCONTRAMOS, CREAMOS EL NEW FLOW
		// ESTE SE PUDO CREAR EN OPERACIONES Y VICEVERSA, EN ROL Y SU ESTADO ES
		// EN BORRADOR
		/* Buscamos el flow por si ya se creo(borrador en revision o aprobacion) */
		List flows = delegado.findByFlow(doc_detalle);
		int size = flows.size();
		Flow f = null;
		// solamente debe haber un solo flow edo borrador,en revision o
		// aprobacion el flujo ese documento
		int j = 0;
		if (doc_detalle.getFlowEditar() != null) {
			f = doc_detalle.getFlowEditar();
		} else {
			for (int i = 0; i < size; i++) {
				f = (Flow) flows.get(i);
				j = i;
			}
		}

			flow = f;

			try {

				// eliminamos todos las operaciones de FlowOnlyNotificacionRole,
				// anteriormente seleccionados
				List listaFlowRoles = delegado.findByFlowOnlyNotificacionRole(flow);
				Iterator it = listaFlowRoles.iterator();
				// Role role = null;

				while (it.hasNext()) {

					FlowOnlyNotificacionRole flow_Role = (FlowOnlyNotificacionRole) it
							.next();

					if (flow == null) {
						flow = flow_Role.getFlow();
//						System.out.println("flow_Role.getFlow().getCodigo()="
//								+ flow_Role.getFlow().getCodigo());
					}

					// BORRAMOS LOS ROLES
					delegado.destroy(flow_Role);
				}
				// introducimos las nuevas operaciones al rol

				FlowOnlyNotificacionRole flowOnlyNotificacionRole;
				if (roleSeleccionados != null && !roleSeleccionados.isEmpty()) {
					for (Role role : roleSeleccionados) {
						if (role != null && role instanceof Role) {

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
				}

			} catch (EcologicaExcepciones e) {
			}

	}

	
	
	

	public void firmarFlows(Flow_Participantes flow_Participante,
			Flow_ParticipantesAttachment flow_ParticipantesAttachment)
			throws EcologicaExcepciones {

		if (flow_ParticipantesAttachment != null) {
			delegado.create(flow_ParticipantesAttachment);
		}
		// aqui ya firmamois el flujo------------------------------
		delegado.edit(flow_Participante);
		// -----------------------------------------
		// EL CONTROL DE TIEMPO LO PARAMOS
		// ME DEVUELVE UN SOLO PARTICIPANTE ESTE FOR
		List<FlowControlByUsuarioBean> flowControlByUsuarioBean = delegado
				.find_allControlTimeByFlowParticipAndFlow(flow_Participante);
		for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
			controlTime.setSwStartHilo(false);
			delegado.edit(controlTime);
		}
		// si es secuencial, activamos el tiempo al siguiente
		// participante
		if (flow_Participante.getFlow().isSecuencial()) {
			List<Flow_Participantes> listaAuxParticipantes = delegado
					.findByFlowParticipantes(flow_Participante.getFlow());
			// Hago un ciclo hasta encontrar quien le toca firmar
			// para llevar su tiempoo
			boolean swSalte = false;
			for (Flow_Participantes flow_Part : listaAuxParticipantes) {
				if (flow_Part.getFirma().getCodigo() == Utilidades
						.getSinFirmar()) {
					// BUSCAMOS EL CONTROL DE TIEMPO DE ESE
					// PARTICIPANTE Y LO ACTIVAMOS
					flowControlByUsuarioBean = delegado
							.find_allControlTimeByFlowParticipAndFlow(flow_Part);
					for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
						// activamos sus hilo para empieze a contar
						// su tiempo
						controlTime.setSwStartHilo(true);
						delegado.edit(controlTime);
						break;// primer for , salimos con un solo
						// registro encontrado
					}
					swSalte = true;// segundo for

				}
				if (swSalte) {
					break;
				}

			}
		}

		// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA MANDARLES
		// UN MAIL
		if (flow_Participante.getFlow().isNotificacionMail()) {
			MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
					delegado.mandarMailsUsuarios(flow_Participante.getFlow(),
							flow_Participante),
					delegado.find_allConfiguracion(),flow_Participante.getFlow());
			myHiloEnvioMails.start();
			//
		}

		// ahora chequeamos si terminamos o no el flujo con la
		// anterior firma
		// chequeamos si es condicionbal, secuencial y elk tipo de
		// firma, para ver si altera
		// las demas firmas del flow
		// AQUI SE ALTERA EL DOCUMENTO DETALLE
		checkFlowToContinuar(flow_Participante);
		// si no es una solicitud de impresion, alteramos el
		// documento
		Flow flow = null;
		if (flow_Participante != null) {
			flow = flow_Participante.getFlow();
			Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
			solicitudimpresion.setFlow(flow);
			solicitudimpresion = delegado
					.findSolicitudimpresionByFlow(solicitudimpresion);
			if (solicitudimpresion == null) {
				// AQUI SE ALTERA EL DOCUMENTO DETALLE
				if (flow_Participante.getFlow().getOrigen() == Utilidades
						.getOrigenDocumentoFlow()) {
					chequearForOldVersionVigente(flow_Participante);
				}
				// SOLO PARA SOLICITUD DE IMPRESION
			} else {

				// REVISAMOS SI EL FLUJO FUE APROBADO O REVISADO PARA ALETARRA
				// LA ORDEN DE IMPRESION
				flow = delegado.findFlow(flow);
				if (Utilidades.getAprobado() == flow.getEstado().getCodigo()) {
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getAprobado());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudimpresion.setEstado(doc_estado);
					delegado.edit(solicitudimpresion);

					// si el flujo es de revision-------------------
				} else if (Utilidades.getRechazado() == flow.getEstado()
						.getCodigo()) {
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getRechazado());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudimpresion.setEstado(doc_estado);
					delegado.edit(solicitudimpresion);

				}

			}
		}

	}

	public void MyHiloEnvioMails(Flow flow) {
		if (flow.isNotificacionMail()) {
			MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
					delegado.mandarMailsUsuarios(flow),
					delegado.find_allConfiguracion(),flow);
			myHiloEnvioMails.start();
		}

	}

	public void MyHiloEnvioMails(Flow flow,
			Flow_Participantes flujo_participantes) {
		if (flow.isNotificacionMail()) {
			MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
					delegado.mandarMailsUsuarios(flujo_participantes.getFlow(),
							flujo_participantes),
					delegado.find_allConfiguracion(),flujo_participantes.getFlow());
			myHiloEnvioMails.start();
		}
		/*
		 * MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
		 * mandarMailsUsuarios( flujo_participantes.getFlow(),
		 * flujo_participantes));
		 */
	}

	public void devolucionStartFlow(Flow_Participantes flow_Participante1) {

		HashMap unico = new HashMap();

		String comentario = flow_Participante1.getComentario();

		List<Flow> subFlujos = (List<Flow>) delegado
				.findByFlowParaleloAllFlows(flow_Participante1.getFlow()
						.getFlowParalelo());
		for (Flow subFlujo : subFlujos) {

			Flow_Participantes flow_Participante = new Flow_Participantes();
			flow_Participante.setParticipante(flow_Participante1
					.getParticipante());
			flow_Participante.setComentario(comentario);
			// devolvemos los particpantes del flujo
			if (!unico.containsKey(subFlujo.getCodigo())) {
				unico.put(subFlujo.getCodigo(), subFlujo.getCodigo());
				// DEVOLVEMOS LOS PARTICIPANTES DEL FLOWPARTICIPANTE ACTUAL
				List<Flow_Participantes> flow_ParticipantesLst = delegado
						.findAllFlowParticipantesByFlow(subFlujo);
				devolverParticipantes(flow_Participante, flow_ParticipantesLst);
				colocarFlujoEnSuEstadoRespectivo(subFlujo);
			}

			// AHORA VEMOS LOS PREDECESORES DEL subFlujo
			String precede = subFlujo.getPrecedencia() != null ? (String) subFlujo
					.getPrecedencia() : "";
			if (!isEmptyOrNull(precede)) {
				StringTokenizer precedenciaStk = new StringTokenizer(precede,
						",");
				String prec = "";
				while (precedenciaStk.hasMoreElements()) {
					prec = (String) precedenciaStk.nextElement();
					Flow flowPrecede = new Flow();
					flowPrecede.setCodigo(new Long(prec));
					if (!unico.containsKey(flowPrecede.getCodigo())) {
						unico.put(flowPrecede.getCodigo(),
								flowPrecede.getCodigo());
						flowPrecede = delegado.findFlow(flowPrecede);
						List<Flow_Participantes> flow_ParticipantesLst = delegado
								.findAllFlowParticipantesByFlow(flowPrecede);
						devolverParticipantes(flow_Participante,
								flow_ParticipantesLst);
						colocarFlujoEnSuEstadoRespectivo(flowPrecede);
					}

				}
			}

		}

	}

	public void devolverParticipantes(Flow_Participantes flow_Participante,
			List<Flow_Participantes> flow_ParticipantesLst) {
		for (Flow_Participantes devolver : flow_ParticipantesLst) {
			delegado.devolvemos(flow_Participante, devolver);
			if (devolver.getFlow().isNotificacionMail()) {

				MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
						delegado.mandarMailsUsuarios(devolver.getFlow(),
								devolver), delegado.find_allConfiguracion(),devolver.getFlow());
				myHiloEnvioMails.start();
				//
			}
		}

	}

	public void devolucionFirmaFlowExecute(Flow_Participantes flow_Participante) {
		String comentario = "";
		try {
			if (flow_Participante != null) {
				comentario = flow_Participante.getComentario();
			}
			if (flow_Participante != null
					&& flow_Participante.getCodigo() != null) {
				// PRIMERO VEMOS EL FLUJO SIMPLE, SIN PRECEDER...
				Flow flowPrecede = delegado.flowQuePrecede(flow_Participante);

				// no hay mas bloques de flujos a nivel anterior
				if (flow_Participante.getFlow().isSecuencial()
						&& flowPrecede == null) {
					Flow_Participantes devolver = delegado
							.precederSimple(flow_Participante);
					if (devolver != null) {
						Flow_Participantes fpComentario = new Flow_Participantes();
						fpComentario.setComentario(comentario);
						fpComentario.setParticipante(flow_Participante
								.getParticipante());
						delegado.devolvemos(fpComentario, devolver);
						// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
						// MANDARLES
						// UN MAIL
						if (flow_Participante.getFlow().isNotificacionMail()) {
							flow_Participante.setComentario(devolver
									.getComentario());
							MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
									delegado.mandarMailsUsuarios(
											flow_Participante.getFlow(),
											flow_Participante),
									delegado.find_allConfiguracion(),flow_Participante.getFlow());
							myHiloEnvioMails.start();
							//
						}
					}
				} else {// SI HAY PREDECESORES ...
						// pueden sea el medio o ultimo en
						// firmar ..
						// entonces retrocedemos al flowParticpante anterior

					Flow_Participantes devolver = delegado
							.precederSimple(flow_Participante);
					if (flow_Participante.getFlow().isSecuencial()
							&& devolver != null) {
						Flow_Participantes fpComentario = new Flow_Participantes();
						fpComentario.setComentario(comentario);
						fpComentario.setParticipante(flow_Participante
								.getParticipante());
						delegado.devolvemos(fpComentario, devolver);
						// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
						// MANDARLES
						// UN MAIL
						if (flow_Participante.getFlow().isNotificacionMail()) {
							flow_Participante.setComentario(devolver
									.getComentario());
							MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
									delegado.mandarMailsUsuarios(
											flow_Participante.getFlow(),
											flow_Participante),
									delegado.find_allConfiguracion(),flow_Participante.getFlow());
							myHiloEnvioMails.start();
							//
						}
					} else {
						try {
							// NOS VAMOS AL FLUJO ANTERIOR
							// // a.2)coloco las firmas mias firmadas y no
							// firmadas
							// (flow_Participante)
							// en estado "sin firmar" con un mensaje del
							// flow_Participante
							List<Flow_Participantes> fps = delegado
									.findByFlowParticipantes(flow_Participante
											.getFlow());

							for (Flow_Participantes fpcolcarSinFirma : fps) {
								// si este es nulo, implica que viene por
								// devolucionFirmaFlowEnParalelo internamente.
								// donde
								// flowparticipante solo trae el flow
								if (flow_Participante.getCodigo() == null) {
									flow_Participante = fpcolcarSinFirma;
								}
								System.out
										.println("fpcolcarSinFirma.getComentario()="
												+ fpcolcarSinFirma
														.getComentario());
								Flow_Participantes fpComentario = new Flow_Participantes();
								fpComentario.setComentario(comentario);
								fpComentario.setParticipante(flow_Participante
										.getParticipante());
								delegado.devolvemos(fpComentario,
										fpcolcarSinFirma);
								// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW
								// PARA
								// MANDARLES
								// UN MAIL

								if (flow_Participante.getFlow()
										.isNotificacionMail()) {
									flow_Participante
											.setComentario(fpcolcarSinFirma
													.getComentario());
									MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
											delegado.mandarMailsUsuarios(
													flow_Participante.getFlow(),
													fpcolcarSinFirma),
											delegado.find_allConfiguracion(),flow_Participante.getFlow());
									myHiloEnvioMails.start();
								}

							}
							// todas enm estado "sin firmar" con un mensaje del
							// flow_Participante
							fps = delegado.findByFlowParticipantes(flowPrecede);
							for (Flow_Participantes fpcancelados : fps) {
								if (fpcancelados != null) {
									System.out
											.println("fpcancelados.getComentario()="
													+ fpcancelados
															.getComentario());
									Flow_Participantes fpComentario = new Flow_Participantes();
									fpComentario.setComentario(comentario);
									fpComentario
											.setParticipante(flow_Participante
													.getParticipante());
									delegado.devolvemos(fpComentario,
											fpcancelados);

									// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW
									// PARA
									// MANDARLES
									// UN MAIL

									if (fpcancelados.getFlow()
											.isNotificacionMail()) {
										fpcancelados.setComentario(fpcancelados
												.getComentario());
										MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
												delegado.mandarMailsUsuarios(
														fpcancelados.getFlow(),
														fpcancelados),
												delegado.find_allConfiguracion(),fpcancelados.getFlow());
										myHiloEnvioMails.start();
									}

									// }

								}

							}

							// colocamos el flujo aprobacion o revision como sea
							// el
							// caso
							/*
							 * Doc_estado doc_estado = null; if
							 * (Utilidades.getAprobado() == flowPrecede
							 * .getEstado().getCodigo()) { doc_estado = new
							 * Doc_estado(); doc_estado.setCodigo(Utilidades
							 * .getEnAprobacion()); doc_estado =
							 * delegado.findDocEstado(doc_estado);
							 * flowPrecede.setEstado(doc_estado); } else if
							 * (Utilidades.getRevisado() == flowPrecede
							 * .getEstado().getCodigo()) { doc_estado = new
							 * Doc_estado(); doc_estado
							 * .setCodigo(Utilidades.getEnRevision());
							 * doc_estado = delegado.findDocEstado(doc_estado);
							 * flowPrecede.setEstado(doc_estado); }
							 * delegado.edit(flowPrecede);
							 */
							colocarFlujoEnSuEstadoRespectivo(flowPrecede);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void colocarFlujoEnSuEstadoRespectivo(Flow flowPrecede) {
		// colocamos el flujo aprobacion o revision como sea
		// el
		// caso
		Doc_estado doc_estado = null;
		if (Utilidades.getAprobado() == flowPrecede.getEstado().getCodigo()) {
			doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getEnAprobacion());
			doc_estado = delegado.findDocEstado(doc_estado);
			flowPrecede.setEstado(doc_estado);
		} else if (Utilidades.getRevisado() == flowPrecede.getEstado()
				.getCodigo()) {
			doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getEnRevision());
			doc_estado = delegado.findDocEstado(doc_estado);
			flowPrecede.setEstado(doc_estado);
		}
		try {
			delegado.edit(flowPrecede);
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upload(Tree tree, Doc_maestro doc_maestro,
			Doc_detalle doc_detalle, File _upFile, Usuario user_logueado,
			String contextType, Map llenarSessiones)
			throws EcologicaExcepciones, EcologicaExcepcionesContextType {
		Archivo archivo = new Archivo();
		Calendar fecha = Calendar.getInstance();
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			Configuracion conf = configuraciones.get(0);

			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		List doc_maestros = delegado.consecutivoseRepite(doc_maestro, tree);
		if (doc_maestros != null
				&& !doc_maestros.isEmpty()
				|| (doc_maestro != null && doc_maestro.getConsecutivo().equals(
						"00000000"))) {
			boolean swNocRepite = false;
			if (!doc_maestro.getConsecutivo().equals(
					Utilidades.getConsecutivoVacio())) {

				llenarSessiones
						.put("conecutivo1", doc_maestro.getConsecutivo());
				/*
				 * session.setAttribute("conecutivo1", doc_maestro
				 * .getConsecutivo());
				 */
			}
			while (!swNocRepite) {
				Doc_consecutivo doc_consecutivo = new Doc_consecutivo();
				delegado.create(doc_consecutivo);
				doc_maestro.setConsecutivo(String.valueOf(doc_consecutivo
						.getCodigo()));
				doc_maestro.setConsecutivo(numSacopDo("",
						doc_maestro.getConsecutivo(),
						Utilidades.getConsecutivoLength()));
				doc_maestros = delegado.consecutivoseRepite(doc_maestro, tree);
				if (doc_maestros != null && !doc_maestros.isEmpty()) {
				} else {
					llenarSessiones.put("conecutivo2",
							doc_maestro.getConsecutivo());
					/*
					 * session.setAttribute("conecutivo2", doc_maestro
					 * .getConsecutivo());
					 */
					swNocRepite = true;
				}

			}

		}

		Doc_maestro d = new Doc_maestro();
		// continuar

		doc_maestro.setTree(tree);

		d = delegado.nombreDocseRepite(doc_maestro);
		/*
		 * if (d != null) { throw new EcologicaExcepciones(
		 * messages.getString("error_nomdocexiste"));
		 * 
		 * 
		 * }
		 */
		String nombreSolamente = "";
		try {
			nombreSolamente = _upFile.getName().substring(0,
					_upFile.getName().lastIndexOf("."));

		} catch (Exception e) {
			nombreSolamente = _upFile.getName();
		}

		doc_maestro.setBusquedakeys(getWordKeys(doc_maestro.getBusquedakeys(),
				nombreSolamente));
		delegado.crearNuevoTree(tree, user_logueado);
		doc_maestro.setTree(tree);
		doc_detalle.setSize_doc(_upFile.length());
		doc_detalle.setDatecambio(fecha.getTime());
		doc_detalle.setDoc_maestro(doc_maestro);
		// si no es valida la extension o el contextype, te genera un
		// mensaje internmo y se sale

		System.out.println("--------------------------------------------");
		System.out.println("_upFile.getName()=" + _upFile.getName());
		System.out.println("nombreSolamente=" + nombreSolamente);

		if (!buscarContextTypeTipoArchivo(doc_detalle, _upFile.getName(),
				contextType)) {
			// dispara una exception y cae en catch
		}

		// algun dia se arreglara los campos blob en postgres
		Blob blob = null;
		try {
			blob = Hibernate.createBlob(archivo.fileToinputStream(_upFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc_detalle.setData(blob);

		doc_maestro.setUsuario_creador(user_logueado);
		if (doc_maestro.getFecha_creado() == null) {
			doc_maestro.setFecha_creado(fecha.getTime());
		}
		doc_detalle.setModificadoPor(doc_detalle.getDuenio());
	 
		try {
			doc_maestro.setEmpresa(doc_maestro.getUsuario_creador()
					.getEmpresa());
		} catch (Exception e) {

			doc_maestro.setEmpresa(user_logueado.getEmpresa());
		}

		delegado.create(doc_maestro);
		delegado.create(doc_detalle);
		if (swConfiguracionHayData) {
			if (swPostgresVieneDeConfiguracion) {
				Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
				doc_dataPostgres.setData_doc_postgres(archivo
						.convertirFileADataBinariaDinamico(_upFile));
				doc_dataPostgres.setDoc_detalle(doc_detalle);
				doc_dataPostgres.setStatus(Utilidades.isActivo());

				delegado.create(doc_dataPostgres);
			}
		} else if ("1".equalsIgnoreCase(confmessages.getString("bdpostgres"))) {
			// si es una base de datos postgres, guardamos la data en
			// otra tabla

			Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
			doc_dataPostgres.setData_doc_postgres(archivo
					.convertirFileADataBinariaDinamico(_upFile));
			doc_dataPostgres.setDoc_detalle(doc_detalle);
			doc_dataPostgres.setStatus(Utilidades.isActivo());
			delegado.create(doc_dataPostgres);
		}

		// si pasa por aca deshabilitamos el bton

		if (_upFile != null) {
			_upFile.delete();
		}
	}

}
