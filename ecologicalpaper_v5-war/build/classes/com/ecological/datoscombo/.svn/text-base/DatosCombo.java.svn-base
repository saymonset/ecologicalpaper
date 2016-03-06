/*
 * DatosCombo.java
 *
 * Created on July 21, 2007, 8:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.datoscombo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_relacionados;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowOnlyNotificacionRole;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.Utilidades;

/**
 * 
 * @author simon
 */
public class DatosCombo extends ContextSessionRequest {

	private ServicioDelegado delegado = new ServicioDelegado();
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");
	private ResourceBundle confmessages = super
			.getResourceBundle("com.util.resource.ecological_conf");
	private Seguridad seguridadTree = new Seguridad();
	private boolean swSuperUsuario;
	private HttpSession session = super.getSession();
	private List<SelectItem> lenguajes;
	private Map country;
	private String pais;
	private List<Flow>participantesGruposPlantila = new ArrayList<Flow>();
	private List<Flow>participantesPerfiles = new ArrayList<Flow>();
	// SEGURIDAD
	private Seguridad seguridadMenu = new Seguridad();
	private boolean swTodosLosRoles;

	/** Creates a new instance of DatosCombo */
	public DatosCombo() {
		session = super.getSession();
		// verificamos si es super usuario
		if (super.getUser_logueado() != null
				&& super.getUser_logueado().getLogin() != null) {
			swSuperUsuario = super.getUser_logueado().getLogin()
					.equalsIgnoreCase(Utilidades.getRoot());
		}
	}

	public List<SelectItem> getUsuariosUnoVacio() {

		List<SelectItem> result = new ArrayList<SelectItem>();
		session = super.getSession();
		// boolean swCacheUsuario=session!=null &&
		// session.getAttribute("swCacheUsuario")!=null?(Boolean)session.getAttribute("swCacheUsuario"):false;
		// if (!swCacheUsuario){
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// este usuario session tiene problemas..--
		// if (user_logueado != null) {
		List<Usuario> usuarios = delegado.findAll_Usuario(user_logueado);
		Usuario user_vacio = new Usuario();
		user_vacio.setId(Utilidades.getMalo());
		SelectItem item = new SelectItem(user_vacio, "");
		result.add(item);
		for (Usuario user : usuarios) {
			String cargo = user.getCargo() != null ? user.getCargo()
					.getNombre() : "";
			if (!"".equals(cargo)) {
				cargo = "[" + cargo + "]";
			}

			String empresa = user.getEmpresa() != null ? user.getEmpresa()
					.getNombre() : "";
			if (!"".equals(cargo)) {
				empresa = "[" + empresa + "]";
			}

			item = new SelectItem(user, user.getApellido() + " "
					+ user.getNombre() + cargo + empresa);
			result.add(item);
		}
		// }
		// session.setAttribute("swCacheUsuario",true);
		// session.setAttribute("result",result);

		/*
		 * }else{
		 * 
		 * session.setAttribute("swCacheUsuario",true);
		 * result=(List<SelectItem>)session.getAttribute("result"); }
		 */

		return result;
	}

	public List<SelectItem> getUsuarios() {

		List<SelectItem> result = new ArrayList<SelectItem>();
		session = super.getSession();
		// boolean swCacheUsuario=session!=null &&
		// session.getAttribute("swCacheUsuario")!=null?(Boolean)session.getAttribute("swCacheUsuario"):false;
		// if (!swCacheUsuario){
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		// este usuario session tiene problemas..--
		// if (user_logueado != null) {
		List<Usuario> usuarios = delegado.findAll_Usuario(user_logueado);
		Usuario user_vacio = new Usuario();
		user_vacio.setId(Utilidades.getMalo());
		SelectItem item = new SelectItem(user_vacio, "");
		// result.add(item);
		for (Usuario user : usuarios) {
			String cargo = user.getCargo() != null ? user.getCargo()
					.getNombre() : "";
			if (!"".equals(cargo)) {
				cargo = "[" + cargo + "]";
			}

			String empresa = user.getEmpresa() != null ? user.getEmpresa()
					.getNombre() : "";
			if (!"".equals(cargo)) {
				empresa = "[" + empresa + "]";
			}

			item = new SelectItem(user, user.getApellido() + " "
					+ user.getNombre() + cargo + empresa);
			result.add(item);
		}
		// }
		// session.setAttribute("swCacheUsuario",true);
		// session.setAttribute("result",result);

		/*
		 * }else{
		 * 
		 * session.setAttribute("swCacheUsuario",true);
		 * result=(List<SelectItem>)session.getAttribute("result"); }
		 */

		return result;
	}

	public List<SelectItem> getAllVersionesSvn() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			SelectItem item = null;
			for (int i = -1; i < 1000; i++) {
				if (i == -1) {
					item = new SelectItem(new Integer(i), "Ultima revision: "
							+ i);
				} else {
					item = new SelectItem(new Integer(i), "revision: " + i);
				}
				result.add(item);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SelectItem> getAllEmpresas() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<Tree> empresas = delegado.findAllEmpresas();
			SelectItem item = null;
			for (Tree empresa : empresas) {
				if (empresa != null) {
					item = new SelectItem(empresa, empresa.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SelectItem> getAllSvnNombreAplicacion(SvnUrlBase svnUrlBase) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<SvnNombreAplicacion> svnNombreAplicaciones = delegado
					.findAllSvnNombreAplicacion(svnUrlBase);
			SelectItem item = null;
			for (SvnNombreAplicacion svnNombreAplicacion : svnNombreAplicaciones) {
				if (svnNombreAplicacion != null) {
					item = new SelectItem(svnNombreAplicacion,
							svnNombreAplicacion.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SelectItem> getAllSvnUrlBase() {
		Usuario usuario = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		SelectItem item = null;
		SvnUrlBase svnUrlBase1 = new SvnUrlBase();
		svnUrlBase1.setCodigo(-1L);
		svnUrlBase1.setNombre("");
		item = new SelectItem(svnUrlBase1, svnUrlBase1.getNombre());
		result.add(item);
		try {
			List<SvnUrlBase> svnUrlBases = delegado.findAllSvnUrlBase(usuario,
					null);
			item = null;
			String nom = "";

			for (SvnUrlBase svnUrlBase : svnUrlBases) {

				if (svnUrlBase != null) {
					nom = svnUrlBase.getNombre() != null ? svnUrlBase
							.getNombre().trim() : "";
					item = new SelectItem(svnUrlBase, nom);
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * public List<SelectItem> getAllSvnModulo( ) { SvnNombreAplicacion
	 * svnNombreAplicacion=session.getAttribute("svnNombreAplicacion")!=null?
	 * (SvnNombreAplicacion)session.getAttribute("svnNombreAplicacion"):null;
	 * List<SelectItem> result = new ArrayList<SelectItem>(); SelectItem item =
	 * null; SvnModulo svnModulo = new SvnModulo(); svnModulo.setCodigo(-1L);
	 * svnModulo.setPathCorto1(""); item = new SelectItem(svnModulo,
	 * svnModulo.toString()); result.add(item); try {
	 * 
	 * List<SvnModulo> lista =delegado.findAllSvnModulo(svnNombreAplicacion);
	 * item = null; if (lista!=null){ for (SvnModulo objeto : lista) { if
	 * (objeto != null) { item = new SelectItem(objeto, objeto.toString());
	 * result.add(item); } } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return result; }
	 */

	/*
	 * public List<SelectItem> getAllSvnNombreAplicacion() { List<SelectItem>
	 * items = new ArrayList<SelectItem>(); SvnNombreAplicacion s= new
	 * SvnNombreAplicacion(); s.setCodigo(-1L); s.setNombre(""); SelectItem it=
	 * new SelectItem(s,s.getNombre()); items.add(it); SvnTipoAmbiente
	 * svnTipoAmbiente
	 * =session.getAttribute("svnTipoAmbiente")!=null?(SvnTipoAmbiente
	 * )session.getAttribute("svnTipoAmbiente"):null; if (svnTipoAmbiente !=
	 * null ) { SvnTipoAmbiente econtrarTodosLosHijos = svnTipoAmbiente;
	 * List<SelectItem> items2 = getAllSvnNombreAplicacion(svnTipoAmbiente); if
	 * (items2!=null){ } items.addAll(items2); } return items; }
	 */

	public List<SelectItem> getAllEmpresas(Usuario usuario) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<Tree> empresas = delegado.findAllEmpresas(usuario);
			SelectItem item = null;
			for (Tree empresa : empresas) {
				if (empresa != null) {
					item = new SelectItem(empresa, empresa.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// sirve para cualquier ramo del arbol,
	// esta vez se filtran solo para areas
	public List<SelectItem> getAllAreas() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<Tree> areas = delegado.findAllAreas();
			SelectItem item = null;
			for (Tree area : areas) {
				if (area != null) {
					item = new SelectItem(area, area.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//
	public List<SelectItem> getAllCargos() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<Tree> cargos = delegado.findAllCargos();
			SelectItem item = null;
			for (Tree cargo : cargos) {
				if (cargo != null) {
					item = new SelectItem(cargo, cargo.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SelectItem> getAllExtensionFiles() {
		List<SelectItem> lstprof = new ArrayList<SelectItem>();
		try {

			List<ExtensionFile> lstbdProf = delegado.find_allExtensionFile();
			ExtensionFile p = new ExtensionFile();
			p.setCodigo(0l);
			p.setExtension("");

			SelectItem item = new SelectItem(p, p.getExtension());
			lstprof.add(item);
			for (ExtensionFile objecto : lstbdProf) {

				if (objecto != null) {
					item = new SelectItem(objecto, objecto.getExtension() + "["
							+ objecto.getMimeType() + "]");
					lstprof.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstprof;
	}

	public List<SelectItem> getAllPaises() {
		List<SelectItem> lstprof = new ArrayList<SelectItem>();
		try {

			List<Pais> lstbdProf = delegado.find_allPaises();
			Pais p = new Pais();
			p.setCodigo(0l);
			p.setNombre("");

			SelectItem item = new SelectItem(p, p.getNombre());
			lstprof.add(item);
			for (Pais pais : lstbdProf) {
				if (pais != null) {
					item = new SelectItem(pais, pais.getNombre());
					lstprof.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstprof;
	}

	public List<SelectItem> getAllEstados(Pais pais) {
		List<SelectItem> lstprof = new ArrayList<SelectItem>();
		try {

			List<Estado> lstbdProf = delegado.findAll_EstadoByPais(pais);
			SelectItem item = null;
			for (Estado estado : lstbdProf) {
				if (estado != null) {
					item = new SelectItem(estado, estado.getNombre());
					lstprof.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstprof;
	}

	public List<SelectItem> getAllCiudades(Estado estado) {
		List<SelectItem> lstprof = new ArrayList<SelectItem>();
		try {

			List<Ciudad> ciudades = delegado.findAll_CiudadByEstado(estado);
			SelectItem item = null;
			for (Ciudad ciudad : ciudades) {
				if (ciudad != null) {
					item = new SelectItem(ciudad, ciudad.getNombre());
					lstprof.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstprof;
	}

	public List<SelectItem> getAllProfesion() {
		List<SelectItem> lstprof = new ArrayList<SelectItem>();
		try {
			Usuario user_logueado = (Usuario) session
					.getAttribute("user_logueado");
			if (user_logueado != null) {
				List<Profesion> lstbdProf = delegado
						.findAll_Profesion(user_logueado);
				SelectItem item = null;
				for (Profesion profesion : lstbdProf) {
					if (profesion != null) {
						item = new SelectItem(profesion, profesion.getNombre());
						lstprof.add(item);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstprof;
	}
	
	public List<Doc_estado> getDoc_EstadosRichFaces() {
		List<Doc_estado> doc_estados = delegado.findAllDoc_estados();
		List<Doc_estado> result = new ArrayList<Doc_estado>();

		Doc_estado vacio = new Doc_estado();
		vacio.setCodigo(Utilidades.getMalo());
		//SelectItem item = new SelectItem(vacio, "");
		// da error
		// result.add(item);

		for (Doc_estado doc_edo : doc_estados) {
			//item = new SelectItem(doc_edo, doc_edo.getNombre());
			result.add(doc_edo);
		}

		return result;
	}

	public List<SelectItem> getDoc_Estados() {
		List<Doc_estado> doc_estados = delegado.findAllDoc_estados();
		List<SelectItem> result = new ArrayList<SelectItem>();

		Doc_estado vacio = new Doc_estado();
		vacio.setCodigo(Utilidades.getMalo());
		SelectItem item = new SelectItem(vacio, "");
		// da error
		// result.add(item);

		for (Doc_estado doc_edo : doc_estados) {
			item = new SelectItem(doc_edo, doc_edo.getNombre());
			result.add(item);
		}

		return result;
	}

	public List<SelectItem> getTodosLosRoles() {
		
		List<SelectItem> result = new ArrayList<SelectItem>();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Role> listas = delegado.findAll_Roles(user_logueado);
			participantesGruposPlantila=participantesPerfiles(listas);
		
			SelectItem item = null;
			for (Role role : listas) {
				item = new SelectItem(role, role.getNombre());
				result.add(item);
			}
		}
		/*
		 * DA ERROR Role vacio = new Role();
		 * vacio.setCodigo(Utilidades.getMalo());
		 * vacio.setNombre("Ningun Grupo"); item=new
		 * SelectItem(vacio,vacio.getNombre()); result.add(item);
		 */

		return result;
	}

	public List<SelectItem> getTipoDeFirma() {
		List<Doc_estado> doc_estados = delegado.findAllTipoDeFirma();
		List<SelectItem> result = new ArrayList<SelectItem>();
		SelectItem item = null;
		for (Doc_estado doc_edo : doc_estados) {
			item = new SelectItem(doc_edo, messages.getString(doc_edo
					.getNombre()));
			result.add(item);
		}

		return result;
	}

	public List<SelectItem> getTipoDeFirma(Flow flow) {
		List<Doc_estado> doc_estados = delegado.findAllTipoDeFirma(flow);
		List<SelectItem> result = new ArrayList<SelectItem>();
		SelectItem item = null;
		for (Doc_estado doc_edo : doc_estados) {
			item = new SelectItem(doc_edo, messages.getString(doc_edo
					.getNombre()));
			result.add(item);
		}
		return result;
	}

	// se lñe puede rechazar al participante anterior
	public List<SelectItem> getTipoDeFirmaWithParticpanteAnterior(Flow flow) {
		List<Doc_estado> doc_estados = delegado
				.findAllTipoDeFirmaWithParticipanteAnterior(flow);
		List<SelectItem> result = new ArrayList<SelectItem>();
		SelectItem item = null;
		for (Doc_estado doc_edo : doc_estados) {
			item = new SelectItem(doc_edo, messages.getString(doc_edo
					.getNombre()));
			result.add(item);
		}

		return result;
	}

	public List<SelectItem> getDoc_tipoOneVacio() {
		session = super.getSession();
		Usuario user_logueado = session != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		if (user_logueado != null) {
			List<Doc_tipo> doc_tipos = delegado.findAllDoc_tipos(user_logueado);

			Doc_tipo vacio = new Doc_tipo();
			vacio.setCodigo(Utilidades.getMalo());
			SelectItem item = new SelectItem(vacio, "");
			result.add(item);
			for (Doc_tipo doc_tipo : doc_tipos) {
				if (doc_tipo != null && doc_tipo.getFormatoTipoDoc() != null) {
					if (Utilidades.getFormatoTipoDoc()
							- doc_tipo.getFormatoTipoDoc() == 0) {
						doc_tipo.setSwTipoFormato(true);
					}
				}
				StringBuffer plantilla = new StringBuffer("");
				;
				if (doc_tipo.isSwTipoFormato()) {
					plantilla.append("[").append(messages.getString("formato"))
							.append("]");
				}
				item = new SelectItem(doc_tipo, doc_tipo.getNombre()
						+ plantilla.toString());
				result.add(item);
			}
		}
		return result;
	}
	public List<SelectItem> getDoc_tipo() {
		session = super.getSession();
		Usuario user_logueado = session != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		if (user_logueado != null) {
			List<Doc_tipo> doc_tipos = delegado.findAllDoc_tipos(user_logueado);
			Doc_tipo vacio = new Doc_tipo();
			vacio.setCodigo(Utilidades.getMalo());
			SelectItem item = new SelectItem(vacio, "");
			// result.add(item);

			for (Doc_tipo doc_tipo : doc_tipos) {
				if (doc_tipo != null && doc_tipo.getFormatoTipoDoc() != null) {
					if (Utilidades.getFormatoTipoDoc()
							- doc_tipo.getFormatoTipoDoc() == 0) {
						doc_tipo.setSwTipoFormato(true);
					}
				}
				StringBuffer plantilla = new StringBuffer("");
				;
				if (doc_tipo.isSwTipoFormato()) {
					plantilla.append("[").append(messages.getString("formato"))
							.append("]");
				}
				item = new SelectItem(doc_tipo, doc_tipo.getNombre()
						+ plantilla.toString());
				result.add(item);
			}
		}
		return result;
	}
	public List<SelectItem> getAreaDocumentosOneVacio() {
		session = super.getSession();
		Usuario user_logueado = session != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		if (user_logueado != null) {
			List<AreaDocumentos> objetos = delegado
					.findAllAreaDocumentos(user_logueado);
			AreaDocumentos vacio = new AreaDocumentos();
			vacio.setCodigo(null);
			SelectItem item = new SelectItem(vacio, "");
			result.add(item);
			for (AreaDocumentos obj : objetos) {
				item = new SelectItem(obj, obj.getNombre());
				result.add(item);
			}
		}

		return result;
	}

	public List<SelectItem> getAreaDocumentos() {
		session = super.getSession();
		Usuario user_logueado = session != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		List<SelectItem> result = new ArrayList<SelectItem>();
		if (user_logueado != null) {
			List<AreaDocumentos> objetos = delegado
					.findAllAreaDocumentos(user_logueado);

			AreaDocumentos vacio = new AreaDocumentos();
			vacio.setCodigo(Utilidades.getMalo());
			SelectItem item = new SelectItem(vacio, "");
			// result.add(item);

			for (AreaDocumentos obj : objetos) {
				item = new SelectItem(obj, obj.getNombre());
				result.add(item);
			}
		}

		return result;
	}

	public void llenarUsuariosFlowVisiblesPlantilla(
			List<SelectItem> seleccionados, List<SelectItem> noSeleccionados,
			Doc_detalle doc) {

		SelectItem item = new SelectItem();
		int size = 0;
		List<Usuario> enBd = new ArrayList<Usuario>();
		List<Flow_Participantes> enBd2 = new ArrayList<Flow_Participantes>();
		if (doc != null) {

			enBd2 = (List<Flow_Participantes>) delegado
					.findByFlowParticipantesPlantilla(doc);
			size = enBd2.size();
			item = null;
			for (Flow_Participantes flow_p : enBd2) {
				if (flow_p != null) {
					Usuario usuario = flow_p.getParticipante();
					if (usuario != null) {
						if (!enBd.contains(usuario)) {
							item = new SelectItem(usuario, usuario.toString());
							seleccionados.add(item);
							enBd.add(usuario);
						}

					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);
				if (!enBd.contains(usu)) {
					item = new SelectItem(usu, usu.toString());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public void llenarUsuariosFlowVisiblesPlantilla(
			List<SelectItem> seleccionados, List<SelectItem> noSeleccionados,
			Flow flow) {

		SelectItem item = new SelectItem();
		int size = 0;
		List<Usuario> enBd = new ArrayList<Usuario>();
		List<Flow_Participantes> enBd2 = new ArrayList<Flow_Participantes>();
		if (flow != null) {

			enBd2 = (List<Flow_Participantes>) delegado
					.findByFlowParticipantes(flow);
			if (enBd2 == null) {
				enBd2 = new ArrayList<Flow_Participantes>();
			}
			size = enBd2.size();
			item = null;
			for (Flow_Participantes flow_p : enBd2) {
				if (flow_p != null) {
					Usuario usuario = flow_p.getParticipante();
					if (usuario != null) {
						if (!enBd.contains(usuario)) {
							item = new SelectItem(usuario, usuario.toString());
							seleccionados.add(item);
							enBd.add(usuario);
						}

					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);
				if (!enBd.contains(usu)) {
					item = new SelectItem(usu, usu.toString());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public void llenarUsuariosFlowVisiblesSolicitudImpresion(
			List<SelectItem> seleccionados, List<SelectItem> noSeleccionados,
			Doc_detalle doc) {

		// CONSEGUIMOS LA SEGURIDAD MENU
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		SelectItem item = new SelectItem();
		int size = 0;
		List<Usuario> enBd = new ArrayList<Usuario>();
		List<Flow_Participantes> enBd2 = new ArrayList<Flow_Participantes>();
		if (doc != null) {
			// si viene editando.. me trae los participantes ya involucradois..
			// ya sea plantilla o sea normal
			if (doc.getFlowEditar() != null) {
				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantesSinRolForEditar(doc
								.getFlowEditar());
			} else {
				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantes(doc);
			}

			size = enBd2.size();
			item = null;
			for (Flow_Participantes flow_p : enBd2) {
				if (flow_p != null) {
					Usuario usuario = flow_p.getParticipante();
					if (usuario != null) {

						System.out.println("usuario.toString()="
								+ usuario.toString());
						System.out
								.println("seguridadTree.isToImprimirAdministrar()="
										+ seguridadTree
												.isToImprimirAdministrar());
						if (seguridadMenu.isToImprimirAdministrar()) {
							if (!enBd.contains(usuario)) {
								item = new SelectItem(usuario,
										usuario.toString());
								seleccionados.add(item);
								enBd.add(usuario);
							}
						}

					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);

				if (seguridadMenu.isToImprimirAdministrar()) {
					if (!enBd.contains(usu)) {
						item = new SelectItem(usu, usu.toString());
						noSeleccionados.add(item);
					}
				}
			}
		}

	}

	public void llenarUsuariosFlowVisibles(List<SelectItem> seleccionados,
			List<SelectItem> noSeleccionados, Doc_detalle doc) {

		SelectItem item = new SelectItem();
		int size = 0;
		List<Usuario> enBd = new ArrayList<Usuario>();
		List<Flow_Participantes> enBd2 = new ArrayList<Flow_Participantes>();
		if (doc != null) {
			// si viene editando.. me trae los participantes ya involucradois..
			// ya sea plantilla o sea normal
			if (doc.getFlowEditar() != null) {
				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantesSinRolForEditar(doc
								.getFlowEditar());
			} else {
				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantes(doc);
			}

			size = enBd2.size();
			item = null;
			for (Flow_Participantes flow_p : enBd2) {
				if (flow_p != null) {
					Usuario usuario = flow_p.getParticipante();
					if (usuario != null) {
						if (!enBd.contains(usuario)) {
							item = new SelectItem(usuario, usuario.toString());
							seleccionados.add(item);
							enBd.add(usuario);
						}

					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);
				if (!enBd.contains(usu)) {
					item = new SelectItem(usu, usu.toString());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public void llenarUsuariosFlowVisiblesRichFaces(List seleccionados,
			List noSeleccionados, Doc_detalle doc) {
		int size = 0;
		List<Usuario> enBd = new ArrayList<Usuario>();
		List<Flow_Participantes> enBd2 = new ArrayList<Flow_Participantes>();
		if (doc != null) {
			// si viene editando.. me trae los participantes ya involucradois..
			// ya sea plantilla o sea normal
			if (doc.getFlowEditar() != null) {
				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantesSinRolForEditar(doc
								.getFlowEditar());
			} else {
				 

				enBd2 = (List<Flow_Participantes>) delegado
						.findByFlowParticipantes(doc);
			}

			size = enBd2.size();

			for (Flow_Participantes flow_p : enBd2) {
				if (flow_p != null) {
					Usuario usuario = flow_p.getParticipante();
					if (usuario != null) {
						if (!enBd.contains(usuario)) {
							seleccionados.add(usuario);
							enBd.add(usuario);
						}

					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);
				if (!enBd.contains(usu)) {
					noSeleccionados.add(usu);
				}
			}
		}

	}
	
	
	public void llenarRoleFlowVisiblesRichFacesNotificacion(List seleccionados,
			List noSeleccionados, Flow flow) {

		int size = 0;
		List enBd = new ArrayList();
		List enBd2 = new ArrayList();
		if (flow != null) {
			enBd2 = delegado.findByFlowOnlyNotificacionRole(flow);
			size = enBd2.size();
			// da error
			// SelectItem item=null;

			for (int i = 0; i < size; i++) {
				FlowOnlyNotificacionRole flow_ref = (FlowOnlyNotificacionRole) enBd2
						.get(i);
				if (flow_ref != null) {
					Role role = flow_ref.getRole();
					if (role != null) {
						enBd.add(role);

						seleccionados.add(role);
					}
				}
			}
		}
		
		//BUSCA TODOS LOS GRUPOS Y CHEQUEA QUE NO PERTENEZCA A LA LISTA ANTERIOR PARA COLOCARLA COMO ESCOJIBLE
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				//wrkflow, variable que me coloca en observar solo los grupos y no los permisos
		boolean workFlow = true;
		if (user_logueado != null) {
			List<Role> items2 = delegado.findAll_Roles(user_logueado, workFlow);
			size = items2.size();

			for (int i = 0; i < size; i++) {
				Role role = (Role) items2.get(i);
				if (!enBd.contains(role)) {

					noSeleccionados.add(role);
				}
			}
		}

	}

	public void llenarRoleFlowVisiblesRichFaces(List seleccionados,
			List noSeleccionados, Flow flow) {

		int size = 0;
		List enBd = new ArrayList();
		List enBd2 = new ArrayList();
		if (flow != null) {
			enBd2 = delegado.findByFlow_referencia_role(flow);
			size = enBd2.size();
			// da error
			// SelectItem item=null;

			for (int i = 0; i < size; i++) {
				Flow_referencia_role flow_ref = (Flow_referencia_role) enBd2
						.get(i);
				if (flow_ref != null) {
					Role role = flow_ref.getRole();
					if (role != null) {
						enBd.add(role);

						seleccionados.add(role);
					}
				}
			}
		}
		
		//BUSCA TODOS LOS GRUPOS Y CHEQUEA QUE NO PERTENEZCA A LA LISTA ANTERIOR PARA COLOCARLA COMO ESCOJIBLE
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
				//wrkflow, variable que me coloca en observar solo los grupos y no los permisos
		boolean workFlow = true;
		if (user_logueado != null) {
			List<Role> items2 = delegado.findAll_Roles(user_logueado, workFlow);
			size = items2.size();

			for (int i = 0; i < size; i++) {
				Role role = (Role) items2.get(i);
				if (!enBd.contains(role)) {

					noSeleccionados.add(role);
				}
			}
		}

	}

	public void llenarRoleFlowVisibles(List seleccionados,
			List noSeleccionados, Flow flow) {

		SelectItem item = new SelectItem();

		int size = 0;
		List enBd = new ArrayList();
		List enBd2 = new ArrayList();
		if (flow != null) {
			enBd2 = delegado.findByFlow_referencia_role(flow);
			size = enBd2.size();
			// da error
			// SelectItem item=null;
			item = null;
			for (int i = 0; i < size; i++) {
				Flow_referencia_role flow_ref = (Flow_referencia_role) enBd2
						.get(i);
				if (flow_ref != null) {
					Role role = flow_ref.getRole();
					if (role != null) {
						enBd.add(role);
						item = new SelectItem(role, role.getNombre());
						seleccionados.add(item);
					}
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		boolean workFlow = true;
		if (user_logueado != null) {
			List<Role> items2 = delegado.findAll_Roles(user_logueado, workFlow);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Role role = (Role) items2.get(i);
				if (!enBd.contains(role)) {
					item = new SelectItem(role, role.getNombre());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public void llenarOperacionesVisiblesRichFaces(List seleccionados,
			List noSeleccionados, Tree tree, Usuario usuario) {

		int size = 0;
		List<Operaciones> enBd = new ArrayList();

		if (tree != null && usuario != null) {
			enBd = delegado.buscarTodosLasOperacionesTreeUsuario(tree, usuario);
			size = enBd.size();
			// da error
			// SelectItem item=null;
			String operacion = "";
			for (int i = 0; i < size; i++) {
				Operaciones operaciones = (Operaciones) enBd.get(i);
				operacion = "";
				if (operaciones != null) {
					if (Utilidades.getPertence_Arbol()
							- operaciones.getPertenece_Arbol() == 0) {

						// item = new SelectItem(operaciones, "<Tree>"
						// + messages.getString(operaciones
						// .getOperacion()));
						operacion = "(Tree)"
								+ messages
										.getString(operaciones.getOperacion());

					}
					if (Utilidades.getPertence_Menu()
							- operaciones.getPertenece_Arbol() == 0) {
						// item = new SelectItem(operaciones, "<Menu>"
						// + messages.getString(operaciones
						// .getOperacion()));

						operacion = "(Menu)"
								+ messages
										.getString(operaciones.getOperacion());

					}
					if (Utilidades.getPertence_Arbol_and_Menu()
							- operaciones.getPertenece_Arbol() == 0) {
						// item = new SelectItem(operaciones, "<Tree>"
						// + messages.getString(operaciones
						// .getOperacion()));

						operacion = "(Tree)"
								+ messages
										.getString(operaciones.getOperacion());

					}
					if (swSuperUsuario
							&& operaciones.getOperacion().equals("toAddRaiz")) {
						operaciones.setOperacion(operacion);

						seleccionados.add(operaciones);
					} else if (!operaciones.getOperacion().equals("toAddRaiz")) {
						operaciones.setOperacion(operacion);
						seleccionados.add(operaciones);
					}
				}
			}
		}
		List items2 = delegado.findAll_operacionesArbol(tree);

		size = items2.size();
		String operacion = "";
		for (int i = 0; i < size; i++) {
			Operaciones operaciones = (Operaciones) items2.get(i);
			if (!enBd.contains(operaciones)) {
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

	public void llenarOperacionesVisibles(List seleccionados,
			List noSeleccionados, Tree tree, Usuario usuario) {
		SelectItem item = new SelectItem();
		int size = 0;
		List<Operaciones> enBd = new ArrayList();

		if (tree != null && usuario != null) {
			enBd = delegado.buscarTodosLasOperacionesTreeUsuario(tree, usuario);
			size = enBd.size();
			// da error
			// SelectItem item=null;
			item = null;
			for (int i = 0; i < size; i++) {
				Operaciones operaciones = (Operaciones) enBd.get(i);
				if (operaciones != null) {
					item = new SelectItem(operaciones,
							messages.getString(operaciones.getOperacion()));

					seleccionados.add(item);
				}
			}
		}
		List items2 = delegado.findAll_operacionesArbol(tree);

		size = items2.size();
		item = null;
		for (int i = 0; i < size; i++) {
			Operaciones operaciones = (Operaciones) items2.get(i);
			if (!enBd.contains(operaciones)) {

				item = new SelectItem(operaciones,
						messages.getString(operaciones.getOperacion()));
				noSeleccionados.add(item);
			}
		}

	}

	public void llenarUsuariosInOperacionesVisibles(List seleccionados,
			List noSeleccionados, Tree tree) {
		SelectItem item = new SelectItem();
		int size = 0;
		List enBd = new ArrayList();
		List enBd1 = new ArrayList();
		Map soloUno = new HashMap();

		if (tree != null) {
			enBd1 = delegado.llenarUsuariosInOperacionesVisibles(tree);
			size = enBd1.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Seguridad_User_Lineal usuario_seg = (Seguridad_User_Lineal) enBd1
						.get(i);
				// enBd.add(usuario.getUsuario());
				if (usuario_seg != null) {
					if (!soloUno.containsKey(usuario_seg.getUsuario())) {
						item = new SelectItem(usuario_seg.getUsuario(),
								usuario_seg.getUsuario().toString());
						seleccionados.add(item);
						enBd.add(usuario_seg.getUsuario());
						soloUno.put(usuario_seg.getUsuario(),
								usuario_seg.getUsuario());
					}

				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List<Usuario> items2 = delegado.findAll_Usuario(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Usuario usu = (Usuario) items2.get(i);
				if (!enBd.contains(usu)) {
					item = new SelectItem(usu, usu.toString());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public void findRoles_PorTreeRichFaces(List seleccionados,
			List noSeleccionados, Tree tree) {

		int size = 0;
		List<Seguridad_Role_Lineal> enBd1 = new ArrayList<Seguridad_Role_Lineal>();
		List<Role> enBd = new ArrayList();

		if (tree != null) {
			enBd1 = delegado.buscarTodosLosRolesTree(tree);
			size = enBd1.size();

			for (int i = 0; i < size; i++) {
				Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) enBd1
						.get(i);
				if (seguridad_Role_Lineal.getRole() != null) {
					enBd.add(seguridad_Role_Lineal.getRole());
					seleccionados.add(seguridad_Role_Lineal.getRole());
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List items2 = delegado.findAll_Roles(user_logueado);
			size = items2.size();
			for (int i = 0; i < size; i++) {
				Role role = (Role) items2.get(i);
				if (!enBd.contains(role)) {
					noSeleccionados.add(role);
				}
			}
		}
	}

	public void llenarRoledVisiblesenNodo(List seleccionados,
			List noSeleccionados, Tree tree) {

		SelectItem item = new SelectItem();
		int size = 0;
		List<Seguridad_Role_Lineal> enBd1 = new ArrayList<Seguridad_Role_Lineal>();
		List<Role> enBd = new ArrayList();

		if (tree != null) {
			enBd1 = delegado.buscarTodosLosRolesTree(tree);
			size = enBd1.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) enBd1
						.get(i);
				if (seguridad_Role_Lineal.getRole() != null) {
					enBd.add(seguridad_Role_Lineal.getRole());
					item = new SelectItem(seguridad_Role_Lineal.getRole(),
							seguridad_Role_Lineal.getRole().getNombre());
					seleccionados.add(item);
				}
			}
		}
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			List items2 = delegado.findAll_Roles(user_logueado);
			size = items2.size();
			item = null;
			for (int i = 0; i < size; i++) {
				Role role = (Role) items2.get(i);
				if (!enBd.contains(role)) {
					item = new SelectItem(role, role.getNombre());
					noSeleccionados.add(item);
				}
			}
		}

	}

	public List<SelectItem> getAllDocumentos() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		try {
			List<Tree> nodoDocs = delegado.findAll_DeQueTipo_Tree(Utilidades
					.getNodoDocumento());
			SelectItem item = null;
			for (Tree doc : nodoDocs) {
				if (doc != null) {
					item = new SelectItem(doc, doc.getNombre());
					result.add(item);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void llenarDocRelacionados(List seleccionados, List noSeleccionados,
			Doc_maestro doc_maestro) {
		if (doc_maestro != null) {
			List enBd2 = delegado.findAll_Doc_Relacionados(doc_maestro);
			List enBd = new ArrayList();
			;
			int size = enBd2.size();
			SelectItem item = null;
			for (int i = 0; i < size; i++) {
				// agregamos todos los documentos relacionados, menos el mismo
				Doc_relacionados doc_rel = (Doc_relacionados) enBd2.get(i);
				Doc_maestro doc_ma_aux = doc_rel.getDoc_rel2();
				seguridadTree = new Seguridad();
				seguridadTree = super.getSeguridadTree(doc_ma_aux.getTree());
				if (seguridadTree.isToView() || swSuperUsuario) {
					item = new SelectItem(doc_ma_aux,
							doc_ma_aux.getConsecutivo() + "->"
									+ doc_ma_aux.getNombre());
					enBd.add(doc_ma_aux);
					seleccionados.add(item);
				}
				// }

			}
			List<Tree> items2 = delegado.findAll_DeQueTipo_Tree(Utilidades
					.getNodoDocumento());
			size = items2.size();
			item = null;

			for (int i = 0; i < size; i++) {
				Tree doc = (Tree) items2.get(i);
				List lstTraeUnoSolo = delegado.findAllDoc_maestros(doc);
				if (!lstTraeUnoSolo.isEmpty()) {
					Doc_maestro doc_m = (Doc_maestro) lstTraeUnoSolo.get(0);
					if (!enBd.contains(doc_m)) {
						if (!doc_m.equals(doc_maestro)) {
							seguridadTree = new Seguridad();
							seguridadTree = super.getSeguridadTree(doc_m
									.getTree());
							if (seguridadTree.isToView() || swSuperUsuario) {
								item = new SelectItem(doc_m,
										doc_m.getConsecutivo() + "->"
												+ doc_m.getNombre());
								noSeleccionados.add(item);
							}

						}
					}
				}

			}

		}
	}
	
	
	public void llenarDocRelacionadosAjaxRichFaces(List seleccionados,
			List noSeleccionados, Doc_maestro doc_maestro,
			Doc_detalle doc_detalle) {

		if (doc_maestro != null) {
			List<Doc_relacionados> enBd2 = delegado
					.findAll_Doc_Relacionados(doc_maestro);
			List enBd = new ArrayList();

			int size = enBd2.size();
			Map unico = new HashMap();
			for (Doc_relacionados doc_rel : enBd2) {

			 
				// agregamos todos los documentos relacionados, menos el mismo
				// Doc_relacionados doc_rel = (Doc_relacionados) enBd2.get(i);
				// emncontreamois l doc que esta al lado como relacionado y
				// preguntamos su seguridad
				Doc_maestro doc_ma_aux = doc_rel.getDoc_rel2();
				seguridadTree = new Seguridad();
				seguridadTree = super.getSeguridadTree(
						super.getUser_logueado(), doc_ma_aux.getTree());
				if ((seguridadTree.isToView() )
						|| swSuperUsuario) {

//				if ((seguridadTree.isToView() && seguridadTree.isToVincDoc())
//						|| swSuperUsuario) {
					if (!unico.containsKey(doc_ma_aux.getCodigo())) {
						unico.put(doc_ma_aux.getCodigo(),
								doc_ma_aux.getCodigo());
						
						enBd.add(doc_ma_aux);
						seleccionados.add(doc_ma_aux);
					}
				}

			}
			// me trae solo los documentos que coincidan en su buesqueda
			unico = new HashMap();
			List<Doc_maestro> items2 = delegado
					.findAllDoc_maestrosNoRelacionados(doc_maestro.getTree(),
							doc_detalle);
			size = items2.size();
			 
			for (Doc_maestro doc_m : items2) {
				if (!enBd.contains(doc_m)) {
					// no se puede relacionar con el mismo anfitrion
					if (!doc_m.equals(doc_maestro)) {
						seguridadTree = new Seguridad();

						seguridadTree = super.getSeguridadTree(
								super.getUser_logueado(), doc_m.getTree());

//						if ((seguridadTree.isToView() && seguridadTree
//								.isToVincDoc()) || swSuperUsuario) {
						if ((seguridadTree.isToView()) || swSuperUsuario) {

							if (!unico.containsKey(doc_m.getCodigo())) {
								unico.put(doc_m.getCodigo(), doc_m.getCodigo());
								
								noSeleccionados.add(doc_m);
							}
						}

					}
				}
			}

		}
	}

	public void llenarDocRelacionadosAjax(List seleccionados,
			List noSeleccionados, Doc_maestro doc_maestro,
			Doc_detalle doc_detalle) {

		if (doc_maestro != null) {
			List<Doc_relacionados> enBd2 = delegado
					.findAll_Doc_Relacionados(doc_maestro);
			List enBd = new ArrayList();

			int size = enBd2.size();
			SelectItem item = null;
			Map unico = new HashMap();
			for (Doc_relacionados doc_rel : enBd2) {

				// for (int i = 0; i < size; i++) {
				// agregamos todos los documentos relacionados, menos el mismo
				// Doc_relacionados doc_rel = (Doc_relacionados) enBd2.get(i);
				// emncontreamois l doc que esta al lado como relacionado y
				// preguntamos su seguridad
				Doc_maestro doc_ma_aux = doc_rel.getDoc_rel2();
				seguridadTree = new Seguridad();
				seguridadTree = super.getSeguridadTree(
						super.getUser_logueado(), doc_ma_aux.getTree());
				if ((seguridadTree.isToView() )
						|| swSuperUsuario) {
//				if ((seguridadTree.isToView() && seguridadTree.isToVincDoc())
//						|| swSuperUsuario) {
					if (!unico.containsKey(doc_ma_aux.getCodigo())) {
						unico.put(doc_ma_aux.getCodigo(),
								doc_ma_aux.getCodigo());
						item = new SelectItem(doc_ma_aux,
								doc_ma_aux.getConsecutivo() + "->"
										+ doc_ma_aux.getNombre());
						enBd.add(doc_ma_aux);
						seleccionados.add(item);
					}
				}

			}
			// me trae solo los documentos que coincidan en su buesqueda
			unico = new HashMap();
			List<Doc_maestro> items2 = delegado
					.findAllDoc_maestrosNoRelacionados(doc_maestro.getTree(),
							doc_detalle);
			size = items2.size();
			item = null;
			for (Doc_maestro doc_m : items2) {
				if (!enBd.contains(doc_m)) {
					// no se puede relacionar con el mismo anfitrion
					if (!doc_m.equals(doc_maestro)) {
						seguridadTree = new Seguridad();

						seguridadTree = super.getSeguridadTree(
								super.getUser_logueado(), doc_m.getTree());

						if ((seguridadTree.isToView() ) || swSuperUsuario) {
//						if ((seguridadTree.isToView() && seguridadTree
//								.isToVincDoc()) || swSuperUsuario) {
							if (!unico.containsKey(doc_m.getCodigo())) {
								unico.put(doc_m.getCodigo(), doc_m.getCodigo());
								item = new SelectItem(doc_m,
										doc_m.getConsecutivo() + "->"
												+ doc_m.getNombre());
								noSeleccionados.add(item);
							}
						}

					}
				}
			}

		}
	}

	public boolean isSwSuperUsuario() {
		return swSuperUsuario;
	}

	public void setSwSuperUsuario(boolean swSuperUsuario) {
		this.swSuperUsuario = swSuperUsuario;
	}

	public List getLenguajes() {
		LLenarLenguajes();
		return lenguajes;
	}

	public void setLenguajes(List lenguajes) {
		this.lenguajes = lenguajes;
	}

	public void LLenarLenguajes() {
		/*
		 * currentLocale = new Locale("en", "US"); // currentLocale = new
		 * Locale("es", "VE");
		 * 
		 * messages = ResourceBundle.getBundle("MessagesBundle",currentLocale);
		 */
		Locale[] locales = Locale.getAvailableLocales();
		lenguajes = new ArrayList();
		country = new HashMap();
		// colocamos al pais por default del idioma configurado en el browser
		Locale locale = Locale.getDefault();
		pais = locale.getCountry();
		// System.out.println("pais=" + pais.toString());
		for (int i = 0; i < locales.length; i++) {

			// Get the 2-letter language code
			String language = locales[i].getLanguage();
			// Get the 2-letter country code; may be equal to ""
			String country2 = locales[i].getCountry();
			// System.out.println("country=" + country);
			// System.out.println("language=" + language);
			// Get localized name suitable for display to the user
			String locName = locales[i].getDisplayName();
			// System.out.println("locName=" + locName);
			// country.put(country2,language);
			// este va para la pantalla bienvenida

			// if
			// (locName.equals("Spanish (Venezuela)")||locName.equals("English (United States)")){
			if ((!super.isEmptyOrNull(country2))
					&& (language.equalsIgnoreCase("es") || language
							.equalsIgnoreCase("en"))) {

				if ("US".equalsIgnoreCase(country2)) {
					if ("es".equalsIgnoreCase(language)) {
						language = "en";
					}
				}
				SelectItem item = new SelectItem(country2, locName);

				country.put(country2, language);
				lenguajes.add(item);
			}
		}
		session.setAttribute("lenguaje", country);

	}

 
	public List<Flow> getParticipantesPerfiles() {
		return participantesPerfiles;
	}

	public void setParticipantesPerfiles(List<Flow> participantesPerfiles) {
		this.participantesPerfiles = participantesPerfiles;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
	}

	public boolean isSwTodosLosRoles() {
		
		List<SelectItem> result = new ArrayList<SelectItem>();
		Usuario user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;
		if (user_logueado != null) {
			swTodosLosRoles=false;
			List<Role> listas = delegado.findAll_Roles(user_logueado);
			if (listas!=null && listas.size()>0 && !listas.isEmpty()){
				swTodosLosRoles=true;
			}
		}
		
		
		return swTodosLosRoles;
	}

	public void setSwTodosLosRoles(boolean swTodosLosRoles) {
		this.swTodosLosRoles = swTodosLosRoles;
	}

}
