/*
 * ParametrosClientes.java
 *
 * Created on July 20, 2008, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.configuracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.http.HttpSession;

import com.ecological.configuracion.Configuracion;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.util.ContextSessionRequest;
import com.ldap.ObtenerDataWithLDAP;
import com.util.EncryptorMD5;
import com.util.Utilidades;

/**
 * 
 * @author simon
 */
public class ConfiguracionCliente extends ContextSessionRequest {
	// las variables colocarlas siempre asi

	private ServicioDelegado delegado = new ServicioDelegado();
	private Configuracion configuracion;

	private List<Configuracion> configuraciones;
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
	public boolean swMasDeUnRow;
	private String passwordConf;
	private boolean swServerEncryp;
	private boolean swServerIpEncryp;
	private boolean swLdapPasswordAdmin;
	private boolean swNumeroUsuariosEncryp;
	private boolean swNombreClienteEncryp;
	private boolean swFechaCaducaEncryp;
	private boolean swCompradoEncryp;
	private boolean swBdPostgresEncryp;
	private Utilidades utilidades = new Utilidades();
	private List<SelectItem> clientes;
	private Tree empresaEscojer;
	private String passwordOculta;

	private List<SelectItem> lenguajes;
	private String usuarioProbarLdap;
	private String passwordProbarLdap;

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * configuracion.setComprado(EncryptorMD5.encrypt(EncryptorMD5.getKey(),
	 * configuracion .getComprado()));
	 */
	/** Creates a new instance of CrearProfesion */
	public ConfiguracionCliente() {
		session = super.getSession();
		// _________________________________________
		// LLenamos los clientes
		clientes = new ArrayList<SelectItem>();
		for (int i = 0; i < utilidades.getClientes().length; i++) {
			String datosEmpresa = utilidades.getClientes()[i];
			// obtenemos cada uno de los datos de la empresa separadsos por coma
			String[] datosEmpresa2 = datosEmpresa.split(",");
			SelectItem data = new SelectItem(
					datosEmpresa2[Utilidades.getIdentificadorEmpresa()],
					datosEmpresa2[Utilidades.getNomEmpresa()]);
			clientes.add(data);
			if (configuracion == null) {
				configuracion = new Configuracion();
			}
		}

		// _________________________________________
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		// variable que viene del menu
		boolean crear = session.getAttribute("crear") != null;
		if (crear) {
			setConfiguracion(new Configuracion());
			configuracion.setServer(super.getMaquinaNameServidor());
			configuracion.setServerIp(super.getMaquinaServidor());
		} else {
			setConfiguracion((Configuracion) session
					.getAttribute("configuracion"));

		}

	}

	public String cancelar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ParametrosClientes", "cancelar", e);
		}

		return "listarmenu";
	}

	public String cancelarListar() {
		try {
			super.clearSession(session,
					confmessages.getString("usuarioSession"));
		} catch (Exception e) {
			redirect("ParametrosClientes", "cancelarLista", e);
		}

		return "listar";
	}

	public String inic_crear() {
		String pagIr = "";
		session.setAttribute("empresaEscojer", empresaEscojer);
		session.setAttribute("crear", true);
		pagIr = "crear";

		return pagIr;
	}

	public String delete() {
		Configuracion obj = session.getAttribute("configuracion") != null ? (Configuracion) session
				.getAttribute("configuracion") : null;
		if (obj != null) {
			configuracion = new Configuracion();
			configuracion.setCodigo(obj.getCodigo());
			configuracion = delegado.find_configuracion(configuracion);

			if (configuracion == null) {
				configuracion = new Configuracion();
				configuracion.setCodigo(obj.getCodigo());
				configuracion = delegado.find_configuracion(configuracion);

				getDelegado().edit(configuracion);
				getConfiguraciones();
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

		}
		return "";
	}

	public String edit() {
		return "editConfiguracion";
	}

	public String procesarLdap() {

		return "";
	}

	public void selection(ActionEvent event) throws RoleMultiples {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("editId");
		if ((component != null) && (component.getValue().toString() != null)) {
			long id = Long.parseLong(component.getValue().toString());
			if (configuracion == null) {
				configuracion = new Configuracion();
			}
			// buscamos el objeto
			if (id >= 0) {
				configuracion.setCodigo(new Long(id));
			}

			configuracion = delegado.find_configuracion(configuracion);
			configuracion.setPasswordOcultaSincambio(configuracion
					.getLdapPasswordAdmin());
			// configuracion.setSmtpClaveHidden(configuracion.getSmtpClave());

			session.setAttribute("configuracion", configuracion);

		}
	}

	/*
	 * public void actionListener(ActionEvent event)throws EcologicaExcepciones,
	 * RoleMultiples,RoleNoEncontrado{ }
	 */

	public String create() {
		String pagIr = "";
		boolean swGood = true;
		EncryptorMD5 encryptorMD5 = new EncryptorMD5();
		// obligatorio encriar
		empresaEscojer = (Tree) session.getAttribute("empresaEscojer");
		configuracion.setEmpresa(empresaEscojer);
		configuracion.setServer(EncryptorMD5.encrypt(EncryptorMD5.getKey(),
				configuracion.getServer()));
		configuracion.setServerIp(EncryptorMD5.encrypt(EncryptorMD5.getKey(),
				configuracion.getServerIp()));
		configuracion.setNumeroUsuarios(encryptorMD5.getNumero_usuarios());
		configuracion.setNumeroUsuarios(EncryptorMD5.encrypt(
				EncryptorMD5.getKey(), configuracion.getNumeroUsuarios()));
		configuracion.setComprado("0");
		configuracion.setComprado(EncryptorMD5.encrypt(EncryptorMD5.getKey(),
				configuracion.getComprado()));

		configuracion.setNombreCliente(String.valueOf(Utilidades
				.getIdentificadorEmpresaEcological()));
		configuracion.setFechaExpira(EncryptorMD5.encrypt(
				EncryptorMD5.getKey(), encryptorMD5.getFechaCaduca()));

		// validamos pasdsword ldap
		if (!isEmptyOrNull(configuracion.getLdapPasswordAdmin())) {
			if (configuracion.getLdapPasswordAdmin().equalsIgnoreCase(
					configuracion.getLdapPasswordAdminHidden())) {
				if (configuracion.getLdapPasswordAdmin().length() > 15) {
				} else {
					configuracion.setLdapPasswordAdmin(EncryptorMD5.encrypt(
							EncryptorMD5.getKey(),
							configuracion.getLdapPasswordAdmin()));
					// es redundante, ya viene inicializado
					swGood = true;
				}
			} else {
				swGood = false;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("usuario_passwordInval")));
			}
		}

		// obtenemos el idioma
		verIdioma(configuracion);
		// fin obtenemos el idioma
		if (swGood && !super.isEmptyOrNull(configuracion.getSmtpHost())) {
			configuracion.setBdpostgres(true);
			getDelegado().create(configuracion);
			session.setAttribute("pagIr", Utilidades.getListarConfiguracion());
			pagIr = Utilidades.getFinexitoso();
		} else {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("colocar smtpost si es true:"
									+ super.isEmptyOrNull(configuracion
											.getSmtpHost())));
		}

		return pagIr;
	}

	private void verIdioma(Configuracion configuracion) {

		if (session.getAttribute("lenguaje") != null) {
			Map country = new HashMap();
			country = (Map) session.getAttribute("lenguaje");
			String idioma = (String) country.get(configuracion.getPaisBundle());
			configuracion.setPaisBundle(configuracion.getPaisBundle());
			configuracion.setIdiomaBundle(idioma);
			/*
			 * Iterator keys = aMap.keySet().iterator(); Iterator values =
			 * aMap.values().iterator();
			 */
			/*
			 * Iterator keys = country.keySet().iterator();
			 * while(keys.hasNext()){ String key = (String)keys.next();
			 * System.out.println("key="+key);
			 * System.out.println("valor="+country.get(key)); }
			 */

		}

	}

	public String saveObjeto() {
		String pagIr = "";
		boolean swValidar = false;
		session = super.getSession();
		 
		List<Configuracion> confList = delegado.find_allConfiguracion();
		if (confList != null && !confList.isEmpty() && confList.size() > 0) {
			Configuracion conf = confList.get(0);
			
		 
			if (isEmptyOrNull(configuracion.getSmtpClave())) {

				if (conf != null) {
					configuracion.setSmtpClave(conf.getSmtpClave());
				}
			}

		}
 
		try {

			if (configuracion.getSmtpClave().length() > 15) {
			} else {
				configuracion.setSmtpClave(EncryptorMD5.encrypt(
						EncryptorMD5.getKey(), configuracion.getSmtpClave()));
			}

			if (swServerEncryp) {
				if (configuracion.getServer().length() > 15) {
				} else {
					configuracion.setServer(EncryptorMD5.encrypt(
							EncryptorMD5.getKey(), configuracion.getServer()));
				}
				swValidar = true;
			}

			if (swServerIpEncryp) {
				if (configuracion.getServerIp().length() > 15) {
				} else {
					configuracion
							.setServerIp(EncryptorMD5.encrypt(
									EncryptorMD5.getKey(),
									configuracion.getServerIp()));
				}
				swValidar = true;
			}

			if (swNumeroUsuariosEncryp) {
				if (configuracion.getNumeroUsuarios().length() > 15) {
				} else {
					configuracion.setNumeroUsuarios(EncryptorMD5.encrypt(
							EncryptorMD5.getKey(),
							configuracion.getNumeroUsuarios()));
				}
				swValidar = true;
			}

			if (swNombreClienteEncryp) {
				if (configuracion.getNombreCliente().length() > 15) {
				} else {
					configuracion.setNombreCliente(EncryptorMD5.encrypt(
							EncryptorMD5.getKey(),
							configuracion.getNombreCliente()));
				}
				// swValidar = true;
			}

			if (swCompradoEncryp) {
				if (configuracion.getComprado().length() > 15) {
				} else {
					configuracion
							.setComprado(EncryptorMD5.encrypt(
									EncryptorMD5.getKey(),
									configuracion.getComprado()));
				}
				swValidar = true;
			}

			// validamos pasdsword ldap
			if (swLdapPasswordAdmin) {
				if (configuracion.getLdapPasswordAdmin().equals(
						configuracion.getLdapPasswordAdminHidden())) {

					if (configuracion.getLdapPasswordAdmin().length() > 15) {
					} else {
						configuracion.setLdapPasswordAdmin(EncryptorMD5
								.encrypt(EncryptorMD5.getKey(),
										configuracion.getLdapPasswordAdmin()));
					}
				} else {
					{
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("usuario_passwordInval")));
						return pagIr;
					}
				}
			} else {

				configuracion.setLdapPasswordAdmin(configuracion
						.getPasswordOcultaSincambio());

			}

			// PROCESANDO FECHA EXPIRA
			// boolean isDateValid(java.util.Date date,boolean futura)
			boolean fechaDespuesDeLaActual = true;
			if ((configuracion.getFechaCaduca() != null)
					&& (super.isDateValid(configuracion.getFechaCaduca(),
							fechaDespuesDeLaActual))) {
				String strDateNew = Utilidades.date1.format(configuracion
						.getFechaCaduca());
				EncryptorMD5 encryptorMD5 = new EncryptorMD5();
				configuracion.setFechaExpira(EncryptorMD5.encrypt(
						EncryptorMD5.getKey(), strDateNew));
				swValidar = true;
			}

			// -----------------------------------------------------------------------------------------------------

			// obtenemos el idioma
			verIdioma(configuracion);
			// fin obtenemos el idioma

			if (swValidar) {
				if (EncryptorMD5.getPasswordConf().equals(passwordConf)) {
					getDelegado().edit(configuracion);
					pagIr = Utilidades.getFinexitoso();
					session.setAttribute("pagIr",
							Utilidades.getListarConfiguracion());
				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(messages.getString("seguridad")));
				}
			} else {
				getDelegado().edit(configuracion);
				pagIr = Utilidades.getFinexitoso();
				session.setAttribute("pagIr",
						Utilidades.getListarConfiguracion());
			}

			/*
			 * DirContext context = conectarseLDAP(); if (context != null) {
			 * 
			 * }
			 */

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

	public List<Configuracion> getConfiguraciones() {

		// if (configuraciones==null){
		configuraciones = new ArrayList<Configuracion>();
		configuraciones = delegado.find_allConfiguracion();// new
		// ArrayList<Configuracion>();

		// }

		return configuraciones;
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
		configuraciones = new ArrayList<Configuracion>();
		configuraciones = delegado.find_allConfiguracion();// new
		// ArrayList<Configuracion>();

		if (configuraciones.size() > 0) {
			swMasDeUnRow = true;
		}

		// solo una fila, luego queda deshabilitado elk boton crear
		if (swMasDeUnRow) {
			swAdd = false;
		}
		return swAdd;
	}

	public String probarLdap() {
		ObtenerDataWithLDAP obtenerDataWithLDAP = new ObtenerDataWithLDAP();
		try {
			if (!isEmptyOrNull(usuarioProbarLdap)
					&& !isEmptyOrNull(passwordProbarLdap)) {
				if (obtenerDataWithLDAP.existeEnLDAP(usuarioProbarLdap,
						passwordProbarLdap)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_estaensession")
									+ ": "
									+ usuarioProbarLdap));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_Noestaensession")
									+ ": " + usuarioProbarLdap));
				}

			} else if (isEmptyOrNull(usuarioProbarLdap)
					&& isEmptyOrNull(passwordProbarLdap)) {
				if (obtenerDataWithLDAP.swConectaAdmin(true, null)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_estaensession")
									+ " "
									+ messages.getString("ldapUsuarioAdmin")));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("usuario_Noestaensession")
									+ " "
									+ messages.getString("ldapUsuarioAdmin")));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_camposvacios")));

			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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

	/*
	 * return pais; }
	 * 
	 * public void setPais(Pais pais) {
	 */

	/** Creates a new instance of ParametrosClientes */

	public ServicioDelegado getDelegado() {
		return delegado;
	}

	public void setDelegado(ServicioDelegado delegado) {
		this.delegado = delegado;
	}

	public Configuracion getConfiguracion() {

		return configuracion;
	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public boolean isSwMasDeUnRow() {
		return swMasDeUnRow;
	}

	public void setSwMasDeUnRow(boolean swMasDeUnRow) {
		this.swMasDeUnRow = swMasDeUnRow;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	public boolean isSwServerEncryp() {
		return swServerEncryp;
	}

	public void setSwServerEncryp(boolean swServerEncryp) {
		this.swServerEncryp = swServerEncryp;
	}

	public boolean isSwServerIpEncryp() {
		return swServerIpEncryp;
	}

	public void setSwServerIpEncryp(boolean swServerIpEncryp) {
		this.swServerIpEncryp = swServerIpEncryp;
	}

	public boolean isSwNumeroUsuariosEncryp() {
		return swNumeroUsuariosEncryp;
	}

	public void setSwNumeroUsuariosEncryp(boolean swNumeroUsuariosEncryp) {
		this.swNumeroUsuariosEncryp = swNumeroUsuariosEncryp;
	}

	public boolean isSwNombreClienteEncryp() {
		return swNombreClienteEncryp;
	}

	public void setSwNombreClienteEncryp(boolean swNombreClienteEncryp) {
		this.swNombreClienteEncryp = swNombreClienteEncryp;
	}

	public boolean isSwFechaCaducaEncryp() {
		return swFechaCaducaEncryp;
	}

	public void setSwFechaCaducaEncryp(boolean swFechaCaducaEncryp) {
		this.swFechaCaducaEncryp = swFechaCaducaEncryp;
	}

	public boolean isSwCompradoEncryp() {
		return swCompradoEncryp;
	}

	public void setSwCompradoEncryp(boolean swCompradoEncryp) {
		this.swCompradoEncryp = swCompradoEncryp;
	}

	public boolean isSwBdPostgresEncryp() {
		return swBdPostgresEncryp;
	}

	public void setSwBdPostgresEncryp(boolean swBdPostgresEncryp) {
		this.swBdPostgresEncryp = swBdPostgresEncryp;
	}

	public List<SelectItem> getClientes() {
		return clientes;
	}

	public void setClientes(List<SelectItem> clientes) {
		this.clientes = clientes;
	}

	public DatosCombo getDatosCombo() {
		return datosCombo;
	}

	public void setDatosCombo(DatosCombo datosCombo) {
		this.datosCombo = datosCombo;
	}

	public List<SelectItem> getLenguajes() {
		lenguajes = datosCombo.getLenguajes();
		return lenguajes;
	}

	public void setLenguajes(List<SelectItem> lenguajes) {
		this.lenguajes = lenguajes;
	}

	public Tree getEmpresaEscojer() {
		return empresaEscojer;
	}

	public void setEmpresaEscojer(Tree empresaEscojer) {
		session.setAttribute("empresaEscojer", empresaEscojer);
		this.empresaEscojer = empresaEscojer;
	}

	public boolean isSwLdapPasswordAdmin() {
		return swLdapPasswordAdmin;
	}

	public void setSwLdapPasswordAdmin(boolean swLdapPasswordAdmin) {
		this.swLdapPasswordAdmin = swLdapPasswordAdmin;
	}

	public String getPasswordOculta() {
		return passwordOculta;
	}

	public void setPasswordOculta(String passwordOculta) {
		this.passwordOculta = passwordOculta;
	}

	public String getUsuarioProbarLdap() {
		return usuarioProbarLdap;
	}

	public void setUsuarioProbarLdap(String usuarioProbarLdap) {
		this.usuarioProbarLdap = usuarioProbarLdap;
	}

	public String getPasswordProbarLdap() {
		return passwordProbarLdap;
	}

	public void setPasswordProbarLdap(String passwordProbarLdap) {
		this.passwordProbarLdap = passwordProbarLdap;
	}

}
