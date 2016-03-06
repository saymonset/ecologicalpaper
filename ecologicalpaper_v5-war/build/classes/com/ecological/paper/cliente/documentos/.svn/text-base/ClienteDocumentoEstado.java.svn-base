/*
 * ClienteDocumentoEstado.java
 *
 * Created on July 25, 2007, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.documentos;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.util.Utilidades;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * 
 * @author simon
 */
public class ClienteDocumentoEstado extends ClientePadreDocumento {
	private ServicioDelegado delegado = new ServicioDelegado();
	private Doc_estado doc_estado;
	private List<Doc_estado> doc_estados;
	private String strBuscar;

	/** Creates a new instance of ClienteDocumentoEstado */
	public ClienteDocumentoEstado() {
		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			doc_estado = new Doc_estado();
		} else {
			doc_estado = session.getAttribute("doc_estado") != null ? (Doc_estado) session
					.getAttribute("doc_estado")
					: new Doc_estado();
			// IniOperaciones();
		}
	}

	public String crear_nuevo() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public String create() {
		try {
			if (("".equals(doc_estado.getNombre().toString().trim()))
					|| (""
							.equals(doc_estado.getDescripcion().toString()
									.trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(doc_estado.getNombre())
					|| (!esValidaCadena(doc_estado.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
			try {
				delegado.createDocEstado(doc_estado);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("operacion_exitosa")));

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
		return "";
	}

	public String save() {
		if (("".equals(doc_estado.getNombre().toString().trim()))
				|| ("".equals(doc_estado.getDescripcion().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {
			try {
				if (!esValidaCadena(doc_estado.getNombre())
						|| (!esValidaCadena(doc_estado.getDescripcion()))) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
					return "failed";
				}
				delegado.editDocEstado(doc_estado);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("operacion_exitosa")));
		}
		return "";
	}

	public Doc_estado getDoc_estado() {
		return doc_estado;
	}

	public void setDoc_estado(Doc_estado doc_estado) {
		this.doc_estado = doc_estado;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<Doc_estado> getDoc_estados() {
		if (!super.isEmptyOrNull(getStrBuscar())) {
			doc_estados = delegado.findAllDoc_estados(getStrBuscar());
		} else {
			doc_estados = delegado.findAllDoc_estados();
		}

		return doc_estados;
	}

	public void setDoc_estados(List<Doc_estado> doc_estados) {
		this.doc_estados = doc_estados;
	}

	public String edit() {
		return "edit";
	}

	public String cancelarEdit() {
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentoEstado", "cancelarEdit", e);
		}

		return "listar";
	}

	public String cancelarCrear() {
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {

		}

		return "listar";
	}

	public String cancelarLista() {
		// ClienteDocumentoEstado
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ClienteDocumentoEstado", "cancelarLista", e);
		}

		return "menu";
	}

	public void selectId(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (doc_estado == null) {
				doc_estado = new Doc_estado();
			}
			// buscamos el role para editar
			if (id >= 0) {
				doc_estado.setCodigo(new Long(id));
			}

			doc_estado = delegado.findDocEstado(doc_estado);
			session.setAttribute("doc_estado", doc_estado);

			/*
			 * if ((visibleItems==null)||(invisibleItems==null)){ visibleItems =
			 * new ArrayList(); invisibleItems = new ArrayList(); }
			 * clienteOperaciones
			 * .llenarOperacionesVisibles(visibleItems,invisibleItems,role);
			 * session.setAttribute("visibleItems",visibleItems);
			 * session.setAttribute("invisibleItems",invisibleItems);
			 */
			// IniOperaciones();
		}
	}

}
