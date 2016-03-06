package com.ecological.reportes;

 
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Reportes  {
	  private List<Participantes> listaParticipante = new ArrayList<Participantes>();
	  private List<Jugador> listaJugadores = new ArrayList<Jugador>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Reportes datasource = new Reportes();
		
		for (int i = 1; i <= 49; i++)   
		{       
			datasource.addJugadores(new Jugador(i, "Jugador " + i , "Wii"));   
		}    

		for(int i = 50; i <= 79; i++)   
		{       
			datasource.addJugadores(new Jugador(i, "Jugador " + i , "XBox"));   
		}    

		for(int i = 80; i <= 100; i++)   
		{       
			datasource.addJugadores(new Jugador(i, "Jugador " + i , "PS3"));   
		}

		for (int i = 1; i <= 100; i++) 
		{ 
		Participantes p = new Participantes(i, "Particpante " + i, "Usuario " + i, "Pass " + i, "Comentarios para " + i);
		p.setPuntos(i);
		datasource.addParticipante(p);  
		} 


		JasperReport reporte;
		try {
			  URL url = Reportes.class.getResource("reporteGrafica.jasper");
			  System.out.println(url);
			
			reporte = (JasperReport) JRLoader.loadObject(url);
			//JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);  
			Map parametros = new HashMap();
			parametros.put("autor", "Juan");
			parametros.put("titulo", "Reporte"); 
			
			JasperPrint jasperPrint =
				JasperFillManager.fillReport(reporte,null, new JRBeanCollectionDataSource(
						datasource.getListaJugadores()));

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.pdf")); 
			exporter.exportReport(); 

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	  public void addJugadores(Jugador jugador)
	    {
	       this.listaJugadores.add(jugador);
	    }


	
	 
	  public void addParticipante(Participantes participante)
	    {
	       this.listaParticipante.add(participante);
	    }


	public List<Participantes> getListaParticipantes() {
		return listaParticipante;
	}


	public void setListaParticipantes(List<Participantes> listaParticipantes) {
		this.listaParticipante = listaParticipantes;
	}

	public List<Jugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(List<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}
}
