package com.ecological.paper.cliente.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.paper.cliente.documentos.ClientePadreDocumento;
import com.ecological.paper.cliente.flows.ClienteFlows;
import com.ecological.paper.cliente.flows.ClienteFlowsParalelo;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.cliente.seguridad.ClienteSeguridadRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridadTab2;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;

import com.ecological.paper.usuario.Usuario;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;

@ManagedBean(name = "viewTreeDocumentos")
@SessionScoped
public class ViewTreeDocumentos extends ClienteTree implements ViewImp {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Seguridad seguridadTree = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;

	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	public ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	public ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");

	// editar con richfaces
	private List<Doc_maestro> allObjectItems = null;
	private Doc_maestro objeto = null;
	private Doc_maestro objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String title;

	private String cualquierComentario;
	private String descripcionTree;
	private boolean swInfo;
	private Tree treeNodoActual;
	private Tree tree;
	private String tipoNodoCrear;
	private String text;
	private boolean swDocumento;
	private boolean swSuperUsuario;
	// Principal
	private boolean toAddPrincipal;

	// Area
	private boolean toAddArea;

	// proceso
	private boolean toAddProceso;

	// carpeta
	private boolean toAddCarpeta;

	// genrar registro
	private boolean toGenerarRegistroII;

	// cargo
	private boolean toAddCargo;

	// Lotes de Documentos
	private boolean toAddLotesDeDocumentos;

	// Documentos
	private boolean toAddDocumentos;

	private boolean toAddDocumentoSvn;

	private boolean toLoadmasivosdocumentos;

	private boolean swNodoRaiz;
	private boolean swNodoPrincipal;
	private boolean swNodoArea;
	private boolean swNodoCargo;
	private boolean swNodoCarpeta;
	private boolean swNodoDocumento;
	private boolean swNodoProceso;
	private boolean swMnuOrganizacion;
	private boolean swToDel;
	private boolean swDocHist;
	private boolean swHeredarPermisos;
	private String nodoPrincipal;
	private DualListModel<Role> operacionesRole;
	private DualListModel<Usuario> operacionesUsuario;
	private DualListModel<Operaciones> operaciones;
	private List<Role> invisibleItems = new ArrayList<Role>();
	private List<Role> visibleItems = new ArrayList<Role>();
	private List<Operaciones> invisibleItemsOperaciones = new ArrayList<Operaciones>();
	private List<Operaciones> visibleItemsOperaciones = new ArrayList<Operaciones>();
	private List<Usuario> invisibleItemsUsuario = new ArrayList<Usuario>();
	private List<Usuario> visibleItemsUsuario = new ArrayList<Usuario>();

	private DatosCombo datosCombo = new DatosCombo();
	private HtmlSelectOneMenu selectUsuario;
	private Usuario usuario;
	private boolean heredaSeguridad;
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private List<Doc_detalle> mostrarListDocumentosDetalles;
	private List<Doc_detalle> doc_detallesAux;
	private List<Doc_detalle> doc_detallesAux_session;
	private Doc_detalle doc_detallePrincipal_2 = null;
	private Role roleParaPermisos;
	private boolean swPermGrupo;
	// FOR FLOWS
	private boolean swEditando;
	private boolean tipoFlow = false;
	// private List mandarMailUsuarios;
	private FlowParalelo flowParalelo;
	// guardar una plantilla de flujos para un documento tipo plantilla
	private boolean toPlantillaInDocFlowParalelo;
	private boolean swCanSolicitudimpresion;
	private boolean swDeshabilitarEditaSolicitudimpresion;
	private boolean swTipoFormatoPlantilla;
	private String areaDocumentos;
	private String tipoDocumento;
	private boolean swFlowParaleloSession;
	private boolean swDeshabilitarflowAprobacionParalelo;
	private Flow flow;
	private boolean flowAprobacion;
	private boolean swDeshabilitarflowAprobacion;
	private boolean swSolicitudimpresion;
	private boolean swCrearHijoDeFlowParalelo;
	private boolean swSi = true;
	private List<Flow> listaFlujosParalelos;
	private boolean swMoverNodo;

	private boolean swMostrarListaPreceder;
	private List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
	private boolean swMostraDocIframe;
	private Doc_detalle doc_detalle_ver;

	public ViewTreeDocumentos() {
		// super();
		// inicializamos
		toPlantillaInDocFlowParalelo = false;

		// damos permisos o escojemos los permisos de un perfil o role
		swPermGrupo = true;
		// fin inicializamos

		session = super.getSession();
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		// CONSEGUIMOS LA SEGURIDAD MENU
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

		// CONSEGUIMOS LA SEGURIDAD TREE
		long tiponodo = 0;
		if (treeNodoActual != null) {
			tiponodo = treeNodoActual.getTiponodo();

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoRaiz()) {
				swNodoRaiz = true;

			}

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {
				seguridadTree = super.getSeguridadTree(user_logueado,
						treeNodoActual);
				swNodoDocumento = true;

			} else {

				if (treeNodoActual == null) {
					treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
							.getAttribute("treeNodoActual") : null;
				}
				seguridadTree = super.getSeguridadTree(treeNodoActual);
			}

			if (tiponodo == Utilidades.getNodoRaiz()) {
				if (seguridadTree != null
						&& (seguridadTree.isToAddPrincipal() || swSuperUsuario)) {
					// SOLO SE SUMAN PRINCIPALES
					setToAddPrincipal(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;
				}
			}

			// SE USA PARA MOVER NODOS...
			swMoverNodo = session.getAttribute("moverNodo") != null;
			Tree moverNodo = (Tree) session.getAttribute("moverNodo");
			toGenerarRegistroII = false;
			// FIN SE USA PARA MOVER NODOS...

			if (tiponodo == Utilidades.getNodoPrincipal()) {

				swNodoPrincipal = true;

				// si esta emoviendo el nodo
				if (swMoverNodo
						&& (moverNodo.getTiponodo() == Utilidades.getNodoArea()
								|| moverNodo.getTiponodo() == Utilidades
										.getNodoProceso() || moverNodo
								.getTiponodo() == Utilidades.getNodoCarpeta())) {

					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;

				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddArea() || swSuperUsuario)) {
					setToAddArea(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarArea"),
					// "agregarArea", getImg_area()));

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
					setToAddProceso(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarProceso"),
					// "agregarProceso", getImg_proceso()));

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
					setToAddCarpeta(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta", getImg_carpeta()));
				}
			}

			if (tiponodo == Utilidades.getNodoArea()) {

				swNodoArea = true;
				// si esta emoviendo el nodo
				if (swMoverNodo
						&& (moverNodo.getTiponodo() == Utilidades
								.getNodoCargo()
								|| moverNodo.getTiponodo() == Utilidades
										.getNodoProceso() || moverNodo
								.getTiponodo() == Utilidades.getNodoCarpeta())) {
					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;
				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddCargo() || swSuperUsuario)) {
					setToAddCargo(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCargo"),
					// "agregarCargo", getImg_cargo()));

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarProceso"),
					// "agregarProceso", getImg_proceso()));
					setToAddProceso(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta", getImg_carpeta()));
					setToAddCarpeta(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
			}
			if (tiponodo == Utilidades.getNodoCargo()) {

				swNodoCargo = true;

				// si esta emoviendo el nodo
				if (swMoverNodo
						&& (moverNodo.getTiponodo() == Utilidades
								.getNodoProceso() || moverNodo.getTiponodo() == Utilidades
								.getNodoCarpeta())) {
					// // pegarDocumento = getMenuNaviagtionItem(
					// // messages.getString("doc_page"),
					// // "generarRegistroII",
					// // confmessages.getString("img_page"));
					// // cancel_pegarDocumento = getMenuNaviagtionItem(
					// // messages.getString("btn_cancelar"),
					// // "cancelpage",
					// // confmessages.getString("img_cancelpage"));
					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;

				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddProceso() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarProceso"),
					// "agregarProceso", getImg_proceso()));
					setToAddProceso(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta", getImg_carpeta()));
					setToAddCarpeta(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
			}

			if (tiponodo == Utilidades.getNodoProceso()) {
				swNodoProceso = true;

				// si esta emoviendo el nodo
				if (swMoverNodo
						&& (moverNodo.getTiponodo() == Utilidades
								.getNodoCarpeta())) {
					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;

				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta", getImg_carpeta()));
					setToAddCarpeta(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
				// products.add(getMenuNaviagtionItem(messages.getString("menu_agregarCarpeta"),
				// "agregarCarpeta",getImg_carpeta()));
			}

			if (tiponodo == Utilidades.getNodoCarpeta()) {

				swNodoCarpeta = true;
				// AL MOVER O PEGAR OI GENERAR REGISTRO SE VA AESTE SERVLET
				// PuenteGenerarRegistroCopyDoc.jsf
				// FIN AL MOVER O PEGAR OI GENERAR REGISTRO SE VA AESTE
				// SERVLET

				// si esta emoviendo el nodo
				if ((swMoverNodo)
						&& (moverNodo.getTiponodo() == Utilidades
								.getNodoDocumento() || moverNodo.getTiponodo() == Utilidades
								.getNodoCarpeta())) {
					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;
				} else if (session.getAttribute("swPage") != null) {

					setToGenerarRegistroII(true);
					swMnuOrganizacion = true;
				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddCarpeta() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarCarpeta"),
					// "agregarCarpeta", getImg_carpeta()));
					setToAddCarpeta(true);
					// SE MUESTRA LA BARRA MENU
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
					setToLoadmasivosdocumentos(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}
				if (seguridadTree != null
						&& (seguridadTree.isToAddDocumentos() || swSuperUsuario)) {
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarDocumento"),
					// "agregarDocumento", getImg_doc()));
					setToAddDocumentos(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}

				if (seguridadTree != null
						&& (seguridadTree.isToAddDocumentoSvn() || swSuperUsuario)) {
					/* agregamos documentos a traves de subversion..., */
					// products.add(getMenuNaviagtionItem(
					// messages.getString("menu_agregarDocumentosvn"),
					// "agregarDocumentosvn",
					// confmessages.getString("img_svn")));
					setToAddDocumentoSvn(true);
					// SE MUESTRA LA BARRA MENU
					swMnuOrganizacion = true;

				}

			}

			if (swMnuOrganizacion) {
				if (seguridadTree != null
						&& ((seguridadTree.isToDel()) || swSuperUsuario)) {

					setSwToDel(true);

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
						setSwDocHist(true);
						// products.add(getMenuNaviagtionItem(
						// messages.getString("doc_hist"), "doc_hist",
						// confmessages.getString("img_historico")));
						swMnuOrganizacion = true;
					}
				}
			}

		}

		objeto = new Doc_maestro();

	}

	public String aplicacionApplet() {

		return "aplicacionApplet";
	}

	public String acercaDeDocumento() {
		return "acercaDeDocumento";
	}

	public String saveUsuario_OperacionesRichFaces() {

		String pagIr = "";
		try {
			ClienteSeguridadTab2 clienteSeguridadTab2 = new ClienteSeguridadTab2(
					"");
			clienteSeguridadTab2.setHeredaSeguridad(heredaSeguridad);
			clienteSeguridadTab2.setUsuario(usuario);
			pagIr = clienteSeguridadTab2
					.saveUsuario_OperacionesRichFaces(operaciones.getTarget());
			// REFRESCAMOS DETALLES
			refrescaraplicaciondetalles();

		} catch (RoleMultiples e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagIr;
	}

	public String saveRole() {

		String pagIr = "";
		// REFRTESCAMOS EL ARBOL
		refrescaraplicacionarbol();
		// REFRESCAMOS DETALLES
		refrescaraplicaciondetalles();

		// para recrear el arbol de seguridad denuevo
		ClienteSeguridadRole clienteSeguridadRole = new ClienteSeguridadRole("");
		try {
			clienteSeguridadRole.setHeredaSeguridad(heredaSeguridad);
			pagIr = clienteSeguridadRole.saveRoleRichFaces(operacionesRole
					.getTarget());

		} catch (RoleMultiples e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagIr;
	}

	public String cancelpage() {
		// BORRAMOS TDO PARA QE NO SE VEAL EL ARBOL Y SE OBLIGUE A SELECCIONAR
		// UN NODO
		session.removeAttribute("swPage");
		setToGenerarRegistroII(false);
		return "cancelpage";
	}

	public String generarRegistroII() {
		session.setAttribute(Utilidades.getTreeprocesando(),
				Utilidades.getTreeprocesando());

		return "generarRegistroII";
	}

	public String flows() {

		tree = treeNodoActual;

		Doc_detalle doc_detalle = null;
		doc_detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (doc_detalle == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		}

		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		obtenerRolesFlows();

		// llenamos usuarios
		visibleItemsUsuario = new ArrayList();
		invisibleItemsUsuario = new ArrayList();

		datosCombo.llenarUsuariosFlowVisiblesRichFaces(visibleItemsUsuario,
				invisibleItemsUsuario, doc_detalle);

		operacionesUsuario = new DualListModel<Usuario>(invisibleItemsUsuario,
				visibleItemsUsuario);

		// inicializaForFlow();

		return "flows";
	}

	public String flows_action() {

		// LLENAMOS TODA SLAS VARIABLES PARA IRSE EL FLOW
		versionId();
		// FIN LLENAMOS TODA SLAS VARIABLES PARA IRSE EL FLOW
		session.removeAttribute("swSolicitudimpresion");
		return Utilidades.getFlows();

	}

	public void obtenerRolesFlows() {

		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
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
		Flow f = null;
		if (doc_detalle.getFlowEditar() != null) {
			f = doc_detalle.getFlowEditar();
		} else {
			List flows = delegado.findByFlow(doc_detalle);
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

		datosCombo.llenarRoleFlowVisiblesRichFaces(visibleItems,
				invisibleItems, f);
		operacionesRole = new DualListModel<Role>(invisibleItems, visibleItems);

	}

	public String modificarDocumento() {
		session.removeAttribute("clientePadreDocumento");
		ClientePadreDocumento clientePadreDocumento = new ClientePadreDocumento();
		doc_detallePrincipal_2.setStatus(true);
		Doc_detalle d = delegado.findDocDetalle(doc_detallePrincipal_2);
		clientePadreDocumento.setDoc_maestro(doc_detallePrincipal_2
				.getDoc_maestro());
		clientePadreDocumento.setDoc_detalle(doc_detallePrincipal_2);
		clientePadreDocumento.getDoc_detalle().setSize_doc(d.getSize_doc());
		clientePadreDocumento.setRoleParaPermisos(roleParaPermisos);
		clientePadreDocumento.setSwPermGrupo(swPermGrupo);

		return clientePadreDocumento.modificarDocumentoRichFaces();
	}

	public String editDocumento() {

		if (doc_detallePrincipal_2 != null) {
			System.out
					.println("doc_detallePrincipal_2.getDoc_maestro().getNombre()="
							+ doc_detallePrincipal_2.getDoc_maestro()
									.getNombre());
		}

		session.setAttribute("doc_detalle", doc_detallePrincipal_2);
		return "editDocumento";
	}

	public String crearTreePropiedades() {

		tree = new Tree();
		return "crearTreePropiedades";
	}

	public String crearTreeSeguridad() {

		session.removeAttribute("clientePadreDocumento");
		tree = treeNodoActual;
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			usuario = user_logueado;
		}

		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		tree.setUsuario_creador(user_logueado);
		datosCombo.findRoles_PorTreeRichFaces(visibleItems, invisibleItems,
				tree);
		operacionesRole = new DualListModel<Role>(invisibleItems, visibleItems);

		List<Role> roles = new ArrayList<Role>();
		roles.addAll(invisibleItems);
		roles.addAll(visibleItems);
		participantesGruposPlantila = super
				.participantesPerfilesPlantila(roles);
		return "seguridadTree";
	}

	public String editTreePropiedades() {
		// porocesaando arbol

		tree = treeNodoActual;
		return "crearTreePropiedades";
	}

	public String deleteTreePropiedades() {

		tree = treeNodoActual;
		return "treePropiedadesEliminar";
	}

	public String deleteTreePropiedadesDetalles() {
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		tree = treeNodoActual;
		return "treePropiedadesEliminar";
	}

	public String remove() {
		// porocesaando arbol
		session.setAttribute(Utilidades.getTreeprocesando(),
				Utilidades.getTreeprocesando());
		session.setAttribute("toDelTree", this.tree);
		super.setCualquierComentario(this.cualquierComentario);
		String pagIr = super.deleteTree();

		// para que ya no se vea la barra de iconos
		session.removeAttribute("treeNodoActual");
		swMnuOrganizacion = false;
		// fin para que ya no se vea la barra de iconos
		return pagIr;
	}

	public List<?> getAllObjectItems() {
		allObjectItems = super.getMostrarListDocumentos();
		return allObjectItems;
	}

	public String editar() {
		String pagIr = "";

		return pagIr;
	}

	public String aceptar() {
		// TODO Auto-generated method stub
		// super.clearSession(session,
		// confmessages.getString("usuarioSession"));
		String pagIr = "aplicacion";
		if (session.getAttribute(Utilidades.getFlows()) != null) {
			session.removeAttribute(Utilidades.getFlows());
			return "flowsResponse";
		}

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		if (treeNodoActual != null) {

			if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {
				refrescaraplicaciondetalles();

				super.clearSession(session,
						confmessages.getString("usuarioSession"));
				pagIr = Utilidades.getListarDocumentos();

				// pagIr = Utilidades.getListarAplicacion();
			} else {
				pagIr = Utilidades.getListarAplicacion();
			}
		} else if (session.getAttribute("pagIr") != null) {
			pagIr = (String) session.getAttribute("pagIr");

			// viene de seguridad y cancelo, ahora vamos alistar documentos
			if ("seguridadTree".equalsIgnoreCase(pagIr)) {
				refrescaraplicaciondetalles();

				pagIr = Utilidades.getListarDocumentos();
				// pagIr = Utilidades.getListarAplicacion();

				super.clearSession(session,
						confmessages.getString("usuarioSession"));
			}
		}

		return pagIr;
	}

	public String listarAplicacionDetalle() {
		// TODO Auto-generated method stub
		// super.clearSession(session,
		// confmessages.getString("usuarioSession"));
		String pagIr = Utilidades.getListarDocumentos();
		return pagIr;
	}

	public String mas() {
		swInfo = true;
		session.setAttribute("treeNodoActual", objectItem.getTree());

		treeNodoActual = objectItem.getTree();
		// ESTE ES EL DOC_MAESTRO
		Doc_detalle doc_detalle = delegado.findUltimolDoc_Detalles(objectItem);
		// SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
		doc_detalle.setOrigen(Utilidades.getOrigenDocumentoFlow());
		// FIN SI SE HACE FLOW
		// FIN SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
		session.setAttribute("doc_detalle", doc_detalle);
		session.setAttribute("doc_detalleforflowplantilla", doc_detalle);
		session.setAttribute("doc_maestro", objectItem);
		// FIN ESTE ES EL DOC_MAESTRO

		session.setAttribute("treeNodoActual", treeNodoActual);
		if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {
			seguridadTree = super.getSeguridadTree(user_logueado,
					treeNodoActual);
		} else {

			seguridadTree = super.getSeguridadTree(treeNodoActual);
		}
		seguridadTree.setTree(treeNodoActual);
		// OBTENEMOPS RTODOS LOS DETALLES EN SESSION
		// getDoc_detallesAux_session();
		// FINB OBTENEMOPS RTODOS LOS DETALLES EN SESSION
		return "aplicaciondetalles";
	}

	public List<Doc_detalle> getDoc_detallesAux() {
		try {

			if (objectItem != null && objectItem.getTree() != null) {
				session.setAttribute("treeNodoActual", objectItem.getTree());
			}

			session.removeAttribute("clientePadreDocumento");
			ClientePadreDocumento clientePadreDocumento = new ClientePadreDocumento();

			doc_detallesAux = clientePadreDocumento.getDoc_detalles();
			doc_detallePrincipal_2 = clientePadreDocumento
					.getDoc_detallePrincipal_2();
			// veremos quienes son vigentes
			List<Doc_detalle> doc_detallesAux2 = new ArrayList();
			// se puede hacer doc publico
			boolean swVigente = false;
			boolean swUnaSolaVez = false;
			for (Doc_detalle d : doc_detallesAux) {

				// SOLO PARA LA LISTA DE DOCUMENTOS QUE SE GENERA CON
				// doc_detallesAux_session
				// SI ES VIGENTE EL DOCUMENTO, PUEDE STAR PUBLICADO

				if ((d.getDoc_estado().getCodigo() - Utilidades.getVigente()) == 0) {
					d.setSwVigente(true);

				}

				// FIN SOLO PARA LA LISTA DE DOCUMENTOS QUE SE GENERA CON
				// doc_detallesAux_session

				// SOLO PARA EWL LINK PUBLICAR DOCUMENTO
				// VEMOS SI HAY UNA VERSION VGENTE Y ESTE PUBLICADO EL DOCUMENTO
				if (!swUnaSolaVez
						&& ((d.getDoc_estado().getCodigo() - Utilidades
								.getVigente()) == 0)) {
					swUnaSolaVez = true;
					// CON EL PUBLICO ME ENCUENTRA EL DOC VIGENTE
					String publico = "publico";
					// fin CON EL PUBLICO ME ENCUENTRA EL DOC VIGENTE
					List<Doc_detalle> doc_detalleVigente = (List<Doc_detalle>) delegado
							.encontrarDetalles(d.getDoc_maestro().getCodigo(),
									publico);
					if (doc_detalleVigente != null
							&& !doc_detalleVigente.isEmpty()
							&& doc_detalleVigente.size() > 0) {
						swVigente = true;
						doc_detallePrincipal_2.setSwCanDoPublicoIsVigente(true);

					}
				}
				// FIN SOLO PARA EWL LINK PUBLICAR DOCUMENTO

				doc_detallesAux2.add(d);
				// FIN VEMOS SI HAY UNA VERSION VGENTE Y PUEDE HACER PUBLICO EL
				// DOCUMENTO

			}
			// fin veremos quienes son vigente

			if (doc_detallesAux != null) {
				session.setAttribute("doc_detallesAux", doc_detallesAux2);
				session.setAttribute("doc_detallePrincipal_2",
						doc_detallePrincipal_2);

				doc_detallesAux_session = session
						.getAttribute("doc_detallesAux") != null ? (List<Doc_detalle>) session
						.getAttribute("doc_detallesAux") : null;

			} else {
				session.setAttribute("doc_detallesAux", new ArrayList());
			}

			if (treeNodoActual != null) {
				swMnuOrganizacion = true;
			}

		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc_detallesAux;
	}

	public String viewOnly() {

		session.setAttribute("doc_detalle", doc_detallePrincipal_2);
		return "editDocumentoReadOnly";
	}

	public String createDocumento() {
		return "crearDocumento";
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public boolean isSwEditar() {
		return swEditar;
	}

	public void setSwEditar(boolean swEditar) {
		this.swEditar = swEditar;
	}

	public Doc_maestro getObjeto() {
		return objeto;
	}

	public void setObjeto(Doc_maestro objeto) {
		this.objeto = objeto;
	}

	public Doc_maestro getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Doc_maestro objectItem) {
		this.objectItem = objectItem;
	}

	public int getCurrentCarIndex() {
		return currentCarIndex;
	}

	public void setCurrentCarIndex(int currentCarIndex) {
		this.currentCarIndex = currentCarIndex;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String refresh() {
		// REFRTESCAMOS EL ARBOL
		refrescaraplicacionarbol();

		super.saveDocScaneadosToArbol();
		session.removeAttribute("obtenArbolSeguridad");
		getObtenArbolSeguridad();
		return "aplicacion";
	}

	public String createTree() {

		super.setTree(this.tree);
		session.setAttribute(Utilidades.getTreeprocesando(),
				Utilidades.getTreeprocesando());
		super.setDescripcionTree(tree.getDescripcion());
		super.setSwHeredarPermisos(this.swHeredarPermisos);
		session.setAttribute("TipoNodoCrear", this.tipoNodoCrear);
		String pagIr = super.ingresarnodoRaiz_action();

		return pagIr;
	}

	public String editTree() {
		session.setAttribute(Utilidades.getTreeprocesando(),
				Utilidades.getTreeprocesando());
		super.setTree(this.tree);
		return super.saveDatosBasicos_action();
	}

	public void setAllObjectItems(List<Doc_maestro> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public String getDescripcionTree() {
		return descripcionTree;
	}

	public void setDescripcionTree(String descripcionTree) {
		this.descripcionTree = descripcionTree;
	}

	public boolean isSwInfo() {
		return swInfo;
	}

	public void setSwInfo(boolean swInfo) {
		this.swInfo = swInfo;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public String getText() {
		return text = "hola mundo";
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSwDocumento() {
		return swDocumento;
	}

	public void setSwDocumento(boolean swDocumento) {
		this.swDocumento = swDocumento;
	}

	public ServicioDelegado getDelegado() {
		return delegado;
	}

	public void setDelegado(ServicioDelegado delegado) {
		this.delegado = delegado;
	}

	public ServicioDelegadoEcological getDelegadoEcological() {
		return delegadoEcological;
	}

	public void setDelegadoEcological(
			ServicioDelegadoEcological delegadoEcological) {
		this.delegadoEcological = delegadoEcological;
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

	public boolean isToAddPrincipal() {
		return toAddPrincipal;
	}

	public void setToAddPrincipal(boolean toAddPrincipal) {
		this.toAddPrincipal = toAddPrincipal;
	}

	public boolean isToAddArea() {
		return toAddArea;
	}

	public void setToAddArea(boolean toAddArea) {
		this.toAddArea = toAddArea;
	}

	public boolean isToAddProceso() {
		return toAddProceso;
	}

	public void setToAddProceso(boolean toAddProceso) {
		this.toAddProceso = toAddProceso;
	}

	public boolean isToAddCarpeta() {
		return toAddCarpeta;
	}

	public void setToAddCarpeta(boolean toAddCarpeta) {
		this.toAddCarpeta = toAddCarpeta;
	}

	public boolean isToAddCargo() {
		return toAddCargo;
	}

	public void setToAddCargo(boolean toAddCargo) {
		this.toAddCargo = toAddCargo;
	}

	public boolean isToAddLotesDeDocumentos() {
		return toAddLotesDeDocumentos;
	}

	public void setToAddLotesDeDocumentos(boolean toAddLotesDeDocumentos) {
		this.toAddLotesDeDocumentos = toAddLotesDeDocumentos;
	}

	public boolean isToAddDocumentos() {
		return toAddDocumentos;
	}

	public void setToAddDocumentos(boolean toAddDocumentos) {
		this.toAddDocumentos = toAddDocumentos;
	}

	public boolean isToAddDocumentoSvn() {
		return toAddDocumentoSvn;
	}

	public void setToAddDocumentoSvn(boolean toAddDocumentoSvn) {
		this.toAddDocumentoSvn = toAddDocumentoSvn;
	}

	public boolean isToLoadmasivosdocumentos() {
		return toLoadmasivosdocumentos;
	}

	public void setToLoadmasivosdocumentos(boolean toLoadmasivosdocumentos) {
		this.toLoadmasivosdocumentos = toLoadmasivosdocumentos;
	}

	public boolean isSwNodoDocumento() {
		return swNodoDocumento;
	}

	public void setSwNodoDocumento(boolean swNodoDocumento) {
		this.swNodoDocumento = swNodoDocumento;
	}

	public boolean isSwNodoRaiz() {
		return swNodoRaiz;
	}

	public void setSwNodoRaiz(boolean swNodoRaiz) {
		this.swNodoRaiz = swNodoRaiz;
	}

	public boolean isSwNodoPrincipal() {
		return swNodoPrincipal;
	}

	public void setSwNodoPrincipal(boolean swNodoPrincipal) {
		this.swNodoPrincipal = swNodoPrincipal;
	}

	public boolean isSwNodoArea() {
		return swNodoArea;
	}

	public void setSwNodoArea(boolean swNodoArea) {
		this.swNodoArea = swNodoArea;
	}

	public boolean isSwNodoCargo() {
		return swNodoCargo;
	}

	public void setSwNodoCargo(boolean swNodoCargo) {
		this.swNodoCargo = swNodoCargo;
	}

	public boolean isSwNodoCarpeta() {
		return swNodoCarpeta;
	}

	public void setSwNodoCarpeta(boolean swNodoCarpeta) {
		this.swNodoCarpeta = swNodoCarpeta;
	}

	public boolean isSwNodoProceso() {
		return swNodoProceso;
	}

	public void setSwNodoProceso(boolean swNodoProceso) {
		this.swNodoProceso = swNodoProceso;
	}

	public boolean isSwMnuOrganizacion() {
		return swMnuOrganizacion;
	}

	public void setSwMnuOrganizacion(boolean swMnuOrganizacion) {
		this.swMnuOrganizacion = swMnuOrganizacion;
	}

	public boolean isSwToDel() {
		return swToDel;
	}

	public void setSwToDel(boolean swToDel) {
		this.swToDel = swToDel;
	}

	public boolean isSwDocHist() {
		return swDocHist;
	}

	public void setSwDocHist(boolean swDocHist) {
		this.swDocHist = swDocHist;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public boolean isSwHeredarPermisos() {
		return swHeredarPermisos;
	}

	public void setSwHeredarPermisos(boolean swHeredarPermisos) {
		this.swHeredarPermisos = swHeredarPermisos;
	}

	public String getTipoNodoCrear() {
		return tipoNodoCrear;
	}

	public void setTipoNodoCrear(String tipoNodoCrear) {
		this.tipoNodoCrear = tipoNodoCrear;
	}

	public String getNodoPrincipal() {
		return nodoPrincipal;
	}

	public void setNodoPrincipal(String nodoPrincipal) {
		this.nodoPrincipal = nodoPrincipal;
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

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public DualListModel<Role> getOperacionesRole() {
		return operacionesRole;
	}

	public void setOperacionesRole(DualListModel<Role> operacionesRole) {
		this.operacionesRole = operacionesRole;
	}

	public DualListModel<Operaciones> getOperaciones() {

		invisibleItemsOperaciones = new ArrayList<Operaciones>();
		visibleItemsOperaciones = new ArrayList<Operaciones>();

		if (getSelectUsuario() != null && getSelectUsuario().getValue() != null) {
			usuario = (Usuario) getSelectUsuario().getValue();
			datosCombo.llenarOperacionesVisiblesRichFaces(
					visibleItemsOperaciones, invisibleItemsOperaciones, tree,
					usuario);

		}
		// else{
		// System.out.println("--------VACIO USUARIO------------------------");
		// //System.out.println(usuario.toString());
		//
		// datosCombo.llenarOperacionesVisiblesRichFaces(
		// visibleItemsOperaciones, invisibleItemsOperaciones, tree,
		// usuario);
		// }
		operaciones = new DualListModel<Operaciones>(invisibleItemsOperaciones,
				visibleItemsOperaciones);
		return operaciones;
	}

	public void setOperaciones(DualListModel<Operaciones> operaciones) {
		this.operaciones = operaciones;
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

	public List<Operaciones> getInvisibleItemsOperaciones() {
		return invisibleItemsOperaciones;
	}

	public void setInvisibleItemsOperaciones(
			List<Operaciones> invisibleItemsOperaciones) {
		this.invisibleItemsOperaciones = invisibleItemsOperaciones;
	}

	public List<Operaciones> getVisibleItemsOperaciones() {
		return visibleItemsOperaciones;
	}

	public void setVisibleItemsOperaciones(
			List<Operaciones> visibleItemsOperaciones) {
		this.visibleItemsOperaciones = visibleItemsOperaciones;
	}

	public HtmlSelectOneMenu getSelectUsuario() {
		return selectUsuario;
	}

	public void setSelectUsuario(HtmlSelectOneMenu selectUsuario) {
		this.selectUsuario = selectUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isHeredaSeguridad() {
		return heredaSeguridad;
	}

	public void setHeredaSeguridad(boolean heredaSeguridad) {
		this.heredaSeguridad = heredaSeguridad;
	}

	public List<Doc_detalle> getMostrarListDocumentosDetalles() {
		List<Doc_maestro> docMaestros = getMostrarListDocumentos();
		List<Doc_detalle> listaDetalles = new ArrayList<Doc_detalle>();
		for (Doc_maestro dm : docMaestros) {
			List<Doc_detalle> lista = delegado.findAllDoc_Detalles(dm);
			if (lista != null && !lista.isEmpty()) {
				Doc_detalle doc = lista.get(0);
				doc.setDoc_maestro(dm);
				doc.setIcono(dm.getDoc_detalle().getIcono());
				doc.setSize_doc(dm.getDoc_detalle().getSize_doc());
				listaDetalles.add(doc);
			}
		}
		mostrarListDocumentosDetalles = listaDetalles;
		return mostrarListDocumentosDetalles;
	}

	public void setMostrarListDocumentosDetalles(
			List<Doc_detalle> mostrarListDocumentosDetalles) {
		this.mostrarListDocumentosDetalles = mostrarListDocumentosDetalles;
	}

	public ClienteSeguridad getClienteSeguridad() {
		return clienteSeguridad;
	}

	public void setClienteSeguridad(ClienteSeguridad clienteSeguridad) {
		this.clienteSeguridad = clienteSeguridad;
	}

	public Doc_detalle getDoc_detallePrincipal_2() {

		doc_detallePrincipal_2 = session.getAttribute("doc_detallePrincipal_2") != null ? (Doc_detalle) session
				.getAttribute("doc_detallePrincipal_2") : new Doc_detalle();

		return doc_detallePrincipal_2;
	}

	public void setDoc_detallePrincipal_2(Doc_detalle doc_detallePrincipal_2) {
		this.doc_detallePrincipal_2 = doc_detallePrincipal_2;
	}

	public void setDoc_detallesAux(List<Doc_detalle> doc_detallesAux) {
		this.doc_detallesAux = doc_detallesAux;
	}

	public Role getRoleParaPermisos() {
		return roleParaPermisos;
	}

	public void setRoleParaPermisos(Role roleParaPermisos) {
		this.roleParaPermisos = roleParaPermisos;
	}

	public boolean isSwPermGrupo() {
		return swPermGrupo;
	}

	public void setSwPermGrupo(boolean swPermGrupo) {
		this.swPermGrupo = swPermGrupo;
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

	public DualListModel<Usuario> getOperacionesUsuario() {
		return operacionesUsuario;
	}

	public void setOperacionesUsuario(DualListModel<Usuario> operacionesUsuario) {
		this.operacionesUsuario = operacionesUsuario;
	}

	public String saveDatosBasicos_actionRichFaces() {
		ClienteFlows clienteFlows = new ClienteFlows("vacio");

		tree = treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		clienteFlows.setTree(tree);
		clienteFlows.setUser_logueado(user_logueado);

		clienteFlows.setTipoFlow(tipoFlow);
		clienteFlows.setFlow(flow);

		// String pagIr = clienteFlows.saveDatosBasicos_actionRichFaces(
		// operacionesUsuario.getTarget(), operacionesRole.getTarget());

		// VAMOS A FLUJO PARALELO Y REFRESCAMOS TODAS LAS VARIABLES CON
		// INCIALIZA FLOWS
		// if (Utilidades.getFlowparalelo().equalsIgnoreCase(pagIr)){
		// System.out.println("inicializando para flows");
		//
		// System.out.println("inicializando para flows");
		// }
		// FIN VAMOS A FLUJO PARALELO Y REFRESCAMOS TODAS LAS VARIABLES CON
		// INCIALIZA FLOWS
		return "";// pagIr;
	}

	public List<Flow> getListaFlujosParalelos() {

		return listaFlujosParalelos;
	}

	public String editandoFlows() {

		flow = delegado.findFlow(flow);
		if (flow != null && flow.getDoc_detalle() != null) {
			Doc_detalle doc = flow.getDoc_detalle();
			doc.setOrigen(flow.getDoc_detalle().getOrigen());
			doc.setFlowEditar(flow);
			session.setAttribute("doc_detalle", doc);
			session.setAttribute("doc_maestro", doc.getDoc_maestro());
			session.setAttribute("flowParalelo", flow.getFlowParalelo());
		}
		session.setAttribute("flows", "flows");
		// INICIALIZAMOS FLOW
		String pagIr = flows();
		// FIN INICIALIZAMOS FLOW
		return pagIr;

	}

	public boolean isSwEditando() {
		return swEditando;
	}

	public void setSwEditando(boolean swEditando) {
		this.swEditando = swEditando;
	}

	public boolean isTipoFlow() {
		return tipoFlow;
	}

	public void setTipoFlow(boolean tipoFlow) {
		this.tipoFlow = tipoFlow;
	}

	public FlowParalelo getFlowParalelo() {
		return flowParalelo;
	}

	public void setFlowParalelo(FlowParalelo flowParalelo) {
		this.flowParalelo = flowParalelo;
	}

	public boolean isToPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;

	}

	public void setToPlantillaInDocFlowParalelo(
			boolean toPlantillaInDocFlowParalelo) {
		this.toPlantillaInDocFlowParalelo = toPlantillaInDocFlowParalelo;
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

	public boolean isSwTipoFormatoPlantilla() {
		return swTipoFormatoPlantilla;
	}

	public void setSwTipoFormatoPlantilla(boolean swTipoFormatoPlantilla) {
		this.swTipoFormatoPlantilla = swTipoFormatoPlantilla;
	}

	public String getAreaDocumentos() {
		return areaDocumentos;
	}

	public void setAreaDocumentos(String areaDocumentos) {
		this.areaDocumentos = areaDocumentos;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isSwFlowParaleloSession() {
		return swFlowParaleloSession;
	}

	public void setSwFlowParaleloSession(boolean swFlowParaleloSession) {
		this.swFlowParaleloSession = swFlowParaleloSession;
	}

	public Flow buscaFlowDocDetalle(Doc_detalle doc_detalle) {
		Flow f = new Flow();
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

	public boolean isSwDeshabilitarflowAprobacionParalelo() {
		return swDeshabilitarflowAprobacionParalelo;
	}

	public void setSwDeshabilitarflowAprobacionParalelo(
			boolean swDeshabilitarflowAprobacionParalelo) {
		this.swDeshabilitarflowAprobacionParalelo = swDeshabilitarflowAprobacionParalelo;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public boolean isFlowAprobacion() {
		return flowAprobacion;
	}

	public void setFlowAprobacion(boolean flowAprobacion) {
		this.flowAprobacion = flowAprobacion;
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

	public boolean isSwSolicitudimpresion() {
		return swSolicitudimpresion;
	}

	public void setSwSolicitudimpresion(boolean swSolicitudimpresion) {
		this.swSolicitudimpresion = swSolicitudimpresion;
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

	public boolean isSwMostrarListaPreceder() {
		return swMostrarListaPreceder;
	}

	public void setSwMostrarListaPreceder(boolean swMostrarListaPreceder) {
		this.swMostrarListaPreceder = swMostrarListaPreceder;
	}

	public String actionEdit() {

		System.out.println("flow.getCodigo()=" + flow.getCodigo());

		session.setAttribute("edit", flow);
		return "editFlowParalelo";
	}

	public String aceptarFlow() {
		ClienteFlows ClienteFlows = new ClienteFlows();
		ClienteFlows.setSwCrearHijoDeFlowParalelo(swCrearHijoDeFlowParalelo);
		// VEMOS SIGRABAMOS EL FLOW O GRABAMOS Y CONTINUAMOS GENERANDCO FLOWS
		String pagIr = ClienteFlows.aceptar();
		// FIN VEMOS SIGRABAMOS EL FLOW O GRABAMOS Y CONTINUAMOS GENERANDCO
		// FLOWS
		if ("flows".equalsIgnoreCase(pagIr)) {
			flow = new Flow();
			// INICIALIZAMOS PARA REALIZAR UN NUEVO FLOW DENTRO DEL FLOWPARALELO
			pagIr = flows();
			// FIN INICIALIZAMOS PARA REALIZAR UN NUEVO FLOW DENTRO DEL
			// FLOWPARALELO
			return pagIr;
		}

		return pagIr;

	}

	public void setListaFlujosParalelos(List<Flow> listaFlujosParalelos) {
		this.listaFlujosParalelos = listaFlujosParalelos;
	}

	public String listaPlantillaFlowParalelo() {
		String pagIr = "";
		// PARA FLUJOS PARALELOS-.-.. SE OIGNORA EL DOCUMENTO MAESTRO Y EL
		// DETALLLE
		// EN EL JSP, SE SELECCIONA TODOS LOS FLUJOS PARALELOS QUE PUEDE AGARRAR
		// EL USU<RIO LOGUEADO..
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		versionId();
		pagIr = Utilidades.getListarflowparalelo();

		return pagIr;

	}

	public void versionId() {
		// PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS
		session.removeAttribute("flowParalelo");
		session.setAttribute("visibleItems", new ArrayList());
		session.setAttribute("invisibleItems", new ArrayList());
		// FIN PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO
		// HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS

		Doc_maestro doc_maestro = doc_detallePrincipal_2.getDoc_maestro();

		// SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
		doc_detallePrincipal_2.setOrigen(Utilidades.getOrigenDocumentoFlow());

		// FIN SI SE HACE FLOW

		// FIN SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
		session.setAttribute("doc_detalle", doc_detallePrincipal_2);
		session.setAttribute("doc_detalleforflowplantilla",
				doc_detallePrincipal_2);
		session.setAttribute("doc_maestro", doc_maestro);

	}

	public List<Doc_detalle> getDoc_detallesAux_session() {
		doc_detallesAux_session = session.getAttribute("doc_detallesAux") != null ? (List<Doc_detalle>) session
				.getAttribute("doc_detallesAux") : null;
		if (doc_detallesAux_session == null) {
			doc_detallesAux_session = getDoc_detallesAux();
		}

		return doc_detallesAux_session;
	}

	public void setDoc_detallesAux_session(
			List<Doc_detalle> doc_detallesAux_session) {
		this.doc_detallesAux_session = doc_detallesAux_session;
	}

	public boolean isToGenerarRegistroII() {
		return toGenerarRegistroII;
	}

	public void setToGenerarRegistroII(boolean toGenerarRegistroII) {
		this.toGenerarRegistroII = toGenerarRegistroII;
	}

	public boolean isSwMoverNodo() {
		return swMoverNodo;
	}

	public void setSwMoverNodo(boolean swMoverNodo) {
		this.swMoverNodo = swMoverNodo;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
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

	public String mostrarDocEnIframe() {

		return "";
	}

	public void mostrarDocEnIframeEvent(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2verdoc");
		if (doc_detalle_ver == null || doc_detalle_ver.getCodigo() == null) {
			doc_detalle_ver = new Doc_detalle();
			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				doc_detalle_ver.setCodigo(new Long(id));
				doc_detalle_ver = delegado.findDocDetalle(doc_detalle_ver);
			}
		}
		
		if (session.getAttribute("swMostraDocIframe") != null) {
			swMostraDocIframe = (Boolean) session
					.getAttribute("swMostraDocIframe");
			session.setAttribute("swMostraDocIframe", !swMostraDocIframe);
			swMostraDocIframe = (Boolean) session
					.getAttribute("swMostraDocIframe");

		} else {
			swMostraDocIframe = true;
			session.setAttribute("swMostraDocIframe", swMostraDocIframe);
		}

		try {
			if (doc_detalle_ver == null) {
				swMostraDocIframe = false;
				session.setAttribute("swMostraDocIframe", swMostraDocIframe);

			} else {
				session.setAttribute("onlyView", "onlyView");
				session.setAttribute("objeto", doc_detalle_ver);
			}

		} catch (Exception e) {
//			FacesMessage msg = new FacesMessage(messages.getString("vuelvaIntentarlo"),  " ");
//			FacesContext.getCurrentInstance().addMessage(null, msg);

			swMostraDocIframe = false;
			session.setAttribute("swMostraDocIframe", swMostraDocIframe);
		}

	}

	public boolean isSwMostraDocIframe() {

		return swMostraDocIframe;
	}

	public void setSwMostraDocIframe(boolean swMostraDocIframe) {
		this.swMostraDocIframe = swMostraDocIframe;
	}

	public Doc_detalle getDoc_detalle_ver() {
		return doc_detalle_ver;
	}

	public void setDoc_detalle_ver(Doc_detalle doc_detalle_ver) {
		this.doc_detalle_ver = doc_detalle_ver;
	}

}
