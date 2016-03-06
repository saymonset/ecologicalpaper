package com.ecological.paper.cliente.usuarios;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.hilos.MySeguridad;

import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.historico.Hist_usuarios;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.Repositorio;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;

import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlInputHidden;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class ClienteUsuario extends ContextSessionRequest {
	private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
	private ServicioDelegado delegado = new ServicioDelegado();
	private HtmlSelectOneMenu selectArea;
	private HtmlSelectOneMenu selectEmpresa;
	private HtmlSelectOneMenu selectPrincipal;
	private String passwordOculta;
	private String passwordConfirm;
	private HtmlSelectOneMenu selectCargo;
	private HtmlOutputText id_str;
	private HtmlSelectOneMenu selectOneMenuProfesion;
	private HtmlSelectOneMenu selectOneMenuCargo;
	private List<Usuario> usuarios;
	private Context context = null;
	private String maquina;
	private Tree cargo;
	private Tree area;
	private Tree empresa;
	private Tree principal;
	private List<Tree> empresas;
	private List<SelectItem> allPrincipal;
	private List<SelectItem> allEmpresas;
	private List<SelectItem> allCargos;
	private List<SelectItem> AllProfesion;
	private Tree treeNodoActual = null;
	private String strBuscar;
	// private Usuario usu=null;
	private HttpSession session = super.getSession();
	private ClienteRole clienteRole = new ClienteRole();
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private Usuario user_logueado;
	private Usuario usuario;
	private String strError;
	private boolean swEditarUsuario;
	private char searchCargo;
	private char searchNombre;
	private char searchApellido;
	private char buscarPor;
	private char searchLogin;
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private Map nodosEmpresa = new HashMap();
	private String archNoCargados;
	private boolean swArchNoCargados;
	// SEGURIDAD
	private Seguridad seguridadMenu = new Seguridad();
	private boolean swModUsuario;
	private boolean swDelUsuario;
	private boolean swAddUsuario;
	private boolean swSuperUsuario;
	private Usuario usuarioEliminar;
	private boolean swHistorico;
	private Seguridad seguridadTree = new Seguridad();
	private int izquierdaBD;
	private int derechaBD;
	private List<Hist_usuarios> hist_usuarios;
	private Tree empresaEscojer;
	private List<SelectItem> lstEmpresas;
	private DatosCombo datosCombo = new DatosCombo();
	private Solicitudimpresion solicitudimpresion;
	private boolean swSolicitudimpresionInCurso;
	private boolean swSolicitudimpresionObsoleto;
	private boolean swEstaInFlow;
	private SolicitudImpPart solicitudImpParts;
	private List<SolicitudImpPart> solicitudImpPartsLst;
	private List<Usuario> usuariosImpresion;
	private boolean toSolicitudImpresion;
	private boolean toImprimirAdministrar;
	private boolean swSolicitudimpresion;
	// editar con richfaces
	private List<Usuario> allObjectItems = null;
	private Usuario objectItem = null;
	private int currentCarIndex;
	private int page = 1;
	private String cualquierComentario;
	private Usuario usuarioRemplazo;
	private boolean swExitoso;

	// para instanciar cliente usuario sin levantar variables extras en otras
	// cñlases
	public ClienteUsuario(String constructorVacio) {

	}

	public ClienteUsuario() {
		session = super.getSession();
		// para que refresque cache en datos combo usuario
		session.setAttribute("swCacheUsuario", false);

		if (session != null) {

			// para solicitud de impresion
			solicitudimpresion = session.getAttribute("solicitudimpresion") != null ? (Solicitudimpresion) session
					.getAttribute("solicitudimpresion") : null;
			if (solicitudimpresion != null
					&& solicitudimpresion.getDoc_detalle() != null) {
				swSolicitudimpresion = true;
				if (solicitudimpresion.getEstado() != null
						&& solicitudimpresion.getEstado().getCodigo() != null) {
					swSolicitudimpresionObsoleto = (solicitudimpresion
							.getEstado().getCodigo() - Utilidades.getObsoleto() == 0);

				}
			}
			// fin para solicitud de impresion

			try {
				// PARA DESPLAZAMIENTO DE LOS REGISTROS EN LA FORMA
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
				if (derechaBD - izquierdaBD == 0) {
					derechaBD += Utilidades.getMaxRegisterMostrar();
					session.setAttribute("derechaBD", derechaBD);
				}

				// verificar si quiere ver los historicos
				swHistorico = session.getAttribute("historico") != null;

				if (super.getUser_logueado() != null
						&& super.getUser_logueado().getLogin() != null) {
					swSuperUsuario = super.getUser_logueado().getLogin()
							.equalsIgnoreCase(Utilidades.getRoot());
				}
				treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
						.getAttribute("treeNodoActual") : null;

				// ESTE PERMISO EN CASO QUE EL USUARIO TENGA APROBADO LA
				// SOLICITUD DE IMPRESION
				// CONSEGUIMOS LA SEGURIDAD TREE
				if (treeNodoActual != null) {
					seguridadTree = super.getSeguridadTree(treeNodoActual);
					if (seguridadTree.isToSolicitudImpresion()) {
						toSolicitudImpresion = seguridadTree
								.isToSolicitudImpresion();
					}
				}
				// CONSEGUIMOS LA SEGURIDAD MENU
				// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
				Tree treeMenu = new Tree();
				treeMenu.setNodo(Utilidades.getNodoRaiz());
				seguridadMenu = super.getSeguridadTree(treeMenu);
				if (seguridadMenu.isToImprimirAdministrar()) {

					toImprimirAdministrar = seguridadMenu
							.isToImprimirAdministrar();
				}

				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				// empresa por defecto que debe ser seleccionada segun el
				// usuario
				if (user_logueado != null) {
					this.empresaEscojer = user_logueado.getEmpresa();
				}

				boolean crear = session.getAttribute("crear") != null;

				IniOperaciones();

				// OBTENEMOS LOS PRIMEROS USUARIOS EN LA LISTA
				getUsuarios();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_userDesconectado")));
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
		visibleItems = filtrarSelectItems(customizePageBean.getInvisibleItems());
		// invisibleItems=customizePageBean.getInvisibleItems();
		return invisibleItems;
	}

	public void setInvisibleItems(List invisibleItems) {
		customizePageBean.setInvisibleItems(invisibleItems);
		this.invisibleItems = invisibleItems;
	}

	public List getVisibleItems() {
		visibleItems = filtrarSelectItems(customizePageBean.getVisibleItems());
		// visibleItems=customizePageBean.getVisibleItems();
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
	/*
	 * public String delete2(){ String pagIr=""; Usuario obj
	 * =session.getAttribute
	 * ("usuarioEliminar")!=null?(Usuario)session.getAttribute
	 * ("usuarioEliminar"):null; if (obj!=null){ if
	 * (obj.equals(usuarioRemplazo)){
	 * FacesContext.getCurrentInstance().addMessage(null,new
	 * FacesMessage(messages.getString("escoja_otrousuario") )); return ""; }
	 * 
	 * try { obj=delegado.getUsuario(obj); } catch (EcologicaExcepciones ex) {
	 * ex.printStackTrace(); } //borramois la seguridad_user del usuario tree
	 * operaciones user fisicamente
	 * delegado.deleteSeguridadIndividual2(obj,usuarioRemplazo); //borramos
	 * Roles_Usuarios y Menus_Usuarios fiicamente delegado.destroy_Menu(obj);
	 * //en flujo se deja porque el fuie el autor //falta detalle, maestro
	 * //cambiamos el flow_participantes
	 * delegado.sustituirFlowParticipantesOldByOtro(obj, usuarioRemplazo);
	 * delegado.deleteTreenewDuenio(obj, usuarioRemplazo);
	 * //__________________________________________________________________
	 * //damos seguridad hasta abajo del nuevo nodo que se crean boolean
	 * swEliminar=false; //cargamos la operacion de la base de datos
	 * List<Operaciones>
	 * operacionViewInBD=delegado.findAll_operacionesArbol(usuarioRemplazo
	 * .getCargo()); for (Operaciones op:operacionViewInBD){ try {
	 * clienteSeguridad
	 * .heredarOperacionTreeUsuarioPermisoSoloParaAbajo(usuarioRemplazo
	 * .getCargo(), usuarioRemplazo,op ,swEliminar); } catch
	 * (EcologicaExcepciones ex) { ex.printStackTrace(); }
	 * 
	 * } try { obj.setStatus(false); delegado.edit(obj); } catch
	 * (EcologicaExcepciones ex) { ex.printStackTrace(); }
	 * //__________________________________________________________________
	 * session.setAttribute("pagIr",Utilidades.getListarUsuarios());
	 * pagIr=Utilidades.getFinexitoso(); //getUsuarios();
	 * FacesContext.getCurrentInstance().addMessage(null,new
	 * FacesMessage(messages.getString("operacion_exitosa") ));
	 * 
	 * } return pagIr; }
	 */
	public String historicoActivo() {
		String pagIr = "";

		Usuario usuarioHistoricoActivo = session.getAttribute("usuario") != null ? (Usuario) session
				.getAttribute("usuario") : null;
		if (usuarioHistoricoActivo != null) {
			session.setAttribute("usuarioHistoricoActivo",
					usuarioHistoricoActivo);
		}
		pagIr = Utilidades.getHistoricoUsuarioActivo();
		return pagIr;
	}

	public Usuario getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(Usuario objectItem) {
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

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public Usuario getUsuarioRemplazo() {
		return usuarioRemplazo;
	}

	public void setUsuarioRemplazo(Usuario usuarioRemplazo) {
		this.usuarioRemplazo = usuarioRemplazo;
	}

	public boolean isSwExitoso() {
		return swExitoso;
	}

	public void setSwExitoso(boolean swExitoso) {
		this.swExitoso = swExitoso;
	}

	public String delete() {
		String pagIr = "";
		session.removeAttribute("verHistoricoUsusario");
		usuarioEliminar = session.getAttribute("usuario") != null ? (Usuario) session
				.getAttribute("usuario") : null;
		if (usuarioEliminar != null) {
			session.setAttribute("usuarioEliminar", usuarioEliminar);
		}

		pagIr = Utilidades.getEliminarUsuario();
		return pagIr;
	}

	public String verHistoricotUsuario() {

		String pagIr = "";
		session.removeAttribute("usuarioEliminar");
		if (session.getAttribute("verHistoricoUsusario") != null) {
			pagIr = Utilidades.getEliminarUsuario();
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_userNoEncontrado")));
		}

		return pagIr;
	}

	public void detalleHistorico(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("historicoId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				Usuario verHistoricoUsusario = new Usuario();
				verHistoricoUsusario.setId(new Long(id));
				verHistoricoUsusario = delegado
						.find_Usuario(verHistoricoUsusario.getId());
				session.setAttribute("verHistoricoUsusario",
						verHistoricoUsusario);
			}
		}

	}

	public String listtUsuario() {
		if (session.getAttribute("error") == null) {
			session.removeAttribute("error");
			return "listar";
		} else {
			session.removeAttribute("error");
			return "";
		}

	}

	public void sacardeApplicationUsuario(ActionEvent event) {
		session.removeAttribute("error");
		if (user_logueado == null) {
			user_logueado = (Usuario) session.getAttribute("user_logueado");
		}
		if (user_logueado != null) {
			Usuario usuarioDesconectar = new Usuario();
			UIParameter component = (UIParameter) event.getComponent()
					.findComponent("desconectarId");
			if ((component != null)
					&& (component.getValue().toString() != null)) {
				long id = Long.parseLong(component.getValue().toString());
				// buscamos el role para editar
				if (id >= 0) {
					usuarioDesconectar.setId(new Long(id));
				}
				try {
					if (user_logueado.getId() - usuarioDesconectar.getId() != 0) {
						ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
						Map application = (Map) super
								.getApplicationMapValue(confmessages
										.getString("variable_global"));
						procesarLoginsConectados.deleteUsuarioById(
								usuarioDesconectar.getId(), application,
								confmessages.getString("usuariosApplicaction"));
					} else {
						FacesContext
								.getCurrentInstance()
								.addMessage(
										null,
										new FacesMessage(
												messages.getString("error_userNoSacarseDelSistema")));
						session.setAttribute("error", true);
					}
					// caso de cambiar contraseña
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_userNoEncontrado")));
			session.setAttribute("error", true);
		}
	}

	public String usuarioFlowStadisticasGraficar() {
		// return "usuarioFlowStadisticasGraficar";
		usuario = (Usuario) session.getAttribute("usuario");
		if (usuario != null) {
			getFacesContext().addMessage(
					null,
					new FacesMessage(messages.getString("graficomostrar") + " "
							+ usuario.getNombre() + " " + usuario.getApellido()
							+ "[" + usuario.getCargo().getNombre() + "]")); // TODO
			// Cambiar
			// mensage
			// por
			// bundle.
		}

		return "";
	}

	public void selectUsuarioStadisticaGraficar(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdgrf");
		HtmlOutputText j = getId_str();
		String id_str1 = j.getTitle();
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (usuario == null) {
				usuario = new Usuario();
			}
			// buscamos el role para editar
			if (id >= 0) {
				usuario.setId(new Long(id));
			}

			try {
				usuario = delegado.find_Usuario(new Long(id));
				session.setAttribute("usuario", usuario);
				// IniOperaciones();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public String viewHistorico() {
		return "listarFlowsHistoricoUser";
	}

	public void selectUsuarioFlow(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		HtmlOutputText j = getId_str();
		String id_str1 = j.getTitle();
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (usuario == null) {
				usuario = new Usuario();
			}
			// buscamos el role para editar
			if (id >= 0) {
				usuario.setId(new Long(id));
			}

			try {
				usuario = delegado.find_Usuario(new Long(id));
				// si hemos moviso el usuario, puede que no esten acrtualizados
				// sus datos de empresa,principal,area
				reparamosUsuarioEmprPrinAreaCargo(usuario);
				session.setAttribute("usuario", usuario);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public String editUsuario() {
		return "editUsuario";
	}

	public void selectUsuario(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		// HtmlOutputText j = getId_str();
		// String id_str1 = j.getTitle();
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (usuario == null) {
				usuario = new Usuario();
			}
			// buscamos el role para editar
			if (id >= 0) {
				usuario.setId(new Long(id));
			}

			try {
				usuario = delegado.find_Usuario(new Long(id));
				// si hemos moviso el usuario, puede que no esten acrtualizados
				// sus datos de empresa,principal,area
				reparamosUsuarioEmprPrinAreaCargo(usuario);
				// caso de cambiar contraseña
				passwordOculta = (String) usuario.getPassword();
				session.setAttribute("passwordOculta", passwordOculta);
				session.setAttribute("usuario", usuario);
				visibleItems = new ArrayList();
				invisibleItems = new ArrayList();
				clienteRole.findRoles_PorUsuario(visibleItems, invisibleItems,
						usuario);
				if ((visibleItems == null)) {
					visibleItems = new ArrayList();
				}
				if ((invisibleItems == null)) {
					invisibleItems = new ArrayList();
				}
				session.setAttribute("visibleItems", visibleItems);
				session.setAttribute("invisibleItems", invisibleItems);
				// IniOperaciones();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void reparamosUsuarioEmprPrinAreaCargo(Usuario usuario) {
		boolean swAlMoverCargoError = false;
		Tree cargo = usuario.getCargo();
		Tree area = usuario.getArea();
		Tree principal = usuario.getPrincipal();
		Tree empresa = usuario.getEmpresa();

		Tree areaReal = delegado.obtenerNodo(usuario.getCargo().getNodopadre());
		if (!areaReal.equals(area)) {
			swAlMoverCargoError = true;
			usuario.setArea(areaReal);
		}

		Tree principalReal = delegado.obtenerNodo(usuario.getArea()
				.getNodopadre());
		if (!principalReal.equals(principal)) {
			swAlMoverCargoError = true;
			usuario.setPrincipal(principalReal);
		}

		Tree empresaReal = delegado.obtenerNodo(usuario.getPrincipal()
				.getNodopadre());
		if (!empresaReal.equals(empresa)) {
			swAlMoverCargoError = true;
			usuario.setEmpresa(empresaReal);
		}

		if (swAlMoverCargoError) {
			try {
				delegado.edit(usuario);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}

	}

	public void IniOperaciones() {
		customizePageBean = new CustomizePageBean();
		invisibleItems = session.getAttribute("invisibleItems") != null ? (List) session
				.getAttribute("invisibleItems") : null;
		visibleItems = session.getAttribute("visibleItems") != null ? (List) session
				.getAttribute("visibleItems") : null;
		if (invisibleItems == null) {
			invisibleItems = new ArrayList();
		}
		if (visibleItems == null) {
			visibleItems = new ArrayList();
		}
		boolean crear = session.getAttribute("crear") != null;
		if (!crear) {
			usuario = session.getAttribute("usuario") != null ? (Usuario) session
					.getAttribute("usuario") : null;
		}

		// session.setAttribute("role",role);
		setInvisibleItems(invisibleItems);
		setVisibleItems(visibleItems);
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = (Usuario) super.getSessionScope().get("nuevo_usuario");
			Tree empresaEscojer = (Tree) session.getAttribute("empresaEscojer");
			usuario.setEmpresa(empresaEscojer);
			if (usuario == null) {
				getFacesContext().addMessage(null,
						new FacesMessage("No Hay Usuario en la session")); // TODO
				// Cambiar
				// mensage
				// por
				// bundle.
			}
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
		return delegado.findAll_Usuario(user_logueado);
	}

	public String cancelarHistoricoUsuarioActivo() {
		return Utilidades.getListarUsuarios();
	}

	public String cancelarListtUser() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));

		} catch (Exception e) {
			redirect("ClienteUsuario", "cancelarListtUser", e);
		}

		return "menu";
	}

	public String listarSolicitudImpresion() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		session.removeAttribute("solicitudimpresion");

		return "listarSolicitudImpresion";

	}

	public String cancelarListSolicitudImpresion() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		String pagIr = "";
		SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
		solicitudImpParts.setSolicitudimpresion(solicitudimpresion);
		if (solicitudimpresion != null) {
			delegado.solicitudImpPartsCancelar(solicitudImpParts);
			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		}

		return Utilidades.getListarDocumentos();
	}

	public String cancelarEditUser() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteUsuario", "cancelarEditUser", e);
		}

		return "listarUsuarios";
	}

	public String saveEditUser() throws NoSuchAlgorithmException {
		String pagIr = "";
		try {
			if (badDatosUsuario()) {
				return pagIr;
			}
			
			if (usuario.getLogin().length() > Utilidades.longCampoNormal) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_longNormalExede")
								+ " "
								+ Utilidades.longCampoNormal));
				 return pagIr;
			}
  
			try {
				if (usuario.getPassword().length() > 0) {

					if (usuario.getPassword().equals(
							(String) getPasswordConfirm())) {
						usuario.setPassword(EncryptorMD5.getMD5(usuario
								.getPassword()));
						 
					} else {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("usuario_passwordInval")));
						return pagIr;
					}
				} else {
					 
					// el getPasswordOculta viwene de usuarioView
					usuario.setPassword(getPasswordOculta());

				}

				if (usuario.getPassword() != null
						&& usuario.getPassword().length() <= 0
						|| super.isEmptyOrNull(usuario.getPassword())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_passwordInval")));
					return pagIr;

				}
				
				Usuario usu=delegado.findUsuarioLoginEditar( usuario.getLogin(), usuario);
				if (usu!=null){
					System.out.println("----------NADA NULO USUARIO....");
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("existe")
									+ " -> Login"
									));
					 return pagIr;
				}

				// ****************EDITAMOS
				// USUARIO****************************************************************

				usuario.setStatus(Utilidades.isActivo());
				usuario.setMail_principal(usuario.getMail_principal()
						.toString().toLowerCase());
				usuario.setLogin(usuario.getLogin().toString().toLowerCase());
				delegado.edit(usuario);
				// ********************************************************************************
				// eliminamos todos las operaciones del ro, anteriormente
				// seleccionados
				// delegado.destroy(usuario);
				delegado.destroy_Menu(usuario);
				// introducimos las nuevas operaciones al rol
				List<Role> listaRoles = session.getAttribute("visibleItems") != null ? (List<Role>) session
						.getAttribute("visibleItems") : null;
				Roles_Usuarios role_usuario;
				if (listaRoles == null) {
					listaRoles = new ArrayList();
				}
				for (Role role : listaRoles) {
					if (role != null && role instanceof Role) {
						role_usuario = new Roles_Usuarios();
						role_usuario.setRole(role);
						role_usuario.setUsuario(usuario);
						delegado.create(role_usuario);
						// aqui si controlamos si el role trae operacionbes para
						// menu
						// en crear usuario, no controlamos esto
						if (isForSeguridadRoleMenu(role)) {
							Menus_Usuarios menus_Usuarios = new Menus_Usuarios();
							menus_Usuarios.setUsuario(usuario);
							menus_Usuarios.setRole(role);
							delegado.create(menus_Usuarios);
						}

					}
				}
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
				// es el menu y tree ambas, no c puede sacar el tree de la cahe
				// y actualizarlo
				// hay que refrescarlo todo
				clienteSeguridad.ponerSeguridadInSession(user_logueado);
				// HiloClienteSeguridad hiloClienteSeguridad = new
				// HiloClienteSeguridad(user_logueado);
				// hiloClienteSeguridad.start();
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr", Utilidades.getListarUsuarios());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));

			} catch (EcologicaExcepciones e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("error")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar")));
		}
		return pagIr;
	}

	// ES VERDAERO Y GRABA EN USUARIO MENU

	public boolean isForSeguridadRoleMenu(Role role) {
		// las operaciones en roles
		// se encuentra vacia
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(role);
		// BUSCAMOS LOS PERMISOS PARA MENUS EN LINEAL
		List<Seguridad_Role_Lineal> listaSegRols = delegado
				.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);
		boolean swGrabar = false;
		if (listaSegRols != null && !listaSegRols.isEmpty()) {
			seguridad_Role_Lineal = listaSegRols.get(0);
			List<Operaciones> enBd = delegado.findAll_operaciones();
			for (Operaciones op : enBd) {
				if (delegado.existeOperacionSeguridadLineal(op,
						seguridad_Role_Lineal)) {
					// /PARA TODAS DEL MENU
					if (Utilidades.getPertence_Menu() - op.getPertenece_Arbol() == 0) {
						swGrabar = true;
						return swGrabar;

					}
					// PARA VIEW
					if (Utilidades.getPertence_Arbol_and_Menu()
							- op.getPertenece_Arbol() == 0) {
						swGrabar = true;
						return swGrabar;

					}
				}

			}
		}

		return swGrabar;
	}

	public String historico() {
		if (session.getAttribute("historico") == null) {
			session.setAttribute("historico", true);
			swHistorico = true;
		} else {
			session.removeAttribute("historico");
			swHistorico = false;
		}
		return "listar";
	}

	public String inic_crear() {
		// if ((visibleItems == null) || (invisibleItems == null)) {
		visibleItems = new ArrayList();
		invisibleItems = new ArrayList();
		// }
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (empresaEscojer == null) {
			if (user_logueado != null) {
				empresaEscojer = user_logueado.getEmpresa();
			}
		}

		Usuario usuEmpresa = new Usuario();
		usuEmpresa.setEmpresa(empresaEscojer);
		session.setAttribute("empresaEscojer", empresaEscojer);
		clienteRole.findRoles_PorUsuario(visibleItems, invisibleItems,
				usuEmpresa);
		session.setAttribute("visibleItems", visibleItems);
		session.setAttribute("invisibleItems", invisibleItems);
		session.setAttribute("crear", true);
		getSessionScope().put("nuevo_usuario", new Usuario());
		// session.setAttribute("nuevo_usuario",new Usuario());
		return "crear";
	}

	public boolean badDatosUsuario() {

	//	if (!isSwEditarUsuario()) {
			if (super.isEmptyOrNull(usuario.getLogin())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return true;
			}

			if (usuario.getLogin().length() > Utilidades.longCampoNormal) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_longNormalExede")
								+ " "
								+ Utilidades.longCampoNormal));
				return true;
			}

		//}

		if (super.isEmptyOrNull(usuario.getMail_principal())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return true;
		}

		String repetido = null;
		Usuario usuarioCheck = new Usuario();
		usuarioCheck.setMail_principal(usuario.getMail_principal());
		usuarioCheck.setLogin(usuario.getLogin());
		usuarioCheck.setEmpresa(usuario.getEmpresa());
		if (usuario.getId() != null) {
			usuarioCheck.setId(usuario.getId());
			// System.out.println("usuario.getId()="+usuario.getId());
		}

		repetido = delegado.findLoginOrMailExista(usuarioCheck);
		if (repetido != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("error_userYaExiste")
							+ " " + repetido.toString()));
			return true;
		}

		if (super.isEmptyOrNull(usuario.getNombre())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return true;
		}
		if (usuario.getNombre().length() > Utilidades.longCampoNormal) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_longNormalExede")
							+ " "
							+ Utilidades.longCampoNormal));
			return true;
		}

		if (super.isEmptyOrNull(usuario.getApellido())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return true;
		}

		if (usuario.getApellido().length() > Utilidades.longCampoNormal) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_longNormalExede")
							+ " "
							+ Utilidades.longCampoNormal));
			return true;
		}

		if (usuario.getDireccion().length() > Utilidades.longCampoGrande) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_longNormalExede")
							+ " "
							+ Utilidades.longCampoGrande));
			return true;
		}

		if (!isSwEditarUsuario()) {
			if (super.isEmptyOrNull(usuario.getPassword())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return true;
			}
			if (usuario.getPassword().length() > Utilidades.longCampoNormal) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_longNormalExede")
								+ " "
								+ Utilidades.longCampoNormal));
				return true;
			}

			if (usuario.getCargo() == null
					|| super.isEmptyOrNull(usuario.getCargo().getNombre())) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(messages
										.getString("error_user_cargo")));
				return true;
			}

			if (usuario.getEmpresa() == null
					|| super.isEmptyOrNull(usuario.getEmpresa().getNombre())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("falta_data")));
				return true;
			}

			if (usuario.getPrincipal() == null
					|| super.isEmptyOrNull(usuario.getPrincipal().getNombre())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("falta_data")));
				return true;
			}
			if (usuario.getArea() == null
					|| super.isEmptyOrNull(usuario.getArea().getNombre())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("falta_data")));
				return true;
			}

		}

		return false;
	}

	public String create() {
		String pagIr = "";
		try {
			Usuario usuario = this.getUsuario();

			if (badDatosUsuario()) {
				return pagIr;
			}

			Calendar date = Calendar.getInstance();
			usuario.setFecha_creado(date.getTime());
			String maq = "";
			try {
				maq = InetAddress.getLocalHost().getHostName() + ";"
						+ InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			maquina = maq;
			if (usuario.getPassword() != null
					&& usuario.getPassword().equalsIgnoreCase(
							getPasswordConfirm())) {
				try {
					usuario.setPassword(EncryptorMD5.getMD5(usuario
							.getPassword()));
					setPasswordConfirm(EncryptorMD5.getMD5(usuario
							.getPassword()));
				} catch (NoSuchAlgorithmException ex) {
					ex.printStackTrace();
				}

				// ***********************CREAMOS EL
				// USUARIO**************************************

				usuario.setMail_principal(usuario.getMail_principal()
						.toString().toLowerCase());
				usuario.setLogin(usuario.getLogin().toString().toLowerCase());

				usuario.setStatus(Utilidades.isActivo());
				// __________________________________________

				;
				if (usuario.getId() != null && usuario.getId() > 0) {
					// el usuario existe
				} else {
					// creamos el nuevo usuario sin cargo
					Tree cargoOld = usuario.getCargo();
					usuario.setCargo(null);
					usuario.setStatus(true);
					try {
						delegado.create(usuario, date.getTime(), maquina, null,
								null);
						// ahora buscamos el cargo para agregar
						usuario.setCargo(cargoOld);
						cargosInUsuarioCrear(usuario);
						// siu da diferente es ue se creo un nuevoi cargo,
						// agarrando como base el viejo
						// if (
						// usuario.getCargo().getNodo()-cargoOld.getNodo()!=0 ){
						// solo modificamos el nueo usuario
						delegado.edit(usuario);
						// }
						// *************************************************************

						// eliminamos todos las operaciones del ro,
						// anteriormente seleccionados
						delegado.destroy(usuario);
					} catch (Exception e) {
					}

					// introducimos las nuevas operaciones al rol
					visibleItems = session.getAttribute("visibleItems") != null ? (List) session
							.getAttribute("visibleItems") : null;
					Roles_Usuarios role_usuario;
					int length = visibleItems.size();
					for (int i = 0; i < length; i++) {
						SelectItem value = (SelectItem) visibleItems.get(i);
						Role role = (Role) value.getValue();
						if (role != null && role instanceof Role) {
							role_usuario = new Roles_Usuarios();
							role_usuario.setRole(role);
							role_usuario.setUsuario(usuario);
							delegado.create(role_usuario);

							// seguridad para los menus en el role
							// me creara el role para menu, asi no tenga ninguna
							// operacion para administrar el menu
							// if (isForSeguridadRoleMenu(role)){
							Menus_Usuarios menus_Usuarios = new Menus_Usuarios();
							menus_Usuarios.setUsuario(usuario);
							menus_Usuarios.setRole(role);
							delegado.create(menus_Usuarios);
							// }
						}
					}
					// damos la nueva seguridad a dichoo nodo del nuevo usuario
					// truco de convertirlo
					Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(usuario);
					seguridad_User_Lineal.setTree(usuario.getCargo());
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);
					// creamos una carpeta
					Tree newCarpeta = new Tree();
					newCarpeta.setDeBaseToUsuario(false);
					newCarpeta.setDescripcion(messages
							.getString("misdocumentos"));
					newCarpeta.setFecha_creado(new Date());
					newCarpeta.setMaquina(super.getMaquinaConectada());
					newCarpeta.setNodopadre(usuario.getCargo().getNodo());
					newCarpeta.setNombre(messages.getString("misdocumentos"));
					newCarpeta.setPrefix(messages.getString("misdocumentos"));
					newCarpeta.setTiponodo(Utilidades.getNodoCarpeta());
					delegado.crearNuevoTree(newCarpeta,usuario);
					seguridad_User_Lineal = new Seguridad_User_Lineal();
					seguridad_User_Lineal.setUsuario(usuario);
					seguridad_User_Lineal.setTree(newCarpeta);
					clienteSeguridad.darSeguridadNodo(seguridad_User_Lineal);

					// damos la nueva seguridad al usuario que creo dicho nodo
					user_logueado = (Usuario) session
							.getAttribute("user_logueado");
					if (user_logueado != null) {
						// asignamos permisos al cargo
						seguridad_User_Lineal = new Seguridad_User_Lineal();
						seguridad_User_Lineal.setUsuario(user_logueado);
						seguridad_User_Lineal.setTree(usuario.getCargo());
						clienteSeguridad
								.darSeguridadNodo(seguridad_User_Lineal);

						// pondemo seguridad en session del usuario,
						// actualizamos sus datos en memoria
						clienteSeguridad.ponerSeguridadInSession(user_logueado);
						// session = super.getSession();
						// HiloClienteSeguridad hiloClienteSeguridad = new
						// HiloClienteSeguridad(user_logueado,session);
						// hiloClienteSeguridad.start();
					}

				}

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));
				usuario = new Usuario();

				session.setAttribute("pagIr", Utilidades.getListarUsuarios());
				pagIr = Utilidades.getFinexitoso();
				return pagIr;
			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("usuario_passwordInval")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar")));
		}
		return pagIr;
	}

	public Usuario cargosInUsuarioCrear(Usuario usuario) {
		if (usuario != null) {
			// buscamos y si me trae un cargo, creamos unno nuevo
			List<Usuario> staCargoUsado = delegado
					.findExisteCargoInUsuario(usuario);
			if (staCargoUsado != null && !staCargoUsado.isEmpty()) {
				int size = staCargoUsado.size();
				// for(int h=0;h<size;h++){
				Usuario user = (Usuario) staCargoUsado.get(0);

				Tree newCargo = new Tree();
				Calendar time = Calendar.getInstance();
				newCargo.setDeBaseToUsuario(false);
				newCargo.setDescripcion(usuario.getCargo().getDescripcion());
				newCargo.setFecha_creado(time.getTime());
				newCargo.setMaquina(super.getMaquinaConectada());
				newCargo.setNodopadre(usuario.getCargo().getNodopadre());
				newCargo.setNombre(usuario.getCargo().getNombre());
				newCargo.setPrefix(usuario.getCargo().getPrefix());
				newCargo.setTiponodo(usuario.getCargo().getTiponodo());

				try {
					delegado.crearNuevoTree(newCargo,usuario);
					usuario.setCargo(newCargo);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return usuario;
	}

	public List<Usuario> getUsuariosImpresion() {

		// SE INICIALIZA EN EL CONTRUCTOIR DE LA CLASE
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		usuariosImpresion = new ArrayList<Usuario>();
		if (solicitudimpresion != null
				&& solicitudimpresion.getDoc_detalle() != null) {
			for (SolicitudImpPart sip : solicitudimpresion
					.getsolicitudImpParts()) {

				if (sip.getEstado().getCodigo() == Utilidades.getImprimirsin()) {
					sip.getUsuario().setCancelar(true);
				}

				if ((sip.getUsuario().getId() - user_logueado.getId()) == 0) {
					sip.getUsuario().setSwMostrar(true);
				}

				sip.getUsuario().setsolicitudImpParts(sip);
				try {
					sip.getUsuario()
							.getsolicitudImpParts()
							.getSolicitudimpresion()
							.setFechadesdeimprimirtxt(
									Utilidades.sdfShow.format(sip.getUsuario()
											.getsolicitudImpParts()
											.getSolicitudimpresion()
											.getFechadesdeimprimir()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					sip.getUsuario()
							.getsolicitudImpParts()
							.getSolicitudimpresion()
							.setFechahastaimprimirtxt(
									Utilidades.sdfShow.format(sip.getUsuario()
											.getsolicitudImpParts()
											.getSolicitudimpresion()
											.getFechahastaimprimir()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					sip.getUsuario()
							.getsolicitudImpParts()
							.getSolicitudimpresion()
							.setFechaSolicitudtxt(
									Utilidades.sdfShow.format(sip.getUsuario()
											.getsolicitudImpParts()
											.getSolicitudimpresion()
											.getFechaSolicitud()));

				} catch (Exception e) {
					// TODO: handle exception
				}

				usuariosImpresion.add(sip.getUsuario());

			}
			return usuariosImpresion;
		} else {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			List<Usuario> usuarios = delegado.findAll_Usuario(user_logueado);
			for (Usuario usu : usuarios) {
				seguridadTree = super.seguridadTreeUsuarioNoLogueado(usu,
						treeNodoActual);
				if (seguridadTree.isToSolicitudImpresion()) {
					usuariosImpresion.add(usu);
				}
			}

			return usuariosImpresion;
		}

	}

	public List<Usuario> getUsuarios() {
		List<Usuario> usuariosVer = new ArrayList();

		;
		// historico
		boolean historico = session.getAttribute("historico") != null;
		boolean activo = false;
		if (!historico) {
			activo = Utilidades.isActivo();
		} else {
			activo = Utilidades.isInactivo();
		}
		derechaBD = session.getAttribute("derechaBD") != null ? (Integer) session
				.getAttribute("derechaBD") : 0;
		izquierdaBD = session.getAttribute("izquierdaBD") != null ? (Integer) session
				.getAttribute("izquierdaBD") : 0;

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		// encontrar por empresa seleccionada
		Tree empresa = (Tree) session.getAttribute("empresaEscojer");
		// esta asignacion de empresa es temporal para hacer el filtrado...
		Usuario usuTmp = new Usuario();
		if (empresa != null) {
			usuTmp.setLogin("temporal");
			usuTmp.setEmpresa(empresa);
		} else {
			usuTmp.setLogin("temporal");
			if (user_logueado != null) {
				usuTmp.setEmpresa(user_logueado.getEmpresa());
			}
		}

		if (Utilidades.getLogin() == getBuscarPor()
				|| Utilidades.getCargo() == getBuscarPor()
				|| Utilidades.getNombre() == getBuscarPor()
				|| Utilidades.getApellido() == getBuscarPor()) {
			// busca el texto y que tipo, si es login, apellido, nombre
			usuarios = delegado.findAllHistoricoUsuarios(usuTmp,
					getStrBuscar(), getBuscarPor(), activo, derechaBD,
					izquierdaBD);

		} else {
			usuarios = delegado.findAllHistoricoUsuarios(usuTmp,
					getStrBuscar(), activo, derechaBD, izquierdaBD);
		}
		// RECUPERAMOS DE NUEVO EL USUARIO ORIGINAL...
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			setSwSuperUsuario(super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot()));
		}
		// revisamos quienes estan conectados y quienes no..
		ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
		Map application = (Map) super.getApplicationMapValue(confmessages
				.getString("variable_global"));
		int size = usuarios.size();
		for (int i = 0; i < size; i++) {

			Usuario usuario = usuarios.get(i);
			// VERIFICAMOS LA SEGURIDAD SI SE PUEDE VER
			Tree cargoUsuario = new Tree();
			if (usuario.getCargo() != null) {
				cargoUsuario = usuario.getCargo();

				seguridadTree = super.getSeguridadTree(user_logueado,
						cargoUsuario);
				// if (seguridadTree.isToView() || swSuperUsuario){
				if (usuario.getFecha_creado() != null) {
					usuario.setFecha_creadotxt(Utilidades.sdfShow
							.format(usuario.getFecha_creado()));

				}
				Usuario user = procesarLoginsConectados.loadUsuarioById(
						usuario.getId(), application,
						confmessages.getString("usuariosApplicaction"));
				if (user != null) {
					usuario.setStatus(true);
					usuario.setUltimaConexion(user.getUltimaConexion());
				} else {
					// nio esta conectado yt lo podemos eliminar
					usuario.setStatus(false);
					// si tienes permisos para borrar
					if (isSwDelUsuario() && !historico) {
						usuario.setDelete(true);
					} else if (historico) {
						usuario.setHistorico(true);
					}

				}
				if (user_logueado.getEmpresa()!=null && usuariosVer!=null){
					if (user_logueado.getEmpresa().getUserByEmpresa()!=null && usuariosVer.size()<user_logueado.getEmpresa().getUserByEmpresa()){
						usuariosVer.add(usuario);
					}else if (user_logueado.getEmpresa().getUserByEmpresa()==null && usuariosVer!=null){
						
						usuariosVer.add(usuario);
					}
						
				}else if (usuariosVer!=null){
					usuariosVer.add(usuario);
				}
				
				// }
			}
		}
		usuarios = usuariosVer;

		return usuarios;
	}

	public void usuarioConectado() {
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public void setAllCargos(List allCargos) {
		this.allCargos = allCargos;
	}

	public List<SelectItem> getAllProfesion() {
		try {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			List<SelectItem> lstprof = new ArrayList<SelectItem>();
			List<Profesion> lstbdProf = delegado
					.findAll_Profesion(user_logueado);
			SelectItem item = null;
			for (Profesion profesion : lstbdProf) {
				if (profesion != null) {
					item = new SelectItem(profesion, profesion.getNombre());
					lstprof.add(item);
				}
			}
			AllProfesion = lstprof;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllProfesion;
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

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		// con esto se resolvio poder hacer filtros e ir al usuario sinq eu
		// diera error
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public HtmlOutputText getId_str() {
		return id_str;
	}

	public void setId_str(HtmlOutputText id_str) {
		this.id_str = id_str;
	}

	public Tree getArea() {

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

	public boolean isSwEditarUsuario() {
		swEditarUsuario = session.getAttribute("usuario") != null;
		return swEditarUsuario;
	}

	public void setSwEditarUsuario(boolean swEditarUsuario) {
		this.swEditarUsuario = swEditarUsuario;
	}

	public char getSearchCargo() {
		searchCargo = Utilidades.getCargo();
		return searchCargo;
	}

	public void setSearchCargo(char searchCargo) {
		this.searchCargo = searchCargo;
	}

	public char getSearchNombre() {
		searchNombre = Utilidades.getNombre();
		return searchNombre;
	}

	public void setSearchNombre(char searchNombre) {
		this.searchNombre = searchNombre;
	}

	public char getSearchApellido() {
		searchApellido = Utilidades.getApellido();
		return searchApellido;
	}

	public void setSearchApellido(char searchApellido) {
		this.searchApellido = searchApellido;
	}

	public char getBuscarPor() {
		buscarPor = Utilidades.getLogin();
		if (session.getAttribute("buscarPor") != null
				&& session.getAttribute("buscarPor") instanceof String) {
			String buscarPorStr = (String) session.getAttribute("buscarPor");
			buscarPor = buscarPorStr.charAt(0);
		}
		return buscarPor;
	}

	public void setBuscarPor(char buscarPor) {
		if (Utilidades.getLogin() == buscarPor
				|| Utilidades.getCargo() == buscarPor
				|| Utilidades.getNombre() == buscarPor
				|| Utilidades.getApellido() == buscarPor) {
			session.setAttribute("buscarPor", String.valueOf(buscarPor));
		}

		this.buscarPor = buscarPor;
	}

	public char getSearchLogin() {
		searchLogin = Utilidades.getLogin();
		return searchLogin;
	}

	public void setSearchLogin(char searchLogin) {

		this.searchLogin = searchLogin;
	}

	public Tree getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Tree empresa) {
		this.empresa = empresa;
	}

	public void changeAreaP() {
		getAllAreas();
	}

	public List<SelectItem> getAllAreas() {
		List<SelectItem> result = new ArrayList();
		result.clear();

		// CUANDO SE CREA UN USUARIO
		if (getSelectPrincipal() != null
				&& getSelectPrincipal().getValue() != null) {
			Tree econtrarTodosLosHijos = (Tree) getSelectPrincipal().getValue();
			List<Tree> areas = new ArrayList();
			areas.clear();
			areas = encontarTodosLosTreeHijos(areas, econtrarTodosLosHijos,
					Utilidades.getNodoArea());
			SelectItem item = null;
			Tree obj = new Tree();
			obj.setNodo((long) -1);
			obj.setNombre("");
			result.add(new SelectItem(obj, obj.getNombre()));
			for (Tree area : areas) {
				if (area != null) {
					if ((area.getTiponodo() - Utilidades.getNodoArea()) == 0) {
						seguridadTree = super.getSeguridadTree(area);
						if (isSwSuperUsuario() || seguridadTree.isToView()) {
							item = new SelectItem(area, area.getNombre());
							result.add(item);
						}

					}
				}
			}
		}
		return result;
	}

	public Doc_detalle devolverDoc_detalleTreeNodoActual() {
		session = super.getSession();
		Doc_detalle doc_detalle = null;
		treeNodoActual = (Tree) session.getAttribute("treeNodoActual");

		List<Doc_maestro> doc_maestros = delegado
				.findAllDoc_maestros(treeNodoActual);
		for (Doc_maestro d : doc_maestros) {
			List<Doc_detalle> Doc_Detallelst = delegado.findAllDoc_Detalles(d);
			for (Doc_detalle dtalle : Doc_Detallelst) {
				doc_detalle = dtalle;
				break;
			}
			break;
		}
		session.setAttribute("objeto", doc_detalle);
		return doc_detalle;
	}

	public String aceptar() {
		String pagIr = "";
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", Utilidades.getListarDocumentos());
		return pagIr;
	}

	public void cancelarsolicitudImpParts(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("usuarioImpresionId");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			// buscamos el role para editar
			if (id >= 0) {
				Usuario usuario = new Usuario();
				usuario.setId(new Long(id));
				usuario = delegado.find_Usuario(usuario.getId());

				String pagIr = "";
				SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
				solicitudImpParts.setSolicitudimpresion(solicitudimpresion);
				solicitudImpParts.setUsuario(usuario);
				if (solicitudimpresion != null) {
					delegado.solicitudImpPartsCancelar(solicitudImpParts);

					// obtenbemos los usuarios para que refresqiue la pag
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
				}

			}
		}

	}

	public String solicitudImpresion() {

		Doc_detalle doc_detalle = devolverDoc_detalleTreeNodoActual();
		/*
		 * treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
		 * 
		 * List<Doc_maestro> doc_maestros =
		 * delegado.findAllDoc_maestros(treeNodoActual); for (Doc_maestro
		 * d:doc_maestros ){ List<Doc_detalle> Doc_Detallelst=
		 * delegado.findAllDoc_Detalles(d); for (Doc_detalle
		 * dtalle:Doc_Detallelst){ doc_detalle=dtalle; break; } break; }
		 */

		solicitudImpPartsLst = new ArrayList();

		Doc_estado doc_estado = null;
		for (Usuario usu : usuariosImpresion) {
			if ((selectedIds != null) && (selectedIds.get(usu.getId()) != null)) {
				if (selectedIds.get(usu.getId()).booleanValue()) {
					SolicitudImpPart solicitudImpParts = new SolicitudImpPart();
					solicitudImpParts.setUsuario(usu);
					doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getImprimirsin());
					doc_estado = delegado.findDocEstado(doc_estado);
					solicitudImpParts.setEstado(doc_estado);
					solicitudImpParts.setStatus(true);
					solicitudImpPartsLst.add(solicitudImpParts);
					// files.add(ar.getArchivo().trim());

				}
			}
		}

		if (!isNumeric(solicitudimpresion.getNumcopias() + "")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("numcopias") + " "
							+ messages.getString("errorNumero")));
			return "";
		}

		if (solicitudimpresion.getNumcopias() <= 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("numcopias") + " "
							+ messages.getString("errorNumero")));
			return "";
		}

		// if(date1.before(date2) || date1.equals(date2)){ // lo anterior se
		// resume: date1 <= date2

		// COMENTARIO
		if (isEmptyOrNull(solicitudimpresion.getComentarios())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("falta_data")));
			// PARTICIPANYTES
		} else if (solicitudImpPartsLst.size() == 0
				|| solicitudImpPartsLst.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("falta_data")));
			return "";
			// FECHA HASTA NULA
		} else if (solicitudimpresion.getFechahastaimprimir() == null) {
			// #{txt.fecha} #{txt.hasta}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("fecha") + " "
							+ messages.getString("hasta")));
			return "";
			// FECHA DESDE < FECHA HASTA
		} else if (solicitudimpresion.getFechadesdeimprimir() != null
				&& solicitudimpresion.getFechahastaimprimir() != null
				&& solicitudimpresion.getFechadesdeimprimir().after(
						solicitudimpresion.getFechahastaimprimir())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("error") + "->"
							+ messages.getString("fecha")));
			return "";
			// FECHA HASTA MENOR QUE DATE
		} else if (solicitudimpresion.getFechadesdeimprimir() == null
				&& solicitudimpresion.getFechahastaimprimir()
						.before(new Date())) {

			// #{txt.fecha} #{txt.hasta}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages.getString("fecha") + " "
							+ messages.getString("hasta")));
			return "";
		} else {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			// perfecto
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getEnAprobacion());
			doc_estado = delegado.findDocEstado(doc_estado);
			solicitudimpresion.setEstado(doc_estado);
			solicitudimpresion.setSolicitante(user_logueado);
			solicitudimpresion.setFechaSolicitud(new Date());
			solicitudimpresion.setDoc_detalle(doc_detalle);
			solicitudimpresion.setsolicitudImpParts(solicitudImpPartsLst);
			session.setAttribute("solicitudimpresion", solicitudimpresion);
			session.setAttribute("doc_detalle", doc_detalle);
			session.setAttribute("doc_maestro", doc_detalle.getDoc_maestro());
			return Utilidades.getFlows();

		}
		return "";

	}

	public void changeCargos() {
		getAllCargos();
	}

	public List<SelectItem> getAllCargos() {
		List<SelectItem> result = new ArrayList<SelectItem>();

		try {
			Tree carg = new Tree();
			carg.setNodo((long) -1);
			carg.setNombre("");
			result.add(new SelectItem(carg, carg.getNombre()));

			if (getSelectArea() != null && getSelectArea().getValue() != null) {
				allCargos = new ArrayList();
				allCargos.clear();
				List<Tree> cargos = delegado
						.findAllCargos((Tree) getSelectArea().getValue());

				SelectItem item = new SelectItem();
				for (Tree cargo : cargos) {
					if (cargo != null) {
						item = new SelectItem(cargo, cargo.getNombre());
						result.add(item);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuario != null && usuario.getCargo() != null) {
			setCargo(usuario.getCargo());
		}

		allCargos = result;
		return allCargos;
	}

	public List<SelectItem> getAllEmpresas() {

		getEmpresas();
		return allEmpresas;
	}

	public List<Tree> getEmpresas() {
		allEmpresas = new ArrayList();
		allEmpresas.clear();
		empresas = delegado.findAllEmpresas();
		SelectItem item = new SelectItem("");
		Tree em = new Tree();
		em.setNodo((long) -1);
		em.setNombre("");
		item = new SelectItem(em, "");
		allEmpresas.add(item);
		int j = 0;
		for (Tree emp : empresas) {
			seguridadTree = super.getSeguridadTree(emp);
			if (isSwSuperUsuario() || seguridadTree.isToView()) {
				item = new SelectItem(emp, emp.getNombre());
				allEmpresas.add(item);
			}
		}

		return empresas;
	}

	public void setEmpresas(List empresas) {
		this.empresas = empresas;
	}

	public void setAllEmpresas(List<SelectItem> allEmpresas) {
		this.allEmpresas = allEmpresas;
	}

	public HtmlSelectOneMenu getSelectEmpresa() {
		return selectEmpresa;
	}

	public void setSelectEmpresa(HtmlSelectOneMenu selectEmpresa) {
		this.selectEmpresa = selectEmpresa;
	}

	public Tree getCargo() {
		return cargo;
	}

	public void setCargo(Tree cargo) {
		this.cargo = cargo;
	}

	public Map getNodosEmpresa() {
		return nodosEmpresa;
	}

	public void setNodosEmpresa(Map nodosEmpresa) {
		this.nodosEmpresa = nodosEmpresa;
	}

	public String getArchNoCargados() {
		return archNoCargados;
	}

	public void setArchNoCargados(String archNoCargados) {
		this.archNoCargados = archNoCargados;
	}

	public boolean isSwArchNoCargados() {
		// _____________________________________________________________________________
		// vengo de cargar archivos y solo doy informacion en exitoso y me salgo
		// de aqui
		swArchNoCargados = session.getAttribute("archNoCargados") != null;
		List lista = (List) session.getAttribute("archNoCargados");
		StringBuffer acumSt = new StringBuffer("");
		if (swArchNoCargados) {
			int k = lista.size();
			for (int i = 0; i < k; i++) {
				String arch = (String) lista.get(i);
				acumSt.append(arch);
			}
		}
		archNoCargados = acumSt.toString();
		// _____________________________________________________________________________
		return swArchNoCargados;
	}

	public void setSwArchNoCargados(boolean swArchNoCargados) {
		this.swArchNoCargados = swArchNoCargados;
	}

	public boolean isSwDelUsuario() {
		swDelUsuario = seguridadMenu.isToDelUsuario();
		if (isSwSuperUsuario()) {
			swDelUsuario = true;
		}

		return swDelUsuario;
	}

	public void setSwDelUsuario(boolean swDelUsuario) {
		this.swDelUsuario = swDelUsuario;
	}

	public boolean isSwAddUsuario() {
		swAddUsuario = seguridadMenu.isToAddUsuario();
		if (isSwSuperUsuario()) {
			swAddUsuario = true;
		}
		if (swHistorico) {
			swAddUsuario = false;
		}
		return swAddUsuario;
	}

	public void setSwAddUsuario(boolean swAddUsuario) {
		this.swAddUsuario = swAddUsuario;
	}

	public boolean isSwModUsuario() {
		swModUsuario = seguridadMenu.isToModUsuario();
		if (isSwSuperUsuario()) {
			swModUsuario = true;
		}
		return swModUsuario;
	}

	public void setSwModUsuario(boolean swModUsuario) {
		this.swModUsuario = swModUsuario;
	}

	public HtmlSelectOneMenu getSelectPrincipal() {
		return selectPrincipal;
	}

	public void setSelectPrincipal(HtmlSelectOneMenu selectPrincipal) {
		this.selectPrincipal = selectPrincipal;
	}

	public Tree getPrincipal() {
		return principal;
	}

	public void setPrincipal(Tree principal) {
		this.principal = principal;
	}

	public void changePrincipalP() {
		getAllPrincipal();
	}

	public List<SelectItem> getAllPrincipal() {

		List<SelectItem> result = new ArrayList();
		result.clear();
		SelectItem item = null;
		Tree obj = new Tree();
		obj.setNodo((long) -1);
		obj.setNombre("");
		result.add(new SelectItem(obj, obj.getNombre()));

		// CUANDO SE CREA UN USUARIO
		if (getSelectEmpresa() != null && getSelectEmpresa().getValue() != null) {
			Tree econtrarTodosLosHijos = (Tree) getSelectEmpresa().getValue();
			List<Tree> principales = new ArrayList();
			principales.clear();
			principales = encontarTodosLosTreeHijos(principales,
					econtrarTodosLosHijos, Utilidades.getNodoPrincipal());

			for (Tree principal : principales) {
				if (principal != null) {
					if ((principal.getTiponodo() - Utilidades
							.getNodoPrincipal()) == 0) {
						seguridadTree = super.getSeguridadTree(principal);
						if (isSwSuperUsuario() || seguridadTree.isToView()) {
							item = new SelectItem(principal,
									principal.getNombre());
							result.add(item);
						}

					}
				}
			}
		}

		allPrincipal = result;
		return allPrincipal;
	}

	public void setAllPrincipal(List<SelectItem> allPrincipal) {
		this.allPrincipal = allPrincipal;
	}

	public Usuario getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(Usuario usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}

	public boolean isSwHistorico() {
		return swHistorico;
	}

	public void setSwHistorico(boolean swHistorico) {
		this.swHistorico = swHistorico;
	}

	public int getIzquierdaBD() {
		izquierdaBD = session.getAttribute("izquierdaBD") != null ? (Integer) session
				.getAttribute("izquierdaBD") : 0;
		return izquierdaBD;
	}

	public void setIzquierdaBD(int izquierdaBD) {
		this.izquierdaBD = izquierdaBD;
	}

	public int getDerechaBD() {
		derechaBD = session.getAttribute("derechaBD") != null ? (Integer) session
				.getAttribute("derechaBD") : 0;
		return derechaBD;
	}

	public void setDerechaBD(int derechaBD) {
		this.derechaBD = derechaBD;
	}

	public List<Hist_usuarios> getHist_usuarios() {
		hist_usuarios = new ArrayList<Hist_usuarios>();
		List<Hist_usuarios> hist = new ArrayList<Hist_usuarios>();

		Usuario usuarioHistoricoActivo = (Usuario) session
				.getAttribute("usuarioHistoricoActivo");
		if (usuarioHistoricoActivo != null) {
			hist_usuarios = delegado
					.findAll_HistoricoByUsuario(usuarioHistoricoActivo);
			for (Hist_usuarios hist_usuario : hist_usuarios) {
				if (hist_usuario.getFecha_conecto_inicio() != null) {
					hist_usuario.setFecha_mostrar(Utilidades.sdfShow
							.format(hist_usuario.getFecha_conecto_inicio()));
					hist.add(hist_usuario);
				}
			}
		}
		hist_usuarios = hist;
		return hist_usuarios;
	}

	public void setHist_usuarios(List<Hist_usuarios> hist_usuarios) {
		this.hist_usuarios = hist_usuarios;
	}

	/**
	 * @return the empresaEscojer
	 */
	public Tree getEmpresaEscojer() {

		return empresaEscojer;
	}

	/**
	 * @param empresaEscojer
	 *            the empresaEscojer to set
	 */
	public void setEmpresaEscojer(Tree empresaEscojer) {
		session.setAttribute("empresaEscojer", empresaEscojer);
		this.empresaEscojer = empresaEscojer;
	}

	/**
	 * @return the swSuperUsuario
	 */
	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	/**
	 * @param swSuperUsuario
	 *            the swSuperUsuario to set
	 */
	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	/**
	 * @return the lstEmpresas
	 */
	public List<SelectItem> getLstEmpresas() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		lstEmpresas = datosCombo.getAllEmpresas(user_logueado);
		return lstEmpresas;
	}

	/**
	 * @param lstEmpresas
	 *            the lstEmpresas to set
	 */
	public void setLstEmpresas(List<SelectItem> lstEmpresas) {
		this.lstEmpresas = lstEmpresas;
	}

	public Map<Long, Boolean> getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(Map<Long, Boolean> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public Solicitudimpresion getSolicitudimpresion() {
		return solicitudimpresion;
	}

	public void setSolicitudimpresion(Solicitudimpresion solicitudimpresion) {
		this.solicitudimpresion = solicitudimpresion;
	}

	public SolicitudImpPart getsolicitudImpParts() {
		return solicitudImpParts;
	}

	public void setsolicitudImpParts(SolicitudImpPart solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}

	public List<SolicitudImpPart> getsolicitudImpPartsLst() {
		return solicitudImpPartsLst;
	}

	public void setsolicitudImpPartsLst(
			List<SolicitudImpPart> solicitudImpPartsLst) {
		this.solicitudImpPartsLst = solicitudImpPartsLst;
	}

	public boolean isSwSolicitudimpresionInCurso() {
		return swSolicitudimpresionInCurso;
	}

	public void setSwSolicitudimpresionInCurso(
			boolean swSolicitudimpresionInCurso) {
		this.swSolicitudimpresionInCurso = swSolicitudimpresionInCurso;
	}

	public boolean isSwEstaInFlow() {
		return swEstaInFlow;
	}

	public void setSwEstaInFlow(boolean swEstaInFlow) {
		this.swEstaInFlow = swEstaInFlow;
	}

	public void setUsuariosImpresion(List<Usuario> usuariosImpresion) {
		this.usuariosImpresion = usuariosImpresion;
	}

	public boolean isToSolicitudImpresion() {
		return toSolicitudImpresion;
	}

	public void setToSolicitudImpresion(boolean toSolicitudImpresion) {
		this.toSolicitudImpresion = toSolicitudImpresion;
	}

	public boolean isToImprimirAdministrar() {
		return toImprimirAdministrar;
	}

	public void setToImprimirAutorizacion(boolean toImprimirAdministrar) {
		this.toImprimirAdministrar = toImprimirAdministrar;
	}

	public boolean isSwSolicitudimpresion() {
		return swSolicitudimpresion;
	}

	public void setSwSolicitudimpresion(boolean swSolicitudimpresion) {
		this.swSolicitudimpresion = swSolicitudimpresion;
	}

	public boolean isSwSolicitudimpresionObsoleto() {
		return swSolicitudimpresionObsoleto;
	}

	public void setSwSolicitudimpresionObsoleto(
			boolean swSolicitudimpresionObsoleto) {
		this.swSolicitudimpresionObsoleto = swSolicitudimpresionObsoleto;
	}

	public List<Usuario> getAllObjectItems() {
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		synchronized (this) {
			// if (allObjectItems == null) {
			allObjectItems = new ArrayList<Usuario>();

			List<Usuario> usuarios = delegado.findAll_Usuario(user_logueado);
			List<Usuario> usu = new ArrayList();
			for (Usuario u : usuarios) {

				// if (seguridadTree.isToView() || swSuperUsuario){
				if (u.getFecha_creado() != null) {
					u.setFecha_creadotxt(Utilidades.sdfShow.format(u
							.getFecha_creado()));

				}
				usu.add(u);
				System.out.println(u.toString());
			}
			allObjectItems.addAll(usu);
		}
		// }
		System.out.println("obteniendo datos");
		// System.out.println("user_logueado="+user_logueado.toString());
		// System.out.println("allObjectItems.size()="+allObjectItems.size());
		return allObjectItems;
	}

	public String delete2() {
		String pagIr = "";
		// usuarioEliminar = session.getAttribute("usuarioEliminar") != null ?
		// (Usuario) session
		// .getAttribute("usuarioEliminar") : null;
		if (usuarioEliminar != null) {
			if (usuarioEliminar.equals(usuarioRemplazo)) {
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
				usuarioEliminar = delegado.getUsuario(usuarioEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

			// borramois la seguridad_user del usuario tree operaciones user
			// fisicamente
			delegado.deleteSeguridadIndividual2(usuarioEliminar,
					usuarioRemplazo);
			// borramos Roles_Usuarios y Menus_Usuarios fiicamente
			delegado.destroy_Menu(usuarioEliminar);
			// en flujo se deja porque el fuie el autor
			// falta detalle, maestro
			// cambiamos el flow_participantes y guardamos el historico del
			// flujo si tiene
			delegado.sustituirFlowParticipantesOldByOtro(usuarioEliminar,
					usuarioRemplazo);
			// cambiamos el duenio y guardamos su historico en el flujo
			delegado.deleteTreenewDuenio(usuarioEliminar, usuarioRemplazo);
			// GUARDAMOS SU HISTORICO GENERAL
			com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
			hist.setStatus(true);
			hist.setFecha_accion(super.fechaActual());
			hist.setComentarios(cualquierComentario);
			hist.setMaquina(super.getMaquinaConectada());
			hist.setTipo_accion(Utilidades.getUsuarioEliminado());
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			hist.setUsuario_accion(user_logueado);
			hist.setUsuario_anterior(usuarioEliminar);
			hist.setUsuario_new(usuarioRemplazo);
			delegado.create(hist);
			// __________________________________________________________________
			// damos seguridad hasta abajo del nuevo nodo que se crean
			boolean swEliminar = false;
			// cargamos la operacion de la base de datos
			// Seguridad_Role_Lineal
			Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
			List<Operaciones> operacionViewInBD = delegado
					.findAll_operacionesArbol(usuarioRemplazo.getCargo());
			for (Operaciones op : operacionViewInBD) {
				delegado.llenarSeguridadLineal(op, seguridad_AuxUser_Lineal);
			}
			// truco de convertirlo
			Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal(
					seguridad_AuxUser_Lineal);
			seguridad_User_Lineal.setUsuario(usuarioRemplazo);
			seguridad_User_Lineal.setTree(usuarioRemplazo.getCargo());
			try {
				clienteSeguridad
						.heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
								seguridad_User_Lineal, swEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

			try {
				usuarioEliminar.setStatus(false);
				delegado.edit(usuarioEliminar);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
			// __________________________________________________________________
			session.setAttribute("pagIr", Utilidades.getListarUsuarios());
			pagIr = Utilidades.getFinexitoso();
			// getUsuarios();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));

		}
		return pagIr;
	}

}
