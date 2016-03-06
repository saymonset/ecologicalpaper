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
import com.ecological.paper.documentos.PublicadosUsuComent;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;

/**
 * Servlet implementation class EditarDocumento
 */

public class EditarDocumento extends HttpServlet {
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
	public EditarDocumento() {
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
		Doc_detalle detalle = new Doc_detalle();
		// EN CASO QUE SE NECESITE TRABAJAR CON LA TABLA publicadosUsuComent 
		String publicadosUsuComentPublico = request
				.getParameter("publicadosUsuComent") != null ? (String) request
				.getParameter("publicadosUsuComent") : "";

		if ("1".equalsIgnoreCase(publicadosUsuComentPublico)) {
			String usuario_id = request.getParameter("usuario_id") != null ? (String) request
					.getParameter("usuario_id") : "0";
					
				  

			Usuario usuario = new Usuario();
			long id = Long.parseLong(usuario_id);

			usuario.setId(new java.lang.Long(id));
			// buscamos el detalle
			usuario = delegado.find_Usuario(usuario.getId());

			String detalle_id = request.getParameter("detalle_id") != null ? (String) request
					.getParameter("detalle_id") : "0";
			Doc_detalle doc_detalle2 = new Doc_detalle();
			id = Long.parseLong(detalle_id);
			doc_detalle2.setCodigo(new java.lang.Long(id));
			// buscamos el detalle
			doc_detalle2 = delegado.findDetalle(doc_detalle2);
	
			PublicadosUsuComent publicadosUsuComent = new PublicadosUsuComent();
			publicadosUsuComent.setDoc_detalle(doc_detalle2);
			publicadosUsuComent.setUsuario(usuario);
			publicadosUsuComent = delegado
					.findOnePublicadosUsuComent(publicadosUsuComent);
			session.setAttribute("publicadosUsuComent", publicadosUsuComent);
		}
		PublicadosUsuComent publicadosUsuComent = session
				.getAttribute("publicadosUsuComent") != null ? (PublicadosUsuComent) session
				.getAttribute("publicadosUsuComent") : null;
		boolean swOtraTabla = false;
		if (publicadosUsuComent != null) {
			// HAQUI YA SABESMO QUE VA A BUSCAR POR LA TABLA PublicadosUsuComent
			// EN MAS PARAMETROS
			// POR EL USUARIO ID TAMBIEN.. YA TODO LO MANEJA EL APLET DE MANERA
			// DINAMICA
			// CON LA NUEVA TABLA ABUSCAR
			detalle.setCodigo(new java.lang.Long(publicadosUsuComent
					.getDoc_detalle().getCodigo()));
			// buscamos el detalle

			detalle = delegado.findDetalle(detalle);
			session.setAttribute("objeto", detalle);

			swOtraTabla = true;
		}

		detalle = (Doc_detalle) session.getAttribute("objeto");
		String detalle_id = request.getParameter("detalle_id") != null ? (String) request
				.getParameter("detalle_id") : "";
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

		System.out.println("codigoDocumento=" + codigoDocumento);
		System.out.println("tmp=" + tmp);
		System.out.println("swBdpostgres=" + swBdpostgres);
		System.out.println("endPoint:" + endPoint);

		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");

		StringBuffer masParametros = new StringBuffer("");
		if (swOtraTabla) {
			masParametros
					.append(" <PARAM NAME=\"tabla\" VALUE=PublicadosUsuComent"
							+ ">");
			masParametros.append(" <PARAM NAME=\"usuarioid\" VALUE="
					+ publicadosUsuComent.getUsuario().getId() + ">");

		}

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

				+ "<APPLET CODE=appletComponentArch.DynamicTreeApplet.class   archive=\"arch.jar\" WIDTH=300 HEIGHT=300>"
				+ " <PARAM NAME=\"paramInt\" VALUE=" + codigoDocumento + ">"
				+ " <PARAM NAME=\"tmp\" VALUE=" + tmp + ">"
				+ " <PARAM NAME=\"swBdpostgres\" VALUE=" + swBdpostgres + ">"
				+ " <PARAM NAME=\"endPoint\" VALUE=" + endPoint + ">"

				+ masParametros.toString()

				+ "</APPLET>"
				/*
				 * 
				 * +
				 * " <noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>"
				 * +
				 * " <script src=\"http://www.java.com/js/deployJava.js\"></script>"
				 * + "<script> \n" +
				 * " var attributes = { code:'appletComponentArch.DynamicTreeApplet.class',"
				 * + " archive:'arch.jar',  width:300, height:300} ;" // var
				 * parameters = {jnlp_href: 'dynamictree-applet.jnlp', //
				 * paramInt: '17', tmp: 'f:/tmp', swBdpostgres:'false'} ;
				 * 
				 * +
				 * " var parameters = {jnlp_href: 'dynamictree-applet.jnlp', paramInt: '"
				 * + codigoDocumento + "', tmp:'" + tmp + "' , swBdpostgres:'" +
				 * swBdpostgres + "', endPoint:'" + endPoint + "'} ;" +
				 * " deployJava.runApplet(attributes, parameters, '1.4');" +
				 * "</script>" +
				 * 
				 * "<!-- Start SiteCatalyst code   -->" +
				 * "<script language=\"JavaScript\" src=\"http://www.oracle.com/ocom/groups/systemobject/@mktg_admin/documents/systemobject/s_code_download.js\"></script>"
				 * +
				 * "<script language=\"JavaScript\" src=\"http://www.oracle.com/ocom/groups/systemobject/@mktg_admin/documents/systemobject/s_code.js\"></script>"
				 * +
				 * "<!-- ********** DO NOT ALTER ANYTHING BELOW THIS LINE ! *********** -->"
				 * +
				 * "<!--  Below code will send the info to Omniture server -->"
				 * +
				 * "<script language=\"javascript\">var s_code=s.t();if(s_code)document.write(s_code)</script>"
				 * + "<!-- End SiteCatalyst code -->"
				 */
				+ "</BODY></HTML>");

	}

}
