/*
 * ServletReporteEUJOVANS.java
 *
 * Created on 24 de febrero de 2008, 03:36 PM
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
import java.net.InetAddress;
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
public class ServletReporteEUJOVANS extends HttpServlet {
    
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
        
        
        String servidor =request.getRemoteAddr();
        String servidor2 =request.getRemoteHost();
        
        boolean   mismoServidor=servidor2.equalsIgnoreCase(confmessages.getString("serverip"));
        String nameFile ="";
        String nameSale="";
        if (mismoServidor){
            nameSale="formatoeujovans";
            nameFile = tmp +"/"+nameSale+".xls";
        }else{
            nameSale="formatoeujovans2";
            nameFile = tmp +"/"+nameSale+".xls";
        }
        
        response.setContentType("application/vnd.ms-excel");
        InputStream inputfile = new FileInputStream(nameFile);
        HSSFWorkbook workbook = new HSSFWorkbook(inputfile,true);
        HSSFSheet sheet = workbook.getSheetAt(0);
//        List<Factura> listamostar=null;
        Factura obj=null;
        if (session.getAttribute("facturaFlete")!=null){
            //listamostar =(List<Factura>)session.getAttribute("facturaFlete") ;
            obj =(Factura)session.getAttribute("facturaFlete") ;
        }
        
        HSSFCellStyle style1 = null;
        //  HSSFCellStyle style2 = null;
        HSSFCellStyle styleFecha = null;
        HSSFCellStyle styleFactura = null;
        HSSFCellStyle styleDestNombre = null;
        HSSFCellStyle styleDestCiudad = null;
        HSSFCellStyle styleDestDireccion = null;
        HSSFCellStyle styleDestTlf = null;
        HSSFCellStyle styleRemNombre = null;
        HSSFCellStyle styleRemCiudad = null;
        try {
            HSSFRow fil = sheet.getRow(5);
            if (fil!=null) {
                fil = sheet.getRow(9);
                HSSFCell cell = fil.getCell((short)1);
                if (cell!=null) {
                    style1 = cell.getCellStyle();
                }
                //total_fletes .. coumna sfilas de abajo
                fil = sheet.getRow(0);
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    styleFecha = cell.getCellStyle();
                }
                fil = sheet.getRow(2);
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    styleFactura = cell.getCellStyle();
                }
                
                fil = sheet.getRow(5);
                cell = fil.getCell((short)0);
                if (cell!=null) {
                    styleDestNombre = cell.getCellStyle();
                }
                fil = sheet.getRow(5);
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    styleDestCiudad = cell.getCellStyle();
                }
                fil = sheet.getRow(6);
                cell = fil.getCell((short)0);
                if (cell!=null) {
                    styleDestDireccion = cell.getCellStyle();
                }
                fil = sheet.getRow(6);
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    styleDestTlf = cell.getCellStyle();
                }
                fil = sheet.getRow(7);
                cell = fil.getCell((short)0);
                if (cell!=null) {
                    styleRemNombre = cell.getCellStyle();
                }
                fil = sheet.getRow(7);
                cell = fil.getCell((short)7);
                if (cell!=null) {
                    styleRemCiudad= cell.getCellStyle();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        int fila = 13;
        HSSFRow row;
        HSSFCell celda;
        HSSFCell celda2;
        //Estableciendo T�tulo del Reporte seg�n el Idioma del Usuario :D
        row = sheet.getRow(0);
        if (row!=null) {
            celda = row.getCell((short)7);
            
            row = sheet.createRow((short)0);
            celda = row.createCell((short)7);
            if (styleFecha!=null) {
                celda.setCellStyle(styleFecha);
            }
            celda.setCellValue(Utilidades.sdfShowWithoutHour.format(new Date()));
        }
        //T�tulos de la Tabla
        row = sheet.getRow(12);
        
        
        
        //    for(Factura obj:listamostar){
        //while (rs.next()) {
        
        
        
        row = sheet.createRow((short)2);
        celda = row.createCell((short)7);
        if (styleFactura!=null) {
            celda.setCellStyle(styleFactura);
        }
        celda.setCellValue(obj.getFactura());
        
        
        row = sheet.createRow((short)5);
        celda = row.createCell((short)0);
        if (styleDestNombre!=null) {
            celda.setCellStyle(styleDestNombre);
        }
        celda.setCellValue(obj.getDestinatario().getNombre());
        //Valor y estilo si Aplica en la Columna # 3
        row = sheet.createRow((short)5);
        celda = row.createCell((short)7);
        if (styleDestCiudad!=null) {
            celda.setCellStyle(styleDestCiudad);
        }
        celda.setCellValue(obj.getDestinatario().getCiudad().getNombre());
        
        row = sheet.createRow((short)6);
        celda = row.createCell((short)0);
        if (styleDestDireccion!=null) {
            celda.setCellStyle(styleDestDireccion);
        }
        celda.setCellValue(obj.getDestinatario().getDireccion());
        //Valor y estilo si Aplica en la Columna # 5
        
        row = sheet.createRow((short)6);
        celda = row.createCell((short)7);
        if (styleDestTlf!=null) {
            celda.setCellStyle(styleDestTlf);
        }
        celda.setCellValue(obj.getDestinatario().getTelefono());
        
        row = sheet.createRow((short)7);
        celda = row.createCell((short)0);
        if (styleRemNombre!=null) {
            celda.setCellStyle(styleRemNombre);
        }
        
        celda.setCellValue(obj.getRemitente().getNombre());
        
        row = sheet.createRow((short)7);
        celda = row.createCell((short)7);
        //if (styleRemCiudad!=null) {
        if (styleDestCiudad!=null) {
//            celda.setCellStyle(styleRemCiudad);
            celda.setCellStyle(styleDestCiudad);
        }
        
        celda.setCellValue(obj.getRemitente().getCiudad().getNombre());
        
        
        
        
        row = sheet.createRow((short)9);
        celda = row.createCell((short)1);
        if (style1!=null) {
            celda.setCellStyle(style1);
        }
        //messages.getString("cant_bultos")+":"+
        celda.setCellValue(obj.getCant_bultos());
        // celda.setCellValue(Math.round(obj.getCant_bultos() * 100) / 100 );
        //Math.round(1.194999999998 * 100) / 100 = 119 / 100 = 1.19
        
        row = sheet.createRow((short)10);
        celda = row.createCell((short)1);
        if (style1!=null) {
            celda.setCellStyle(style1);
        }
        //messages.getString("valor_decl")+":"
        celda.setCellValue(obj.getValor_decl());
        //celda.setCellValue(Math.round(obj.getValor_decl() * 100) / 100 );
        
      /*  row = sheet.createRow((short)8);
        celda = row.createCell((short)5);
        if (style1!=null) {
            celda.setCellStyle(style1);
        }
        celda.setCellValue(messages.getString("factura")+":"+obj.getFactura());*/
        
      /*  row = sheet.createRow((short)9);
        celda = row.createCell((short)0);
        if (style1!=null) {
            celda.setCellStyle(style1);
        }
        if (obj.isCredito()){
            celda.setCellValue(messages.getString("credito")+":"+messages.getString("si"));
        }else{
            celda.setCellValue(messages.getString("credito")+":"+messages.getString("no"));
        }*/
        
        //PAGADERO EN DESTINO
       /* row = sheet.createRow((short)9);
        celda = row.createCell((short)5);
        if (style1!=null) {
            celda.setCellStyle(style1);
        }
        if (obj.isCredito()){
            celda.setCellValue(messages.getString("pagadero_destino")+":"+messages.getString("no"));
        }else{
            celda.setCellValue(messages.getString("pagadero_destino")+":"+messages.getString("si"));
        }*/
        
        if (!obj.isCredito()){
            row = sheet.createRow((short)11);
            celda = row.createCell((short)1);
            if (style1!=null) {
                celda.setCellStyle(style1);
            }
            //messages.getString("flete_kg_vol")+":"+
            celda.setCellValue(obj.getFlete_kg_vol());
            // celda.setCellValue(Math.round(obj.getFlete_kg_vol() * 100) / 100 );
            
            row = sheet.createRow((short)12);
            celda = row.createCell((short)1);
            if (style1!=null) {
                celda.setCellStyle(style1);
            }
            //messages.getString("seguro")+":"+
            celda.setCellValue(obj.getSeguro());
            //  celda.setCellValue(Math.round(obj.getSeguro() * 100) / 100 );
            
            row = sheet.createRow((short)14);
            celda = row.createCell((short)1);
            if (style1!=null) {
                celda.setCellStyle(style1);
            }
            //messages.getString("total_fletes")+":"+
            celda.setCellValue(obj.getTotal_fletes());
            //  celda.setCellValue(Math.round(obj.getTotal_fletes() * 100) / 100 );
        }
        
        fila++;
        //  }
        //Estableciendo Estad�sticas
        //Borrador
        
        //Enviando el Archivo al Cliente
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename="+nameSale+".xls");
        response.setHeader("content-transfer-encoding", "binary");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
        
    }
    
    
    // </editor-fold>
}
