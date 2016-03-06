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
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.SvnModulo;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

public class SvnModuloCliente extends ClientePadreDocumento {
	private SvnModulo svnModulo;
	private List<SvnModulo> svnModulos;
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
	private SvnNombreAplicacion svnNombreAplicacion;
	private SvnTipoAmbiente svnTipoAmbiente;
	private SvnUrlBase svnUrlBase;
	private List<SelectItem> svnUrlBaseLista;
	private HtmlSelectOneMenu selectPrincipal;
	private String prueba;

	/** Creates a new instance of ClienteDocumentoEstado */
	/** Creates a new instance of ClienteDocumentoTipo */
	public SvnModuloCliente() {
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

		//boolean crear = session.getAttribute("crear") != null;
		
		/*if (crear) {
				svnModulo = new SvnModulo();	
				svnUrlBase= new SvnUrlBase();	
				 
		} else {
			svnModulo = session.getAttribute("svnModulo") != null ? (SvnModulo) session
					.getAttribute("svnModulo") : new SvnModulo();
			// IniOperaciones();
		}*/
	}

	
	
	public String crear_nuevo() {
		session.setAttribute("crear", true);
		return "crear";
	}

	public String create() {
		String pagIr = "";
		try {
			System.out.println("---------------------------------------------------");
			//System.out.println("svnModulo.getSvnNombreAplicacion() ="+(svnModulo.getSvnNombreAplicacion() == null));
			System.out.println("svnModulo=null?"+(svnModulo==null));
			System.out.println("svnModulo corto= ="+(svnModulo.getPathCorto1()));
			System.out.println("corto= ="+(svnModulo.getPathCorto2()));
			System.out.println("corto= ="+(svnModulo.getPathCorto3()));
		/*	if (svnModulo.getPathCorto1()==null 
					&& svnModulo.getPathCorto2()==null
					&& svnModulo.getPathCorto3()!=null){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
			}*/
			if (svnModulo.getSvnTipoAmbiente() == null) {
				System.out.println("pasa por aca es nulo");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));
				return "";
			}
		/*	if (!esValidaCadena(svnModulo.getPathCorto1())
					|| (!esValidaCadena(svnModulo.getPathCorto2()))
					|| (!esValidaCadena(svnModulo.getPathCorto3()))) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("caracteresinvalidos")));
				return "failed";
			}*/

			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");

			System.out.println("GRABADNO---------------");
			delegado.create(svnModulo);
			session.setAttribute("pagIr", Utilidades.getListarsvnmodulo());
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
		if ((svnModulo.getSvnTipoAmbiente() == null || ("".equals(svnModulo
				.getPathCorto1().toString().trim()))
				&& "".equals(svnModulo.getPathCorto2().toString().trim()))
				&& ("".equals(svnModulo.getPathCorto3().toString().trim()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}
		if (!esValidaCadena(svnModulo.getPathCorto1())
				&& (!esValidaCadena(svnModulo.getPathCorto2()))
				&& (!esValidaCadena(svnModulo.getPathCorto3()))) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
			return "failed";
		}
		delegado.edit(svnModulo);
		session.setAttribute("pagIr", Utilidades.getListarsvnmodulo());
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


	public void change() {
		if (svnUrlBase!=null){
			session.setAttribute("svnUrlBase", svnUrlBase);
		}
	}
	
	
	public List<SelectItem> getAllSvnUrlBase( ) {
		Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado")
				: null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		SelectItem item = null;
		SvnUrlBase svnUrlBase1 = new SvnUrlBase();
		svnUrlBase1.setCodigo(-1L);
		svnUrlBase1.setNombre("");
		item = new SelectItem(svnUrlBase1, svnUrlBase1.getNombre());
		result.add(item);
		try {
			List<SvnUrlBase> svnUrlBases = delegado.findAllSvnUrlBase(usuario,null);
			item = null;
			for (SvnUrlBase svnUrlBase : svnUrlBases) {
				if (svnUrlBase != null) {
					item = new SelectItem(svnUrlBase, svnUrlBase.getNombre());
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

	public List<SvnModulo> getLista() {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (!super.isEmptyOrNull(getStrBuscar())) {
			/*svnModulos = delegado.findAllSvnNombreAplicacion(user_logueado,
					getStrBuscar());*/
			svnModulos = delegado.findAllSvnModulo(svnTipoAmbiente);
		} else {
			svnModulos = delegado.findAllSvnModulo(svnTipoAmbiente);
		}
	
		List<SvnModulo> lista = new ArrayList<SvnModulo>();
		if (svnModulos==null){
			return lista;
		}
		for (SvnModulo svnModulo : svnModulos) {
			svnModulo.setDelete(false);
			if (isSwDel()) {
				svnModulo.setDelete(true);
			}
			lista.add(svnModulo);
		}

		svnModulos = lista;
		return svnModulos;
	}

	public void setDoc_tipos(List<SvnModulo> svnModulos) {
		this.svnModulos = svnModulos;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToAddGrupos();

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

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public SvnNombreAplicacion getSvnNombreAplicacion() {
		return svnNombreAplicacion;
	}

	public void setSvnNombreAplicacion(SvnNombreAplicacion svnNombreAplicacion) {
		this.svnNombreAplicacion = svnNombreAplicacion;
	}
	
 
	
	/*
	 * 	public String getStrBuscar() {
		strBuscar = (String) session.getAttribute("strBuscar");
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		session.setAttribute("strBuscar", strBuscar);
		this.strBuscar = strBuscar;
	}

	 * */
 
	public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
		session.setAttribute("svnUrlBase", svnUrlBase);
		
		this.svnUrlBase = svnUrlBase;
	}

	public SvnUrlBase getSvnUrlBase() {
		strBuscar = (String) session.getAttribute("strBuscar");
		
		return svnUrlBase;
	}

	public void setSvnUrlBaseLista(List<SelectItem> svnUrlBaseLista) {
		this.svnUrlBaseLista = svnUrlBaseLista;
	}

	public HtmlSelectOneMenu getSelectPrincipal() {
		return selectPrincipal;
	}

	public void setSelectPrincipal(HtmlSelectOneMenu selectPrincipal) {
		this.selectPrincipal = selectPrincipal;
	}



	public String getPrueba() {
		return prueba;
	}



	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}



	public SvnModulo getSvnModulo() {
		
		return svnModulo;
	}



	public void setSvnModulo(SvnModulo svnModulo) {
		this.svnModulo = svnModulo;
	}



	public List<SvnModulo> getSvnModulos() {
		return svnModulos;
	}



	public void setSvnModulos(List<SvnModulo> svnModulos) {
		this.svnModulos = svnModulos;
	}



	public SvnTipoAmbiente getSvnTipoAmbiente() {
		return svnTipoAmbiente;
	}



	public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
		this.svnTipoAmbiente = svnTipoAmbiente;
	}



}
