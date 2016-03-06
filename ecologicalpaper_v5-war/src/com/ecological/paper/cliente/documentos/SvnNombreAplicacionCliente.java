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

import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.util.Utilidades;

public class SvnNombreAplicacionCliente extends ClientePadreDocumento {
	private HtmlSelectOneMenu selectOneMenu1;
	private HtmlSelectOneMenu selectOneMenu3;
	private HtmlSelectOneMenu selectOneMenu2;
	private SvnNombreAplicacion svnNombreAplicacion;
	private List<SvnNombreAplicacion> svnNombreAplicaciones;
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
	private List<SelectItem> allSvnTipoAmbientes;
	private SvnTipoAmbiente svnTipoAmbiente;
	private SvnNombreAplicacion svnnombreaplicacion;

	/** Creates a new instance of ClienteDocumentoEstado */
	/** Creates a new instance of ClienteDocumentoTipo */
	public SvnNombreAplicacionCliente() {
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
			svnNombreAplicacion = new SvnNombreAplicacion();
		} else {
			svnNombreAplicacion = session.getAttribute("svnNombreAplicacion") != null ? (SvnNombreAplicacion) session
					.getAttribute("svnNombreAplicacion")
					: new SvnNombreAplicacion();
					if (svnNombreAplicacion!=null && svnNombreAplicacion.getSvnUrlBase()!=null){
						svnUrlBase=svnNombreAplicacion.getSvnUrlBase();	
						//svnTipoAmbiente=svnNombreAplicacion.get;	
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
			if ((svnNombreAplicacion.getSvnUrlBase() == null || ""
					.equals(svnNombreAplicacion.getNombre().toString().trim()))
					|| ("".equals(svnNombreAplicacion.getDescripcion()
							.toString().trim()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
			if (!esValidaCadena(svnNombreAplicacion.getNombre())
					|| (!esValidaCadena(svnNombreAplicacion.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
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

			delegado.create(svnNombreAplicacion);
			session.setAttribute("pagIr",
					Utilidades.getListarsvnnombreaplicacion());
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
		if ((svnNombreAplicacion.getSvnUrlBase() == null || ""
				.equals(svnNombreAplicacion.getNombre().toString().trim()))
				|| ("".equals(svnNombreAplicacion.getDescripcion().toString()
						.trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		} else {

			if (!esValidaCadena(svnNombreAplicacion.getNombre())
					|| (!esValidaCadena(svnNombreAplicacion.getDescripcion()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}
			delegado.edit(svnNombreAplicacion);
			session.setAttribute("pagIr",
					Utilidades.getListarsvnnombreaplicacion());
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
		SvnNombreAplicacion doc = session.getAttribute("svnNombreAplicacion") != null ? (SvnNombreAplicacion) session
				.getAttribute("svnNombreAplicacion") : null;
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
			if (svnNombreAplicacion == null) {
				svnNombreAplicacion = new SvnNombreAplicacion();
			}
			// buscamos el role para editar
			if (id >= 0) {
				svnNombreAplicacion.setCodigo(new Long(id));
			}

			svnNombreAplicacion = delegado.find(svnNombreAplicacion);
			session.setAttribute("svnNombreAplicacion", svnNombreAplicacion);

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

/*	public List<SvnNombreAplicacion> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SvnNombreAplicacion> lista = new ArrayList<SvnNombreAplicacion>();
		if (getSelectOneMenu2() != null
				&& getSelectOneMenu2().getValue() != null) {
			svnTipoAmbiente = (SvnTipoAmbiente) getSelectOneMenu2().getValue();
			if (svnTipoAmbiente != null) {
				svnNombreAplicaciones = delegado
						.findAllSvnNombreAplicacion(svnTipoAmbiente);
				for (SvnNombreAplicacion svnNombreAplicacion : svnNombreAplicaciones) {
					svnNombreAplicacion.setDelete(false);
					if (isSwDel()) {
						svnNombreAplicacion.setDelete(true);
					}
					lista.add(svnNombreAplicacion);
				}

			}
		}
		svnNombreAplicaciones = lista;
		return svnNombreAplicaciones;
	}*/
	
	public List<SvnNombreAplicacion> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				List<SvnNombreAplicacion> lista = new ArrayList<SvnNombreAplicacion>();
		if (getSelectOneMenu1() != null
				&& getSelectOneMenu1().getValue() != null) {
			svnUrlBase = (SvnUrlBase) getSelectOneMenu1().getValue();
			if (svnUrlBase != null) {
				svnNombreAplicaciones = delegado.findAllSvnNombreAplicacion(svnUrlBase);
				for (SvnNombreAplicacion svnNombreAplicacion : svnNombreAplicaciones) {
					svnNombreAplicacion.setDelete(false);
					if (isSwDel()) {
						svnNombreAplicacion.setDelete(true);
					}
					lista.add(svnNombreAplicacion);
				}
				 
			}
		}

	 		svnNombreAplicaciones = lista;
		return svnNombreAplicaciones;
	}

	

	public void change() {
		allSvnTipoAmbientes = new ArrayList<SelectItem>();
		getAllSvnTipoAmbientes();
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

	public void setDoc_tipos(List<SvnNombreAplicacion> svnNombreAplicaciones) {
		this.svnNombreAplicaciones = svnNombreAplicaciones;
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

	public SvnNombreAplicacion getSvnNombreAplicacion() {
		return svnNombreAplicacion;
	}

	public void setSvnNombreAplicacion(SvnNombreAplicacion svnNombreAplicacion) {
		this.svnNombreAplicacion = svnNombreAplicacion;
	}

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
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

	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		this.svnUrlBase = svnUrlBase;
	}

	public SvnTipoAmbiente getSvnTipoAmbiente() {
		return svnTipoAmbiente;
	}

	public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}

	public SvnUrlBase getSvnUrlBase() {
		return svnUrlBase;
	}

	public SvnNombreAplicacion getSvnnombreaplicacion() {
		return svnnombreaplicacion;
	}

	public void setSvnnombreaplicacion(SvnNombreAplicacion svnnombreaplicacion) {
		this.svnnombreaplicacion = svnnombreaplicacion;
	}

	public HtmlSelectOneMenu getSelectOneMenu3() {
		return selectOneMenu3;
	}

	public void setSelectOneMenu3(HtmlSelectOneMenu selectOneMenu3) {
		this.selectOneMenu3 = selectOneMenu3;
	}
	
	public List<SvnNombreAplicacion> getListaRichFaces() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				List<SvnNombreAplicacion> lista = new ArrayList<SvnNombreAplicacion>();
			if (svnUrlBase != null) {
				svnNombreAplicaciones = delegado.findAllSvnNombreAplicacion(svnUrlBase);
				for (SvnNombreAplicacion svnNombreAplicacion : svnNombreAplicaciones) {
					svnNombreAplicacion.setDelete(false);
					if (isSwDel()) {
						svnNombreAplicacion.setDelete(true);
					}
					lista.add(svnNombreAplicacion);
				}
				 
			}
		

	 		svnNombreAplicaciones = lista;
		return svnNombreAplicaciones;
	}


}
