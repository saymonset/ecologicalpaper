/*
 * SessionListener.java
 *
 * Created on August 29, 2008, 6:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.listener.sessiones;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author simon
 */
public class SessionListener implements ServletContextListener,
		HttpSessionAttributeListener, HttpSessionListener {

	// ----------------------------------------------------- Instance Variables

	/**
	 * The servlet context with which we are associated.
	 */
	private ServletContext context = null;

	// --------------------------------------------------------- Public Methods

	/**
	 * Record the fact that a servlet context attribute was added.
	 * 
	 * @param event
	 *            The session attribute event
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {

	/*	log("attributeAdded('" + event.getSession().getId() + "', '"
				+ event.getName() + "', '" + event.getValue() + "')");*/
		/*
		 * System.out.println("sumando una session  ");
		 * System.out.println("event.getSession().getId()= "
		 * +event.getSession().getId());
		 * System.out.println("event.getName()= "+event.getName());
		 * System.out.println("event.getValue()= "+event.getValue());
		 */

	}

	/**
	 * Record the fact that a servlet context attribute was removed.
	 * 
	 * @param event
	 *            The session attribute event
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {

		/*log("attributeRemoved('" + event.getSession().getId() + "', '"
				+ event.getName() + "', '" + event.getValue() + "')");*/
		/*
		 * System.out.println("Removiendo una session  ");
		 * System.out.println("event.getSession().getId()= "
		 * +event.getSession().getId());
		 * System.out.println("event.getName()= "+event.getName());
		 * System.out.println("event.getValue()= "+event.getValue());
		 */

	}

	/**
	 * Record the fact that a servlet context attribute was replaced.
	 * 
	 * @param event
	 *            The session attribute event
	 */
	public void attributeReplaced(HttpSessionBindingEvent event) {

	/*	log("attributeReplaced('" + event.getSession().getId() + "', '"
				+ event.getName() + "', '" + event.getValue() + "')");*/

	}

	/**
	 * Record the fact that this web application has been destroyed.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	public void contextDestroyed(ServletContextEvent event) {

		//log("contextDestroyed()");
		this.context = null;
		// System.out.println("Destruido ");

	}

	/**
	 * Record the fact that this web application has been initialized.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	public void contextInitialized(ServletContextEvent event) {

		this.context = event.getServletContext();
	//	log("contextInitialized()");

	}

	/**
	 * Record the fact that a session has been created.
	 * 
	 * @param event
	 *            The session event
	 */
	public void sessionCreated(HttpSessionEvent event) {

		//log("sessionCreated('" + event.getSession().getId() + "')");
		/*
		 * System.out.println("Creado");
		 * System.out.println("event.getSession().getId()= "
		 * +event.getSession().getId());
		 */

	}

	/**
	 * Record the fact that a session has been destroyed.
	 * 
	 * @param event
	 *            The session event
	 */
	public void sessionDestroyed(HttpSessionEvent event) {

		//log("sessionDestroyed('" + event.getSession().getId() + "')");
		/*
		 * System.out.println("Destruido session");
		 * System.out.println("event.getSession().getId()= "
		 * +event.getSession().getId());
		 */

	}

	// -------------------------------------------------------- Private Methods

	/**
	 * Log a message to the servlet context application log.
	 * 
	 * @param message
	 *            Message to be logged
	 */
	private void log(String message) {

		//if (context != null)
		//	context.log("SessionListener: " + message);
	}

	/**
	 * Log a message and associated exception to the servlet context application
	 * log.
	 * 
	 * @param message
	 *            Message to be logged
	 * @param throwable
	 *            Exception to be logged
	 */
	private void log(String message, Throwable throwable) {

		/*if (context != null)
			context.log("SessionListener: " + message, throwable);
		else {
			throwable.printStackTrace(System.out);
		}*/

	}

}