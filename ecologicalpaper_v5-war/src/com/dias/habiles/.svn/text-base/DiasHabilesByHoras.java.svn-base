/*
 * DiasHabilesByHoras.java
 *
 * Created on August 12, 2008, 8:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.dias.habiles;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.hilos.MySeguridad;

import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.role.ClienteRole;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.historico.Hist_usuarios;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;

import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;

import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlInputHidden;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class DiasHabilesByHoras extends ContextSessionRequest {
	// las variables colocarlas siempre asi
	private ServicioDelegado delegado = new ServicioDelegado();
	private DiasHabiles diasHabiles;
	// private List<Role> roles;
	private HttpSession session = super.getSession();
	private String strBuscar;
	private String diaHabilNombre;
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
	private List<SelectItem> llenarHoras;
	private List<SelectItem> llenarMinutos;
	private String horaInicialAm;
	private String horaFinalAm;
	private String horaInicialPm;
	private String horaFinalPm;
	private String minutos;
	private List<DiasHabiles> listaDiasHabiles;

	/** Creates a new instance of CrearProfesion */
	public DiasHabilesByHoras() {
		session = super.getSession();
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
			diasHabiles = new DiasHabiles();
		} else {
			diasHabiles = (DiasHabiles) session.getAttribute("diasHabiles");
			if (diasHabiles != null && diasHabiles.getNombre() != null) {
				diaHabilNombre = messages.getString(diasHabiles.getNombre());
			}
		}

	}

	public String cancelarListar() {
		try {
			super.clearSession(session, confmessages.getString("usuarioSession"));	
		} catch (Exception e) {
			redirect("DiasHabilesByHoras","cancelarListar",e);
		}
		
		return "listar";
	}

	public String inic_crear() {

		session.setAttribute("crear", true);
		return "crear";
	}

	public String editDiasHabiles() {
		return "diasHabiles";
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		delegado.create(diasHabiles);
		session.setAttribute("pagIr", Utilidades.getListarProfesion());
		pagIr = Utilidades.getFinexitoso();

		return pagIr;
	}

	public String saveObjeto() {
		String pagIr = "";
		boolean swError = false;
		try {
			if (diasHabiles.getH_InicialAM().trim().equalsIgnoreCase("00:00")
					&& !diasHabiles.getH_FinalAM().trim().equalsIgnoreCase(
							"00:00")) {

				swError = true;
			} else if (!diasHabiles.getH_InicialAM().trim().equalsIgnoreCase(
					"00:00")
					&& diasHabiles.getH_FinalAM().trim().equalsIgnoreCase(
							"00:00")) {

				swError = true;
			}
			if (!diasHabiles.getH_InicialPM().trim().equalsIgnoreCase("00:00")
					&& diasHabiles.getH_FinalPM().trim().equalsIgnoreCase(
							"00:00")) {

				swError = true;
			} else if (diasHabiles.getH_InicialPM().equalsIgnoreCase("00:00")
					&& !diasHabiles.getH_FinalPM().trim().equalsIgnoreCase(
							"00:00")) {

				swError = true;
			}

			if (!swError) {

				delegado.edit(diasHabiles);
				pagIr = Utilidades.getFinexitoso();
				session
						.setAttribute("pagIr", Utilidades
								.getListarDiasHabiles());
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(messages.getString("error")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_intentar") + e));

		}

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
		swMod = seguridadMenu.isToModDiasHabiles();
		if (swSuperUsuario) {
			swMod = true;
		}
		return swMod;
	}

	public void setSwMod(boolean swMod) {
		this.swMod = swMod;
	}

	public boolean isSwDel() {
		swDel = seguridadMenu.isToDelProfesiones();
		if (swSuperUsuario) {
			swDel = true;
		}
		return swDel;
	}

	public void setSwDel(boolean swDel) {
		this.swDel = swDel;
	}

	public boolean isSwAdd() {
		swAdd = seguridadMenu.isToAddProfesiones();
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

	public List<SelectItem> getLlenarHoras() {
		llenarHoras = new ArrayList<SelectItem>();
		SelectItem item;
		String hora = "";
		for (int i = 0; i <= 12; i++) {
			hora = "" + i;
			if (hora.length() < 2) {
				hora = "0" + hora;
			}
			hora += ":00";
			item = new SelectItem(hora, hora);
			llenarHoras.add(item);
		}

		return llenarHoras;
	}

	public String editar() {
		return "editar";
	}

	public void versionId(ActionEvent event) {

		UIParameter component = null;
		component = (UIParameter) event.getComponent().findComponent(
				"editId2_Detalle");

		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (diasHabiles == null) {
				diasHabiles = new DiasHabiles();
			}
			// buscamos el objeto
			if (id >= 0) {
				diasHabiles.setCodigo(new Long(id));
			}
			diasHabiles = delegado.find(diasHabiles);

			session.setAttribute("diasHabiles", diasHabiles);

		}
	}

	public List<SelectItem> getLlenarMinutos() {
		llenarMinutos = new ArrayList<SelectItem>();
		SelectItem item;
		String minto;
		for (int i = 0; i < Utilidades.getMinutosNumeroConfigurar(); i++) {
			minto = "" + i;
			if (minto.length() < 2) {
				minto = "0" + minto;
			}
			item = new SelectItem(minto, minto);
			llenarMinutos.add(item);
		}

		return llenarMinutos;
	}

	public void setLlenarHoras(List<SelectItem> llenarHoras) {
		this.llenarHoras = llenarHoras;
	}

	public void setLlenarMinutos(List<SelectItem> llenarMinutos) {
		this.llenarMinutos = llenarMinutos;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public String getHoraInicialAm() {
		return horaInicialAm;
	}

	public void setHoraInicialAm(String horaInicialAm) {
		this.horaInicialAm = horaInicialAm;
	}

	public String getHoraFinalAm() {
		return horaFinalAm;
	}

	public void setHoraFinalAm(String horaFinalAm) {
		this.horaFinalAm = horaFinalAm;
	}

	public String getHoraInicialPm() {
		return horaInicialPm;
	}

	public void setHoraInicialPm(String horaInicialPm) {
		this.horaInicialPm = horaInicialPm;
	}

	public String getHoraFinalPm() {
		return horaFinalPm;
	}

	public void setHoraFinalPm(String horaFinalPm) {
		this.horaFinalPm = horaFinalPm;
	}

	public List<DiasHabiles> getListaDiasHabiles() {
		session = super.getSession();
		Usuario user_logueado =session!=null?(Usuario) session.getAttribute("user_logueado"):null;
		List<DiasHabiles> l2 = new ArrayList<DiasHabiles>();
		if (user_logueado != null) {
			listaDiasHabiles = delegado.find_allDiasHabiles(user_logueado
					.getEmpresa());
			for (DiasHabiles l : listaDiasHabiles) {
				l.setNombreStr(messages.getString(l.getNombre()));
				l2.add(l);

			}
		}
		listaDiasHabiles = l2;
		return listaDiasHabiles;
	}

	public void setListaDiasHabiles(List<DiasHabiles> listaDiasHabiles) {
		this.listaDiasHabiles = listaDiasHabiles;
	}

	public ServicioDelegado getDelegado() {
		return delegado;
	}

	public void setDelegado(ServicioDelegado delegado) {
		this.delegado = delegado;
	}

	public DiasHabiles getDiasHabiles() {
		return diasHabiles;
	}

	public void setDiasHabiles(DiasHabiles diasHabiles) {
		this.diasHabiles = diasHabiles;
	}

	public String getDiaHabilNombre() {

		return diaHabilNombre;
	}

	public void setDiaHabilNombre(String diaHabilNombre) {
		this.diaHabilNombre = diaHabilNombre;
	}

}
