package com.ecological.extensionesfilecliente;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.Utilidades;

public class ExtensionFileHijosCliente extends ContextSessionRequest {
	// las variables colocarlas siempre asi
	private HtmlSelectOneMenu selectPais;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ExtensionFileHijos extensionFileHijos;
	private ExtensionFile extensionFile;
	private List<ExtensionFileHijos> extensionFileHijosLst;
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

	/** Creates a new instance of CrearProfesion */
	public ExtensionFileHijosCliente() {
		session = super.getSession();
		// _________________________________________
		// un truquillo para que no me salga null el extensionFile
		extensionFile = (ExtensionFile) session.getAttribute("extensionFile");
		if (extensionFile == null) {
			List<ExtensionFile> extensionFiles = delegado
					.find_allExtensionFile();
			for (ExtensionFile p : extensionFiles) {
				extensionFile = p;
				session.setAttribute("extensionFile", p);
				break;
			}
		}
		// _________________________________________
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
			setExtensionFileHijos(new ExtensionFileHijos());
			extensionFileHijos.setExtensionFile(extensionFile);
		} else {
			setExtensionFileHijos((ExtensionFileHijos) session
					.getAttribute("extensionFileHijos"));
			if (extensionFileHijos != null) {
				extensionFileHijos.setExtensionFile(extensionFile);
			}
		}

	}

	public String cancelar() {
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("CrearEstado", "cancelar", e);
		}

		return "listarmenu";
	}

	public String cancelarListar() {
		super.clearSession(session, confmessages.getString("usuarioSession"));
		return "listar";
	}

	public String inic_crear() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public String delete() {
		ExtensionFileHijos obj = session.getAttribute("extensionFileHijos") != null ? (ExtensionFileHijos) session
				.getAttribute("extensionFileHijos")
				: null;
		if (obj != null) {
			extensionFileHijos = new ExtensionFileHijos();
			extensionFileHijos.setCodigo(obj.getCodigo());
			extensionFileHijos = delegado.find(extensionFileHijos);
			if (extensionFileHijos != null) {

				extensionFileHijos.setStatus(false);
				delegado.edit(extensionFileHijos);

				getExtensionFileHijosLst();
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

	public String edit() {
		return "edit";
	}

	public void selection(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (extensionFileHijos == null) {
				extensionFileHijos = new ExtensionFileHijos();
			}
			// buscamos el objeto
			if (id >= 0) {
				extensionFileHijos.setCodigo(new Long(id));
			}
			// System.out.println("pasa?? id="+id);
			extensionFileHijos = delegado.find(extensionFileHijos);
			session.setAttribute("extensionFileHijos", extensionFileHijos);

		}
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		try {
			if (("".equals(extensionFileHijos.getExtension().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			
			if (extensionFileHijos.getExtension().indexOf(".")!=-1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_sinpunto")));
				return "";
			}

			delegado.create(extensionFileHijos);
			session.setAttribute("pagIr", Utilidades
					.getListarextensionfilehijos());
			pagIr = Utilidades.getFinexitoso();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}

		return pagIr;
	}

	public String saveObjeto() {
		String pagIr = "";
		if (("".equals(extensionFileHijos.getExtension().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}

		delegado.edit(extensionFileHijos);
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", Utilidades.getListarextensionfilehijos());

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

	public void colocarInSession() {
		session.setAttribute("extensionFile", extensionFile);

	}

	public List<ExtensionFileHijos> getExtensionFileHijosLst() {
		extensionFileHijosLst = new ArrayList<ExtensionFileHijos>();
		extensionFile = (ExtensionFile) session.getAttribute("extensionFile");
		if (extensionFile != null) {
			extensionFileHijosLst = delegado
					.find_allExtensionFile(extensionFile);
			if (extensionFileHijosLst == null) {
				extensionFileHijosLst = new ArrayList<ExtensionFileHijos>();
			} else {
				List<ExtensionFileHijos> lista = new ArrayList<ExtensionFileHijos>();
				for (ExtensionFileHijos p : extensionFileHijosLst) {
					p.setDelete(false);
					if (isSwDel()) {
						p.setDelete(true);
					}
					lista.add(p);
				}
				extensionFileHijosLst = lista;
			}

		}
		return extensionFileHijosLst;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		//swMod = seguridadMenu.isToModUsuario();
		swMod = seguridadMenu.isToModTipoDocumentos();
		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelTipoDocumentos();
		//swDel = seguridadMenu.isToDelUsuario();
		if (swSuperUsuario) {
			swDel = true;
		}
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		swAdd = seguridadMenu.isToAddTipoDocumentos();
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

	public ExtensionFileHijos getExtensionFileHijos() {
		return extensionFileHijos;
	}

	public void setExtensionFileHijos(ExtensionFileHijos extensionFileHijos) {
		this.extensionFileHijos = extensionFileHijos;
	}

	public void setEstados(List<ExtensionFileHijos> extensionFileHijosLst) {
		this.extensionFileHijosLst = extensionFileHijosLst;
	}

	public HtmlSelectOneMenu getSelectPais() {
		return selectPais;
	}

	public void setSelectPais(HtmlSelectOneMenu selectPais) {
		this.selectPais = selectPais;
	}

	public ExtensionFile getExtensionFile() {
		extensionFile = (ExtensionFile) session.getAttribute("extensionFile");
		return extensionFile;
	}

	public void setExtensionFile(ExtensionFile extensionFile) {
		session.setAttribute("extensionFile", extensionFile);
		this.extensionFile = extensionFile;
	}

}
