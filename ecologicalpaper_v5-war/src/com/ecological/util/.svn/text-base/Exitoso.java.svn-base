/*
 * Exitoso.java
 *
 * Created on September 30, 2007, 6:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.util;

import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class Exitoso extends ContextSessionRequest {
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private HttpSession session = super.getSession();
	private String archNoCargados;
	private boolean swArchNoCargados;
	private String pagIr;
	private Usuario user_logueado;
	private boolean swConsecutivoRemplazado;
	private boolean swMensaje;
	private boolean swUrl;
	private String mensaje;
	private String num1;
	private String num2;

	/** Creates a new instance of Exitoso */
	public Exitoso() {
		session = super.getSession();
		// verificamos si el consecutivo que se introdujho, ya existia y se
		// remplazo
		if (session.getAttribute("conecutivo1") != null
				&& session.getAttribute("conecutivo2") != null) {

			num1 = (String) session.getAttribute("conecutivo1");
			num2 = (String) session.getAttribute("conecutivo2");
			if (!num1.equalsIgnoreCase(num2)) {
				swConsecutivoRemplazado = true;
			}
		}
		session.removeAttribute("conecutivo1");
		session.removeAttribute("conecutivo2");

		swUrl=session.getAttribute("swUrl") != null;
		session.removeAttribute("swUrl");
		
		if (session.getAttribute("mensaje") != null
				) {

			mensaje = (String) session.getAttribute("mensaje");
			swMensaje=true;
		}
		session.removeAttribute("mensaje");
		
		

		// ____________________________________________

		pagIr = session.getAttribute("pagIr") != null ? (String) session
				.getAttribute("pagIr") : "";
		if (super.isEmptyOrNull(pagIr)) {
			pagIr = Utilidades.getListarAplicacion();
		}

	}

	public String regresarExitoso() {
		try {
			boolean existe=session.getAttribute("noClearSession")!=null;
			//SI NO EXISTE 
			if (!existe){
			 
				super.clearSession(session,
						confmessages.getString("usuarioSession"));	
			}else{
				String noBorrar=(String)session.getAttribute("noClearSession");
				if (!isEmptyOrNull(noBorrar)){
					super.clearSession(session,
							confmessages.getString("usuarioSession")+","+noBorrar);
				}
				
				System.out.println("NO LIMPIAMOS SESSION----------------");
			}
			session.removeAttribute("noClearSession");
		} catch (Exception e) {
			redirect("Exitoso", "regresarExitoso", e);
		}
		// colocamos la nueva seguridad en session
		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos
		session.setAttribute("swbusqueda", true);
		// se hace nueva busqueda para los documentoscon nueva tree de la
		// carpeta getMostrarListDocumentos

		return pagIr;
	}

	public String getArchNoCargados() {
		return archNoCargados;
	}

	public void setArchNoCargados(String archNoCargados) {
		this.archNoCargados = archNoCargados;
	}

	public boolean isSwArchNoCargados() {
		// _____________________________________________________________________________
		// vengo de cargar archivos y solo doy informacion en exitoso y me salgo
		// de aqui
		swArchNoCargados = session.getAttribute("archNoCargados") != null;
		List lista = (List) session.getAttribute("archNoCargados");
		StringBuffer acumSt = new StringBuffer("");
		if (swArchNoCargados) {
			int k = lista.size();
			for (int i = 0; i < k; i++) {
				String arch = (String) lista.get(i);
				acumSt.append(arch);
			}
		}
		archNoCargados = acumSt.toString();
		// _____________________________________________________________________________
		return swArchNoCargados;
	}

	public void setSwArchNoCargados(boolean swArchNoCargados) {
		this.swArchNoCargados = swArchNoCargados;
	}

	public String getPagIr() {
		return pagIr;
	}

	public void setPagIr(String pagIr) {
		this.pagIr = pagIr;
	}

	public boolean isSwConsecutivoRemplazado() {
		return swConsecutivoRemplazado;
	}

	public void setSwConsecutivoRemplazado(boolean swConsecutivoRemplazado) {
		this.swConsecutivoRemplazado = swConsecutivoRemplazado;
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	public boolean isSwMensaje() {
		return swMensaje;
	}

	public void setSwMensaje(boolean swMensaje) {
		this.swMensaje = swMensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isSwUrl() {
		return swUrl;
	}

	public void setSwUrl(boolean swUrl) {
		this.swUrl = swUrl;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getConfmessages() {
		return confmessages;
	}

	public void setConfmessages(ResourceBundle confmessages) {
		this.confmessages = confmessages;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}
}
