package com.ecological.paper.cliente.documentos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import com.ecological.util.ContextSessionRequest;

/**
 * Servlet implementation class ClienteDocumentoDiferencia
 */
public class ClienteDocumentoDiferencia extends HttpServlet {

	private ServicioDelegado delegado = new ServicioDelegado();

	// private Doc_maestro maestro = null;
	private Doc_detalle detalle = null;
	private Doc_detalle detalle2 = null;
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
	public ClienteDocumentoDiferencia() {

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
			detalle = (Doc_detalle) session.getAttribute("doc_detalle1");
			detalle2 = (Doc_detalle) session.getAttribute("doc_detalle2");
			System.out.println("NO ES NULA LA SESSION ");
			if (detalle != null) {
				session.setAttribute("doc_detalle", detalle);
				System.out.println("detalle.getNameFile()="
						+ detalle.getNameFile());
			}
		} else {
			System.out.println("SI ES NULA LA SESSION ");

		}
		// ___________________________________________________________________
		// hay extensuiones que no c porque no pueden ser solo only view
		String extension = "";
		String extension2 = "";
		if (detalle != null && detalle.getNameFile() != null
				&& detalle.getNameFile().lastIndexOf(".") != -1) {
			extension = detalle.getNameFile().substring(
					detalle.getNameFile().lastIndexOf(".") + 1,
					detalle.getNameFile().length());
		}

		if (detalle2 != null && detalle2.getNameFile() != null
				&& detalle2.getNameFile().lastIndexOf(".") != -1) {
			extension2 = detalle2.getNameFile().substring(
					detalle2.getNameFile().lastIndexOf(".") + 1,
					detalle2.getNameFile().length());
		}

		if (session != null && session.getAttribute("onlyView") != null) {
			onlyView = (String) session.getAttribute("onlyView");
			if (onlyView != null) {
				session.setAttribute("onlyView", onlyView);
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
			String nombre1 = "file1"+detalle.getNameFile().replace(" ", "_");
			String nombre2 = "file2"+detalle2.getNameFile().replace(" ", "_");

			try {
				// borramos cualquier fichero del mismo tipo borrado
				// anteriormente
				File ficheroBorra = new File(path + "/" + nombre1);
				ficheroBorra.delete();

				detalle2.setNameFile("segundodocumento.txt");

				ficheroBorra = new File(path + "/" + nombre2);
				ficheroBorra.delete();

				// hacemos la conversion para tratar en lo mas minimo de que no
				// lo modifiquen
				// siempore van hacer .doc, para que sean convertidos
				HashMap queTipoContextType = new HashMap();
				if (detalle.getData() == null
						|| detalle.getData().getBinaryStream() == null) {
					saveFileInDisk(detalle, null, path, nombre1);
					saveFileInDisk(detalle2, null, path, nombre2);
				} else {
					saveFileInDisk(detalle,
							detalle.getData().getBinaryStream(), path, nombre1);
					saveFileInDisk(detalle2, detalle2.getData()
							.getBinaryStream(), path, nombre2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(path + "/" + nombre2);
			// DIFERENCIKAS DEL ARCHIVO
			String diferencia = diffLinux(path, nombre1, nombre2,
					detalle.getNameFile(),detalle2.getNameFile());
			
			File ficheroBorra = new File(path + "/" + "diferencia.txt");
			ficheroBorra.delete();

			File fileDiferencia = new File(path + "/" + "diferencia.txt");

			FileWriter writer = new FileWriter(fileDiferencia, true);
			writer.write(diferencia);
			writer.flush();
			writer.close();

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
					+ fileDiferencia.getName();

			System.out.println("fileURL=" + fileURL);
			response.resetBuffer();
			try {
				boolean onlyRead = "onlyView".equalsIgnoreCase(onlyView);

				boolean sendURL = request.getParameter("sendURL") != null;
				if (onlyRead) {
					response.setHeader("Content-disposition",
							"inline; filename=\"" + fileDiferencia.getName()
									+ "\"");
				} else {
					response.setHeader("Content-disposition",
							"attachment; filename=\""
									+ fileDiferencia.getName() + "\"");
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

					response
							.setHeader("Content-Type", detalle.getContextType());
					in = new FileInputStream(fileDiferencia);
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
						fileDiferencia.delete();
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
		}
	}

	public String diffLinux(String path, String nameFile, String nameFile2,
			String nomOriginalFile1,String nomOriginalFile2) {

		Process proc;
		
		StringBuffer diferencia = new StringBuffer("Ninguna Diferencia");
		try {
			 

			String ruta = path + "/" + nameFile;
			String ruta2 = path + "/" + nameFile2;
			System.out.println(ruta);
			System.out.println(ruta2);
			ArrayList<String> file1 = new ArrayList<String>();
			ArrayList<String> file2 = new ArrayList<String>();
			
			String linea = new String();

			BufferedReader origen = new BufferedReader(new FileReader(ruta));
			while ((linea = origen.readLine()) != null) {
				file1.add(linea);
			}
			
			origen = new BufferedReader(new FileReader(ruta2));
			while ((linea = origen.readLine()) != null) {
				file2.add(linea);
			}
			
			if (file1.size()>=file2.size()){
				diferencia = new StringBuffer("");
				int k=0;
				while(k<file1.size()){
					String cad1=file1.get(k);
					String cad2="";
					
					if (k<file2.size()){
						cad2=file2.get(k);
					}
				 
					if (cad1.compareTo(cad2)!=0){
						diferencia.append(nomOriginalFile1+"  Line:"+(k+1)+" >> ").append(cad1).append("\n");
						diferencia.append(nomOriginalFile2+" Line:"+(k+1)+" << ").append(cad2).append("\n");
					}
					++k;
				}
				
			}else if (file2.size()>file1.size()){
				diferencia = new StringBuffer("");
				int k=0;
				while(k<file2.size()){
					String cad1=file2.get(k);
					String cad2="";
					if (k<file1.size()){
						cad2=file1.get(k);
					}
					if (cad1.compareTo(cad2)!=0){
						diferencia.append(nomOriginalFile2+"  Line:"+(k+1)+" >> ").append(cad1).append("\n");
						diferencia.append(nomOriginalFile1+" Line:"+(k+1)+" << ").append(cad2).append("\n");

					}
					++k;
				}
				
			}

			/*proc = Runtime.getRuntime().exec("diff " + ruta + " " + ruta2);

			InputStream is = proc.getInputStream();
			int size;
			String s;
			try {
				int exCode = proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer ret = new StringBuffer();
			while ((size = is.available()) != 0) {
				byte[] b = new byte[size];
				is.read(b);
				s = new String(b);
				ret.append(s);
			}
		     System.out.println(ret.toString());
			diferencia = ret.toString();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diferencia.toString();

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
