package com.ecological.paper.cliente.documentos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;

/**
 * Servlet implementation class EditarDocumento
 */

public class ImprimirDocumento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private byte[] dataPostgres;
	ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
	ResourceBundle confmessages = ContextSessionRequest
			.getResourceBundleStatic("com.util.resource.ecological_conf");
	ResourceBundle messages = ContextSessionRequest
			.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
	private String carpeta_compartida;
	private Configuracion conf = new Configuracion();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private ServicioDelegado delegado = new ServicioDelegado();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImprimirDocumento() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Doc_detalle detalle = (Doc_detalle) session.getAttribute("objeto");
		String detalle_id = request.getParameter("detalle_id") != null ? (String) request
				.getParameter("detalle_id") : "";

		String solicitudimpresion = request.getParameter("solicitudimpresion") != null ? (String) request
				.getParameter("solicitudimpresion") : "";
		if (isEmptyOrNull(solicitudimpresion)) {
			solicitudimpresion = session.getAttribute("solicitudimpresion") != null ? (String) session
					.getAttribute("solicitudimpresion") : "";
		}
		String numCopias = request.getParameter("numcopias") != null ? (String) request
				.getParameter("numcopias") : "";
		if (isEmptyOrNull(numCopias)) {
			numCopias = session.getAttribute("numCopias") != null ? (String) session
					.getAttribute("numCopias") : "";
		}

		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		if (!isEmptyOrNull(detalle_id)) {
			detalle = new Doc_detalle();
			detalle.setCodigo(new java.lang.Long(detalle_id));
			// buscamos el detalle
			detalle = delegado.findDetalle(detalle);
		}
		// viene de un lugar diferente a flowresponse
		if (detalle == null) {
			detalle = (Doc_detalle) session.getAttribute("doc_detalle");

		}

		// revisamos del archivo plano primero la configuracion
		boolean swBdpostgres = "1".equalsIgnoreCase(confmessages
				.getString("bdpostgres"));
		String endPoint = confmessages.getString("endPoint");
		String tmp = confmessages.getString("carpeta_compartida");
		// vemos si hay configuracion en bd
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			Configuracion conf = configuraciones.get(0);
			swBdpostgres = conf.isBdpostgres();
			tmp = conf.getCarpetaCompartida();
			endPoint = conf.getEndPoint();
		}

 
		long codigoDocumento = 0l;
		if (detalle != null) {
			codigoDocumento = detalle.getCodigo();
		}

		System.out.println("codigoDocumento=" + codigoDocumento);
		System.out.println("tmp=" + tmp);
		System.out.println("swBdpostgres=" + swBdpostgres);
		System.out.println("endPoint:" + endPoint);

		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
				+ "Transitional//EN\">\n"
				+ " <html lang=\"en-US\">\n"
				+ "<HEAD><TITLE></TITLE> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\"> "
				+ " <meta http-equiv=\"Cache-Control\" content=\"no-store\"> \n"
				+ " <meta http-equiv=\"Pragma\" content=\"no-cache\"> \n"
				+ " <meta http-equiv=\"expires\" content=\"0\"> \n"
				+ " </HEAD>\n"
				+ "<BODY>\n"
			  +"<APPLET CODE=appletComponentArch.DynamicImpApplet.class   archive=\"arch.jar\" WIDTH=300 HEIGHT=300>"
			   +" <PARAM NAME=\"paramInt\" VALUE="+codigoDocumento+">"
			   +" <PARAM NAME=\"tmp\" VALUE="+tmp+">"
			   +" <PARAM NAME=\"swBdpostgres\" VALUE="+swBdpostgres+">"
			   +" <PARAM NAME=\"endPoint\" VALUE="+endPoint+">"
			   +" <PARAM NAME=\"numCopias\" VALUE="+numCopias+">"
			   +" <PARAM NAME=\"solicitudimpresion\" VALUE="+solicitudimpresion+">"
			   +" <PARAM NAME=\"user_logueado\" VALUE="+user_logueado.getId()+">"
			   +"</APPLET>"
			    

				
		 
				
				+ "</BODY></HTML>");

	}

	public boolean isEmptyOrNull(String valor) {
		boolean swVacioCadena = (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
		return swVacioCadena;
	}
}
