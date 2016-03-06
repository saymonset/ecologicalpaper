package com.ecological.paper.cliente.documentos;

import java.io.File;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;

import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.hilosBackend.CopiarBranchesTags;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepcionesContextType;
import com.ecological.paper.ecologicaexcepciones.ExceptionSubVersion;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.subversion.Repositorio;
import com.ecological.paper.subversion.RepositorioSVN;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.subversion.SvnExtension;
import com.ecological.paper.subversion.SvnModulo;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnRutasRelativasFiles;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUpload;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import com.util.file.Archivo;
import com.util.file.ZipearDoc;

public class DocumentoSvn extends ContextSessionRequest {
	private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
	private Map<Long, Boolean> selectedIdsUploads = new HashMap<Long, Boolean>();
	private HtmlDataTable myDataTable;
	private HtmlSelectOneMenu selectOneMenu1;
	private HtmlSelectOneMenu selectOneMenu2;
	private HtmlSelectOneMenu selectOneMenu3;
	private HtmlSelectOneMenu selectOneMenu4;
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
	private Doc_maestro doc_maestroModifica;
	private Doc_detalle doc_detalleModifica;
	private Doc_detalle doc_detalle_aux;
	private List<Doc_maestro> doc_maestros;
	private Usuario user_logueado;
	private Object _upFile;
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
	private boolean publico;
	private boolean siEsVigentepuedeSerPublico;
	private String publicoStr;
	private HashMap carpetasMasivas = new HashMap();
	private List archNoCargados = new ArrayList();
	private HashMap queTipoContextType = new HashMap();
	// SEGURIDAD
	private Seguridad seguridadTree = new Seguridad();
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
	private ClienteDocumentoDetalle clienteDocumentoDetalle;
	private List<ClienteDocumentoMaestro> clienteDocumentoMaestros;
	private SubVersionUsuario subVersionUsuario = new SubVersionUsuario();
	private List<Repositorio> listaRepositorio;
	private String irFlowsResponse;
	private List<SvnUpload> svnUploadLista;
	private boolean uploadSVN;
	private String ultimoTagsBranchesTrunk;
	private boolean swUploadSeleccionSvnSinFlow;
	private boolean swUploadSvnSinFlowJsp;
	// private boolean swCreoTags;
	private SvnUrlBase svnUrlBase;
	private SvnNombreAplicacion svnNombreAplicacion;
	private SvnModulo svnModulo;
	private String prueba;
	private boolean automaticoCargaSvn;
	// private String filesllenadosdeLista;
	private String filesllenadosdeListaVista;
	private Map<String, Object> filesllenadosdeListaObjeto;
	private String comentario;
	private List<SelectItem> allSvnNombreAplicacion;
	private List<SelectItem> allSvnModulo;
	private List<SelectItem> allSvnUrlBase;
	private SvnTipoAmbiente svnTipoAmbiente;
	private List<SelectItem> allSvnTipoAmbientes;
	private SvnNombreAplicacion svnnombreaplicacion;
	private long revision=0;

	// private boolean swRepositorioCopiado;

	/** Creates a new instance of ClientePadreDocumento */
	public DocumentoSvn() {
		try {

			session = super.getSession();
			// UPLOAD AUTOMATICO.. GENERANDO LA URL AUTOMATICA CON SUS
			// EXTENSIONES DE LOS ARCHIVOS..
			automaticoCargaSvn = session.getAttribute("automaticoCargaSvn") != null;

			// subimos docs sin flujos al repositorio
			swUploadSeleccionSvnSinFlow = session.getAttribute("swPrimeraVez") != null;
			// swCreoTags = session.getAttribute("swCreoTags") != null;

			// esta varable solo se usa en jsp
			swUploadSvnSinFlowJsp = session
					.getAttribute("swUploadSvnSinFlowJsp") != null;

			boolean primeraVezInDocumentoSvn = session
					.getAttribute("primeraVezInDocumentoSvn") == null;
			if (primeraVezInDocumentoSvn) {
				// inicializamos y colocamos la variable llena para qu no
				// vuelcva a pasar por aca
				inicilizaChange();
				session.setAttribute("primeraVezInDocumentoSvn", true);
			}

			// VERIFICAMOS QUE VENGA DE UN WORKFLOW
			irFlowsResponse = "";
			irFlowsResponse = session.getAttribute("flowsResponse") != null ? (String) session
					.getAttribute("flowsResponse") : "";
			uploadSVN = session.getAttribute("uploadSVN") != null ? (Boolean) session
					.getAttribute("uploadSVN") : false;

			if (uploadSVN) {
				svnUploadLista = session.getAttribute("svnUploadLista") != null ? (List<SvnUpload>) session
						.getAttribute("svnUploadLista") : null;
				if (svnUploadLista == null) {
					Flow_Participantes flow_Participantes = session
							.getAttribute("flow_Participantes") != null ? (Flow_Participantes) session
							.getAttribute("flow_Participantes") : null;
					if (flow_Participantes.getFlow().getFlowParalelo()
							.getFlow() != null) {
						svnUploadLista = delegado
								.listUploadAttachmentSvn(flow_Participantes
										.getFlow().getFlowParalelo().getFlow());
						session.setAttribute("svnUploadLista", svnUploadLista);

					}
				}

			}

			// ------------------------

			// carga docs masivos
			docVersionMayorMasivo = 1;
			docVersionMenorMasivo = 0;
			// ------------------------
			configuraciones = delegado.find_allConfiguracion();
			if (configuraciones != null && configuraciones.size() > 0) {
				Configuracion conf = configuraciones.get(0);

				swPostgresVieneDeConfiguracion = conf.isBdpostgres();
				swConfiguracionHayData = true;
			}

			// por defecto es true para darle permisos al grupo al crear un
			// documento.
			swHeredarPermisos = false;

			// verificamos si es super usuario
			if (super.getUser_logueado() != null
					&& super.getUser_logueado().getLogin() != null) {
				swSuperUsuario = super.getUser_logueado().getLogin()
						.equalsIgnoreCase(Utilidades.getRoot());
			}
			// USUARIO SUBVERSION BUSCAR...
			// inicializamos eñl usuario con ecologicalpaper y los checkbocs
			// inicializados
			// en blanco
			selectedIds = new HashMap<Long, Boolean>();
			selectedIdsUploads = new HashMap<Long, Boolean>();
			subVersionUsuario.setUsuario(super.getUser_logueado());
			subVersionUsuario = delegado.find(subVersionUsuario);

			if (subVersionUsuario == null) {
				listaRepositorio = new ArrayList<Repositorio>();
				subVersionUsuario = new SubVersionUsuario();
				subVersionUsuario.setUsuario(super.getUser_logueado());
				subVersionUsuario.setSwExiste(false);
			}
			// esto no c guarda en bd, por eso siempre lo inicializamos
			subVersionUsuario.setMostrarLogSVN(Utilidades
					.getMinRegisterMostrar());

			/*
			 * System.out.println("-----------1-------------------"); if
			 * (session.getAttribute("svnNombreAplicacion")!=null){
			 * System.out.println("-----------2-------------------");
			 * svnNombreAplicacion
			 * =session.getAttribute("svnNombreAplicacion")!=null?
			 * (SvnNombreAplicacion
			 * )session.getAttribute("svnNombreAplicacion"):null;
			 * subVersionUsuario
			 * .setUrlsubversionUpload(svnNombreAplicacion.getSvnUrlBase
			 * ()+"/"+svnNombreAplicacion.getSvnUrlBase().getTipoAmbiente()); }
			 * System.out.println("-----------3-------------------");
			 */

			treeNodoActual = (Tree) session.getAttribute("treeNodoActual");

			// OBTENEMOS SEGURIDAD;
			seguridadTree = super.getSeguridadTree(treeNodoActual);

			// verificamos si tienes permisologia para publicar el documento
			swPublico = false;
			if (seguridadTree != null && seguridadTree.isToDoPublico()
					|| swSuperUsuario) {
				swPublico = true;
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

			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			boolean crear = session.getAttribute("crear") != null;

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
					doc_detalle.setIcono(super.obtenIconoDoc(doc_detalle
							.getNameFile()));
				}

				doc_detalle_aux = doc_detalle;
				session.setAttribute("doc_detalle_aux", doc_detalle_aux);
				doc_maestro = session.getAttribute("doc_maestro") != null ? (Doc_maestro) session
						.getAttribute("doc_maestro") : new Doc_maestro();

				if (doc_maestro != null && doc_maestro.getTree() != null) {
					// armamos el prefijo
					boolean nombreCorto = false;
					String prefijo = "";
					prefijo = getPrefijo(doc_maestro.getTree(), prefijo,
							nombreCorto);
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
				publicoEs_Doc_Maestro(doc_maestro);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

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

	public String uploadSvnSinFlow() {
		// se coloca falso para realizar busquedas por la url pincipal, pero se
		// sube
		// por la url upload
		session.setAttribute("uploadSVN", false);
		// solo se usa en jsp
		session.setAttribute("swUploadSvnSinFlowJsp", true);
		return "crearDocumentosvn";
	}

	public String nuevaVersionDoc() throws IOException, EcologicaExcepciones {
		String pagIr = "";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (_upFile != null) {
			if (doc_detalle != null) {
				if (session.getAttribute("edit") != null) {
					if (super.isEmptyOrNull(doc_detalle.getDescripcion())) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_camposvacios")));
						return "";
					}

					//
					// verificamos que si es bd postgres, no exceda los 8mb en
					// data
					if (swConfiguracionHayData) {
						if (swPostgresVieneDeConfiguracion) {
							if ((Double.compare(
									converBytesMbytes(new Long("0")),
									Utilidades.getPostgresMBmax()) > 0)) {
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
						if ((Double.compare(
								converBytesMbytes(new Long("0")),
								Utilidades.getPostgresMBmax()) > 0)) {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("postgmenorochomb")));
							return "";
						}

					}

					// si no es valida la extension o el contextype, te genera
					// un mensaje internmo y se sale

					if (!buscarContextTypeTipoArchivo(doc_detalle,
							"REVISAR")) {

						return "";
					}
					Calendar fecha = Calendar.getInstance();
					Doc_detalle docDetalle_newBorrador = new Doc_detalle();
					docDetalle_newBorrador.setDoc_maestro(doc_maestro);
					docDetalle_newBorrador.setSize_doc(new Long("0"));
					docDetalle_newBorrador.setDatecambio(fecha.getTime());

					// docDetalle_newBorrador.setNameFile(nameFile(_upFile.getName()));
					docDetalle_newBorrador.setNameFile(doc_detalle
							.getNameFile());
					// docDetalle_newBorrador.setContextType(_upFile.getContentType());
					docDetalle_newBorrador.setContextType(doc_detalle
							.getContextType());

					// docDetalle_newBorrador.setNameFile(nameFile(_upFile.getName()));
					// docDetalle_newBorrador.setContextType(_upFile.getContentType());
					Blob blob = null;//Hibernate.createBlob(_upFile.getInputStream());
					docDetalle_newBorrador.setData(blob);
					docDetalle_newBorrador.setMayorVer(doc_detalle
							.getMayorVer());
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
					// buscamoçs el original detalle, antes de hacer la nueva
					// version
					doc_detalle_aux = delegado.findDetalle(doc_detalle);
					// grabamos la nueva version
					delegado.createDetalle(docDetalle_newBorrador);
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
							Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
							doc_dataPostgresNew.setData_doc_postgres(null);
							doc_dataPostgresNew
									.setDoc_detalle(docDetalle_newBorrador);
							doc_dataPostgresNew
									.setStatus(Utilidades.isActivo());
							delegado.create(doc_dataPostgresNew);
						}
					} else if ("1".equalsIgnoreCase(confmessages
							.getString("bdpostgres"))) {
						Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
						doc_dataPostgresNew.setData_doc_postgres(null);
						doc_dataPostgresNew
								.setDoc_detalle(docDetalle_newBorrador);
						doc_dataPostgresNew.setStatus(Utilidades.isActivo());
						delegado.create(doc_dataPostgresNew);
					}

					// ____________________________________
					// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					boolean nuevaVerVig = true;
					super.guardamosHistoricoActivoDelDocumento(user_logueado,
							docDetalle_newBorrador.getDoc_maestro(), false,
							false, false, false, false, nuevaVerVig, false,
							false, "");
					// ____________________________________

					// damos la seguridad, al usuario logfueado
					// clienteSeguridad.darSeguridadNodo(tree,user_logueado);
					// al duenio
					// clienteSeguridad.darSeguridadNodo(tree,docDetalle_newBorrador.getDuenio());
					session.removeAttribute("edit");
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
		return pagIr;
	}

	public void borrarConstraintsDetalle(Doc_detalle doc_detalle) {

		// eliminamos todos los historicos de doc_detalle
		List<FlowsHistorico> actualizaFlowHistoricos = delegado
				.findAll_FlowsHistoricoDoc_detalle(doc_detalle);
		for (FlowsHistorico fh : actualizaFlowHistoricos) {

			try {
				// aqui se borra el flujo y el detalle de historico
				delegado.destroy(fh);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}

		// eliminamos el flujo de doc_dfetalle
		List<Flow> actualizaFlows = delegado
				.findDetalleTreeHistoricoPersonalInFlow(doc_detalle);
		for (Flow f : actualizaFlows) {
			borrarFlujos(f);
		}

		// ELIMINAMOS SI VIENE DE POSTGRES EN POSTGRES EL
		// DETALLE
		// SI ES BD POSTGRES
		if (swConfiguracionHayData) {
			if (swPostgresVieneDeConfiguracion) {
				// BORRAMOS LA DATA VIEJA
				Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
				doc_dataPostgres = delegado.findDoc_dataPostgres(doc_detalle);
				if (doc_dataPostgres != null) {
					delegado.destroy(doc_dataPostgres);
				}

			}
		} else if ("1".equalsIgnoreCase(confmessages.getString("bdpostgres"))) {
			// BORRAMOS LA DATA VIEJA
			Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
			doc_dataPostgres = delegado.findDoc_dataPostgres(doc_detalle);
			if (doc_dataPostgres != null) {
				delegado.destroy(doc_dataPostgres);
			}
		}
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

			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (_upFile != null) {
				if (doc_detalle != null) {
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

					if (!buscarContextTypeTipoArchivo(doc_detalle,
							"REVISAR")) {
						return "";
					}
					//
					// verificamos que si es bd postgres, no exceda los 8mb en
					// data
					if (swConfiguracionHayData) {
						if (swPostgresVieneDeConfiguracion) {
							if ((Double.compare(
									converBytesMbytes(new Long("0")),
									Utilidades.getPostgresMBmax()) > 0)) {
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
						if ((Double.compare(
								converBytesMbytes(new Long("0")),
								Utilidades.getPostgresMBmax()) > 0)) {
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
						docDetalle_newBorrador.setSize_doc(new Long("0"));
						docDetalle_newBorrador.setDatecambio(fecha.getTime());
						// docDetalle_newBorrador.setNameFile(nameFile(_upFile.getName()));
						docDetalle_newBorrador.setNameFile(doc_detalle
								.getNameFile());
						// docDetalle_newBorrador.setContextType(_upFile.getContentType());
						docDetalle_newBorrador.setContextType(doc_detalle
								.getContextType());
						Blob blob = null;

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
						docDetalle_newBorrador.setModificadoPor(user_logueado);
						// buscamoçs el original detalle, antes de hacer la
						// nueva
						// version
						doc_detalle_aux = delegado.findDetalle(doc_detalle);
						// grabamos la version actualizada, lo hacemos asi
						// porque si
						// editamos.
						// la data del archivo nunca cambia :(
						delegado.createDetalle(docDetalle_newBorrador);
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
								doc_dataPostgresNew
										.setData_doc_postgres(null);
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
				if (super.getUser_logueado() != null
						&& super.getUser_logueado().getLogin() != null) {
					delegado.crearNuevoTree(carpeta,super.getUser_logueado());
				}
				
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
					// doc_detalle.setContextType(typeContext);

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

		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			delegado.crearNuevoTree(tree,super.getUser_logueado());	
		}
		
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
									+ (_upFile != null ? null: "")));
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
					_upFileFile = archivo.fileDesdeStream(
							null, nom2, ext);

				} else {
					throw new EcologicaExcepcionesContextType(
							(messages.getString("doc_ext") + ":" + (nomArch != null ? nomArch
									: "")));
				}

				//
				// verificamos que si es bd postgres, no exceda los 8mb en data
				if (swConfiguracionHayData) {
					if (swPostgresVieneDeConfiguracion) {
						if ((Double.compare(
								converBytesMbytes(new Long("0")),
								Utilidades.getPostgresMBmax()) > 0)) {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("postgmenorochomb")));
							return "";
						}
					}
				} else if ("1".equalsIgnoreCase(confmessages
						.getString("bdpostgres"))) {
					if ((Double.compare(converBytesMbytes(new Long("0")),
							Utilidades.getPostgresMBmax()) > 0)) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("postgmenorochomb")));
						return "";
					}

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
					doc_maestro.setNombre(sacarSlashFiltro("REVISAR"));

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
						_upFileFile, user_logueado, null,
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

				/*
				 * session.setAttribute("conecutivo2", doc_maestro
				 * .getConsecutivo());
				 */
				// ------------------------------
				/*
				 * List doc_maestros = delegado.consecutivoseRepite(doc_maestro,
				 * tree); if (doc_maestros != null && !doc_maestros.isEmpty() ||
				 * (doc_maestro != null && doc_maestro.getConsecutivo()
				 * .equals("00000000"))) { boolean swNocRepite = false; if
				 * (!doc_maestro.getConsecutivo().equals(
				 * Utilidades.getConsecutivoVacio())) {
				 * session.setAttribute("conecutivo1", doc_maestro
				 * .getConsecutivo()); } while (!swNocRepite) { Doc_consecutivo
				 * doc_consecutivo = new Doc_consecutivo();
				 * delegado.create(doc_consecutivo);
				 * doc_maestro.setConsecutivo(String
				 * .valueOf(doc_consecutivo.getCodigo()));
				 * doc_maestro.setConsecutivo(numSacopDo("", doc_maestro
				 * .getConsecutivo(), Utilidades .getConsecutivoLength()));
				 * doc_maestros = delegado.consecutivoseRepite( doc_maestro,
				 * tree); if (doc_maestros != null && !doc_maestros.isEmpty()) {
				 * } else { session.setAttribute("conecutivo2", doc_maestro
				 * .getConsecutivo()); swNocRepite = true; }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * Doc_maestro d = new Doc_maestro(); // continuar
				 * 
				 * doc_maestro.setTree(tree); d =
				 * delegado.nombreDocseRepite(doc_maestro); if (d != null) {
				 * FacesContext.getCurrentInstance().addMessage( null, new
				 * FacesMessage(messages .getString("error_nomdocexiste")));
				 * return ""; }
				 * doc_maestro.setBusquedakeys(getWordKeys(doc_maestro
				 * .getBusquedakeys(), _upFile.getName()));
				 * delegado.crearNuevoTree(tree); doc_maestro.setTree(tree);
				 * 
				 * doc_detalle.setSize_doc(new Long("0"));
				 * doc_detalle.setDatecambio(fecha.getTime());
				 * doc_detalle.setDoc_maestro(doc_maestro); // si no es valida
				 * la extension o el contextype, te genera un // mensaje
				 * internmo y se sale if
				 * (!buscarContextTypeTipoArchivo(doc_detalle, _upFile
				 * .getName())) { return ""; }
				 * 
				 * // si pasa por aca deshabilitamos el bton swDeshabiltarBtn =
				 * true; // algun dia se arreglara los campos blob en postgres
				 * Blob blob = Hibernate.createBlob(_upFile.getInputStream());
				 * doc_detalle.setData(blob);
				 * 
				 * doc_maestro.setUsuario_creador(user_logueado); if
				 * (doc_maestro.getFecha_creado() == null) {
				 * doc_maestro.setFecha_creado(fecha.getTime()); }
				 * doc_detalle.setModificadoPor(doc_detalle.getDuenio());
				 * 
				 * delegado.createMaestro(doc_maestro);
				 * delegado.createDetalle(doc_detalle);
				 * 
				 * if (swConfiguracionHayData) { if
				 * (swPostgresVieneDeConfiguracion) { // si es una base de datos
				 * postgres, guardamos la data // en otra tabla Doc_dataPostgres
				 * doc_dataPostgres = new Doc_dataPostgres();
				 * doc_dataPostgres.setData_doc_postgres(_upFile .getBytes());
				 * doc_dataPostgres.setDoc_detalle(doc_detalle);
				 * doc_dataPostgres.setStatus(Utilidades.isActivo());
				 * delegado.create(doc_dataPostgres); } } else if
				 * ("1".equalsIgnoreCase(confmessages .getString("bdpostgres")))
				 * { // si es una base de datos postgres, guardamos la data en
				 * // otra tabla Doc_dataPostgres doc_dataPostgres = new
				 * Doc_dataPostgres();
				 * doc_dataPostgres.setData_doc_postgres(_upFile.getBytes());
				 * doc_dataPostgres.setDoc_detalle(doc_detalle);
				 * doc_dataPostgres.setStatus(Utilidades.isActivo());
				 * delegado.create(doc_dataPostgres); }
				 */

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

		if (!isEmptyOrNull(irFlowsResponse)) {
			session.removeAttribute("flowsResponse");
			return irFlowsResponse;
		} else {
			try {
				super.clearSession(session,
						confmessages.getString("usuarioSession"));
			} catch (Exception e) {
				redirect("ClientePadreDocumento", "cancelarLista", e);
			}
		}

		return "menu";
	}

	public String cancelarListaUploadSvnIndividual() {
		session.removeAttribute("swUploadSvnSinFlowJsp");
		return "crearDocumentosvnUpload";
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
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClientePadreDocumento", "cancelar", e);
		}

		return "listar";
	}

	public String putPublico() {
		return "";
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
						List doc_maestros = delegado.consecutivoseRepite(
								doc_maestro, treeNodoActual);
						if (doc_maestros != null && !doc_maestros.isEmpty()) {
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
				delegado.editMaestro(doc_maestro);
				delegado.editDetalle(doc_detalle);
				session.setAttribute("doc_detalle", doc_detalle);
				session.setAttribute("doc_maestro", doc_maestro);
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

	public String regresarVerDocumento() {
		String parametro = session.getAttribute("parametro") != null ? (String) session
				.getAttribute("parametro") : "";
		if (!"publico".equalsIgnoreCase(parametro)) {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		}

		return "listar";
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
			/*boolean verDetalles = true;
			super.guardamosHistoricoActivoDelDocumento(user_logueado,
					detalle.getDoc_maestro(), false, false, verDetalles, false,
					false, false, false, false, "");*/
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
				// System.out.println("id=" + id);
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
		session.setAttribute("edit", true);
		return "viewOnly";
	}

	public String viewHistoricoDocActivo() {
		return "listarviewHistoricoDocActivo";
	}

	public String viewHistorico() {
		return "listarFlowsHistorico";
	}

	public String generarRegistro() {
		try {

			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
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

	public String crear_nuevaversion() {
		try {

			session.setAttribute("edit", true);
			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			detalle.setDescripcion("");
			session.setAttribute("doc_detalle", detalle);
		} catch (Exception e) {
			redirect();
		}
		return "crear_nuevaversion";
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
		return "docVinculados";
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
		return Utilidades.getFlows();

	}

	public String flowsView_action() {
		return "flowsResponse";
	}

	public void viewFlow(ActionEvent event) {
		try {

			UIParameter component = (UIParameter) event.getComponent()
					.findComponent("editId22");
			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				Doc_detalle doc_detBuscaFlow = new Doc_detalle();
				doc_detBuscaFlow.setCodigo(new Long(id));
				doc_detBuscaFlow = delegado.findDetalle(doc_detBuscaFlow);

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

	public void versionId(ValueChangeEvent event)
			throws AbortProcessingException {
		// System.out.println(event.getComponent().getId());

		// System.out.println(event.getNewValue());

	}

	public void versionId(ActionEvent event) {
		try {

			String attrname1 = (String) event.getComponent().getAttributes()
					.get("attrname1");
			UIParameter component = null;
			component = (UIParameter) event.getComponent().findComponent(
					"editId2");
			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_Update");
			}
			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_flow");
			}
			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_checkout");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_Desbloqueo");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_Copiar");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_historicoflow");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_HistoricoDoc");
			}

			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_Detalle");
			}
			if ((component == null)
					|| (component.getValue().toString() == null)) {
				component = (UIParameter) event.getComponent().findComponent(
						"editId2_NameFile");
			}

			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
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

	public String viewListarDocumento() {
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());

		return Utilidades.getFinexitoso();
	}

	public void liberarBloqueoId(ActionEvent event) {

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
							System.out
									.println("borramndo rechazado...............6...........");
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
		// BORRAMOS PRIMERO EL CONTROL DE TIEMPÒ DE LOS
		// FLUJOS
		List<FlowControlByUsuarioBean> controlTiempo = delegado
				.find_allControlTimeByFlowParticipAndRole(f);
		try {

			if (controlTiempo != null && !controlTiempo.isEmpty()) {
				for (FlowControlByUsuarioBean borrar : controlTiempo) {
					delegado.destroy(borrar);
				}
			}

			List<Flow_referencia_role> f_r_rs = delegado
					.findAllFlow_delete_role_ByFlow(f);
			Role role = new Role();
			for (Flow_referencia_role f_r_r : f_r_rs) {
				delegado.destroy(f_r_r);
			}

			List<Flow_Participantes> f_ps = delegado
					.findByDeleteFlowParticipantes(f);
			for (Flow_Participantes f_p : f_ps) {
				// BORRAMOS EL FLOW DE PARTICIPANTES
				delegado.destroy(f_p);
			}

			delegado.destroy(f);

			// ESTO LO USAMOS PARA BORRAR UN BORRADOR EN LISTARDOCUMENTOS
			List<Flow_Participantes> flows_participantes = delegado
					.findByFlowParticipantes(f);
			for (Flow_Participantes f_p : flows_participantes) {
				try {
					delegado.destroy(f_p);
				} catch (Exception exception) {
					continue;
				}
			}
			List<Flow_referencia_role> flows_roles = delegado
					.findByFlow_referencia_role(f);
			for (Flow_referencia_role f_r : flows_roles) {
				try {
					delegado.destroy(f_r);
				} catch (Exception exception) {
					continue;
				}
			}

			// ELIMNINAMOS LOS FLUJOS HISTORICO DEL
			// DOCUMENTO
			List<FlowsHistorico> flowsHistoricos = delegado
					.findAll_FlowsHistoricoDoc_detalle(doc_detalle);
			for (FlowsHistorico f_h : flowsHistoricos) {
				delegado.destroy(f_h);
			}

			delegado.destroy(f);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public List<ClienteDocumentoMaestro> getDoc_maestros()
			throws EcologicaExcepciones {
		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");

		usuariosInFlowStr = "";

		List<ClienteDocumentoMaestro> listamostar = new ArrayList<ClienteDocumentoMaestro>();
		ClienteDocumentoMaestro clienteDocumentoMaestro = new ClienteDocumentoMaestro();
		// aqui verificamos seguridad de documentos vinculados, documento
		// punblico etc

		if (treeNodoActual != null) {
			doc_maestros = delegado.findAllDoc_maestros(treeNodoActual);
			int size = doc_maestros.size();
			for (int k = 0; k < size; k++) {
				Doc_maestro doc = (Doc_maestro) doc_maestros.get(k);
				// solo un maestro se agarra, los demas son identicos
				if (k == 0) {
					// verificamos si colocamos etiqueta publico o publicar
					publicoEs_Doc_Maestro(doc);
					boolean nombreCorto = true;
					String prefijo = "";
					prefijo = getPrefijo(doc.getTree(), prefijo, nombreCorto);
					doc.getTree().setPrefix(prefijo);
					setDoc_maestro(doc);
				}

				List<Doc_detalle> doc_detalles = null;
				String publico = session.getAttribute("parametro") != null ? (String) session
						.getAttribute("parametro") : "";
				doc_detalles = delegado.encontrarDetalles(doc.getCodigo(),
						publico);

				if (doc_detalles != null) {
					Iterator it = doc_detalles.iterator();
					boolean swSoloEncabezadoTieneBotones = true;
					int elPrimeroVaInmaestro = 0;
					Doc_detalle doc_detalleVigente = new Doc_detalle();
					doc_detalles2.clear();
					while (it.hasNext()) {
						elPrimeroVaInmaestro++;
						// i++;
						Doc_detalle d = (Doc_detalle) it.next();
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
						ClienteDocumentoDetalle doc_detalle_copy = new ClienteDocumentoDetalle(
								d, swPuedeRealizarFlujo, swIsObsoleto,
								swActualizar, swCheckOut, swCopy, swPage,
								swCute, swLocked, swLockedwithkey, swUnlocked,
								swFlujoActivo, swVincularDocumento, swPublico,
								swMover, swRegistro, icono, usuariosInFlowStr,
								swHistDoc, swHistFlow);
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
						List flowActivo = delegado.findByFlow(d);

						// es un solo registro y puede ser el borrador o un
						// dlcumento vigente
						if (swSoloEncabezadoTieneBotones) {
							swCopy = true;
							swCute = true;
							swVincularDocumento = false;
//							if (seguridadTree.isToVincDoc() || swSuperUsuario) {
							if (seguridadTree.isToView() || swSuperUsuario) {
								swVincularDocumento = true;
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
										.findByFlowParticipantes(flow);
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
								doc_detalle_copy = new ClienteDocumentoDetalle(
										d, swPuedeRealizarFlujo, swIsObsoleto,
										swActualizar, swCheckOut, swCopy,
										swPage, swCute, swLocked,
										swLockedwithkey, swUnlocked,
										swFlujoActivo, swVincularDocumento,
										swPublico, swMover, swRegistro, icono,
										usuariosInFlowStr, swHistDoc,
										swHistFlow);
							} // ______________SI NO ESTA EN
								// FLUJO____________________________________________________________________//
							else {
								// verificamos quien puede actualizar si el
								// documento es borrador
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
										doc_detalle_copy = new ClienteDocumentoDetalle(
												d, swPuedeRealizarFlujo,
												swIsObsoleto, swActualizar,
												swCheckOut, swCopy, swPage,
												swCute, swLocked,
												swLockedwithkey, swUnlocked,
												swFlujoActivo,
												swVincularDocumento, swPublico,
												swMover, swRegistro, icono,
												usuariosInFlowStr, swHistDoc,
												swHistFlow);
									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
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

											doc_detalle_copy = new ClienteDocumentoDetalle(
													d, swPuedeRealizarFlujo,
													swIsObsoleto, swActualizar,
													swCheckOut, swCopy, swPage,
													swCute, swLocked,
													swLockedwithkey,
													swUnlocked, swFlujoActivo,
													swVincularDocumento,
													swPublico, swMover,
													swRegistro, icono,
													usuariosInFlowStr,
													swHistDoc, swHistFlow);
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
										doc_detalle_copy = new ClienteDocumentoDetalle(
												d, swPuedeRealizarFlujo,
												swIsObsoleto, swActualizar,
												swCheckOut, swCopy, swPage,
												swCute, swLocked,
												swLockedwithkey, swUnlocked,
												swFlujoActivo,
												swVincularDocumento, swPublico,
												swMover, swRegistro, icono,
												usuariosInFlowStr, swHistDoc,
												swHistFlow);
									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
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

											doc_detalle_copy = new ClienteDocumentoDetalle(
													d, swPuedeRealizarFlujo,
													swIsObsoleto, swActualizar,
													swCheckOut, swCopy, swPage,
													swCute, swLocked,
													swLockedwithkey,
													swUnlocked, swFlujoActivo,
													swVincularDocumento,
													swPublico, swMover,
													swRegistro, icono,
													usuariosInFlowStr,
													swHistDoc, swHistFlow);
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
										doc_detalle_copy = new ClienteDocumentoDetalle(
												d, swPuedeRealizarFlujo,
												swIsObsoleto, swActualizar,
												swCheckOut, swCopy, swPage,
												swCute, swLocked,
												swLockedwithkey, swUnlocked,
												swFlujoActivo,
												swVincularDocumento, swPublico,
												swMover, swRegistro, icono,
												usuariosInFlowStr, swHistDoc,
												swHistFlow);
									} else {
										if ((doc_detalleVigente == null)
												|| (!doc_detalleVigente
														.isDoc_checkout())) {
											if (seguridadTree.isToMod()
													|| swSuperUsuario) {
												swActualizar = true;
											}

											if (seguridadTree.isToDoFlow()
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

											doc_detalle_copy = new ClienteDocumentoDetalle(
													d, swPuedeRealizarFlujo,
													swIsObsoleto, swActualizar,
													swCheckOut, swCopy, swPage,
													swCute, swLocked,
													swLockedwithkey,
													swUnlocked, swFlujoActivo,
													swVincularDocumento,
													swPublico, swMover,
													swRegistro, icono,
													usuariosInFlowStr,
													swHistDoc, swHistFlow);
										}

									}

								}
								// es el ultimo aprobado vigente
								if ((!d.isDoc_checkout())
										&& (d.getDoc_estado().getCodigo()
												.equals(Utilidades.getVigente()))) {
									if (seguridadTree.isToDoFlow()
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

									doc_detalle_copy = new ClienteDocumentoDetalle(
											d, swPuedeRealizarFlujo,
											swIsObsoleto, swActualizar,
											swCheckOut, swCopy, swPage, swCute,
											swLocked, swLockedwithkey,
											swUnlocked, swFlujoActivo,
											swVincularDocumento, swPublico,
											swMover, swRegistro, icono,
											usuariosInFlowStr, swHistDoc,
											swHistFlow);
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

									doc_detalle_copy = new ClienteDocumentoDetalle(
											d, swPuedeRealizarFlujo,
											swIsObsoleto, swActualizar,
											swCheckOut, swCopy, swPage, swCute,
											swLocked, swLockedwithkey,
											swUnlocked, swFlujoActivo,
											swVincularDocumento, swPublico,
											swMover, swRegistro, icono,
											usuariosInFlowStr, swHistDoc,
											swHistFlow);
								}
								// esta por demas ...
								if (d.getDoc_estado().getCodigo()
										.equals(Utilidades.getObsoleto())) {
									swPuedeRealizarFlujo = false;
									swIsObsoleto = true;
									swActualizar = false;
									swCheckOut = false;
									doc_detalle_copy = new ClienteDocumentoDetalle(
											d, swPuedeRealizarFlujo,
											swIsObsoleto, swActualizar,
											swCheckOut, swCopy, swPage, swCute,
											swLocked, swLockedwithkey,
											swUnlocked, swFlujoActivo,
											swVincularDocumento, swPublico,
											swMover, swRegistro, icono,
											usuariosInFlowStr, swHistDoc,
											swHistFlow);
								}

							}
						}
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
						// convertimos el tam,año de bytes a mg
						if (doc_detalle_copy.getSize_doc() != 0) {
							doc_detalle_copy.setSize_doc(converBytesMbytes(Math
									.ceil(doc_detalle_copy.getSize_doc())));
						}
						if (elPrimeroVaInmaestro == 1) {

							clienteDocumentoMaestro = new ClienteDocumentoMaestro(
									publicoStr, getDoc_maestro(),
									doc_detalle_copy);

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

							// ahora el detalle va a tener el maestro
							// tambien----------
							// estas son las versiones del docuimento
							String extension = doc_detalle_copy.getNameFile()
									.substring(
											doc_detalle_copy.getNameFile()
													.lastIndexOf(".") + 1,
											doc_detalle_copy.getNameFile()
													.length());

							// ------------------------------------------
							// CHEQUEAMOS SI ES UN ARCHIVO PLANO PARA PROCESAR
							// DIFERENCIAS CON OTROS ARCHIVOS
							ExtensionFile extensionFile = delegado
									.tipoContextType(extension);
							if (extensionFile != null) {
								if (extensionFile
										.getMimeType()
										.equalsIgnoreCase(
												Utilidades
														.getMimeTypeTextPlain())) {
									doc_detalle_copy
											.setSwDiferenciaEntreVersiones(true);
								}
							} else {

								ExtensionFileHijos extensionFileHijos = new ExtensionFileHijos();
								extensionFileHijos.setExtension(extension);
								extensionFileHijos = delegado
										.find_ExtensionFileByExtension(extensionFileHijos);
								if (extensionFileHijos != null) {
									extensionFile = extensionFileHijos
											.getExtensionFile();
									if (extensionFile
											.getMimeType()
											.equalsIgnoreCase(
													Utilidades
															.getMimeTypeTextPlain())) {
										doc_detalle_copy
												.setSwDiferenciaEntreVersiones(true);
									}
								}
							}
							// ------------------------------------------
							doc_detalles2.add(doc_detalle_copy);

						} else {
							// ------------------------------------------
							// CHEQUEAMOS SI ES UN ARCHIVO PLANO PARA PROCESAR
							// DIFERENCIAS CON OTROS ARCHIVOS
							String extension = doc_detalle_copy.getNameFile()
									.substring(
											doc_detalle_copy.getNameFile()
													.lastIndexOf(".") + 1,
											doc_detalle_copy.getNameFile()
													.length());

							ExtensionFile extensionFile = delegado
									.tipoContextType(extension);

							// SI ESTA EN EXTENSION FILE
							if (extensionFile != null) {
								if (extensionFile
										.getMimeType()
										.equalsIgnoreCase(
												Utilidades
														.getMimeTypeTextPlain())) {
									doc_detalle_copy
											.setSwDiferenciaEntreVersiones(true);
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
									extensionFile = extensionFileHijos
											.getExtensionFile();
									if (extensionFile
											.getMimeType()
											.equalsIgnoreCase(
													Utilidades
															.getMimeTypeTextPlain())) {
										doc_detalle_copy
												.setSwDiferenciaEntreVersiones(true);
									}
								}
							}
							// ------------------------------------------

							// estas son las versiones del docuimento
							doc_detalles2.add(doc_detalle_copy);
						}

					}
				}
			}
		}
		// clienteDocumentoMaestro = new
		// ClienteDocumentoMaestro(getDoc_maestro());

		clienteDocumentoMaestro.setDoc_detallesCliente(doc_detalles2);
		listamostar.add(clienteDocumentoMaestro);
		return listamostar;
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
	 * public String refrescaRepositorioUpload() { String pagIr = "";
	 * 
	 * try {
	 * 
	 * //agarramos el file seleccionado List<String> files= new
	 * ArrayList<String>(); if (listaRepositorio!=null &&
	 * listaRepositorio.size()>0){ for (Repositorio ar : listaRepositorio) { if
	 * ((selectedIds != null) && (selectedIds.get(ar.getCodigo()) != null)) { if
	 * (selectedIds.get(ar.getCodigo()).booleanValue()) {
	 * files.add(ar.getArchivo());
	 * subVersionUsuario.setFilePath(ar.getArchivo()); //break; } } } }
	 * 
	 * subVersionUsuario.setFilePaths(files); //actalizamos datos en la base de
	 * datos delegado.createSubVersionUsuario(subVersionUsuario);
	 * 
	 * 
	 * //RepositorioSVN repositorioSVN = new RepositorioSVN(); //boolean
	 * swUpload=true; //subVersionUsuario =
	 * repositorioSVN.obtenerFile(subVersionUsuario,swUpload); //
	 * ----------------------------------------------------------
	 * 
	 * 
	 * 
	 * listaRepositorio = listarepositorioSVN(subVersionUsuario);
	 * session.setAttribute("listaRepositorio",listaRepositorio); //
	 * session.removeAttribute("flowsResponse"); pagIr= "";
	 * 
	 * //fin si viene de un work flow .. nos vamos para el workflow con el
	 * archivo
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * return pagIr; }
	 */

	public String deletebranchestag() {
		RepositorioSVN tester = new RepositorioSVN();

		try {
			if (!isEmptyOrNull(subVersionUsuario.getUrlsubversionUpload())) {
				ultimoTagsBranchesTrunk = ultimoNombreUrl(subVersionUsuario
						.getUrlsubversionUpload());
			}
			if ((!isEmptyOrNull(ultimoTagsBranchesTrunk) && (!isEmptyOrNull(subVersionUsuario
					.getDir())))
					&& ultimoTagsBranchesTrunk.compareTo(subVersionUsuario
							.getDir()) == 0) {
				System.out.println("-------------------------------------");
				System.out.println("" + ultimoTagsBranchesTrunk);
				System.out.println(subVersionUsuario.getDir());
				System.out.println("-------------------------------------");
				// BORRAMOS EWL TAGS
				tester.deleteInRepositorio(subVersionUsuario);
				try {
					subVersionUsuario
							.setUrlsubversionUpload(subVersionUsuario
									.getUrlsubversionUpload()
									.substring(
											0,
											subVersionUsuario
													.getUrlsubversionUpload()
													.lastIndexOf(
															subVersionUsuario
																	.getDir()) - 1));
				} catch (Exception e) {
					// TODO: handle exception
				}
				// actalizamos datos en la base de datos
				delegado.createSubVersionUsuario(subVersionUsuario);
				// esto se hace si quieres boirrar el ultimo tag del la url
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No es el ultimo tags"));
			}

		} catch (ExceptionSubVersion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	public String crearbranchestag() {
		RepositorioSVN tester = new RepositorioSVN();

		try {
			subVersionUsuario.setDir(subVersionUsuario.getDir()
					.replace("/", ""));
			subVersionUsuario.setDir(subVersionUsuario.getDir()
					.replace(" ", ""));
			subVersionUsuario.setDir(subVersionUsuario.getDir().trim());
			// CREAMOS EWL TAGS
			tester.crearDirInRepositorio(subVersionUsuario);

			// lo colocamos en la base decdatos el nuevo url
			if (!(subVersionUsuario.getUrlsubversionUpload().endsWith("/"))) {
				subVersionUsuario.setUrlsubversionUpload(subVersionUsuario
						.getUrlsubversionUpload()
						+ "/"
						+ subVersionUsuario.getDir());
			} else {
				subVersionUsuario.setUrlsubversionUpload(subVersionUsuario
						.getUrlsubversionUpload() + subVersionUsuario.getDir());
			}

			// actalizamos datos en la base de datos
			delegado.createSubVersionUsuario(subVersionUsuario);

		} catch (ExceptionSubVersion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	private String comunSvnUpload() {
		return "";
	}

	public String uploadSeleccionSvnSinFlow() {
		boolean swPrimeraVez = session.getAttribute("swPrimeraVez") == null;
		if (swPrimeraVez) {
			session.setAttribute("swPrimeraVez", true);
		}
		String pagIr = "";
		String urlsubversionUploadOriginal = subVersionUsuario
				.getUrlsubversionUpload();
		System.out.println("inicio subVersionUsuario.getUrlsubversionUpload()="
				+ subVersionUsuario.getUrlsubversionUpload());
		String urlNueva = "";
		String prefijo = "_ecolo_";
		List<File> listFiles = null;
		// si la url para upload esta vacio .. dara un mensaje de error
		if (isEmptyOrNull(urlsubversionUploadOriginal)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
		}
		try {
			// /COMO YA HAY UNA FOTO DEL REPOSITOIO,
			// NO C VUELVE A CREAR MAS FOTOS MIENTRAS SE SIGA DESCARGANDO LOS
			// ZIPS
			// swRepositorioCopiado=session.getAttribute("swRepositorioCopiado")!=null;

			// agarramos el file seleccionado
			List<String> files = new ArrayList<String>();
			if (listaRepositorio != null && listaRepositorio.size() > 0) {
				for (Repositorio ar : listaRepositorio) {
					if ((selectedIds != null)
							&& (selectedIds.get(ar.getCodigo()) != null)) {
						if (selectedIds.get(ar.getCodigo()).booleanValue()) {
							files.add(ar.getArchivo());
							subVersionUsuario.setFilePath(ar.getArchivo());
						}
					}
				}
			}
			if (files == null || files.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("NO "
								+ messages.getString("seleccionvacia")));
				return "";
			}

			if (files != null && files.size() > 0) {
				subVersionUsuario.setFilePaths(files);
				RepositorioSVN repositorioSVN = new RepositorioSVN();
				boolean swUpload = false;
				listFiles = repositorioSVN.obtenerListaFiles(subVersionUsuario,
						swUpload);

				if (swPrimeraVez) {
					// creamos una rama... el mismo nombre.-. pero con diferente
					// fecha
					String ramaCrearDelUrl = subVersionUsuario
							.getUrlsubversionUpload();
					// sin es una rama con la fecha puesta de ecological.. la
					// quitamos..
					if (subVersionUsuario.getUrlsubversionUpload().indexOf(
							prefijo) != -1) {
						subVersionUsuario
								.setUrlsubversionUpload(subVersionUsuario
										.getUrlsubversionUpload()
										.substring(
												0,
												subVersionUsuario
														.getUrlsubversionUpload()
														.indexOf(prefijo)));
						ramaCrearDelUrl = subVersionUsuario
								.getUrlsubversionUpload();
					}
					String ultimoNombreUrlStr = ultimoNombreUrl(ramaCrearDelUrl);
					// QUITAMOS EL NOMBRE ORIGINAL PARA COLOCAR UN NOMBRE NUEVO
					String fecha = Utilidades.sdfShowConvert.format(new Date());
					fecha = fecha.replace(" ", "_").toLowerCase();
					fecha = fecha.replace("-", "_").toLowerCase();
					fecha = fecha.replace(":", "_").toLowerCase();

					String raizUrl = subVersionUsuario.getUrlsubversionUpload()
							.replaceAll(ultimoNombreUrlStr, "");
					if (raizUrl.endsWith("//")) {
						raizUrl = raizUrl.substring(0, raizUrl.length() - 1);
					}

					ultimoNombreUrlStr = ultimoNombreUrlStr.replace("/", "");
					String nuevoNombre = ultimoNombreUrlStr + prefijo + fecha;
					if (raizUrl.endsWith("/")) {
						urlNueva = raizUrl + nuevoNombre;
					} else {
						urlNueva = raizUrl + "/" + nuevoNombre;
					}

					// String subirIn=urlsubversionUploadOriginal+"/"+urlNueva;

					// creamos el directorio en la nueva raiz url
					RepositorioSVN tester = new RepositorioSVN();
					// CREAMOS LA RAMA O FOTO
					// sustituimos por la orginal para hacer la copia
					subVersionUsuario
							.setUrlsubversionUpload(urlsubversionUploadOriginal);
					boolean uploadSVNAux = true;
					// si el repositorio no sta vacio.. se creara una foto del
					// mismo
					listaRepositorio = listarepositorioSVN(subVersionUsuario,
							uploadSVNAux);
					if (listaRepositorio != null && !listaRepositorio.isEmpty()) {
						subVersionUsuario.setDir(urlNueva);
						// creamos la rama
						tester.createBranchesOrTagsRepositorio(subVersionUsuario);
					} else {
						subVersionUsuario.setDir(urlsubversionUploadOriginal);
					}
				}

			}

			if (swPrimeraVez) {
				// subimos al repositorio
				// si el repositorio no sta vacio.. se creara una foto del mismo
				if (listaRepositorio != null && !listaRepositorio.isEmpty()) {
					subVersionUsuario.setUrlsubversionUpload(urlNueva);
				} else {
					// si esta vacio, se descargga en el mismo directorio.. sin
					// crear una foto
					subVersionUsuario
							.setUrlsubversionUpload(urlsubversionUploadOriginal);
				}
			} else {
				subVersionUsuario
						.setUrlsubversionUpload(urlsubversionUploadOriginal);
			}

			RepositorioSVN repositorioSVN = new RepositorioSVN();
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;
			if (files != null && files.size() > 0) {
				for (File file : listFiles) {
					subVersionUsuario.setFile(file);

					repositorioSVN.subirFileZipInRepositorio(subVersionUsuario);
				}

				if (swPrimeraVez) {
					// actalizamos datos en la base de datos
					// subimos al repositorio
					// si el repositorio no sta vacio.. se creara una foto del
					// mismo
					if (listaRepositorio != null && !listaRepositorio.isEmpty()) {
						subVersionUsuario.setUrlsubversionUpload(urlNueva);
						session.setAttribute("mensaje", urlNueva);
					} else {
						// si esta vacio, se descargga en el mismo directorio..
						// sin crear una foto
						subVersionUsuario
								.setUrlsubversionUpload(urlsubversionUploadOriginal);
						session.setAttribute("mensaje",
								urlsubversionUploadOriginal);
					}
					session.setAttribute("swUrl", true);
				} else {
					// si no es primera vez.. se sigue descargando los archivos
					// a la misma url
					subVersionUsuario
							.setUrlsubversionUpload(urlsubversionUploadOriginal);
				}

				System.out
						.println("subVersionUsuario.getUrlsubversionUpload()="
								+ subVersionUsuario.getUrlsubversionUpload());
				delegado.createSubVersionUsuario(subVersionUsuario);
				subVersionUsuario = delegado.find(subVersionUsuario);

				pagIr = "";// Utilidades.getFinexitoso();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
				// /COMO YA HAY UNA FOTO DEL REPOSITOIO,
				// NO C VUELVE A CREARA MAS FOTOS MIENTRAS SE SIGA DESCARGANDO
				// LOS ZIPS
				// session.setAttribute("swRepositorioCopiado", true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
			}
			// fin si viene de un work flow .. nos vamos para el workflow con el
			// archivo
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error") + e));
			e.printStackTrace();
		}
		return pagIr;
	}

	public String uploadSeleccionSvn() {

		subVersionUsuario
				.setUrlsubversionUploadAutomatico(session
						.getAttribute("urlsubversionUploadAutomatico") != null ? (String) session
						.getAttribute("urlsubversionUploadAutomatico") : "");
		

		if (automaticoCargaSvn
				&& isEmptyOrNull(subVersionUsuario
						.getUrlsubversionUploadAutomatico())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("seleccionvacia")));
			return "";
		}
		if (isEmptyOrNull(subVersionUsuario.getComentario())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("comentario")));
			return "";
		}

		if (!isEmptyOrNull(subVersionUsuario.getUrlsubversionUploadAutomatico())) {

			subVersionUsuario.setUrlsubversionUpload(subVersionUsuario
					.getUrlsubversionUploadAutomatico());

		}
		
		
		if (automaticoCargaSvn) {
			if (getSelectOneMenu1().getValue()!=null &&
					getSelectOneMenu2().getValue()!=null &&
					getSelectOneMenu3().getValue()!=null &&
					getSelectOneMenu4().getValue()!=null){
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
			}
			
			
			svnModulo = (SvnModulo) getSelectOneMenu4().getValue();
			if (svnModulo ==null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
			}
			
			svnTipoAmbiente = (SvnTipoAmbiente) getSelectOneMenu2().getValue();
			if (svnTipoAmbiente!=null && svnTipoAmbiente
					.getNombre()!=null){
				subVersionUsuario.setUrlarchivorelativoUpload(svnTipoAmbiente
						.getNombre() + "/" + svnModulo);
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
			}
			
		}
		

		// esto me permite saber en el metodo uploadSeleccionSvnSinFlow si hay
		// que hacer una nuecva rama si vamos o no a subir archivos
		// individuales a subersion
		boolean swPrimeraVez = session.getAttribute("swPrimeraVez") == null;
		if (swPrimeraVez) {
			session.setAttribute("swPrimeraVez", true);
		}

		// fin--
		String pagIr = "";
		String urlsubversionUploadOriginal = subVersionUsuario
				.getUrlsubversionUpload();

		String urlNueva = "";
		String prefijo = "_ecolo_";

		if (isEmptyOrNull(urlsubversionUploadOriginal)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
		}

		try {
			// /COMO YA HAY UNA FOTO DEL REPOSITOIO,
			// NO C VUELVE A CREAR MAS FOTOS MIENTRAS SE SIGA DESCARGANDO LOS
			// ZIPS
			// swRepositorioCopiado=session.getAttribute("swRepositorioCopiado")!=null;

			// agarramos el file seleccionado
			List<SvnUpload> svnUploadSeleccionados = new ArrayList<SvnUpload>();
			if (svnUploadLista != null && svnUploadLista.size() > 0) {
				for (SvnUpload ar : svnUploadLista) {
					if ((selectedIdsUploads != null)
							&& (selectedIdsUploads.get(ar
									.getCodigoParticipante()) != null)) {
						if (selectedIdsUploads.get(ar.getCodigoParticipante())
								.booleanValue()) {
							svnUploadSeleccionados.add(ar);
						}
					}
				}
			}
			if (svnUploadSeleccionados != null
					&& svnUploadSeleccionados.size() > 0) {

				// CREAMOS LA RAMA O FOTO
				// sustituimos por la orginal para hacer la copia

				boolean uploadSVNAux = true;

				// si el repositorio no sta vacio.. se creara una foto del mismo
				// listaRepositorio = listarepositorioSVN(subVersionUsuario,
				// uploadSVNAux);
				// if (listaRepositorio != null && !listaRepositorio.isEmpty())
				// {
				RepositorioSVN tester = new RepositorioSVN();
				// QUITAMOS EL NOMBRE ORIGINAL PARA COLOCAR UN NOMBRE NUEVO
				String fecha = Utilidades.sdfShowConvert.format(new Date());
				fecha = fecha.replace(" ", "_").toLowerCase();
				fecha = fecha.replace("-", "_").toLowerCase();
				fecha = fecha.replace(":", "_").toLowerCase();
				boolean swRutaTagCongelar = true;
				SubVersionUsuario subVersionUsuarioHilo = new SubVersionUsuario();

				subVersionUsuario
						.setUrlsubversionUpload(urlSacar(swRutaTagCongelar));
				String ultimoNombreUrlStr = ultimoNombreUrl(subVersionUsuario
						.getUrlsubversionUpload());
				// es el nombre de la carpetra a copiar
				// String ultimoNombreUrlStrCopy=ultimoNombreUrlStr;

				// String raizUrl = subVersionUsuario.getUrlsubversionUpload()
				// .replaceAll(ultimoNombreUrlStr, "");
				String raizUrl = subVersionUsuario.getUrlsubversionUpload();

				subVersionUsuario.setUrlsubversionUpload(raizUrl);

				String rutaFoto = urlSacar(swRutaTagCongelar) + prefijo + fecha;

				subVersionUsuario.setDir(rutaFoto);

				subVersionUsuarioHilo.setUsuariosubversion(subVersionUsuario
						.getUsuariosubversion());
				subVersionUsuarioHilo.setPasswordsubversion(subVersionUsuario
						.getPasswordsubversion());
				subVersionUsuarioHilo.setComentario(subVersionUsuario
						.getComentario());
				subVersionUsuarioHilo.setDir(subVersionUsuario.getDir());
				subVersionUsuarioHilo.setUrlsubversionUpload(subVersionUsuario
						.getUrlsubversionUpload());
				CopiarBranchesTags copiarBranchesTags = new CopiarBranchesTags(
						subVersionUsuarioHilo);
				copiarBranchesTags.start();
				// tester.createBranchesOrTagsRepositorio(subVersionUsuario);
				// } else {
				subVersionUsuario
						.setUrlsubversionUpload(urlsubversionUploadOriginal);
				// subVersionUsuario.setDir(urlsubversionUploadOriginal);
				// }

			}

			RepositorioSVN repositorioSVN = new RepositorioSVN();
			RepositorioSVNCliente repositorioSVNCliente = new RepositorioSVNCliente();
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;
			if (svnUploadSeleccionados != null
					&& svnUploadSeleccionados.size() > 0) {
				for (SvnUpload ar : svnUploadSeleccionados) {
					// si es == a -1 quiere decir que es el documentos
					// principal.. no es ningun attachment
					if (ar.getCodigoParticipante() != -1) {

						Flow_Participantes flow_Participante = new Flow_Participantes();
						flow_Participante.setCodigo(ar.getCodigoParticipante());
						flow_Participante = (Flow_Participantes) delegado
								.findFlow(flow_Participante);

						// aqui buscamois el archivo que attchment el usuario
						flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
						flow_ParticipantesAttachment
								.setFlowParticipantes(flow_Participante);
						flow_ParticipantesAttachment.setStatus(Utilidades
								.isActivo());
						flow_ParticipantesAttachment = delegado
								.find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);
						// colocamos una bandera que se ha subido..
						if (flow_ParticipantesAttachment != null) {
							flow_ParticipantesAttachment.setSwSVNUpload(true);
							flow_ParticipantesAttachment
									.setUrlsubversionUpload(subVersionUsuario
											.getUrlsubversionUpload());
							delegado.edit(flow_ParticipantesAttachment);
						}

						File file = delegado
								.filetFlowAttachment(flow_ParticipantesAttachment);
						subVersionUsuario
								.setFlow_ParticipantesAttachment(flow_ParticipantesAttachment);

						if (file != null && file.length() > 0) {
							subVersionUsuario.setFile(file);
							if (automaticoCargaSvn) {
								repositorioSVNCliente
										.subirFileZipInRepositorio(subVersionUsuario);
							} else {
								repositorioSVN
										.subirFileZipInRepositorio(subVersionUsuario);
							}
						}

					} else {
						long codigoFlowPrincipal = 0;
						for (SvnUpload lista : svnUploadLista) {
							if (lista.getCodigoParticipante() == -1) {
								codigoFlowPrincipal = lista.getCodigo();
								break;
							}
						}
						Flow flow = new Flow();
						flow.setCodigo(codigoFlowPrincipal);
						flow = delegado.findFlow(flow);
						subVersionUsuario.setDoc_maestro(flow.getDoc_detalle()
								.getDoc_maestro());
						subVersionUsuario
								.setFile(delegado.fileAttachmentDocDetalle(flow
										.getDoc_detalle()));
						if (automaticoCargaSvn) {
							
							repositorioSVNCliente
									.subirFileZipInRepositorio(subVersionUsuario);
						} else {
							repositorioSVN
									.subirFileZipInRepositorio(subVersionUsuario);
						}

					}

				}

				// actalizamos datos en la base de datos
				// subimos al repositorio
				// si el repositorio no sta vacio.. se creara una foto del mismo
				// if (listaRepositorio != null && !listaRepositorio.isEmpty())
				// {
				// subVersionUsuario.setUrlsubversionUpload(urlNueva);
				// session.setAttribute("mensaje", urlNueva);
				// } else {
				// si esta vacio, se descargga en el mismo directorio.. sin
				// crear una foto
				// SIEMPORE SE TRABAJARA LA URL ORIGINAL--
				subVersionUsuario
						.setUrlsubversionUpload(urlsubversionUploadOriginal);
				session.setAttribute("mensaje", urlsubversionUploadOriginal);
				// }

				session.setAttribute("swUrl", true);

				if (isEmptyOrNull(subVersionUsuario.getUrlsubversion())) {
					subVersionUsuario.setUrlsubversion("http://localhost");
					/*
					 * SubVersionUsuario subVersionUsuarioAux = delegado
					 * .find(subVersionUsuario);
					 * 
					 * subVersionUsuario.setUrlsubversion(subVersionUsuarioAux
					 * .getUrlsubversion() != null ? subVersionUsuarioAux
					 * .getUrlsubversion() : urlsubversionUploadOriginal);
					 */
				}

				delegado.createSubVersionUsuario(subVersionUsuario);
				subVersionUsuario = delegado.find(subVersionUsuario);

				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", "listarAplicacion");
				session.setAttribute("swUrl", true);
				/*
				 * FacesContext.getCurrentInstance().addMessage( null, new
				 * FacesMessage(messages .getString("operacion_exitosa")));
				 */
				// /COMO YA HAY UNA FOTO DEL REPOSITOIO,
				// NO C VUELVE A CREARA MAS FOTOS MIENTRAS SE SIGA DESCARGANDO
				// LOS ZIPS
				// session.setAttribute("swRepositorioCopiado", true);

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
			}
			// fin si viene de un work flow .. nos vamos para el workflow con el
			// archivo
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("lastrevision") +": "+ e.getMessage()));
		//	e.printStackTrace();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error") + e));
			e.printStackTrace();
		}

		return pagIr;
	}

	private String ext(String nombre) {
		String extension = nombre.substring(nombre.lastIndexOf(".") + 1,
				nombre.length());
		return extension;
	}

	public String aceptar() {
		String pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", "listarAplicacion");
		session.setAttribute("swUrl", true);
		return pagIr;
	}

	public void refrescar() {
		if (!subVersionUsuario.isSwBusquedaRecursiva()
				&& subVersionUsuario.getStartRevision() == 0
				&& subVersionUsuario.getVersion() == -1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Las versiones deben ser diferente a 0 y -1 "));

		} else {
			// *******************************************
			// REFRESCAMOS EL REPOSITORIO..........
			listaRepositorio = listarepositorioSVN(subVersionUsuario);
			// *******************************************
			try {
				delegado.createSubVersionUsuario(subVersionUsuario);
				subVersionUsuario = delegado.find(subVersionUsuario);
			} catch (ExceptionSubVersion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("listaRepositorio", listaRepositorio);
			// return "";
		}
	}

	public String viewDocumentoAttachment() {
		boolean flowPrincipal = session.getAttribute("flowPrincipal") != null;
		session.removeAttribute("flowPrincipal");
		String pagIr = "";
		session.setAttribute("onlyView", "onlyView");
		if (flowPrincipal) {
			session.getAttribute("doc_detalle");
			pagIr = "viewPDF";
		} else {
			Flow_ParticipantesAttachment detalle = (Flow_ParticipantesAttachment) session
					.getAttribute("attachment");
			pagIr = "viewAttachment";
		}

		return pagIr;
	}

	public void selectCommunAttachment(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdAttach");
		if ((component != null)
				&& (!super.isEmptyOrNull(component.getValue().toString()))) {
			long id = Long.parseLong(component.getValue().toString());
			if (id != -1) {
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
					System.out.println("flow_ParticipantesAttachment="
							+ flow_ParticipantesAttachment.getCodigo());
					session.setAttribute("attachment",
							flow_ParticipantesAttachment);
					System.out.println("flow_ParticipantesAttachment codigo ="
							+ flow_ParticipantesAttachment.getCodigo());
				}
			} else {
				long codigoFlowPrincipal = 0;
				for (SvnUpload lista : svnUploadLista) {
					if (lista.getCodigoParticipante() == -1) {
						codigoFlowPrincipal = lista.getCodigo();
						break;
					}
				}
				Flow flow = new Flow();
				flow.setCodigo(codigoFlowPrincipal);
				flow = delegado.findFlow(flow);
				session.setAttribute("flowPrincipal", true);
				session.setAttribute("doc_detalle", flow.getDoc_detalle());
				session.setAttribute("onlyView", "onlyView");
			}

		} // se va a firmar

	}

	public void cancelarllenadosdeLista() {
		String pagIr = "";
		session.removeAttribute("filesllenadosdeLista");
		session.removeAttribute("filesllenadosdeListaVista");
		session.removeAttribute("filesllenadosdeListaObjeto");
	}

	public void llenarLista() {
		String pagIr = "";
		StringBuffer str = new StringBuffer("");
		// agarramos el file seleccionado
		// List<String> files = new ArrayList<String>();
		if (listaRepositorio != null && listaRepositorio.size() > 0) {
			boolean swPrimeraVez = true;
			filesllenadosdeListaObjeto = session
					.getAttribute("filesllenadosdeListaObjeto") != null ? (Map) session
					.getAttribute("filesllenadosdeListaObjeto") : null;
			if (filesllenadosdeListaObjeto == null) {
				filesllenadosdeListaObjeto = new HashMap<String, Object>();
			}
			for (Repositorio repo : listaRepositorio) {
				if ((selectedIds != null)
						&& (selectedIds.get(repo.getCodigo()) != null)) {
					if (selectedIds.get(repo.getCodigo()).booleanValue()) {
						// files.add(ar.getArchivo().trim());
						if (!filesllenadosdeListaObjeto.containsKey(repo
								.getArchivo().trim())) {
							System.out.println("->" + repo.getArchivo().trim());
							filesllenadosdeListaObjeto.put(repo.getArchivo()
									.trim(), repo);
						}

					}
				}
			}

			session.setAttribute("filesllenadosdeListaObjeto",
					filesllenadosdeListaObjeto);
			Iterator it = filesllenadosdeListaObjeto.keySet().iterator();
			boolean swPrimeravez = true;
			// session.removeAttribute("filesllenadosdeLista");
			session.removeAttribute("filesllenadosdeListaVista");

			// estos son los archivos que se muetran por pantalla
			filesllenadosdeListaVista = "";
			HashMap unicoPOrSiAcaso = new HashMap<String, String>();
			while (it.hasNext()) {
				String clave = (String) it.next();
				Repositorio repositorio = (Repositorio) filesllenadosdeListaObjeto
						.get(clave);
				int nom = repositorio.getArchivo().lastIndexOf("/") != -1 ? repositorio
						.getArchivo().lastIndexOf("/") + 1 : 0;

				String nomArchivo = repositorio.getArchivo()
						.substring(nom, repositorio.getArchivo().length())
						.trim();

				if (!unicoPOrSiAcaso.containsKey(nomArchivo.trim())) {
					if (swPrimeravez) {
						// filesllenadosdeLista = filesllenadosdeLista
						// + (String) filesllenadosdeListaObjeto
						// .get(clave);
						filesllenadosdeListaVista = filesllenadosdeListaVista
								+ nomArchivo;
						swPrimeravez = false;
					} else {
						// filesllenadosdeLista = filesllenadosdeLista
						// + " ¬   "
						// + (String) filesllenadosdeListaObjeto
						// .get(clave);
						filesllenadosdeListaVista = filesllenadosdeListaVista
								+ " ¬ " + nomArchivo;
					}

					unicoPOrSiAcaso.put(nomArchivo.trim(), nomArchivo.trim());
				}

			}
			// session.setAttribute("filesllenadosdeLista",
			// filesllenadosdeLista);
			session.setAttribute("filesllenadosdeListaVista",
					filesllenadosdeListaVista);
			// listaRepositorio = new ArrayList<Repositorio>();
			// session.removeAttribute("listaRepositorio") ;
		}

	}

	public String create() {
		String pagIr = "";
		Map unico = new HashMap();
		try {

			if (isEmptyOrNull(subVersionUsuario.getNombreZip())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("NO "
								+ messages.getString("falta_data")));
				return "";
			}

			if (isEmptyOrNull(subVersionUsuario.getBusquedakeys())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("NO "
								+ messages.getString("falta_data")));
				return "";
			}
			subVersionUsuario
			.setUrlsubversionUploadAutomatico(session
					.getAttribute("urlsubversionUploadAutomatico") != null ? (String) session
							.getAttribute("urlsubversionUploadAutomatico") : "");
			subVersionUsuario.setUrlsubversionUpload(session
					.getAttribute("urlsubversionUploadAutomatico") != null ? (String) session
							.getAttribute("urlsubversionUploadAutomatico") : "");
			
			 		if (isEmptyOrNull(subVersionUsuario
							.getUrlsubversionUploadAutomatico())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("seleccionvacia")));
				return "";
			}
			 		
			 		
			// agarramos el file seleccionado
			List<Repositorio> repositorios = new ArrayList<Repositorio>();

			filesllenadosdeListaObjeto = session
					.getAttribute("filesllenadosdeListaObjeto") != null ? (Map) session
					.getAttribute("filesllenadosdeListaObjeto") : null;
			if (filesllenadosdeListaObjeto == null) {
				filesllenadosdeListaObjeto = new HashMap<String, Object>();
			}
			Iterator it = filesllenadosdeListaObjeto.keySet().iterator();
			// estos son los archivos que se muetran por pantalla
			HashMap unicoPOrSiAcaso = new HashMap<String, String>();
			while (it.hasNext()) {
				String clave = (String) it.next();
				Repositorio repositorio = (Repositorio) filesllenadosdeListaObjeto
						.get(clave);
				if (!unico.containsKey(repositorio.getArchivo().toLowerCase())) {
					repositorio.setArchivo(repositorio.getArchivo().trim());
					repositorios.add(repositorio);
					unico.put(repositorio.getArchivo().toLowerCase(),
							repositorio.getArchivo().toLowerCase());
				}
			}

		 

			if (listaRepositorio != null && listaRepositorio.size() > 0) {
				for (Repositorio ar : listaRepositorio) {
					if ((selectedIds != null)
							&& (selectedIds.get(ar.getCodigo()) != null)) {
						if (selectedIds.get(ar.getCodigo()).booleanValue()) {
							if (!unico.containsKey(ar.getArchivo()
									.toLowerCase())) {
								ar.setArchivo(ar.getArchivo().trim());
								repositorios.add(ar);
								System.out.println("ar.getArchivo().trim()="
										+ ar.getArchivo().trim());
								unico.put(ar.getArchivo().toLowerCase(), ar
										.getArchivo().toLowerCase());
							}

							// subVersionUsuario.setFilePath(ar.getArchivo());
						}
					}
				}
			}

			if (repositorios == null || repositorios.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("NO "
								+ messages.getString("seleccionvacia")));
				return "";
			}

			//ultima revision de produccion
	 		revision=session.getAttribute("revision")!=null?(Long)session.getAttribute("revision"):0;
	 		subVersionUsuario.setNumeroRevision(revision+"");
	 		subVersionUsuario.setNombreZip(subVersionUsuario.getNombreZip()
	 				+" ("+messages.getString("enrevision")+":"+revision+")");
			
			subVersionUsuario.setRepositorioLst(repositorios);
			// actalizamos datos en la base de datos
			delegado.createSubVersionUsuario(subVersionUsuario);
			// buscamos en el repositorio....

			RepositorioSVN repositorioSVN = new RepositorioSVN();
			boolean swUpload = false;
			try {

				subVersionUsuario = repositorioSVN.obtenerFile(
						subVersionUsuario, swUpload);

			} catch (ExceptionSubVersion e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

			// ----------------------------------------------------------

			File _upFileFile = null;
			String nomArch = "";
			String ext = null;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			// if cont >0.. escojio un archivo para agregar al ecological de
			// subversion...
			if (subVersionUsuario.getFile() != null) {

				nomArch = sacarSlashFiltro(subVersionUsuario.getFile()
						.getName());

				FileInputStream streamArchivo = null;

				Archivo arUtil = new Archivo();
				InputStream inputStream = arUtil
						.fileToinputStream(subVersionUsuario.getFile());
				if (nomArch.lastIndexOf(".") != -1) {
					ext = nomArch.substring(nomArch.lastIndexOf(".") + 1,
							nomArch.length());
					String nom2 = nomArch.substring(0,
							nomArch.lastIndexOf(".") - 1);
					_upFileFile = arUtil
							.fileDesdeStream(inputStream, nom2, ext);

				} else {

					throw new EcologicaExcepcionesContextType(
							(messages.getString("doc_ext") + ":" + (nomArch != null ? nomArch
									: "")));
				}
				// tratamos de borrar el archivo para que no quede basura
				if (subVersionUsuario.getFile() != null) {
					subVersionUsuario.getFile().delete();
				}

				// si viene de un work flow .. nos vamos para el workflow con el
				// archivo
				if (!isEmptyOrNull(irFlowsResponse)) {
					// nos vamos a flowresponse
					if (_upFileFile != null) {
						session.setAttribute("_upFileFile",
								subVersionUsuario.getFile());
						// ---llenamos la tabla de mapeo donde van los archoivos
						// de subversion
						List<SvnRutasRelativasFiles> rutasRelativaSVN = svnRutasRelativasFilesLst(subVersionUsuario);
						session.setAttribute("rutasRelativaSVN",
								rutasRelativaSVN);
						// ---fin llenamos la tabla de mapeo donde van los
						// archoivos de subversion
					}
					session.removeAttribute("flowsResponse");
					return Utilidades.getFlowsResponse();
				}
				// fin si viene de un work flow .. nos vamos para el workflow
				// con el archivo

				//
				// verificamos que si es bd postgres, no exceda los 8mb en data
				if (swConfiguracionHayData) {
					if (swPostgresVieneDeConfiguracion) {
						if ((Double.compare(converBytesMbytes(subVersionUsuario
								.getFile().length()), Utilidades
								.getPostgresMBmax()) > 0)) {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("postgmenorochomb")));
							return "";
						}
					}
				} else if ("1".equalsIgnoreCase(confmessages
						.getString("bdpostgres"))) {
					if ((Double
							.compare(converBytesMbytes(subVersionUsuario
									.getFile().length()), Utilidades
									.getPostgresMBmax()) > 0)) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("postgmenorochomb")));
						return "";
					}

				}

				Tree tree = new Tree();
				Calendar fecha = Calendar.getInstance();
				tree.setNodopadre(treeNodoActual.getNodo());
				// si es editar, no va hacer getNodoDocumento para que no se vea
				// reflejado en el aire
				tree.setTiponodo(Utilidades.getNodoDocumento());

				// tree.setFecha_creado(fecha.getTime());
				Date date = new Date();

				tree.setFecha_creado(date);
				tree.setMaquina(super.getMaquinaConectada());

				// a que empresa pertenece el dopcumento
				Doc_maestro doc_maestro = new Doc_maestro();
				doc_maestro.setSwSVN(true);
				if (user_logueado != null) {
					doc_maestro.setEmpresa(user_logueado.getEmpresa());
				}
				// _____________________________________________//
				// grabnamos el tree nuevo
				tree.setDescripcion(subVersionUsuario.getBusquedakeys());
				if (isEmptyOrNull(doc_maestro.getNombre())) {
					doc_maestro.setNombre(subVersionUsuario.getNombreZip());

				}
				doc_maestro
						.setBusquedakeys(subVersionUsuario.getBusquedakeys());
				tree.setNombre(doc_maestro.getNombre());

				// _____________________________________________//
				if (treeNodoActual == null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("error_tree")));
					return "";
				}

				doc_maestro.setConsecutivo("00000000");
				Doc_tipo doc_tipo = new Doc_tipo();
				doc_tipo.setCodigo(Utilidades.getRegistroTipoDoc());
				doc_tipo = delegado.findTipoDoc(doc_tipo);
				doc_maestro.setDoc_tipo(doc_tipo);

				doc_detalle.setDescripcion(subVersionUsuario.getMensaje());

				Doc_detalle docDetalle = new Doc_detalle();
				docDetalle.setDoc_maestro(doc_maestro);

				Doc_estado doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getBorrador());
				doc_estado = delegado.findDocEstado(doc_estado);
				doc_detalle.setDoc_estado(doc_estado);
				Map llenarSessiones = new HashMap();

				HashMap contextType = new HashMap();
				boolean soportaExtension = extensionAcepotadaToConverter(ext,
						contextType);
				String textPlain = "";
				if (soportaExtension) {
					textPlain = (String) contextType.get("1");
				}
				if (isEmptyOrNull(textPlain)) {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage("NO "
									+ messages
											.getString("extensionesSoportadas")
									+ " " + ext));
					return "";
				}

				delegadoEcological.upload(tree, doc_maestro, doc_detalle,
						_upFileFile, user_logueado, textPlain, llenarSessiones);

				if (_upFileFile != null) {
					_upFileFile.delete();
				}
				String nombreArchivo = "";
				// ---llenamos la tabla de mapeo donde van los archoivos de
				// subversion
				List<SvnRutasRelativasFiles> rutasRelativas = svnRutasRelativasFilesLst(subVersionUsuario);
				for (SvnRutasRelativasFiles svnRutasRelativasFiles : rutasRelativas) {
					svnRutasRelativasFiles.setDoc_maestro(doc_maestro);
					delegado.create(svnRutasRelativasFiles);
				}
				// -fin--llenamos la tabla de mapeo donde van los archoivos de
				// subversion

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

			}

		} catch (ExceptionSubVersion e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (EcologicaExcepcionesContextType e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}

		return pagIr;
	}

	public List<SvnRutasRelativasFiles> svnRutasRelativasFilesLst(
			SubVersionUsuario subVersionUsuario) {

		//verificamos donde el usuario va  a subir a produccion stros archivos
		long revision=0;
		try {
			RepositorioSVN tester = new RepositorioSVN();
			String urlUpload=subVersionUsuario
			.getUrlsubversionUploadAutomatico();
			subVersionUsuario.setUrlsubversionUpload(subVersionUsuario
					.getUrlsubversionUploadAutomatico());
			boolean swUpload=true;
			revision=tester.lastInfoBranches(subVersionUsuario,swUpload);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<SvnRutasRelativasFiles> rutas = new ArrayList<SvnRutasRelativasFiles>();
		// ---llenamos la tabla de mapeo donde van los archoivos de subversion
		String nombreArchivo = "";
		for (Repositorio repositorio : subVersionUsuario.getRepositorioLst()) {
			SvnRutasRelativasFiles svnRutasRelativasFiles = new SvnRutasRelativasFiles();

			subVersionUsuario.setFilePath(repositorio.getArchivo().trim());
			int nom = subVersionUsuario.getFilePath().lastIndexOf("/") != -1 ? subVersionUsuario
					.getFilePath().lastIndexOf("/") + 1 : 0;
			nombreArchivo = subVersionUsuario.getFilePath()
					.substring(nom, subVersionUsuario.getFilePath().length())
					.trim();
			svnRutasRelativasFiles.setUrlRepositorio(subVersionUsuario
					.getUrlsubversion());
			svnRutasRelativasFiles.setUrlArchivoRelativo(subVersionUsuario
					.getFilePath().replace(nombreArchivo, ""));
			svnRutasRelativasFiles.setArchivo(nombreArchivo);
			rutas.add(svnRutasRelativasFiles);
			svnRutasRelativasFiles.setUrlsubversionUpload(subVersionUsuario.getUrlsubversionUpload());
			svnRutasRelativasFiles.setRevision(revision);

		}
		// --------------------------------------------
		return rutas;
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

		Object treeData = null;//new TreeNodeBase(
//				Utilidades.getIdentificaRaizTree(),
//				messages.getString("doc_hist") + " ", false);

		if (doc_historicoActivoMaestros != null
				&& !doc_historicoActivoMaestros.isEmpty()) {
			treeData =null;

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
				Object treeDetalle = null;//new TreeNodeBase("", "", false);
			 

				// vemos Actualizar Borrador
			 
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
			Object treeDetalle = null;
			
			// treeDetalle1.setIdentifier(String.valueOf(doc_historico.getUsuario().getId())
			// );
			String fecha = Utilidades.sdfShow.format(doc_historicoDetalle
					.getFecha());
			String comentario = doc_historicoDetalle.getComentario() != null ? doc_historicoDetalle
					.getComentario() : "";
		 
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

	public List<ClienteDocumentoMaestro> getClienteDocumentoMaestros() {
		try {
			clienteDocumentoMaestros = getDoc_maestros();
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

	public SubVersionUsuario getSubVersionUsuario() {
		return subVersionUsuario;
	}

	public void setSubVersionUsuario(SubVersionUsuario subVersionUsuario) {
		this.subVersionUsuario = subVersionUsuario;
	}

	private List<Repositorio> listarepositorioSVN(
			SubVersionUsuario subVersionUsuario, boolean uploadSVN) {
		List<Repositorio> lista = null;
		try {
			RepositorioSVN repositorioSVN = new RepositorioSVN();
			lista = repositorioSVN.mostrarInfoSVN(subVersionUsuario, uploadSVN);
			return lista;

		} catch (Exception e) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages.getString("error")
									+ e.toString()));
		}

		return lista;
	}

	private List<Repositorio> listarepositorioSVN(
			SubVersionUsuario subVersionUsuario) {
		List<Repositorio> lista = null;
		try {
			RepositorioSVN repositorioSVN = new RepositorioSVN();
			lista = repositorioSVN.mostrarInfoSVN(subVersionUsuario, uploadSVN);
			// lista = repositorioSVN.mostrarInfoSVNFiltrado(subVersionUsuario,
			// uploadSVN);

			return lista;

		} catch (Exception e) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages.getString("error")
									+ e.toString()));
		}

		return lista;
	}

	public void setSwsubVersionUsuarioExiste(boolean swsubVersionUsuarioExiste) {
		swsubVersionUsuarioExiste = swsubVersionUsuarioExiste;
	}

	public List<Repositorio> getListaRepositorio() {
		// la session se crea en refrescar()
		listaRepositorio = session.getAttribute("listaRepositorio") != null ? (List<Repositorio>) session
				.getAttribute("listaRepositorio") : null;
		return listaRepositorio;
	}

	public void setListaRepositorio(List<Repositorio> listaRepositorio) {
		this.listaRepositorio = listaRepositorio;
	}

	public String getIrFlowsResponse() {
		return irFlowsResponse;
	}

	public void setIrFlowsResponse(String irFlowsResponse) {
		this.irFlowsResponse = irFlowsResponse;
	}

	public List<SvnUpload> getSvnUploadLista() {

		svnUploadLista = session.getAttribute("svnUploadLista") != null ? (List<SvnUpload>) session
				.getAttribute("svnUploadLista") : null;

		return svnUploadLista;
	}

	public void setSvnUploadLista(List<SvnUpload> svnUploadLista) {
		this.svnUploadLista = svnUploadLista;
	}

	public boolean isUploadSVN() {
		return uploadSVN;
	}

	public void setUploadSVN(boolean uploadSVN) {
		this.uploadSVN = uploadSVN;
	}

	public Map<Long, Boolean> getSelectedIdsUploads() {
		return selectedIdsUploads;
	}

	public void setSelectedIdsUploads(Map<Long, Boolean> selectedIdsUploads) {
		this.selectedIdsUploads = selectedIdsUploads;
	}

	public String getUltimoTagsBranchesTrunk() {
		return ultimoTagsBranchesTrunk;
	}

	public void setUltimoTagsBranchesTrunk(String ultimoTagsBranchesTrunk) {
		this.ultimoTagsBranchesTrunk = ultimoTagsBranchesTrunk;
	}

	public boolean isSwUploadSeleccionSvnSinFlow() {
		return swUploadSeleccionSvnSinFlow;
	}

	public void setSwUploadSeleccionSvnSinFlow(
			boolean swUploadSeleccionSvnSinFlow) {
		this.swUploadSeleccionSvnSinFlow = swUploadSeleccionSvnSinFlow;
	}

	public boolean isSwUploadSvnSinFlowJsp() {
		return swUploadSvnSinFlowJsp;
	}

	public void setSwUploadSvnSinFlowJsp(boolean swUploadSvnSinFlowJsp) {
		this.swUploadSvnSinFlowJsp = swUploadSvnSinFlowJsp;
	}

	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		session.setAttribute("svnUrlBase", svnUrlBase);
		this.svnUrlBase = svnUrlBase;
	}

	public SvnUrlBase getSvnUrlBase() {
		svnUrlBase = session.getAttribute("svnUrlBase") != null ? (SvnUrlBase) session
				.getAttribute("svnUrlBase") : null;

		return svnUrlBase;
	}

	public List<SelectItem> getAllSvnUrlBase() {
		Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		allSvnUrlBase = new ArrayList<SelectItem>();
		SelectItem item = null;
		SvnUrlBase svnUrlBase1 = new SvnUrlBase();
		svnUrlBase1.setCodigo(-1L);
		svnUrlBase1.setNombre("");
		item = new SelectItem(svnUrlBase1, svnUrlBase1.getNombre());
		allSvnUrlBase.add(item);
		try {
			List<SvnUrlBase> svnUrlBases = delegado.findAllSvnUrlBase(usuario,
					null);
			item = null;
			String nom = "";

			for (SvnUrlBase svnUrlBase : svnUrlBases) {

				if (svnUrlBase != null) {
					nom = svnUrlBase.getNombre() != null ? svnUrlBase
							.getNombre().trim() : "";
					item = new SelectItem(svnUrlBase, nom);
					allSvnUrlBase.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allSvnUrlBase;
	}

	public void change() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		allSvnModulo = new ArrayList<SelectItem>();
		getAllSvnNombreAplicacion();
	}

 /*
	public List<SelectItem> getAllSvnTipoAmbientes() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		SvnTipoAmbiente s = new SvnTipoAmbiente();
		s.setCodigo(-1L);
		s.setNombre("");
		SelectItem it = new SelectItem(s, s.getNombre());
		allSvnTipoAmbientes.add(it);
		if (getSelectOneMenu1() != null
				&& getSelectOneMenu1().getValue() != null) {
			svnUrlBase = (SvnUrlBase) getSelectOneMenu1().getValue();
		}
		if (svnUrlBase != null) {

			List<SelectItem> items2 = getAllSvnTipoAmbientes(svnUrlBase);
			if (items2 != null) {
			}
			allSvnTipoAmbientes.addAll(items2);
		}
		return allSvnTipoAmbientes;
	}*/

	public List<SelectItem> getAllSvnTipoAmbientes(SvnNombreAplicacion svnNombreAplicacion) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<SvnTipoAmbiente> objetos = delegado
					.findAllSvnTipoAmbiente(svnNombreAplicacion);
			SelectItem item = null;
			for (SvnTipoAmbiente objeto : objetos) {
				if (objeto != null) {
					item = new SelectItem(objeto, objeto.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void change2() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		allSvnModulo = new ArrayList<SelectItem>();
		getAllSvnTipoAmbientes();
	}

	public List<SelectItem> getAllSvnNombreAplicacion() {
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		SvnNombreAplicacion s = new SvnNombreAplicacion();
		s.setCodigo(-1L);
		s.setNombre("");
		SelectItem it = new SelectItem(s, s.getNombre());
		allSvnNombreAplicacion.add(it);
		if (getSelectOneMenu1() != null
				&& getSelectOneMenu1().getValue() != null) {
			svnUrlBase = (SvnUrlBase) getSelectOneMenu1().getValue();
		}
		if (svnUrlBase != null) {

			List<SelectItem> items2 = getAllSvnNombreAplicacion(svnUrlBase);
			if (items2 != null) {
			}
			allSvnNombreAplicacion.addAll(items2);
		}
		return allSvnNombreAplicacion;
	}

	public List<SelectItem> getAllSvnNombreAplicacion(
			SvnUrlBase svnUrlBase) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<SvnNombreAplicacion> objetos = delegado
					.findAllSvnNombreAplicacion(svnUrlBase);
			SelectItem item = null;
			for (SvnNombreAplicacion objeto : objetos) {
				if (objeto != null) {
					item = new SelectItem(objeto, objeto.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	 

	public List<SelectItem> getAllSvnTipoAmbientes() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		SvnTipoAmbiente s = new SvnTipoAmbiente();
		s.setCodigo(-1L);
		s.setNombre("");
		SelectItem it = new SelectItem(s, s.getNombre());
		allSvnTipoAmbientes.add(it);
		if (getSelectOneMenu3() != null
				&& getSelectOneMenu3().getValue() != null) {
			svnnombreaplicacion = (SvnNombreAplicacion) getSelectOneMenu3().getValue();
		}
		if (svnnombreaplicacion != null) {

			List<SelectItem> items2 = getAllSvnTipoAmbientes(svnnombreaplicacion);
			if (items2 != null) {
			}
			allSvnTipoAmbientes.addAll(items2);
		}
		return allSvnTipoAmbientes;
	}
	public List<SelectItem> getAllSvnModulo() {
		SvnTipoAmbiente svnTipoAmbiente = null;
		allSvnModulo = new ArrayList<SelectItem>();
		SelectItem item = null;
		SvnModulo svnModulo = new SvnModulo();
		svnModulo.setCodigo(-1L);
		svnModulo.setPathCorto1("");
		item = new SelectItem(svnModulo, svnModulo.toString());
		allSvnModulo.add(item);
		if (getSelectOneMenu2() != null
				&& getSelectOneMenu2().getValue() != null) {
			svnTipoAmbiente = (SvnTipoAmbiente) getSelectOneMenu2()
					.getValue();
			System.out.println(svnTipoAmbiente.getNombre());
			try {

				List<SvnModulo> lista = delegado
						.findAllSvnModulo(svnTipoAmbiente);
				item = null;
				if (lista != null) {
					for (SvnModulo objeto : lista) {
						if (objeto != null) {
							item = new SelectItem(objeto, objeto.toString());
							allSvnModulo.add(item);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// session.getAttribute("svnNombreAplicacion")!=null?
		// (SvnNombreAplicacion)session.getAttribute("svnNombreAplicacion"):null;

		return allSvnModulo;
	}

	public void change3() {
		allSvnModulo = new ArrayList<SelectItem>();
		getAllSvnModulo();
	}

	public void change4() {
		session.removeAttribute("urlsubversionUploadAutomatico");
		subVersionUsuario.setUrlsubversionUploadAutomatico("");

		boolean swRutaTagCongelar = false;
		String url = urlSacar(swRutaTagCongelar);
		subVersionUsuario.setUrlsubversionUploadAutomatico(url.toString());
		session.setAttribute("urlsubversionUploadAutomatico", url.toString());
		RepositorioSVN tester = new RepositorioSVN();
		boolean swUpload=true;
		try {
			revision=tester.lastInfoBranches(subVersionUsuario,swUpload);
			session.setAttribute("revision", revision);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String urlSacar(boolean swRutaTagCongelar) {
		StringBuffer url = new StringBuffer();
		if (getSelectOneMenu4() != null
				&& getSelectOneMenu4().getValue() != null) {
			svnModulo = (SvnModulo) getSelectOneMenu4().getValue();
		}
		if (svnModulo != null) {

			url.append(svnModulo.getSvnTipoAmbiente()
					.getSvnnombreaplicacion().getSvnUrlBase());

			//APLICACION
			if (!isEmptyOrNull(svnModulo
					.getSvnTipoAmbiente().getSvnnombreaplicacion().getNombre())) {
				if (url != null && url.toString().endsWith("/")) {
					url.append(svnModulo.getSvnTipoAmbiente()
							.getSvnnombreaplicacion().getNombre());
				} else {
					url.append("/").append(
							svnModulo.getSvnTipoAmbiente()
							.getSvnnombreaplicacion().getNombre());
				}
			}

		//AMBIENTE	
			if (!isEmptyOrNull(svnModulo.getSvnTipoAmbiente().getNombre())) {
				if (url != null && url.toString().endsWith("/")) {
					url.append(svnModulo.getSvnTipoAmbiente().getNombre());
				} else {
					url.append("/").append(
							svnModulo.getSvnTipoAmbiente().getNombre());
				}
			}
			//MODULO
			if (swRutaTagCongelar) {
				if (!isEmptyOrNull(svnModulo.getPathCorto1())) {
					if (url != null && url.toString().endsWith("/")) {
						url.append(svnModulo.toString());
					} else {
						url.append("/").append(svnModulo.getPathCorto1());
					}
				}
			} else {
				if (!isEmptyOrNull(svnModulo.toString())) {
					if (url != null && url.toString().endsWith("/")) {
						url.append(svnModulo.toString());
					} else {
						url.append("/").append(svnModulo.toString());
					}
				}
			}

		}
		return url.toString();

	}

	public void inicilizaChange() {
		allSvnUrlBase = new ArrayList<SelectItem>();
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		allSvnModulo = new ArrayList<SelectItem>();
		// session.removeAttribute("svnNombreAplicacion");
		// session.removeAttribute("svnModulo");
		// session.removeAttribute("svnUrlBase");

		subVersionUsuario.setUrlsubversionUploadAutomatico("");
		session.removeAttribute("urlsubversionUploadAutomatico");
	}

	public SvnNombreAplicacion getSvnNombreAplicacion() {
		svnNombreAplicacion = session.getAttribute("svnNombreAplicacion") != null ? (SvnNombreAplicacion) session
				.getAttribute("svnNombreAplicacion") : null;
		return svnNombreAplicacion;
	}

	public void setSvnNombreAplicacion(SvnNombreAplicacion svnNombreAplicacion) {
		session.setAttribute("svnNombreAplicacion", svnNombreAplicacion);
		this.svnNombreAplicacion = svnNombreAplicacion;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public SvnModulo getSvnModulo() {
		svnModulo = session.getAttribute("svnModulo") != null ? (SvnModulo) session
				.getAttribute("svnModulo") : null;
		return svnModulo;
	}

	public void setSvnModulo(SvnModulo svnModulo) {
		session.setAttribute("svnModulo", svnModulo);
		this.svnModulo = svnModulo;
	}

	public boolean isAutomaticoCargaSvn() {
		return automaticoCargaSvn;
	}

	public void setAutomaticoCargaSvn(boolean automaticoCargaSvn) {
		this.automaticoCargaSvn = automaticoCargaSvn;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public HtmlSelectOneMenu getSelectOneMenu2() {
		return selectOneMenu2;
	}

	public void setSelectOneMenu2(HtmlSelectOneMenu selectOneMenu2) {
		this.selectOneMenu2 = selectOneMenu2;
	}

	public HtmlSelectOneMenu getSelectOneMenu3() {
		return selectOneMenu3;
	}

	public void setSelectOneMenu3(HtmlSelectOneMenu selectOneMenu3) {
		this.selectOneMenu3 = selectOneMenu3;
	}

	public void setAllSvnNombreAplicacion(
			List<SelectItem> allSvnNombreAplicacion) {
		this.allSvnNombreAplicacion = allSvnNombreAplicacion;
	}

	public void setAllSvnModulo(List<SelectItem> allSvnModulo) {
		this.allSvnModulo = allSvnModulo;
	}

	public void setAllSvnUrlBase(List<SelectItem> allSvnUrlBase) {
		this.allSvnUrlBase = allSvnUrlBase;
	}

	public Map<String, Object> getFilesllenadosdeListaObjeto() {
		return filesllenadosdeListaObjeto;
	}

	public void setFilesllenadosdeListaObjeto(
			Map<String, Object> filesllenadosdeListaObjeto) {
		this.filesllenadosdeListaObjeto = filesllenadosdeListaObjeto;
	}

	public String getFilesllenadosdeListaVista() {
		filesllenadosdeListaVista = session
				.getAttribute("filesllenadosdeListaVista") != null ? (String) session
				.getAttribute("filesllenadosdeListaVista") : "";
		return filesllenadosdeListaVista;
	}

	public void setFilesllenadosdeListaVista(String filesllenadosdeListaVista) {
		this.filesllenadosdeListaVista = filesllenadosdeListaVista;
	}

	public SvnTipoAmbiente getSvnTipoAmbiente() {
		return svnTipoAmbiente;
	}

	public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}

	public HtmlSelectOneMenu getSelectOneMenu4() {
		return selectOneMenu4;
	}

	public void setSelectOneMenu4(HtmlSelectOneMenu selectOneMenu4) {
		this.selectOneMenu4 = selectOneMenu4;
	}

	public SvnNombreAplicacion getSvnnombreaplicacion() {
		return svnnombreaplicacion;
	}

	public void setSvnnombreaplicacion(SvnNombreAplicacion svnnombreaplicacion) {
		this.svnnombreaplicacion = svnnombreaplicacion;
	}

}
