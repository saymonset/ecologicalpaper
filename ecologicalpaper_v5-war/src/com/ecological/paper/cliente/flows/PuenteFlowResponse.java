/*
 * PuenteFlowResponse.java
 *
 * Created on August 7, 2007, 6:38 PM
 */

package com.ecological.paper.cliente.flows;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * @author simon
 * @version
 */
public class PuenteFlowResponse extends HttpServlet {
	ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
	private ServicioDelegado delegado = new ServicioDelegado();
	ResourceBundle confmessages = ContextSessionRequest
			.getResourceBundleStatic("com.util.resource.ecological_conf");
	ResourceBundle messages = ContextSessionRequest
			.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
	private Doc_maestro doc_maestro;
	private Doc_detalle doc_detalle;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);
		Tree treeNodoActual = null;

		 

		String codigoFlowDetalle = request.getParameter("codigoFlowDetalle");
		String flowPartSifirmo = request.getParameter("flowPartSifirmo");
		String flowpuente= request.getParameter("flowpuente");
		
	 
		boolean swIdrootVacio = false;
		// inicializamos todas las sessiones,dejamos solo las basicas
		contextSessionRequest.clearSession(session, confmessages
				.getString("usuarioSession"));

		if (doc_detalle == null) {
			doc_detalle = new Doc_detalle();
		}
		if (codigoFlowDetalle != null) {

			long id = Long.parseLong(codigoFlowDetalle.toString());
			// buscamos el role para editar
			if (id >= 0) {
				doc_detalle.setCodigo(new Long(id));
				doc_detalle = delegado.findDetalle(doc_detalle);
			}

			if (doc_detalle != null) {
				if (flowPartSifirmo != null) {
					session.setAttribute("flowPartSifirmo", flowPartSifirmo);
				}
				
				if (flowpuente != null) {
					session.setAttribute("flowpuente", flowpuente);
				}

				doc_maestro = doc_detalle.getDoc_maestro();
				if (session == null) {
					RequestDispatcher reqdisp = request
							.getRequestDispatcher("/errorPage.jsf");
					reqdisp.forward(request, response);

				} else {
					session.setAttribute("doc_detalle", doc_detalle);
					session.setAttribute("doc_maestro", doc_maestro);
					// comprobamos que la bd no este vacia
					RequestDispatcher reqdisp = request
							.getRequestDispatcher("/flowsResponse.jsf");
					reqdisp.forward(request, response);
				}

			}

		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

	// </editor-fold>

	public Doc_maestro getDoc_maestro() {
		return doc_maestro;
	}

	public void setDoc_maestro(Doc_maestro doc_maestro) {
		this.doc_maestro = doc_maestro;
	}

	public Doc_detalle getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}
}
