/*
 * ClienteUsuarioPassword.java
 *
 * Created on August 30, 2008, 8:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.usuarios;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.hilos.MySeguridad;

import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

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
public class ClienteUsuarioPassword extends ContextSessionRequest {
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
	private boolean swActivo = false;

	public ClienteUsuarioPassword() {
		session = super.getSession();
		// para que refresque cache en datos combo usuario
		session.setAttribute("swCacheUsuario", false);
		// EDITAMOS USUARIO
		usuario = new Usuario();
		// _______________________________________________________
		usuario = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		// _______________________________________________________

		if (usuario != null) {
			Usuario u = new Usuario();
			u.setId(usuario.getId());
			u = delegado.find_Usuario(u.getId());
			usuario = u;
			session.setAttribute("usuario", usuario);
			// caso de cambiar contraseña
			passwordOculta = (String) usuario.getPassword();

			session.setAttribute("passwordOculta", passwordOculta);

		} else {

			usuario = new Usuario();
		}

		// para que no pase
		session = null;
		/*
		 * if (session!=null){
		 * 
		 * try { //PARA DESPLAZAMIENTO DE LOS REGISTROS EN LA FORMA if
		 * (session.getAttribute("izquierdaBD")==null){
		 * session.setAttribute("izquierdaBD",0); }else{ izquierdaBD
		 * =(Integer)session.getAttribute("izquierdaBD"); } if
		 * (session.getAttribute("derechaBD")==null){
		 * session.setAttribute("derechaBD",Utilidades.getMaxRegisterMostrar());
		 * }else{ derechaBD =(Integer)session.getAttribute("derechaBD"); } if
		 * (derechaBD-izquierdaBD==0){
		 * derechaBD+=Utilidades.getMaxRegisterMostrar();
		 * session.setAttribute("derechaBD",derechaBD); }
		 * 
		 * 
		 * 
		 * //verificar si quiere ver los historicos
		 * swHistorico=session.getAttribute("historico")!=null;
		 * 
		 * 
		 * if (super.getUser_logueado()!=null &&
		 * super.getUser_logueado().getLogin()!=null){ swSuperUsuario=
		 * super.getUser_logueado
		 * ().getLogin().equalsIgnoreCase(Utilidades.getRoot()) ; }
		 * treeNodoActual
		 * =session.getAttribute("treeNodoActual")!=null?(Tree)session
		 * .getAttribute("treeNodoActual"):null; //CONSEGUIMOS LA SEGURIDAD MENU
		 * Tree treeMenu = new Tree();
		 * treeMenu.setNodo(Utilidades.getNodoRaiz());
		 * seguridadMenu=super.getSeguridadTree(treeMenu);
		 * 
		 * 
		 * user_logueado = (Usuario)session.getAttribute("user_logueado");
		 * boolean crear = session.getAttribute("crear")!=null;
		 * 
		 * IniOperaciones();
		 * 
		 * //OBTENEMOS LOS PRIMEROS USUARIOS EN LA LISTA getUsuarios();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } }else{
		 * FacesContext.getCurrentInstance().addMessage(null,new
		 * FacesMessage(messages.getString("error_userDesconectado") )); }
		 */
	}

	public String derechaGoBD() {

		if (session.getAttribute("izquierdaBD") == null) {
			session.setAttribute("izquierdaBD", 0);
		} else {
			izquierdaBD = (Integer) session.getAttribute("izquierdaBD");
		}
		if (session.getAttribute("derechaBD") == null) {
			session.setAttribute("derechaBD", Utilidades
					.getMaxRegisterMostrar());
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
			session.setAttribute("derechaBD", Utilidades
					.getMaxRegisterMostrar());
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
				.getAttribute("usuario")
				: null;
		if (usuarioHistoricoActivo != null) {
			session.setAttribute("usuarioHistoricoActivo",
					usuarioHistoricoActivo);
		}
		pagIr = Utilidades.getHistoricoUsuarioActivo();
		return pagIr;
	}

	public String delete() {
		String pagIr = "";
		session.removeAttribute("verHistoricoUsusario");
		usuarioEliminar = session.getAttribute("usuario") != null ? (Usuario) session
				.getAttribute("usuario")
				: null;
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
												messages
														.getString("error_userNoSacarseDelSistema")));
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

	public String editUsuario() {
		return "editUsuario";
	}

	public void selectUsuario(ActionEvent event) {
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
				// caso de cambiar contraseña
				passwordOculta = (String) usuario.getPassword();
				session.setAttribute("passwordOculta", passwordOculta);

				session.setAttribute("usuario", usuario);

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
				.getAttribute("invisibleItems")
				: null;
		visibleItems = session.getAttribute("visibleItems") != null ? (List) session
				.getAttribute("visibleItems")
				: null;
		if (invisibleItems == null) {
			invisibleItems = new ArrayList();
		}
		if (visibleItems == null) {
			visibleItems = new ArrayList();
		}
		boolean crear = session.getAttribute("crear") != null;
		if (!crear) {
			usuario = session.getAttribute("usuario") != null ? (Usuario) session
					.getAttribute("usuario")
					: null;

		}

		// session.setAttribute("role",role);
		setInvisibleItems(invisibleItems);
		setVisibleItems(visibleItems);
	}

	public Usuario getUsuario() {
		// _______________________________________________________

		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> findAll() {
		// return servicioUsuario.findAll();
		Usuario user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		
			return delegado.findAll_Usuario(user_logueado);
		
	}

	public String cancelarHistoricoUsuarioActivo() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Utilidades.getListarUsuarios();
	}

	public String cancelarListtUser() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			super.clearSession(session, confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("ClienteUsuarioPassword","cancelarListtUser",e);
		}
		

		return "menu";
	}

	public String cancelarEditUser() {
		// inicializamos todas las sessiones,dejamos solo las basicas, antes de
		// irnos
		try {
			super.clearSession(session, confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("ClienteUsuarioPassword","cancelarEditUser",e);
		}
		
		return "listar";
	}

	public String saveEditUser() throws NoSuchAlgorithmException {
		// String pagIr="listar";
		String pagIr = "";
		try {
			if (badDatosUsuario()) {
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

				// ****************EDITAMOS
				// USUARIO****************************************************************

				usuario.setStatus(Utilidades.isActivo());
				usuario.setMail_principal(usuario.getMail_principal()
						.toString().toLowerCase());
				usuario.setLogin(usuario.getLogin().toString().toLowerCase());
				Usuario u = new Usuario();
				u.setId(usuario.getId());

				delegado.edit(usuario);

				u = delegado.find_Usuario(u.getId());
				if (u != null) {
					if (session == null) {
						session = super.getSession();
					}

					session.setAttribute("user_logueado", u);
				}
				pagIr = Utilidades.getFinexitoso();
				// session.setAttribute("pagIr","listarAplicacion");

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));

			} catch (EcologicaExcepciones e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("error") + e));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
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
		if ((visibleItems == null) || (invisibleItems == null)) {
			visibleItems = new ArrayList();
			invisibleItems = new ArrayList();
		}
		clienteRole.findRoles_PorUsuario(visibleItems, invisibleItems, null);
		session.setAttribute("visibleItems", visibleItems);
		session.setAttribute("invisibleItems", invisibleItems);
		session.setAttribute("crear", true);
		getSessionScope().put("nuevo_usuario", new Usuario());
		// session.setAttribute("nuevo_usuario",new Usuario());
		return "crear";
	}

	public boolean badDatosUsuario() {

		if (!isSwEditarUsuario()) {
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
								+ " " + Utilidades.longCampoNormal));
				return true;
			}

		}

		if (super.isEmptyOrNull(usuario.getMail_principal())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return true;
		}

		String repetido = null;
		Usuario usuarioCheck = new Usuario();
		usuarioCheck.setMail_principal(usuario.getMail_principal());
		usuarioCheck.setLogin(usuario.getLogin());
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
							+ " " + Utilidades.longCampoNormal));
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
							+ " " + Utilidades.longCampoNormal));
			return true;
		}

		if (usuario.getDireccion().length() > Utilidades.longCampoGrande) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_longNormalExede")
							+ " " + Utilidades.longCampoGrande));
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
								+ " " + Utilidades.longCampoNormal));
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
			/*
			 * Tree c=null; if (getSelectCargo()!=null){ c=
			 * (Tree)getSelectCargo().getValue(); usuario.setCargo(c); }
			 */
			if (badDatosUsuario()) {
				return pagIr;
			}
			try {
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
					usuario.setPassword(EncryptorMD5.getMD5(usuario
							.getPassword()));

					setPasswordConfirm(EncryptorMD5.getMD5(usuario
							.getPassword()));
					// ***********************CREAMOS EL
					// USUARIO**************************************

					usuario.setMail_principal(usuario.getMail_principal()
							.toString().toLowerCase());
					usuario.setLogin(usuario.getLogin().toString()
							.toLowerCase());

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
						// introducimos las nuevas operaciones al rol
						visibleItems = session.getAttribute("visibleItems") != null ? (List) session
								.getAttribute("visibleItems")
								: null;
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
								// me creara el role para menu, asi no tenga
								// ninguna operacion para administrar el menu
								// if (isForSeguridadRoleMenu(role)){
								Menus_Usuarios menus_Usuarios = new Menus_Usuarios();
								menus_Usuarios.setUsuario(usuario);
								menus_Usuarios.setRole(role);
								delegado.create(menus_Usuarios);
								// }
							}
						}
						// damos la nueva seguridad a dichoo nodo del nuevo
						// usuario
						// truco de convertirlo
						Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
						seguridad_User_Lineal.setUsuario(usuario);
						seguridad_User_Lineal.setTree(usuario.getCargo());
						clienteSeguridad
								.darSeguridadNodo(seguridad_User_Lineal);

						// damos la nueva seguridad al usuario que creo dicho
						// nodo
						user_logueado = (Usuario) session
								.getAttribute("user_logueado");
						if (user_logueado != null) {
							seguridad_User_Lineal = new Seguridad_User_Lineal();
							seguridad_User_Lineal.setUsuario(user_logueado);
							seguridad_User_Lineal.setTree(usuario.getCargo());
							clienteSeguridad
									.darSeguridadNodo(seguridad_User_Lineal);
							clienteSeguridad
								.ponerSeguridadInSession(user_logueado);
//							HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//							hiloClienteSeguridad.start();
						}

					}

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
					usuario = new Usuario();

					session.setAttribute("pagIr", Utilidades
							.getListarUsuarios());
					pagIr = Utilidades.getFinexitoso();
					return pagIr;
				} else {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_passwordInval")));
				}

			} catch (EcologicaExcepciones e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
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
				.getAttribute("derechaBD")
				: 0;
		izquierdaBD = session.getAttribute("izquierdaBD") != null ? (Integer) session
				.getAttribute("izquierdaBD")
				: 0;

		user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		if (Utilidades.getLogin() == getBuscarPor()
				|| Utilidades.getCargo() == getBuscarPor()
				|| Utilidades.getNombre() == getBuscarPor()
				|| Utilidades.getApellido() == getBuscarPor()) {
			// busca el texto y que tipo, si es login, apellido, nombre
			usuarios = delegado.findAllHistoricoUsuarios(user_logueado,
					getStrBuscar(), getBuscarPor(), activo, derechaBD,
					izquierdaBD);

		} else {
			usuarios = delegado.findAllHistoricoUsuarios(user_logueado,
					getStrBuscar(), activo, derechaBD, izquierdaBD);
		}

		user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
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
				Usuario user = procesarLoginsConectados.loadUsuarioById(usuario
						.getId(), application, confmessages
						.getString("usuariosApplicaction"));
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
				usuariosVer.add(usuario);
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
			// user_logueado = (Usuario) session.getAttribute("user_logueado");
			List<SelectItem> lstprof = new ArrayList<SelectItem>();
			System.out.println("user_logueado=null?" + (usuario == null));
			List<Profesion> lstbdProf = delegado.findAll_Profesion(usuario);
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
		swEditarUsuario = true;// session.getAttribute("user_logueado")!=null;
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
						if (swSuperUsuario || seguridadTree.isToView()) {
							item = new SelectItem(area, area.getNombre());
							result.add(item);
						}

					}
				}
			}
		}
		return result;
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
			if (swSuperUsuario || seguridadTree.isToView()) {
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
		if (swSuperUsuario) {
			swDelUsuario = true;
		}

		return swDelUsuario;
	}

	public void setSwDelUsuario(boolean swDelUsuario) {
		this.swDelUsuario = swDelUsuario;
	}

	public boolean isSwAddUsuario() {
		swAddUsuario = seguridadMenu.isToAddUsuario();
		if (swSuperUsuario) {
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
		if (swSuperUsuario) {
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
						if (swSuperUsuario || seguridadTree.isToView()) {
							item = new SelectItem(principal, principal
									.getNombre());
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
				.getAttribute("izquierdaBD")
				: 0;
		return izquierdaBD;
	}

	public void setIzquierdaBD(int izquierdaBD) {
		this.izquierdaBD = izquierdaBD;
	}

	public int getDerechaBD() {
		derechaBD = session.getAttribute("derechaBD") != null ? (Integer) session
				.getAttribute("derechaBD")
				: 0;
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

	public boolean isSwActivo() {
		swActivo = true;
		return swActivo;
	}

	public void setSwActivo(boolean swActivo) {
		this.swActivo = swActivo;
	}

}
