package com.ecological.filter;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionTimeoutFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		
		
		
		 if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
	            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	            
	        }
		 

	        try {
	         
	        	if (isInvalidSession((HttpServletRequest) request)){
	        		 HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	 	            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	 	           
	 	    		HttpServletRequest hr = (HttpServletRequest) request;
	 	    		String uri = hr.getRequestURI();
	 	    		String ctxPath = hr.getContextPath();
	 	    		((HttpServletResponse) response).sendRedirect(ctxPath + "/"
	 	    				);	
	        	}
	         
	        } catch (ViewExpiredException ve) {
	        	 
	            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

	    		HttpServletRequest hr = (HttpServletRequest) request;
	    		String uri = hr.getRequestURI();
	    		String ctxPath = hr.getContextPath();
	    		((HttpServletResponse) response).sendRedirect(ctxPath + "/"
	    				);	
	        } catch (Exception e) {
//	    		HttpServletRequest hr = (HttpServletRequest) request;
//	    		String uri = hr.getRequestURI();
//	    		String ctxPath = hr.getContextPath();
//	    		((HttpServletResponse) response).sendRedirect(ctxPath + "/"
//	    				+ "index.jsf");	
	        	return;
	        }
	        chain.doFilter(request, response);
		
	}
	
	
	 private boolean isInvalidSession(HttpServletRequest httpServletRequest) {

	        boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null) 
	                                 && !httpServletRequest.isRequestedSessionIdValid();
	        return sessionInValid;
	    }
 

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
 
}
