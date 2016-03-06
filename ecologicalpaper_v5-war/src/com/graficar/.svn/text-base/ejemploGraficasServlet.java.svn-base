/*
 * ejemploGraficasServlet.java
 *
 * Created on 11 de noviembre de 2007, 10:51 AM
 */

package com.graficar;

import com.ecological.hilos.ControlTiempoInFlows;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

import java.util.Iterator;



import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PiePlot;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;
/**
 *
 * @author  Roberto Canales
 * @version
 */
public class ejemploGraficasServlet extends HttpServlet {
  /*  public JFreeChart crearChart() {
        XYSeries series = new XYSeries("Evolucion Sesiones");
        series.add(1, 23);
        series.add(2, 34);
        series.add(3, 51);
        series.add(4, 67);
        series.add(5, 89);
        series.add(6, 121);
        series.add(7, 137);
        XYDataset juegoDatos= new XYSeriesCollection(series);
   
        JFreeChart chart =
                ChartFactory.createLineXYChart("Sesiones en Adictos al Trabajo",
                "Meses", "Sesiones", juegoDatos,
                PlotOrientation.VERTICAL,
                true,true,true);
   
        return chart;
   
    }*/
    
    public void init(ServletConfig config) throws ServletException {
      
        
    }
    
    //   public static BufferedImage gerarGraficoBarraVertical3D(String tituloGrafico, String
    public static JFreeChart gerarGraficoBarraVertical3D(String tituloGrafico, String
            
            tituloEixoX, String tituloEixoY) throws Exception {
        
        BufferedImage buf = null;
        JFreeChart chart =null;
        try {
            
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            
            ArrayList arrayValores= new ArrayList();
            for(int i=0;i<12;i++){
                ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
                modeloGraficoItem.setMes("Mayo");
                modeloGraficoItem.setProduto("Ppsicola"+i);
                modeloGraficoItem.setQuantidade(15+i*7);
                arrayValores.add(modeloGraficoItem);
            }
            
            
            
            
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
    
    
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("image/jpeg");
        
        OutputStream salida = response.getOutputStream();
        JFreeChart grafica=null;
        try {
            //  grafica = gerarGraficoLinha3D("HABITAT", "XXXX", "YYYY");
            grafica = gerarGraficoBarraVertical3D("HABITAT", "XXXX", "YYYY");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        int ancho = getParamEntero(request,"ancho",400);
        int alto = getParamEntero(request,"alto",300);
        
        ChartUtilities.writeChartAsJPEG(salida,grafica,ancho,alto);
        
        salida.close();
    }
    
}