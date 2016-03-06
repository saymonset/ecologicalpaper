/*
 * DocumentoStadisticasGraficar.java
 *
 * Created on September 6, 2008, 9:36 AM
 */

package com.graficar;

import com.ecological.datoscombo.Converdocumento;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.util.Utilidades;
import java.util.Locale;
import org.jfree.chart.*;

import java.awt.image.BufferedImage;



import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.documentos.ClienteDocumentoMaestro;
import com.ecological.util.ContextSessionRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author simon
 * @version
 */
public class DocumentoStadisticasGraficar extends HttpServlet {
    private ServicioDelegado delegado = new ServicioDelegado();
    private Doc_maestro maestro = null;
       private Doc_detalle detalle = null;
    private Converdocumento convertirToPDF = new Converdocumento();
    private byte[] dataPostgres;
 

    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    ResourceBundle confmessages = ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages = null;//ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
    private String carpeta_compartida;
    private Configuracion conf= new Configuracion();
    private boolean swPostgresVieneDeConfiguracion;
    private boolean swConfiguracionHayData;
    private List<Configuracion>   configuraciones = new ArrayList<Configuracion>();
    private Doc_historicoActivoMaestro doc_historicoActivoMaestro= new Doc_historicoActivoMaestro();
    private Doc_maestro doc_maestro;
    //historicos
    private int hist_actuBorradors;
    private int hist_darPublicos;
    private int hist_verDetalless;
    private int hist_verVinculadoss;
    private int hist_verSometerFlows;
    private int hist_nuevaVerVigs;
    private int hist_deshNuevaVers;
    private int hist_genRegs;
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        messages = contextSessionRequest.getResourceBundleGrafico("com.ecological.resource.ecologicalpaper");
        
        hist_actuBorradors=0;
        hist_darPublicos=0;
        hist_verDetalless=0;
        hist_verVinculadoss=0;
        hist_verSometerFlows=0;
        hist_nuevaVerVigs=0;
        hist_deshNuevaVers=0;
        hist_genRegs=0;
        String codigo = request.getParameter("codigo")!=null?request.getParameter("codigo"):null;
           
        //if (session != null) {
        if (codigo != null) {
            // detalle = (Doc_detalle) session.getAttribute("doc_detalleGrafico");
            //colocamos la nueva seguridad en session
          //  contextSessionRequest.clearSession(session,confmessages.getString("usuarioSession"));
            Doc_maestro doc_m= new Doc_maestro();
            doc_m.setCodigo(Long.parseLong(codigo));
            detalle =   delegado.findUltimolDoc_Detalles(doc_m);
            if (detalle != null) {
                doc_historicoActivoMaestro.setStatus(Utilidades.isActivo());
                
                doc_historicoActivoMaestro.setDoc_maestro(detalle.getDoc_maestro());
                List<Doc_historicoActivoMaestro>doc_historicoActivoMaestros=delegado.findAllDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
                if (doc_historicoActivoMaestros!=null && !doc_historicoActivoMaestros.isEmpty()){
                    for (Doc_historicoActivoMaestro doc_historico:doc_historicoActivoMaestros){
                        //obtenemos arreglos de trenodes de todos los cambios en detalle que se hicxieron
                        //para agregarlos luego como collectyions
                        getInfDetlladaHist(doc_historico);
                    }
                }
                response.setContentType("image/jpeg");
                OutputStream salida = response.getOutputStream();
                JFreeChart grafica=null;
                try {
                    //  grafica = gerarGraficoLinha3D("HABITAT", "XXXX", "YYYY");
                    grafica = gerarGraficoBarraVertical3D(messages.getString("doc_crearDocumentotab"), messages.getString("coordenadasX"), messages.getString("coordenadasY"));
                    
                      
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                
                int ancho = getParamEntero(request,"ancho",400);
                int alto = getParamEntero(request,"alto",300);
                
                ChartUtilities.writeChartAsJPEG(salida,grafica,ancho,alto);
                
                salida.close();
                
                
                
            }
            
            
            
        } else {
            System.out.println("SI ES NULA LA SESSION");
            
        }
        
        
        
        
        
    }
    
    
    public void  getInfDetlladaHist(Doc_historicoActivoMaestro doc_historico){
        
        
        Doc_historicoActivoDetalle doc_historicoActivoDetalle=new Doc_historicoActivoDetalle();
        doc_historicoActivoDetalle.setDoc_histActMaestro(doc_historico);
        List<Doc_historicoActivoDetalle> doc_historicoActivoDetalles=delegado.findAllDoc_historicoActivoDetalle(doc_historicoActivoDetalle);
        for(Doc_historicoActivoDetalle doc_historicoDetalle:doc_historicoActivoDetalles){
            if (doc_historicoDetalle.isActuBorrador()){
                hist_actuBorradors +=1;
            }
            if (doc_historicoDetalle.isDarPublico()){
                hist_darPublicos+=1;
            }
            if (doc_historicoDetalle.isVerDetalles()){
                hist_verDetalless+=1;
            }
            if (doc_historicoDetalle.isVerVinculados()){
                hist_verVinculadoss+=1;
            }
            if (doc_historicoDetalle.isVerSometerFlow()){
                hist_verSometerFlows+=1;
            }
            if (doc_historicoDetalle.isNuevaVerVig()){
                hist_nuevaVerVigs+=1;
            }
            if (doc_historicoDetalle.isDeshNuevaVer()){
                hist_deshNuevaVers+=1;
            }
            if (doc_historicoDetalle.isGenReg()){
                hist_genRegs+=1;
            }
        }
        
        //return treeData;
    }
    
    
    public void init(ServletConfig config) throws ServletException {
        
        
    }
    
    //   public static BufferedImage gerarGraficoBarraVertical3D(String tituloGrafico, String
    public  JFreeChart  gerarGraficoBarraVertical3D(String tituloGrafico, String
            
            tituloEixoX, String tituloEixoY) throws Exception {
        
        BufferedImage buf = null;
        JFreeChart chart =null;
        try {
     
            messages = contextSessionRequest.getResourceBundleGrafico("com.ecological.resource.ecologicalpaper");
      
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            
            ArrayList arrayValores= new ArrayList();
         /*   for(int i=0;i<12;i++){
                ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
                modeloGraficoItem.setMes("Mayo");
                modeloGraficoItem.setProduto("Ppsicola"+i);
                modeloGraficoItem.setQuantidade(15+i*7);
                arrayValores.add(modeloGraficoItem);
            }*/
            //   for(int i=0;i<12;i++){
            
            ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("actualizaBorrador"));
            modeloGraficoItem.setQuantidade((hist_actuBorradors));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("doc_publico"));
            modeloGraficoItem.setQuantidade((hist_darPublicos));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("doc_verdocumento"));
            modeloGraficoItem.setQuantidade((hist_verDetalless));
            arrayValores.add(modeloGraficoItem);
            
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("vincular_documentos"));
            modeloGraficoItem.setQuantidade((hist_verVinculadoss));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("flow_someter"));
            modeloGraficoItem.setQuantidade((hist_verSometerFlows));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("doc_version_nueva"));
            modeloGraficoItem.setQuantidade((hist_nuevaVerVigs));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("doc_desbloquear"));
            modeloGraficoItem.setQuantidade((hist_deshNuevaVers));
            arrayValores.add(modeloGraficoItem);
            
            modeloGraficoItem = new ModeloGraficoItem();
            modeloGraficoItem.setMes(detalle.getDoc_maestro().getNombre());
            modeloGraficoItem.setProduto(messages.getString("registro"));
            modeloGraficoItem.setQuantidade((hist_genRegs));
            arrayValores.add(modeloGraficoItem);
            
            
            Iterator iterator = arrayValores.iterator();
            
            while (iterator.hasNext()) {
                
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                
                defaultCategoryDataset.addValue(modelo.getQuantidade(),
                        
                        modelo.getProduto(), modelo.getMes().substring(0, 3));
                
            }
            
            chart = ChartFactory.createBarChart3D(tituloGrafico, tituloEixoX,
                    
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                    
                    true, false, false);
            
            chart.setBorderVisible(true);
            
            chart.setBorderPaint(Color.black);
            
            buf = chart.createBufferedImage(400, 250);
            
        } catch (Exception e) {
            
            throw new Exception(e);
            
        }
        
        // return buf;
        return chart;
        
    }
    
    
    int getParamEntero(HttpServletRequest request,String pNombre, int pDefecto) {
        String param = request.getParameter(pNombre);
        
        if (param == null || param.compareTo("") == 0) {
            return pDefecto;
        }
        
        return Integer.parseInt(param);
        
    }
    
    
    /** Processes requests for both HTTP GET and POST methods.
     * @param request servlet request
     * @param response servlet response
     */
    
    
    
    /** Handles the HTTP GET method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     *
     *
     *
     */
    
    public static JFreeChart gerarGraficoBarraVertical(String tituloGrafico, String
            
            tituloEixoX, String tituloEixoY)throws Exception {
        
        BufferedImage buf = null;
        JFreeChart chart =null;
        try {
            ArrayList arrayValores= new ArrayList();
            for(int i=0;i<3;i++){
                ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
                modeloGraficoItem.setMes("Mayo");
                modeloGraficoItem.setProduto("Ppsicola"+i);
                modeloGraficoItem.setQuantidade(15+i*7);
                arrayValores.add(modeloGraficoItem);
            }
            
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            
            Iterator iterator = arrayValores.iterator();
            
            while (iterator.hasNext()) {
                
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                
                defaultCategoryDataset.addValue(modelo.getQuantidade(),
                        
                        modelo.getProduto(), modelo.getMes().substring(0, 3));
                
            }
            
            chart = ChartFactory.createBarChart(tituloGrafico, tituloEixoX,
                    
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL, true, false, false);
            
            chart.setBorderVisible(true);
            
            chart.setBorderPaint(Color.black);
            
            buf = chart.createBufferedImage(400, 250);
            
        } catch (Exception e) {
            
            throw new Exception(e);
            
        }
        
        return chart;
        
    }
    
    
    
    /**
     *
     * Gera um Grafico de Linhas
     *
     */
    
    public static JFreeChart gerarGraficoLinha(String tituloGrafico, String tituloEixoX,
            
            String tituloEixoY) throws Exception {
        JFreeChart chart =null;
        BufferedImage buf = null;
        
        try {
            ArrayList arrayValores= new ArrayList();
            for(int i=0;i<3;i++){
                ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
                modeloGraficoItem.setMes("Mayo");
                modeloGraficoItem.setProduto("Ppsicola"+i);
                modeloGraficoItem.setQuantidade(15+i*7);
                arrayValores.add(modeloGraficoItem);
            }
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            
            Iterator iterator = arrayValores.iterator();
            
            while (iterator.hasNext()) {
                
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                
                defaultCategoryDataset.addValue(modelo.getQuantidade(),
                        
                        modelo.getProduto(), modelo.getMes().substring(0, 3));
                
            }
            
            chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX,
                    
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                    
                    true, false, false);
            
            chart.setBorderVisible(true);
            
            chart.setBorderPaint(Color.black);
            
            buf = chart.createBufferedImage(400, 250);
            
        } catch (Exception e) {
            
            throw new Exception(e);
            
        }
        
        return chart;
        
    }
    
    
    
    /**
     *
     * Gera um grafico de linhas 3D
     *
     */
    
    public static JFreeChart gerarGraficoLinha3D(String tituloGrafico, String
            
            tituloEixoX, String tituloEixoY)throws Exception {
        
        BufferedImage buf = null;
        JFreeChart chart =null;
        try {
            ArrayList arrayValores= new ArrayList();
            for(int i=0;i<3;i++){
                ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
                modeloGraficoItem.setMes("Mayo");
                modeloGraficoItem.setProduto("Ppsicola"+i);
                modeloGraficoItem.setQuantidade(15+i*7);
                arrayValores.add(modeloGraficoItem);
            }
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            
            Iterator iterator = arrayValores.iterator();
            
            while (iterator.hasNext()) {
                
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                
                defaultCategoryDataset.addValue(modelo.getQuantidade(),
                        
                        modelo.getProduto(), modelo.getMes().substring(0, 3));
                
            }
            
            chart = ChartFactory.createLineChart3D(tituloGrafico, tituloEixoX,
                    
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                    
                    true, false, false);
            
            chart.setBorderVisible(true);
            
            chart.setBorderPaint(Color.black);
            
            buf = chart.createBufferedImage(400, 250);
            
        } catch (Exception e) {
            
            throw new Exception(e);
            
        }
        
        return chart;
        
    }
    
    
    
    
}