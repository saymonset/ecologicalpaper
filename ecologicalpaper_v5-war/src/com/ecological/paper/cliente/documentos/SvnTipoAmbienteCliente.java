package com.ecological.paper.cliente.documentos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class SvnTipoAmbienteCliente extends ClientePadreDocumento {
	private HtmlSelectOneMenu selectOneMenu1;
	private HtmlSelectOneMenu selectOneMenu3;
	private SvnTipoAmbiente svnTipoAmbiente;
	private List<SvnTipoAmbiente> svnTipoAmbientes;
	private SvnNombreAplicacion svnnombreaplicacion;
	public HttpSession session = super.getSession();
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
	private List<SelectItem> allSvnNombreAplicacion;

	/** Creates a new instance of ClienteDocumentoEstado */
	/** Creates a new instance of ClienteDocumentoTipo */
	public SvnTipoAmbienteCliente() {
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
			svnTipoAmbiente = new SvnTipoAmbiente();
		} else {
			svnTipoAmbiente = session.getAttribute("svnTipoAmbiente") != null ? (SvnTipoAmbiente) session
					.getAttribute("svnTipoAmbiente") : new SvnTipoAmbiente();
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
			if ((svnTipoAmbiente.getSvnnombreaplicacion() == null || ""
					.equals(svnTipoAmbiente.getNombre().toString().trim()))
					|| ("".equals(svnTipoAmbiente.getDescripcion().toString()
							.trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(svnTipoAmbiente.getNombre())
					|| (!esValidaCadena(svnTipoAmbiente.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}

			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");
			
			/*if (user_logueado != null) {
				List lista = delegado.findAllSvnTipoAmbiente(user_logueado,
						svnTipoAmbiente.getNombre().toString().trim());
				if (lista != null && lista.size() > 0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("error_userYaExiste")));
					return "";
				}
			}*/

			delegado.create(svnTipoAmbiente);
			session.setAttribute("pagIr",
					Utilidades.getListarsvntipodeambiente());
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
		if ((svnTipoAmbiente.getSvnnombreaplicacion() == null || ""
				.equals(svnTipoAmbiente.getNombre().toString().trim()))
				|| ("".equals(svnTipoAmbiente.getDescripcion().toString()
						.trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {

			if (!esValidaCadena(svnTipoAmbiente.getNombre())
					|| (!esValidaCadena(svnTipoAmbiente.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
			delegado.edit(svnTipoAmbiente);
			session.setAttribute("pagIr",
					Utilidades.getListarsvntipodeambiente());
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
		SvnTipoAmbiente doc = session.getAttribute("svnTipoAmbiente") != null ? (SvnTipoAmbiente) session
				.getAttribute("svnTipoAmbiente") : null;
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
			if (svnTipoAmbiente == null) {
				svnTipoAmbiente = new SvnTipoAmbiente();
			}
			// buscamos el role para editar
			if (id >= 0) {
				svnTipoAmbiente.setCodigo(new Long(id));
			}

			svnTipoAmbiente = delegado.find(svnTipoAmbiente);
			session.setAttribute("svnTipoAmbiente", svnTipoAmbiente);

		}
	}

	
	public void change() {
		
		allSvnNombreAplicacion = new ArrayList<SelectItem>();
		getAllSvnNombreAplicacion();
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
				allSvnNombreAplicacion.addAll(items2);
			}
			
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
				System.out.println("objeto.getNombre()="+objeto.getNombre());
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

	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	public List<SvnTipoAmbiente> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (getSelectOneMenu3() != null
				&& getSelectOneMenu3().getValue() != null) {
			svnnombreaplicacion = (SvnNombreAplicacion) getSelectOneMenu3().getValue();
			if (svnnombreaplicacion != null) {
				
				svnTipoAmbientes = delegado.findAllSvnTipoAmbiente(svnnombreaplicacion);
				List<SvnTipoAmbiente> lista = new ArrayList<SvnTipoAmbiente>();
				for (SvnTipoAmbiente svnTipoAmbiente : svnTipoAmbientes) {
					svnTipoAmbiente.setDelete(false);
					if (isSwDel()) {
						svnTipoAmbiente.setDelete(true);
					}
					lista.add(svnTipoAmbiente);
				}

				svnTipoAmbientes = lista;
			}
		}

		/*
		 * if (!super.isEmptyOrNull(getStrBuscar())) { svnTipoAmbientes =
		 * delegado.findAllSvnTipoAmbiente(user_logueado, getStrBuscar()); }
		 * else { svnTipoAmbientes =
		 * delegado.findAllSvnTipoAmbiente(user_logueado, null); }
		 */

		return svnTipoAmbientes;
	}

	public void setSvnTipoAmbientes(List<SvnTipoAmbiente> svnTipoAmbientes) {
		this.svnTipoAmbientes = svnTipoAmbientes;
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

	public SvnTipoAmbiente getSvnNombreAplicacion() {
		return svnTipoAmbiente;
	}

	public void setSvnNombreAplicacion(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public SvnTipoAmbiente getSvnTipoAmbiente() {
		return svnTipoAmbiente;
	}

	public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}

	public List<SvnTipoAmbiente> getSvnTipoAmbientes() {
		return svnTipoAmbientes;
	}

	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		this.svnUrlBase = svnUrlBase;
	}

	public HtmlSelectOneMenu getSelectOneMenu1() {
		return selectOneMenu1;
	}

	public void setSelectOneMenu1(HtmlSelectOneMenu selectOneMenu1) {
		this.selectOneMenu1 = selectOneMenu1;
	}

	public SvnUrlBase getSvnUrlBase() {
		return svnUrlBase;
	}

	public void setAllSvnNombreAplicacion(List<SelectItem> allSvnNombreAplicacion) {
		this.allSvnNombreAplicacion = allSvnNombreAplicacion;
	}

	public HtmlSelectOneMenu getSelectOneMenu3() {
		return selectOneMenu3;
	}

	public void setSelectOneMenu3(HtmlSelectOneMenu selectOneMenu3) {
		this.selectOneMenu3 = selectOneMenu3;
	}

	public SvnNombreAplicacion getSvnnombreaplicacion() {
		return svnnombreaplicacion;
	}

	public void setSvnnombreaplicacion(SvnNombreAplicacion svnnombreaplicacion) {
		this.svnnombreaplicacion = svnnombreaplicacion;
	}

	public List<SvnTipoAmbiente> getListaRichFaces() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		 
			if (svnnombreaplicacion != null) {
				
				svnTipoAmbientes = delegado.findAllSvnTipoAmbiente(svnnombreaplicacion);
				List<SvnTipoAmbiente> lista = new ArrayList<SvnTipoAmbiente>();
				for (SvnTipoAmbiente svnTipoAmbiente : svnTipoAmbientes) {
					svnTipoAmbiente.setDelete(false);
					if (isSwDel()) {
						svnTipoAmbiente.setDelete(true);
					}
					lista.add(svnTipoAmbiente);
				}

				svnTipoAmbientes = lista;
			}
	 
		return svnTipoAmbientes;
	}

}
