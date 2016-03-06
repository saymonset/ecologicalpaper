/*
 * PuentedeTask.java
 *
 * Created on September 12, 2007, 4:32 PM
 */

package com.ecological.paper.tree;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.login.Login;
import com.ecological.login.ProcesarLoginsConectados;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author simon
 * @version
 */
public class PuentedeTask extends HttpServlet {
    ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    private ServicioDelegado delegado =new ServicioDelegado();
    ResourceBundle confmessages =ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages =ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        session.removeAttribute("clienteTree");
        Tree treeNodoActual=null;
        String idroot =request.getParameter("idroot");
        //String publicados = request.getParameter("publicados");
        String parametro = request.getParameter("parametro");
        
        boolean swIdrootVacio=false;
        
        
        //inicializamos todas las sessiones,dejamos solo las basicas
        contextSessionRequest.clearSession(session,confmessages.getString("usuarioSession"));
        
        
        String pagIr="";
        Usuario usu=null;
        if (session!=null){
            usu=(Usuario) session.getAttribute("user_logueado");
        }
        
        if (usu!=null){
            //buscamos que nodo es ...
            if (idroot!=null && usu!=null){
                int idr= Integer.parseInt(idroot);
                
                switch (idr) {
                    case 1:
                    	
                    	pagIr="/menu.jsf"; 
                    	break;
                    case 2:
                    	System.out.println("--salimos----------------");
                    	 
                    	contextSessionRequest.clearSession(session, "");
                    	//pagIr="/cerrarAplicacion.jsf"; 
                    	pagIr="/index.jsf";
                    	
                    	break;
                    case 3:
                    	if ("publico".equalsIgnoreCase(parametro)){
                         	pagIr="/listarDocumentoSearchPublicados.jsf";                 		
                    	}else {
                         	pagIr="/listarDocumentoSearch.jsf";
                    	}
                    	
   
                    //solobuscar
                    session.setAttribute("parametro",parametro);
                    break;
                    //publico
                    case 4: pagIr="/listarDocumentoSearchPublicados.jsf";
                    session.setAttribute("parametro",parametro);
                    break;
                    
                    default: pagIr="";break;
                }
                
            }else{
                swIdrootVacio=true;
                request.setAttribute("usuario_nologueado",messages.getString("error_idrootPuente"));
                pagIr="/errorPage.jsf";
            }
            
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
    
	public void getSalir(Usuario user_logueado ) {
		
		ProcesarLoginsConectados procesarLoginsConectados = new ProcesarLoginsConectados();
		Map application = (Map) contextSessionRequest
				.getApplicationMapValue(confmessages
						.getString("variable_global"));
		procesarLoginsConectados.deleteUsuarioById(
				user_logueado.getId(), application,
				confmessages.getString("usuariosApplicaction"));
		
		 

		
	}
    
}
