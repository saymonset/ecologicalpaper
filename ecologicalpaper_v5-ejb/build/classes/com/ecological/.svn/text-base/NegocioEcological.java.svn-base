package com.ecological;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;

import com.ecological.configuracion.Configuracion;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepcionesContextType;
import com.ecological.paper.ecologicaexcepciones.ExceptionSubVersion;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.subversion.RepositorioSVN;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class NegocioEcological {
	@EJB
	private DelegadoEJBLocal delegado = null;

	private boolean swFueAprobado;
	private List mandarMailUsuarios;
	private HashMap queTipoContextType = new HashMap();
	public ResourceBundle messages = ResourceBundle
			.getBundle("com.ecological.resource.ecologicalpaper");
	public ResourceBundle confmessages = ResourceBundle
			.getBundle("com.util.resource.ecological_conf");
	private String carpeta_compartida;
	private Configuracion conf = new Configuracion();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();

	// da la seguridad completa al nuevo nodo creado y a su usuario duenio y
	// de forma descendente le da permiso de solo vista a la raiz
	public void darSeguridadNodo(Seguridad_User_Lineal seguridad_User_Lineal) {

		// el primer nbodo . le damos toda la permisologia
		Tree nodoPermView = seguridad_User_Lineal.getTree();
		// dependiendo el tipo de nodo, sacamos el tipo de operaciones
		List todasOperaciones = delegado
				.findAll_operacionesArbol(seguridad_User_Lineal.getTree());
		Iterator it = todasOperaciones.iterator();

		// LLENAMOS TODOS LASOPERACIONES EN BOOLEAN CON SEGURIDAD ROLE
		Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
		while (it.hasNext()) {
			Operaciones opeTodas = (Operaciones) it.next();
			delegado.llenarSeguridadLineal(opeTodas, seguridad_AuxUser_Lineal);
		}
		// truco de convertirlo PARA GRABARLO
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal(
				seguridad_AuxUser_Lineal);
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		grabarOperaciones(seguridad_User_Lineal2);

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view
		seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setToView(true);
		boolean swHayPadre = true;
		while (swHayPadre) {
			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			// datr permisologia de vista al nodo
			grabarOperaciones(seguridad_User_Lineal2);
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();
		}
		/* VAMOS A HEREDAR PERMISOS DEL PADRE */
		try {
			heredaSeguridadNodo(seguridad_User_Lineal);
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// da la seguridad completa al nuevo nodo creado y a su usuario duenio y
	// de forma descendente le da permiso de solo vista a la raiz
	public void darSeguridadNodoSoloEnCrearNoDamosPublicarDocumento(
			Seguridad_User_Lineal seguridad_User_Lineal)
			throws EcologicaExcepciones {

		// el primer nbodo . le damos toda la permisologia
		Tree nodoPermView = seguridad_User_Lineal.getTree();
		List todasOperaciones = delegado
				.findAll_operacionesArbol(seguridad_User_Lineal.getTree());
		Iterator it = todasOperaciones.iterator();

		// LLENAMOS TODOS LASOPERACIONES EN BOOLEAN CON SEGURIDAD ROLE
		Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
		while (it.hasNext()) {
			Operaciones opeTodas = (Operaciones) it.next();
			if (!Utilidades.getToDoPublico().equalsIgnoreCase(
					opeTodas.getOperacion())) {
				delegado.llenarSeguridadLineal(opeTodas,
						seguridad_AuxUser_Lineal);
			}

		}
		// truco de convertirlo PARA GRABARLO
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal(
				seguridad_AuxUser_Lineal);
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		grabarOperaciones(seguridad_User_Lineal2);

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view

		seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setToView(true);
		boolean swHayPadre = true;
		while (swHayPadre) {

			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			// datr permisologia de vista al nodo
			grabarOperaciones(seguridad_User_Lineal2);
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();
		}
		/* VAMOS A HEREDAR PERMISOS DEL PADRE */
		heredaSeguridadNodo(seguridad_User_Lineal);
	}

	public void heredaSeguridadNodo(Seguridad_User_Lineal seguridad_User_Lineal)
			throws EcologicaExcepciones {
		// ---------------------------------------------------------------------------------
		/* VAMOS A HEREDAR PERMISOS */
		if (seguridad_User_Lineal.isSwHereda()) {
			/*
			 * VAMOS A HEREDAR PERMISOS EN USUARIOS INDIVIDUALES DEL PADRE AL
			 * NUEVO NOODO 2008-06-8
			 */
			Tree padre = delegado.obtenerNodo(seguridad_User_Lineal.getTree()
					.getNodopadre());
			// nodoPermView =llegarHastadAbuelosPadres();
			if (padre != null) {
				List<Seguridad_User_Lineal> users_hereda = delegado
						.findAllSeguridad_User_Lineal(padre);
				for (Seguridad_User_Lineal user_hereda : users_hereda) {
					user_hereda.setTree(seguridad_User_Lineal.getTree());
					grabarOperaciones(user_hereda);
				}

				List<Seguridad_Role_Lineal> lista_role_tree = delegado
						.buscarTodosLosRolesTree(padre);
				for (Seguridad_Role_Lineal role_tree : lista_role_tree) {
					role_tree.setTree(seguridad_User_Lineal.getTree());
					Seguridad_Role_Lineal segur_Role_Lineal = new Seguridad_Role_Lineal();
					segur_Role_Lineal.setTree(role_tree.getTree());
					segur_Role_Lineal.setRole(role_tree.getRole());
					List<Seguridad_Role_Lineal> existe_role_tree = delegado
							.findSeguridad_Role_RoleAndTree(segur_Role_Lineal);
					if (existe_role_tree != null && existe_role_tree.isEmpty()) {
						Seguridad_Role_Lineal segur_Role_LinealGrabar = new Seguridad_Role_Lineal(
								role_tree);
						grabarRole_Tree(segur_Role_LinealGrabar);
					} else if (existe_role_tree == null) {
						Seguridad_Role_Lineal segur_Role_LinealGrabar = new Seguridad_Role_Lineal(
								role_tree);
						grabarRole_Tree(segur_Role_LinealGrabar);
					}
				}

			}

		}
	}

	public void grabarRole_Tree(Seguridad_Role_Lineal role_tree) {
		List<Seguridad_Role_Lineal> existe_role_tree = null;
		try {
			existe_role_tree = delegado
					.findSeguridad_Role_RoleAndTree(role_tree);
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (existe_role_tree != null && existe_role_tree.isEmpty()) {
			delegado.create(role_tree);
			givePermparaverToGroup(role_tree);
		} else if (existe_role_tree == null) {
			delegado.create(role_tree);
			givePermparaverToGroup(role_tree);
		}
	}

	public void givePermparaverToGroup(
			Seguridad_Role_Lineal seguridad_Role_Lineal2) {
		// verificamos que el role tenga permisos para veer
		if (seguridad_Role_Lineal2.isToView()) {
			List<Roles_Usuarios> roles_Usuarios = delegado
					.findRoles_AND_Usuario(seguridad_Role_Lineal2.getRole());
			for (Roles_Usuarios rol_usu : roles_Usuarios) {
				// damos permisos para ver hasta padres abuelos
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				// grabamos la seguridad del nodo
				seguridad_User_Lineal.setUsuario(rol_usu.getUsuario());
				seguridad_User_Lineal.setTree(seguridad_Role_Lineal2.getTree());
				seguridad_User_Lineal.setToView(true);
				grabarOperacionesInConfiguracion(seguridad_User_Lineal);
			}
		}
	}

	public void grabarOperacionesInConfiguracion(
			Seguridad_User_Lineal seguridad_User_Lineal) {

		// grabamos todos los permisos en el nodo y suvimos con solo ver
		if (seguridad_User_Lineal.isToView()) {
			darViewNodoHastaRaiz(seguridad_User_Lineal);
		} else {
			grabarOperaciones(seguridad_User_Lineal);
		}

	}

	public void darViewNodoHastaRaiz(Seguridad_User_Lineal seguridad_User_Lineal) {

		seguridad_User_Lineal.setToView(true);
		grabarOperaciones(seguridad_User_Lineal);
		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view
		boolean swHayPadre = true;
		Tree nodoPermView;
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		while (swHayPadre) {
			seguridad_User_Lineal2.setToView(true);
			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			seguridad_User_Lineal2.setToView(true);

			List<Seguridad_User_Lineal> listseg_user = delegado
					.findAllSeguridad_User_Lineal(
							seguridad_User_Lineal2.getTree(),
							seguridad_User_Lineal2.getUsuario());
			if (listseg_user == null || listseg_user.isEmpty()) {
				// datr permisologia de vista al nodo
				grabarOperaciones(seguridad_User_Lineal2);
			} else {
				Seguridad_User_Lineal seguridad_User_Linealexiste = listseg_user
						.get(0);
				seguridad_User_Linealexiste.setToView(true);
				delegado.edit(seguridad_User_Linealexiste);
			}
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();

		}

	}

	public Tree llegarHastadAbuelosPadres(Tree padre) {
		List abuelos = delegado.findAllTreePadresAbuelos(padre);
		Iterator it = abuelos.iterator();
		if (it.hasNext()) {
			Tree tree = (Tree) it.next();
			if (!tree.equals(padre)) {
				return tree;
			} else {
				// para que salga del ciclo
				padre.setTiponodo(Utilidades.getNodoRaiz());
				return padre;
			}

		} else {
			// para que salga del ciclo
			padre.setTiponodo(Utilidades.getNodoRaiz());
			return padre;
		}
	}

	public void grabarOperaciones(Seguridad_User_Lineal seguridad_User_Lineal) {
		try {
			Seguridad_User_Lineal seg_user = new Seguridad_User_Lineal(
					seguridad_User_Lineal);

			// buscamos que la seguridad ya no este grabada
			List<Seguridad_User_Lineal> existe_segUser_tree = delegado
					.findAllSeguridad_User_Lineal(
							seguridad_User_Lineal.getTree(),
							seguridad_User_Lineal.getUsuario());
			if (existe_segUser_tree != null && existe_segUser_tree.isEmpty()) {
				delegado.create(seg_user);
			} else if (existe_segUser_tree == null) {
				delegado.create(seg_user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarDiasHabiles(Tree empresa) {
		for (int i = 0; i < 7; i++) {

			DiasHabiles diasHabiles = new DiasHabiles();
			diasHabiles.setStatus(Utilidades.isActivo());
			diasHabiles.setH_InicialAM(Utilidades.getInicializarHora());
			diasHabiles.setH_FinalAM(Utilidades.getInicializarHora());
			diasHabiles.setH_InicialPM(Utilidades.getInicializarHora());
			diasHabiles.setH_FinalPM(Utilidades.getInicializarHora());
			switch (i) {
			case 0:
				diasHabiles.setNombre(Utilidades.getLunes());
				break;
			case 1:
				diasHabiles.setNombre(Utilidades.getMartes());
				break;

			case 2:
				diasHabiles.setNombre(Utilidades.getMiercoles());
				break;

			case 3:
				diasHabiles.setNombre(Utilidades.getJueves());
				break;

			case 4:
				diasHabiles.setNombre(Utilidades.getViernes());
				break;

			case 5:
				diasHabiles.setNombre(Utilidades.getSabado());
				break;

			case 6:
				diasHabiles.setNombre(Utilidades.getDomingo());
				break;

			}
			diasHabiles.setEmpresa(empresa);
			delegado.create(diasHabiles);

		}
	}

	public void llenarEstadosDelDoc(Tree empresa) {
		Usuario usuEmpresa = new Usuario();
		usuEmpresa.setEmpresa(empresa);

		Doc_tipo tipo = new Doc_tipo();
		tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());

		tipo = delegado.findDocTipo(tipo, usuEmpresa);
		if (tipo == null) {

			tipo = new Doc_tipo();
			// ESTOS NOMBRES DEBE IR EN LOS PROPERTIES
			tipo.setDescripcion("formato");
			tipo.setNombre("formato");
			tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.create(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tipo = new Doc_tipo();
			// ESTOS NOMBRES DEBE IR EN LOS PROPERTIES
			tipo.setDescripcion("procedimientos");
			tipo.setNombre("procedimientos");

			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.create(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tipo = new Doc_tipo();
			// ESTOS NOMBRES DEBE IR EN LOS PROPERTIES
			tipo.setDescripcion("normas");
			tipo.setNombre("normas");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.create(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tipo = new Doc_tipo();
			// ESTOS NOMBRES DEBE IR EN LOS PROPERTIES
			tipo.setDescripcion("politicas");
			tipo.setNombre("politicas");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.create(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else {

		}

		tipo = new Doc_tipo();
		tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
		tipo = delegado.findDocTipo(tipo, usuEmpresa);

		if (tipo == null) {
			tipo = new Doc_tipo();
			// ESTOS NOMBRES DEBE IR EN LOS PROPERTIES
			tipo.setDescripcion("registro");
			tipo.setNombre("registro");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
			try {
				tipo.setEmpresa(usuEmpresa.getEmpresa());
				delegado.create(tipo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

	}

	public void checkFlowToContinuar(Flow_Participantes flow_Participante) {
		Flow flow = null;
		try {
			Doc_detalle doc_d = null;
			boolean swCondRechAprob = false;

			if (flow_Participante != null) {
				flow = flow_Participante.getFlow();
				// si es condicional, y es un flujo de aprobacion
				// y ha sido rechazado, se cancela los demas flujos pendientes
				if (flow.isCondicional()
						&& (Long.parseLong(flow.getTipoFlujo()) == Utilidades
								.getEnAprobacion())) {
					if (flow_Participante.getFirma().getCodigo() == Utilidades
							.getRechazado()) {

						FlowParalelo flowParalelo = new FlowParalelo();
						flowParalelo = flow.getFlowParalelo();
						List<Flow> subFlujos = (List<Flow>) delegado
								.findByFlowParaleloAllFlows(flowParalelo);
						boolean swFinFlow = true;
						for (Flow subFlow : subFlujos) {
							colocarFlujoRechazado(subFlow, flow_Participante);
						}

						// si no es una solicitud de impresion, alteramos el
						// documento
						Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
						solicitudimpresion.setFlow(flow);
						if (delegado
								.findSolicitudimpresionByFlow(solicitudimpresion) == null) {
							// AQUI ALTERAMOS EL DOCUMENTO----------------
							// esti es paraalterar el documento, siempre que se
							// a de origen documento flow
							if ((flow_Participante.getFlow().getOrigen() == Utilidades
									.getOrigenDocumentoFlow())
									) {
								Doc_estado rechazado = new Doc_estado();
								rechazado.setCodigo(Utilidades.getRechazado());
								rechazado = delegado.findDocEstado(rechazado);
								doc_d = flow.getFlowParalelo().getFlow()
										.getDoc_detalle();
								doc_d = delegado.findDocDetalle(doc_d);
								doc_d.setDoc_estado(rechazado);
								delegado.edit(doc_d);
							}

						}

					}
				}
			}
			// ________________________________________________________
			// SI ES FIN DEL FLUJO
			// si no fue condicional de aprbacion
			if (!swCondRechAprob && delegado.flowIsFinFlowParticipantes(flow)) {
				// BUSCAMOS SI HA SIDO RECHAZADO
				Doc_estado estadoFinDoc = new Doc_estado();
				Doc_estado estadoFinFlow = new Doc_estado();
				boolean swRechazarAllFlowParalelos = false;
				if (delegado.flowIsFinEdoRechazadoFlowParticipantes(flow)) {
					// firmamos el flujo como rechazado
					estadoFinDoc.setCodigo(Utilidades.getRechazado());
					estadoFinDoc = delegado.findDocEstado(estadoFinDoc);
					estadoFinFlow = estadoFinDoc;
					flow.setEstado(estadoFinFlow);
					swRechazarAllFlowParalelos = true;
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

				if (swRechazarAllFlowParalelos) {
					// SI CUALQUIERA DE LOS FLUJOS FUE RECHAZADO , ENTONCES
					// TODOS LOS FLKUJOS PARALELOS SONB
					// RECHAZADOS
					FlowParalelo flowParalelo = new FlowParalelo();
					flowParalelo = flow.getFlowParalelo();
					List<Flow> subFlujos = (List<Flow>) delegado
							.findByFlowParaleloAllFlows(flowParalelo);
					boolean swFinFlow = true;
					for (Flow subFlow : subFlujos) {
						colocarFlujoRechazado(subFlow, flow_Participante);
					}
				}

				// si no es una solicitud de impresion, alteramos el documento
				Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
				solicitudimpresion.setFlow(flow);
				if (delegado.findSolicitudimpresionByFlow(solicitudimpresion) == null) {
					// AQUI ALTERAMOS EL DOCUMENTO DETALLE
					// esti es paraalterar el documento, siempre que se a de
					// origen documento flow
					if ((flow_Participante.getFlow().getOrigen() == Utilidades
							.getOrigenDocumentoFlow())
							) {
						if (delegado.finFlowParaleloForAlterarDocdetalle(flow)) {
							// REVISAMOS SUBIR VERSIONES DE UN DOCUMENTO SI EL
							// FLUJO
							// NACE
							// DE UN WORKFLOW
							if (flow.getFlow_Participantes() == null) {
								doc_d = flow.getFlowParalelo().getFlow()
										.getDoc_detalle();// flow.getDoc_detalle();
								doc_d = delegado.findDocDetalle(doc_d);
								// si es aprobado y es el ultmo flowparalelo,
								// aumentamos
								// la version y se coloca vigente
								if (flow_Participante.getFlow().getOrigen() == Utilidades
										.getOrigenDocumentoFlow()) {
									if (Utilidades.getVigente()
											- estadoFinDoc.getCodigo() == 0) {
										doc_d.setMinorVer("0");
										doc_d.setMayorVer(incInUnoString(doc_d
												.getMayorVer()));
									}
								}
								doc_d.setDoc_estado(estadoFinDoc);
								delegado.edit(doc_d);
							}
						}
					}

				}

				// ________________________________________________________

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void colocarFlujoRechazado(Flow flow,
			Flow_Participantes flow_Participante) {
		boolean swCondRechAprob = true;
		List lst = delegado.findByFlowParticipantes(flow);
		Iterator it = lst.iterator();
		int i = 0;

		while (it.hasNext()) {

			Flow_Participantes flujo_participantes = (Flow_Participantes) it
					.next();

			if (flujo_participantes.getFirma().getCodigo() == Utilidades
					.getSinFirmar()) {

				Doc_estado firma = new Doc_estado();
				firma.setCodigo(Utilidades.getRechazadoAutomatico());
				flujo_participantes.setFirma(firma);
				flujo_participantes.setComentario(flow_Participante
						.getComentario());
				flujo_participantes.setFecha_firma(flow_Participante
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
		try {
			delegado.edit(flow);
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String incInUnoString(String string) {
		// int i = Integer.valueOf(num);
		StringBuffer numNew = new StringBuffer("");
		boolean Sw = false;
		int valor = 0;
		StringBuffer retorna = new StringBuffer();
		if (string == null) {
			string = "0";
		}
		char[] arreglo = string.toCharArray();
		if (arreglo.length > 0) {
			char car = arreglo[arreglo.length - 1];
			if ((car != '9') && ((car != 'z') || (car != 'Z'))) {
				char oldcar = car;
				car = movementChar(car);
				arreglo[arreglo.length - 1] = car;
				for (int h = 0; h < arreglo.length; h++) {
					retorna.append(arreglo[h]);
				}

			}
			if (car == '9') {
				retorna.append(string).append("0");
			}

			if ((car == 'z') || (car == 'Z')) {
				if (car == 'Z') {
					retorna.append(string).append("A");
				} else {
					retorna.append(string).append("a");
				}

			}
		}

		return retorna.toString();
	}

	public char movementChar(char car) {
		int asciiValue = car;
		asciiValue++;
		return (char) asciiValue;
	}

	public void chequearForOldVersionVigente(
			Flow_Participantes flow_Participante) {

		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}
		if (swConfiguracionHayData) {
			carpeta_compartida = conf.getCarpetaCompartida().trim();
		} else {
			carpeta_compartida = confmessages.getString("carpeta_compartida");
		}

		if (flow_Participante != null) {
			Flow flow = flow_Participante.getFlow();

			// chequeamos si todos los flujos paralelos fueron firmados...
			/*
			 * FlowParalelo flowParalelo = new FlowParalelo();
			 * flowParalelo=flow.getFlowParalelo(); List<Flow> subFlujos =
			 * (List<Flow> )delegado.findByFlowParaleloAllFlows(flowParalelo);
			 * boolean swTodosParticipanteSubflowFirmaron=true; for (Flow
			 * subFlow:subFlujos){ swTodosParticipanteSubflowFirmaron=delegado.
			 * flowIsFinFlowParticipantes(subFlow); if
			 * (!swTodosParticipanteSubflowFirmaron){ break; } }
			 */
			// fin chequeamos si todos los flujos paralelos fueron firmados...
			if (delegado.finFlowParaleloForAlterarDocdetalle(flow)) {
				Doc_detalle old_version = delegado
						.chequearForOldVersionVigente(flow.getFlowParalelo()
								.getFlow().getDoc_detalle());
				if (old_version != null) {
					try {
						if ((swFueAprobado)
								&& (Long.parseLong(flow.getTipoFlujo()) == Utilidades
										.getEnAprobacion())) {
							// colocamos el estado del documento vigente
							// anterior en oboleto
							Doc_estado colocar_obsoleto = new Doc_estado();
							colocar_obsoleto.setCodigo(com.util.Utilidades
									.getObsoleto());
							colocar_obsoleto = delegado
									.findDocEstado(colocar_obsoleto);
							old_version.setDoc_estado(colocar_obsoleto);
							old_version.setDoc_checkout(false);
							delegado.edit(old_version);

							/*
							 * //si ha sido aprobado.., buscamos el repositorio
							 * svn urlsubversionUpload y //colocamos el archivo
							 * svn si es qaue lo attachment desde el flujo el
							 * usuari que inicio el flujo
							 * Flow_ParticipantesAttachment
							 * flow_ParticipantesAttachment = new
							 * Flow_ParticipantesAttachment();
							 * flow_ParticipantesAttachment
							 * .setFlowParticipantes(flow_Participante);
							 * flow_ParticipantesAttachment
							 * .setStatus(Utilidades.isActivo());
							 * flow_ParticipantesAttachment = delegado
							 * .find_Flow_ParticipantesAttachment
							 * (flow_ParticipantesAttachment);
							 * 
							 * //BUSVCAMOS SI HAY UN USUARIO ATTACHMENT UN DOC
							 * SVN System.out.println(
							 * "------------------1--------------------------------"
							 * ); Flow_ParticipantesAttachment
							 * flow_ParticipantesAttachmentsvn =
							 * delegado.find_Flow_ParticipantesAttachmentSVN
							 * (flow_ParticipantesAttachment); if
							 * (flow_ParticipantesAttachmentsvn != null) {
							 * System.out.println(
							 * "------------------2--------------------------------"
							 * ); // mostramos en el panel if
							 * (flow_ParticipantesAttachmentsvn.isSwSVN()){
							 * System.out.println(
							 * "------------------3--------------------------------"
							 * ); //SE GRABA EN DISCO File fichero = null; try {
							 * if
							 * (flow_ParticipantesAttachmentsvn.getAttachmentDoc
							 * ()==null ||
							 * flow_ParticipantesAttachmentsvn.getAttachmentDoc
							 * ().getBinaryStream()==null){ fichero=
							 * saveFileInDisk(flow_ParticipantesAttachmentsvn,
							 * null, carpeta_compartida,
							 * flow_ParticipantesAttachmentsvn.getNameFile());
							 * }else{ fichero=
							 * saveFileInDisk(flow_ParticipantesAttachmentsvn,
							 * flow_ParticipantesAttachmentsvn
							 * .getAttachmentDoc().getBinaryStream(),
							 * carpeta_compartida,
							 * flow_ParticipantesAttachmentsvn.getNameFile()); }
							 * } catch (SQLException e) { // TODO Auto-generated
							 * catch block e.printStackTrace(); }
							 * 
							 * 
							 * SubVersionUsuario subVersionUsuario= new
							 * SubVersionUsuario();
							 * subVersionUsuario.setUsuario(
							 * flow_ParticipantesAttachmentsvn
							 * .getFlowParticipantes().getParticipante());
							 * subVersionUsuario =
							 * delegado.find(subVersionUsuario);
							 * 
							 * subVersionUsuario.setFile(fichero);
							 * 
							 * //buscamos en el repositorio.... RepositorioSVN
							 * repositorioSVN = new RepositorioSVN();
							 * repositorioSVN
							 * .subirInRepositorio(subVersionUsuario);
							 * 
							 * 
							 * }
							 * 
							 * }
							 */

						}

					} catch (EcologicaExcepciones ex) {
						ex.toString();
						// Logger.getLogger("global").log(Level.SEVERE, null,
						// ex);
					}
				}

			}
		}

	}

	public File saveFileInDisk(Flow_ParticipantesAttachment doc_detalle,
			InputStream imagenBuffer, String path, String nameFile) {
		// String ruta = path + "\\" + nameFile;
		String ruta = path + "/" + nameFile;
		File fichero = new File(ruta);
		try {
			if (swConfiguracionHayData) {
				if (swPostgresVieneDeConfiguracion) {
					ByteArrayInputStream stream = new ByteArrayInputStream(
							doc_detalle.getAttachmentDocPostgres());
					imagenBuffer = (InputStream) stream;
				}
			} else if ("1".equalsIgnoreCase(confmessages
					.getString("bdpostgres"))) {
				ByteArrayInputStream stream = new ByteArrayInputStream(
						doc_detalle.getAttachmentDocPostgres());
				imagenBuffer = (InputStream) stream;
			}

			BufferedInputStream in = new BufferedInputStream(imagenBuffer);
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(fichero));
			byte[] bytes = new byte[8096];
			int len = 0;
			while ((len = in.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return fichero;
	}

	public String numSacopDo(String siglas, String num, String lengthnum) {
		// int i = Integer.valueOf(num);
		StringBuffer numNew = new StringBuffer("");
		boolean Sw = false;
		int valor = 0;
		// for (int i=0;(i<num.length() && !Sw);i++){
		for (int i = 0; (i < Integer.parseInt(lengthnum) && !Sw); i++) {
			// valor =num.length() + numNew.toString().length();
			valor = numNew.toString().length() + num.length();
			// if (valor<3){
			if (valor < Integer.parseInt(lengthnum)) {
				numNew.append("0");
			} else {
				Sw = true;
			}
		}

		// return siglas.toString()+ "-".toString() +
		// numNew.toString()+num.toString();
		return numNew.toString() + num.toString();
	}

	public String getWordKeys(String keyWord, String nomArchivo) {

		
	
		nomArchivo = sacarSlashFiltro(nomArchivo);
		
		StringTokenizer palabras = new StringTokenizer(nomArchivo, ",");
		StringBuffer cadena = new StringBuffer("");
		while (palabras.hasMoreTokens()) {
			String pal = palabras.nextToken();
			System.out.println("pal="+pal);
			cadena.append(pal);
			if (palabras.hasMoreTokens()) {
				cadena.append(",");
			}
		}
		if (!isEmptyOrNull(keyWord)) {
			cadena.append(",").append(keyWord.toString());
		}
	 		return cadena.toString();
	}

	public String sacarSlashFiltro(String nombre1) {
		StringTokenizer kk = new StringTokenizer(nombre1, "\\");
		String nombre = "";
		while (kk.hasMoreElements()) {
			nombre = (String) kk.nextToken();
		}

		return nombre;

	}

	public boolean isEmptyOrNull(String valor) {
		boolean swVacioCadena = (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
		return swVacioCadena;
	}

	public boolean buscarContextTypeTipoArchivo(Doc_detalle doc_detall,
			String nombreFile, String contextType)
			throws EcologicaExcepcionesContextType {
		boolean tipoArchValido = false;
		// _upFile.getName();
		String nomArch = nameFile(nombreFile);

		if (nomArch.lastIndexOf(".") != -1) {
			String ext = nomArch.substring(nomArch.lastIndexOf(".") + 1,
					nomArch.length());

			if (extensionAcepotadaToConverter(ext.toLowerCase(),
					queTipoContextType)) {
				// /ubicamos en la posicion 1 el tipo de contexto
				String typeContext = (String) queTipoContextType.get("1");
				if (!isEmptyOrNull(typeContext)) {
					tipoArchValido = true;
					// System.out.println("typeContext="+typeContext!=null?typeContext:"");
					// System.out.println("_upFile.getContentType()="+(_upFile!=null?_upFile.getContentType():""));
					doc_detall.setContextType(typeContext);
					doc_detall.setNameFile(nomArch);

				} else {

					throw new EcologicaExcepcionesContextType(
							messages.getString("doc_contexttype") + ":"
									+ (contextType != null ? contextType : ""));
					/*
					 * FacesContext.getCurrentInstance().addMessage( null, new
					 * FacesMessage(messages .getString("doc_contexttype") + ":"
					 * + (_upFile != null ? _upFile .getContentType() : "")));
					 */
				}
				// doc_detalle.setContextType(_upFile.getContentType());
			} else {

				throw new EcologicaExcepcionesContextType(
						messages.getString("doc_ext") + ":"
								+ (ext != null ? ext : ""));
				/*
				 * FacesContext.getCurrentInstance().addMessage( null, new
				 * FacesMessage(messages.getString("doc_ext") + ":" + (ext !=
				 * null ? ext : "")));
				 */
			}
		} else {

			throw new EcologicaExcepcionesContextType(
					(messages.getString("doc_ext") + ":" + (nomArch != null ? nomArch
							: "")));
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages.getString("doc_ext") + ":" + (nomArch !=
			 * null ? nomArch : "")));
			 */
		}
		return tipoArchValido;
	}

	public String nameFile(String f) {
		StringBuffer result = new StringBuffer();
		if (f.lastIndexOf("\\") != -1) {
			if (f.lastIndexOf(".") != -1) {
				String nombre = f.substring(f.lastIndexOf("\\") + 1,
						f.lastIndexOf("."));
				String ext = f.substring(f.lastIndexOf(".") + 1, f.length());
				StringBuffer name = new StringBuffer("");
				name.append(nombre).append(".").append(ext);
				result.append(name.toString());
				return result.toString();
			}
		}
		result.append(f);
		return result.toString();

	}

	public boolean extensionAcepotadaToConverter(String ext, HashMap contextType) {
		boolean swPerm = false;
		swPerm = tipoContextType(ext, contextType);
		/*
		 * StringTokenizer stk = new StringTokenizer(extensionesAceptadas, ",");
		 * while (stk.hasMoreTokens() && !swPerm) {
		 * 
		 * 
		 * String extPerm = (String) stk.nextToken();
		 * 
		 * tipoContextType(ext, contextType);
		 * 
		 * swPerm = extPerm.equals(ext);
		 * 
		 * }
		 */

		return swPerm;
	}

	public boolean tipoContextType(String ext, HashMap contextType) {
		boolean swPerm = false;
		// por defecto

		ExtensionFile extensionFile = delegado.tipoContextType(ext);
		if (extensionFile != null) {
			contextType.put("1", extensionFile.getMimeType());
			swPerm = true;
			return swPerm;
		} else {
			ExtensionFileHijos extensionFileHijos = new ExtensionFileHijos();
			extensionFileHijos.setExtension(ext);
			extensionFileHijos = delegado
					.find_ExtensionFileByExtension(extensionFileHijos);
			if (extensionFileHijos != null) {
				extensionFile = extensionFileHijos.getExtensionFile();
				contextType.put("1", extensionFile.getMimeType());
				swPerm = true;
				return swPerm;
			}
		}
		return swPerm;

	}
}
