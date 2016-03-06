/*
 * Converdocumento.java
 *
 * Created on August 12, 2007, 11:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.datoscombo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.ecological.paper.documentos.Doc_detalle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

/**
 * 
 * @author simon
 */
public class Converdocumento {

	/** Creates a new instance of Converdocumento */
	public Converdocumento() {
	}

	public  String convertirDocumento(String path, String origen,
			Doc_detalle detalle, boolean swPdf) {
		String archivoDestinoPDF;
		String archivoSeguro = new String(detalle.getCodigo() + "segurida.pdf");

		if (origen.lastIndexOf(".") != -1) {
			archivoDestinoPDF= detalle.getCodigo()
					+ origen.substring(0, origen.lastIndexOf(".")) + ".pdf";
		} else {
			archivoDestinoPDF = detalle.getCodigo() + "docView.pdf";
		}

		String ruta1 = path + "/" + origen.toString();
		File inputFile = new File(ruta1);

		// File inputFile = new File(path+"/"+origen.toString());
		String ruta2 = path + "/" + archivoDestinoPDF.toString();
		String ruta3 = path + "/" + archivoSeguro.toString();
//		System.out.println("ruta1=" + ruta1);
//		System.out.println("ruta2=" + ruta2);
//		System.out.println("ruta3=" + ruta3);
		
		try {
			if (!swPdf) {
				File outputFile = new File(ruta2);
			
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);
				connection.connect();
				try {
					
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							connection);
					converter.convert(inputFile, outputFile);
					// close the connection
					connection.disconnect();
				} catch (Exception e) {
					 System.out.println("No lo pudo convertir");
					 return origen.toString();
					 
				}
			

			} 
			
		//	File archivoSeguroFile = new File(ruta3);
			PdfReader reader;
			OutputStream fout;
			try {
				if (!swPdf) {
					//SE CONVIRTYIO EN PDF
					reader = new PdfReader(ruta2);
					  fout = new FileOutputStream(ruta3);	
				}else{
					//ESTE ES UN PDF ORIGEN
					File loCreamosPDF = new File(ruta3);
					reader = new PdfReader(ruta1);
					  fout = new FileOutputStream(ruta3);
				}
				
				PdfStamper stamp;
				try {
	 				stamp = new PdfStamper(reader, fout);
					stamp.setEncryption(null, null, 0, true);
					stamp.setViewerPreferences(PdfWriter.HideMenubar);
					stamp.setViewerPreferences(PdfWriter.HideWindowUI);
					stamp.setViewerPreferences(PdfWriter.HideToolbar);
					stamp.close();

					stamp.close();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
				 
					return origen.toString();
				}

			} catch (IOException e) {
				return origen.toString();
			}

		} catch (ConnectException e) {
			//CREAMOS UN PDF QUE NOS DICE QUE DEBE N TENER OIPEN OFFICE ABIERTO SU PUERTO 8100
			// step 1
			Document document = new Document();
			// step 2
			try {
				PdfWriter.getInstance(document, new FileOutputStream(ruta3));
				// step 3
				document.open();
				// step 4
				document.add(new Paragraph(
						"the program use openoffice for the conversion, so you have to start openoffice as a service. Open the terminal and enter: soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard"));
				// step 5
				document.close();
				//INICIO si quitamos este origen.toString() , nos devoilvera un pdf  que dice u have to start openoffice as a service. Open the terminal and enter: soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard"));
				return origen.toString();
				//FIN si quitamos este origen.toString() , nos devoilvera un pdf  que dice u have to start openoffice as a service. Open the terminal and enter: soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return archivoSeguro;
	}

	public File darSeguridadPDF(File file) {

		try {
			// step 1
			Document document = new Document();
			int permissions = ~(PdfWriter.ALLOW_MODIFY_ANNOTATIONS
					| PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_ASSEMBLY
					| PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY
					| PdfWriter.AllowModifyAnnotations | PdfWriter.AllowFillIn
					| PdfWriter.AllowAssembly | PdfWriter.AllowModifyContents | PdfWriter.AllowScreenReaders);
			document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file.getAbsolutePath()));
			// writer.setEncryption(PdfWriter.STRENGTH128BITS, "", "",
			// permissions );
			writer.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.CenterWindow | permissions);

			document.open();
			document.add(new Paragraph("hello"));
			document.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;

	}

	public static File pegarArchivosPDF(List<File> archivosPDF,
			final String prefijo, Doc_detalle detalle) throws Exception {
		File archivoPDF = null;
		String archivoSeguro = new String(detalle.getCodigo() + "segurida.pdf");
		String ruta3 = prefijo + "/" + archivoSeguro.toString();
		try {
			archivoPDF = new File(ruta3);
			// archivoPDF = File.createTempFile(prefijo + "_", ".pdf");
			Converdocumento converdocumento = new Converdocumento();

			PdfReader reader = new PdfReader(new FileInputStream(
					(File) archivosPDF.get(0)));
			Document document = new Document(reader.getPageSizeWithRotation(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(
					archivoPDF));
			PdfImportedPage page = null;

			document.open();
			for (File file : archivosPDF) {
				reader = new PdfReader(new FileInputStream(file));
				int paginas = reader.getNumberOfPages();
				for (int i = 1; i <= paginas; i++) {
					page = copy.getImportedPage(reader, i);
					copy.addPage(page);
					// if (i == 22) {
					// System.out.println("terminando");
					// break;
					// }
				}
			}
			// for (int x = archivosPDF.size() - 1; x >= 0; x--) {
			// File file = archivosPDF.get(x);
			// reader = new PdfReader(new FileInputStream(file));
			// int paginas = reader.getNumberOfPages();
			// for(int i = 1; i <= paginas; i++) {
			// page = copy.getImportedPage(reader, i);
			// copy.addPage(page);
			// }
			// }
			document.close();
			reader.close();
			document = null;
			reader = null;
			archivoPDF = converdocumento.darSeguridadPDF(archivoPDF);
			return archivoPDF;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new Exception("error.sis.generacion_pdf");
		} catch (DocumentException ex) {
			throw new Exception("error.sis.generacion_pdf");
		}
	}
}
