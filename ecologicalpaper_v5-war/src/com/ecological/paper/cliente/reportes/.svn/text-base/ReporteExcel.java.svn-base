/*
 * ReporteExcel.java
 *
 * Created on October 1, 2007, 10:01 AM
 */

package com.ecological.paper.cliente.reportes;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.documentos.ClienteDocumentoMaestro;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 

/**
 *
 * @author simon
 * @version
 */
public class ReporteExcel extends HttpServlet {
    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    private ServicioDelegado delegado =new ServicioDelegado();
    ResourceBundle confmessages =ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages =ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
    List<ClienteDocumentoMaestro> listamostar = new ArrayList<ClienteDocumentoMaestro>();
       private String carpeta_compartida;
    private Configuracion conf= new Configuracion();
    private boolean swPostgresVieneDeConfiguracion;
    private boolean swConfiguracionHayData;
    private List<Configuracion>   configuraciones = new ArrayList<Configuracion>();
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            generarExcel(request,response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    
    
    
    public void generarExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
         
        
          configuraciones= delegado.find_allConfiguracion();
        if (configuraciones!=null && configuraciones.size()>0){
            conf=configuraciones.get(0);
            swPostgresVieneDeConfiguracion=conf.isBdpostgres();
            swConfiguracionHayData=true;
        }
        if (swConfiguracionHayData){
            carpeta_compartida=conf.getCarpetaCompartida().trim();
        }else{
            carpeta_compartida=  confmessages.getString("carpeta_compartida");
        }
        String tmp=carpeta_compartida;//confmessages.getString("carpeta_compartida");
        String nameFile = tmp +"/FormatoSacop.xls";
        response.setContentType("application/vnd.ms-excel");
        InputStream inputfile = new FileInputStream(nameFile);
        HSSFWorkbook workbook = new HSSFWorkbook(inputfile,true);
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Doc_detalle> listamostar=null;
        if (session.getAttribute("listamostar")!=null){
            listamostar =(List<Doc_detalle>)session.getAttribute("listamostar") ;
        }
        
        HSSFCellStyle style1 = null;
        HSSFCellStyle style2 = null;
        HSSFCellStyle style3 = null;
        HSSFCellStyle style4 = null;
        HSSFCellStyle style5 = null;
        HSSFCellStyle style6 = null;
        HSSFCellStyle style7 = null;
        HSSFCellStyle style8 = null;
        HSSFCellStyle style9 = null;
        try {
            HSSFRow fil = sheet.getRow(13);
            if (fil!=null) {
                HSSFCell cell = fil.getCell((short)0);
                if (cell!=null) {
                    style1 = cell.getCellStyle();
                }
                cell = fil.getCell((short)1);
                if (cell!=null) {
                    style2 = cell.getCellStyle();
                }
                cell = fil.getCell((short)2);
                if (cell!=null) {
                    style3 = cell.getCellStyle();
                }
                cell = fil.getCell((short)3);
                if (cell!=null) {
                    style4 = cell.getCellStyle();
                }
                cell = fil.getCell((short)4);
                if (cell!=null) {
                    style5 = cell.getCellStyle();
                }
                cell = fil.getCell((short)5);
                if (cell!=null) {
                    style6 = cell.getCellStyle();
                }
                cell = fil.getCell((short)6);
                if (cell!=null) {
                    style7 = cell.getCellStyle();
                }
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    style8 = cell.getCellStyle();
                }
                cell = fil.getCell((short)8);
                if (cell!=null) {
                    style9 = cell.getCellStyle();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        int fila = 13;
        HSSFRow row;
        HSSFCell celda;
        //Estableciendo T�tulo del Reporte seg�n el Idioma del Usuario :D
        row = sheet.getRow(1);
        if (row!=null) {
            celda = row.getCell((short)0);
            celda.setCellValue(messages.getString("usuario_fechac") +  " " +  Utilidades.sdfShowWithoutHour.format(new java.util.Date()));
        }
        //T�tulos de la Tabla
        row = sheet.getRow(12);
      
        
        int cont = 0;
        int contBorrador = 0;
        int contEmitido  = 0;
        int contAprobado = 0;
        int contEnEjecucion = 0;
        int edoVerificacion = 0;
        int edoCerrado = 0;
        int edoRechazado = 0;
        for(Doc_detalle doc:listamostar){
            //while (rs.next()) {
            row = sheet.createRow((short)fila);
            //Valor y estilo si Aplica en la Columna # 1
            celda = row.createCell((short)0);
            if (style1!=null) {
                celda.setCellStyle(style1);
            }
            celda.setCellValue(cont++);
            
            //Valor y estilo si Aplica en la Columna # 2
            celda = row.createCell((short)1);
            if (style2!=null) {
                celda.setCellStyle(style2);
            }
            celda.setCellValue(doc.getDoc_maestro().getNombre());
            //Valor y estilo si Aplica en la Columna # 3
            celda = row.createCell((short)2);
            if (style3!=null) {
                celda.setCellStyle(style3);
            }
            celda.setCellValue(doc.getDoc_maestro().isPublico());
            
            //Valor y estilo si Aplica en la Columna # 4
            celda = row.createCell((short)3);
            if (style4!=null) {
                celda.setCellStyle(style4);
            }
            celda.setCellValue(doc.getDoc_estado().getNombre());
            //Valor y estilo si Aplica en la Columna # 5
            celda = row.createCell((short)4);
            if (style5!=null) {
                celda.setCellStyle(style5);
            }
            
            celda.setCellValue(doc.getDoc_maestro().getConsecutivo());
            //Valor y estilo si Aplica en la Columna # 6
            celda = row.createCell((short)5);
            
            if (style6!=null) {
                celda.setCellStyle(style6);
            }
            if (doc.getDoc_maestro().getFecha_creado()!=null){
                celda.setCellValue(doc.getDoc_maestro().getFecha_creado());
            }
            
            
            //Valor y estilo si Aplica en la Columna # 7
            celda = row.createCell((short)6);
            if (style7!=null) {
                celda.setCellStyle(style7);
            }
            if (doc.getDuenio()!=null && doc.getDoc_maestro().getDoc_tipo()!=null
                    && doc.getDoc_maestro().getDoc_tipo()!=null){
                celda.setCellValue(doc.getDoc_maestro().getDoc_tipo().getNombre());
            }
            
            
            //Valor y estilo si Aplica en la Columna # 8
            celda = row.createCell((short)7);
            if (style8!=null) {
                celda.setCellStyle(style8);
            }
            if (doc.getDuenio()!=null && doc.getDuenio().getNombre()!=null
                    && doc.getDuenio().getApellido()!=null && doc.getDuenio().getCargo()!=null
                    && doc.getDuenio().getCargo().getNombre()!=null){
                celda.setCellValue(doc.getDuenio().getNombre()+" "+
                        doc.getDuenio().getApellido()+" "+
                        "["+doc.getDuenio().getCargo().getNombre()+"]");
                
            }
            //Valor y estilo si Aplica en la Columna # 9
            celda = row.createCell((short)8);
            if (style9!=null) {
                celda.setCellStyle(style9);
            }
            
            fila++;
        }
        //Estableciendo Estad�sticas
        //Borrador
    
        //Enviando el Archivo al Cliente
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=reporteSACOP.xls");
        response.setHeader("content-transfer-encoding", "binary");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
        
    }
    
    
    // </editor-fold>
}
