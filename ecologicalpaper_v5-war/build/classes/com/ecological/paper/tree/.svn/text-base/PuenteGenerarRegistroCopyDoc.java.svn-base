/*
 * PuenteGenerarRegistroCopyDoc.java
 *
 * Created on September 12, 2007, 7:11 AM
 */
package com.ecological.paper.tree;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.Hibernate;

/**
 *
 * @author simon
 * @version
 */
 public  class PuenteGenerarRegistroCopyDoc extends HttpServlet {
    
    private ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
    private ServicioDelegado delegado =new ServicioDelegado();
    ResourceBundle confmessages = ContextSessionRequest.getResourceBundleStatic("com.util.resource.ecological_conf");
    ResourceBundle messages = ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
 
    private List<Configuracion>   configuraciones = new ArrayList<Configuracion>();
    private boolean swMoverPadreEnHijo;
    private boolean swFlowParalelo=false;
    
    
    protected   void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        //AQUI SE MUEVE O SE GENERA REGISTROS
        
        Tree treeNodoActual = (Tree) session.getAttribute("treeNodoActual");
        //en caso que sea un registro a generar
        Doc_detalle doc_registro = (Doc_detalle) session.getAttribute("swPage");
        
        //mover nbodo cualquiera
        Tree moverNodo = (Tree) session.getAttribute("moverNodo");
        //__________________________________________________________________
        //inicializamos todas las sessiones,dejamos solo las basicas
        contextSessionRequest.clearSession(session, confmessages.getString("usuarioSession"));
        
        //__________________________________________________________________
        String pagIr = "";
        
        Usuario usu = null;
        if (session != null) {
            usu = (Usuario) session.getAttribute("user_logueado");
        }
        
        if (usu != null) {
        	//buscamos si el nodo a cambiar es un hijo, si es asi, no se hace nada
        	  List<Tree> nodosHijosLst = new ArrayList();
        	  nodosHijosLst= contextSessionRequest.encontarTodosLosTreeHijos(nodosHijosLst, moverNodo);
       	  swMoverPadreEnHijo=false;
              if (nodosHijosLst != null && !nodosHijosLst.isEmpty()) {
            	  for(Tree t:nodosHijosLst ){
            		  if((t!=null && treeNodoActual!=null)&& (t.getNodo()-treeNodoActual.getNodo()==0)){
            			  swMoverPadreEnHijo=true;  
            			  break;
            		  } 
            		  
            	  }
              } 
        	 
        	 
            //buscamos que nodo es ...
            if (treeNodoActual != null && usu != null) {
                if (doc_registro != null) {
                	Doc_detalle d=	contextSessionRequest.grabarDocumento(doc_registro, treeNodoActual, usu,swFlowParalelo,"");
                	
                	
                    //AQUI LE DAMOS LA SEGURIDAD, CON ESTA SESSION Y EN CLIENTETREE
                    session.setAttribute("give_permiso_A_registro", treeNodoActual);
                   
					 
                    //aqui se navega en el menu
                    pagIr = "/aplicacion.jsf";
                } else if (moverNodo != null) {
                    //no c mueve al mismo sitio
                    if (!swMoverPadreEnHijo && (moverNodo.getNodo() - treeNodoActual.getNodo()) != 0) {
                        //moverNodo.setNodopadre(treeNodoActual.getNodo());
                        session.setAttribute("moverNodo", moverNodo);
                        //delegado.edit(moverNodo);
                        //aqui se navega en el menu
                        pagIr = "/razonDeCambio.jsf";
                    } else {
                        //no se hizo ningun cambio
                        pagIr = "/aplicacion.jsf";
                    }
                }
            } else {
                
                request.setAttribute("usuario_nologueado", messages.getString("error_idrootPuente"));
                pagIr = "/errorPage.jsf";
            }
            
            RequestDispatcher reqdisp = request.getRequestDispatcher(pagIr);
            reqdisp.forward(request, response);
        } else {
            //
            request.setAttribute("usuario_nologueado", messages.getString("usuario_nologueado"));
            RequestDispatcher reqdisp = request.getRequestDispatcher("/errorPage.jsf");
            reqdisp.forward(request, response);
        }
    }
    
    public void darpermiso(Tree treeNodoActual) {
        boolean swEliminar = false;
        //cargamos la operacion de la base de datos y le damos seguridad full
        //List<Operaciones> operacionViewInBD = delegado.findAll_operacionesArbol(treeNodoActual);
       // for (Operaciones op : operacionViewInBD) {
            //       clienteSeguridad.heredarOperacionTreeUsuarioPermiso(treeNodoActual,
            //      usu,op ,swEliminar);
        //}
        
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
