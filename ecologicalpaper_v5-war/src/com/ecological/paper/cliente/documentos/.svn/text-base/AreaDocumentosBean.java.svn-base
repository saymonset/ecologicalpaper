package com.ecological.paper.cliente.documentos;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
 
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;

public class AreaDocumentosBean extends ContextSessionRequest {
	// las variables colocarlas siempre asi
	private ServicioDelegado delegado =new ServicioDelegado();
	private AreaDocumentos areaDocumento;
	private List<AreaDocumentos> areaDocumentosLsta;
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

	/** Creates a new instance of areaDocumentosBean */
	public AreaDocumentosBean() {
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
			areaDocumento = new AreaDocumentos();
		} else {
			areaDocumento = (AreaDocumentos) session.getAttribute("areaDocumento");
		}

	}

	public String cancelarListar() {
		try {
		super.clearSession(session, confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("areaDocumentosBean","cancelarLista",e);
		}
		return "listar";
	}

	public String inic_crear() {

		session.setAttribute("crear", true);
		return "crear";
	}

	public String delete() {
		AreaDocumentos obj = session.getAttribute("areaDocumento") != null ? (AreaDocumentos) session
				.getAttribute("areaDocumento")
				: null;
		if (obj != null) {
			obj = delegado.find(obj);
			if (!delegado.findExisteAreaDocumentosDocDetallesFlowParalelo(obj)) {
				obj.setStatus(false);
				delegado.edit(obj);
				getAreaDocumentosLsta();
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

	public void selectObjeto(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		
		if ((component == null)
				|| (component.getValue().toString() == null)) {
			component = (UIParameter) event.getComponent()
					.findComponent("editId2");
		}
		
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (areaDocumento == null) {
				areaDocumento = new AreaDocumentos();
			}
			// buscamos el objeto
			if (id >= 0) {
				areaDocumento.setCodigo(new Long(id));
			}
			// System.out.println("pasa?? id="+id);
			areaDocumento = delegado.find(areaDocumento);
			 
			session.setAttribute("areaDocumento", areaDocumento);

		}
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		try {
			if (("".equals(areaDocumento.getNombre().toString().trim()))
					|| ("".equals(areaDocumento.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
			if (user_logueado != null) {
				areaDocumento.setEmpresa(user_logueado.getEmpresa());
			}
			delegado.create(areaDocumento);
			session.setAttribute("pagIr", Utilidades.getListarareadocumentos());
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
			if (("".equals(areaDocumento.getNombre().toString().trim()))
					|| ("".equals(areaDocumento.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}

			delegado.edit(areaDocumento);
			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarareadocumentos());
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

	public List<AreaDocumentos> getAreaDocumentosLsta() {
		user_logueado = session.getAttribute("user_logueado")!=null?(Usuario) session.getAttribute("user_logueado"):null;
		if (!super.isEmptyOrNull(getStrBuscar())) {
			areaDocumentosLsta = delegado.findAllAreaDocumentos(user_logueado,
					getStrBuscar());
		} else {
			areaDocumentosLsta = delegado.findAllAreaDocumentos(user_logueado);
		}
		List<AreaDocumentos> lista = new ArrayList<AreaDocumentos>();
		for (AreaDocumentos p : areaDocumentosLsta) {
			p.setDelete(false);
			if (isSwDel()) {
				p.setDelete(true);
			}
			lista.add(p);
		}

		areaDocumentosLsta = lista;

		return areaDocumentosLsta;
	}

 

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToListarArea();
		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToListarArea();
		if (swSuperUsuario) {
			swDel = true;
		}
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		swAdd = seguridadMenu.isToListarArea();
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

	public AreaDocumentos getAreaDocumento() {
		return areaDocumento;
	}

	public void setAreaDocumento(AreaDocumentos areaDocumento) {
		this.areaDocumento = areaDocumento;
	}

	public void setAreaDocumentosLst(List<AreaDocumentos> areaDocumentosLst) {
		this.areaDocumentosLsta = areaDocumentosLst;
	}
}
