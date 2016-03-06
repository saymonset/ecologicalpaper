package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Utilidades {

	// LDAP------------------------INICIO
	private static final String distinguishedNameWin = "distinguishedName";
	private static final String dc = "dc";
	private static final String uid = "uid";// * uid (id de usuario), ésta es
											// una identificación única
											// obligatoria;

	private static final String cn = "cn";// * cn (nombre común), éste es el
											// nombre de la persona;
	private static final String mail = "mail";// * mail, ésta es la dirección de
												// correo electrónico de la
												// persona (por supuesto).
	private static final String u = "u";// * u (unidad organizacional), éste es
										// el departamento de la compañía para
										// la que trabaja la persona.
	private static final String o = "o";// * o (organización), ésta es la
										// compañía de la persona.
	private static final String ou = "ou";// * o (organización), ésta es la
											// compañía de la persona.
	private static final String sn = "sn";// * sn (apellido), éste es el
											// apellido de la persona.
	private static final String givenname = "givenname";// * givenname, éste es
														// el nombre de pila de
														// la persona;
	private static final String telephonenumber = "telephonenumber";
	private static final String streetAddress = "streetAddress";
	private static final String company = "company";
	private static final String department = "department";
	private static final String homePhone = "homePhone";
	private static final String otherTelephone = "otherTelephone";

	private static final String mobile = "mobile";

	// LDAP------------------------FIN
	private static final String am = "am";
	private static final String pm = "pm";
	private static final String inicializarHora = "00:00";
	private static final String lunes = "lunes";
	private static final String martes = "martes";
	private static final String miercoles = "miercoles";
	private static final String jueves = "jueves";
	private static final String viernes = "viernes";
	private static final String sabado = "sabado";
	private static final String domingo = "domingo";
	private static final int minutosNumeroConfigurar = 60;
	private static final int horasNumeroConfigurar = 160;
	private static final int horasAntesEnviaMails = 8;

	private static final int nomEmpresa = 0;// posicion 0
	private static final int usuariosEmpresa = 1;// posicion 1
	private static final int imgEmpresa = 2;// posicion 2
	private static final int fechaEmpresa = 3;// posicion 3
	private static final int identificadorEmpresa = 4;// posicion 4
	private static final int identificadorEmpresaEcological = 0;// posicion 0
	private static final double postgresMBmax = 16;
	// IMAGEN HEIGHT=317,WIDTH=748
	// NOMBRE,LICENCIA,IMAGEN DE FONDO,FECHA CREACION, IDENTIFICADOR DE ARREGLO
	private String[] clientes = {
			"ECOLOGICAL,10,img/tope.jpg,2007-5-25,0",
			"EUJOVANS,3,img/491798949_4416a9ca29_o.png,2008-02-04,1",
			"SupporTeam Consultores S.A.S,100,img/wfsupporteam.png,2011-06-22,2",
			"BANCO DE VENEZUELA,350,img/gruposantan.gif,2008-08-28,3",
			"Sodexo,100,img/sodexo.jpg,2011-02-23,4",
			"Policia Municipio Baruta,800,img/LOGO_POLICIA.jpg,2012-10-17,5",
			"DVINCI PANAMA CORP,100,img/dvinci.gif,2011-04-05,6" };

	private HashMap seguridad;
	private static final boolean activo = true;
	private static final boolean inactivo = false;
	private static final long esraiz = -1;
	private static final long nodoRaiz = -1;
	private static final long nodoPrincipal = 0;
	private static final long nodoArea = 1;
	private static final long nodoCargo = 2;
	private static final long nodoProceso = 3;
	private static final long nodoCarpeta = 4;
	private static final long nodoDocumento = 5;
	private static final long versionnodoDocumento = 6;

	private static final String consecutivoLength = "8";
	private static final String consecutivoVacio = "00000000";

	private static final long pertence_Menu = 0;
	private static final long pertence_Arbol = 1;
	private static final long pertence_Arbol_and_Menu = 2;

	private static final long raicesNegativasMenu = -9999L;

	private static final int maxRegisterMostrar = 20;
	private static final int maxRegisterMostrarAll = 20;
	private int maxRegisterMostrarForTable = 15;
	private static final int maxRegisterMostrarForDocs = 20;

	private int verNumeroDeRegistrosAll = 99000;
	private int verNumeroDeRegistros = 20;
	private int verNumeroDeRegistrosSVN = 100;
	private int verpaginatorMaxPages;

	private static final int minRegisterMostrar = 15;

	private static final char searchNombreDoc = 'n';
	private static final char searchConsecutivoDoc = 'c';

	private static final char none = '0';
	private static final char login = 'l';
	private static final char apellido = 'a';
	private static final char nombre = 'n';
	private static final char cargo = 'c';

	private static final char commilla = '\'';

	private static final String treeprocesando = "treeprocesando";
	private static final String refrescarArbolWorkFlows = "refrescarArbolWorkFlows";

	private static final String obtenArbolSeguridad = "obtenArbolSeguridad";
	private static final String listarTreeEmpresas = "listarTreeEmpresas";
	private static final String listarflowsParaleCambiarNomComent = "listarflowsParaleCambiarNomComent";
	private static final String listarDiasFeriados = "listarDiasFeriados";
	private static final String finexitoso = "finexitoso";
	private static final String aplicacion = "aplicacion";
	private static final String listarDocumentos = "aplicaciondetalles";
	private static final String finexitosorefresca = "finexitoso";
	private static final String listarConfiguracion = "listarConfiguracion";
	private static final String listarRoles = "listarRoles";
	private static final String listarUsuarios = "listarUsuarios";
	private static final String listarProfesion = "listarProfesion";
	private static final String listarAreaDocumentos = "listarAreaDocumentos";

	private static final String listarDiasHabiles = "listarDiasHabiles";
	private static final String listarPaises = "listarPaises";
	private static final String listarExtensionFile = "listarExtensionFile";
	private static final String listarExtensionFileHijos = "listarExtensionFileHijos";
	private static final String listarEstados = "listarEstados";
	private static final String listarCiudad = "listarCiudad";
	private static final String listarCliente_EUJ = "listarCliente_EUJ";
	private static final String ListarFletes_EUJ = "ListarFletes_EUJ";

	private static final String listarRole = "listarRole";
	private static final String listarAplicacion = "aplicacion";
	private static final String listarSeguridadTree = "seguridadTree";

	private static final String docVinculados = "docVinculados";

	private static final String flowparalelo = "flowparalelo";

	private static final String listarDocumentoSearchPublicados = "listarDocumentoSearchPublicados";
	private static final String listarDoc_Tipo = "listarDoc_Tipo";
	private static final String editDocumentoReadOnly = "editDocumentoReadOnly";

	private static final String listarSVNUrlBase = "listarSVNUrlBase";
	private static final String listarSVNNombreAplicacion = "listarSVNNombreAplicacion";
	private static final String listarSVNTipodeAmbiente = "listarSVNTipodeAmbiente";
	private static final String listarSVNModulo = "listarSVNModulo";
	private static final String listarSvnExtension = "listarSvnExtension";
	private static final String solicitudImpresion = "solicitudImpresion";
	private static final String noClearSession = "noClearSession";

	private static final String listarFlowParalelo = "listarflowsParalelo";
	private static final String flows = "flows";

	private static final String flowsResponse = "flowsResponse";
	private static final String agregarDocumentosvn = "agregarDocumentosvn";
	private static final String agregarDocumentosvnUpload = "agregarDocumentosvnUpload";

	// HISTORICO
	private static final String razondeCambio = "razonDeCambio";
	private static final String eliminarUsuario = "eliminarUsuario";
	private static final String flowCambiarUsuarioParticipante = "flowCambiarUsuarioParticipante";
	private static final String historicoUsuarioActivo = "historicoUsuarioActivo";

	private static final String identifica1Tree = "identifica1Tree";
	private static final String identifica2Tree = "identifica2Tree";
	private static final String identifica3Tree = "identifica3Tree";
	private static final String identifica4Tree = "identifica4Tree";
	private static final String identifica5Tree = "identifica5Tree";
	private static final String identifica6Tree = "identifica6Tree";
	private static final String identifica7Tree = "identifica7Tree";
	private static final String identifica8Tree = "identifica8Tree";
	private static final String identifica9Tree = "identifica9Tree";
	private static final String identificaRaizTree = "identificaRaizTree";

	private static final String scanner = "scanner";

	private static final long usuarioEliminado = 0;
	private static final long treeEliminado = 1;
	private static final long treeMoverNodo = 2;
	private static final long participanteCambiado = 3;

	// TRANSPORTE EUJOVANS
	private static final long probarint = 8;
	private static final int estadosinemitir = 0;
	private static final long estadoemitido = 1;
	private static final int estadoejecutadopagado = 2;
	private static final int estadoejecutadosinpagar = 3;

	// __________________________________________________________________________________________
	private static final int calcularTiempoEnDias = 40;

	private static final String root = "root";

	private static final String tab2 = "2";
	private static final String tab1 = "1";
	private static final String tab0 = "0";

	// es eñl tipo de documento, puede ser sacop tambien
	private static final long OrigenDocumentoFlow = 10;
	private static final long OrigenFlowImpresor = 11;
	// private static final long OrigenFlowNoAlteraVersion = 12;

	private static final Boolean SequencialFlow = true;
	private static final Boolean CondicionalFlow = true;

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss a");
	public static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	public static final SimpleDateFormat sdfShowConvert1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdfShowConvert = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss a");
	public static final SimpleDateFormat sdfShowWithoutHour = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static final SimpleDateFormat sdfShowHora = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss a");
	public static final SimpleDateFormat sdfShow = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss a");
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat(
			"hh:mm:ss a");
	public static final SimpleDateFormat sdfTime24 = new SimpleDateFormat(
			"HH:mm");
	public static final SimpleDateFormat date1 = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final int longCampoNormal = 40;
	public static final int longCampoGrande = 3999;

	// __________________________________________________
	// seguridad para la base de datos
	// esto va en tabla estados
	private static final long malo = -21;
	// tipo de documento formato
	private static final long formatoTipoDoc = 1;
	private static final long registroTipoDoc = 2;
	private static final long flujoParaleloTipoDoc = 3;

	// -----------------------------------------------
	// para ver historicos del flow
	private static final long creadoByDuenioFlow = 1;
	private static final long asignadoByDuenioFlow = 2;
	// -------------------------------------------------

	private static final int fechaSumarRestarDias = 30;

	private static final long Obsoleto = 1;
	private static final long Borrador = 2;
	private static final long Rechazado = 3;// flow
	private static final long sinFirmar = 4;// flow
	private static final long RechazadoAutomatico = 5;// estra sin firmar y es
														// cancelado por el
														// dueñon deñl flujo
	private static final long revisado = 6;// flow
	private static final long aprobado = 7;// flow
	private static final long vigente = 8;
	private static final long enAprobacion = 9;
	private static final long enRevision = 10;
	private static final long canceladoByDuenio = 11;// flow
	private static final long devolucionFirmaFlow = 12;// flow
	private static final long devolucionstartflow = 13;// flow
	private static final long vencido = 14;// solicitud impresion
	private static final long impreso = 15;// solicitud impresion
	private static final long imprimirSin = 16;// solicitud impresion

	// __________________________________________________
	// SEGURIDAD DEL SISTEMA
	// ______________________________________________
	// seguridad del arbol
	// esto va en tabla operaciones
	// de los dos
	private static final String confSeguridad = "confSeguridad";
	private static final String confSeguridadGlobal = "confSeguridadGlobal";

	private static final String toPlantillaInDocFlowParalelo = "toPlantillaInDocFlowParalelo";
	private static final String toSolicitudImpresion = "toSolicitudImpresion";
	private static final String toSolicitudImpresion0 = "toSolicitudImpresion0";
	private static final String toImprimirAdministrar = "toImprimirAdministrar";

	// control Time
	private static final String toListarDiasFeriados = "toListarDiasFeriados";
	private static final String toListarDiasHabiles = "toListarDiasHabiles";
	private static final String toAddDiaFeriado = "toAddDiaFeriado";
	private static final String toModDiasHabiles = "toModDiasHabiles";
	private static final String toModDiaFeriado = "toModDiaFeriado";
	private static final String toDelDiaFeriado = "toDelDiaFeriado";

	// menu
	private static final String toListUsuarios = "toListUsuarios";
	private static final String toAddUsuario = "toAddUsuario";
	private static final String toModUsuario = "toModUsuario";
	private static final String toDelUsuario = "toDelUsuario";

	private static final String toListProfesiones = "toListProfesiones";
	private static final String toAddProfesiones = "toAddProfesiones";
	private static final String toModProfesiones = "toModProfesiones";
	private static final String toDelProfesiones = "toDelProfesiones";

	private static final String toListGrupos = "toListGrupos";
	private static final String toListGruposWorkflow = "toListGruposWorkflow";
	private static final String toAddGrupos = "toAddGrupos";
	private static final String toModGrupos = "toModGrupos";
	private static final String toDelGrupos = "toDelGrupos";

	private static final String toListTipoDocumentos = "toListTipoDocumentos";
	private static final String toAddTipoDocumentos = "toAddTipoDocumentos";
	private static final String toModTipoDocumentos = "toModTipoDocumentos";
	private static final String toDelTipoDocumentos = "toDelTipoDocumentos";

	private static final String toView = "toView";
	private static final String toMod = "toMod";
	private static final String toDel = "toDel";
	private static final String toMove = "toMove";
	private static final String toModFlow = "toModFlow";

	private static final String toAddRaiz = "toAddRaiz";
	// Principal
	private static final String toAddPrincipal = "toAddPrincipal";

	// Area
	private static final String toAddArea = "toAddArea";
	// proceso
	private static final String toAddProceso = "toAddProceso";
	// carpeta
	private static final String toAddCarpeta = "toAddCarpeta";

	private static final String toAddDocumentos = "toAddDocumentos";

	private static final String toAddDocumentoSvn = "toAddDocumentoSvn";

	// cargo
	private static final String toAddCargo = "toAddCargo";

	// Lotes de Documentos
	private static final String toAddLotesDeDocumentos = "toAddLotesDeDocumentos";

	// Documentos

	private static final String toDoFlow = "toDoFlow";
	private static final String toVincDoc = "toVincDoc";
	private static final String toDoRegistros = "toDoRegistros";
	private static final String toDoPublico = "toDoPublico";
	//

	// solo para configuracion de seguridad
	/*
	 * Guardar Datos Basicos Dar Permisos a Usuarios Activar Permiso de los
	 * Grupos
	 */
	private static final String toSaveDataBasic = "toSaveDataBasic";
	private static final String toGivePermUser = "toGivePermUser";
	private static final String toActivePermGroup = "toActivePermGroup";

	private static final String toHistFlow = "toHistFlow";
	private static final String toHistDoc = "toHistDoc";

	private static final String toListarArea = "toListarArea";
	private static final String toDoFlowRevision = "toDoFlowRevision";
	private static final String toDownload = "toDownload";
	private static final String toViewComentPublic = "toViewComentPublic";
	private static final String toRoleByDefault = "aDefaultPerfil";

	// __________________________________________________

	// TIPO DE ARCHIVO
	private static final String mimeTypeTextPlain = "text/plain";

	private static final int cambioParticipanteActual = 0;
	private static final int cambioParticipanteInFlow = 1;
	private static final int cambioParticipanteAllFlow = 2;

	//NUMERO DE ARCHIVOS A UPLOAD
	public static final int MAXFILES = 1;
	
	
	public static String getFlowsResponse() {
		return flowsResponse;
	}

	public static String getFlows() {
		return flows;
	}

	public static String getRemplazoCaracter(String str, char oldCaracter,
			char newCaracter) {
		if (str != null && str.length() > 0) {
			return str.replace(oldCaracter, newCaracter);
		} else {
			return "";
		}

	}

	public static Date formateada(Date fecha) {
		Locale locale = Locale.getDefault();
		String result;
		SimpleDateFormat formatter;
		locale = new Locale("es", "VE");

		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", locale);
		result = formatter.format(fecha);

		try {
			return formatter.parse(result);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String formatDateShow(String value, boolean withHour)
			throws Exception {
		if (value != null && value.length() > 0) {
			java.util.Date date = null;
			String dateCreation = null;
			if (withHour) {
				try {
					date = sdfShowConvert.parse(value);
					dateCreation = sdfShow.format(date);
				} catch (Exception ex) {
					date = sdfShowConvert1.parse(value);
					dateCreation = sdfShow.format(date);
				}
			} else {
				try {
					date = date1.parse(value);
					dateCreation = sdfShowWithoutHour.format(date);
				} catch (Exception ex) {
					date = sdfShowConvert1.parse(value);
					dateCreation = sdfShow.format(date);
				}
			}
			return dateCreation;
		}
		return "";
	}

	public static Date fechaCreacion() {
		String fecha = getSdf().format(new Date());
		Date date = null;
		try {
			date = getSdf().parse(fecha.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long getEsraiz() {
		return esraiz;
	}

	public static long getNodoRaiz() {
		return nodoRaiz;
	}

	public static long getNodoPrincipal() {
		return nodoPrincipal;
	}

	public static long getNodoArea() {
		return nodoArea;
	}

	public static long getNodoCargo() {
		return nodoCargo;
	}

	public static long getNodoProceso() {
		return nodoProceso;
	}

	public static long getNodoCarpeta() {
		return nodoCarpeta;
	}

	public static long getNodoDocumento() {
		return nodoDocumento;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static long getVersionnodoDocumento() {
		return versionnodoDocumento;
	}

	public static long getEnAprobacion() {
		return enAprobacion;
	}

	public static long getEnRevision() {
		return enRevision;
	}

	public static long getVigente() {
		return vigente;
	}

	public static long getRevisado() {
		return revisado;
	}

	/*
	 * public static long getVersionAnterior() { return VersionAnterior; }
	 */

	public static long getBorrador() {
		return Borrador;
	}

	public static long getObsoleto() {
		return Obsoleto;
	}

	public static long getRechazado() {
		return Rechazado;
	}

	public static long getOrigenDocumentoFlow() {
		return OrigenDocumentoFlow;
	}

	public static Boolean getSequencialFlow() {
		return SequencialFlow;
	}

	public static Boolean getCondicionalFlow() {
		return CondicionalFlow;
	}

	public static long getSinFirmar() {
		return sinFirmar;
	}

	public static SimpleDateFormat getSdf1() {
		return sdf1;
	}

	public static SimpleDateFormat getSdfShowConvert1() {
		return sdfShowConvert1;
	}

	public static SimpleDateFormat getSdfShowConvert() {
		return sdfShowConvert;
	}

	public static SimpleDateFormat getSdfShowWithoutHour() {
		return sdfShowWithoutHour;
	}

	public static SimpleDateFormat getSdfShow() {
		return sdfShow;
	}

	public static SimpleDateFormat getSdfTime() {
		return sdfTime;
	}

	public static SimpleDateFormat getSdfTime24() {
		return sdfTime24;
	}

	public static SimpleDateFormat getDate1() {
		return date1;
	}

	public static long getRechazadoAutomatico() {
		return RechazadoAutomatico;
	}

	public static int getLongCampoNormal() {
		return longCampoNormal;
	}

	public static int getLongCampoGrande() {
		return longCampoGrande;
	}

	public static char getApellido() {
		return apellido;
	}

	public static char getNombre() {
		return nombre;
	}

	public static char getCargo() {
		return cargo;
	}

	public static boolean isActivo() {
		return activo;
	}

	public static boolean isInactivo() {
		return inactivo;
	}

	public static String getFinexitoso() {
		return finexitoso;
	}

	public static char getLogin() {
		return login;
	}

	public static char getCommilla() {
		return commilla;
	}

	public static String getListarRoles() {
		return listarRoles;
	}

	public static String getListarUsuarios() {
		return listarUsuarios;
	}

	public static String getListarRole() {
		return listarRole;
	}

	public HashMap getSeguridad() {
		return seguridad;
	}

	public void setSeguridad(HashMap seguridad) {
		this.seguridad = seguridad;
	}

	public static String getToDoFlow() {
		return toDoFlow;
	}

	public static String getToView() {
		return toView;
	}

	public static String getRoot() {
		return root;
	}

	public static String getListarAplicacion() {
		return listarAplicacion;
	}

	public static String getTab2() {
		return tab2;
	}

	public static String getTab1() {
		return tab1;
	}

	public static String getListarDocumentos() {
		return listarDocumentos;
	}

	public int getVerNumeroDeRegistros() {
		return verNumeroDeRegistros;
	}

	public void setVerNumeroDeRegistros(int verNumeroDeRegistros) {
		this.verNumeroDeRegistros = verNumeroDeRegistros;
	}

	public int getVerpaginatorMaxPages() {
		verpaginatorMaxPages = verNumeroDeRegistros - 1;
		return verpaginatorMaxPages;
	}

	public void setVerpaginatorMaxPages(int verpaginatorMaxPages) {
		this.verpaginatorMaxPages = verpaginatorMaxPages;
	}

	public static String getListarProfesion() {
		return listarProfesion;
	}

	public static char getSearchNombreDoc() {
		return searchNombreDoc;
	}

	public static char getSearchConsecutivoDoc() {
		return searchConsecutivoDoc;
	}

	public static char getNone() {
		return none;
	}

	public static long getMalo() {
		return malo;
	}

	public static String getConsecutivoLength() {
		return consecutivoLength;
	}

	public static String getToListUsuarios() {
		return toListUsuarios;
	}

	public static String getToListProfesiones() {
		return toListProfesiones;
	}

	/*
	 * public static String getToMove() { return toMove; }
	 */

	public static String getConfSeguridad() {
		return confSeguridad;
	}

	public static String getToVincDoc() {
		return toVincDoc;
	}

	public static String getToDoRegistros() {
		return toDoRegistros;
	}

	public static String getToDoPublico() {
		return toDoPublico;
	}

	public static String getToAddPrincipal() {
		return toAddPrincipal;
	}

	public static String getToAddArea() {
		return toAddArea;
	}

	public static String getToAddProceso() {
		return toAddProceso;
	}

	public static String getToAddCarpeta() {
		return toAddCarpeta;
	}

	public static String getToAddCargo() {
		return toAddCargo;
	}

	public static String getToAddLotesDeDocumentos() {
		return toAddLotesDeDocumentos;
	}

	public static String getToAddDocumentos() {
		return toAddDocumentos;
	}

	public static String getToAddRaiz() {
		return toAddRaiz;
	}

	public static long getFormatoTipoDoc() {
		return formatoTipoDoc;
	}

	public static String getToSaveDataBasic() {
		return toSaveDataBasic;
	}

	public static String getToGivePermUser() {
		return toGivePermUser;
	}

	public static String getToActivePermGroup() {
		return toActivePermGroup;
	}

	public static long getPertence_Menu() {
		return pertence_Menu;
	}

	public static long getPertence_Arbol() {
		return pertence_Arbol;
	}

	public static long getPertence_Arbol_and_Menu() {
		return pertence_Arbol_and_Menu;
	}

	public static String getToListGrupos() {
		return toListGrupos;
	}

	public static String getToListTipoDocumentos() {
		return toListTipoDocumentos;
	}

	public static String getToAddUsuario() {
		return toAddUsuario;
	}

	public static String getToModUsuario() {
		return toModUsuario;
	}

	public static String getToDelUsuario() {
		return toDelUsuario;
	}

	public static String getToAddProfesiones() {
		return toAddProfesiones;
	}

	public static String getToModProfesiones() {
		return toModProfesiones;
	}

	public static String getToDelProfesiones() {
		return toDelProfesiones;
	}

	public static String getToAddGrupos() {
		return toAddGrupos;
	}

	public static String getToModGrupos() {
		return toModGrupos;
	}

	public static String getToDelGrupos() {
		return toDelGrupos;
	}

	public static String getToMod() {
		return toMod;
	}

	public static String getToDel() {
		return toDel;
	}

	public static String getToMove() {
		return toMove;
	}

	public static String getToAddTipoDocumentos() {
		return toAddTipoDocumentos;
	}

	public static String getToModTipoDocumentos() {
		return toModTipoDocumentos;
	}

	public static String getToDelTipoDocumentos() {
		return toDelTipoDocumentos;
	}

	public static long getRegistroTipoDoc() {
		return registroTipoDoc;
	}

	public static long getCanceladoByDuenio() {
		return canceladoByDuenio;
	}

	public static long getRaicesNegativasMenu() {
		return raicesNegativasMenu;
	}

	public static String getRazondeCambio() {
		return razondeCambio;
	}

	public static long getUsuarioEliminado() {
		return usuarioEliminado;
	}

	public static long getTreeEliminado() {
		return treeEliminado;
	}

	public static String getEliminarUsuario() {
		return eliminarUsuario;
	}

	public static long getTreeMoverNodo() {
		return treeMoverNodo;
	}

	public static int getMaxRegisterMostrar() {
		return maxRegisterMostrar;
	}

	public int getMaxRegisterMostrarForTable() {
		return maxRegisterMostrarForTable;
	}

	public void setMaxRegisterMostrarForTable(int maxRegisterMostrarForTable) {
		this.maxRegisterMostrarForTable = maxRegisterMostrarForTable;
	}

	public static String getFinexitosorefresca() {
		return finexitosorefresca;
	}

	public static String getConsecutivoVacio() {
		return consecutivoVacio;
	}

	public static String getHistoricoUsuarioActivo() {
		return historicoUsuarioActivo;
	}

	public static int getMinRegisterMostrar() {
		return minRegisterMostrar;
	}

	public static String getIdentifica1Tree() {
		return identifica1Tree;
	}

	public static String getIdentifica2Tree() {
		return identifica2Tree;
	}

	public static String getIdentificaRaizTree() {
		return identificaRaizTree;
	}

	public static String getIdentifica3Tree() {
		return identifica3Tree;
	}

	public static String getIdentifica4Tree() {
		return identifica4Tree;
	}

	public static String getIdentifica5Tree() {
		return identifica5Tree;
	}

	public static String getIdentifica6Tree() {
		return identifica6Tree;
	}

	public static String getIdentifica7Tree() {
		return identifica7Tree;
	}

	public static String getIdentifica8Tree() {
		return identifica8Tree;
	}

	public static String getIdentifica9Tree() {
		return identifica9Tree;
	}

	public static String getToHistFlow() {
		return toHistFlow;
	}

	public static String getToHistDoc() {
		return toHistDoc;
	}

	public static String getListarPaises() {
		return listarPaises;
	}

	public static String getListarEstados() {
		return listarEstados;
	}

	public static String getListarCiudad() {
		return listarCiudad;
	}

	public static String getListarCliente_EUJ() {
		return listarCliente_EUJ;
	}

	public static String getListarFletes_EUJ() {
		return ListarFletes_EUJ;
	}

	public static int getEstadosinemitir() {
		return estadosinemitir;
	}

	public static int getEstadoejecutadopagado() {
		return estadoejecutadopagado;
	}

	public static int getEstadoejecutadosinpagar() {
		return estadoejecutadosinpagar;
	}

	public static long getEstadoemitido() {
		return estadoemitido;
	}

	public static long getAprobado() {
		return aprobado;
	}

	public static String getDocVinculados() {
		return docVinculados;
	}

	public static long getProbarint() {
		return probarint;
	}

	public static String getListarConfiguracion() {
		return listarConfiguracion;
	}

	public String[] getClientes() {
		return clientes;
	}

	public void setClientes(String[] clientes) {
		this.clientes = clientes;
	}

	public static int getNomEmpresa() {
		return nomEmpresa;
	}

	public static int getUsuariosEmpresa() {
		return usuariosEmpresa;
	}

	public static int getImgEmpresa() {
		return imgEmpresa;
	}

	public static int getFechaEmpresa() {
		return fechaEmpresa;
	}

	public static int getIdentificadorEmpresa() {
		return identificadorEmpresa;
	}

	public static int getIdentificadorEmpresaEcological() {
		return identificadorEmpresaEcological;
	}

	public static int getMinutosNumeroConfigurar() {
		return minutosNumeroConfigurar;
	}

	public static int getHorasNumeroConfigurar() {
		return horasNumeroConfigurar;
	}

	public static String getLunes() {
		return lunes;
	}

	public static String getMartes() {
		return martes;
	}

	public static String getMiercoles() {
		return miercoles;
	}

	public static String getJueves() {
		return jueves;
	}

	public static String getViernes() {
		return viernes;
	}

	public static String getSabado() {
		return sabado;
	}

	public static String getDomingo() {
		return domingo;
	}

	public static String getInicializarHora() {
		return inicializarHora;
	}

	public static String getListarDiasHabiles() {
		return listarDiasHabiles;
	}

	public static String getListarDiasFeriados() {
		return listarDiasFeriados;
	}

	public static int getHorasAntesEnviaMails() {
		return horasAntesEnviaMails;
	}

	public static String getAm() {
		return am;
	}

	public static String getPm() {
		return pm;
	}

	public static String getToListarDiasFeriados() {
		return toListarDiasFeriados;
	}

	public static String getToListarDiasHabiles() {
		return toListarDiasHabiles;
	}

	public static String getToAddDiaFeriado() {
		return toAddDiaFeriado;
	}

	public static String getToModDiasHabiles() {
		return toModDiasHabiles;
	}

	public static String getToModDiaFeriado() {
		return toModDiaFeriado;
	}

	public static String getToDelDiaFeriado() {
		return toDelDiaFeriado;
	}

	public static long getAsignadoByDuenioFlow() {
		return asignadoByDuenioFlow;
	}

	public static long getCreadoByDuenioFlow() {
		return creadoByDuenioFlow;
	}

	public static double getPostgresMBmax() {
		return postgresMBmax;
	}

	public static String getUid() {
		return uid;
	}

	public static String getCn() {
		return cn;
	}

	public static String getMail() {
		return mail;
	}

	public static String getU() {
		return u;
	}

	public static String getO() {
		return o;
	}

	public static String getSn() {
		return sn;
	}

	public static String getGivenname() {
		return givenname;
	}

	public static String getOu() {
		return ou;
	}

	public static String getDc() {
		return dc;
	}

	public static String getTelephonenumber() {
		return telephonenumber;
	}

	public static String getStreetAddress() {
		return streetAddress;
	}

	public static String getCompany() {
		return company;
	}

	public static String getDepartment() {
		return department;
	}

	public static String getHomePhone() {
		return homePhone;
	}

	public static String getOtherTelephone() {
		return otherTelephone;
	}

	public static String getMobile() {
		return mobile;
	}

	public static String getDistinguishednamewin() {
		return distinguishedNameWin;
	}

	public static String getDistinguishedNameWin() {
		return distinguishedNameWin;
	}

	public static String getMimeTypeTextPlain() {
		return mimeTypeTextPlain;
	}

	public static String getListarextensionfile() {
		return listarExtensionFile;
	}

	public static String getListarextensionfilehijos() {
		return listarExtensionFileHijos;
	}

	public static String getTab0() {
		return tab0;
	}

	public static String getToadddocumentosvn() {
		return toAddDocumentoSvn;
	}

	public static String getTolistgruposworkflow() {
		return toListGruposWorkflow;
	}

	public static long getFlujoparalelotipodoc() {
		return flujoParaleloTipoDoc;
	}

	public static String getListarflowparalelo() {
		return listarFlowParalelo;
	}

	public static String getAgregardocumentosvn() {
		return agregarDocumentosvn;
	}

	public static String getAgregardocumentosvnupload() {
		return agregarDocumentosvnUpload;
	}

	public int getVerNumeroDeRegistrosSVN() {
		return verNumeroDeRegistrosSVN;
	}

	public void setVerNumeroDeRegistrosSVN(int verNumeroDeRegistrosSVN) {
		this.verNumeroDeRegistrosSVN = verNumeroDeRegistrosSVN;
	}

	public static int getCalculartiempoendias() {
		return calcularTiempoEnDias;
	}

	public String obtenIconoDoc(String nombre) {
		// buscamos la extension del usuario, si la encontramos, colocamos sun
		// icono
		String icono = "img/filetypes32/_default.gif";
		if (!isEmptyOrNull(nombre)) {
			String ext = nombre.substring(nombre.lastIndexOf(".") + 1,
					nombre.length());

			icono = "";
			icono = "img/filetypes32/" + ext + ".gif";
		}
		return icono;
	}
	
	/**
	 * Obtiene nombre de un archivo y deja su extension 
	 * */

	public static String obtenNombreExte(String nombre) {
		// buscamos la extension del usuario, si la encontramos, colocamos sun
		// icono
		if (!isEmptyOrNull2(nombre)) {
			if (nombre.indexOf(".") != -1) {
				String ext = nombre.substring(0, nombre.lastIndexOf(".") );
				return ext;
			}

		}
		return nombre;
	}
	/**
	 * Los archivos recientes de word.. stan hechos con XML , y el converter no los 
	 * procesa para generar pdf, por lo tanto verificamos si es un archivo xml para realizar una accion
	 * 
	 * */
	public static boolean obtenerXdelIcono(String icono){
		
		String x=obtenNombreExte(icono);
		x= x.substring(x.length()-1, x.length());
		if ("x".equalsIgnoreCase(x)){
			return true;
		}
		return false;
	}

	public boolean isEmptyOrNull(String valor) {
		boolean swVacioCadena = (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
		return swVacioCadena;
	}
	
	public static boolean isEmptyOrNull2(String valor) {
		boolean swVacioCadena = (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
		return swVacioCadena;
	}


	public static String getListardocTipo() {
		return listarDoc_Tipo;
	}

	public static String getListarsvnurlbase() {
		return listarSVNUrlBase;
	}

	public static String getListarsvnnombreaplicacion() {
		return listarSVNNombreAplicacion;
	}

	public static String getListarsvnmodulo() {
		return listarSVNModulo;
	}

	public static String getListarsvnextension() {
		return listarSvnExtension;
	}

	public static String getListarsvntipodeambiente() {
		return listarSVNTipodeAmbiente;
	}

	public static String getTomodflow() {
		return toModFlow;
	}

	public static String getFlowcambiarusuarioparticipante() {
		return flowCambiarUsuarioParticipante;
	}

	public static long getParticipantecambiado() {
		return participanteCambiado;
	}

	public static long getDevolucionfirmaflow() {
		return devolucionFirmaFlow;
	}

	public static String getListarflowsparalecambiarnomcoment() {
		return listarflowsParaleCambiarNomComent;
	}

	public static int getMaxregistermostrarall() {
		return maxRegisterMostrarAll;
	}

	public static int getCambioparticipanteactual() {
		return cambioParticipanteActual;
	}

	public static int getCambioparticipanteinflow() {
		return cambioParticipanteInFlow;
	}

	public static int getCambioparticipanteallflow() {
		return cambioParticipanteAllFlow;
	}

	public static long getDevolucionstartflow() {
		return devolucionstartflow;
	}

	public static String gettoPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;
	}

	public static long getVencido() {
		return vencido;
	}

	public static long getImpreso() {
		return impreso;
	}

	public static long getImprimirsin() {
		return imprimirSin;
	}

	public static String getSolicitudimpresion() {
		return solicitudImpresion;
	}

	public static String getTosolicitudimpresion() {
		return toSolicitudImpresion;
	}

	public static String getToimprimirautorizacion() {
		return toImprimirAdministrar;
	}

	public static String getConfseguridadglobal() {
		return confSeguridadGlobal;
	}

	public static String getTosolicitudimpresion0() {
		return toSolicitudImpresion0;
	}

	public static int getFechasumarrestardias() {
		return fechaSumarRestarDias;
	}

	public static String getListardocumentosearchpublicados() {
		return listarDocumentoSearchPublicados;
	}

	public static String getEditdocumentoreadonly() {
		return editDocumentoReadOnly;
	}

	public static String getListarareadocumentos() {
		return listarAreaDocumentos;
	}

	public static long getOrigenflowimpresor() {
		return OrigenFlowImpresor;
	}

	public static String getTodoflowrevision() {
		return toDoFlowRevision;
	}

	public static String getTodownload() {
		return toDownload;
	}

	public static String getTolistararea() {
		return toListarArea;
	}

	public static String getToviewcomentpublic() {
		return toViewComentPublic;
	}

	public static String getListartreeempresas() {
		return listarTreeEmpresas;
	}

	public static String getAplicacion() {
		return aplicacion;
	}

	public static String getListarseguridadtree() {
		return listarSeguridadTree;
	}

	public static String getFlowparalelo() {
		return flowparalelo;
	}

	public static String getNoclearsession() {
		return noClearSession;
	}

	public static String getObtenarbolseguridad() {
		return obtenArbolSeguridad;
	}

	public static String getTreeprocesando() {
		return treeprocesando;
	}

	public int getVerNumeroDeRegistrosAll() {
		return verNumeroDeRegistrosAll;
	}

	public void setVerNumeroDeRegistrosAll(int verNumeroDeRegistrosAll) {
		this.verNumeroDeRegistrosAll = verNumeroDeRegistrosAll;
	}

	public static String getScanner() {
		return scanner;
	}

	public static String getRefrescararbolworkflows() {
		return refrescarArbolWorkFlows;
	}

	public static int getMaxregistermostrarfordocs() {
		return maxRegisterMostrarForDocs;
	}

	public static String getTorolebydefault() {
		return toRoleByDefault;
	}

}
