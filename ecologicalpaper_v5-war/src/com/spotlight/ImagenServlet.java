package com.spotlight;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.file.Archivo;

public class ImagenServlet  extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	
	public static String ID_MMOOPCION = "IdMmoOpcion";
	protected static String ARCHIVO_IMAGE = "imagen.jpg";
	private SpotLightOpcionDTO spotLightOpcion = new SpotLightOpcionDTO();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response = process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response = process(request, response);
	}

	private HttpServletResponse process(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter(ID_MMOOPCION);
		try{
//			ConfiguracionProviderDelegate configuracionProviderDelegate = new ConfiguracionProviderDelegate();
//			spotLightOpcion.setId_mmoOpcion(Long.parseLong(id));
//			SpotLightOpcionDTO dataSpotLightImagem = configuracionProviderDelegate.dataSpotLightImagem(spotLightOpcion);
			
			File file = new File("/home/simon/workspace2/ecologicalpaper_v5-war/war/img/barras.gif");
			
			
			byte[] imagenByte =Archivo.convertirFileADataBinariaDinamico(file);
			
			
			response.setHeader("Content-Disposition", "inline; filename=imagen.jpg");
			response.setBufferSize(1024);
			response.setContentLength(imagenByte.length);
			OutputStream out = response.getOutputStream();
			try {
				out.write(imagenByte);
				out.flush();
			} finally {
				out.close();
			}
			return response; 
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return response;
		       
	}
}
