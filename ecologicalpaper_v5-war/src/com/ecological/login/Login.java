package com.ecological.login;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecological.configuracion.Configuracion;
import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.historico.Hist_usuarios;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ldap.ObtenerDataWithLDAP;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import com.util.ValidaVencimiento;

public class Login extends ContextSessionRequest {
	private ServicioDelegado delegado = new ServicioDelegado();

	private HtmlForm form1;
	private HtmlPanelGrid panelGrid1;
	private HtmlOutputText outputText1;
	private HtmlInputText inputText1;
	private HtmlOutputText outputText2;
	private HtmlInputSecret inputSecret1;
	private HtmlCommandButton commandButton1;
	private Usuario usuario = new Usuario();
	private HttpSession session = super.getSession();
	private String goSalir = "inicio";
	private String pais;
	private List lenguajes;
	private HashMap country;
	private long diasSeVence;
	private boolean swNoComprado;
	private boolean swComprado;
	private boolean mismoServidor;
	private boolean servidorLdap;
	EncryptorMD5 encryptorMD5 = new EncryptorMD5();
	/* nuevo mundo */
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private String nameServer;
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private Configuracion conf = new Configuracion();
	private String numero_usuarios;
	public String fechaCaduca;
	private Utilidades utilidades = new Utilidades();
	private String imgLogoEmpresa;
	private boolean swAlteroFecha;
	private List<SelectItem> lstEmpresas;
	private Tree empresa;
	private DatosCombo datosCombo = new DatosCombo();
	private String ldapactivedirectoryhost;
	private String ldapdominiodc;
	private boolean swLdapdominiodc;
	private boolean swEscojerempresa;

	public Login() {
		session = super.getSession();
		String login = session.getAttribute("login") != null ? (String) session
				.getAttribute("login") : "";
		String password = session.getAttribute("password") != null ? (String) session
				.getAttribute("password") : "";
		swEscojerempresa = session.getAttribute("escojerempresa") != null ? (Boolean) session
				.getAttribute("escojerempresa") : false;

		usuario.setLogin(login);
		usuario.setPassword(password);
		configuramosDeBaseDatosEmpresa();
	}

	public Login(String seUsaEnOtroLado) {
	}

	public void configuramosDeBaseDatosEmpresa() {

		// siempre va hacer para la primera...
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {

			conf = configuraciones.get(0);
			ldapdominiodc = conf.getLdapdominiodc();
			ldapactivedirectoryhost = conf.getLdapactivedirectoryhost();
			swLdapdominiodc = ((null != ldapdominiodc && !isEmptyOrNull(ldapdominiodc)) && (null != ldapactivedirectoryhost && !isEmptyOrNull(ldapactivedirectoryhost)));
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		if (swConfiguracionHayData) {

			// logo De la Empresa
			// obtenemos todos los clientes en getClientes en un arregloa
			String[] dataEmpresa = utilidades.getClientes();
			String dataEmpresa2 = dataEmpresa[Integer.parseInt(conf
					.getNombreCliente())];
			String[] dataEmpresa3 = dataEmpresa2.split(",");
			imgLogoEmpresa = dataEmpresa3[Utilidades.getImgEmpresa()];

			session.setAttribute("nombreEmpresa",
					dataEmpresa3[Utilidades.getNomEmpresa()]);

			String comprado = "0";
			if (conf.getComprado() != null
					&& (conf.getComprado().length() > 15)) {
				comprado = EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getComprado());
			}

			// me dice si lo compre o no
			// SWNOCOMPRTADO Y SWCOMPRADO SON DIFERENTES
			swNoComprado = comprado.equals("0");
			// validamos que si lo compra.. no copie y pegue en otro servdor
			swComprado = comprado.equals("1");
			// si la longitud es mayor que 15 , viene encriptado
			if (conf.getNumeroUsuarios() != null
					&& conf.getNumeroUsuarios().length() > 15) {
				numero_usuarios = EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getNumeroUsuarios());
			} else {
				numero_usuarios = encryptorMD5.getNumero_usuarios();
			}

			// CALCULAMOS LA FECHA DE VENCIMIENTO
			if ((conf.getFechaExpira() != null && conf.getFechaExpira()
					.toString().length() > 15)) {
				fechaCaduca = EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getFechaExpira());
			} else {
				fechaCaduca = encryptorMD5.getFechaCaduca();
			}

			// //

			// para que no valida el servidor
			// swComprado=false;
			mismoServidor = getNameServer().equalsIgnoreCase(
					EncryptorMD5.decrypt(EncryptorMD5.getKey(),
							conf.getServer()));
		} else {
			// ----------------------------------------------------------
			// logo de la empresa
			// todos los clientes en un arreglo
			String[] dataEmpresa = utilidades.getClientes();
			// agarramos el cliente cero, que es ecologicalpaper
			String dataEmpresa2 = dataEmpresa[Utilidades
					.getIdentificadorEmpresaEcological()];
			// separa,mos la datra en un arreglo que viene por comas
			String[] dataEmpresa3 = dataEmpresa2.split(",");
			// obtenemos la imagen
			imgLogoEmpresa = dataEmpresa3[Utilidades.getImgEmpresa()];
			// me dice si lo compre o no
			// SWNOCOMPRTADO Y SWCOMPRADO SON DIFERENTES
			swNoComprado = confmessages.getString("comprado").equals("0");
			// validamos que si lo compra.. no copie y pegue en otro servdor
			swComprado = confmessages.getString("comprado").equals("1");

			numero_usuarios = encryptorMD5.getNumero_usuarios();

			fechaCaduca = encryptorMD5.getFechaCaduca();

			// para que no valida el servidor
			// swComprado=false;
			mismoServidor = getNameServer().equalsIgnoreCase(
					confmessages.getString("server"));
		}

		// ----------------------------------------------------------
		// BUSCAMOS LA DATA DE LA FECHA VENCIMIENTO EN EL LOG BASE DE DATOS
		// PARA DESCARTAR QUE NO SE A ALTERADO FECHA EN EL PC
		// DE TAL MANERA QUE NO LA ALTEREN CAMBIANDOLA EN EL SISTEMA

		List<ValidaVencimiento> logVencimiento = delegado
				.find_allValidaVencimiento();
		if (logVencimiento == null || logVencimiento.isEmpty()) {
			ValidaVencimiento validaVencimiento = new ValidaVencimiento();
			validaVencimiento.setFecha(new Date());
			validaVencimiento.setStatus(Utilidades.isActivo());
			validaVencimiento.setFecha2(new Date().toString());
			delegado.create(validaVencimiento);
		} else {
			ValidaVencimiento validaVencimiento = logVencimiento.get(0);
			String fechaSistema = Utilidades.date1.format(new Date());
			String fechaRegistrada = validaVencimiento.getFecha().toString();
			/*
			 * if (new
			 * Date(validaVencimiento.getFecha().getYear(),validaVencimiento
			 * .getFecha().getMonth(),validaVencimiento.getFecha().getDay())
			 * .before(new Date(new Date().getYear(),new Date().getMonth(),new
			 * Date().getDay())) ){
			 */
			int menorQueCero = fechaRegistrada.compareTo(fechaSistema);
			if (menorQueCero < 0) {

				validaVencimiento.setFecha(new Date());
				validaVencimiento.setStatus(Utilidades.isActivo());
				validaVencimiento.setFecha2(new Date().toString());
				delegado.edit(validaVencimiento);
				/*
				 * }else if (new
				 * Date(validaVencimiento.getFecha().getYear(),validaVencimiento
				 * .getFecha().getMonth(),validaVencimiento.getFecha().getDay())
				 * .after(new Date(new Date().getYear(),new
				 * Date().getMonth(),new Date().getDay())) ){
				 */
			} else if (menorQueCero > 0) {// alteraron fechas
				swAlteroFecha = true;
			}
		}

		try {
			// me dice cuantos dias se vence el sistema
			Date date = Utilidades.date1.parse(getFechaCaduca());
			diasSeVence = super.numerodeDiasEnFechaCaducar(date, null);

			if (swAlteroFecha) {
				diasSeVence = -9999;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}

	public String inicio() {
		session.removeAttribute("escojerempresa");
		return "inicio";
	}

	public String login_actionbienvenido() {

		// Add event code here...
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context
				.getResponse();
		// try{

		String login = usuario.getLogin();
		String password = usuario.getPassword();
		try {
			usuario.setPassword(EncryptorMD5.getMD5(usuario.getPassword()));
		} catch (Exception e) {

		}

		if ((usuario.getPassword() != null && usuario.getLogin() != null)) {
			try {
				if (!esValidaCadena(login) || (!esValidaCadena(password))) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
					return "";
				}
				usuario = delegado.findUsuarioLogin(usuario);

			} catch (UsuarioNoEncontrado e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_userNoEncontrado")
								+ " "
								+ login));
				return "";

			} catch (UsuarioMultiples e) {
				// no pasa nasda, escojemos la empresa
			}
			if (usuario != null) {
				List<Tree> empresas = delegado.findAllEmpresas(usuario);
				if (empresas.size() == 1) {
					empresa = empresas.get(0);
					usuario.setEmpresa(empresas.get(0));
					// colocamos sin encriptarlos
					usuario.setLogin(login);
					usuario.setPassword(password);
					return login_action();

				} else if (empresas.size() > 1) {
					session.setAttribute("login", login);
					session.setAttribute("password", password);
					session.setAttribute("escojerempresa", true);
					return "escojerempresa";

				}

			}
			// return "ecological";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("usuario_faltaLoginPassword")));
			return "";
		}

		return "";
	}
	public Flow login_action(String mailPrincipal,String codigoFlow,HttpSession session) {
		Flow flow= new Flow();
		flow.setCodigo(new Long(codigoFlow));
		flow = delegado.findFlow(flow);
		
		usuario = new Usuario();
		if (flow!=null && flow.getDuenio()!=null && flow.getDuenio().getEmpresa()!=null){
			usuario.setEmpresa(flow.getDuenio().getEmpresa());		
		}
	
		usuario.setMail_principal(mailPrincipal);
		
//		usuario.setEmpresa(empresa);
		try {
			usuario = delegado.findUsuarioByMail(usuario);
		} catch (UsuarioNoEncontrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioMultiples e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//cargamos la seguridad
		conectarByFlow(session,usuario);
		
		session.removeAttribute("flowParalelo");
		session.removeAttribute("clientePadreDocumento");
		// PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS
		session.removeAttribute("flowParalelo");
		session.setAttribute("visibleItems", new ArrayList());
		session.setAttribute("invisibleItems", new ArrayList());
		// FIN PARA QUE NO TENGA ṔRBLEMAS CON LOS ROLES Y USJARIOS.. NO
		// HACEN
		// CATCH
		// ESTA EMBASIURADO ESTOS ITEMS

 
		if (flow.getDoc_detalle()!=null){
			session.setAttribute("doc_detalle", flow.getDoc_detalle());
		}
		session.setAttribute("flowHistorico", flow);
		
		
		return flow;
	}
	public String login_action() {

		// Add event code here...
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context
				.getResponse();
		// try{

		String login = usuario.getLogin();
		String password = usuario.getPassword();
		try {
			usuario.setPassword(EncryptorMD5.getMD5(usuario.getPassword()));
		} catch (Exception e) {

		}

		if ((usuario.getPassword() != null && usuario.getLogin() != null)) {
			try {
				if (!esValidaCadena(login) || (!esValidaCadena(password))) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(messages
									.getString("caracteresinvalidos")));
					return "";
				}
				usuario.setEmpresa(empresa);
				if (servidorLdap) {

					ObtenerDataWithLDAP obtenerDataWithLDAP = new ObtenerDataWithLDAP();
					if (Utilidades.getRoot().equals(login)) {
						usuario = delegado.findUsuarioLogin(usuario);
						if (usuario != null) {
							// llenamos estructura de ldap
							boolean esRoot = true;
							obtenerDataWithLDAP.llenarBuscarBdWithLDAP(usuario,
									esRoot);
						}
					} else {
						try {
							String login1 = login;
							String clave = password;
							// PASSWORD SIN ENCRIPTAR
							boolean existe = obtenerDataWithLDAP.existeEnLDAP(
									login1, clave);
							if (existe) {
								boolean esRoot = false;

								obtenerDataWithLDAP.llenarBuscarBdWithLDAP(
										usuario, esRoot);

								usuario = delegado.findUsuarioLogin(usuario);

							} else {
								FacesContext
										.getCurrentInstance()
										.addMessage(
												null,
												new FacesMessage(
														messages.getString("error_userNoEncontrado")
																+ " " + login));
								return "";

							}
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				} else {
					usuario.setEmpresa(empresa);
					usuario = delegado.findUsuarioLogin(usuario);
				}

			} catch (UsuarioNoEncontrado e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages
								.getString("error_userNoEncontrado")
								+ " "
								+ login));
				return "";

			} catch (UsuarioMultiples e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(messages.getString("error_userDupl")
								+ login));
				return "";
			}

			int cuantosConectados = 0;
			if (usuario != null) {

				ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
				Map application = null;
				if (confmessages == null) {
					confmessages = super
							.getResourceBundle("com.util.resource.ecological_conf");
				}

				try {
					application = (Map) super
							.getApplicationMapValue(confmessages
									.getString("variable_global"));
					if (application == null) {
						application = new HashMap();
						// nuevo hashmap en variable global
						super.setApplicationMapValue(
								confmessages.getString("variable_global"),
								application);
					}
				} catch (Exception e) {
					application = new HashMap();
					// nuevo hashmap en variable global
					super.setApplicationMapValue(
							confmessages.getString("variable_global"),
							application);
				}

				// crea y llena la session con el numero de usuarios conectados
				ClienteTree clienteTree = new ClienteTree("");
				clienteTree.getUsuariosConectados();
				cuantosConectados = session.getAttribute("usuariosConectados") != null ? (Integer) session
						.getAttribute("usuariosConectados") : 0;
				// fin usuarios conectados

				// el suuario a buscar, hashmap applicacion y la variable
				// collection pais
				Calendar date = Calendar.getInstance();
				// Date date = new Date();
				String fechaConexion = Utilidades.sdfShow
						.format(date.getTime());
				usuario.setUltimaConexion(super.getMaquinaConectada() + " "
						+ fechaConexion);
				Usuario user = null;
				if (usuario != null && usuario.getId() != null) {
					if (procesarLoginsConectados != null) {
						user = procesarLoginsConectados.loadUsuarioById(
								usuario.getId(), application,
								confmessages.getString("usuariosApplicaction"));
					}

				}

				// SI ESTA LOGUEADO O NO LO ESTA, ACTUALIZAMOS SU SESSION ID Y
				// LUEGO LO SACAMOS SI HAY DOS
				// if ((user==null) ||
				// (super.getUser_logueado().getLogin().equalsIgnoreCase(Utilidades.getRoot())
				// )){
				// si es igual a null es porque no lo encontro en las variables
				// de session
				if (user == null) {
					if (usuario.getFecha_caduca() != null
							&& numerodeDiasEnFechaCaducar(
									usuario.getFecha_caduca(), null) <= 0) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("cuentaexpiro")));
						return "";
					} else if ((diasSeVence <= 0) && swNoComprado) {
						if ((usuario != null && usuario.getId() != null)
								&& usuario.getNombre().equals(
										Utilidades.getRoot())) {
							// es root
							conectar();
							return "ir_aplicacion";
						} else {
							// SI ES USUARIO ROOT, QUE VALLA A LA APLICACION
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("expirado")));
							return "";

						}
						// EMPIEZA DE CERO CUANTOS CONECTADOS
					} else if ((cuantosConectados + 1) > Integer
							.parseInt(getNumero_usuarios())) {
						if (usuario != null
								&& usuario.getNombre().equals(
										Utilidades.getRoot())) {
							// es root
							conectar();
							return "ir_aplicacion";
						} else {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(messages
											.getString("limitelicencias")));
						}
						return "";
					} else if (swComprado && !mismoServidor) {

						if ((usuario != null && usuario.getId() != null)
								&& usuario.getNombre().equals(
										Utilidades.getRoot())) {
							// es root
							conectar();
							return "ir_aplicacion";
						} else {
							// else if (!mismoServidor){
							// System.out.println("Server:"
							// + confmessages.getString("server"));
							// FacesContext
							// .getCurrentInstance()
							// .addMessage(
							// null,
							// new FacesMessage(
							// messages.getString("violando_derechos_autor")));
							// return "";
							conectar();
							return "ir_aplicacion";
						}

					} else {
						// grabamos al usuario en la aplicacioon context y su
						// variable colleccion alcance
						long idUser = procesarLoginsConectados.saveToUsuario(
								usuario, application,
								confmessages.getString("usuariosApplicaction"));
						if (usuario != null && usuario.getId() != null) {
							conectar();
							return "ir_aplicacion";
						}

					}

				} else {
					if (user != null
							&& user.getNombre().equals(Utilidades.getRoot())) {
						// es root
						if (usuario != null && usuario.getId() != null) {
							conectar();
							return "ir_aplicacion";
						}
					} else {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(messages
										.getString("usuario_estaensession")));
						// return "";
						// noi validamos que este conectado, lo dejamos que se
						// loguee nuevamente..
						if (usuario != null && usuario.getId() != null) {
							conectar();
							return "ir_aplicacion";
						}

					}
				}

			}
			// return "ecological";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(messages
							.getString("usuario_faltaLoginPassword")));
			return "";
		}

		/*
		 * }catch(Exception e){ e.printStackTrace(); }
		 */
		return "ecological";
	}

	public void conectarByFlow(HttpSession session,Usuario usuario) {
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {

			conf = configuraciones.get(0);
			ldapdominiodc = conf.getLdapdominiodc();
			ldapactivedirectoryhost = conf.getLdapactivedirectoryhost();
			swLdapdominiodc = ((null != ldapdominiodc && !isEmptyOrNull(ldapdominiodc)) && (null != ldapactivedirectoryhost && !isEmptyOrNull(ldapactivedirectoryhost)));
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		// grabamos al usuario en la aplicacioon context y su variable
		// colleccion alcance
		if (usuario != null && usuario.getId() != null) {
			if (usuario.getEmpresa()==null){
				usuario.setEmpresa(empresa);	
			}
			if (empresa==null && usuario.getEmpresa()!=null){
				empresa=usuario.getEmpresa();
			}
			

		//	InputStream imagenBuffer = null;
//			try {
//				if (!swPostgresVieneDeConfiguracion) {
//					if (usuario.getEmpresa() != null
//							&& usuario.getEmpresa().getImgOracle() != null) {
//						try {
//							imagenBuffer = usuario.getEmpresa().getImgOracle()
//									.getBinaryStream();
//						} catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//				} else {
//					if (usuario.getEmpresa() != null
//							&& usuario.getEmpresa().getImgPostgres() != null) {
//						ByteArrayInputStream stream = new ByteArrayInputStream(
//								usuario.getEmpresa().getImgPostgres());
//						imagenBuffer = (InputStream) stream;
//
//					}
//				}
//				if (imagenBuffer != null
//						&& usuario.getEmpresa() != null
//						&& !isEmptyOrNull(usuario.getEmpresa().getNameFileImg())) {
//					File imgEmpresa = saveFileInDisk(imagenBuffer, usuario
//							.getEmpresa().getNameFileImg());
//
//					if (imgEmpresa != null) {
//
//						usuario.setImgEmpresas(imgEmpresa);
//
//					}
//
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			session.setAttribute("user_logueado", usuario);
			session.setAttribute("idsession", session.getId());
			
			
			// guarda,mos la empresa en la cual entro
			
			session.setAttribute("empresa",empresa);
			session.setAttribute("idsession", session.getId());
			
			// colocamos la seguridad en sessio
			long milisegundosactuales, milisegundos;
			int tiempo = 0;

			if (session.getAttribute("user_seguridad") == null) {
				long tiempoInicio = System.currentTimeMillis();
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad(session);
				clienteSeguridad.ponerSeguridadInSession(usuario,session);
				long totalTiempo = System.currentTimeMillis() - tiempoInicio;
				// System.out.println("00000El tiempo login  de demora es :" +
				// totalTiempo
				// + " miliseg");
				

			}

			// HiloClienteSeguridad hiloClienteSeguridad = new
			// HiloClienteSeguridad(usuario);
			// hiloClienteSeguridad.start();


			// guardamos historico
			Hist_usuarios hist_usuarios = new Hist_usuarios();
			hist_usuarios.setHistusuario(usuario);
			Calendar cal = Calendar.getInstance();
			hist_usuarios.setFecha_conecto_inicio(cal.getTime());
			hist_usuarios.setStatus(Utilidades.isActivo());
			hist_usuarios.setMaquina(super.getMaquinaConectada());
			try {
				delegado.create(hist_usuarios);
			} catch (Exception e) {
				System.out.println("No preocupar error en historico ="
						+ e.toString());
			}
		}
	}

	public void conectar() {
		// grabamos al usuario en la aplicacioon context y su variable
		// colleccion alcance
		if (usuario != null && usuario.getId() != null) {
			usuario.setEmpresa(empresa);

			InputStream imagenBuffer = null;

			if (!swPostgresVieneDeConfiguracion) {
				if (usuario.getEmpresa() != null
						&& usuario.getEmpresa().getImgOracle() != null) {
					try {
						imagenBuffer = usuario.getEmpresa().getImgOracle()
								.getBinaryStream();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else {
				if (usuario.getEmpresa() != null
						&& usuario.getEmpresa().getImgPostgres() != null) {
					ByteArrayInputStream stream = new ByteArrayInputStream(
							usuario.getEmpresa().getImgPostgres());
					imagenBuffer = (InputStream) stream;

				}
			}
			if (imagenBuffer != null && usuario.getEmpresa() != null
					&& !isEmptyOrNull(usuario.getEmpresa().getNameFileImg())) {
				File imgEmpresa = saveFileInDisk(imagenBuffer, usuario
						.getEmpresa().getNameFileImg());

				if (imgEmpresa != null) {
					
					usuario.setImgEmpresas(imgEmpresa);
					
				}

			}

			super.setSession("user_logueado", usuario);
			usuario.setIdsession(super.getSession().getId());
			// guarda,mos la empresa en la cual entro
			super.setSession("empresa", empresa);

			// colocamos la seguridad en sessio
			long milisegundosactuales, milisegundos;
			int tiempo = 0;

			if (session.getAttribute("user_seguridad") == null) {
				long tiempoInicio = System.currentTimeMillis();
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
				clienteSeguridad.ponerSeguridadInSession(usuario);
				long totalTiempo = System.currentTimeMillis() - tiempoInicio;
				// System.out.println("00000El tiempo login  de demora es :" +
				// totalTiempo
				// + " miliseg");
			}

			// HiloClienteSeguridad hiloClienteSeguridad = new
			// HiloClienteSeguridad(usuario);
			// hiloClienteSeguridad.start();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("usuario_exito")));
			// guardamos historico
			Hist_usuarios hist_usuarios = new Hist_usuarios();
			hist_usuarios.setHistusuario(usuario);
			Calendar cal = Calendar.getInstance();
			hist_usuarios.setFecha_conecto_inicio(cal.getTime());
			hist_usuarios.setStatus(Utilidades.isActivo());
			hist_usuarios.setMaquina(super.getMaquinaConectada());
			try {
				delegado.create(hist_usuarios);
				
				//INICIO CREAMOS UN ROLE PO DEFAULT A LA EMPRESA SI ES QUE NO LO TIENE
				// creamos siempre un role por default y que empize por a para que sea
				// predeterminado
				llenarPerfilRoleDefault(empresa);
				//FIN CREAMOS UN ROLE PO DEFAULT A LA EMPRESA SI ES QUE NO LO TIENE
				
			} catch (Exception e) {
				System.out.println("No preocupar error en historico ="
						+ e.toString());
			}
		}
	}

	public String getSalir() {
		ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
		Map application = (Map) super.getApplicationMapValue(confmessages
				.getString("variable_global"));
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null && user_logueado.getId() != null) {
			Usuario user = procesarLoginsConectados.loadUsuarioById(
					user_logueado.getId(), application,
					confmessages.getString("usuariosApplicaction"));
			if (user != null) {
				long id_usuario_salir = user_logueado.getId();
				procesarLoginsConectados.deleteUsuarioById(id_usuario_salir,
						application,
						confmessages.getString("usuariosApplicaction"));
			}
		}

		super.clearSession(session, "");

		return "bienvenidoUsuarioPagPrincipal";
	}

	public Usuario getUsuario() {
		return usuario;

	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getGoSalir() {
		getSalir();
		return "";
	}

	public void setGoSalir(String goSalir) {
		
		this.goSalir = goSalir;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public long getDiasSeVence() {

		return diasSeVence;
	}

	public void setDiasSeVence(long diasSeVence) {
		this.diasSeVence = diasSeVence;
	}

	public boolean isSwNoComprado() {
		return swNoComprado;
	}

	public void setSwNoComprado(boolean swNoComprado) {
		this.swNoComprado = swNoComprado;
	}

	public String getNameServer() {
		nameServer = super.getMaquinaNameServidor();
		return nameServer;
	}

	public void setNameServer(String nameServer) {
		this.nameServer = nameServer;
	}

	public boolean isSwComprado() {
		return swComprado;
	}

	public void setSwComprado(boolean swComprado) {
		this.swComprado = swComprado;
	}

	public boolean isMismoServidor() {
		return mismoServidor;
	}

	public void setMismoServidor(boolean mismoServidor) {
		this.mismoServidor = mismoServidor;
	}

	public String getNumero_usuarios() {

		return numero_usuarios;
	}

	public void setNumero_usuarios(String numero_usuarios) {
		this.numero_usuarios = numero_usuarios;
	}

	public String getFechaCaduca() {
		return fechaCaduca;
	}

	public void setFechaCaduca(String fechaCaduca) {
		this.fechaCaduca = fechaCaduca;
	}

	public String getImgLogoEmpresa() {

		return imgLogoEmpresa;
	}

	public void setImgLogoEmpresa(String imgLogoEmpresa) {
		this.imgLogoEmpresa = imgLogoEmpresa;
	}

	/**
	 * @return the lstEmpresas
	 */
	public List<SelectItem> getLstEmpresas() {
		Usuario user = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user != null) {
			lstEmpresas = datosCombo.getAllEmpresas(user);
		} else {
			lstEmpresas = datosCombo.getAllEmpresas(usuario);
		}

		return lstEmpresas;
	}

	/**
	 * @param lstEmpresas
	 *            the lstEmpresas to set
	 */
	public void setLstEmpresas(List<SelectItem> lstEmpresas) {
		this.lstEmpresas = lstEmpresas;
	}

	/**
	 * @return the empresa
	 */
	public Tree getEmpresa() {

		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Tree empresa) {

		this.empresa = empresa;
	}

	public boolean isServidorLdap() {
		return servidorLdap;
	}

	public void setServidorLdap(boolean servidorLdap) {
		this.servidorLdap = servidorLdap;
	}

	public void setForm1(HtmlForm form1) {
		this.form1 = form1;
	}

	public HtmlForm getForm1() {
		return form1;
	}

	public void setPanelGrid1(HtmlPanelGrid panelGrid1) {
		this.panelGrid1 = panelGrid1;
	}

	public HtmlPanelGrid getPanelGrid1() {
		return panelGrid1;
	}

	public void setOutputText1(HtmlOutputText outputText1) {
		this.outputText1 = outputText1;
	}

	public HtmlOutputText getOutputText1() {
		return outputText1;
	}

	public void setInputText1(HtmlInputText inputText1) {
		this.inputText1 = inputText1;
	}

	public HtmlInputText getInputText1() {
		return inputText1;
	}

	public void setOutputText2(HtmlOutputText outputText2) {
		this.outputText2 = outputText2;
	}

	public HtmlOutputText getOutputText2() {
		return outputText2;
	}

	public void setInputSecret1(HtmlInputSecret inputSecret1) {
		this.inputSecret1 = inputSecret1;
	}

	public HtmlInputSecret getInputSecret1() {
		return inputSecret1;
	}

	public void setCommandButton1(HtmlCommandButton commandButton1) {
		this.commandButton1 = commandButton1;
	}

	public HtmlCommandButton getCommandButton1() {
		return commandButton1;
	}

	public String getLdapactivedirectoryhost() {
		return ldapactivedirectoryhost;
	}

	public void setLdapactivedirectoryhost(String ldapactivedirectoryhost) {
		this.ldapactivedirectoryhost = ldapactivedirectoryhost;
	}

	public String getLdapdominiodc() {
		return ldapdominiodc;
	}

	public void setLdapdominiodc(String ldapdominiodc) {
		this.ldapdominiodc = ldapdominiodc;
	}

	public boolean isSwConfiguracionHayData() {
		return swConfiguracionHayData;
	}

	public void setSwConfiguracionHayData(boolean swConfiguracionHayData) {
		this.swConfiguracionHayData = swConfiguracionHayData;
	}

	public boolean isSwLdapdominiodc() {
		return swLdapdominiodc;
	}

	public boolean isSwEscojerempresa() {
		return swEscojerempresa;
	}

	public void setSwEscojerempresa(boolean swEscojerempresa) {
		this.swEscojerempresa = swEscojerempresa;
	}

	public void setSwLdapdominiodc(boolean swLdapdominiodc) {
		this.swLdapdominiodc = swLdapdominiodc;
	}

}
