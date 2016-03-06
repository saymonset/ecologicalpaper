/*
 * ClienteRole.java
 *
 * Created on July 16, 2007, 7:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.cliente.role;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;

import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.cliente.usuarios.ClienteUsuario;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class ClienteRole extends ContextSessionRequest {

	private ServicioDelegado delegado = new ServicioDelegado();
	private Role role;
	private List operaciones;
	private List<Role> roles;
	private HttpSession session = super.getSession();
	private ClienteOperaciones clienteOperaciones = new ClienteOperaciones();
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private String strBuscar;
	private Usuario user_logueado;
	// _____________________________________
	// mostrar todos los roles estilo popup
	private List _roles_popup = new ArrayList();
	// _____________________________________
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	/** Creates a new instance of ClienteRole */
	private Seguridad seguridadMenu = new Seguridad();
	private boolean swMod;
	private boolean swDel;
	private boolean swAdd;
	private Tree treeNodoActual = null;
	private boolean swSuperUsuario;
	private boolean usadoParaCrearFlujo;

	private Tree empresa;

	/** Creates a new instance of ClienteRole */
	public ClienteRole() {

		session = super.getSession();
		if (session != null) {
			// ES PARA FLUJO DE TRABAJO O PARA PRIVILEGIOS DE GRUPO
			usadoParaCrearFlujo = session.getAttribute("usadoParaCrearFlujo") != null ? (Boolean) session
					.getAttribute("usadoParaCrearFlujo") : false;

			treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			// verificamos si es super usuario
			if (super.getUser_logueado() != null
					&& super.getUser_logueado().getLogin() != null) {
				swSuperUsuario = super.getUser_logueado().getLogin()
						.equalsIgnoreCase(Utilidades.getRoot());
			}
			// obtenemos lista de roles
			try {
				getRoles();				
			} catch (Exception e) {
				// TODO: handle exception
			}

			// -----------
			// OBTENEMOS SEGURIDAD;
			// CONSEGUIMOS LA SEGURIDAD MENU
			Tree treeMenu = new Tree();
			treeMenu.setNodo(Utilidades.getNodoRaiz());
			seguridadMenu = super.getSeguridadTree(treeMenu);
			if (seguridadMenu == null) {
				seguridadMenu = new Seguridad();
			}

			// variable que viene del menu
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			boolean crear = session.getAttribute("crear") != null;
			if (crear) {
				role = new Role();
			} else {
				IniOperaciones();
			}
		} else {
			role = new Role();
		}
		// role=new Role();
		// se usa aqui, cada vez que alla una submit del .jsp, o cuando se
		// inicialicen las variables para editar(selectRol)

	}

	public String cancelarEditar() {
		try {
			// inicializamos todas las sessiones,dejamos solo las basicas, antes
			// de irnos
			super.clearSession(session,
					confmessages.getString("usuarioSession"));

		} catch (Exception e) {
			redirect("ClienteRole", "cancelarEditar", e);
		}
		return "listarRole";
	}

	public String delete() {
		Role obj = session.getAttribute("role") != null ? (Role) session
				.getAttribute("role") : null;

		if (obj != null) {
			try {
				obj = delegado.findRole(obj);

				try {
					// ponemos anul todos los flujos de referencia con ese rol
					// colocamos los controles de tiempoi del usuario con rol a
					// null
					// en la tabla FlowControlByUsuarioBean
					List<FlowControlByUsuarioBean> lista = delegado
							.find_allFlowControlByRoleBean(obj);
					for (FlowControlByUsuarioBean ponAnull : lista) {
						ponAnull.setRole(null);
						delegado.edit(ponAnull);
					}

					// roles que pertenece al menu
					delegado.destroy_MenuByRole(obj);// fisico
					// los flujos donde van los roles, se colocan en null el rol
					delegado.deletellFlow_ParticipantesByRole(obj);
					// flow referencia role tabla
					delegado.destroyAllFlow_referencia_roleByRole(obj);

					// roles que pertenece al usuario
					delegado.destroyAlldRoles_UsuarioByRole(obj);// fisico
					// role operaciones
					delegado.destroy_Rol(obj);// fisico

				} catch (EcologicaExcepciones ex) {
					System.out.println(ex.toString());
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											messages.getString("error")
													+ messages
															.getString(ex
																	.toString()
																	.substring(
																			0,
																			ex.toString()
																					.length() - 1))));
				}// fisico
			} catch (RoleMultiples ex) {
				System.out.println(ex.toString());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("error")
								+ messages.getString(ex.toString().substring(0,
										ex.toString().length() - 1))));
			} catch (RoleNoEncontrado ex) {
				System.out.println(ex.toString());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("error")
								+ messages.getString(ex.toString().substring(0,
										ex.toString().length() - 1))));
			}

			// ELIMINAMOS SEGURIDDA_ROLE
			List<Seguridad_Role_Lineal> segRs = delegado
					.buscarTodosLosRolesTreeCompleto(obj);
			for (Seguridad_Role_Lineal s_r : segRs) {
				try {
					// ELIMINAMOS DENUEVO POR SI ACASO
					// los flujos donde van los roles, se colocan en null el rol
					delegado.deletellFlow_ParticipantesByRole(role);
					// flow referencia role tabla
					delegado.destroyAllFlow_referencia_roleByRole(role);

					// roles que pertenece al usuario
					delegado.destroyAlldRoles_UsuarioByRole(s_r.getRole());// fisico
					// role operaciones
					delegado.destroy_Rol(s_r.getRole());// fisico
					// seguridad role role, tree
					delegado.destroy(s_r);// fisico

				} catch (EcologicaExcepciones ex) {
					System.out.println(ex.toString());
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											messages.getString("error")
													+ messages
															.getString(ex
																	.toString()
																	.substring(
																			0,
																			ex.toString()
																					.length() - 1))));
				}// fisico
			}
			try {
				try {

					obj = delegado.findRole(obj);
					delegado.destroy(obj);// eliminamos el role de una vez
					// fisicamente

				} catch (RoleMultiples ex) {
					System.out.println(ex.toString());
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											messages.getString("error")
													+ messages
															.getString(ex
																	.toString()
																	.substring(
																			0,
																			ex.toString()
																					.length() - 1))));
				} catch (RoleNoEncontrado ex) {
					System.out.println(ex.toString());
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											messages.getString("error")
													+ messages
															.getString(ex
																	.toString()
																	.substring(
																			0,
																			ex.toString()
																					.length() - 1))));
				}

			} catch (EcologicaExcepciones ex) {
				System.out.println(ex.toString());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("error")
								+ messages.getString(ex.toString().substring(0,
										ex.toString().length() - 1))));
			}// eliminamos el role de una vez fisicamente

			getRoles();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));

		}
		return "";
	}

	public String editRole() {
		return "editRole";
	}

	public String cancelarListRol() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteRole", "cancelarListRol", e);
		}

		return "menu";
	}

	public String inic_crear() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public void selectRol(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (role == null) {
				role = new Role();
			}
			// buscamos el role para editar
			if (id >= 0) {
				role.setCodigo(new Long(id));
			}

			try {
				role = getFindRole();
				session.setAttribute("role", role);

				visibleItems = new ArrayList();
				invisibleItems = new ArrayList();

				visibleItems2 = new ArrayList();
				invisibleItems2 = new ArrayList();

				Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
				seguridad_Role_Lineal.setRole(role);
				clienteOperaciones.llenarOperacionesVisibles(visibleItems,
						invisibleItems, seguridad_Role_Lineal);
				session.setAttribute("visibleItems", visibleItems);
				session.setAttribute("invisibleItems", invisibleItems);

				findUsuario_PorRoles(visibleItems2, invisibleItems2, role);
				session.setAttribute("visibleItems2", visibleItems2);
				session.setAttribute("invisibleItems2", invisibleItems2);

				// IniOperaciones();
			} catch (RoleMultiples ex) {
				ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				ex.printStackTrace();
			}

		}
	}

	public void IniOperaciones() {
		customizePageBean = new CustomizePageBean();
		customizePageBean2 = new CustomizePageBean();
		invisibleItems = session.getAttribute("invisibleItems") != null ? (List) session
				.getAttribute("invisibleItems") : null;
		visibleItems = session.getAttribute("visibleItems") != null ? (List) session
				.getAttribute("visibleItems") : null;

		invisibleItems2 = session.getAttribute("invisibleItems2") != null ? (List) session
				.getAttribute("invisibleItems2") : null;
		visibleItems2 = session.getAttribute("visibleItems2") != null ? (List) session
				.getAttribute("visibleItems2") : null;
		role = session.getAttribute("role") != null ? (Role) session
				.getAttribute("role") : null;
		// session.setAttribute("role",role);
		setInvisibleItems(invisibleItems);
		setVisibleItems(visibleItems);

		setInvisibleItems2(invisibleItems2);
		setVisibleItems2(visibleItems2);
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */
	public String create() {
		String pagIr = "";
		try {
			if (("".equals(role.getNombre().toString().trim()))
					|| ("".equals(role.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			try {

				if (swSuperUsuario) {
					role.setEmpresa(empresa);
				} else {
					user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
							.getAttribute("user_logueado") : null;
					role.setEmpresa(user_logueado.getEmpresa());
				}
				role.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
				// System.out.println("grabando role ... ");
				delegado.create(role);
				// System.out.println("fin  role ... ");
				session.setAttribute("pagIr", Utilidades.getListarRoles());
				pagIr = Utilidades.getFinexitoso();
			} catch (RoleNoEncontrado ex) {
				ex.printStackTrace();
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			} catch (RoleMultiples ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String saveRole() {
		String pagIr = "";
		try {
			role.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
			delegado.edit(role);

			// INICIO SOLO PARA LOS ROLES QUE NO SEAN USADOS PARA
			// FLUJOS--------------------------
			if (!usadoParaCrearFlujo) {
				// borra solo el role que tiene tree es null
				delegado.destroy_RolEditando(role);
				// eliminamos todos las operaciones del ro, anteriormente
				// seleccionados
				// el role lineal con tree = null es el rol de referencia..,los
				// demas que tree !=null , se actualizaran

				// introducimos las nuevas operaciones al rol
				visibleItems = session.getAttribute("visibleItems") != null ? (List) session
						.getAttribute("visibleItems") : null;
				Seguridad_Role_Lineal seguridad_Role_Lineal = null;
				int length = visibleItems.size();
				// Seguridad_Role_Lineal
				seguridad_Role_Lineal = new Seguridad_Role_Lineal();
				for (int i = 0; i < length; i++) {
					// AGARRAMOS CADA OPERACION INDIVIDUAL
					SelectItem value = (SelectItem) visibleItems.get(i);
					Operaciones oper2 = (Operaciones) value.getValue();
					if (oper2 != null && oper2 instanceof Operaciones) {
						// llenamos por referencia el objeto
						// seguridad_Role_Lineal
						// con la operacion que se le da
						delegado.llenarSeguridadLineal(oper2,
								seguridad_Role_Lineal);
					}
				}
				// tenemos todas las operaciones guardadas, colocamos el rol que
				// representa la operacion y
				// guardamos en la BD.
				seguridad_Role_Lineal.setRole(role);
				delegado.create(seguridad_Role_Lineal);

				// SI HAY YA ROLES GRABADOS EN TREE, LO ACTUALIZAMOS
				List<Seguridad_Role_Lineal> listaActualizarTree = delegado
						.findAllSeguridad_Role_Lineal(role);
				for (Seguridad_Role_Lineal segTree : listaActualizarTree) {
					if (segTree.getTree() != null) {
						Seguridad_Role_Lineal seguridad_Role_Lineal3 = new Seguridad_Role_Lineal(
								seguridad_Role_Lineal);
						seguridad_Role_Lineal3.setCodigo(segTree.getCodigo());
						seguridad_Role_Lineal3.setTree(segTree.getTree());
						delegado.edit(seguridad_Role_Lineal3);
					//	super.givePermparaverToGroup(seguridad_Role_Lineal3);
					}
				}
			}
			// FIN SOLO PARA LOS ROLES QUE NO SEAN USADOS PARA
			// FLUJOS--------------------------

			// GRABAMOS LOS NUEVOS PERSONAS EN ROLES
			List<Roles_Usuarios> enBdRolUser = new ArrayList<Roles_Usuarios>();
			enBdRolUser = delegado.findRoles_AND_Usuario(role);

			// Eliminamnos los usuarios del rol
			for (Roles_Usuarios rolUser : enBdRolUser) {
				delegado.destroy(rolUser);
			}
			// llenamos los nuevos usuarios del rol
			visibleItems2 = session.getAttribute("visibleItems2") != null ? (List) session
					.getAttribute("visibleItems2") : null;
			Roles_Usuarios role_usuario;
			int length = 0;
			if (visibleItems2 != null) {
				length = visibleItems2.size();
			}
			// para grabar en usuario menu
			ClienteUsuario clienteUsuario = new ClienteUsuario(null);
			for (int i = 0; i < length; i++) {
				SelectItem value = (SelectItem) visibleItems2.get(i);
				Usuario usuario = (Usuario) value.getValue();
				if (usuario != null && usuario instanceof Usuario) {
					role_usuario = new Roles_Usuarios();
					role_usuario.setRole(role);
					role_usuario.setUsuario(usuario);
					delegado.create(role_usuario);
					if (clienteUsuario.isForSeguridadRoleMenu(role)) {
						Menus_Usuarios menus_Usuarios = new Menus_Usuarios();
						menus_Usuarios.setUsuario(usuario);
						menus_Usuarios.setRole(role);
						delegado.create(menus_Usuarios);
					}
				}
			}
			// HAY QUE ACTUALIZAR LOS USUARIOS QUE TENGAN EL NUEVO ROLE
			// actualizamos la s3gureidad en xession
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
//					HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//					hiloClienteSeguridad.start();
	clienteSeguridad.ponerSeguridadInSession(user_logueado);

			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarRoles());

		} catch (EcologicaExcepciones e) {
			System.out.println(e.toString());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public void destroy() throws EcologicaExcepciones {
		try {
			delegado.destroy(role);
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			e.printStackTrace();
		}
	}

	public void findRole() throws RoleMultiples, RoleNoEncontrado {
		try {
			role = delegado.findRole(role);
		} catch (RoleMultiples e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			e.printStackTrace();
		} catch (RoleNoEncontrado e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			e.printStackTrace();
		}
	}

	public Role getFindRole() throws RoleMultiples, RoleNoEncontrado {
		try {
			role = delegado.getRole(role);
		} catch (EcologicaExcepciones ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			ex.printStackTrace();
		} catch (RoleMultiples ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			ex.printStackTrace();
		} catch (RoleNoEncontrado ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			ex.printStackTrace();
		}
		return role;

	}

	public List<Role> findAll() {
		// return servicioUsuario.findAll();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		return delegado.findAll_Roles(user_logueado);
	}

	public List<Role> findAll(Usuario usuario) {
		// return servicioUsuario.findAll();

		return delegado.findAll_Roles(usuario);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {

			// encontrar por empresa seleccionada
			Tree empresa = (Tree) session.getAttribute("empresaEscojer");
			// esta asignacion de empresa es temporal para hacer el filtrado...
			Usuario usuTmp = new Usuario();
			if (empresa != null) {
				usuTmp.setLogin("temporal");
				usuTmp.setEmpresa(empresa);
			} else {
				usuTmp.setLogin("temporal");
				usuTmp.setEmpresa(user_logueado.getEmpresa());
			}

			if (!super.isEmptyOrNull(getStrBuscar())) {
				// SOLO SE USA PARA ROLES CON ASIGNACION DE PRIVILEGIOS O FLUJOS
				// CON usadoParaCrearFlujo
				roles = delegado.findAll_Roles(usuTmp, getStrBuscar(),
						usadoParaCrearFlujo);

			} else {
				// SOLO SE USA PARA ROLES CON ASIGNACION DE PRIVILEGIOS O FLUJOS
				// CON usadoParaCrearFlujo
				roles = delegado.findAll_Roles(usuTmp, usadoParaCrearFlujo);
			}

			List<Role> lista = new ArrayList<Role>();
			for (Role p : roles) {
				p.setDelete(false);
				if (isSwDel()) {
					p.setDelete(true);
				}
				lista.add(p);
			}

			roles = lista;
		} else {

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			try {
				response.sendRedirect("./errorPage.jsf ");
			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			roles = new ArrayList<Role>();
			role = new Role();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	// List<Roles_Usuarios> (Role role)
	public void findUsuario_PorRoles(List seleccionados, List noSeleccionados,
			Role role) {
		List<Roles_Usuarios> enBdRolUser = new ArrayList<Roles_Usuarios>();
		List<Usuario> enBd = new ArrayList<Usuario>();
		session = super.getSession();
		if (session != null) {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
		}
		SelectItem item = null;
		if (role != null) {
			enBdRolUser = delegado.findRoles_AND_Usuario(role);
			item = null;
			// Roles_Usuarios role_user = (Roles_Usuarios) result.get(i);
			for (Roles_Usuarios rolUser : enBdRolUser) {
				enBd.add(rolUser.getUsuario());
			}
			for (Usuario user : enBd) {

				String cargo = user.getCargo() != null ? user.getCargo()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					cargo = "[" + cargo + "]";
				}

				String empresa = user.getEmpresa() != null ? user.getEmpresa()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					empresa = "[" + empresa + "]";
				}
				item = new SelectItem(user, user.getApellido() + " "
						+ user.getNombre() + cargo + empresa);
				seleccionados.add(item);
			}
		}
		List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
		item = null;
		for (Usuario user : items2) {
			if (!enBd.contains(user)) {
				String cargo = user.getCargo() != null ? user.getCargo()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					cargo = "[" + cargo + "]";
				}

				String empresa = user.getEmpresa() != null ? user.getEmpresa()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					empresa = "[" + empresa + "]";
				}
				item = new SelectItem(user, user.getApellido() + " "
						+ user.getNombre() + cargo + empresa);
				// item = new SelectItem(user, user.getNombre());
				noSeleccionados.add(item);
			}
		}
	}

	public void findRoles_PorUsuario(List seleccionados, List noSeleccionados,
			Usuario usuario) {
		List enBd = new ArrayList();
		int size = 0;
		SelectItem item = null;
		if (usuario != null) {
			enBd = delegado.findRoles_Usuario(usuario);
			size = enBd.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Role role = (Role) enBd.get(i);
				item = new SelectItem(role, role.getNombre());
				seleccionados.add(item);
			}
		}
		List items2 = findAll(usuario);
		size = items2.size();
		item = null;
		for (int i = 0; i < size; i++) {
			Role role = (Role) items2.get(i);
			if (!enBd.contains(role)) {
				item = new SelectItem(role, role.getNombre());
				noSeleccionados.add(item);
			}
		}
	}

	/*
	 * public void buscarTodosLosRoles(){ ClienteRole_OperacionesPopup
	 * role_oper_popup = new ClienteRole_OperacionesPopup(); List<Role> rols =
	 * delegado.findAll_Roles(); for(Role role:rols){
	 * 
	 * //buscamos todos las operaciones del rol List<SelectItem>
	 * OperacionesLista= clienteSeguridad.operacionesInRolesMenuAndTree(role);
	 * StringBuffer cuantasOperaciones = new StringBuffer(""); boolean
	 * swPrimeravez=true; for (SelectItem item:OperacionesLista){ //Operaciones
	 * op=(Operaciones)item.getValue(); if (swPrimeravez){
	 * cuantasOperaciones.append(item.getLabel()); swPrimeravez=false; }else{
	 * cuantasOperaciones.append(", ").append(item.getLabel()); } } //buscamos
	 * todos los usuarios del rol List<SelectItem> UsuariosLista=
	 * clienteSeguridad.usuariosInRole(role); StringBuffer UsuarioInf = new
	 * StringBuffer(""); swPrimeravez=true; for (SelectItem item:UsuariosLista){
	 * if (swPrimeravez){ UsuarioInf.append(item.getLabel());
	 * swPrimeravez=false; }else{
	 * UsuarioInf.append(", ").append(item.getLabel()); } }
	 * 
	 * role_oper_popup = new
	 * ClienteRole_OperacionesPopup(role.getCodigo(),role.getNombre
	 * (),role.getDescripcion
	 * (),cuantasOperaciones.toString(),UsuarioInf.toString());
	 * _roles_popup.add(role_oper_popup);
	 * 
	 * } }
	 */
	public List getRoles_popup() {
		_roles_popup = new ArrayList();
		_roles_popup.clear();

		// encontrar por empresa seleccionada
		Tree empresa = (Tree) session.getAttribute("empresaEscojer");
		// esta asignacion de empresa es temporal para hacer el filtrado...
		Usuario usuTmp = new Usuario();
		if (empresa != null) {
			usuTmp.setLogin("temporal");
			usuTmp.setEmpresa(empresa);
		} else {
			usuTmp.setLogin("temporal");
			usuTmp.setEmpresa(user_logueado.getEmpresa());
		}

		clienteSeguridad.buscarTodosLosRoles(_roles_popup, usadoParaCrearFlujo);
		return _roles_popup;
	}

	public List getRolesFlow_popup() {
		_roles_popup = new ArrayList();
		_roles_popup.clear();

		// encontrar por empresa seleccionada
		Tree empresa = (Tree) session.getAttribute("empresaEscojer");
		// esta asignacion de empresa es temporal para hacer el filtrado...
		Usuario usuTmp = new Usuario();
		if (empresa != null) {
			usuTmp.setLogin("temporal");
			usuTmp.setEmpresa(empresa);
		} else {
			usuTmp.setLogin("temporal");
			usuTmp.setEmpresa(user_logueado.getEmpresa());
		}
		usadoParaCrearFlujo = true;
		clienteSeguridad.buscarTodosLosRoles(_roles_popup, usadoParaCrearFlujo);
		return _roles_popup;
	}

	public void setRoles_popup(List roles_popup) {
		this._roles_popup = roles_popup;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	/*************** SELECT MULTIPLES *****************************************/
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
		invisibleItems = customizePageBean.getInvisibleItems();
		return invisibleItems;
	}

	public void setInvisibleItems(List invisibleItems) {
		customizePageBean.setInvisibleItems(invisibleItems);
		this.invisibleItems = invisibleItems;
	}

	public List getVisibleItems() {
		visibleItems = customizePageBean.getVisibleItems();
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

	/*************** SELECT MULTIPLES2 *****************************************/
	/********************************************************/
	/********************************************************/
	private CustomizePageBean customizePageBean2;
	// List of SelectItem instances
	private List invisibleItems2;

	// List of SelectItem instances
	private List visibleItems2;
	// array of values of selected items in "Invisible" list
	private Object[] selectedInvisibleItems2;
	// array of values of selected items in the "Visible" list
	private Object[] selectedVisibleItems2;

	public void moveSelectedToVisible2(ActionEvent actionEvent) {
		customizePageBean2.moveSelectedToVisible(actionEvent);
	}

	public void moveAllToVisible2(ActionEvent actionEvent) {
		customizePageBean2.moveAllToVisible(actionEvent);
	}

	public void moveSelectedToInvisible2(ActionEvent actionEvent) {
		customizePageBean2.moveSelectedToInvisible(actionEvent);
	}

	public void moveAllToInvisible2(ActionEvent actionEvent) {
		customizePageBean2.moveAllToInvisible(actionEvent);
	}

	public CustomizePageBean getCustomizePageBean2() {
		return customizePageBean2;
	}

	public void setCustomizePageBean2(CustomizePageBean customizePageBean2) {
		this.customizePageBean2 = customizePageBean2;
	}

	public List getInvisibleItems2() {
		invisibleItems2 = customizePageBean2.getInvisibleItems();
		return invisibleItems2;
	}

	public void setInvisibleItems2(List invisibleItems2) {
		customizePageBean2.setInvisibleItems(invisibleItems2);
		this.invisibleItems2 = invisibleItems2;
	}

	public List getVisibleItems2() {
		visibleItems2 = customizePageBean2.getVisibleItems();
		return visibleItems2;
	}

	public void setVisibleItems2(List visibleItems2) {
		customizePageBean2.setVisibleItems(visibleItems2);
		this.visibleItems2 = visibleItems2;
	}

	public Object[] getSelectedInvisibleItems2() {
		selectedInvisibleItems2 = customizePageBean2
				.getSelectedInvisibleItems();
		return selectedInvisibleItems2;
	}

	public void setSelectedInvisibleItems2(Object[] selectedInvisibleItems2) {
		customizePageBean2.setSelectedInvisibleItems(selectedInvisibleItems2);
		this.selectedInvisibleItems2 = selectedInvisibleItems2;
	}

	public Object[] getSelectedVisibleItems2() {
		selectedVisibleItems2 = customizePageBean2.getSelectedVisibleItems();
		return selectedVisibleItems2;
	}

	public void setSelectedVisibleItems2(Object[] selectedVisibleItems2) {
		customizePageBean2.setSelectedVisibleItems(selectedVisibleItems2);
		this.selectedVisibleItems2 = selectedVisibleItems2;
	}

	/********************************************************/
	/********************************************************/
	/********************************************************/
	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToModGrupos();
		if (swSuperUsuario) {
			swMod = true;
		}

		return swMod;
	}

	public void setSwMod(boolean swMod) {

		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelGrupos();
		if (swSuperUsuario) {
			swDel = true;
		}

		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		if (seguridadMenu != null) {
			swAdd = seguridadMenu.isToAddGrupos();
			if (swSuperUsuario) {
				swAdd = true;
			}
		}

		return swAdd;
	}

	public void setSwAdd(boolean swAdd) {

		this.swAdd = swAdd;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	/**
	 * @return the empresa
	 */
	public Tree getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Tree empresa) {
		session.setAttribute("empresaEscojer", empresa);
		this.empresa = empresa;
	}

	public boolean isUsadoParaCrearFlujo() {
		return usadoParaCrearFlujo;
	}

	public void setUsadoParaCrearFlujo(boolean usadoParaCrearFlujo) {
		this.usadoParaCrearFlujo = usadoParaCrearFlujo;
	}

	public void findUsuario_PorRolesRichFaces(List seleccionados,
			List noSeleccionados, Role role) {
		List<Roles_Usuarios> enBdRolUser = new ArrayList<Roles_Usuarios>();
		List<Usuario> enBd = new ArrayList<Usuario>();
		session = super.getSession();
		if (session != null) {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
		}

		if (role != null) {
			enBdRolUser = delegado.findRoles_AND_Usuario(role);

			// Roles_Usuarios role_user = (Roles_Usuarios) result.get(i);
			for (Roles_Usuarios rolUser : enBdRolUser) {
				enBd.add(rolUser.getUsuario());
			}
			for (Usuario user : enBd) {

				String cargo = user.getCargo() != null ? user.getCargo()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					cargo = "[" + cargo + "]";
				}

				String empresa = user.getEmpresa() != null ? user.getEmpresa()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					empresa = "[" + empresa + "]";
				}
				// item = new SelectItem(user, user.getApellido() + " "
				// + user.getNombre() + cargo + empresa);
				seleccionados.add(user);
			}
		}
		List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);

		for (Usuario user : items2) {
			if (!enBd.contains(user)) {
				String cargo = user.getCargo() != null ? user.getCargo()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					cargo = "[" + cargo + "]";
				}

				String empresa = user.getEmpresa() != null ? user.getEmpresa()
						.getNombre() : "";
				if (!"".equals(cargo)) {
					empresa = "[" + empresa + "]";
				}
				// item = new SelectItem(user, user.getApellido() + " "
				// + user.getNombre() + cargo + empresa);
				//
				noSeleccionados.add(user);
			}
		}
	}

	public void findRoles_PorUsuarioRichFaces(List seleccionados,
			List noSeleccionados, Usuario usuario) {
		List enBd = new ArrayList();
		int size = 0;
		// SelectItem item = null;
		if (usuario != null) {
			enBd = delegado.findRoles_Usuario(usuario);
			size = enBd.size();

			for (int i = 0; i < size; i++) {
				Role role = (Role) enBd.get(i);
				System.out.println("role 0=" + role.getNombre());
				// item = new SelectItem(role, role.getNombre());
				seleccionados.add(role);
			}
		}
		List items2 = delegado.findAll_Roles(usuario);
		;// findAll(usuario);
		size = items2.size();
		// item = null;
		for (int i = 0; i < size; i++) {
			Role role = (Role) items2.get(i);
			System.out.println("role=" + role.getNombre());
			if (!enBd.contains(role)) {
				// item = new SelectItem(role, role.getNombre());
				noSeleccionados.add(role);
			}
		}
	}

	public String saveRoleRichFaces() {
		String pagIr = "";
		//REFRTESCAMOS EL ARBOL
		refrescaraplicacionarbol();
		//para recrear el arbol de seguridad denuevo
		try {
			role.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
			delegado.edit(role);

			// INICIO SOLO PARA LOS ROLES QUE NO SEAN USADOS PARA
			// FLUJOS--------------------------
			if (!usadoParaCrearFlujo) {
				delegado.destroy_RolEditando(role);
				// eliminamos todos las operaciones del ro, anteriormente
				// seleccionados
				// el role lineal con tree = null es el rol de referencia..,los
				// demas que tree !=null , se actualizaran
				// introducimos las nuevas operaciones al rol
				List<Operaciones> visibleItems = session.getAttribute("visibleItems") != null ? (List) session
						.getAttribute("visibleItems") : null;
				delegado.llenarDatosRole(role,visibleItems);
			}
			// FIN SOLO PARA LOS ROLES QUE NO SEAN USADOS PARA
			// FLUJOS--------------------------

			// GRABAMOS LOS NUEVOS PERSONAS EN ROLES
			List<Roles_Usuarios> enBdRolUser = new ArrayList<Roles_Usuarios>();
			enBdRolUser = delegado.findRoles_AND_Usuario(role);

			// Eliminamnos los usuarios del rol
			for (Roles_Usuarios rolUser : enBdRolUser) {
				delegado.destroy(rolUser);
			}

			llenarUsuariosAlRole(role);

			// HAY QUE ACTUALIZAR LOS USUARIOS QUE TENGAN EL NUEVO ROLE
			// actualizamos la s3gureidad en xession
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			clienteSeguridad.ponerSeguridadInSession(user_logueado);
//					HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//					hiloClienteSeguridad.start();

			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarRoles());

		} catch (EcologicaExcepciones e) {
			System.out.println(e.toString());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
		}
		return pagIr;
	}

	public void llenarUsuariosAlRole(Role role) {
		// llenamos los nuevos usuarios del rol
		List<Usuario> visibleItems2 = session.getAttribute("visibleItems2") != null ? (List) session
				.getAttribute("visibleItems2") : null;
		Roles_Usuarios role_usuario;

		// para grabar en usuario menu
		ClienteUsuario clienteUsuario = new ClienteUsuario(null);
		for (Usuario usuario : visibleItems2) {

			if (usuario != null && usuario instanceof Usuario) {
				role_usuario = new Roles_Usuarios();
				role_usuario.setRole(role);
				role_usuario.setUsuario(usuario);
				delegado.create(role_usuario);
				if (clienteUsuario.isForSeguridadRoleMenu(role)) {
					Menus_Usuarios menus_Usuarios = new Menus_Usuarios();
					menus_Usuarios.setUsuario(usuario);
					menus_Usuarios.setRole(role);
					delegado.create(menus_Usuarios);
				}
			}
		}
	}

//	public void llenarDatosRole(Role role) {
//		// introducimos las nuevas operaciones al rol
//		List<Operaciones> visibleItems = session.getAttribute("visibleItems") != null ? (List) session
//				.getAttribute("visibleItems") : null;
//		Seguridad_Role_Lineal seguridad_Role_Lineal = null;
//		// Seguridad_Role_Lineal
//		seguridad_Role_Lineal = new Seguridad_Role_Lineal();
//		for (Operaciones oper2 : visibleItems) {
//			// AGARRAMOS CADA OPERACION INDIVIDUAL
//			if (oper2 != null && oper2 instanceof Operaciones) {
//				// llenamos por referencia el objeto
//				// seguridad_Role_Lineal
//				// con la operacion que se le da
//				delegado.llenarSeguridadLineal(oper2, seguridad_Role_Lineal);
//			}
//		}
//		// tenemos todas las operaciones guardadas, colocamos el rol que
//		// representa la operacion y
//		// guardamos en la BD.
//		seguridad_Role_Lineal.setRole(role);
//		delegado.create(seguridad_Role_Lineal);
//
//		// SI HAY YA ROLES GRABADOS EN TREE, LO ACTUALIZAMOS
//		List<Seguridad_Role_Lineal> listaActualizarTree = delegado
//				.findAllSeguridad_Role_Lineal(role);
//		for (Seguridad_Role_Lineal segTree : listaActualizarTree) {
//			if (segTree.getTree() != null) {
//				Seguridad_Role_Lineal seguridad_Role_Lineal3 = new Seguridad_Role_Lineal(
//						seguridad_Role_Lineal);
//				seguridad_Role_Lineal3.setCodigo(segTree.getCodigo());
//				seguridad_Role_Lineal3.setTree(segTree.getTree());
//				delegado.edit(seguridad_Role_Lineal3);
//				super.givePermparaverToGroup(seguridad_Role_Lineal3);
//			}
//		}
//	}
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

}
