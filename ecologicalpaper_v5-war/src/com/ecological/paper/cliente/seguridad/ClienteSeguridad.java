/*
 * ClienteSeguridad.java
 *
 * Created on July 21, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.seguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.ecological.datoscombo.DatosCombo;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.role.ClienteRole_OperacionesPopup;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.ecological.util.CustomizePageBean;
import com.util.Utilidades;

/**
 * 
 * @author simon
 */
public class ClienteSeguridad extends ContextSessionRequest {

	private ServicioDelegado delegado = new ServicioDelegado();
	private HttpSession session = super.getSession();
	private Tree tree;
	private HtmlInputText inputTextNombre;
	private HtmlInputText inputTextDescripcion;
	private Usuario usuario;
	private HtmlSelectOneMenu selectItemUsuario;
	private DatosCombo datosCombo = new DatosCombo();

	private String selectPermisologiaRolUser;
	ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	// __________________________________
	private boolean swPrincipalVisible;
	private boolean swUserVisible;
	private boolean swRoleVisible;
	private Object[] selectedUsuarios;
	private List visibleUsers;
	private List visibleRole;
	private Object[] selectedRoles;

	private CustomizePageBean customizePageBean;
	// ____________________________________________________________________________
	// cuando se pulsa en el rol, se muestran las operaciones em operacionInROl
	// y
	// los usuarios en usuariosInRol
	private Object[] selectedOperaciones;
	private Object[] selectedOperacionesUsers;
	private Object[] selectedUsuariosInRoles;
	private List operacionesInRol;
	private List usuariosInRol;
	private List operacionesInUsuario;
	private boolean swVerInfRol;
	private String rolSeleccionado;
	private String userSeleccionado;
	private Tree treePadreTipoHereda;

	// ____________________________________________________________________________

	public ClienteSeguridad(String noseUsa) {

	}

	/** Creates a new instance of ClienteSeguridad */
	public ClienteSeguridad() {
		try {
			session = super.getSession();
			tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public ClienteSeguridad(HttpSession session) {
		try {
			
			tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
					.getAttribute("treeNodoActual") : null;
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// _________________________________________________________________________________________________

	// ROLE_________________________ROLE_________________________________GRABA
	// OPERACIONES______________________

	/*
	 * public void grabarOperacionesInConfiguracion(Usuario user,Tree tree,Role
	 * role){
	 * 
	 * List<Operaciones> filtro=delegado.findAll_operacionesArbol(tree);
	 * 
	 * //las operaciones en roles List enBd =
	 * delegado.buscarTodosLasOperacionesRol(role); int h = enBd.size(); List
	 * operacionesInrol= new ArrayList(); for(int k =0;k<h;k++){ Operaciones
	 * operacion = (Operaciones)enBd.get(k); if (filtro.contains(operacion)){ if
	 * (Utilidades.getToView().equalsIgnoreCase(operacion.getOperacion())){
	 * //este es recursivo y le da vista de permisologia hasta la raiz
	 * darViewNodoHastaRaiz(tree,user,role); }
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * public void darViewNodoHastaRaiz(Tree newCargo,Usuario user,Role role){
	 * Tree nodoPermView = newCargo; Operaciones operacion= new Operaciones();
	 * operacion.setOperacion(Utilidades.getToView()); //cargamos la operacion
	 * de la base de datos List
	 * operacionViewInBD=delegado.findOperacionName(operacion); Operaciones
	 * opSoloView=null; opSoloView = (Operaciones)operacionViewInBD.get(0);
	 * grabarOperaciones( user, nodoPermView ,opSoloView,role); //ahora, nos
	 * vamos a dar permiso de manera descendente hasta //llegar la raiz, solo
	 * permisologia de view boolean swHayPadre=true; while(swHayPadre){
	 * 
	 * nodoPermView
	 * =llegarHastadAbuelosPadres(nodoPermView);///darSeguridadAbuelosPadres
	 * (nodoPermView); //datr permisologia de vista al nodo grabarOperaciones(
	 * user, nodoPermView ,opSoloView,role );
	 * swHayPadre=nodoPermView.getTiponodo()!=Utilidades.getNodoRaiz(); }
	 * 
	 * }
	 * 
	 * public void grabarOperaciones(Usuario user,Tree tree , Operaciones
	 * operacion,Role role){ Seguridad_User seguridadOperacionesUser = new
	 * Seguridad_User(); seguridadOperacionesUser.setRole(role);
	 * seguridadOperacionesUser.setOperaciones(operacion);
	 * seguridadOperacionesUser.setUsuario(user);
	 * seguridadOperacionesUser.setTree(tree); try {
	 * 
	 * if (delegado.findTreeOperUser(seguridadOperacionesUser)==null){
	 * seguridadOperacionesUser = new Seguridad_User();
	 * seguridadOperacionesUser.setOperaciones(operacion);
	 * seguridadOperacionesUser.setUsuario(user);
	 * seguridadOperacionesUser.setTree(tree);
	 * seguridadOperacionesUser.setRole(role);
	 * delegado.create(seguridadOperacionesUser); }
	 * 
	 * } catch (RoleNoEncontrado ex) { ex.printStackTrace(); } catch
	 * (EcologicaExcepciones ex) { ex.printStackTrace(); } catch (RoleMultiples
	 * ex) { ex.printStackTrace(); }
	 * 
	 * }
	 */

	// FIN
	// ROLE_________________________ROLE_________________________________GRABA
	// OPERACIONES______________________
	// OPERACIONES
	// INDIVIDUALES_________________________________________________________________________
	public void grabarOperaciones(Seguridad_User_Lineal seguridad_User_Lineal) {
		try {
			Seguridad_User_Lineal seg_user = new Seguridad_User_Lineal(
					seguridad_User_Lineal);

			// buscamos que la seguridad ya no este grabada
			List<Seguridad_User_Lineal> existe_segUser_tree = delegado
					.findAllSeguridad_User_Lineal(
							seguridad_User_Lineal.getTree(),
							seguridad_User_Lineal.getUsuario());
			if (existe_segUser_tree != null && existe_segUser_tree.isEmpty()) {

				delegado.create(seg_user);
			} else if (existe_segUser_tree == null) {
				delegado.create(seg_user);
			}else{
				//se deshabilita,m porque me altera la seguridad de las ramas de arriba o pdres
//				seg_user.setCodigo(existe_segUser_tree.get(0).getCodigo());
//				delegado.edit(seg_user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void grabarRole_Tree(Seguridad_Role_Lineal role_tree) {
		List<Seguridad_Role_Lineal> existe_role_tree = delegado
				.findSeguridad_Role_RoleAndTree(role_tree);
		if (existe_role_tree != null && existe_role_tree.isEmpty()) {
			delegado.create(role_tree);
			//givePermparaverToGroup(role_tree);
		} else if (existe_role_tree == null) {
			delegado.create(role_tree);
		//	givePermparaverToGroup(role_tree);
		}else{
			//se deshabilita,m porque me altera la seguridad de las ramas de arriba o pdres
//			role_tree.setCodigo(existe_role_tree.get(0).getCodigo());
//			delegado.edit(role_tree);
		}
	}

	public void grabarOperacionesInConfiguracion(
			Seguridad_User_Lineal seguridad_User_Lineal) {

		// grabamos todos los permisos en el nodo y suvimos con solo ver
		if (seguridad_User_Lineal.isToView()) {
			darViewNodoHastaRaiz(seguridad_User_Lineal);
		} else {
			grabarOperaciones(seguridad_User_Lineal);
		}

	}

	public void grabarOperacionesInConfiguracionSoloParaAbajo(
			Seguridad_User_Lineal seguridad_User_Lineal) {
		grabarOperaciones(seguridad_User_Lineal);
		// colocamos la nueva seguridad en session

	}

	//
	public void heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
			Seguridad_User_Lineal seguridad_User_Lineal, boolean swElimina)
			throws EcologicaExcepciones {

		if (swElimina) {
			delegado.destroy(seguridad_User_Lineal);
		} else {
			grabarOperacionesInConfiguracionSoloParaAbajo(seguridad_User_Lineal);
		}
		List<Tree> hijos = llegarHastaHijosTodos(seguridad_User_Lineal
				.getTree());
		for (Tree hijo : hijos) {
			seguridad_User_Lineal.setTree(hijo);
			// System.out.println("hijo="+hijo.getNombre());
			heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal, swElimina);
		}

	}

	public void heredarOperacionTreeUsuarioPermiso(
			Seguridad_User_Lineal seguridad_User_Lineal, boolean swElimina)
			throws EcologicaExcepciones {

		if (swElimina) {
			delegado.destroy(seguridad_User_Lineal);
		} else {
			grabarOperacionesInConfiguracion(seguridad_User_Lineal);
		}
		// ___________________________________________________________________
		// vareiable que controla que tipo de herencia poermite traer
		// documentos..
		if (treePadreTipoHereda == null) {
			treePadreTipoHereda = seguridad_User_Lineal.getTree();
		} else {

			seguridad_User_Lineal.getTree().setTiponodo(
					treePadreTipoHereda.getTiponodo());
		}
		// ___________________________________________________________________
		List<Tree> hijos = llegarHastaHijosTodos(seguridad_User_Lineal
				.getTree());

		for (Tree hijo : hijos) {

			seguridad_User_Lineal.setTree(hijo);
			// recursiva...se llama asi mismo
			heredarOperacionTreeUsuarioPermiso(seguridad_User_Lineal, swElimina);
		}

	}

	public void darViewNodoHastaRaiz(Seguridad_User_Lineal seguridad_User_Lineal) {

		seguridad_User_Lineal.setToView(true);
		grabarOperaciones(seguridad_User_Lineal);
		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view
		boolean swHayPadre = true;
		Tree nodoPermView;
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		while (swHayPadre) {
			seguridad_User_Lineal2.setToView(true);
			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			seguridad_User_Lineal2.setToView(true);

			List<Seguridad_User_Lineal> listseg_user = delegado
					.findAllSeguridad_User_Lineal(
							seguridad_User_Lineal2.getTree(),
							seguridad_User_Lineal2.getUsuario());
			if (listseg_user == null || listseg_user.isEmpty()) {
				// datr permisologia de vista al nodo
				grabarOperaciones(seguridad_User_Lineal2);
			} else {
				Seguridad_User_Lineal seguridad_User_Linealexiste = listseg_user
						.get(0);
				seguridad_User_Linealexiste.setToView(true);
				delegado.edit(seguridad_User_Linealexiste);
			}
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();

		}

	}

	// da la seguridad completa al nuevo nodo creado y a su usuario duenio y
	// de forma descendente le da permiso de solo vista a la raiz
	public void darSeguridadNodoSoloEnCrearNoDamosPublicarDocumento(
			Seguridad_User_Lineal seguridad_User_Lineal) {

		// el primer nbodo . le damos toda la permisologia
		Tree nodoPermView = seguridad_User_Lineal.getTree();
		List todasOperaciones = delegado
				.findAll_operacionesArbol(seguridad_User_Lineal.getTree());
		Iterator it = todasOperaciones.iterator();

		// LLENAMOS TODOS LASOPERACIONES EN BOOLEAN CON SEGURIDAD ROLE
		Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
		while (it.hasNext()) {
			Operaciones opeTodas = (Operaciones) it.next();
			if (!Utilidades.getToDoPublico().equalsIgnoreCase(
					opeTodas.getOperacion())) {
				delegado.llenarSeguridadLineal(opeTodas,
						seguridad_AuxUser_Lineal);
			}

		}
		// truco de convertirlo PARA GRABARLO
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal(
				seguridad_AuxUser_Lineal);
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		grabarOperaciones(seguridad_User_Lineal2);

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view

		seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setToView(true);
		boolean swHayPadre = true;
		while (swHayPadre) {

			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			// datr permisologia de vista al nodo
			grabarOperaciones(seguridad_User_Lineal2);
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();
		}
		/* VAMOS A HEREDAR PERMISOS DEL PADRE */
		heredaSeguridadNodo(seguridad_User_Lineal);
	}

	// da la seguridad completa al nuevo nodo creado y a su usuario duenio y
	// de forma descendente le da permiso de solo vista a la raiz
	public void darSeguridadNodo(Seguridad_User_Lineal seguridad_User_Lineal) {

		// el primer nbodo . le damos toda la permisologia
		Tree nodoPermView = seguridad_User_Lineal.getTree();
		// dependiendo el tipo de nodo, sacamos el tipo de operaciones
		List todasOperaciones = delegado
				.findAll_operacionesArbol(seguridad_User_Lineal.getTree());
		Iterator it = todasOperaciones.iterator();

		// LLENAMOS TODOS LASOPERACIONES EN BOOLEAN CON SEGURIDAD ROLE
		Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
		while (it.hasNext()) {
			Operaciones opeTodas = (Operaciones) it.next();
			delegado.llenarSeguridadLineal(opeTodas, seguridad_AuxUser_Lineal);
		}
		// truco de convertirlo PARA GRABARLO
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal(
				seguridad_AuxUser_Lineal);
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		grabarOperaciones(seguridad_User_Lineal2);

		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view
		seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setToView(true);
		boolean swHayPadre = true;
		while (swHayPadre) {
			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			// datr permisologia de vista al nodo
			grabarOperaciones(seguridad_User_Lineal2);
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();
		}
		/* VAMOS A HEREDAR PERMISOS DEL PADRE */
		heredaSeguridadNodo(seguridad_User_Lineal);

	}

	// FIN OPERACIONES
	// INDIVIDUALES_________________________________________________________________________

	public void heredaSeguridadNodo(Seguridad_User_Lineal seguridad_User_Lineal) {
		// ---------------------------------------------------------------------------------
		/* VAMOS A HEREDAR PERMISOS */
		if (seguridad_User_Lineal.isSwHereda()) {
			/*
			 * VAMOS A HEREDAR PERMISOS EN USUARIOS INDIVIDUALES DEL PADRE AL
			 * NUEVO NOODO 2008-06-8
			 */
			Tree padre = delegado.obtenerNodo(seguridad_User_Lineal.getTree()
					.getNodopadre());
			// nodoPermView =llegarHastadAbuelosPadres();
			if (padre != null) {
				List<Seguridad_User_Lineal> users_hereda = delegado
						.findAllSeguridad_User_Lineal(padre);
				for (Seguridad_User_Lineal user_hereda : users_hereda) {
					user_hereda.setTree(seguridad_User_Lineal.getTree());
					grabarOperaciones(user_hereda);
				}

				List<Seguridad_Role_Lineal> lista_role_tree = delegado
						.buscarTodosLosRolesTree(padre);
				for (Seguridad_Role_Lineal role_tree : lista_role_tree) {
					role_tree.setTree(seguridad_User_Lineal.getTree());
					Seguridad_Role_Lineal segur_Role_Lineal = new Seguridad_Role_Lineal();
					segur_Role_Lineal.setTree(role_tree.getTree());
					segur_Role_Lineal.setRole(role_tree.getRole());
					List<Seguridad_Role_Lineal> existe_role_tree = delegado
							.findSeguridad_Role_RoleAndTree(segur_Role_Lineal);
					if (existe_role_tree != null && existe_role_tree.isEmpty()) {
						Seguridad_Role_Lineal segur_Role_LinealGrabar = new Seguridad_Role_Lineal(
								role_tree);
						grabarRole_Tree(segur_Role_LinealGrabar);
					} else if (existe_role_tree == null) {
						Seguridad_Role_Lineal segur_Role_LinealGrabar = new Seguridad_Role_Lineal(
								role_tree);
						grabarRole_Tree(segur_Role_LinealGrabar);
					}
				}

			}

		}
	}

	// _________________________________________________________________________________________________

	/*
	 * public void ponerSeguridadInSession(Usuario user,Tree tree){ //si
	 * mandamos a crear o edita usuarioos, por supuesto //que les cambia la
	 * seguridad pero no me altera mi session de seguridada Usuario
	 * user_logueado =
	 * session.getAttribute("user_logueado")!=null?(Usuario)session
	 * .getAttribute("user_logueado"):new Usuario(); if (user_logueado!=null &&
	 * user!=null){ if (user_logueado.getId()-user.getId()==0){ //OBTENGO LA
	 * SEGURIDAD DEL SISTEMA QUE ME HAN OTORGADO
	 * //________________________________________ Map
	 * user_seguridad=(Map)super.getUser_seguridad();//
	 * //________________________________________ //ES NULL, HACEMOS UN
	 * RECORRIDO A LA BASE DE DATOS A BUSCAR LA SEGURIDAD if
	 * (user_seguridad==null){ user_seguridad= getMySeguridad(user); }
	 * //PREGIUNTAMOS NUEVAMENTE POR SI ACASO if (user_seguridad==null){
	 * user_seguridad=new HashMap(); }else{ if
	 * (user_seguridad.containsKey(tree)){ //eliminamos el tree viejo, e
	 * ingresamos el nuevo user_seguridad.remove(tree); }
	 * List<Seguridad_Role_Lineal>
	 * permisoUserInTree=delegado.buscarSeguridadIndividual(usuario,tree);
	 * 
	 * ClienteSeguridad c = new ClienteSeguridad();
	 * user_seguridad=(Map)super.getUser_seguridad();
	 * c.mySeguridadPropiaInRoles_and_Individual
	 * (permisoUserInTree,user_seguridad);
	 * super.setUser_seguridad(user_seguridad);
	 * 
	 * } super.setSession("user_seguridad", user_seguridad); } }
	 * 
	 * 
	 * }
	 */

	public void ponerSeguridadInSession(Usuario user, HttpSession session) {
		// si mandamos a crear o edita usuarioos, por supuesto
		// que les cambia la seguridad pero no me altera mi session de
		// seguridada
		Usuario user_logueado = null;
		try {
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : new Usuario();
		} catch (Exception e) {
			user_logueado = user;
		}

		if (user_logueado != null && user != null) {
			if (user_logueado.getId() - user.getId() == 0) {
				// OBTENGO LA SEGURIDAD DEL SISTEMA QUE ME HAN OTORGADO
				// ________________________________________
				Map user_seguridad = getMySeguridad(user);
				// ________________________________________

				if (user_seguridad == null) {
					user_seguridad = new HashMap();
				}

				System.out.println("user_seguridad=null?"
						+ (user_seguridad == null));
				session.setAttribute("user_seguridad", user_seguridad);
				session.setAttribute("idsession", session.getId());
			}
		}

	}
	

	public void ponerSeguridadInSession(Usuario user) {
		// si mandamos a crear o edita usuarioos, por supuesto
		// que les cambia la seguridad pero no me altera mi session de
		// seguridada
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : new Usuario();
		if (user_logueado != null && user != null) {

			if (user_logueado.getId() - user.getId() == 0) {

				// OBTENGO LA SEGURIDAD DEL SISTEMA QUE ME HAN OTORGADO
				// ________________________________________

				Map user_seguridad = getMySeguridad(user);
				// ________________________________________

				if (user_seguridad == null) {
					user_seguridad = new HashMap();
				}

				super.setSession("user_seguridad", user_seguridad);
			}
		}

	}

	private Map getMySeguridad(Usuario user_logueado) {
		Map seguridadIndividualPertenesco = new HashMap();

		long tiempoInicio = System.currentTimeMillis();
		// SOLO PARA MENU

		seguridadIndividualPertenesco=delegado.soloMenuOptimizado(user_logueado, seguridadIndividualPertenesco);
		
		
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("soloMenu El tiempo de demora es :"
				+ totalTiempo + " miliseg");
		//FIN SOLO PARA MENU
		


		// SOLO PARA TREE, ESTA SEGURIDAD ES PARA TODOS MENOS PARA DOCUMENTOS..
		// TANTO DE USUARIO_LINBEAL, COMO ROLES_LINEAL
		tiempoInicio = System.currentTimeMillis();
		
		//SEGURIDAD INDIVIDUAL Y DE ROLE
		seguridadIndividualPertenesco=delegado.buscarSeguridadIndividualOptimizado(user_logueado,seguridadIndividualPertenesco);
		
//		List<Seguridad_Role_Lineal> permisoUser = delegado
//				.buscarSeguridadIndividual(user_logueado);
		
		totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("seguridadIndividualPertenesco El tiempo de demora es :"
				+ totalTiempo + " miliseg");
		// FIN SOLO PARA TREE, ESTA SEGURIDAD ES PARA TODOS MENOS PARA DOCUMENTOS..
		// TANTO DE USUARIO_LINBEAL, COMO ROLES_LINEAL
		
		
		//TIEMPO SEGURIDAD TOTAL MENU Y TREE
		tiempoInicio = System.currentTimeMillis();
		
		
//		mySeguridadPropiaInRoles_and_Individual(permisoUser,
//				seguridadIndividualPertenesco);
		
		//FIN TIEMPO SEGURIDAD TOTAL MENU Y TREE
		totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("TOTAL seguridad El tiempo de demora es :"
				+ totalTiempo + " miliseg");
		System.out.println("-------------------");

		// System.out.println("role lista inicio-----------------------------");
		// for (Seguridad_Role_Lineal seg : permisoUser) {
		// if ((seg.getTree().getNodo() - 962) == 0) {
		// System.out.println("nodo=" + seg.getTree().getNodo());
		// System.out.println("role=" + seg.getRole().getCodigo());
		// System.out.println("Flow :=" + seg.isToDoFlow());
		// }
		// }
		// System.out.println("role lista fin-----------------");

		// System.out.println("role lista inicio--AJAP---------------------------");
		// Tree tree = null;
		// Iterator it = seguridadIndividualPertenesco.entrySet().iterator();
		// Seguridad segMismaParaLosDos = new Seguridad();
		// while (it.hasNext()) {
		// Map.Entry e = (Map.Entry) it.next();
		//
		// tree = (Tree) e.getKey();
		//
		// segMismaParaLosDos = (Seguridad) e.getValue();
		// System.out.println("nodo=" + tree.getNodo());
		// System.out.println(tree.getNombre() + " realiza flujo="
		// + segMismaParaLosDos.isToDoFlow());
		// if ((tree.getNodo() - 962) == 0) {
		// System.out.println("FASHION-------------------");
		// }
		// }
		// System.out.println("role lista inicio--AJAP--------FIN-------------------");
		/*
		 * Seguridad segMismaParaLosDos = new Seguridad(); // el arbol donde
		 * estoy ubicado Tree tree = null; Iterator it =
		 * seguridadIndividualPertenesco.entrySet().iterator();
		 * 
		 * while (it.hasNext()) { Map.Entry e = (Map.Entry) it.next();
		 * 
		 * tree = (Tree) e.getKey();
		 * 
		 * segMismaParaLosDos = (Seguridad) e.getValue();
		 * System.out.println(tree.getNombre() + " realiza flujo=" +
		 * segMismaParaLosDos.isToDoFlow()); System.out.println(tree.getNombre()
		 * + " segMismaParaLosDos.isToAddDocumentoSvn()=" +
		 * segMismaParaLosDos.isToAddDocumentoSvn());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListGruposWorkflow()=" +
		 * segMismaParaLosDos.isToListGruposWorkflow());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListarDiasFeriados()=" +
		 * segMismaParaLosDos.isToListarDiasFeriados());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListarDiasHabiles()=" +
		 * segMismaParaLosDos.isToListarDiasHabiles());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddDiaFeriado()=" +
		 * segMismaParaLosDos.isToAddDiaFeriado());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModDiasHabiles()=" +
		 * segMismaParaLosDos.isToModDiasHabiles());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModDiaFeriado()=" +
		 * segMismaParaLosDos.isToModDiaFeriado());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDelDiaFeriado()=" +
		 * segMismaParaLosDos.isToDelDiaFeriado());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isConfSeguridad()=" +
		 * segMismaParaLosDos.isConfSeguridad());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddArea()=" +
		 * segMismaParaLosDos.isToAddArea());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddCargo()=" +
		 * segMismaParaLosDos.isToAddCargo());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddCarpeta()=" +
		 * segMismaParaLosDos.isToAddCarpeta());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddDocumentos()=" +
		 * segMismaParaLosDos.isToAddDocumentos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddGrupos()=" +
		 * segMismaParaLosDos.isToAddGrupos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddLotesDeDocumentos()=" +
		 * segMismaParaLosDos.isToAddLotesDeDocumentos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddPrincipal()=" +
		 * segMismaParaLosDos.isToAddPrincipal());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddProceso()=" +
		 * segMismaParaLosDos.isToAddProceso());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddProfesiones()=" +
		 * segMismaParaLosDos.isToAddProfesiones());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddRaiz()=" +
		 * segMismaParaLosDos.isToAddRaiz());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddTipoDocumentos()=" +
		 * segMismaParaLosDos.isToAddTipoDocumentos());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToAddUsuario()=" +
		 * segMismaParaLosDos.isToAddUsuario());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDel()=" + segMismaParaLosDos.isToDel());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDelGrupos()=" +
		 * segMismaParaLosDos.isToDelGrupos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDelProfesiones()=" +
		 * segMismaParaLosDos.isToDelProfesiones());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDelTipoDocumentos()=" +
		 * segMismaParaLosDos.isToDelTipoDocumentos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDelUsuario()=" +
		 * segMismaParaLosDos.isToDelUsuario());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDoFlow()=" +
		 * segMismaParaLosDos.isToDoFlow());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDoPublico()=" +
		 * segMismaParaLosDos.isToDoPublico());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToDoRegistros()=" +
		 * segMismaParaLosDos.isToDoRegistros());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToGivePermUser()=" +
		 * segMismaParaLosDos.isToGivePermUser());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListGrupos()=" +
		 * segMismaParaLosDos.isToListGrupos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListProfesiones()=" +
		 * segMismaParaLosDos.isToListProfesiones());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListTipoDocumentos()=" +
		 * segMismaParaLosDos.isToListTipoDocumentos());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToListUsuarios()=" +
		 * segMismaParaLosDos.isToListUsuarios());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToMod()=" + segMismaParaLosDos.isToMod());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModGrupos()=" +
		 * segMismaParaLosDos.isToModGrupos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModProfesiones()=" +
		 * segMismaParaLosDos.isToModProfesiones());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModTipoDocumentos()=" +
		 * segMismaParaLosDos.isToModTipoDocumentos());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToModUsuario()=" +
		 * segMismaParaLosDos.isToModUsuario());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToMove()=" + segMismaParaLosDos.isToMove());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToSaveDataBasic()=" +
		 * segMismaParaLosDos.isToSaveDataBasic());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToView()=" + segMismaParaLosDos.isToView());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToVincDoc()=" +
		 * segMismaParaLosDos.isToVincDoc());
		 * 
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.getTree().getNodo()=" +
		 * segMismaParaLosDos.getNodo());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToActivePermGroup()=" +
		 * segMismaParaLosDos.isToActivePermGroup());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToHistFlow()=" +
		 * segMismaParaLosDos.isToHistFlow());
		 * 
		 * System.out.println(tree.getNombre() +
		 * " segMismaParaLosDos.isToHistDoc()=" +
		 * segMismaParaLosDos.isToHistDoc());
		 * 
		 * 
		 * }
		 */

		// System.out.println(" salimops...de procesar");
		return seguridadIndividualPertenesco;

	}

	// Seguridad_Role_Lineal
	// Seguridad_User_Lineal(
	public void mySeguridadPropiaInRoles_and_Individual(
			List<Seguridad_Role_Lineal> permisoUser,
			Map seguridadIndividualPertenesco) {
		Seguridad seg=null;
		// el arbol donde estoy ubicado
		Tree tree = null;
		for (Seguridad_Role_Lineal seg2 : permisoUser) {

			// hacemos la conversion...
			 seg = new Seguridad(seg2);
			

			tree = delegado.obtenerNodo(seg.getNodo());

			// s en este arbol no esta la segurida, la coloco como venga
			if (seguridadIndividualPertenesco != null
					&& !seguridadIndividualPertenesco.containsKey(tree)) {
				seguridadIndividualPertenesco.put(tree, seg);

			} else {
				// repaso la segurida para ver si cambio y la actualizo en el
				// arbol
				if (seguridadIndividualPertenesco == null) {
					Usuario user_logueado = session
							.getAttribute("user_logueado") != null ? (Usuario) session
							.getAttribute("user_logueado") : new Usuario();
					seguridadIndividualPertenesco = getMySeguridad(user_logueado);
				}
				Seguridad segNodo = (Seguridad) seguridadIndividualPertenesco
						.get(tree);

				/***************************************************************/
				/***************** EMPEZAMOS DE AQUI CON LA SEGURIDAD **********************************************/
				/***************************************************************/

				if (!segNodo.isToViewComentPublic()) {
					segNodo.setToViewComentPublic(seg.isToViewComentPublic());
				}

				if (!segNodo.isToDownload()) {
					segNodo.setToDownload(seg.isToDownload());
				}

				if (!segNodo.isToDoFlowRevision()) {
					segNodo.setToDoFlowRevision(seg.isToDoFlowRevision());
				}

				if (!segNodo.isToListarArea()) {
					segNodo.setToListarArea(seg.isToListarArea());
				}

				// si todavia es falso, coloco la seguridad del tree haber
				// si cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isConfSeguridadGlobal()) {
					segNodo.setConfSeguridadGlobal(seg.isConfSeguridadGlobal());
				}
				// _______________________________________________
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToImprimirAdministrar()) {

					segNodo.setToImprimirAutorizacion(seg
							.isToImprimirAdministrar());
				}

				// _______________________________________________
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToSolicitudImpresion()) {

					segNodo.setToSolicitudImpresion(seg
							.isToSolicitudImpresion());
				}

				if (!segNodo.isToSolicitudImpresion0()) {

					segNodo.setToSolicitudImpresion0(seg
							.isToSolicitudImpresion0());
				}

				// _______________________________________________
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.istoPlantillaInDocFlowParalelo()) {

					segNodo.settoPlantillaInDocFlowParalelo(seg
							.istoPlantillaInDocFlowParalelo());
				}

				if (!segNodo.isToView()) {
					segNodo.setToView(seg.isToView());
				}

				// RAICES, este va para la seguridad -1 que es del menu
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddRaiz()) {
					segNodo.setToAddRaiz(seg.isToAddRaiz());

				}

				// CONTROL DE TIEMPO

				if (!segNodo.isToListarDiasFeriados()) {

					segNodo.setToListarDiasFeriados(seg
							.isToListarDiasFeriados());
				}
				if (!segNodo.isToListarDiasHabiles()) {

					segNodo.setToListarDiasHabiles(seg.isToListarDiasHabiles());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddDiaFeriado()) {
					segNodo.setToAddDiaFeriado(seg.isToAddDiaFeriado());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModDiasHabiles()) {
					segNodo.setToModDiasHabiles(seg.isToModDiasHabiles());
				}
				if (!segNodo.isToModDiaFeriado()) {
					segNodo.setToModDiaFeriado(seg.isToModDiaFeriado());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelDiaFeriado()) {
					segNodo.setToDelDiaFeriado(seg.isToDelDiaFeriado());
				}

				// FINB CONTROL DE TIEMPO

				// _______________________________________________
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModFlow()) {

					segNodo.setToModFlow(seg.isToModFlow());
				}

				// _______________________________________________
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToListUsuarios()) {

					segNodo.setToListUsuarios(seg.isToListUsuarios());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddUsuario()) {
					segNodo.setToAddUsuario(seg.isToAddUsuario());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModUsuario()) {
					segNodo.setToModUsuario(seg.isToModUsuario());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelUsuario()) {
					segNodo.setToDelUsuario(seg.isToDelUsuario());
				}

				// PROFESION
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToListProfesiones()) {
					segNodo.setToListProfesiones(seg.isToListProfesiones());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddProfesiones()) {
					segNodo.setToAddProfesiones(seg.isToAddProfesiones());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModProfesiones()) {
					segNodo.setToModProfesiones(seg.isToModProfesiones());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelProfesiones()) {
					segNodo.setToDelProfesiones(seg.isToDelProfesiones());
				}

				// GRUPOS
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToListGrupos()) {
					segNodo.setToListGrupos(seg.isToListGrupos());
				}

				if (!segNodo.isToListGruposWorkflow()) {
					segNodo.setToListGruposWorkflow(seg
							.isToListGruposWorkflow());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddGrupos()) {
					segNodo.setToAddGrupos(seg.isToAddGrupos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModGrupos()) {
					segNodo.setToModGrupos(seg.isToModGrupos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelGrupos()) {
					segNodo.setToDelGrupos(seg.isToDelGrupos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelProfesiones()) {
					segNodo.setToDelProfesiones(seg.isToDelProfesiones());
				}
				// TIPOS DE DOCUMENTOS
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToListTipoDocumentos()) {
					segNodo.setToListTipoDocumentos(seg
							.isToListTipoDocumentos());
				}
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddTipoDocumentos()) {
					segNodo.setToAddTipoDocumentos(seg.isToAddTipoDocumentos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToModTipoDocumentos()) {
					segNodo.setToModTipoDocumentos(seg.isToModTipoDocumentos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDelTipoDocumentos()) {
					segNodo.setToDelTipoDocumentos(seg.isToDelTipoDocumentos());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToMod()) {
					segNodo.setToMod(seg.isToMod());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToDel()) {
					segNodo.setToDel(seg.isToDel());
				}

				if (!segNodo.isConfSeguridad()) {
					segNodo.setConfSeguridad(seg.isConfSeguridad());
				}

				// Principal

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddPrincipal()) {
					segNodo.setToAddPrincipal(seg.isToAddPrincipal());
				}

				// AREA
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddArea()) {
					segNodo.setToAddArea(seg.isToAddArea());
				}

				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToMove()) {
					segNodo.setToMove(seg.isToMove());
				}

				// CARGO
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddCargo()) {
					segNodo.setToAddCargo(seg.isToAddCargo());
				}

				// PROCESO
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddProceso()) {
					segNodo.setToAddProceso(seg.isToAddProceso());
				}

				// CARPETA
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddCarpeta()) {
					segNodo.setToAddCarpeta(seg.isToAddCarpeta());
				}

				// DOCUMENMTOS
				// si todavia es falso, coloco la seguridad del tree haber si
				// cambia
				// si es verdaero, la dejo quieta
				if (!segNodo.isToAddDocumentos()) {
					segNodo.setToAddDocumentos(seg.isToAddDocumentos());
				}

				if (!segNodo.isToAddDocumentoSvn()) {
					segNodo.setToAddDocumentoSvn(seg.isToAddDocumentoSvn());
				}

				if (!segNodo.isToAddLotesDeDocumentos()) {
					segNodo.setToAddLotesDeDocumentos(seg
							.isToAddLotesDeDocumentos());
				}

				if (!segNodo.isToDoFlow()) {

					segNodo.setToDoFlow(seg.isToDoFlow());
				}
				if (!segNodo.isToVincDoc()) {
					segNodo.setToVincDoc(seg.isToVincDoc());
				}
				if (!segNodo.isToDoRegistros()) {
					segNodo.setToDoRegistros(seg.isToDoRegistros());
				}
				if (!segNodo.isToDoPublico()) {
					segNodo.setToDoPublico(seg.isToDoPublico());
				}

				if (!segNodo.isToSaveDataBasic()) {
					segNodo.setToSaveDataBasic(seg.isToSaveDataBasic());
				}
				if (!segNodo.isToGivePermUser()) {
					segNodo.setToGivePermUser(seg.isToGivePermUser());
				}

				if (!segNodo.isToActivePermGroup()) {

					segNodo.setToActivePermGroup(seg.isToActivePermGroup());
				}

				// _________________________________________________
				seguridadIndividualPertenesco.put(tree, segNodo);
			}

		}
	}

	private void mySeguridadMenu(List permisoUser,
			Map seguridadIndividualPertenesco) {
		try {
			int size = permisoUser.size();
			int i = 0;
			while (i < size) {
				Seguridad seg = (Seguridad) permisoUser.get(i);
				i++;
				if (seg == null) {
					continue;
				}
				// SEGURIDDA PARA EL MENU

				// el arbol donde estoy ubicado
				Tree tree = null;
				// si los nodos son negativos, implica que viene para la
				// seguridad del menu
				if (seg.getNodo() < 0) {
					// buscam,os para el menu
					tree = new Tree();
					tree.setNodo(Utilidades.getNodoRaiz());
				} else {
					tree = delegado.obtenerNodo(seg.getNodo());
				}

				// s en este arbol no esta la segurida, la coloco como venga

				if (!seguridadIndividualPertenesco.containsKey(tree)) {
					seguridadIndividualPertenesco.put(tree, seg);

				} else {
					// repaso la segurida para ver si cambio y la actualizo en
					// el arbol
					Seguridad segNodo = (Seguridad) seguridadIndividualPertenesco
							.get(tree);

					/***************************************************************/
					/***************** EMPEZAMOS DE AQUI CON LA SEGURIDAD **********************************************/
					/***************************************************************/

					if (!segNodo.isToViewComentPublic()) {
						segNodo.setToViewComentPublic(seg
								.isToViewComentPublic());
					}

					if (!segNodo.isToDownload()) {
						segNodo.setToDownload(seg.isToDownload());
					}

					if (!segNodo.isToDoFlowRevision()) {
						segNodo.setToDoFlowRevision(seg.isToDoFlowRevision());
					}

					if (!segNodo.isToListarArea()) {
						segNodo.setToListarArea(seg.isToListarArea());
					}

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isConfSeguridadGlobal()) {
						segNodo.setConfSeguridadGlobal(seg
								.isConfSeguridadGlobal());
					}

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToImprimirAdministrar()) {
						segNodo.setToImprimirAutorizacion(seg
								.isToImprimirAdministrar());
					}

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToSolicitudImpresion()) {
						segNodo.setToSolicitudImpresion(seg
								.isToSolicitudImpresion());
					}

					if (!segNodo.isToSolicitudImpresion0()) {
						segNodo.setToSolicitudImpresion0(seg
								.isToSolicitudImpresion0());
					}

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.istoPlantillaInDocFlowParalelo()) {
						segNodo.settoPlantillaInDocFlowParalelo(seg
								.istoPlantillaInDocFlowParalelo());
					}

					if (!segNodo.isToView()) {
						segNodo.setToView(seg.isToView());
					}

					// RAICES, este va para la seguridad -1 que es del menu
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddRaiz()) {
						segNodo.setToAddRaiz(seg.isToAddRaiz());

					}

					// CONTROL DE TIEMPO

					if (!segNodo.isToListarDiasFeriados()) {

						segNodo.setToListarDiasFeriados(seg
								.isToListarDiasFeriados());
					}
					if (!segNodo.isToListarDiasHabiles()) {

						segNodo.setToListarDiasHabiles(seg
								.isToListarDiasHabiles());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddDiaFeriado()) {
						segNodo.setToAddDiaFeriado(seg.isToAddDiaFeriado());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModDiasHabiles()) {
						segNodo.setToModDiasHabiles(seg.isToModDiasHabiles());
					}
					if (!segNodo.isToModDiaFeriado()) {
						segNodo.setToModDiaFeriado(seg.isToModDiaFeriado());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelDiaFeriado()) {
						segNodo.setToDelDiaFeriado(seg.isToDelDiaFeriado());
					}

					// FINB CONTROL DE TIEMPO

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModFlow()) {

						segNodo.setToModFlow(seg.isToModFlow());
					}

					// _______________________________________________
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToListUsuarios()) {

						segNodo.setToListUsuarios(seg.isToListUsuarios());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddUsuario()) {
						segNodo.setToAddUsuario(seg.isToAddUsuario());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModUsuario()) {
						segNodo.setToModUsuario(seg.isToModUsuario());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelUsuario()) {
						segNodo.setToDelUsuario(seg.isToDelUsuario());
					}

					// PROFESION
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToListProfesiones()) {
						segNodo.setToListProfesiones(seg.isToListProfesiones());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddProfesiones()) {
						segNodo.setToAddProfesiones(seg.isToAddProfesiones());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModProfesiones()) {
						segNodo.setToModProfesiones(seg.isToModProfesiones());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelProfesiones()) {
						segNodo.setToDelProfesiones(seg.isToDelProfesiones());
					}

					// GRUPOS
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToListGrupos()) {
						segNodo.setToListGrupos(seg.isToListGrupos());
					}

					if (!segNodo.isToListGruposWorkflow()) {
						segNodo.setToListGruposWorkflow(seg
								.isToListGruposWorkflow());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddGrupos()) {
						segNodo.setToAddGrupos(seg.isToAddGrupos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModGrupos()) {
						segNodo.setToModGrupos(seg.isToModGrupos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelGrupos()) {
						segNodo.setToDelGrupos(seg.isToDelGrupos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelProfesiones()) {
						segNodo.setToDelProfesiones(seg.isToDelProfesiones());
					}
					// TIPOS DE DOCUMENTOS
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToListTipoDocumentos()) {
						segNodo.setToListTipoDocumentos(seg
								.isToListTipoDocumentos());
					}
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddTipoDocumentos()) {
						segNodo.setToAddTipoDocumentos(seg
								.isToAddTipoDocumentos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToModTipoDocumentos()) {
						segNodo.setToModTipoDocumentos(seg
								.isToModTipoDocumentos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDelTipoDocumentos()) {
						segNodo.setToDelTipoDocumentos(seg
								.isToDelTipoDocumentos());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToMod()) {
						segNodo.setToMod(seg.isToMod());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToDel()) {
						segNodo.setToDel(seg.isToDel());
					}

					if (!segNodo.isConfSeguridad()) {
						segNodo.setConfSeguridad(seg.isConfSeguridad());
					}

					// Principal

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddPrincipal()) {
						segNodo.setToAddPrincipal(seg.isToAddPrincipal());
					}

					// AREA
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddArea()) {
						segNodo.setToAddArea(seg.isToAddArea());
					}

					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToMove()) {
						segNodo.setToMove(seg.isToMove());
					}

					// CARGO
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddCargo()) {
						segNodo.setToAddCargo(seg.isToAddCargo());
					}

					// PROCESO
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddProceso()) {
						segNodo.setToAddProceso(seg.isToAddProceso());
					}

					// CARPETA
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddCarpeta()) {
						segNodo.setToAddCarpeta(seg.isToAddCarpeta());
					}

					// DOCUMENMTOS
					// si todavia es falso, coloco la seguridad del tree haber
					// si cambia
					// si es verdaero, la dejo quieta
					if (!segNodo.isToAddDocumentos()) {
						segNodo.setToAddDocumentos(seg.isToAddDocumentos());
					}

					if (!segNodo.isToAddDocumentoSvn()) {
						segNodo.setToAddDocumentoSvn(seg.isToAddDocumentoSvn());
					}

					if (!segNodo.isToAddLotesDeDocumentos()) {
						segNodo.setToAddLotesDeDocumentos(seg
								.isToAddLotesDeDocumentos());
					}

					if (!segNodo.isToDoFlow()) {

						segNodo.setToDoFlow(seg.isToDoFlow());
					}
					if (!segNodo.isToVincDoc()) {
						segNodo.setToVincDoc(seg.isToVincDoc());
					}
					if (!segNodo.isToDoRegistros()) {
						segNodo.setToDoRegistros(seg.isToDoRegistros());
					}
					if (!segNodo.isToDoPublico()) {
						segNodo.setToDoPublico(seg.isToDoPublico());
					}

					if (!segNodo.isToSaveDataBasic()) {
						segNodo.setToSaveDataBasic(seg.isToSaveDataBasic());
					}
					if (!segNodo.isToGivePermUser()) {
						segNodo.setToGivePermUser(seg.isToGivePermUser());
					}
					if (!segNodo.isToActivePermGroup()) {
						segNodo.setToActivePermGroup(seg.isToActivePermGroup());
					}

					// _________________________________________________
					seguridadIndividualPertenesco.put(tree, segNodo);
				}

			}

		} catch (Exception e) {

		}
	}

	public void setSelectPermisologiaRolUser(String selectPermisologiaRolUser) {
		this.selectPermisologiaRolUser = selectPermisologiaRolUser;
	}

	public void buscarTodosLosRoles(List _roles_popup,
			boolean usadoParaCrearFlujo) {
		ClienteRole_OperacionesPopup role_oper_popup = new ClienteRole_OperacionesPopup();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		List<Role> rols = delegado.findAll_Roles(user_logueado,
				usadoParaCrearFlujo); // delegado.findAll_Roles(user_logueado);

		for (Role role : rols) {
			boolean swPrimeravez = true;
			// buscamos todos las operaciones del rol
			// List<SelectItem> OperacionesLista=
			// operacionesInRolesMenuAndTree(role);
			StringBuffer cuantasOperaciones = new StringBuffer("");
			/*
			 * for (SelectItem item:OperacionesLista){ //Operaciones
			 * op=(Operaciones)item.getValue(); if (swPrimeravez){
			 * cuantasOperaciones.append(item.getLabel()); swPrimeravez=false;
			 * }else{ cuantasOperaciones.append(", ").append(item.getLabel()); }
			 * }
			 */
			// buscamos todos los usuarios del rol
			List<SelectItem> UsuariosLista = usuariosInRole(role);
			StringBuffer UsuarioInf = new StringBuffer("");
			swPrimeravez = true;
			for (SelectItem item : UsuariosLista) {
				if (swPrimeravez) {
					UsuarioInf.append(item.getLabel());
					swPrimeravez = false;
				} else {
					UsuarioInf.append(", ").append(item.getLabel());
				}
			}

			role_oper_popup = new ClienteRole_OperacionesPopup(
					role.getCodigo(), role.getNombre(), role.getDescripcion(),
					cuantasOperaciones.toString(), UsuarioInf.toString());
			_roles_popup.add(role_oper_popup);

		}
	}

	public void buscarTodosLosRoles(List _roles_popup) {
		ClienteRole_OperacionesPopup role_oper_popup = new ClienteRole_OperacionesPopup();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		boolean usadoParaCrearFlujo = false;
		List<Role> rols = delegado.findAll_Roles(user_logueado,
				usadoParaCrearFlujo);
		for (Role role : rols) {
			boolean swPrimeravez = true;
			// buscamos todos las operaciones del rol
			// List<SelectItem> OperacionesLista=
			// operacionesInRolesMenuAndTree(role);
			StringBuffer cuantasOperaciones = new StringBuffer("");
			/*
			 * for (SelectItem item:OperacionesLista){ //Operaciones
			 * op=(Operaciones)item.getValue(); if (swPrimeravez){
			 * cuantasOperaciones.append(item.getLabel()); swPrimeravez=false;
			 * }else{ cuantasOperaciones.append(", ").append(item.getLabel()); }
			 * }
			 */
			// buscamos todos los usuarios del rol
			List<SelectItem> UsuariosLista = usuariosInRole(role);
			StringBuffer UsuarioInf = new StringBuffer("");
			swPrimeravez = true;
			for (SelectItem item : UsuariosLista) {
				if (swPrimeravez) {
					UsuarioInf.append(item.getLabel());
					swPrimeravez = false;
				} else {
					UsuarioInf.append(", ").append(item.getLabel());
				}
			}

			role_oper_popup = new ClienteRole_OperacionesPopup(
					role.getCodigo(), role.getNombre(), role.getDescripcion(),
					cuantasOperaciones.toString(), UsuarioInf.toString());
			_roles_popup.add(role_oper_popup);

		}
	}

	public List operacionesInRolesMenuAndTree(Role role) {
		// filtro segunb el arbol donde estoy
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		List<Operaciones> filtro = null;
		if (tree != null) {
			filtro = delegado.findAll_operacionesArbol(tree);
			List<Operaciones> filtro2 = delegado.findAll_operacionesMenu();
			filtro.containsAll(filtro2);
		} else {
			filtro = delegado.findAll_operacionesMenu();
		}

		// las operaciones en roles
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(role);
		List<Seguridad_Role_Lineal> listaSegRols = delegado
				.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);

		List operacionesInrol = new ArrayList();
		if (listaSegRols != null && !listaSegRols.isEmpty()) {
			seguridad_Role_Lineal = listaSegRols.get(0);
			List<Operaciones> enBd = delegado.findAll_operaciones();
			SelectItem item;
			for (Operaciones op : enBd) {
				if (delegado.existeOperacionSeguridadLineal(op,
						seguridad_Role_Lineal)) {
					if (filtro.contains(op)) {
						item = new SelectItem(op, messages.getString(op
								.getOperacion().toString()));
						operacionesInrol.add(item);
					}
				}
			}
		}
		return operacionesInrol;
	}

	public List<String> operacionesInRolesForTreeOnlyRichFaces(Role role) {
		// filtro segunb el arbol donde estoy
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		List<Operaciones> filtro = null;
		if (tree == null) {
			return null;
		}

		filtro = delegado.findAll_operacionesArbol(tree);

		// las operaciones en roles
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(role);
		List<Seguridad_Role_Lineal> listaSegRols = delegado
				.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);

		List operacionesInrol = new ArrayList();
		if (listaSegRols != null && !listaSegRols.isEmpty()) {
			seguridad_Role_Lineal = listaSegRols.get(0);
			List<Operaciones> enBd = delegado.findAll_operaciones();
			for (Operaciones op : enBd) {
				if (delegado.existeOperacionSeguridadLineal(op,
						seguridad_Role_Lineal)) {
					if (filtro.contains(op)) {
						// item = new SelectItem(op, messages.getString(op
						// .getOperacion().toString()));
						operacionesInrol.add(messages.getString(op
								.getOperacion().toString()));
					}
				}
			}
		}
		return operacionesInrol;
	}

	public List operacionesInRolesForTreeOnly(Role role) {
		// filtro segunb el arbol donde estoy
		tree = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		List<Operaciones> filtro = null;
		if (tree == null) {
			return null;
		}

		filtro = delegado.findAll_operacionesArbol(tree);

		// las operaciones en roles
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(role);
		List<Seguridad_Role_Lineal> listaSegRols = delegado
				.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);

		List operacionesInrol = new ArrayList();
		if (listaSegRols != null && !listaSegRols.isEmpty()) {
			seguridad_Role_Lineal = listaSegRols.get(0);
			List<Operaciones> enBd = delegado.findAll_operaciones();
			SelectItem item;
			for (Operaciones op : enBd) {
				if (delegado.existeOperacionSeguridadLineal(op,
						seguridad_Role_Lineal)) {
					if (filtro.contains(op)) {
						item = new SelectItem(op, messages.getString(op
								.getOperacion().toString()));
						operacionesInrol.add(item);
					}
				}
			}
		}
		return operacionesInrol;
	}

	public List usuariosInRole(Role role) {
		// usuarios en roles
		List usuariosInRol = new ArrayList();
		// System.out.println("role.getCodigo()="+role.getCodigo());
		List enBd = delegado.findRoles_Usuario(role);
		if (enBd != null) {
			int h = enBd.size();
			SelectItem item;
			for (int k = 0; k < h; k++) {
				// System.out.println("usuariosss="+k);
				Usuario user = (Usuario) enBd.get(k);
				item = new SelectItem(user, user.toString());
				usuariosInRol.add(item);
			}

		}
		return usuariosInRol;
	}

	public List getUsuariosInRol() {
		return usuariosInRol;
	}

	public void setUsuariosInRol(List usuariosInRol) {
		this.usuariosInRol = usuariosInRol;
	}

	public List getOperacionesInUsuario() {
		return operacionesInUsuario;
	}

	public void setOperacionesInUsuario(List operacionesInUsuario) {
		this.operacionesInUsuario = operacionesInUsuario;
	}

	public Tree getTreePadreTipoHereda() {
		return treePadreTipoHereda;
	}

	public void setTreePadreTipoHereda(Tree treePadreTipoHereda) {
		this.treePadreTipoHereda = treePadreTipoHereda;
	}
	public void refrescarArbolWorkFlows() {
		if (session != null) {
			if (session.getAttribute("docTaskPendiente") != null) {
				session.removeAttribute("docTaskPendiente");
			}
			session.setAttribute(Utilidades.getRefrescararbolworkflows(), "");
		}
	}

	public void refrescaraplicacionarbol() {
		if (session != null) {
			if (session.getAttribute("obtenArbolSeguridad") != null) {
				session.removeAttribute("obtenArbolSeguridad");
			}
			if (session.getAttribute("docBuscar") != null) {
				session.removeAttribute("docBuscar");
			}
			 
			session.setAttribute(Utilidades.getTreeprocesando(), "");
		}
	}

	public void refrescaraplicaciondetalles() {
		if (session != null) {
			if (session.getAttribute("doc_detallesAux") != null) {
				session.removeAttribute("doc_detallesAux");
			}

			if (session.getAttribute("parametro") != null) {
				session.removeAttribute("parametro");
			}

			if (session.getAttribute("clienteDocumentoMaestrosPanelControl") != null) {
				session.removeAttribute("clienteDocumentoMaestrosPanelControl");
			}
			if (session.getAttribute("clienteDocumentoMaestros") != null) {
				session.removeAttribute("clienteDocumentoMaestros");
			}

			if (session.getAttribute("listReferenciaUnavez") != null) {
				session.removeAttribute("listReferenciaUnavez");
			}
		}
	}
}
