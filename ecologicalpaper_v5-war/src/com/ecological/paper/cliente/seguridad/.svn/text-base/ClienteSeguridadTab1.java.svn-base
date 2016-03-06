/*
 * ClienteSeguridadTab1.java
 *
 * Created on September 7, 2007, 12:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.seguridad;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.hilos.HiloClienteSeguridad;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.ecological.util.MyHtmlValue;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 

/**
 * 
 * @author simon
 */
public class ClienteSeguridadTab1 extends ContextSessionRequest {
	private ServicioDelegado delegado = new ServicioDelegado();
	private HttpSession session = super.getSession();
	private Tree tree;
	private HtmlInputText inputTextNombre;
	private Object inputTextDescripcion1;
	private MyHtmlValue inputTextDescripcion = new MyHtmlValue();
	private Usuario usuario;
	private HtmlSelectOneMenu selectItemUsuario;
	private DatosCombo datosCombo = new DatosCombo();

	private String selectPermisologiaRolUser;
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	// __________________________________
	private boolean swPrincipalVisible;
	private boolean swUserVisible;
	private boolean swRoleVisible;
	private boolean swMostrarCatalogo;
	private Object[] selectedUsuarios;
	private List visibleUsers;
	private List visibleRole;
	private Object[] selectedRoles;

	private CustomizePageBean customizePageBean;
	// ____________________________________________________________________________
	// cuando se pulsa en el rol, se muestran las operaciones em operacionInROl
	// y
	// los usuarios en usuariosInRol
	private Object[] selectedOperaciones;
	private Object[] selectedOperacionesUsers;
	private Object[] selectedUsuariosInRoles;
	private List operacionesInRol;
	private List usuariosInRol;
	private List operacionesInUsuario;
	private boolean swVerInfRol;
	private String rolSeleccionado;
	private String userSeleccionado;
	private ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
	private Seguridad seguridadTree = new Seguridad();
	private boolean _tab1Visible;
	private boolean _tab2Visible;
	private boolean _tab3Visible;
	private boolean swSaveDatosBasicos;
	private boolean swSuperUsuario;
	private boolean swServidor;
	private boolean browser;
	private boolean swMostrarCuidadoVariable = true;

	/** Creates a new instance of ClienteSeguridad */
	/** Creates a new instance of ClienteSeguridadTab1 */
	public ClienteSeguridadTab1() {
		
		String browser = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("User-Agent");
		
 
		session = super.getSession();
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// OBTENEMOS SEGURIDAD;
		seguridadTree = super.getSeguridadTree(tree);

		if (seguridadTree == null) {
			Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado")
					: null;
			if (user_logueado != null) {
				// -------------------------20090823
				// probando la seguridad a ver que tal, si no pone lento el
				// sistema
				// colocamos la seguridad en session con esta clase y metodo
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
				clienteSeguridad.ponerSeguridadInSession(user_logueado);
//				HiloClienteSeguridad hiloClienteSeguridad = new HiloClienteSeguridad(user_logueado);
//				hiloClienteSeguridad.start();
				// OBTENEMOS SEGURIDAD;
			 
				seguridadTree = super.getSeguridadTree(tree);
				 
			}
			// -------------------------------------------------------------
		}

		inputTextDescripcion = new MyHtmlValue();

		swServidor = super.getMaquinaConectada().equalsIgnoreCase(
				super.getMaquinaServidor());

		inicializa();
		session.setAttribute("mostrarCatalogo", true);
		isSwPrincipalVisible();
		isSwRoleVisible();
		isSwUserVisible();
		
		//BUSCAMOS EL BROWSER EN QUE NAVEGAMOS...
		String useragent = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getHeader("User-Agent"); 
		
		System.out.println(useragent);
		  useragent=useragent.toLowerCase();
          if (useragent.indexOf("opera")!=-1) 
               
          if (useragent.indexOf("netscape")!=-1) 
               
          if (useragent.indexOf("msie")!=-1) {
        	  swMostrarCuidadoVariable=false;
          }
               
          if (useragent.indexOf("firefox")!=-1){
          } 
               
          if (useragent.indexOf("nokia")!=-1)
          if (useragent.indexOf("symbianos")!=-1){
        	  
          }
               


	}

	public void inicializa() {

		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		visibleUsers = new ArrayList();
		visibleRole = new ArrayList();
		if (tree != null && tree.getDescripcion() != null) {
			inputTextDescripcion.setValue(tree.getDescripcion());
		} else {
			inputTextDescripcion.setValue("");
		}

		if (swMostrarCuidadoVariable) {
			// cuando se pulsa en el rol, se muestran las operaciones em
			// operacionInROl y
			// los usuarios en usuariosInRol
			operacionesInRol = new ArrayList();
			usuariosInRol = new ArrayList();
			operacionesInUsuario = new ArrayList();

			List noSeUsa = new ArrayList();
			customizePageBean = new CustomizePageBean();
			visibleUsers.clear();
			noSeUsa.clear();
			datosCombo.llenarUsuariosInOperacionesVisibles(visibleUsers,
					noSeUsa, tree);
			datosCombo.llenarRoledVisiblesenNodo(visibleRole, noSeUsa, tree);
			setVisibleUsers(visibleUsers);
			setVisibleRole(visibleRole);

		}

	}

	public String cancelar() {
		return "";
	}

	public String saveDatosBasicos_action() {
		if (("".equals(inputTextNombre.getValue().toString()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {
			// estoy en el mismo nodo
			Tree treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual")
					: null;
			if (validaHijosMismoNombreError(treeNodoActual, tree)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_tree_existe")));
				return "";
			}

			tree.setNombre(inputTextNombre.getValue().toString());
			tree.setDescripcion(inputTextDescripcion.getValue().toString());
			delegado.edit(tree);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));
		}
		return "";
	}

	public String changeCurrentUsuario() {
		Object[] obj = getSelectedUsuarios();
		SelectItem item = null;
		operacionesInUsuario = new ArrayList();
		boolean swUnoSolo = true;
		for (int i = 0; (i < obj.length && swUnoSolo); i++) {
			Usuario user = (Usuario) obj[i];
			setUserSeleccionado(user.toString());
			Tree treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual")
					: null;
			List enBd = delegado.buscarTodosLasOperacionesTreeUsuario(
					treeNodoActual, user);
			int h = enBd.size();
			for (int k = 0; k < h; k++) {
				Operaciones o = (Operaciones) enBd.get(k);
				item = new SelectItem(o, messages.getString(o.getOperacion()
						.toString()));
				operacionesInUsuario.add(item);
			}
			swUnoSolo = false;
		}
		return null;
	}

	public String changeCurrent() {
		Object[] obj = getSelectedRoles();
		SelectItem item = null;
		operacionesInRol = new ArrayList();
		usuariosInRol = new ArrayList();
		boolean swUnoSolo = true;
		for (int i = 0; (i < obj.length && swUnoSolo); i++) {
			Role role = (Role) obj[i];
			setRolSeleccionado(role.getNombre());

			operacionesInRol = clienteSeguridad
					.operacionesInRolesForTreeOnly(role);
			usuariosInRol = clienteSeguridad.usuariosInRole(role);
			swUnoSolo = false;
		}
		return null;
	}

	/*
	 * public List operacionesInRoles(Role role){ //las operaciones en roles
	 * List enBd = delegado.buscarTodosLasOperacionesRol(role); int h =
	 * enBd.size(); SelectItem item; List operacionesInrol= new ArrayList();
	 * for(int k =0;k<h;k++){ Operaciones o = (Operaciones)enBd.get(k); item =
	 * new SelectItem(o,messages.getString(o.getOperacion().toString()));
	 * operacionesInrol.add(item) ; } return operacionesInrol; }
	 * 
	 * public List usuariosInRole(Role role){ //usuarios en roles List
	 * usuariosInRol= new ArrayList(); List enBd=
	 * delegado.findRoles_Usuario(role); if (enBd!=null){ int h = enBd.size();
	 * SelectItem item; for(int k =0;k<h;k++){ Usuario
	 * user=(Usuario)enBd.get(k); item = new SelectItem(user,user.toString());
	 * usuariosInRol.add(item) ; }
	 * 
	 * } return usuariosInRol; }
	 */
	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HtmlInputText getInputTextNombre() {
		return inputTextNombre;
	}

	public void setInputTextNombre(HtmlInputText inputTextNombre) {
		this.inputTextNombre = inputTextNombre;
	}

	public void setSelectItemUsuario(HtmlSelectOneMenu selectItemUsuario) {
		this.selectItemUsuario = selectItemUsuario;
	}

	public HtmlSelectOneMenu getSelectItemUsuario() {
		return selectItemUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSwPrincipalVisible() {
		boolean habilitar = session.getAttribute("BanderaSeguridad") == null;// (isSwRoleVisible()==false)
																				// &&
																				// (isSwUserVisible()==false);
		swPrincipalVisible = habilitar;

		return swPrincipalVisible;
	}

	public void setSwPrincipalVisible(boolean swPrincipalVisible) {
		this.swPrincipalVisible = swPrincipalVisible;
	}

	public boolean isSwUserVisible() {
		String tab = session.getAttribute("BanderaSeguridad") != null ? (String) session
				.getAttribute("BanderaSeguridad")
				: "";
		swUserVisible = Utilidades.getTab1().equals(tab);
		return swUserVisible;
	}

	public void setSwUserVisible(boolean swUserVisible) {
		this.swUserVisible = swUserVisible;
	}

	public boolean isSwRoleVisible() {
		String tab = session.getAttribute("BanderaSeguridad") != null ? (String) session
				.getAttribute("BanderaSeguridad")
				: "";
		swRoleVisible = Utilidades.getTab2().equals(tab);

		return swRoleVisible;
	}

	public void setSwRoleVisible(boolean swRoleVisible) {
		this.swRoleVisible = swRoleVisible;
	}

	public boolean isSwMostrarCatalogo() {
		// la principal v entana que te muesytran usuarios y roles que ya se han
		// escojidos
		swMostrarCatalogo = session.getAttribute("mostrarCatalogo") != null;
		return swMostrarCatalogo;

	}

	public void setSwMostrarCatalogo(boolean swMostrarCatalogo) {
		this.swMostrarCatalogo = swMostrarCatalogo;
	}

	public Object[] getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(Object[] selectedUsuarios) {
		this.selectedUsuarios = selectedUsuarios;
	}

	public List getVisibleUsers() {
		return visibleUsers;
	}

	public void setVisibleUsers(List visibleUsers) {
		this.visibleUsers = visibleUsers;
	}

	public List getVisibleRole() {
		return visibleRole;
	}

	public void setVisibleRole(List visibleRole) {
		this.visibleRole = visibleRole;
	}

	public Object[] getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(Object[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public List getOperacionesInRol() {
		return operacionesInRol;
	}

	public void setOperacionesInRol(List operacionesInRol) {
		this.operacionesInRol = operacionesInRol;
	}

	public Object[] getSelectedOperaciones() {
		return selectedOperaciones;
	}

	public void setSelectedOperaciones(Object[] selectedOperaciones) {
		this.selectedOperaciones = selectedOperaciones;
	}

	public Object[] getSelectedUsuariosInRoles() {
		return selectedUsuariosInRoles;
	}

	public void setSelectedUsuariosInRoles(Object[] selectedUsuariosInRoles) {
		this.selectedUsuariosInRoles = selectedUsuariosInRoles;
	}

	public boolean isSwVerInfRol() {
		return swVerInfRol;
	}

	public void setSwVerInfRol(boolean swVerInfRol) {
		this.swVerInfRol = swVerInfRol;
	}

	public String getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(String rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public Object[] getSelectedOperacionesUsers() {
		return selectedOperacionesUsers;
	}

	public void setSelectedOperacionesUsers(Object[] selectedOperacionesUsers) {
		this.selectedOperacionesUsers = selectedOperacionesUsers;
	}

	public String getUserSeleccionado() {
		return userSeleccionado;
	}

	public void setUserSeleccionado(String userSeleccionado) {
		this.userSeleccionado = userSeleccionado;
	}

	public List getUsuariosInRol() {
		return usuariosInRol;
	}

	public void setUsuariosInRol(List usuariosInRol) {
		this.usuariosInRol = usuariosInRol;
	}

	public List getOperacionesInUsuario() {
		return operacionesInUsuario;
	}

	public void setOperacionesInUsuario(List operacionesInUsuario) {
		this.operacionesInUsuario = operacionesInUsuario;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public boolean isTab1Visible() {
		_tab1Visible = seguridadTree.isToSaveDataBasic();
		if (swSuperUsuario) {
			_tab1Visible = true;
		}
		return _tab1Visible;
	}

	public void setTab1Visible(boolean tab1Visible) {
		this._tab1Visible = tab1Visible;
	}

	public boolean isTab2Visible() {
		_tab2Visible = seguridadTree.isToGivePermUser();
		if (swSuperUsuario) {
			_tab2Visible = true;
		}
		return _tab2Visible;
	}

	public void setTab2Visible(boolean tab2Visible) {

		this._tab2Visible = tab2Visible;
	}

	public boolean isTab3Visible() {
		_tab3Visible = seguridadTree.isToActivePermGroup();
		if (swSuperUsuario) {
			_tab3Visible = true;
		}
		return _tab3Visible;
	}

	public void setTab3Visible(boolean tab3Visible) {
		this._tab3Visible = tab3Visible;
	}

	public MyHtmlValue getInputTextDescripcion() {
		return inputTextDescripcion;
	}

	public void setInputTextDescripcion(MyHtmlValue inputTextDescripcion) {
		this.inputTextDescripcion = inputTextDescripcion;
	}

 

	public boolean isSwServidor() {
		return swServidor;
	}

	public void setSwServidor(boolean swServidor) {
		this.swServidor = swServidor;
	}

	public boolean isSwMostrarCuidadoVariable() {
		return swMostrarCuidadoVariable;
	}

	public void setSwMostrarCuidadoVariable(boolean swMostrarCuidadoVariable) {
		this.swMostrarCuidadoVariable = swMostrarCuidadoVariable;
	}

	public boolean isBrowser() {
		return browser;
	}

	public void setBrowser(boolean browser) {
		this.browser = browser;
	}

}
