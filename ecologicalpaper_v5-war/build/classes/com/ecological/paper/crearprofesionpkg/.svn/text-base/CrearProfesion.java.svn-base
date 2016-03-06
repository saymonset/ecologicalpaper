/*
 * CrearProfesion.java
 *
 * Created on July 10, 2007, 2:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.crearprofesionpkg;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.operaciones.ClienteOperaciones;
import com.ecological.paper.cliente.role.ClienteRole_OperacionesPopup;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class CrearProfesion extends ContextSessionRequest {
	// las variables colocarlas siempre asi
	private ServicioDelegado delegado =new ServicioDelegado();
	private Profesion profesion;
	private List<Profesion> profesiones;
	// private List<Role> roles;
	private HttpSession session = super.getSession();
	private String strBuscar;
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
	private Usuario user_logueado;

	/** Creates a new instance of CrearProfesion */
	public CrearProfesion() {
		session = super.getSession();
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual")
				: null;
		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		// variable que viene del menu
		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			profesion = new Profesion();
		} else {
			profesion = (Profesion) session.getAttribute("profesion");
		}

	}

	public String cancelarListar() {
		try {
		super.clearSession(session, confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("CrearProfesion","cancelarLista",e);
		}
		return "listar";
	}

	public String inic_crear() {

		session.setAttribute("crear", true);
		return "crear";
	}

	public String delete() {
		Profesion obj = session.getAttribute("profesion") != null ? (Profesion) session
				.getAttribute("profesion")
				: null;
		if (obj != null) {
			obj = delegado.find(obj.getCodigo());
			List<Usuario> user = delegado.findUsuario_Profesion(obj);
			if (user == null || user.isEmpty()) {
				obj.setStatus(false);
				delegado.edit(obj);
				getProfesiones();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("erro_resgistroenuso")));
			}
		}
		return "";
	}

	public String editProfesion() {
		return "editProfesion";
	}

	public void selectProfesion(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (profesion == null) {
				profesion = new Profesion();
			}
			// buscamos el objeto
			if (id >= 0) {
				profesion.setCodigo(new Long(id));
			}
			// System.out.println("pasa?? id="+id);
			profesion = delegado.find(profesion.getCodigo());
			session.setAttribute("profesion", profesion);

		}
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		try {
			if (("".equals(profesion.getNombre().toString().trim()))
					|| ("".equals(profesion.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
			if (user_logueado != null) {
				profesion.setEmpresa(user_logueado.getEmpresa());
			}
			delegado.addProfesion(profesion);
			session.setAttribute("pagIr", Utilidades.getListarProfesion());
			pagIr = Utilidades.getFinexitoso();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String saveObjeto() {
		String pagIr = "";
		try {
			if (("".equals(profesion.getNombre().toString().trim()))
					|| ("".equals(profesion.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}

			delegado.edit(profesion);
			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarProfesion());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}

		return pagIr;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<Profesion> getProfesiones() {
		user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		if (!super.isEmptyOrNull(getStrBuscar())) {
			profesiones = delegado.findAll_Profesion(user_logueado,
					getStrBuscar());
		} else {
			profesiones = delegado.findAll_Profesion(user_logueado);
		}
		List<Profesion> lista = new ArrayList<Profesion>();
		for (Profesion p : profesiones) {
			p.setDelete(false);
			if (isSwDel()) {
				p.setDelete(true);
			}
			lista.add(p);
		}

		profesiones = lista;

		return profesiones;
	}

	public void setProfesiones(List<Profesion> profesiones) {
		this.profesiones = profesiones;
	}

	public Profesion getProfesion() {
		return profesion;
	}

	public void setProfesion(Profesion profesion) {
		this.profesion = profesion;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToModProfesiones();
		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelProfesiones();
		if (swSuperUsuario) {
			swDel = true;
		}
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		swAdd = seguridadMenu.isToAddProfesiones();
		if (swSuperUsuario) {
			swAdd = true;
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

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}
	

	public String deleteRichfaces() {
		Profesion obj = this.profesion;
		if (obj != null) {
			obj = delegado.find(obj.getCodigo());
			List<Usuario> user = delegado.findUsuario_Profesion(obj);
			if (user == null || user.isEmpty()) {
				obj.setStatus(false);
				delegado.edit(obj);
				getProfesiones();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("erro_resgistroenuso")));
			}
		}
		return "";
	}

	
}
