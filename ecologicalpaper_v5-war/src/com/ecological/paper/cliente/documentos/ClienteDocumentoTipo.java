/*
 * ClienteDocumentoEstado.java
 *
 * Created on July 25, 2007, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

/**
 * 
 * @author simon
 */
public class ClienteDocumentoTipo extends ClientePadreDocumento {
	private Doc_tipo doc_tipo;
	private List<Doc_tipo> doc_tipos;
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
	public ClienteDocumentoTipo() {
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
			doc_tipo = new Doc_tipo();
		} else {
			doc_tipo = session.getAttribute("doc_tipo") != null ? (Doc_tipo) session
					.getAttribute("doc_tipo") : new Doc_tipo();
			if (doc_tipo != null && doc_tipo.getFormatoTipoDoc()!=null) {
				if (Utilidades.getFormatoTipoDoc()
						- doc_tipo.getFormatoTipoDoc() == 0) {
					doc_tipo.setSwTipoFormato(true);
				}
			}

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
			if (("".equals(doc_tipo.getNombre().toString().trim()))
					|| ("".equals(doc_tipo.getDescripcion().toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(doc_tipo.getNombre())
					|| (!esValidaCadena(doc_tipo.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
			try {
				Usuario user_logueado = (Usuario) session
						.getAttribute("user_logueado");

				if (doc_tipo.isSwTipoFormato()) {
					doc_tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());
				} else {
					doc_tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
				}

				if (user_logueado != null) {
					doc_tipo.setEmpresa(user_logueado.getEmpresa());
				}
				delegado.createTipoDoc(doc_tipo);
				session.setAttribute("pagIr", Utilidades.getListardocTipo());
				pagIr = Utilidades.getFinexitoso();
				/*
				 * FacesContext.getCurrentInstance().addMessage( null, new
				 * FacesMessage(messages .getString("operacion_exitosa")));
				 */

			} catch (EcologicaExcepciones ex) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("error_intentar")
								+ ex));
				ex.printStackTrace();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String save() {
		String pagIr = "";
		if (("".equals(doc_tipo.getNombre().toString().trim()))
				|| ("".equals(doc_tipo.getDescripcion().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {
			try {
				if (!esValidaCadena(doc_tipo.getNombre())
						|| (!esValidaCadena(doc_tipo.getDescripcion()))) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
					return "failed";
				}

				if (doc_tipo.isSwTipoFormato()) {
					doc_tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());
				} else {
					doc_tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
				}

				delegado.editTipoDoc(doc_tipo);
				session.setAttribute("pagIr", Utilidades.getListardocTipo());
				pagIr = Utilidades.getFinexitoso();
				/*
				 * FacesContext.getCurrentInstance().addMessage( null, new
				 * FacesMessage(messages .getString("operacion_exitosa")));
				 */
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

		}
		return pagIr;
	}

	public String edit() {
		return "edit";
	}

	public String delete() {
		Doc_tipo doc = session.getAttribute("doc_tipo") != null ? (Doc_tipo) session
				.getAttribute("doc_tipo") : null;
		if (doc != null) {
			doc = delegado.findTipoDoc(doc);
			List<Doc_maestro> maestro = delegado.findDoc_Maestr_Doc_Tipo(doc);
			if (maestro == null || maestro.isEmpty()) {
				doc.setStatus(false);
				try {
					delegado.editTipoDoc(doc);
					getDoc_tipos();
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("operacion_exitosa")));
				} catch (EcologicaExcepciones ex) {
					ex.printStackTrace();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("erro_resgistroenuso")));
			}
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
			if (doc_tipo == null) {
				doc_tipo = new Doc_tipo();
			}
			// buscamos el role para editar
			if (id >= 0) {
				doc_tipo.setCodigo(new Long(id));
			}

			doc_tipo = delegado.findTipoDoc(doc_tipo);
			session.setAttribute("doc_tipo", doc_tipo);

		}
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<Doc_tipo> getDoc_tipos() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (!super.isEmptyOrNull(getStrBuscar())) {
			doc_tipos = delegado
					.findAllDoc_tipos(user_logueado, getStrBuscar());
		} else {
			doc_tipos = delegado.findAllDoc_tipos(user_logueado);
		}

		List<Doc_tipo> lista = new ArrayList<Doc_tipo>();
		for (Doc_tipo doc_tipo : doc_tipos) {
			doc_tipo.setDelete(false);

			if (Utilidades.getFormatoTipoDoc() - doc_tipo.getFormatoTipoDoc() == 0) {
				doc_tipo.setSwTipoFormato(true);
			}

			if (isSwDel()) {
				doc_tipo.setDelete(true);
			}

			// codigo tipo formato y registros.. que sera la base para crear
			// los demas tipos formatos y registros
			if ((doc_tipo.getCodigo() - Utilidades.getRegistroTipoDoc() != 0)) {
				if ((doc_tipo.getCodigo() - Utilidades.getFormatoTipoDoc() != 0)) {
					if ((doc_tipo != null
							&& doc_tipo.getFormatoTipoDoc() != null && doc_tipo
							.getFormatoTipoDoc()
							- Utilidades.getFlujoparalelotipodoc() != 0)) {
						lista.add(doc_tipo);

						// no pasa nada

						// no pasa nada
					}

				}

			}
		}

		doc_tipos = lista;
		return doc_tipos;
	}

	public void setDoc_tipos(List<Doc_tipo> doc_tipos) {
		this.doc_tipos = doc_tipos;
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
			System.out.println("swDel=" + swDel);
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

}
