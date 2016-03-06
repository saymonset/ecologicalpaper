/*
 * Puente.java
 *
 * Created on June 25, 2007, 7:39 PM
 */

package com.ecological.paper.tree;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.tree.ClienteTree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.*;
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
public class Puente extends HttpServlet {
	ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
	private ServicioDelegado delegado = new ServicioDelegado();
	ResourceBundle confmessages = ContextSessionRequest
			.getResourceBundleStatic("com.util.resource.ecological_conf");
	ResourceBundle messages = ContextSessionRequest
			.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);
		   session.removeAttribute("clienteTree");
		Tree treeNodoActual = null;
		String idroot = request.getParameter("idroot");
		String publicados = request.getParameter("publicados");
		String idDetalle = request.getParameter("idDetalle");
		boolean swIdrootVacio = false;

		// auxiliar de pegar para realizar copias
		Doc_detalle doc_registro = null;
		if (session.getAttribute("swPage") != null) {
			doc_registro = (Doc_detalle) session.getAttribute("swPage");
		}
		Tree moverNodo = null;
		if (session.getAttribute("moverNodo") != null) {
			moverNodo = (Tree) session.getAttribute("moverNodo");
		}

		// evitara que me borre de la session la session query
		if (!"1".equalsIgnoreCase(publicados)) {
			// inicializamos todas las sessiones,dejamos solo las basicas
			contextSessionRequest.clearSession(session,
					confmessages.getString("usuarioSession"));
		}

		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos
		session.setAttribute("swbusqueda", true);
		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos
		
		if (doc_registro != null) {
			session.setAttribute("swPage", doc_registro);
		}
		if (moverNodo != null) {
			session.setAttribute("moverNodo", moverNodo);
		}

		String pagIr = "";
		Usuario usu = null;
		if (session != null) {
			usu = (Usuario) session.getAttribute("user_logueado");
		}

		if (usu != null) {
			// buscamos que nodo es ...
			if (idroot != null && usu != null) {
				long idrootl = Long.parseLong(idroot);
				// idroot=raiz, si la pulsamos el link de craerarbol.jsf
				if (Utilidades.getNodoRaiz() == idrootl) {// si es nodo raiz,
															// implica que
															// crearemos una
															// nueva raiz
					treeNodoActual = new Tree();
					treeNodoActual.setTiponodo(Utilidades.getNodoRaiz());
					session.setAttribute("treeNodoActual", treeNodoActual);
					// aqui se crea la raiz, si no hay usuarios, crea el usuario
					// primero
					session.setAttribute("crear", true);
					pagIr = "/crearTree.jsf";
				} else {
					treeNodoActual = delegado.obtenerNodo(idrootl);

					session.setAttribute("treeNodoActual", treeNodoActual);
					if ("1".equalsIgnoreCase(publicados)) {

						Doc_detalle doc_detalle = new Doc_detalle();
						if (!contextSessionRequest.isEmptyOrNull(idDetalle)) {
							doc_detalle.setCodigo(Long.parseLong(idDetalle));
							doc_detalle = delegado.findDetalle(doc_detalle);
							session.setAttribute("doc_detalle", doc_detalle);
						}

						session.setAttribute("parametro", "publico");
						pagIr = "/listarDocumentos.jsf";
					} else {

						// aqui se navega en el me145145u
						pagIr = "/aplicacion.jsf";
					}

				}

			} else {
				swIdrootVacio = true;
				request.setAttribute("usuario_nologueado",
						messages.getString("error_idrootPuente"));
				pagIr = "/errorPage.jsf";
			}

			 
			RequestDispatcher reqdisp = request.getRequestDispatcher(pagIr);
			reqdisp.forward(request, response);
		} else {
			//
			request.setAttribute("usuario_nologueado",
					messages.getString("usuario_nologueado"));
			RequestDispatcher reqdisp = request
					.getRequestDispatcher("/errorPage.jsf");
			reqdisp.forward(request, response);
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

	public void init() throws ServletException {

	}

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
}
