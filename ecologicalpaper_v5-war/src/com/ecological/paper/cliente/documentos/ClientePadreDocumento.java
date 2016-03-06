/*
 *
 * Created on July 25, 2007, 11:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.documentos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.exception.GenericJDBCException;
import org.primefaces.event.FileUploadEvent;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.paper.cliente.flows.ClienteFlowsParalelo;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_consecutivo;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.PublicadosUsuComent;
import com.ecological.paper.documentos.RolesOnlyViewDocPublicados;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepcionesContextType;
import com.ecological.paper.ecologicaexcepciones.ExcepcioFlowImprimirActivo;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.subversion.Repositorio;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import com.util.file.Archivo;

/**
 * 
 * @author simon
 */
public class ClientePadreDocumento extends ContextSessionRequest {
	private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
	private HtmlDataTable myDataTable;
	private HtmlSelectOneMenu selectOneMenu1;
	private HtmlInputText name1;
	private HtmlInputText mayor_ver;
	private HtmlInputText minor_ver;
	private HtmlInputText numconsecutivo;
	private HtmlInputTextarea desc;
	private HtmlInputTextarea desc2;
	private HtmlSelectOneMenu duenio;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();
	private Object chkPublicado;
	public HttpSession session = super.getSession();
	private HttpServletRequest publicados = (HttpServletRequest) super
			.geRequest();
	public ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	public ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private Doc_maestro doc_maestro;
	private Doc_detalle doc_detalle;
	private ClienteDocumentoDetalle doc_detallePrincipal;
	private Doc_detalle doc_detallePrincipal_2;
	private Doc_maestro doc_maestroModifica;
	private Doc_detalle doc_detalleModifica;
	private Doc_detalle doc_detalle_aux;
	private List<Doc_maestro> doc_maestros;
	private Usuario user_logueado;
	private Tree treeNodoActual;
	private String directorio;
	// private DefaultMutableTreeNode root;
	private Object root;
	private boolean swPuedeRealizarFlujo;
	private boolean swVincularDocumento;
	private boolean swPublico;
	private boolean swMover;
	private boolean swRegistro;
	private boolean swFlujoActivo;
	private boolean swIsObsoleto;
	private boolean swActualizar;
	private boolean swCheckOut;
	private boolean swCopy;
	private boolean swPage;
	private boolean swCute;
	private boolean swUnlocked;
	private boolean swLocked;
	private boolean swLockedwithkey;
	private boolean swImprimir;
	private boolean publico;
	private boolean siEsVigentepuedeSerPublico;
	private String publicoStr;
	private HashMap carpetasMasivas = new HashMap();
	private List archNoCargados = new ArrayList();
	private HashMap queTipoContextType = new HashMap();
	// SEGURIDAD
	private Seguridad seguridadTree = new Seguridad();
	private Seguridad seguridadMenu = new Seguridad();
	private boolean swMod;
	private boolean swDel;
	private boolean swAdd;
	private boolean swSuperUsuario;
	private String icono;
	// esta variable se usa en editDocumento.jsp, donde se mantiene la variable
	// para solo
	// v realizar una actualizacion del propio documento o realizar una nueva
	// version del documento
	private boolean swExecuteActualizar;
	private String usuariosInFlowStr;
	private String cualquierComentario;
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private Doc_tipo doc_tipo;
	private Role roleParaPermisos;
	private Object treeData;
	// historicos
	private List<Object> hist_actuBorradors;
	private List<Object> hist_darPublicos;
	private List<Object> hist_verDetalless;
	private List<Object> hist_verVinculadoss;
	private List<Object> hist_verSometerFlows;
	private List<Object> hist_nuevaVerVigs;
	private List<Object> hist_deshNuevaVers;
	private List<Object> hist_genRegs;
	private boolean swPermGrupo;
	private boolean swHeredarPermisos;
	private boolean swDeshabiltarBtn;
	// ------------

	// seguridad historico
	private boolean swHistFlow;
	private boolean swHistDoc;
	// private String extensionesSoportadas;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private Integer docVersionMayorMasivo;
	private Integer docVersionMenorMasivo;
	private List<ClienteDocumentoDetalle> doc_detalles2 = new ArrayList<ClienteDocumentoDetalle>();
	private List<Doc_detalle> doc_detalles2_2 = new ArrayList<Doc_detalle>();
	private ClienteDocumentoDetalle clienteDocumentoDetalle;
	private List<ClienteDocumentoMaestro> clienteDocumentoMaestros;
	private boolean swHayPlantillasFlujosparalelos;
	private List<Role> roles;
	private boolean swPublicoEstaElDocumento;
	private PublicadosUsuComent publicadosUsuComent;
	private List<PublicadosUsuComent> publicadosUsuComentLst;
	private Configuracion conf = new Configuracion();
	private boolean swIsPublicador;
	private List<ClienteDocumentoMaestro> panelControl;
	private List<Doc_detalle> doc_detalles;
	private FileUploadEvent _upFile;
	private ArrayList<FileUploadEvent> filesPrimeFaces = new ArrayList<FileUploadEvent>();
	private Seguridad seguridadTreeMas = new Seguridad();
	private List<ClienteDocumentoDetalle> doc_detallesCliente;
	private List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
	// -------------------SEGURIDAD BARRA HERRAMIENTAS
	private boolean swEditar;
	private String tipoNodoCrear;
	private String text;
	private boolean swDocumento;

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
	private long tiponodo = 0;
	private boolean swMoverNodo;

	private String nodoPrincipal;
	private boolean swAttachment;
	private boolean swMostraDocIframe;
	private boolean swMostraDocIframe2;
	private String name;

	// -------------------FIN SEGURIDAD BARRA DE HERRAMIENTAS

	public ClientePadreDocumento(String vacio) {

	}

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

	/** Creates a new instance of ClientePadreDocumento */
	public ClientePadreDocumento() {

		long tiempoInicio = System.currentTimeMillis();
		session = super.getSession();
		if (session.getAttribute("clientePadreDocumento") == null) {

			session.setAttribute("clientePadreDocumento", true);
			inicializar();
			System.out
					.println("INICIALIZANDO CONSTRUCTOR clientePadreDocumento ");
		} else {
			// SI EL USUARIO QUIERE HACER UN COMENTARIO AL DOCUMENTO PUBLICO
			publicadosUsuComent = session.getAttribute("publicadosUsuComent") != null ? (PublicadosUsuComent) session
					.getAttribute("publicadosUsuComent")
					: new PublicadosUsuComent();
			System.out.println(" ya se encuentra iniializada el constructor");
		}

		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		// System.out.println("El tiempo ClientePadreDocumento  de demora es :"
		// + totalTiempo + " miliseg");
	}

	public void inicializar() {
		// session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");

		// ------------------------
		// carga docs masivos
		docVersionMayorMasivo = 1;
		docVersionMenorMasivo = 0;
		// ------------------------
		if (configuraciones == null || configuraciones.size() == 0) {
			configuraciones = delegado.find_allConfiguracion();
		}

		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);

			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		// SI EL USUARIO QUIERE HACER UN COMENTARIO AL DOCUMENTO PUBLICO
		publicadosUsuComent = session.getAttribute("publicadosUsuComent") != null ? (PublicadosUsuComent) session
				.getAttribute("publicadosUsuComent")
				: new PublicadosUsuComent();
		// FIN SI EL USUARIO QUIERE HACER UN COMENTARIO AL DOCUMENTO PUBLICO
		// por defecto es true para darle permisos al grupo al crear un
		// documento.
		swHeredarPermisos = false;

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}

		// OBTENEMOS SEGURIDAD;
		// OBTENEMOS SEGURIDAD DE DOCUMENTO ES CON TREE Y USUARIO LOGUEADO
		try {
			if (treeNodoActual != null
					&& treeNodoActual.getTiponodo() == Utilidades
							.getNodoDocumento()) {

				seguridadTree = super.getSeguridadTree(user_logueado,
						treeNodoActual);
			} else {

				seguridadTree = super.getSeguridadTree(treeNodoActual);
			}

		} catch (Exception e) {
		}

		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		// verificamos si tienes permisologia para publicar el documento
		swPublico = false;
		if (seguridadTree != null && seguridadTree.isToDoPublico()
				|| swSuperUsuario) {
			swPublico = true;
		}

		String parametro = session.getAttribute("parametro") != null ? (String) session
				.getAttribute("parametro") : "";
		if ("publico".equalsIgnoreCase(parametro)) {
			swPublicoEstaElDocumento = true;
		}

		// seguridad historico
		swHistFlow = false;
		if (seguridadTree != null && seguridadTree.isToHistFlow()
				|| swSuperUsuario) {
			swHistFlow = true;
		}

		swHistDoc = false;
		if (seguridadTree != null && seguridadTree.isToHistDoc()
				|| swSuperUsuario) {
			swHistDoc = true;
		}

		// SEGURIDAD DE LA BARRA DE HERRAMIENTAS

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

					swMnuOrganizacion = true;
				}

				// List<com.ecological.paper.historico.Historico>
				// listaNoVacia = delegado
				// .findAllHistoricoTree(null, treeNodoActual);
				// if (listaNoVacia != null && !listaNoVacia.isEmpty()) {
				// if (seguridadTree != null
				// && ((seguridadTree.isToView()) || swSuperUsuario)) {
				// setSwDocHist(true);
				//
				//
				// swMnuOrganizacion = true;
				// }
				// }

			}

			// FIN SEGURIDAD DE LA BARRA DE HERRAMIENTAS

			boolean crear = session.getAttribute("crear") != null;

			// VERIFICAMOS SI TIENE FLUJO PLANTILLAS
			List<FlowParalelo> lista = getListarParalelos() != null ? getListarParalelos()
					: new ArrayList();
			if (lista != null && lista.size() > 0) {
				swHayPlantillasFlujosparalelos = true;
			}
			// FIN VERIFICAMOS SI TIENE FLUJO PLANTILLAS

			if (crear) {
				doc_maestro = new Doc_maestro();
				doc_detalle = new Doc_detalle();
				if (user_logueado != null) {
					doc_detalle.setDuenio(user_logueado);
				}
				// version mayor y menor por defecto
				doc_detalle.setMayorVer("1");
				doc_detalle.setMinorVer("0");
			} else {
				// en caso que valla a editar o abrir documento, este es el
				// mismo
				// backing para crear,editar
				doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
						.getAttribute("doc_detalle") : new Doc_detalle();

				// obtenemos el icono---------
				doc_detalle.setIcono("");
				if (doc_detalle != null && doc_detalle.getNameFile() != null) {
					if (doc_detalle.getIcono() == null) {
						doc_detalle.setIcono(super.obtenIconoDoc(doc_detalle
								.getNameFile()));
					}

				}

				doc_detalle_aux = doc_detalle;
				session.setAttribute("doc_detalle_aux", doc_detalle_aux);
				doc_maestro = session.getAttribute("doc_maestro") != null ? (Doc_maestro) session
						.getAttribute("doc_maestro") : new Doc_maestro();

				if (doc_maestro != null && doc_maestro.getTree() != null) {
					// armamos el prefijo
					boolean nombreCorto = false;
					String prefijo = "";
					// prefijo = getPrefijo(doc_maestro.getTree(), prefijo,
					// nombreCorto);
					doc_maestro.getTree().setPrefix(prefijo);

				}

				// verificamos si podemos hacer publico el documento
				if (doc_detalle != null && doc_detalle.getCodigo() != null) {
					Doc_detalle detalleVigenteEs = null;
					detalleVigenteEs = getDocDetalleVigente(doc_detalle);
					if (detalleVigenteEs != null) {
						// puede ser publico el documento porque es vigente
						siEsVigentepuedeSerPublico = true;

					}

					// ES VARIABLE PARA MODIFICAR
					doc_maestroModifica = new Doc_maestro();
					doc_detalleModifica = new Doc_detalle();
					doc_maestroModifica.setNombre(doc_maestro.getNombre());
					doc_detalleModifica.setMayorVer(doc_detalle.getMayorVer());
					doc_detalleModifica.setMinorVer(doc_detalle.getMinorVer());
					doc_maestroModifica.setConsecutivo(doc_maestro
							.getConsecutivo());
					doc_detalleModifica.setDuenio(doc_detalle.getDuenio());
					doc_maestroModifica.setBusquedakeys(doc_maestro
							.getBusquedakeys());
					doc_detalleModifica.setDescripcion(doc_detalle
							.getDescripcion());
					doc_detalleModifica.setAreaDocumentos(doc_detalle
							.getAreaDocumentos());

				}
				// obtenemois el icono del documento
				if (doc_detalle != null
						&& !super.isEmptyOrNull(doc_detalle.getNameFile())) {
					icono = obtenIconoDoc(doc_detalle.getNameFile());
				}

				// colocamos standar de la fecha
				if (doc_maestro != null
						&& doc_maestro.getFecha_creado() != null) {
					doc_maestro.setFecha_mostrar(Utilidades.sdfShow
							.format(doc_maestro.getFecha_creado()));
				}

				// si colocamos o no el sw publiucar
				publicoEs_Doc_Detalle(doc_detalle, user_logueado);

				// VEMOS SI EL DOCUMENTO , PUEDE VER..SI ES ASI ..
				// COLOCAM,OS EL
				// ICONO MAS
				seguridadTreeMas = new Seguridad();
				seguridadTreeMas = super
						.getSeguridadTree(doc_maestro.getTree());
				//
				/*
				 * @deprected
				 */
				// publicoEs_Doc_Maestro(doc_maestro);
			}
		}
		// }
	}

	public String editDocumento() {
		session.removeAttribute("clientePadreDocumento");
		if (doc_detalle != null) {
			System.out.println("doc_detalle.getDoc_maestro().getNombre()="
					+ doc_detalle.getDoc_maestro().getNombre());
		}

		session.setAttribute("doc_detalle", doc_detalle);
		return "editDocumento";
	}

	public List<FlowParalelo> getListarParalelos() {
		List<FlowParalelo> listarParalelos = null;
		try {

			// EL CONSTRUCTOR CLIENBTEPADREDOCUMENTO.. LO LLAMAN VARIAS VECES Y
			// ESTE LLAMA
			// LISTARFLOWPARALELO CADA RATO, CON ESTO LO ARREGLAMOS
			List<FlowParalelo> listReferenciaUnavez = session
					.getAttribute("listReferenciaUnavez") != null ? (List<FlowParalelo>) session
					.getAttribute("listReferenciaUnavez") : null;
			if (listReferenciaUnavez == null) {

				treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
				doc_maestros = delegado.findAllDoc_maestros(treeNodoActual);
				Doc_maestro doc_maestro = new Doc_maestro();
				if (doc_maestros != null && doc_maestros.size() > 0) {
					doc_maestro = doc_maestros.get(0);
				}

				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				// ESTE DOC_DETALLE ES PARA OBTENER EL TIPO DE DOCUMENTO DEL
				// FLUJOPARALELO
				Doc_detalle doc_detalle = new Doc_detalle();
				doc_detalle.setDoc_maestro(doc_maestro);
				if (doc_detalle == null || doc_detalle.getCodigo() == null) {
					treeNodoActual = (Tree) session
							.getAttribute("treeNodoActual");
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

				user_logueado.setDoc_detall(doc_detalle);

				listarParalelos = delegado.findParalelos(user_logueado);
				listReferenciaUnavez = listarParalelos;
				session.setAttribute("listReferenciaUnavez", listarParalelos);
			} else {
				listarParalelos = listReferenciaUnavez;
			}
			// FIN EL CONSTRUCTOR CLIENBTEPADREDOCUMENTO.. LO LLAMAN VARIAS
			// VECES Y ESTE LLAMA
			// FIN LISTARFLOWPARALELO CADA RATO, CON ESTO LO ARREGLAMOS

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listarParalelos == null) {
			listarParalelos = new ArrayList<FlowParalelo>();
		}
		return listarParalelos;
	}

	public String cerrarVentanaViewPDF() {
		return "";
	}

	public String verDocumento() {
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context
				.getResponse();
		RequestDispatcher ir = request
				.getRequestDispatcher("/ClienteDocumentoGenerar");
		session.setAttribute("doc_detalle_id", "2");
		try {
			ir.forward(request, response);
		} catch (ServletException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return "";
	}

	public String nuevaVersionDoc() throws IOException, EcologicaExcepciones {
		String pagIr = "";

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (doc_detalle != null) {
			if (session.getAttribute("edit") != null) {
				if (super.isEmptyOrNull(doc_detalle.getDescripcion())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_camposvacios")));
					return "";
				}

				Calendar fecha = Calendar.getInstance();
				Doc_detalle docDetalle_newBorrador = new Doc_detalle();
				// PARA PUBLICAR

				docDetalle_newBorrador.setAreaDocumentos(doc_detalle
						.getAreaDocumentos());
				docDetalle_newBorrador.setPublitogrupo(doc_detalle
						.isPublitogrupo());
				docDetalle_newBorrador.setPublicador(doc_detalle
						.getPublicador());
				docDetalle_newBorrador.setFechapubli(doc_detalle
						.getFechapubli());

				boolean swSumar = true;
				docDetalle_newBorrador.setFechaexpirapubli(super
						.fechaSumarRestarDias(new Date(),
								Utilidades.getFechasumarrestardias(), swSumar));
				// FIN PARA PUBLICAR

				docDetalle_newBorrador.setDoc_maestro(doc_maestro);
				docDetalle_newBorrador.setSize_doc(doc_detalle.getSize_doc());
				docDetalle_newBorrador.setDatecambio(fecha.getTime());

				docDetalle_newBorrador.setNameFile(doc_detalle.getNameFile());
				docDetalle_newBorrador.setContextType(doc_detalle
						.getContextType());

				docDetalle_newBorrador.setData(doc_detalle.getData());
				docDetalle_newBorrador.setMayorVer(doc_detalle.getMayorVer());
				if (doc_detalle.getMinorVer() != null) {
					docDetalle_newBorrador.setMinorVer(super
							.incInUnoString(doc_detalle.getMinorVer()));
				}

				// colocamos el tipo de estado donde sen encuentra
				// actualmente
				Doc_estado borrador = new Doc_estado();
				borrador.setCodigo(Utilidades.getBorrador());
				borrador = delegado.findDocEstado(borrador);
				docDetalle_newBorrador.setDoc_estado(borrador);
				docDetalle_newBorrador.setDescripcion(doc_detalle
						.getDescripcion());
				docDetalle_newBorrador.setDoc_maestro(doc_detalle
						.getDoc_maestro());
				docDetalle_newBorrador.setDuenio(doc_detalle.getDuenio());
				if (user_logueado != null) {
					docDetalle_newBorrador.setModificadoPor(user_logueado);
				}
				// buscamoÃƒÂ§s el original detalle, antes de hacer la nueva
				// version
				doc_detalle_aux = delegado.findDetalle(doc_detalle);
				// grabamos la nueva version
				delegado.createDetalle(docDetalle_newBorrador);

				// colocamos aqui los grupos del detalle viejo al detalle nuevo
				// para publicar
				List<RolesOnlyViewDocPublicados> rolesOnlyViewDocPublicadosLst = delegado
						.findAllRolesOnlyViewDocPublicados(doc_detalle_aux);

				if (rolesOnlyViewDocPublicadosLst != null) {

					for (RolesOnlyViewDocPublicados r : rolesOnlyViewDocPublicadosLst) {

						RolesOnlyViewDocPublicados rnew = new RolesOnlyViewDocPublicados();

						rnew.setDoc_detalle(docDetalle_newBorrador);
						rnew.setRole(r.getRole());
						delegado.create(rnew);
					}

				}

				// colocamos aqui los grupos del detalle viejo al detalle nuevo
				// para publicar

				// colocamos el doc_detalle que origino el doc_borrador en
				// checkOut
				doc_detalle_aux.setDoc_checkout(true);
				delegado.editDetalle(doc_detalle_aux);
				// SI ES BD POSTGRES, ACTUALIZAMOS SU DATA EN LA TABLA
				// AUXILIAR DE POSTGRES

				if (swConfiguracionHayData) {
					if (swPostgresVieneDeConfiguracion) {
						// si es una base de datos postgres, guardamos la
						// data en otra tabla

						Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
						doc_dataPostgres = delegado
								.findDoc_dataPostgres(doc_detalle);

						Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();

						doc_dataPostgresNew
								.setData_doc_postgres(doc_dataPostgres
										.getData_doc_postgres());
						doc_dataPostgresNew
								.setDoc_detalle(docDetalle_newBorrador);
						doc_dataPostgresNew.setStatus(Utilidades.isActivo());
						delegado.create(doc_dataPostgresNew);
					}
				} else if ("1".equalsIgnoreCase(confmessages
						.getString("bdpostgres"))) {

					Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
					doc_dataPostgres = delegado
							.findDoc_dataPostgres(doc_detalle);

					Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
					doc_dataPostgresNew.setData_doc_postgres(doc_dataPostgres
							.getData_doc_postgres());
					doc_dataPostgresNew.setDoc_detalle(docDetalle_newBorrador);
					doc_dataPostgresNew.setStatus(Utilidades.isActivo());
					delegado.create(doc_dataPostgresNew);
				}

				// ____________________________________
				// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
				user_logueado = (Usuario) session.getAttribute("user_logueado");
				boolean nuevaVerVig = true;
				super.guardamosHistoricoActivoDelDocumento(user_logueado,
						docDetalle_newBorrador.getDoc_maestro(), false, false,
						false, false, false, nuevaVerVig, false, false, "");
				// ____________________________________

				// damos la seguridad, al usuario logfueado
				// clienteSeguridad.darSeguridadNodo(tree,user_logueado);
				// al duenio
				session.removeAttribute("edit");
				session.setAttribute("pagIr", Utilidades.getListarDocumentos());
				pagIr = Utilidades.getFinexitoso();
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("error_doc_edit")));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_doc_detalleflow")));
		}
		/*
		 * } else { FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage("Archivo nullo")); }
		 */
		return pagIr;
	}

	public void borrarConstraintsDetalle(Doc_detalle doc_detalle) {

		// // eliminamos todos los historicos de doc_detalle
		// List<FlowsHistorico> actualizaFlowHistoricos = delegado
		// .findAll_FlowsHistoricoDoc_detalle(doc_detalle);
		// for (FlowsHistorico fh : actualizaFlowHistoricos) {
		//
		// try {
		// // aqui se borra el flujo y el detalle de historico
		// delegado.destroy(fh);
		// } catch (EcologicaExcepciones ex) {
		// ex.printStackTrace();
		// }
		// }
		//
		// // eliminamos el flujo de doc_dfetalle
		// List<Flow> actualizaFlows = delegado
		// .findDetalleTreeHistoricoPersonalInFlow(doc_detalle);
		// for (Flow f : actualizaFlows) {
		// // Solicitudimpresion solicitudimpresion = new Solicitudimpresion
		// // ();
		// // solicitudimpresion.setFlow(f);
		// //
		// //
		// solicitudimpresion=delegado.findSolicitudimpresionByFlowDelete(solicitudimpresion);
		// // delegado.destroy(ciudad);
		// borrarFlujos(f);
		// }
		//
		// // ELIMINAMOS SI VIENE DE POSTGRES EN POSTGRES EL
		// // DETALLE
		// // SI ES BD POSTGRES
		// if (swConfiguracionHayData) {
		// if (swPostgresVieneDeConfiguracion) {
		// // BORRAMOS LA DATA VIEJA
		// Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
		// doc_dataPostgres = delegado.findDoc_dataPostgres(doc_detalle);
		// if (doc_dataPostgres != null) {
		// delegado.destroy(doc_dataPostgres);
		// }
		//
		// }
		// } else if
		// ("1".equalsIgnoreCase(confmessages.getString("bdpostgres"))) {
		// // BORRAMOS LA DATA VIEJA
		// Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
		// doc_dataPostgres = delegado.findDoc_dataPostgres(doc_detalle);
		// if (doc_dataPostgres != null) {
		// delegado.destroy(doc_dataPostgres);
		// }
		// }
		try {

			// borramos docdetalle
			delegado.destroyDetalle(doc_detalle);
		} catch (EcologicaExcepciones ex) {
			// System.out.println("ex.toString()="+ex.toString());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
	}

	public void actualizaConstraintsDetalle(Doc_detalle doc_detalle_aux,
			Doc_detalle docDetalle_newBorrador) {

		// antes de borrar el detalle old
		// actualizamos con el nuevo detalle el flow y el historico
		List<Flow> actualizaFlows = delegado
				.findDetalleTreeHistoricoPersonalInFlow(doc_detalle_aux);
		for (Flow f : actualizaFlows) {
			f.setDoc_detalle(docDetalle_newBorrador);
			try {
				delegado.edit(f);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}
		List<FlowsHistorico> actualizaFlowHistoricos = delegado
				.findAll_FlowsHistoricoDoc_detalle(doc_detalle_aux);
		for (FlowsHistorico fh : actualizaFlowHistoricos) {
			fh.setDoc_detalle(docDetalle_newBorrador);
			try {
				delegado.edit(fh);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}

	}

	public String actualizarDoc() throws IOException, EcologicaExcepciones {
		String pagIr = "";
		try {
			System.out
					.println("---------------11------------------------------------------");
			session.removeAttribute("clientePadreDocumento");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (_upFile != null) {
				if (doc_detalle != null) {
					System.out
							.println("---------------12------------------------------------------");
					if (super.isEmptyOrNull(doc_detalle.getDescripcion())) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_camposvacios")));
						return "";
					}
					// si no es valida la extension o el contextype, te genera
					// un
					// mensaje internmo y se sale

					if (!buscarContextTypeTipoArchivo(doc_detalle, "REVISAR")) {
						return "";
					}
					//
					// verificamos que si es bd postgres, no exceda los 8mb en
					// data
					if (swConfiguracionHayData) {
						if (swPostgresVieneDeConfiguracion) {
							if ((Double.compare(converBytesMbytes(new Long(
									"REVISAR")), Utilidades.getPostgresMBmax()) > 0)) {
								FacesContext
										.getCurrentInstance()
										.addMessage(
												null,
												new FacesMessage(
														messages.getString("postgmenorochomb")));
								return "";
							}
						}
					} else if ("1".equalsIgnoreCase(confmessages
							.getString("bdpostgres"))) {
						if ((Double.compare(converBytesMbytes(new Long(
								"REVISAR")), Utilidades.getPostgresMBmax()) > 0)) {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("postgmenorochomb")));
							return "";
						}
					}
					if (session.getAttribute("edit") != null) {

						Calendar fecha = Calendar.getInstance();
						Doc_detalle docDetalle_newBorrador = new Doc_detalle();
						docDetalle_newBorrador.setSize_doc(new Long("REVISAR"));
						docDetalle_newBorrador.setDatecambio(fecha.getTime());
						// docDetalle_newBorrador.setNameFile(nameFile(_upFile.getName()));
						docDetalle_newBorrador.setNameFile(doc_detalle
								.getNameFile());
						// docDetalle_newBorrador.setContextType(_upFile.getContentType());
						docDetalle_newBorrador.setContextType(doc_detalle
								.getContextType());
						Blob blob = null;// Hibernate.createBlob(_upFile
						// .getInputStream());

						docDetalle_newBorrador.setData(blob);
						docDetalle_newBorrador.setMayorVer(doc_detalle
								.getMayorVer());
						docDetalle_newBorrador.setMinorVer(doc_detalle
								.getMinorVer());

						// colocamos el tipo de estado donde sen encuentra
						// actualmente
						docDetalle_newBorrador.setDoc_estado(doc_detalle
								.getDoc_estado());
						docDetalle_newBorrador.setDescripcion(doc_detalle
								.getDescripcion());
						docDetalle_newBorrador.setDoc_maestro(doc_detalle
								.getDoc_maestro());
						docDetalle_newBorrador.setDuenio(doc_detalle
								.getDuenio());
						docDetalle_newBorrador.setAreaDocumentos(doc_detalle
								.getAreaDocumentos());
						docDetalle_newBorrador.setPublitogrupo(doc_detalle
								.isPublitogrupo());
						docDetalle_newBorrador.setFechapubli(doc_detalle
								.getFechapubli());
						docDetalle_newBorrador.setFechaexpirapubli(doc_detalle
								.getFechaexpirapubli());
						docDetalle_newBorrador.setPublicador(doc_detalle
								.getPublicador());
						// buscamoÃ§s el original detalle, antes de hacer la
						// nueva
						// version
						doc_detalle_aux = delegado.findDetalle(doc_detalle);
						// grabamos la version actualizada, lo hacemos asi
						// porque si
						// editamos.
						// la data del archivo nunca cambia :(
						delegado.createDetalle(docDetalle_newBorrador);

						// colocamos aqui los grupos del detalle viejo al
						// detalle nuevo para publicar
						List<RolesOnlyViewDocPublicados> rolesOnlyViewDocPublicadosLst = delegado
								.findAllRolesOnlyViewDocPublicados(doc_detalle_aux);
						if (rolesOnlyViewDocPublicadosLst != null) {
							for (RolesOnlyViewDocPublicados r : rolesOnlyViewDocPublicadosLst) {

								RolesOnlyViewDocPublicados rnew = new RolesOnlyViewDocPublicados();
								System.out
										.println("docDetalle_newBorrador.getCodigo()="
												+ docDetalle_newBorrador
														.getCodigo());
								rnew.setDoc_detalle(docDetalle_newBorrador);
								rnew.setRole(r.getRole());
								delegado.create(rnew);
							}
						}
						// colocamos aqui los grupos del detalle viejo al
						// detalle nuevo para publicar

						actualizaConstraintsDetalle(doc_detalle_aux,
								docDetalle_newBorrador);
						// _______________________________________________________________________
						// SI ES BD POSTGRES, ACTUALIZAMOS SU DATA EN LA TABLA
						// AUXILIAR DE POSTGRES
						if (swConfiguracionHayData) {
							if (swPostgresVieneDeConfiguracion) {
								// BORRAMOS LA DATA VIEJA
								Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
								doc_dataPostgres = delegado
										.findDoc_dataPostgres(doc_detalle_aux);
								if (doc_dataPostgres != null) {
									delegado.destroy(doc_dataPostgres);
								}
								// GRABAMOS LA DATA NUEVA
								Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
								doc_dataPostgresNew.setData_doc_postgres(null);
								doc_dataPostgresNew
										.setDoc_detalle(docDetalle_newBorrador);
								doc_dataPostgresNew.setStatus(Utilidades
										.isActivo());
								delegado.create(doc_dataPostgresNew);
							}
						} else if ("1".equalsIgnoreCase(confmessages
								.getString("bdpostgres"))) {
							// BORRAMOS LA DATA VIEJA
							Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
							doc_dataPostgres = delegado
									.findDoc_dataPostgres(doc_detalle_aux);
							if (doc_dataPostgres != null) {
								delegado.destroy(doc_dataPostgres);
							}
							// GRABAMOS LA DATA NUEVA
							Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
							doc_dataPostgresNew.setData_doc_postgres(null);
							doc_dataPostgresNew
									.setDoc_detalle(docDetalle_newBorrador);
							doc_dataPostgresNew
									.setStatus(Utilidades.isActivo());
							delegado.create(doc_dataPostgresNew);
						}
						// _______________________________________________________________________

						// Ahora si procedemos aborrarlo
						// borramos la que era original
						delegado.destroyDetalle(doc_detalle_aux);

						// ____________________________________
						session.removeAttribute("edit");

						// ____________________________________
						// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
						user_logueado = (Usuario) session
								.getAttribute("user_logueado");
						boolean actualizarBorrador = true;
						super.guardamosHistoricoActivoDelDocumento(
								user_logueado,
								docDetalle_newBorrador.getDoc_maestro(),
								actualizarBorrador, false, false, false, false,
								false, false, false, "");
						// ____________________________________

						session.setAttribute("pagIr",
								Utilidades.getListarDocumentos());
						pagIr = Utilidades.getFinexitoso();
					} else {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_doc_edit")));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_doc_detalleflow")));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Archivo nullo"));
			}
		} catch (Exception e) {
			redirect("ClientePadreDocumento", "actualizarDoc", e);
		}
		return pagIr;
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

	// ________________________________________________________________________
	// It is also possible to filter the list of returned files.
	// This example does not return any files that start with `.'.
	FilenameFilter filter = new FilenameFilter() {

		public boolean accept(File dir, String name) {
			return !name.startsWith(".");
		}
	};

	public Tree encontrarPadreCarpetaPC(String ruta) {
		StringTokenizer str = new StringTokenizer(ruta.toString(),
				File.separator);
		// colocar alrevez la cadena
		boolean swContinue = true;
		String[] rutaAlrevez = new String[str.countTokens()];
		int i = 0;
		while (str.hasMoreTokens()) {
			rutaAlrevez[i++] = (String) str.nextToken();
		}

		int k = rutaAlrevez.length - 1;
		for (int j = 0; j < rutaAlrevez.length; j++) {
			String carpeta = rutaAlrevez[k--];
			Tree carpetaTree = (Tree) carpetasMasivas.get(carpeta.toString());
			if (carpetaTree != null && carpetaTree instanceof Tree) {
				return carpetaTree;
			}

		}
		return null;

	}

	// Process all files and directories under dir
	public void visitAllDirsAndFiles(File dir) {
		// process(dir);
		if (dir.isDirectory()) {
			Tree carpeta = (Tree) carpetasMasivas.get(dir.getName());
			if (carpeta == null) {
				carpeta = new Tree();
				carpeta.setDescripcion(dir.getName());
				carpeta.setFecha_creado(new Date());
				carpeta.setMaquina(super.getMaquinaConectada());
				carpeta.setNombre(dir.getName());
				carpeta.setPrefix(dir.getName());
				carpeta.setTiponodo(Utilidades.getNodoCarpeta());
				Tree padre = encontrarPadreCarpetaPC(dir.getPath());
				if (padre == null) {
					carpeta.setNodopadre(treeNodoActual.getNodo());
				} else {
					carpeta.setNodopadre(padre.getNodo());
				}

				/*
				 * List treeExiste = delegado.finTreeByName(carpeta);
				 * StringBuffer noSoporta=new StringBuffer(""); if
				 * (!treeExiste.isEmpty()){ if
				 * (!validaHijosMismoNombreError(padre,carpeta)){
				 */
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				delegado.crearNuevoTree(carpeta, user_logueado);
				carpetasMasivas.put(carpeta.getNombre(), carpeta);
				/*
				 * }else{
				 * noSoporta.append(messages.getString("error_tree_existe"
				 * )).append("=").append(carpeta.getNombre());
				 * archNoCargados.add(noSoporta.toString()); } }else{
				 * noSoporta.append
				 * (messages.getString("error_tree_existe")).append
				 * ("=").append(carpeta.getNombre());
				 * archNoCargados.add(noSoporta.toString()); }
				 */

			}

		} else {
			// CARGAMOS EL ARCHIVO EN EL DIRECTORIO PADRE
			Tree padre = encontrarPadreCarpetaPC(dir.getPath());
			if (padre == null) {
				padre = new Tree();
				padre.setNodopadre(treeNodoActual.getNodo());
			} else {
				padre.setNodopadre(padre.getNodo());
			}
			File file = dir;
			try {

				// verificamois que si es postgres , el archivo no sea mayor
				// a8mg
				boolean swPostgres = false;
				boolean swPostgresMayorMb = false;
				if (swConfiguracionHayData) {
					if (swPostgresVieneDeConfiguracion) {
						// si es una base de datos postgres, guardamos la
						// data en otra
						// tabla
						swPostgres = true;
					}
				} else if ("1".equalsIgnoreCase(confmessages
						.getString("bdpostgres"))) {
					// si es una base de datos postgres, guardamos la data
					// en otra tabla
					swPostgres = true;
				}

				if ((Double.compare(converBytesMbytes(file.length()),
						Utilidades.getPostgresMBmax()) > 0) && swPostgres) {
					swPostgresMayorMb = true;

				}
				String ext = file.getName().substring(
						file.getName().lastIndexOf(".") + 1,
						file.getName().length());
				// /ubicamos en la posicion 1 el tipo de contexto
				// verificamois que si es postgres , el archivo no sea mayor
				// a8mg

				if (!swPostgresMayorMb
						&& buscarContextTypeTipoArchivo(doc_detalle,
								file.getName())) {

					uploadMasivoArch(padre, file, doc_maestro, doc_detalle);

				} else {
					if (swPostgresMayorMb) {
						StringBuffer noSoporta = new StringBuffer("");
						noSoporta.append(messages.getString("doc_file"))
								.append("=").append(file.getName());
						noSoporta.append("ext=").append(ext);
						noSoporta
								.append(messages.getString("postgmenorochomb"));
						archNoCargados.add(noSoporta.toString());
					} else {

						// RCHIVO NO SOPORTADO
						StringBuffer noSoporta = new StringBuffer("");
						noSoporta.append(messages.getString("doc_file"))
								.append("=").append(file.getName());
						noSoporta.append("ext=").append(ext);
						archNoCargados.add(noSoporta.toString());
					}

				}

			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

		}

		if (dir.isDirectory()) {
			String[] children = dir.list(filter);
			for (int i = 0; i < children.length; i++) {
				visitAllDirsAndFiles(new File(dir, children[i]));
			}
		}
	}

	public String uploadMasivos() throws IOException, EcologicaExcepciones {
		String pagIr = "";
		File dir = new File(getDirectorio());
		try {
			carpetasMasivas = new HashMap();
			Tree carpetaPadre = treeNodoActual = (Tree) session
					.getAttribute("treeNodoActual");
			// se trabaja por nombre
			carpetasMasivas.put(carpetaPadre.getNombre(), carpetaPadre);
			boolean isDir = dir.isDirectory();
			if (isDir) {
				// procesamos
				visitAllDirsAndFiles(dir);
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("archNoCargados", archNoCargados);
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("doc_ruta_error")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}

		return pagIr;
	}

	public void uploadMasivoArch(Tree padre, File file, Doc_maestro maestro,
			Doc_detalle detalle) throws IOException, EcologicaExcepciones {
		Doc_detalle doc_detalle = new Doc_detalle(detalle);

		Doc_maestro doc_maestro = new Doc_maestro(maestro);
		Tree tree = new Tree();
		Calendar fecha = Calendar.getInstance();
		tree.setNodopadre(padre.getNodo());
		tree.setTiponodo(Utilidades.getNodoDocumento());

		tree.setFecha_creado(fecha.getTime());
		tree.setMaquina(super.getMaquinaConectada());
		// _____________________________________________//
		doc_detalle.setNameFile(nameFile(file.getName()));
		doc_maestro.setNombre(sacarSlashFiltro(doc_detalle.getNameFile()));
		doc_maestro.setBusquedakeys(getWordKeys("", file.getName()));
		// grabnamos el tree nuevo
		tree.setDescripcion(doc_detalle.getDescripcion());
		tree.setNombre(doc_maestro.getNombre());

		// _____________________________________________//
		doc_maestro.setConsecutivo(numSacopDo("", doc_maestro.getConsecutivo(),
				Utilidades.getConsecutivoLength()));
		// sumamos en un el consecutivo
		maestro.setConsecutivo(incInUnoString(maestro.getConsecutivo()));

		if ((doc_detalle.getDoc_estado().getCodigo().equals(Utilidades
				.getBorrador())) && (doc_maestro.isPublico())) {
			// para ser publico, el documento debe ser vignte
			doc_maestro.setPublico(false);
		}

		doc_detalle.setSize_doc(file.length());
		doc_detalle.setDatecambio(fecha.getTime());
		doc_detalle.setDoc_maestro(doc_maestro);

		boolean swPostgres = false;
		if (swConfiguracionHayData) {
			if (swPostgresVieneDeConfiguracion) {
				// si es una base de datos postgres, guardamos la data en otra
				// tabla
				swPostgres = true;
			}
		} else if ("1".equalsIgnoreCase(confmessages.getString("bdpostgres"))) {
			// si es una base de datos postgres, guardamos la data en otra tabla
			swPostgres = true;
		}

		// ________data del archivo_________________
		FileInputStream fileinputStream;
		Blob blob = null;
		try {
			fileinputStream = new FileInputStream(file);

			try {
				if (!swPostgres) {

					// ORACLE Y SQL SERVER
					blob = Hibernate.createBlob(fileinputStream);
					doc_detalle.setData(blob);

				}

				// fileinputStream.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		// ________fin de data del archivo_________________

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		delegado.crearNuevoTree(tree, user_logueado);
		doc_maestro.setTree(tree);

		doc_maestro.setUsuario_creador(user_logueado);
		if (doc_maestro.getFecha_creado() == null) {
			doc_maestro.setFecha_creado(fecha.getTime());
		}
		doc_detalle.setModificadoPor(doc_detalle.getDuenio());
		if (doc_detalle.getMayorVer() == null) {
			doc_detalle.setMayorVer("1");
		}
		if (doc_detalle.getMinorVer() == null) {
			doc_detalle.setMinorVer("0");
		}

		delegado.createMaestro(doc_maestro);
		delegado.createDetalle(doc_detalle);

		if (swPostgres) {
			// POSTGRES SQL
			// EN CASO QIUE VENBGA PARA POSTGRES CONFIGURADO EN BASE DE DATOS O
			// ARCHIVO CONF
			int length = (int) file.length();
			byte[] bytes = new byte[length];
			fileinputStream = new FileInputStream(file);
			fileinputStream.read(bytes);
			fileinputStream.close();
			// si es una base de datos postgres, guardamos la data en otra tabla
			Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
			doc_dataPostgres.setData_doc_postgres(bytes);
			doc_dataPostgres.setDoc_detalle(doc_detalle);
			doc_dataPostgres.setStatus(Utilidades.isActivo());
			delegado.create(doc_dataPostgres);

		}

		// FIN DE POSTGRES
		// permisologia patra el usuario logueado y el diuenio
		Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
		seguridad_User_Lineal.setUsuario(user_logueado);
		seguridad_User_Lineal.setTree(tree);
		clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

		seguridad_User_Lineal = new Seguridad_User_Lineal();
		seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
		seguridad_User_Lineal.setTree(tree);
		clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
	}

	public boolean buscarContextTypeTipoArchivo(Doc_detalle doc_detall,
			String nombreFile) {
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
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("doc_contexttype")
									+ ":"
									+ (_upFile != null ? null : "")));
				}
				// doc_detalle.setContextType(_upFile.getContentType());
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("doc_ext") + ":"
								+ (ext != null ? ext : "")));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("doc_ext") + ":"
							+ (nomArch != null ? nomArch : "")));
		}
		return tipoArchValido;
	}

	// ________________________________________________________________________
	public String upload() throws IOException, EcologicaExcepciones {
		String pagIr = "";
		File _upFileFile = null;
		//
		try {
			String nomArch = "";
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (_upFile != null) {
				Archivo archivo = new Archivo();

				nomArch = sacarSlashFiltro("REVISAR");

				if (nomArch.lastIndexOf(".") != -1) {
					String ext = nomArch.substring(
							nomArch.lastIndexOf(".") + 1, nomArch.length());
					String nom2 = nomArch.substring(0,
							nomArch.lastIndexOf(".") - 1);
					_upFileFile = archivo.fileDesdeStream(null, nom2, ext);

				} else {
					throw new EcologicaExcepcionesContextType(
							(messages.getString("doc_ext") + ":" + (nomArch != null ? nomArch
									: "")));
				}

				Tree tree = new Tree();
				Calendar fecha = Calendar.getInstance();
				tree.setNodopadre(treeNodoActual.getNodo());
				// si es editar, no va hacer getNodoDocumento para que no se vea
				// reflejado en el aire
				if (session.getAttribute("edit") != null) {
					tree.setTiponodo(Utilidades.getVersionnodoDocumento());
				} else {
					tree.setTiponodo(Utilidades.getNodoDocumento());
				}

				// tree.setFecha_creado(fecha.getTime());
				Date date = new Date();

				tree.setFecha_creado(date);
				tree.setMaquina(super.getMaquinaConectada());
				// a que empresa pertenece el dopcumento
				if (user_logueado != null) {
					doc_maestro.setEmpresa(user_logueado.getEmpresa());
				}
				// _____________________________________________//
				// grabnamos el tree nuevo
				tree.setDescripcion(doc_detalle.getDescripcion());
				if (isEmptyOrNull(doc_maestro.getNombre())) {
					String nombreArch = nomArch.substring(0,
							nomArch.lastIndexOf("."));
					doc_maestro.setNombre(sacarSlashFiltro(nombreArch));

				}
				tree.setNombre(doc_maestro.getNombre());

				// _____________________________________________//
				if (treeNodoActual == null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("error_tree")));
					return "";
				}

				if ((doc_detalle.getDoc_estado().getCodigo().equals(Utilidades
						.getBorrador())) && (doc_maestro.isPublico())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_docpublico")));
					return "";
				}

				doc_maestro.setConsecutivo(numSacopDo("",
						doc_maestro.getConsecutivo(),
						Utilidades.getConsecutivoLength()));

				Map llenarSessiones = new HashMap();

				delegadoEcological.upload(tree, doc_maestro, doc_detalle,
						_upFileFile, user_logueado, "REVISAR", llenarSessiones);

				if (llenarSessiones.containsKey("conecutivo1")) {
					String consecutivo = (String) llenarSessiones
							.get("conecutivo1");
					session.setAttribute("conecutivo1", consecutivo);
				}
				if (llenarSessiones.containsKey("conecutivo2")) {
					String consecutivo = (String) llenarSessiones
							.get("conecutivo2");
					session.setAttribute("conecutivo2", consecutivo);
				}

				// ________________________________________________________________________________
				// damos permisos al role que se escojio
				if (roleParaPermisos != null && swPermGrupo) {

					Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
					seguridad_Role_Lineal.setRole(roleParaPermisos);
					seguridad_Role_Lineal.setTree(tree);
					// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU
					// SEGURIDA
					List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
							.findAllSeguridad_Role_Identificador(roleParaPermisos);
					if (seguridad_Role_LinealList != null
							&& !seguridad_Role_LinealList.isEmpty()) {
						seguridad_Role_Lineal = seguridad_Role_LinealList
								.get(0);
						seguridad_Role_Lineal.setRole(roleParaPermisos);
						seguridad_Role_Lineal.setTree(tree);
					}
					// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
					// para que sea totalmente nuevo y no traiga el primary key
					// viejo

					Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
							seguridad_Role_Lineal);

					delegado.create(seguridad_Role_Lineal2);
					//givePermparaverToGroup(seguridad_Role_Lineal2);
					// ________________________________________________________________________________
				}

				// permisologia patra el usuario logueado y el diuenio
				// permisologia patra el usuario logueado y el diuenio
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				seguridad_User_Lineal.setUsuario(user_logueado);
				seguridad_User_Lineal.setTree(tree);
				seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
				clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

				seguridad_User_Lineal = new Seguridad_User_Lineal();
				seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
				seguridad_User_Lineal.setTree(tree);
				seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
				clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());
				// }
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Archivo nullo"));
			}

		}

		catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();

		} catch (EcologicaExcepcionesContextType e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}

		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("bdpostgresdefault")
							+ e));
			e.printStackTrace();

		} finally {
			if (_upFileFile != null) {
				_upFileFile.delete();
			}
		}

		return pagIr;
	}

	public String uploadRichFaces(File _upFileFile, String nomArch,
			String contextType) throws IOException, EcologicaExcepciones {
		String pagIr = "";

		//
		try {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (_upFileFile != null) {

				Tree tree = new Tree();
				Calendar fecha = Calendar.getInstance();
				tree.setNodopadre(treeNodoActual.getNodo());
				// si es editar, no va hacer getNodoDocumento para que no se vea
				// reflejado en el aire
				if (session.getAttribute("edit") != null) {
					tree.setTiponodo(Utilidades.getVersionnodoDocumento());
				} else {
					tree.setTiponodo(Utilidades.getNodoDocumento());
				}

				// tree.setFecha_creado(fecha.getTime());
				Date date = new Date();

				tree.setFecha_creado(date);
				tree.setMaquina(super.getMaquinaConectada());
				// a que empresa pertenece el dopcumento
				if (user_logueado != null) {
					doc_maestro.setEmpresa(user_logueado.getEmpresa());
				} else {
					user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
							.getAttribute("user_logueado") : null;
					doc_maestro.setEmpresa(user_logueado.getEmpresa());
				}
				// _____________________________________________//
				// grabnamos el tree nuevo
				tree.setDescripcion(doc_detalle.getDescripcion());
				if (isEmptyOrNull(doc_maestro.getNombre())) {

					doc_maestro.setNombre(sacarSlashFiltro(nomArch));

				}
				tree.setNombre(doc_maestro.getNombre());

				// _____________________________________________//
				if (treeNodoActual == null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("error_tree")));
					return "";
				}

				if ((doc_detalle.getDoc_estado().getCodigo().equals(Utilidades
						.getBorrador())) && (doc_maestro.isPublico())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_docpublico")));
					return "";
				}

				doc_maestro.setConsecutivo(numSacopDo("",
						doc_maestro.getConsecutivo(),
						Utilidades.getConsecutivoLength()));

				Map llenarSessiones = new HashMap();

				delegadoEcological.upload(tree, doc_maestro, doc_detalle,
						_upFileFile, user_logueado, contextType,
						llenarSessiones);

				if (llenarSessiones.containsKey("conecutivo1")) {
					String consecutivo = (String) llenarSessiones
							.get("conecutivo1");
					session.setAttribute("conecutivo1", consecutivo);
				}
				if (llenarSessiones.containsKey("conecutivo2")) {
					String consecutivo = (String) llenarSessiones
							.get("conecutivo2");
					session.setAttribute("conecutivo2", consecutivo);
				}

				//INICIO ESTOS PERMISOS SE COLOCAN DE PRIMERO PARA DARLE PRIORIDAD QUE A LAS PERSONAS QUE ESTAN EN LOS ROLES
				// permisologia patra el usuario logueado y el diuenio
				// permisologia patra el usuario logueado y el diuenio
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				seguridad_User_Lineal.setUsuario(user_logueado);
				seguridad_User_Lineal.setTree(tree);
				seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
				clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

				seguridad_User_Lineal = new Seguridad_User_Lineal();
				seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
				seguridad_User_Lineal.setTree(tree);
				seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
				clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
				//FIN ESTOS PERMISOS SE COLOCAN DE PRIMERO PARA DARLE PRIORIDAD QUE A LAS PERSONAS QUE ESTAN EN LOS ROLES
				
				// ________________________________________________________________________________
				// damos permisos al role que se escojio
				if (roleParaPermisos != null && swPermGrupo) {

					Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
					seguridad_Role_Lineal.setRole(roleParaPermisos);
					seguridad_Role_Lineal.setTree(tree);
					// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU
					// SEGURIDA
					List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
							.findAllSeguridad_Role_Identificador(roleParaPermisos);
					if (seguridad_Role_LinealList != null
							&& !seguridad_Role_LinealList.isEmpty()) {
						seguridad_Role_Lineal = seguridad_Role_LinealList
								.get(0);
						seguridad_Role_Lineal.setRole(roleParaPermisos);
						seguridad_Role_Lineal.setTree(tree);
					}
					// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
					// para que sea totalmente nuevo y no traiga el primary key
					// viejo

					Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
							seguridad_Role_Lineal);

					delegado.create(seguridad_Role_Lineal2);
				//	givePermparaverToGroup(seguridad_Role_Lineal2);
					// ________________________________________________________________________________
				}

			

				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());
				// }
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Archivo nullo"));
			}

		}

		catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();

		} catch (EcologicaExcepcionesContextType e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}

		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("bdpostgresdefault")
							+ e));
			e.printStackTrace();

		} finally {
			if (_upFileFile != null) {
				_upFileFile.delete();
			}
		}

		return pagIr;
	}

	public boolean isUploaded() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getApplicationMap()
				.get("fileupload_bytes") != null;
	}

	public String eliminarBtn() {
		super.clearSession(session, confmessages.getString("usuarioSession"));
		return "menu";
	}

	public String cancelarLista() {

		// public void versionId(ActionEvent event) {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClientePadreDocumento", "cancelarLista", e);
		}

		return "menu";
	}

	public String diferenciaentreversiones() {
		int soloDosFiles = 0;
		for (ClienteDocumentoMaestro dataItem : clienteDocumentoMaestros) {
			Collection<ClienteDocumentoDetalle> detalles = dataItem
					.getDoc_detallesCliente();
			Iterator it = detalles.iterator();
			while (it.hasNext()) {
				clienteDocumentoDetalle = (ClienteDocumentoDetalle) it.next();
				try {
					if (selectedIds.get(clienteDocumentoDetalle.getCodigo())
							.booleanValue()) {

						++soloDosFiles;

						doc_detalle = new Doc_detalle();
						// buscamos el role para editar
						if (clienteDocumentoDetalle.getCodigo() >= 0) {
							doc_detalle.setCodigo(new Long(
									clienteDocumentoDetalle.getCodigo()));

							doc_detalle = delegado.findDetalle(doc_detalle);
						}
						if (doc_detalle != null) {
							doc_maestro = doc_detalle.getDoc_maestro();
							if (soloDosFiles == 1) {
								session.setAttribute("doc_detalle1",
										doc_detalle);
								session.setAttribute("doc_maestro1",
										doc_maestro);
							} else if (soloDosFiles == 2) {
								session.setAttribute("doc_detalle2",
										doc_detalle);
								session.setAttribute("doc_maestro2",
										doc_maestro);
							}
						}

					}

				} catch (NullPointerException e) {
					// System.out.println("no pasa nada");
					// TODO: handle exception
				}

			}

		}
		if (soloDosFiles == 2) {
			return "viewDiferencia";
		} else {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages
									.getString("selecciondosarchivos")));
			return "";
		}

	}

	public String cancelarCrear() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClientePadreDocumento", "cancelarCrear", e);
		}

		return "menu";
	}

	public String regresarFlowResponse() {
		return "finexitoso";
	}

	public String cancelar() {
		try {
			session.removeAttribute("clientePadreDocumento");
			super.clearSession(session,
					confmessages.getString("usuarioSession")
							+ ",doc_detalle,doc_maestro,treeNodoActual");

		} catch (Exception e) {
			redirect("ClientePadreDocumento", "cancelar", e);
		}

		return "listar";
	}

	public String putPublico() {

		refrescaraplicaciondetalles();

		String pagIr = "";
		List<Role> roleSeleccionados = new ArrayList<Role>();
		for (Role obj : roles) {
			if ((selectedIds != null)
					&& (selectedIds.get(obj.getCodigo()) != null)) {
				if (selectedIds.get(obj.getCodigo()).booleanValue()) {
					roleSeleccionados.add(obj);
				}
			}
		}

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// CON EL PUBLICO ME ENCUENTRA EL DOC VIGENTE
		String publico = "publico";
		// fin CON EL PUBLICO ME ENCUENTRA EL DOC VIGENTE
		List<Doc_detalle> doc_detalleVigente = (List<Doc_detalle>) delegado
				.encontrarDetalles(doc_detalle.getDoc_maestro().getCodigo(),
						publico);

		if (doc_detalleVigente != null && !doc_detalleVigente.isEmpty()
				&& doc_detalleVigente.size() > 0) {

			try {

				delegado.putPublicoDocumento(doc_detalle, roleSeleccionados,
						user_logueado);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			session.setAttribute("pagIr", Utilidades.getListarDocumentos());
			session.setAttribute(Utilidades.getNoclearsession(), "");
			pagIr = Utilidades.getFinexitoso();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_docpublico")));
			return "";
		}

		return pagIr;
	}

	public void versionIdIsPublico(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdPublic2");
		UIParameter component2 = (UIParameter) event.getComponent()
				.findComponent("editIdPublic2String");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			Doc_maestro doc_maestroGrabar = new Doc_maestro();
			// buscamos el role para editar
			if (id >= 0) {

				doc_maestroGrabar.setCodigo(new Long(id));
				doc_maestroGrabar = delegado.findMaestro(doc_maestroGrabar);
				doc_maestroGrabar.setPublico(doc_maestro.isPublico());
				try {
					delegado.editMaestro(doc_maestroGrabar);
					publico = doc_maestroGrabar.isPublico();

					if (doc_maestroGrabar.isPublico()) {
						publicoStr = messages.getString("doc_publico");

					} else {
						publicoStr = messages.getString("doc_publico");
						// publicoStr = messages
						// .getString("doc_publicarDocumento");
					}

					// ______________________________________________________
					// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					boolean darPublico = true;
					super.guardamosHistoricoActivoDelDocumento(user_logueado,
							doc_maestroGrabar, false, darPublico, false, false,
							false, false, false, false, publicoStr);
					// ______________________________________________________

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
				} catch (EcologicaExcepciones ex) {
					ex.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("error")));
				}

			}
		}
	}

	public String modificarDocumento() {
		String pagIr = "";
		boolean swConsecutivoRepite = false;
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		try {

			if (getSelectOneMenu1() != null) {
				if (getSelectOneMenu1().getValue() != null) {
					doc_tipo = (Doc_tipo) getSelectOneMenu1().getValue();
					doc_maestro.setDoc_tipo(doc_tipo);
				}
			}
			if (getName1() != null) {
				if (getName1().getValue() != null) {
					String name1 = (String) getName1().getValue();
					doc_maestro.setNombre(name1.trim());
				}
			}
			if (getMayor_ver() != null) {
				if (getMayor_ver().getValue() != null) {
					String mayorVer = (String) getMayor_ver().getValue();
					doc_detalle.setMayorVer(mayorVer.trim());
				}
			}

			if (getMinor_ver() != null) {
				if (getMinor_ver().getValue() != null) {
					String minorVer = (String) getMinor_ver().getValue();
					doc_detalle.setMinorVer(minorVer.trim());
				}
			}

			if (getNumconsecutivo() != null) {
				if (getNumconsecutivo().getValue() != null) {
					String numConsec = "";
					numConsec = (String) getNumconsecutivo().getValue();
					numConsec = numConsec.trim();
					numConsec = super.numSacopDo("", numConsec.trim(),
							Utilidades.getConsecutivoLength());

					// si el consecutivo no es nulo, y no es el mismo al que
					// trae el documento
					if (!super.isEmptyOrNull(numConsec)) {
						// chequeamos que en el propio arbol o registros del
						// arbol no esten repetidos
						doc_maestro.setConsecutivo(numConsec.trim());
						List<Doc_maestro> doc_maestros = delegado
								.consecutivoseRepite(doc_maestro,
										treeNodoActual);

						if (doc_maestros != null && !doc_maestros.isEmpty()
								&& doc_maestros.size() > 0) {
							swConsecutivoRepite = true;
							// return "";
						} else {
							doc_maestro.setConsecutivo(numConsec.trim());
						}
					}

				}
			}

			if (getDesc() != null) {
				if (getDesc().getValue() != null) {
					String Descr = (String) getDesc().getValue();
					doc_maestro.setBusquedakeys(Descr.trim());
				}
			}

			if (getDesc2() != null) {
				if (getDesc2().getValue() != null) {
					String Descr2 = (String) getDesc2().getValue();
					doc_detalle.setDescripcion(Descr2.trim());
				}
			}

			if (getDuenio() != null) {
				if (getDuenio().getValue() != null) {
					Usuario duenio2 = (Usuario) getDuenio().getValue();
					doc_detalle.setDuenio(duenio2);
				}
			}

			if (swConsecutivoRepite) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_consecutivo")));
			} else {
				doc_maestro.getTree().setNombre(doc_maestro.getNombre());
				doc_maestro.getTree().setDescripcion(doc_maestro.getNombre());
				doc_detalle.setAreaDocumentos(doc_detalleModifica
						.getAreaDocumentos());
				delegado.edit(doc_maestro.getTree());
				delegado.editMaestro(doc_maestro);
				delegado.editDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
				if (doc_maestro.isPublico()) {
					// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					boolean darPublico = true;
					super.guardamosHistoricoActivoDelDocumento(user_logueado,
							doc_maestro, false, darPublico, false, false,
							false, false, false, false, publicoStr);
					// ______________________________________________________
				}
				if (doc_maestro != null && doc_maestro.getTree() != null) {

					Tree tree = doc_maestro.getTree();
					// ________________________________________________________________________________
					// damos permisos al role que se escojio
					if (roleParaPermisos != null && swPermGrupo) {
						Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
						seguridad_Role_Lineal.setRole(roleParaPermisos);
						seguridad_Role_Lineal.setTree(tree);
						// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
						// BUSCAMOS SU
						// SEGURIDA
						List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
								.findAllSeguridad_Role_Identificador(roleParaPermisos);
						if (seguridad_Role_LinealList != null
								&& !seguridad_Role_LinealList.isEmpty()) {
							seguridad_Role_Lineal = seguridad_Role_LinealList
									.get(0);
							seguridad_Role_Lineal.setRole(roleParaPermisos);
							seguridad_Role_Lineal.setTree(tree);
						}
						// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
						// para que sea totalmente nuevo y no traiga el primary
						// key
						// viejo
						Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
								seguridad_Role_Lineal);
						delegado.create(seguridad_Role_Lineal2);
					//	givePermparaverToGroup(seguridad_Role_Lineal2);
						// ________________________________________________________________________________
					}

					// permisologia patra el usuario logueado y el diuenio
					// permisologia patra el usuario logueado y el diuenio
					Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(user_logueado);
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

					seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
				}
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());

				session.setAttribute("pagIr", Utilidades.getListarDocumentos());
				pagIr = Utilidades.getFinexitoso();
			}
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public String modificarDocumentoRichFaces() {
		String pagIr = "";
		boolean swConsecutivoRepite = false;
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		try {

			if (doc_maestro.getConsecutivo() != null) {
				if (doc_maestro.getConsecutivo() != null) {
					String numConsec = "";
					numConsec = (String) doc_maestro.getConsecutivo();
					numConsec = numConsec.trim();
					numConsec = super.numSacopDo("", numConsec.trim(),
							Utilidades.getConsecutivoLength());

					// si el consecutivo no es nulo, y no es el mismo al que
					// trae el documento
					if (!super.isEmptyOrNull(numConsec)) {
						// chequeamos que en el propio arbol o registros del
						// arbol no esten repetidos
						doc_maestro.setConsecutivo(numConsec.trim());
						List<Doc_maestro> doc_maestros = delegado
								.consecutivoseRepite(doc_maestro,
										treeNodoActual);

						if (doc_maestros != null && !doc_maestros.isEmpty()
								&& doc_maestros.size() > 0) {
							swConsecutivoRepite = true;
							// return "";
						} else {
							doc_maestro.setConsecutivo(numConsec.trim());
						}
					}

				}
			}

			if (swConsecutivoRepite) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_consecutivo")));
			} else {
				doc_maestro.getTree().setNombre(doc_maestro.getNombre());
				doc_maestro.getTree().setDescripcion(doc_maestro.getNombre());

				// /BUSCAMOS EL NOMBRE DEL ARCHIVO ORIGINAL Y SE LO CAMBIAMOS
				// POR EL TITULO DEL DOCUMENBTO
				String extension = extensionObtener(doc_detalle.getNameFile());
				if (!isEmptyOrNull(extension)
						&& !isEmptyOrNull(doc_maestro.getNombre())) {
					doc_detalle.setNameFile(doc_maestro.getNombre() + "."
							+ extension);
				}

				// FIN BUSCAMOS EL NOMBRE DEL ARCHIVO ORIGINAL Y SE LO CAMBIAMOS
				// POR EL TITULO DEL DOCUMENTO

				delegado.edit(doc_maestro.getTree());
				delegado.editMaestro(doc_maestro);
				delegado.editDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
				if (doc_maestro.isPublico()) {
					// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					boolean darPublico = true;
					super.guardamosHistoricoActivoDelDocumento(user_logueado,
							doc_maestro, false, darPublico, false, false,
							false, false, false, false, publicoStr);
					// ______________________________________________________
				}
				if (doc_maestro != null && doc_maestro.getTree() != null) {

					Tree tree = doc_maestro.getTree();
					// ________________________________________________________________________________
					// damos permisos al role que se escojio
					if (roleParaPermisos != null && swPermGrupo) {
						Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
						seguridad_Role_Lineal.setRole(roleParaPermisos);
						seguridad_Role_Lineal.setTree(tree);
						// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
						// BUSCAMOS SU
						// SEGURIDA
						List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
								.findAllSeguridad_Role_Identificador(roleParaPermisos);
						if (seguridad_Role_LinealList != null
								&& !seguridad_Role_LinealList.isEmpty()) {
							seguridad_Role_Lineal = seguridad_Role_LinealList
									.get(0);
							seguridad_Role_Lineal.setRole(roleParaPermisos);
							seguridad_Role_Lineal.setTree(tree);
						}
						// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
						// para que sea totalmente nuevo y no traiga el primary
						// key
						// viejo
						Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
								seguridad_Role_Lineal);
						delegado.create(seguridad_Role_Lineal2);
					//	givePermparaverToGroup(seguridad_Role_Lineal2);
						// ________________________________________________________________________________
					}

					// permisologia patra el usuario logueado y el diuenio
					// permisologia patra el usuario logueado y el diuenio
					Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(user_logueado);
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

					seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
				}
				pagIr = Utilidades.getFinexitoso();
				// session.setAttribute(Utilidades.getNoclearsession(), "");
				session.setAttribute("pagIr", Utilidades.getListarDocumentos());
			}
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public String regresarVerDocumento() {
		String parametro = session.getAttribute("parametro") != null ? (String) session
				.getAttribute("parametro") : "";

		if ("listarDocumentoSearch".equalsIgnoreCase(parametro)) {
			session.removeAttribute("parametro");
			return "listarDocumentoSearch";
		} else if (!"publico".equalsIgnoreCase(parametro)) {
			// super.clearSession(session,
			// confmessages.getString("usuarioSession"));
		} else {
			return "listarDocumentoSearchPublicados";
		}

		return "listar";
	}

	public String listarDocumentos() {

		refrescaraplicaciondetalles();
		session.setAttribute("treeNodoActual", doc_maestro.getTree());
		return "listar";
	}

	public String regresar() {

		String parametro = session.getAttribute("parametro") != null ? (String) session
				.getAttribute("parametro") : "";

		if ("publico".equalsIgnoreCase(parametro)) {

			return Utilidades.getListardocumentosearchpublicados();
		} else {

			return "listar";
		}

	}

	public String regresarOfFlow() {

		String pagIr = session
				.getAttribute("listarflowsParaleCambiarNomComent") != null ? (String) session
				.getAttribute("listarflowsParaleCambiarNomComent") : "";
		FlowParalelo flowParalelo = session.getAttribute("flowParalelo") != null ? (FlowParalelo) session
				.getAttribute("flowParalelo") : null;

		if (flowParalelo != null) {
			return "flowparalelo";
		} else if (!isEmptyOrNull(pagIr)) {
			return "listarflowsParaleCambiarNomComent";
		} else {
			return "listar";
		}

	}

	public String regresarListarDocumentosPublicar() {
		session.setAttribute("parametro", "publico");
		return "listarDocumentoSearchPublicados";
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

	public String viewDocumentoEditar() {
		try {
			session.removeAttribute("clientePadreDocumento");
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
		// return "viewPDF";
		return "";
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

	public String listarMenu() {
		return "listarMenu";
	}

	public String viewOnly() {

		session.removeAttribute("clientePadreDocumento");
		session.setAttribute("edit", true);
		return "editDocumentoReadOnly";
	}

	public String editamosMetaCaracteresdoc() {
		session.setAttribute("edit", true);
		return "editDocumento";
	}

	public String viewHistoricoSearchDocs() {
		session.setAttribute("parametro", "listarDocumentoSearch");
		versionId();
		return "listarFlowsHistorico";
	}

	public String viewHistorico() {
		versionId();
		return "listarFlowsHistorico";
	}

	public String viewHistoricoFlow() {
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : null;

		return "listarFlowsHistorico";
	}

	public String viewHistoricoDocActivo() {
		return "listarviewHistoricoDocActivo";
	}

	public String generarRegistro() {
		try {

			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");

			// BORRAMOS TDO PARA QE NO SE VEAL EL ARBOL Y SE OBLIGUE A
			// SELECCIONAR UN NODO
			super.clearSession(session,
					confmessages.getString("usuarioSession"));

			session.setAttribute("swPage", detalle);
			// ____________________________________
			// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO

			boolean genReg = true;

			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			super.guardamosHistoricoActivoDelDocumento(user_logueado,
					detalle.getDoc_maestro(), false, false, false, false,
					false, false, false, genReg, "");
			// ____________________________________
		} catch (Exception e) {
			redirect();
		}
		return "generarRegistro";
	}

	public String mover() {
		session.setAttribute(Utilidades.getTreeprocesando(),
				Utilidades.getTreeprocesando());
		session.removeAttribute("clientePadreDocumento");
		session.setAttribute("moverNodo", treeNodoActual);

		session.setAttribute("treeNodoActual", treeNodoActual);

		return "";
	}

	public String crear_nuevaversionDocument() {
		try {

			session.removeAttribute("clientePadreDocumento");
			session.setAttribute("edit", true);
			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			detalle.setDescripcion("");
			session.setAttribute("doc_detalle", detalle);
		} catch (Exception e) {
			redirect();
		}
		return "editDocumentoNuevaVersion";
	}

	public String crear_nuevaversion() {
		try {

			versionId();

			session.setAttribute("edit", true);
			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			detalle.setDescripcion("");
			session.setAttribute("doc_detalle", detalle);
		} catch (Exception e) {
			redirect();
		}
		return "editDocumentoNuevaVersion";
	}

	public void versionId() {
		// PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS

		session.removeAttribute("clientePadreDocumento");
		session.removeAttribute("flowParalelo");
		session.setAttribute("visibleItems", new ArrayList());
		session.setAttribute("invisibleItems", new ArrayList());
		// FIN PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO
		// HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS

		if (doc_detalle != null) {
			doc_maestro = doc_detalle.getDoc_maestro();

			// SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
			doc_detalle.setOrigen(Utilidades.getOrigenDocumentoFlow());

			// FIN SI SE HACE FLOW

			// FIN SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
			session.setAttribute("doc_detalle", doc_detalle);
			session.setAttribute("doc_detalleforflowplantilla", doc_detalle);
			session.setAttribute("doc_maestro", doc_maestro);
		}
	}

	public void versionId(ActionEvent event) {
		try {
			// PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO HACEN
			// CATCH
			// ESTA EMBASIURADO ESTOS ITEMS
			session.removeAttribute("clientePadreDocumento");
			session.removeAttribute("flowParalelo");
			session.setAttribute("visibleItems", new ArrayList());
			session.setAttribute("invisibleItems", new ArrayList());
			// FIN PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO
			// HACEN
			// CATCH
			// ESTA EMBASIURADO ESTOS ITEMS
			if (doc_detalle != null && doc_detalle.getCodigo() != null
					&& doc_detalle.getCodigo() > 0) {
				doc_detalle = delegado.findDocDetalle(doc_detalle);
				doc_maestro = doc_detalle.getDoc_maestro();
				// System.out.println("maestroi tipo="
				// + doc_detalle.getDoc_maestro().getDoc_tipo());
				// SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
				doc_detalle.setOrigen(Utilidades.getOrigenDocumentoFlow());

				// FIN SI SE HACE FLOW

				// FIN SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_detalleforflowplantilla", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
			} else {
				String attrname1 = (String) event.getComponent()
						.getAttributes().get("attrname1");
				UIParameter component = null;
				component = (UIParameter) event.getComponent().findComponent(
						"editId2");

				if (component != null) {
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editPublicox");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editIdx2_2");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {

					component = (UIParameter) event.getComponent()
							.findComponent("editIdx2_2_2");

				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Update");
				}
				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_flow");

				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_flowplantilla");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_checkout");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Desbloqueo");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Copiar");

				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_historicoflow");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_HistoricoDoc");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Publico_1");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Publico");
				}

				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_Detalle");
				}
				if ((component == null)
						|| (component.getValue().toString() == null)) {
					component = (UIParameter) event.getComponent()
							.findComponent("editId2_NameFile");
				}
				if ((component != null)
						&& (component.getValue().toString() != null)) {
					long id = Long.parseLong(component.getValue().toString());
					if (doc_detalle == null) {
						doc_detalle = new Doc_detalle();
					}
					if (id >= 0) {
						doc_detalle.setCodigo(new Long(id));

						doc_detalle = delegado.findDetalle(doc_detalle);
					}

					if (doc_detalle != null) {
						doc_maestro = doc_detalle.getDoc_maestro();

						// SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC FLOW
						doc_detalle.setOrigen(Utilidades
								.getOrigenDocumentoFlow());

						// FIN SI SE HACE FLOW

						// FIN SI SE HACE POR ACA EL FLOW , SIEMPRE SERA UN DOC
						// FLOW
						session.setAttribute("doc_detalle", doc_detalle);
						session.setAttribute("doc_detalleforflowplantilla",
								doc_detalle);
						session.setAttribute("doc_maestro", doc_maestro);
					}
				}
			}

		} catch (Exception e) {
			e.toString();
			redirect();
		}
	}

	public String actualizarDoc_Go() {
		String pagIr = "";
		session.setAttribute("actualizarDoc", true);
		session.setAttribute("edit", true);
		Doc_detalle detalle = (Doc_detalle) session.getAttribute("doc_detalle");
		if (detalle != null) {
			detalle.setDescripcion("");
			session.setAttribute("doc_detalle", detalle);
			pagIr = "crear_nuevaversion";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
		}

		return pagIr;
	}

	public String docVinculados() {
		// flows.jsp
		versionId();
		return "docVinculados";
	}

	public String docPublicar() {
		// flows.jsp
		return "docPublicar";
	}

	public String flow() {
		// flows.jsp
		return "flows";
	}

	public String edit() {
		session.setAttribute("edit", true);
		return "edit";
	}

	public String crearFlow() {
		return "flow";
	}

	public String flows_action() {
		session.removeAttribute("swSolicitudimpresion");
		session.removeAttribute("clientePadreDocumento");
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : null;

		if (doc_detalle != null) {
			if (doc_detalle.getDoc_maestro() == null) {
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
			}
		}
		return Utilidades.getFlows();

	}

	public String listaPlantillaFlowParalelo() {
		String pagIr = "";
		// PARA FLUJOS PARALELOS-.-.. SE OIGNORA EL DOCUMENTO MAESTRO Y EL
		// DETALLLE
		// EN EL JSP, SE SELECCIONA TODOS LOS FLUJOS PARALELOS QUE PUEDE AGARRAR
		// EL USU<RIO LOGUEADO..
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		return Utilidades.getListarflowparalelo();

	}

	public String flowsView_action() {
		session.removeAttribute("flowParalelo");
		session.removeAttribute("clientePadreDocumento");
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

		Doc_detalle doc_detBuscaFlow = new Doc_detalle();

		doc_detBuscaFlow = doc_detalle;
		if (doc_detalle != null) {
			session.setAttribute("doc_detalle", doc_detalle);
		}

		Flow flow = null;
		List<Flow> flowActivo = delegado.findByFlow(doc_detBuscaFlow);
		for (Flow f_for : flowActivo) {
			flow = f_for;
			if (flow != null) {
				session.setAttribute("flowHistorico", flow);
			}
			break;
		}

		return "flowsResponse";
	}

	public void viewFlow(ActionEvent event) {
		try {

			// PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO HACEN
			// CATCH
			// ESTA EMBASIURADO ESTOS ITEMS
			session.removeAttribute("clientePadreDocumento");
			session.removeAttribute("flowParalelo");
			session.setAttribute("visibleItems", new ArrayList());
			session.setAttribute("invisibleItems", new ArrayList());
			// FIN PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO
			// HACEN
			// CATCH
			// ESTA EMBASIURADO ESTOS ITEMS

			UIParameter component = (UIParameter) event.getComponent()
					.findComponent("editId22");
			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				Doc_detalle doc_detBuscaFlow = new Doc_detalle();
				doc_detBuscaFlow.setCodigo(new Long(id));
				doc_detBuscaFlow = delegado.findDetalle(doc_detBuscaFlow);
				doc_detalle = doc_detBuscaFlow;

				Flow flow = null;
				List<Flow> flowActivo = delegado.findByFlow(doc_detBuscaFlow);
				for (Flow f_for : flowActivo) {
					flow = f_for;
					if (flow != null) {
						session.setAttribute("flowHistorico", flow);
					}
					break;
				}

			}
		} catch (Exception e) {
			redirect();
		}
	}

	public String goDownloaDocument() {

		return "clienteDocumentoDownloadFile";
	}

	public void downloaDocument(ActionEvent event) {

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdDownload");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar
			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
			}

		}

	}

	public void listarVersionIdPublicar(ActionEvent event) {
		session.removeAttribute("clientePadreDocumento");
		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editid2publico");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();

			}
			// buscamos el role para editar
			if (id >= 0) {
				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);

				// PRIMERO ASEGURAMOS QUE EL DOC-DETALLE SEA EL VIGENTE
				List<Doc_detalle> doc_detalles = null;
				String publico = "publico";
				doc_detalles = delegado.encontrarDetalles(doc_detalle
						.getDoc_maestro().getCodigo(), publico);
				if (doc_detalles != null && doc_detalles.size() > 0) {
					doc_detalle = doc_detalles.get(0);
				}
				// FIN PRIMERO ASEGURAMOS QUE EL DOC-DETALLE SEA EL VIGENTE

				session.setAttribute("doc_detalle", doc_detalle);

			}
		}
	}

	public void versionIdPublicar(ActionEvent event) {
		try {

			UIParameter component = null;
			component = (UIParameter) event.getComponent().findComponent(
					"editid2publico");

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editid2publico2");
			}

			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				if (doc_detalle == null) {
					doc_detalle = new Doc_detalle();
				}
				// buscamos el role para editar
				System.out.println("doc-detalle=" + id);
				if (id >= 0) {
					System.out.println("id=" + id);

					doc_detalle.setCodigo(new Long(id));

					doc_detalle = delegado.findDetalle(doc_detalle);
				}

				if (doc_detalle != null) {
					System.out
							.println("doc_detalle.getDoc_maestro().getNombre()="
									+ doc_detalle.getDoc_maestro().getNombre());
					System.out
							.println("doc_detalle=" + doc_detalle.getCodigo());
					doc_maestro = doc_detalle.getDoc_maestro();
					session.setAttribute("doc_detalle", doc_detalle);
					session.setAttribute("doc_detalleforflowplantilla",
							doc_detalle);
					session.setAttribute("doc_maestro", doc_maestro);
				}
			}

		} catch (Exception e) {
			e.toString();
			redirect();
		}
	}

	public String viewListarDocumento() {
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		session.removeAttribute("clientePadreDocumento");
		// EN CASO DE QUE SE DESHIZO LA VERSION BORRADOR.. BUSCAMOS LA ULTIMO
		// DETALLE DE DOC_MAESTRO
		doc_maestro = session.getAttribute("doc_maestro") != null ? (Doc_maestro) session
				.getAttribute("doc_maestro") : null;
		if (doc_maestro != null) {
			doc_detalle = delegado.findUltimolDoc_Detalles(doc_maestro);
			session.setAttribute("doc_detalle", doc_detalle);
		}
		// FIN EN CASO DE QUE SE DESHIZO LA VERSION BORRADOR.. BUSCAMOS LA
		// ULTIMO
		// DETALLE DE DOC_MAESTRO

		session.setAttribute(Utilidades.getNoclearsession(),
				"doc_detalle,doc_maestro,treeNodoActual");
		return Utilidades.getFinexitoso();
	}

	public void editPublicoId(ActionEvent event) {

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editPublicox");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar

			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));

			}
		}
	}

	public void editBloqueoId(ActionEvent event) {
		session.removeAttribute("clientePadreDocumento");
		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2_applet");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar

			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
			}

		}

	}

	public void liberarBloqueoId(ActionEvent event) {
		session.removeAttribute("clientePadreDocumento");
		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar
			if (id >= 0) {
				try {
					doc_detalle.setCodigo(new java.lang.Long(id));
					// buscamos el detalle
					doc_detalle = delegado.findDetalle(doc_detalle);
					// ahora buscamos con el docmaestro de detalle y que sea
					// borrador el doc detalle
					// buscamos que el detalle sea tipo borrador
					Doc_estado buscar_edo = new Doc_estado();
					buscar_edo.setCodigo(Utilidades.getBorrador());
					buscar_edo = delegado.findDocEstado(buscar_edo);
					doc_detalle.setDoc_estado(buscar_edo);
					doc_detalle = delegado.findDocDetalle_TipoEdo(doc_detalle);
					if (doc_detalle != null) {
						// ____________________________________
						// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
						user_logueado = (Usuario) session
								.getAttribute("user_logueado");
						boolean deshNuevaVer = true;
						super.guardamosHistoricoActivoDelDocumento(
								user_logueado, doc_detalle.getDoc_maestro(),
								false, false, false, false, false, false,
								deshNuevaVer, false, "");
						// ____________________________________
						// borra,mos el estado borrador
						// --------------------------------------------------------------------------
						// buscamos los flujos y flujos participantes para
						// eliminarlos
						List<Flow> flowDelete = delegado
								.findByFlowDocDetalle(doc_detalle);
						for (Flow f : flowDelete) {
							borrarFlujos(f);
						}

						// ---------------------------------------------------------------------------
						// ELIMINAMOS EL BORRADOR
						borrarConstraintsDetalle(doc_detalle);

						// BUSCAMOS EL VIGENTE QUE ESTA EN CHECKOUT, LO
						// LIBERAMOS Y LO COLOCAMOS DE PRKMERO
						buscar_edo.setCodigo(Utilidades.getVigente());
						buscar_edo = delegado.findDocEstado(buscar_edo);
						doc_detalle.setDoc_estado(buscar_edo);
						doc_detalle = delegado
								.findDocDetalle_TipoEdo(doc_detalle);
						doc_detalle.setDoc_checkout(false);
						delegado.editDetalle(doc_detalle);
					} else {

						doc_detalle = new Doc_detalle();
						doc_detalle.setCodigo(new java.lang.Long(id));
						// buscamos el detalle
						doc_detalle = delegado.findDetalle(doc_detalle);
						// puede que el documento estando borrador, ya este
						// revisado y quiera hacer el cambio o estando borrador
						// este rechazado
						buscar_edo.setCodigo(Utilidades.getRevisado());
						buscar_edo = delegado.findDocEstado(buscar_edo);
						doc_detalle.setDoc_estado(buscar_edo);
						doc_detalle = delegado
								.findDocDetalle_TipoEdo(doc_detalle);

						// si no lo encuentra como revisado, lo buscamos como
						// rechazado
						if (doc_detalle == null) {
							doc_detalle = new Doc_detalle();
							doc_detalle.setCodigo(new java.lang.Long(id));
							// buscamos el detalle
							doc_detalle = delegado.findDetalle(doc_detalle);
							buscar_edo.setCodigo(Utilidades.getRechazado());
							buscar_edo = delegado.findDocEstado(buscar_edo);
							doc_detalle.setDoc_estado(buscar_edo);
							doc_detalle = delegado
									.findDocDetalle_TipoEdo(doc_detalle);

						}
						// aqui si chequeamos si boirramos el borrador, ya este
						// reviaso o rechazado
						if (doc_detalle != null) {
							Doc_detalle doc_revisadoBorrar = new Doc_detalle();
							doc_revisadoBorrar.setCodigo(doc_detalle
									.getCodigo());
							// actualizamos el aprobado que estaba en checkOut a
							// que este libre nuevamente
							buscar_edo.setCodigo(Utilidades.getVigente());
							buscar_edo = delegado.findDocEstado(buscar_edo);
							doc_detalle.setDoc_estado(buscar_edo);
							doc_detalle = delegado
									.findDocDetalle_TipoEdo(doc_detalle);
							doc_detalle.setDoc_checkout(false);
							// tenemos el doc vigente
							if (doc_detalle != null) {
								// este es el vigente
								delegado.editDetalle(doc_detalle);
								// borramos el revisado que primero fue borrador
								// y ahora es revisado
								doc_revisadoBorrar = delegado
										.findDetalle(doc_revisadoBorrar);
								borrarConstraintsDetalle(doc_revisadoBorrar);

							}

						}

					}
				} catch (EcologicaExcepciones ex) {
					System.out.println("error=" + ex.toString());

				}
			}

		}

	}

	public String save() {
		if (("".equals(doc_maestro.getNombre().toString().trim()))
				|| ("".equals(doc_maestro.getNombre().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {
			try {
				delegado.editMaestro(doc_maestro);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

		}
		return "";
	}

	public void obtenHijos(Tree tree, List nodos) {
		List lista = delegado.findAllTreeHijos(tree.getNodo());
		if (lista != null) {
			int size = lista.size();
			int i = 0;
			while (i < size) {
				Tree tree1 = (Tree) lista.get(i);
				i++;

				nodos.add(tree1);
				obtenHijos(tree1, nodos);
			}

		}
	}

	public void publicoEs_Doc_Detalle(Doc_detalle doc, Usuario usuario_loguedo) {
		if (doc != null) {
			if (usuario_loguedo != null && doc.getPublicador() != null) {
				doc.setSwIsPublicador((usuario_loguedo.getId()
						- doc.getPublicador().getId() == 0));
			}
			if (doc.isSwIsPublicador()) {
				this.swIsPublicador = doc.getDoc_maestro().isPublico();
			}
			try {
				if (doc.getDoc_maestro().isPublico()) {
					publicoStr = messages.getString("doc_publico");
					publico = true;
				} else {
					publicoStr = messages.getString("doc_publico");
					// publicoStr = messages.getString("doc_publicarDocumento");
					publico = false;
				}
			} catch (NullPointerException e) {
				publicoStr = messages.getString("doc_publico");
				// publicoStr = messages.getString("doc_publicarDocumento");
				publico = false;
			}

		}
	}

	public void publicoEs_Doc_Maestro(Doc_maestro doc) {
		if (doc != null) {
			if (doc.isPublico()) {
				publicoStr = messages.getString("doc_publico");
				publico = true;
			} else {
				publicoStr = messages.getString("doc_publico");
				// publicoStr = messages.getString("doc_publicarDocumento");
				publico = false;
			}

		}
	}

	public void borrarFlujos(Flow f) {
		// borramos flow paralelo
		// try {
		// delegado.delete(f.getFlowParalelo());
		// } catch (EcologicaExcepciones e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// BORRAMOS PRIMERO EL CONTROL DE TIEMPÃ’ DE LOS
		// FLUJOS
		// List<FlowControlByUsuarioBean> controlTiempo = delegado
		// .find_allControlTimeByFlowParticipAndRole(f);
		// try {
		//
		// if (controlTiempo != null && !controlTiempo.isEmpty()) {
		// for (FlowControlByUsuarioBean borrar : controlTiempo) {
		// delegado.destroy(borrar);
		// }
		// }
		//
		// List<Flow_referencia_role> f_r_rs = delegado
		// .findAllFlow_delete_role_ByFlow(f);
		// Role role = new Role();
		// for (Flow_referencia_role f_r_r : f_r_rs) {
		// delegado.destroy(f_r_r);
		// }
		//
		// List<Flow_Participantes> f_ps = delegado
		// .findByDeleteFlowParticipantes(f);
		// for (Flow_Participantes f_p : f_ps) {
		// // BORRAMOS EL FLOW DE PARTICIPANTES
		// delegado.destroy(f_p);
		// }

		// delegado.destroy(f);
		//
		// // ESTO LO USAMOS PARA BORRAR UN BORRADOR EN LISTARDOCUMENTOS
		// List<Flow_Participantes> flows_participantes = delegado
		// .findByFlowParticipantes(f);
		// for (Flow_Participantes f_p : flows_participantes) {
		// try {
		// delegado.destroy(f_p);
		// } catch (Exception exception) {
		// continue;
		// }
		// }
		// List<Flow_referencia_role> flows_roles = delegado
		// .findByFlow_referencia_role(f);
		// for (Flow_referencia_role f_r : flows_roles) {
		// try {
		// delegado.destroy(f_r);
		// } catch (Exception exception) {
		// continue;
		// }
		// }

		// ELIMNINAMOS LOS FLUJOS HISTORICO DEL
		// DOCUMENTO
		// List<FlowsHistorico> flowsHistoricos = delegado
		// .findAll_FlowsHistoricoDoc_detalle(doc_detalle);
		// for (FlowsHistorico f_h : flowsHistoricos) {
		// delegado.destroy(f_h);
		// }

		try {

			delegado.destroy(f);

		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// } catch (Exception e) {
		// System.out.println(e.toString());
		// }
	}

	public List<ClienteDocumentoMaestro> getDoc_maestros()
			throws EcologicaExcepciones {
		// VERIFICAMOS SI EL DOC ES VIGENTE para COLOCARLO PUBLICO EN LA VISTA
		boolean swVigente = false;

		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
		// ENCONTRAMOS EL AREA DEL DOCUMENTO
		Tree area = null;
		area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(treeNodoActual,
				Utilidades.getNodoArea());
		// OBTENEMOS SEGURIDAD DE DOCUMENTO ES CON TREE Y USUARIO LOGUEADO

		if (treeNodoActual.getTiponodo() == Utilidades.getNodoDocumento()) {

			seguridadTree = super.getSeguridadTree(user_logueado,
					treeNodoActual);
		} else {

			seguridadTree = super.getSeguridadTree(treeNodoActual);
		}

		// OBTENEMOS SEGURIDAD;

		// ---------------------------

		// -------------------------------

		usuariosInFlowStr = "";
		String areaStr = "";
		String tipoFormato = "";

		List<ClienteDocumentoMaestro> listamostar = new ArrayList<ClienteDocumentoMaestro>();
		ClienteDocumentoMaestro clienteDocumentoMaestro = new ClienteDocumentoMaestro();
		// aqui verificamos seguridad de documentos vinculados, documento
		// punblico etc

		if (treeNodoActual != null) {
			doc_maestros = delegado.findAllDoc_maestros(treeNodoActual);
			// DEBERIAMOS TENER SIEMPRE UN SOLO MAESTRO
			int size = doc_maestros.size();
			for (int k = 0; k < size; k++) {
				Doc_maestro doc = (Doc_maestro) doc_maestros.get(k);

				// se le agrega plantilla al nombre del tipo de documento si es
				// plantilla
				if (doc.getDoc_tipo() != null
						&& doc.getDoc_tipo().getFormatoTipoDoc() != null) {
					if (Utilidades.getFormatoTipoDoc()
							- doc.getDoc_tipo().getFormatoTipoDoc() == 0) {
						StringBuffer plantilla = new StringBuffer("");
						plantilla.append("[")
								.append(messages.getString("formato"))
								.append("]");
						Doc_tipo t = new Doc_tipo();
						t.setCodigo(doc.getDoc_tipo() != null ? doc
								.getDoc_tipo().getCodigo() : 0);
						t = delegado.findDocTipo(t);
						doc.getDoc_tipo().setNombre(
								t.getNombre() + plantilla.toString());
					}
				}

				List<Doc_detalle> doc_detalles = null;
				String publico = session.getAttribute("parametro") != null ? (String) session
						.getAttribute("parametro") : "";
				doc_detalles = delegado.encontrarDetalles(doc.getCodigo(),
						publico);

				if (doc_detalles != null) {
					// Iterator it = doc_detalles.iterator();
					boolean swSoloEncabezadoTieneBotones = true;
					int elPrimeroVaInmaestro = 0;
					Doc_detalle doc_detalleVigente = new Doc_detalle();
					doc_detalles2.clear();
					for (Doc_detalle d : doc_detalles) {
						// while (it.hasNext()) {
						elPrimeroVaInmaestro++;

						// solo un maestro se agarra, los demas son identicos
						if (k == 0) {
							// COLOCAMOS EL AREA DEL DOCUMENTO + EL CONSECUTIVO
							areaStr = d.getAreaDocumentos() != null ? d
									.getAreaDocumentos().getNombre() : "";
							tipoFormato = doc.getDoc_tipo().getAbreviatura() != null ? doc
									.getDoc_tipo().getAbreviatura() : doc
									.getDoc_tipo().getNombre();

							doc.setConsecutivo(doc.getConsecutivo());
							// verificamos si colocamos etiqueta publico o
							// publicar
							publicoEs_Doc_Maestro(doc);
							boolean nombreCorto = true;
							String prefijo = "";
							prefijo = getPrefijo(doc.getTree(), prefijo,
									nombreCorto);
							doc.getTree().setPrefix(prefijo);
							setDoc_maestro(doc);
						}

						// VERIFICAMOS SI EL DOC ES VIGENTE
						if (((d.getDoc_estado().getCodigo() - Utilidades
								.getVigente()) == 0)) {
							swVigente = true;
						}

						// FIN VERIFICAMOS SI EL DOC ES VIGENTE

						// SI EL PRIMER DOCUMENTO ES OBSOLETO.. LO OBVIAMOS..
						// DEBE SER
						// O BORRADOR O VIGENTE..LO COLOCAMOS COMO BORRADORs
						Doc_estado doc_edo = new Doc_estado();
						doc_edo.setCodigo(Utilidades.getObsoleto());
						doc_edo = delegado.findDocEstado(doc_edo);
						if (elPrimeroVaInmaestro == 1
								&& d.getDoc_estado().getCodigo()
										.equals(doc_edo.getCodigo())) {
							doc_edo = new Doc_estado();
							doc_edo.setCodigo(Utilidades.getBorrador());
							doc_edo = delegado.findDocEstado(doc_edo);
							d.setDoc_estado(doc_edo);
							delegado.editDetalle(d);

						}
						// FIN SI EL PRIMER DOCUMENTO ES OBSOLETO.. LO
						// OBVIAMOS..

						swPuedeRealizarFlujo = false;
						swIsObsoleto = false;
						swActualizar = false;
						swCheckOut = false;
						swCopy = false;
						swPage = false;
						swCute = false;
						// si hay uno solo doc detalle, se agarra por defecto
						// un borrador y se va hacer workflows
						swLocked = d.isDoc_checkout();
						swLockedwithkey = false;
						swUnlocked = false;
						ClienteDocumentoDetalle doc_detalle_copy = null;
						// __________________________________________________
						// hacemos un parcxh, si el flow no dice que tipo es,
						// esta mal hecho
						// lo borramos de flowParticiapntes, flow referencia
						// ro,e y flow
						List<Flow> flowActivoMalos = delegado.findByFlowBad(d);
						for (Flow f : flowActivoMalos) {
							borrarFlujos(f);
						}
						// ______________________________________________________
						// si el documento esta en flujo, agarramos el estado
						// del flujo
						d.setOrigen(Utilidades.getOrigenDocumentoFlow());
						List flowActivo = delegado.findByFlowByOrigen(d);

						// es un solo registro y puede ser el borrador o un
						// dlcumento vigente
						if (swSoloEncabezadoTieneBotones) {
							swCopy = true;
							swCute = true;
							swVincularDocumento = false;
							if (seguridadTree.isToView()
									|| swSuperUsuario) {
								swVincularDocumento = true;
							}
//							if ((d.getDoc_estado().getCodigo() - Utilidades
//									.getVigente()) == 0
//									|| (d.getDoc_estado().getCodigo() - Utilidades
//											.getAprobado()) == 0) {
//								if (seguridadTree.isToVincDoc()
//										|| swSuperUsuario) {
//									swVincularDocumento = true;
//								}
//							}
							// _______________________________________________________
							// seguridad historico
							swHistFlow = false;
							if (seguridadTree != null
									&& seguridadTree.isToHistFlow()
									|| swSuperUsuario) {
								swHistFlow = true;
							}

							swHistDoc = false;
							if (seguridadTree != null
									&& seguridadTree.isToHistDoc()
									|| swSuperUsuario) {
								swHistDoc = true;
							}
							// _______________________________________________________
							// buscamos del actual detalle, el detalle vigente

							doc_detalleVigente.setCodigo(d.getCodigo());
							doc_detalleVigente = getDocDetalleVigente(doc_detalleVigente);
							// ________________________________________________________________________________
							// SI PASA, EL DOCUMENTO ES VIGENTE
							if (doc_detalleVigente != null) {
								swLocked = doc_detalleVigente.isDoc_checkout();
								// __________________________________________________________________________________//
								// buscamos si hay vigentes ya sean en checkout
								// o no..para activar estos candados
								// es el ultimo aprobado vigente
								// CANDADO DESBLOQUEADO
								if ((!doc_detalleVigente.isDoc_checkout())
										&& (doc_detalleVigente.getDoc_estado()
												.getCodigo().equals(Utilidades
												.getVigente()))) {
									/* SEGURIDAD DE CHECKOUT CON LOS CANDADOS */
									/*
									 * este icono dice que se puede realizar una
									 * nueva version
									 */
									swUnlocked = true;
								}
								// CANDADO BLOQUEADO CON LLAVE O SIN LLAVE,
								// DEPENDE PERMISO
								if ((doc_detalleVigente.isDoc_checkout())
										&& (doc_detalleVigente.getDoc_estado()
												.getCodigo().equals(Utilidades
												.getVigente()))) {
									/* SEGURIDAD DE CHECKOUT CON LOS CANDADOS */
									// sin es el duenio, lo puede desproteger
									// BLOQUEADO SE INICIALIZA
									if (user_logueado != null
											&& user_logueado.equals(d
													.getModificadoPor())) {
										// la llave para desbloquear
										swLockedwithkey = true;
									}

								}
								// si el documento se pueden hacer registros ..
								// Y SI NO STA EN FLUJO y es vigente
								if (flowActivo.isEmpty()) {
									// VALIDAMOS PARA HACER REGISTRO, SI NO ESTA
									// EN PEGAR Y SI ES TIPO FORMATO
									if ((doc.getDoc_tipo() != null)
											&& doc.getDoc_tipo()
													.getFormatoTipoDoc() == Utilidades
													.getFormatoTipoDoc()) {
										if (seguridadTree.isToDoRegistros()
												|| swSuperUsuario) {
											if (session.getAttribute("swPage") != null) {
												swRegistro = false;
											} else {
												swRegistro = true;
											}

										}

									}
								}

							}
							// ________________________________________________________________________________
							// OJO CON LOS
							// FLUJOS-----FLOWS--------------------------------------------------------
							// SI HAY UN VERSION VIGENTE Y LE DAN NUEVA
							// VERSION.. EL USUARIOMQUE HIZO ESO
							// ES EL UNICO QUE PUEDE REALIZAR FLUJO.. PORQUE EL
							// DOCUMENTO ESTA CHECKOUT POR EL
							// SI EL USUARIO DESHACE LA VERSION..YA TODOS PUEDEN
							// CREAR UNA NNUEVA VERSION
							// CON UN DOCUMENTO BORRADOR.. QUEDA CHECKOUT EL
							// DOCUMENTO
							// Y SOILO EL PODRA HACER FLUJO MIENTRAS TANTO..

							// ______________SI ESTA EN
							// FLUJO____________________________________________________________________//
							swSoloEncabezadoTieneBotones = false;
							// el documento esta en flujo

							if (!flowActivo.isEmpty()) {

								Flow flow = (Flow) flowActivo.get(0);
								// si vengo de la lista maestra para ver los
								// documentos vigentes y estoy en flujo, no me
								// cambie
								// el estado en que esta actualmnehnte que es
								// vigente
								if (!"publico".equalsIgnoreCase(publico)) {
									d.setDoc_estado(flow.getEstado());
								}

								// buscamos los participantes del flujo
								List flowParts = delegado
										.findByFlowParticipantesOrigenFlow(flow);
								Iterator itfp = flowParts.iterator();

								StringBuffer strFlowUser = new StringBuffer();
								while (itfp.hasNext()) {
									Flow_Participantes flujo_participantes = (Flow_Participantes) itfp
											.next();
									if (flujo_participantes.getFirma()
											.getCodigo()
											.equals(Utilidades.getSinFirmar())) {
										strFlowUser.append(flujo_participantes
												.getParticipante().toString());
										strFlowUser.append("\r\t");
									}

								}
								usuariosInFlowStr = strFlowUser.toString();
								swFlujoActivo = true;
								swPuedeRealizarFlujo = false;
								swIsObsoleto = true;
								swActualizar = false;
								swCheckOut = false;

								swLockedwithkey = false;
								swUnlocked = false;

							} // ______________SI NO ESTA EN
								// FLUJO____________________________________________________________________//
							else {
								// verificamos quien puede actualizar si el
								// documento es borrador
								// si un detalle es borrador, pero hay un
								// documento vigente..y es checkout
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getBorrador())) {
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {
										// preguntamos en seguridad si puede
										// realizar flujo
										swPuedeRealizarFlujo = false;
										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}

										}

										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}
										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}
											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}
											}

										}

									}

								}
								// verificamos quien puede actualizar si el
								// documento es rechazado
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getRechazado())) {
									// chequeamos si ha sido checkaout
									// anteriormente
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {

										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}

										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}
										}

										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}
											}

										}

									}

								}
								// verificamos quien puede actualizar si el
								// documento es revisado
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getRevisado())) {
									// chequeamos si ha sido checkaout
									// anteriormente
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {
										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}

										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}

										}

										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}

											}

										}

									}

								}
								// es el ultimo aprobado vigente
								if ((!d.isDoc_checkout())
										&& (d.getDoc_estado().getCodigo()
												.equals(Utilidades.getVigente()))) {
									if (seguridadTree.isToDoFlow()
											|| seguridadTree
													.isToDoFlowRevision()
											|| swSuperUsuario) {
										// REDUNDANTE ARRIBA Y ESTA CONDICION,
										// PERO NO IMPOIRTA
										if (!d.getDoc_estado()
												.getCodigo()
												.equals(Utilidades.getVigente())) {
											// por decision que todo documento
											// vigente no se puede realizar
											// fluyjos..
											// se debe crear una nueva version
											// borrador para someterlo a un
											// flujo
											swPuedeRealizarFlujo = true;
										}
									}

									if (seguridadTree.isToMod()
											|| swSuperUsuario) {
										swCheckOut = true;
									}

									swIsObsoleto = false;
									swActualizar = false;

								}
								// es una copia, hay uno que lo repreesenta como
								// borrador
								if ((d.isDoc_checkout())
										&& (d.getDoc_estado().getCodigo()
												.equals(Utilidades.getVigente()))) {
									swPuedeRealizarFlujo = false;
									swIsObsoleto = true;
									swActualizar = false;
									if (seguridadTree.isToMod()
											|| swSuperUsuario) {
										swCheckOut = true;
									}

								}
								// esta por demas ...
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getObsoleto())) {
									swPuedeRealizarFlujo = false;
									swIsObsoleto = true;
									swActualizar = false;
									swCheckOut = false;

								}

							}
						}

						// se graba con las varfiables alteradas arriba de este
						// codigo
						doc_detalle_copy = new ClienteDocumentoDetalle(d,
								swPuedeRealizarFlujo, swIsObsoleto,
								swActualizar, swCheckOut, swCopy, swPage,
								swCute, swLocked, swLockedwithkey, swUnlocked,
								swFlujoActivo, swVincularDocumento, swPublico,
								swMover, swRegistro, icono, usuariosInFlowStr,
								swHistDoc, swHistFlow);

						// el primer detalle va en maestro :)
						if (doc_detalle_copy.getDoc_estado() != null
								&& doc_detalle_copy.getDoc_estado().getNombre() != null) {
							// doc_detalle_copy.getDoc_estado().setNombre(messages.getString(doc_detalle_copy.getDoc_estado().getNombre().toLowerCase()));
							doc_detalle_copy.getDoc_estado().setNombre(
									doc_detalle_copy.getDoc_estado()
											.getNombre().toLowerCase());
						}

						// colocamos que icono llevara
						// obtenemois el icono del documento
						// icono = super.obtenIconoDoc(d.getNameFile());
						icono = super.obtenIconoDoc(doc_detalle_copy
								.getNameFile());

						if (Utilidades.obtenerXdelIcono(icono)) {
							swAttachment = true;
						}

						doc_detalle_copy.setIcono(icono);
						// convertimos el tam,aÃ±o de bytes a mg
						if (doc_detalle_copy.getSize_doc() != 0) {
							doc_detalle_copy.setSize_doc(converBytesMbytes(Math
									.ceil(doc_detalle_copy.getSize_doc())));
						}

						if (elPrimeroVaInmaestro == 1) {

							// BUSCAMOS SEGURIDAD PARA IMPRESION
							if (seguridadTree != null
									&& ((seguridadTree.isToSolicitudImpresion()
											|| seguridadTree
													.isToSolicitudImpresion0()
											|| seguridadMenu
													.isToImprimirAdministrar() || swSuperUsuario))) {
								swImprimir = true;
								doc_detalle_copy.setSwImprimir(swImprimir);
								doc_detalle_copy
										.setSwHabilitadoImprimir(habilitadoImprimir(
												d, user_logueado));
								doc_detalle_copy
										.setSwEstaInFlowToImprimir(estaInFlowToImprimir(
												d, user_logueado));

							}
							// SI EL DOCUMENTO ES BORRADOR, SE PUEDE EDITAR
							// SI ES REVISADO O RECHAZADO .. SE PUEDE EDITAR..
							if (d.getDoc_estado().getCodigo()
									.equals(Utilidades.getBorrador())
									|| d.getDoc_estado().getCodigo()
											.equals(Utilidades.getRevisado())
									|| d.getDoc_estado().getCodigo()
											.equals(Utilidades.getRechazado())
									&& doc_detalles.size() == 1) {
								doc_detalle_copy.setSwFirstBorrador(true);
							}
							// FIN SI EL DOCUMENTO ES BORRADOR, SE PUEDE EDITAR

							// FINSEGURIDAD PARA IMPRESION

							// BUSCAMOS SI EXPIRO EL PUBLICADO
							// PRIMERO ASEGURAMOS QUE EL DOC-DETALLE SEA EL
							// VIGENTE
							List<Doc_detalle> doc_detallesExpira = null;
							String publicor = "publico";
							doc_detallesExpira = delegado.encontrarDetalles(d
									.getDoc_maestro().getCodigo(), publicor);
							Doc_detalle docDetalleExpiradoPublico = null;
							if (doc_detallesExpira != null
									&& doc_detallesExpira.size() > 0) {
								docDetalleExpiradoPublico = doc_detallesExpira
										.get(0);
							}
							// FIN PRIMERO ASEGURAMOS QUE EL DOC-DETALLE SEA EL
							// VIGENTE

							if (docDetalleExpiradoPublico != null) {
								doc_detalle_copy
										.setSwFechaPublicoExpiro(docDetalleExpiradoPublico
												.isSwFechaPublicoExpiro());
							}
							// FIN BUSCAMOS SI EXPIRO EL PUBLICADO

							clienteDocumentoMaestro = new ClienteDocumentoMaestro(
									publicoStr, getDoc_maestro(),
									doc_detalle_copy);

							clienteDocumentoMaestro
									.setTipoDocumento(tipoFormato);

							clienteDocumentoMaestro.setSwLocked(swLocked);

							if (doc_detalle_copy.getDatecambio() != null) {
								clienteDocumentoMaestro
										.setDatecambio(Utilidades.sdfShow
												.format(doc_detalle_copy
														.getDatecambio()));

							} else {
								clienteDocumentoMaestro
										.setDatecambio(Utilidades.sdfShow
												.format(clienteDocumentoMaestro
														.getFecha_creado()));
							}

							// podemos hacer publico el doc
							if (swVigente) {
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getVigente())
										|| d.getDoc_estado()
												.getCodigo()
												.equals(Utilidades
														.getAprobado())) {
									doc_detalle_copy
											.setSwCanDoPublicoIsVigente(true);
									doc_detalle_copy.setSwVigente(true);
								}

							}
							// fin podemos hacer publico el doc

							chequearExtenion(doc_detalle_copy);
							doc_detalles2.add(doc_detalle_copy);
							doc_detallePrincipal = doc_detalle_copy;
						} else {
							// podemos hacer publico el doc
							if (swVigente) {
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getVigente())
										|| d.getDoc_estado()
												.getCodigo()
												.equals(Utilidades
														.getAprobado())) {
									doc_detalle_copy
											.setSwCanDoPublicoIsVigente(true);
									doc_detalle_copy.setSwVigente(true);
								}

							}
							// fin podemos hacer publico el doc
							chequearExtenion(doc_detalle_copy);
							// ------------------------------------------

							// estas son las versiones del docuimento
							doc_detalles2.add(doc_detalle_copy);
						}

					}
				}
			}
		}

		// podemos hacer publico el doc
		if (swVigente) {
			doc_detallePrincipal.setSwCanDoPublicoIsVigente(true);
		}
		// ESTE ES LA VARIABLE DE UN SOLO ENCABEZADO EN DOC-DETALLES
		session.setAttribute("doc_detallePrincipal", doc_detallePrincipal);
		// FIN ESTE ES LA VARIABLE DE UN SOLO ENCABEZADO EN DOC-DETALLES

		// fin podemos hacer publico el doc
		clienteDocumentoMaestro.setDoc_detallesCliente(doc_detalles2);
		listamostar.add(clienteDocumentoMaestro);
		// VALIDAMOS QUE SOLO SEA UN BORRADOR QUE PRESENTE..
		boolean swUnSoloBorrador = true;
		List<ClienteDocumentoMaestro> listamostar2 = new ArrayList<ClienteDocumentoMaestro>();
		for (ClienteDocumentoMaestro g : listamostar) {
			try {
				if (("borrador".equalsIgnoreCase(g
						.getPrimerClienteDocumentoDetalle().getDoc_estado()
						.getNombre()))) {
					if (swUnSoloBorrador) {
						listamostar2.add(g);
						swUnSoloBorrador = false;
					}
				} else {
					listamostar2.add(g);
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

		}

		// LOS DETALLES APARTE
		doc_detallesCliente = doc_detalles2;
		// FIN LOS DETALLES APRATE

		// FIN VALIDAMOS QUE SOLO SEA UN BORRADOR QUE PRESENTE..
		return listamostar2;
	}

	public boolean habilitadoImprimir(Doc_detalle docImp, Usuario user_logueado) {

		// SI ES SOLO IMPRESION SIMPLE O ES EL ADMINISTRADOR PARA IMPRIMIR
		if (seguridadTree.isToSolicitudImpresion0()
				|| seguridadMenu.isToImprimirAdministrar()) {
			return true;
			// SI ES EGURIDAD A TRAVES DE FLUJOS
		} else if (seguridadTree.isToSolicitudImpresion()) {
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

		// doc_detalle viene de usar jsf2 en la vista
		session.removeAttribute("clientePadreDocumento");
		session.setAttribute("doc_detalle", doc_detalle);

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

	public void hacerSendSolicitudImpresionDirecto(ActionEvent event) {
		// ESTO ES MANDAR A IMPRIMIR DE MANERA AUTOMATICA

		session.setAttribute("numCopias", "1");
		session.setAttribute("solicitudimpresion", "-1");

	}

	public String publicaComentarioUsuario() {
		return "editarDocumento";
	}

	public void publicaComentarioUsuarioId(ActionEvent event) {

		Usuario usuario = new Usuario();
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("usuarioid");

		if ((component != null) && (component.getValue().toString() != null)) {

			long id = Long.parseLong(component.getValue().toString());

			usuario.setId(new java.lang.Long(id));
			// buscamos el detalle
			usuario = delegado.find_Usuario(usuario.getId());

			if (usuario != null) {
				System.out.println("usuario=" + usuario.toString());
			}

		}
		Doc_detalle doc_detalle2 = new Doc_detalle();
		UIParameter docdetalleid = (UIParameter) event.getComponent()
				.findComponent("docdetalleid");
		if ((docdetalleid != null)
				&& (docdetalleid.getValue().toString() != null)) {
			long id = Long.parseLong(docdetalleid.getValue().toString());
			doc_detalle2.setCodigo(new java.lang.Long(id));
			// buscamos el detalle
			doc_detalle2 = delegado.findDetalle(doc_detalle2);
			if (doc_detalle2 != null) {
				System.out.println("doc_detalle2=" + doc_detalle2.getCodigo());
			}

		}

		publicadosUsuComent = new PublicadosUsuComent();
		publicadosUsuComent.setDoc_detalle(doc_detalle2);
		publicadosUsuComent.setUsuario(usuario);
		publicadosUsuComent = delegado
				.findOnePublicadosUsuComent(publicadosUsuComent);
		session.setAttribute("publicadosUsuComent", publicadosUsuComent);

	}

	/**
	 * @deprecated AHORA SE HACE DE MANERA INDIVIDUAL POR FLOW
	 * @see #fooBar(String)
	 */

	public void listarSolicitudImpresionId(ActionEvent event) {

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2Imprimir");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar

			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);

			}

		}

	}

	public void listarSolicitudImpPartsToSendToImprimir(ActionEvent event) {

		String attrname1 = (String) event.getComponent().getAttributes()
				.get("attrname1");
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2Imprimir");
		if (component == null) {
			component = (UIParameter) event.getComponent().findComponent(
					"editId2Imprimirversion");
		}

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar

			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);

			}

		}

	}

	public void chequearExtenion(ClienteDocumentoDetalle doc_detalle_copy) {
		// ------------------------------------------
		// CHEQUEAMOS SI ES UN ARCHIVO PLANO PARA PROCESAR
		// DIFERENCIAS CON OTROS ARCHIVOS
		String extension = doc_detalle_copy.getNameFile().substring(
				doc_detalle_copy.getNameFile().lastIndexOf(".") + 1,
				doc_detalle_copy.getNameFile().length());

		ExtensionFile extensionFile = delegado.tipoContextType(extension);

		// SI ESTA EN EXTENSION FILE
		if (extensionFile != null) {
			if (extensionFile.getMimeType().equalsIgnoreCase(
					Utilidades.getMimeTypeTextPlain())) {
				doc_detalle_copy.setSwDiferenciaEntreVersiones(true);
			}
			// SI LA EXTENSION NO EXISTE, LA BUSCAMOS EN
			// EXTENSION HIJOS EL PADRE QUE ES
			// EXTENSION FILE
		} else {
			ExtensionFileHijos extensionFileHijos = new ExtensionFileHijos();
			extensionFileHijos.setExtension(extension);
			extensionFileHijos = delegado
					.find_ExtensionFileByExtension(extensionFileHijos);
			if (extensionFileHijos != null) {
				extensionFile = extensionFileHijos.getExtensionFile();
				if (extensionFile.getMimeType().equalsIgnoreCase(
						Utilidades.getMimeTypeTextPlain())) {
					doc_detalle_copy.setSwDiferenciaEntreVersiones(true);
				}
			}
		}
	}

	public void chequearExtenion(Doc_detalle doc_detalle_copy) {
		// ------------------------------------------
		// CHEQUEAMOS SI ES UN ARCHIVO PLANO PARA PROCESAR
		// DIFERENCIAS CON OTROS ARCHIVOS
		String extension = doc_detalle_copy.getNameFile().substring(
				doc_detalle_copy.getNameFile().lastIndexOf(".") + 1,
				doc_detalle_copy.getNameFile().length());

		ExtensionFile extensionFile = delegado.tipoContextType(extension);

		// SI ESTA EN EXTENSION FILE
		if (extensionFile != null) {
			if (extensionFile.getMimeType().equalsIgnoreCase(
					Utilidades.getMimeTypeTextPlain())) {
				doc_detalle_copy.setSwDiferenciaEntreVersiones(true);
			}
			// SI LA EXTENSION NO EXISTE, LA BUSCAMOS EN
			// EXTENSION HIJOS EL PADRE QUE ES
			// EXTENSION FILE
		} else {
			ExtensionFileHijos extensionFileHijos = new ExtensionFileHijos();
			extensionFileHijos.setExtension(extension);
			extensionFileHijos = delegado
					.find_ExtensionFileByExtension(extensionFileHijos);
			if (extensionFileHijos != null) {
				extensionFile = extensionFileHijos.getExtensionFile();
				if (extensionFile.getMimeType().equalsIgnoreCase(
						Utilidades.getMimeTypeTextPlain())) {
					doc_detalle_copy.setSwDiferenciaEntreVersiones(true);
				}
			}
		}
	}

	public Doc_detalle getDocDetalleVigente(Doc_detalle detalle) {
		detalle = delegado.findDetalle(detalle);
		Doc_detalle doc_detalleVig = null;
		if (detalle != null) {
			Doc_estado doc_edo = new Doc_estado();
			doc_edo.setCodigo(Utilidades.getVigente());
			doc_edo = delegado.findDocEstado(doc_edo);
			detalle = delegado.findDetalle(detalle);
			doc_detalleVig = detalle;
			doc_detalleVig.setDoc_estado(doc_edo);
			doc_detalleVig = delegado.findDocDetalle_TipoEdo(doc_detalleVig);

		}

		return doc_detalleVig;
	}

	public Doc_detalle getDocDetalleBorrador(Doc_detalle d) {
		Doc_estado doc_edo = new Doc_estado();
		doc_edo.setCodigo(Utilidades.getBorrador());
		Doc_detalle doc_detalleBorrdor = d;
		doc_detalleBorrdor.setDoc_estado(doc_edo);
		doc_detalleBorrdor = delegado
				.findDocDetalle_TipoEdo(doc_detalleBorrdor);
		return doc_detalleBorrdor;
	}

	public Doc_detalle getDocDetalleRevisado(Doc_detalle d) {
		Doc_estado doc_edo = new Doc_estado();
		doc_edo.setCodigo(Utilidades.getRevisado());
		Doc_detalle doc_detalleRevisado = d;
		doc_detalleRevisado.setDoc_estado(doc_edo);
		doc_detalleRevisado = delegado
				.findDocDetalle_TipoEdo(doc_detalleRevisado);
		return doc_detalleRevisado;
	}

	private Doc_detalle[] createDetalles(ArrayList doc_detalles) {
		Doc_detalle[] result = new Doc_detalle[doc_detalles.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (Doc_detalle) doc_detalles.get(i);
		}

		return result;
	}

	/*
	 * private SimpleCity[] createCities(String[] names) { SimpleCity[] result =
	 * new SimpleCity[names.length]; for (int i = 0; i < result.length; i++) {
	 * result[i] = new SimpleCity(names[i]); } return result; }
	 */
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

	public boolean isSwPuedeRealizarFlujo() {
		return swPuedeRealizarFlujo;
	}

	public void setSwPuedeRealizarFlujo(boolean swPuedeRealizarFlujo) {
		this.swPuedeRealizarFlujo = swPuedeRealizarFlujo;
	}

	public boolean isSwIsObsoleto() {
		return swIsObsoleto;
	}

	public void setSwIsObsoleto(boolean swIsObsoleto) {
		this.swIsObsoleto = swIsObsoleto;
	}

	public boolean isSwActualizar() {
		return swActualizar;
	}

	public void setSwActualizar(boolean swActualizar) {
		this.swActualizar = swActualizar;
	}

	public boolean isSwCheckOut() {
		return swCheckOut;
	}

	public void setSwCheckOut(boolean swCheckOut) {
		this.swCheckOut = swCheckOut;
	}

	public boolean isSwExecuteActualizar() {
		swExecuteActualizar = session.getAttribute("actualizarDoc") != null;
		return swExecuteActualizar;
	}

	public void setSwExecuteActualizar(boolean swExecuteActualizar) {
		this.swExecuteActualizar = swExecuteActualizar;
	}

	public Doc_detalle getDoc_detalle_aux() {
		return doc_detalle_aux;
	}

	public void setDoc_detalle_aux(Doc_detalle doc_detalle_aux) {
		this.doc_detalle_aux = doc_detalle_aux;
	}

	public boolean isSwCopy() {
		return swCopy;
	}

	public void setSwCopy(boolean swCopy) {
		this.swCopy = swCopy;
	}

	public boolean isSwCute() {
		return swCute;
	}

	public void setSwCute(boolean swCute) {
		this.swCute = swCute;
	}

	public boolean isSwLocked() {
		return swLocked;
	}

	public void setSwLocked(boolean swLocked) {
		this.swLocked = swLocked;
	}

	public boolean isSwLockedwithkey() {
		return swLockedwithkey;
	}

	public void setSwLockedwithkey(boolean swLockedwithkey) {
		this.swLockedwithkey = swLockedwithkey;
	}

	public boolean isSwPage() {
		return swPage;
	}

	public void setSwPage(boolean swPage) {
		this.swPage = swPage;
	}

	public boolean isSwUnlocked() {
		return swUnlocked;
	}

	public void setSwUnlocked(boolean swUnlocked) {
		this.swUnlocked = swUnlocked;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public String getPublicoStr() {
		return publicoStr;
	}

	public void setPublicoStr(String publicoStr) {
		this.publicoStr = publicoStr;
	}

	public boolean isSiEsVigentepuedeSerPublico() {
		try {
			// verificamos si tienes permisologia para publicar el documento
			swPublico = false;
			if (seguridadTree.isToDoPublico() || swSuperUsuario) {
				swPublico = true;
			}

			siEsVigentepuedeSerPublico = siEsVigentepuedeSerPublico
					&& swPublico;

			// si vengo de la lista publico maestra, este no debe poder
			// modificar
			if (session.getAttribute("listaDepublico") != null) {
				siEsVigentepuedeSerPublico = false;
			}

		} catch (Exception e) {

		}
		return siEsVigentepuedeSerPublico;

	}

	public void setSiEsVigentepuedeSerPublico(boolean siEsVigentepuedeSerPublico) {
		this.siEsVigentepuedeSerPublico = siEsVigentepuedeSerPublico;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public HashMap getCarpetasMasivas() {
		return carpetasMasivas;
	}

	public void setCarpetasMasivas(HashMap carpetasMasivas) {
		this.carpetasMasivas = carpetasMasivas;
	}

	public List getArchNoCargados() {
		return archNoCargados;
	}

	public void setArchNoCargados(List archNoCargados) {
		this.archNoCargados = archNoCargados;
	}

	public HashMap getQueTipoContextType() {
		return queTipoContextType;
	}

	public void setQueTipoContextType(HashMap queTipoContextType) {
		this.queTipoContextType = queTipoContextType;
	}

	public boolean isSwFlujoActivo() {
		return swFlujoActivo;
	}

	public void setSwFlujoActivo(boolean swFlujoActivo) {
		this.swFlujoActivo = swFlujoActivo;
	}

	public boolean isSwVincularDocumento() {
		return swVincularDocumento;
	}

	public void setSwVincularDocumento(boolean swVincularDocumento) {
		this.swVincularDocumento = swVincularDocumento;
	}

	public boolean isSwPublico() {
		return swPublico;
	}

	public void setSwPublico(boolean swPublico) {
		this.swPublico = swPublico;
	}

	public boolean isSwMover() {
		return swMover;
	}

	public void setSwMover(boolean swMover) {
		this.swMover = swMover;
	}

	public boolean isSwRegistro() {
		return swRegistro;
	}

	public void setSwRegistro(boolean swRegistro) {
		this.swRegistro = swRegistro;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getUsuariosInFlowStr() {
		return usuariosInFlowStr;
	}

	public void setUsuariosInFlowStr(String usuariosInFlowStr) {
		this.usuariosInFlowStr = usuariosInFlowStr;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public HtmlSelectOneMenu getSelectOneMenu1() {
		return selectOneMenu1;
	}

	public void setSelectOneMenu1(HtmlSelectOneMenu selectOneMenu1) {
		this.selectOneMenu1 = selectOneMenu1;
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public Doc_maestro getDoc_maestroModifica() {
		return doc_maestroModifica;
	}

	public void setDoc_maestroModifica(Doc_maestro doc_maestroModifica) {
		this.doc_maestroModifica = doc_maestroModifica;
	}

	public Doc_detalle getDoc_detalleModifica() {
		return doc_detalleModifica;
	}

	public void setDoc_detalleModifica(Doc_detalle doc_detalleModifica) {
		this.doc_detalleModifica = doc_detalleModifica;
	}

	public HtmlInputText getName1() {
		return name1;
	}

	public void setName1(HtmlInputText name1) {
		this.name1 = name1;
	}

	public HtmlInputText getMayor_ver() {
		return mayor_ver;
	}

	public void setMayor_ver(HtmlInputText mayor_ver) {
		this.mayor_ver = mayor_ver;
	}

	public HtmlInputText getMinor_ver() {
		return minor_ver;
	}

	public void setMinor_ver(HtmlInputText minor_ver) {
		this.minor_ver = minor_ver;
	}

	public HtmlInputText getNumconsecutivo() {
		return numconsecutivo;
	}

	public void setNumconsecutivo(HtmlInputText numconsecutivo) {
		this.numconsecutivo = numconsecutivo;
	}

	public HtmlSelectOneMenu getDuenio() {
		return duenio;
	}

	public void setDuenio(HtmlSelectOneMenu duenio) {
		this.duenio = duenio;
	}

	public HtmlInputTextarea getDesc() {
		return desc;
	}

	public void setDesc(HtmlInputTextarea desc) {
		this.desc = desc;
	}

	public HtmlInputTextarea getDesc2() {
		return desc2;
	}

	public void setDesc2(HtmlInputTextarea desc2) {
		this.desc2 = desc2;
	}

	public boolean isSwMod() {
		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
		// OBTENEMOS SEGURIDAD;
		seguridadTree = super.getSeguridadTree(treeNodoActual);
		swMod = false;

		if (seguridadTree != null && seguridadTree.isToMod() || swSuperUsuario) {
			swMod = true;
		}

		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public Role getRoleParaPermisos() {
		return roleParaPermisos;
	}

	public void setRoleParaPermisos(Role roleParaPermisos) {
		this.roleParaPermisos = roleParaPermisos;
	}

	public Object getTreeData() {

		Doc_historicoActivoMaestro doc_historicoActivoMaestro = new Doc_historicoActivoMaestro();
		doc_historicoActivoMaestro.setStatus(Utilidades.isActivo());
		doc_historicoActivoMaestro.setDoc_maestro(doc_maestro);
		List<Doc_historicoActivoMaestro> doc_historicoActivoMaestros = delegado
				.findAllDoc_historicoActivoMaestro(doc_historicoActivoMaestro);

		// TreeNode treeData = new TreeNodeBase(
		// Utilidades.getIdentificaRaizTree(),
		// messages.getString("doc_hist") + " ", false);

		if (doc_historicoActivoMaestros != null
				&& !doc_historicoActivoMaestros.isEmpty()) {
			// treeData = new TreeNodeBase(Utilidades.getIdentificaRaizTree(),
			// messages.getString("doc_hist") + " "
			// + getDoc_maestro().getNombre(), false);

			for (Doc_historicoActivoMaestro doc_historico : doc_historicoActivoMaestros) {
				// obtenemos arreglos de trenodes de todos los cambios en
				// detalle que se hicxieron
				// para agregarlos luego como collectyions
				getInfDetlladaHist(doc_historico);

				// se continua
				doc_historicoActivoMaestro = new Doc_historicoActivoMaestro();
				doc_historicoActivoMaestro.setStatus(Utilidades.isActivo());
				doc_historicoActivoMaestro.setDoc_maestro(doc_historico
						.getDoc_maestro());
				doc_historicoActivoMaestro.setUsuario(doc_historico
						.getUsuario());
				doc_historicoActivoMaestro = delegado
						.findDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
				// TreeNode treeDetalle = new TreeNodeBase("", "", false);
				// treeDetalle.setType(Utilidades.getIdentifica1Tree());
				// treeDetalle.setIdentifier(String.valueOf(doc_historico
				// .getUsuario().getId()));
				String cargo = doc_historico.getUsuario().getCargo() != null ? doc_historico
						.getUsuario().getCargo().getNombre()
						: "";
				// treeDetalle.setDescription(doc_historico.getUsuario()
				// .getNombre()
				// + " "
				// + doc_historico.getUsuario().getApellido()
				// + "["
				// + cargo + "]");
				// treeDetalle.setLeaf(false);
				// treeData.getChildren().add(treeDetalle);
				//
				// // vemos Actualizar Borrador
				// TreeNode treeDetalle1 = new TreeNodeBase("", "", false);
				// treeDetalle1.setType(Utilidades.getIdentifica2Tree());
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// );
				// treeDetalle1.setDescription(messages
				// .getString("actualizaBorrador"));
				// treeDetalle1.setLeaf(false);
				// treeDetalle.getChildren().add(treeDetalle1);
				// treeDetalle1.getChildren().addAll(hist_actuBorradors);
				//
				// TreeNode treeDetalle2 = new TreeNodeBase("", "", false);
				// treeDetalle2.setType(Utilidades.getIdentifica3Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle2.setDescription(messages.getString("doc_publico"));
				// treeDetalle2.setLeaf(false);
				// treeDetalle2.getChildren().addAll(hist_darPublicos);
				// treeDetalle.getChildren().add(treeDetalle2);
				//
				// TreeNode treeDetalle3 = new TreeNodeBase("", "", false);
				// treeDetalle3.setType(Utilidades.getIdentifica4Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle3.setDescription(messages
				// .getString("doc_verdocumento"));
				// treeDetalle3.setLeaf(false);
				// treeDetalle3.getChildren().addAll(hist_verDetalless);
				// treeDetalle.getChildren().add(treeDetalle3);
				//
				// TreeNode treeDetalle4 = new TreeNodeBase("", "", false);
				// treeDetalle4.setType(Utilidades.getIdentifica4Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle4.setDescription(messages
				// .getString("vincular_documentos"));
				// treeDetalle4.setLeaf(false);
				// treeDetalle4.getChildren().addAll(hist_verVinculadoss);
				// treeDetalle.getChildren().add(treeDetalle4);
				//
				// TreeNode treeDetalle5 = new TreeNodeBase("", "", false);
				// treeDetalle5.setType(Utilidades.getIdentifica5Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle5.setDescription(messages.getString("flow_someter"));
				// treeDetalle5.setLeaf(false);
				// treeDetalle5.getChildren().addAll(hist_verSometerFlows);
				// treeDetalle.getChildren().add(treeDetalle5);
				//
				// TreeNode treeDetalle6 = new TreeNodeBase("", "", false);
				// treeDetalle6.setType(Utilidades.getIdentifica6Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle6.setDescription(messages
				// .getString("doc_version_nueva"));
				// treeDetalle6.setLeaf(false);
				// treeDetalle6.getChildren().addAll(hist_nuevaVerVigs);
				// treeDetalle.getChildren().add(treeDetalle6);
				//
				// TreeNode treeDetalle7 = new TreeNodeBase("", "", false);
				// treeDetalle7.setType(Utilidades.getIdentifica7Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle7.setDescription(messages
				// .getString("doc_desbloquear"));
				// treeDetalle7.setLeaf(false);
				// treeDetalle7.getChildren().addAll(hist_deshNuevaVers);
				// treeDetalle.getChildren().add(treeDetalle7);
				//
				// TreeNode treeDetalle8 = new TreeNodeBase("", "", false);
				// treeDetalle8.setType(Utilidades.getIdentifica8Tree());
				// //
				// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
				// // );
				// treeDetalle8.setDescription(messages.getString("registro"));
				// treeDetalle8.setLeaf(false);
				// treeDetalle8.getChildren().addAll(hist_genRegs);
				// treeDetalle.getChildren().add(treeDetalle8);

			}
		}

		return treeData;
	}

	public void getInfDetlladaHist(Doc_historicoActivoMaestro doc_historico) {

		hist_actuBorradors = new ArrayList<Object>();
		hist_darPublicos = new ArrayList<Object>();
		hist_verDetalless = new ArrayList<Object>();
		hist_verVinculadoss = new ArrayList<Object>();
		hist_verSometerFlows = new ArrayList<Object>();
		hist_nuevaVerVigs = new ArrayList<Object>();
		;
		hist_deshNuevaVers = new ArrayList<Object>();
		hist_genRegs = new ArrayList<Object>();
		Doc_historicoActivoDetalle doc_historicoActivoDetalle = new Doc_historicoActivoDetalle();
		doc_historicoActivoDetalle.setDoc_histActMaestro(doc_historico);
		List<Doc_historicoActivoDetalle> doc_historicoActivoDetalles = delegado
				.findAllDoc_historicoActivoDetalle(doc_historicoActivoDetalle);
		for (Doc_historicoActivoDetalle doc_historicoDetalle : doc_historicoActivoDetalles) {
			Object treeDetalle = null;// new TreeNodeBase("", "", false);
			// treeDetalle.setType(Utilidades.getIdentifica9Tree());
			// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
			// );
			String fecha = Utilidades.sdfShow.format(doc_historicoDetalle
					.getFecha());
			String comentario = doc_historicoDetalle.getComentario() != null ? doc_historicoDetalle
					.getComentario() : "";
			// treeDetalle.setDescription(fecha + " " + comentario);
			// treeDetalle.setLeaf(false);
			if (doc_historicoDetalle.isActuBorrador()) {
				hist_actuBorradors.add(treeDetalle);
			}
			if (doc_historicoDetalle.isDarPublico()) {
				hist_darPublicos.add(treeDetalle);
			}
			if (doc_historicoDetalle.isVerDetalles()) {
				hist_verDetalless.add(treeDetalle);
			}
			if (doc_historicoDetalle.isVerVinculados()) {
				hist_verVinculadoss.add(treeDetalle);
			}
			if (doc_historicoDetalle.isVerSometerFlow()) {
				hist_verSometerFlows.add(treeDetalle);
			}
			if (doc_historicoDetalle.isNuevaVerVig()) {
				hist_nuevaVerVigs.add(treeDetalle);
			}
			if (doc_historicoDetalle.isDeshNuevaVer()) {
				hist_deshNuevaVers.add(treeDetalle);
			}
			if (doc_historicoDetalle.isGenReg()) {
				hist_genRegs.add(treeDetalle);
			}
		}

		// return treeData;
	}

	public boolean isSwHistFlow() {
		return swHistFlow;
	}

	public void setSwHistFlow(boolean swHistFlow) {
		this.swHistFlow = swHistFlow;
	}

	public boolean isSwHistDoc() {
		return swHistDoc;
	}

	public void setSwHistDoc(boolean swHistDoc) {
		this.swHistDoc = swHistDoc;
	}

	public boolean isSwPermGrupo() {
		return swPermGrupo;
	}

	public void setSwPermGrupo(boolean swPermGrupo) {
		this.swPermGrupo = swPermGrupo;
	}

	public boolean isSwHeredarPermisos() {
		return swHeredarPermisos;
	}

	public void setSwHeredarPermisos(boolean swHeredarPermisos) {
		this.swHeredarPermisos = swHeredarPermisos;
	}

	public boolean isSwDeshabiltarBtn() {
		return swDeshabiltarBtn;
	}

	public void setSwDeshabiltarBtn(boolean swDeshabiltarBtn) {
		this.swDeshabiltarBtn = swDeshabiltarBtn;
	}

	public Integer getDocVersionMayorMasivo() {
		return docVersionMayorMasivo;
	}

	public void setDocVersionMayorMasivo(Integer docVersionMayorMasivo) {
		this.docVersionMayorMasivo = docVersionMayorMasivo;
	}

	public Integer getDocVersionMenorMasivo() {
		return docVersionMenorMasivo;
	}

	public void setDocVersionMenorMasivo(Integer docVersionMenorMasivo) {
		this.docVersionMenorMasivo = docVersionMenorMasivo;
	}

	public List<ClienteDocumentoDetalle> getDoc_detalles2() {
		return doc_detalles2;
	}

	public void setDoc_detalles2(List<ClienteDocumentoDetalle> doc_detalles2) {
		this.doc_detalles2 = doc_detalles2;
	}

	public HtmlDataTable getMyDataTable() {
		return myDataTable;
	}

	public void setMyDataTable(HtmlDataTable myDataTable) {
		this.myDataTable = myDataTable;
	}

	public ClienteDocumentoDetalle getClienteDocumentoDetalle() {
		return clienteDocumentoDetalle;
	}

	public void setClienteDocumentoDetalle(
			ClienteDocumentoDetalle clienteDocumentoDetalle) {
		this.clienteDocumentoDetalle = clienteDocumentoDetalle;
	}

	public List<ClienteDocumentoMaestro> getPanelControl() {
		// ESTO VIENE DE getClienteDocumentoMaestros QUE A SU VES VIENE DE
		// getDoc_maestros
		List<ClienteDocumentoMaestro> panels = session
				.getAttribute("clienteDocumentoMaestrosPanelControl") != null ? (List<ClienteDocumentoMaestro>) session
				.getAttribute("clienteDocumentoMaestrosPanelControl") : null;
		if (panels == null) {
			panels = new ArrayList<ClienteDocumentoMaestro>();
		}
		panelControl = panels;
		return panelControl;
	}

	public List<ClienteDocumentoMaestro> getClienteDocumentoMaestros() {
		try {

			// INICIO PARA CHEQUEAR SI HAY FLUJOS PARALELOS PLANTILAS,SI NO , NO
			// SALEN LAS PLANTILLAS
			inicializar();
			session.removeAttribute("listReferenciaUnavez");
			// FIN PARA CHEQUEAR SI HAY FLUJOS PARALELOS PLANTILAS,SI NO , NO
			// SALEN LAS PLANTILLAS

			if (session.getAttribute("clienteDocumentoMaestros") == null) {

				clienteDocumentoMaestros = getDoc_maestros();
				session.setAttribute("clienteDocumentoMaestrosPanelControl",
						clienteDocumentoMaestros);
				session.setAttribute("clienteDocumentoMaestros",
						clienteDocumentoMaestros);
			} else {
				clienteDocumentoMaestros = (List<ClienteDocumentoMaestro>) session
						.getAttribute("clienteDocumentoMaestros");
				session.setAttribute("clienteDocumentoMaestrosPanelControl",
						clienteDocumentoMaestros);
			}

		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clienteDocumentoMaestros;
	}

	public void setClienteDocumentoMaestros(
			List<ClienteDocumentoMaestro> clienteDocumentoMaestros) {
		this.clienteDocumentoMaestros = clienteDocumentoMaestros;
	}

	public Map<Long, Boolean> getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(Map<Long, Boolean> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public boolean isSwHayPlantillasFlujosparalelos() {

		return swHayPlantillasFlujosparalelos;
	}

	public void setSwHayPlantillasFlujosparalelos(
			boolean swHayPlantillasFlujosparalelos) {
		this.swHayPlantillasFlujosparalelos = swHayPlantillasFlujosparalelos;
	}

	public void setDoc_detallePrincipal(
			ClienteDocumentoDetalle doc_detallePrincipal) {
		this.doc_detallePrincipal = doc_detallePrincipal;
	}

	public boolean isSwImprimir() {
		return swImprimir;
	}

	public void setSwImprimir(boolean swImprimir) {
		this.swImprimir = swImprimir;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	public List<Role> getRoles() {
		boolean usadoParaCrearFlujo = true;
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();
		roles = delegado.findAll_Roles(user_logueado, usadoParaCrearFlujo);
		RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados = null;
		List<Role> rolesLst = new ArrayList<Role>();
		participantesGruposPlantila = participantesGruposPlantila(roles);
		for (Role r : roles) {
			rolesOnlyViewDocPublicados = new RolesOnlyViewDocPublicados();
			rolesOnlyViewDocPublicados.setRole(r);
			rolesOnlyViewDocPublicados.setDoc_detalle(doc_detalle);
			if (delegado
					.findAllRolesOnlyViewDocPublicados(rolesOnlyViewDocPublicados) != null) {

				selectedIds.put(r.getCodigo(), true);
			}
			rolesLst.add(r);
		}

		return rolesLst;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isSwPublicoEstaElDocumento() {
		return swPublicoEstaElDocumento;
	}

	public void setSwPublicoEstaElDocumento(boolean swPublicoEstaElDocumento) {
		this.swPublicoEstaElDocumento = swPublicoEstaElDocumento;
	}

	public PublicadosUsuComent getPublicadosUsuComent() {
		return publicadosUsuComent;
	}

	public void setPublicadosUsuComent(PublicadosUsuComent publicadosUsuComent) {
		this.publicadosUsuComent = publicadosUsuComent;
	}

	public void inicalizaPublicaComentario() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();

		// se pregunta si ha editado este doc_detalle
		publicadosUsuComent = new PublicadosUsuComent();
		publicadosUsuComent.setDoc_detalle(doc_detalle);
		publicadosUsuComent.setUsuario(user_logueado);
		publicadosUsuComent = delegado
				.findOnePublicadosUsuComent(publicadosUsuComent);
		// si nunca lo ha editado , lo crea..
		if (publicadosUsuComent == null) {
			publicadosUsuComent = new PublicadosUsuComent();
			publicadosUsuComent.setDoc_detalle(doc_detalle);
			publicadosUsuComent.setUsuario(user_logueado);
			publicadosUsuComent.setFecha(new Date());
			File file = null;
			try {
				file = saveFileInDisk(doc_detalle, doc_detalle.getData()
						.getBinaryStream(), doc_detalle.getNameFile());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			publicadosUsuComent.setFile(file);
			if (file != null) {
				Archivo ar = new Archivo();

				configuraciones = delegado.find_allConfiguracion();
				if (configuraciones != null && configuraciones.size() > 0) {
					conf = configuraciones.get(0);

					swPostgresVieneDeConfiguracion = conf.isBdpostgres();
					swConfiguracionHayData = true;
				}
				if (swConfiguracionHayData) {
					if (swPostgresVieneDeConfiguracion) {
						publicadosUsuComent.setData_doc_postgres(ar
								.convertirFileADataBinariaDinamico(file));
					}
				} else if ("1".equalsIgnoreCase(confmessages
						.getString("bdpostgres"))) {
					publicadosUsuComent.setData_doc_postgres(ar
							.convertirFileADataBinariaDinamico(file));
				}

				try {
					publicadosUsuComent.setData_doc(Hibernate.createBlob(ar
							.fileToinputStream(file)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// grabamos al usuario
				delegado.create(publicadosUsuComent);
				// lo buscamos para ubicarlo
				publicadosUsuComent = delegado
						.findOnePublicadosUsuComent(publicadosUsuComent);
			}
		}

		session.setAttribute("publicadosUsuComent", publicadosUsuComent);
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

	public String publicadoComentario() {

		inicalizaPublicaComentario();
		// /ESTE SE VA DIRECTAMENTE A LÑA PAGINA publicadoComentario.XHTML sin
		// pasar por facelets
		return "publicadoComentario";
	}

	public String savePublicadoComentario() {
		String pagIr = "";

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();

		if (isEmptyOrNull(publicadosUsuComent.getComentario())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));

			return "";

		}
		if (publicadosUsuComent != null
				&& (!isEmptyOrNull(publicadosUsuComent.getComentario()))) {
			// solo guardamos el comentario, no sobreescribimos el doc_data , no
			// el de doc_postgres
			String comentario = publicadosUsuComent.getComentario();
			inicalizaPublicaComentario();
			publicadosUsuComent = session.getAttribute("publicadosUsuComent") != null ? (PublicadosUsuComent) session
					.getAttribute("publicadosUsuComent") : null;

			if (publicadosUsuComent != null) {
				publicadosUsuComent.setComentario(comentario);
				delegado.edit(publicadosUsuComent);
			}

			session.setAttribute("parametro", "publico");

			// session.setAttribute("pagIr", Utilidades.getListarAplicacion());

			pagIr = "listarDocumentoSearchPublicados";// Utilidades.getFinexitoso();

		}

		return pagIr;

	}

	public String cancelarPublicadoComentario() {
		session.removeAttribute("publicadosUsuComent");
		return "listarDocumentoSearchPublicados";

	}

	public String verDocumentoDesdePublico() {

		// ENCONTRAMOS EL ULTIMO.. HACIA SEA VIGENTE POUBLICO, PUEDE QUE EL
		// ULTIMO ESTE BORRADOR
		doc_detalle = session.getAttribute("doc_detalle") != null ? (Doc_detalle) session
				.getAttribute("doc_detalle") : new Doc_detalle();
		session.setAttribute("treeNodoActual", doc_detalle.getDoc_maestro()
				.getTree());
		super.clearSession(session, confmessages.getString("usuarioSession"));

		return "listar";
	}

	public boolean isSwIsPublicador() {
		return swIsPublicador;
	}

	public void setSwIsPublicador(boolean swIsPublicador) {
		this.swIsPublicador = swIsPublicador;
	}

	public List<PublicadosUsuComent> getPublicadosUsuComentLst() {
		if (session.getAttribute("publicadosUsuComentLst") != null) {
			publicadosUsuComentLst = (List<PublicadosUsuComent>) session
					.getAttribute("publicadosUsuComentLst");
		}
		return publicadosUsuComentLst;
	}

	public void setPublicadosUsuComentLst(
			List<PublicadosUsuComent> publicadosUsuComentLst) {
		this.publicadosUsuComentLst = publicadosUsuComentLst;
	}

	public void setPanelControl(List<ClienteDocumentoMaestro> panelControl) {
		this.panelControl = panelControl;
	}

	public String modificarDocumentoRichFaces(Doc_detalle doc_detalle) {
		String pagIr = "";
		boolean swConsecutivoRepite = false;
		treeNodoActual = doc_detalle.getDoc_maestro().getTree();
		// session.getAttribute("treeNodoActual") != null ? (Tree) session
		// .getAttribute("treeNodoActual") : null;
		try {

			if (doc_detalle.getDoc_maestro().getConsecutivo() != null) {
				if (doc_detalle.getDoc_maestro().getConsecutivo() != null) {
					String numConsec = "";
					numConsec = (String) doc_detalle.getDoc_maestro()
							.getConsecutivo();
					numConsec = numConsec.trim();
					numConsec = super.numSacopDo("", numConsec.trim(),
							Utilidades.getConsecutivoLength());

					// si el consecutivo no es nulo, y no es el mismo al que
					// trae el documento
					if (!super.isEmptyOrNull(numConsec)) {
						// chequeamos que en el propio arbol o registros del
						// arbol no esten repetidos
						doc_detalle.getDoc_maestro().setConsecutivo(
								doc_detalle.getDoc_maestro().getConsecutivo()
										.trim());
						List<Doc_maestro> doc_maestros = delegado
								.consecutivoseRepite(
										doc_detalle.getDoc_maestro(),
										treeNodoActual);

						if (doc_maestros != null && !doc_maestros.isEmpty()
								&& doc_maestros.size() > 0) {
							swConsecutivoRepite = true;
							// return "";
						} else {
							doc_detalle.getDoc_maestro().setConsecutivo(
									doc_detalle.getDoc_maestro()
											.getConsecutivo().trim());
						}
					}

				}
			}

			if (swConsecutivoRepite) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_consecutivo")));
			} else {

				delegado.editMaestro(doc_detalle.getDoc_maestro());
				delegado.editDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro",
						doc_detalle.getDoc_maestro());
				if (doc_detalle.getDoc_maestro().isPublico()) {
					// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					boolean darPublico = true;
					super.guardamosHistoricoActivoDelDocumento(user_logueado,
							doc_maestro, false, darPublico, false, false,
							false, false, false, false, publicoStr);
					// ______________________________________________________
				}
				if (doc_detalle.getDoc_maestro() != null
						&& doc_detalle.getDoc_maestro().getTree() != null) {

					Tree tree = doc_detalle.getDoc_maestro().getTree();
					// ________________________________________________________________________________
					// damos permisos al role que se escojio
					if (roleParaPermisos != null && swPermGrupo) {
						Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
						seguridad_Role_Lineal.setRole(roleParaPermisos);
						seguridad_Role_Lineal.setTree(tree);
						// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
						// BUSCAMOS SU
						// SEGURIDA
						List<Seguridad_Role_Lineal> seguridad_Role_LinealList = delegado
								.findAllSeguridad_Role_Identificador(roleParaPermisos);
						if (seguridad_Role_LinealList != null
								&& !seguridad_Role_LinealList.isEmpty()) {
							seguridad_Role_Lineal = seguridad_Role_LinealList
									.get(0);
							seguridad_Role_Lineal.setRole(roleParaPermisos);
							seguridad_Role_Lineal.setTree(tree);
						}
						// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
						// para que sea totalmente nuevo y no traiga el primary
						// key
						// viejo
						Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
								seguridad_Role_Lineal);
						delegado.create(seguridad_Role_Lineal2);
					//	givePermparaverToGroup(seguridad_Role_Lineal2);
						// ________________________________________________________________________________
					}

					// permisologia patra el usuario logueado y el diuenio
					// permisologia patra el usuario logueado y el diuenio
					Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(user_logueado);
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

					seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(doc_detalle.getDuenio());
					seguridad_User_Lineal.setTree(tree);
					seguridad_User_Lineal.setSwHereda(swHeredarPermisos);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
				}
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", Utilidades.getListarAplicacion());

				session.setAttribute("pagIr", Utilidades.getListarDocumentos());
				pagIr = Utilidades.getFinexitoso();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
			}
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public List<Doc_detalle> getDoc_detalles() throws EcologicaExcepciones {
		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
		// ENCONTRAMOS EL AREA DEL DOCUMENTO
		Tree area = null;
		area = delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(treeNodoActual,
				Utilidades.getNodoArea());
		// OBTENEMOS SEGURIDAD;
		seguridadTree = super.getSeguridadTree(treeNodoActual);

		usuariosInFlowStr = "";
		String areaStr = "";
		String tipoFormato = "";
		// aqui verificamos seguridad de documentos vinculados, documento
		// punblico etc

		if (treeNodoActual != null) {
			doc_maestros = delegado.findAllDoc_maestros(treeNodoActual);
			// DEBERIAMOS TENER SIEMPRE UN SOLO MAESTRO
			int size = doc_maestros.size();
			int k = -1;
			for (Doc_maestro doc : doc_maestros) {
				++k;
				// se le agrega plantilla al nombre del tipo de documento si es
				// plantilla
				if (doc.getDoc_tipo() != null
						&& doc.getDoc_tipo().getFormatoTipoDoc() != null) {
					if (Utilidades.getFormatoTipoDoc()
							- doc.getDoc_tipo().getFormatoTipoDoc() == 0) {
						StringBuffer plantilla = new StringBuffer("");
						plantilla.append("[")
								.append(messages.getString("formato"))
								.append("]");
						Doc_tipo t = new Doc_tipo();
						t.setCodigo(doc.getDoc_tipo() != null ? doc
								.getDoc_tipo().getCodigo() : 0);
						t = delegado.findDocTipo(t);
						doc.getDoc_tipo().setNombre(
								t.getNombre() + plantilla.toString());
					}
				}

				List<Doc_detalle> doc_detalles = null;
				String publico = session.getAttribute("parametro") != null ? (String) session
						.getAttribute("parametro") : "";
				doc_detalles = delegado.encontrarDetalles(doc.getCodigo(),
						publico);

				if (doc_detalles != null) {
					// Iterator it = doc_detalles.iterator();
					boolean swSoloEncabezadoTieneBotones = true;
					int elPrimeroVaInmaestro = 0;
					Doc_detalle doc_detalleVigente = new Doc_detalle();
					doc_detalles2.clear();
					doc_detalles2_2 = new ArrayList<Doc_detalle>();
					doc_detalles2_2.clear();
					for (Doc_detalle d : doc_detalles) {
						// while (it.hasNext()) {
						elPrimeroVaInmaestro++;

						// solo un maestro se agarra, los demas son identicos
						if (k == 0) {
							// COLOCAMOS EL AREA DEL DOCUMENTO + EL CONSECUTIVO
							areaStr = d.getAreaDocumentos() != null ? d
									.getAreaDocumentos().getNombre() : "";
							tipoFormato = doc.getDoc_tipo().getAbreviatura() != null ? doc
									.getDoc_tipo().getAbreviatura() : doc
									.getDoc_tipo().getNombre();

							doc.setConsecutivo(doc.getConsecutivo());
							// verificamos si colocamos etiqueta publico o
							// publicar
							publicoEs_Doc_Maestro(doc);
							boolean nombreCorto = true;
							String prefijo = "";
							prefijo = getPrefijo(doc.getTree(), prefijo,
									nombreCorto);
							doc.getTree().setPrefix(prefijo);
							setDoc_maestro(doc);
						}

						// SI EL PRIMER DOCUMENTO ES OBSOLETO.. LO OBVIAMOS..
						// DEBE SER
						// O BORRADOR O VIGENTE..LO COLOCAMOS COMO BORRADORs
						Doc_estado doc_edo = new Doc_estado();
						doc_edo.setCodigo(Utilidades.getObsoleto());
						doc_edo = delegado.findDocEstado(doc_edo);
						if (elPrimeroVaInmaestro == 1
								&& d.getDoc_estado().getCodigo()
										.equals(doc_edo.getCodigo())) {
							doc_edo = new Doc_estado();
							doc_edo.setCodigo(Utilidades.getBorrador());
							doc_edo = delegado.findDocEstado(doc_edo);
							d.setDoc_estado(doc_edo);
							delegado.editDetalle(d);

						}
						// FIN SI EL PRIMER DOCUMENTO ES OBSOLETO.. LO
						// OBVIAMOS..

						swPuedeRealizarFlujo = false;
						swIsObsoleto = false;
						swActualizar = false;
						swCheckOut = false;
						swCopy = false;
						swPage = false;
						swCute = false;
						// si hay uno solo doc detalle, se agarra por defecto
						// un borrador y se va hacer workflows
						swLocked = d.isDoc_checkout();
						swLockedwithkey = false;
						swUnlocked = false;
						Doc_detalle doc_detalle_copy = null;
						// __________________________________________________
						// hacemos un parcxh, si el flow no dice que tipo es,
						// esta mal hecho
						// lo borramos de flowParticiapntes, flow referencia
						// ro,e y flow
						List<Flow> flowActivoMalos = delegado.findByFlowBad(d);
						for (Flow f : flowActivoMalos) {
							borrarFlujos(f);
						}
						// ______________________________________________________
						// si el documento esta en flujo, agarramos el estado
						// del flujo
						d.setOrigen(Utilidades.getOrigenDocumentoFlow());
						List flowActivo = delegado.findByFlowByOrigen(d);

						// es un solo registro y puede ser el borrador o un
						// dlcumento vigente
						if (swSoloEncabezadoTieneBotones) {
							swCopy = true;
							swCute = true;
							swVincularDocumento = false;
							if ((d.getDoc_estado().getCodigo() - Utilidades
									.getVigente()) == 0
									|| (d.getDoc_estado().getCodigo() - Utilidades
											.getAprobado()) == 0) {
//								if (seguridadTree.isToVincDoc()
//										|| swSuperUsuario) {
								if (seguridadTree.isToView()
										|| swSuperUsuario) {
									swVincularDocumento = true;
								}
							}
							// _______________________________________________________
							// seguridad historico
							swHistFlow = false;
							if (seguridadTree != null
									&& seguridadTree.isToHistFlow()
									|| swSuperUsuario) {
								swHistFlow = true;
							}

							swHistDoc = false;
							if (seguridadTree != null
									&& seguridadTree.isToHistDoc()
									|| swSuperUsuario) {
								swHistDoc = true;
							}
							// _______________________________________________________
							// buscamos del actual detalle, el detalle vigente

							doc_detalleVigente.setCodigo(d.getCodigo());
							doc_detalleVigente = getDocDetalleVigente(doc_detalleVigente);
							// ________________________________________________________________________________
							// SI PASA, EL DOCUMENTO ES VIGENTE
							if (doc_detalleVigente != null) {
								swLocked = doc_detalleVigente.isDoc_checkout();
								// __________________________________________________________________________________//
								// buscamos si hay vigentes ya sean en checkout
								// o no..para activar estos candados
								// es el ultimo aprobado vigente
								// CANDADO DESBLOQUEADO
								if ((!doc_detalleVigente.isDoc_checkout())
										&& (doc_detalleVigente.getDoc_estado()
												.getCodigo().equals(Utilidades
												.getVigente()))) {
									/* SEGURIDAD DE CHECKOUT CON LOS CANDADOS */
									/*
									 * este icono dice que se puede realizar una
									 * nueva version
									 */
									swUnlocked = true;
								}
								// CANDADO BLOQUEADO CON LLAVE O SIN LLAVE,
								// DEPENDE PERMISO
								if ((doc_detalleVigente.isDoc_checkout())
										&& (doc_detalleVigente.getDoc_estado()
												.getCodigo().equals(Utilidades
												.getVigente()))) {
									/* SEGURIDAD DE CHECKOUT CON LOS CANDADOS */
									// sin es el duenio, lo puede desproteger
									// BLOQUEADO SE INICIALIZA
									if (user_logueado != null
											&& user_logueado.equals(d
													.getModificadoPor())) {
										// la llave para desbloquear
										swLockedwithkey = true;
									}

								}
								// si el documento se pueden hacer registros ..
								// Y SI NO STA EN FLUJO y es vigente
								if (flowActivo.isEmpty()) {
									// VALIDAMOS PARA HACER REGISTRO, SI NO ESTA
									// EN PEGAR Y SI ES TIPO FORMATO
									if ((doc.getDoc_tipo() != null)
											&& doc.getDoc_tipo()
													.getFormatoTipoDoc() == Utilidades
													.getFormatoTipoDoc()) {
										if (seguridadTree.isToDoRegistros()
												|| swSuperUsuario) {
											if (session.getAttribute("swPage") != null) {
												swRegistro = false;
											} else {
												swRegistro = true;
											}

										}

									}
								}

							}
							// ________________________________________________________________________________
							// OJO CON LOS
							// FLUJOS-----FLOWS--------------------------------------------------------
							// SI HAY UN VERSION VIGENTE Y LE DAN NUEVA
							// VERSION.. EL USUARIOMQUE HIZO ESO
							// ES EL UNICO QUE PUEDE REALIZAR FLUJO.. PORQUE EL
							// DOCUMENTO ESTA CHECKOUT POR EL
							// SI EL USUARIO DESHACE LA VERSION..YA TODOS PUEDEN
							// CREAR UNA NNUEVA VERSION
							// CON UN DOCUMENTO BORRADOR.. QUEDA CHECKOUT EL
							// DOCUMENTO
							// Y SOILO EL PODRA HACER FLUJO MIENTRAS TANTO..

							// ______________SI ESTA EN
							// FLUJO____________________________________________________________________//
							swSoloEncabezadoTieneBotones = false;
							// el documento esta en flujo
							if (!flowActivo.isEmpty()) {
								Flow flow = (Flow) flowActivo.get(0);
								// si vengo de la lista maestra para ver los
								// documentos vigentes y estoy en flujo, no me
								// cambie
								// el estado en que esta actualmnehnte que es
								// vigente
								if (!"publico".equalsIgnoreCase(publico)) {
									d.setDoc_estado(flow.getEstado());
								}

								// buscamos los participantes del flujo
								List flowParts = delegado
										.findByFlowParticipantesOrigenFlow(flow);
								Iterator itfp = flowParts.iterator();

								StringBuffer strFlowUser = new StringBuffer();
								while (itfp.hasNext()) {
									Flow_Participantes flujo_participantes = (Flow_Participantes) itfp
											.next();
									if (flujo_participantes.getFirma()
											.getCodigo()
											.equals(Utilidades.getSinFirmar())) {
										strFlowUser.append(flujo_participantes
												.getParticipante().toString());
										strFlowUser.append("\r\t");
									}

								}
								usuariosInFlowStr = strFlowUser.toString();
								swFlujoActivo = true;
								swPuedeRealizarFlujo = false;
								swIsObsoleto = true;
								swActualizar = false;
								swCheckOut = false;

								swLockedwithkey = false;
								swUnlocked = false;

							} // ______________SI NO ESTA EN
								// FLUJO____________________________________________________________________//
							else {
								// verificamos quien puede actualizar si el
								// documento es borrador
								// si un detalle es borrador, pero hay un
								// documento vigente..y es checkout
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getBorrador())) {
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {
										// preguntamos en seguridad si puede
										// realizar flujo
										swPuedeRealizarFlujo = false;
										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}

										}

										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}
										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}
											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}
											}

										}

									}

								}
								// verificamos quien puede actualizar si el
								// documento es rechazado
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getRechazado())) {
									// chequeamos si ha sido checkaout
									// anteriormente
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {

										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}

										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}
										}

										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}
											}

										}

									}

								}
								// verificamos quien puede actualizar si el
								// documento es revisado
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getRevisado())) {
									// chequeamos si ha sido checkaout
									// anteriormente
									if ((doc_detalleVigente != null)
											&& ((doc_detalleVigente
													.isDoc_checkout()))
											&& (user_logueado != null && user_logueado
													.equals(d
															.getModificadoPor()))) {
										if (seguridadTree.isToMod()
												|| swSuperUsuario) {
											swActualizar = true;
										}

										if (seguridadTree.isToDoFlow()
												|| seguridadTree
														.isToDoFlowRevision()
												|| swSuperUsuario) {
											if (!d.getDoc_estado()
													.getCodigo()
													.equals(Utilidades
															.getVigente())) {
												// por decision que todo
												// documento vigente no se puede
												// realizar fluyjos..
												// se debe crear una nueva
												// version borrador para
												// someterlo a un flujo
												swPuedeRealizarFlujo = true;
											}

										}

										swIsObsoleto = false;
										swCheckOut = false;

									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
													|| seguridadTree
															.isToDoFlowRevision()
													|| swSuperUsuario) {
												if (!d.getDoc_estado()
														.getCodigo()
														.equals(Utilidades
																.getVigente())) {
													// por decision que todo
													// documento vigente no se
													// puede realizar fluyjos..
													// se debe crear una nueva
													// version borrador para
													// someterlo a un flujo
													swPuedeRealizarFlujo = true;
												}

											}

										}

									}

								}
								// es el ultimo aprobado vigente
								if ((!d.isDoc_checkout())
										&& (d.getDoc_estado().getCodigo()
												.equals(Utilidades.getVigente()))) {
									if (seguridadTree.isToDoFlow()
											|| seguridadTree
													.isToDoFlowRevision()
											|| swSuperUsuario) {
										// REDUNDANTE ARRIBA Y ESTA CONDICION,
										// PERO NO IMPOIRTA
										if (!d.getDoc_estado()
												.getCodigo()
												.equals(Utilidades.getVigente())) {
											// por decision que todo documento
											// vigente no se puede realizar
											// fluyjos..
											// se debe crear una nueva version
											// borrador para someterlo a un
											// flujo
											swPuedeRealizarFlujo = true;
										}
									}

									if (seguridadTree.isToMod()
											|| swSuperUsuario) {
										swCheckOut = true;
									}

									swIsObsoleto = false;
									swActualizar = false;

								}
								// es una copia, hay uno que lo repreesenta como
								// borrador
								if ((d.isDoc_checkout())
										&& (d.getDoc_estado().getCodigo()
												.equals(Utilidades.getVigente()))) {
									swPuedeRealizarFlujo = false;
									swIsObsoleto = true;
									swActualizar = false;
									if (seguridadTree.isToMod()
											|| swSuperUsuario) {
										swCheckOut = true;
									}

								}
								// esta por demas ...
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getObsoleto())) {
									swPuedeRealizarFlujo = false;
									swIsObsoleto = true;
									swActualizar = false;
									swCheckOut = false;

								}

							}
						}

						// se graba con las varfiables alteradas arriba de este
						// codigo
						doc_detalle_copy = new Doc_detalle(d,
								swPuedeRealizarFlujo, swIsObsoleto,
								swActualizar, swCheckOut, swCopy, swPage,
								swCute, swLocked, swLockedwithkey, swUnlocked,
								swFlujoActivo, swVincularDocumento, swPublico,
								swMover, swRegistro, icono, usuariosInFlowStr,
								swHistDoc, swHistFlow, publicoStr);

						// el primer detalle va en maestro :)
						if (doc_detalle_copy.getDoc_estado() != null
								&& doc_detalle_copy.getDoc_estado().getNombre() != null) {
							// doc_detalle_copy.getDoc_estado().setNombre(messages.getString(doc_detalle_copy.getDoc_estado().getNombre().toLowerCase()));
							doc_detalle_copy.getDoc_estado().setNombre(
									doc_detalle_copy.getDoc_estado()
											.getNombre().toLowerCase());
						}

						// colocamos que icono llevara
						// obtenemois el icono del documento
						// icono = super.obtenIconoDoc(d.getNameFile());
						icono = super.obtenIconoDoc(doc_detalle_copy
								.getNameFile());
						doc_detalle_copy.setIcono(icono);
						// convertimos el tam,aÃ±o de bytes a mg
						if (doc_detalle_copy.getSize_doc() != 0) {
							doc_detalle_copy.setSize_doc(converBytesMbytes(Math
									.ceil(doc_detalle_copy.getSize_doc())));
						}
						if (elPrimeroVaInmaestro == 1) {

							// BUSCAMOS SEGURIDAD PARA IMPRESION
							if (seguridadTree != null
									&& ((seguridadTree.isToSolicitudImpresion()
											|| seguridadTree
													.isToSolicitudImpresion0()
											|| seguridadMenu
													.isToImprimirAdministrar() || swSuperUsuario))) {
								swImprimir = true;
								doc_detalle_copy.setSwImprimir(swImprimir);
								doc_detalle_copy
										.setSwHabilitadoImprimir(habilitadoImprimir(
												d, user_logueado));
								doc_detalle_copy
										.setSwEstaInFlowToImprimir(estaInFlowToImprimir(
												d, user_logueado));

							}
							// SI EL DOCUMENTO ES BORRADOR, SE PUEDE EDITAR
							// SI ES REVISADO O RECHAZADO .. SE PUEDE EDITAR..
							if (d.getDoc_estado().getCodigo()
									.equals(Utilidades.getBorrador())
									|| d.getDoc_estado().getCodigo()
											.equals(Utilidades.getRevisado())
									|| d.getDoc_estado().getCodigo()
											.equals(Utilidades.getRechazado())
									&& doc_detalles.size() == 1) {
								doc_detalle_copy.setSwFirstBorrador(true);
							}
							// FIN SI EL DOCUMENTO ES BORRADOR, SE PUEDE EDITAR

							// FINSEGURIDAD PARA IMPRESION

							// buscamos si expiro el publicado
							doc_detalle_copy.setSwFechaPublicoExpiro(d
									.isSwFechaPublicoExpiro());

							doc_detalle_copy.setDoc_maestro(getDoc_maestro());

							// clienteDocumentoMaestro = new
							// ClienteDocumentoMaestro(
							// publicoStr, getDoc_maestro(),
							// doc_detalle_copy);

							doc_detalle_copy.setTipoDocumento(tipoFormato);

							// clienteDocumentoMaestro
							// .setTipoDocumento(tipoFormato);

							doc_detalle_copy.setSwLocked(swLocked);

							chequearExtenion(doc_detalle_copy);
							doc_detalles2_2.add(doc_detalle_copy);
							doc_detallePrincipal_2 = doc_detalle_copy;
							doc_detallePrincipal_2
									.setDoc_maestro(getDoc_maestro());

						} else {
							chequearExtenion(doc_detalle_copy);
							// ------------------------------------------

							// estas son las versiones del docuimento
							doc_detalles2_2.add(doc_detalle_copy);
						}

					}
				}
			}
		}
		// clienteDocumentoMaestro = new
		// ClienteDocumentoMaestro(getDoc_maestro());

		// VALIDAMOS QUE SOLO SEA UN BORRADOR QUE PRESENTE..
		boolean swUnSoloBorrador = true;
		List<Doc_detalle> listamostar2 = new ArrayList<Doc_detalle>();
		for (Doc_detalle g : doc_detalles2_2) {

			try {
				if (("borrador".equalsIgnoreCase(g.getDoc_estado().getNombre()))) {
					if (swUnSoloBorrador) {
						listamostar2.add(g);
						swUnSoloBorrador = false;
					}
				} else {
					listamostar2.add(g);
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

		}
		// FIN VALIDAMOS QUE SOLO SEA UN BORRADOR QUE PRESENTE..
		return listamostar2;
	}

	public void setDoc_detalles(List<Doc_detalle> doc_detalles) {
		this.doc_detalles = doc_detalles;
	}

	public List<Doc_detalle> getDoc_detalles2_2() {
		return doc_detalles2_2;
	}

	public void setDoc_detalles2_2(List<Doc_detalle> doc_detalles2_2) {
		this.doc_detalles2_2 = doc_detalles2_2;
	}

	public Doc_detalle getDoc_detallePrincipal_2() {
		return doc_detallePrincipal_2;
	}

	public void setDoc_detallePrincipal_2(Doc_detalle doc_detallePrincipal_2) {
		this.doc_detallePrincipal_2 = doc_detallePrincipal_2;
	}

	public Seguridad getSeguridadTreeMas() {
		return seguridadTreeMas;
	}

	public void setSeguridadTreeMas(Seguridad seguridadTreeMas) {
		this.seguridadTreeMas = seguridadTreeMas;
	}

	public List<ClienteDocumentoDetalle> getDoc_detallesCliente() {

		// clienteDocumentoMaestros ESTO VIENE DEL METODO
		// getClienteDocumentoMaestros UY ESTE VIENE DE DOC_MAESTROS()

		List<ClienteDocumentoMaestro> panels = session
				.getAttribute("clienteDocumentoMaestros") != null ? (List<ClienteDocumentoMaestro>) session
				.getAttribute("clienteDocumentoMaestros") : null;
		if (panels == null || panels.size() == 0) {

			getClienteDocumentoMaestros();

			panels = session.getAttribute("clienteDocumentoMaestros") != null ? (List<ClienteDocumentoMaestro>) session
					.getAttribute("clienteDocumentoMaestros") : null;
		}

		if (panels == null || panels.size() == 0) {

			doc_detallesCliente = new ArrayList<ClienteDocumentoDetalle>();
		} else {

			ClienteDocumentoMaestro clienteDocumentoMaestro = panels.get(0);
			doc_detallesCliente = (List) clienteDocumentoMaestro
					.getDoc_detallesCliente();

		}

		return doc_detallesCliente;
	}

	public void setDoc_detallesCliente(
			List<ClienteDocumentoDetalle> doc_detallesCliente) {
		this.doc_detallesCliente = doc_detallesCliente;
	}

	public ClienteDocumentoDetalle getDoc_detallePrincipal() {
		if (session.getAttribute("doc_detallePrincipal") == null) {
			getClienteDocumentoMaestros();
		}
		doc_detallePrincipal = session.getAttribute("doc_detallePrincipal") != null ? (ClienteDocumentoDetalle) session
				.getAttribute("doc_detallePrincipal") : null;
		return doc_detallePrincipal;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
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

	public Object getChkPublicado() {
		return chkPublicado;
	}

	public void setChkPublicado(Object chkPublicado) {
		this.chkPublicado = chkPublicado;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getPublicados() {
		return publicados;
	}

	public void setPublicados(HttpServletRequest publicados) {
		this.publicados = publicados;
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

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public boolean isSwDel() {
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		return swAdd;
	}

	public void setSwAdd(boolean swAdd) {
		this.swAdd = swAdd;
	}

	public ClienteSeguridad getClienteSeguridad() {
		return clienteSeguridad;
	}

	public void setClienteSeguridad(ClienteSeguridad clienteSeguridad) {
		this.clienteSeguridad = clienteSeguridad;
	}

	public List<Object> getHist_actuBorradors() {
		return hist_actuBorradors;
	}

	public void setHist_actuBorradors(List<Object> hist_actuBorradors) {
		this.hist_actuBorradors = hist_actuBorradors;
	}

	public List<Object> getHist_darPublicos() {
		return hist_darPublicos;
	}

	public void setHist_darPublicos(List<Object> hist_darPublicos) {
		this.hist_darPublicos = hist_darPublicos;
	}

	public List<Object> getHist_verDetalless() {
		return hist_verDetalless;
	}

	public void setHist_verDetalless(List<Object> hist_verDetalless) {
		this.hist_verDetalless = hist_verDetalless;
	}

	public List<Object> getHist_verVinculadoss() {
		return hist_verVinculadoss;
	}

	public void setHist_verVinculadoss(List<Object> hist_verVinculadoss) {
		this.hist_verVinculadoss = hist_verVinculadoss;
	}

	public List<Object> getHist_verSometerFlows() {
		return hist_verSometerFlows;
	}

	public void setHist_verSometerFlows(List<Object> hist_verSometerFlows) {
		this.hist_verSometerFlows = hist_verSometerFlows;
	}

	public List<Object> getHist_nuevaVerVigs() {
		return hist_nuevaVerVigs;
	}

	public void setHist_nuevaVerVigs(List<Object> hist_nuevaVerVigs) {
		this.hist_nuevaVerVigs = hist_nuevaVerVigs;
	}

	public List<Object> getHist_deshNuevaVers() {
		return hist_deshNuevaVers;
	}

	public void setHist_deshNuevaVers(List<Object> hist_deshNuevaVers) {
		this.hist_deshNuevaVers = hist_deshNuevaVers;
	}

	public List<Object> getHist_genRegs() {
		return hist_genRegs;
	}

	public void setHist_genRegs(List<Object> hist_genRegs) {
		this.hist_genRegs = hist_genRegs;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public boolean isSwPostgresVieneDeConfiguracion() {
		return swPostgresVieneDeConfiguracion;
	}

	public void setSwPostgresVieneDeConfiguracion(
			boolean swPostgresVieneDeConfiguracion) {
		this.swPostgresVieneDeConfiguracion = swPostgresVieneDeConfiguracion;
	}

	public boolean isSwConfiguracionHayData() {
		return swConfiguracionHayData;
	}

	public void setSwConfiguracionHayData(boolean swConfiguracionHayData) {
		this.swConfiguracionHayData = swConfiguracionHayData;
	}

	public Configuracion getConf() {
		return conf;
	}

	public void setConf(Configuracion conf) {
		this.conf = conf;
	}

	public FileUploadEvent get_upFile() {
		return _upFile;
	}

	public void set_upFile(FileUploadEvent _upFile) {
		this._upFile = _upFile;
	}

	public ArrayList<FileUploadEvent> getFilesPrimeFaces() {
		return filesPrimeFaces;
	}

	public void setFilesPrimeFaces(ArrayList<FileUploadEvent> filesPrimeFaces) {
		this.filesPrimeFaces = filesPrimeFaces;
	}

	public String getTipoNodoCrear() {
		return tipoNodoCrear;
	}

	public void setTipoNodoCrear(String tipoNodoCrear) {
		this.tipoNodoCrear = tipoNodoCrear;
	}

	public String getText() {
		return text;
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

	public boolean isToGenerarRegistroII() {
		return toGenerarRegistroII;
	}

	public void setToGenerarRegistroII(boolean toGenerarRegistroII) {
		this.toGenerarRegistroII = toGenerarRegistroII;
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

	public boolean isSwNodoDocumento() {
		return swNodoDocumento;
	}

	public void setSwNodoDocumento(boolean swNodoDocumento) {
		this.swNodoDocumento = swNodoDocumento;
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

	public long getTiponodo() {
		return tiponodo;
	}

	public void setTiponodo(long tiponodo) {
		this.tiponodo = tiponodo;
	}

	public boolean isSwMoverNodo() {
		return swMoverNodo;
	}

	public void setSwMoverNodo(boolean swMoverNodo) {
		this.swMoverNodo = swMoverNodo;
	}

	public String getNodoPrincipal() {
		return nodoPrincipal;
	}

	public void setNodoPrincipal(String nodoPrincipal) {
		this.nodoPrincipal = nodoPrincipal;
	}

	public FilenameFilter getFilter() {
		return filter;
	}

	public void setFilter(FilenameFilter filter) {
		this.filter = filter;
	}

	public void setTreeData(Object treeData) {
		this.treeData = treeData;
	}

	public boolean isSwEditar() {
		return swEditar;
	}

	public void setSwEditar(boolean swEditar) {
		this.swEditar = swEditar;
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

	public boolean isSwAttachment() {
		return swAttachment;
	}

	public void setSwAttachment(boolean swAttachment) {
		this.swAttachment = swAttachment;
	}

	public String mostrarDocEnIframe() {

		return "";
	}

	public void mostrarDocEnIframeEvent(ActionEvent event) {

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId2verdoc");
		 
			doc_detalle = new Doc_detalle();
			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				doc_detalle.setCodigo(new Long(id));
				doc_detalle = delegado.findDocDetalle(doc_detalle);
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
		if (doc_detalle != null && doc_detalle.getCodigo() != null) {
			if (swMostraDocIframe) {

				session.setAttribute("onlyView", "onlyView");
				session.setAttribute("objeto", doc_detalle);
			}
		} else {
			//
			// FacesMessage msg = new
			// FacesMessage("Vuelva a intentarlo por favor",
			// " Es la primera vez.");
			// FacesContext.getCurrentInstance().addMessage(null, msg);
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
	
	
	public void mostrarDocEnIframeEvent2(ActionEvent event) {
		
		 
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdiframe_applet");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_detalle == null) {
				doc_detalle = new Doc_detalle();
			}
			// buscamos el role para editar

			if (id >= 0) {

				doc_detalle.setCodigo(new java.lang.Long(id));
				// buscamos el detalle
				doc_detalle = delegado.findDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
			}

		}

		
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
