/*
 * PuenteCrearRaiz.java
 *
 * Created on September 11, 2007, 9:12 AM
 */

package com.ecological.paper.tree;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author simon
 * @version
 */
public class PuenteCrearRaiz extends HttpServlet {
    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    private ServicioDelegado delegado =new ServicioDelegado();
    ResourceBundle confmessages =ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages =ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        Tree treeNodoActual=null;
      
         
        
        
        String pagIr="";
        
        Usuario usu=null;
        if (session!=null){
            usu=(Usuario) session.getAttribute("user_logueado");
        }
        
     
        if (usu!=null){
            //buscamos que nodo es ...
            treeNodoActual = new Tree();
            treeNodoActual.setTiponodo(Utilidades.getNodoRaiz());
            session.setAttribute("treeNodoActual",treeNodoActual);
            //aqui se crea la raiz, si no hay usuarios, crea el usuario primero
            session.setAttribute("crear",true);
            pagIr="/crearRaiz.jsf";
            RequestDispatcher reqdisp = request.getRequestDispatcher(pagIr);
            reqdisp.forward(request,response);
        }else{
            //
            request.setAttribute("usuario_nologueado",messages.getString("usuario_nologueado"));
            RequestDispatcher reqdisp = request.getRequestDispatcher("/errorPage.jsf");
            reqdisp.forward(request,response);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    
    
    public void init() throws ServletException {
        
        
    }
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
    // </editor-fold>
}
