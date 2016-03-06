package com.ecological.paper.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.documentos.ClientePadreDocumento;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.cliente.seguridad.ClienteSeguridadRole;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.ScannerDoc;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.ExceptionTree;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;

public class ClienteTreeMenu  extends ContextSessionRequest {

	private HtmlInputHidden varOculta;
	private static final Log log = LogFactory.getLog(ClienteTree.class);
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	/** Creates a new instance of ClienteTree */
	// private String obtenArbol;
	private String descripcionTree;
	private String obtenArbolSeguridad;
	private String maquina;
	private String nombre;
	private String descripcion;
	private String tipodenodo;
	private java.util.Date fecha_creado;
	private long idTipoNodo;
	private final int activo = 1;
	private final long noEsCrearNodo = -11;
	private Map sessionScope;
	private FacesContext facesContext;
	// private Tree nodoTree;
	private List panelNavigationItems;
	private Object mnUser_logueado = null;
	private Usuario user_logueado;
	// private boolean swSessionSta;
	private HttpSession session = super.getSession();
	private Tree treeNodoActual;
	private boolean swTreeNodoActual;
	private Tree tree;
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private String docTaskPendiente;
	private String prefijo;
	private boolean swMostrarArbol;
	// seguridad
	private Seguridad seguridadTree = new Seguridad();
	private Seguridad seguridadMenu = new Seguridad();
	private String icono;
	private boolean swSuperUsuario;
	private boolean swMoverDocumento;
	private boolean swMostrarListDocumentos;
	private List<Doc_maestro> mostrarListDocumentos;
	private List<Doc_maestro> mostrarListDocumentosAux;
	private boolean swVerArbol;
	private List<Usuario> usuariosConectados;
	private String cantidadCapturada;
	private HashMap treeHijosMap = new HashMap();
	private String creado;
	private boolean swHeredarPermisos;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private Configuracion conf = new Configuracion();
	private String cualquierComentario = "";
	private String docBuscar;
	private List flowsSinFirmarLst;
	private int flowsSinFirmar;
	private Date fecha = new Date();
	private String cadenaFecha = Utilidades.sdfShowHora.format(fecha);
	private Doc_tipo doc_tipo;
	private String strBuscar;
	private boolean swbusqueda;
	private String diasVence;
	private String text = "600px;";
	private String anchoLeftTree = "250px;";
	private String anchoCentrelTree = "270px;";

	public ClienteTreeMenu(String paraHacerDifernete) {

	}

	public ClienteTreeMenu() {
		session = super.getSession();
	
			long tiempoInicio = System.currentTimeMillis();
					session = super.getSession();

					if (session != null) {
						try {
							user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
									.getAttribute("user_logueado") : null;
							if (user_logueado != null
									&& !isEmptyOrNull(user_logueado.getAnchoLeftTree())) {
								anchoLeftTree = user_logueado.getAnchoLeftTree();
								anchoCentrelTree = anchoLeftTree.substring(0,
										anchoLeftTree.lastIndexOf("p"));
								if (isNumeric(anchoCentrelTree)) {
									int numeroCentral = Integer.parseInt(anchoCentrelTree) + 20;
									anchoCentrelTree = numeroCentral + "px";
								}

							}

							// if (user_logueado.getTreeNodoActualCache() != null) {
							// System.out
							// .println("user_logueado.getTreeNodoActualCache()="
							// + user_logueado.getTreeNodoActualCache()
							// .getNodo());
							// }

							treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
									.getAttribute("treeNodoActual") : null;

							// listar documentros
							try {
								setMostrarListDocumentosAux(getMostrarListDocumentos());
							} catch (NullPointerException e) {
								// TODO: handle exception
							}

							// fin listar documentros
							// ****************************************************************************************
							// se hace como cache del trenodoactual para evitar que pase dos
							// veces
							// ****************************************************************************************
							Tree treeCache = null;
							try {
								treeCache = user_logueado.getTreeNodoActualCache();
							} catch (Exception e) {
								treeCache = new Tree();
							}
							try {
								if (user_logueado.getTreeNodoActualCache() == null
										|| (treeNodoActual != null && (treeCache != null && treeCache
												.getNodo() - treeNodoActual.getNodo() != 0))) {

									// BUSCAMOS CUANTOS FLUJOS ESTAN SIN FIRMAR
									user_logueado.setSwSiFirmaron(false);
									flowsSinFirmarLst = delegado
											.findAllFlowParticipantesPendientes(user_logueado);
									if (flowsSinFirmarLst != null
											&& !flowsSinFirmarLst.isEmpty()) {
										flowsSinFirmar = flowsSinFirmarLst.size();
									}
									// FIN

									// _____________________________________________________________________________
									// verificamos si es super usuario
									if (super.getUser_logueado() != null
											&& super.getUser_logueado().getLogin() != null) {
										swSuperUsuario = super.getUser_logueado()
												.getLogin()
												.equalsIgnoreCase(Utilidades.getRoot());
									}

									if (tree == null) {
										tree = new Tree();
									}

									if (treeNodoActual != null) {
										// si es cvero,m es una raiz
										tree.setTiponodo(treeNodoActual.getTiponodo());
										tree.setNodopadre(treeNodoActual.getNodo());
									}

									if (treeNodoActual != null) {
										if (user_logueado != null) {
											mnUser_logueado = null;
											;
										}
									}
									if (treeNodoActual != null
											&& treeNodoActual.getTiponodo()
													- Utilidades.getNodoCarpeta() == 0) {
										swMostrarListDocumentos = true;

									}

									// CONSEGUIMOS LA SEGURIDAD TREE
									if (treeNodoActual != null) {
										seguridadTree = super
												.getSeguridadTree(treeNodoActual);
									}
									// CONSEGUIMOS LA SEGURIDAD MENU
									Tree treeMenu = new Tree();
									treeMenu.setNodo(Utilidades.getNodoRaiz());
									seguridadMenu = super.getSeguridadTree(treeMenu);

									if (treeNodoActual != null) {
										user_logueado
												.setTreeNodoActualCache(treeNodoActual);
										// System.out.println("treeNodoActual="
										// + treeNodoActual.getNodo());
									}

									// listar documentros
									// setMostrarListDocumentosAux(getMostrarListDocumentos());
									// fin listar documentros
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							// ****************************************************************************************
							// fin se hace como cache del trenodoactual para evitar que pase
							// dos veces
							// ****************************************************************************************

							// BLOQUE S0LO PARA DAR OERMISO AL UEVO REGISTRO
							swHeredarPermisos = false;
							// _____________________________________________________________________________
							// si venimos de generar un registro en el servlet
							// puentegenerarregistrocopydoc,
							// le damos permisos al nodo
							if (session.getAttribute("give_permiso_A_registro") != null) {

								tiempoInicio = System.currentTimeMillis();
								ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
								Tree registro = (Tree) session
										.getAttribute("give_permiso_A_registro");
								// delegado.asignamosPermiso(
								// registro,user_logueado,treeNodoActual);
								// __________________________________________________________________
								// damos seguridad hasta abajo del nuevo nodo que se
								// crean
								boolean swEliminar = false;
								// cargamos la operacion de la base de datos
								// Seguridad_Role_Lineal
								Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
								// EL TIPO DE NODO DE UN REGISTRO ES
								// DOCUMENTO,MOMENTANMEMANET COLOCAMOS NODO DOCUMENTO
								registro.setTiponodo(Utilidades.getNodoDocumento());
								List<Operaciones> operacionViewInBD = delegado
										.findAll_operacionesArbol(registro);
								for (Operaciones op : operacionViewInBD) {
									delegado.llenarSeguridadLineal(op,
											seguridad_AuxUser_Lineal);
								}
								// volvemos a dejar el nodo como tipo carpetra para que
								// no c
								// altere nada en el registro y logica del sistema
								registro.setTiponodo(Utilidades.getNodoCarpeta());
								// truco de convertirlo
								Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal(
										seguridad_AuxUser_Lineal);
								seguridad_User_Lineal.setUsuario(user_logueado);
								seguridad_User_Lineal.setTree(registro);
								clienteSeguridad
										.heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
												seguridad_User_Lineal, swEliminar);

								// ----------------role
								// seguridad---------------------------------------------

								ClienteSeguridadRole clienteSeguridadRole = new ClienteSeguridadRole(
										"vacio constructor");
								swEliminar = true;
								Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();

								// AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE

								List<Seguridad_Role_Lineal> lista = delegado
										.findAllSeguridad_Role_Lineal(treeNodoActual);
								for (Seguridad_Role_Lineal s_R_Lineal : lista) {
									Role role = s_R_Lineal.getRole();
									swEliminar = false;
									seguridad_Role_Lineal = new Seguridad_Role_Lineal();
									seguridad_Role_Lineal.setRole(role);
									seguridad_Role_Lineal.setTree(registro);
									// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
									// BUSCAMOS SU SEGURIDA
									List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
											.findAllSeguridad_Role_Identificador(role);
									if (seguridad_Role_LinealList != null
											&& !seguridad_Role_LinealList.isEmpty()) {
										seguridad_Role_Lineal = seguridad_Role_LinealList
												.get(0);
										seguridad_Role_Lineal.setRole(role);
										seguridad_Role_Lineal.setTree(registro);
									}
									// para que sea totalmente nuevo y no traiga el
									// primary
									// key viejo
									Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
											seguridad_Role_Lineal);
									delegado.create(seguridad_Role_Lineal2);
									try {
										delegado.heredarRolePermiso(seguridad_Role_Lineal,
												swEliminar, registro.getTiponodo());
									} catch (EcologicaExcepciones e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//givePermparaverToGroup(seguridad_Role_Lineal2);
									// FIN AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE
								}

								// -------------fin---role
								// FIN BLOQUE S0LO PARA DAR OERMISO AL UEVO REGISTRO
								// seguridad---------------------------------------------
								session.removeAttribute("give_permiso_A_registro");
								FacesContext.getCurrentInstance().getExternalContext()
										.redirect("/exitoso.jsf");

								// long totalTiempo = System.currentTimeMillis()
								// - tiempoInicio;
								// System.out
								// .println("give_permiso_A_registro El tiempo de demora es :"
								// + totalTiempo + " miliseg");

							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_userDesconectado")));
					}
					// long totalTiempo = System.currentTimeMillis() - tiempoInicio;
					// System.out.println("El tiempo tree  de demora es :" + totalTiempo
					// + " miliseg");

			System.out.println("INICIALIZANDO CONSTRUCTOR clienteTree ");
		
	
	}

	public void shouldBeConfirmed() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		user_logueado.setAnchoLeftTree(text);

		try {
			delegado.edit(user_logueado);
			anchoLeftTree = text;
			// PARA CUADRAR EL ANCHO TAMBIEN---------------
			anchoCentrelTree = anchoLeftTree.substring(0,
					anchoLeftTree.lastIndexOf("p"));
			if (isNumeric(anchoCentrelTree)) {
				int numeroCentral = Integer.parseInt(anchoCentrelTree) + 20;
				anchoCentrelTree = numeroCentral + "px";
			}

			// FIN PARA CUADRAR EL ANCHO TAMBIEN---------------
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void InicializaParaCrearNodo(String idTipoNodo) {
		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			// tipo de nodo enb caso que se valla acrear uno nuevo
			session.setAttribute("TipoNodoCrear", idTipoNodo);
			tree = new Tree();
		}
	}

	public String ingresarnodoRaiz_action() {
		String pagIr = "";

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		String TipoNodoCrear = session.getAttribute("TipoNodoCrear") != null ? (String) session
				.getAttribute("TipoNodoCrear") : String.valueOf(Utilidades
				.getNodoRaiz());
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		tree.setDescripcion(getDescripcionTree());

		if (("".equals(tree.getNombre().toString()))
		// || ("".equals(tree.getDescripcion()))
		// || ( super.isEmptyOrNull(tree.getDescripcion()) )
		) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else if (noEsCrearNodo == tree.getTiponodo()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_treeTipoNodo")));
			return "";
		}
		if (!esValidaCadena(tree.getNombre())) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
			return "failed";
		}

		// buscamos si se repite el nombre en hijos
		Tree verificaTReeExiste = validaHijosMismoNombreSinVerError(
				treeNodoActual, tree);
		if (verificaTReeExiste != null) {
			boolean nombreCorto = false;
			String prefijo = super.getPrefijo(verificaTReeExiste, "",
					nombreCorto);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("error_tree_existe")
							+ "  " + prefijo));
			return "";
		}
		/*
		 * if (validaHijosMismoNombreError(treeNodoActual, tree)) {
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage(messages.getString("error_tree_existe"))); return ""; }
		 */
		if (Long.parseLong(TipoNodoCrear) == Utilidades.getNodoRaiz()) {
			// buscamos si se repite el nombre de la raiz
			if (super.findExisteNombreRaiz(tree)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_tree_existe")));
				return "";
			}
		}

		// colocamois el prefijo automaticamente
		// if (tree.getNombre().){
		// }
		if (tree.getNombre().length() > 4) {
			tree.setPrefix(tree.getNombre().substring(0, 4));
		} else {
			tree.setPrefix(tree.getNombre());
		}

		setMaquina(getMaquinaConectada());
		Calendar date = Calendar.getInstance();
		session.removeAttribute("TipoNodoCrear");
		if (Long.parseLong(TipoNodoCrear) == Utilidades.getNodoRaiz()) {
			// el padre es el mismo
			tree.setDeBaseToUsuario(true);
			tree.setFecha_creado(date.getTime());
			tree.setTiponodo(Utilidades.getNodoRaiz());
			tree.setMaquina(maquina);
			// buscamos si se repite el nombre
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			if (user_logueado != null) {
				tree.setUsuario_creador(user_logueado);
			}

			// delegado.crearNuevoTree(tree);
			try {
				tree = delegadoEcological.crearNuevoTree(tree, user_logueado,
						swHeredarPermisos);

				// agregamod los dias y horas para configurar en la empresa
				// InicializaAplication inicializaAplication = new
				// InicializaAplication();
				// inicializaAplication.llenarDiasHabiles(tree);
				// agreagamos los tipo de doc formato y registro a la empresa
				// inicializaAplication.llenarEstadosDelDoc(tree);
				// __________________________________
				// grabamos la seguridad del nodo
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
				/*
				 * Seguridad_User_Lineal seguridad_User_Lineal = new
				 * Seguridad_User_Lineal();
				 * seguridad_User_Lineal.setUsuario(user_logueado);
				 * seguridad_User_Lineal.setTree(tree);
				 * seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
				 * 
				 * if (tree.getTiponodo() == Utilidades.getNodoCarpeta()) {
				 * clienteSeguridad
				 * .darSeguridadNodoSoloEnCrearNoDamosPublicarDocumento
				 * (seguridad_User_Lineal); } else {
				 * clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal); }
				 */

				// clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
				// colocamos loa seguridad en session
				clienteSeguridad.ponerSeguridadInSession(user_logueado);
				// HiloClienteSeguridad hiloClienteSeguridad = new
				// HiloClienteSeguridad(user_logueado);
				// hiloClienteSeguridad.start();

				// __________________________________
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());
				pagIr = Utilidades.getFinexitosorefresca();
			} catch (ExceptionTree e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_tree_existe")));
			}

		} else {
			// se agrega eñl nodo padre=treeNodoActual.getNodo(), y el tipo
			// de nodo que se escoojio
			// en el menu y esta actualizado treeNodoActual.getTiponodo()
			if (TipoNodoCrear != null && !"".equals(TipoNodoCrear)
					&& TipoNodoCrear.length() > 0) {
				if (treeNodoActual != null) {
					tree.setNodopadre(treeNodoActual.getNodo());
					tree.setTiponodo(Long.parseLong(TipoNodoCrear));
					tree.setFecha_creado(date.getTime());
					tree.setMaquina(maquina);
					tree.setDeBaseToUsuario(true);
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					if (user_logueado != null) {
						tree.setUsuario_creador(user_logueado);
					}
					delegado.crearNuevoTree(tree, user_logueado);
					ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
					Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(user_logueado);
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);

					if (tree.getTiponodo() == Utilidades.getNodoCarpeta()) {
						clienteSeguridad
								.darSeguridadNodoSoloEnCrearNoDamosPublicarDocumento(seguridad_User_Lineal);
					} else {
						clienteSeguridad
								.darSeguridadNodo(seguridad_User_Lineal);
					}

					// colocamos loa seguridad en session
					clienteSeguridad.ponerSeguridadInSession(user_logueado);
					// HiloClienteSeguridad hiloClienteSeguridad = new
					// HiloClienteSeguridad(user_logueado);
					// hiloClienteSeguridad.start();

					session.setAttribute("pagIr",
							Utilidades.getListarAplicacion());
					pagIr = Utilidades.getFinexitosorefresca();

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("error_tree")));
					return "";
				}
			}
		}
		// servicioClientes.actualizaNodoPadreNull();
		delegado.actualizaNodoPadreNull();
		// obtenArbol=delegado.construirArbol(session.getAttribute("user_logueado")!=null);

		return pagIr;
	}

	public String cancelar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteTree", "cancelar", e);
		}

		return "menu";
	}

	public List getPanelNavigationItems() {
		List menu = new ArrayList();
		try {

			// buscamos si esta moviendo el nodo
			if (session == null) {
				session = super.getSession();
			}
			boolean swMoverNodo = session.getAttribute("moverNodo") != null;
			Tree moverNodo = (Tree) session.getAttribute("moverNodo");
			// genera registro o documento mueve
			Object pegarDocumento = null;
			Object cancel_pegarDocumento = null;
			Object raiz_menu = null;
			Object configuracionSistema = null;
			// obtenemos variables del arbol, atravez del puente
			treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			// CONSEGUIMOS LA SEGURIDAD TREE
			long tiponodo = 0;
			if (treeNodoActual != null) {
				tiponodo = treeNodoActual.getTiponodo();

				if (treeNodoActual.getTiponodo() == Utilidades
						.getNodoDocumento()) {
					seguridadTree = super.getSeguridadTree(user_logueado,
							treeNodoActual);
				} else {

					seguridadTree = super.getSeguridadTree(treeNodoActual);
				}
			}

			// CONSEGUIMOS LA SEGURIDAD MENU
			Tree treeMenu = new Tree();
			treeMenu.setNodo(Utilidades.getNodoRaiz());
			seguridadMenu = super.getSeguridadTree(treeMenu);
			if (seguridadMenu == null) {
				seguridadMenu = new Seguridad();
			}
			if (super.getUser_logueado() != null
					&& super.getUser_logueado().getLogin() != null) {
				swSuperUsuario = super.getUser_logueado().getLogin()
						.equalsIgnoreCase(Utilidades.getRoot());
			}

			// TREE SEGURIDAD..........................

			boolean swMnuOrganizacion = false;
			if (treeNodoActual != null && treeNodoActual.getNombre() != null) {
				// VAMOS EN ACTION LISTENER, HAY PROCESAMOS ESTA VARIABLE
				// Principal
				String nombreEmpresa = "";
				if (session.getAttribute("nombreEmpresa") != null) {
					nombreEmpresa = (String) session
							.getAttribute("nombreEmpresa");
				}
				Object products = null;
				// getMenuNaviagtionItem(
				// messages.getString("apli_mnuOrganizacion") + " "
				// + nombreEmpresa + " ", "",
				// confmessages.getString("img_organizacion"));
				if (seguridadTree != null
						&& (seguridadTree.isToSaveDataBasic() || swSuperUsuario)) {
					// seguridadTree.isToMod();
					Object propiedades = null;
					// getMenuNaviagtionItem(
					// messages.getString("") + " "
					// + treeNodoActual.getNombre(),
					// "propiedades",
					// confmessages.getString("img_popiedades"));
					// products.add(propiedades);
					swMnuOrganizacion = true;
				}

				Object mnuconfNodo = null;

				if (seguridadTree != null && seguridadTree.isConfSeguridad()
						|| swSuperUsuario) {
					// se toco un nodo, y pocedemos ofrecer su coinfiguracion
					// mnuconfNodo = getMenuNaviagtionItem(
					// messages.getString("struct_confNodo") + " "
					// + treeNodoActual.getNombre(),
					// "agregarConfNodo",
					// confmessages.getString("img_locked"));
					// // configuracion de permisologia para el propio nodo
					// products.add(mnuconfNodo);
					// swMnuOrganizacion = true;
				}

				if (tiponodo == Utilidades.getNodoRaiz()) {
					if (seguridadTree != null
							&& (seguridadTree.isToAddPrincipal() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarPrincipal"),
						// "agregarPrincipal", getImg_principal()));
						// swMnuOrganizacion = true;
					}
				}

				if (tiponodo == Utilidades.getNodoPrincipal()) {

					// si esta emoviendo el nodo
					if (swMoverNodo
							&& (moverNodo.getTiponodo() == Utilidades
									.getNodoArea()
									|| moverNodo.getTiponodo() == Utilidades
											.getNodoProceso() || moverNodo
									.getTiponodo() == Utilidades
									.getNodoCarpeta())) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("btn_cancelar"),
						// "cancelpage",
						// confmessages.getString("img_cancelpage"));
						// if (pegarDocumento != null) {
						// menu.add(pegarDocumento);
						// menu.add(cancel_pegarDocumento);
						// }
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddArea() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarArea"),
						// "agregarArea", getImg_area()));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarProceso"),
						// "agregarProceso", getImg_proceso()));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCarpeta"),
						// "agregarCarpeta", getImg_carpeta()));
						swMnuOrganizacion = true;
					}
				}
				if (tiponodo == Utilidades.getNodoArea()) {
					// si esta emoviendo el nodo
					if (swMoverNodo
							&& (moverNodo.getTiponodo() == Utilidades
									.getNodoCargo()
									|| moverNodo.getTiponodo() == Utilidades
											.getNodoProceso() || moverNodo
									.getTiponodo() == Utilidades
									.getNodoCarpeta())) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("btn_cancelar"),
						// "cancelpage",
						// confmessages.getString("img_cancelpage"));
						if (pegarDocumento != null) {
							menu.add(pegarDocumento);
							menu.add(cancel_pegarDocumento);
						}
					}

					if (!swMoverNodo && seguridadTree != null
							&& (seguridadTree.isToMove() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_cute"), "mover",
						// confmessages.getString("img_cute")));
						swMnuOrganizacion = true;
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddCargo() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCargo"),
						// "agregarCargo", getImg_cargo()));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarProceso"),
						// "agregarProceso", getImg_proceso()));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCarpeta"),
						// "agregarCarpeta", getImg_carpeta()));
						swMnuOrganizacion = true;
					}
				}
				if (tiponodo == Utilidades.getNodoCargo()) {
					// si esta emoviendo el nodo
					if (swMoverNodo
							&& (moverNodo.getTiponodo() == Utilidades
									.getNodoProceso() || moverNodo
									.getTiponodo() == Utilidades
									.getNodoCarpeta())) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("btn_cancelar"),
						// "cancelpage",
						// confmessages.getString("img_cancelpage"));
						if (pegarDocumento != null) {
							menu.add(pegarDocumento);
							menu.add(cancel_pegarDocumento);
						}
					}
					if (!swMoverNodo
							&& (seguridadTree != null && (seguridadTree
									.isToMove() || swSuperUsuario))) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_cute"), "mover",
						// confmessages.getString("img_cute")));
						swMnuOrganizacion = true;
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarProceso"),
						// "agregarProceso", getImg_proceso()));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCarpeta"),
						// "agregarCarpeta", getImg_carpeta()));
						swMnuOrganizacion = true;
					}
				}
				if (tiponodo == Utilidades.getNodoProceso()) {
					// si esta emoviendo el nodo
					if (swMoverNodo
							&& (moverNodo.getTiponodo() == Utilidades
									.getNodoCarpeta())) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("btn_cancelar"),
						// "cancelpage",
						// confmessages.getString("img_cancelpage"));
						if (pegarDocumento != null) {
							menu.add(pegarDocumento);
							menu.add(cancel_pegarDocumento);
						}
					}

					if (!swMoverNodo
							&& (seguridadTree != null && (seguridadTree
									.isToMove() || swSuperUsuario))) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_cute"), "mover",
						// confmessages.getString("img_cute")));
						swMnuOrganizacion = true;
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCarpeta"),
						// "agregarCarpeta", getImg_carpeta()));
						swMnuOrganizacion = true;
					}
					// products.add(getMenuNaviagtionItem(messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta",getImg_carpeta()));
				}

				if (tiponodo == Utilidades.getNodoCarpeta()) {
					// AL MOVER O PEGAR OI GENERAR REGISTRO SE VA AESTE SERVLET
					// PuenteGenerarRegistroCopyDoc.jsf
					// FIN AL MOVER O PEGAR OI GENERAR REGISTRO SE VA AESTE
					// SERVLET

					// si esta emoviendo el nodo
					if ((swMoverNodo)
							&& (moverNodo.getTiponodo() == Utilidades
									.getNodoDocumento() || moverNodo
									.getTiponodo() == Utilidades
									.getNodoCarpeta())) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("cancelar"), "cancelpage",
						// confmessages.getString("img_cancelpage"));
						if (pegarDocumento != null) {
							menu.add(pegarDocumento);
							menu.add(cancel_pegarDocumento);
						}
						// SI ES PARA GENERAR REGISTROS
					} else if (session.getAttribute("swPage") != null) {
						// pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("doc_page"),
						// "generarRegistroII",
						// confmessages.getString("img_page"));
						// cancel_pegarDocumento = getMenuNaviagtionItem(
						// messages.getString("cancelar"), "cancelpage",
						// confmessages.getString("img_cancelpage"));
						// if (pegarDocumento != null) {
						// menu.add(pegarDocumento);
						// menu.add(cancel_pegarDocumento);
						// }
					}

					if (!swMoverNodo
							&& (seguridadTree != null && (seguridadTree
									.isToMove() || swSuperUsuario))) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_cute"), "mover",
						// confmessages.getString("img_cute")));
						swMnuOrganizacion = true;
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarCarpeta"),
						// "agregarCarpeta", getImg_carpeta()));
						swMnuOrganizacion = true;
					}
					// DOCUMENTOS EN ,LOTES, AGRERGAR
					// if (seguridadTree != null &&
					// (seguridadTree.isToAddLotesDeDocumentos() ||
					// swSuperUsuario))
					// {
					if (swSuperUsuario) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_loadmasivosdocumentos"),
						// "loadmasivosdocumentos",
						// confmessages.getString("img_lot_docs")));
						swMnuOrganizacion = true;
					}
					if (seguridadTree != null
							&& (seguridadTree.isToAddDocumentos() || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarDocumento"),
						// "agregarDocumento", getImg_doc()));
						swMnuOrganizacion = true;
					}

					if (seguridadTree != null
							&& (seguridadTree.isToAddDocumentoSvn() || swSuperUsuario)) {
						/* agregamos documentos a traves de subversion..., */
						// products.add(getMenuNaviagtionItem(
						// messages.getString("menu_agregarDocumentosvn"),
						// "agregarDocumentosvn",
						// confmessages.getString("img_svn")));
						swMnuOrganizacion = true;
					}

				}

				if (swMnuOrganizacion) {
					if (seguridadTree != null
							&& ((seguridadTree.isToDel()) || swSuperUsuario)) {
						// products.add(getMenuNaviagtionItem(
						// messages.getString("toDel"), "toDel",
						// confmessages.getString("img_erase")));
						swMnuOrganizacion = true;
					}

					List<com.ecological.paper.historico.Historico> listaNoVacia = delegado
							.findAllHistoricoTree(null, treeNodoActual);
					if (listaNoVacia != null && !listaNoVacia.isEmpty()) {
						if (seguridadTree != null
								&& ((seguridadTree.isToView()) || swSuperUsuario)) {
							// products.add(getMenuNaviagtionItem(
							// messages.getString("doc_hist"), "doc_hist",
							// confmessages.getString("img_historico")));
							swMnuOrganizacion = true;
						}
					}

					/* DOCUMENTO */

					if (tiponodo == Utilidades.getNodoDocumento()) {
						if (seguridadTree != null
								&& ((seguridadTree.isToView()) || swSuperUsuario)) {
							// products.add(getMenuNaviagtionItem(
							// messages.getString("doc_documento"),
							// "doc_documento",
							// confmessages.getString("img_default")));
							swMnuOrganizacion = true;

						}

					}

					menu.add(products);
				}
			}

			// SEGURIDAD MENU........
			Object administracion = null;
			// getMenuNaviagtionItem(
			// messages.getString("menu_administracion"), "",
			// getImg_logo());
			// para usuarios menus
			Object mnuUsuario = null;
			// getMenuNaviagtionItem(
			// messages.getString("menu_Usuario"), "", getImg_logo());
			boolean swUsuario = false;
			if (swSuperUsuario || seguridadMenu != null
					&& (seguridadMenu.isToListUsuarios() || swSuperUsuario)) {
				// mnuUsuario.add(getMenuNaviagtionItem(
				// messages.getString("menu_listarUsuario"),
				// "listarUsuarios",
				//
				// confmessages.getString("img_usuario")));
				swUsuario = true;
			}

			if (swSuperUsuario || seguridadMenu != null
					&& (seguridadMenu.isToListProfesiones() || swSuperUsuario)) {
				// mnuUsuario.add(getMenuNaviagtionItem(
				// messages.getString("menu_listarprofesion"),
				// "listarProfesion",
				// confmessages.getString("img_profesion")));
				swUsuario = true;
			}

			if (swSuperUsuario || seguridadMenu != null
					&& (seguridadMenu.isToListProfesiones() || swSuperUsuario)) {
				// mnuUsuario.add(getMenuNaviagtionItem(
				// messages.getString("areadocumento"), "areadocumento",
				// confmessages.getString("img_areadocumento")));
				swUsuario = true;
			}

			if (swSuperUsuario
					|| seguridadMenu != null
					&& (seguridadMenu.isToListarDiasHabiles() || swSuperUsuario)) {
				// mnuUsuario.add(getMenuNaviagtionItem(
				// messages.getString("listardiashabiles"),
				// "listardiashabiles",
				// confmessages.getString("img_diashabiles")));
				swUsuario = true;
			}

			if (swSuperUsuario
					|| seguridadMenu != null
					&& (seguridadMenu.isToListarDiasFeriados() || swSuperUsuario)) {
				// mnuUsuario.add(getMenuNaviagtionItem(
				// messages.getString("listardiasferiados"),
				// "listardiasferiados",
				// confmessages.getString("img_diasferiados")));
				swUsuario = true;
			}

			if (swUsuario) {
				// administracion.add(mnuUsuario);
			}
			// ______________________________________________________________________________________________________________________________________________
			// ______________________________________________________________________________________________________________________________________________
			// ______________________________________________________________________________________________________________________________________________
			if ("1".equalsIgnoreCase(confmessages.getString("clienteeujovans"))
					|| "1".equalsIgnoreCase(confmessages
							.getString("moduloadministrativo"))) {

				// EUJVANS---------------
				if ("1".equalsIgnoreCase(confmessages
						.getString("clienteeujovans"))) {

					// PARA LAS ZONAS-----------------------
					Object mnuZonas = null;
					// getMenuNaviagtionItem(
					// "Zonas", "", getImg_logo());
					boolean swZona = false;
					// if (swSuperUsuario || seguridadMenu != null &&
					// (seguridadMenu.isToListUsuarios() || swSuperUsuario)) {
					// mnuZonas.add(getMenuNaviagtionItem(
					// messages.getString("listarPaises"), "listarPaises",
					// confmessages.getString("img_usuario")));
					// mnuZonas.add(getMenuNaviagtionItem(
					// messages.getString("listarEstados"),
					// "listarEstados",
					// confmessages.getString("img_usuario")));
					// mnuZonas.add(getMenuNaviagtionItem(
					// messages.getString("listarCiudad"), "listarCiudad",
					// confmessages.getString("img_usuario")));

					swZona = true;
					// }
					if (swZona) {
						// administracion.add(mnuZonas);
					}
					// PARTE ADMINISTRATIVA------------------------
					// Object mnuAdministrativo = getMenuNaviagtionItem(
					// messages.getString("administrativo"), "",
					// getImg_logo());
					// mnuAdministrativo.add(getMenuNaviagtionItem(
					// messages.getString("listareujclientes"),
					// "listareujclientes",
					// confmessages.getString("img_usuario")));
					// administracion.add(mnuAdministrativo);
					//
					// // CLIENTES EUJOVANS CA----------------------
					// Object mnuEUJ = getMenuNaviagtionItem(
					// messages.getString("eujovans"), "", getImg_logo());
					// boolean swEUJ = false;
					// mnuEUJ.add(getMenuNaviagtionItem(
					// messages.getString("listareujclientes"),
					// "listareujclientes",
					// confmessages.getString("img_usuario")));
					// mnuEUJ.add(getMenuNaviagtionItem(
					// messages.getString("listareujfacturas"),
					// "listareujfletes",
					// confmessages.getString("img_usuario")));

					// swEUJ = true;
					// // }
					// if (swEUJ) {
					// administracion.add(mnuEUJ);
					// }
				}
			}
			// -------------------------------------
			// ______________________________________________________________________________________________________________________________________________
			// ______________________________________________________________________________________________________________________________________________
			// ______________________________________________________________________________________________________________________________________________
			// para roles menus
			boolean swRole = false;
			// Object role = getMenuNaviagtionItem(
			// messages.getString("menu_Role"), "", getImg_logo());

			// LISTAR GRUPOS Y LISTARGRUPOS WORKFLOW LLAMAN LAS MISMAS PAG Y
			// CLASE
			// ,SE DIFERENCIA
			// PORQUE EN EL LISTENER COLOCAMOS UNA SESSION TRUE O FALSE
			// DEPENDIENDO SEA EL CASO
			if (swSuperUsuario || seguridadMenu != null
					&& (seguridadMenu.isToListGrupos() || swSuperUsuario)) {
				// role.add(getMenuNaviagtionItem(
				// messages.getString("menu_listarRole"), "listarRole",
				// confmessages.getString("img_group")));
				swRole = true;
			}

			if (swSuperUsuario
					|| seguridadMenu != null
					&& (seguridadMenu.isToListGruposWorkflow() || swSuperUsuario)) {
				// role.add(getMenuNaviagtionItem(
				// messages.getString("menu_listarRoleWorkFlow"),
				// "listarRoleWorkFlow",
				// confmessages.getString("img_groupflow")));
				swRole = true;
			}

			if (swRole) {
				// administracion.add(role);
			}
			if (swRole || swUsuario) {
				menu.add(administracion); // img_detalle
			}

			if (swSuperUsuario
					|| seguridadMenu != null
					&& (seguridadMenu.isToListTipoDocumentos() || swSuperUsuario)) {
				// Object confDocumentos = getMenuNaviagtionItem(
				// messages.getString("menu_confDocumentos"), "",
				// getImg_logo());
				//
				// confDocumentos.add(getMenuNaviagtionItem(
				// messages.getString("menu_tipoDocumentos"),
				// "menu_tipoDocumentos",
				// confmessages.getString("img_tipodoc")));

				// CONFIGURA EXTENSIONES NUEVAS SOPORTADAS
				// Object mnUser_extensionFile = getMenuNaviagtionItem(
				// messages.getString("extensionFile"), "extensionFile",
				// confmessages.getString("img_tipodoc3"));
				//
				// confDocumentos.add(mnUser_extensionFile);
				//
				// Object mnUser_extensionFileHijos = getMenuNaviagtionItem(
				// messages.getString("extensionFileHijos"),
				// "extensionFileHijos",
				// confmessages.getString("img_tipodoc2"));
				//
				// confDocumentos.add(mnUser_extensionFileHijos);
				//
				// // CONFIGURA DOCUMENTOS SVN
				// Object svn = getMenuNaviagtionItem(
				// messages.getString("svn"), "",
				// confmessages.getString("img_svn"));
				//
				// Object svnurlbase = getMenuNaviagtionItem(
				// messages.getString("svnurlbase"), "listarSVNUrlBase",
				// confmessages.getString("img_svn"));
				//
				// svn.add(svnurlbase);
				//
				// Object svnnombreaplicacion = getMenuNaviagtionItem(
				// messages.getString("svnnombreaplicacion"),
				// "listarSVNNombreAplicacion",
				// confmessages.getString("img_svn"));
				// svn.add(svnnombreaplicacion);
				//
				// Object svntipoambiente = getMenuNaviagtionItem(
				// messages.getString("svntipoambiente"),
				// "listarSVNTipodeAmbiente",
				// confmessages.getString("img_svn"));
				// svn.add(svntipoambiente);
				//
				// Object svnmodulo = getMenuNaviagtionItem(
				// messages.getString("svnmodulo"), "listarSVNModulo",
				// confmessages.getString("img_svn"));
				//
				// svn.add(svnmodulo);
				//
				// /*
				// * Object svnextension= getMenuNaviagtionItem(
				// * messages.getString("svnextension"), "listarSvnExtension",
				// * confmessages.getString("img_svn"));
				// */
				//
				// // svn.add(svnextension);
				//
				// confDocumentos.add(svn);
				//
				// menu.add(confDocumentos);

			}

			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			if (user_logueado != null) {
				long diasVence = 0;
				if (user_logueado.getFecha_caduca() != null) {
					diasVence = numerodeDiasEnFechaCaducar(
							user_logueado.getFecha_caduca(), null);
					// mnUser_logueado = getMenuNaviagtionItem(
					// user_logueado.getNombre() + " "
					// + user_logueado.getApellido() + ", "
					// + messages.getString("expiraen") + ":"
					// + diasVence + " "
					// + messages.getString("dias"), "refrescar",
					// getImg_userLogueado());
				} else {
					// mnUser_logueado = getMenuNaviagtionItem(
					// user_logueado.getNombre() + " "
					// + user_logueado.getApellido(), "refrescar",
					// confmessages.getString("img_refresh"));
				}
				// l damos facilidad al usuario a que cambie su password
				// if (!swSuperUsuario) {
				// Object mnUser_logueadoOpcionesI = getMenuNaviagtionItem(
				// messages.getString("configuracionbasica"),
				// "configuracionbasica", getImg_logo());
				//
				// Object mnUser_logueadoOpcionesII = getMenuNaviagtionItem(
				// messages.getString("perfil"), "cambiarclave",
				// confmessages.getString("img_seguridadkey"));
				// mnUser_logueadoOpcionesI.add(mnUser_logueadoOpcionesII);
				// mnUser_logueado.add(mnUser_logueadoOpcionesI);
				// CONSEGUIMOS LA SEGURIDAD MENU
				treeMenu = new Tree();
				treeMenu.setNodo(Utilidades.getNodoRaiz());
				seguridadMenu = super.getSeguridadTree(treeMenu);

				if (seguridadMenu != null
						&& seguridadMenu.isConfSeguridadGlobal()
						|| swSuperUsuario) {
					// COLOCARLE SEGURIDAD-----------
					// configuracionSistema = getMenuNaviagtionItem(
					// messages.getString("configuracion"),
					// "configuracion",
					// confmessages.getString("administration"));
					if (configuracionSistema != null) {
						// mnUser_logueado.add(configuracionSistema);
					}
					// COLOCARLE SEGURIDAD-----------
				}

				// }
				/*


             */
				// colocamos para qu3 agregue raiz...
				// if (swSuperUsuario || seguridadTree != null &&
				// seguridadTree.isToAddRaiz() || swSuperUsuario) {
				if (swSuperUsuario) {
					// raiz_menu = getMenuNaviagtionItem(
					// messages.getString("toAddRaiz"), "agregarRaiz",
					// confmessages.getString("img_raiz"));
					// if (raiz_menu != null) {
					// mnUser_logueado.add(raiz_menu);
					// }
				}

				// SALIR
				// Object confJavaInCliente = getMenuNaviagtionItem(
				// messages.getString("ecologicalpaper"),
				// "acercaDeDocumento",
				// confmessages.getString("img_applet0"));
				// mnUser_logueado.add(confJavaInCliente);
				//
				// Object salir = getMenuNaviagtionItem(
				// messages.getString("menu_exit"), "Salir",
				// confmessages.getString("img_salir"));
				// mnUser_logueado.add(salir);

				menu.add(mnUser_logueado);
				String nombreEmpresa = "";
				if (session.getAttribute("nombreEmpresa") != null) {
					nombreEmpresa = (String) session
							.getAttribute("nombreEmpresa");
				}
				/*
				 * if (session.getAttribute("ExisteVerOrganizacion") == null) {
				 * // ORGANIZACION NavigationMenuItem organizacion =
				 * getMenuNaviagtionItem(
				 * messages.getString("apli_mnuOrganizacion") + "  " + " " +
				 * nombreEmpresa + " ", "organizacion",
				 * confmessages.getString("img_organizacion"));
				 * menu.add(organizacion); }
				 */
			}

		} catch (Exception e) {
			redirect("ClienteTree", "getPanelNavigationItems", e);
		}
		panelNavigationItems = menu;
		return panelNavigationItems;
	}

	public List getSeguridad_session() {
		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);
		if (seguridadMenu == null) {
			seguridadMenu = new Seguridad();
		}
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}

		return new ArrayList();
	}

	// private static NavigationMenuItem getMenuNaviagtionItem(String label,
	// String action) {
	// NavigationMenuItem item = new NavigationMenuItem(label, action);
	// item.setActionListener("#{tree.actionListener}");
	// item.setIcon("");
	// item.setValue(label);
	// return item;
	// }
	//
	// private static NavigationMenuItem getMenuNaviagtionItem(String label,
	// String action, String icono) {
	// NavigationMenuItem item = new NavigationMenuItem(label, action);
	// item.setActionListener("#{tree.actionListener}");
	// item.setIcon(icono.toString().trim());
	// item.setValue(label);
	// return item;
	// }

	// ____________________________________________________________________________________________________
	// ________________________VAMOS EN ACTION LISTENER, HAY PROCESAMOS ESTA
	// VARIABLE____________________________________________________________________________
	// ____________________________________________________________________________________________________
	// public String actionListener(ActionEvent event) {
	// if (event.getComponent() instanceof HtmlCommandNavigationItem) {
	// log.info("ActionListener: "
	// + ((HtmlCommandNavigationItem) event.getComponent())
	// .getValue());
	// return getAction1();
	// } else {
	//
	// HtmlCommandJSCookMenu command = (HtmlCommandJSCookMenu) event
	// .getSource();
	// MethodBinding binding = command.getAction(); // SimpleActionMethodBinding
	// String action = binding.toString();
	//
	// String outcome = (String) ((HtmlCommandJSCookMenu) event
	// .getComponent()).getValue();
	//
	// idTipoNodo = buscarTipoNodo(action.toString());
	// String vamosAcRear = action.toLowerCase();
	//
	// // si el action listener es listar roes workflows
	// if ("listarRoleWorkFlow".equalsIgnoreCase(action.toString())) {
	// session.setAttribute("usadoParaCrearFlujo", true);
	// } else {
	// session.setAttribute("usadoParaCrearFlujo", false);
	// }
	//
	// //
	// _________________________________________________________________________
	// // si es refrescar la pag cuando se ve listar documnetos
	// if ("refrescarindocumentos".equalsIgnoreCase(action.toString())) {
	// session.setAttribute("pagIr", Utilidades.getListarDocumentos());
	// }
	// //
	// _________________________________________________________________________
	//
	// if ("organizacion".equalsIgnoreCase(action.toString())) {
	// session.setAttribute("ExisteVerOrganizacion", true);
	// }
	//
	// session.setAttribute("treeNodoActual", treeNodoActual);
	// if ("doc_hist".equalsIgnoreCase(action.toLowerCase())) {
	// session.setAttribute("doc_hist", treeNodoActual);
	// }
	//
	// if ("propiedades".equalsIgnoreCase(action.toLowerCase())) {
	// if (treeNodoActual.getTiponodo() - Utilidades.getNodoRaiz() == 0) {
	// } else {
	// }
	// session.setAttribute("propiedades", treeNodoActual);
	// }
	//
	// if ("toDel".equalsIgnoreCase(action.toLowerCase())) {
	// session.setAttribute("toDelTree", treeNodoActual);
	// }
	//
	// if ("swPage".equalsIgnoreCase(action.toLowerCase())
	// || "mover".equalsIgnoreCase(action.toLowerCase())) {
	// // para una carpeta o etc menos documnetos y es para procesarlo
	// // en el puente PuenteGenerarRegistroCopyDoc, mas no para
	// // grabarlo todavia
	// session.setAttribute("moverNodo", treeNodoActual);
	// // __generarRegistroII_____
	// // si viene con swPage, agarra el treeNodoActaul para guardar el
	// // documento en nueva posicion.. el documento
	// session.setAttribute("treeNodoActual", treeNodoActual);
	// } else if ("cancelpage".equalsIgnoreCase(action.toLowerCase())) {
	//
	// session.removeAttribute("swPage");
	// session.removeAttribute("moverNodo");
	// }
	// if ((vamosAcRear.startsWith("agregar"))
	// || (vamosAcRear.startsWith("crear"))) {
	// session.setAttribute("crear", true);
	// session.setAttribute("treeNodoActual", treeNodoActual);
	// InicializaParaCrearNodo(String.valueOf(idTipoNodo));
	// } else {
	// session.removeAttribute("crear");
	// }
	// log.info("ActionListener: " + outcome);
	//
	// return action;
	// }
	// }

	public String getAction1() {
		return "go_panelnavigation_1";
	}

	public long buscarTipoNodo(String titulo) {
		// se usara en el constructor para buscar el tipo de nodo
		if ("agregarPrincipal".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoPrincipal();
			return Utilidades.getNodoPrincipal();
		}
		if ("agregarArea".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoArea();
			return Utilidades.getNodoArea();
		}
		if ("agregarCargo".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoCargo();
			return Utilidades.getNodoCargo();
		}

		if ("agregarProceso".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoProceso();
			return Utilidades.getNodoProceso();
		}
		if ("agregarCarpeta".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoCarpeta();
			return Utilidades.getNodoCarpeta();
		}
		if ("agregarDocumento".equals(titulo)) {
			return Utilidades.getNodoDocumento();
		}
		if ("agregarRaiz".equals(titulo)) {
			idTipoNodo = Utilidades.getNodoRaiz();
			return Utilidades.getNodoRaiz();
		}
		idTipoNodo = noEsCrearNodo;
		return noEsCrearNodo;
	}

	public String getAction2() {
		// se dispara action 2
		return "go_panelnavigation_2";
	}

	public String getAction3() {
		return "go_panelnavigation_3";
	}

	public String goHome() {
		return "go_home";
	}

	public boolean getDisabled() {
		return true;
	}

	public long getIdTipoNodo() {
		return idTipoNodo;
	}

	public void setIdTipoNodo(long idTipoNodo) {
		this.idTipoNodo = idTipoNodo;
	}

	public Tree obtenerNodo(long idRoot) {
		Tree tree = null;
		try {
			tree = delegado.obtenerNodo(new Long(idRoot));
			// tree =getServicioClientes().obtenerNodo(new Long(idRoot)) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getImgDelTipoNodo(Tree tree) {

		if (Utilidades.getNodoRaiz() == tree.getTiponodo()) {
			return getImg_raiz();
		}
		if (Utilidades.getNodoPrincipal() == tree.getTiponodo()) {
			return getImg_principal();
		}

		if (Utilidades.getNodoArea() == tree.getTiponodo()) {
			return getImg_area();
		}

		if (Utilidades.getNodoCargo() == tree.getTiponodo()) {
			return getImg_cargo();
		}

		if (Utilidades.getNodoProceso() == tree.getTiponodo()) {
			return getImg_proceso();
		}

		if (Utilidades.getNodoCarpeta() == tree.getTiponodo()) {
			return getImg_carpeta();
		}

		if (Utilidades.getNodoDocumento() == tree.getTiponodo()) {
			return getImg_doc();
		}

		return "";
	}

	public String getImg_principal() {
		return delegado.getImg_principal();
	}

	public String getImg_area() {
		return delegado.getImg_area();
	}

	public String getImg_cargo() {
		return delegado.getImg_cargo();
	}

	public String getImg_proceso() {
		return delegado.getImg_proceso();
	}

	public String getImg_carpeta() {
		return delegado.getImg_carpeta();
	}

	public String getImg_doc() {
		return delegado.getImg_doc();
	}

	public String getImg_raiz() {
		return delegado.getImg_raiz();
	}

	public String getImg_logo() {
		return delegado.getImg_logo();
	}

	public String getImg_userLogueado() {
		return delegado.getImg_userLogueado();
	}

	public void setPanelNavigationItems(List panelNavigationItems) {
		this.panelNavigationItems = panelNavigationItems;
	}

	/*
	 * public List<SelectItem>getPaises(){ List<Pais> paises=
	 * delegadogrf.buscarTodosLosPaises(); List<SelectItem> result = new
	 * ArrayList<SelectItem>(); SelectItem item = null; for(Pais pais:paises){
	 * item = new SelectItem(pais,pais.getNombre()); result.add(item); } return
	 * result; }
	 */
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getDocBuscar() {
		Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		docBuscar = session.getAttribute("docBuscar") != null ? (String) session
				.getAttribute("docBuscar") : null;
		boolean swTreeProcesando = session.getAttribute(Utilidades
				.getTreeprocesando()) != null;
		// SI ES TREE PROCESANDO., QUE REFRESQUE EL ARBOL
		if ((isEmptyOrNull(docBuscar)) || swTreeProcesando) {
			if (usuario != null) {
				String resp = arbolDeBuscar(usuario);
				if (!super.isEmptyOrNull(resp)) {
					docBuscar = resp;
					session.setAttribute("docBuscar", docBuscar);
				}
			}
		}

		return docBuscar;
	}

	public String getDocTaskPendiente() {
		Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		docTaskPendiente = session.getAttribute("docTaskPendiente") != null ? (String) session
				.getAttribute("docTaskPendiente") : null;
		boolean swRefrescararbolworkflows = session.getAttribute(Utilidades
				.getRefrescararbolworkflows()) != null;
		// SI ES TREE PROCESANDO., QUE REFRESQUE EL ARBOL
		if ((isEmptyOrNull(docTaskPendiente)) || swRefrescararbolworkflows) {

			if (usuario != null) {
				String resp = arbolDeTask(usuario);
				if (!super.isEmptyOrNull(resp)) {
					if (flowsSinFirmarLst != null
							&& !flowsSinFirmarLst.isEmpty()) {
						flowsSinFirmar = flowsSinFirmarLst.size();
					}
					docTaskPendiente = resp;
					session.setAttribute("docTaskPendiente", docTaskPendiente);
				}
			}

		}

		return docTaskPendiente;
	}

	public void setDocTaskPendiente(String docTaskPendiente) {
		this.docTaskPendiente = docTaskPendiente;
	}

	public String getObtenArbolSeguridad() {
		if (session.getAttribute("user_logueado") != null) {

			obtenArbolSeguridad = session.getAttribute("obtenArbolSeguridad") != null ? (String) session
					.getAttribute("obtenArbolSeguridad") : null;
			boolean swTreeProcesando = session.getAttribute(Utilidades
					.getTreeprocesando()) != null;
			// SI ES TREE PROCESANDO., QUE REFRESQUE EL ARBOL
			if (obtenArbolSeguridad == null || swTreeProcesando) {
				System.out
						.println("-------obtenArbolSeguridad---inicio-----------------------------------------------");
				Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				Seguridad seguridadTree = new Seguridad();
				long tiempoInicio = System.currentTimeMillis();
				obtenArbolSeguridad = delegado.seguridadArbol(user_logueado,
						seguridadTree, getUser_seguridad());
				session.removeAttribute("swTreeProcesando");
				// obtenArbolSeguridad= seguridadArbol();
				long totalTiempo = System.currentTimeMillis() - tiempoInicio;
				System.out
						.println(" arbol en llenarse "
								+ "El tiempo de demora es :" + totalTiempo
								+ " miliseg");

				session.setAttribute("obtenArbolSeguridad", obtenArbolSeguridad);
				System.out
						.println("-------obtenArbolSeguridad---------fin-----------------------------------------");
			}

		}
		return obtenArbolSeguridad;
	}

	public void setObtenArbolSeguridad(String obtenArbolSeguridad) {
		this.obtenArbolSeguridad = obtenArbolSeguridad;
	}

	public String arbolDeTask(Usuario usuario) {
		String nombreEmpresa = "";
		if (session.getAttribute("nombreEmpresa") != null) {
			nombreEmpresa = (String) session.getAttribute("nombreEmpresa");
		}
		StringBuffer str = new StringBuffer();
		System.out.println("");
		str.append(" d = new dTree('d'); \n\r");
		System.out.println("");
		str.append(" d.add(0,-1,'").append(" ").append("','','")
				.append(messages.getString("apli_mnuOrganizacion"))
				.append("','','").append(confmessages.getString("img_flow"))
				.append("','").append(confmessages.getString("img_flow"))
				.append("','").append(confmessages.getString("img_flow"))
				.append("'); \n\r");
		// str.append(" d.add(0,-1,''); \n\r");
		// confmessages.getString("img_raiz")
		System.out.println("");

		// str.append(" d.add(1,0,'"+messages.getString("flow_commentPrincipal")+"','aplicacion.jsf','"+messages.getString("flow_commentPrincipal")+"','','"
		// + confmessages.getString("img_menu") +
		// function(id, pid, name, url, title, target, icon, iconOpen, open) {
		// str.append(" d.add(1,0,'" + messages.getString("menu")
		// + "','./PuentedeTask.jsf?idroot=1','"
		// + messages.getString("flow_commentPrincipal") + "','','"
		// + confmessages.getString("img_menu") + "','"
		// + confmessages.getString("img_menu") + "','"
		// + confmessages.getString("img_menu") + "'); \n\r");
		// System.out.println("");
		// cerrarAplicacion.jsf
		// str.append(" d.add(2,0,'" + messages.getString("menu_exit")
		// + "','./PuentedeTask.jsf?idroot=2','"
		// + messages.getString("menu_exit") + "','','"
		// + confmessages.getString("img_salir") + "','"
		// + confmessages.getString("img_salir") + "','"
		// + confmessages.getString("img_salir") + "'); \n\r");
		// System.out.println("");

		str.append(" d.add(4,0,'")
				.append(messages.getString("tareas_pendientes"))
				.append("','','")
				.append(messages.getString("tareas_pendientes"))
				.append("','','").append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("'); \n\r");
		System.out.println("");
		str.append(" d.add(5,4,'")
				.append(messages.getString("flow_pendientes")).append("','','")
				.append(messages.getString("flow_pendientes")).append("','','")
				.append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("'); \n\r");
		// -----
		long rows = 5;
		long cols = 6;
		List cols_obj = new ArrayList();
		cols_obj.add(0, new Long(cols));
		cols_obj.add(1, new Long(rows));

		// me trae los ue no han firmado
		boolean swMostrarLosqueFirmaron = false;
		str.append(ParticipantesPendientes(cols_obj, usuario, str.toString(),
				swMostrarLosqueFirmaron));

		// me trae el ultimo nuemro de la columna
		cols = (Long) cols_obj.get(0);
		str.append(" d.add(").append(cols).append(",0,'")
				.append(messages.getString("tareas_realizadas"))
				.append("','','")
				.append(messages.getString("tareas_realizadas"))
				.append("','','").append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("'); \n\r");
		// las filas son en la actual columna quedo
		rows = cols;
		// las col es la suma de ellas + 1
		cols++;

		str.append(" d.add(").append(cols).append(",").append(rows)
				.append(",'").append(messages.getString("flow_firmados"))
				.append("','','").append(messages.getString("flow_firmados"))
				.append("','','").append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("','")
				.append(getImg_carpeta()).append("'); \n\r");
		rows = cols;
		cols++;
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		swMostrarLosqueFirmaron = true; // muestra los que ya firmaron

		str.append(ParticipantesPendientes(cols_obj, usuario, str.toString(),
				swMostrarLosqueFirmaron));

		// DOCUMENTOS BUSQUEDA
		cols = (Long) cols_obj.get(0);
		// str.append(" d.add(").append(cols).append(",0,'")
		// .append(messages.getString("doc_bucar_documentos"));
		// // listarDocumentoSearchPublicados.jsf?
		// str.append("','./PuentedeTask.jsf?idroot=3&parametro=solobuscar','")
		// .append(messages.getString("doc_bucar_documentos"))
		// .append("','','").append(confmessages.getString("img_search"))
		// .append("','").append(confmessages.getString("img_search"))
		// .append("','").append(confmessages.getString("img_search"))
		// .append("'); \n\r");
		rows = cols;
		cols++;
		// DOCUMENTOS PUBLICADOS
		// str.append(" d.add(").append(cols).append(",0,'")
		// .append(messages.getString("doc_publicados"));
		// // listarDocumentoSearchPublicados.jsf
		// str.append("','./PuentedeTask.jsf?idroot=4&parametro=publico','")
		// .append(messages.getString("doc_publicados")).append("','','")
		// .append(confmessages.getString("img_publicados")).append("','")
		// .append(confmessages.getString("img_publicados")).append("','")
		// .append(confmessages.getString("img_publicados"))
		// .append("'); \n\r");
		rows = cols;
		cols++;
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		// str.append(documentosPublicados(cols_obj,usuario,str.toString()));
		cols = (Long) cols_obj.get(0);
		rows = cols;
		cols++;
		// siquiente arbol ..
		str.append(" document.write(d)");

		return str.toString();
	}

	public String arbolDeBuscar(Usuario usuario) {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		String nombreEmpresa = "";

		if (session.getAttribute("nombreEmpresa") != null) {
			nombreEmpresa = (String) session.getAttribute("nombreEmpresa");
		}
		StringBuffer str = new StringBuffer();
		System.out.println("");
		str.append(" d = new dTree('d'); \n\r");
		System.out.println("");
		String cargo = "";
		if (usuario.getCargo() != null) {
			cargo = " [" + usuario.getCargo().getNombre() + "]";
		}
		// d.add(12,0,'Recycle Bin','example01.html','','','img/trash.gif');

		str.append(" d.add(0,-1,'")
				.append(user_logueado.getApellido() + " "
						+ user_logueado.getNombre() + " ").append("','','")
				.append(cargo).append("','")
				.append(confmessages.getString("img.cargo")).append("','")
				.append(confmessages.getString("img.cargo")).append("','")
				.append(confmessages.getString("img.cargo")).append("','")
				.append(confmessages.getString("img.cargo")).append("'); \n\r");

		// str.append(" d.add(0,-1,''); \n\r");
		// confmessages.getString("img_raiz")
		System.out.println("");

		str.append(" d.add(1,0,'" + messages.getString("paneldecontrol")
				+ "','./PuentedeTask.jsf?idroot=1','"
				+ messages.getString("flow_commentPrincipal") + "','','"
				+ confmessages.getString("img_menu") + "','"
				+ confmessages.getString("img_menu") + "','"
				+ confmessages.getString("img_menu") + "'); \n\r");
		System.out.println("");

		// str.append(" d.add(1,0,'"+messages.getString("flow_commentPrincipal")+"','aplicacion.jsf','"+messages.getString("flow_commentPrincipal")+"','','"
		// + confmessages.getString("img_menu") +
		// function(id, pid, name, url, title, target, icon, iconOpen, open) {

		// cerrarAplicacion.jsf
		str.append(" d.add(2,0,'" + messages.getString("menu_exit")
				+ "','./PuentedeTask.jsf?idroot=2','"
				+ messages.getString("menu_exit") + "','','"
				+ confmessages.getString("img_salir") + "','"
				+ confmessages.getString("img_salir") + "','"
				+ confmessages.getString("img_salir") + "'); \n\r");
		System.out.println("");

		// -----
		long rows = 5;
		long cols = 6;
		List cols_obj = new ArrayList();
		cols_obj.add(0, new Long(cols));
		cols_obj.add(1, new Long(rows));

		// me trae los ue no han firmado

		rows = cols;
		cols++;
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		// muestra los que ya firmaron

		// DOCUMENTOS BUSQUEDA
		cols = (Long) cols_obj.get(0);
		str.append(" d.add(").append(cols).append(",0,'")
				.append(messages.getString("doc_bucar_documentos"));
		// listarDocumentoSearchPublicados.jsf?
		str.append("','./PuentedeTask.jsf?idroot=3&parametro=solobuscar','")
				.append(messages.getString("doc_bucar_documentos"))
				.append("','','").append(confmessages.getString("img_search"))
				.append("','").append(confmessages.getString("img_search"))
				.append("','").append(confmessages.getString("img_search"))
				.append("'); \n\r");
		rows = cols;
		cols++;
		// DOCUMENTOS PUBLICADOS
		str.append(" d.add(").append(cols).append(",0,'")
				.append(messages.getString("doc_publicados"));
		// listarDocumentoSearchPublicados.jsf
		str.append("','./PuentedeTask.jsf?idroot=4&parametro=publico','")
				.append(messages.getString("doc_publicados")).append("','','")
				.append(confmessages.getString("img_publicados")).append("','")
				.append(confmessages.getString("img_publicados")).append("','")
				.append(confmessages.getString("img_publicados"))
				.append("'); \n\r");
		rows = cols;
		cols++;
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		// str.append(documentosPublicados(cols_obj,usuario,str.toString()));
		cols = (Long) cols_obj.get(0);
		rows = cols;
		cols++;
		// siquiente arbol ..
		str.append(" document.write(d)");

		return str.toString();
	}

	public String ParticipantesPendientes(List cols_obj, Usuario usuario,
			String strArbol, boolean swSiFirmaron) {
		// la columna la empezamos sacandola del objeto
		long cols = (Long) cols_obj.get(0);
		long rows = (Long) cols_obj.get(1);
		StringBuffer str = new StringBuffer("  ");
		str.append(strArbol.toString());
		boolean swFlowFlowImpresion = false;
		List lst = null;
		String sifirma = "";

		usuario.setSwSiFirmaron(swSiFirmaron);
		if (swSiFirmaron) {
			// lst = delegado.findAllFlowDelParticipanteFirmados(usuario);
			lst = delegado.findAllFlowParticipantesPendientes(usuario);
		} else {
			lst = delegado.findAllFlowParticipantesPendientes(usuario);
		}

		boolean swMostrarDocEnTree;
		if (!lst.isEmpty()) {
			int j = lst.size();
			Map<String, Object> unico = new HashMap<String, Object>();

			String consecutivo = "0";
			for (int i = 0; i < j; i++) {
				swMostrarDocEnTree = false;
				swFlowFlowImpresion = false;
				Flow_Participantes flow_p = (Flow_Participantes) lst.get(i);

				// SI ES UN FLOW PARA SOLICITUD DE IMPRESION
				// NO SE ALMACENA EN HISTORICOS
				if ((flow_p.getFlow().getOrigen() - Utilidades
						.getOrigenflowimpresor()) == 0) {
					swFlowFlowImpresion = true;
					if (swSiFirmaron) {
						continue;
					}

				}
				// FIN SI ES UN FLOW PARA SOLICITUD DE IMPRESION

				if (flow_p != null
						&& flow_p.getFlow() != null
						&& flow_p.getFlow().getFlowParalelo() != null
						&& flow_p.getFlow().getFlowParalelo().getFlow() != null
						&& flow_p.getFlow().getFlowParalelo().getFlow()
								.getDoc_detalle() != null
						&& flow_p.getFlow().getFlowParalelo().getFlow()
								.getDoc_detalle().getDoc_maestro() != null
						&& flow_p.getFlow().getFlowParalelo().getFlow()
								.getDoc_detalle().getDoc_maestro()
								.getConsecutivo() != null) {
					consecutivo = flow_p.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getConsecutivo();
				}

				String unicoStr = consecutivo;

				if (!swSiFirmaron) {
					if (((usuario.getId() - flow_p.getParticipante().getId() == 0))
							&& (flow_p.getFirma().getCodigo() == Utilidades
									.getSinFirmar())) {
						swMostrarDocEnTree = true;
					}
				}

				if (swSiFirmaron) {
					swMostrarDocEnTree = true;
				}

				if (swMostrarDocEnTree) {
					long codigoFlowDetalle = flow_p.getFlow().getDoc_detalle()
							.getCodigo();
					// SOLO UN REPRESENTRANTE DE FLUJO PARALELOS
					StringBuffer str1 = new StringBuffer("");
					if (flow_p == null || flow_p.getFlow() == null
							|| flow_p.getFlow().getFlowParalelo() == null) {
						continue;
					}

					// ENCONTRAMOS EL AREA DEL DOCUMENTO
					Tree area = null;
					Doc_maestro d_m = flow_p.getFlow().getFlowParalelo()
							.getFlow().getDoc_detalle().getDoc_maestro();
					area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(flow_p
							.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getTree(),
							Utilidades.getNodoArea());
					if (area != null) {
						String areaStr = flow_p.getFlow().getFlowParalelo()
								.getFlow().getDoc_detalle().getAreaDocumentos() != null ? flow_p
								.getFlow().getFlowParalelo().getFlow()
								.getDoc_detalle().getAreaDocumentos()
								.getNombre()
								: "";
						String tipoFormato = d_m.getDoc_tipo().getAbreviatura() != null ? d_m
								.getDoc_tipo().getAbreviatura() : d_m
								.getDoc_tipo().getNombre();
						// COLOCAMOS EL AREA DEL DOCUMENTO + EL CONSECUTIVO
						consecutivo = areaStr + "-" + tipoFormato + "-"
								+ consecutivo + " ";
					}

					// e unico es consecutrivo y el nombre
					/*
					 * String
					 * unicoStr=flow_p.getFlow().getFlowParalelo().getFlow()
					 * .getDoc_detalle().getDoc_maestro()
					 * .getConsecutivo()+flow_p.getFlow().getFlowParalelo()
					 * .getFlow().getDoc_detalle()
					 * .getDoc_maestro().getNombre();
					 */
					if ((flow_p.getFlow().getOrigen() - Utilidades
							.getOrigenflowimpresor()) == 0) {
						str1.append(
								flow_p.getFlow().getFlowParalelo().getNombre())
								.append("->");
					}

					str1.append(flow_p.getFlow().getFlowParalelo().getFlow()
							.getDoc_detalle().getDoc_maestro().getNombre());
					str1.append("")
							.append("(")
							.append(flow_p.getFlow().getFlowParalelo()
									.getFlow().getDoc_detalle().getMayorVer())
							.append(".")
							.append(flow_p.getFlow().getFlowParalelo()
									.getFlow().getDoc_detalle().getMinorVer())
							.append(")");

					String stado = "";
					try {
						stado = messages.getString(flow_p.getFlow().getEstado()
								.getNombre());
					} catch (Exception e) {
						stado = flow_p.getFlow().getEstado().getNombre();
					}

					str1.append("[").append(stado).append("]");
					str1.append(" ").append(consecutivo).append("'");
					// si es FLUJO DE IMPRESION PASA.., NOI IMPORTA SI SE REPITE
					// EL CODIGO
					boolean swPasa = swFlowFlowImpresion;
					if (!swPasa) {
						// CHEWQUEAMOS QUE NO SE REPITA EL CODIGO
						swPasa = !unico.containsKey(unicoStr.trim());
					}

					// POR AHORA TODO EL MUJDO PASA
					// swPasa=true;
					// FIN PORE AHORA TODO EL MUNDO PASA

					if (swPasa) {
						unico.put(unicoStr.trim(), unicoStr.trim());

						// colocamos el nombre del maestro
						str.append(" d.add(").append(cols).append(",")
								.append(rows).append(",'");
						str.append(str1);

						if (swSiFirmaron) {
							str.append(
									",'./PuenteFlowResponse?codigoFlowDetalle=")
									.append(codigoFlowDetalle
											+ "&flowPartSifirmo="
											+ flow_p.getCodigo()
											+ "&flowpuente="
											+ flow_p.getFlow().getCodigo() + "")
									.append("',");
						} else {
							str.append(
									",'./PuenteFlowResponse?codigoFlowDetalle=")
									.append(codigoFlowDetalle + "&flowpuente="
											+ flow_p.getFlow().getCodigo() + "")
									.append("',");
						}

						// colocamos el nombre del estado
						str.append("'").append(stado).append("','',");
						str.append("'").append(getImg_doc()).append("','")
								.append(getImg_doc()).append("','")
								.append(getImg_doc()).append("'); \n\r");

						cols += 1;
					}
				}
			}
		}
		// lça colocamos nuevamen te en elñ objeto como referencia
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		return str.toString();
	}

	public String documentosPublicados(List cols_obj, Usuario usuario,
			String strArbol) {
		// la columna la empezamos sacandola del objeto
		long cols = (Long) cols_obj.get(0);
		long rows = (Long) cols_obj.get(1);
		StringBuffer str = new StringBuffer("  ");
		str.append(strArbol.toString());
		List lst = null;
		String sifirma = "";
		List<Doc_maestro> doc_maestros;

		// esta session es para que la utilze la pag de buscar tambien
		session.setAttribute("query", delegado.queryDoc_maestrosPublicos());

		doc_maestros = delegado.queryExecute(delegado
				.queryDoc_maestrosPublicos());

		int size = doc_maestros.size();
		for (int i = 0; i < size; i++) {

			Doc_maestro doc = (Doc_maestro) doc_maestros.get(i);

			List<Doc_detalle> doc_detalles = delegado.queryExecute(delegado
					.queryDetallesVigentePublico(doc));

			if (doc_detalles != null && !doc_detalles.isEmpty()) {
				Doc_detalle doc_detalle = doc_detalles.get(0);
				// colocamos el nombre del maestro
				str.append(" d.add(").append(cols).append(",").append(rows)
						.append(",'").append(doc.getNombre());
				str.append("(").append(doc_detalle.getMayorVer()).append(".")
						.append(doc_detalle.getMinorVer()).append(")");
				str.append("");
				str.append("[").append(doc_detalle.getDoc_estado().getNombre())
						.append("]'");
				str.append(",'./puente.jsf?idroot=").append(
						doc.getTree().getNodo() + "&publicados=1");
				str.append("&idDetalle=").append(doc_detalle.getCodigo())
						.append("").append("',");
				// colocamos el nombre del estado
				str.append("'").append(doc_detalle.getDoc_estado().getNombre())
						.append("','',");
				str.append("'").append(getImg_doc()).append("','")
						.append(getImg_doc()).append("','")
						.append(getImg_doc()).append("'); \n\r");
				System.out.println("");
				cols += 1;
			}
		}
		// lça colocamos nuevamen te en elñ objeto como referencia
		cols_obj.set(0, new Long(cols));
		cols_obj.set(1, new Long(rows));
		return str.toString();
	}

	private String seguridadArbol() {

		if (user_logueado == null) {
			user_logueado = (Usuario) session.getAttribute("user_logueado");
		}

		StringBuffer cad = new StringBuffer();
		try {

			// listamos todas las raices padres
			List arbolPadresLst = null;

			arbolPadresLst = delegado.findAllTreePadres();

			Iterator arbolPadresIt = arbolPadresLst.iterator();
			char objArbol = 'e';
			while (arbolPadresIt.hasNext()) {

				Tree treePadre = (Tree) arbolPadresIt.next();

				seguridadTree = super.getSeguridadTree(treePadre);
				if (seguridadTree == null) {
					seguridadTree = new Seguridad();
				}

				if (user_logueado != null
						&& user_logueado.getLogin().equalsIgnoreCase(
								Utilidades.getRoot())
						|| seguridadTree.isToView()) {
					cad.append(objArbol).append("= new dTree('")
							.append(objArbol).append("');");
					cad.append("\n\r");
					cad.append("\n\r");
					// dTree.prototype.add = function(id, pid, name, url, title,
					// target, icon, iconOpen, open) {
					cad.append(objArbol)
							.append(".add(")
							.append(treePadre.getNodopadre())
							.append(",-1,'")
							.append(treePadre.getNombre() != null ? treePadre
									.getNombre().trim() : "Vacio").append("'");
					cad.append(",'./puente.jsf?");
					cad.append("idroot=").append(treePadre.getNodopadre());
					cad.append("'");

					cad.append(",'','','")
							.append(confmessages.getString("img_raiz"))
							.append("');   ");
					// String nodos =
					// delegado.getNodosHijos(treePadre.getNodopadre(),
					// objArbol);
					String nodos = seguridadHijos(treePadre.getNodopadre(),
							objArbol);
					if ((nodos != null) && (nodos.length() > 0)) {
						cad.append(nodos);
					}
					cad.append("\n\r");
					cad.append(" document.write(").append(objArbol)
							.append(");");
					cad.append("\n\r");
					++objArbol;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cad.toString();
	}

	private String seguridadHijos(long nodoPadre, char objArbol) {
		StringBuffer cad2 = new StringBuffer("");

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List nodosHijosLst = null;
		Tree estaInSeguridadTree = delegado.obtenerNodo(nodoPadre);
		seguridadTree = super.getSeguridadTree(estaInSeguridadTree);
		// chequeamos la seguridad, si es root o tiene permiso de ver, se pinta
		// el arbol
		if (user_logueado != null
				&& user_logueado.getLogin().equalsIgnoreCase(
						Utilidades.getRoot())
				|| (seguridadTree != null && seguridadTree.isToView())) {

			nodosHijosLst = delegado.findAllTreeHijos(nodoPadre);

			if (nodosHijosLst != null) {
				Iterator nodosHijosIt = nodosHijosLst.iterator();
				Usuario user_cargo;
				while (nodosHijosIt.hasNext()) {
					Tree treeHijo = (Tree) nodosHijosIt.next();
					seguridadTree = super.getSeguridadTree(treeHijo);

					user_cargo = null;
					String nombre = null;
					String apellido = null;

					// chequeamos la seguridad, si es root o tiene permiso de
					// ver, se pinta el arbol
					if (user_logueado != null
							&& user_logueado.getLogin().equalsIgnoreCase(
									Utilidades.getRoot())
							|| (seguridadTree != null && seguridadTree
									.isToView())) {

						/*********************** Buscando Cargo *******************************************************************/
						if (treeHijo.getTiponodo() == Utilidades.getNodoCargo()) {
							user_cargo = new Usuario();
							user_cargo.setCargo(treeHijo);
							List staCargoUsu = delegado
									.findExisteCargoInUsuario(user_cargo);
							Iterator it = staCargoUsu.iterator();
							if (it.hasNext()) {

								user_cargo = (Usuario) it.next();
								nombre = Utilidades.getRemplazoCaracter(
										user_cargo.getNombre(),
										Utilidades.getCommilla(), '_');
								apellido = Utilidades.getRemplazoCaracter(
										user_cargo.getApellido(),
										Utilidades.getCommilla(), '_');
							}
						}
						/******************** Fin***Buscando Cargo *******************************************************************/
						cad2.append("\n\r");
						cad2.append(objArbol).append(".add(")
								.append(treeHijo.getNodo()).append(",")
								.append(treeHijo.getNodopadre()).append(",'");
						if (user_cargo != null && nombre != null) {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("[")
									.append(nombre != null ? nombre.trim() : "")
									.append(" ")
									.append(apellido != null ? apellido.trim()
											: "Vacio").append("]").append("'");
						} else {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("'");
						}

						// ___________________________________________________________________________________________
						// primera pag con parametros
						cad2.append(",'./puente.jsf?");
						cad2.append("idroot=").append(treeHijo.getNodo());
						cad2.append("'");

						// ___________________________________________________________________________________________
						if (treeHijo.getTiponodo() == Utilidades
								.getNodoPrincipal()) {
							cad2.append(",'Titulo','','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoArea()) {
							cad2.append(",'Titulo','','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCargo()) {
							if (user_cargo != null) {
								cad2.append(",'")
										.append(messages
												.getString("usuario_mail"))
										.append(":")
										.append(user_cargo.getMail_principal() != null ? user_cargo
												.getMail_principal().trim()
												: "");
								cad2.append("  ")
										.append(messages
												.getString("usuario_fechac"))
										.append(":")
										.append(user_cargo.getFecha_creado());
								cad2.append("','','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("');");
							} else {

								cad2.append(",'Titulo','','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("');");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoProceso()) {

							cad2.append(",'Titulo','','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCarpeta()) {
							// buscamos si tiene documentos el nodo
							List<Doc_maestro> lista = delegado
									.findAllDoc_maestrosCarpetas(treeHijo);
							if (lista != null && !lista.isEmpty()) {

								cad2.append(",'','','")
										.append(confmessages
												.getString("img.carpetallena"))
										.append("');   ");
							} else {
								cad2.append(",'','','")
										.append(getImg_carpeta())
										.append("');   ");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoDocumento()) {

							// icono= obtenIconoDoc(doc_detalle.getNameFile());
							//
							cad2.append(",'Titulo','','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("');");
						} else {
							cad2.append(",'Versiones ...','','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("');");
						}
						cad2.append("\n\r");
						String nodos = null;
						if (treeHijo != null) {
							nodos = seguridadHijos(treeHijo.getNodo(), objArbol);
						}

						if ((nodos != null) && (nodos.length() > 0)) {
							cad2.append(nodos);
						}
					}
				}
			}
		}
		return cad2.toString();
	}

	public String getPrefijo() {
		boolean nomCorto = false;
		prefijo = "";

		prefijo = getPrefijo(treeNodoActual, prefijo, nomCorto);

		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public boolean isSwMostrarArbol() {
		if (session.getAttribute("swMostrarArbol") == null) {
			session.setAttribute("swMostrarArbol", true);
		}
		swMostrarArbol = (Boolean) session.getAttribute("swMostrarArbol");
		session.setAttribute("swMostrarArbol", !swMostrarArbol);
		return swMostrarArbol;
	}

	public void setSwMostrarArbol(boolean swMostrarArbol) {
		this.swMostrarArbol = swMostrarArbol;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public boolean isSwMostrarListDocumentos() {
		return swMostrarListDocumentos;
	}

	public void setSwMostrarListDocumentos(boolean swMostrarListDocumentos) {
		this.swMostrarListDocumentos = swMostrarListDocumentos;
	}

	public void nodoDocumento(ActionEvent ev) {
	}

	public List<Doc_maestro> getMostrarListDocumentos() {
		try {
			long tiempoInicio = System.currentTimeMillis();
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			// isSwbusqueda() esta en session, si algien quiere filtrar , este
			// se
			// colocaen truew
			if (((user_logueado != null && user_logueado
					.getTreeNodoActualCache() == null) || (treeNodoActual != null && user_logueado
					.getTreeNodoActualCache().getNodo()
					- treeNodoActual.getNodo() != 0))
					|| isSwbusqueda()) {
				// para que no busque añlmemnios que se le pregunte en un nodo o
				// carpeta

				setSwbusqueda(false);
				// fin para que no busque añlmemnios que se le pregunte en un
				// nodo
				// o
				// carpeta

				List<Doc_maestro> mostrarList = null;
				// SOLO EN CARPETA ES QUE SE LISTA DOCUMENTOS Y ENCONTRAMOS EL
				// AREA
				// DEL
				// DOCUMENTO
				Tree area = null;
				if (treeNodoActual == null) {
					return new ArrayList<Doc_maestro>();
				}
				if (treeNodoActual.getTiponodo() - Utilidades.getNodoCarpeta() == 0) {
					treeNodoActual.setDoc_tipo(getDoc_tipo());
					treeNodoActual.setStrBuscar(getStrBuscar());
					mostrarList = delegado
							.findAllDoc_maestrosCarpetas(treeNodoActual);
					area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(
							treeNodoActual, Utilidades.getNodoArea());
				}
				// verificamos si es super usuario
				if (super.getUser_logueado() != null
						&& super.getUser_logueado().getLogin() != null) {
					swSuperUsuario = super.getUser_logueado().getLogin()
							.equalsIgnoreCase(Utilidades.getRoot());
				}

				if (mostrarList != null) {

					HashMap unico = new HashMap();
					List<Doc_maestro> lista = new ArrayList<Doc_maestro>();
					for (Doc_maestro doc : mostrarList) {

						// se le agrega plantilla al nombre del tipo de
						// documento si
						// es
						// plantilla
						StringBuffer plantilla = new StringBuffer("");
						if (doc.getDoc_tipo() != null
								&& doc.getDoc_tipo().getFormatoTipoDoc() != null) {
							if (Utilidades.getFormatoTipoDoc()
									- doc.getDoc_tipo().getFormatoTipoDoc() == 0) {
								Doc_tipo t = new Doc_tipo();
								t.setCodigo(doc.getDoc_tipo().getCodigo());
								t = delegado.findDocTipo(t);
								plantilla.append("[")
										.append(messages.getString("formato"))
										.append("]");
								String nom = t.getNombre()
										+ plantilla.toString();

								doc.getDoc_tipo().setNombre(nom);
								plantilla = new StringBuffer("");
							}
						}

						if (!lista.contains(doc)) {

							// BUSCAMOS LA SEGURIDAD DEL DOCUMENTO
							seguridadTree = super.getSeguridadTree(
									user_logueado, doc.getTree());

							if (seguridadTree.isToView() || swSuperUsuario) {
								if (doc != null
										&& doc.getFecha_creado() != null) {
									doc.setFecha_mostrar(Utilidades.sdfShow
											.format(doc.getFecha_creado()));
								}
								List<Doc_detalle> doc_detalles = delegado
										.encontrarDetalles(doc.getCodigo(),
												"nulo.. no me interesa parametro");
								Doc_detalle d = null;
								Doc_detalle da = null, db = null;
								int i = 0;
								for (Doc_detalle d1 : doc_detalles) {
									if (i == 0) {
										da = d1;
									} else {
										db = d1;
										break;
									}
									i++;

								}

								if ((db != null)
										&& ((db.getDoc_estado().getCodigo() == (Utilidades
												.getVigente())) || (db
												.getDoc_estado().getCodigo() == (Utilidades
												.getAprobado())))) {
									d = db;
								} else {
									d = da;
								}

								icono = super.obtenIconoDoc(d.getNameFile());
								d.setIcono(icono);

								d.setSize_doc(converBytesMbytes(Math.ceil(d
										.getSize_doc())));
								doc.setDoc_detalle(d);

								// INGRESAMOS UN AREA AL DOCUMENTO SI NO LA
								// TIENE
								if (doc.getDoc_detalle().getAreaDocumentos() == null) {
									List<AreaDocumentos> areas = delegado
											.findAllAreaDocumentos(
													user_logueado, null);
									if (areas != null && !areas.isEmpty()) {
										Doc_detalle doc_det = new Doc_detalle();
										doc_det.setCodigo(doc.getDoc_detalle()
												.getCodigo());
										doc_det = delegado
												.findDocDetalle(doc_det);
										doc_det.setAreaDocumentos(areas.get(0));
										try {
											delegado.editDetalle(doc_det);
											doc.getDoc_detalle()
													.setAreaDocumentos(
															doc_det.getAreaDocumentos());
										} catch (EcologicaExcepciones e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								}
								// FIN INGRESAMOS UN AREA AL DOCUMENTO SI NO LA
								// TIENE

								if (area != null) {
									String areaStr = doc.getDoc_detalle()
											.getAreaDocumentos() != null ? doc
											.getDoc_detalle()
											.getAreaDocumentos().getNombre()
											: "";
									doc.setArea(areaStr);
									String tipoFormato = doc.getDoc_tipo()
											.getAbreviatura() != null ? doc
											.getDoc_tipo().getAbreviatura()
											: doc.getDoc_tipo().getNombre();
									doc.setTipoDocumento(tipoFormato);
									doc.setConsecutivo(doc.getConsecutivo());
								} else {
									doc.setConsecutivo(doc.getConsecutivo());
								}

								lista.add(doc);
							}
						}
					}
					mostrarListDocumentos = lista; // mostrarList;
				} else {
					mostrarListDocumentos = new ArrayList<Doc_maestro>();
				}
				// if (treeNodoActual != null) {
				// System.out.println("treeNodoActual.getNodo()="
				// + treeNodoActual.getNodo());
				// }
				long totalTiempo = System.currentTimeMillis() - tiempoInicio;
				// System.out.println("listardocumentos El tiempo de demora es :"
				// + totalTiempo + " miliseg");
			}
		} catch (Exception e) {
			mostrarListDocumentos = new ArrayList();
		}

		return mostrarListDocumentos;
	}

	public List<Doc_maestro> getMostrarListDocumentosOptimizado() {

		long tiempoInicio = System.currentTimeMillis();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// isSwbusqueda() esta en session, si algien quiere filtrar , este se
		// colocaen truew
		if (((user_logueado != null && user_logueado.getTreeNodoActualCache() == null) || (treeNodoActual != null && user_logueado
				.getTreeNodoActualCache().getNodo() - treeNodoActual.getNodo() != 0))
				|| isSwbusqueda()) {
			// para que no busque añlmemnios que se le pregunte en un nodo o
			// carpeta

			setSwbusqueda(false);
			// fin para que no busque añlmemnios que se le pregunte en un nodo
			// o
			// carpeta

			List<Doc_maestro> mostrarList = null;
			// SOLO EN CARPETA ES QUE SE LISTA DOCUMENTOS Y ENCONTRAMOS EL AREA
			// DEL
			// DOCUMENTO
			Tree area = null;
			if (treeNodoActual == null) {
				return new ArrayList<Doc_maestro>();
			}
			if (treeNodoActual.getTiponodo() - Utilidades.getNodoCarpeta() == 0) {
				treeNodoActual.setDoc_tipo(getDoc_tipo());
				treeNodoActual.setStrBuscar(getStrBuscar());
				mostrarList = delegado
						.findAllDoc_maestrosCarpetas(treeNodoActual);
				area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(
						treeNodoActual, Utilidades.getNodoArea());
			}
			// verificamos si es super usuario
			if (super.getUser_logueado() != null
					&& super.getUser_logueado().getLogin() != null) {
				swSuperUsuario = super.getUser_logueado().getLogin()
						.equalsIgnoreCase(Utilidades.getRoot());
			}

			if (mostrarList != null) {

				HashMap unico = new HashMap();
				List<Doc_maestro> lista = new ArrayList<Doc_maestro>();
				for (Doc_maestro doc : mostrarList) {

					// se le agrega plantilla al nombre del tipo de documento si
					// es
					// plantilla
					StringBuffer plantilla = new StringBuffer("");
					if (doc.getDoc_tipo() != null
							&& doc.getDoc_tipo().getFormatoTipoDoc() != null) {
						if (Utilidades.getFormatoTipoDoc()
								- doc.getDoc_tipo().getFormatoTipoDoc() == 0) {
							Doc_tipo t = new Doc_tipo();
							t.setCodigo(doc.getDoc_tipo().getCodigo());
							t = delegado.findDocTipo(t);
							plantilla.append("[")
									.append(messages.getString("formato"))
									.append("]");
							String nom = t.getNombre() + plantilla.toString();

							doc.getDoc_tipo().setNombre(nom);
							plantilla = new StringBuffer("");
						}
					}

					if (!lista.contains(doc)) {

						// BUSCAMOS LA SEGURIDAD DEL DOCUMENTO
						seguridadTree = super.getSeguridadTree(user_logueado,
								doc.getTree());

						if (seguridadTree.isToView() || swSuperUsuario) {
							if (doc != null && doc.getFecha_creado() != null) {
								doc.setFecha_mostrar(Utilidades.sdfShow
										.format(doc.getFecha_creado()));
							}
							List<Doc_detalle> doc_detalles = delegado
									.encontrarDetalles(doc.getCodigo(),
											"nulo.. no me interesa parametro");
							Doc_detalle d = null;
							Doc_detalle da = null, db = null;
							int i = 0;
							for (Doc_detalle d1 : doc_detalles) {
								if (i == 0) {
									da = d1;
								} else {
									db = d1;
									break;
								}
								i++;

							}

							if ((db != null)
									&& ((db.getDoc_estado().getCodigo() == (Utilidades
											.getVigente())) || (db
											.getDoc_estado().getCodigo() == (Utilidades
											.getAprobado())))) {
								d = db;
							} else {
								d = da;
							}

							icono = super.obtenIconoDoc(d.getNameFile());
							d.setIcono(icono);

							d.setSize_doc(converBytesMbytes(Math.ceil(d
									.getSize_doc())));
							doc.setDoc_detalle(d);

							// INGRESAMOS UN AREA AL DOCUMENTO SI NO LA TIENE
							if (doc.getDoc_detalle().getAreaDocumentos() == null) {
								List<AreaDocumentos> areas = delegado
										.findAllAreaDocumentos(user_logueado,
												null);
								if (areas != null && !areas.isEmpty()) {
									Doc_detalle doc_det = new Doc_detalle();
									doc_det.setCodigo(doc.getDoc_detalle()
											.getCodigo());
									doc_det = delegado.findDocDetalle(doc_det);
									doc_det.setAreaDocumentos(areas.get(0));
									try {
										delegado.editDetalle(doc_det);
										doc.getDoc_detalle().setAreaDocumentos(
												doc_det.getAreaDocumentos());
									} catch (EcologicaExcepciones e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}
							// FIN INGRESAMOS UN AREA AL DOCUMENTO SI NO LA
							// TIENE

							if (area != null) {
								String areaStr = doc.getDoc_detalle()
										.getAreaDocumentos() != null ? doc
										.getDoc_detalle().getAreaDocumentos()
										.getNombre() : "";
								doc.setArea(areaStr);
								String tipoFormato = doc.getDoc_tipo()
										.getAbreviatura() != null ? doc
										.getDoc_tipo().getAbreviatura() : doc
										.getDoc_tipo().getNombre();
								doc.setTipoDocumento(tipoFormato);
								doc.setConsecutivo(doc.getConsecutivo());
							} else {
								doc.setConsecutivo(doc.getConsecutivo());
							}

							lista.add(doc);
						}
					}
				}
				mostrarListDocumentos = lista; // mostrarList;
			} else {
				mostrarListDocumentos = new ArrayList<Doc_maestro>();
			}
			// if (treeNodoActual != null) {
			// System.out.println("treeNodoActual.getNodo()="
			// + treeNodoActual.getNodo());
			// }
			long totalTiempo = System.currentTimeMillis() - tiempoInicio;
			// System.out.println("listardocumentos El tiempo de demora es :"
			// + totalTiempo + " miliseg");
		}

		return mostrarListDocumentos;
	}

	public void setMostrarListDocumentos(List<Doc_maestro> mostrarListDocumentos) {
		this.mostrarListDocumentos = mostrarListDocumentos;
	}

	public String actionMoverDocumento() {
		String pagIr = "mover";

		session.setAttribute("moverNodo", treeNodoActual);
		return pagIr;
	}

	public String actionCancelarMoverDocumento() {
		String pagIr = "";
		try {
			session.removeAttribute("swPage");
			session.removeAttribute("moverNodo");

		} catch (Exception e) {
			redirect("ClienteTree", "actionCancelarMoverDocumento", e);
		}
		return pagIr;
	}

	public String desdeWorkFlowDocumento() {
		String pagIr = "";

		Doc_detalle detalle = (Doc_detalle) session.getAttribute("objeto");
		// System.out.println("detalle="+detalle.);
		setIcono(super.obtenIconoDoc(detalle.getNameFile()));

		// ____________________________________
		// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// obtenemos el nodo padre del docyumento

		treeNodoActual = delegado.obtenerNodo(detalle.getDoc_maestro()
				.getTree().getNodo());

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {

			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// CONSEGUIMOS LA SEGURIDAD TREE
		if (treeNodoActual != null) {

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {
				seguridadTree = super.getSeguridadTree(user_logueado,
						treeNodoActual);
			}
		}
		if (seguridadTree.isToView() || swSuperUsuario) {
			session.setAttribute("treeNodoActual", treeNodoActual);
			/*
			 * boolean verDetalles = true;
			 * super.guardamosHistoricoActivoDelDocumento(user_logueado,
			 * detalle.getDoc_maestro(), false, false, verDetalles, false,
			 * false, false, false, false, "");
			 */
			session.setAttribute(Utilidades.getFlows(), Utilidades.getFlows());
			pagIr = "listarDocumentos";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("Operaciones_noSelec")
							+ "(" + messages.getString("toView") + ") "
							+ messages.getString("doc_dueniotab") + ":"
							+ detalle.getDoc_maestro().getUsuario_creador()));
		}

		return pagIr;
	}

	public String desdeWorkFlowDocumentoEditar() {
		String pagIr = "";
		// revisamos del archivo plano primero la configuracion
		boolean swBdpostgres = "1".equalsIgnoreCase(confmessages
				.getString("bdpostgres"));
		String endPoint = confmessages.getString("endPoint");
		String tmp = confmessages.getString("carpeta_compartida");
		// vemos si hay configuracion en bd
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			Configuracion conf = configuraciones.get(0);
			swBdpostgres = conf.isBdpostgres();
			tmp = conf.getCarpetaCompartida();
			endPoint = conf.getEndPoint();
		}
		session.setAttribute("endPoint", endPoint);
		session.setAttribute("tmp", tmp);
		session.setAttribute("swBdpostgres", swBdpostgres);
		Doc_detalle detalle = (Doc_detalle) session.getAttribute("objeto");
		// System.out.println("detalle="+detalle.);

		// ____________________________________
		// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// obtenemos el nodo padre del docyumento

		treeNodoActual = delegado.obtenerNodo(detalle.getDoc_maestro()
				.getTree().getNodo());

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {

			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// CONSEGUIMOS LA SEGURIDAD TREE
		if (treeNodoActual != null) {

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {
				seguridadTree = super.getSeguridadTree(user_logueado,
						treeNodoActual);
			}
		}
		if (seguridadTree.isToView() || swSuperUsuario) {
			session.setAttribute("treeNodoActual", treeNodoActual);
			/*
			 * boolean verDetalles = true;
			 * super.guardamosHistoricoActivoDelDocumento(user_logueado,
			 * detalle.getDoc_maestro(), false, false, verDetalles, false,
			 * false, false, false, false, "");
			 */
			pagIr = "editarDocumento";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("Operaciones_noSelec")
							+ "(" + messages.getString("toView") + ") "
							+ messages.getString("doc_dueniotab") + ":"
							+ detalle.getDoc_maestro().getUsuario_creador()));
		}

		return pagIr;
	}

	public String actionDocumento() {
		String pagIr = "";
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {

			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// CONSEGUIMOS LA SEGURIDAD TREE
		if (treeNodoActual != null) {

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {

				seguridadTree = super.getSeguridadTree(user_logueado,
						treeNodoActual);

			}
		}

		if (seguridadTree.isToView() || swSuperUsuario) {

			pagIr = "listarDocumentos";
		}
		return pagIr;
	}

	public void selectId(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			Tree tree = delegado.obtenerNodo(id);
			session.setAttribute("treeNodoActual", tree);
		}
	}

	public boolean isSwMoverDocumento() {
		if (session.getAttribute("moverNodo") != null) {

			swMoverDocumento = true;
		}
		return swMoverDocumento;
	}

	public void setSwMoverDocumento(boolean swMoverDocumento) {
		this.swMoverDocumento = swMoverDocumento;
	}

	public boolean isSwVerArbol() {
		if (session.getAttribute("user_logueado") != null) {
			swVerArbol = true;
		}
		// solo a super usuario root vera los usuarios conectados n el sistema
		if (swSuperUsuario) {
			swVerArbol = true;
		} else {
			swVerArbol = false;
		}
		return swVerArbol;
	}

	public void setSwVerArbol(boolean swVerArbol) {
		this.swVerArbol = swVerArbol;
	}

	public List<Usuario> obtenerUsuariosConectados(List<Usuario> usuarios) {
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		ServletContext servletContext = (ServletContext) context.getContext();

		Map application = (Map) servletContext.getAttribute(confmessages
				.getString("variable_global")); // super.getApplicationMapValue(confmessages.getString("variable_global"));
		ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<Usuario> users = delegado.findAll_Usuario(user_logueado);
		for (Usuario usu : users) {
			if (application != null) {
				Usuario user = procesarLoginsConectados.loadUsuarioById(
						usu.getId(), application,
						confmessages.getString("usuariosApplicaction"));
				if (user != null) {
					usuarios.add(user);
				}
			}
		}
		return usuarios;
	}

	public List<Usuario> getUsuariosConectados() {
		usuariosConectados = new ArrayList();
		usuariosConectados = obtenerUsuariosConectados(usuariosConectados);

		session.setAttribute("usuariosConectados", usuariosConectados.size());
		return usuariosConectados;
	}

	public void setUsuariosConectados(List<Usuario> usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public List getNavigationItemsDocuments() {
		List menu = new ArrayList();
		// buscamos si esta moviendo el nodo
		if (session == null) {
			session = super.getSession();
		}
		// boolean swMoverNodo = session.getAttribute("moverNodo") != null;
		// Tree moverNodo = (Tree) session.getAttribute("moverNodo");
		// // genera registro o documento mueve
		// NavigationMenuItem pegarDocumento = null;
		// NavigationMenuItem cancel_pegarDocumento = null;
		// NavigationMenuItem raiz_menu = null;
		// // obtenemos variables del arbol, atravez del puente
		// treeNodoActual = session.getAttribute("treeNodoActual") != null ?
		// (Tree) session
		// .getAttribute("treeNodoActual") : null;
		// user_logueado = session.getAttribute("user_logueado") != null ?
		// (Usuario) session
		// .getAttribute("user_logueado") : null;
		//
		// // CONSEGUIMOS LA SEGURIDAD TREE
		// long tiponodo = 0;
		// if (treeNodoActual != null) {
		// tiponodo = treeNodoActual.getTiponodo();
		//
		// seguridadTree = super.getSeguridadTree(user_logueado,
		// treeNodoActual);
		// }
		//
		// // TREE SEGURIDAD..........................
		// boolean swMnuOrganizacion = false;
		// if (treeNodoActual != null && treeNodoActual.getNombre() != null) {
		// // VAMOS EN ACTION LISTENER, HAY PROCESAMOS ESTA VARIABLE
		// // Principal
		// String nombreEmpresa = "";
		// if (session.getAttribute("nombreEmpresa") != null) {
		// nombreEmpresa = (String) session.getAttribute("nombreEmpresa");
		// }
		// NavigationMenuItem products = getMenuNaviagtionItem(
		// messages.getString("apli_mnuOrganizacion") + " "
		// + nombreEmpresa + " ", "",
		// confmessages.getString("img_organizacion"));
		// if (seguridadTree != null
		// && (seguridadTree.isToSaveDataBasic() || swSuperUsuario)) {
		// // seguridadTree.isToMod();
		// NavigationMenuItem propiedades = getMenuNaviagtionItem(
		// messages.getString("propiedades") + " "
		// + treeNodoActual.getNombre(), "propiedades",
		// confmessages.getString("img_popiedades"));
		// products.add(propiedades);
		// swMnuOrganizacion = true;
		// }
		//
		// NavigationMenuItem mnuconfNodo = null;
		//
		// if (seguridadTree != null && seguridadTree.isConfSeguridad()
		// || swSuperUsuario) {
		// // se toco un nodo, y pocedemos ofrecer su coinfiguracion
		// mnuconfNodo = getMenuNaviagtionItem(
		// messages.getString("struct_confNodo") + " "
		// + treeNodoActual.getNombre(),
		// "agregarConfNodo", confmessages.getString("img_locked"));
		// // configuracion de permisologia para el propio nodo
		// products.add(mnuconfNodo);
		// swMnuOrganizacion = true;
		// }
		//
		// if (tiponodo == Utilidades.getNodoDocumento()) {
		// if (!swMoverNodo
		// && (seguridadTree != null && (seguridadTree.isToMove() ||
		// swSuperUsuario))) {
		// products.add(getMenuNaviagtionItem(
		// messages.getString("doc_cute"), "mover",
		// confmessages.getString("img_cute")));
		// swMnuOrganizacion = true;
		// }
		// }
		//
		//
		//
		// // LOS MENUS PARA ARRIBA.. PARA ABAJO SOLO HISTORICO Y ELIMINAR
		//
		// if (swMnuOrganizacion) {
		//
		// if (seguridadTree != null
		// && ((seguridadTree.isToDel()) || swSuperUsuario)) {
		// products.add(getMenuNaviagtionItem(
		// messages.getString("toDel"), "toDel",
		// confmessages.getString("img_erase")));
		// swMnuOrganizacion = true;
		// }
		//
		// List<com.ecological.paper.historico.Historico> listaNoVacia =
		// delegado
		// .findAllHistoricoTree(null, treeNodoActual);
		// if (listaNoVacia != null && !listaNoVacia.isEmpty()) {
		// if (seguridadTree != null
		// && ((seguridadTree.isToView()) || swSuperUsuario)) {
		// products.add(getMenuNaviagtionItem(
		// messages.getString("doc_hist"), "doc_hist",
		// confmessages.getString("img_historico")));
		// swMnuOrganizacion = true;
		// }
		// }
		//
		// if (seguridadTree != null
		// && ((seguridadTree.isToView()) || swSuperUsuario)) {
		// products.add(getMenuNaviagtionItem(
		// messages.getString("doc_documento"),
		// "doc_documento",
		// confmessages.getString("img_default")));
		// swMnuOrganizacion = true;
		// }
		//
		// menu.add(products);
		// }
		// }
		//
		// if (user_logueado != null) {
		// mnUser_logueado = getMenuNaviagtionItem(user_logueado.getNombre()
		// + " " + user_logueado.getApellido(),
		// "refrescarindocumentos", getImg_userLogueado());
		// menu.add(mnUser_logueado);
		//
		// // SALIR
		// NavigationMenuItem salir = getMenuNaviagtionItem(
		// messages.getString("menu_exit"), "Salir",
		// confmessages.getString("img_salir"));
		//
		// menu.add(salir);
		// }
		//
		// panelNavigationItems = menu;
		return panelNavigationItems;
	}

	public String getObtenVariable() {

		if (getVarOculta() != null) {
			if (getVarOculta().getValue() != null) {
				cantidadCapturada = (String) getVarOculta().getValue();
			}
		}

		return cantidadCapturada;
	}

	public String getCantidadCapturada() {

		return cantidadCapturada;
	}

	public void setCantidadCapturada(String cantidadCapturada) {
		this.cantidadCapturada = cantidadCapturada;
	}

	public HtmlInputHidden getVarOculta() {
		return varOculta;
	}

	public void setVarOculta(HtmlInputHidden varOculta) {
		this.varOculta = varOculta;
	}

	public HashMap getTreeHijosMap() {
		return treeHijosMap;
	}

	public void setTreeHijosMap(HashMap treeHijosMap) {
		this.treeHijosMap = treeHijosMap;
	}

	public String getCreado() {
		String creado = "";
		if (treeNodoActual.getFecha_creado() != null) {
			creado = messages.getString("usuario_fechac")
					+ " ["
					+ Utilidades.sdfShow.format(treeNodoActual
							.getFecha_creado()) + "]";
		}
		if (treeNodoActual.getUsuario_creador() != null) {
			creado = creado
					+ " "
					+ treeNodoActual.getUsuario_creador().getNombre()
					+ " "
					+ treeNodoActual.getUsuario_creador().getApellido()
					+ "["
					+ treeNodoActual.getUsuario_creador().getCargo()
							.getNombre() + "]";
		}

		return creado;
	}

	public void setCreado(String creado) {
		this.creado = creado;
	}

	public boolean isSwHeredarPermisos() {
		return swHeredarPermisos;
	}

	public void setSwHeredarPermisos(boolean swHeredarPermisos) {
		this.swHeredarPermisos = swHeredarPermisos;
	}

	public String getDescripcionTree() {
		return descripcionTree;
	}

	public void setDescripcionTree(String descripcionTree) {
		this.descripcionTree = descripcionTree;
	}

	public String saveDatosBasicos_action() {
		String pagIr = "";
		if (tree != null) {
			if (("".equals(tree.getNombre().toString()))
					|| (super.isEmptyOrNull(tree.getDescripcion().toString()))
					|| ("".equals(tree.getDescripcion().toString()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			} else {
				// estoy en el mismo nodo
				Tree treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
						.getAttribute("treeNodoActual") : null;
				if (validaHijosMismoNombreError(treeNodoActual, tree)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_tree_existe")));

					return "";
				}

				refrescaraplicacionarbol();
				delegado.edit(tree);
				session.setAttribute("pagIr", Utilidades.getAplicacion());
				pagIr = Utilidades.getFinexitoso();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			}

		}

		return pagIr;
	}

	public String deleteTree() {
		String pagIr = "";
		Tree toDelTree;
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
				session.setAttribute("pagIr", Utilidades.getAplicacion());
				Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				// eliminamos de la cache de session
				// si es doc ., lo borramos de la cache
				if (toDelTree.getTiponodo() - Utilidades.getNodoDocumento() == 0) {
					super.deleteSeguridadTree(toDelTree);
					ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
					clienteSeguridad.ponerSeguridadInSession(user_logueado);
					// HiloClienteSeguridad hiloClienteSeguridad = new
					// HiloClienteSeguridad(user_logueado);
					// hiloClienteSeguridad.start();
				} else {
					ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
					clienteSeguridad.ponerSeguridadInSession(user_logueado);
					// HiloClienteSeguridad hiloClienteSeguridad = new
					// HiloClienteSeguridad(user_logueado);
					// hiloClienteSeguridad.start();
				}

				pagIr = Utilidades.getFinexitoso();
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

	private String seguridadHijosRichFaces(long nodoPadre, char objArbol) {
		StringBuffer cad2 = new StringBuffer("");

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List nodosHijosLst = null;
		Tree estaInSeguridadTree = delegado.obtenerNodo(nodoPadre);
		seguridadTree = super.getSeguridadTree(estaInSeguridadTree);
		// chequeamos la seguridad, si es root o tiene permiso de ver, se pinta
		// el arbol
		if (user_logueado != null
				&& user_logueado.getLogin().equalsIgnoreCase(
						Utilidades.getRoot())
				|| (seguridadTree != null && seguridadTree.isToView())) {

			nodosHijosLst = delegado.findAllTreeHijos(nodoPadre);

			if (nodosHijosLst != null) {
				Iterator nodosHijosIt = nodosHijosLst.iterator();
				Usuario user_cargo;
				while (nodosHijosIt.hasNext()) {
					Tree treeHijo = (Tree) nodosHijosIt.next();
					seguridadTree = super.getSeguridadTree(treeHijo);

					user_cargo = null;
					String nombre = null;
					String apellido = null;

					// chequeamos la seguridad, si es root o tiene permiso de
					// ver, se pinta el arbol
					if (user_logueado != null
							&& user_logueado.getLogin().equalsIgnoreCase(
									Utilidades.getRoot())
							|| (seguridadTree != null && seguridadTree
									.isToView())) {

						/*********************** Buscando Cargo *******************************************************************/
						if (treeHijo.getTiponodo() == Utilidades.getNodoCargo()) {
							user_cargo = new Usuario();
							user_cargo.setCargo(treeHijo);
							List staCargoUsu = delegado
									.findExisteCargoInUsuario(user_cargo);
							Iterator it = staCargoUsu.iterator();
							if (it.hasNext()) {

								user_cargo = (Usuario) it.next();
								nombre = Utilidades.getRemplazoCaracter(
										user_cargo.getNombre(),
										Utilidades.getCommilla(), '_');
								apellido = Utilidades.getRemplazoCaracter(
										user_cargo.getApellido(),
										Utilidades.getCommilla(), '_');
							}
						}
						/******************** Fin***Buscando Cargo *******************************************************************/
						cad2.append("\n\r");
						cad2.append(objArbol).append(".add(")
								.append(treeHijo.getNodo()).append(",")
								.append(treeHijo.getNodopadre()).append(",'");
						if (user_cargo != null && nombre != null) {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("[")
									.append(nombre != null ? nombre.trim() : "")
									.append(" ")
									.append(apellido != null ? apellido.trim()
											: "Vacio").append("]").append("'");
						} else {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("'");
						}

						// ___________________________________________________________________________________________
						// primera pag con parametros
						cad2.append(",'./puente.jsf?");
						cad2.append("idroot=").append(treeHijo.getNodo());
						cad2.append("'");

						// ___________________________________________________________________________________________
						if (treeHijo.getTiponodo() == Utilidades
								.getNodoPrincipal()) {
							cad2.append(",'Titulo','','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoArea()) {
							cad2.append(",'Titulo','','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCargo()) {
							if (user_cargo != null) {
								cad2.append(",'")
										.append(messages
												.getString("usuario_mail"))
										.append(":")
										.append(user_cargo.getMail_principal() != null ? user_cargo
												.getMail_principal().trim()
												: "");
								cad2.append("  ")
										.append(messages
												.getString("usuario_fechac"))
										.append(":")
										.append(user_cargo.getFecha_creado());
								cad2.append("','','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("');");
							} else {

								cad2.append(",'Titulo','','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("');");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoProceso()) {

							cad2.append(",'Titulo','','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCarpeta()) {
							// buscamos si tiene documentos el nodo
							List<Doc_maestro> lista = delegado
									.findAllDoc_maestrosCarpetas(treeHijo);
							if (lista != null && !lista.isEmpty()) {

								cad2.append(",'','','")
										.append(confmessages
												.getString("img.carpetallena"))
										.append("');   ");
							} else {
								cad2.append(",'','','")
										.append(getImg_carpeta())
										.append("');   ");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoDocumento()) {

							// icono= obtenIconoDoc(doc_detalle.getNameFile());
							//
							cad2.append(",'Titulo','','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("');");
						} else {
							cad2.append(",'Versiones ...','','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("');");
						}
						cad2.append("\n\r");
						String nodos = null;
						if (treeHijo != null) {
							nodos = seguridadHijos(treeHijo.getNodo(), objArbol);
						}

						if ((nodos != null) && (nodos.length() > 0)) {
							cad2.append(nodos);
						}
					}
				}
			}
		}
		return cad2.toString();
	}

	public void saveDocScaneadosToArbol() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<ScannerDoc> lista = delegado.findAllScannerDoc(user_logueado);
		ClientePadreDocumento clientePadreDocumento = null;
		if (lista != null) {
			int i = ((int) Math.random() * 9999);
			;
			for (ScannerDoc sc : lista) {
				i++;
				clientePadreDocumento = new ClientePadreDocumento("");
				File file = null;
				try {
					if (sc != null && sc.getData_doc_postgres() != null) {
						file = saveFileInDisk(sc.getData_doc_postgres(), null,
								"documento.jpg");
					} else if (sc != null
							&& sc.getData_doc().getBinaryStream() != null) {
						file = saveFileInDisk(new byte[8096], sc.getData_doc()
								.getBinaryStream(), "documento.jpg");
					}

				} catch (Exception e) {
					// e.printStackTrace();
					e.printStackTrace();
					delegado.destroy(sc);
					continue;
					// TODO Auto-generated catch block

				}

				Doc_detalle doc_detalle = new Doc_detalle();
				Doc_maestro doc_maestro = new Doc_maestro();
				doc_maestro.setEmpresa(user_logueado.getEmpresa());
				doc_maestro.setUsuario_creador(user_logueado);
				doc_detalle.setDoc_maestro(doc_maestro);
				int azar = ((int) Math.random() * 9999);
				doc_detalle.getDoc_maestro().setNombre("scanner_" + i + azar);
				doc_detalle.getDoc_maestro().setConsecutivo("1111_" + i + azar);
				Doc_tipo tipo = new Doc_tipo();
				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

				tipo = delegado.findDocTipo(tipo, user_logueado);
				doc_detalle.getDoc_maestro().setDoc_tipo(tipo);
				doc_detalle.setMayorVer("1");
				doc_detalle.setMinorVer("0");
				doc_detalle.setDuenio(user_logueado);
				Doc_estado borrador = new Doc_estado();
				borrador.setCodigo(Utilidades.getBorrador());
				borrador = delegado.findDocEstado(borrador);
				doc_detalle.setDoc_estado(borrador);
				doc_detalle.getDoc_maestro().setBusquedakeys("scanner");

				Tree treeScanner = sc.getTree();
				clientePadreDocumento.setTreeNodoActual(treeScanner);
				clientePadreDocumento.setUser_logueado(this.user_logueado);
				clientePadreDocumento.setDoc_detalle(doc_detalle);
				clientePadreDocumento.setDoc_maestro(doc_detalle
						.getDoc_maestro());
				clientePadreDocumento.setRoleParaPermisos(null);
				clientePadreDocumento.setSwPermGrupo(false);
				clientePadreDocumento.setSwHeredarPermisos(false);
				try {
					String pagIr = clientePadreDocumento.uploadRichFaces(file,
							"documento_" + i, "image/x-bmp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				delegado.destroy(sc);
			}
		}

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

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public void setDocBuscar(String docBuscar) {
		this.docBuscar = docBuscar;
	}

	public List getFlowsSinFirmarLst() {
		return flowsSinFirmarLst;
	}

	public void setFlowsSinFirmarLst(List flowsSinFirmarLst) {
		this.flowsSinFirmarLst = flowsSinFirmarLst;
	}

	public int getFlowsSinFirmar() {
		return flowsSinFirmar;
	}

	public void setFlowsSinFirmar(int flowsSinFirmar) {
		this.flowsSinFirmar = flowsSinFirmar;
	}

	public String getCadenaFecha() {
		return cadenaFecha;
	}

	public void setCadenaFecha(String cadenaFecha) {
		this.cadenaFecha = cadenaFecha;
	}

	public boolean isSwTreeNodoActual() {
		swTreeNodoActual = session.getAttribute("treeNodoActual") != null;
		return swTreeNodoActual;
	}

	public void setSwTreeNodoActual(boolean swTreeNodoActual) {
		this.swTreeNodoActual = swTreeNodoActual;
	}

	public Doc_tipo getDoc_tipo() {
		doc_tipo = session.getAttribute("doc_tipo") != null ? (Doc_tipo) session
				.getAttribute("doc_tipo") : null;

		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		session.setAttribute("doc_tipo", doc_tipo);
		setSwbusqueda(true);
		this.doc_tipo = doc_tipo;
	}

	public String getStrBuscar() {
		strBuscar = session.getAttribute("strBuscar") != null ? (String) session
				.getAttribute("strBuscar") : null;
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		setSwbusqueda(true);
		session.setAttribute("swbusqueda", true);

		this.strBuscar = strBuscar;
	}

	public List<Doc_maestro> getMostrarListDocumentosAux() {
		mostrarListDocumentosAux = session
				.getAttribute("mostrarListDocumentosAux") != null ? (List<Doc_maestro>) session
				.getAttribute("mostrarListDocumentosAux") : null;

		return mostrarListDocumentosAux;
	}

	public void setMostrarListDocumentosAux(
			List<Doc_maestro> mostrarListDocumentosAux) {
		session.setAttribute("mostrarListDocumentosAux",
				mostrarListDocumentosAux);
		this.mostrarListDocumentosAux = mostrarListDocumentosAux;
	}

	public boolean isSwbusqueda() {
		swbusqueda = session.getAttribute("swbusqueda") != null ? (Boolean) session
				.getAttribute("swbusqueda") : false;
		return swbusqueda;
	}

	public void setSwbusqueda(boolean swbusqueda) {
		session.setAttribute("swbusqueda", swbusqueda);
		this.swbusqueda = swbusqueda;
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

	public String getDiasVence() {
		try {
			if (user_logueado.getFecha_caduca() != null) {
				try {
					diasVence = numerodeDiasEnFechaCaducar(
							user_logueado.getFecha_caduca(), null)
							+ "";
					// mnUser_logueado = getMenuNaviagtionItem(
					// user_logueado.getNombre() + " "
					// + user_logueado.getApellido() + ", "
					// + messages.getString("expiraen") + ":"
					// + diasVence + " "
					// + messages.getString("dias"), "refrescar",
					// getImg_userLogueado());
					diasVence = messages.getString("expiraen") + ":"
							+ diasVence + " " + messages.getString("dias");
				} catch (Exception e) {
					diasVence = "";
				}

			} else {
				diasVence = "";
				// mnUser_logueado = getMenuNaviagtionItem(
				// user_logueado.getNombre() + " "
				// + user_logueado.getApellido(), "refrescar",
				// confmessages.getString("img_refresh"));
			}
		} catch (Exception e) {
			System.out.println("dia vence");
		}

		return diasVence;
	}

	public void setDiasVence(String diasVence) {
		this.diasVence = diasVence;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAnchoLeftTree() {
		return anchoLeftTree;
	}

	public void setAnchoLeftTree(String anchoLeftTree) {
		this.anchoLeftTree = anchoLeftTree;
	}

	public String getAnchoCentrelTree() {
		return anchoCentrelTree;
	}

	public void setAnchoCentrelTree(String anchoCentrelTree) {
		this.anchoCentrelTree = anchoCentrelTree;
	}

}