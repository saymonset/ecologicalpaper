package com.ecological.paper.cliente.documentos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.subversion.SvnExtension;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class SvnExtensionCliente extends ClientePadreDocumento {
	private SvnExtension svnExtension;
	private List<SvnExtension> svnExtensiones;
	private ServicioDelegado delegado = new ServicioDelegado();
	private String doc_estados;
	private String strBuscar;
	// SEGURIDAD
	private Seguridad seguridadMenu = new Seguridad();
	private boolean swMod;
	private boolean swDel;
	private boolean swAdd;
	private Tree treeNodoActual = null;
	private boolean swSuperUsuario;

	/** Creates a new instance of ClienteDocumentoEstado */
	/** Creates a new instance of ClienteDocumentoTipo */
	public SvnExtensionCliente() {
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		// OBTENEMOS SEGURIDAD;

		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);
		// System.out.println("........------------------");
		// System.out.println("swMod=seguridadMenu.isToDelTipoDocumentos()="+seguridadMenu.isToDelTipoDocumentos());
		// System.out.println("........------------------");

		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			svnExtension = new SvnExtension();
		} else {
			svnExtension = session.getAttribute("svnExtension") != null ? (SvnExtension) session
					.getAttribute("svnExtension") : new SvnExtension();
			// IniOperaciones();
		}
	}

	public String crear_nuevo() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public String create() {
		String pagIr = "";
		try {
			
			if (isEmptyOrNull(svnExtension.getAnidacion0()) && 
					isEmptyOrNull(svnExtension.getAnidacion1())){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if ("".equals(svnExtension.getExt().toString().trim()))
					  {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(svnExtension.getExt())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}

		 
			 
			delegado.create(svnExtension);
			session.setAttribute("pagIr", Utilidades.getListarsvnextension());
			pagIr = Utilidades.getFinexitoso();
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("operacion_exitosa")));
			 */

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String save() {
		String pagIr = "";
		if (isEmptyOrNull(svnExtension.getAnidacion0()) && 
				isEmptyOrNull(svnExtension.getAnidacion1())){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_camposvacios")));
			return "";
		}
		if ("".equals(svnExtension.getExt().toString().trim()))
				  {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("error_camposvacios")));
			return "";
		}
		if (!esValidaCadena(svnExtension.getExt())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("caracteresinvalidos")));
			return "failed";
		}
			delegado.edit(svnExtension);
			session.setAttribute("pagIr", Utilidades.getListarsvnextension());
			pagIr = Utilidades.getFinexitoso();
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("operacion_exitosa")));
			 */

		 
		return pagIr;
	}

	public String edit() {
		return "edit";
	}

	public String delete() {
		SvnExtension doc = session.getAttribute("svnExtension") != null ? (SvnExtension) session
				.getAttribute("svnExtension") : null;
		if (doc != null) {
			doc = delegado.find(doc);
			doc.setStatus(false);
			delegado.edit(doc);
			getLista();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));
		}
		return "";
	}

	public String cancelarLista() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentoTipo", "cancelarLista", e);
		}
		return "menu";
	}

	public String cancelar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentoTipo", "cancelar", e);
		}

		return "listar";
	}

	public void selectId(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (svnExtension == null) {
				svnExtension = new SvnExtension();
			}
			// buscamos el role para editar
			if (id >= 0) {
				svnExtension.setCodigo(new Long(id));
			}

			svnExtension = delegado.find(svnExtension);
			session.setAttribute("svnExtension", svnExtension);

		}
	}

	 

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<SvnExtension> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
			svnExtensiones = delegado.findAllSvnExtension(
					getStrBuscar());
		List<SvnExtension> lista = new ArrayList<SvnExtension>();
		for (SvnExtension svnExtension : svnExtensiones) {
			svnExtension.setDelete(false);
			if (isSwDel()) {
				svnExtension.setDelete(true);
			}
			lista.add(svnExtension);
		}

		svnExtensiones = lista;
		return svnExtensiones;
	}

	public void setDoc_tipos(List<SvnExtension> svnExtensiones) {
		this.svnExtensiones = svnExtensiones;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
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
		// System.out.println("swMod=seguridadMenu.isToDelTipoDocumentos()="+seguridadMenu.isToDelTipoDocumentos());
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

	public SvnExtension getSvnExtension() {
		return svnExtension;
	}

	public void setSvnExtension(SvnExtension svnExtension) {
		this.svnExtension = svnExtension;
	}

}
