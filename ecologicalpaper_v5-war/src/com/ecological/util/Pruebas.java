/*
 * Pruebas.java
 *
 * Created on July 27, 2007, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.util;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author simon
 */
public class Pruebas extends HttpServlet {
    private String directorioTemporal;
    private ServicioDelegado delegado = new ServicioDelegado();
    private Doc_maestro maestro =null;
    private Doc_detalle detalle=null;
    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    ResourceBundle confmessages =ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages =ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
    
    
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
    /** Creates a new instance of Pruebas */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String doc_detalle_id = "2";
        
        detalle = new Doc_detalle();
        detalle.setCodigo(Long.parseLong(doc_detalle_id));
        detalle=delegado.findDetalle(detalle);
        maestro=detalle.getDoc_maestro();
        
        
        
        
        //String path = this.getServletContext().getRealPath("/")+"\\tmp";
        String path=System.getProperty("java.io.tmpdir");
        //UploadedFile upFile = null;
        try {
            saveFileInDisk(detalle.getData().getBinaryStream() ,path,detalle.getNameFile());
            File archivo = new File(path + "//" + detalle.getNameFile());
            
            Desktop desktop = Desktop.getDesktop();
            desktop.open(archivo);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public  void saveFileInDisk(InputStream imagenBuffer,String path,String nameFile) {
        try {
            String ruta = path + "\\" + nameFile;
            File fichero = new File(ruta);
            BufferedInputStream in = new BufferedInputStream(imagenBuffer);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fichero));
            byte[] bytes = new byte[8096];
            int len = 0;
            while ( (len = in.read( bytes )) > 0 ) {
                out.write( bytes, 0, len );
            }
            out.flush();
            out.close();
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
