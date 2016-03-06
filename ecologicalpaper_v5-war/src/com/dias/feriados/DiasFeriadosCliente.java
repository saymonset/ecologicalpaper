/*
 * DiasFeriadosCliente.java
 *
 * Created on August 14, 2008, 10:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.dias.feriados;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.paper.cliente.documentos.ClientePadreDocumento;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * 
 * @author simon
 */
public class DiasFeriadosCliente extends ClientePadreDocumento {
	private DiasFeriadosBean diasFeriadosBean;
	private List<DiasFeriadosBean> diasFeriadosBeans;
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
	public DiasFeriadosCliente() {
		session = super.getSession();
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual")
				: null;
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
			diasFeriadosBean = new DiasFeriadosBean();
		} else {
			diasFeriadosBean = session.getAttribute("diasFeriadosBean") != null ? (DiasFeriadosBean) session
					.getAttribute("diasFeriadosBean")
					: new DiasFeriadosBean();
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

			if ("".equals(diasFeriadosBean.getDescripcion().toString().trim())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(diasFeriadosBean.getDescripcion() )) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
			boolean fechaFutuira = false;
			if (diasFeriadosBean.getFechaonly() == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			
			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");
			if (user_logueado != null) {
				diasFeriadosBean.setEmpresa(user_logueado.getEmpresa());
			}

			delegado.create(diasFeriadosBean);
			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarDiasFeriados());

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}
		return pagIr;
	}

	public String save() {
		String pagIr = "";
		try {
			if ("".equals(diasFeriadosBean.getDescripcion().toString().trim())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}

			delegado.edit(diasFeriadosBean);

			pagIr = Utilidades.getFinexitoso();
			session.setAttribute("pagIr", Utilidades.getListarDiasFeriados());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));
		}

		return pagIr;
	}

	public String edit() {
		return "edit";
	}

	public String delete() {
		DiasFeriadosBean doc = session.getAttribute("diasFeriadosBean") != null ? (DiasFeriadosBean) session
				.getAttribute("diasFeriadosBean")
				: null;
		if (doc != null) {
			doc = delegado.find(doc);
			delegado.destroy(doc);
		}
		return "";
	}

	public String cancelarLista() {
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("DiasFeriadosCliente", "cancelarLista", e);
		}

		return "listar";
	}

	public String cancelar() {
		try {
			super.clearSession(session, confmessages
					.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("DiasFeriadosCliente", "cancelar", e);
		}

		return "listar";
	}

	public void selectId(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (diasFeriadosBean == null) {
				diasFeriadosBean = new DiasFeriadosBean();
			}
			// buscamos el role para editar
			if (id >= 0) {
				diasFeriadosBean.setCodigo(new Long(id));
			}

			diasFeriadosBean = delegado.find(diasFeriadosBean);
			session.setAttribute("diasFeriadosBean", diasFeriadosBean);

		}
	}

	public void setDoc_tipo(DiasFeriadosBean diasFeriadosBean) {
		this.diasFeriadosBean = diasFeriadosBean;
	}

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToModDiaFeriado();

		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelDiaFeriado();
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
		swAdd = seguridadMenu.isToAddDiaFeriado();
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

	public DiasFeriadosBean getDiasFeriadosBean() {
		return diasFeriadosBean;
	}

	public void setDiasFeriadosBean(DiasFeriadosBean diasFeriadosBean) {
		this.diasFeriadosBean = diasFeriadosBean;
	}

	public List<DiasFeriadosBean> getDiasFeriadosBeans() {
		session = super.getSession();
		Usuario user_logueado = session != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		List<DiasFeriadosBean> diasF = new ArrayList();

		if (user_logueado != null) {
			diasFeriadosBeans = delegado
					.find_allDiasFeriadosBean(user_logueado);

			if (diasFeriadosBeans != null && !diasFeriadosBeans.isEmpty()) {
				for (DiasFeriadosBean d : diasFeriadosBeans) {
					d.setFechaStr(Utilidades.sdfShowWithoutHour.format(d
							.getFechaonly()));
					diasF.add(d);
				}
			}
		}
		diasFeriadosBeans = diasF;
		return diasFeriadosBeans;
	}

	public void setDiasFeriadosBeans(List<DiasFeriadosBean> diasFeriadosBeans) {
		this.diasFeriadosBeans = diasFeriadosBeans;
	}

}
