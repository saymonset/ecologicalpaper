/*
 * ContextSessionRequest.java
 *
 * Created on July 1, 2007, 6:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.util;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.cliente.seguridad.ClienteSeguridadRole;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.primefaces.model.DualListModel;

/**
 * 
 * @author simon
 */

public class ContextSessionRequest extends Exception implements Serializable {

	private ServicioDelegado delegado = new ServicioDelegado();
	private FacesContext facesContext = null;
	private Map sessionScope, applicationScope, requestScope,
			requestParameterMap;
	private ResourceBundle resourceBundle;
	private ResourceBundle messages;
	// SEGURIDAD EN LOS NODOS____________________________
	private Map user_seguridad = null;
	private Seguridad seguridadTree;
	private Usuario user_logueado;
	// private String extensionesAceptadas =
	// "emb,dst,txt,xls,ppt,pps,ppd,acp,asf,bmp,png,csv,doc,ftl,gif,htm,html,jpg,jpeg,js,odf,odg,odp,sxi,ods,sxc,sxw,odt,rtf,shtml,tif,tiff,qtif,xml,pub,vsd,vss,vst,tgz,gz,tar,zip,pub,pdf"
	// +
	// ",docx,docm,dotx,dotm,xlsx,xlsm,xltx,xltm,xlsb,xlam,pptx,pptm,potx,potm,ppam,ppsx,sldx,ppsm,sldm,thmx";
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private Configuracion configuracion;
	private String idiomaBundle;
	private String paisBundle;
	private HttpSession session = getSession();
	private Locale locale = null;// new Locale("es", "VE");
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private static GregorianCalendar calendar = new GregorianCalendar();
	private DualListModel<Usuario> operacionesUsuario;
	private List<Usuario> invisibleItemsUsuario = new ArrayList<Usuario>();
	private List<Usuario> visibleItemsUsuario = new ArrayList<Usuario>();

	public double converBytesMbytes(double valorBytes) {
		double kbytes = valorBytes / 1024;
		double mbytes = kbytes / 1024;
		return Math.ceil(mbytes);

	}

	public enum TipoExcepcion {

		/** The ERROR. */
		ERROR,

		/** The INFORMACION. */
		INFORMACION,

		/** The ADVERTENCIA. */
		ADVERTENCIA
	}

	/** The id mensaje. */
	private String idMensaje = null;

	/** The mensaje. */
	private String mensaje = null;

	/** The tipo excepcion. */
	private TipoExcepcion tipoExcepcion = TipoExcepcion.ERROR;

	/**
	 * Instantiates a new general exception.
	 * 
	 * @param idMensaje
	 *            the mensaje
	 */
	public ContextSessionRequest(String idMensaje) {
		super(idMensaje);
		this.idMensaje = idMensaje;
		redirect();
	}

	/**
	 * Instantiates a new general exception.
	 * 
	 * @param idMensaje
	 *            the id mensaje
	 * @param mensaje
	 *            the mensaje
	 * @param tipoExcepcion
	 *            the tipo excepcion
	 */
	public ContextSessionRequest(String idMensaje, String mensaje,
			TipoExcepcion tipoExcepcion) {
		super();
		this.idMensaje = idMensaje;
		this.mensaje = mensaje;
		this.tipoExcepcion = tipoExcepcion;
	}

	/**
	 * Instantiates a new general exception.
	 * 
	 * @param idMensaje
	 *            the id mensaje
	 * @param tipoExcepcion
	 *            the tipo excepcion
	 */
	public ContextSessionRequest(String idMensaje, TipoExcepcion tipoExcepcion) {
		super();
		this.idMensaje = idMensaje;
		this.tipoExcepcion = tipoExcepcion;
	}

	/**
	 * Gets the tipo excepcion.
	 * 
	 * @return the tipo excepcion
	 */
	public TipoExcepcion getTipoExcepcion() {
		return tipoExcepcion;
	}

	/**
	 * Sets the tipo excepcion.
	 * 
	 * @param tipoExcepcion
	 *            the new tipo excepcion
	 */
	public void setTipoExcepcion(TipoExcepcion tipoExcepcion) {
		this.tipoExcepcion = tipoExcepcion;
	}

	/**
	 * Gets the mensaje.
	 * 
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Sets the mensaje.
	 * 
	 * @param mensaje
	 *            the new mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Gets the id mensaje.
	 * 
	 * @return the id mensaje
	 */
	public String getIdMensaje() {
		return idMensaje;
	}

	/**
	 * Sets the id mensaje.
	 * 
	 * @param idMensaje
	 *            the new id mensaje
	 */
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String redirect(String clase, String metodo, Exception e) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			response.sendRedirect("exitoso.jsf");
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("conecutivo1", clase);
			session.setAttribute("conecutivo2", metodo + "");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public String redirect() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			response.sendRedirect("exitoso.jsf");
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getView() {
		String viewId = "/bienvenido.jsf"; // or look this up somewhere
		return viewId;
	}

	/**
	 * Regular link to page
	 */
	public String getUrlLink() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = getView();
		String navUrl = context.getExternalContext().encodeActionURL(
				extContext.getRequestContextPath() + viewId);

		return navUrl;
	}

	//
	// __________________________________________________

	/*
	 * VALIDA RANGO DE NUMEROS
	 * 
	 * 
	 * 
	 * Public class validarNumeros {
	 * 
	 * Public static int exceptionThrower(String error) throws Exception { throw
	 * new Exception(error); }
	 * 
	 * Public static void main(String args[]) throws Exception { int valorMin =
	 * 1; int valorMax = 999; int valor = 1000;
	 * 
	 * valor = ( (valorMin <= valor) && (valor <= valorMax) ) ? valor :
	 * exceptionThrower("Valor fuera del rango");
	 * 
	 * System.out.println("Valor dentro del rango "); } }
	 */
	public void calcularMinutosHorasAcumuladas(
			FlowControlByUsuarioBean flowControlByUsuarioBean,
			Usuario usuario_logueado) {
		this.user_logueado = usuario_logueado;
		calcularMinutosHorasAcumuladas(flowControlByUsuarioBean);
	}

	public void calcularMinutosHorasAcumuladas(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {

		if ((flowControlByUsuarioBean.getHorasAsignadas() == 0)
				&& (flowControlByUsuarioBean.getMinutosAsignados() == 0)) {

			// no se hace nada
		} else {

			if (!flowControlByUsuarioBean.getFlow().isSecuencial()) {

				if (user_logueado == null) {
					calcularMinutosHoras(flowControlByUsuarioBean);
				} else {
					calcularMinutosHoras(flowControlByUsuarioBean,
							user_logueado);
				}
			} else if (flowControlByUsuarioBean.getFlow().isSecuencial()) {

				// obtenemos el ultimo que foirmo
				FlowControlByUsuarioBean ultimoEnFirmar = delegado
						.find_FlowControlIsSecuencialGetFechaFirmaUltimo(flowControlByUsuarioBean);
				if (ultimoEnFirmar == null) {// es el primero secuencial
					if (flowControlByUsuarioBean.getFlow_Participantes()
							.getFecha_firma() != null) {
					}
					if (user_logueado == null) {
						calcularMinutosHoras(flowControlByUsuarioBean);
					} else {
						calcularMinutosHoras(flowControlByUsuarioBean,
								user_logueado);
					}
				} else {
					if (ultimoEnFirmar.getFlow_Participantes().getFecha_firma() != null) {
						flowControlByUsuarioBean.setFechaemitido(ultimoEnFirmar
								.getFlow_Participantes().getFecha_firma());
						if (flowControlByUsuarioBean.getFlow_Participantes()
								.getFecha_firma() != null) {
						}
						if (user_logueado == null) {
							calcularMinutosHoras(flowControlByUsuarioBean);
						} else {
							calcularMinutosHoras(flowControlByUsuarioBean,
									user_logueado);
						}

					} else {
						// no hagas nada
					}
				}
			}
		}

	}

	public void calcularMinutosHoras(
			FlowControlByUsuarioBean flowControlByUsuarioBean,
			Usuario user_logueado) {
		this.user_logueado = user_logueado;
		calcularMinutosHoras(flowControlByUsuarioBean);
	}

	public void calcularMinutosHoras(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {
		session = getSession();
		if (session != null) {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
		}
		System.out.println("calcularMinutosHoras user_logueado=null:"
				+ (user_logueado == null));
		Date fecha = new Date();
		String fechaActual = Utilidades.sdfShowWithoutHour.format(fecha);
		Date horas = new Date();
		Date minutos = new Date();
		Date segundos = new Date();
		// LA HORA ACTUAL
		Integer h = Integer.parseInt(String.valueOf(horas.getHours()));
		// String laHora(String ho)
		Integer m = minutos.getMinutes();
		String meridiano = meridiano(String.valueOf(h));
		h = Integer.parseInt(laHora(String.valueOf(h)));

		Integer ini = 0;
		Integer fin = 0;
		Integer hActual = 0;
		Integer horasAcumuladas = 0;
		Integer minutosAcumuladas = 0;
		String fechaFirma = null;
		// FECHA DE LA
		// FIRMA-------------------------------------------------------------------
		if (flowControlByUsuarioBean.getFlow_Participantes().getFecha_firma() != null) {
			fechaFirma = Utilidades.sdfShowWithoutHour
					.format(flowControlByUsuarioBean.getFlow_Participantes()
							.getFecha_firma());
		}
		String horaMinutoFirma = null;
		Integer hFirma = 0;
		Integer minFirma = 0;
		String meridianoFirma = null;
		if (fechaFirma != null) {
			horaMinutoFirma = Utilidades.sdfTime
					.format(flowControlByUsuarioBean.getFlow_Participantes()
							.getFecha_firma());
			hFirma = Integer.parseInt(horaMinutoFirma.substring(0, 2));
			minFirma = Integer.parseInt(horaMinutoFirma.substring(3, 5));
			meridianoFirma = horaMinutoFirma.substring(9, 11);
		}
		// FIN FECHA DE LA
		// FIRMA-------------------------------------------------------------------

		// FECHA
		// EMITIDO-------------------------------------------------------------------
		String fechaEmitido = Utilidades.sdfShowWithoutHour
				.format(flowControlByUsuarioBean.getFechaemitido());
		String horaMinutoStart = Utilidades.sdfTime
				.format(flowControlByUsuarioBean.getFechaemitido());
		Integer hInicioFlow = Integer.parseInt(horaMinutoStart.substring(0, 2));
		Integer minInicioFlow = Integer.parseInt(horaMinutoStart
				.substring(3, 5));
		String meridianoInicioFlow = horaMinutoStart.substring(9, 11);

		// FIN FECHA
		// EMITIDO-------------------------------------------------------------------

		// SE CALCULA LOS DIAS QUE TIENE EL FLUJO
		// String fechaStr
		// =Utilidades.sdfShowWithoutHour.format(flowControlByUsuarioBean.getFechaemitido());
		Date fecha2 = null;
		try {
			fecha2 = Utilidades.sdfShowWithoutHour.parse(fechaEmitido
					.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long dias = Math.abs(numerodeDiasEnFechaCaducar(fecha2, null));
		long dias_hasta = Math.abs(numerodeDiasEnFechaCaducar(
				flowControlByUsuarioBean.getFlow_Participantes()
						.getFecha_firma(), null));
		// FIN SE CALCULA LOS DIAS QUE TIENE EL FLUJO
		// ------------------------------------DIAS
		// ANTERIORES----------------------------------------------
		boolean swPrimerDiaFlow = true;
		boolean swUltimoDia = false;
		// si el dia es cero, es el dia de lhoy
		System.out.println("diasAntesHoy=" + dias);
		System.out.println("dias_hasta=" + dias_hasta);
		boolean swEsFechaActual = false;
		boolean swEsFirmoMismoDia = false;
		boolean swUnaSolaVez = true;
		int j = 0;
		boolean swHFirmaFlowAm = false;
		boolean swHFirmaFlowPm = false;
		boolean swHinicioFlowAm = false;
		boolean swHinicioFlowPm = false;
		boolean swHoraAm = false;
		boolean swHoraPm = false;
		swEsFechaActual = fechaEmitido.equalsIgnoreCase(fechaActual);
		if (fechaFirma != null) {
			swEsFirmoMismoDia = fechaEmitido.equalsIgnoreCase(fechaFirma);
		}

		boolean swMuestraCalculoDias = false;
		System.out.println("Tope calculo en horas="
				+ Utilidades.getCalculartiempoendias());
		for (long i = dias; i >= dias_hasta; i--) {
			if (!swMuestraCalculoDias) {
				System.out.println("dias_hasta=" + dias_hasta);
				swMuestraCalculoDias = true;
			}
			j++;
			if (dias > Utilidades.getCalculartiempoendias()) {
				break;
			}

			// ________________________________________________________________________________________________________________

			// si es el ultimo dia
			swUltimoDia = (dias_hasta == i);
			// ________________________________________________________________________________________________________________
			Integer iniAm = 0;
			Integer finAm = 0;
			Integer iniPm = 0;
			Integer finPm = 0;
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.add(java.util.Calendar.DATE, -(int) i);
			com.ecological.dias.habiles.DiasHabiles dia = null;
			if (!isFeriado(cal.getTime())) {
				if (user_logueado != null) {
					dia = delegado.find_DiasHabilesByDia(user_logueado,
							diaSemanaInterno(cal.getTime()));
				}

				if (dia != null) {
					// inicializando
					// variables------------------------------------
					// sacamos horas de am
					if (isAmGood(dia)) {
						iniAm = Integer.valueOf(dia.getH_InicialAM().substring(
								0, 2));
						finAm = Integer.valueOf(dia.getH_FinalAM().substring(0,
								2));
					}
					// sacamos horas de pm
					if (isPmGood(dia)) {
						iniPm = Integer.valueOf(dia.getH_InicialPM().substring(
								0, 2));
						finPm = Integer.valueOf(dia.getH_FinalPM().substring(0,
								2));
					}
					if (meridianoInicioFlow
							.equalsIgnoreCase(Utilidades.getAm())) {
						swHinicioFlowAm = ((hInicioFlow >= iniAm) && (hInicioFlow < finAm));
					} else {
						swHinicioFlowPm = ((hInicioFlow >= iniPm) && (hInicioFlow < finPm));
					}

					if (fechaFirma != null) {
						if (meridianoFirma.equalsIgnoreCase(Utilidades.getAm())) {
							swHFirmaFlowAm = ((hFirma >= iniAm) && (hFirma < finAm));
						} else {
							swHFirmaFlowPm = ((hFirma >= iniPm) && (hFirma < finPm));
						}
					}
					if (meridiano.equalsIgnoreCase(Utilidades.getAm())) {
						swHoraAm = ((h >= iniAm) && (h < finAm));
					} else {
						swHoraPm = ((h >= iniPm) && (h < finPm));
					}
					// fin inicializando
					// variables------------------------------------
					if (!swUltimoDia) {
						if (swPrimerDiaFlow) {
							swPrimerDiaFlow = false;
							if (swHinicioFlowAm) {
								// sumamos una hora, y restamos 60 minutos
								// menos los mintos que ingreso en el flow
								hInicioFlow += 1;
								minutosAcumuladas += Math
										.abs(minInicioFlow - 60);
								// System.out.println("5horasAcumuladas="
								// + horasAcumuladas);
								horasAcumuladas += Math.abs(iniPm - finPm)
										+ Math.abs(finAm - hInicioFlow);
								System.out.println("5horasAcumuladas="
										+ horasAcumuladas);
							} else if (swHinicioFlowPm) {
								if ((hInicioFlow + 1) <= finPm) {
									// sumamos una hora, y restamos 60
									// minutos menos los mintos que ingreso
									// en el flow
									hInicioFlow += 1;
								}
								minutosAcumuladas += Math
										.abs(minInicioFlow - 60);
								System.out.println("6horasAcumuladas="
										+ horasAcumuladas);
								horasAcumuladas += Math
										.abs(finPm - hInicioFlow);
								// System.out.println("6horasAcumuladas="
								// + horasAcumuladas);
								// ----------
							}
						} else {// fin swPrimerDiaFlow
							// System.out.println("7horasAcumuladas="
							// + horasAcumuladas);
							horasAcumuladas += Math.abs(finAm - iniAm)
									+ (finPm - iniPm);
							// System.out.println("7horasAcumuladas="
							// + horasAcumuladas);
						}
					} else {// swUltimoDia
						if (swEsFechaActual || swEsFirmoMismoDia) {
							if (swHinicioFlowAm) {
								if (swHFirmaFlowAm) {
									// sumamos una hora, y restamos 60
									// minutos menos los mintos que ingreso
									// en el flow
									minutosAcumuladas += Math.abs(minInicioFlow
											- minFirma);
									System.out.println("10horasAcumuladas="
											+ horasAcumuladas);
									horasAcumuladas += Math.abs(hInicioFlow
											- hFirma);
									// System.out.println("10horasAcumuladas="
									// + horasAcumuladas);
								} else if (swHFirmaFlowPm) {
									if ((hInicioFlow + 1) <= finAm) {
										// sumamos una hora, y restamos 60
										// minutos menos los mintos que
										// ingreso en el flow
										hInicioFlow += 1;
										minutosAcumuladas += Math
												.abs(minInicioFlow - 60);
										// para los minutos de la firma, se
										// acumula lo que trae, la hora se
										// deja igual
										minutosAcumuladas += minFirma;
									} else {
										minutosAcumuladas += Math
												.abs(minInicioFlow - minFirma);
									}

									horasAcumuladas += Math.abs(hInicioFlow
											- finAm)
											+ Math.abs(iniPm - hFirma);
								} else if (swHoraAm) {
									if ((hInicioFlow + 1) <= h) {
										// sumamos una hora, y restamos 60
										// minutos menos los mintos que
										// ingreso en el flow
										hInicioFlow += 1;
										minutosAcumuladas += Math
												.abs(minInicioFlow - 60);
										minutosAcumuladas += m;
									} else {
										minutosAcumuladas += Math
												.abs(minInicioFlow - m);
									}

									horasAcumuladas += Math
											.abs(hInicioFlow - h);
								} else if (swHoraPm) {
									if ((hInicioFlow + 1) <= finAm) {
										// sumamos una hora, y restamos 60
										// minutos menos los mintos que
										// ingreso en el flow
										hInicioFlow += 1;
										minutosAcumuladas += Math
												.abs(minInicioFlow - 60);
										// para los minutos de la firma, se
										// acumula lo que trae, la hora se
										// deja igual
										minutosAcumuladas += m;
									} else {
										minutosAcumuladas += Math
												.abs(minInicioFlow - m);
									}

									// System.out.println("13horasAcumuladas="
									// + horasAcumuladas);
									horasAcumuladas += Math.abs(hInicioFlow
											- finAm)
											+ Math.abs(iniPm - h);
									System.out.println("13horasAcumuladas="
											+ horasAcumuladas);
								}
							} else if (swHinicioFlowPm) {
								// ---------
								if (swHFirmaFlowPm) {
									if ((hInicioFlow + 1) <= hFirma) {
										hInicioFlow += 1;
										minutosAcumuladas += Math
												.abs(minInicioFlow - 60);
										minutosAcumuladas += minFirma;
									} else {
										minutosAcumuladas += Math
												.abs(minInicioFlow - minFirma);
									}

									horasAcumuladas += Math.abs(hInicioFlow
											- hFirma);

								} else if (swHoraPm) {
									if ((hInicioFlow + 1) <= h) {
										hInicioFlow += 1;
										minutosAcumuladas += Math
												.abs(minInicioFlow - 60);
										minutosAcumuladas += m;
									} else {
										minutosAcumuladas += Math
												.abs(minInicioFlow - m);
										;
									}

									horasAcumuladas += Math
											.abs(hInicioFlow - h);
								} else {
									if ((hInicioFlow + 1) <= finPm) {
										hInicioFlow += 1;
									}
									minutosAcumuladas += Math
											.abs(minInicioFlow - 60);

									horasAcumuladas += Math.abs(hInicioFlow
											- finPm);
								}
								// ----------
							}
						} else {// fin del if swEsFechaActual
							if (swHFirmaFlowAm) {
								minutosAcumuladas += minFirma;
								horasAcumuladas += Math.abs(iniAm - hFirma);
							} else if (swHFirmaFlowPm) {
								minutosAcumuladas += minFirma;
								horasAcumuladas += Math.abs(iniAm - finAm)
										+ Math.abs(iniPm - hFirma);
								// }

							} else if (swHoraAm) {
								minutosAcumuladas += m;
								horasAcumuladas += Math.abs(iniAm - h);
							} else if (swHoraPm) {
								minutosAcumuladas += m;
								horasAcumuladas += Math.abs(iniAm - finAm)
										+ Math.abs(iniPm - h);
							} else if (meridiano.equalsIgnoreCase(Utilidades
									.getPm())) {
								if ((h < finPm) || (h == 12)) {// si es el
									// mediodia
									horasAcumuladas += Math.abs(iniAm - finAm);
								} else {// si ya paso todo el dia
									horasAcumuladas += Math.abs(iniAm - finAm)
											+ Math.abs(finPm - iniPm);
								}

							}
						}
					}// fin del else

				}
			}
		}

		do {
			if (minutosAcumuladas >= 60) {
				minutosAcumuladas = minutosAcumuladas - 60;
				horasAcumuladas += 1;
			}

		} while (minutosAcumuladas >= 60);

		flowControlByUsuarioBean.setHorasAcumuladas(horasAcumuladas);
		flowControlByUsuarioBean.setMinutosAcumulados(minutosAcumuladas);
	}

	public boolean isAmGood(com.ecological.dias.habiles.DiasHabiles dia) {
		boolean swDiAm = false;

		if (dia.getH_InicialAM() != null && dia.getH_FinalAM() != null
				&& !dia.getH_InicialAM().equalsIgnoreCase("00:00")
				&& !dia.getH_FinalAM().equalsIgnoreCase("00:00")) {
			// System.out.println("dia.getH_InicialAM()="+dia.getH_InicialAM());
			// System.out.println("dia.getH_FinalAM()="+dia.getH_FinalAM());
			swDiAm = true;
		}
		return swDiAm;
	}

	public boolean isPmGood(com.ecological.dias.habiles.DiasHabiles dia) {
		boolean swDiPm = false;
		if (dia.getH_InicialPM() != null && dia.getH_FinalPM() != null
				&& !dia.getH_InicialPM().equalsIgnoreCase("00:00")
				&& !dia.getH_FinalPM().equalsIgnoreCase("00:00")) {
			// System.out.println("dia.getH_InicialPM()="+dia.getH_InicialPM());
			// System.out.println("dia.getH_FinalPM()="+dia.getH_FinalPM());
			swDiPm = true;
		}
		return swDiPm;
	}

	public boolean isFeriado(Date fecha) {
		boolean swDiaFeeriado = false;
		// FLOWCONTROLBYUSUARIOBEAN
		// FlowControlByUsuarioBean lista = delegado.
		// BUSCAMOS QUE NO SEA UN DIA FERIADO
		String fechaActual = Utilidades.sdfShowWithoutHour.format(fecha);
		session = getSession();
		if (session != null) {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
		}
		System.out.println("user_logueado=null:" + (user_logueado == null));

		List<DiasFeriadosBean> diasFeriadosBeans = delegado
				.find_allDiasFeriadosBean(user_logueado);
		if (diasFeriadosBeans != null && !diasFeriadosBeans.isEmpty()) {
			for (DiasFeriadosBean d : diasFeriadosBeans) {
				d.setFechaStr(Utilidades.sdfShowWithoutHour.format(d
						.getFechaonly()));
				/*
				 * System.out.println("fechaActual="+fechaActual);
				 * System.out.println("d.getFechaStr()="+d.getFechaStr());
				 */
				if (fechaActual.equalsIgnoreCase(d.getFechaStr())) {
					swDiaFeeriado = true;
				}
			}
		}
		return swDiaFeeriado;
	}

	public String sacarSlashFiltro(String nombre1) {
		StringTokenizer kk = new StringTokenizer(nombre1, "\\");
		String nombre = "";
		while (kk.hasMoreElements()) {
			nombre = (String) kk.nextToken();
		}

		return nombre;

	}

	public String establecerHora() { // Obtiene la hora del sistema

		Date horas = new Date();
		Date minutos = new Date();
		Date segundos = new Date();

		String h = String.valueOf(horas.getHours());
		String m = String.valueOf(minutos.getMinutes());
		String s = String.valueOf(segundos.getSeconds());

		String hora = laHora(h) + ":" + m + ":" + s + " " + meridiano(h);

		return (hora);

	}// establecerHora

	public String horaCompleta() {
		Date horas = new Date();
		Date minutos = new Date();
		Date segundos = new Date();
		String h = String.valueOf(horas.getHours());
		if (h.length() == 1) {
			h = "0" + h;
		}
		String m = String.valueOf(minutos.getMinutes());
		if (m.length() == 1) {
			m = "0" + m;
		}
		String s = String.valueOf(segundos.getSeconds());
		if (s.length() == 1) {
			s = "0" + s;
		}
		String meridiano = meridiano(h);
		String hCompleta = laHora(h) + ":" + m + ":" + s + " " + meridiano(h);
		return hCompleta;
	}

	public String laHora(String ho) { // Convierte la hora militar

		int a = Integer.parseInt(ho);

		String horas[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11" };
		String retorno = "";

		if (a == 0) {
			retorno = "12";
		} else if (a >= 13 && a <= 23) {
			retorno = horas[a - 13];
		} else {
			retorno = ho;
		}
		return (retorno);

	}// hora

	public String diaSemanaInterno() {
		String DiaSemanaLetra = "";
		DiaSemanaLetra = Utilidades.getDomingo();
		try {
			messages = getResourceBundle("com.ecological.resource.ecologicalpaper");
			Calendar DiaSemana = Calendar.getInstance();
			int NumeroDia;
			// este método nos devuelve el día se la semana en números
			NumeroDia = DiaSemana.get(Calendar.DAY_OF_WEEK);
			if (NumeroDia == 1) {
				DiaSemanaLetra = Utilidades.getDomingo();
			} else if (NumeroDia == 2) {
				DiaSemanaLetra = Utilidades.getLunes();
			} else if (NumeroDia == 3) {
				DiaSemanaLetra = Utilidades.getMartes();
			} else if (NumeroDia == 4) {
				DiaSemanaLetra = Utilidades.getMiercoles();
			} else if (NumeroDia == 5) {
				DiaSemanaLetra = Utilidades.getJueves();
			} else if (NumeroDia == 6) {
				DiaSemanaLetra = Utilidades.getViernes();
			} else if (NumeroDia == 7) {
				DiaSemanaLetra = Utilidades.getSabado();
			}
		} catch (Exception e) {
		}

		return DiaSemanaLetra;
	}

	public String diaSemanaInterno(Date date) {
		String DiaSemanaLetra = "";

		try {
			SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Calendar DiaSemana = java.util.Calendar.getInstance();
			String dateCreation = date1.format(date);

			int anio = Integer.parseInt(dateCreation.substring(0, 4));
			int mes = Integer.parseInt(dateCreation.substring(5, 7));
			int dia = Integer.parseInt(dateCreation.substring(8, 10));

			// restamos uno al mes, va de 0 a 11
			DiaSemana.set(anio, mes - 1, dia);

			int NumeroDia;
			// este método nos devuelve el día se la semana en números

			NumeroDia = DiaSemana.get(DiaSemana.DAY_OF_WEEK);

			if (NumeroDia == 1) {
				DiaSemanaLetra = Utilidades.getDomingo();
			} else if (NumeroDia == 2) {
				DiaSemanaLetra = Utilidades.getLunes();
			} else if (NumeroDia == 3) {
				DiaSemanaLetra = Utilidades.getMartes();
			} else if (NumeroDia == 4) {
				DiaSemanaLetra = Utilidades.getMiercoles();
			} else if (NumeroDia == 5) {
				DiaSemanaLetra = Utilidades.getJueves();
			} else if (NumeroDia == 6) {
				DiaSemanaLetra = Utilidades.getViernes();
			} else if (NumeroDia == 7) {
				DiaSemanaLetra = Utilidades.getSabado();
			}
		} catch (Exception e) {
		}

		return DiaSemanaLetra;
	}

	public String diaSemana() {
		messages = getResourceBundle("com.ecological.resource.ecologicalpaper");
		Calendar DiaSemana = Calendar.getInstance();
		int NumeroDia;
		// este método nos devuelve el día se la semana en números
		NumeroDia = DiaSemana.get(Calendar.DAY_OF_WEEK);
		String DiaSemanaLetra = "";
		if (NumeroDia == 1) {
			DiaSemanaLetra = messages.getString(Utilidades.getDomingo());
		} else if (NumeroDia == 2) {
			DiaSemanaLetra = messages.getString(Utilidades.getLunes());
		} else if (NumeroDia == 3) {
			DiaSemanaLetra = messages.getString(Utilidades.getMartes());
		} else if (NumeroDia == 4) {
			DiaSemanaLetra = messages.getString(Utilidades.getMiercoles());
		} else if (NumeroDia == 5) {
			DiaSemanaLetra = messages.getString(Utilidades.getJueves());
		} else if (NumeroDia == 6) {
			DiaSemanaLetra = messages.getString(Utilidades.getViernes());
		} else if (NumeroDia == 7) {
			DiaSemanaLetra = messages.getString(Utilidades.getSabado());
		}

		return DiaSemanaLetra;
	}

	public String meridiano(String ho) { // Establece si la hora es pm o am

		int b = Integer.parseInt(ho);

		String retorno = "";

		if (b >= 12 && b <= 23) {
			retorno = Utilidades.getPm();
		} else {
			retorno = Utilidades.getAm();
		}
		return (retorno);

	}// meridiano

	public void guardamosHistoricoActivoDelDocumento(Usuario user_logueado,
			Doc_maestro doc_maestro, boolean actuBorrador, boolean darPublico,
			boolean verDetalles, boolean verVinculados, boolean verSometerFlow,
			boolean nuevaVerVig, boolean deshNuevaVer, boolean genReg,
			String comentario) {

		// aqui guardamos el historico del quien lo hizo
		Doc_historicoActivoMaestro doc_historicoActivoMaestro = new Doc_historicoActivoMaestro();

		// buscamos si eitse el doc maestro ya en historico

		doc_historicoActivoMaestro.setDoc_maestro(doc_maestro);
		doc_historicoActivoMaestro.setUsuario(user_logueado);
		doc_historicoActivoMaestro = delegado
				.findDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
		if (doc_historicoActivoMaestro == null) {

			doc_historicoActivoMaestro = new Doc_historicoActivoMaestro();
			doc_historicoActivoMaestro.setDoc_maestro(doc_maestro);
			doc_historicoActivoMaestro.setUsuario(user_logueado);
			doc_historicoActivoMaestro.setStatus(Utilidades.isActivo());
			delegado.create(doc_historicoActivoMaestro);
		}
		doc_historicoActivoMaestro = delegado
				.findDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
		if (doc_historicoActivoMaestro != null) {
			Doc_historicoActivoDetalle doc_historicoActivoDetalle = new Doc_historicoActivoDetalle();
			doc_historicoActivoDetalle.setStatus(Utilidades.isActivo());
			doc_historicoActivoDetalle
					.setDoc_histActMaestro(doc_historicoActivoMaestro);
			doc_historicoActivoDetalle.setActuBorrador(actuBorrador);
			doc_historicoActivoDetalle.setDarPublico(darPublico);
			doc_historicoActivoDetalle.setVerDetalles(verDetalles);
			doc_historicoActivoDetalle.setVerVinculados(verVinculados);
			doc_historicoActivoDetalle.setVerSometerFlow(verSometerFlow);
			doc_historicoActivoDetalle.setNuevaVerVig(nuevaVerVig);
			doc_historicoActivoDetalle.setDeshNuevaVer(deshNuevaVer);
			doc_historicoActivoDetalle.setGenReg(genReg);
			doc_historicoActivoDetalle.setFecha(new Date());
			doc_historicoActivoDetalle.setComentario(comentario);

			delegado.create(doc_historicoActivoDetalle);

		}

		// _____________________________________________________________________________________

	}

	public List filtrarSelectItems(List items) {
		List<SelectItem> filtroArray = new ArrayList();
		List<SelectItem> filtroListo = new ArrayList();

		HashMap filtroHash = new HashMap();
		filtroArray = items;
		for (SelectItem item : filtroArray) {

			if (!filtroHash.containsKey(item.getValue())) {
				filtroHash.put(item.getValue(), item.getValue());
				filtroListo.add(item);
			}

		}
		// invisibleItems = customizePageBean.getInvisibleItems();
		return filtroListo;
	}

	public String remplazarCarcateresENie(String cadena) {
		String nameFile = cadena;
		nameFile = nameFile.replace("ñ", "ñ");
		nameFile = nameFile.replace("Ñ", "Ñ");
		return nameFile;
	}

	public void usuarioDesconectado() {

		String theURL = "./errorPage.jsf";

		HttpSession session = null;

		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		if (FacesContext.getCurrentInstance() != null && faces != null
				&& context != null) {
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(true);
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			HttpServletResponse response = (HttpServletResponse) context
					.getResponse();
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();
			if (user_logueado != null) {
				if (getResourceBundle("com.ecological.resource.ecologicalpaper") != null) {
					ResourceBundle messages = getResourceBundle("com.ecological.resource.ecologicalpaper");
					// request.setAttribute("usuario_nologueado",messages.getString("usuario_nologueado"));
					// response.sendRedirect(theURL);
					request.setAttribute("usuario_nologueado",
							messages.getString("usuario_nologueado"));
					RequestDispatcher reqdisp = request
							.getRequestDispatcher("/errorPage.jsf");
					try {
						reqdisp.forward(request, response);
					} catch (IOException ex) {
						ex.printStackTrace();
					} catch (ServletException ex) {
						ex.printStackTrace();
					}

				}
			}

		}
	}

	public void givePermparaverToGroup(
			Seguridad_Role_Lineal seguridad_Role_Lineal2) {
		// verificamos que el role tenga permisos para veer

		if (seguridad_Role_Lineal2.isToView()) {
			List<Roles_Usuarios> roles_Usuarios = delegado
					.findRoles_AND_Usuario(seguridad_Role_Lineal2.getRole());
			for (Roles_Usuarios rol_usu : roles_Usuarios) {
				// damos permisos para ver hasta padres abuelos
				ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				// grabamos la seguridad del nodo
				seguridad_User_Lineal.setUsuario(rol_usu.getUsuario());
				seguridad_User_Lineal.setTree(seguridad_Role_Lineal2.getTree());
				seguridad_User_Lineal.setToView(true);
				clienteSeguridad
						.grabarOperacionesInConfiguracion(seguridad_User_Lineal);
			}
		}
	}

	public Tree llegarHastadAbuelosPadres(Tree padre) {
		List abuelos = delegado.findAllTreePadresAbuelos(padre);
		Iterator it = abuelos.iterator();
		if (it.hasNext()) {
			Tree tree = (Tree) it.next();
			if (!tree.equals(padre)) {
				return tree;
			} else {
				// para que salga del ciclo
				padre.setTiponodo(Utilidades.getNodoRaiz());
				return padre;
			}

		} else {
			// para que salga del ciclo
			padre.setTiponodo(Utilidades.getNodoRaiz());
			return padre;
		}
	}

	// ____________________TREE_______________________________________________
	public boolean findExisteNombreRaiz(Tree raiz) {
		boolean existe = false;
		List<Tree> lista = delegado.findExisteNombreRaiz(raiz);
		if (lista != null && !lista.isEmpty()) {
			existe = true;
		}
		return existe;
	}

	public boolean validaHijosMismoNombreError(Tree treeActual, Tree nuevo) {
		if (treeActual != null) {
			List<Tree> lista = new ArrayList();
			lista.clear();
			// buscamos todos los hijos del nodo actual
			lista = llegarHastaHijosTodos(treeActual);
			if (lista != null) {
				for (Tree hijo : lista) {
					List<Tree> hayRegistro = delegado.hayRegistros(hijo, nuevo);
					if (!hayRegistro.isEmpty()) {
						return true;
					}
				}

			}
		}
		return false;
	}

	public Tree validaHijosMismoNombreSinVerError(Tree treeActual, Tree nuevo) {
		if (treeActual != null) {
			List<Tree> lista = new ArrayList();
			lista.clear();
			// buscamos todos los hijos del nodo actual
			lista = llegarHastaHijosTodos(treeActual);
			if (lista != null) {
				for (Tree hijo : lista) {
					List<Tree> hayRegistro = delegado.hayRegistros(hijo, nuevo);
					if (!hayRegistro.isEmpty() && hayRegistro.size() > 0) {
						return hayRegistro.get(0);
					}
				}

			}
		}
		return null;
	}

	// ______solo para hijos.. de mayor amenor ..
	public List<Tree> encontarTodosLosTreeHijos(List objeto, Tree nodoPadre,
			long tipo) {
		objeto.add(nodoPadre);
		List<Tree> lista = null;
		lista = llegarHastaHijosTodos(nodoPadre, tipo);

		for (Tree hijos : lista) {
			encontarTodosLosTreeHijos(objeto, hijos, tipo);
		}
		return objeto;
	}

	public List<Tree> encontarTodosLosTreeHijos(List objeto, Tree nodoPadre) {
		objeto.add(nodoPadre);
		List<Tree> lista = null;
		lista = llegarHastaHijosTodos(nodoPadre);
		if (lista != null) {
			for (Tree hijos : lista) {
				encontarTodosLosTreeHijos(objeto, hijos);
			}
		}
		return objeto;
	}

	public List<Tree> llegarHastaHijosTodos(Tree nodoPadre) {
		if (nodoPadre != null && nodoPadre.getNodo() != null) {
			List<Tree> nodosHijosLst = delegado
					.findAllHeredaTreeHijos(nodoPadre.getNodo());
			if (nodosHijosLst != null) {
				return nodosHijosLst;
			} else {
				return null;
			}
		}
		return null;
	}

	public List<Tree> llegarHastaHijosTodos(Tree nodoPadre, long tiponodo) {
		List<Tree> Lista = new ArrayList();
		if (nodoPadre != null && nodoPadre.getNodo() != null) {
			List<Tree> nodosHijosLst = null;
			if (tiponodo - Utilidades.getNodoRaiz() == 0) {

				Lista = delegado
						.findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(nodoPadre
								.getNodo());

			} else if (tiponodo - Utilidades.getNodoPrincipal() == 0) {
				Lista = delegado
						.findAllHeredaTreeHijosMenosDocumentos(nodoPadre
								.getNodo());
			} else {

				// TODOS
				Lista = delegado.findAllHeredaTreeHijos(nodoPadre.getNodo());
			}

		}
		return Lista;
	}

	public List<Tree> llegarHastaHijosTodosDeleted(Tree nodoPadre,
			boolean inactivo) {
		if (nodoPadre != null && nodoPadre.getNodo() != null) {
			List<Tree> nodosHijosLst = delegado.findAllHeredaTreeHijosDeleted(
					nodoPadre.getNodo(), inactivo);
			if (nodosHijosLst != null) {
				return nodosHijosLst;
			} else {
				return null;
			}
		}
		return null;
	}

	// ______FIN solo para hijos.. de mayor amenor ..

	// ECONTRAMOS TANTO HIJOS COMO PADRES
	public List<Tree> AbueloEHijos(Tree tree, List<Tree> lista) {
		lista = llegarAbuelosPadresRaiz(tree, lista);
		lista = encontarTodosLosTreeHijos(lista, tree);
		return lista;
	}

	// FIN ECONTRAMOS TANTO HIJOS COMO PADRES

	// ste usa el metodo de arriba para obtener todos los padres abuelos hasta
	// la raiz
	public List<Tree> llegarAbuelosPadresRaiz(Tree tree, List<Tree> lista) {
		boolean swHayPadre = true;

		while (swHayPadre) {
			lista.add(tree);
			tree = llegarHastadAbuelosPadres(tree);
			// datr permisologia de vista al nodo
			swHayPadre = tree.getTiponodo() != Utilidades.getNodoRaiz();

		}
		return lista;
	}

	// __________FIN__________TREE_______________________________________________

	public String incInUnoString(String string) {
		// int i = Integer.valueOf(num);
		StringBuffer numNew = new StringBuffer("");
		boolean Sw = false;
		int valor = 0;
		StringBuffer retorna = new StringBuffer();
		if (string == null) {
			string = "0";
		}
		char[] arreglo = string.toCharArray();
		if (arreglo.length > 0) {
			char car = arreglo[arreglo.length - 1];
			if ((car != '9') && ((car != 'z') || (car != 'Z'))) {
				char oldcar = car;
				car = movementChar(car);
				arreglo[arreglo.length - 1] = car;
				for (int h = 0; h < arreglo.length; h++) {
					retorna.append(arreglo[h]);
				}

			}
			if (car == '9') {
				retorna.append(string).append("0");
			}

			if ((car == 'z') || (car == 'Z')) {
				if (car == 'Z') {
					retorna.append(string).append("A");
				} else {
					retorna.append(string).append("a");
				}

			}
		}

		return retorna.toString();
	}

	public String numSacopDo(String siglas, String num, String lengthnum) {
		// int i = Integer.valueOf(num);
		StringBuffer numNew = new StringBuffer("");
		boolean Sw = false;
		int valor = 0;
		// for (int i=0;(i<num.length() && !Sw);i++){
		for (int i = 0; (i < Integer.parseInt(lengthnum) && !Sw); i++) {
			// valor =num.length() + numNew.toString().length();
			valor = numNew.toString().length() + num.length();
			// if (valor<3){
			if (valor < Integer.parseInt(lengthnum)) {
				numNew.append("0");
			} else {
				Sw = true;
			}
		}

		// return siglas.toString()+ "-".toString() +
		// numNew.toString()+num.toString();
		return numNew.toString() + num.toString();
	}

	public static java.util.Date sumUnitsToDate(int unit, java.util.Date date,
			int cant) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(unit, cant);
		return cal.getTime();
	}

	public static boolean isDateValid(java.util.Date date, boolean futura) {
		String dateStr = Utilidades.sdf.format(date);
		String dateSytem = Utilidades.sdf.format(new Date());
		if (futura) {
			return (dateStr.compareTo(dateSytem) > 0);
		}
		return (dateStr.compareTo(dateSytem) <= 0);
	}

	public boolean isNumeric(String valor) {
		if (!isEmptyOrNull(valor)) {
			try {
				Integer.parseInt(valor.trim());
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public char movementChar(char car) {
		int asciiValue = car;
		asciiValue++;
		return (char) asciiValue;
	}

	public String getWordKeys(String keyWord, String nomArchivo) {

		nomArchivo = sacarSlashFiltro(nomArchivo);
		StringTokenizer palabras = new StringTokenizer(nomArchivo);
		StringBuffer cadena = new StringBuffer("");
		while (palabras.hasMoreTokens()) {
			String pal = palabras.nextToken();
			cadena.append(pal);
			if (palabras.hasMoreTokens()) {
				cadena.append(",");
			}
		}
		if (!isEmptyOrNull(keyWord)) {
			cadena.append(",").append(keyWord.toString());
		}
		return cadena.toString();
	}

	public boolean isEmptyOrNull(String valor) {
		boolean swVacioCadena = (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
		return swVacioCadena;
	}

	public boolean esValidaCadena(String cadena) {
		boolean sw = true;
		for (int i = 0; i < cadena.length(); i++) {
			if (!esValido(new Character(cadena.charAt(i)))) {
				return false;
			}
		}
		return sw;
	}

	private boolean esValido(Character caracter) {
		char c = caracter.charValue();
		char comillita = '\'';
		// if ( !(Character.isLetter(c)) || c==' ' || c==8 ||
		// !(Character.isDigit(c))) { /* aceptamos el ingreso de espacios o el
		// backspace*/
		if (c == comillita) { /* aceptamos el ingreso de espacios o el backspace */
			return false;
		} else {
			return true;
		}
	}

	public Date fechaActual() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	//

	public String getMaquinaNameServidor() {
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		if (FacesContext.getCurrentInstance() != null && faces != null
				&& context != null) {
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();
			try {
				// request.getRemoteAddr();
				return InetAddress.getLocalHost().getHostName();
			} catch (java.net.UnknownHostException ex) {
				ex.printStackTrace();
			}
		}
		return "";
	}

	public String getMaquinaServidor() {
		FacesContext faces = FacesContext.getCurrentInstance();
		ExternalContext context = faces.getExternalContext();
		if (FacesContext.getCurrentInstance() != null && faces != null
				&& context != null) {
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();
			try {
				// request.getRemoteAddr();
				return InetAddress.getLocalHost().getHostAddress();
			} catch (java.net.UnknownHostException ex) {
				ex.printStackTrace();
			}
		}
		return "";
	}

	public String getMaquinaConectada() {
		FacesContext faces = FacesContext.getCurrentInstance();
		if (faces != null) {
			ExternalContext context = faces.getExternalContext();
			if (FacesContext.getCurrentInstance() != null && faces != null
					&& context != null) {
				HttpServletRequest request = (HttpServletRequest) context
						.getRequest();
				// request.getRemoteAddr();
				return request.getRemoteAddr();
			}
		}

		return "";
	}

	// HttpSession session =
	// FacesContext.getCurrentInstance().getExternalContext().getSesion();
	public HttpSession getSession() {
		HttpSession session = null;
		try {
			if (FacesContext.getCurrentInstance() != null) {
				session = (HttpSession) FacesContext.getCurrentInstance()
						.getExternalContext().getSession(true);
				ExternalContext externalContext = FacesContext
						.getCurrentInstance().getExternalContext();
				try {
					if (session == null) {
						externalContext.redirect("./errorPage.jsf");
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public HttpServletRequest geRequest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}

	public void setSession(String key, Object value) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute(key, value);
		session.setAttribute("idsession", session.getId());
	}

	public ContextSessionRequest() {

		try {
			if (FacesContext.getCurrentInstance() != null) {
				facesContext = FacesContext.getCurrentInstance();

			}
			if ((facesContext != null)
					&& (facesContext.getExternalContext().getSessionMap() != null)) {
				sessionScope = facesContext.getExternalContext()
						.getSessionMap();
			}

			if ((facesContext != null)
					&& (facesContext.getExternalContext().getApplicationMap() != null)) {
				applicationScope = facesContext.getExternalContext()
						.getApplicationMap();
			}
			if ((facesContext != null)
					&& (facesContext.getExternalContext().getRequestMap() != null)) {
				requestScope = facesContext.getExternalContext()
						.getRequestMap();
			}
			if ((facesContext != null)
					&& (facesContext.getExternalContext()
							.getRequestParameterMap() != null)) {
				requestParameterMap = facesContext.getExternalContext()
						.getRequestParameterMap();
			}

		} catch (Exception e) {
			System.out.println("ERROR CONTREOLADO EN ContextSessionRequest");
			e.printStackTrace();
		}

	}

	public static String parseParameters(HttpServletRequest request) {
		StringBuffer result = new StringBuffer(100);
		boolean first = true;
		Enumeration parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String name = (String) parameters.nextElement();
			String value = request.getParameter(name);
			boolean swVacioCadena = (value == null
					|| value.trim().length() == 0
					|| value.trim().equalsIgnoreCase("null") || value.trim()
					.equalsIgnoreCase("#000000"));
			if (!swVacioCadena) {
				if (first) {
					result.append("?").append(name).append("=").append(value);
					first = false;
				} else {
					result.append("&").append(name).append("=").append(value);
				}
			}
		}
		return result.toString();
	}

	public static ResourceBundle getResourceBundleStatic(String bungle) {

		// Locale locale =new Locale("es", "VE", "");//Locale.getDefault();
		// private Locale currentLocale = new Locale("es", "VE");
		// new Locale("en", "US");
		// currentLocale = new Locale("es", "VE");
		Locale locale = new Locale("es", "VE");// Locale.getDefault();

		ResourceBundle messages;
		messages = ResourceBundle.getBundle(bungle, locale);
		return messages;
	}

	public ResourceBundle getResourceBundle(String bungle) {
		ResourceBundle messages = null;

		FacesContext faces = FacesContext.getCurrentInstance();
		if (faces != null) {

			ExternalContext context = faces.getExternalContext();
			// Locale locale =new Locale("es", "VE",
			// "");//context.getRequestLocale();
			Locale locale = context.getRequestLocale();
			messages = ResourceBundle.getBundle(bungle, locale);
		}

		return messages;
	}

	public ResourceBundle getResourceBundleGrafico(String bungle) {

		ResourceBundle messages = null;
		FacesContext faces = FacesContext.getCurrentInstance();
		if (faces != null) {
			ExternalContext context = faces.getExternalContext();
			// Locale locale =new Locale("es", "VE",
			// "");//context.getRequestLocale();
			if (locale == null) {
				locale = context.getRequestLocale();
			}
		}
		configuracion = null;
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			configuracion = configuraciones.get(0);
			paisBundle = configuracion.getPaisBundle();
			idiomaBundle = configuracion.getIdiomaBundle();
			if (idiomaBundle != null && paisBundle != null) {
				locale = new Locale(idiomaBundle, paisBundle);
			}
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}

		messages = ResourceBundle.getBundle(bungle, locale);

		return messages;
	}

	public Map getSessionScope() {
		if (sessionScope == null) {
			sessionScope = getFacesContext().getExternalContext()
					.getSessionMap();
		}
		return sessionScope;
	}

	public Map getApplicationScope() {
		if (applicationScope == null) {
			applicationScope = getFacesContext().getExternalContext()
					.getApplicationMap();
		}
		return applicationScope;
	}

	public Map getRequestScope() {
		if (requestScope == null) {
			requestScope = getFacesContext().getExternalContext()
					.getRequestMap();
		}
		return requestScope;
	}

	public FacesContext getFacesContext() {
		if (facesContext == null) {
			facesContext = FacesContext.getCurrentInstance();
		}
		return facesContext;
	}

	public Map getRequestParameterMap() {
		if (requestParameterMap == null) {
			requestParameterMap = getFacesContext().getExternalContext()
					.getRequestParameterMap();
		}
		return requestParameterMap;
	}

	public static Object getApplicationMapValue(String key) {
		try {
			return FacesContext.getCurrentInstance().getExternalContext()
					.getApplicationMap().get(key);

		} catch (Exception e) {
			return null;
		}
	}

	public void setApplicationMapValue(String key, Object value) {
		if (key != null
				&& value != null
				&& FacesContext.getCurrentInstance() != null
				&& FacesContext.getCurrentInstance().getExternalContext() != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getApplicationMap().put(key, value);
		}

	}

	public Object getSessionMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}

	public void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(key, value);
	}

	public Object getRequestMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap().get(key);
	}

	// Setters ----------------------------------------------------------
	public void setRequestMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put(key, value);
	}

	public void clearSession(HttpSession session, String todasMenosEstas) {
		StringTokenizer st = new StringTokenizer(todasMenosEstas.trim(), ",");
		// ArrayList elementos = getDefaults();
		ArrayList elementos = new ArrayList();
		while (st.hasMoreTokens()) {
			String item = st.nextToken();
			elementos.add(item);
		}
		if (session != null) {
			Enumeration atributteInSession = session.getAttributeNames();
			while (atributteInSession.hasMoreElements()) {
				String param = (String) atributteInSession.nextElement();
				if (!elementos.contains(param)) {
					session.removeAttribute(param);
				}
			}

		}
	}

	public Map getSession(String string) {
		return null;
	}

	private boolean tipoContextType(String ext, HashMap contextType) {
		boolean swPerm = false;
		if (ext != null) {
			ext = ext.toLowerCase();
		}
		// por defecto
		// System.out.println("exT=" + ext);
		ExtensionFile extensionFile = delegado.tipoContextType(ext);
		if (extensionFile != null) {
			// System.out.println("extensionFile.getMimeType()="
			// + extensionFile.getMimeType());
			contextType.put("1", extensionFile.getMimeType());
			swPerm = true;
			return swPerm;
		} else {
			ExtensionFileHijos extensionFileHijos = new ExtensionFileHijos();
			extensionFileHijos.setExtension(ext);
			extensionFileHijos = delegado
					.find_ExtensionFileByExtension(extensionFileHijos);
			if (extensionFileHijos != null) {

				extensionFile = extensionFileHijos.getExtensionFile();
				System.out.println("extensionFile.getMimeType()="
						+ extensionFile.getMimeType());
				contextType.put("1", extensionFile.getMimeType());
				swPerm = true;
				return swPerm;
			}
		}
		return swPerm;

	}

	public String extensionObtener(String nombre) {
		String ext = "";
		try {
			ext = nombre
					.substring(nombre.lastIndexOf(".") + 1, nombre.length());

		} catch (Exception e) {
			return ext;
		}
		return ext;
	}

	public String obtenIconoDoc(String nombre) {
		// buscamos la extension del usuario, si la encontramos, colocamos sun
		// icono
		String icono = "img/filetypes32/_default.gif";
		if (!isEmptyOrNull(nombre)) {
			String ext = nombre.substring(nombre.lastIndexOf(".") + 1,
					nombre.length());
			if (ext != null) {
				ext = ext.toLowerCase();
			}
			if (extensionAceptadaToConverter(ext)) {

//				System.out.println("------------inicio icono----------------------------------------------");
				icono = "";
				icono = "img/filetypes32/" + ext + ".gif";
//				 File f = new File(icono);
//				 System.out.println("ext="+ext);
//System.out.println("icono="+icono);
//				 System.out.println("f.getPath()="+f.getPath());
//				 System.out.println("f.getAbsolutePath()="+f.getAbsolutePath());
//                try {
//   				 if (!f.exists()) {
//   					// System.out.println("sustituye");
//   					 icono = "img/filetypes32/_default.gif";
//   					// }else{
//   					// System.out.println("NO sustituye");
//   					 }
//					
//				} catch (Exception e) {
//                       e.printStackTrace();
//				}
//                System.out.println("------------fin icono----------------------------------------------");
			}
		}
		return icono;
	}

	// se usa para colocar los iconos
	public boolean extensionAceptadaToConverter(String ext) {
		boolean swPerm = false;
		HashMap noseUsa = new HashMap();
		if (ext != null) {
			ext = ext.toLowerCase();
		}
		swPerm = tipoContextType(ext, noseUsa);/*
												 * HashMap noseUsa = new
												 * HashMap(); StringTokenizer
												 * stk = newStringTokenizer(
												 * extensionesAceptadas, ",");
												 * while (stk.hasMoreTokens() &&
												 * !swPerm) { String extPerm =
												 * (String) stk.nextToken();
												 * swPerm = tipoContextType(ext,
												 * noseUsa); }
												 */
		return swPerm;
	}

	// esta esx para upload archivos
	public boolean extensionAcepotadaToConverter(String ext, HashMap contextType) {
		boolean swPerm = false;
		swPerm = tipoContextType(ext, contextType);
		/*
		 * StringTokenizer stk = new StringTokenizer(extensionesAceptadas, ",");
		 * while (stk.hasMoreTokens() && !swPerm) {
		 * 
		 * 
		 * String extPerm = (String) stk.nextToken();
		 * 
		 * tipoContextType(ext, contextType);
		 * 
		 * swPerm = extPerm.equals(ext);
		 * 
		 * }
		 */

		return swPerm;
	}

	private String prefijoBien(String prefijo) {
		String[] str = prefijo.split("/");
		StringBuffer aux = new StringBuffer("");
		int j = str.length - 1;
		ArrayList a = new ArrayList();

		for (int i = 0; i < str.length; i++) {
			if (str[j] != null && !str[j].equalsIgnoreCase("null")) {
				aux.append(str[j]);
				if (i + 1 < str.length) {
					aux.append("/");
				}
			}

			j--;
		}
		return aux.toString();
	}

	public String getPrefijo(Tree tree, String prefijo, boolean nomCorto) {

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view

		if (nomCorto) {
			if (tree.getPrefix() == null) {
				prefijo += "/" + "_";
			} else {
				prefijo += "/" + tree.getPrefix();
			}

		} else {
			if (tree.getNombre() == null) {
				prefijo += "/" + "_";
			} else {
				prefijo += "/" + tree.getNombre();
			}

		}
		boolean swHayPadre = true;
		while (swHayPadre) {
			Tree padre = tree;
			tree = llegarHastadAbuelosPadres(tree);

			if (tree != null) {

				// datr permisologia de vista al nodo
				swHayPadre = tree.getTiponodo() != Utilidades.getNodoRaiz();
				if (tree.equals(padre)) {
					swHayPadre = false;
				}

				if (nomCorto) {
					if (tree.getPrefix() != null) {
						prefijo += "/" + tree.getPrefix();
					}

				} else {
					if (tree.getNombre() != null) {
						prefijo += "/" + tree.getNombre();
					}
				}
			} else {

				swHayPadre = false;
			}
		}
		String pref = prefijoBien(prefijo);
		if (pref != null) {
			prefijo = pref.substring(0, pref.length() - 1);
			prefijo = "/" + prefijo;
		}

		return prefijo;
	}

	public String getPrefijo(Tree tree, String prefijo, boolean nomCorto,
			String nombre) {

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view

		if (nomCorto) {
			if (tree.getPrefix() == null) {
				prefijo += "/" + "_";
			} else {
				prefijo += "/" + tree.getPrefix();
			}

		} else {
			if (nombre == null) {
				prefijo += "/" + "_";
			} else {
				prefijo += "/" + nombre;
			}

		}
		boolean swHayPadre = true;
		while (swHayPadre) {
			Tree padre = tree;
			tree = llegarHastadAbuelosPadres(tree);

			if (tree != null) {

				// datr permisologia de vista al nodo
				swHayPadre = tree.getTiponodo() != Utilidades.getNodoRaiz();
				if (tree.equals(padre)) {
					swHayPadre = false;
				}

				if (nomCorto) {
					if (tree.getPrefix() != null) {
						prefijo += "/" + tree.getPrefix();
					}

				} else {
					if (nombre != null) {
						prefijo += "/" + nombre;
					}
				}
			} else {

				swHayPadre = false;
			}
		}
		String pref = prefijoBien(prefijo);
		if (pref != null) {
			prefijo = pref.substring(0, pref.length() - 1);
			prefijo = "/" + prefijo;
		}

		return prefijo;
	}

	public Seguridad seguridadTreeUsuarioNoLogueado(Usuario usuario, Tree tree) {

		Map user_seguridad = new HashMap();
		Seguridad seguridadTree = new Seguridad();

		// List<Seguridad_Role_Lineal> permisoUserInTree = delegado
		// .buscarSeguridadIndividual(usuario, tree);
		user_seguridad = delegado.buscarSeguridadIndividualOptimizado(usuario,
				tree, user_seguridad);
		ClienteSeguridad c = new ClienteSeguridad();

		// c.mySeguridadPropiaInRoles_and_Individual(permisoUserInTree,
		// user_seguridad);

		if (user_seguridad.containsKey(tree)) {

			seguridadTree = (Seguridad) user_seguridad.get(tree);
		} else {
			seguridadTree = new Seguridad();
		}

		return seguridadTree;
	}

	public Seguridad getSeguridadTree(Usuario usuario, Tree tree) {

		if (getUser_seguridad() != null) {
			// si el usuario es root, tiene permiso para todo
			if (getUser_seguridad().containsKey(tree)) {
				seguridadTree = (Seguridad) user_seguridad.get(tree);
			} else {

				// BUSCANDO SEGURIDAD
				// BUSCA,MOPS LAS SESSIONES DE SEGURIDAD COMPLETA Y LA LLENA KS
				// CON EL NUEVO TREE ENCONTRADO..
				user_seguridad = (Map) getUser_seguridad();

				// List<Seguridad_Role_Lineal> permisoUserInTree = delegado
				// .buscarSeguridadIndividual(usuario, tree);
				user_seguridad = delegado.buscarSeguridadIndividualOptimizado(
						usuario, tree, user_seguridad);
				ClienteSeguridad c = new ClienteSeguridad();

				// VALIDAMOS NUEVAMENTE LA SEGURIDAD
				// c.mySeguridadPropiaInRoles_and_Individual(permisoUserInTree,
				// user_seguridad);
				// GUSTRDAMOS EN EL SET LA SEGURIDAD NUEVO
				setUser_seguridad(user_seguridad);
				// VERIFICAMNOS SI HAY SEGURRIDAD EN EL NODO

				if (getUser_seguridad().containsKey(tree)) {

					user_seguridad = (Map) getUser_seguridad();
					seguridadTree = (Seguridad) user_seguridad.get(tree);
				} else {

					seguridadTree = new Seguridad();
				}

			}
		}
		return seguridadTree;
	}

	public void deleteSeguridadTree(Tree treeDelete) {
		user_seguridad = (Map) getUser_seguridad();
		if (user_seguridad.containsKey(treeDelete)) {
			user_seguridad.remove(treeDelete);
		}
		setUser_seguridad(user_seguridad);
	}

	public Seguridad getSeguridadTree(Tree tree) {
		if (getUser_seguridad() != null) {
			if (tree != null) {
				// si el usuario es root, tiene permiso para todo
				if (user_seguridad != null
						&& getUser_seguridad().containsKey(tree)) {
					seguridadTree = user_seguridad.get(tree) != null ? (Seguridad) user_seguridad
							.get(tree) : new Seguridad();
				} else {

					seguridadTree = new Seguridad();
				}

			} else {
				seguridadTree = new Seguridad();
			}
		}
		return seguridadTree;
	}

	public Map getUser_seguridad() {
		HttpSession session = null;
		user_seguridad = null;
		if (FacesContext.getCurrentInstance() != null) {
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(true);
			user_seguridad = (Map) session.getAttribute("user_seguridad");
		}
		return user_seguridad;
	}

	public void setUser_seguridad(Map user_seguridad) {
		HttpSession session = null;
		if (FacesContext.getCurrentInstance() != null) {
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(true);
			if (user_seguridad != null) {
				session.setAttribute("user_seguridad", user_seguridad);
			}
		}
		this.user_seguridad = user_seguridad;
	}

	public Seguridad getSeguridadTree() {
		/*
		 * if (getUser_seguridad()!=null){ //si el usuario es root, tiene
		 * permiso para todo if (getUser_seguridad().containsKey(tree)){
		 * seguridadTree= (Seguridad)user_seguridad.get(tree); }else{
		 * 
		 * seguridadTree= new Seguridad(); }
		 * 
		 * }
		 */

		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public Usuario getUser_logueado() {
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;

			return user_logueado;
		} catch (Exception e) {
			return null;
		}

	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public void checUsuarioInsessionValida(Usuario usuario_logueado) {
		ResourceBundle confmessages = getResourceBundle("com.util.resource.ecological_conf");
		// revisamos quienes estan conectados y quienes no..
		ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
		Map application = (Map) getApplicationMapValue(confmessages
				.getString("variable_global"));
		Usuario user = procesarLoginsConectados.loadUsuarioById(
				usuario_logueado.getId(), application,
				confmessages.getString("usuariosApplicaction"));

		if (user != null) {
			StringTokenizer strz = new StringTokenizer(",",
					user.getIdsessionObsoleta());
			while (strz.hasMoreElements()) {
				String sessionVieja = (String) strz.nextElement();
			}
			// EL USUARIO SE CONECTO EN OTRA MAQUINA

		}

	}

	public String getRequestParameter(FacesContext context, String name) {
		return (String) context.getExternalContext().getRequestParameterMap()
				.get(name);
	}

	public long numerodeDiasEnFechaCaducar(Date date, Date fechaFirma) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		// "yyyy-MM-dd");
		if (date == null) {
			date = new Date();
		}
		String dateCreation = Utilidades.date1.format(date);
		int anio = Integer.parseInt(dateCreation.substring(0, 4));
		int mes = Integer.parseInt(dateCreation.substring(5, 7));
		int dia = Integer.parseInt(dateCreation.substring(8, 10));

		cal1.set(anio, mes, dia);
		Date date2 = new Date();
		if (fechaFirma != null) {
			date2 = fechaFirma;
		}
		String datehoy = Utilidades.date1.format(date2);

		int anio2 = Integer.parseInt(datehoy.substring(0, 4));
		int mes2 = Integer.parseInt(datehoy.substring(5, 7));
		int dia2 = Integer.parseInt(datehoy.substring(8, 10));

		cal2.set(anio2, mes2, dia2);
		long dias = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 1000
				/ 60 / 60 / 24;
		return (int) dias;
	}

	public String getIdiomaBundle() {
		return idiomaBundle;
	}

	public void setIdiomaBundle(String idiomaBundle) {
		this.idiomaBundle = idiomaBundle;
	}

	public String getPaisBundle() {
		return paisBundle;
	}

	public void setPaisBundle(String paisBundle) {
		this.paisBundle = paisBundle;
	}

	public Doc_detalle grabarDocumento(Doc_detalle doc_registro,
			Tree treeNodoActual, Usuario usu, boolean swFlowParalelo,
			String msgFlowParalelo) {
		Doc_detalle detalle = null;
		try {
			ResourceBundle confmessages = getResourceBundleStatic("com.util.resource.ecological_conf");
			ResourceBundle messages = ContextSessionRequest
					.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
			if (!swFlowParalelo) {
				// SE BUSCA EL REGISTRO, PERO CUYO DOCUMENTO de detalle SEA
				// VIGENTE
				doc_registro = delegado.findDoc_detalleVigente(doc_registro);
			}

			Doc_maestro maestro = new Doc_maestro(
					doc_registro.getDoc_maestro(),
					"NULL vacio completo,lo mas basico");

			Doc_maestro docActauliza = null;
			if (!swFlowParalelo) {
				// ACTUALIZAMOS EL NUMERO DE REGISTROS QUE SE HACE CON ESTE
				// DOCUMENTO
				long numRegistrosHechos = maestro.getNumRegistrosHechos() + 1;
				docActauliza = doc_registro.getDoc_maestro();

				docActauliza.setNumRegistrosHechos(numRegistrosHechos);
				delegado.editMaestro(docActauliza);
				// ACTUALIZAMOS EL NUMERO DE REGISTROS QUE SE HACE CON ESTE
				// DOCUMENTO
				for (int i = 0; i < numRegistrosHechos; i++) {
					maestro.setConsecutivo(incInUnoString(maestro
							.getConsecutivo()));
				}
			}
			// es un registro,no puede originar registros.
			maestro.setNumRegistrosHechos(0);
			// creamos el arbol
			Tree tree = new Tree();
			Calendar fecha = Calendar.getInstance();
			tree.setNodopadre(treeNodoActual.getNodo());
			tree.setTiponodo(Utilidades.getNodoDocumento());
			tree.setFecha_creado(fecha.getTime());
			tree.setMaquina(getMaquinaConectada());

			maestro.setFecha_creado(fecha.getTime());
			doc_registro.setDatecambio(fecha.getTime());
			String registro = "";
			Doc_tipo doc_tipo = new Doc_tipo();
			if (swFlowParalelo) {
				doc_tipo.setCodigo(Utilidades.getFlujoparalelotipodoc());
				doc_tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());

				// doc_tipo.setCodigo(Utilidades.getRegistroTipoDoc());;
				doc_tipo = delegado.findTipoDoc(usu, doc_tipo);
				if (doc_tipo == null) {
					doc_tipo = new Doc_tipo();
					doc_tipo.setCodigo(new Long(Utilidades
							.getFlujoparalelotipodoc()));
				}

				try {
					registro = messages.getString(doc_tipo.getNombre());
				} catch (Exception e) {
					registro = doc_tipo.getNombre();
				}
				maestro.setDoc_tipo(doc_tipo);
			} else {
				doc_tipo.setCodigo(Utilidades.getRegistroTipoDoc());
				doc_tipo = delegado.findTipoDoc(doc_tipo);
				if (doc_tipo == null) {
					doc_tipo = new Doc_tipo();
					doc_tipo.setCodigo(new Long(2));
				}

				try {
					registro = messages.getString(doc_tipo.getNombre());
				} catch (Exception e) {
					registro = doc_tipo.getNombre();
				}
				// if (docActauliza == null) {
				maestro.setDoc_tipo(doc_tipo);
				// } else {
				// //se coloca el mismo tipo de documentoq ue lo esta creando
				// maestro.setDoc_tipo(docActauliza.getDoc_tipo());
				// }

			}

			Doc_estado doc_estado = new Doc_estado();
			doc_estado.setCodigo(Utilidades.getBorrador());
			doc_estado = delegado.findDocEstado(doc_estado);

			if (swFlowParalelo) {
				maestro.setNombre(msgFlowParalelo);

			} else {
				maestro.setNombre(maestro.getNombre() + "(" + registro + ")");
			}

			tree.setNombre(maestro.getNombre());
			tree.setDescripcion(maestro.getNombre());

			maestro.setUsuario_creador(usu);
			doc_registro.setDoc_maestro(maestro);
			// si se va hacer un flujo paralelo.. INSERTAMOS UN DOCUMENTO NULO
			// EN EL DETALLE
			if (swFlowParalelo) {
				doc_registro.setData(null);
				doc_registro.setData_doc(null);
			}

			// aqui se graba y nace todos los datos set del documento detalle
			detalle = new Doc_detalle(doc_registro,
					"NULL lo mas basico,completmente vacio");

			detalle.setDuenio(usu);
			detalle.setModificadoPor(usu);

			detalle.setDoc_estado(doc_estado);

			delegado.crearNuevoTree(tree, usu);
			maestro.setTree(tree);
			delegado.createMaestro(maestro);

			configuraciones = delegado.find_allConfiguracion();
			if (configuraciones != null && configuraciones.size() > 0) {
				Configuracion conf = configuraciones.get(0);
				swPostgresVieneDeConfiguracion = conf.isBdpostgres();
				swConfiguracionHayData = true;
			}
			// SI ES BD POSTGRESQL
			if (swConfiguracionHayData) {
				if (swPostgresVieneDeConfiguracion) {
					// buscamos la data de postgres con el doc_registro que es
					// el verdadero doc_detalle
					Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
					doc_dataPostgres.setDoc_detalle(detalle);
					doc_dataPostgres = delegado
							.findDoc_dataPostgres(doc_registro);
					// algun dia se arreglara los campos blob en postgres

					// grabamos el detalle
					doc_dataPostgres.setStatus(Utilidades.isActivo());
					if (doc_dataPostgres.getData_doc_postgres() != null) {
						ByteArrayInputStream stream = new ByteArrayInputStream(
								doc_dataPostgres.getData_doc_postgres());
						InputStream imagenBuffer = (InputStream) stream;
						Blob blob = Hibernate.createBlob(imagenBuffer);
						// si se va hacer un flujo paralelo.. INSERTAMOS UN
						// DOCUMENTO NULO
						// EN EL DETALLE
						if (swFlowParalelo) {
							blob = null;
						}
						detalle.setData(blob);
						detalle.setData_doc(blob);
					}
					delegado.createDetalle(detalle);

					// grabamos la data de postgres
					Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
					// si se va hacer un flujo paralelo.. INSERTAMOS UN
					// DOCUMENTO NULO
					// EN EL DETALLE
					// si se va hacer un flujo paralelo.. INSERTAMOS UN
					// DOCUMENTO NULO
					// EN EL DETALLE
					if (swFlowParalelo) {
						doc_dataPostgres.setData_doc_postgres(null);
					}
					doc_dataPostgresNew.setData_doc_postgres(doc_dataPostgres
							.getData_doc_postgres());
					doc_dataPostgresNew.setDoc_detalle(detalle);
					doc_dataPostgresNew.setStatus(Utilidades.isActivo());
					delegado.create(doc_dataPostgresNew);
				} else {
					delegado.createDetalle(detalle);
				}
			} else if ("1".equalsIgnoreCase(confmessages
					.getString("bdpostgres"))) {
				// buscamos la data de postgres con el doc_registro que es
				// el verdadero doc_detalle
				Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
				doc_dataPostgres.setDoc_detalle(detalle);
				doc_dataPostgres = delegado.findDoc_dataPostgres(doc_registro);
				// algun dia se arreglara los campos blob en postgres

				// grabamos el detalle
				doc_dataPostgres.setStatus(Utilidades.isActivo());
				if (doc_dataPostgres.getData_doc_postgres() != null) {
					ByteArrayInputStream stream = new ByteArrayInputStream(
							doc_dataPostgres.getData_doc_postgres());
					InputStream imagenBuffer = (InputStream) stream;
					Blob blob = Hibernate.createBlob(imagenBuffer);
					// si se va hacer un flujo paralelo.. INSERTAMOS UN
					// DOCUMENTO
					// NULO
					// EN EL DETALLE
					if (swFlowParalelo) {
						blob = null;
					}
					detalle.setData(blob);
					detalle.setData_doc(blob);
				}
				delegado.createDetalle(detalle);
				// si se va hacer un flujo paralelo.. INSERTAMOS UN DOCUMENTO
				// NULO
				// EN EL DETALLE
				if (swFlowParalelo) {
					doc_dataPostgres.setData_doc_postgres(null);
				}
				// grabamos la data de postgres
				Doc_dataPostgres doc_dataPostgresNew = new Doc_dataPostgres();
				doc_dataPostgresNew.setData_doc_postgres(doc_dataPostgres
						.getData_doc_postgres());
				doc_dataPostgresNew.setDoc_detalle(detalle);
				doc_dataPostgresNew.setStatus(Utilidades.isActivo());
				delegado.create(doc_dataPostgresNew);
			} else {
				delegado.createDetalle(detalle);
			}

			// }
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
		}
		return detalle;
	}

	public String ultimoNombreUrl(String h) {

		StringTokenizer ultimoStk = new StringTokenizer(h, "/");
		String ultimo = "";
		while (ultimoStk.hasMoreTokens()) {
			ultimo = ultimoStk.nextToken();
		}

		return ultimo;
	}

	public Date fechaSumarRestarDias(Date date, int dias, boolean swSumar) {
		calendar.setGregorianChange(date);
		if (swSumar) {
			calendar.set(GregorianCalendar.DAY_OF_YEAR,
					calendar.get(GregorianCalendar.DAY_OF_YEAR) + dias);
		} else {
			calendar.set(GregorianCalendar.DAY_OF_YEAR,
					calendar.get(GregorianCalendar.DAY_OF_YEAR) - dias);
		}

		return calendar.getTime();
	}

	public File saveFileInDisk(Doc_detalle doc_detalle,
			InputStream imagenBuffer, String nameFile) {
		ResourceBundle confmessages = ContextSessionRequest
				.getResourceBundleStatic("com.util.resource.ecological_conf");
		Configuracion conf = new Configuracion();

		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);

			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		String path = "";

		if (swConfiguracionHayData) {
			path = conf.getCarpetaCompartida().trim();
		} else {
			path = confmessages.getString("carpeta_compartida");
		}

		// String ruta = path + "\\" + nameFile;
		String ruta = path + "/" + nameFile;

		File fichero = new File(ruta);
		try {

			if (swConfiguracionHayData) {
				if (swPostgresVieneDeConfiguracion) {
					Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
					doc_dataPostgres = delegado
							.findDoc_dataPostgres(doc_detalle);
					ByteArrayInputStream stream = new ByteArrayInputStream(
							doc_dataPostgres.getData_doc_postgres());
					imagenBuffer = (InputStream) stream;
				}
			} else if ("1".equalsIgnoreCase(confmessages
					.getString("bdpostgres"))) {
				Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
				doc_dataPostgres = delegado.findDoc_dataPostgres(doc_detalle);
				ByteArrayInputStream stream = new ByteArrayInputStream(
						doc_dataPostgres.getData_doc_postgres());
				imagenBuffer = (InputStream) stream;
			}

			BufferedInputStream in = new BufferedInputStream(imagenBuffer);
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(fichero));
			byte[] bytes = new byte[8096];
			int len = 0;
			while ((len = in.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (imagenBuffer != null) {
					imagenBuffer.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fichero;
	}

	public File saveFileInDisk(byte[] data_doc_postgres,
			InputStream imagenBuffer, String nameFile) {
		ResourceBundle confmessages = ContextSessionRequest
				.getResourceBundleStatic("com.util.resource.ecological_conf");
		Configuracion conf = new Configuracion();

		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);

			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		String path = "";

		if (swConfiguracionHayData) {
			path = conf.getCarpetaCompartida().trim();
		} else {
			path = confmessages.getString("carpeta_compartida");
		}

		// String ruta = path + "\\" + nameFile;
		String ruta = path + "/" + nameFile;

		File fichero = new File(ruta);
		try {

			if (swConfiguracionHayData) {
				if (swPostgresVieneDeConfiguracion) {

					ByteArrayInputStream stream = new ByteArrayInputStream(
							data_doc_postgres);
					imagenBuffer = (InputStream) stream;
				}
			} else if ("1".equalsIgnoreCase(confmessages
					.getString("bdpostgres"))) {

				ByteArrayInputStream stream = new ByteArrayInputStream(
						data_doc_postgres);
				imagenBuffer = (InputStream) stream;
			}

			BufferedInputStream in = new BufferedInputStream(imagenBuffer);
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(fichero));
			byte[] bytes = new byte[8096];
			int len = 0;
			while ((len = in.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (imagenBuffer != null) {
					imagenBuffer.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fichero;
	}

	public File saveFileInDisk(InputStream imagenBuffer, String nameFile) {
		File fichero  = new File("");
		try {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String realPath = (String) servletContext.getRealPath("/"); // Sustituye
																		// "/"
																		// por
																		// el
																		// directorio
																		// ej:
																		// "/upload"

			ResourceBundle confmessages = ContextSessionRequest
					.getResourceBundleStatic("com.util.resource.ecological_conf");
			Configuracion conf = new Configuracion();

			configuraciones = delegado.find_allConfiguracion();
			if (configuraciones != null && configuraciones.size() > 0) {
				conf = configuraciones.get(0);

				swPostgresVieneDeConfiguracion = conf.isBdpostgres();
				swConfiguracionHayData = true;
			}

			String path = "";

			if (swConfiguracionHayData) {
				path = conf.getCarpetaCompartida().trim();
			} else {
				path = confmessages.getString("carpeta_compartida");
			}

			// String ruta = path + "\\" + nameFile;
			String ruta = path + "/" + nameFile;

			fichero = new File(ruta);
			try {

				BufferedInputStream in = new BufferedInputStream(imagenBuffer);
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(fichero));
				byte[] bytes = new byte[8096];
				int len = 0;
				while ((len = in.read(bytes)) > 0) {
					out.write(bytes, 0, len);
				}
				out.flush();
				out.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (imagenBuffer != null) {
						imagenBuffer.close();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return fichero;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fichero;
	}

	public DualListModel<Usuario> getOperacionesUsuario() {
		return operacionesUsuario;
	}

	public void setOperacionesUsuario(DualListModel<Usuario> operacionesUsuario) {
		this.operacionesUsuario = operacionesUsuario;
	}

	public List<Usuario> getInvisibleItemsUsuario() {
		return invisibleItemsUsuario;
	}

	public void setInvisibleItemsUsuario(List<Usuario> invisibleItemsUsuario) {
		this.invisibleItemsUsuario = invisibleItemsUsuario;
	}

	public List<Usuario> getVisibleItemsUsuario() {
		return visibleItemsUsuario;
	}

	public void setVisibleItemsUsuario(List<Usuario> visibleItemsUsuario) {
		this.visibleItemsUsuario = visibleItemsUsuario;
	}

	public List<Flow> participantesGruposPlantila(List<Role> roles) {
		List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
		ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
		Flow flow = null;
		for (Role fr : roles) {
			flow = new Flow();
			flow.setGrupo(fr);
			participantesGruposPlantila.add(flow);

			// PARA USUARIOS
			List<Roles_Usuarios> roleUsuarioLst = delegado
					.findRoles_AND_Usuario(fr);

			for (Roles_Usuarios ru : roleUsuarioLst) {
				flow = new Flow();
				flow.setGrupo(null);
				flow.setParticipante(ru.getUsuario());
				participantesGruposPlantila.add(flow);
			}
			// PARA PERMISOS
			List<String> operacionesInRol = clienteSeguridad
					.operacionesInRolesForTreeOnlyRichFaces(fr);
			if (operacionesInRol != null && operacionesInRol.size() > 0) {
				for (String ru : operacionesInRol) {
					flow = new Flow();
					flow.setGrupo(null);
					flow.setParticipante(null);
					flow.setPermiso(ru.toString());
					participantesGruposPlantila.add(flow);
				}

			}

		}
		return participantesGruposPlantila;
	}

	public List<Flow> participantesPerfiles(List<Role> roles) {
		List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
		ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
		Flow flow = null;
		for (Role fr : roles) {
			flow = new Flow();
			flow.setGrupo(fr);
			participantesGruposPlantila.add(flow);

			// PARA USUARIOS
			List<Roles_Usuarios> roleUsuarioLst = delegado
					.findRoles_AND_Usuario(fr);

			for (Roles_Usuarios ru : roleUsuarioLst) {
				flow = new Flow();
				flow.setGrupo(null);
				flow.setParticipante(ru.getUsuario());
				participantesGruposPlantila.add(flow);
			}
			// PARA PERMISOS
			List<String> operacionesInRol = clienteSeguridad
					.operacionesInRolesForTreeOnlyRichFaces(fr);
			if (operacionesInRol != null && operacionesInRol.size() > 0) {
				for (String ru : operacionesInRol) {
					flow = new Flow();
					flow.setGrupo(null);
					flow.setParticipante(null);
					flow.setPermiso(ru.toString());
					participantesGruposPlantila.add(flow);
				}

			}

		}
		return participantesGruposPlantila;
	}

	public List<Flow> participantesPerfilesPlantila(List<Role> roles) {
		List<Flow> participantesGruposPlantila = new ArrayList<Flow>();
		ClienteSeguridad clienteSeguridad = new ClienteSeguridad();
		Flow flow = null;
		for (Role fr : roles) {

			flow = new Flow();
			flow.setGrupo(fr);
			participantesGruposPlantila.add(flow);

			// PARA USUARIOS
			List<Roles_Usuarios> roleUsuarioLst = delegado
					.findRoles_AND_Usuario(fr);

			for (Roles_Usuarios ru : roleUsuarioLst) {
				flow = new Flow();
				flow.setGrupo(null);
				flow.setParticipante(ru.getUsuario());
				participantesGruposPlantila.add(flow);
			}
			// PARA PERMISOS
			List<String> operacionesInRol = clienteSeguridad
					.operacionesInRolesForTreeOnlyRichFaces(fr);
			if (operacionesInRol != null && operacionesInRol.size() > 0) {
				for (String ru : operacionesInRol) {
					flow = new Flow();
					flow.setGrupo(null);
					flow.setParticipante(null);
					flow.setPermiso(ru.toString());
					participantesGruposPlantila.add(flow);
				}

			}

		}
		return participantesGruposPlantila;
	}

	
	// creamos siempre un role por default y que empize por a para que sea
		// predeterminado
	public void llenarPerfilRoleDefault(Tree empresa) {
		if (empresa != null) {
			Role role = new Role();
			role.setEmpresa(empresa);
			role.setNombre(Utilidades.getTorolebydefault());
			try {
				role = delegado.findRoleADefault(role);
				if (role == null) {
					role = new Role();
					role.setNombre(Utilidades.getTorolebydefault());
					role.setDescripcion(Utilidades.getTorolebydefault());
					role.setEmpresa(empresa);
					boolean usadoParaCrearFlujo = false;
					role.setUsadoParaCrearFlujo(usadoParaCrearFlujo);
					try {
						delegado.create(role);
					} catch (EcologicaExcepciones e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (RoleMultiples e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			} catch (RoleNoEncontrado e1) {
				System.out
						.println("no se pudo crear el role por default part2");
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
		}
	}
}
