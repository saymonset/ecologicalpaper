/*
 * ClienteDocumentoGenerar.java
 *
 * Created on September 21, 2007, 7:01 AM
 */
package com.ecological.paper.cliente.documentos;

import com.ecological.configuracion.Configuracion;
import com.ecological.datoscombo.Converdocumento;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import java.io.*;
import java.net.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;


import org.hibernate.Hibernate;

/**
 * 
 * @author simon
 * @version
 */
public class ClienteDocumentoGenerar extends HttpServlet {

	private ServicioDelegado delegado = new ServicioDelegado();

	// private Doc_maestro maestro = null;
	private Doc_detalle detalle = null;
	private Converdocumento convertirToPDF = new Converdocumento();
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

	/** Creates a new instance of ClienteDocumentoGenerar */
	public ClienteDocumentoGenerar() {

	}

	protected boolean processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);
		String docPDF = "";
		// verificamos si es solo lectura
		String onlyViewR = request.getParameter("onlyView");
		String onlyView = "";

		Doc_detalle detalle = null;
		if (session != null) {
			detalle = (Doc_detalle) session.getAttribute("doc_detalle");
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
				session.setAttribute("doc_detalle", detalle);
				System.out.println("detalle.getNameFile()="
						+ detalle.getNameFile());
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
			String ruta = carpeta_compartida;// confmessages.getString("carpeta_compartida");//
												// + "\\" + "doc_detalle" + "\\"
												// + detalle.getCodigo();
			boolean success = (new File(ruta)).mkdir();
			// LINUX
			String tmp = ruta;// ;confmessages.getString("carpeta_compartida");
			// String path =this.getServletContext().getRealPath("/")+tmp;
			String path = ruta;// confmessages.getString("carpeta_compartida");
			// UploadedFile upFile = null;

			try {
				// borramos cualquier fichero del mismo tipo borrado
				// anteriormente
				// File ficheroBorra = new File(path + "\\" +
				// detalle.getNameFile());
				File ficheroBorra = new File(path + "/" + detalle.getNameFile());
				ficheroBorra.delete();
				// getExtensionFile(detalle.getNameFile());
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
							// detalle.setNameFile("origen.doc");
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
								docPDF = convertirToPDF.convertirDocumento(
										path, detalle.getNameFile(),detalle,true);
								detalle.setContextType("application/pdf");
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
			response.setContentType(detalle.getContextType());
			InputStream in = null;
			OutputStream out = null;

			StringBuffer action = new StringBuffer();
			action.append("http://").append(request.getServerName())
					.append(":").append(request.getServerPort());
			action.append(request.getContextPath());

			String fileURL = new String();

			// fileURL = action.toString() + "/" +
			// confmessages.getString("carpeta_compartida") + "/" +
			// detalle.getNameFile();
			// System.out.println("System.getProperty(java.io.tmpdir="+System.getProperty("java.io.tmpdir"));
			fileURL = action.toString() + "/" + tmp + "/"
					+ detalle.getNameFile();

			System.out.println("fileURL=" + fileURL);
			response.resetBuffer();
			try {
				boolean onlyRead = "onlyView".equalsIgnoreCase(onlyView);

				boolean sendURL = request.getParameter("sendURL") != null;
				if (onlyRead) {
					response.setHeader("Content-disposition",
							"inline; filename=\"" + detalle.getNameFile()
									+ "\"");
				} else {
					response.setHeader("Content-disposition",
							"attachment; filename=\"" + detalle.getNameFile()
									+ "\"");
				}
				// SIEMPRE SE MANDARA A DESCARGAR EL ARCHIVO INICIO
				response.setHeader("Content-disposition",
						"attachment; filename=\"" + detalle.getNameFile()
								+ "\"");
				// SIEMPRE SE MANDARA A DESCARGAR EL ARCHIVO FIN
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setHeader("content-transfer-encoding", "binary");

				if (!sendURL || (!onlyRead)) {
					// File fichero = new File(path + "\\" +
					// detalle.getNameFile());
					File fichero = new File(path + "/" + detalle.getNameFile());

					/*
					 * System.out.println("path + \"\\\" + detalle.getNameFile()="
					 * +path + "\\" + detalle.getNameFile());
					 * System.out.println(
					 * "fichero.getPath()="+fichero.getPath());
					 * System.out.println
					 * ("fichero.getAbsolutePath()="+fichero.getAbsolutePath());
					 * System.out.println("detalle.getContextType())"+detalle.
					 * getContextType());
					 */
					response.setHeader("Content-Type", detalle.getContextType());
					in = new FileInputStream(fichero);
					response.setHeader("Content-Length", "" + in.available());
					out = response.getOutputStream();
					int bytesRead = 0;
					byte buffer[] = new byte[8192];
					while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
						out.write(buffer, 0, bytesRead);
						response.flushBuffer();
					}
					closeIOs(in, out);
					try {
						fichero.delete();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return true;
				} else {
					response.setHeader("Cache-control", "no-cache");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);
					response.sendRedirect(fileURL);
					return true;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				closeIOs(in, out);
				
			}
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
			try {
				if (imagenBuffer != null) {
					imagenBuffer.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
}
