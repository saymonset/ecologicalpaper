/*
 * CrearCliente_EUJ.java
 *
 * Created on 21 de febrero de 2008, 03:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sofytware.cliente.euj;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.software.eujovans.clientes.Cliente_EUJ;
import com.software.eujovans.clientes.Factura;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class CrearCliente_EUJ extends ContextSessionRequest {
	// las variables colocarlas siempre asi
	private HtmlSelectOneMenu selectPais;
	private ServicioDelegado delegado = new ServicioDelegado();
	private Ciudad ciudad;
	private Pais pais;
	private Estado estado;
	private List<Cliente_EUJ> cliente_EUJ_S;
	private Cliente_EUJ cliente_EUJ;
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
	private DatosCombo datosCombo = new DatosCombo();
	private boolean swNoModificar;

	/** Creates a new instance of CrearProfesion */
	public CrearCliente_EUJ() {

		// EN CASO QUE VENGA A CONSULTAR POR LA LISTA DE FLETES EN FACTURAS..
		// CONSULTA.. PERO NO MODIFICA
		swNoModificar = session.getAttribute("nomodificar") != null;

		// para que refresque cache en crear Fletes para variable
		// cliente_EUJ_TODOS
		session.setAttribute("swCacheCliente_EUJ_TODOS", false);
		session.setAttribute("swCacheCliente_EUJ_SOLO", false);

		// _________________________________________
		// un truquillo para que no me salga null el pais
		pais = (Pais) session.getAttribute("pais");
		estado = (Estado) session.getAttribute("estado");
		ciudad = (Ciudad) session.getAttribute("ciudad");

		// _________________________________________
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
			cliente_EUJ = new Cliente_EUJ();
		} else {
			setCliente_EUJ((Cliente_EUJ) session.getAttribute("cliente_EUJ"));
			if (cliente_EUJ != null) {
				pais = cliente_EUJ.getPais();
				estado = cliente_EUJ.getEstado();
				ciudad = cliente_EUJ.getCiudad();
				session.setAttribute("pais", pais);
				session.setAttribute("estado", estado);
				session.setAttribute("ciudad", ciudad);

			} else {

			}

		}

	}

	public String cancelar() {
		super.clearSession(session, confmessages.getString("usuarioSession"));

		return "listarmenu";
	}

	public String cancelarListar() {
		String pagIr = "";
		swNoModificar = session.getAttribute("nomodificar") != null;
		session.removeAttribute("nomodificar");
		super.clearSession(session, confmessages.getString("usuarioSession"));
		if (swNoModificar) {
			pagIr = "listarFacturas";
		} else {
			pagIr = "listar";
		}
		return pagIr;
	}

	public String inic_crear() {
		String pagIr = "";
		pais = (Pais) session.getAttribute("pais");
		estado = (Estado) session.getAttribute("estado");
		ciudad = (Ciudad) session.getAttribute("ciudad");
		if (pais == null || estado == null || ciudad == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));

		} else {
			session.setAttribute("crear", true);
			pagIr = "crear";
		}

		return pagIr;
	}

	public String delete() {
		Cliente_EUJ obj = session.getAttribute("cliente_EUJ") != null ? (Cliente_EUJ) session
				.getAttribute("cliente_EUJ")
				: null;
		if (obj != null) {
			cliente_EUJ = new Cliente_EUJ();
			cliente_EUJ.setCodigo(obj.getCodigo());
			cliente_EUJ = delegado.find(cliente_EUJ);

			if (cliente_EUJ != null) {
				Factura factura = delegado.findClienteEUJOVANS_InFactura(
						cliente_EUJ, cliente_EUJ);
				if (factura == null) {
					cliente_EUJ.setStatus(false);
					delegado.edit(cliente_EUJ);
					getCliente_EUJ_S();
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
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("CLIENTE NULLO"));
			}

		}
		return "";
	}

	public String editDestRemit() {
		session.setAttribute("nomodificar", true);
		return "editDestRemit";
	}

	public String edit() {
		return "edit";
	}

	public void selectionDel(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdDel");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (cliente_EUJ == null) {
				cliente_EUJ = new Cliente_EUJ();
			}
			// buscamos el objeto
			if (id >= 0) {
				cliente_EUJ.setCodigo(new Long(id));
			}

			cliente_EUJ = delegado.find(cliente_EUJ);
			if (cliente_EUJ != null) {
				System.out.println("-----------------------------------------");
				System.out.println("cliente_EUJ=" + cliente_EUJ.getNombre());
				System.out.println("-----------------------------------------");
			} else {
				System.out.println("-----------------------------------------");
				System.out
						.println("NULLO-------------------------------------");
				System.out.println("-----------------------------------------");

			}

			session.setAttribute("cliente_EUJ", cliente_EUJ);
		}
	}

	public void selection(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (cliente_EUJ == null) {
				cliente_EUJ = new Cliente_EUJ();
			}
			// buscamos el objeto
			if (id >= 0) {
				cliente_EUJ.setCodigo(new Long(id));
			}

			cliente_EUJ = delegado.find(cliente_EUJ);
			if (cliente_EUJ != null) {
				System.out.println("-----------------------------------------");
				System.out.println("cliente_EUJ=" + cliente_EUJ.getNombre());
				System.out.println("-----------------------------------------");
			} else {
				System.out.println("-----------------------------------------");
				System.out
						.println("NULLO-------------------------------------");
				System.out.println("-----------------------------------------");

			}

			session.setAttribute("cliente_EUJ", cliente_EUJ);
		}
	}

	// CLIENTES SELECCIONADOS EN FACTURA
	public void selectionFacturaDestinatario(ActionEvent event)
			throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdF");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (cliente_EUJ == null) {
				cliente_EUJ = new Cliente_EUJ();
			}
			// buscamos el objeto
			if (id >= 0) {
				cliente_EUJ.setCodigo(new Long(id));
			}

			cliente_EUJ = delegado.find(cliente_EUJ);
			session.setAttribute("cliente_EUJ", cliente_EUJ);
		}
	}

	public void selectionFacturaRemite(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editIdR");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());

			if (cliente_EUJ == null) {
				cliente_EUJ = new Cliente_EUJ();
			}
			// buscamos el objeto
			if (id >= 0) {
				cliente_EUJ.setCodigo(new Long(id));
			}

			cliente_EUJ = delegado.find(cliente_EUJ);
			session.setAttribute("cliente_EUJ", cliente_EUJ);
		}
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		if (super.isEmptyOrNull(cliente_EUJ.getNombre())
				|| super.isEmptyOrNull(cliente_EUJ.getTelefono())
				|| super.isEmptyOrNull(cliente_EUJ.getDireccion())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}
		if (!esValidaCadena(cliente_EUJ.getNombre())
				|| !esValidaCadena(cliente_EUJ.getTelefono())
				|| !esValidaCadena(cliente_EUJ.getDireccion())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("caracteresinvalidos")));
			return "";
		}

		pais = (Pais) session.getAttribute("pais");
		estado = (Estado) session.getAttribute("estado");
		ciudad = (Ciudad) session.getAttribute("ciudad");
		cliente_EUJ.setPais(pais);
		cliente_EUJ.setCiudad(ciudad);
		cliente_EUJ.setEstado(estado);
		cliente_EUJ.setStatus(Utilidades.isActivo());
		Cliente_EUJ cliente_EUJ2 = new Cliente_EUJ();
		cliente_EUJ2.setNombre(cliente_EUJ.getNombre());
		cliente_EUJ2.setPais(cliente_EUJ.getPais());
		cliente_EUJ2.setEstado(cliente_EUJ.getEstado());
		cliente_EUJ2.setCiudad(cliente_EUJ.getCiudad());
		cliente_EUJ2 = delegado.find_cliente_EUJ_BYNombre(cliente_EUJ2);
		if (cliente_EUJ2 != null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(messages
									.getString("erro_resgistroenuso")));
		} else {
			cliente_EUJ.setNombre(cliente_EUJ.getNombre().toString().trim());
			delegado.create(cliente_EUJ);
			session.setAttribute("pagIr", Utilidades.getListarCliente_EUJ());
			pagIr = Utilidades.getFinexitoso();

		}

		return pagIr;
	}

	public String saveObjeto() {
		String pagIr = "";
		if (super.isEmptyOrNull(cliente_EUJ.getNombre())
				|| super.isEmptyOrNull(cliente_EUJ.getTelefono())
				|| super.isEmptyOrNull(cliente_EUJ.getDireccion())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_camposvacios")));
			return "";
		}
		if (!esValidaCadena(cliente_EUJ.getNombre())
				|| !esValidaCadena(cliente_EUJ.getTelefono())
				|| !esValidaCadena(cliente_EUJ.getDireccion())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("caracteresinvalidos")));
			return "";
		}
		// cliente_EUJ.setPais(pais);
		// cliente_EUJ.setCiudad(ciudad);
		// cliente_EUJ.setEstado(estado);
		cliente_EUJ.setNombre(cliente_EUJ.getNombre().toString().trim());
		delegado.edit(cliente_EUJ);
		pagIr = Utilidades.getFinexitoso();
		session.setAttribute("pagIr", Utilidades.getListarCliente_EUJ());

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

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public boolean isSwMod() {
		swMod = seguridadMenu.isToModUsuario();
		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelUsuario();
		if (swSuperUsuario) {
			swDel = true;
		}
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		swAdd = seguridadMenu.isToAddUsuario();
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

	public HtmlSelectOneMenu getSelectPais() {
		return selectPais;
	}

	public void setSelectPais(HtmlSelectOneMenu selectPais) {
		this.selectPais = selectPais;
	}

	public Pais getPais() {
		pais = (Pais) session.getAttribute("pais");
		return pais;
	}

	public void setPais(Pais pais) {
		session.setAttribute("pais", pais);
		this.pais = pais;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/*
	 * return pais; }
	 * 
	 * public void setPais(Pais pais) {
	 */
	public Estado getEstado() {
		estado = (Estado) session.getAttribute("estado");
		return estado;
	}

	public void setEstado(Estado estado) {
		session.setAttribute("estado", estado);

		this.estado = estado;
	}

	public void colocarPaisSession() {
		session.setAttribute("pais", pais);
		colocarEstadoSession();
		getEstados();
		getCiudades();
	}

	public void colocarEstadoSession() {
		session.setAttribute("estado", estado);
		getCiudades();
	}

	public void colocarCiudadSession() {
		session.setAttribute("ciudad", ciudad);
		// getCiudades();
	}

	public List<SelectItem> getEstados() {
		pais = (Pais) session.getAttribute("pais");
		List<SelectItem> items = new ArrayList<SelectItem>();
		Estado edo = new Estado();
		edo.setNombre("");
		edo.setCodigo(0l);
		SelectItem item = new SelectItem(edo, edo.getNombre());
		items.add(item);
		if (pais != null) {
			List<SelectItem> items2 = datosCombo.getAllEstados(pais);
			items.addAll(items2);
		}
		getCiudades();
		return items;
	}

	public List<SelectItem> getCiudades() {
		estado = (Estado) session.getAttribute("estado");
		List<SelectItem> items = new ArrayList<SelectItem>();
		Ciudad ciud = new Ciudad();
		ciud.setNombre("");
		ciud.setCodigo(0l);
		SelectItem item = new SelectItem(ciud, ciud.getNombre());
		items.add(item);
		if (estado != null) {
			List<SelectItem> items2 = datosCombo.getAllCiudades(estado);
			items.addAll(items2);

		}

		return items;
	}

	public List<Cliente_EUJ> getCliente_EUJ_S() {

		cliente_EUJ_S = new ArrayList<Cliente_EUJ>();
		ciudad = (Ciudad) session.getAttribute("ciudad");
		estado = (Estado) session.getAttribute("estado");
		pais = (Pais) session.getAttribute("pais");
		strBuscar = (String) session.getAttribute("strBuscar");
		if (ciudad != null || pais != null || estado != null
				|| !super.isEmptyOrNull(strBuscar)) {
			cliente_EUJ_S = delegado.findAll_CiudadByClienteEUJ(ciudad, estado,
					pais, strBuscar);
		} else {
			cliente_EUJ_S = delegado.find_allCliente_EUJ();
		}

		List<Cliente_EUJ> lista = new ArrayList<Cliente_EUJ>();
		for (Cliente_EUJ p : cliente_EUJ_S) {
			p.setDelete(false);
			if (isSwDel()) {
				p.setDelete(true);
			}
			if (p.isEujovans()) {
				p.setEujovansStr(messages.getString("si"));
			} else {
				p.setEujovansStr(messages.getString("no"));
			}
			if (p.isStatus()) {
				lista.add(p);
			}

		}
		cliente_EUJ_S = lista;

		return cliente_EUJ_S;
	}

	public void setCliente_EUJ_S(List<Cliente_EUJ> cliente_EUJ_S) {
		this.cliente_EUJ_S = cliente_EUJ_S;
	}

	public Cliente_EUJ getCliente_EUJ() {
		return cliente_EUJ;
	}

	public void setCliente_EUJ(Cliente_EUJ cliente_EUJ) {
		this.cliente_EUJ = cliente_EUJ;
	}

	public boolean isSwNoModificar() {
		return swNoModificar;
	}

	public void setSwNoModificar(boolean swNoModificar) {
		this.swNoModificar = swNoModificar;
	}

}
