/*
 * ServletReporteREMESA.java
 *
 * Created on 27 de febrero de 2008, 07:47 AM
 */

package com.sofytware.cliente.euj;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.documentos.ClienteDocumentoMaestro;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.software.eujovans.clientes.Factura;
import com.util.Utilidades;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
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
public class ServletReporteREMESA extends HttpServlet {
    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    private ServicioDelegado delegado = new ServicioDelegado();
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
        //String path = this.getServletContext().getRealPath("/")+"\\estilo";
         
        
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
        boolean swRemesa= session.getAttribute("swRemesa")!=null?(Boolean) session.getAttribute("swRemesa"):false;
        String nameFile ="";
        
        String servidor =request.getRemoteAddr();
        String servidor2 =request.getRemoteHost();
        
        boolean   mismoServidor=servidor2.equalsIgnoreCase(confmessages.getString("serverip"));
        if (mismoServidor){
            if (swRemesa){
                nameFile = tmp +"/remesa1.xls";
            } else{
                nameFile = tmp +"/cobranza1.xls";
            }
        }else{
            if (swRemesa){
                nameFile = tmp +"/remesa2.xls";
            } else{
                nameFile = tmp +"/cobranza2.xls";
            }
        }
        
        
        response.setContentType("application/vnd.ms-excel");
        InputStream inputfile = new FileInputStream(nameFile);
        HSSFWorkbook workbook = new HSSFWorkbook(inputfile,true);
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Factura> listamostar=null;
        
        if (session.getAttribute("factura_S")!=null){
            listamostar =(List<Factura>)session.getAttribute("factura_S") ;
        }
        //Estableciendo Estad�sticas
        //Borrador
        
        if (swRemesa){
            remesa( sheet,listamostar);
        }else{
            cobranza( sheet,listamostar);
        }
        
        //Enviando el Archivo al Cliente
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=reporteSACOP.xls");
        response.setHeader("content-transfer-encoding", "binary");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
        
    }
    public void remesa( HSSFSheet sheet,List<Factura> listamostar){
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
            HSSFRow fil = sheet.getRow(2);
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
        
        int fila = 2;
        HSSFRow row;
        HSSFCell celda;
        //Estableciendo T�tulo del Reporte seg�n el Idioma del Usuario :D
        row = sheet.getRow(0);
        if (row!=null) {
            celda = row.getCell((short)0);
            //         celda.setCellValue(Utilidades.sdfShowWithoutHour.format(new java.util.Date()));
        }
        //T�tulos de la Tabla
        row = sheet.getRow(12);
        
        
        for(Factura doc:listamostar){
            
            //while (rs.next()) {
            row = sheet.createRow((short)fila);
            //Valor y estilo si Aplica en la Columna # 1
            celda = row.createCell((short)0);
            
            if (doc.getFecha()!=null){
                celda.setCellValue(Utilidades.sdfShowWithoutHour.format(doc.getFecha()));
            }else{
                celda.setCellValue(Utilidades.sdfShowWithoutHour.format(new Date()));
            }
            
            //Valor y estilo si Aplica en la Columna # 2
            celda = row.createCell((short)1);
            
            celda.setCellValue(doc.getNumero_entrega());
            
            
            //Valor y estilo si Aplica en la Columna # 3
          /*  celda = row.createCell((short)2);
            if (style3!=null) {
                celda.setCellStyle(style3);
            }
            celda.setCellValue(doc.getNumero_entrega());*/
            
            //Valor y estilo si Aplica en la Columna # 4
            celda = row.createCell((short)3);
            
            celda.setCellValue(doc.getDestinatario().getNombre());
            //Valor y estilo si Aplica en la Columna # 5
            celda = row.createCell((short)4);
            
            
            celda.setCellValue(doc.getCant_bultos());
            //Valor y estilo si Aplica en la Columna # 6
            celda = row.createCell((short)5);
            
            
            celda.setCellValue(doc.getDestinatario().getCiudad().getNombre());
            
            
            
            //Valor y estilo si Aplica en la Columna # 7
            celda = row.createCell((short)6);
            
            
            celda.setCellValue(doc.getFactura());
            
            
            //Valor y estilo si Aplica en la Columna # 8
            celda = row.createCell((short)7);
            
            if (doc.isCredito()){
                // celda = row.createCell((short)7);
                celda.setCellValue(doc.getRemitente().getNombre());
                
            }else{
                //celda = row.createCell((short)7);
                celda.setCellValue(doc.getFlete_kg_vol());
            }
            
            
            //Valor y estilo si Aplica en la Columna # 9
            celda = row.createCell((short)8);
            
            if (doc.isCredito()){
                //celda = row.createCell((short)8);
                celda.setCellValue(doc.getFlete_kg_vol());
            }else{
                //celda = row.createCell((short)8);
                celda.setCellValue(doc.getRemitente().getNombre());
            }
            
            
            celda = row.createCell((short)9);
            
            celda.setCellValue(doc.getSeguro());
            
            celda = row.createCell((short)10);
            
            celda.setCellValue(doc.getIva());
            
            celda = row.createCell((short)11);
            
            celda.setCellValue(doc.getTotal_fletes());
            
            celda = row.createCell((short)12);
            
            celda.setCellValue(doc.getValor_decl());
            
            celda = row.createCell((short)13);
            
            if (doc.getChofer()!=null){
                celda.setCellValue(doc.getChofer().getNombre() + " " + doc.getChofer().getApellido());
                
            }
            
            celda = row.createCell((short)14);
            
            if (doc.getFechaemitido()!=null){
                celda.setCellValue(Utilidades.sdfShowWithoutHour.format(doc.getFechaemitido()));
            }
            
            
            
            if (!doc.isStatus()){
                celda = row.createCell((short)15);
                
                celda.setCellValue(messages.getString("anulada"));
            }
            fila++;
        }
        
        
    }
    
    
    
    public void cobranza( HSSFSheet sheet,List<Factura> listamostar){
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
            HSSFRow fil = sheet.getRow(11);
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
        
        int fila = 6;
        HSSFRow row;
        HSSFCell celda;
        //Estableciendo T�tulo del Reporte seg�n el Idioma del Usuario :D
        row = sheet.getRow(2);
        if (row!=null) {
            celda = row.getCell((short)0);
            for(Factura doc:listamostar){
                celda.setCellValue(doc.getRemitente().getNombre());
                break;
            }
        }
        row = sheet.getRow(0);
        celda = row.getCell((short)7);
        celda.setCellValue(messages.getString("fecha") + " " + Utilidades.sdfShowWithoutHour.format(new Date()));
        
        //T�tulos de la Tabla
        row = sheet.getRow(6);
        for(Factura doc:listamostar){
            
            //while (rs.next()) {
            row = sheet.createRow((short)fila);
            //Valor y estilo si Aplica en la Columna # 1
            celda = row.createCell((short)0);
            
            celda.setCellValue(doc.getNumero_entrega());
            
            //Valor y estilo si Aplica en la Columna # 2
            celda = row.createCell((short)1);
            
            celda.setCellValue(doc.getCant_bultos());
            
            
            //Valor y estilo si Aplica en la Columna # 3
            celda = row.createCell((short)2);
            
            //if (doc.getFechaconfirmaentrega()!=null){
            if (doc.getFecha()!=null){
                celda.setCellValue(Utilidades.sdfShowWithoutHour.format(doc.getFecha()) );
            } else{
//                celda.setCellValue(Utilidades.sdfShowWithoutHour.format(new Date()) );
            }
            
            
            //Valor y estilo si Aplica en la Columna # 4
            celda = row.createCell((short)3);
            
            celda.setCellValue(doc.getDestinatario().getNombre());
            
//Valor y estilo si Aplica en la Columna # 5
            celda = row.createCell((short)4);
            
            celda.setCellValue(doc.getDestinatario().getCiudad().getNombre());
            //Valor y estilo si Aplica en la Columna # 6
            
            celda = row.createCell((short)5);
            
            celda.setCellValue(doc.getValor_decl());
            
            
            
            //Valor y estilo si Aplica en la Columna # 7
            celda = row.createCell((short)6);
            
            celda.setCellValue(doc.getFlete_kg_vol());
            
            
            //Valor y estilo si Aplica en la Columna # 8
            celda = row.createCell((short)7);
            
            celda.setCellValue(doc.getSeguro());
            //Valor y estilo si Aplica en la Columna # 9
            celda = row.createCell((short)8);
            
            celda.setCellValue(doc.getIva());
            //Valor y estilo si Aplica en la Columna # 9
            celda = row.createCell((short)9);
            
            celda.setCellValue(doc.getTotal_fletes());
            
            
            fila++;
        }
        
        
    }
    
    // </editor-fold>
}
