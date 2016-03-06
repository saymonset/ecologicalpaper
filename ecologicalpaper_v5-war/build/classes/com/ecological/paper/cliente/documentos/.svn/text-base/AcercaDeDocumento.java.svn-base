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

public class AcercaDeDocumento extends HttpServlet {
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
	public AcercaDeDocumento() {
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
		if ("".equalsIgnoreCase(solicitudimpresion)) {
			solicitudimpresion = session.getAttribute("solicitudimpresion") != null ? (String) session
					.getAttribute("solicitudimpresion") : "";
		}

		String numCopias = request.getParameter("numcopias") != null ? (String) request
				.getParameter("numcopias") : "";

		if ("".equalsIgnoreCase(numCopias)) {
			numCopias = session.getAttribute("numCopias") != null ? (String) session
					.getAttribute("numCopias") : "";
		}

		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		if (!"".equalsIgnoreCase(detalle_id)) {
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

		// carpeta temporal
		/*
		 * String tmp=""; String endPoint=""; boolean swBdpostgres=false; if
		 * (session != null) { detalle = (Doc_detalle)
		 * session.getAttribute("objeto"); tmp =
		 * session.getAttribute("tmp")!=null?(String)
		 * session.getAttribute("tmp"):""; swBdpostgres=
		 * session.getAttribute("swBdpostgres")!=null?(Boolean)
		 * session.getAttribute("swBdpostgres"):false; endPoint =
		 * session.getAttribute("endPoint")!=null?(String)
		 * session.getAttribute("endPoint"
		 * ):"http://localhost:8180/axis2/services/StatisticsService"; }
		 */
		long codigoDocumento = 0l;
		if (detalle != null) {
			codigoDocumento = detalle.getCodigo();
		}

	 

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
				+ " <noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>"
				+ " <script src=\"http://www.java.com/js/deployJava.js\"></script>"
				+ "<script> \n"
				+ " var attributes = { code:'appletComponentArch.DynamicAcercaDeApplet.class',"
				+ " archive:'arch.jar',  width:300, height:300} ;"
				// var parameters = {jnlp_href: 'dynamictree-applet.jnlp',
				// paramInt: '17', tmp: 'f:/tmp', swBdpostgres:'false'} ;

				+ " var parameters = {jnlp_href: 'dynamicacercade-applet.jnlp', paramInt: '"
				+ codigoDocumento
				+ "', tmp:'"
				+ tmp
				+ "' , swBdpostgres:'"
				+ swBdpostgres
				+ "', endPoint:'"
				+ endPoint
				+ "', numCopias:'"
				+ numCopias
				+ "',solicitudimpresion:'"
				+ solicitudimpresion
				+ "',user_logueado:'"
				+ user_logueado.getId()
				+ "'} ;"
				+ " deployJava.runApplet(attributes, parameters, '1.4');"
				+ "</script>"
				+

				"<!-- Start SiteCatalyst code   -->"
				+ "<script language=\"JavaScript\" src=\"http://www.oracle.com/ocom/groups/systemobject/@mktg_admin/documents/systemobject/s_code_download.js\"></script>"
				+ "<script language=\"JavaScript\" src=\"http://www.oracle.com/ocom/groups/systemobject/@mktg_admin/documents/systemobject/s_code.js\"></script>"
				+ "<!-- ********** DO NOT ALTER ANYTHING BELOW THIS LINE ! *********** -->"
				+ "<!--  Below code will send the info to Omniture server -->"
				+ "<script language=\"javascript\">var s_code=s.t();if(s_code)document.write(s_code)</script>"
				+ "<!-- End SiteCatalyst code -->" + "</BODY></HTML>");

	}

}
