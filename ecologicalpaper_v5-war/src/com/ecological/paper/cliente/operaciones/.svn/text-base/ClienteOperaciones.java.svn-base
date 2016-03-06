/*
 * ClienteOperaciones.java
 *
 * Created on July 18, 2007, 10:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.operaciones;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author simon
 */
public class ClienteOperaciones extends ContextSessionRequest {
	private ServicioDelegado delegado = new ServicioDelegado();
	private HttpSession session = super.getSession();
	private Operaciones operaciones = null;

	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");

	/** Creates a new instance of ClienteOperaciones */
	public ClienteOperaciones() {
		session = super.getSession();
		operaciones = new Operaciones();
	}

	public void actionListener(ActionEvent event) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado {
		try {
			delegado.create(operaciones);
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			// e.printStackTrace();
		} catch (RoleMultiples e2) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			// e2.printStackTrace();
		} catch (RoleNoEncontrado e3) {
			// FacesContext.getCurrentInstance().addMessage(null,new
			// FacesMessage(messages.getString("error_NoEncontrado") ));
			// e3.printStackTrace();
		}
	}

	public String create() {
		return "";
	}

	public void edit() throws EcologicaExcepciones {
		try {
			delegado.edit(operaciones);
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			// e.printStackTrace();
		}
	}

	public void destroy() throws EcologicaExcepciones {
		try {
			delegado.destroy(operaciones);
		} catch (EcologicaExcepciones e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			e.printStackTrace();
		}
	}

	public void findRole() throws RoleMultiples, RoleNoEncontrado,
			EcologicaExcepciones {
		try {
			operaciones = delegado.getOperacion(operaciones);
		} catch (RoleMultiples e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			e.printStackTrace();
		} catch (RoleNoEncontrado e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			e.printStackTrace();
		}
	}

	// busca p[or nombre
	public void getUnicoRole() throws RoleMultiples, RoleNoEncontrado {

		try {
			operaciones = delegado.getOperacion(operaciones);
		} catch (EcologicaExcepciones ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error")));
			ex.printStackTrace();
		} catch (RoleMultiples ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_Dupl")));
			ex.printStackTrace();
		} catch (RoleNoEncontrado ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("error_NoEncontrado")));
			ex.printStackTrace();
		}

	}

	public void llenarOperacionesVisibles(List seleccionados,
			List noSeleccionados, Seguridad_Role_Lineal seguridad_Role_Lineal) {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		boolean swSuperUsuario = false;
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		if (seguridad_Role_Lineal != null) {
			List<Seguridad_Role_Lineal> seguridad_Role_LinealS = delegado
					.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);
			List<Operaciones> enBd = new ArrayList();
			enBd = delegado.findAll_operaciones();
			List<Operaciones> enBdAux = new ArrayList();
			enBdAux.addAll(enBd);

			if (seguridad_Role_LinealS != null
					&& !seguridad_Role_LinealS.isEmpty()) {
				seguridad_Role_Lineal = seguridad_Role_LinealS.get(0);

				SelectItem item = null;
				for (Operaciones operaciones : enBdAux) {
					if (delegado.existeOperacionSeguridadLineal(operaciones,
							seguridad_Role_Lineal)) {
						enBd.remove(operaciones);
						if (Utilidades.getPertence_Arbol()
								- operaciones.getPertenece_Arbol() == 0) {
							item = new SelectItem(operaciones, "<Tree>"
									+ messages.getString(operaciones
											.getOperacion()));
						}
						if (Utilidades.getPertence_Menu()
								- operaciones.getPertenece_Arbol() == 0) {
							item = new SelectItem(operaciones, "<Menu>"
									+ messages.getString(operaciones
											.getOperacion()));

						}
						if (Utilidades.getPertence_Arbol_and_Menu()
								- operaciones.getPertenece_Arbol() == 0) {
							item = new SelectItem(operaciones, "<Tree>"
									+ messages.getString(operaciones
											.getOperacion()));
						}
						if (swSuperUsuario
								&& operaciones.getOperacion().equals(
										"toAddRaiz")) {
							seleccionados.add(item);
						} else if (!operaciones.getOperacion().equals(
								"toAddRaiz")) {
							seleccionados.add(item);
						}

					}

				}
			}
			// lo que queda en Bd lo sacamos
			SelectItem item = null;
			for (Operaciones operaciones : enBd) {
				// Operaciones operaciones = (Operaciones) items2.get(i);

				if (Utilidades.getPertence_Arbol()
						- operaciones.getPertenece_Arbol() == 0) {
					item = new SelectItem(operaciones, "<Tree>"
							+ messages.getString(operaciones.getOperacion()));
				}
				if (Utilidades.getPertence_Menu()
						- operaciones.getPertenece_Arbol() == 0) {
					item = new SelectItem(operaciones, "<Menu>"
							+ messages.getString(operaciones.getOperacion()));

				}
				if (Utilidades.getPertence_Arbol_and_Menu()
						- operaciones.getPertenece_Arbol() == 0) {
					item = new SelectItem(operaciones, "<Tree>"
							+ messages.getString(operaciones.getOperacion()));
				}
				noSeleccionados.add(item);
			}
		}
	}

	/*
	 * public void llenarOperaciones(List selectItem){
	 * 
	 * List items = findAll(); int size = items.size(); SelectItem item=null;
	 * for (int i=0; i<size; i++) { Operaciones operaciones = (Operaciones)
	 * items.get(i); item = new
	 * SelectItem(operaciones,operaciones.getOperacion()); selectItem.add(item);
	 * } }
	 */

	public Operaciones getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(Operaciones operaciones) {
		this.operaciones = operaciones;
	}

	public void llenarOperacionesVisiblesRichFaces(List seleccionados,
			List noSeleccionados, Seguridad_Role_Lineal seguridad_Role_Lineal) {
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		boolean swSuperUsuario = false;

		String operacion = "";

		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
		if (seguridad_Role_Lineal != null) {
			List<Seguridad_Role_Lineal> seguridad_Role_LinealS = delegado
					.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);
			List<Operaciones> enBd = new ArrayList();
			enBd = delegado.findAll_operaciones();
			List<Operaciones> enBdAux = new ArrayList();
			enBdAux.addAll(enBd);

			if (seguridad_Role_LinealS != null
					&& !seguridad_Role_LinealS.isEmpty()) {
				seguridad_Role_Lineal = seguridad_Role_LinealS.get(0);

				// SelectItem item = null;
				for (Operaciones operaciones : enBdAux) {
					operacion = "";
					
					//esta la rechazamos, la sacamos de los permisos
					if (operaciones.getOperacion().equalsIgnoreCase(Utilidades.getToVincDoc())){
						continue;
					}
					
					if (delegado.existeOperacionSeguridadLineal(operaciones,
							seguridad_Role_Lineal)) {
						enBd.remove(operaciones);
						if (Utilidades.getPertence_Arbol()
								- operaciones.getPertenece_Arbol() == 0) {

							// item = new SelectItem(operaciones, "<Tree>"
							// + messages.getString(operaciones
							// .getOperacion()));
							operacion = "(Tree)"
									+ messages.getString(operaciones
											.getOperacion());

						}
						if (Utilidades.getPertence_Menu()
								- operaciones.getPertenece_Arbol() == 0) {
							// item = new SelectItem(operaciones, "<Menu>"
							// + messages.getString(operaciones
							// .getOperacion()));

							operacion = "(Menu)"
									+ messages.getString(operaciones
											.getOperacion());

						}
						if (Utilidades.getPertence_Arbol_and_Menu()
								- operaciones.getPertenece_Arbol() == 0) {
							// item = new SelectItem(operaciones, "<Tree>"
							// + messages.getString(operaciones
							// .getOperacion()));

							operacion = "(Tree)"
									+ messages.getString(operaciones
											.getOperacion());

						}
						if (swSuperUsuario
								&& operaciones.getOperacion().equals(
										"toAddRaiz")) {
							operaciones.setOperacion(operacion);

							seleccionados.add(operaciones);
						} else if (!operaciones.getOperacion().equals(
								"toAddRaiz")) {
							operaciones.setOperacion(operacion);
							seleccionados.add(operaciones);
						}

					}

				}
			}
			// lo que queda en Bd lo sacamos
			// SelectItem item = null;
			for (Operaciones operaciones : enBd) {
				operacion = "";
						//esta la rechazamos, la sacamos de los permisos
				if (operaciones.getOperacion().equalsIgnoreCase(Utilidades.getToVincDoc())){
					continue;
				}
				// Operaciones operaciones = (Operaciones) items2.get(i);

				if (Utilidades.getPertence_Arbol()
						- operaciones.getPertenece_Arbol() == 0) {
					// item = new SelectItem(operaciones, "<Tree>"
					// + messages.getString(operaciones.getOperacion()));
					operacion = "(Tree)"
							+ messages.getString(operaciones.getOperacion());
				}
				if (Utilidades.getPertence_Menu()
						- operaciones.getPertenece_Arbol() == 0) {
					// item = new SelectItem(operaciones, "<Menu>"
					// + messages.getString(operaciones.getOperacion()));
					operacion = "(Menu)"
							+ messages.getString(operaciones.getOperacion());

				}
				if (Utilidades.getPertence_Arbol_and_Menu()
						- operaciones.getPertenece_Arbol() == 0) {
					operacion = "(Tree)"
							+ messages.getString(operaciones.getOperacion());
					// item = new SelectItem(operaciones, "<Tree>"
					// + messages.getString(operaciones.getOperacion()));
				}

				operaciones.setOperacion(operacion.trim());
				noSeleccionados.add(operaciones);
			}
		}
	}

}
