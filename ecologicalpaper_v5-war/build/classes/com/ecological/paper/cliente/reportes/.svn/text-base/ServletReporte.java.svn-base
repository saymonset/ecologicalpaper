package com.ecological.paper.cliente.reportes;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.reportes.Participantes;
import com.ecological.reportes.Reportes;
import com.ecological.reportes.SubFlowReporte;
import com.ecological.util.ContextSessionRequest;
import com.util.file.Archivo;

/**
 * Servlet implementation class ServletReporte
 */

public class ServletReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private ServicioDelegado delegado = new ServicioDelegado();
	ResourceBundle confmessages = ContextSessionRequest
			.getResourceBundleStatic("com.util.resource.ecological_conf");
	private Configuracion conf = new Configuracion();
	  private boolean swPostgresVieneDeConfiguracion;
	    private boolean swConfiguracionHayData;
	    private String carpeta_compartida;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletReporte() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"reporte.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		
		File temp=null;
		ServletOutputStream out = response.getOutputStream();

		try {

			configuraciones = delegado.find_allConfiguracion();
			if (configuraciones != null && configuraciones.size() > 0) {
				conf = configuraciones.get(0);
				swPostgresVieneDeConfiguracion = conf.isBdpostgres();
				swConfiguracionHayData = true;
			}
			if (swConfiguracionHayData) {
				carpeta_compartida = conf.getCarpetaCompartida().trim();
			} else {
				carpeta_compartida = confmessages
						.getString("carpeta_compartida").trim();
			}

			 
			String tempdir = System.getProperty("java.io.tmpdir");
			if ( !(tempdir.endsWith("/") || tempdir.endsWith("\\")) )
			   tempdir = tempdir + System.getProperty("file.separator");

			 System.out.println("OS current temporary directory is "
					 + tempdir);
		 
			/* File recusrso = new File(carpeta_compartida+"/"+confmessages.getString("recursos"));
			 if (!recusrso.isDirectory()){
				 recusrso.mkdirs();
			 }*/
			 
			    
			   // Abro URL
				
				Archivo archivo = new Archivo();
    		    InputStream is = getClass().getResourceAsStream(
				"logo.jpg"); //archivo.fileToinputStream(new File (carpeta_compartida+"/"+confmessages.getString("recursos") + "/logo.jpg")) ;   
			/*InputStream templateStream1 = getClass().getResourceAsStream(
					"logo.jpg");*/
			
			
			
			

			Map parametros = new HashMap();
			String nomCompletoFlowDoc = session
					.getAttribute("nomCompletoFlowDoc") != null ? (String) session
					.getAttribute("nomCompletoFlowDoc") : "Flujo de Trabajo";
			parametros.put("flujo", nomCompletoFlowDoc);
			String fechacreado = session.getAttribute("fechacreado") != null ? (String) session
					.getAttribute("fechacreado") : "";
			parametros.put("fechacreado", fechacreado);
			parametros.put("logo", is);

			List<SubFlowReporte> listaSubFlowReporte = null;
			if (session.getAttribute("listaSubFlowReporte") != null) {
				listaSubFlowReporte = (List<SubFlowReporte>) session
						.getAttribute("listaSubFlowReporte");
			}

			// Create temp file. 
			 temp = File.createTempFile("reporte", ".jrxml"); 
			//File temporaryFile = new File(carpeta_compartida, confmessages.getString("recursos") + "/reporte.jrxml");
			InputStream templateStream = getClass().getResourceAsStream(
					"reporteflujos.jrxml");
			IOUtils.copy(templateStream, new FileOutputStream(temp));
			String absolutePath = temp.getAbsolutePath();
			JasperReport jasperReport = JasperCompileManager
					.compileReport(absolutePath);
			
			
			//jasperReport.setProperty(JRCrosstab.PROPERTY_IGNORE_WIDTH, "true");


			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parametros, new JRBeanCollectionDataSource(
							listaSubFlowReporte));

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			temp.deleteOnExit(); 
		}

	}

	public void init(final ServletConfig config) {
		final String context = config.getServletContext().getRealPath("/");

	}
}
