package com.ecological.paper.cliente.documentos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class SvnUrlBaseCliente extends ClientePadreDocumento {
	private SvnUrlBase svnUrlBase;
	private List<SvnUrlBase> svnUrlBases;
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
	public SvnUrlBaseCliente() {
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
			svnUrlBase = new SvnUrlBase();
		} else {
			svnUrlBase = session.getAttribute("svnUrlBase") != null ? (SvnUrlBase) session
					.getAttribute("svnUrlBase") : new SvnUrlBase();
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
			if ((isEmptyOrNull(svnUrlBase.getNombre().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");

			if (user_logueado != null) {
				svnUrlBase.setEmpresa(user_logueado.getEmpresa());
				List lista = delegado.findAllSvnUrlBase(user_logueado,
						svnUrlBase.getNombre().toString().trim());
				if (lista != null && lista.size() > 0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_userYaExiste")));
					return "";
				}
			}

			/*
			 * if (!esValidaCadena(svnUrlBase.getNombre()) ||
			 * (!esValidaCadena(svnUrlBase.getTipoAmbiente()))) {
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("caracteresinvalidos"))); return
			 * "failed"; }
			 */

			delegado.create(svnUrlBase);
			session.setAttribute("pagIr", Utilidades.getListarsvnurlbase());
			pagIr = Utilidades.getFinexitoso();
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("operacion_exitosa")));
			 */

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String save() {
		String pagIr = "";
		if (("".equals(svnUrlBase.getNombre().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {

			/*
			 * if (!esValidaCadena(svnUrlBase.getNombre()) ||
			 * (!esValidaCadena(svnUrlBase.getTipoAmbiente()))) {
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("caracteresinvalidos"))); return
			 * "failed"; }
			 */
			delegado.edit(svnUrlBase);
			session.setAttribute("pagIr", Utilidades.getListarsvnurlbase());
			pagIr = Utilidades.getFinexitoso();
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(messages .getString("operacion_exitosa")));
			 */

		}
		return pagIr;
	}

	public String edit() {
		return "edit";
	}

	public String delete() {
		SvnUrlBase doc = session.getAttribute("svnUrlBase") != null ? (SvnUrlBase) session
				.getAttribute("svnUrlBase") : null;
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
			if (svnUrlBase == null) {
				svnUrlBase = new SvnUrlBase();
			}
			// buscamos el role para editar
			if (id >= 0) {
				svnUrlBase.setCodigo(new Long(id));
			}

			svnUrlBase = delegado.find(svnUrlBase);
			session.setAttribute("svnUrlBase", svnUrlBase);

		}
	}

	public SvnUrlBase getSvnUrlBase() {
		return svnUrlBase;
	}

	public void setDoc_tipo(SvnUrlBase svnUrlBase) {
		this.svnUrlBase = svnUrlBase;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<SvnUrlBase> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (!super.isEmptyOrNull(getStrBuscar())) {
			svnUrlBases = delegado.findAllSvnUrlBase(user_logueado,
					getStrBuscar());
		} else {
			svnUrlBases = delegado.findAllSvnUrlBase(user_logueado, null);
		}

		List<SvnUrlBase> lista = new ArrayList<SvnUrlBase>();
		for (SvnUrlBase svnUrlBase : svnUrlBases) {
			svnUrlBase.setDelete(false);
			if (isSwDel()) {
				svnUrlBase.setDelete(true);
			}
			lista.add(svnUrlBase);
		}

		svnUrlBases = lista;
		return svnUrlBases;
	}

	public void setDoc_tipos(List<SvnUrlBase> svnUrlBases) {
		this.svnUrlBases = svnUrlBases;
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
	
	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		this.svnUrlBase = svnUrlBase;
	}


}
