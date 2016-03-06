package com.ecological.paper.reportes.delegado;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import com.ecological.NegocioEcological;
import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.FlowsHistorico;
import com.util.Utilidades;

@Stateless
public class DelegadoEcologicalReporteEJBBean extends NegocioEcological implements
DelegadoEcologicalReporteEJBLocal{
	@EJB
	private DelegadoEJBLocal delegado = null;
	
	
	 public List<FlowsHistorico> getReporteFlowsHistorico(Doc_detalle doc_detalle) {
		   List<FlowsHistorico> flowsHistorico= new ArrayList<FlowsHistorico>();
	        //doc_detalle = (Doc_detalle)session.getAttribute("doc_detalle");
		 Doc_maestro doc_maestro=doc_detalle.getDoc_maestro();
	        flowsHistorico=delegado.findAll_FlowsHistorico(doc_maestro);
	        if (flowsHistorico==null){
	            flowsHistorico= new ArrayList();
	        }
	        
	        List<FlowsHistorico> lista = new ArrayList<FlowsHistorico>();
	        for (FlowsHistorico fh:flowsHistorico){
	            String result;
	            /*SimpleDateFormat formatter;
	            Locale locale = new Locale("es", "VE");
	            formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
	            result = formatter.format(fh.getFlow().getFecha_creado());*/
	            if (fh.getFlow().getFecha_creado()!=null){
	                fh.setFechaCreado(Utilidades.sdfShow.format(fh.getFlow().getFecha_creado()));
	            }
	            
	            Doc_estado doc_edo= new Doc_estado();
	            doc_edo.setCodigo(Long.parseLong(fh.getFlow().getTipoFlujo()));
	            doc_edo=delegado.findDocEstado(doc_edo);
	            fh.setTipoFlujo(messages.getString(doc_edo.getNombre()));
	            fh.setStatusEnQedo(messages.getString(fh.getFlow().getEstado().getNombre()));
	            lista.add(fh);
	        }
	        
	     /*   JasperReport reporte;
			try {
				  URL url = DelegadoEcologicalReporteEJBBean.class.getResource("flowHistorico.jasper");
				  System.out.println(url);
				
				reporte = (JasperReport) JRLoader.loadObject(url);
				//JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);  
				Map parametros = new HashMap();
				parametros.put("autor", "Juan");
				parametros.put("titulo", "Reporte"); 
				
				JasperPrint jasperPrint =
					JasperFillManager.fillReport(reporte,null, new JRBeanCollectionDataSource(
							lista));

				JRExporter exporter = new JRPdfExporter();  
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.pdf")); 
				exporter.exportReport(); 

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  */
	        return lista;
	    }

	public DelegadoEJBLocal getDelegado() {
		return delegado;
	}

	public void setDelegado(DelegadoEJBLocal delegado) {
		this.delegado = delegado;
	}

}
