package com.ecological.paper.cliente.documentos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.subversion.SvnModulo;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class SvnModuloCliente2 extends ClientePadreDocumento {
	private HtmlSelectOneMenu selectOneMenu1;
	private HtmlSelectOneMenu selectOneMenu2;
	private HtmlSelectOneMenu selectOneMenu3;
	private SvnModulo svnModulo;
	private List<SvnModulo> svnModules;
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
	private DatosCombo datosCombo = new DatosCombo();
	private SvnUrlBase svnUrlBase;
	private SvnNombreAplicacion svnNombreAplicacion;
	private SvnTipoAmbiente svnTipoAmbiente;
	private List<SelectItem> allSvnTipoAmbientes;
	private List<SelectItem> allSvnNombreAplicacion;
	private SvnNombreAplicacion svnnombreaplicacion;

	/** Creates a new instance of ClienteDocumentoEstado */
	/** Creates a new instance of ClienteDocumentoTipo */
	public SvnModuloCliente2() {
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
			svnModulo = new SvnModulo();
		} else {
			svnModulo = session.getAttribute("svnModulo") != null ? (SvnModulo) session
					.getAttribute("svnModulo") : new SvnModulo();
					if (svnModulo!=null && svnModulo.getSvnTipoAmbiente()!=null){
						svnUrlBase=svnModulo.getSvnTipoAmbiente().getSvnnombreaplicacion().getSvnUrlBase();
						svnNombreAplicacion=svnModulo.getSvnTipoAmbiente().getSvnnombreaplicacion();
					}
			/*
			 * if (svnModulo == null) { svnModulo = new SvnModulo(); } else {
			 * svnUrlBase = svnModulo.getSvnNombreAplicacion() != null ?
			 * svnModulo .getSvnNombreAplicacion().getSvnUrlBase() : null; }
			 */

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

			if (isEmptyOrNull(svnModulo.getPathCorto1())) {
				if (isEmptyOrNull(svnModulo.getPathCorto2())) {
					if (isEmptyOrNull(svnModulo.getPathCorto3())) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("error_camposvacios")));
						return "";
					}
				}
			}

			if (svnModulo.getSvnTipoAmbiente() == null) {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}

			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");
			
			/*if (user_logueado != null) {
				List lista = delegado.findAllSvnNombreAplicacion(user_logueado,
						svnNombreAplicacion.getNombre().toString().trim());
				if (lista != null && lista.size() > 0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_userYaExiste")));
					return "";
				}
			}*/


			delegado.create(svnModulo);
			session.setAttribute("pagIr", Utilidades.getListarsvnmodulo());
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
		if (isEmptyOrNull(svnModulo.getPathCorto1())) {
			if (isEmptyOrNull(svnModulo.getPathCorto2())) {
				if (isEmptyOrNull(svnModulo.getPathCorto3())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_camposvacios")));
					return "";
				}
			}
		}

		if (svnModulo.getSvnTipoAmbiente() == null) {
			System.out.println("pasa por aca es nulo");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}
		delegado.edit(svnModulo);
		session.setAttribute("pagIr", Utilidades.getListarsvnmodulo());
		pagIr = Utilidades.getFinexitoso();

		return pagIr;
	}

	public String delete() {
		SvnModulo doc = session.getAttribute("svnModulo") != null ? (SvnModulo) session
				.getAttribute("svnModulo") : null;
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

	public String edit() {
		return "edit";
	}

	public void selectId(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (svnModulo == null) {
				svnModulo = new SvnModulo();
			}
			// buscamos el role para editar
			if (id >= 0) {
				svnModulo.setCodigo(new Long(id));
			}

			svnModulo = delegado.find(svnModulo);
			session.setAttribute("svnModulo", svnModulo);

		}
	}

	/*
	 * public List<SelectItem> getSvnUrlBase() { Usuario user_logueado =
	 * (Usuario) session .getAttribute("user_logueado"); List<SelectItem> items
	 * = new ArrayList<SelectItem>(); List<SelectItem> items2 =
	 * datosCombo.getAllSvnUrlBase(); items.addAll(items2);
	 * 
	 * return items; }
	 */

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public void change() {
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		getAllSvnNombreAplicacion();
		
	}

	public List<SelectItem> getAllSvnTipoAmbientes() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		SvnTipoAmbiente s = new SvnTipoAmbiente();
		s.setCodigo(-1L);
		s.setNombre("");
		SelectItem it = new SelectItem(s, s.getNombre());
		allSvnTipoAmbientes.add(it);
		if (getSelectOneMenu3() != null
				&& getSelectOneMenu3().getValue() != null) {
			svnnombreaplicacion = (SvnNombreAplicacion) getSelectOneMenu3().getValue();
		}
		if (svnnombreaplicacion != null) {

			List<SelectItem> items2 = getAllSvnTipoAmbientes(svnnombreaplicacion);
			if (items2 != null) {
			}
			allSvnTipoAmbientes.addAll(items2);
		}
		return allSvnTipoAmbientes;
	}

	public List<SelectItem> getAllSvnTipoAmbientes(SvnNombreAplicacion svnnombreaplicacion) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<SvnTipoAmbiente> objetos = delegado
					.findAllSvnTipoAmbiente(svnnombreaplicacion);
			SelectItem item = null;
			for (SvnTipoAmbiente objeto : objetos) {
				if (objeto != null) {
					item = new SelectItem(objeto, objeto.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void change2() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		getAllSvnTipoAmbientes();
	}

	public List<SelectItem> getAllSvnNombreAplicacion() {
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		SvnNombreAplicacion s = new SvnNombreAplicacion();
		s.setCodigo(-1L);
		s.setNombre("");
		SelectItem it = new SelectItem(s, s.getNombre());
		allSvnNombreAplicacion.add(it);
		if (getSelectOneMenu1() != null
				&& getSelectOneMenu1().getValue() != null) {
			svnUrlBase = (SvnUrlBase) getSelectOneMenu1().getValue();
		}
		if (svnUrlBase != null) {

			List<SelectItem> items2 = getAllSvnNombreAplicacion(svnUrlBase);
			if (items2 != null) {
			}
			allSvnNombreAplicacion.addAll(items2);
		}
		return allSvnNombreAplicacion;
	}

	public List<SelectItem> getAllSvnNombreAplicacion(
			SvnUrlBase svnUrlBase) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<SvnNombreAplicacion> objetos = delegado
					.findAllSvnNombreAplicacion(svnUrlBase);
			SelectItem item = null;
			for (SvnNombreAplicacion objeto : objetos) {
				if (objeto != null) {
					item = new SelectItem(objeto, objeto.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SvnModulo> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (getSelectOneMenu2() != null
				&& getSelectOneMenu2().getValue() != null) {
			svnTipoAmbiente = (SvnTipoAmbiente) getSelectOneMenu2()
					.getValue();
		}
		if (svnTipoAmbiente != null) {
			svnModules = delegado.findAllSvnModulo(svnTipoAmbiente);
		}

		// } else {
		// svnModules =
		// delegado.findAllSvnModulo(svnModulo.getSvnNombreAplicacion());
		// }

		List<SvnModulo> lista = new ArrayList<SvnModulo>();
		if (svnModules != null && svnModules.size() > 0) {
			for (SvnModulo svnModulo : svnModules) {
				svnModulo.setDelete(false);
				if (isSwDel()) {
					svnModulo.setDelete(true);
				}
				lista.add(svnModulo);
			}
		}

		svnModules = lista;
		return svnModules;
	}

	public void setDoc_tipos(List<SvnModulo> svnModules) {
		this.svnModules = svnModules;
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

	public SvnModulo getSvnModulo() {
		return svnModulo;
	}

	public void setSvnModulo(SvnModulo svnModulo) {
		this.svnModulo = svnModulo;
	}

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		session.setAttribute("svnUrlBase", svnUrlBase);
		this.svnUrlBase = svnUrlBase;
	}

	public SvnUrlBase getSvnUrlBase() {
		svnUrlBase = session.getAttribute("svnUrlBase") != null ? (SvnUrlBase) session
				.getAttribute("svnUrlBase") : null;

		return svnUrlBase;
	}

	public SvnNombreAplicacion getSvnNombreAplicacion() {
		svnNombreAplicacion = session.getAttribute("svnNombreAplicacion") != null ? (SvnNombreAplicacion) session
				.getAttribute("svnNombreAplicacion") : null;
		return svnNombreAplicacion;
	}

	public void setSvnNombreAplicacion(SvnNombreAplicacion svnNombreAplicacion) {
		session.setAttribute("svnNombreAplicacion", svnNombreAplicacion);
		this.svnNombreAplicacion = svnNombreAplicacion;
	}

	public HtmlSelectOneMenu getSelectOneMenu1() {
		return selectOneMenu1;
	}

	public void setSelectOneMenu1(HtmlSelectOneMenu selectOneMenu1) {
		this.selectOneMenu1 = selectOneMenu1;
	}

	public HtmlSelectOneMenu getSelectOneMenu2() {
		return selectOneMenu2;
	}

	public void setSelectOneMenu2(HtmlSelectOneMenu selectOneMenu2) {
		this.selectOneMenu2 = selectOneMenu2;
	}

	public HtmlSelectOneMenu getSelectOneMenu3() {
		return selectOneMenu3;
	}

	public void setSelectOneMenu3(HtmlSelectOneMenu selectOneMenu3) {
		this.selectOneMenu3 = selectOneMenu3;
	}

	public SvnTipoAmbiente getSvnTipoAmbiente() {
		return svnTipoAmbiente;
	}

	public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}

	public void setAllSvnTipoAmbientes(List<SelectItem> allSvnTipoAmbientes) {
		this.allSvnTipoAmbientes = allSvnTipoAmbientes;
	}

	public void setAllSvnNombreAplicacion(
			List<SelectItem> allSvnNombreAplicacion) {
		this.allSvnNombreAplicacion = allSvnNombreAplicacion;
	}

	public SvnNombreAplicacion getSvnnombreaplicacion() {
		return svnnombreaplicacion;
	}

	public void setSvnnombreaplicacion(SvnNombreAplicacion svnnombreaplicacion) {
		this.svnnombreaplicacion = svnnombreaplicacion;
	}
	public List<SvnModulo> getListaRichFaces() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		if (svnTipoAmbiente != null) {
			svnModules = delegado.findAllSvnModulo(svnTipoAmbiente);
		}
	

		// } else {
		// svnModules =
		// delegado.findAllSvnModulo(svnModulo.getSvnNombreAplicacion());
		// }

		List<SvnModulo> lista = new ArrayList<SvnModulo>();
		
		if (svnModules != null && svnModules.size() > 0) {
			for (SvnModulo svnModulo : svnModules) {
				svnModulo.setDelete(false);
				if (isSwDel()) {
					svnModulo.setDelete(true);
				}
				lista.add(svnModulo);
			}
		}
		svnModules = lista;
		return svnModules;
	}


}
