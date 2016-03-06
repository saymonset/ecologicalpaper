/*
 * InicializaAplication.java
 *
 * Created on August 15, 2007, 5:37 PM
 */

package com.ecological.inicializando;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.hilos.ControlTiempoInFlows;
import com.ecological.hilos.MySeguridad;
import com.ecological.paper.cliente.documentos.ClienteDocumentoEstado;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.EncryptorMD5;
import com.util.Utilidades;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * 
 * @author simon
 * @version Web application lifecycle listener.
 */

public class InicializaAplication extends ContextSessionRequest implements
		ServletContextListener {
	private ServletContext ctx_aplication = null;
	private ServicioDelegado delegado = new ServicioDelegado();
	private Tree tree;
	private HttpSession session = super.getSession();

	public InicializaAplication() {

	}

	/**
	 * ### Method from ServletContextListener ###
	 * 
	 * Called when a Web application is first ready to process requests (i.e. on
	 * Web server startup and when a context is added or reloaded).
	 * 
	 * For example, here might be database connections established and added to
	 * the servlet context attributes.
	 */
	public void contextInitialized(ServletContextEvent evt) {
		ctx_aplication = evt.getServletContext();

		// arreglamos campos nulos que se crean al incluir mejoras
		// chequear en la clase seguridad.java, hay estan los escripts que hay
		// que correr
		// para actualizar las tablas con nuevo campos creados y son nulos...
		// delegado.arreglarCamposNullInTablas();

		checkUsuario_Vacio();
		// ____________________________________________
		// TOODS LOS PERMISOS QUE NO SON DEL ARBOL, LISTAR,BORRAR,EDITAR ,CREAR
		// CONTROL TIME ,PROFESIONES, USUARIOS,TIPOS DE DOCUMENBTOS ETC..
		llenarPermIndivDelSitema();
		// ____________________________________________
		// llenamos estados del doc y creamos un perfil por default en cada una
		// de las empresas, cuando agreguen un documento, ese perfil sera el
		// seleccionado
		llenarEstadosDelDoc();

		// permisos del arboil y documentos
		llenaDocumentosPermIndivDelSitema();
		llenaLotesDeDocumentosPermIndivDelSitema();
		llenaCargoPermIndivDelSitema();
		llenaCarpetaPermIndivDelSitema();
		llenaProcesoPermIndivDelSitema();
		llenaAreaPermIndivDelSitema();
		llenaPrincipalPermIndivDelSitema();
		llenaRaizrPermIndivDelSitema();
		llenarDiasHabiles();
		llenarExtensionesFile();
		// System.out.println("uno solo hilo------");
		// ControlTiempoInFlows controlFlowsInTime = new ControlTiempoInFlows();
		// controlFlowsInTime.start();
		// System.out.println("uno solo hilo------");
		// MySeguridad mySeguridad = new MySeguridad();
		// mySeguridad.start();
	}

	public void llenarDiasHabiles(Tree empresa) {
		for (int i = 0; i < 7; i++) {

			DiasHabiles diasHabiles = new DiasHabiles();
			diasHabiles.setStatus(Utilidades.isActivo());
			diasHabiles.setH_InicialAM(Utilidades.getInicializarHora());
			diasHabiles.setH_FinalAM(Utilidades.getInicializarHora());
			diasHabiles.setH_InicialPM(Utilidades.getInicializarHora());
			diasHabiles.setH_FinalPM(Utilidades.getInicializarHora());
			switch (i) {
			case 0:
				diasHabiles.setNombre(Utilidades.getLunes());
				break;
			case 1:
				diasHabiles.setNombre(Utilidades.getMartes());
				break;

			case 2:
				diasHabiles.setNombre(Utilidades.getMiercoles());
				break;

			case 3:
				diasHabiles.setNombre(Utilidades.getJueves());
				break;

			case 4:
				diasHabiles.setNombre(Utilidades.getViernes());
				break;

			case 5:
				diasHabiles.setNombre(Utilidades.getSabado());
				break;

			case 6:
				diasHabiles.setNombre(Utilidades.getDomingo());
				break;

			}
			diasHabiles.setEmpresa(empresa);
			delegado.create(diasHabiles);

		}

	}

	public void llenarDiasHabiles() {
		List<Tree> empresas = delegado.findAllEmpresas();

		try {
			for (Tree empresa : empresas) {

				List<DiasHabiles> diasHabilesList = delegado
						.find_allDiasHabiles(empresa);
				if (diasHabilesList == null || diasHabilesList.isEmpty()) {
					llenarDiasHabiles(empresa);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void checkUsuario_Vacio() {
		Usuario usuario = (Usuario) super.getSession("user_logueado");
		List HayUsuarios = delegado.findAll_Usuario(usuario);
		if (HayUsuarios != null && HayUsuarios.isEmpty()) {
			try {
				Usuario root = new Usuario();

				Calendar cal = Calendar.getInstance();
				tree = new Tree();

				tree.setNombre("EMPRESA");
				tree.setDeBaseToUsuario(true);
				tree.setFecha_creado(cal.getTime());
				tree.setTiponodo(Utilidades.getNodoRaiz());
				tree.setMaquina(null);
				delegado.crearTreeEmpresa(tree);
				tree.setNodopadre(tree.getNodo());

				delegado.edit(tree);
				Tree empresa = new Tree();
				empresa = delegado.obtenerNodo(tree.getNodo());

				root.setEmpresa(empresa);

				tree = new Tree();
				tree.setNombre("SUCURSAL DE LA EMPRESA");
				tree.setDeBaseToUsuario(true);
				tree.setFecha_creado(cal.getTime());
				tree.setTiponodo(Utilidades.getNodoPrincipal());
				tree.setNodopadre(empresa.getNodo());
				tree.setMaquina(null);
				delegado.crearTreeEmpresa(tree);
				Tree principal = new Tree();
				principal = delegado.obtenerNodo(tree.getNodo());

				root.setPrincipal(principal);

				tree = new Tree();
				tree.setNombre("UNIDADES DE NEGOCIO");
				tree.setDeBaseToUsuario(true);
				tree.setFecha_creado(cal.getTime());
				tree.setTiponodo(Utilidades.getNodoArea());
				tree.setMaquina(null);
				tree.setNodopadre(principal.getNodo());
				delegado.crearTreeEmpresa(tree);

				Tree area = new Tree();
				area = delegado.obtenerNodo(tree.getNodo());
				root.setArea(area);

				tree = new Tree();
				tree.setNombre("CARGO");
				tree.setDeBaseToUsuario(true);
				tree.setFecha_creado(cal.getTime());
				tree.setTiponodo(Utilidades.getNodoCargo());
				tree.setMaquina(null);
				tree.setNodopadre(area.getNodo());
				delegado.crearTreeEmpresa(tree);
				Tree cargo = new Tree();
				cargo = delegado.obtenerNodo(tree.getNodo());

				root.setCargo(cargo);
				root.setApellido("root");
				root.setDireccion("root");
				root.setFecha_creado(new Date());
				root.setLogin("root");
				root.setMail_principal("invitado@ecologicalpaper.com");
				root.setNombre("root");
				root.setStatus(true);

				root.setPassword(EncryptorMD5.getMD5("123456"));
				delegado.create(root);

				List<Tree> treeUsuarioNulos = delegado
						.findTreeUsuarioNull(tree);
				for (Tree t : treeUsuarioNulos) {
					t.setUsuario_creador(root);
					delegado.edit(t);
				}

			} catch (NoSuchAlgorithmException ex) {
				// ex.printStackTrace();
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		} else {
			System.out
					.println("no hay data.. chequee base de datos..tcpip.. firewall");
		}
	}

	public void llenarExtensionesFile() {
		Usuario usuario = (Usuario) super.getSession("user_logueado");
		List<ExtensionFile> extensionesFile = delegado.find_allExtensionFile();
		if (extensionesFile == null || extensionesFile.isEmpty()) {
			try {
				ExtensionFile extensionFile = new ExtensionFile();
				extensionFile.setExtension("emb");
				extensionFile.setMimeType("image/tiff");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("dst");
				extensionFile.setMimeType("image/tiff");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("txt");
				extensionFile.setMimeType("text/plain");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xls");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("ppt");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pps");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("acp");
				extensionFile.setMimeType("audio/x-mei-aac");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("asf");
				extensionFile.setMimeType("video/x-ms-asf");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("bmp");
				extensionFile.setMimeType("image/x-bmp");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("png");
				extensionFile.setMimeType("image/png");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("csv");
				extensionFile.setMimeType("text/plain");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("doc");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("gif");
				extensionFile.setMimeType("image/gif");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("htm");
				extensionFile.setMimeType("text/html");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("html");
				extensionFile.setMimeType("text/html");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("jpg");
				extensionFile.setMimeType("image/jpeg");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("jpeg");
				extensionFile.setMimeType("image/jpeg");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("js");
				extensionFile.setMimeType("application/x-javascript");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("odp");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("sxi");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("ods");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("sxc");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("sxw");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("odt");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("rtf");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("shtml");
				extensionFile.setMimeType("text/html");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("tif");
				extensionFile.setMimeType("image/tiff");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("tiff");
				extensionFile.setMimeType("image/tiff");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("qtif");
				extensionFile.setMimeType("image/x-quicktime");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xml");
				extensionFile.setMimeType("text/xml");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pub");
				extensionFile.setMimeType("application/octet-stream");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("vsd");
				extensionFile.setMimeType("application/x-visio");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("vst");
				extensionFile.setMimeType("application/x-visio");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("tgz");
				extensionFile.setMimeType("application/x-compressed");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("gz");
				extensionFile.setMimeType("application/x-compressed");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("tar");
				extensionFile.setMimeType("application/x-compressed");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("zip");
				extensionFile.setMimeType("application/x-zip-compressed");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("rar");
				extensionFile.setMimeType("application/x-rar-compressed");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pub");
				extensionFile.setMimeType("application/octet-stream");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pdf");
				extensionFile.setMimeType("application/pdf");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("docx");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("docm");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("dotx");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("dotm");
				extensionFile.setMimeType("application/msword");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xlsx");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xlsm");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xltx");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xltm");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xlsb");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("xlam");
				extensionFile.setMimeType("application/ms-excel");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pptx");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("pptm");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("potx");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("potm");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("ppam");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("ppsx");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("sldx");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("ppsm");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("sldm");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

				extensionFile = new ExtensionFile();
				extensionFile.setExtension("thmx");
				extensionFile.setMimeType("application/ms-powerpoint");
				delegado.create(extensionFile);

			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		} else {

			

			ExtensionFile extensionFile = new ExtensionFile();
			extensionFile.setExtension("rar");
			extensionFile.setMimeType("application/x-rar-compressed");
			extensionesFile = delegado
					.find_ExtensionFileByExtAndMime(extensionFile);
			if (extensionesFile == null || extensionesFile.isEmpty()) {
				extensionFile = new ExtensionFile();
				extensionFile.setExtension("rar");
				extensionFile.setMimeType("application/x-rar-compressed");
				delegado.create(extensionFile);
			}

		}
	}

	public void llenarEstadosDelDoc(Tree empresa) {
		Usuario usuEmpresa = new Usuario();
		usuEmpresa.setEmpresa(empresa);

		Doc_tipo tipo = new Doc_tipo();
		tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());

		tipo = delegado.findDocTipo(tipo, usuEmpresa);
		if (tipo == null) {

			tipo = new Doc_tipo();

			tipo.setDescripcion("formato");
			tipo.setNombre("formato");
			tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.createTipoDoc(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}

			tipo = new Doc_tipo();
			tipo.setDescripcion("registro");
			tipo.setNombre("registro");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
			try {
				tipo.setEmpresa(usuEmpresa.getEmpresa());
				delegado.createTipoDoc(tipo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}

			tipo = new Doc_tipo();
			tipo.setDescripcion("flujoParaleloTipoDoc");
			tipo.setNombre("flujoParaleloTipoDoc");
			tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
			try {
				tipo.setEmpresa(usuEmpresa.getEmpresa());
				delegado.createTipoDoc(tipo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}

			tipo = new Doc_tipo();

			tipo.setDescripcion("procedimientos");
			tipo.setNombre("procedimientos");

			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.createTipoDoc(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tipo = new Doc_tipo();

			tipo.setDescripcion("normas");
			tipo.setNombre("normas");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.createTipoDoc(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tipo = new Doc_tipo();

			tipo.setDescripcion("politicas");
			tipo.setNombre("politicas");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

			tipo.setEmpresa(empresa);
			try {
				delegado.createTipoDoc(tipo);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		// chequeamos siempre que halla un documento tipo registro
		tipo = new Doc_tipo();
		tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
		tipo = delegado.findDocTipo(tipo, usuEmpresa);
		if (tipo == null) {
			tipo = new Doc_tipo();
			tipo.setDescripcion("registro");
			tipo.setNombre("registro");
			tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
			try {
				tipo.setEmpresa(usuEmpresa.getEmpresa());
				delegado.createTipoDoc(tipo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		// chequeamos siempre que halla un documento flujo paralelo
		tipo = new Doc_tipo();
		tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
		tipo = delegado.findDocTipo(tipo, usuEmpresa);
		if (tipo == null) {
			tipo = new Doc_tipo();
			tipo.setDescripcion("flujoParaleloTipoDoc");
			tipo.setNombre("flujoParaleloTipoDoc");
			tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
			try {
				tipo.setEmpresa(usuEmpresa.getEmpresa());
				delegado.createTipoDoc(tipo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

	}

	

	public void llenarEstadosDelDoc() {

		List<Tree> empresas = delegado.findAllEmpresas();

		try {
			for (Tree empresa : empresas) {
				llenarEstadosDelDoc(empresa);
				// creamos un perfil por default en cada una de las empresas,
				// cuando agreguen un documento, ese perfil sera el seleccionado
				 llenarPerfilRoleDefault(empresa);
			}
		} catch (Exception e) {

		}
		// __________________________________________
		// INICIO ESTADOS DEL DOCUMENTO
		Doc_estado edo = new Doc_estado();
		edo.setCodigo(Utilidades.getObsoleto());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();

			edo.setDescripcion("obsoleto");
			edo.setNombre("obsoleto");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getBorrador());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();

			edo.setDescripcion("borrador");
			edo.setNombre("borrador");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getRechazado());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();

			edo.setDescripcion("rechazado");
			edo.setNombre("rechazado");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getSinFirmar());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();

			edo.setDescripcion("sinfirmar");
			edo.setNombre("sinfirmar");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getRechazadoAutomatico());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("rechazadoAutomatico");
			edo.setNombre("rechazadoAutomatico");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getRevisado());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("revisado");
			edo.setNombre("revisado");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}
		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getAprobado());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("aprobado");
			edo.setNombre("aprobado");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getVigente());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("vigente");
			edo.setNombre("vigente");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}
		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getEnAprobacion());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("enaprobacion");
			edo.setNombre("enaprobacion");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getEnRevision());
		edo = delegado.findDocEstado(edo);
		System.out.println("edo=" + edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("enrevision");
			edo.setNombre("enrevision");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getCanceladoByDuenio());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("canceladoByDuenio");
			edo.setNombre("canceladoByDuenio");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}
		// INICIO DEVOLUCION O RECHAZADO Y PASAR AL FIRMANTE ANTERUIOR DE UN
		// FLUJO

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getDevolucionfirmaflow());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("devolucionfirmaflow");
			edo.setNombre("devolucionfirmaflow");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getDevolucionstartflow());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("devolucionstartflow");
			edo.setNombre("devolucionstartflow");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getVencido());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("vencido");
			edo.setNombre("vencido");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getImpreso());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("impreso");
			edo.setNombre("impreso");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Doc_estado();
		edo.setCodigo(Utilidades.getImprimirsin());
		edo = delegado.findDocEstado(edo);
		if (edo == null) {
			edo = new Doc_estado();
			edo.setDescripcion("imprimirSin");
			edo.setNombre("imprimirSin");
			try {
				delegado.createDocEstado(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			}
		}
		// FIN DEVOLUCION O RECHAZADO Y PASAR AL FIRMANTE ANTERUIOR DE UN FLUJO

	}

	// FIN ESTADOS DEL DOCUMENTO
	public void llenarPermIndivDelSitema() {
		Operaciones edo = new Operaciones();
		List lstSta = null;
		Iterator it = null;

		// Control de tiempo y dias feriados
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListarDiasFeriados());

		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToListarDiasFeriados());
			edo.setOperacion(Utilidades.getToListarDiasFeriados());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		// guardar una plantilla de flujos para un documento tipo plantilla

		edo = new Operaciones();
		edo.setOperacion(Utilidades.gettoPlantillaInDocFlowParalelo());

		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.gettoPlantillaInDocFlowParalelo());
			edo.setOperacion(Utilidades.gettoPlantillaInDocFlowParalelo());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getTosolicitudimpresion());

		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getTosolicitudimpresion());
			edo.setOperacion(Utilidades.getTosolicitudimpresion());
			edo.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getTosolicitudimpresion0());

		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getTosolicitudimpresion0());
			edo.setOperacion(Utilidades.getTosolicitudimpresion0());
			edo.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToimprimirautorizacion());

		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToimprimirautorizacion());
			edo.setOperacion(Utilidades.getToimprimirautorizacion());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListarDiasHabiles());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToListarDiasHabiles());
			edo.setOperacion(Utilidades.getToListarDiasHabiles());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToAddDiaFeriado());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToAddDiaFeriado());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModDiasHabiles());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModDiasHabiles());
			edo.setOperacion(Utilidades.getToModDiasHabiles());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModDiaFeriado());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModDiaFeriado());
			edo.setOperacion(Utilidades.getToModDiaFeriado());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToDelDiaFeriado());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToDelDiaFeriado());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		// Grupos

		// ----------------------------------------------------

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getTolistgruposworkflow());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getTolistgruposworkflow());
			edo.setOperacion(Utilidades.getTolistgruposworkflow());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}

		// ----------------------------------------------------

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListGrupos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToListGrupos());
			edo.setOperacion(Utilidades.getToListGrupos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToAddGrupos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToAddGrupos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModGrupos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModGrupos());
			edo.setOperacion(Utilidades.getToModGrupos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToDelGrupos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToDelGrupos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		// area
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getTolistararea());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {

			edo.setDescripcion(Utilidades.getTolistararea());
			edo.setOperacion(Utilidades.getTolistararea());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		// profesiones
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListProfesiones());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {

			edo.setDescripcion(Utilidades.getToListProfesiones());
			edo.setOperacion(Utilidades.getToListProfesiones());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToAddProfesiones());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToAddProfesiones());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModProfesiones());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModProfesiones());
			edo.setOperacion(Utilidades.getToModProfesiones());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToDelProfesiones());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToDelProfesiones());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		// TipoDocumentos
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListTipoDocumentos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {

			edo.setDescripcion(Utilidades.getToListTipoDocumentos());
			edo.setOperacion(Utilidades.getToListTipoDocumentos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToAddTipoDocumentos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToAddTipoDocumentos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModTipoDocumentos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModTipoDocumentos());
			edo.setOperacion(Utilidades.getToModTipoDocumentos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToDelTipoDocumentos());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToDelTipoDocumentos());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		// usuariio------
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToListUsuarios());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToListUsuarios());
			edo.setOperacion(Utilidades.getToListUsuarios());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToAddUsuario());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToAddUsuario());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToModUsuario());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToModUsuario());
			edo.setOperacion(Utilidades.getToModUsuario());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToDelUsuario());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToDelUsuario());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}

		// este permiso es para alterar flujos de trabajo
		edo = new Operaciones();
		edo.setOperacion(Utilidades.getTomodflow());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getTomodflow());
			edo.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}

		edo = new Operaciones();
		edo.setOperacion(Utilidades.getToView());
		lstSta = delegado.findOperacionName(edo);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			edo.setDescripcion(Utilidades.getToView());
			edo.setPertenece_Arbol(Utilidades.getPertence_Arbol_and_Menu());
			try {
				delegado.create(edo);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}
		// YA ESTA PUBLICAR DOCUMENTO REPETRIDO, SE PUEDFE USAR PARA UN FUURO
		/*
		 * edo = new Operaciones();
		 * edo.setOperacion(Utilidades.getToPublicar());
		 * lstSta=delegado.findOperacionName(edo); it = lstSta.iterator();
		 * if(!it.hasNext() || lstSta.isEmpty() ){
		 * edo.setDescripcion(Utilidades.getToPublicar());
		 * edo.setPertenece_Arbol(Utilidades.getPertence_Arbol()); try {
		 * delegado.create(edo); } catch (EcologicaExcepciones ex) { //
		 * ex.printStackTrace(); } catch (RoleNoEncontrado ex) { //
		 * ex.printStackTrace(); } catch (RoleMultiples ex) { //
		 * ex.printStackTrace(); } }
		 */
		// verifCTX();
	}

	/*
	 * public void verifCTX(){ Context ctx =null; try { ctx=new
	 * InitialContext(); Enumeration ce = ctx.list("");
	 * System.out.println("empezAMOS CTX"); while(ce.hasMoreElements()){
	 * NameClassPair c = (NameClassPair)ce.nextElement();
	 * System.out.print("Class name="+c.getClassName());
	 * System.out.print("n.getName()="+c.getName()); if
	 * ("ecologicalpaper2".equalsIgnoreCase(c.getName())){ try {
	 * System.out.println("c.getName()="+c.getName()); DataSource ds =
	 * (DataSource) ctx.lookup(c.getName()); Connection con =
	 * ds.getConnection(); if (con!=null){ //
	 * System.out.println("grabando connection");
	 * ctx_aplication.setAttribute("connection",con); } } catch (Exception e) {
	 * e.toString(); } } } System.out.println("FIN CTX"); } catch (Exception e)
	 * {
	 * 
	 * } }
	 */

	public void contextDestroyed(ServletContextEvent evt) {
		// TODO add your code here e.g.:
		/*
		 * Connection con = (Connection)
		 * e.getServletContext().getAttribute("con"); try { con.close(); } catch
		 * (SQLException ignored) { } // close connection
		 */
	}

	public void llenaRaizrPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddRaiz());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddRaiz());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToMod());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToMod());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToDel());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToDel());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getConfSeguridad());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getConfSeguridad());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getConfseguridadglobal());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getConfseguridadglobal());
			ope.setPertenece_Arbol(Utilidades.getPertence_Menu());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Principal
	public void llenaPrincipalPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddPrincipal());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddPrincipal());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToMove());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToMove());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Area
	public void llenaAreaPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddArea());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddArea());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Proceso
	public void llenaProcesoPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddProceso());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddProceso());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Carpeta
	public void llenaCarpetaPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddCarpeta());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddCarpeta());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Cargo
	public void llenaCargoPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddCargo());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddCargo());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// LotesDeDocumentos
	public void llenaLotesDeDocumentosPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddLotesDeDocumentos());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddLotesDeDocumentos());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

	}

	// Documentos
	public void llenaDocumentosPermIndivDelSitema() {
		Operaciones ope = new Operaciones();

		ope.setOperacion(Utilidades.getToAddDocumentos());
		List lstSta = delegado.findOperacionName(ope);
		Iterator it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			// System.out.println("Creando  "+Utilidades.getToMod());
			ope.setDescripcion(Utilidades.getToAddDocumentos());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}
		}

		// svn inicio

		ope = new Operaciones();

		ope.setOperacion(Utilidades.getToadddocumentosvn());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToadddocumentosvn());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// // ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// // ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// // ex.printStackTrace();
			}

		}

		ope = new Operaciones();

		ope.setOperacion(Utilidades.getTodoflowrevision());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getTodoflowrevision());
			ope.setOperacion(Utilidades.getTodoflowrevision());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// // ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// // ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// // ex.printStackTrace();
			}

		}

		ope = new Operaciones();

		ope.setOperacion(Utilidades.getToviewcomentpublic());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToviewcomentpublic());
			ope.setOperacion(Utilidades.getToviewcomentpublic());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// // ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// // ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// // ex.printStackTrace();
			}

		}

		ope = new Operaciones();

		ope.setOperacion(Utilidades.getTodownload());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getTodownload());
			ope.setOperacion(Utilidades.getTodownload());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// // ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// // ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// // ex.printStackTrace();
			}

		}

		// svn fin

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToActivePermGroup());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToActivePermGroup());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// // ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// // ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// // ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToGivePermUser());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToGivePermUser());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToSaveDataBasic());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToSaveDataBasic());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToDoFlow());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToDoFlow());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToVincDoc());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToVincDoc());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToDoRegistros());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToDoRegistros());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToDoPublico());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToDoPublico());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToHistFlow());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToHistFlow());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToHistDoc());
		lstSta = delegado.findOperacionName(ope);
		it = lstSta.iterator();
		if (!it.hasNext() || lstSta.isEmpty()) {
			ope.setDescripcion(Utilidades.getToHistDoc());
			ope.setPertenece_Arbol(Utilidades.getPertence_Arbol());
			try {
				delegado.create(ope);
			} catch (EcologicaExcepciones ex) {
				// ex.printStackTrace();
			} catch (RoleNoEncontrado ex) {
				// ex.printStackTrace();
			} catch (RoleMultiples ex) {
				// ex.printStackTrace();
			}

		}
	}
}
