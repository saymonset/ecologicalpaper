package com.ecological.paper.cliente.documentos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecological.configuracion.Configuracion;
import com.ecological.datoscombo.Converdocumento;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;

public  class ClienteDocumentoGenerarFlowParalelo extends HttpServlet {

	private ServicioDelegado delegado = new ServicioDelegado();

	// private Doc_maestro maestro = null;
	private Doc_detalle detalle = null;
	private Converdocumento convertirToPDF = new Converdocumento();;
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
	private boolean swPdf;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private File f;
	private String rutaArchCompleto;

	/** Creates a new instance of ClienteDocumentoGenerar */
	public ClienteDocumentoGenerarFlowParalelo() {

	}

	protected  boolean processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.reset();
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);
		String docPDF = "";
		// verificamos si es solo lectura
		String onlyViewR = request.getParameter("onlyView");
		String onlyView = "";

		Doc_detalle detalle = null;

		if (session != null) {

			detalle = (Doc_detalle) session.getAttribute("objeto");
			if (detalle == null) {
				detalle = (Doc_detalle) session.getAttribute("doc_detalle");
			}

			try {
								
			} catch (Exception e) {
				// TODO: handle exception
			}

			String detalle_id = request.getParameter("detalle_id") != null ? (String) request
					.getParameter("detalle_id") : "";
			if (!"".equalsIgnoreCase(detalle_id)) {
				detalle = new Doc_detalle();
				detalle.setCodigo(new java.lang.Long(detalle_id));
				// buscamos el detalle
				detalle = delegado.findDetalle(detalle);
			}

//			System.out.println("------------detalle==null="+(detalle==null));
			System.out.println("NO ES NULA LA SESSION ");
			if (detalle != null) {
				/*
				 * if (detalle.getData()==null){ Doc_dataPostgres
				 * doc_dataPostgres=delegado.findDoc_dataPostgres(detalle);
				 * ByteArrayInputStream streamBlob = new
				 * ByteArrayInputStream(doc_dataPostgres
				 * .getData_doc_postgres()); Blob blob =
				 * Hibernate.createBlob(streamBlob); detalle.setData(blob); try
				 * { delegado.editDetalle(detalle); } catch
				 * (EcologicaExcepciones ex) { ex.printStackTrace(); } }
				 */

				detalle = delegado.findDetalle(detalle);
				session.setAttribute("objeto", detalle);

			}
		} else {
			System.out.println("SI ES NULA LA SESSION");

		}
		// ___________________________________________________________________
		// hay extensuiones que no c porque no pueden ser solo only view
		String extension = "";
		if (detalle != null && detalle.getNameFile() != null
				&& detalle.getNameFile().lastIndexOf(".") != -1) {
			extension = detalle.getNameFile().substring(
					detalle.getNameFile().lastIndexOf(".") + 1,
					detalle.getNameFile().length());
		}
		
		
		
		// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO
	Usuario	user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado")
				: null;
		boolean verDetalles = true;
		if (detalle!=null){
			contextSessionRequest.guardamosHistoricoActivoDelDocumento(user_logueado, detalle
					.getDoc_maestro(), false, false, verDetalles, false, false,
					false, false, false, "");	
		}
		
		// ____________________________________

		// ES UN PROBLEMON CON PWERPOINT, NO C UE PASA CON EXTENSION PPT
		boolean swEspecial = false;

		StringTokenizer extEspeciales = new StringTokenizer(
				confmessages.getString("extEspeciales"), ",");
		while (extEspeciales.hasMoreTokens() && !swEspecial) {
			String extEsp = (String) extEspeciales.nextToken();

			if (extEsp != null) {
				if (extEsp.equals(extension)) {
					swEspecial = true;
				}
			}
		}
		// }
		// si es swEspecial, no debe ser oonly view
		if (!swEspecial) {
			if (session != null && session.getAttribute("onlyView") != null) {
				onlyView = (String) session.getAttribute("onlyView");
				if (onlyView != null) {
					session.setAttribute("onlyView", onlyView);
				}
			}

		}
		// ___________________________________________________________________

		if (detalle != null && detalle.getCodigo() > 0) {

			// maestro = detalle.getDoc_maestro();
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
						.getString("carpeta_compartida");
			}
			System.out.println("carpeta_compartida=" + carpeta_compartida);
			System.out
					.println("detalle.getNameFile()=" + detalle.getNameFile());
			System.out.println("detalle.getCodigo()=" + detalle.getCodigo());
			String ruta = carpeta_compartida;// confmessages.getString("carpeta_compartida");//
												// + "\\" + "objeto" + "\\" +
												// detalle.getCodigo();
			boolean success = (new File(ruta)).mkdir();
			// LINUX
			String tmp = ruta;// ;confmessages.getString("carpeta_compartida");
			String path = ruta;// confmessages.getString("carpeta_compartida");

			try {
				// borramos cualquier fichero del mismo tipo borrado
				// anteriormente
				File ficheroBorra = new File(path + "/" + detalle.getNameFile());
				ficheroBorra.delete();
				if (("pdf".equalsIgnoreCase(extension))
						|| ("application/pdf".equalsIgnoreCase(detalle
								.getContextType()))) {

					// SE GRABA EN DISCO
					if (detalle.getData() == null
							|| detalle.getData().getBinaryStream() == null) {
						saveFileInDisk(detalle, null, path,
								detalle.getNameFile());
					} else {
						saveFileInDisk(detalle, detalle.getData()
								.getBinaryStream(), path, detalle.getNameFile());
					}
					swPdf=true;
					docPDF = convertirToPDF.convertirDocumento(path,
							detalle.getNameFile(), detalle,swPdf);

					
					detalle.setNameFile(docPDF.toString());

				} else {
					if ("onlyView".equalsIgnoreCase(onlyView)) {
						// SE GRABA EN DISCO

						// hacemos la conversion para tratar en lo mas minimo de
						// que no lo modifiquen
						// siempore van hacer .doc, para que sean convertidos
						HashMap queTipoContextType = new HashMap();
						if (contextSessionRequest
								.extensionAcepotadaToConverter(extension,
										queTipoContextType)) {
							// para que sea aceptadom por el convertidor de pdf.
							if (detalle.getData() == null
									|| detalle.getData().getBinaryStream() == null) {
								saveFileInDisk(detalle, null, path,
										detalle.getNameFile());
							} else {
								saveFileInDisk(detalle, detalle.getData()
										.getBinaryStream(), path,
										detalle.getNameFile());
							}

							if ("true"
									.equals(confmessages.getString("pdftrue"))) {

								swPdf=true;
								docPDF = convertirToPDF.convertirDocumento(
										path, detalle.getNameFile(), detalle,swPdf);
								detalle.setNameFile(docPDF.toString());

							}else{
								swPdf=false;
								docPDF = convertirToPDF.convertirDocumento(
										path, detalle.getNameFile(), detalle,swPdf);

							
								detalle.setNameFile(docPDF.toString());
							}
						} else {

							if (detalle.getData() == null
									|| detalle.getData().getBinaryStream() == null) {
								saveFileInDisk(detalle, null, path,
										detalle.getNameFile());
							} else {
								saveFileInDisk(detalle, detalle.getData()
										.getBinaryStream(), path,
										detalle.getNameFile());
							}
							swPdf=false;
							docPDF = convertirToPDF.convertirDocumento(path,
									detalle.getNameFile(), detalle,swPdf);

					
							detalle.setNameFile(docPDF.toString());

						}
					} else {
						// SE GRABA EN DISCO
						// no es lectura y se va a modificar con su respectivo
						// formato

						if (detalle.getData() == null
								|| detalle.getData().getBinaryStream() == null) {
							saveFileInDisk(detalle, null, path,
									detalle.getNameFile());
						} else {
							saveFileInDisk(detalle, detalle.getData()
									.getBinaryStream(), path,
									detalle.getNameFile());
						}
						swPdf=false;
						docPDF = convertirToPDF.convertirDocumento(path,
								detalle.getNameFile(), detalle,swPdf);

						
						detalle.setNameFile(docPDF.toString());

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/";
			System.out.println("basePath=" + basePath);
			// El tipo de documento a mostrar (El MimeTipe)
			
			
			
			  //INICIO DE SERVLET--------------------------------------------------------------------------------		
			
				
			
		    OutputStream outStream = response.getOutputStream();
  	        String fileName=detalle.getNameFile();
	         f = new File(path + "/" + detalle.getNameFile());
	        extension = "";
			if (detalle != null && detalle.getNameFile() != null
					&& detalle.getNameFile().lastIndexOf(".") != -1) {
				extension = detalle.getNameFile().substring(
						detalle.getNameFile().lastIndexOf(".") + 1,
						detalle.getNameFile().length());
			}
	       // String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length());
	        if ("pdf".equalsIgnoreCase(extension)){
	        	response.setContentType( "application/pdf" );	
	        }else{
//	        	System.out.println("fileName="+fileName);
//	        	System.out.println("detalle.getContextType()="+detalle.getContextType());
	        	response.setContentType( detalle.getContextType().trim() );
	        }
	        	
	        
	        

	        response.setContentLength((int)f.length());
	    	response.setHeader("Content-disposition", "inline; filename=\""
					+ detalle.getNameFile() + "\"");
	    	
	    	
	        
	        byte[] buf = new byte[8192];
	        FileInputStream inStream = new FileInputStream(f);
	        int sizeRead = 0;
	        while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
	            outStream.write(buf, 0, sizeRead);
	        }
	        inStream.close();
	        outStream.close();		
			
			
			
	  	 
			
			
			//FIN DE SERVLET----------------------------------------------------------------------------------
		}
		
		
		
		return false;

	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

	public String getExtensionFile(String nameFile) {
		int pos = nameFile.indexOf(".");
		String extension = ".doc";
		if (pos != -1) {
			// extension = nameFile.substring(pos).toLowerCase();
			extension = nameFile.substring(nameFile.lastIndexOf(".") + 1,
					nameFile.length());
		}
		return extension;
	}

	private void closeIOs(InputStream in, OutputStream out) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void saveFileInDisk(Doc_detalle doc_detalle,
			InputStream imagenBuffer, String path, String nameFile) {
		try {
			// String ruta = path + "\\" + nameFile;
			String ruta = path + "/" + nameFile;

			File fichero = new File(ruta);
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
		}
	}

	/*
	 * private long retrieveBlobAsBytea( Connection conn, String fileName )
	 * throws Exception { long t1, t2; t1 = System.currentTimeMillis();
	 * PreparedStatement ps = conn.prepareStatement(
	 * "SELECT blob_data FROM blob_bytea WHERE blob_name=?"); ps.setString(1,
	 * fileName); ResultSet rs = ps.executeQuery(); if ( rs.next() ) { byte[]
	 * imgBytes = rs.getBytes(1); // use the stream in some way here }
	 * rs.close(); ps.close(); t2 = System.currentTimeMillis(); return (t2 -
	 * t1); }
	 */
	public byte[] getDataPostgres() {
		return dataPostgres;
	}

	public void setDataPostgres(byte[] dataPostgres) {
		this.dataPostgres = dataPostgres;
	}

	public boolean isSwPdf() {
		return swPdf;
	}

	public void setSwPdf(boolean swPdf) {
		this.swPdf = swPdf;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public String getRutaArchCompleto() {
		if (f!=null){
			rutaArchCompleto=f.getAbsolutePath();
		}
		return rutaArchCompleto;
	}

	public void setRutaArchCompleto(String rutaArchCompleto) {
		this.rutaArchCompleto = rutaArchCompleto;
	}
}
