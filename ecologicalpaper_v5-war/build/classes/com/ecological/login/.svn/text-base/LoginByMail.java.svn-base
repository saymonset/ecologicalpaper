package com.ecological.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.EncryptorMD5;

public class LoginByMail extends ContextSessionRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private Usuario user_logueado;
	private HttpSession session = super.getSession();
	private String password;
	private boolean loggedIn = false;
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	public LoginByMail() {
		session = super.getSession();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String loginSend() {
		String pagIr="";
		if (loggedIn){
			Flow flowResp = session.getAttribute("flowResp") != null ? (Flow) session
					.getAttribute("flowResp") : null;
			if (flowResp!=null){
				Doc_detalle doc_detBuscaFlow = new Doc_detalle();
				session.setAttribute("doc_detalle", flowResp.getDoc_detalle());
					session.setAttribute("flowHistorico", flowResp);
	
			}
			
			pagIr="flowsResponse";
			
		}
		return pagIr;

	}

	public void login(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				 
		try {
			password = EncryptorMD5.getMD5(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FacesMessage msg = null;

		if (password != null && password.equals(user_logueado.getPassword())) {
			loggedIn = true;

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (messages
					.getString("welcome")),
					username);
		} else {
			loggedIn = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, messages
					.getString("loginError"),
					messages
					.getString("invalidCredentials"));
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("loggedIn", loggedIn);
	}

	public String cancelar(){
		try {
			// inicializamos todas las sessiones
			if (session==null){
				session = super.getSession();
				super.clearSession(session, "");

			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "cancelar";
		// msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome",
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
