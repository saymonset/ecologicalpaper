package com.ecological.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecological.login.Login;
import com.ecological.paper.flows.Flow;

public class IndexFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hr = (HttpServletRequest) request;
		
		 HttpServletRequest request1 = (HttpServletRequest) request;
	        HttpServletResponse response1 = (HttpServletResponse) response;
	        String url = request1.getServletPath();
	        
		
		String uri = hr.getRequestURI();
		String flow = hr.getParameter("flow") != null ? (String) hr
				.getParameter("flow") : "";
		String mail = hr.getParameter("mail") != null ? (String) hr
				.getParameter("mail") : "";
				 

				HttpSession session =  request1.getSession(true);
				//limpiuamos session
				try {
					Enumeration atributteInSession = session.getAttributeNames();
					while (atributteInSession.hasMoreElements()) {
						String param = (String) atributteInSession.nextElement();
							session.removeAttribute(param);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				
		if (flow != null && !"".equalsIgnoreCase(flow)) {
			if (mail != null && !"".equalsIgnoreCase(mail)) {
				Login login = new Login("No se Usa ");
				
				
				//CARGAMOS EL FLOW Y EL USER_LOGUEADO EN SESSION, POR FUERA DEL METODO TAMBIEN COLOCAMOS FLOWRESP EN SESSION
				Flow flowResp = login.login_action(mail, flow,session);
				String ctxPath = hr.getContextPath();
				session.setAttribute("flowResp", flowResp);
				
				((HttpServletResponse) response).sendRedirect(ctxPath + "/"
				+ "bienvenidousuarioByMail.jsf");

//				((HttpServletResponse) response).sendRedirect(ctxPath + "/"
//						+ "PuenteFlowResponse?codigoFlowDetalle="
//						+ flowResp.getDoc_detalle().getCodigo()
//						+ "&flowpuente=" + flowResp.getCodigo());
				chain.doFilter(request, response);

			}else {
				String ctxPath = hr.getContextPath();
				((HttpServletResponse) response).sendRedirect(ctxPath + "/"
						+ "bienvenidousuario.jsf");
				chain.doFilter(request, response);
			}

		} else {
			String ctxPath = hr.getContextPath();
			

		 
			((HttpServletResponse) response).sendRedirect(ctxPath + "/"
					+ "bienvenidousuario.jsf");
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
