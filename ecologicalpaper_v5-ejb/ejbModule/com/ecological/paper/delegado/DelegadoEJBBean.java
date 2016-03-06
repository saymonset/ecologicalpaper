/*

 *
findAllDoc_maestrosCarpetas * Created on August 20, 2007, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.delegado;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.hilosBackend.MyHiloEnvioMails;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_consecutivo;
import com.ecological.paper.documentos.Doc_dataPostgres;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_relacionados;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.PublicadosUsuComent;
import com.ecological.paper.documentos.RolesOnlyViewDocPublicados;
import com.ecological.paper.documentos.ScannerDoc;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.documentos.TablaAuxiliar;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.ExceptionSubVersion;
import com.ecological.paper.ecologicaexcepciones.UsuarioMultiples;
import com.ecological.paper.ecologicaexcepciones.UsuarioNoEncontrado;
import com.ecological.paper.ecologicaexcepciones.role.RoleMultiples;
import com.ecological.paper.ecologicaexcepciones.role.RoleNoEncontrado;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowOnlyNotificacionRole;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.historico.Hist_usuarios;
import com.ecological.paper.historico.Historico;
import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.subversion.SvnExtension;
import com.ecological.paper.subversion.SvnModulo;
import com.ecological.paper.subversion.SvnNombreAplicacion;
import com.ecological.paper.subversion.SvnRutasRelativasFiles;
import com.ecological.paper.subversion.SvnTipoAmbiente;
import com.ecological.paper.subversion.SvnUpload;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.software.eujovans.clientes.Cliente_EUJ;
import com.software.eujovans.clientes.ConsecutivoEUJ;
import com.software.eujovans.clientes.Factura;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.EncryptorMD5;
import com.util.ServicioLogger;
import com.util.Utilidades;
import com.util.ValidaVencimiento;
import com.util.file.Archivo;

/**
 * 
 * @author simon
 */
@Stateless
public class DelegadoEJBBean implements
		com.ecological.paper.delegado.DelegadoEJBRemote,
		com.ecological.paper.delegado.DelegadoEJBLocal {

	// @SuppressWarnings("unused")
	// @PersistenceContext(name = "ecological_v5_PU")
	// @PersistenceContext
	@PersistenceContext(unitName = "ecological_v5_PU")
	EntityManager em;
	Locale locale = Locale.getDefault();

	ResourceBundle messagesConf = ResourceBundle.getBundle(
			"com.util.resource.ecological_conf", locale);

	ResourceBundle confmessages = ResourceBundle.getBundle(
			"com.util.resource.ecological_conf", locale);

	ResourceBundle messages = ResourceBundle.getBundle(
			"com.ecological.resource.ecologicalpaper", locale);

	private String img_logo = messagesConf.getString("img.logo");
	private String img_principal = messagesConf.getString("img.principal");
	private String img_area = messagesConf.getString("img.area");
	private String img_cargo = messagesConf.getString("img.cargo");
	private String img_proceso = messagesConf.getString("img.proceso");
	private String img_carpeta = messagesConf.getString("img.carpeta");
	private String img_doc = messagesConf.getString("img.doc");
	private String img_raiz = messagesConf.getString("img.raiz");
	private String img_userLogueado = messagesConf
			.getString("img.userlogueado");
	private Object ServicioFlows;
	private Utilidades utilidades;
	private Tree treePadreTipoHereda;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private String carpeta_compartida;
	private Configuracion conf = new Configuracion();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private boolean swFueAprobado;

	/** Creates a new instance of DelegadoEJBBean */
	public DelegadoEJBBean() {
		utilidades = new Utilidades();
	}

	// ________________________________________________________________________________________________________________________//
	// DOCUMENTOS
	// _______________________________________________________________________________________________________________________//
	public void arreglarCamposNullInTablas() {

		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Seguridad_User_Lineal as o where o.toHistFlow is null");

		Query query = em.createQuery(sql.toString());
		List<Seguridad_User_Lineal> seg_list = query.getResultList();
		for (Seguridad_User_Lineal h : seg_list) {
			h.setToHistFlow(false);
			em.merge(h);
		}

		sql = new StringBuffer();
		sql.append("select object(o) from Seguridad_User_Lineal as o where o.toHistDoc is null");
		query = em.createQuery(sql.toString());
		seg_list = query.getResultList();
		for (Seguridad_User_Lineal h : seg_list) {
			h.setToHistDoc(false);
			em.merge(h);
		}

		// PARA LOS ROLES
		sql = new StringBuffer();
		sql.append("select object(o) from Seguridad_Role_Lineal as o where o.toHistDoc is null");
		query = em.createQuery(sql.toString());

		List<Seguridad_Role_Lineal> seg_Rolelist = query.getResultList();
		for (Seguridad_Role_Lineal h : seg_Rolelist) {
			h.setToHistDoc(false);
			em.merge(h);
		}

		sql = new StringBuffer();
		sql.append("select object(o) from Seguridad_Role_Lineal as o where o.toHistFlow is null");
		query = em.createQuery(sql.toString());

		seg_Rolelist = query.getResultList();
		for (Seguridad_Role_Lineal h : seg_Rolelist) {
			h.setToHistFlow(false);
			em.merge(h);
		}

		// en la tabla role, nuevo campo donde usado para crear flujo debe ser
		// falso

		/*
		 * sql = new StringBuffer();
		 * sql.append("select object(o) from Role as o "); try{ query =
		 * em.createQuery(sql.toString()); List<Role> roles =
		 * query.getResultList(); seg_Rolelist = query.getResultList(); for
		 * (Role h : roles) { h.getCodigo(); if
		 * (h.isUsadoParaCrearFlujo()!=null){ h.setUsadoParaCrearFlujo(false);
		 * em.merge(h);
		 * 
		 * } } }catch(Exception e){ e.printStackTrace(); }
		 */

	}

	public void create(Doc_detalle doc) throws EcologicaExcepciones {
		try {
			doc.setStatus(true);
			em.persist(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Doc_detalle doc) throws EcologicaExcepciones {
		try {

			em.merge(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Doc_detalle doc_detalle) throws EcologicaExcepciones {
		try {

			// eliminamos los comentarios publicos de ese detalle
			PublicadosUsuComent publicadosUsuComent = new PublicadosUsuComent();
			publicadosUsuComent.setDoc_detalle(doc_detalle);
			List<PublicadosUsuComent> publicadosUsuComentLst = findAllPublicadosUsuComentLst(publicadosUsuComent);
			for (PublicadosUsuComent p : publicadosUsuComentLst) {
				destroy(p);

			}

			// eliminamos rol con detalles publicados
			List<RolesOnlyViewDocPublicados> rolesOnlyViewDocPublicadosLst = findAllRolesOnlyViewDocPublicados(doc_detalle);
			for (RolesOnlyViewDocPublicados r : rolesOnlyViewDocPublicadosLst) {
				destroy(r);
			}

			// eliminamos todos los historicos de doc_detalle
			List<FlowsHistorico> actualizaFlowHistoricos = findAll_FlowsHistoricoDoc_detalle(doc_detalle);
			for (FlowsHistorico fh : actualizaFlowHistoricos) {

				try {
					// aqui se borra el flujo y el detalle de historico
					destroy(fh);
				} catch (EcologicaExcepciones ex) {
					ex.printStackTrace();
				}
			}

			// eliminamos el flujo de doc_dfetalle
			List<Flow> actualizaFlows = findDetalleTreeHistoricoPersonalInFlow(doc_detalle);
			for (Flow f : actualizaFlows) {
				// Solicitudimpresion solicitudimpresion = new
				// Solicitudimpresion
				// ();
				// solicitudimpresion.setFlow(f);
				//
				// solicitudimpresion=delegado.findSolicitudimpresionByFlowDelete(solicitudimpresion);
				// delegado.destroy(ciudad);
				try {
					destroy(f);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// BORRAMOS LA DATA VIEJA
			Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
			doc_dataPostgres = findDoc_dataPostgres(doc_detalle);
			if (doc_dataPostgres != null) {
				destroy(doc_dataPostgres);
			}

			doc_detalle = findDocDetalle(doc_detalle);
			// em.merge(doc);
			em.remove(doc_detalle);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}

	}

	public Doc_detalle find(Doc_detalle pk) {
		return (Doc_detalle) em.find(Doc_detalle.class, pk.getCodigo());
	}

	public Doc_detalle findDocDetalle(Doc_detalle pk) {
		return (Doc_detalle) em.find(Doc_detalle.class, pk.getCodigo());
	}

	public List findAllDoc_Detalles(Doc_maestro doc_maestro) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Doc_detalle as o where o.doc_maestro.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " order by o.codigo desc";

		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", doc_maestro.getCodigo());

		return query.getResultList();
	}

	public Doc_detalle findUltimolDoc_Detalles(Doc_maestro doc_maestro,
			Doc_estado doc_estado) {
		Doc_detalle detalle = null;
		String str = "select object(o) from Doc_detalle as o where o.doc_maestro.codigo=:codigo ";
		str += " and o.doc_estado.codigo=" + doc_estado.getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", doc_maestro.getCodigo());

		List<Doc_detalle> docs = query.getResultList();
		for (Doc_detalle doc_detalle : docs) {
			detalle = doc_detalle;
			return detalle;
		}
		return detalle;
	}

	public Doc_detalle findUltimolDoc_Detalles(Doc_maestro doc_maestro) {
		Doc_detalle detalle = null;
		String str = "select object(o) from Doc_detalle as o where o.doc_maestro.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", doc_maestro.getCodigo());
		List<Doc_detalle> docs = query.getResultList();
		for (Doc_detalle doc_detalle : docs) {
			detalle = doc_detalle;
			return detalle;
		}
		return detalle;
	}

	public List findAllDoc_Detalles() {
		// order by o.codigo desc
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Doc_detalle as o");
		sql.append(" and o.status=").append(Utilidades.isActivo());

		sql.append(" order by o.codigo desc");

		return em.createQuery(sql.toString()).getResultList();
	}

	// SOLO SIRVE PARA BORRADOR O VIGENTE
	// NO SIRVE PARA OBOLETOS
	public Doc_detalle findDocDetalle_TipoEdo(Doc_detalle doc_detalle) {
		Doc_detalle doc_deta = null;
		// return em.createQuery("").getResultList();
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_detalle as o ");
		str.append(" where o.doc_maestro.codigo=:codigo  ");
		str.append(" and o.doc_estado.codigo=").append(
				doc_detalle.getDoc_estado().getCodigo());
		str.append("  and o.status=").append(Utilidades.isActivo());

		str.append(" order by o.codigo desc");
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", doc_detalle.getDoc_maestro().getCodigo());
		List<Doc_detalle> lista = query.getResultList();
		boolean swVigenteYaEsta = false;
		for (Doc_detalle doc_d : lista) {
			// hacemos una conprobacion redundante
			if (!swVigenteYaEsta
					&& (doc_d.getDoc_estado().getCodigo() - doc_detalle
							.getDoc_estado().getCodigo()) == 0) {
				doc_deta = doc_d;
				swVigenteYaEsta = true;
			} else if (swVigenteYaEsta
					&& (doc_d.getDoc_estado().getCodigo() - doc_detalle
							.getDoc_estado().getCodigo()) == 0) {
				Doc_estado doc_edo = new Doc_estado();
				doc_edo.setCodigo(Utilidades.getObsoleto());
				doc_edo = findDocEstado(doc_edo);
				doc_d.setDoc_estado(doc_edo);
				try {
					edit(doc_d);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return doc_deta;
	}

	public Doc_detalle chequearForOldVersionVigente(Doc_detalle doc_detalle) {
		Doc_detalle doc_deta = null;
		// return em.createQuery("").getResultList();
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_detalle as o ");
		str.append(" where o.doc_maestro.codigo=").append(
				doc_detalle.getDoc_maestro().getCodigo());
		str.append(" and o.doc_estado.codigo=").append(Utilidades.getVigente());
		str.append(" and o.doc_checkout=").append(Utilidades.isActivo());
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by o.codigo desc");
		Query query = em.createQuery(str.toString());
		try {

			List resultList = query.getResultList();
			List<Doc_detalle> lista = resultList;
			if (lista != null && !lista.isEmpty() && lista.size() > 0) {
				Doc_detalle d = new Doc_detalle();
				d = lista.get(0);
				return d;
			}
			// for (Doc_detalle doc_d : lista) {
			// // hacemos una conprobacion redundante
			// return doc_d;
			// }
		} catch (Exception e) {
			str = new StringBuffer("select object(o) from Doc_detalle as o ");
			str.append(" where o.doc_maestro.codigo=").append(
					doc_detalle.getDoc_maestro().getCodigo());
			str.append("  and o.status=").append(Utilidades.isActivo());
			str.append(" order by o.codigo desc");
			query = em.createQuery(str.toString());
			List<Doc_detalle> lista = query.getResultList();
			if (lista != null && !lista.isEmpty() && lista.size() > 0) {
				Doc_detalle d = new Doc_detalle();
				d = lista.get(0);
				return d;
			}
		}

		return doc_deta;
	}

	public String queryDetallesVigentePublico(Doc_maestro doc) {
		StringBuffer sqlDocDetalle = new StringBuffer("");
		sqlDocDetalle
				.append("select object(o) from Doc_detalle as o where o.doc_maestro.codigo=")
				.append(doc.getCodigo());
		sqlDocDetalle.append(" and o.doc_estado.codigo=").append(
				Utilidades.getVigente());
		sqlDocDetalle.append("  and o.status=").append(Utilidades.isActivo());
		// descendente para que me traiga el ultimo que se proceso
		sqlDocDetalle.append(" order by o.codigo desc");
		return sqlDocDetalle.toString();
	}

	public String queryDoc_maestrosPublicos() {

		StringBuffer query = new StringBuffer("");
		query.append("select object(o) from Doc_maestro as o where o.publico="
				+ Utilidades.isActivo());
		query.append("  and o.status=").append(Utilidades.isActivo());
		query.append(" order by o.nombre ");
		return query.toString();
	}

	/*
	 * public Doc_relacionados find_d_r(Doc_relacionados pk){ return
	 * (Doc_relacionados) em.find(Doc_relacionados.class, pk.getCodigo());
	 * 
	 * } public void destroy(Doc_relacionados doc_r){ doc_r=find_d_r(doc_r);
	 * em.merge(doc_r); em.remove(doc_r); }
	 */

	// _________________________________DOC
	// MAESTRO__________________________________________________________
	public void create(Doc_maestro doc) throws EcologicaExcepciones {
		try {
			doc.setStatus(true);
			java.util.Date fecha = new java.util.Date();
			doc.setFechaCreadoOnly(fecha);
			em.persist(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Doc_maestro doc) throws EcologicaExcepciones {
		try {
			em.merge(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Doc_maestro doc) throws EcologicaExcepciones {
		try {
			doc = findDocMaestro(doc);
			// em.merge(doc);
			em.remove(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public List<Doc_maestro> consecutivoseRepite(Doc_maestro doc_maestro,
			Tree tree) {

		// buscamos si el consecutivo se repite
		StringBuffer query = new StringBuffer("");
		query.append("select object(o) from Doc_maestro as o  ");
		query.append(" where lower(o.consecutivo)='")
				.append(doc_maestro.getConsecutivo().toLowerCase().trim())
				.append("'");
		if (doc_maestro.getTree() != null
				&& doc_maestro.getTree().getNodopadre() != null) {
			query.append("  and o.tree.nodopadre=").append(
					doc_maestro.getTree().getNodopadre());
		} else {
			if (tree != null && tree.getNodopadre() != null) {
				doc_maestro.setTree(tree);
				query.append("  and o.tree.nodopadre=").append(
						doc_maestro.getTree().getNodopadre());
			}
		}
		query.append(" and  o.doc_tipo.codigo <> ").append(
				Utilidades.getFlujoparalelotipodoc());
		query.append("  and o.codigo<>").append(doc_maestro.getCodigo());
		query.append("  and o.status=").append(Utilidades.isActivo());
		query.append("  and o.empresa.nodo=").append(
				doc_maestro.getEmpresa().getNodo());

		List doc_maestros = queryExecute(query.toString());
		return doc_maestros;
	}

	public List<Doc_maestro> consecutivoseRepite(Doc_maestro doc_maestro) {

		// buscamos si el consecutivo se repite
		StringBuffer query = new StringBuffer("");
		query.append("select object(o) from Doc_maestro as o  ");
		query.append(" where lower(o.consecutivo)='")
				.append(doc_maestro.getConsecutivo().toLowerCase().trim())
				.append("'");
		query.append("  and o.status=").append(Utilidades.isActivo());
		query.append(" and  o.doc_tipo.codigo <> ").append(
				Utilidades.getFlujoparalelotipodoc());
		query.append("  and o.empresa.nodo=").append(
				doc_maestro.getEmpresa().getNodo());
		List doc_maestros = queryExecute(query.toString());
		return doc_maestros;
	}

	public List<Doc_detalle> encontrarDetalles(long codigo, String parametro) {
		StringBuffer sqlDocDetalle = new StringBuffer("");
		sqlDocDetalle
				.append("select object(o) from Doc_detalle as o where o.doc_maestro.codigo=")
				.append(codigo);
		// el parametroi me indica si el documento vien del link publicar
		// String
		// parametro=session.getAttribute("parametro")!=null?(String)session.getAttribute("parametro"):"";
		if ("publico".equalsIgnoreCase(parametro)) {
			sqlDocDetalle.append(" and o.doc_estado.codigo=").append(
					Utilidades.getVigente());
		}
		sqlDocDetalle.append("  and o.status=").append(Utilidades.isActivo());
		sqlDocDetalle.append(" order by o.codigo desc");
		List<Doc_detalle> detalles = queryExecute(sqlDocDetalle.toString());

		return detalles;
	}

	public Doc_maestro nombreDocseRepite(Doc_maestro doc_maestro) {

		// buscamos si el consecutivo se repite
		StringBuffer query = new StringBuffer("");
		query = new StringBuffer("");
		query.append("select object(o) from Doc_maestro as o  ");
		query.append(" where lower(o.nombre)='")
				.append(doc_maestro.getNombre().toLowerCase()).append("'");
		query.append("  and o.status=").append(Utilidades.isActivo());
		query.append(" and o.empresa.nodo=").append(
				doc_maestro.getEmpresa().getNodo());
		if (doc_maestro.getTree() != null
				&& doc_maestro.getTree().getNodopadre() != null) {
			query.append("  and o.tree.nodopadre=").append(
					doc_maestro.getTree().getNodopadre());
		}
		Doc_maestro doc = null;
		Query q = em.createQuery(query.toString());
		List<Doc_maestro> ds = q.getResultList();
		if (!ds.isEmpty()) {
			doc = ds.get(0);
		}
		return doc;
	}

	public Doc_maestro findDocMaestro(Doc_maestro pk) {
		return (Doc_maestro) em.find(Doc_maestro.class, pk.getCodigo());
	}

	public List findAllDoc_maestros(Usuario usuario, Doc_tipo doc_tipo,
			Doc_estado doc_estado) {
		StringBuffer sql = new StringBuffer("");
		StringBuffer sql2 = new StringBuffer("");
		boolean sw1 = false;

		if (Utilidades.getMalo() != usuario.getId()) {
			sql2.append("  o.usuario_creador.id=").append(usuario.getId());
			sw1 = true;
		}

		if (Utilidades.getMalo() != doc_tipo.getCodigo()) {
			if (sw1) {
				sql2.append(" and ");
			}
			sql2.append("  o.doc_tipo.codigo=").append(doc_tipo.getCodigo());
		}

		if (Utilidades.getMalo() != doc_estado.getCodigo()) {
			if (sw1) {
				sql2.append(" and ");
			}
			sql2.append("  d.doc_estado.codigo=")
					.append(doc_estado.getCodigo());
		}

		sql.append("select object(o) from Doc_maestro as o ,Doc_detalle as d");
		sql.append(" where o.codigo=d.doc_maestro.codigo  ");
		if (sw1) {
			sql.append(" and (").append(sql2.toString()).append(")");
		}
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by   lower(d.doc_estado.nombre),lower(o.doc_tipo.nombre),lower(o.usuario_creador.nombre) ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllDoc_maestros() {
		// sqlDocDetalle.append("  and o.status=").append(Utilidades.isActivo());
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_maestro as o ");
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by  lower(o.tree.nombre) ");

		return em.createQuery(str.toString()).getResultList();
	}

	public List findAllDoc_maestros(Tree tree) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_maestro as o,Doc_detalle as d ");
		str.append(" where o.codigo= d.doc_maestro.codigo and o.tree.nodo=:nodo   ");
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by  lower(o.tree.nombre)");

		Query query = em.createQuery(str.toString());
		if (tree != null) {
			query.setParameter("nodo", tree.getNodo());
		} else {
			return new ArrayList();
		}

		return query.getResultList();

	}

	public List findAllDoc_maestrosNoRelacionados(Tree tree, Doc_detalle doc) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_maestro as o,Doc_detalle as d ");
		str.append(" where o.codigo= d.doc_maestro.codigo and  ");
		str.append("   o.status=").append(Utilidades.isActivo());
		str.append("  and  ( ");
		// str.append(" d.doc_estado.codigo=").append(Utilidades.getAprobado());
		// str.append(" or  d.doc_estado.codigo=").append(Utilidades.getVigente());
		// str.append(" or  d.doc_estado.codigo=").append(Utilidades.getBorrador());
		// str.append(" or  d.doc_estado.codigo<>").append(Utilidades.getObsoleto());
		// TRAEME TODOS LOS DOC MAESTROS QUE NO SEAN OBSOLETOS
		str.append("   d.doc_estado.codigo <> ").append(
				Utilidades.getObsoleto());
		str.append(" ) ");

		if (doc.getDoc_maestro().getConsecutivo() != null) {
			str.append("  and lower(o.consecutivo) like '%")
					.append(doc.getDoc_maestro().getConsecutivo().toLowerCase()
							.trim()).append("%'");
		}

		// TODO DOCUMENTO RELACIONADOI DEBE SER VIGENTE

		if (doc.getDoc_maestro().getDoc_tipo() != null) {
			str.append("  and o.doc_tipo.codigo=").append(
					doc.getDoc_maestro().getDoc_tipo().getCodigo());
		}
		str.append(" order by  lower(o.nombre)");

		Query query = em.createQuery(str.toString());

		return query.getResultList();
	}

	public List findAllDoc_maestrosCarpetas(Tree tree, Doc_detalle doc) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_maestro as o,Doc_detalle as d ");
		str.append(" where o.codigo= d.doc_maestro.codigo and  ");
		str.append("   o.status=").append(Utilidades.isActivo());
		if (doc.getDuenio() != null) {
			str.append("  and d.duenio.id=").append(doc.getDuenio().getId());
		}

		// TODO DOCUMENTO RELACIONADOI DEBE SER VIGENTE
		// str.append("  and d.doc_estado.codigo=").append(Utilidades.getVigente());

		if (doc.getDoc_maestro().getDoc_tipo() != null) {
			str.append("  and o.doc_tipo.codigo=").append(
					doc.getDoc_maestro().getDoc_tipo().getCodigo());
		}
		str.append(" order by  lower(o.nombre)");

		Query query = em.createQuery(str.toString());

		return query.getResultList();
		// return em.createQuery("").getResultList();
	}

	public List findAllDoc_maestrosCarpetas(Tree tree) {

		// long tiempoInicio = System.currentTimeMillis();
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_maestro as o ");
		str.append(" where   o.tree.nodopadre=:nodo  ");
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append("  and o.doc_tipo.formatoTipoDoc <> ").append(
				Utilidades.getFlujoparalelotipodoc());

		if (tree.getDoc_tipo() != null) {

			str.append("  and o.doc_tipo.codigo=").append(
					tree.getDoc_tipo().getCodigo());
		}
		if (!isEmptyOrNull(tree.getStrBuscar())) {

			str.append("  and lower(o.consecutivo) like '%")
					.append(tree.getStrBuscar().toLowerCase()).append("%'");
		}
		str.append(" order by  o.codigo desc");

		Query query = em.createQuery(str.toString());
		query.setParameter("nodo", tree.getNodo());
		List lista = query.setMaxResults(
				Utilidades.getMaxregistermostrarfordocs()).getResultList();
		// long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		// System.out.println("El tiempo login  de demora es :" + totalTiempo
		// + " miliseg");
		if (lista != null && lista.isEmpty()) {
			lista = null;
		}
		return lista;
		// return em.createQuery("").getResultList();
	}

	public List findAllDoc_maestros(String str, char c) {
		String sql = "select object(o) from Doc_maestro as o ";
		boolean swStatus = false;
		if (str != null && str.length() > 0) {

			if (Utilidades.getSearchNombreDoc() == c) {
				sql += " where ";
				sql += " ( lower(o.tree.nombre) like '"
						+ str.toLowerCase().trim() + "%') ";
				// sql += " and o.status=:status ";
				swStatus = true;
			}
			if (Utilidades.getSearchConsecutivoDoc() == c) {
				sql += " where ";
				sql += " ( lower(o.consecutivo) like '"
						+ str.toLowerCase().trim() + "%') ";
				// sql += " and o.status=:status ";
				swStatus = true;
			}

		}
		sql += "  and o.status=" + Utilidades.isActivo();
		sql += " order by  lower(o.tree.nombre)";

		Query query = em.createQuery(sql.toString());
		/*
		 * if (swStatus) { query.setParameter("status", Utilidades.isActivo());
		 * }
		 */
		return query.getResultList();
	}

	public List<Tree> find(Tree tree, Doc_maestro doc) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where  o.nodopadre=")
				.append(tree.getNodopadre());
		sql.append(" and lower(o.nombre)= '")
				.append(doc.getNombre().toLowerCase()).append("'");
		sql.append("  and o.status=").append(Utilidades.isActivo());

		Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	public List<Doc_maestro> findDoc_Maestr_Doc_Tipo(Doc_tipo doc_tipo) {
		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select object(o) from Doc_maestro as o where  o.doc_tipo.codigo=")
				.append(doc_tipo.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		// System.out.println("sql="+sql.toString());
		Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	// _________________________________DOC
	// ESTADOS__________________________________________________________
	public void create(Doc_estado doc) throws EcologicaExcepciones {
		try {
			doc.setStatus(true);
			em.persist(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Doc_estado doc) throws EcologicaExcepciones {
		try {
			doc.setDescripcion(doc.getDescripcion().toString().trim());
			doc.setNombre(doc.getNombre().toString().trim());
			em.merge(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Doc_estado doc) throws EcologicaExcepciones {
		try {
			doc = findDocEstado(doc);
			// em.merge(doc);
			em.remove(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public Doc_estado findDocEstado(Doc_estado pk) {

		return (Doc_estado) em.find(Doc_estado.class, pk.getCodigo());
	}

	public List findDocEstadoName(Doc_estado estado) {
		StringBuffer sql = new StringBuffer();
		sql.append("                         select object(o) from Doc_estado as o where o.nombre=:nombre");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		query.setParameter("nombre", estado.getNombre());
		return query.getResultList();
	}

	public List findAllDoc_estados(String strBuscar) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append("( o.codigo=").append(Utilidades.getBorrador());

		str.append(" or o.codigo=").append(Utilidades.getRechazado());
		str.append(" or o.codigo=").append(Utilidades.getRevisado());
		str.append(" or o.codigo=").append(Utilidades.getCanceladoByDuenio());

		str.append(" or o.codigo=").append(Utilidades.getEnRevision())
				.append(" ");
		str.append(" or o.codigo=").append(Utilidades.getEnAprobacion())
				.append(" ");
		str.append(" or o.codigo=").append(Utilidades.getVigente()).append(")");
		if (strBuscar != null && strBuscar.length() > 0) {
			str.append("  and  lower(nombre) like '")
					.append(strBuscar.toString().toLowerCase()).append("%'");
		}
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by lower(nombre)");
		Query query = em.createQuery(str.toString());

		return query.getResultList();
	}

	public List findAllDoc_estadosInSearch() {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append(" ( o.codigo=").append(Utilidades.getBorrador());
		str.append(" or o.codigo=").append(Utilidades.getVigente());
		str.append(" or o.codigo=").append(Utilidades.getRechazado());
		str.append(" or o.codigo=").append(Utilidades.getRevisado());
		str.append(" or o.codigo=").append(Utilidades.getCanceladoByDuenio());
		str.append(" or o.codigo=").append(Utilidades.getEnAprobacion());
		str.append(" or o.codigo=").append(Utilidades.getEnRevision())
				.append(" ) ");

		str.append(" and  o.status=").append(Utilidades.isActivo());
		str.append(" order by lower(nombre)");
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List findAllDoc_estados() {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append(" ( o.codigo=").append(Utilidades.getBorrador());
		str.append(" or o.codigo=").append(Utilidades.getVigente())
				.append(" ) ");
		str.append(" and  o.status=").append(Utilidades.isActivo());
		str.append(" order by lower(nombre)");
		Query query = em.createQuery(str.toString());

		return query.getResultList();
	}

	public List findAllTipoDeFirma() {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append(" o.codigo=:aprobado");
		str.append(" or o.codigo=:revisado");
		str.append(" or o.codigo=:rechazado");
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append("  order by lower(o.nombre) ");
		Query query = em.createQuery(str.toString());
		query.setParameter("aprobado", Utilidades.getAprobado());
		query.setParameter("revisado", Utilidades.getRevisado());
		query.setParameter("rechazado", Utilidades.getRechazado());

		Doc_estado dUltimo = null;
		List<Doc_estado> listaAux = new ArrayList<Doc_estado>();
		List<Doc_estado> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			for (Doc_estado d : lista) {
				if ((d.getCodigo() - Utilidades.getRechazado()) == 0) {
					dUltimo = d;
				} else {
					listaAux.add(d);
				}
			}
			if (dUltimo != null) {
				listaAux.add(dUltimo);
			}
		}

		return listaAux;
	}

	public List findAllTipoDeFirma(Flow flow) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append(" o.codigo=:codigo");
		str.append(" or o.codigo=:rechazado");
		str.append("  order by lower(o.nombre) ");
		Long id = 0L;
		if (Utilidades.getEnAprobacion() == flow.getEstado().getCodigo()) {
			id = Utilidades.getAprobado();
		} else if (Utilidades.getEnRevision() == flow.getEstado().getCodigo()) {
			id = Utilidades.getRevisado();
		}

		Query query = em.createQuery(str.toString());
		if (id != 0L) {
			query.setParameter("codigo", id);
		} else {
			// para que no me de error
			query.setParameter("codigo", Utilidades.getRechazado());
		}

		query.setParameter("rechazado", Utilidades.getRechazado());

		Doc_estado dUltimo = null;
		List<Doc_estado> listaAux = new ArrayList<Doc_estado>();
		List<Doc_estado> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			for (Doc_estado d : lista) {
				if ((d.getCodigo() - Utilidades.getRechazado()) == 0) {
					dUltimo = d;
				} else {
					listaAux.add(d);
				}
			}
			if (dUltimo != null) {
				listaAux.add(dUltimo);
			}
		}

		return listaAux;
	}

	// se le puede rechazar al participante anterior
	public List findAllTipoDeFirmaWithParticipanteAnterior(Flow flow) {
		StringBuffer str = new StringBuffer(
				"select object(o) from Doc_estado as o where ");
		str.append(" o.codigo=:codigo");
		str.append(" or o.codigo=:rechazado");
		str.append(" or o.codigo=:devolucionfirmaflow");
		str.append(" or o.codigo=:devolucionstartflow");
		str.append("  order by lower(o.nombre) ");
		Long id = 0L;
		// si el flujo es de aprobacion-------------------
		if (Utilidades.getEnAprobacion() == flow.getEstado().getCodigo()) {
			id = Utilidades.getAprobado();
			// si el flujo es de revision-------------------
		} else if (Utilidades.getEnRevision() == flow.getEstado().getCodigo()) {
			id = Utilidades.getRevisado();
		}

		Query query = em.createQuery(str.toString());
		if (id != 0L) {
			query.setParameter("codigo", id);
		} else {
			// para que no me de error
			query.setParameter("codigo", Utilidades.getRechazado());
		}

		query.setParameter("rechazado", Utilidades.getRechazado());
		query.setParameter("devolucionfirmaflow",
				Utilidades.getDevolucionfirmaflow());
		query.setParameter("devolucionstartflow",
				Utilidades.getDevolucionstartflow());

		return query.getResultList();
	}

	// _________________________________DOC
	// TIPOS__________________________________________________________
	public void create(Doc_tipo doc) throws EcologicaExcepciones {
		try {
			doc.setStatus(true);
			em.persist(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Doc_tipo doc) throws EcologicaExcepciones {
		try {
			em.merge(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Doc_tipo doc) throws EcologicaExcepciones {
		try {
			doc = findDocTipo(doc);
			// em.merge(doc);
			em.remove(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public Doc_tipo findDocTipo(Doc_tipo pk, Usuario usuario) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Doc_tipo as o ");
		sql.append("  where o.status=").append(Utilidades.isActivo());

		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
			sql.append(" and o.formatoTipoDoc=").append(pk.getFormatoTipoDoc());
			List<Doc_tipo> lista = em.createQuery(sql.toString())
					.getResultList();
			for (Doc_tipo d : lista) {
				return d;
			}
			return null;
		}
		// si no viene el usuario con la empresa, se manda un objeto vacio
		// para quw no haga nada..
		Doc_tipo t = new Doc_tipo();
		return t;
	}

	public Doc_tipo findDocTipo(Doc_tipo pk) {
		return (Doc_tipo) em.find(Doc_tipo.class, pk.getCodigo());
	}

	public List findAllDoc_tipos(Usuario usuario, String strBuscar) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Doc_tipo as o ");
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" where lower(o.nombre) like '")
					.append(strBuscar.toLowerCase()).append("%'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
		}
		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
		}

		sql.append(" order by lower(nombre) ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public Doc_tipo findTipoDoc(Usuario usuario, Doc_tipo doc_tipo) {
		Doc_tipo doc_tipoResult = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Doc_tipo as o ");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
		}
		sql.append("  and o.formatoTipoDoc=").append(
				doc_tipo.getFormatoTipoDoc());

		// sql.append(" order by lower(nombre) ");
		List<Doc_tipo> lista = em.createQuery(sql.toString()).getResultList();
		if (lista != null && !lista.isEmpty()) {
			doc_tipoResult = lista.get(0);
		} else {
			// System.out
			// .println("Doc_tipo findTipoDoc(Usuario usuario,Doc_tipo doc_tipo)  en delegadoEJB no hay");
			// System.out.println("doc_tipo.getFormatoTipoDoc()="
			// + doc_tipo.getFormatoTipoDoc());
		}

		return doc_tipoResult;
	}

	public List<Doc_tipo> findAllDoc_tipos(Usuario usuario) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Doc_tipo as o ");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append("  and o.formatoTipoDoc <> ").append(
				Utilidades.getFlujoparalelotipodoc());
		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
		}

		// sql.append(" order by lower(nombre) ");
		List<Doc_tipo> lista = em.createQuery(sql.toString()).getResultList();
		if (lista == null || lista.isEmpty()) {
			crearDocTipoDoc(usuario);
			lista = em.createQuery(sql.toString()).getResultList();
		}

		return lista;
	}

	private void crearDocTipoDoc(Usuario usuario) {
		Doc_tipo tipo = new Doc_tipo();

		tipo.setDescripcion("formato");
		tipo.setNombre("formato");
		tipo.setFormatoTipoDoc(Utilidades.getFormatoTipoDoc());

		if (usuario != null && usuario.getEmpresa() != null) {
			tipo.setEmpresa(usuario.getEmpresa());

			try {
				create(tipo);

				tipo = new Doc_tipo();
				tipo.setDescripcion("registro");
				tipo.setNombre("registro");
				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
				try {
					tipo.setEmpresa(usuario.getEmpresa());
					create(tipo);
				} catch (EcologicaExcepciones ex) {
					// ex.printStackTrace();
				}

				tipo = new Doc_tipo();
				tipo.setDescripcion("flujoParaleloTipoDoc");
				tipo.setNombre("flujoParaleloTipoDoc");
				tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
				try {
					tipo.setEmpresa(usuario.getEmpresa());
					create(tipo);
				} catch (EcologicaExcepciones ex) {
					// ex.printStackTrace();
				}

				tipo = new Doc_tipo();

				tipo.setDescripcion("procedimientos");
				tipo.setNombre("procedimientos");

				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

				tipo.setEmpresa(usuario.getEmpresa());
				try {
					create(tipo);
				} catch (Exception e) {
					// TODO: handle exception
				}
				tipo = new Doc_tipo();

				tipo.setDescripcion("normas");
				tipo.setNombre("normas");
				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

				tipo.setEmpresa(usuario.getEmpresa());
				try {
					create(tipo);
				} catch (Exception e) {
					// TODO: handle exception
				}
				tipo = new Doc_tipo();

				tipo.setDescripcion("politicas");
				tipo.setNombre("politicas");
				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());

				tipo.setEmpresa(usuario.getEmpresa());
				try {
					create(tipo);
				} catch (Exception e) {
					// TODO: handle exception
				}

				// chequeamos siempre que halla un documento tipo registro
				tipo = new Doc_tipo();
				tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
				tipo = findDocTipo(tipo, usuario);
				if (tipo == null) {
					tipo = new Doc_tipo();
					tipo.setDescripcion("registro");
					tipo.setNombre("registro");
					tipo.setFormatoTipoDoc(Utilidades.getRegistroTipoDoc());
					try {
						tipo.setEmpresa(usuario.getEmpresa());
						create(tipo);
					} catch (EcologicaExcepciones ex) {
						// ex.printStackTrace();
					}
				}

				// chequeamos siempre que halla un documento flujo paralelo
				tipo = new Doc_tipo();
				tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
				tipo = findDocTipo(tipo, usuario);
				if (tipo == null) {
					tipo = new Doc_tipo();
					tipo.setDescripcion("flujoParaleloTipoDoc");
					tipo.setNombre("flujoParaleloTipoDoc");
					tipo.setFormatoTipoDoc(Utilidades.getFlujoparalelotipodoc());
					try {
						tipo.setEmpresa(usuario.getEmpresa());
						create(tipo);
					} catch (EcologicaExcepciones ex) {
						// ex.printStackTrace();
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	// _________________________________DOC
	// RELACIONADOS__________________________________________________________
	public void create(Doc_relacionados doc) throws EcologicaExcepciones {
		try {

			em.persist(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Doc_relacionados doc) throws EcologicaExcepciones {
		try {
			em.merge(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Doc_relacionados doc) throws EcologicaExcepciones {
		try {
			doc = findDocRel(doc);
			// em.merge(doc);
			em.remove(doc);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public Doc_relacionados findDocRel(Doc_relacionados pk) {
		return (Doc_relacionados) em.find(Doc_relacionados.class,
				pk.getCodigo());
	}

	public List findAllDoc_relacionados() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Doc_relacionados as o");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_Doc_Relacionados(Doc_maestro doc, String strBuscar) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Doc_relacionados as o ");
		sql.append(" where o.doc_rel1.codigo=").append(doc.getCodigo());
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" and lower(o.doc_rel2.nombre) like '")
					.append(strBuscar.toLowerCase()).append("%'");

		}
		sql.append(" order by lower(doc_rel2.nombre) ");
		// sql.append(" and o.doc_rel2.codigo");
		return em.createQuery(sql.toString()).getResultList();

	}

	public List findAll_Doc_Relacionados(Doc_maestro doc) {
		// en los doc relacionados, tenemos el codigoi del doc_maestro
		StringBuffer sql = new StringBuffer(
				"select object(o) from Doc_relacionados as o ");
		sql.append(" where o.doc_rel1.codigo=").append(doc.getCodigo());
		sql.append(" order by lower(doc_rel2.nombre) ");
		// sql.append(" and o.doc_rel2.codigo");
		// System.out.println("sql = " + sql.toString());
		return em.createQuery(sql.toString()).getResultList();

	}

	// ________________________________________________________________________________________________________________________://
	// FIN DOCUMENTOS
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// INICIO FLUJOS
	// __________________________________________________________________________________________________________________________//

	public void create(FlowParalelo objeto) throws EcologicaExcepciones {
		try {
			objeto.setStatus(true);
			if (isEmptyOrNull(objeto.getNombre())) {
				objeto.setNombre("vacio");
			}
			objeto.setNombre(objeto.getNombre().toLowerCase());
			em.persist(objeto);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public FlowParalelo find(FlowParalelo objeto) throws EcologicaExcepciones {
		try {
			objeto = (FlowParalelo) em.find(FlowParalelo.class,
					objeto.getCodigo());
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
		return objeto;
	}

	// PLANTILLAS DE FLUJOS PARA DOICUMENTOS TIPO PLANTILLAS
	public FlowParalelo findPlantillaDoc_DetallePlantillaInFlowParalelo(
			FlowParalelo objeto) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from FlowParalelo as o where ");
		sql.append("   o.status=").append(Utilidades.isActivo());
		sql.append("  and o.usuario.empresa.nodo=").append(
				objeto.getUsuario().getEmpresa().getNodo());
		// sql.append("  and o.doc_tipo.codigo=").append(
		// objeto.getDoc_tipo().getCodigo());
		// if (objeto.getAreaDocumentos() != null
		// && objeto.getAreaDocumentos().getCodigo() != null) {
		// sql.append("  and o.areaDocumentos.codigo=").append(
		// objeto.getAreaDocumentos().getCodigo());
		// }

		sql.append("  and o.flow.plantilla=").append(Utilidades.isActivo());
		sql.append("  order by o.codigo desc");

		// System.out.println("sql="+sql.toString());
		List<FlowParalelo> lista = em.createQuery(sql.toString())
				.getResultList();

		for (FlowParalelo fp : lista) {
			return fp;
		}
		return null;
	}

	public FlowParalelo findExistePlantillaImpresionFlowObjeto(
			FlowParalelo objeto) throws EcologicaExcepciones {
		FlowParalelo flowParalelo = null;
		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from FlowParalelo as o  ");
			sql.append("  where  ");
			sql.append(" o.status=").append(Utilidades.isActivo());
			sql.append("  and o.usuario.empresa.nodo=").append(
					objeto.getUsuario().getEmpresa().getNodo());
			sql.append("  and o.solicituImpresion=").append(
					Utilidades.isActivo());
			sql.append("  order by o.codigo desc");
			List<FlowParalelo> lista = em.createQuery(sql.toString())
					.getResultList();

			if (lista != null && lista.size() > 0) {
				flowParalelo = lista.get(0);
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}

		return flowParalelo;
	}

	public Boolean findExistePlantillaImpresionFlow(FlowParalelo objeto)
			throws EcologicaExcepciones {
		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from FlowParalelo as o  ");
			sql.append("  where  ");
			sql.append(" o.status=").append(Utilidades.isActivo());
			sql.append("  and o.usuario.empresa.nodo=").append(
					objeto.getUsuario().getEmpresa().getNodo());
			sql.append("  and o.solicituImpresion=").append(
					Utilidades.isActivo());
			sql.append("  order by o.codigo desc");
			List<FlowParalelo> lista = em.createQuery(sql.toString())
					.getResultList();

			if (lista != null && lista.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}

		return false;
	}

	public FlowParalelo findExiste(FlowParalelo objeto)
			throws EcologicaExcepciones {
		try {
			if (objeto.getFlow().isPlantilla()) {
				// si viene falso es porque la queremos eliminar logicamente y
				// no queremos hacer eta validacion
				if (objeto.isStatus()) {
					if (isEmptyOrNull(objeto.getNombre())) {
						throw new EcologicaExcepciones("Nombre vacio");
					}
					StringBuffer sql = new StringBuffer("");
					sql.append("select object(o) from FlowParalelo as o  ");
					sql.append("  where lower(o.nombre)=").append("'")
							.append(objeto.getNombre().trim().toLowerCase())
							.append("'").append(" and o.usuario.id=")
							.append(objeto.getUsuario().getId())
							.append(" and o.codigo <>")
							.append(objeto.getCodigo())
							.append(" and o.status=")
							.append(Utilidades.isActivo());
					sql.append("  and o.usuario.empresa.nodo=").append(
							objeto.getUsuario().getEmpresa().getNodo());
					sql.append("  and o.flow.plantilla=").append(
							Utilidades.isActivo());
					sql.append("  order by o.codigo desc");

					List<FlowParalelo> lista = em.createQuery(sql.toString())
							.getResultList();

					if (lista != null && lista.size() > 0) {
						throw new EcologicaExcepciones("Nombre Existe");
					}
				}
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}

		return objeto;
	}

	public void edit(FlowParalelo objeto) throws EcologicaExcepciones {
		try {
			// si existe lanzamos un error
			objeto = findExiste(objeto);
			em.merge(objeto);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void delete(FlowParalelo objeto) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Flow as o  ");
			sql.append("  where o.flowParalelo.codigo=").append(
					objeto.getCodigo());
			List<Flow> lista = em.createQuery(sql.toString()).getResultList();
			for (Flow f : lista) {
				f.setFlowParalelo(null);
				edit(f);
			}

			sql = new StringBuffer("");
			sql.append("select object(o) from FlowParalelo as o  ");
			sql.append("  where o.flow.codigo=").append(
					objeto.getFlow().getCodigo());
			List<FlowParalelo> lista2 = em.createQuery(sql.toString())
					.getResultList();
			for (FlowParalelo f : lista2) {
				em.remove(f);
			}

			objeto = (FlowParalelo) em.find(FlowParalelo.class,
					objeto.getCodigo());
			// em.merge(flow);
			if (objeto != null) {
				em.remove(objeto);
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public boolean finFlowParaleloForAlterarDocdetalle(Flow flow) {
		// chequeamos si todos los flujos paralelos fueron firmados...
		FlowParalelo flowParalelo = new FlowParalelo();
		flowParalelo = flow.getFlowParalelo();
		List<Flow> subFlujos = (List<Flow>) findByFlowParaleloAllFlows(flowParalelo);
		boolean swFinFlow = true;
		for (Flow subFlow : subFlujos) {
			// si por lo menois uno no es fin del flujo, se sale..y les dice que
			// no es fin del flujo
			swFinFlow = flowIsFinFlowParticipantes(subFlow);
			if (!swFinFlow) {
				break;
			}
		}
		// fin chequeamos si todos los flujos paralelos fueron firmados...
		return swFinFlow;
	}

	public List<SvnUpload> listUploadAttachmentSvn(Flow flow) {
		List<SvnUpload> lista = new ArrayList<SvnUpload>();
		// chequeamos si todos los flujos paralelos fueron firmados...
		FlowParalelo flowParalelo = new FlowParalelo();
		flowParalelo = flow.getFlowParalelo();
		List<Flow> subFlujos = (List<Flow>) findByFlowParaleloAllFlows(flowParalelo);
		SvnUpload svnUpload = null;
		Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;

		Map<Long, Object> unSoloPadre = new HashMap<Long, Object>();
		Map<Long, Object> unSoloParticipante = new HashMap<Long, Object>();
		boolean swSVN = false;
		for (Flow subFlow : subFlujos) {
			boolean isParticipante = true;
			// ES EL FLUJO PADRE
			if (subFlow.getFlowParalelo().getFlow().getCodigo()
					- subFlow.getCodigo() == 0
					&& flow.getFlowParalelo().getFlow().getCodigo()
							- subFlow.getFlowParalelo().getFlow().getCodigo() == 0) {
				isParticipante = false;
				try {
					if (!unSoloPadre.containsKey(subFlow.getFlowParalelo()
							.getCodigo())) {
						File file = fileAttachmentDocDetalle(subFlow
								.getFlowParalelo().getFlow().getDoc_detalle());
						svnUpload = new SvnUpload(isParticipante,
								subFlow.getCodigo(), -1, file,
								subFlow.getComentarios(), subFlow.getDuenio()
										.toString(), false,
								utilidades.obtenIconoDoc(file.getName()),
								swSVN, "", file.getName());

						unSoloPadre.put(subFlow.getFlowParalelo().getCodigo(),
								subFlow.getFlowParalelo().getCodigo());
						lista.add(svnUpload);
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// AGARRAMOS LOS DOCUMENTOS ATTACHMENT AL PARTICIPANTE..
			isParticipante = true;
			List<Flow_Participantes> participantes = findByFlowParticipantes(subFlow);
			for (Flow_Participantes p : participantes) {

				flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
				flow_ParticipantesAttachment.setFlowParticipantes(p);
				flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
				flow_ParticipantesAttachment = find_Flow_ParticipantesAttachmentSvnPorParticipante(flow_ParticipantesAttachment);
				try {
					if (flow_ParticipantesAttachment != null) {
						if (!unSoloParticipante.containsKey(p.getCodigo())) {
							File file = filetFlowAttachment(flow_ParticipantesAttachment);
							svnUpload = new SvnUpload(isParticipante, -1,
									p.getCodigo(), file, p.getComentario(), p
											.getParticipante().toString(),
									flow_ParticipantesAttachment
											.isSwSVNUpload(),
									utilidades.obtenIconoDoc(file.getName()),
									flow_ParticipantesAttachment.isSwSVN(),
									flow_ParticipantesAttachment
											.getUrlsubversionUpload(),
									file.getName());
							unSoloParticipante
									.put(p.getCodigo(), p.getCodigo());
							lista.add(svnUpload);
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// fin chequeamos si todos los flujos paralelos fueron firmados...
		return lista;
	}

	public File fileAttachmentDocDetalle(Doc_detalle doc_detalle)
			throws SQLException {
		File file = null;
		List<Configuracion> configuraciones = find_allConfiguracion();
		String carpeta_compartida = "";
		Configuracion conf = new Configuracion();
		boolean swPostgresVieneDeConfiguracion = false;
		boolean swConfiguracionHayData = false;

		configuraciones = find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

		InputStream imagenBuffer = null;
		if (doc_detalle.getData() != null
				&& doc_detalle.getData().getBinaryStream() != null) {
			imagenBuffer = doc_detalle.getData().getBinaryStream();
		}

		if (swConfiguracionHayData) {
			if (swPostgresVieneDeConfiguracion) {
				Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
				doc_dataPostgres = findDoc_dataPostgres(doc_detalle);
				if (doc_dataPostgres != null
						&& doc_dataPostgres.getData_doc_postgres() != null) {
					ByteArrayInputStream stream = new ByteArrayInputStream(
							doc_dataPostgres.getData_doc_postgres());
					imagenBuffer = (InputStream) stream;
				}

			}
		} else if ("1".equalsIgnoreCase(messagesConf.getString("bdpostgres"))) {
			Doc_dataPostgres doc_dataPostgres = new Doc_dataPostgres();
			doc_dataPostgres = findDoc_dataPostgres(doc_detalle);
			if (doc_dataPostgres != null
					&& doc_dataPostgres.getData_doc_postgres() != null) {
				ByteArrayInputStream stream = new ByteArrayInputStream(
						doc_dataPostgres.getData_doc_postgres());
				imagenBuffer = (InputStream) stream;
			}

		}
		Archivo archivo = new Archivo();
		String nombre = doc_detalle.getNameFile() != null ? doc_detalle
				.getNameFile() : "";
		String ext = getExtensionFile(nombre);
		try {
			if (imagenBuffer != null) {

				file = archivo.fileDesdeStream(imagenBuffer, nombre, ext);
			} else {
				System.out
						.println("delegadoEJB->fileAttachmentDocDetalle->imagenBuffer=NULA");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	public String getExtensionFile(String nameFile) {
		int pos = nameFile.indexOf(".");
		String extension = ".doc";
		if (pos != -1) {
			// extension = nameFile.substring(pos).toLowerCase();
			extension = nameFile.substring(nameFile.lastIndexOf(".") + 1,
					nameFile.length());
		}
		return extension;
	}

	public File filetFlowAttachment(Flow_ParticipantesAttachment doc_detalle)
			throws SQLException {
		File file = null;
		List<Configuracion> configuraciones = find_allConfiguracion();
		String carpeta_compartida = "";
		Configuracion conf = new Configuracion();
		boolean swPostgresVieneDeConfiguracion = false;
		boolean swConfiguracionHayData = false;
		InputStream imagenBuffer = null;
		if (doc_detalle.getAttachmentDoc() != null
				&& doc_detalle.getAttachmentDoc().getBinaryStream() != null) {
			imagenBuffer = doc_detalle.getAttachmentDoc().getBinaryStream();
		}

		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}
		if (swConfiguracionHayData) {
			carpeta_compartida = conf.getCarpetaCompartida().trim();
			if (swPostgresVieneDeConfiguracion) {
				ByteArrayInputStream stream = new ByteArrayInputStream(
						doc_detalle.getAttachmentDocPostgres());
				imagenBuffer = (InputStream) stream;
			}
		} else if ("1".equalsIgnoreCase(messagesConf.getString("bdpostgres"))) {
			ByteArrayInputStream stream = new ByteArrayInputStream(
					doc_detalle.getAttachmentDocPostgres());
			imagenBuffer = (InputStream) stream;
			carpeta_compartida = messagesConf.getString("carpeta_compartida");
		}
		Archivo archivo = new Archivo();
		String nombre = doc_detalle.getNameFile() != null ? doc_detalle
				.getNameFile() : "";
		String ext = getExtensionFile(nombre);
		if (isEmptyOrNull(nombre)) {
			nombre = "svn";
		}
		if (isEmptyOrNull(ext)) {
			nombre = "zip";
		}

		try {
			file = archivo.fileDesdeStream(imagenBuffer, nombre, ext);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	public void saveFileInDisk(Flow_ParticipantesAttachment doc_detalle,
			InputStream imagenBuffer, String path, String nameFile) {
		try {
			// String ruta = path + "\\" + nameFile;
			String ruta = path + "/" + nameFile;

			File fichero = new File(ruta);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/*
	 * public FlowParalelo find(FlowParalelo objeto) throws EcologicaExcepciones
	 * { StringBuffer sql = new StringBuffer();
	 * sql.append("select object(o) from FlowParalelo as o ");
	 * sql.append("  where o.status=").append(Utilidades.isActivo());
	 * sql.append(
	 * " and o.flowreferencia.codigo=").append(objeto.getFlowreferencia
	 * ().getCodigo()); if (usuario != null && usuario.getEmpresa() != null) {
	 * sql.append("  and o.empresa.nodo=").append(
	 * usuario.getEmpresa().getNodo());
	 * sql.append(" and o.formatoTipoDoc=").append(pk.getFormatoTipoDoc());
	 * List<Doc_tipo> lista = em.createQuery(sql.toString()) .getResultList();
	 * for (Doc_tipo d : lista) { return d; } return null; } // si no viene el
	 * usuario con la empresa, se manda un objeto vacio // para quw no haga
	 * nada.. Doc_tipo t = new Doc_tipo(); return t; }
	 */

	public void create(Flow flow) throws EcologicaExcepciones {
		try {
			flow.setFechaCreadoOnly(new Date());
			flow.setStatus(true);
			em.persist(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public Flow createFlow(Flow flow) throws EcologicaExcepciones {
		try {
			flow.setFechaCreadoOnly(new Date());
			flow.setStatus(true);
			em.persist(flow);
			return flow;
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Flow flow) throws EcologicaExcepciones {
		try {
			em.merge(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void borrandoSolicitudImpresion(Flow flow) {
		Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
		solicitudimpresion.setFlow(flow);

		solicitudimpresion = findSolicitudimpresionByFlowDelete(solicitudimpresion);
		if (solicitudimpresion != null) {
			List<SolicitudImpPart> solicitudImpParts = findAllsolicitudImpPartsDelete(solicitudimpresion);
			if (solicitudImpParts != null) {
				for (SolicitudImpPart sp : solicitudImpParts) {
					destroy(sp);
				}
			}
			destroy(solicitudimpresion);
		}
	}

	public void destroy(Flow flow) throws EcologicaExcepciones {

		flow = findFlow(flow);
		// borramos el paralelo del flow
		if (flow != null || flow.getFlowParalelo() != null) {

			if (flow.getFlowParalelo() != null) {
				delete(flow.getFlowParalelo());
			}

			if (flow != null) {

				StringBuffer sql = new StringBuffer("");
				sql.append("select object(o) from FlowParalelo as o  ");
				sql.append("  where o.flow.codigo=").append(flow.getCodigo());

				List<FlowParalelo> lista2 = em.createQuery(sql.toString())
						.getResultList();
				for (FlowParalelo f : lista2) {
					em.remove(f);
				}
			}

		}

		// BORRAMOS PRIMERO EL CONTROL DE TIEMPÃ’ DE LOS
		// FLUJOS
		List<FlowControlByUsuarioBean> controlTiempo = find_allControlTimeByFlowParticipAndRole(flow);
		if (controlTiempo != null && !controlTiempo.isEmpty()) {
			for (FlowControlByUsuarioBean borrar : controlTiempo) {
				destroy(borrar);
			}
		}

		// REFERENCIA ROLES
		List<Flow_referencia_role> f_r_rs = findAllFlow_delete_role_ByFlow(flow);
		Role role = new Role();
		for (Flow_referencia_role f_r_r : f_r_rs) {
			destroy(f_r_r);
		}

		List<Flow_Participantes> f_ps = findByDeleteFlowParticipantes(flow);
		for (Flow_Participantes f_p : f_ps) {
			// BORRAMOS EL FLOW DE PARTICIPANTES
			destroy(f_p);
		}

		try {
			// ESTO LO USAMOS PARA BORRAR PARTICIPANTES DEL FLOW
			List<Flow_Participantes> flows_participantes = findByFlowParticipantes(flow);
			for (Flow_Participantes f_p : flows_participantes) {
				try {
					destroy(f_p);
				} catch (Exception exception) {
					continue;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// REFERENCIA DEL FLOW

		try {
			List<Flow_referencia_role> flows_roles = findByFlow_referencia_role(flow);
			if (flows_roles != null) {
				for (Flow_referencia_role f_r : flows_roles) {
					try {
						destroy(f_r);
					} catch (Exception exception) {
						continue;
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}

		// HISTORICOS DEL FLOW

		List<FlowsHistorico> flowsHistoricos = findAll_FlowsHistoricoFlow(flow);
		for (FlowsHistorico f_h : flowsHistoricos) {
			destroy(f_h);
		}

		// SOLICITUD DE IMPRESION
		borrandoSolicitudImpresion(flow);
		flow = findFlow(flow);
		// em.merge(flow);

		// REMOVEMOS EL FLOW COMO TAL
		em.remove(flow);

	}

	public Flow findFlow(Flow pk) {
		return (Flow) em.find(Flow.class, pk.getCodigo());
	}

	public List findAllFlowParalelos(Flow flow) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from FlowParalelo as o  ");
		sql.append("  where o.flow.codigo=").append(flow.getCodigo());
		List<Flow> lista = em.createQuery(sql.toString()).getResultList();
		return lista;
	}

	public List findAllFlowParalelos(FlowParalelo flowParalelo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow as o  ");
		// LOS FLUJOS CREADOS PARA CREAR FLUJOS PARALELOS SIEMPRE SERAN
		// INACTIVOS

		sql.append("  where o.flowParalelo.codigo=").append(
				flowParalelo.getCodigo());
		sql.append(" and ( ");
		sql.append("   o.estado.codigo=").append(Utilidades.getEnAprobacion());
		sql.append(" or  o.estado.codigo=").append(Utilidades.getEnRevision());
		sql.append(" ) ");

		sql.append(" order by o.nombredelflujo ");
		List<Flow> lista = em.createQuery(sql.toString()).getResultList();
		return lista;
	}

	public List findAllFlowParalelosDelSistema(FlowParalelo flowParalelo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow as o  ");
		// LOS FLUJOS CREADOS PARA CREAR FLUJOS PARALELOS SIEMPRE SERAN
		// INACTIVOS

		sql.append("  where o.flowParalelo.codigo=").append(
				flowParalelo.getCodigo());
		sql.append(" and ( ");
		sql.append("   o.estado.codigo=").append(Utilidades.getEnAprobacion());
		sql.append(" or  o.estado.codigo=").append(Utilidades.getEnRevision());
		sql.append(" ) ");

		sql.append(" order by codigo asc ");
		List<Flow> lista = em.createQuery(sql.toString()).getResultList();
		return lista;
	}

	public List findParalelos(Usuario usuario) {
		List<FlowParalelo> listaTotal = new ArrayList<FlowParalelo>();
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from FlowParalelo as o  ");
		sql.append("  where  o.usuario.empresa.nodo=").append(
				usuario.getEmpresa().getNodo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" and  ");
		sql.append("           o.usuario.id=").append(usuario.getId());
		sql.append(" and o.doc_tipo is  null  ");
		sql.append("           and o.flow.plantilla=")
				.append(Utilidades.isActivo()).append("    ");
		// sql.append(" and o.flow.origen=" +
		// Utilidades.getOrigenDocumentoFlow());
		if (!isEmptyOrNull(usuario.getStrBuscar())) {
			sql.append(
					" and lower(o.nombre) like '%"
							+ usuario.getStrBuscar().toLowerCase())
					.append("%'");
		}
		sql.append(" order by nombre ");

		List<FlowParalelo> lista1 = em.createQuery(sql.toString())
				.getResultList();
		if (lista1 != null && lista1.size() > 0) {
			listaTotal.addAll(lista1);
		}

		// SI EL TIPO DOCUMENTO ES TIPO PLANTRILLA BUSCAMOS LAS PLANTILLA SQUE
		// ESTEN EN EL FLOWPARALELO
		if (usuario != null && usuario.getDoc_detall() != null) {
			// if (Utilidades.getFormatoTipoDoc()
			// - usuario.getDoc_detall().getDoc_maestro().getDoc_tipo()
			// .getFormatoTipoDoc() == 0) {
			sql = new StringBuffer("");
			sql.append("select object(o) from FlowParalelo as o  ");
			sql.append("  where  o.usuario.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
			sql.append("  and o.areaDocumentos.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());

			sql.append("  and o.doc_tipo.status=")
					.append(Utilidades.isActivo());
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append("           and o.flow.plantilla=")
					.append(Utilidades.isActivo())
					.append(" and o.doc_tipo is not null  ");
			sql.append(" and o.areaDocumentos is not null  ");
			// sql.append(" and o.flow.origen="
			// + Utilidades.getOrigenDocumentoFlow());
			if (usuario.getDoc_detall().getDoc_maestro() != null
					&& usuario.getDoc_detall().getDoc_maestro().getDoc_tipo() != null
					&& usuario.getDoc_detall().getDoc_maestro().getDoc_tipo()
							.getNombre() != null) {
				sql.append(" and lower(o.doc_tipo.nombre) = '")
						.append(usuario.getDoc_detall().getDoc_maestro()
								.getDoc_tipo().getNombre().toLowerCase().trim())
						.append("'  ");
			}

			if (!isEmptyOrNull(usuario.getStrBuscar())) {
				sql.append(
						" and lower(o.nombre) like '%"
								+ usuario.getStrBuscar().toLowerCase()).append(
						"%'");
			}

			sql.append(" order by o.nombre ");

			List<FlowParalelo> lista2 = null;
			List<FlowParalelo> lista2_2 = new ArrayList<FlowParalelo>();
			try {

				lista2 = em.createQuery(sql.toString()).getResultList();
				for (FlowParalelo fp : lista2) {
					if (fp.getAreaDocumentos() != null
							&& usuario.getDoc_detall().getAreaDocumentos() != null) {
						if (fp.getAreaDocumentos().getCodigo()
								- usuario.getDoc_detall().getAreaDocumentos()
										.getCodigo() == 0) {
							lista2_2.add(fp);

						}
					}

				}
			} catch (Exception e) {

				e.printStackTrace();
				System.out.println("-----ERROR CONTROLADO-----------");
				sql = new StringBuffer("");
				sql.append("select object(o) from FlowParalelo as o  ");
				sql.append("  where  o.usuario.empresa.nodo=").append(
						usuario.getEmpresa().getNodo());
				sql.append("  and o.doc_tipo.status=").append(
						Utilidades.isActivo());
				sql.append("  and o.status=").append(Utilidades.isActivo());
				sql.append("           and o.flow.plantilla=")
						.append(Utilidades.isActivo())
						.append(" and o.doc_tipo is not null  ");
				sql.append(" and o.areaDocumentos is not null  ");

				if (usuario.getDoc_detall().getDoc_maestro() != null
						&& usuario.getDoc_detall().getDoc_maestro()
								.getDoc_tipo() != null
						&& usuario.getDoc_detall().getDoc_maestro()
								.getDoc_tipo().getNombre() != null) {
					sql.append(" and lower(o.doc_tipo.nombre)='")
							.append(usuario.getDoc_detall().getDoc_maestro()
									.getDoc_tipo().getNombre().toLowerCase()
									.trim()).append("'  ");
				}
				lista2 = em.createQuery(sql.toString()).getResultList();
			}

			if (lista2_2 != null && lista2_2.size() > 0) {
				listaTotal.addAll(lista2_2);
			}
			// }
		}

		if (listaTotal == null) {
			listaTotal = new ArrayList<FlowParalelo>();
		}

		return listaTotal;
	}

	public List calculandoPrecendenciaFlowParalelos(FlowParalelo flowParalelo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow as o  ");
		// siempre para flujos paralelos.. siempre seran inactivos..
		// sql.append("  where o.status=").append(Utilidades.isInactivo());
		sql.append("  where o.codigo < ").append(
				flowParalelo.getFlow().getCodigo());
		sql.append("  and o.flowParalelo.codigo=").append(
				flowParalelo.getCodigo());
		sql.append(" order by o.fecha_creado ");

		// System.out.println("sql.toString()=" + sql.toString());
		List<Flow> lista = em.createQuery(sql.toString()).getResultList();
		return lista;
	}

	public List findAllFlow() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow as o  ");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.fecha_creado ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public void findFlowBorrarObsoleto(Usuario duenio) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());
		String str = "select object(o) from Flow as o ";
		str += " where  ";
		str += " ( o.estado.codigo=:borrador)";
		str += " and o.duenio.id=:id";
		str += " and  o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("id", duenio.getId());
		List<Flow> lst = query.getResultList();
		for (Flow flow : lst) {

			// eliminamois primeroi el flujo de control de tiempo
			borrarFlowControlTime(flow);

			try {
				List<Flow_Participantes> flows_participantes = findByFlowParticipantes(flow);
				for (Flow_Participantes f_p : flows_participantes) {
					try {
						destroy(f_p);
					} catch (Exception exception) {
						continue;
					}
				}
				List<Flow_referencia_role> flows_roles = findByFlow_referencia_role(flow);
				for (Flow_referencia_role f_r : flows_roles) {
					try {
						destroy(f_r);
					} catch (Exception exception) {
						continue;
					}
				}
				destroy(flow);
			} catch (EcologicaExcepciones ex) {
				continue;
			}
		}
	}

	// verifica si el flujo se hizo mal
	public List findByFlowBad(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow as o ";
		str += " where  o.doc_detalle.codigo=:docCodigo and ";
		str += " (o.estado.codigo=:borrador or o.estado.codigo=:enrevision or o.estado.codigo=:enaprobacion)";
		str += " and o.tipoFlujo is null ";
		str += " and o.origen=" + Utilidades.getOrigenDocumentoFlow();
		str += " and  o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());
		List lst = query.getResultList();
		;
		return lst;
	}

	public List findByFlow(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow as o,FlowParalelo as fp ";
		str += " where  o.flowParalelo.codigo=fp.codigo and o.flowParalelo.flow.doc_detalle.doc_maestro.codigo=:docCodigo and ";
		str += " (o.estado.codigo=:borrador or o.estado.codigo=:enrevision or o.estado.codigo=:enaprobacion)";
		str += " and  o.status=" + Utilidades.isActivo();
		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and  o.origen=" + doc.getOrigen();
		}
		str += " order by o.codigo desc ";
		// str += " and o.tipoFlujo is not null ";
		Query query = em.createQuery(str.toString());

		query.setParameter("docCodigo", doc.getDoc_maestro().getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());
		List lst = query.getResultList();

		return lst;
	}

	public List findByFlowByOrigen(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		// se coloca borrador, pero recuerda que estas buscando por las tablas
		// de flow.. por lo tanto aunque sea
		// un borrador esta en flows
		String str = "select object(o) from Flow as o,FlowParalelo as fp ";
		str += " where  o.flowParalelo.codigo=fp.codigo and o.flowParalelo.flow.doc_detalle.doc_maestro.codigo=:docCodigo and ";
		str += " (o.estado.codigo=:borrador or o.estado.codigo=:enrevision or o.estado.codigo=:enaprobacion)";
		str += " and  o.status=" + Utilidades.isActivo();
		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and  o.origen=" + doc.getOrigen();
		}

		str += " order by o.codigo desc ";
		// str += " and o.tipoFlujo is not null ";
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getDoc_maestro().getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());
		List lst = query.getResultList();

		return lst;
	}

	public List findByFlowInFlow(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow as o,FlowParalelo as fp ";
		str += " where  o.flowParalelo.codigo=fp.codigo and o.flowParalelo.flow.doc_detalle.codigo=:docCodigo and ";
		str += " ( o.estado.codigo=:enrevision or o.estado.codigo=:enaprobacion)";
		str += " and  o.status=" + Utilidades.isActivo();

		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and o.origen=" + doc.getOrigen();
		}

		str += " order by o.codigo desc ";
		// str += " and o.tipoFlujo is not null ";
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());

		List lst = query.getResultList();

		return lst;
	}

	public List findByFlowDocDetalle(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow as o ";
		str += " where  o.doc_detalle.codigo=:docCodigo ";
		// str += " and  o.status=" + Utilidades.isActivo();
		// str += " and o.tipoFlujo is not null ";
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		List lst = query.getResultList();
		// System.out.println("n str = " + str.toString());
		// System.out.println("doc.getCodigo() = " + doc.getCodigo());
		return lst;
	}

	public List findHistoricooDetalleInFlow(Flow flow) {
		String str = "select object(o) from Flow as o ";
		str += " where  o.codigo=:codigo ";
		str += " and  o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		List lst = query.getResultList();
		return lst;
	}

	public List findDetalleTreeHistoricoPersonalInFlow(Doc_detalle doc) {

		String str = "select object(o) from Flow as o ";
		str += " where  o.doc_detalle.codigo=:docCodigo  ";
		str += " and  o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());

		List lst = query.getResultList();

		return lst;
	}

	public List findDocumentoDetalleInFlow(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow as o ";
		str += " where  o.doc_detalle.codigo=:docCodigo  ";
		str += " and (o.estado.codigo=:borrador or o.estado.codigo=:enrevision or o.estado.codigo=:enaprobacion)";
		str += " and  o.status=" + Utilidades.isActivo();
		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and  o.origen=" + doc.getOrigen();
		}

		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());

		List lst = query.getResultList();

		return lst;
	}

	// _________________________________FLOW
	// PARTICIPANTES__________________________________________________________
	public void create(Flow_Participantes flow) throws EcologicaExcepciones {
		try {
			if (!hayFlow_ParticipantesByRole(flow)) {
				flow.setActivo(Utilidades.isActivo());
				em.persist(flow);
			}
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Flow_Participantes flow) throws EcologicaExcepciones {
		try {

			em.merge(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString() + e);
		}
	}

	public void destroy(Flow_Participantes flow) throws EcologicaExcepciones {
		try {
			flow = findFlow(flow);
			// em.merge(flow);
			em.remove(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString() + e.toString());
		}
	}

	public Flow_Participantes findFlow(Flow_Participantes pk) {
		return (Flow_Participantes) em.find(Flow_Participantes.class,
				pk.getCodigo());
	}

	public void deletellFlow_ParticipantesByRole(Role role) {
		String str = "select object(o) from Flow_Participantes as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += "  and o.role.codigo=" + role.getCodigo();
		Query query = em.createQuery(str.toString());
		List<Flow_Participantes> f_ps = query.getResultList();
		for (Flow_Participantes f_p : f_ps) {
			f_p.setRole(null);
			try {
				edit(f_p);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean hayFlow_ParticipantesByRole(Flow_Participantes fp) {
		boolean swHayFlow_ParticipantesByRole = false;
		try {
			String str = "select object(o) from Flow_Participantes as o  ";
			str += "  where o.status=" + Utilidades.isActivo();
			if (fp.getRole() != null) {
				str += "  and o.role.codigo=" + fp.getRole().getCodigo();
			}
			str += "  and o.participante.id=" + fp.getParticipante().getId();
			str += "  and o.flow.codigo=" + fp.getFlow().getCodigo();
			str += "  and o.firma.codigo=" + Utilidades.getSinFirmar();

			Query query = em.createQuery(str.toString());

			List<Flow_Participantes> f_ps = query.getResultList();
			if (f_ps != null && !f_ps.isEmpty() && f_ps.size() > 0) {
				swHayFlow_ParticipantesByRole = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return swHayFlow_ParticipantesByRole;
	}

	// -----------------------------------------------------------------------
	// PARA SACAR LAS ESTADISTICAS INICIO

	public List findAllFlow_ParticipantesByUsuaurio(Usuario usuario) {
		String str = "select object(o) from Flow_Participantes as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += "  and o.participante.id=" + usuario.getId();
		str += " order by codigo desc";
		return em.createQuery(str.toString())
				.setMaxResults(Utilidades.getMaxregistermostrarall())
				.getResultList();
	}

	public List findAllFlowEnviadosByUsuario(Flow f) {
		String str = "select object(o) from Flow as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += "  and o.duenio.id=" + f.getDuenio().getId();
		str += " order by codigo desc";

		return em.createQuery(str.toString())
				.setMaxResults(Utilidades.getMaxregistermostrarall())
				.getResultList();
	}

	public List findAllFlowOfParticipantes(Flow_Participantes f_p) {
		String str = "select object(o) from Flow as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += "  and o.codigo=" + f_p.getFlow().getCodigo();
		str += " order by codigo desc";
		return em.createQuery(str.toString())
				.setMaxResults(Utilidades.getMaxregistermostrarall())
				.getResultList();
	}

	public List findAllFlowControlOfParticipantes(Flow_Participantes f_p) {
		String str = "select object(o) from FlowControlByUsuarioBean  as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += "  and o.flow_Participantes.codigo=" + f_p.getCodigo();

		return em.createQuery(str.toString()).getResultList();
	}

	// PARA SACAR LAS ESTADISTICAS FIN
	// -----------------------------------------------------------------------
	public List findAllFlow_Participantes() {
		String str = "select object(o) from Flow_Participantes as o  ";
		str += "  where o.status=" + Utilidades.isActivo();
		str += " order by o.fecha_firma";
		return em.createQuery(str.toString()).getResultList();
	}

	public List findAllFlow_delete_role_ByFlow(Flow flow) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow_referencia_role as o ");
		sql.append(" where o.flow.codigo=").append(flow.getCodigo());
		// sql.append(" and o.status=").append(Utilidades.isActivo());
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findByDeleteFlowParticipantes(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		// str +=" and o.status="+Utilidades.isActivo();
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());

		return query.getResultList();
	}

	// ME ARREGLA UN FLUJO QUE ESTA EDN REVISION O APROBACION
	// Y TODOS YA FIRMARON
	public void findFlowParticipantesFirmadosAndFlowIspendiente(Flow flow) {

		try {

			String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
			str += " and  o.firma.codigo=" + Utilidades.getSinFirmar();
			str += " and ( o.flow.estado.codigo=" + Utilidades.getAprobado();
			str += " or o.flow.estado.codigo=" + Utilidades.getRevisado() + " ";
			str += " or o.flow.estado.codigo="
					+ Utilidades.getCanceladoByDuenio() + " ";
			str += " or o.flow.estado.codigo=" + Utilidades.getRechazado()
					+ " )";
			Query query = em.createQuery(str.toString());
			query.setParameter("codigo", flow.getCodigo());
			List<Flow_Participantes> lista = query.getResultList();
			// tambien si no tiene participantes
			if (lista == null || lista.isEmpty()) {
				flow.setStatus(false);
				try {
					edit(flow);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (NumberFormatException nfe) {
		}

	}

	// ME ARREGLA UN FLUJO QUE YA FUE APROBADO,
	// RECHAZADO O REVISADO Y HAY GENTE SIN FIRMAR
	// tambien si no tiene participantes
	public void findFlowParticipantesSinFirmar(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and  o.firma.codigo=" + Utilidades.getSinFirmar();
		str += " and ( o.flow.estado.codigo=" + Utilidades.getAprobado();
		str += " or o.flow.estado.codigo=" + Utilidades.getRevisado() + " ";
		str += " or o.flow.estado.codigo=" + Utilidades.getRechazado() + " )";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());

		List<Flow_Participantes> lista = query.getResultList();

		for (Flow_Participantes fp : lista) {
			// hay fp quye estan sin firmar y el flujo es aprobado o revisado
			Doc_estado estado = new Doc_estado();
			if ((Long.parseLong(fp.getFlow().getTipoFlujo()) == Utilidades
					.getEnAprobacion())) {
				estado.setCodigo(Utilidades.getEnAprobacion());
			}
			if ((Long.parseLong(fp.getFlow().getTipoFlujo()) == Utilidades
					.getEnRevision())) {
				estado.setCodigo(Utilidades.getEnRevision());
			}
			estado = findDocEstado(estado);
			fp.getFlow().setEstado(estado);

			try {
				edit(fp.getFlow());
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		// Integer.parseInt(flow.getCodigo() + "");
		str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		// str += " and  o.firma.codigo = " + Utilidades.getSinFirmar();
		query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		lista = query.getResultList();
		// tambien si no tiene participantes
		if (lista == null || lista.isEmpty() || lista.size() == 0) {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Flow as o where o.codigo=")
					.append(flow.getCodigo());
			sql.append(" and ");
			sql.append(" ( o.tipoFlujo ='")
					.append(Utilidades.getEnAprobacion()).append("'");
			sql.append(" or o.tipoFlujo = '")
					.append(Utilidades.getEnRevision()).append("'");
			sql.append(" ) ");
			query = em.createQuery(sql.toString());
			// System.out.println("sql.toString()=" + sql.toString());
			List<Flow> listaFlow = query.getResultList();
			if (listaFlow != null && listaFlow.size() > 0) {
				flow.setStatus(false);
				try {
					edit(flow);
				} catch (EcologicaExcepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void sustituirFlowParticipantesOldByOtro(Usuario viejo, Usuario nuevo) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.participante.id=:id";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.firma.codigo=" + Utilidades.getSinFirmar();
		Query query = em.createQuery(str.toString());
		query.setParameter("id", viejo.getId());
		List<Flow_Participantes> lista = query.getResultList();
		for (Flow_Participantes cambio : lista) {
			cambio.setParticipante(nuevo);
			try {
				edit(cambio);
				// HISTORICO
				com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
				hist.setStatus(true);
				hist.setFecha_accion(fechaActual());
				hist.setTipo_accion(Utilidades.getUsuarioEliminado());
				hist.setFlow(cambio.getFlow());
				hist.setUsuario_anterior(viejo);
				hist.setUsuario_new(nuevo);
				create(hist);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}
	}

	public void sustituirFlowParticipantesOldByOtroFlows(
			Flow_Participantes flow_Participante, Usuario nuevo,
			String comentario) {
		// return em.createQuery("").getResultList();

		String str = "select object(o) from Flow_Participantes as o where o.participante.id=:id";
		if (flow_Participante.getTipoDeCambioParticipante()
				- Utilidades.getCambioparticipanteactual() == 0) {
			str += " and o.codigo=" + flow_Participante.getCodigo();
		}
		if (flow_Participante.getTipoDeCambioParticipante()
				- Utilidades.getCambioparticipanteinflow() == 0) {
			str += " and o.flow.flowParalelo.flow.codigo="
					+ flow_Participante.getFlow().getFlowParalelo().getFlow()
							.getCodigo();
		}
		if (flow_Participante.getTipoDeCambioParticipante()
				- Utilidades.getCambioparticipanteallflow() == 0) {
			// NO SE FILTRA NADA
		}

		str += " and o.status=" + Utilidades.isActivo();

		str += " and o.firma.codigo=" + Utilidades.getSinFirmar();
		Query query = em.createQuery(str.toString());
		query.setParameter("id", flow_Participante.getParticipante().getId());
		List<Flow_Participantes> lista = query.getResultList();
		for (Flow_Participantes cambio : lista) {
			cambio.setParticipante(nuevo);
			try {
				edit(cambio);
				/*
				 * Flow f=findFlow(cambio.getFlow());
				 * f.setComentarios(f.getComentarios()+
				 * ":"+messages.getString("toModFlow")+ " " +flow_Participante
				 * .getParticipante().toString()+"->"+messages .getString
				 * ("usuario_nuevo")+":"+nuevo.toString()+" "+comentario);
				 * edit(f);
				 */

			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}
		}
	}

	public List findByFlowParaleloPlantilla(FlowParalelo flowParalelo) {

		String str = "select object(o) from Flow as o where o.flowParalelo.codigo=:codigo ";
		str += " and o.plantilla=" + Utilidades.isActivo();
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flowParalelo.getCodigo());

		return query.getResultList();
	}

	public List activarFlowParaleloAllFlows(FlowParalelo flowParalelo) {
		Doc_estado aprobacion = new Doc_estado();
		aprobacion.setCodigo(Utilidades.getEnAprobacion());
		String str = "select object(o) from Flow as o where o.flowParalelo.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isInactivo();
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flowParalelo.getCodigo());
		// es la misma variable para ambos casos..
		if (flowParalelo.isSwAprobacion()
				|| flowParalelo.isSwNoStaInAprobacion()) {
			query.setParameter("enaprobacion", aprobacion.getCodigo());
		}
		return query.getResultList();
	}

	public List findByFlowParaleloAllFlowsForPadreFlow(FlowParalelo flowParalelo) {
		Doc_estado aprobacion = new Doc_estado();
		aprobacion.setCodigo(Utilidades.getEnAprobacion());
		String str = "select object(o) from Flow as o where o.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();

		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		// System.out.println("flowParalelo.getFlow().getCodigo()="
		// + flowParalelo.getFlow().getCodigo());
		query.setParameter("codigo", flowParalelo.getFlow().getCodigo());

		return query.getResultList();
	}

	public List findByFlowParaleloAllFlows(FlowParalelo flowParalelo) {
		Doc_estado aprobacion = new Doc_estado();
		aprobacion.setCodigo(Utilidades.getEnAprobacion());
		String str = "select object(o) from Flow as o where o.flowParalelo.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		if (flowParalelo.isSwAprobacion()) {
			str += " and o.estado.codigo=:enaprobacion ";
		}
		if (flowParalelo.isSwNoStaInAprobacion()) {
			str += " and o.estado.codigo<>:enaprobacion ";
		}

		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flowParalelo.getCodigo());

		// es la misma variable para ambos casos..
		if (flowParalelo.isSwAprobacion()
				|| flowParalelo.isSwNoStaInAprobacion()) {
			query.setParameter("enaprobacion", aprobacion.getCodigo());
		}
		return query.getResultList();
	}

	public List findByFlowParticipantes(Flow flow) {
		// return em.createQuery("").getResultList();

		Query query = null;
		if (flow != null) {

			String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
			str += " and o.status=" + Utilidades.isActivo();
			str += " order by o.codigo";
			query = em.createQuery(str.toString());
			query.setParameter("codigo", flow.getCodigo());

		}

		List lista = null;
		try {
			lista = query.getResultList();
		} catch (Exception e) {
			lista = new ArrayList();
		}

		return lista;
	}

	public List findByFlowParticipantes(Flow_Participantes flow_Participantes) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		if (flow_Participantes.getParticipante() != null) {
			str += " and o.participante.id="
					+ flow_Participantes.getParticipante().getId();
		}

		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow_Participantes.getFlow().getCodigo());

		return query.getResultList();
	}

	public List findByFlowParticipantesOrigenFlow(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.flow.origen=" + Utilidades.getOrigenDocumentoFlow();
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		return query.getResultList();
	}

	public List findByFlowParticipantesSinRolForEditar(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.role is null ";
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		return query.getResultList();
	}

	public List findByFlowParticipantesFlowIsActivo(Flow flow) {
		// return em.createQuery("").getResultList();
		// String str =
		// "select object(o) from Flow_Participantes as o where (o.flow.codigo=:codigo or o.flow.flowParalelo.flow.codigo=:codigo2) ";
		String str = "select object(o) from Flow_Participantes as o where (o.flow.codigo=:codigo ) ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.flow.status=" + Utilidades.isActivo();
		str += " order by o.codigo";
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		// query.setParameter("codigo2", flow.getCodigo());
		return query.getResultList();
	}

	public Boolean flowIsFinFlowParticipantes(Flow flow) {
		boolean finFlow = false;
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.firma.codigo=:sinFirmar";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.flow.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		query.setParameter("sinFirmar", Utilidades.getSinFirmar());
		List lista = query.getResultList();
		if (lista == null || lista.isEmpty()) {
			finFlow = true;
		}
		return finFlow;
	}

	// ME TRAE EL ULTIMO PARTICIPANTE DEL FLIJO
	public Flow_Participantes flowUltimoParticipante(Flow flow) {
		boolean finFlow = false;
		Flow_Participantes flow_Participante = null;
		String str = "select object(o) from Flow_Participantes as o,Flow as o1,FlowParalelo as fp where o.flow.codigo=o1.codigo and o1.flowParalelo.codigo=:codigo ";
		str += " and o1.flowParalelo.codigo=fp.codigo";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o1.status=" + Utilidades.isActivo();
		str += " order by o.codigo desc";
		// String str =
		// "select object(o) from Flow as o,FlowParalelo fp where o.flowParalelo.codigo=fp.codigo and fp.codigo=:codigo ";
		// str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getFlowParalelo().getCodigo());
		List<Flow_Participantes> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			for (Flow_Participantes fp : lista) {
				flow_Participante = fp;
				break;
			}
		}
		return flow_Participante;
	}

	public Flow_Participantes devolucionFirmaFlow(
			Flow_Participantes flow_Participante) {
		// LA PRIMERA LLAMADA ES PARA VALIDAR SI SE PUEDE DEVOLVER UN FLOW EN
		// DATACOMBO
		// LA SEGUNDA LLAMADA.. DEVOLVEMOS EL PARTICIPANTE A DEVOLVER
		Flow_Participantes devolver = null;
		if (flow_Participante.getFlow().isSecuencial()
		// && flow_Participante.getFlow().isCondicional()
		) {
			List<Flow_Participantes> listaAuxParticipantes = findByFlowParticipantes(flow_Participante
					.getFlow());
			int i = 0;
			List<Flow_Participantes> lista = new ArrayList<Flow_Participantes>();
			for (Flow_Participantes g : listaAuxParticipantes) {
				lista.add(i, g);
				if (g.getCodigo() - flow_Participante.getCodigo() == 0) {
					// nos vamos a la lista del anterior
					i--;
					if (i != -1) {
						devolver = lista.get(i);
						// aqui devolvemos cualquier lista.. lo importante es
						// deir que se puede retroceder
						return devolver;
					} else {
						// verificamos si tiene flujo que precece...
						String precede = flow_Participante.getFlow()
								.getPrecedencia() != null ? (String) flow_Participante
								.getFlow().getPrecedencia() : "";
						// aqui devolvemos cualquier Flow_Participantes.. lo
						// importante
						// es
						// deir que se puede retroceder
						if (!isEmptyOrNull(precede)) {
							devolver = new Flow_Participantes();
						}
					}
				}
				i++;
			}
		} else {
			// buscamos predecesores del este flow.. si existen..
			// pueden que sea secuencial el predecesor o no secuencial
			// si es secuencial.. habilitamos el ultimo patrticipante..
			// si no es secuencial.. habilitamos todos los participantes

			// a)busco el mayor predecesor del flujos y si los hay..
			// a.1) verifico si el predecesor es secuencial..
			// a.2)coloco las firmas mias (flowparalelo) sin firmar con un
			// mensaje,
			// desactivo la firma de esa persona (flowparalelo) secuencial con
			// un mensaje..
			// a.3)si no es secuencial.. coloco las firmas mias (flowparalelo)
			// sin firmar con
			// un mensaje,,
			// coloco todas las fimas de el (flowparalelo) sin firmar.. con un
			// mensaje..

			// nos vamos al papa del flow en el paralelo
			String precede = flow_Participante.getFlow().getPrecedencia() != null ? (String) flow_Participante
					.getFlow().getPrecedencia() : "";
			long flowMayorLong = 0;

			if (!isEmptyOrNull(precede)) {
				StringTokenizer precedenciaStk = new StringTokenizer(precede,
						",");

				String prec = "";
				while (precedenciaStk.hasMoreElements()) {
					prec = (String) precedenciaStk.nextElement();
					if (isNumeric(prec)) {
						long numeroFlow = new Long(prec);
						if (numeroFlow > flowMayorLong) {
							flowMayorLong = numeroFlow;
						}
					}
				}

				// aqui devolvemos cualquier Flow_Participantes.. lo importante
				// es
				// deir que se puede retroceder
				Flow flowMayor = new Flow();
				flowMayor.setCodigo(flowMayorLong);
				flowMayor = findFlow(flowMayor);
				devolver = new Flow_Participantes();
				;
				return devolver;
			}

		}
		return devolver;
	}

	public Flow_Participantes precederSimple(
			Flow_Participantes flow_Participante) {
		Flow_Participantes devolver = null;
		List<Flow_Participantes> listaAuxParticipantes = findByFlowParticipantes(flow_Participante
				.getFlow());
		int i = 0;
		List<Flow_Participantes> lista = new ArrayList<Flow_Participantes>();
		for (Flow_Participantes g : listaAuxParticipantes) {
			lista.add(i, g);
			// si el flowparticipante.getcodigo==null.. entonces devolvemos
			// al primer particpante, esto se debe porque vamos al flujo
			// anterior
			// y ebemos buscar el primer particpante del flujo anterior y si el
			// flujo anterior es secuencial
			if (flow_Participante.getCodigo() == null) {
				return g;
			}

			if (g != null && flow_Participante != null && g.getCodigo() != null
					&& flow_Participante.getCodigo() != null) {
				// que g sea el mismo que flow_participante para ubicarnos..
				if ((g.getCodigo() - flow_Participante.getCodigo() == 0)) {
					// nos vamos a la lista del anterior
					i--;
					if (i != -1) {
						devolver = lista.get(i);
						return devolver;
					}
				}
				i++;
			}
		}
		return null;
	}

	public Flow_Participantes devolucionFirmaFlowEnParalelo(
			Flow_Participantes flow_Participante) {

		// FIN SI ES UN PRECDER
		// SIMPLE----------------------------------------------------
		// LA PRIMERA LLAMADA ES PARA VALIDAR SI SE PUEDE DEVOLVER UN FLOW EN
		// DATACOMBO
		// LA SEGUNDA LLAMADA.. DEVOLVEMOS EL PARTICIPANTE A DEVOLVER
		Flow_Participantes devolver = precederSimple(flow_Participante);
		if (devolver != null) {
			return devolver;
		}

		// SI FUE SECUENCIAL.. PERO NO RETORNO NADA,ES DECIR.. ERA EL ULTIMO DEL
		// FLOW
		// .. ENTONCES.. CHEQUEAMOS SI HAY PRECEDENCIA PARA
		// /DEVOLVER

		// else {
		// UN PRECDER
		// PARALELO----------------------------------------------------
		// buscamos predecesores del este flow.. si existen..

		// a.1) verifico si el predecesor es secuencial..
		// a.2)coloco las firmas mias sin firmar con un mensaje,
		// desactivo la firma de esa persona con un mensaje..
		// a.3)si no es secuencial.. coloco las firmas mias sin firmar con
		// un mensaje,,
		// coloco todas las fimas de el sin firmar.. con un mensaje..

		// nos vamos al papa del flow en el paralelo
		String precede = flow_Participante.getFlow().getPrecedencia() != null ? (String) flow_Participante
				.getFlow().getPrecedencia() : "";

		long flowMayorLong = 0;

		if (!isEmptyOrNull(precede)) {

			Flow flowPrecede = flowQuePrecede(flow_Participante);
			// // a.2)coloco las firmas mias firmadas y no firmadas
			// (flow_Participante)
			// en estado "sin firmar" con un mensaje del
			// flow_Participante
			// no editamos el flujo principal porque estamos todavia en
			// revision o aprobacion
			List<Flow_Participantes> fps = findByFlowParticipantes(flow_Participante
					.getFlow());

			for (Flow_Participantes fpcolcarSinFirma : fps) {
				// si este es nulo, implica que viene por
				// devolucionFirmaFlowEnParalelo internamente. donde
				// flowparticipante solo trae el flow
				if (flow_Participante.getCodigo() == null) {
					flow_Participante = fpcolcarSinFirma;
				}

				devolvemos(flow_Participante, fpcolcarSinFirma);

				// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA MANDARLES
				// UN MAIL
				/*
				 * if (flow_Participante.getFlow().isNotificacionMail()) {
				 * flow_Participante.setComentario(fpcolcarSinFirma
				 * .getComentario()); MyHiloEnvioMails myHiloEnvioMails = new
				 * MyHiloEnvioMails(
				 * mandarMailsUsuarios(flow_Participante.getFlow(),
				 * fpcolcarSinFirma), find_allConfiguracion());
				 * myHiloEnvioMails.start(); // }
				 */

			}
			// verifico si el predecesor es secuencial..
			if (flowPrecede.isSecuencial()) {

				Flow_Participantes devolvemosFP = new Flow_Participantes();
				devolvemosFP.setFlow(flowPrecede);
				// traeemos el flowparticipante a devolver
				devolvemosFP = devolucionFirmaFlowEnParalelo(devolvemosFP);
				if (devolvemosFP != null) {
					devolvemos(flow_Participante, devolvemosFP);

					// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA MANDARLES
					// UN MAIL
					/*
					 * if (fpPrincipalAux.getFlow().isNotificacionMail()) {
					 * fpPrincipalAux.setComentario(devolvemosFP
					 * .getComentario()); MyHiloEnvioMails myHiloEnvioMails =
					 * new MyHiloEnvioMails(
					 * mandarMailsUsuarios(fpPrincipalAux.getFlow(),
					 * fpPrincipalAux), find_allConfiguracion());
					 * myHiloEnvioMails.start(); // }
					 */
				}

			} else {
				// si no es secuencial....
				// todas enm estado "sin firmar" con un mensaje del
				// flow_Participante
				fps = findByFlowParticipantes(flowPrecede);
				for (Flow_Participantes fpcancelados : fps) {
					// si este es nulo, implica que viene por
					// devolucionFirmaFlowEnParalelo internamente. donde
					// flowparticipante solo trae el flow
					if (flow_Participante.getCodigo() == null) {
						flow_Participante = fpcancelados;
					}

					if (fpcancelados != null) {

						devolvemos(flow_Participante, fpcancelados);

						// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA
						// MANDARLES
						// UN MAIL
						/*
						 * if (fpPrincipalAux.getFlow().isNotificacionMail()) {
						 * fpPrincipalAux.setComentario(fpcancelados
						 * .getComentario()); MyHiloEnvioMails myHiloEnvioMails
						 * = new MyHiloEnvioMails( mandarMailsUsuarios(
						 * fpPrincipalAux.getFlow(), fpPrincipalAux),
						 * find_allConfiguracion()); myHiloEnvioMails.start();
						 * // }
						 */
					}

				}
			}

			// colocamos el flujo aprobacion o revision como sea el caso
			Doc_estado doc_estado = null;
			if (Utilidades.getAprobado() == flowPrecede.getEstado().getCodigo()) {
				doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getEnAprobacion());
				doc_estado = findDocEstado(doc_estado);
				flowPrecede.setEstado(doc_estado);
			} else if (Utilidades.getRevisado() == flowPrecede.getEstado()
					.getCodigo()) {
				doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getEnRevision());
				doc_estado = findDocEstado(doc_estado);
				flowPrecede.setEstado(doc_estado);
			}
			try {
				edit(flowPrecede);
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// retornamos null.. porque los flujos paralelos se tratan en
			// este metodo
			// y no en ningun otro lugar
			// DelegadoEcologicalEJBBean->devolucionFirmaFlowExecute
			return null;

		}
		// }
		// FIN PRECDER
		// PARALELO----------------------------------------------------

		// }
		return devolver;
	}

	public Flow flowQuePrecede(Flow_Participantes flow_Participante) {
		// nos vamos al papa del flow en el paralelo
		Flow flowPrecede = null;
		String precede = flow_Participante.getFlow().getPrecedencia() != null ? (String) flow_Participante
				.getFlow().getPrecedencia() : "";
		long flowMayorLong = 0;

		if (!isEmptyOrNull(precede)) {
			StringTokenizer precedenciaStk = new StringTokenizer(precede, ",");

			String prec = "";
			while (precedenciaStk.hasMoreElements()) {
				prec = (String) precedenciaStk.nextElement();
				if (isNumeric(prec)) {
					long numeroFlow = new Long(prec);
					if (numeroFlow > flowMayorLong) {
						flowMayorLong = numeroFlow;
					}
				}
			}
			flowPrecede = new Flow();
			flowPrecede.setCodigo(flowMayorLong);
			flowPrecede = findFlow(flowPrecede);
		}

		return flowPrecede;
	}

	public void devolvemos(Flow_Participantes flow_Participante,
			Flow_Participantes devolver) {

		// ESTO ERA PORQUE SALIA UNA CADENA REPETIDA Y CON ESTO LA ARREGLABA--
		String cad = "[" + flow_Participante.getParticipante() + "]->";

		String coment = devolver.getComentario() != null ? devolver
				.getComentario() : "";
		// if (cad.contains(coment)){
		// devolver.setComentario(flow_Participante.getComentario());
		// }else{
		devolver.setComentario(cad + " " + flow_Participante.getComentario());
		// }
		// FIN ESTO ERA PORQUE SALIA UNA CADENA REPETIDA Y CON ESTO LA
		// ARREGLABA--

		Doc_estado sinFirmar = new Doc_estado();
		sinFirmar.setCodigo(Utilidades.getSinFirmar());
		sinFirmar = findDocEstado(sinFirmar);
		devolver.setFirma(sinFirmar);
		devolver.setFecha_firma(null);

		// aqui devolvemos lo firmado------------------------------
		try {
			edit(devolver);
			Flow_ParticipantesAttachment flow_ParticipantesAttachment = new Flow_ParticipantesAttachment();
			flow_ParticipantesAttachment.setFlowParticipantes(devolver);
			flow_ParticipantesAttachment.setStatus(Utilidades.isActivo());
			flow_ParticipantesAttachment = find_Flow_ParticipantesAttachment(flow_ParticipantesAttachment);
			if (flow_ParticipantesAttachment != null) {
				List<SvnRutasRelativasFiles> SvnRutasRelativasFilesLst = findAllSvnRutasRelativasFiles(flow_ParticipantesAttachment);
				for (SvnRutasRelativasFiles svnRutasRelativasFiles : SvnRutasRelativasFilesLst) {
					destroy(svnRutasRelativasFiles);
				}
				destroy(flow_ParticipantesAttachment);
			}

		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Usuario> mandarMailsUsuarios(Flow flow) {
		List<Usuario> mandarMailUsuarios = new ArrayList();
		List<Flow_Participantes> f_ps = findAllFlowParticipantesByFlow(flow);
		boolean swSendDuenio = true;
		String nomEmp = "";
		for (Flow_Participantes mailUsuario : f_ps) {
			nomEmp = "";
			if (flow.getDuenio() != null
					&& flow.getDuenio().getEmpresa() != null
					&& flow.getDuenio().getEmpresa().getNombre() != null) {
				nomEmp = flow.getDuenio().getEmpresa().getNombre() != null ? "["
						+ flow.getDuenio().getEmpresa().getNombre() + "]"
						: "";
			}

			mailUsuario.getParticipante().setComentarioMailFlujo("");
			mailUsuario.getParticipante().setComentarioMailFlujo(
					"\n"
							+ messages.getString("enviadoPor")
							+ ":"
							+ flow.getDuenio().getApellido()
							+ " "
							+ flow.getDuenio().getNombre()
							+ nomEmp
							+ "\n\n      "
							+ messages.getString("fecha")
							+ ":"
							+ flow.getFechaCreadoOnly()
							+ "\n     "
							+ messages.getString("titulosubflujo")
							+ ":"
							+ flow.getNombredelflujo()
							+ "\n     "
							+ messages.getString("doc_descripciontab")
							+ ":"
							+ flow.getComentarios()
							+ "\n\n\n        "
							+ messages.getString("doc_documento")
							+ ": "
							+ flow.getDoc_detalle().getDoc_maestro()
									.getNombre()
							+ " ("
							+ flow.getDoc_detalle().getDoc_maestro()
									.getConsecutivo() + ")");
			mandarMailUsuarios.add(mailUsuario.getParticipante());

			if (swSendDuenio) {
				swSendDuenio = false;
				Usuario duenio = new Usuario();
				duenio = flow.getDuenio();
				mandarMailUsuarios.add(duenio);
			}
		}

		// INICIO AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS
		sendMailsUsuariosForNotificar(flow, null, mandarMailUsuarios);
		// FIN AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS

		return mandarMailUsuarios;
	}

	public List<Usuario> mandarMailsUsuariosDocPublico(List<Usuario> usuarios,
			Doc_detalle doc_detalle, String msg) {
		List mandarMailUsuarios = new ArrayList();

		// SOLO LO HACE UNA SOLA VEZ, ESTE FOR
		for (Usuario mailUsuario : usuarios) {

			mailUsuario.setComentarioMailFlujo("");
			mailUsuario.setComentarioMailFlujo(msg);

			mandarMailUsuarios.add(mailUsuario);
		}
		return mandarMailUsuarios;
	}

	public List<Usuario> mandarMailsUsuarios(Flow flow,
			Flow_Participantes flow_Participante) {
		List<Usuario> mandarMailUsuarios = new ArrayList();
		if (flow_Participante == null) {
			return mandarMailUsuarios;
		}
		List<Flow_Participantes> f_ps = findAllFlowParticipantesByFlow(flow);
		String mensBundle = " ";
		if (flow_Participante != null && flow_Participante.getFirma() != null
				&& flow_Participante.getFirma().getNombre() != null) {
			mensBundle = messages.getString(flow_Participante.getFirma()
					.getNombre());
		}

		flow_Participante.setFirmaStr(mensBundle.toString());
		boolean swSendDuenio = true;
		// SOLO LO HACE UNA SOLA VEZ, ESTE FOR
		for (Flow_Participantes mailUsuario : f_ps) {
			// Walter Jose Araujo Valero tambi�n coment� la foto de Karina
			// Linares
			// tambiencomento
			// el primer comentarioes qle que siempre se manda
			mailUsuario.getParticipante().setComentarioMailFlujo("");
			mailUsuario.getParticipante().setComentarioMailFlujo(
					flow_Participante.getParticipante().toString().trim()
							+ "\n\n"
							+ messages.getString("flow")
							+ ":"
							+ flow.getDoc_detalle().getDoc_maestro()
									.getNombre()
							+ " "
							+ flow.getDoc_detalle().getDoc_maestro()
									.getConsecutivo() + " " + "\n"
							+ messages.getString("fecha") + ":"
							+ flow.getFechaCreadoOnly() + "\n     "
							+ messages.getString("titulosubflujo") + ":"
							+ flow.getNombredelflujo() + "\n     "
							+ flow.getComentarios() + "\n\n"
							+ messages.getString("tambiencomento") + "\n\n \""
							+ flow_Participante.getComentario() + "\"\n\n"
							+ flow_Participante.getFirmaStr() + "\n\n\n");

			mandarMailUsuarios.add(mailUsuario.getParticipante());
			// fin del primer mensaje
			// aqui solo agarramos el usuario.. el mensaje no es mandado
			if (swSendDuenio) {
				swSendDuenio = false;
				Usuario duenio = new Usuario();
				duenio = flow.getDuenio();
				mandarMailUsuarios.add(duenio);
			}
			// fin aqui solo agarramos el usuario.. el mensaje no es mandado
		}
		// INICIO AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS
		sendMailsUsuariosForNotificar(flow, flow_Participante,
				mandarMailUsuarios);
		// FIN AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS

		return mandarMailUsuarios;
	}

	public void sendMailsUsuariosForNotificar(Flow flow,
			Flow_Participantes flow_Participante, List mandarMailUsuarios) {
		// INICIO AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS
		// PARA GRUPOS Y LOS USUARIOS DENTRO DEL GRUPO NOTIFICACION

		List<FlowOnlyNotificacionRole> frlst1 = findAllFlowOnlyNotificacionRoleByFlow(flow);
		List<Flow> participantesGruposPlantilaNotif = new ArrayList<Flow>();
		for (FlowOnlyNotificacionRole fr : frlst1) {
			List<Roles_Usuarios> roleUsuarioLst = findRoles_AND_Usuario(fr
					.getRole());
			for (Roles_Usuarios ru : roleUsuarioLst) {
				if (flow_Participante != null) {
					ru.getUsuario().setComentarioMailFlujo(
							messages.getString("notificacion").toUpperCase()
									+ "\n\n"
									+ flow_Participante.getParticipante()
											.toString().trim()
									+ "\n\n"
									+ messages.getString("flow")
									+ ":"
									+ flow.getDoc_detalle().getDoc_maestro()
											.getNombre()
									+ " "
									+ flow.getDoc_detalle().getDoc_maestro()
											.getConsecutivo() + " " + "\n"
									+ messages.getString("fecha") + ":"
									+ flow.getFechaCreadoOnly() + "\n     "
									+ messages.getString("titulosubflujo")
									+ ":" + flow.getNombredelflujo()
									+ "\n     " + flow.getComentarios()
									+ "\n\n"
									+ messages.getString("tambiencomento")
									+ "\n\n \""
									+ flow_Participante.getComentario()
									+ "\"\n\n"
									+ flow_Participante.getFirmaStr()
									+ "\n\n\n");
				} else {
					ru.getUsuario().setComentarioMailFlujo(
							messages.getString("notificacion").toUpperCase()
									+ "\n\n"
									+ messages.getString("enviadoPor")
									+ ":"
									+ flow.getDuenio().getApellido()
									+ " "
									+ flow.getDuenio().getNombre()
									+ flow.getDuenio().getEmpresa().getNombre()
									+ "\n\n      "
									+ messages.getString("fecha")
									+ ":"
									+ flow.getFechaCreadoOnly()
									+ "\n     "
									+ messages.getString("titulosubflujo")
									+ ":"
									+ flow.getNombredelflujo()
									+ "\n     "
									+ messages.getString("doc_descripciontab")
									+ ":"
									+ flow.getComentarios()
									+ "\n\n\n        "
									+ messages.getString("doc_documento")
									+ ": "
									+ flow.getDoc_detalle().getDoc_maestro()
											.getNombre()
									+ " ("
									+ flow.getDoc_detalle().getDoc_maestro()
											.getConsecutivo() + ")");
				}

				mandarMailUsuarios.add(ru.getUsuario());
			}
		}

		// FIN AQUI MANDAMOS MAILS A LOS USUARIOS NOTIFICADOS

	}

	public Boolean flowIsFinEdoRechazadoFlowParticipantes(Flow flow) {
		boolean rechazado = false;
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.firma.codigo=:rechazado";
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		query.setParameter("rechazado", Utilidades.getRechazado());
		List lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			rechazado = true;
		}
		return rechazado;
	}

	public List findByFlowParticipantesPlantilla(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
		str += " where o.flow.codigo=f.codigo and f.doc_detalle.codigo=:docCodigo and ";
		str += "  (f.estado.codigo=:borrador or f.estado.codigo=:enrevision or f.estado.codigo=:enaprobacion)";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and f.plantilla=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());
		return query.getResultList();
	}

	public List findByFlowParticipantes(Doc_detalle doc) {
		// return em.createQuery("").getResultList();
		// return em.createQuery("").getResultList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
		str += " where o.flow.codigo=f.codigo and f.doc_detalle.codigo=:docCodigo and ";
		str += "  (f.estado.codigo=:borrador or f.estado.codigo=:enrevision or f.estado.codigo=:enaprobacion)";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and f.status=" + Utilidades.isActivo();
		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and f.origen=" + doc.getOrigen();
		}
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());
		return query.getResultList();
	}

	public List findByFlowParticipantesByRevOrAprob(Doc_detalle doc,
			Doc_estado doc_estado) {
		// Doc_estado borrador = new Doc_estado();
		// borrador.setCodigo(Utilidades.getBorrador());
		//
		// Doc_estado enaprobacion = new Doc_estado();
		// enaprobacion.setCodigo(Utilidades.getEnAprobacion());
		//
		// Doc_estado enrevision = new Doc_estado();
		// enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
		str += " where o.flow.codigo=f.codigo and f.doc_detalle.codigo=:docCodigo and ";
		str += "  (f.estado.codigo=:doc_estado )";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and f.status=" + Utilidades.isActivo();
		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and f.origen=" + doc.getOrigen();
		}
		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("doc_estado", doc_estado.getCodigo());
		return query.getResultList();
	}

	public List findByFlowParticipantesRoleIsNull(Doc_detalle doc,
			boolean swRoleIsNull) {

		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());

		String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
		str += " where  o.flow.codigo=f.codigo and f.doc_detalle.codigo=:docCodigo and ";
		if (swRoleIsNull) {
			str += " o.role is null and ";
		} else {
			str += " o.role is not null and ";
		}
		str += "  (f.estado.codigo=:borrador or f.estado.codigo=:enrevision or f.estado.codigo=:enaprobacion)";
		str += " and o.status=" + Utilidades.isActivo();

		if (doc != null && doc.getFlowEditar() != null) {
			// si es plantilla..,el estatus es cero.. , por eso la negamos, para
			// que me traiga solo plantillas
			str += " and o.flow.codigo=" + doc.getFlowEditar().getCodigo();
		} else {

			// NO LO COLOCAMOS.. PUEDE ESTAR INACTIVO SI LO ESTAS HACIENDO..
			// str += " and f.status=" + Utilidades.isActivo();
			// NO LO COLOCAMOS.. PUEDE ESTAR INACTIVO SI LO ESTAS HACIENDO..
		}

		if (doc.getOrigen() != null && doc.getOrigen() > 0) {
			str += " and f.origen=" + doc.getOrigen();
		}

		Query query = em.createQuery(str.toString());
		query.setParameter("docCodigo", doc.getCodigo());
		query.setParameter("borrador", borrador.getCodigo());
		query.setParameter("enrevision", enrevision.getCodigo());
		query.setParameter("enaprobacion", enaprobacion.getCodigo());

		return query.getResultList();
	}

	public List findAllFlowDelParticipanteFirmados(Usuario participante) {
		List<Flow_Participantes> listaEnviar = new ArrayList();
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());
		List<Flow_Participantes> lista = new ArrayList();
		if (participante != null) {

			String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
			str += " where o.flow.codigo=f.codigo  ";
			str += "  and o.participante.id=:codigo ";
			str += "  and o.firma <> " + Utilidades.getSinFirmar();
			str += "  and o.activo = " + Utilidades.isActivo();
			str += " and o.status=" + Utilidades.isActivo();
			// str +=" order by lower(f.estado.nombre)";
			str += " order by o.codigo desc";
			Query query = em.createQuery(str.toString());
			query.setParameter("codigo", participante.getId());

			query.setMaxResults(Utilidades.getMinRegisterMostrar())
					.setFirstResult(0);
			lista = query.getResultList();
			/*
			 * Map<Long,Object> unico = new HashMap<Long,Object>();
			 * 
			 * //SOLO UN REPRESENTRANTE DE FLUJO PARALELOS for
			 * (Flow_Participantes fp:lista){ if
			 * (!unico.containsKey(fp.getFlow().getFlowParalelo().getCodigo())){
			 * unico.put(fp.getFlow().getFlowParalelo().getCodigo(),
			 * fp.getFlow().getFlowParalelo().getCodigo()); listaEnviar.add(fp);
			 * 
			 * }
			 * 
			 * }
			 */

		}
		return lista;
	}

	public List findAllFlowParticipantesPendientesDoc_Detalle(
			Usuario participante, Doc_detalle doc_detalle) {
		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		// PARA FLOWS PARALELOS
		Doc_estado aprobado = new Doc_estado();
		aprobado.setCodigo(Utilidades.getAprobado());
		// PARA FLOWS PARALELOS

		int mostrar = Utilidades.getMinRegisterMostrar();

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());
		List<Flow_Participantes> lista = new ArrayList<Flow_Participantes>();
		List<Flow_Participantes> listAmandar = new ArrayList<Flow_Participantes>();
		if (participante != null) {
			String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
			str += " where o.flow.codigo=f.codigo ";

			str += "  and o.participante.id=:codigo  ";
			str += " and o.status=" + Utilidades.isActivo();
			str += " and f.status=" + Utilidades.isActivo();
			mostrar *= 4;
			str += "  and o.firma = " + Utilidades.getSinFirmar();

			str += " order by o.codigo desc";
			// System.out.println(str.toString());
			Query query = em.createQuery(str.toString());
			query.setParameter("codigo", participante.getId());
			query.setMaxResults(mostrar).setFirstResult(0);
			lista = query.getResultList();

			if (lista != null && lista.size() > 0) {

				for (Flow_Participantes fp : lista) {
					Flow flow = fp.getFlow();
					if (doc_detalle != null
							&& ((flow.getDoc_detalle().getCodigo()
									- doc_detalle.getCodigo() == 0) || (flow
									.getFlowParalelo().getFlow()
									.getDoc_detalle().getCodigo()
									- doc_detalle.getCodigo() == 0))) {
						// encontro flujo con el docdetalle y el participante
						listAmandar.add(fp);
						break;
					}
				}
			}
		}
		return listAmandar;
	}

	public List findAllFlowParticipantesPendientes(Usuario participante) {

		Doc_estado borrador = new Doc_estado();
		borrador.setCodigo(Utilidades.getBorrador());

		Doc_estado enaprobacion = new Doc_estado();
		enaprobacion.setCodigo(Utilidades.getEnAprobacion());

		// PARA FLOWS PARALELOS
		Doc_estado aprobado = new Doc_estado();
		aprobado.setCodigo(Utilidades.getAprobado());
		// PARA FLOWS PARALELOS

		// int mostrar = Utilidades.getMinRegisterMostrar();

		Doc_estado enrevision = new Doc_estado();
		enrevision.setCodigo(Utilidades.getEnRevision());
		List<Flow_Participantes> lista = new ArrayList<Flow_Participantes>();
		List<Flow_Participantes> listAmandar = new ArrayList<Flow_Participantes>();
		if (participante != null) {
			String str = "select object(o) from Flow_Participantes as o,Flow as f  ";
			str += " where o.flow.codigo=f.codigo  ";
			// str +=
			// " and (f.estado.codigo=:enrevision or f.estado.codigo=:enaprobacion)";

			str += "  and o.participante.id=:codigo  ";
			str += " and o.status=" + Utilidades.isActivo();
			str += " and f.status=" + Utilidades.isActivo();
			if (participante.isSwSiFirmaron()) {
				str += "  and o.firma <> " + Utilidades.getSinFirmar();
			} else {
				// mostrar *= 4;
				str += "  and o.firma = " + Utilidades.getSinFirmar();
			}

			str += " order by o.codigo desc";
			Query query = em.createQuery(str.toString());
			query.setParameter("codigo", participante.getId());
			// query.setParameter("enrevision", enrevision.getCodigo());
			// query.setParameter("enaprobacion", enaprobacion.getCodigo());

			// no es objeto getOrigenDocumentoFlow() ni secuencial ni condiciona

			query.setMaxResults(Utilidades.getMinRegisterMostrar())
					.setFirstResult(0);
			lista = query.getResultList();

			// AQUI HAREMOS EL USO DE FLUJOS PARALELOS
			// chqueamos que si hay precedencias.. y esten aprobados..
			// entonces.. el flow lo pueda firmar el suario
			if (lista != null && lista.size() > 0) {
				Flow flowPrecedeIsAprobado = null;

				for (Flow_Participantes fp : lista) {

					Flow flow = fp.getFlow();
					boolean swAprobado = true;
					// bucamos si algun flujo precedente falta por ser aprobado
					if (!isEmptyOrNull(flow.getPrecedencia())) {
						StringTokenizer stk = new StringTokenizer(
								flow.getPrecedencia(), ",");
						while (stk.hasMoreElements()) {
							String f = (String) stk.nextElement();
							flowPrecedeIsAprobado = new Flow();
							flowPrecedeIsAprobado.setCodigo(new Long(f));
							flowPrecedeIsAprobado = findFlow(flowPrecedeIsAprobado);

							// comprobamos que tenga participantes..
							str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
							query = em.createQuery(str.toString());
							query.setParameter("codigo",
									flowPrecedeIsAprobado.getCodigo());
							lista = query.getResultList();
							// tambien si no tiene participantes
							if (lista == null || lista.isEmpty()) {
								flowPrecedeIsAprobado.setStatus(false);
								try {
									edit(flowPrecedeIsAprobado);
								} catch (EcologicaExcepciones e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								if ((flowPrecedeIsAprobado.getEstado()
										.getCodigo() - aprobado.getCodigo()) != 0) {
									swAprobado = false;
									break;
								}
							}

						}
					}

					if (swAprobado) {

						listAmandar.add(fp);
					}

				}
			}

			// FIN FLOW PARALELOS
		}
		return listAmandar;
	}

	public List<Flow_Participantes> findAllFlowParticipantesByFlow(Flow flow) {
		Flow_Participantes flow_Participante = null;
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.activo = " + Utilidades.isActivo();
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		List<Flow_Participantes> lista = query.getResultList();

		return lista;
	}

	public Flow_Participantes flowFlowOfParticipantes(Flow flow, Usuario usuario) {
		Flow_Participantes flow_Participante = null;
		String str = "select object(o) from Flow_Participantes as o where o.flow.codigo=:codigo ";
		str += " and o.participante.id=:id and o.activo = "
				+ Utilidades.isActivo();
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		query.setParameter("id", usuario.getId());
		List<Flow_Participantes> lista = query.getResultList();
		for (Flow_Participantes flow_Pa : lista) {
			flow_Participante = flow_Pa;
		}
		return flow_Participante;
	}

	// _______________________Flow_referencia_role_____________________________________________________________________
	public void create(Flow_referencia_role flow) throws EcologicaExcepciones {
		try {
			flow.setStatus(true);
			em.persist(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void create(FlowOnlyNotificacionRole flow)
			throws EcologicaExcepciones {
		try {
			flow.setStatus(true);
			em.persist(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void edit(Flow_referencia_role flow) throws EcologicaExcepciones {
		try {
			em.merge(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(FlowOnlyNotificacionRole flow)
			throws EcologicaExcepciones {
		try {
			flow = findFlow(flow);

			// em.merge(flow);
			em.remove(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString() + e.toString());
		}
	}

	public void destroy(Flow_referencia_role flow) throws EcologicaExcepciones {
		try {
			flow = findFlow(flow);

			// em.merge(flow);
			em.remove(flow);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString() + e.toString());
		}
	}

	public FlowOnlyNotificacionRole findFlow(FlowOnlyNotificacionRole pk) {
		return (FlowOnlyNotificacionRole) em.find(
				FlowOnlyNotificacionRole.class, pk.getCodigo());
	}

	public Flow_referencia_role findFlow(Flow_referencia_role pk) {
		return (Flow_referencia_role) em.find(Flow_referencia_role.class,
				pk.getCodigo());
	}

	public List findAllFlow_referencia_role_ByFlow(Flow flow) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow_referencia_role as o ");
		sql.append(" where o.flow.codigo=").append(flow.getCodigo());
		sql.append(" and o.status=").append(Utilidades.isActivo());
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllFlowOnlyNotificacionRoleByFlow(Flow flow) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from FlowOnlyNotificacionRole as o ");
		sql.append(" where o.flow.codigo=").append(flow.getCodigo());
		sql.append(" and o.status=").append(Utilidades.isActivo());

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllFlow_referencia_role() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow_referencia_role as o ");
		sql.append(" where o.status=").append(Utilidades.isActivo());

		return em.createQuery(sql.toString()).getResultList();
	}

	public void destroyAllFlow_referencia_roleByRole(Role role) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Flow_referencia_role as o ");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.role.codigo=").append(role.getCodigo());
		Query query = em.createQuery(sql.toString());
		List<Flow_referencia_role> lista = query.getResultList();
		for (Flow_referencia_role f_r : lista) {
			try {
				destroy(f_r);
			} catch (EcologicaExcepciones ex) {
				ex.printStackTrace();
			}

		}
		// return em.createQuery(sql.toString()).getResultList();
	}

	public List findByFlow_referencia_role(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Flow_referencia_role as o where o.flow.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.flow.origen=" + flow.getOrigen();

		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		List lista = null;
		try {
			lista = query.getResultList();
		} catch (Exception e) {
			lista = new ArrayList();
		}
		return lista;
	}

	public List findByFlowOnlyNotificacionRole(Flow flow) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from FlowOnlyNotificacionRole as o where o.flow.codigo=:codigo ";
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.flow.origen=" + flow.getOrigen();

		Query query = em.createQuery(str.toString());
		query.setParameter("codigo", flow.getCodigo());
		List lista = null;
		try {
			lista = query.getResultList();
		} catch (Exception e) {
			lista = new ArrayList();
		}
		return lista;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN FLUJOS
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// HISTORICO
	// __________________________________________________________________________________________________________________________//
	public void create(Hist_usuarios hist_usuarios) {

		em.persist(hist_usuarios);
	}

	public void edit(Hist_usuarios hist_usuarios) {
		em.merge(hist_usuarios);
	}

	public void destroy(Hist_usuarios hist_usuarios) {

		hist_usuarios = (Hist_usuarios) em.find(Hist_usuarios.class,
				hist_usuarios.getId());
		em.remove(hist_usuarios);
	}

	public Hist_usuarios find(Object pk) {
		return (Hist_usuarios) em.find(Hist_usuarios.class, pk);
	}

	public List findAll_Historico() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Hist_usuarios as o ");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		return em.createQuery(sql.toString()).setMaxResults(1000)
				.getResultList();
	}

	public List findAll_HistoricoByUsuario(Usuario usuario) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Hist_usuarios as o ");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" and histusuario.id=").append(usuario.getId());
		sql.append(" order by fecha_conecto_inicio desc");
		return em.createQuery(sql.toString()).setMaxResults(1000)
				.getResultList();

	}

	// ________________________________________________________________________________________________________________________://
	// FIN HISTORICO
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// OPERACIONES
	// __________________________________________________________________________________________________________________________//

	public void edit(Operaciones operaciones) throws EcologicaExcepciones {
		try {
			getOperacion(operaciones);
			em.merge(operaciones);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void destroy(Operaciones operaciones) throws EcologicaExcepciones {
		try {
			getOperacion(operaciones);
			// em.merge(operaciones);
			em.remove(operaciones);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public void create(Operaciones operaciones) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado {
		try {
			getOperacion(operaciones);
			// throw new
			// EcologicaExcepciones(messagesConf.getString("error_userYaExiste"));
		} catch (EcologicaExcepciones e3) {
			ServicioLogger.logger.log(Level.INFO,
					"es nulo :" + operaciones.getOperacion());
			operaciones.setStatus(true);
			em.persist(operaciones);
			ServicioLogger.logger.log(Level.INFO, "NUEVO REGISTRO SUCESSFULL :"
					+ operaciones.getOperacion());
		} catch (NoResultException e) {
			operaciones.setStatus(true);
			em.persist(operaciones);
			ServicioLogger.logger.log(Level.INFO,
					"Nuevo Registro :" + operaciones.getCodigo() + " nombre:"
							+ operaciones.getOperacion());
		} catch (NonUniqueResultException e2) {
			operaciones.setStatus(true);

			if (isEmptyOrNull(operaciones.getOperacion())) {
				if (!isEmptyOrNull(operaciones.getDescripcion())) {
					operaciones.setOperacion(operaciones.getDescripcion());
				}
			}

			em.persist(operaciones);
		}
	}

	public Operaciones getOperacion(Operaciones operacion)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		Operaciones operaciones = null;
		try {
			if (operacion.getCodigo() == null) {
				throw new EcologicaExcepciones();
			} else {
				operaciones = (Operaciones) em.find(Operaciones.class,
						operacion.getCodigo());
			}
		} catch (NoResultException e) {

			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {

			throw new RoleMultiples(messagesConf.getString("error_userDupl"));
		}
		return operaciones;
	}

	// Operaciones
	public List findAll_operaciones() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Operaciones as o");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.pertenece_Arbol, lower(o.operacion) desc");

		Query query = em.createQuery(sql.toString());
		List<Operaciones> lista = query.getResultList();
		return lista;
	}

	public List findAll_operacionesMenu() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Operaciones as o");
		sql.append(" where o.pertenece_Arbol=").append(
				Utilidades.getPertence_Menu());
		sql.append(" or o.pertenece_Arbol=").append(
				Utilidades.getPertence_Arbol_and_Menu());
		sql.append(" and o.status=").append(Utilidades.isActivo());
		sql.append(" order by lower(o.operacion)");
		return em.createQuery(sql.toString()).getResultList();
	}

	public List<Operaciones> findAll_operacionesArbol(Tree tree) {

		return permisosDeacuerdoTipoDeNodo(tree);
	}

	// ESTO ES SOLO PARA PERMISOS TREE
	private List<Operaciones> permisosDeacuerdoTipoDeNodo(Tree tree) {
		if (tree == null) {
			return null;
		}
		long tiponodo = tree.getTiponodo();

		Operaciones ope = new Operaciones();
		List<Operaciones> lista = new ArrayList();
		lista.clear();

		// LLENA LOS PERMISOS DE ACUERDO AL TIPO DE NODO EN QUE SE ESTE

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getConfSeguridad());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToMod());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToDel());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToMove());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToView());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToActivePermGroup());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToGivePermUser());
		ope = findOperacion(ope);
		lista.add(ope);

		ope = new Operaciones();
		ope.setOperacion(Utilidades.getToSaveDataBasic());
		ope = findOperacion(ope);
		lista.add(ope);
		// SOLO PARA NODOS.................ARRIBA Y ABAJO....................
		// RAIZ
		if (tiponodo == Utilidades.getNodoRaiz()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddPrincipal());
			ope = findOperacion(ope);
			lista.add(ope);

			// PRINCIPAL
		} else if (tiponodo == Utilidades.getNodoPrincipal()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddArea());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddProceso());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCarpeta());
			ope = findOperacion(ope);
			lista.add(ope);

			// AREA
		} else if (tiponodo == Utilidades.getNodoArea()) {
			// INICIO--------20110105---------------------------------------------------------------
			// ESTO ES NUEVO.. UNA AREA PUEDE AGREGAR DENTRO DE ELLA MAS AREAS
			/*
			 * ope = new Operaciones();
			 * ope.setOperacion(Utilidades.getToAddArea()); ope =
			 * findOperacion(ope); lista.add(ope);
			 */
			// ESTO ES NUEVO.. UNA AREA PUEDE AGREGAR DENTRO DE ELLA MAS AREAS
			// FIN-----------------------------------------------------------------------

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCargo());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddProceso());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCarpeta());
			ope = findOperacion(ope);
			lista.add(ope);

			// CARGO
		} else if (tiponodo == Utilidades.getNodoCargo()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddProceso());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCarpeta());
			ope = findOperacion(ope);
			lista.add(ope);

			// PROCESO
		} else if (tiponodo == Utilidades.getNodoProceso()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCarpeta());
			ope = findOperacion(ope);
			lista.add(ope);

			// CARPETA
		} else if (tiponodo == Utilidades.getNodoCarpeta()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddCarpeta());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddLotesDeDocumentos());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToadddocumentosvn());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToAddDocumentos());
			ope = findOperacion(ope);
			lista.add(ope);

			// tambien en carpetas permisos para documentos
			// ope = new Operaciones();
			// ope.setOperacion(Utilidades.getToVincDoc());
			// ope = findOperacion(ope);
			// lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoRegistros());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoFlow());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoPublico());
			ope = findOperacion(ope);
			lista.add(ope);

			// DOCUMENTO
		} else if (tiponodo == Utilidades.getNodoDocumento()) {

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getTosolicitudimpresion());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getTosolicitudimpresion0());
			ope = findOperacion(ope);
			lista.add(ope);

			// ope = new Operaciones();
			// ope.setOperacion(Utilidades.getToVincDoc());
			// ope = findOperacion(ope);
			// lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoRegistros());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoFlow());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToDoPublico());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToHistDoc());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToHistFlow());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getTodoflowrevision());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getTodownload());
			ope = findOperacion(ope);
			lista.add(ope);

			ope = new Operaciones();
			ope.setOperacion(Utilidades.getToviewcomentpublic());
			ope = findOperacion(ope);
			lista.add(ope);

		}

		return lista;
	}

	// modificado encontrar
	public Operaciones Encontrar_operaciones(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			Operaciones prof = new Operaciones();
			StringBuffer sql = new StringBuffer(
					"select object(o) from Operaciones as o where o.codigo=:codigo ");
			sql.append("");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("codigo", new Long(id));
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				prof = (Operaciones) it.next();
				return prof;
			}
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Operaciones findOperacion(Operaciones op) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Operaciones as o where lower(o.operacion)=:operacion");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		query.setParameter("operacion", op.getOperacion().toLowerCase());
		return (Operaciones) query.getSingleResult();
	}

	public List findOperacionName(Operaciones op) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Operaciones as o where o.operacion=:operacion");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		query.setParameter("operacion", op.getOperacion());
		return query.getResultList();
	}

	// ________________________________________________________________________________________________________________________://
	// FIN OPERACIONES
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// ROLE
	// __________________________________________________________________________________________________________________________//
	public void create(Role role) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado {
		try {
			role.setStatus(true);
			em.persist(role);
		} catch (Exception e) {
			// e.printStackTrace();
			e.toString();
		}
	}

	public void edit(Role role) throws EcologicaExcepciones {
		try {
			getRole(role);
			em.merge(role);
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado")
							+ e.toString());
		}
	}

	public void destroy(Role role) throws EcologicaExcepciones {
		try {

			role = getRole(role);
			// em.merge(role);
			em.remove(role);
		} catch (Exception e) {

			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado")
							+ e.toString());
		}
	}

	public Role findRole(Role role) throws RoleMultiples, RoleNoEncontrado {
		Role rol = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Role as o where o.nombre=:nombre or o.codigo=:codigo  ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			if (role.getEmpresa() != null) {
				sql.append(" and o.empresa.nodo=").append(
						role.getEmpresa().getNodo());
			}
			Query query = em.createQuery(sql.toString());

			String nom = role.getNombre() != null ? role.getNombre().trim()
					: "";
			query.setParameter("codigo", role.getCodigo());
			query.setParameter("nombre", nom);

			if (query.getResultList().size() == 1) {

				rol = (Role) query.getSingleResult();
			} else if (query.getResultList().size() > 1) {
				sql = new StringBuffer(
						"select object(o) from Role as o where  o.codigo=:codigo  ");
				sql.append(" and o.status=").append(Utilidades.isActivo());
				query = em.createQuery(sql.toString());
				query.setParameter("codigo", role.getCodigo());

				rol = (Role) query.getSingleResult();

			}

		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado") + " "
							+ role.getNombre());

		} catch (NonUniqueResultException e) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl")
					+ role.getNombre());
		}

		return rol;
	}

	public Role findRoleADefault(Role role) throws RoleMultiples,
			RoleNoEncontrado {
		Role rol = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Role as o where lower(o.nombre)=:nombre  ");
			sql.append(" and o.status=").append(Utilidades.isActivo());

			sql.append(" and o.empresa.nodo=").append(
					role.getEmpresa().getNodo());
			Query query = em.createQuery(sql.toString());

			String nom = role.getNombre() != null ? role.getNombre()
					.toLowerCase().trim() : "";
			query.setParameter("nombre", nom);

			if (query.getResultList().size() == 1) {

				rol = (Role) query.getSingleResult();
			} else if (query.getResultList().size() > 1) {

				rol = (Role) query.getResultList().get(0);

			}

		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado") + " "
							+ role.getNombre());

		} catch (NonUniqueResultException e) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl")
					+ role.getNombre());
		}

		return rol;
	}

	public Role getRole(Role role) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado {
		Role rol = null;
		if (role.getCodigo() == null) {
			role.setCodigo(0l);
		}

		try {
			rol = (Role) em.find(Role.class, role.getCodigo());
		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl"));
		}
		return rol;
	}

	public List findAll_Roles(Usuario usuario, String search) {
		StringBuffer sql = new StringBuffer("");
		try {
			sql.append("select object(o) from Role as o");
			if (search != null && search.length() > 0) {
				sql.append(
						" where o.usadoParaCrearFlujo=false and lower(o.nombre) like '")
						.append(search.toLowerCase()).append("%'");
				sql.append(" and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append(" where o.usadoParaCrearFlujo=false and o.status=")
						.append(Utilidades.isActivo());
			}
			if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())
					&& usuario.getEmpresa() != null) {
				sql.append(" and o.empresa.nodo="
						+ usuario.getEmpresa().getNodo());
			}

			sql.append("  order by lower(o.nombre)");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_Roles(Usuario usuario) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Role as o where o.usadoParaCrearFlujo=false");
		List lista = null;
		try {
			sql.append(" and o.status=").append(Utilidades.isActivo());
			if (usuario != null) {
				if (!"root".equalsIgnoreCase(usuario.getLogin())
						&& usuario.getEmpresa() != null) {
					sql.append(" and o.empresa.nodo="
							+ usuario.getEmpresa().getNodo());
				}
			}
			sql.append("  order by lower(o.nombre)");

			lista = em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List findAll_Roles(Usuario usuario, String search, boolean workFlow) {
		StringBuffer sql = new StringBuffer("");
		try {
			sql.append("select object(o) from Role as o");
			if (search != null && search.length() > 0) {
				sql.append(" where o.usadoParaCrearFlujo=").append(workFlow);
				sql.append("  and lower(o.nombre) like '")
						.append(search.toLowerCase()).append("%'");
				sql.append(" and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append(" where o.usadoParaCrearFlujo=").append(workFlow);
				sql.append(" and o.status=").append(Utilidades.isActivo());
			}
			if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())
					&& usuario.getEmpresa() != null) {
				sql.append(" and o.empresa.nodo="
						+ usuario.getEmpresa().getNodo());
			}

			sql.append("  order by lower(o.nombre)");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_Roles(Usuario usuario, boolean workFlow) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Role as o where o.usadoParaCrearFlujo=");
		sql.append(workFlow);
		List lista = null;
		try {
			sql.append(" and o.status=").append(Utilidades.isActivo());
			if (usuario != null) {
				if (!"root".equalsIgnoreCase(usuario.getLogin())
						&& usuario.getEmpresa() != null) {
					sql.append(" and o.empresa.nodo="
							+ usuario.getEmpresa().getNodo());
				}
			}
			sql.append("  order by lower(o.nombre)");

			lista = em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<Seguridad_Role_Lineal> buscarTodosLasOperacionesRol(
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_Role_Lineal as o ");
		sql.append(" where o.role.codigo= ").append(
				seguridad_Role_Lineal.getRole().getCodigo());
		sql.append(" and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());

		List result = query.getResultList();

		return result;
	}

	public List<Seguridad_Role_Lineal> buscarTodosLasOperacionesRolSoloMenu(
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_Role_Lineal as o ");
		sql.append(" where o.role.codigo= ").append(
				seguridad_Role_Lineal.getRole().getCodigo());
		sql.append(" and o.status=").append(Utilidades.isActivo());
		// A VECES EN EL MENU -- LOS TREE NO ESTAB NULOS
		// sql.append(" and o.tree is null");
		Query query = em.createQuery(sql.toString());

		List result = query.getResultList();

		return result;
	}

	public List<Roles_Usuarios> findRoles_AND_Usuario(Role role) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Roles_Usuarios as o where o.role.codigo=")
				.append(role.getCodigo());

		Query query = em.createQuery(sql.toString());
		List<Roles_Usuarios> result = query.getResultList();
		return result;
	}

	public List findRoles_Usuario(Usuario usuario) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Roles_Usuarios as o where o.usuario.id=:id ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("id", usuario.getId());
		List result = query.getResultList();
		List roles = new ArrayList();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Roles_Usuarios role_user = (Roles_Usuarios) result.get(i);
			roles.add(role_user.getRole());
		}
		return roles;
	}

	public void destroyAlldRoles_UsuarioByRole(Role role) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Roles_Usuarios as o where o.role.codigo=:id ");
		Query query = em.createQuery(sql.toString());
		if (role != null && role.getCodigo() != null) {
			query.setParameter("id", role.getCodigo());
			List result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Roles_Usuarios role_user = (Roles_Usuarios) result.get(i);
				try {
					destroy(role_user);
				} catch (EcologicaExcepciones ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// modificado
	public List findRoles_Usuario(Role role) {
		List usuarios = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select object(o) from Roles_Usuarios as o where o.role.codigo=:id ");

		Query query = em.createQuery(sql.toString());
		if (role != null && role.getCodigo() != null) {
			query.setParameter("id", role.getCodigo());
			List result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Roles_Usuarios role_user = (Roles_Usuarios) result.get(i);
				usuarios.add(role_user.getUsuario());
			}
		}
		return usuarios;
	}

	// modificado
	public Role Encontrar_roles(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			Role prof = new Role();
			StringBuffer sql = new StringBuffer(
					"select object(o) from Role as o where o.codigo=:codigo ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("codigo", new Long(id));
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				prof = (Role) it.next();
				return prof;
			}
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ________________________________________________________________________________________________________________________://
	// Role_oper_userEJBBean
	// __________________________________________________________________________________________________________________________//
	public void edit(Roles_Usuarios operaciones) throws EcologicaExcepciones {
		try {
			getRole_User(operaciones);
			em.merge(operaciones);
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy(Roles_Usuarios rol_user) throws EcologicaExcepciones {
		try {

			rol_user = (Roles_Usuarios) em.find(Roles_Usuarios.class,
					rol_user.getCodigo());

			em.remove(rol_user);

		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy_MenuByRole(Role role) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Menus_Usuarios as o where o.role.codigo=:codigo ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("codigo", role.getCodigo());
			List<Menus_Usuarios> result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Menus_Usuarios mnu_user = (Menus_Usuarios) result.get(i);
				destroy(mnu_user);
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy_Menu(Usuario usuario, Usuario remplazo)
			throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Roles_Usuarios as o where o.usuario.id=:id ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			List result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				sql = new StringBuffer(
						"select object(o) from Roles_Usuarios as o where o.usuario.id=:id ");
				query = em.createQuery(sql.toString());
				query.setParameter("id", remplazo.getId());
				result = query.getResultList();
				if (result == null || result.isEmpty()) {
					Roles_Usuarios role_oper_user = (Roles_Usuarios) result
							.get(i);
					role_oper_user.setUsuario(remplazo);
					edit(role_oper_user);
				}

			}

			sql = new StringBuffer(
					"select object(o) from Menus_Usuarios as o where o.usuario.id=:id ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			result = query.getResultList();
			size = result.size();
			for (int i = 0; i < size; i++) {
				sql = new StringBuffer(
						"select object(o) from Menus_Usuarios as o where o.usuario.id=:id ");
				sql.append(" and o.status=").append(Utilidades.isActivo());
				query = em.createQuery(sql.toString());
				query.setParameter("id", remplazo.getId());
				result = query.getResultList();
				if (result == null || result.isEmpty()) {
					Menus_Usuarios mnu_user = (Menus_Usuarios) result.get(i);
					mnu_user.setUsuario(remplazo);
					edit(mnu_user);
				}

			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy_Menu(Usuario usuario) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Roles_Usuarios as o where o.usuario.id=:id ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			List result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Roles_Usuarios role_oper_user = (Roles_Usuarios) result.get(i);
				destroy(role_oper_user);
			}

			sql = new StringBuffer(
					"select object(o) from Menus_Usuarios as o where o.usuario.id=:id ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			result = query.getResultList();
			size = result.size();
			for (int i = 0; i < size; i++) {
				Menus_Usuarios mnu_user = (Menus_Usuarios) result.get(i);
				destroy(mnu_user);
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy(Usuario usuario) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Roles_Usuarios as o where o.usuario.id=:id ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			List result = query.getResultList();
			if (result != null && !result.isEmpty()) {
				int size = result.size();
				for (int i = 0; i < size; i++) {
					Roles_Usuarios role_oper_user = (Roles_Usuarios) result
							.get(i);
					destroy(role_oper_user);
				}

			}

			sql = new StringBuffer(
					"select object(o) from Menus_Usuarios as o where o.usuario.id=:id ");

			query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			result = query.getResultList();
			if (result != null && !result.isEmpty()) {
				int size = result.size();
				for (int i = 0; i < size; i++) {
					Menus_Usuarios mnu_user = (Menus_Usuarios) result.get(i);
					destroy(mnu_user);
				}
			}

		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void create(Roles_Usuarios rol_user) {

		em.persist(rol_user);
	}

	/*
	 * public void actualizamosSeguridadUserINUsuarioWithNewRol(Usuario usuario)
	 * { try { StringBuffer sql = newStringBuffer(
	 * "select object(o) from Seguridad_User  as o where o.usuario.id=:id "); //
	 * sql.append(" and o.role is not null "); Query query =
	 * em.createQuery(sql.toString()); query.setParameter("id",
	 * usuario.getId());
	 * 
	 * List<Seguridad_User> seguridad_UserOlds = query.getResultList(); for
	 * (Seguridad_User seguridad_UserOld : seguridad_UserOlds) { //buscamos si
	 * el role existe en el usuario, si no existe, lo borramos StringBuffer sql2
	 * = new StringBuffer("select object(o) from Roles_Usuarios as o");
	 * sql2.append(" where o.usuario.id=").append(usuario.getId());
	 * sql2.append(" and o.role.codigo="
	 * ).append(seguridad_UserOld.getRole().getCodigo()); Query queryNew =
	 * em.createQuery(sql2.toString()); List<Roles_Usuarios> roleUser_Check =
	 * queryNew.getResultList(); if (roleUser_Check == null ||
	 * roleUser_Check.isEmpty()) { //borramos la seguridad_user de ese role
	 * em.merge(seguridad_UserOld); em.remove(seguridad_UserOld); } } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
	public Roles_Usuarios getRole_User(Roles_Usuarios role_user)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		Roles_Usuarios role_oper_user = null;
		try {
			if (role_user.getCodigo() == null) {
				throw new EcologicaExcepciones(
						messagesConf.getString("error_userNoEncontrado"));
			} else {
				role_oper_user = (Roles_Usuarios) em.find(Roles_Usuarios.class,
						role_user.getCodigo());
			}
		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl"));
		}
		return role_oper_user;
	}

	// modificado
	public List findAll_Role_User() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Roles_Usuarios as o");

		return em.createQuery(sql.toString()).getResultList();
	}

	// ________________________________________________________________________________________________________________________://
	// FIN Role_oper_userEJBBean
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// OPERACIONES
	// __________________________________________________________________________________________________________________________//
	/** Creates a new instance of Role_OperacionesEJBBean */
	public void edit(Role_Operaciones operaciones) throws EcologicaExcepciones {
		try {
			getRole_Operaciones(operaciones);
			em.merge(operaciones);
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy(Role_Operaciones operaciones)
			throws EcologicaExcepciones {
		try {
			// em.merge(operaciones);
			operaciones = (Role_Operaciones) em.find(Role_Operaciones.class,
					operaciones.getCodigo());
			em.remove(operaciones);
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	// destroy MODIFICADO
	public void destroy_RolEditando(Role rol) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Seguridad_Role_Lineal as o where o.role.codigo=:cod  ");

			sql.append(" and o.tree is null ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("cod", rol.getCodigo());
			query.executeUpdate();

			// List result = query.getResultList();
			// int size = result.size();
			// for (int i = 0; i < size; i++) {
			// Seguridad_Role_Lineal seguridad_Role_Lineal =
			// (Seguridad_Role_Lineal) result
			// .get(i);
			// em.remove(seguridad_Role_Lineal);
			// }
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy_Rol(Role rol) throws EcologicaExcepciones {
		try {
			StringBuffer sql = new StringBuffer(
					"select object(o) from Seguridad_Role_Lineal as o where o.role.codigo=:cod ");
			sql.append(" and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("cod", rol.getCodigo());
			List result = query.getResultList();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) result
						.get(i);
				// em.merge(seguridad_Role_Lineal);
				em.remove(seguridad_Role_Lineal);
			}
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void create(Role_Operaciones role_operaciones) {
		role_operaciones.setStatus(true);
		em.persist(role_operaciones);
		// actualizamosSeguridadUserINRolWithNewOperaciones(role_operaciones.getRole());
		// actaulizamos las operaciones que tiene role en la tabla de seguridad
	}

	/*
	 * private void actualizamosSeguridadUserINRolWithNewOperaciones(Role role)
	 * { try { Query query =em.createQuery(
	 * "select object(o) from Seguridad_User as o where o.usuario.id=:id and o.tree.nodo=:nodo"
	 * ); query.setParameter(" and o.role.codigo is null ", role.getCodigo());
	 * List<Seguridad_User> seguridad_UserOlds = query.getResultList(); for
	 * (Seguridad_User seguridad_UserOld : seguridad_UserOlds) { //respaldamos
	 * los viejos valorees en un nueva objeto Seguridad_User seguridad_UserMew =
	 * new Seguridad_User();
	 * seguridad_UserMew.setRole(seguridad_UserOld.getRole());
	 * seguridad_UserMew.setTree(seguridad_UserOld.getTree());
	 * seguridad_UserMew.setUsuario(seguridad_UserOld.getUsuario()); //borramos
	 * la seguridad_user de ese role em.merge(seguridad_UserOld);
	 * em.remove(seguridad_UserOld); //buscamos las operaciones de dicho ropl y
	 * la introducuimos al a permisologia del suuareio StringBuffer sql = new
	 * StringBuffer("select object(o) from Role_Operaciones as o");
	 * sql.append(" where o.role.codigo=").append(role.getCodigo()); Query
	 * queryNew = em.createQuery(sql.toString()); List<Role_Operaciones>
	 * role_operNews = queryNew.getResultList(); for (Role_Operaciones r_oNew :
	 * role_operNews) {
	 * seguridad_UserMew.setOperaciones(r_oNew.getOperaciones());
	 * em.persist(seguridad_UserMew); } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	public Role_Operaciones getRole_Operaciones(Role_Operaciones role_user)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		Role_Operaciones role_oper_user = null;
		try {
			if (role_user.getCodigo() == null) {
				throw new EcologicaExcepciones(
						messagesConf.getString("error_userNoEncontrado"));
			} else {
				role_oper_user = (Role_Operaciones) em.find(
						Role_Operaciones.class, role_user.getCodigo());
			}
		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl"));
		}
		return role_oper_user;
	}

	// modificado findAll
	public List findAll_Role_Operaciones() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Role_Operaciones as o");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		return em.createQuery(sql.toString()).getResultList();
	}

	// ________________________________________________________________________________________________________________________://
	// FIN OPERACIONES
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// SEGURIDAD
	// __________________________________________________________________________________________________________________________//
	// *********************************************************************************************************
	// CREAR SOLO PARA LOS PERMISOS DEL MENU
	// modificado create
	public void creat(Menus_Usuarios menus_Usuarios) {
		menus_Usuarios.setStatus(true);
		em.persist(menus_Usuarios);
	}

	public void create(Menus_Usuarios menus_Usuarios) {
		menus_Usuarios.setStatus(true);
		em.persist(menus_Usuarios);
	}

	public void edit(Menus_Usuarios operaciones_Usuarios) {
		em.merge(operaciones_Usuarios);
	}

	public void destroy(Menus_Usuarios operaciones_Usuarios) {
		// em.merge(operaciones_Usuarios);
		operaciones_Usuarios = (Menus_Usuarios) em.find(Menus_Usuarios.class,
				operaciones_Usuarios.getCodigo());
		em.remove(operaciones_Usuarios);
	}

	public List findAllPermMenu(Usuario user) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from Operaciones_Usuarios as o");
		sql.append(" where ");
		sql.append(" o.status=").append(Utilidades.isActivo());
		sql.append(" and ");
		sql.append(" o.usuario.id=:id order by o.usuario.apellido ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("id", user.getId());
		return query.getResultList();
	}

	// *********************************************************************************************************
	public void create(Seguridad_User seguridad_User)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {

		em.persist(seguridad_User);
	}

	public void create(Seguridad_Role seguridad_Role)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {

		em.persist(seguridad_Role);
	}

	public void edit(Seguridad_Role seguridadRole) {

		em.merge(seguridadRole);
	}

	public void edit(Seguridad_User seguridad_User) throws EcologicaExcepciones {
		try {
			getObjeto(seguridad_User);
			em.merge(seguridad_User);
		} catch (Exception e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		}
	}

	public void destroy2(Seguridad_Role_Lineal seguridadRole) {
		// em.merge(seguridadRole);
		seguridadRole = (Seguridad_Role_Lineal) em.find(
				Seguridad_Role_Lineal.class, seguridadRole.getCodigo());

		em.remove(seguridadRole);

		em.flush();
		em.clear();

	}

	public void destroy(Seguridad_Role_Lineal seguridadRole) {

		// obtengo el rol del tree en que me ubico
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_Role_Lineal as o where  o.tree.nodo=:nodo");
		Query query = em.createQuery(sql.toString());
		if (seguridadRole.getTree() != null) {
			query.setParameter("nodo", seguridadRole.getTree().getNodo());
		} else {
			query.setParameter("nodo", -1);
			// System.out.println("seguridadRole.getTree().getNodo()=" + -1);
		}

		// busco el rol del tree
		List result = query.getResultList();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			// por cada seguridad del rol booro operaciones con rol y usuarios
			// en la tabla seguridad_usuarios

			Seguridad_Role_Lineal seguridad_roledelete = (Seguridad_Role_Lineal) result
					.get(i);

			// borramnos donde se utilizo este rol emn operaciones
			// borrarSeguridadUserInRolWithOperaciones(seguridad_roledelete.getRole(),
			// seguridad_roledelete.getTree());
			// ahora si borramos la seguridad del rol
			destroy2(seguridad_roledelete);

		}

	}

	public List<Seguridad_Role_Lineal> findSeguridad_Role_RoleAndTree(
			Seguridad_Role_Lineal seguridadRole) {
		List result = null;
		try {
			// obtengo el rol del tree en que me ubico
			StringBuffer sql = new StringBuffer(
					"select object(o) from Seguridad_Role_Lineal  as o where  o.tree.nodo=:nodo");
			sql.append(" and o.role.codigo=:codigo");
			Query query = em.createQuery(sql.toString());
			query.setParameter("nodo", seguridadRole.getTree().getNodo());
			query.setParameter("codigo", seguridadRole.getRole().getCodigo());
			// busco el rol del tree

			result = query.getResultList();
		} catch (Exception e) {
			result = new ArrayList();
		}

		return result;
	}

	public Seguridad_User getObjeto(Seguridad_User seguridad_User)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		Seguridad_User seguridad = null;
		if (seguridad_User.getCodigo() == null) {
			seguridad_User.setCodigo(0l);
		}

		try {
			seguridad = (Seguridad_User) em.find(Seguridad_User.class,
					seguridad_User.getCodigo());
		} catch (NoResultException e) {
			throw new RoleNoEncontrado(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {
			throw new RoleMultiples(messagesConf.getString("error_userDupl"));
		}
		return seguridad;
	}

	public Seguridad_User findTreeOperUser(Seguridad_User seg) {
		Seguridad_User seguridad_User = new Seguridad_User();
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_User as o");
		sql.append(" where o.usuario.id=:id and o.operaciones.codigo=:codigo and o.tree.nodo=:nodo ");

		if (seg != null && seg.getUsuario() != null) {
			Query query = em.createQuery(sql.toString());
			query.setParameter("id", seg.getUsuario().getId());
			query.setParameter("codigo", seg.getOperaciones().getCodigo());
			query.setParameter("nodo", seg.getTree().getNodo());
			try {
				seguridad_User = (Seguridad_User) query.getSingleResult();
			} catch (NoResultException e) {
				seguridad_User = null;
				// throw new
				// UsuarioNoEncontrado(messagesConf.getString("error_userNoEncontrado")
				// + " " + usuario.getNombre() );
			} catch (NonUniqueResultException e) {
				seguridad_User = new Seguridad_User();
				// throw new
				// UsuarioMultiples(messagesConf.getString("error_userDupl") +
				// usuario.getNombre() );
			}

		}

		return seguridad_User;
	}

	// cambio findAll
	public List findAll_Seguridad_User() {
		return em.createQuery("select object(o) from Seguridad_User as o  ")
				.getResultList();
	}

	public List findAllRole() {
		return em.createQuery("select object(o) from Seguridad_Role as o")
				.getResultList();
	}

	public Seguridad_User Encontrar(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			Seguridad_User prof = new Seguridad_User();
			Query query = em
					.createQuery("select object(o) from Seguridad_User as o where o.codigo=:codigo   ");
			query.setParameter("codigo", new Long(id));
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				prof = (Seguridad_User) it.next();
				return prof;
			}
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Seguridad_User_Lineal> llenarUsuariosInOperacionesVisibles(
			Tree tree) {
		List seguridad_User = new ArrayList();
		if (tree != null) {

			// o.usuario.id=:id
			StringBuffer sql = new StringBuffer(
					"select object(o) from Seguridad_User_Lineal as o where o.tree.nodo=:nodo");
			sql.append(" ");
			sql.append(" order  by lower(o.usuario.apellido) ");
			Query query = em.createQuery(sql.toString());
			query.setParameter("nodo", tree.getNodo());

			try {
				List<Seguridad_User_Lineal> result = query.getResultList();

				int size = result.size();
				for (int i = 0; i < size; i++) {
					Seguridad_User_Lineal seguridad = (Seguridad_User_Lineal) result
							.get(i);
					seguridad_User.add(seguridad);
				}
			} catch (Exception e) {
			}
		}
		return seguridad_User;
	}

	public List buscarTodosLasOperacionesTreeUsuario(Tree tree, Usuario usuario) {
		List operaciones = new ArrayList();
		if (tree != null && usuario != null) {

			StringBuffer sql = new StringBuffer(
					"select object(o) from Seguridad_User_Lineal as o where usuario.id=:id and  o.tree.nodo=:nodo");
			sql.append(" order  by lower(o.usuario.apellido) ");
			Query query = em.createQuery(sql.toString());
			query.setParameter("id", usuario.getId());
			query.setParameter("nodo", tree.getNodo());

			try {
				List result = query.getResultList();
				if (!result.isEmpty()) {
					List<Operaciones> enBd = new ArrayList();
					enBd = findAll_operaciones();
					Seguridad_User_Lineal seguridad_User_Lineal = (Seguridad_User_Lineal) result
							.get(0);
					Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal(
							seguridad_User_Lineal);
					for (Operaciones op : enBd) {
						if (existeOperacionSeguridadLineal(op,
								seguridad_Role_Lineal)) {
							operaciones.add(op);
						}
					}
				}
			} catch (Exception e) {
			}
		}
		return operaciones;
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTree(Tree tree,
			Role role) {
		Query query = em
				.createQuery("select object(o) from Seguridad_Role_Lineal as o where o.role.codigo=:codigo and o.tree.nodo=:nodo");
		query.setParameter("codigo", role.getCodigo());
		query.setParameter("nodo", tree.getNodo());
		List result = query.getResultList();
		List roles = new ArrayList();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) result
					.get(i);
			roles.add(seguridad_Role_Lineal);
		}
		return roles;
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTree(Tree tree) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_Role_Lineal as o where  o.tree.nodo=:nodo");
		// if (tree.getUsuario_creador() != null) {
		//
		// sql.append(" and o.tree.usuario_creador.empresa.nodo=").append(
		// tree.getUsuario_creador().getEmpresa().getNodo());
		// }
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", tree.getNodo());

		List result = query.getResultList();
		List<Seguridad_Role_Lineal> roles = new ArrayList();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) result
					.get(i);
			roles.add(seguridad_Role_Lineal);
		}
		return roles;
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTreeCompleto(Role role) {
		Query query = em
				.createQuery("select object(o) from Seguridad_Role_Lineal as o where  o.role.codigo=:codigo");
		query.setParameter("codigo", role.getCodigo());
		List result = query.getResultList();
		List<Seguridad_Role_Lineal> roles = new ArrayList<Seguridad_Role_Lineal>();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Seguridad_Role_Lineal seguridad_Role_Lineal = (Seguridad_Role_Lineal) result
					.get(i);
			roles.add(seguridad_Role_Lineal);
		}
		em.clear();
		return roles;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN SEGURIDAD
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// TREE
	// __________________________________________________________________________________________________________________________//

	public List findAll_DeQueTipo_Tree(Long queTipo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(queTipo));
		em.clear();
		return query.getResultList();
	}

	public List findAll_DeQueTipo_Tree(Long queTipo, String search) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		if (search != null && search.length() > 0) {
			sql.append(" and lower(o.nombre) like '").append(search)
					.append("%'");
		}

		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(queTipo));
		em.clear();
		return query.getResultList();
	}

	// solo es aplicable al cargo..
	// en el cargo .. se repite mucho por el numero de usuario..
	// el que se agarra como referencia al cargo original, tiene de
	// deBaseToUsuario true

	public List findAllCargos() {
		boolean soloSeMuestraAlUsuario = true;
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.tiponodo=:tiponodo and o.deBaseToUsuario=:deBaseToUsuario  ");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());

		query.setParameter("tiponodo", new Long(Utilidades.getNodoCargo()));
		query.setParameter("deBaseToUsuario", soloSeMuestraAlUsuario);

		return query.getResultList();
	}

	public List findAllCargos(Tree area) {
		StringBuffer sql = new StringBuffer();
		boolean soloSeMuestraAlUsuario = true;
		sql.append("select object(o) from Tree as o ");
		sql.append(" where ");
		sql.append(" o.deBaseToUsuario=:deBaseToUsuario");
		sql.append(" and o.tiponodo=:tiponodo  ");
		sql.append(" and ");
		sql.append(" o.nodopadre=:nodopadre  ");
		sql.append(" and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoCargo()));
		query.setParameter("nodopadre", area.getNodo());
		query.setParameter("deBaseToUsuario", soloSeMuestraAlUsuario);
		return query.getResultList();
	}

	public List findAllEmpresas() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoRaiz()));
		return query.getResultList();
	}

	public List findAllEmpresas(Usuario usuario) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		if (!"root".equalsIgnoreCase(usuario.getLogin())) {

			String passwd = "";
			passwd = usuario.getPassword();
			if (passwd.length() > 15) {
				passwd = usuario.getPassword();
			} else {
				try {
					passwd = EncryptorMD5.getMD5(passwd);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			sql.append("  and o.nodo in ( ").append(
					" select distinct(empresa) from Usuario usu ");
			sql.append(" where usu.status=").append(Utilidades.isActivo())
					.append(" and ").append(" usu.login='")
					.append(usuario.getLogin()).append("' ");
			sql.append(" and ").append(" usu.password='").append(passwd)
					.append("' ");
			sql.append(" ) ");
		}

		sql.append(" order by  lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoRaiz()));

		return query.getResultList();
	}

	public List findAllAreas() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoArea()));
		return query.getResultList();
	}

	public List findAllPrincipal() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoPrincipal()));
		return query.getResultList();
	}

	public List findAllAreas(Tree empresa) {
		StringBuffer sql = new StringBuffer();
		boolean soloSeMuestraAlUsuario = true;
		sql.append("select object(o) from Tree as o ");
		sql.append(" where ");
		// sql.append(" o.deBaseToUsuario=:deBaseToUsuario and ");
		sql.append(" o.tiponodo=:tiponodo  ");
		sql.append(" and ");
		sql.append(" o.nodopadre=:nodopadre  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoArea()));
		query.setParameter("nodopadre", empresa.getNodo());

		return query.getResultList();
	}

	public List findAllTreePadres() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.tiponodo=:tiponodo  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by lower(o.nombre)");

		Query query = em.createQuery(sql.toString());
		query.setParameter("tiponodo", new Long(Utilidades.getNodoRaiz()));
		return query.getResultList();
	}

	public Tree obtenerNodo(Long idRoot) {
		Tree tree = null;
		try {
			if (idRoot != null) {
				tree = em.find(Tree.class, idRoot);
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return tree;
	}

	public Tree findTree(Long idRoot) {
		Tree tree = null;
		try {
			if (idRoot != null) {
				tree = em.find(Tree.class, idRoot);
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return tree;
	}

	// SOLO ME VA AL PADRE .. SI EL PADRE NO ES EL TIPO DE NODO A BUSCAR.. SE
	// HACE RECURSIVO
	public Tree findEnNivelArribaTreeDeAcuerdoSuTipo(Tree tree, long tiponodo) {
		Tree devolver = new Tree();
		// Query query =
		// em.createQuery("select object(o) from Tree as o where o.nodo=:nodo and tiponodo <> :raiz order by o.tiponodo");
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.nodo=:nodopadre ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodopadre", tree.getNodopadre());
		// query.setParameter("raiz",new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		boolean swPrimeraVez = true;
		String nombre = "";
		for (Tree treeBuscar : lista) {

			if (swPrimeraVez) {
				swPrimeraVez = false;
				nombre = treeBuscar.getNombre().trim();
			} else {
				if (nombre.equalsIgnoreCase(treeBuscar.getNombre().trim())) {
					return null;
				}
			}

			if ((treeBuscar.getTiponodo() - tiponodo) == 0) {
				return treeBuscar;
			} else {
				treeBuscar = findEnNivelArribaTreeDeAcuerdoSuTipo(treeBuscar,
						tiponodo, nombre);
				if (treeBuscar == null) {
					return null;
				} else if ((treeBuscar.getTiponodo() - tiponodo) == 0) {
					return treeBuscar;
				}
			}

		}
		em.clear();
		return devolver;
	}

	// SOLO ME VA AL PADRE .. SI EL PADRE NO ES EL TIPO DE NODO A BUSCAR.. SE
	// HACE RECURSIVO
	public Tree findEnNivelArribaTreeDeAcuerdoSuTipo(Tree tree, long tiponodo,
			String nombre) {
		Tree devolver = new Tree();
		// Query query =
		// em.createQuery("select object(o) from Tree as o where o.nodo=:nodo and tiponodo <> :raiz order by o.tiponodo");
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.nodo=:nodopadre ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodopadre", tree.getNodopadre());
		// query.setParameter("raiz",new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();

		for (Tree treeBuscar : lista) {

			if (nombre.equalsIgnoreCase(treeBuscar.getNombre().trim())) {
				return null;
			}

			if ((treeBuscar.getTiponodo() - tiponodo) == 0) {
				return treeBuscar;
			} else {
				treeBuscar = findEnNivelArribaTreeDeAcuerdoSuTipo(treeBuscar,
						tiponodo);
				if (treeBuscar == null) {
					return null;
				} else if ((treeBuscar.getTiponodo() - tiponodo) == 0) {
					return treeBuscar;
				}
			}

		}
		em.clear();
		return devolver;
	}

	public List findAllTreePadresAbuelos(Tree tree) {
		// Query query =
		// em.createQuery("select object(o) from Tree as o where o.nodo=:nodo and tiponodo <> :raiz order by o.tiponodo");
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.nodo=:nodopadre ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodopadre", tree.getNodopadre());
		// query.setParameter("raiz",new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllTreeHijos(long nodo, Usuario usuario) {
		StringBuffer sql = new StringBuffer("");
		/* sql.append("select object(o) from Tree as o ; */

		sql.append("select object(r)  from Tree r,Roles_Usuarios as u ,");

		sql.append("Seguridad_Role as o,Role_Operaciones p ");
		sql.append(", Operaciones q  where o.role.codigo= u.role.codigo");
		sql.append(" and o.role.codigo=p.role.codigo");
		sql.append(" and p.operaciones.codigo=q.codigo");
		sql.append(" and r.nodopadre= ").append(nodo);
		sql.append(" and r.nodo=o.tree.nodo  and r.tiponodo <>  ").append(
				Utilidades.getNodoRaiz());
		sql.append(" and r.tiponodo <>   ").append(
				Utilidades.getNodoDocumento());
		sql.append(" and r.tiponodo <>   ").append(
				Utilidades.getVersionnodoDocumento());
		sql.append("  and r.status=").append(Utilidades.isActivo());
		sql.append(" and u.usuario.id=").append(usuario.getId());
		sql.append(" and  ");
		sql.append("  q.operacion='").append(Utilidades.getToView())
				.append("'");
		/*
		 * sql.append(" and ( ");
		 * sql.append("  r.tiponodo=").append(Utilidades.getNodoRaiz());
		 * sql.append("  or r.tiponodo=").append(Utilidades.getNodoPrincipal());
		 * sql.append("  or r.tiponodo=").append(Utilidades.getNodoArea());
		 * sql.append("  or r.tiponodo=").append(Utilidades.getNodoCargo());
		 * sql.append("  or r.tiponodo=").append(Utilidades.getNodoCarpeta());
		 * sql.append("  or r.tiponodo=").append(Utilidades.getNodoProceso());
		 * sql.append(" )  ");
		 */
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		// System.out.println("sql=" + sql.toString());
		Query query = em.createQuery(sql.toString());

		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllTreeHijos(long nodo, String tipoNodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append(" and tiponodo <>   ").append(Utilidades.getNodoDocumento());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllTreeHijos(long nodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append(" and tiponodo <>   ").append(Utilidades.getNodoDocumento());

		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeHijos(long nodo, long tiponodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append("  and o.tiponodo=").append(tiponodo);
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeHijosSoloPrincipal(long nodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and o.tiponodo=").append(Utilidades.getNodoPrincipal());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(long nodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and ( o.tiponodo=").append(Utilidades.getNodoPrincipal());
		sql.append("  or o.tiponodo=").append(Utilidades.getNodoArea());
		sql.append("  or o.tiponodo=").append(Utilidades.getNodoCargo());
		sql.append("  or o.tiponodo=").append(Utilidades.getNodoProceso());
		sql.append(" ) ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeHijosMenosDocumentos(long nodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and o.tiponodo<>").append(Utilidades.getNodoDocumento());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeHijos(long nodo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by o.tiponodo, lower(o.nombre)");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List findAllHeredaTreeHijosDeleted(long nodo, boolean inactivo) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where o.nodopadre=:nodo and tiponodo <> :raiz  ");
		sql.append("  and o.status=").append(inactivo);
		sql.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(sql.toString());
		query.setParameter("nodo", new Long(nodo));
		query.setParameter("raiz", new Long(Utilidades.getNodoRaiz()));
		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List finTreeByNameAndTipo(Tree tree) {
		StringBuffer str = new StringBuffer();

		str.append("select object(o) from Tree as o where lower(o.nombre)='")
				.append(tree.getNombre().toLowerCase()).append("'");
		str.append(" and o.tiponodo=").append(tree.getTiponodo());
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(str.toString());

		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List finTreeByName(Tree tree) {
		StringBuffer str = new StringBuffer();

		str.append("select object(o) from Tree as o where lower(o.nombre)='")
				.append(tree.getNombre().toLowerCase()).append("'");
		str.append(" and o.tiponodo=").append(tree.getTiponodo());
		str.append(" and o.nodo= ").append(tree.getNodopadre());
		str.append("  and o.status=").append(Utilidades.isActivo());
		str.append(" order by o.tiponodo, lower(o.nombre)");

		Query query = em.createQuery(str.toString());
		// query.setParameter("nombre", tree.getNombre().toLowerCase());
		// query.setParameter("tiponodo", tree.getTiponodo());
		// query.setParameter("nodo", tree.getNodo());

		List<Tree> lista = query.getResultList();
		em.clear();
		return lista;
	}

	public List<Tree> hayRegistros(Tree t, Tree nuevo) {
		StringBuffer sql = new StringBuffer("");
		sql.append(
				" select object(o) from Tree as o where lower(o.nombre)='"
						+ nuevo.getNombre().toLowerCase()).append("'");
		sql.append(" and o.nodo=").append(t.getNodo());
		if (nuevo != null && nuevo.getNodo() != null) {
			sql.append(" and o.nodo <>").append(nuevo.getNodo());
		}

		sql.append("  and  o.status=").append(Utilidades.isActivo());

		List<Tree> ts = queryExecute(sql.toString());
		em.clear();
		return ts;

	}

	public List<Tree> findTreeUsuarioNull(Tree tree) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Tree as o where  o.usuario_creador is null ");
		sql.append("  and o.status=").append(Utilidades.isActivo());

		Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	public void crearTreeEmpresa(Tree tree) {
		tree.setStatus(true);
		tree.setNombre(tree.getNombre().trim());
		em.persist(tree);
	}

	public void crearNuevoTree(Tree tree, Usuario usuario) {
		tree.setStatus(true);
		tree.setNombre(tree.getNombre().trim());
		tree.setUsuario_creador(usuario);
		em.persist(tree);
	}

	public void edit(Tree tree) {
		tree.setNombre(tree.getNombre().trim());
		em.merge(tree);
	}

	public void destroy(Tree tree) {
		// em.merge(tree);
		tree = (Tree) em.find(Tree.class, tree.getNodo());
		em.remove(tree);
	}

	public void actualizaNodoPadreNull() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where o.nodopadre IS NULL or o.nodopadre=0 ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		List lista = query.getResultList();
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			Tree tree = (Tree) it.next();
			tree.setNodopadre(tree.getNodo());
			// em.find(tree.class ,tree.getNodo());
			// em.merge(tree);
		}
	}

	public String construirArbol(boolean swSession) {

		if (swSession) {
			return construirArbol();
		}
		return "";
	}

	public String construirArbol() {
		StringBuffer cad = new StringBuffer();
		try {
			// listamos todas las raices padres
			// String nombreUser = ctx.getCallerPrincipal().getName();
			List arbolPadresLst = findAllTreePadres();

			Iterator arbolPadresIt = arbolPadresLst.iterator();
			char objArbol = 'e';
			while (arbolPadresIt.hasNext()) {
				Tree tree = (Tree) arbolPadresIt.next();

				cad.append(objArbol).append("= new dTree('").append(objArbol)
						.append("');");
				cad.append("\n\r");
				cad.append("\n\r");
				// dTree.prototype.add = function(id, pid, name, url, title,
				// target, icon, iconOpen, open) {
				cad.append(objArbol).append(".add(")
						.append(tree.getNodopadre()).append(",-1,'")
						.append(tree.getNombre()).append("'");
				cad.append(",'./puente.jsf?");
				cad.append("idroot=").append(tree.getNodopadre());
				cad.append("'");

				cad.append(",'','','").append(getImg_raiz()).append("');   ");
				String nodos = getNodosHijos(tree.getNodopadre(), objArbol);
				if ((nodos != null) && (nodos.length() > 0)) {
					cad.append(nodos);
				}
				cad.append("\n\r");
				cad.append(" document.write(").append(objArbol).append(");");
				cad.append("\n\r");
				++objArbol;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("ARBOL="+cad.toString());
		return cad.toString();
	}

	public String getNodosHijos(long nodoPadre, char objArbol) {

		StringBuffer cad2 = new StringBuffer("");
		List nodosHijosLst = findAllTreeHijos(nodoPadre);
		if (nodosHijosLst != null) {

			Iterator nodosHijosIt = nodosHijosLst.iterator();
			while (nodosHijosIt.hasNext()) {
				Tree tree = (Tree) nodosHijosIt.next();

				cad2.append("\n\r");
				cad2.append(objArbol)
						.append(".add(")
						.append(tree.getNodo())
						.append(",")
						.append(tree.getNodopadre())
						.append(",'")
						.append(tree.getNombre() != null ? tree.getNombre()
								.trim() : "Vacio").append("'");
				// ___________________________________________________________________________________________
				// primera pag con parametros
				cad2.append(",'./puente.jsf?");
				cad2.append("idroot=").append(tree.getNodo());
				cad2.append("'");
				// ___________________________________________________________________________________________
				if (tree.getTiponodo() == Utilidades.getNodoPrincipal()) {
					// dTree.prototype.add = function(id, pid, name, url, title,
					// target, icon, iconOpen, open) {
					cad2.append(",'Titulo','','").append(getImg_principal())
							.append("','").append(getImg_principal())
							.append("','").append(getImg_principal())
							.append("');");
					// .append(",'Titulo','','img/globe.gif','img/globe.gif','img/globe.gif');");
				} else if (tree.getTiponodo() == Utilidades.getNodoArea()) {
					cad2.append(",'Titulo','','").append(getImg_area())
							.append("','").append(getImg_area()).append("','")
							.append(getImg_area()).append("');");
				} else if (tree.getTiponodo() == Utilidades.getNodoCargo()) {

					cad2.append(",'Titulo','','").append(getImg_cargo())
							.append("','").append(getImg_cargo()).append("','")
							.append(getImg_cargo()).append("');");
				} else if (tree.getTiponodo() == Utilidades.getNodoProceso()) {

					cad2.append(",'Titulo','','").append(getImg_proceso())
							.append("','").append(getImg_proceso())
							.append("','").append(getImg_proceso())
							.append("');");
				} else if (tree.getTiponodo() == Utilidades.getNodoCarpeta()) {
					cad2.append(",'','','").append(getImg_carpeta())
							.append("');   ");
				} else if (tree.getTiponodo() == Utilidades.getNodoDocumento()) {
					cad2.append(",'Titulo','','").append(getImg_doc())
							.append("','").append(getImg_doc()).append("','")
							.append(getImg_doc()).append("');");
				} else {
					cad2.append(",'Versiones ...','','").append(getImg_doc())
							.append("','").append(getImg_doc()).append("','")
							.append(getImg_doc()).append("');");
				}
				cad2.append("\n\r");

				String nodos = getNodosHijos(tree.getNodo(), objArbol);

				if ((nodos != null) && (nodos.length() > 0)) {
					cad2.append(nodos);
				}
			}
		}

		return cad2.toString();
	}

	public String docTaskPendiente(boolean swSession) {
		if (swSession) {
			return docTaskPendiente();
		}
		return "";
	}

	private String docTaskPendiente() {

		StringBuffer str = new StringBuffer();
		System.out.println("");
		str.append(" d = new dTree('d');");
		System.out.println("");
		str.append(" d.add(0,-1,'Informacion Pendiente del tree ejb');");
		System.out.println("");
		str.append(" d.add(1,0,'Documentos','example01.html');");
		System.out.println("");
		str.append(" d.add(2,1,'Documentos Expirados','example01.html','Documentos','','img/imgfolder.gif','img/imgfolder.gif','img/imgfolder.gif');");
		System.out.println("");
		str.append(" d.add(3,1,'Documentos para Modificar','example01.html','Documentos','','img/imgfolder.gif','img/imgfolder.gif','img/imgfolder.gif');");
		System.out.println("");
		str.append(" d.add(4,0,'Flujos de Trabajo','example01.html');");

		System.out.println("");
		str.append(" d.add(5,4,'Pendientes','example01.html','Documentos','','img/imgfolder.gif','img/imgfolder.gif','img/imgfolder.gif');");
		System.out.println("");

		str.append(" d.add(6,4,'Expirados','example01.html','Documentos','','img/imgfolder.gif','img/imgfolder.gif','img/imgfolder.gif');");
		System.out.println("");
		str.append(" d.add(7,4,'Cancelados','example01.html','Documentos','','img/imgfolder.gif','img/imgfolder.gif','img/imgfolder.gif');	");
		System.out.println("");
		str.append("	d.add(7,4,'Cancelados','example01.html');");
		System.out.println("");
		str.append(" d.add(8,0,'Cuentas de Correo','example01.html');");
		System.out.println("");
		str.append(" d.add(9 , 8 ,'Hotmail', 'http://www.hotmail.com');");
		System.out.println("");
		str.append(" d.add(10, 8 ,'Yahoo', 'http://www.yahoo.com');");
		System.out.println("");
		str.append(" document.write(d)");

		return str.toString();
	}

	public String getImg_principal() {
		return img_principal;
	}

	public void setImg_principal(String img_principal) {
		this.img_principal = img_principal;
	}

	public String getImg_area() {
		return img_area;
	}

	public void setImg_area(String img_area) {
		this.img_area = img_area;
	}

	public String getImg_cargo() {
		return img_cargo;
	}

	public void setImg_cargo(String img_cargo) {
		this.img_cargo = img_cargo;
	}

	public String getImg_proceso() {
		return img_proceso;
	}

	public void setImg_proceso(String img_proceso) {
		this.img_proceso = img_proceso;
	}

	public String getImg_carpeta() {
		return img_carpeta;
	}

	public void setImg_carpeta(String img_carpeta) {
		this.img_carpeta = img_carpeta;
	}

	public String getImg_doc() {
		return img_doc;
	}

	public void setImg_doc(String img_doc) {
		this.img_doc = img_doc;
	}

	public String getImg_raiz() {
		return img_raiz;
	}

	public void setImg_raiz(String img_raiz) {
		this.img_raiz = img_raiz;
	}

	public String getImg_logo() {
		return img_logo;
	}

	public void setImg_logo(String img_logo) {
		this.img_logo = img_logo;
	}

	public String getImg_userLogueado() {
		return img_userLogueado;
	}

	public void setImg_userLogueado(String img_userLogueado) {
		this.img_userLogueado = img_userLogueado;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN TREE
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// ProfesionEJBBean
	// __________________________________________________________________________________________________________________________//
	public void create(Profesion profesion) {
		try {
			profesion.setStatus(true);
			em.persist(profesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(Profesion profesion) {
		try {
			em.merge(profesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy(Profesion profesion) {
		try {
			// em.merge(profesion);
			profesion = (Profesion) em.find(Profesion.class,
					profesion.getCodigo());
			em.remove(profesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Profesion find(Long pk) {
		Profesion prof = null;
		try {
			return (Profesion) em.find(Profesion.class, pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// cambiado Encontrar
	public Profesion Encontrar_Profesion(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			Profesion prof = new Profesion();
			StringBuffer sql = new StringBuffer(
					"select object(o) from Profesion as o where o.codigo=:codigo ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("codigo", new Long(id));
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				prof = (Profesion) it.next();
				return prof;
			}
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// cambiado findAll
	public List findAll_Profesion(Usuario usuario, String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Profesion as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.nombre) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			if (usuario != null && usuario.getEmpresa() != null) {
				sql.append("  and o.empresa.nodo=").append(
						usuario.getEmpresa().getNodo());
			}

			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findAll_Profesion(Usuario usuario) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Profesion as o");
			sql.append("  where o.status=").append(Utilidades.isActivo());
			if (usuario != null && usuario.getEmpresa() != null) {
				sql.append("  and o.empresa.nodo=").append(
						usuario.getEmpresa().getNodo());
			}
			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN ProfesionEJBBean
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// USUARIO
	// __________________________________________________________________________________________________________________________//
	public void create(Usuario usuario) throws EcologicaExcepciones,
			NoSuchAlgorithmException {
		try {
			usuario.setNombre(usuario.getNombre() != null ? usuario.getNombre()
					.trim() : "Vacio");
			usuario.setLogin(usuario.getLogin() != null ? usuario.getLogin()
					.trim() : "Vacio");
			em.persist(usuario);
		} catch (NoResultException e) {
			ServicioLogger.logger.log(Level.INFO,
					"Nuevo Usuario Registrado codigo:" + usuario.getId()
							+ " nombre:" + usuario.getNombre());
		} catch (NonUniqueResultException e2) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userYaExiste"));
		}
	}

	public void edit(Usuario usuario) throws EcologicaExcepciones {
		try {
			usuario.setNombre(usuario.getNombre() != null ? usuario.getNombre()
					.trim() : "Vacio");
			em.merge(usuario);
		} catch (Exception e) {
			System.out.println("e=" + e);
			throw new EcologicaExcepciones(e.toString());
		}
	}

	// CAMBIAR DESTROY
	public void destroy_Usuario(Usuario usuario) throws EcologicaExcepciones {
		try {
			usuario = find_Usuario(usuario);
			// em.merge(usuario);
			em.remove(usuario);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	// CAMBIO find_Usuario
	public Usuario find_Usuario(Object pk) {
		return (Usuario) em.find(Usuario.class, pk);
	}

	public Usuario findLoginOrMailExistaLdap(Usuario usuario) {
		Usuario usua = null;
		String repite = null;
		Usuario user = null;
		String mail = usuario.getMail_principal() != null ? usuario
				.getMail_principal().toLowerCase() : "";
		String login2 = usuario.getLogin() != null ? usuario.getLogin()
				.toLowerCase() : "";
		StringBuffer str = new StringBuffer(
				"select object(o) from Usuario as o where lower(o.login)='"
						+ login2 + "'  ");
		str.append("  and o.status=").append(Utilidades.isActivo());
		if (usuario.getId() != null && usuario.getId() != 0) {
			str.append(" and o.id <>:id");
		}

		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				str.append(" and o.empresa.nodo="
						+ usuario.getEmpresa().getNodo());
			}

		}

		Query query = em.createQuery(str.toString());
		if (usuario.getId() != null && usuario.getId() != 0) {
			query.setParameter("id", usuario.getId());
		}

		List<Usuario> lista = query.getResultList();
		for (Usuario usu : lista) {
			return usu;
		}
		return usua;
	}

	public String findLoginOrMailExista(Usuario usuario) {
		String repite = null;
		Usuario user = null;
		String mail = usuario.getMail_principal() != null ? usuario
				.getMail_principal().toLowerCase() : "";
		String login2 = usuario.getLogin() != null ? usuario.getLogin()
				.toLowerCase() : "";
		StringBuffer str = new StringBuffer(
				"select object(o) from Usuario as o where  (lower(o.mail_principal)='"
						+ mail + "' or lower(o.login)='" + login2 + "' ) ");
		// str.append(" and o.status=:status");
		str.append("  and o.status=").append(Utilidades.isActivo());
		if (usuario.getId() != null && usuario.getId() != 0) {
			str.append(" and o.id <>:id");
		}

		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				str.append(" and o.empresa.nodo="
						+ usuario.getEmpresa().getNodo());
			}

		}

		Query query = em.createQuery(str.toString());
		// query.setParameter("mail",
		// usuario.getMail_principal()!=null?usuario.getMail_principal().toLowerCase():null);
		// query.setParameter("login",usuario.getLogin()!=null?usuario.getLogin().toLowerCase():null);

		// query.setParameter("status", Utilidades.isActivo());
		if (usuario.getId() != null && usuario.getId() != 0) {
			query.setParameter("id", usuario.getId());
		}

		List<Usuario> lista = query.getResultList();
		for (Usuario usu : lista) {
			if (usu.getMail_principal() != null)
				repite = usu.getMail_principal();

			if (usu.getLogin() != null)
				repite += "," + usu.getLogin();
		}
		// int size = lista.size();
		/*
		 * if (!lista.isEmpty() && size > 0) {
		 * 
		 * user = (Usuario) lista.get(0); if (user != null &&
		 * user.getMail_principal() != null) { if (usuario != null &&
		 * usuario.getMail_principal() != null) { if
		 * (user.getMail_principal().equalsIgnoreCase(
		 * usuario.getMail_principal())) { System.out.println("mail:" +
		 * usuario.getMail_principal().toString()); repite =
		 * usuario.getMail_principal().toString(); } else { repite =
		 * usuario.getLogin().toString(); System.out.println("login:" +
		 * usuario.getLogin().toString()); } } } }
		 */
		return repite;
	}

	public Usuario findUsuarioLoginEditar(String login, Usuario usuEditado)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		Usuario user = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			// select object(o) from Usuario as o
			StringBuffer str = new StringBuffer(
					"select object(o) from Usuario as o where lower(o.login)=:login ");
			str.append(" and o.status=:status");
			str.append(" and o.id <>").append(usuEditado.getId());
			// filtramos para solo usuarios de determinada empresa
			if (usuEditado != null
					&& !"root".equalsIgnoreCase(usuEditado.getLogin())) {
				if (usuEditado != null && usuEditado.getEmpresa() != null) {
					str.append(" and o.empresa.nodo="
							+ usuEditado.getEmpresa().getNodo());
				}

			}

			if (usuEditado != null
					&& "root".equalsIgnoreCase(usuEditado.getLogin())
					&& !"root".equalsIgnoreCase(login)) {

				// NO SE PUEDE EDITAR EL root
				return new Usuario();

			}

			Query query = em.createQuery(str.toString());
			query.setParameter("login", usuEditado.getLogin().toLowerCase());
			query.setParameter("status", Utilidades.isActivo());
			usuarios = (List<Usuario>) query.getResultList();
			user = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			user = usuarios.get(0);
			return user;
			/*
			 * throw new
			 * UsuarioMultiples(messagesConf.getString("error_userDupl") +
			 * usuario.getNombre());
			 */
		}

		return user;
	}

	public Usuario findUsuarioLogin(Usuario usuario)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		Usuario user = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			// select object(o) from Usuario as o
			StringBuffer str = new StringBuffer(
					"select object(o) from Usuario as o where o.login=:login and o.password=:password  ");
			str.append(" and o.status=:status");
			// filtramos para solo usuarios de determinada empresa
			if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
				if (usuario != null && usuario.getEmpresa() != null) {
					str.append(" and o.empresa.nodo="
							+ usuario.getEmpresa().getNodo());
				}

			}

			Query query = em.createQuery(str.toString());
			query.setParameter("password", usuario.getPassword());
			query.setParameter("login", usuario.getLogin());
			query.setParameter("status", Utilidades.isActivo());
			usuarios = (List<Usuario>) query.getResultList();
			user = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new UsuarioNoEncontrado(
					messagesConf.getString("error_userNoEncontrado") + " "
							+ usuario.getNombre());
		} catch (NonUniqueResultException e) {
			user = usuarios.get(0);
			return user;
			/*
			 * throw new
			 * UsuarioMultiples(messagesConf.getString("error_userDupl") +
			 * usuario.getNombre());
			 */
		}

		return user;
	}

	public Usuario findUsuarioByMail(Usuario usuario)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		Usuario user = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			// select object(o) from Usuario as o
			StringBuffer str = new StringBuffer(
					"select object(o) from Usuario as o where o.mail_principal=:mail_principal ");
			str.append(" and o.status=:status");
			// filtramos para solo usuarios de determinada empresa
			if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
				if (usuario != null && usuario.getEmpresa() != null) {
					str.append(" and o.empresa.nodo="
							+ usuario.getEmpresa().getNodo());
				}

			}

			Query query = em.createQuery(str.toString());
			query.setParameter("mail_principal", usuario.getMail_principal());
			query.setParameter("status", Utilidades.isActivo());
			usuarios = (List<Usuario>) query.getResultList();
			user = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new UsuarioNoEncontrado(
					messagesConf.getString("error_userNoEncontrado") + " "
							+ usuario.getNombre());
		} catch (NonUniqueResultException e) {
			user = usuarios.get(0);
			return user;
			/*
			 * throw new
			 * UsuarioMultiples(messagesConf.getString("error_userDupl") +
			 * usuario.getNombre());
			 */
		}

		return user;
	}

	public Usuario findUsuarioByCargo(Tree cargo) {
		Usuario user = null;
		try {
			// select object(o) from Usuario as o
			StringBuffer str = new StringBuffer(
					"select object(o) from Usuario as o where o.cargo.nodo="
							+ cargo.getNodo());
			str.append(" and o.status=:status");
			Query query = em.createQuery(str.toString());
			query.setParameter("status", Utilidades.isActivo());
			user = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			// System.out.println(e.toString());
		}

		return user;
	}

	public Usuario getUsuario(Usuario usuario) throws EcologicaExcepciones {
		Usuario user = null;
		try {
			if (usuario.getId() == null) {
				throw new EcologicaExcepciones(
						messagesConf.getString("error_userNoEncontrado"));
			} else {
				user = (Usuario) em.find(Usuario.class, usuario.getId());
			}
		} catch (NoResultException e) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userNoEncontrado"));
		} catch (NonUniqueResultException e2) {
			throw new EcologicaExcepciones(
					messagesConf.getString("error_userDupl"));
		}
		return user;
	}

	// cambiar findAll
	public List findAll_Usuario(Usuario usuario) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Usuario as o   ");
		sql.append("  where o.status=").append(Utilidades.isActivo());

		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append(" and o.empresa.nodo=" + usuario.getEmpresa().getNodo());

		}

		sql.append(" order by lower(o.apellido) ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findExisteNombreRaiz(Tree raiz) {
		String sql = "select object(o) from Tree as o ";
		sql += "  where lower(o.nombre)='" + raiz.getNombre().toLowerCase()
				+ "'";
		sql += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(sql.toString());
		return query.getResultList();

	}

	public List findExisteCargoInUsuario(Usuario usuario) {

		String sql = "select object(o) from Usuario as o,Tree t ";
		sql += "  where o.cargo.nodo=" + usuario.getCargo().getNodo();
		sql += " and o.status=:status";
		sql += " and o.cargo.nodo=t.nodo";
		Query query = em.createQuery(sql.toString());
		query.setParameter("status", Utilidades.isActivo());
		return query.getResultList();
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo, int maximo, int minimo) {

		String sql = "select object(o) from Usuario as o where o.status=:status ";
		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		if (str != null && str.length() > 0) {
			sql += "  and ";
			sql += "  (lower(o.login) like '%" + str.toLowerCase().trim()
					+ "%') ";

			sql += "  ";
		}

		sql += " order by o.apellido";

		// Query query = em.createQuery(sql.toString()).setMaxResults(maximo)
		// .setFirstResult(minimo);
		//
		Query query = em.createQuery(sql.toString()).setMaxResults(maximo)
				.setFirstResult(minimo);

		query.setParameter("status", inactivo);
		return query.getResultList();
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo) {

		String sql = "select object(o) from Usuario as o where o.status=:status ";
		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		if (str != null && str.length() > 0) {
			sql += "  and ";
			sql += "  (lower(o.login) like '%" + str.toLowerCase().trim()
					+ "%') ";
			sql += "  ";
		}

		sql += " order by o.apellido";
		Query query = em.createQuery(sql.toString());
		query.setParameter("status", inactivo);
		return query.getResultList();
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str, char c,
			boolean inactivo, int maximo, int minimo) {
		String sql = "select object(o) from Usuario as o ";
		boolean swStatus = false;
		if (str != null && str.length() > 0) {

			if (Utilidades.getLogin() == c) {
				sql += " where ";
				sql += " ( lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";

				swStatus = true;
			}
			if (Utilidades.getCargo() == c) {
				sql += " where ";
				sql += " ( lower(o.cargo.nombre) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";

				swStatus = true;
			}

			if (Utilidades.getNombre() == c) {
				sql += " where ";
				sql += " ( lower(o.nombre) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";

				swStatus = true;
			}

			if (Utilidades.getApellido() == c) {
				sql += " where ";
				sql += " ( lower(o.apellido) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
			if (!swStatus && str != null && str.length() > 0) {
				sql += " where ";
				sql += "  lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
		}

		if (!swStatus) {
			sql += " where ";
			sql += "  o.status=:status ";
		}

		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		sql += " order by o.apellido";

		Query query = em.createQuery(sql.toString()).setMaxResults(maximo)
				.setFirstResult(minimo);
		query.setParameter("status", inactivo);
		return query.getResultList();
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str, char c,
			boolean inactivo) {
		String sql = "select object(o) from Usuario as o ";
		boolean swStatus = false;
		if (str != null && str.length() > 0) {

			if (Utilidades.getLogin() == c) {
				sql += " where ";
				sql += " ( lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
			if (Utilidades.getCargo() == c) {
				sql += " where ";
				sql += " ( lower(o.cargo.nombre) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}

			if (Utilidades.getNombre() == c) {
				sql += " where ";
				sql += " ( lower(o.nombre) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}

			if (Utilidades.getApellido() == c) {
				sql += " where ";
				sql += " ( lower(o.apellido) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
			if (!swStatus && str != null && str.length() > 0) {
				sql += " where ";
				sql += "  lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
		}

		if (!swStatus) {
			sql += " where ";
			sql += "  o.status=:status ";
		}

		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		sql += " order by o.apellido";

		Query query = em.createQuery(sql.toString());
		query.setParameter("status", inactivo);
		return query.getResultList();
	}

	public List findAll(Usuario usuario, String str) {

		String sql = "select object(o) from Usuario as o where o.status=:status ";
		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		if (str != null && str.length() > 0) {
			sql += "  and ";
			sql += "  (lower(o.login) like '%" + str.toLowerCase().trim()
					+ "%') ";
			sql += "  ";
		}

		sql += " order by o.apellido";
		Query query = em.createQuery(sql.toString());
		query.setParameter("status", Utilidades.isActivo());
		return query.getResultList();
	}

	public List findAll(Usuario usuario, String str, char c) {
		String sql = "select object(o) from Usuario as o ";
		boolean swStatus = false;
		if (str != null && str.length() > 0) {

			if (Utilidades.getLogin() == c) {
				sql += " where ";
				sql += " ( lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
			if (Utilidades.getCargo() == c) {
				sql += " where ";
				sql += " ( lower(o.cargo.nombre) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}

			if (Utilidades.getNombre() == c) {
				sql += " where ";
				sql += " ( lower(o.nombre) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}

			if (Utilidades.getApellido() == c) {
				sql += " where ";
				sql += " ( lower(o.apellido) like '%"
						+ str.toLowerCase().trim() + "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
			if (!swStatus && str != null && str.length() > 0) {
				sql += " where ";
				sql += "  lower(o.login) like '%" + str.toLowerCase().trim()
						+ "%') ";
				sql += " and o.status=:status ";
				swStatus = true;
			}
		}

		if (!swStatus) {
			sql += " where ";
			sql += "  o.status=:status ";
		}

		// filtramos para solo usuarios de determinada empresa
		if (usuario != null && !"root".equalsIgnoreCase(usuario.getLogin())) {
			if (usuario != null && usuario.getEmpresa() != null) {
				sql += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
			}

		}

		sql += " order by o.apellido";

		Query query = em.createQuery(sql.toString());
		query.setParameter("status", Utilidades.isActivo());
		return query.getResultList();
	}

	// cambiar
	public Usuario Encontrar_Usuario(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			Usuario prof = new Usuario();
			Query query = em
					.createQuery("select object(o) from Usuario as o where o.id=:id and o.status=:status ");
			query.setParameter("id", new Long(id));
			query.setParameter("status", Utilidades.isActivo());
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				prof = (Usuario) it.next();
				return prof;
			}
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteSeguridadIndividual2(Usuario usuario, Usuario remplazo) {
		List<Seguridad_User_Lineal> lista = new ArrayList();
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Seguridad_User_Lineal as o ");
		sql.append(" where o.usuario.id=").append(usuario.getId());
		Query query = em.createQuery(sql.toString());
		lista = query.getResultList();
		for (Seguridad_User_Lineal seg : lista) {

			destroy(seg);

		}

	}

	// **************************************************
	// _____________________________________________________________________________________
	public List<Seguridad> soloMenu(Usuario usuario) {
		// BUSCAMOS OPERACIONES DE LOS ROLES DE DICXHO USUARIO
		List<Seguridad_User> listaSacdaDeloRoles = new ArrayList<Seguridad_User>();

		// AQUI BUSCAMOS LA SEGURIDAD_USER A TRAVES DE listaSacdaDeloRoles
		menuSeguridad(usuario, listaSacdaDeloRoles);
		List<Seguridad> lista2 = new ArrayList();

		// AQUI YA LLENAMOS DE FOM MAS GENERALIZADA LA SEGURIDAD Y NOS
		// DEVOLVEMOS
		lista2 = llenarSeguridad(listaSacdaDeloRoles);

		return lista2;
	}

	public Map soloMenuOptimizado(Usuario usuario,
			Map seguridadIndividualPertenesco) {
		// BUSCAMOS OPERACIONES DE LOS ROLES DE DICXHO USUARIO
		List<Seguridad_User> listaSacdaDeloRoles = new ArrayList<Seguridad_User>();

		// SE PASA EL OBJETO POR REFERENCIA listaSacdaDeloRoles
		// AQUI BUSCAMOS LA SEGURIDAD_USER A TRAVES DE listaSacdaDeloRoles
		menuSeguridad(usuario, listaSacdaDeloRoles);

		// SE PASA EL OBJETO POR REFERENCIA seguridadIndividualPertenesco
		// AQUI YA LLENAMOS DE FOM MAS GENERALIZADA LA SEGURIDAD Y NOS
		// DEVOLVEMOS
		llenarSeguridadOptimizadaMenu(listaSacdaDeloRoles,
				seguridadIndividualPertenesco);

		return seguridadIndividualPertenesco;
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadIndividual(
			Usuario usuario, Tree tree) {
		StringBuffer sql = new StringBuffer("");

		sql.append("select object(o) from  Seguridad_User_Lineal as o ");
		sql.append(" where o.usuario.id=:id");
		sql.append("  and o.tree.nodo=").append(tree.getNodo());
		Query query = em.createQuery(sql.toString());
		if (usuario == null) {
			query.setParameter("id", 0L);
		} else {
			query.setParameter("id", usuario.getId());
		}

		List<Seguridad_User_Lineal> lista_seg_user = query.getResultList();
		// TRANSFORMAMOS LA LISTA USUARIO LINEAL EN LISTA USUARIO ROLES CON EL
		// CONSTRUCTOR
		List<Seguridad_Role_Lineal> trasnformamosUserLineal = new ArrayList<Seguridad_Role_Lineal>();
		for (Seguridad_User_Lineal user_seg : lista_seg_user) {
			Seguridad_Role_Lineal seguridad_Role_Linealaux = new Seguridad_Role_Lineal(
					user_seg);
			trasnformamosUserLineal.add(seguridad_Role_Linealaux);
		}
		List<Seguridad_Role_Lineal> listaRoles = buscarSeguridadGrupoPertenece(
				usuario, tree);

		if (trasnformamosUserLineal != null
				&& !trasnformamosUserLineal.isEmpty()) {
			listaRoles.addAll(trasnformamosUserLineal);
		}

		return listaRoles;
	}

	public Map buscarSeguridadIndividualOptimizado(Usuario usuario, Tree tree,
			Map user_seguridad) {
		StringBuffer sql = new StringBuffer("");

		sql.append("select object(o) from  Seguridad_User_Lineal as o ");
		sql.append(" where o.usuario.id=:id");
		sql.append("  and o.tree.nodo=").append(tree.getNodo());
		Query query = em.createQuery(sql.toString());
		if (usuario == null) {
			query.setParameter("id", 0L);
		} else {
			query.setParameter("id", usuario.getId());
		}

		List<Seguridad_User_Lineal> lista_seg_user = query.getResultList();
		// TRANSFORMAMOS LA LISTA USUARIO LINEAL EN LISTA USUARIO ROLES CON EL
		// CONSTRUCTOR

		for (Seguridad_User_Lineal user_seg : lista_seg_user) {
			Seguridad_Role_Lineal seguridad_Role_Linealaux = new Seguridad_Role_Lineal(
					user_seg);
			mySeguridadPropiaInRoles_and_Individual(seguridad_Role_Linealaux,
					user_seguridad, usuario);

		}
		List<Seguridad_Role_Lineal> listaRoles = buscarSeguridadGrupoPertenece(
				usuario, tree);

		if (listaRoles != null && !listaRoles.isEmpty()) {
			for (Seguridad_Role_Lineal seguridad_Role_Linealaux : listaRoles) {

				mySeguridadPropiaInRoles_and_Individual(
						seguridad_Role_Linealaux, user_seguridad, usuario);
			}
		}

		return user_seguridad;
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadIndividual(Usuario usuario) {
		StringBuffer sql = new StringBuffer("");

		sql.append("select object(o) from  Seguridad_User_Lineal as o ");
		sql.append(" where o.usuario.id=:id");

		sql.append("  and  o.tree.tiponodo<>").append(
				Utilidades.getNodoDocumento());

		Query query = em.createQuery(sql.toString());

		query.setParameter("id", usuario.getId());

		List<Seguridad_User_Lineal> lista_seg_user = query.getResultList();
		// TRANSFORMAMOS LA LISTA USUARIO LINEAL EN LISTA USUARIO ROLES CON EL
		// CONSTRUCTOR
		List<Seguridad_Role_Lineal> trasnformamosUserLineal = new ArrayList<Seguridad_Role_Lineal>();
		for (Seguridad_User_Lineal user_seg : lista_seg_user) {

			// seguridad del usuario
			Seguridad_Role_Lineal seguridad_Role_Linealaux = new Seguridad_Role_Lineal(
					user_seg);
			// almacenamos la seguridad del user en una lista
			trasnformamosUserLineal.add(seguridad_Role_Linealaux);
		}

		// seguriudad de los roles dfel usuario
		List<Seguridad_Role_Lineal> listaRoles = buscarSeguridadGrupoPertenece(usuario);

		if (trasnformamosUserLineal != null
				&& !trasnformamosUserLineal.isEmpty()) {
			// almacenamos la seguridad del user que tiene los roles en una
			// lista
			listaRoles.addAll(trasnformamosUserLineal);
		}

		return listaRoles;
	}

	public Map buscarSeguridadIndividualOptimizado(Usuario usuario,
			Map seguridadIndividualPertenesco) {
		StringBuffer sql = new StringBuffer("");

		sql.append("select object(o) from  Seguridad_User_Lineal as o ");
		sql.append(" where o.usuario.id=:id");

		sql.append("  and  o.tree.tiponodo<>").append(
				Utilidades.getNodoDocumento());

		Query query = em.createQuery(sql.toString());

		query.setParameter("id", usuario.getId());

		List<Seguridad_User_Lineal> lista_seg_user = query.getResultList();
		// TRANSFORMAMOS LA LISTA USUARIO LINEAL EN LISTA USUARIO ROLES CON EL
		// CONSTRUCTOR

		for (Seguridad_User_Lineal user_seg : lista_seg_user) {

			// seguridad del usuario
			Seguridad_Role_Lineal seguridad_Role_Linealaux = new Seguridad_Role_Lineal(
					user_seg);

			mySeguridadPropiaInRoles_and_Individual(seguridad_Role_Linealaux,
					seguridadIndividualPertenesco, usuario);
			// almacenamos la seguridad del user en una lista
			// trasnformamosUserLineal.add(seguridad_Role_Linealaux);
		}

		// seguriudad de los roles dfel usuario
		List<Seguridad_Role_Lineal> listaRoles = buscarSeguridadGrupoPertenece(usuario);
		for (Seguridad_Role_Lineal seguridad_Role_Linealaux : listaRoles) {
			// AQUI HAY QUE NAVERGAR HASTA ROOT SI TIENE PERMISOS VER EN TREE
			// DEL ROL Y HACER HERENCIA..
			
			if (seguridad_Role_Linealaux.isToView()) {
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				seguridad_User_Lineal.setUsuario(usuario);
				seguridad_User_Lineal.setTree(seguridad_Role_Linealaux.getTree());
				seguridad_User_Lineal.setToView(true);
				darViewNodoHastaRaizInMemoria(seguridad_User_Lineal,
						seguridadIndividualPertenesco);
			}

			// FIN--------------------------------------
			// AQUI HAY QUE NAVERGAR HASTA ROOT SI TIENE PERMISOS VER EN TREE
			// DEL ROL Y HACER HERENCIA..


			mySeguridadPropiaInRoles_and_Individual(seguridad_Role_Linealaux,
					seguridadIndividualPertenesco, usuario);

		}

		return seguridadIndividualPertenesco;
	}

	public void mySeguridadPropiaInRoles_and_Individual(
			Seguridad_Role_Lineal seg2, Map seguridadIndividualPertenesco,
			Usuario user_logueado) {
		Seguridad seg = null;
		// el arbol donde estoy ubicado
		Tree tree = null;

		// hacemos la conversion...
		// try {
		// System.out.println("seg2.getRole().getNombre()="
		// + seg2.getRole().getNombre());
		// System.out.println("seg2.getCodigo()=" + seg2.getCodigo());
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		seg = new Seguridad(seg2);

		tree = obtenerNodo(seg.getNodo());

		// s en este arbol no esta la segurida, la coloco como venga
		if (seguridadIndividualPertenesco != null
				&& !seguridadIndividualPertenesco.containsKey(tree)) {

			seguridadIndividualPertenesco.put(tree, seg);

		} else {
			// repaso la segurida para ver si cambio y la actualizo en el
			// arbol
			if (seguridadIndividualPertenesco == null) {
				seguridadIndividualPertenesco = soloMenuOptimizado(
						user_logueado, seguridadIndividualPertenesco);
				// getMySeguridad(user_logueado);
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

				segNodo.setToImprimirAutorizacion(seg.isToImprimirAdministrar());
			}

			// _______________________________________________
			// si todavia es falso, coloco la seguridad del tree haber si
			// cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToSolicitudImpresion()) {

				segNodo.setToSolicitudImpresion(seg.isToSolicitudImpresion());
			}

			if (!segNodo.isToSolicitudImpresion0()) {

				segNodo.setToSolicitudImpresion0(seg.isToSolicitudImpresion0());
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

				segNodo.setToListarDiasFeriados(seg.isToListarDiasFeriados());
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
				segNodo.setToListGruposWorkflow(seg.isToListGruposWorkflow());
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
				segNodo.setToListTipoDocumentos(seg.isToListTipoDocumentos());
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

	public void menuSeguridad(Usuario usuario, List<Seguridad_User> lista) {

		// BUSCAMOS LOS ROLES DE DICHO USUARIO
		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select object(o)  from Roles_Usuarios as o where o.usuario.id=")
				.append(usuario.getId());

		Query query = em.createQuery(sql.toString());
		List<Roles_Usuarios> role_users = query.getResultList();
		for (Roles_Usuarios role_user : role_users) {
			// PARA MENU
			sql = new StringBuffer("");
			sql.append(
					"select object(o) from Menus_Usuarios as o where o.role.codigo=")
					.append(role_user.getRole().getCodigo());
			sql.append("  and o.status=").append(Utilidades.isActivo());

			Query query2 = em.createQuery(sql.toString());
			List<Menus_Usuarios> menus_Usuarios = query2.getResultList();
			for (Menus_Usuarios menus_Usuario : menus_Usuarios) {
				Role role = menus_Usuario.getRole();

				descomponeRoleForSeguridad_User(role, usuario, lista);
			}

		}
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadGrupoPertenece(
			Usuario usuario, Tree tree) {
		List<Seguridad_Role_Lineal> todos = new ArrayList();
		// BUSCAMOS LOS ROLES DE DICHO USUARIO
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o)  from Seguridad_Role_Lineal as o,Roles_Usuarios as p where ");
		if (usuario == null) {
			sql.append(" p.usuario.id=0");
		} else {
			sql.append(" p.usuario.id=").append(usuario.getId());
		}

		sql.append(" and o.role.codigo=p.role.codigo and o.tree.nodo=").append(
				tree.getNodo());
		// System.out.println("observar->" + sql.toString());
		Query query = em.createQuery(sql.toString());
		List<Seguridad_Role_Lineal> role_users = query.getResultList();
		if (role_users != null && !role_users.isEmpty()) {
			todos.addAll(role_users);
		}

		// for (Seguridad_Role_Lineal r : todos) {
		// System.out
		// .println("-------------------ROLE-------------------------------------");
		// System.out.println("r.getRole().getNombre()="
		// + r.getRole().getNombre());
		// System.out.println("r.TREE.getNombre()=" + r.getTree().getNombre());
		// System.out.println("r.isToView()=" + r.isToView());
		// System.out.println("r.isToDoRegistros()=" + r.isToDoRegistros());
		//
		// }

		return todos;
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadGrupoPertenece(
			Usuario usuario) {

		List<Seguridad_Role_Lineal> todos = new ArrayList();
		// BUSCAMOS LOS ROLES DE DICHO USUARIO
		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select object(o)  from Seguridad_Role_Lineal as o,Roles_Usuarios as p where p.usuario.id=")
				.append(usuario.getId());
		sql.append(" and o.role.codigo=p.role.codigo and o.tree is not null");
		sql.append("  and  o.tree.tiponodo<>").append(
				Utilidades.getNodoDocumento());

		Query query = em.createQuery(sql.toString());
		List<Seguridad_Role_Lineal> role_users = query.getResultList();
		todos.addAll(role_users);

		return todos;
	}

	// descomponer solo en roles
	// ___________________________________________________________________
	// PARA EL MENU

	private void descomponeRoleForSeguridad_User(Role role, Usuario usuario,
			List<Seguridad_User> lista) {
		// buscamos un tree ficticio para rellenar
		// raices empkiezan desder -999
		Tree tree = new Tree();

		long raicesNegativasMenu = Utilidades.getRaicesNegativasMenu();
		StringBuffer sql = new StringBuffer("");

		sql.append(
				"select object(o) from Seguridad_Role_Lineal as o where o.role.codigo=")
				.append(role.getCodigo());
		// HAY ALGUNAS VECES DONDE EKL TREE NO ES NULO
		// sql.append(" and tree is null and o.status=").append(
		// Utilidades.isActivo());
		// FIN HAY ALGUNAS VECES DONDE EKL TREE NO ES NULO
		Query query = em.createQuery(sql.toString());
		List<Role_Operaciones> r_ops = query.getResultList();

		// las operaciones en roles
		// se encuentra vacia
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
		seguridad_Role_Lineal.setRole(role);
		// BUSCAMOS LOS PERMISOS PARA MENUS EN LINEAL
		List<Seguridad_Role_Lineal> listaSegRols = buscarTodosLasOperacionesRolSoloMenu(seguridad_Role_Lineal);
		if (listaSegRols != null && !listaSegRols.isEmpty()) {
			seguridad_Role_Lineal = listaSegRols.get(0);
			List<Operaciones> enBd = findAll_operaciones();
			for (Operaciones r_op : enBd) {
				if (existeOperacionSeguridadLineal(r_op, seguridad_Role_Lineal)) {
					Seguridad_User seg_user = new Seguridad_User();

					seg_user.setOperaciones(r_op);
					seg_user.setUsuario(usuario);
					tree.setNodo(raicesNegativasMenu);
					seg_user.setTree(tree);

					lista.add(seg_user);
					++raicesNegativasMenu;
				}

			}
		}

	}

	private void descomponeRoleForSeguridad_User(Tree tree, Role role,
			Usuario usuario, List<Seguridad_User> lista) {

		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select object(o) from Role_Operaciones as o where o.role.codigo=")
				.append(role.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		// System.out.println("sql="+sql.toString());
		Query query = em.createQuery(sql.toString());
		List<Role_Operaciones> r_ops = query.getResultList();

		for (Role_Operaciones r_op : r_ops) {

			// llenamos lista por referencia
			Seguridad_User seg_user = new Seguridad_User();
			seg_user.setOperaciones(r_op.getOperaciones());
			seg_user.setUsuario(usuario);
			seg_user.setTree(tree);
			// System.out.println("treeHijo="+treeHijo.getNombre());

			lista.add(seg_user);
			// darViewNodoHastaRaiz(tree,usuario,r_op,lista);

		}

		// return listaSeguser;
	}

	public void darViewNodoHastaRaiz(Tree tree, Usuario usuario,
			Role_Operaciones r_op, List<Seguridad_User> lista) {
		Seguridad_User seg_user = null;

		if (Utilidades.getToView().equalsIgnoreCase(
				r_op.getOperaciones().getOperacion())) {
			boolean swHayPadre = true;
			seg_user = new Seguridad_User();

			Tree treeHijo = new Tree();
			treeHijo = tree;
			seg_user.setOperaciones(r_op.getOperaciones());
			seg_user.setUsuario(usuario);
			seg_user.setTree(treeHijo);
			// System.out.println("treeHijo="+treeHijo.getNombre());

			lista.add(seg_user);
			while (swHayPadre) {

				seg_user = new Seguridad_User();

				treeHijo = llegarHastadAbuelosPadres(treeHijo);
				seg_user.setOperaciones(r_op.getOperaciones());
				seg_user.setUsuario(usuario);
				seg_user.setTree(treeHijo);
				// System.out.println("treeHijo="+treeHijo.getNombre());

				lista.add(seg_user);
				swHayPadre = treeHijo.getTiponodo() != Utilidades.getNodoRaiz();
			}
		} else {
			seg_user = new Seguridad_User();

			seg_user.setOperaciones(r_op.getOperaciones());
			seg_user.setUsuario(usuario);
			seg_user.setTree(tree);

			/*
			 * System.out.println("r_op.getOperaciones()="+r_op.getOperaciones().
			 * getOperacion());
			 * System.out.println("usuario="+usuario.getNombre());
			 * System.out.println("tree.getNombre()="+tree.getNombre());
			 */
			lista.add(seg_user);
		}

	}

	private Tree llegarHastadAbuelosPadres(Tree padre) {
		List abuelos = findAllTreePadresAbuelos(padre);
		Iterator it = abuelos.iterator();
		if (it.hasNext()) {
			Tree tree = (Tree) it.next();
			if (!tree.equals(padre)) {
				return tree;
			} else {
				// para que salga del ciclo
				padre.setTiponodo(Utilidades.getNodoRaiz());
				return padre;
			}
		} else {
			// para que salga del ciclo
			padre.setTiponodo(Utilidades.getNodoRaiz());

			return padre;
		}
	}

	// FIN descomponer solo en roles
	// ___FIN________________________________________________________________
	// INVERSA OPERACIONES Y ROLES

	// ESTA ES CUANDO ESTAMOS EDITANDO UN ROL, LLENAMOIS LOS PERMISOS DE LAS
	// OPERACIONES EM SEGURIDAD LINEAL
	public boolean existeOperacionSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		boolean swOp = false;
		String operacion = seg_user.getOperacion();

		if (Utilidades.getToviewcomentpublic().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToViewComentPublic()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTodownload().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDownload()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTodoflowrevision().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDoFlowRevision()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTolistararea().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListarArea()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getConfseguridadglobal().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isConfSeguridadGlobal()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTosolicitudimpresion().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToSolicitudImpresion()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTosolicitudimpresion0().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToSolicitudImpresion0()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToimprimirautorizacion().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToImprimirAdministrar()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.gettoPlantillaInDocFlowParalelo().equalsIgnoreCase(
				operacion)
				&& seguridad_Role_Lineal.istoPlantillaInDocFlowParalelo()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTomodflow().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModFlow()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getTolistgruposworkflow().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListGruposWorkflow()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToadddocumentosvn().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddDocumentoSvn()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToView().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToView()) {
			swOp = true;
			return swOp;
		}

		// control tiempo
		if (Utilidades.getToListarDiasFeriados().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListarDiasFeriados()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToListarDiasHabiles().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListarDiasHabiles()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToAddDiaFeriado().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddDiaFeriado()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModDiasHabiles().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModDiasHabiles()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModDiaFeriado().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModDiaFeriado()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToDelDiaFeriado().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDelDiaFeriado()) {
			swOp = true;
			return swOp;

		}

		// ______________

		if (Utilidades.getToListUsuarios().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListUsuarios()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToAddUsuario().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddUsuario()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModUsuario().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModUsuario()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToDelUsuario().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDelUsuario()) {
			swOp = true;
			return swOp;

		}

		if (Utilidades.getToListProfesiones().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListProfesiones()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToAddProfesiones().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddProfesiones()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModProfesiones().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModProfesiones()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDelProfesiones().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDelProfesiones()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToListGrupos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListGrupos()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToAddGrupos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddGrupos()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModGrupos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModGrupos()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDelGrupos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDelGrupos()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToListTipoDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToListTipoDocumentos()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToAddTipoDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddTipoDocumentos()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToModTipoDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToModTipoDocumentos()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDelTipoDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDelTipoDocumentos()) {
			swOp = true;
			return swOp;
		}

		// RAIZ
		if (Utilidades.getToAddRaiz().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddRaiz()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToMove().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToMove()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToMod().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToMod()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDel().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDel()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getConfSeguridad().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isConfSeguridad()) {
			swOp = true;
			return swOp;
		}
		// PRINCIPAL
		if (Utilidades.getToAddPrincipal().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddPrincipal()) {
			swOp = true;
			return swOp;
		}

		// AREA
		if (Utilidades.getToAddArea().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddArea()) {
			swOp = true;
			return swOp;
		}

		// Cargo
		if (Utilidades.getToAddCargo().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddCargo()) {
			swOp = true;
			return swOp;
		}

		// PROCESO
		if (Utilidades.getToAddProceso().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddProceso()) {
			swOp = true;
			return swOp;
		}

		// Carpeta
		if (Utilidades.getToAddCarpeta().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddCarpeta()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToAddDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddDocumentos()) {
			swOp = true;
			return swOp;
		}

		// DOCUMENTOS
		if (Utilidades.getToAddLotesDeDocumentos().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToAddLotesDeDocumentos()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToDoFlow().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDoFlow()) {
			swOp = true;
			return swOp;

		}

		if (Utilidades.getToVincDoc().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToVincDoc()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDoRegistros().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDoRegistros()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToDoPublico().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToDoPublico()) {
			swOp = true;
			return swOp;
		}
		//
		if (Utilidades.getToSaveDataBasic().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToSaveDataBasic()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToGivePermUser().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToGivePermUser()) {
			swOp = true;
			return swOp;
		}
		if (Utilidades.getToActivePermGroup().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToActivePermGroup()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToHistFlow().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToHistFlow()) {
			swOp = true;
			return swOp;
		}

		if (Utilidades.getToHistDoc().equalsIgnoreCase(operacion)
				&& seguridad_Role_Lineal.isToHistDoc()) {
			swOp = true;
			return swOp;
		}
		return swOp;
	}

	// ESTA ES CUANDO ESTAMOS EDITANDO UN ROL, LLENAMOIS LOS PERMISOS DE LAS
	// OPERACIONES EM SEGURIDAD LINEAL
	public void llenarSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal) {

		String operacion = seg_user.getOperacion();

		if (Utilidades.getToviewcomentpublic().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToViewComentPublic(true);
		}

		if (Utilidades.getTodownload().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDownload(true);
		}

		if (Utilidades.getTodoflowrevision().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDoFlowRevision(true);
		}

		if (Utilidades.getTolistararea().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListarArea(true);
		}

		if (Utilidades.getConfseguridadglobal().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setConfSeguridadGlobal(true);
		}

		if (Utilidades.getTosolicitudimpresion().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToSolicitudImpresion(true);
		}

		if (Utilidades.getTosolicitudimpresion0().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToSolicitudImpresion0(true);
		}

		if (Utilidades.getToimprimirautorizacion().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToImprimirAutorizacion(true);
		}

		if (Utilidades.gettoPlantillaInDocFlowParalelo().equalsIgnoreCase(
				operacion)) {
			seguridad_Role_Lineal.settoPlantillaInDocFlowParalelo(true);
		}

		if (Utilidades.getTomodflow().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToModFlow(true);
		}

		if (Utilidades.getToView().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToView(true);
		}

		if (Utilidades.getTolistgruposworkflow().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListGruposWorkflow(true);
		}

		if (Utilidades.getToadddocumentosvn().equalsIgnoreCase(operacion)) {
			// System.out.println(".......AddDocumentos....................");
			seguridad_Role_Lineal.setToAddDocumentoSvn(true);
		}

		/*
		 * private static final String toListarDiasFeriados=""; private static
		 * final String toListarDiasHabiles=""; private static final String
		 * toAddDiaFeriado=""; private static final String toModDiasHabiles="";
		 * private static final String toModDiaFeriado=""; private static final
		 * String toDelDiaFeriado="";
		 */
		// CONTROL DE TIEMPO
		if (Utilidades.getToListarDiasFeriados().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListarDiasFeriados(true);
		}
		if (Utilidades.getToListarDiasHabiles().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListarDiasHabiles(true);
		}

		if (Utilidades.getToAddDiaFeriado().equalsIgnoreCase(operacion)) {
			// System.out.println("......AddUsuario.....................");
			seguridad_Role_Lineal.setToAddDiaFeriado(true);

		}

		if (Utilidades.getToModDiasHabiles().equalsIgnoreCase(operacion)) {
			// System.out.println(".......ModUsuario....................");
			seguridad_Role_Lineal.setToModDiasHabiles(true);
		}
		if (Utilidades.getToModDiaFeriado().equalsIgnoreCase(operacion)) {
			// System.out.println(".......ModUsuario....................");
			seguridad_Role_Lineal.setToModDiaFeriado(true);
		}

		if (Utilidades.getToDelDiaFeriado().equalsIgnoreCase(operacion)) {
			// System.out.println("......DelUsuario.....................");
			seguridad_Role_Lineal.setToDelDiaFeriado(true);
		}
		// ------------------FIN CONTROL TIME

		if (Utilidades.getToListUsuarios().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListUsuarios(true);
		}
		if (Utilidades.getToAddUsuario().equalsIgnoreCase(operacion)) {
			// System.out.println("......AddUsuario.....................");
			seguridad_Role_Lineal.setToAddUsuario(true);

		}

		if (Utilidades.getToModUsuario().equalsIgnoreCase(operacion)) {
			// System.out.println(".......ModUsuario....................");
			seguridad_Role_Lineal.setToModUsuario(true);
		}

		if (Utilidades.getToDelUsuario().equalsIgnoreCase(operacion)) {
			// System.out.println("......DelUsuario.....................");
			seguridad_Role_Lineal.setToDelUsuario(true);
		}

		if (Utilidades.getToListProfesiones().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListProfesiones(true);
		}
		if (Utilidades.getToAddProfesiones().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddProfesiones(true);
		}

		if (Utilidades.getToModProfesiones().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToModProfesiones(true);
		}
		if (Utilidades.getToDelProfesiones().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDelProfesiones(true);
		}

		if (Utilidades.getToListGrupos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListGrupos(true);
		}

		if (Utilidades.getToAddGrupos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddGrupos(true);
		}

		if (Utilidades.getToModGrupos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToModGrupos(true);
		}
		if (Utilidades.getToDelGrupos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDelGrupos(true);
		}

		if (Utilidades.getToListTipoDocumentos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToListTipoDocumentos(true);
		}
		if (Utilidades.getToAddTipoDocumentos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddTipoDocumentos(true);
		}

		if (Utilidades.getToModTipoDocumentos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToModTipoDocumentos(true);
		}
		if (Utilidades.getToDelTipoDocumentos().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDelTipoDocumentos(true);
		}

		// RAIZ
		if (Utilidades.getToAddRaiz().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddRaiz(true);
		}

		if (Utilidades.getToMove().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToMove(true);
		}
		if (Utilidades.getToMod().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToMod(true);
		}
		if (Utilidades.getToDel().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToDel(true);
		}
		if (Utilidades.getConfSeguridad().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setConfSeguridad(true);
		}
		// PRINCIPAL
		if (Utilidades.getToAddPrincipal().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddPrincipal(true);
		}

		// AREA
		if (Utilidades.getToAddArea().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddArea(true);
		}

		// Cargo
		if (Utilidades.getToAddCargo().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddCargo(true);
		}

		// PROCESO
		if (Utilidades.getToAddProceso().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddProceso(true);
		}

		// Carpeta
		if (Utilidades.getToAddCarpeta().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToAddCarpeta(true);
		}

		if (Utilidades.getToAddDocumentos().equalsIgnoreCase(operacion)) {
			// System.out.println(".......AddDocumentos....................");
			seguridad_Role_Lineal.setToAddDocumentos(true);
		}

		// DOCUMENTOS
		if (Utilidades.getToAddLotesDeDocumentos().equalsIgnoreCase(operacion)) {
			// System.out.println(".......AddLotesDeDocumentos....................");
			seguridad_Role_Lineal.setToAddLotesDeDocumentos(true);
		}

		if (Utilidades.getToDoFlow().equalsIgnoreCase(operacion)) {
			// System.out.println(".......DoFlow....................");

			seguridad_Role_Lineal.setToDoFlow(true);

		}

		if (Utilidades.getToVincDoc().equalsIgnoreCase(operacion)) {
			// System.out.println(".......VincDoc....................");
			seguridad_Role_Lineal.setToVincDoc(true);
		}
		if (Utilidades.getToDoRegistros().equalsIgnoreCase(operacion)) {
			// System.out.println(".......DoRegistros....................");
			seguridad_Role_Lineal.setToDoRegistros(true);
		}
		if (Utilidades.getToDoPublico().equalsIgnoreCase(operacion)) {
			// System.out.println(".......DoPublico....................");
			seguridad_Role_Lineal.setToDoPublico(true);
		}

		//
		if (Utilidades.getToSaveDataBasic().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToSaveDataBasic(true);
		}

		if (Utilidades.getToGivePermUser().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToGivePermUser(true);
		}
		if (Utilidades.getToActivePermGroup().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToActivePermGroup(true);
		}

		// Hsitorico

		if (Utilidades.getToHistFlow().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToHistFlow(true);
		}

		if (Utilidades.getToHistDoc().equalsIgnoreCase(operacion)) {
			seguridad_Role_Lineal.setToHistDoc(true);
		}
	}

	public List<Seguridad> llenarSeguridad(List<Seguridad_User> listaSeg) {
		List lista = new ArrayList();

		int i = -1;
		for (Seguridad_User seg_user : listaSeg) {
			++i;

			// while (permisoUser.next()) {
			Seguridad seg = new Seguridad();
			// simulamos el jdbc por un string
			String operacion = seg_user.getOperaciones().getOperacion();
			String nodo = String.valueOf(seg_user.getTree().getNodo());

			seg.setNodo(Long.parseLong(nodo));

			if (Utilidades.getToviewcomentpublic().equalsIgnoreCase(operacion)) {
				seg.setToViewComentPublic(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTodownload().equalsIgnoreCase(operacion)) {
				seg.setToDownload(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTodoflowrevision().equalsIgnoreCase(operacion)) {
				seg.setToDoFlowRevision(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTolistararea().equalsIgnoreCase(operacion)) {
				seg.setToListarArea(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getConfseguridadglobal().equalsIgnoreCase(operacion)) {
				seg.setConfSeguridadGlobal(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTosolicitudimpresion()
					.equalsIgnoreCase(operacion)) {
				seg.setToSolicitudImpresion(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTosolicitudimpresion0().equalsIgnoreCase(
					operacion)) {
				seg.setToSolicitudImpresion0(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToimprimirautorizacion().equalsIgnoreCase(
					operacion)) {
				seg.setToImprimirAutorizacion(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.gettoPlantillaInDocFlowParalelo().equalsIgnoreCase(
					operacion)) {

				seg.settoPlantillaInDocFlowParalelo(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTomodflow().equalsIgnoreCase(operacion)) {
				seg.setToModFlow(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToView().equalsIgnoreCase(operacion)) {
				seg.setToView(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getTolistgruposworkflow()
					.equalsIgnoreCase(operacion)) {
				seg.setToListGruposWorkflow(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToadddocumentosvn().equalsIgnoreCase(operacion)) {
				// System.out.println(".......AddDocumentos....................");
				seg.setToAddDocumentoSvn(true);
				lista.add(seg);
				continue;
			}

			// CONTROL DE TIEMPO
			if (Utilidades.getToListarDiasFeriados()
					.equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListarDiasFeriados(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToListarDiasHabiles().equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListarDiasHabiles(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToAddDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println("......AddUsuario.....................");
				seg.setToAddDiaFeriado(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToModDiasHabiles().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModDiasHabiles(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToModDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModDiaFeriado(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToDelDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println("......DelUsuario.....................");
				seg.setToDelDiaFeriado(true);
				lista.add(seg);
				continue;
			}
			// FIN CONTROL DE TIEMPO

			if (Utilidades.getToListUsuarios().equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListUsuarios(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToAddUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println("......AddUsuario.....................");
				seg.setToAddUsuario(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToModUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModUsuario(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToDelUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println("......DelUsuario.....................");
				seg.setToDelUsuario(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToListProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToListProfesiones(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToAddProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToAddProfesiones(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToModProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToModProfesiones(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDelProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToDelProfesiones(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToListGrupos().equalsIgnoreCase(operacion)) {
				seg.setToListGrupos(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToAddGrupos().equalsIgnoreCase(operacion)) {
				seg.setToAddGrupos(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToModGrupos().equalsIgnoreCase(operacion)) {
				seg.setToModGrupos(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDelGrupos().equalsIgnoreCase(operacion)) {
				seg.setToDelGrupos(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToListTipoDocumentos()
					.equalsIgnoreCase(operacion)) {
				seg.setToListTipoDocumentos(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToAddTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToAddTipoDocumentos(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToModTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToModTipoDocumentos(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDelTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToDelTipoDocumentos(true);
				lista.add(seg);
				continue;
			}

			// RAIZ
			if (Utilidades.getToAddRaiz().equalsIgnoreCase(operacion)) {
				seg.setToAddRaiz(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToMove().equalsIgnoreCase(operacion)) {
				seg.setToMove(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToMod().equalsIgnoreCase(operacion)) {
				seg.setToMod(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDel().equalsIgnoreCase(operacion)) {
				seg.setToDel(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getConfSeguridad().equalsIgnoreCase(operacion)) {
				seg.setConfSeguridad(true);
				lista.add(seg);
				continue;
			}
			// PRINCIPAL
			if (Utilidades.getToAddPrincipal().equalsIgnoreCase(operacion)) {
				seg.setToAddPrincipal(true);
				lista.add(seg);
				continue;
			}

			// AREA
			if (Utilidades.getToAddArea().equalsIgnoreCase(operacion)) {
				seg.setToAddArea(true);
				lista.add(seg);
				continue;
			}

			// Cargo
			if (Utilidades.getToAddCargo().equalsIgnoreCase(operacion)) {
				seg.setToAddCargo(true);
				lista.add(seg);
				continue;
			}

			// PROCESO
			if (Utilidades.getToAddProceso().equalsIgnoreCase(operacion)) {
				seg.setToAddProceso(true);
				lista.add(seg);
				continue;
			}

			// Carpeta
			if (Utilidades.getToAddCarpeta().equalsIgnoreCase(operacion)) {
				seg.setToAddCarpeta(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToAddDocumentos().equalsIgnoreCase(operacion)) {
				// System.out.println(".......AddDocumentos....................");
				seg.setToAddDocumentos(true);
				lista.add(seg);
				continue;
			}

			// DOCUMENTOS
			if (Utilidades.getToAddLotesDeDocumentos().equalsIgnoreCase(
					operacion)) {
				// System.out.println(".......AddLotesDeDocumentos....................");
				seg.setToAddLotesDeDocumentos(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToDoFlow().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoFlow....................");

				seg.setToDoFlow(true);

				lista.add(seg);

				continue;
			}

			if (Utilidades.getToVincDoc().equalsIgnoreCase(operacion)) {
				// System.out.println(".......VincDoc....................");
				seg.setToVincDoc(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDoRegistros().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoRegistros....................");
				seg.setToDoRegistros(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToDoPublico().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoPublico....................");
				seg.setToDoPublico(true);
				lista.add(seg);
				continue;
			}

			//
			if (Utilidades.getToSaveDataBasic().equalsIgnoreCase(operacion)) {
				seg.setToSaveDataBasic(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToGivePermUser().equalsIgnoreCase(operacion)) {
				seg.setToGivePermUser(true);
				lista.add(seg);
				continue;
			}
			if (Utilidades.getToActivePermGroup().equalsIgnoreCase(operacion)) {
				seg.setToActivePermGroup(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToHistDoc().equalsIgnoreCase(operacion)) {
				seg.setToHistDoc(true);
				lista.add(seg);
				continue;
			}

			if (Utilidades.getToHistFlow().equalsIgnoreCase(operacion)) {
				seg.setToHistFlow(true);
				lista.add(seg);
				continue;
			}
		}
		// System.out.println("fin.. de llenar seguridad................");
		return lista;
	}

	public void llenarSeguridadOptimizadaMenu(List<Seguridad_User> listaSeg,
			Map seguridadIndividualPertenesco) {

		int i = -1;
		for (Seguridad_User seg_user : listaSeg) {
			++i;

			// while (permisoUser.next()) {
			Seguridad seg = new Seguridad();
			// simulamos el jdbc por un string
			String operacion = seg_user.getOperaciones().getOperacion();
			String nodo = String.valueOf(seg_user.getTree().getNodo());

			seg.setNodo(Long.parseLong(nodo));

			if (Utilidades.getToviewcomentpublic().equalsIgnoreCase(operacion)) {
				seg.setToViewComentPublic(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTodownload().equalsIgnoreCase(operacion)) {
				seg.setToDownload(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTodoflowrevision().equalsIgnoreCase(operacion)) {
				seg.setToDoFlowRevision(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTolistararea().equalsIgnoreCase(operacion)) {
				seg.setToListarArea(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getConfseguridadglobal().equalsIgnoreCase(operacion)) {
				seg.setConfSeguridadGlobal(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTosolicitudimpresion()
					.equalsIgnoreCase(operacion)) {
				seg.setToSolicitudImpresion(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTosolicitudimpresion0().equalsIgnoreCase(
					operacion)) {
				seg.setToSolicitudImpresion0(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToimprimirautorizacion().equalsIgnoreCase(
					operacion)) {
				seg.setToImprimirAutorizacion(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.gettoPlantillaInDocFlowParalelo().equalsIgnoreCase(
					operacion)) {

				seg.settoPlantillaInDocFlowParalelo(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTomodflow().equalsIgnoreCase(operacion)) {
				seg.setToModFlow(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToView().equalsIgnoreCase(operacion)) {
				seg.setToView(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getTolistgruposworkflow()
					.equalsIgnoreCase(operacion)) {
				seg.setToListGruposWorkflow(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToadddocumentosvn().equalsIgnoreCase(operacion)) {
				// System.out.println(".......AddDocumentos....................");
				seg.setToAddDocumentoSvn(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// CONTROL DE TIEMPO
			if (Utilidades.getToListarDiasFeriados()
					.equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListarDiasFeriados(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToListarDiasHabiles().equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListarDiasHabiles(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToAddDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println("......AddUsuario.....................");
				seg.setToAddDiaFeriado(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToModDiasHabiles().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModDiasHabiles(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToModDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModDiaFeriado(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToDelDiaFeriado().equalsIgnoreCase(operacion)) {
				// System.out.println("......DelUsuario.....................");
				seg.setToDelDiaFeriado(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			// FIN CONTROL DE TIEMPO

			if (Utilidades.getToListUsuarios().equalsIgnoreCase(operacion)) {
				// System.out.println("........ListUsuarios...................");
				seg.setToListUsuarios(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToAddUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println("......AddUsuario.....................");
				seg.setToAddUsuario(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToModUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println(".......ModUsuario....................");
				seg.setToModUsuario(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToDelUsuario().equalsIgnoreCase(operacion)) {
				// System.out.println("......DelUsuario.....................");
				seg.setToDelUsuario(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToListProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToListProfesiones(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToAddProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToAddProfesiones(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToModProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToModProfesiones(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDelProfesiones().equalsIgnoreCase(operacion)) {
				seg.setToDelProfesiones(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToListGrupos().equalsIgnoreCase(operacion)) {
				seg.setToListGrupos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToAddGrupos().equalsIgnoreCase(operacion)) {
				seg.setToAddGrupos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToModGrupos().equalsIgnoreCase(operacion)) {
				seg.setToModGrupos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDelGrupos().equalsIgnoreCase(operacion)) {
				seg.setToDelGrupos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToListTipoDocumentos()
					.equalsIgnoreCase(operacion)) {
				seg.setToListTipoDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToAddTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToAddTipoDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToModTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToModTipoDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDelTipoDocumentos().equalsIgnoreCase(operacion)) {
				seg.setToDelTipoDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// RAIZ
			if (Utilidades.getToAddRaiz().equalsIgnoreCase(operacion)) {
				seg.setToAddRaiz(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToMove().equalsIgnoreCase(operacion)) {
				seg.setToMove(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToMod().equalsIgnoreCase(operacion)) {
				seg.setToMod(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDel().equalsIgnoreCase(operacion)) {
				seg.setToDel(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getConfSeguridad().equalsIgnoreCase(operacion)) {
				seg.setConfSeguridad(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);

				continue;
			}
			// PRINCIPAL
			if (Utilidades.getToAddPrincipal().equalsIgnoreCase(operacion)) {
				seg.setToAddPrincipal(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// AREA
			if (Utilidades.getToAddArea().equalsIgnoreCase(operacion)) {
				seg.setToAddArea(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// Cargo
			if (Utilidades.getToAddCargo().equalsIgnoreCase(operacion)) {
				seg.setToAddCargo(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// PROCESO
			if (Utilidades.getToAddProceso().equalsIgnoreCase(operacion)) {
				seg.setToAddProceso(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// Carpeta
			if (Utilidades.getToAddCarpeta().equalsIgnoreCase(operacion)) {
				seg.setToAddCarpeta(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToAddDocumentos().equalsIgnoreCase(operacion)) {
				// System.out.println(".......AddDocumentos....................");
				seg.setToAddDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			// DOCUMENTOS
			if (Utilidades.getToAddLotesDeDocumentos().equalsIgnoreCase(
					operacion)) {
				// System.out.println(".......AddLotesDeDocumentos....................");
				seg.setToAddLotesDeDocumentos(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToDoFlow().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoFlow....................");

				seg.setToDoFlow(true);

				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);

				continue;
			}

			if (Utilidades.getToVincDoc().equalsIgnoreCase(operacion)) {
				// System.out.println(".......VincDoc....................");
				seg.setToVincDoc(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDoRegistros().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoRegistros....................");
				seg.setToDoRegistros(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToDoPublico().equalsIgnoreCase(operacion)) {
				// System.out.println(".......DoPublico....................");
				seg.setToDoPublico(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			//
			if (Utilidades.getToSaveDataBasic().equalsIgnoreCase(operacion)) {
				seg.setToSaveDataBasic(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToGivePermUser().equalsIgnoreCase(operacion)) {
				seg.setToGivePermUser(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
			if (Utilidades.getToActivePermGroup().equalsIgnoreCase(operacion)) {
				seg.setToActivePermGroup(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToHistDoc().equalsIgnoreCase(operacion)) {
				seg.setToHistDoc(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}

			if (Utilidades.getToHistFlow().equalsIgnoreCase(operacion)) {
				seg.setToHistFlow(true);
				seguridadMenuOptimizada(seg, seguridadIndividualPertenesco);
				continue;
			}
		}

	}

	public void seguridadMenuOptimizada(Seguridad seg,
			Map seguridadIndividualPertenesco) {

		// el arbol donde estoy ubicado
		Tree tree = null;
		// si los nodos son negativos, implica que viene para la
		// seguridad del menu
		if (seg.getNodo() < 0) {
			// buscam,os para el menu
			tree = new Tree();
			tree.setNodo(Utilidades.getNodoRaiz());
		} else {
			tree = obtenerNodo(seg.getNodo());
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

			// _______________________________________________
			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isConfSeguridadGlobal()) {
				segNodo.setConfSeguridadGlobal(seg.isConfSeguridadGlobal());
			}

			// _______________________________________________
			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToImprimirAdministrar()) {
				segNodo.setToImprimirAutorizacion(seg.isToImprimirAdministrar());
			}

			// _______________________________________________
			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToSolicitudImpresion()) {
				segNodo.setToSolicitudImpresion(seg.isToSolicitudImpresion());
			}

			if (!segNodo.isToSolicitudImpresion0()) {
				segNodo.setToSolicitudImpresion0(seg.isToSolicitudImpresion0());
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

				segNodo.setToListarDiasFeriados(seg.isToListarDiasFeriados());
			}
			if (!segNodo.isToListarDiasHabiles()) {

				segNodo.setToListarDiasHabiles(seg.isToListarDiasHabiles());
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
				segNodo.setToListGruposWorkflow(seg.isToListGruposWorkflow());
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
				segNodo.setToListTipoDocumentos(seg.isToListTipoDocumentos());
			}
			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToAddTipoDocumentos()) {
				segNodo.setToAddTipoDocumentos(seg.isToAddTipoDocumentos());
			}

			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToModTipoDocumentos()) {
				segNodo.setToModTipoDocumentos(seg.isToModTipoDocumentos());
			}

			// si todavia es falso, coloco la seguridad del tree haber
			// si cambia
			// si es verdaero, la dejo quieta
			if (!segNodo.isToDelTipoDocumentos()) {
				segNodo.setToDelTipoDocumentos(seg.isToDelTipoDocumentos());
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

	private Connection getConnection() {
		Connection conoracle2 = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conoracle2 = DriverManager.getConnection(
					"jdbc:oracle:thin:@saymon:1521:ORCL", "ECOLOGICAL",
					"ECOLOGICAL");
		} catch (Exception exception) {
			exception.toString();
		}
		return conoracle2;
	}

	private void comunExecutejDBC(String sql, List lista) {
		Connection conoracle2 = null;
		ResultSet result;
		try {
			conoracle2 = getConnection();
			PreparedStatement ps = conoracle2.prepareStatement(sql.toString());

			result = ps.executeQuery();

			ps.close();
		} catch (Exception e) {
			e.toString();
		} finally {
			if (conoracle2 != null) {
				try {
					conoracle2.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		// return result;
	}

	public void persist(Object object) {
		em.persist(object);
	}

	// ________________________________________________________________________________________________________________________://
	// FIN USUARIO
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// HISTORICO
	// __________________________________________________________________________________________________________________________//
	public void create(FlowsHistorico flowHist) {
		try {
			flowHist.setStatus(true);
			em.persist(flowHist);
		} catch (Exception e) {
			e.toString();
		}
	}

	public void edit(FlowsHistorico flowhist) throws EcologicaExcepciones {
		try {
			em.merge(flowhist);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public FlowsHistorico find(FlowsHistorico flowhist) {
		FlowsHistorico flowsHistorico = null;
		try {
			return (FlowsHistorico) em.find(FlowsHistorico.class,
					flowhist.getCodigo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void destroy(FlowsHistorico flowhist) throws EcologicaExcepciones {
		try {
			flowhist = find(flowhist);
			// em.merge(flowhist);
			em.remove(flowhist);
		} catch (Exception e) {
			throw new EcologicaExcepciones(e.toString());
		}
	}

	public List findAll_FlowsHistoricoUserCreados(Usuario user) {
		StringBuffer sql = new StringBuffer(
				" select object(h) from FlowsHistorico as h  ");
		sql.append(" , Flow as f,Flow_Participantes as o ");
		sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
		// sql.append(" and (o.participante.id=").append(user.getId()).append("  or ").append(" f.duenio.id=").append(user.getId()).append(" ) ");
		sql.append(" and ( f.duenio.id=").append(user.getId()).append("   ")
				.append(" ) ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowParaleloUserCreados(Usuario user) {
		StringBuffer sql = new StringBuffer(
				" select object(h) from FlowParalelo as h  ");
		sql.append(" , Flow as f,Flow_Participantes as o ");
		sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
		// sql.append(" and (o.participante.id=").append(user.getId()).append("  or ").append(" f.duenio.id=").append(user.getId()).append(" ) ");
		sql.append(" and ( f.duenio.id=").append(user.getId()).append("   ")
				.append(" ) ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowsHistoricoUser(Usuario user) {
		StringBuffer sql = new StringBuffer(
				" select object(h) from FlowsHistorico as h  ");
		sql.append(" , Flow as f,Flow_Participantes as o ");
		sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
		// sql.append(" and (o.participante.id=").append(user.getId()).append("  or ").append(" f.duenio.id=").append(user.getId()).append(" ) ");
		sql.append(" and ( o.participante.id=").append(user.getId())
				.append("   ").append(" ) ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowParaleloUser(Usuario user) {
		StringBuffer sql = new StringBuffer(
				" select object(h) from FlowParalelo as h  ");
		sql.append(" , Flow as f,Flow_Participantes as o ");
		sql.append(" where h.flow.codigo=f.codigo and f.codigo=o.flow.codigo ");
		// sql.append(" and (o.participante.id=").append(user.getId()).append("  or ").append(" f.duenio.id=").append(user.getId()).append(" ) ");
		sql.append(" and ( o.participante.id=").append(user.getId())
				.append("   ").append(" ) ");
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append("  and f.status=").append(Utilidades.isActivo());
		sql.append("  and h.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");

		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowsHistoricoDoc_detalle(Doc_detalle doc) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from FlowsHistorico as o ");
		sql.append(" where o.doc_detalle.codigo=").append(doc.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowsHistoricoFlow(Flow flow) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from FlowsHistorico as o ");
		sql.append(" where o.flow.codigo=").append(flow.getCodigo());
		// sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");
		List lista = null;
		try {
			lista = em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			lista = new ArrayList();
		}
		return lista;
	}

	public List findAll_FlowsHistorico(Doc_maestro doc) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from FlowsHistorico as o ");
		sql.append(" where o.doc_detalle.doc_maestro.codigo=").append(
				doc.getCodigo());
		// sql.append("  and o.status=").append(Utilidades.isActivo());
		sql.append("  and o.doc_detalle.doc_maestro.doc_tipo.formatoTipoDoc <>")
				.append(Utilidades.getFlujoparalelotipodoc());
		sql.append(" order by  o.flow.fecha_creado desc ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAll_FlowsHistorico() {
		StringBuffer sql = new StringBuffer(
				"select object(o) from FlowsHistorico as o ");
		sql.append("  where  o.status=").append(Utilidades.isActivo());
		sql.append(" order by  o.flow.fecha_creado desc ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public Doc_detalle findDetalleDocumewntoPublico(Doc_maestro doc) {
		// AHORA CREAMOS EL DOCUMENTO DETALLE
		StringBuffer sqlDocDetalle = new StringBuffer("");
		sqlDocDetalle
				.append("select object(o) from Doc_detalle as o where o.doc_maestro.codigo=")
				.append(doc.getCodigo());
		sqlDocDetalle.append("  and  o.status=").append(Utilidades.isActivo());
		sqlDocDetalle.append(" and o.doc_estado.codigo=").append(
				Utilidades.getVigente());
		sqlDocDetalle.append(" order by o.codigo desc");

		Query query = em.createQuery(sqlDocDetalle.toString());

		Doc_detalle d = (Doc_detalle) query.getSingleResult();
		// List<Doc_detalle> ds=queryExecute(sqlDocDetalle.toString());
		return d;

	}

	public Doc_detalle findDetalleDocumewntoBusqueda(Doc_maestro doc,
			String doc_estadoId, String usuarioId, String parametro) {
		// AHORA CREAMOS EL DOCUMENTO DETALLE
		StringBuffer sqlDocDetalle = new StringBuffer("");
		sqlDocDetalle
				.append("select object(o) from Doc_detalle as o where o.doc_maestro.codigo=")
				.append(doc.getCodigo());
		sqlDocDetalle.append("  and  o.status=").append(Utilidades.isActivo());
		// estos parametros solo para busquedas
		if (doc_estadoId != null && !isEmptyOrNull(doc_estadoId)) {
			sqlDocDetalle.append(" and o.doc_estado.codigo=" + doc_estadoId);
		}
		// estos parametros solo para busquedas
		if (usuarioId != null && !isEmptyOrNull(usuarioId)) {
			sqlDocDetalle.append(" and o.duenio.id=" + usuarioId);
		}

		if ("publico".equalsIgnoreCase(parametro)) {
			sqlDocDetalle.append(" and o.doc_estado.codigo=").append(
					Utilidades.getVigente());
		}

		sqlDocDetalle.append(" order by o.codigo desc");

		Query query = em.createQuery(sqlDocDetalle.toString());
		Doc_detalle d = (Doc_detalle) query.getSingleResult();
		// List<Doc_detalle> ds=queryExecute(sqlDocDetalle.toString());
		return d;

	}

	public List<Doc_detalle> findDetallesListarDocumewntos(Doc_maestro doc,
			String doc_estadoId, String usuarioId, String parametro) {
		// AHORA CREAMOS EL DOCUMENTO DETALLE
		StringBuffer sqlDocDetalle = new StringBuffer("");
		sqlDocDetalle
				.append("select object(o) from Doc_detalle as o where o.doc_maestro.codigo=")
				.append(doc.getCodigo());
		sqlDocDetalle.append("  and  o.status=").append(Utilidades.isActivo());
		// estos parametros solo para busquedas
		if (doc_estadoId != null && !isEmptyOrNull(doc_estadoId)) {
			sqlDocDetalle.append(" and o.doc_estado.codigo=" + doc_estadoId);
		}
		// estos parametros solo para busquedas
		if (usuarioId != null && !isEmptyOrNull(usuarioId)) {
			sqlDocDetalle.append(" and o.duenio.id=" + usuarioId);
		}

		if ("publico".equalsIgnoreCase(parametro)) {
			sqlDocDetalle.append(" and o.doc_estado.codigo=").append(
					Utilidades.getVigente());
		}

		sqlDocDetalle.append(" order by o.codigo desc");

		List<Doc_detalle> ds = queryExecute(sqlDocDetalle.toString());
		return ds;

	}

	public List queryFecha_Doc_maestro(String str, Date fechaparametro) {
		Query query = em.createQuery(str);
		query.setParameter("fechaparametro", fechaparametro,
				TemporalType.TIMESTAMP);
		return query.getResultList();
	}

	public List queryExecute(String str, Date fechaDesde, Date fechaHasta) {

		Query query = em.createQuery(str);
		query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);

		return query.setMaxResults(Utilidades.getMaxRegisterMostrar())
				.getResultList();
	}

	public List queryExecute(String str, Date fechaparametro) {

		Query query = em.createQuery(str);
		// query.setParameter("fechaparametro", fechaparametro,
		// TemporalType.TIMESTAMP);

		return query.setMaxResults(Utilidades.getMaxRegisterMostrar())
				.getResultList();
	}

	public List queryExecute(String query, int mayor, int menor,
			List cuantosLista) {
		// Query q1 = em.createQuery(query.toString());
		// cuantosLista=q1.getResultList();

		Query q = em.createQuery(query.toString());
		q.setMaxResults(mayor).setFirstResult(menor);
		List lista = q.getResultList();
		em.clear();
		return lista;
	}

	public List queryExecute(String query, int mayor, int menor) {
		Query q = em.createQuery(query.toString());
		q.setMaxResults(mayor).setFirstResult(menor);
		List lista = q.getResultList();
		em.clear();
		return lista;
	}

	/*
	 * select count(dt.codigo) cuantos,dt.nombre from doc_tipo dt, doc_maestro
	 * dm where dt.codigo=dm.doc_tipo group by dt.nombre order by dt.nombre asc;
	 */

	public List queryExecute(String query) {

		return em.createQuery(query.toString()).getResultList();
	}

	public boolean isEmptyOrNull(String valor) {
		return (valor == null || valor.trim().length() == 0
				|| valor.trim().equalsIgnoreCase("null") || valor.trim()
				.equalsIgnoreCase("#000000"));
	}

	public List<Usuario> findUsuario_Profesion(Profesion profesion) {
		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select object(o) from Usuario as o where  o.profesion.codigo=")
				.append(profesion.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		// System.out.println("sql="+sql.toString());
		Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	public void deleteTreenewDuenio(Usuario old, Usuario nuevo) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Tree as o where  o.nodopadre=");
		sql.append(old.getCargo().getNodo());
		Query query = em.createQuery(sql.toString());
		List<Tree> lista = query.getResultList();
		for (Tree tree : lista) {
			tree.setNodopadre(nuevo.getCargo().getNodo());
			edit(tree);

			// HISTORICO
			com.ecological.paper.historico.Historico hist = new com.ecological.paper.historico.Historico();
			hist.setStatus(true);
			hist.setFecha_accion(fechaActual());
			hist.setTipo_accion(Utilidades.getUsuarioEliminado());
			hist.setTree_origen(old.getCargo());
			hist.setTree_anterior(old.getCargo());
			hist.setTree_new(nuevo.getCargo());
			hist.setUsuario_anterior(old);
			hist.setUsuario_new(nuevo);
			create(hist);

		}
		Tree tree2 = obtenerNodo(old.getCargo().getNodo());
		// si el usuario no forma parte del cargo que es base para crear
		// usuarios, se elimina..
		if (!tree2.isDeBaseToUsuario()) {
			tree2.setStatus(false);
			edit(tree2);
		}

		// return roles;
	}

	// ________________________________________________________________________________________________________________________://
	// HISTORICO
	// __________________________________________________________________________________________________________________________//

	public void create(Historico hist) {
		hist.setStatus(true);
		em.persist(hist);
	}

	public void edit(Historico hist) {
		em.merge(hist);
	}

	public void destroy(Historico hist) {

		hist = findHistorico(hist);
		// em.merge(hist);
		em.remove(hist);

	}

	public Historico findHistorico(Historico hist) {
		return (Historico) em.find(Historico.class, hist.getCodigo());
	}

	public List<Historico> findAllHistorico(Historico hist, Usuario usuario) {

		String str = "select object(o) from Historico as o where o.tipo_accion="
				+ hist.getTipo_accion();
		str += "  and o.status=" + Utilidades.isActivo();
		str += "  and o.usuario_anterior.id=" + usuario.getId();
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Historico> findAllHistoricoTree(Historico hist, Tree tree) {

		String str = "select object(o) from Historico as o where ";
		str += "  o.status=" + Utilidades.isActivo();
		str += "  and (o.tree_origen.nodo=" + tree.getNodo();
		str += " or o.tree_origen.nodopadre=" + tree.getNodo();
		str += ") ";
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Historico> findAllHistoricoTreeEliminados(Tree tree) {

		String str = "select object(o) from Historico as o where ";
		str += "  o.status=" + Utilidades.isActivo();
		str += "  and (o.tree_origen.nodo=" + tree.getNodo();
		str += " or o.tree_origen.nodopadre=" + tree.getNodo();
		str += ") ";
		str += " and o.tipo_accion=" + Utilidades.getTreeEliminado();
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Historico> findAllHistoricoTreeMoverNodo(Tree tree) {

		String str = "select object(o) from Historico as o where ";
		str += "  o.status=" + Utilidades.isActivo();
		str += "  and (o.tree_origen.nodo=" + tree.getNodo();
		str += " or o.tree_origen.nodopadre=" + tree.getNodo();
		str += ") ";
		str += " and o.tipo_accion=" + Utilidades.getTreeMoverNodo();
		str += " order by o.codigo desc";
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Historico> findAllHistorico(Historico hist) {
		// return em.createQuery("").getResultList();
		String str = "select object(o) from Historico as o where o.tipo_accion="
				+ hist.getTipo_accion();
		str += "  and o.status=" + Utilidades.isActivo();
		str += " and o.tipo_accion=" + hist.getTipo_accion();
		str += " order by o.fecha_accion desc";
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public Date fechaActual() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	// ________________________________________________________________________________________________________________________://
	// FIN HISTORICO
	// __________________________________________________________________________________________________________________________//

	// ________________________________________________________________________________________________________________________://
	// Seguridad_Role
	// __________________________________________________________________________________________________________________________//
	public void create(Seguridad_Role_Lineal seg) {
		// verificamos si existe
		String str = "select object(o) from Seguridad_Role_Lineal as o where o.role.codigo="
				+ seg.getRole().getCodigo();

		if (seg != null && seg.getTree() != null) {
			str += " and ";
			str += "   o.tree.nodo=" + seg.getTree().getNodo();
		} else {
			str += " and ";
			str += "   o.tree is null";
		}
		str += " and o.status=" + Utilidades.isActivo();
		// System.out.println("sqlGrabar=" + str);

		Query query = em.createQuery(str.toString());
		if (query.getResultList() == null || query.getResultList().isEmpty()) {
			seg.setStatus(true);
			em.persist(seg);
			em.flush();
			em.clear();
		} else {
			if (seg.isToView()) {
				seg = (Seguridad_Role_Lineal) query.getResultList().get(0);
				seg.setToView(true);
				em.merge(seg);
			}

		}
	}

	public void edit(Seguridad_Role_Lineal seg) {
		em.merge(seg);
		em.flush();
		em.clear();
	}

	public Seguridad_Role_Lineal findSeguridad_Role_Lineal(
			Seguridad_Role_Lineal seg) {
		return (Seguridad_Role_Lineal) em.find(Seguridad_Role_Lineal.class,
				seg.getCodigo());
	}

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(Tree tree) {
		String str = "select object(o) from Seguridad_Role_Lineal as o where o.tree.nodo="
				+ tree.getNodo();
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(Role role) {
		String str = "select object(o) from Seguridad_Role_Lineal as o where o.role.codigo="
				+ role.getCodigo();
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public void seguridadForRolesByTree(List<Role> roleLst, Tree tree,
			boolean heredaSeguridad) {

		for (Role role : roleLst) {

			if (role != null && role instanceof Role) {

				// damos permiso al papa

				if (heredaSeguridad) {
					boolean swEliminar = false;

					Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
					seguridad_Role_Lineal.setRole(role);

					// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU
					// SEGURIDA
					List<Seguridad_Role_Lineal> seguridad_Role_LinealList = findAllSeguridad_Role_Identificador(role);
					if (seguridad_Role_LinealList != null
							&& !seguridad_Role_LinealList.isEmpty()) {
						seguridad_Role_Lineal = seguridad_Role_LinealList
								.get(0);

						// /no se coloca setTree porque si no, lo graba, en el
						// mismno registro
					}
					// para que sea totalmente nuevo y no traiga el primary key
					// viejo
					Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
							seguridad_Role_Lineal);
					seguridad_Role_Lineal2.setRole(role);
					seguridad_Role_Lineal2.setTree(tree);

					create(seguridad_Role_Lineal2);
					try {
						heredarRolePermiso(seguridad_Role_Lineal2, swEliminar,
								tree.getTiponodo());
					} catch (EcologicaExcepciones e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// givePermparaverToGroup(seguridad_Role_Lineal2);

					if (seguridad_Role_Lineal2.isToView()) {
						HashMap unico = new HashMap();
						unico.put(tree.getNodo(), tree);
						recursivoRoleAbuelo(tree, role, unico);
					}

				} else {
					Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();
					seguridad_Role_Lineal.setRole(role);
					seguridad_Role_Lineal.setTree(tree);
					// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS, BUSCAMOS SU
					// SEGURIDA
					List<Seguridad_Role_Lineal> seguridad_Role_LinealList = findAllSeguridad_Role_Identificador(role);
					if (seguridad_Role_LinealList != null
							&& !seguridad_Role_LinealList.isEmpty()) {
						seguridad_Role_Lineal = seguridad_Role_LinealList
								.get(0);

					}
					// EXCEPCION CON LA SEGURIDAD EN SESSION DEL USUARIO AUI,
					// POR LO GNERAL
					// S3E COLOCA EN EXITOSO EN EL CONSTRUCTOR
					// para que sea totalmente nuevo y no traiga el primary key
					// viejo
					Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
							seguridad_Role_Lineal);
					seguridad_Role_Lineal2.setRole(role);
					seguridad_Role_Lineal2.setTree(tree);
					// grabarRole_Tree(seguridad_Role_Lineal2);
					create(seguridad_Role_Lineal2);
					if (seguridad_Role_Lineal2.isToView()) {
						HashMap unico = new HashMap();
						unico.put(tree.getNodo(), tree);
						recursivoRoleAbuelo(tree, role, unico);
					}

					// EN CASO QUE FIN EL GRUPO TENGA UN HIJO CONM PERMISOS VER,
					// ESTE HIJO DEBE COOCAR A TOSOS
					// SUS PADRES CON PERMISOS VER
					// create(seguridad_Role_Lineal2);
					// givePermparaverToGroup(seguridad_Role_Lineal2);
				}

			}

		}
	}

	private void recursivoRoleAbuelo(Tree tree, Role role, HashMap unico) {
		Seguridad_Role_Lineal segRolAbuelo = null;
		List<Tree> abuelos = findAllTreePadresAbuelos(tree);

		for (Tree t : abuelos) {
			System.out.println("nodo=" + t.getNodo());
			System.out.println("nombre=" + t.getNombre());
			segRolAbuelo = new Seguridad_Role_Lineal();
			segRolAbuelo.setRole(role);
			segRolAbuelo.setTree(t);
			segRolAbuelo.setToView(true);
			create(segRolAbuelo);
			if (!unico.containsKey(t.getNodo())) {
				unico.put(t.getNodo(), tree);
				recursivoRoleAbuelo(t, role, unico);
			}

			// EN CASO QUE EL GRUPO TENGA UN HIJO CONM PERMISOS
			// VER, ESTE HIJO DEBE COOCAR A TOSOS
			// SUS PADRES CON PERMISOS VER

		}

	}

	// todsa la seguridad del tree con sus roles

	public void heredarRolePermiso(Seguridad_Role_Lineal seguridad_Role_Lineal,
			boolean swElimina, long tipoNodo) throws EcologicaExcepciones {

		if (swElimina) {
			// elimina todos los ro,es de este tree

			destroy(seguridad_Role_Lineal);

		} else {

			List<Seguridad_Role_Lineal> lista = findSeguridad_Role_RoleAndTree(seguridad_Role_Lineal);
			// si es nula o vacia la grabamos
			if (lista == null || lista.isEmpty()) {
				// para que sea totalmente nuevo y no traiga el primary key
				// viejo
				Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
						seguridad_Role_Lineal);

				create(seguridad_Role_Lineal2);
				em.flush();
				em.clear();
			}
		}

		List<Tree> hijos = llegarHastaHijosTodos(
				seguridad_Role_Lineal.getTree(), tipoNodo);

		for (Tree hijo : hijos) {
			System.out.println("hijo.getNodo()=" + hijo.getNodo());
			seguridad_Role_Lineal.setTree(hijo);
			heredarRolePermiso(seguridad_Role_Lineal, swElimina,
					hijo.getTiponodo());
		}
	}

	public List<Tree> llegarHastaHijosTodos(Tree nodoPadre, long tiponodo) {
		List<Tree> Lista = new ArrayList();
		if (nodoPadre != null && nodoPadre.getNodo() != null) {

			List<Tree> nodosHijosLst = null;
			if (tiponodo - Utilidades.getNodoRaiz() == 0) {

				Lista = findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(nodoPadre
						.getNodo());

			} else if (tiponodo - Utilidades.getNodoPrincipal() == 0) {
				Lista = findAllHeredaTreeHijosMenosDocumentos(nodoPadre
						.getNodo());
			} else {
				// TODOS
				Lista = findAllHeredaTreeHijos(nodoPadre.getNodo());
			}

		}
		return Lista;
	}

	public void grabarRole_Tree(Seguridad_Role_Lineal role_tree) {
		List<Seguridad_Role_Lineal> existe_role_tree = findSeguridad_Role_RoleAndTree(role_tree);
		if (existe_role_tree != null && existe_role_tree.isEmpty()) {
			create(role_tree);
			givePermparaverToGroup(role_tree);
		} else if (existe_role_tree == null) {
			create(role_tree);
			givePermparaverToGroup(role_tree);
		}
	}

	public void givePermparaverToGroup(
			Seguridad_Role_Lineal seguridad_Role_Lineal2) {
		// verificamos que el role tenga permisos para veer

		if (seguridad_Role_Lineal2.isToView()) {
			List<Roles_Usuarios> roles_Usuarios = findRoles_AND_Usuario(seguridad_Role_Lineal2
					.getRole());
			for (Roles_Usuarios rol_usu : roles_Usuarios) {
				// damos permisos para ver hasta padres abuelos
				Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
				// grabamos la seguridad del nodo
				seguridad_User_Lineal.setUsuario(rol_usu.getUsuario());
				seguridad_User_Lineal.setTree(seguridad_Role_Lineal2.getTree());
				seguridad_User_Lineal.setToView(true);
				grabarOperacionesInConfiguracion(seguridad_User_Lineal);
			}
		}
	}

	public void grabarOperacionesInConfiguracion(
			Seguridad_User_Lineal seguridad_User_Lineal) {

		// grabamos todos los permisos en el nodo y suvimos con solo ver
		if (seguridad_User_Lineal.isToView()) {
			System.out.println("darViewNodoHastaRaiz="
					+ seguridad_User_Lineal.getTree().getNodo());
			darViewNodoHastaRaiz(seguridad_User_Lineal);
		} else {
			grabarOperaciones(seguridad_User_Lineal);
		}

	}

	public void darViewNodoHastaRaizInMemoria(
			Seguridad_User_Lineal seguridad_User_Lineal,
			Map seguridadIndividualPertenesco) {

		seguridad_User_Lineal.setToView(true);
		// ahora, nos vamos a dar permiso de manera descendente hasta
		// llegar la raiz, solo permisologia de view
		boolean swHayPadre = true;
		Tree nodoPermView;
		Seguridad_User_Lineal seguridad_User_Lineal2 = new Seguridad_User_Lineal();
		seguridad_User_Lineal2.setTree(seguridad_User_Lineal.getTree());
		seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal.getUsuario());
		while (swHayPadre) {
			nodoPermView = llegarHastadAbuelosPadres(seguridad_User_Lineal2
					.getTree());
			seguridad_User_Lineal2.setTree(nodoPermView);
			seguridad_User_Lineal2.setToView(true);
			seguridad_User_Lineal2.setUsuario(seguridad_User_Lineal
					.getUsuario());

			if (seguridadIndividualPertenesco != null
					&& !seguridadIndividualPertenesco.containsKey(nodoPermView)) {

				seguridadIndividualPertenesco.put(nodoPermView,
						seguridad_User_Lineal2);

			} else {
				//
				Seguridad segNodo = (Seguridad) seguridadIndividualPertenesco
						.get(nodoPermView);
				segNodo.setToView(true);
				seguridadIndividualPertenesco.put(nodoPermView,
						segNodo);
				
			}

			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();

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

			List<Seguridad_User_Lineal> listseg_user = findAllSeguridad_User_Lineal(
					seguridad_User_Lineal2.getTree(),
					seguridad_User_Lineal2.getUsuario());
			if (listseg_user == null || listseg_user.isEmpty()) {
				// datr permisologia de vista al nodo
				grabarOperaciones(seguridad_User_Lineal2);
			} else {
				Seguridad_User_Lineal seguridad_User_Linealexiste = listseg_user
						.get(0);
				seguridad_User_Linealexiste.setToView(true);
				edit(seguridad_User_Linealexiste);
			}
			swHayPadre = nodoPermView.getTiponodo() != Utilidades.getNodoRaiz();

		}

	}

	public void grabarOperaciones(Seguridad_User_Lineal seguridad_User_Lineal) {
		try {
			Seguridad_User_Lineal seg_user = new Seguridad_User_Lineal(
					seguridad_User_Lineal);

			// buscamos que la seguridad ya no este grabada
			List<Seguridad_User_Lineal> existe_segUser_tree = findAllSeguridad_User_Lineal(
					seguridad_User_Lineal.getTree(),
					seguridad_User_Lineal.getUsuario());
			if (existe_segUser_tree != null && existe_segUser_tree.isEmpty()) {

				create(seg_user);
			} else if (existe_segUser_tree == null) {
				create(seg_user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarDatosRole(Role role, List<Operaciones> visibleItems) {
		// introducimos las nuevas operaciones al rol
		Seguridad_Role_Lineal seguridad_Role_Lineal = null;
		// Seguridad_Role_Lineal
		seguridad_Role_Lineal = new Seguridad_Role_Lineal();

		for (Operaciones oper2 : visibleItems) {
			// AGARRAMOS CADA OPERACION INDIVIDUAL
			if (oper2 != null && oper2 instanceof Operaciones) {
				// llenamos por referencia el objeto
				// seguridad_Role_Lineal
				// con la operacion que se le da
				llenarSeguridadLineal(oper2, seguridad_Role_Lineal);
			}
		}
		// tenemos todas las operaciones guardadas, colocamos el rol que
		// representa la operacion y
		// guardamos en la BD.
		seguridad_Role_Lineal.setTree(null);
		seguridad_Role_Lineal.setRole(role);
		// guardamos o actualizamos en bd
		create(seguridad_Role_Lineal);
		// SI HAY YA ROLES GRABADOS EN TREE, LO ACTUALIZAMOS
		List<Seguridad_Role_Lineal> listaActualizarTree = findAllSeguridad_Role_Lineal(role);
		for (Seguridad_Role_Lineal segTree : listaActualizarTree) {
			if (segTree.getTree() != null) {
				Seguridad_Role_Lineal seguridad_Role_Lineal3 = new Seguridad_Role_Lineal(
						seguridad_Role_Lineal);
				// or cada registro de seguridadrolelineal, actualizamos sus
				// nuevo permisos en cada tree o nodo
				seguridad_Role_Lineal3.setCodigo(segTree.getCodigo());
				seguridad_Role_Lineal3.setTree(segTree.getTree());
				seguridad_Role_Lineal3.setCodigo(segTree.getCodigo());
				edit(seguridad_Role_Lineal3);
				// OJOOOOOOOOOOOOOOO ESTO ESTA OCUPANO BASTANTRES RECURSOS
				// SE TENDRA QUE COMENTAR ..
			//	givePermparaverToGroup(seguridad_Role_Lineal3);
			}
		}
	}

	// fin todsa la seguridad del tree con sus roles

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Identificador(
			Role role) {
		String str = "select object(o) from Seguridad_Role_Lineal as o where o.role.codigo="
				+ role.getCodigo();
		str += " and  o.tree is null ";
		System.out.println("------------ALERTA------------------------");
		System.out.println("role =" + role.getCodigo());
		Query query = em.createQuery(str.toString());

		List<Seguridad_Role_Lineal> lista = query.getResultList();
		if (lista == null || lista.isEmpty()) {
			System.out
					.println("-----findAllSeguridad_Role_Identificador-----------------");
			System.out
					.println("ROLE IDENTIFICADOR ES NULO .. NPO HAY NADA DONDE TREE ES NULL");
			System.out.println("----------------------");

			// str =
			// "select object(o) from Seguridad_Role_Lineal as o where o.role.codigo="
			// + role.getCodigo();
			// query = em.createQuery(str.toString());
			// lista = query.getResultList();
		}

		return lista;
	}

	public void create(Seguridad_User_Lineal seg) {
		String str = "select object(o) from Seguridad_User_Lineal as o where o.usuario.id="
				+ seg.getUsuario().getId();

		if (seg != null && seg.getTree() != null) {
			str += " and o.tree.nodo=" + seg.getTree().getNodo();
		}

		str += " and o.status=" + Utilidades.isActivo();

		Query query = em.createQuery(str.toString());
		if (query.getResultList() == null || query.getResultList().isEmpty()) {
			seg.setStatus(true);
			em.persist(seg);

		} else {
			seg.setStatus(true);
			seg.setToView(true);
			em.persist(seg);

		}
	}

	public void edit(Seguridad_User_Lineal seg) {
		em.merge(seg);
	}

	public void destroy(Seguridad_User_Lineal seguridad_User_Lineal) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Seguridad_User_Lineal as o where o.usuario.id=:id and o.tree.nodo=:nodo");

		Query query = em.createQuery(sql.toString());
		query.setParameter("id", seguridad_User_Lineal.getUsuario().getId());
		query.setParameter("nodo", seguridad_User_Lineal.getTree().getNodo());
		List result = query.getResultList();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Seguridad_User_Lineal seguridad_userdelete = (Seguridad_User_Lineal) result
					.get(i);
			// em.merge(seguridad_userdelete);
			em.remove(seguridad_userdelete);
		}
	}

	public Seguridad_User_Lineal findSeguridad_User_Lineal(
			Seguridad_User_Lineal seg) {
		return (Seguridad_User_Lineal) em.find(Seguridad_User_Lineal.class,
				seg.getCodigo());
	}

	public List<Seguridad_User_Lineal> findAllSeguridad_User_Lineal(Tree tree) {
		String str = "select object(o) from Seguridad_User_Lineal as o where o.tree.nodo="
				+ tree.getNodo();
		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	public List<Seguridad_User_Lineal> findAllSeguridad_User_Lineal(Tree tree,
			Usuario usuario) {
		String str = "select object(o) from Seguridad_User_Lineal as o where o.tree.nodo="
				+ tree.getNodo();
		str += " and o.usuario.id=" + usuario.getId();

		Query query = em.createQuery(str.toString());
		return query.getResultList();
	}

	// ________________________________________________________________________________________________________________________://
	// INICIO DETALLE POSTGRES
	// __________________________________________________________________________________________________________________________//
	public void create(Doc_dataPostgres datapostgres) {
		datapostgres.setStatus(true);
		em.persist(datapostgres);
	}

	public void edit(Doc_dataPostgres datapostgres) {
		em.merge(datapostgres);
	}

	public void destroy(Doc_dataPostgres datapostgres) {

		datapostgres = findDoc_dataPostgres(datapostgres);
		// em.merge(datapostgres);
		em.remove(datapostgres);

	}

	public Doc_dataPostgres findDoc_dataPostgres(Doc_dataPostgres datapostgres) {
		return (Doc_dataPostgres) em.find(Doc_dataPostgres.class,
				datapostgres.getCodigo());
	}

	public Doc_dataPostgres findDoc_dataPostgres(Doc_detalle doc_detalle) {
		Doc_dataPostgres doc_dataPostgres = null;
		String str = "select object(o) from Doc_dataPostgres as o where o.doc_detalle.codigo="
				+ doc_detalle.getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		// System.out.println("str=" + str.toString());
		Query query = em.createQuery(str.toString());
		List<Doc_dataPostgres> objetos = query.getResultList();
		for (Doc_dataPostgres obj : objetos) {
			doc_dataPostgres = obj;
			break;
		}
		return doc_dataPostgres;
	}

	public Doc_detalle findDoc_detalleVigente(Doc_detalle doc_detalle) {
		Doc_detalle doc_data = null;
		String str = "select object(o) from Doc_detalle as o where o.doc_maestro.codigo="
				+ doc_detalle.getDoc_maestro().getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		str += "  and o.doc_estado.codigo=" + Utilidades.getVigente();
		// System.out.println("str=" + str.toString());
		Query query = em.createQuery(str.toString());
		List<Doc_detalle> objetos = query.getResultList();
		for (Doc_detalle obj : objetos) {
			doc_data = obj;
			break;
		}
		return doc_data;
	}

	public void create(Doc_consecutivo doc_consecutivo) {
		em.persist(doc_consecutivo);
	}

	// -------------------------------------------------------------------------------------------

	public void destroy(TablaAuxiliar tablaAuxiliar) {

		tablaAuxiliar = findTablaAuxiliar(tablaAuxiliar);
		// em.merge(tablaAuxiliar);
		em.remove(tablaAuxiliar);

	}

	public TablaAuxiliar findTablaAuxiliar(TablaAuxiliar tablaAuxiliar) {
		return (TablaAuxiliar) em.find(TablaAuxiliar.class,
				tablaAuxiliar.getCodigo());
	}

	public void create(TablaAuxiliar tablaAuxiliar) {
		em.persist(tablaAuxiliar);
	}

	// ________________________________________________________________________________________________________________________://
	// INICIO HISTORICO DOCUMENTOS ACTIVOS POR USUARIO

	// __________________________________________________________________________________________________________________________//
	public void create(Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		doc_historicoActivoMaestro.setStatus(Utilidades.isActivo());
		em.persist(doc_historicoActivoMaestro);
	}

	public Doc_historicoActivoMaestro findDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Doc_historicoActivoMaestro as o");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.doc_maestro.codigo=").append(
				doc_historicoActivoMaestro.getDoc_maestro().getCodigo());
		sql.append(" and o.usuario.id=").append(
				doc_historicoActivoMaestro.getUsuario().getId());
		Query query = em.createQuery(sql.toString());

		List<Doc_historicoActivoMaestro> lista = query.getResultList();
		Doc_historicoActivoMaestro doc_histActivoMaestro = null;
		for (Doc_historicoActivoMaestro doc_histoActivoMaes : lista) {
			doc_histActivoMaestro = doc_histoActivoMaes;
			break;
		}
		return doc_histActivoMaestro;
	}

	public List<Doc_historicoActivoMaestro> findAllDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Doc_historicoActivoMaestro as o");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.doc_maestro.codigo=").append(
				doc_historicoActivoMaestro.getDoc_maestro().getCodigo());
		Query query = em.createQuery(sql.toString());

		List<Doc_historicoActivoMaestro> lista = query.getResultList();
		return lista;
	}

	public void create(Doc_historicoActivoDetalle doc_historicoActivoDetalle) {
		doc_historicoActivoDetalle.setStatus(Utilidades.isActivo());
		em.persist(doc_historicoActivoDetalle);
	}

	public List<Doc_historicoActivoDetalle> findAllDoc_historicoActivoDetalle(
			Doc_historicoActivoDetalle doc_historicoActivoDetalle) {
		StringBuffer sql = new StringBuffer(
				"select object(o) from Doc_historicoActivoDetalle as o");
		sql.append(" where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.doc_histActMaestro.codigo=").append(
				doc_historicoActivoDetalle.getDoc_histActMaestro().getCodigo());
		sql.append("order by o.fecha desc");

		Query query = em.createQuery(sql.toString());

		return query.getResultList();
	}

	// ________________________________________________________________________________________________________________________://
	// FIN HISTORICO DOCUMENTOS ACTIVOS POR USUARIO
	// __________________________________________________________________________________________________________________________//
	// PAISES
	public void create(Pais pais) {
		pais.setStatus(true);
		em.persist(pais);
	}

	public void edit(Pais pais) {
		em.merge(pais);
	}

	public void destroy(Pais pais) {

		pais = find_pais(pais);
		// em.merge(pais);
		em.remove(pais);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Pais find(Pais pais) {
		return find_pais(pais);
	}

	public List<Pais> find_allPaises() {
		List<Pais> paises = null;
		String sql = "select object(o) from Pais as o  where o.status="
				+ Utilidades.isActivo();
		sql += " order by lower(o.nombre)";
		Query query = em.createQuery(sql.toString());
		paises = query.getResultList();
		return paises;
	}

	public Pais findPais_InEstado(Pais pais) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Estado as o");
		sql.append(" where o.pais.codigo=").append(pais.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		if (query.getResultList().isEmpty()) {
			return null;
		}
		return pais;

	}

	public Estado findEstado_InCiudad(Estado estado) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Ciudad as o");
		sql.append(" where o.estado.codigo=").append(estado.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());

		Query query = em.createQuery(sql.toString());
		if (query.getResultList().isEmpty()) {
			return null;
		}
		return estado;

	}

	public Ciudad findCiudad_InFacturaEUJOVANS(Ciudad ciudad) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Cliente_EUJ as o");
		sql.append(" where o.ciudad.codigo=").append(ciudad.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		if (query.getResultList().isEmpty()) {
			return null;
		}
		return ciudad;

	}

	public Factura findClienteEUJOVANS_InFactura(Cliente_EUJ destinatario,
			Cliente_EUJ remitente) {
		Factura factura = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Factura as o");
		sql.append(" where o.destinatario.codigo=").append(
				destinatario.getCodigo());
		sql.append(" or o.remitente.codigo=").append(remitente.getCodigo());
		sql.append("  and o.status=").append(Utilidades.isActivo());
		Query query = em.createQuery(sql.toString());
		if (query.getResultList().isEmpty()) {
			return null;
		} else {
			List<Factura> fs = query.getResultList();
			for (Factura f : fs) {
				factura = f;
				break;
			}
		}
		return factura;

	}

	public List findAll_Paises(String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Pais as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.nombre) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Estado> find_allEstados() {
		List<Estado> estados = null;
		String sql = "select object(o) from Estado as o  where o.status="
				+ Utilidades.isActivo();
		sql += " order by lower(o.pais.nombre)";
		Query query = em.createQuery(sql.toString());
		estados = query.getResultList();
		return estados;
	}

	public List<Estado> findAll_EstadoByPais(Pais pais) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Estado as o");
			sql.append(" where o.pais.codigo =").append(pais.getCodigo())
					.append("");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append(" order by lower(o.nombre)");
			Query query = em.createQuery(sql.toString());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Ciudad> findAll_CiudadByEstado(Estado estado) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Ciudad as o");
			sql.append(" where o.estado.codigo =").append(estado.getCodigo())
					.append("");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append(" order by lower(o.nombre)");
			Query query = em.createQuery(sql.toString());

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findAll_Estado(String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Estado as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.nombre) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Ciudad> find_allCiudad() {
		List<Ciudad> ciudades = null;
		String sql = "select object(o) from Ciudad as o  where o.status="
				+ Utilidades.isActivo();
		sql += " order by lower(o.nombre)";
		Query query = em.createQuery(sql.toString());
		ciudades = query.getResultList();
		return ciudades;
	}

	public List findAll_Ciudad(String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Ciudad as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.nombre) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Pais find_pais(Pais pais) {
		Pais pais2 = null;
		String str = "select object(o) from Pais as o where o.codigo="
				+ pais.getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Pais> objetos = query.getResultList();
		for (Pais obj : objetos) {
			pais2 = obj;
			break;
		}
		return pais2;
	}

	// ESTADO
	public void create(Estado estado) {
		estado.setStatus(true);
		em.persist(estado);
	}

	public void edit(Estado estado) {
		em.merge(estado);
	}

	public void destroy(Estado estado) {

		estado = find_estado(estado);
		// em.merge(estado);
		em.remove(estado);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Estado find(Estado estado) {
		return find_estado(estado);
	}

	public Estado find_estado(Estado estado) {
		Estado estado2 = null;
		String str = "select object(o) from Estado as o where o.codigo="
				+ estado.getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Estado> objetos = query.getResultList();
		for (Estado obj : objetos) {
			estado2 = obj;
			break;
		}
		return estado2;
	}

	// CIUDAD
	public void create(Ciudad ciudad) {
		ciudad.setStatus(true);
		em.persist(ciudad);
	}

	public void edit(Ciudad ciudad) {
		em.merge(ciudad);
	}

	public void destroy(Ciudad ciudad) {

		ciudad = find_ciudad(ciudad);
		// em.merge(ciudad);
		em.remove(ciudad);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Ciudad find(Ciudad ciudad) {
		return find_ciudad(ciudad);
	}

	public Ciudad find_ciudad(Ciudad ciudad) {
		Ciudad ciudad2 = null;
		String str = "select object(o) from Ciudad as o where o.codigo="
				+ ciudad.getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Ciudad> objetos = query.getResultList();
		for (Ciudad obj : objetos) {
			ciudad2 = obj;
			break;
		}
		return ciudad2;
	}

	// ________________________________________________________________________________________________________________________://
	// CLIENTE EUJ
	// __________________________________________________________________________________________________________________________//
	public List<Cliente_EUJ> findAll_CiudadByClienteEUJ(Ciudad ciudad,
			Estado estado, Pais pais, String buscar) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from Cliente_EUJ as o ");
			sql.append(" where (  ");
			boolean sw = false;

			if (buscar != null && buscar.length() > 0) {
				sw = true;
				sql.append("   lower(o.nombre) like '")
						.append(buscar.toLowerCase().trim()).append("%'");
			}
			if (ciudad != null) {
				if (sw) {
					sql.append(" and ");
				}
				sw = true;

				sql.append("   o.ciudad.codigo =").append(ciudad.getCodigo())
						.append("");
			}
			if (estado != null) {
				if (sw) {
					sql.append(" and ");
				}
				sw = true;

				sql.append("  o.estado.codigo =").append(estado.getCodigo())
						.append("");
			}
			if (pais != null) {
				if (sw) {
					sql.append(" and ");
				}
				sw = true;

				sql.append("  o.pais.codigo =").append(pais.getCodigo())
						.append("");
			}

			sql.append(" ) ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append(" order by lower(o.nombre)");
			Query query = em.createQuery(sql.toString());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// CIUDAD
	public void create(Cliente_EUJ cliente_EUJ) {
		cliente_EUJ.setStatus(true);
		em.persist(cliente_EUJ);
	}

	public void edit(Cliente_EUJ cliente_EUJ) {
		em.merge(cliente_EUJ);
	}

	public void destroy(Cliente_EUJ cliente_EUJ) {

		cliente_EUJ = find_cliente_EUJ(cliente_EUJ);
		// em.merge(cliente_EUJ);
		em.remove(cliente_EUJ);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Cliente_EUJ find(Cliente_EUJ cliente_EUJ) {
		return find_cliente_EUJ(cliente_EUJ);
	}

	public Cliente_EUJ find_cliente_EUJ(Cliente_EUJ cliente_EUJ) {
		Cliente_EUJ cliente_EUJ2 = null;
		String str = "select object(o) from Cliente_EUJ as o where o.codigo="
				+ cliente_EUJ.getCodigo();
		// str +="  and o.status="+Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Cliente_EUJ> objetos = query.getResultList();
		for (Cliente_EUJ obj : objetos) {
			cliente_EUJ2 = obj;
			break;
		}
		return cliente_EUJ2;
	}

	public Cliente_EUJ find_cliente_EUJ_BYNombre(Cliente_EUJ cliente_EUJ) {
		Cliente_EUJ cliente_EUJ2 = null;
		String str = "select object(o) from Cliente_EUJ as o where lower(o.nombre)='"
				+ cliente_EUJ.getNombre().toString().toLowerCase().trim() + "'";
		str += "  and o.pais.codigo=" + cliente_EUJ.getPais().getCodigo();
		str += "  and o.estado.codigo=" + cliente_EUJ.getEstado().getCodigo();
		str += "  and o.ciudad.codigo=" + cliente_EUJ.getCiudad().getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Cliente_EUJ> objetos = query.getResultList();
		for (Cliente_EUJ obj : objetos) {
			cliente_EUJ2 = obj;
			break;
		}
		return cliente_EUJ2;
	}

	public List<Cliente_EUJ> find_allCliente_EUJ() {
		List<Cliente_EUJ> cliente_EUJ = null;
		String sql = "select object(o) from Cliente_EUJ as o  ";
		// sql +=" where o.status="+Utilidades.isActivo()
		sql += " order by lower(o.nombre)";
		Query query = em.createQuery(sql.toString());
		cliente_EUJ = query.getResultList();
		return cliente_EUJ;
	}

	public void create(Factura factura) {
		factura.setStatus(true);
		em.persist(factura);
	}

	public void edit(Factura factura) {
		em.merge(factura);
	}

	public void destroy(Factura factura) {

		factura = find_Factura(factura);
		// em.merge(factura);
		em.remove(factura);
	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Factura find(Factura factura) {
		return find_Factura(factura);
	}

	public Factura find_Factura(Factura factura) {
		Factura factura2 = null;
		String str = "select object(o) from Factura as o where o.codigo="
				+ factura.getCodigo();
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<Factura> objetos = query.getResultList();
		for (Factura obj : objetos) {
			factura2 = obj;
			break;
		}
		return factura2;
	}

	public Factura find_FacturaNumEntrega(Factura factura) {
		Factura factura2 = null;
		String str = "select object(o) from Factura as o where o.numero_entrega="
				+ factura.getNumero_entrega();
		Query query = em.createQuery(str.toString());
		List<Factura> objetos = query.getResultList();
		for (Factura obj : objetos) {
			factura2 = obj;
			break;
		}
		return factura2;
	}

	/*
	 * Turnos.add(s.createQuery("FROM "+Turno.class.getName()+" AS t where
	 * t.fecha=:fecha") .setDate( "fecha", fecha) .list());
	 * 
	 * | Date date=new Date(); | em.createQuery("from Person p where
	 * p.registeredDate=:date").setParameter("date",date,TemporalType.DATE); n
	 */

	public List<Factura> find_allFactura(Factura factura) {
		List<Factura> factura2 = null;
		// StringBuffer sql = new
		// StringBuffer("select object(o) from Factura as o  where o.status="+factura.isStatus());
		// me trae nulas y no nulas
		StringBuffer sql = new StringBuffer(
				"select object(o) from Factura as o  where ( o.status="
						+ factura.isStatus());
		sql.append(" or o.status=" + !factura.isStatus()).append(" ) ");
		boolean sw = true;
		if (factura != null) {

			if (factura.getFecha() != null) {
				if (factura.getFechaHasta() != null) {
					sql.append(" and (o.fechaonly >=:fecha and o.fechaonly<=:fechaHasta ) ");
				} else {
					sql.append(" and (o.fechaonly=:fecha ) ");
				}

			}
			if (factura.getFechaemitido() != null) {
				if (factura.getFechaemitidoHasta() != null) {
					sql.append(" and (o.fechaemitidoonly>=:fechaemitido and o.fechaemitidoonly<=:fechaemitidoHasta ) ");
				} else {
					sql.append(" and ( o.fechaemitidoonly=:fechaemitido) ");
				}

			}

			if (factura.getFechaconfirmaentrega() != null) {
				if (factura.getFechaconfirmaentregaHasta() != null) {
					sql.append(" and (o.fechaconfirmaentregaonly >=:fechaconfirmaentrega and o.fechaconfirmaentregaonly <=:FechaconfirmaentregaHasta ) ");
				} else {
					sql.append(" and (o.fechaconfirmaentregaonly=:fechaconfirmaentrega ) ");
				}

			}

			if (factura.getFechapagado() != null) {
				if (factura.getFechapagadoHasta() != null) {
					sql.append(" and (o.fechapagadoonly>=:fechapagado and o.fechapagadoonly<=:fechapagadoHasta ) ");
				} else {
					sql.append(" and (o.fechapagadoonly=:fechapagado) ");
				}

			}
			if (factura.getNumero_entrega() != null
					&& factura.getNumero_entrega() > 0) {
				sql.append(" and o.numero_entrega=").append(
						factura.getNumero_entrega());

			}
			if (factura.getDestinatario() != null) {
				if (sw) {
					sql.append(" and ");
				}
				System.out.println("destinatario="
						+ factura.getDestinatario().getNombre());
				sql.append("   o.destinatario.codigo=").append(
						factura.getDestinatario().getCodigo());

			}
			if (factura.getRemitente() != null) {
				if (sw) {
					sql.append(" and ");
				}
				System.out.println("remite="
						+ factura.getRemitente().getNombre());
				sql.append("   o.remitente.codigo=").append(
						factura.getRemitente().getCodigo());

			}

			if (factura.getChofer() != null) {
				if (sw) {
					sql.append(" and ");
				}
				sql.append("   o.chofer.id=").append(
						factura.getChofer().getId());
				sw = true;
			}
		}
		if (factura.isReporte()) {
			sql.append(" order by o.numero_entrega ");
		} else {
			sql.append(" order by o.codigo desc ");
		}

		// System.out.println("sql ="+sql.toString());
		Query query = em.createQuery(sql.toString());
		if (factura.getFecha() != null) {
			query.setParameter("fecha", factura.getFecha(), TemporalType.DATE);
			if (factura.getFechaHasta() != null) {
				query.setParameter("fechaHasta", factura.getFechaHasta(),
						TemporalType.DATE);
			}
		}

		if (factura.getFechaemitido() != null) {
			query.setParameter("fechaemitido", factura.getFechaemitido(),
					TemporalType.DATE);
			if (factura.getFechaemitidoHasta() != null) {
				query.setParameter("fechaemitidoHasta",
						factura.getFechaemitidoHasta(), TemporalType.DATE);
			}

		}

		if (factura.getFechaconfirmaentrega() != null) {
			query.setParameter("fechaconfirmaentrega",
					factura.getFechaconfirmaentrega(), TemporalType.DATE);
			if (factura.getFechaconfirmaentregaHasta() != null) {
				query.setParameter("FechaconfirmaentregaHasta",
						factura.getFechaconfirmaentregaHasta(),
						TemporalType.DATE);
			}
		}
		if (factura.getFechapagado() != null) {
			query.setParameter("fechapagado", factura.getFechapagado(),
					TemporalType.DATE);
			if (factura.getFechapagadoHasta() != null) {
				query.setParameter("fechapagadoHasta",
						factura.getFechapagadoHasta(), TemporalType.DATE);
			}
		}
		if (!factura.isReporte()) {
			query.setMaxResults(Utilidades.getMaxRegisterMostrar())
					.setFirstResult(0);
		}

		factura2 = query.getResultList();
		// query.setMaxResults(Utilidades.getMinRegisterMostrar()).setFirstResult(0);
		return factura2;
	}

	// __________________________________________________________________________________________________________________________________
	// __________________________________________________________________________________________________________________________________
	// __________________________________________________________________________________________________________________________________
	// ValidaVencimiento
	public void create(ValidaVencimiento validaVencimiento) {
		if (validaVencimiento != null) {
			validaVencimiento.setStatus(true);
			em.persist(validaVencimiento);
		}

	}

	public void edit(ValidaVencimiento validaVencimiento) {
		em.merge(validaVencimiento);
	}

	public ValidaVencimiento find_ValidaVencimiento(
			ValidaVencimiento validaVencimiento) {
		ValidaVencimiento validaVencimiento2 = null;
		String fecha = Utilidades.sdfShow.format(validaVencimiento.getFecha());
		String str = "select object(o) from ValidaVencimiento as o where o.fecha2 > '"
				+ fecha.toString() + "'";
		str += "  and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());

		List<ValidaVencimiento> objetos = query.getResultList();
		for (ValidaVencimiento obj : objetos) {
			validaVencimiento2 = obj;
			break;
		}
		return validaVencimiento2;
	}

	public List<ValidaVencimiento> find_allValidaVencimiento() {
		List<ValidaVencimiento> validaVencimiento = null;
		String sql = "select object(o) from ValidaVencimiento as o  where o.status="
				+ Utilidades.isActivo();
		sql += " order by o.codigo asc";

		Query query = em.createQuery(sql.toString());
		validaVencimiento = query.getResultList();
		return validaVencimiento;
	}

	// ________________________________________________________________________________________________________________________://
	// CONSECUTIVO
	// __________________________________________________________________________________________________________________________//

	public void create(ConsecutivoEUJ consecutivoEUJ) {

		em.persist(consecutivoEUJ);
	}

	public void edit(ConsecutivoEUJ consecutivoEUJ) {
		em.merge(consecutivoEUJ);
	}

	public void destroy(ConsecutivoEUJ consecutivoEUJ) {

		consecutivoEUJ = find_ConsecutivoEUJ(consecutivoEUJ);
		// em.merge(consecutivoEUJ);
		em.remove(consecutivoEUJ);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public ConsecutivoEUJ find(ConsecutivoEUJ consecutivoEUJ) {
		return find_ConsecutivoEUJ(consecutivoEUJ);
	}

	public ConsecutivoEUJ find_ConsecutivoEUJ(ConsecutivoEUJ consecutivoEUJ) {
		ConsecutivoEUJ consecutivoEUJ2 = null;
		String str = "select object(o) from ConsecutivoEUJ as o where o.codigo="
				+ consecutivoEUJ.getCodigo();
		Query query = em.createQuery(str.toString());
		List<ConsecutivoEUJ> objetos = query.getResultList();
		for (ConsecutivoEUJ obj : objetos) {
			consecutivoEUJ2 = obj;
			break;
		}
		return consecutivoEUJ2;
	}

	public List<ConsecutivoEUJ> find_allConsecutivoEUJ(boolean status) {
		List<ConsecutivoEUJ> consecutivoEUJ = null;
		String sql = "select object(o) from ConsecutivoEUJ as o  where o.status="
				+ status;
		sql += " order by o.codigo asc";

		Query query = em.createQuery(sql.toString());
		consecutivoEUJ = query.getResultList();
		return consecutivoEUJ;
	}

	// CIUDAD
	public void create(Configuracion configuracion) {
		em.persist(configuracion);
	}

	public void edit(Configuracion configuracion) {
		em.merge(configuracion);
	}

	public void destroy(Configuracion configuracion) {

		configuracion = find_configuracion(configuracion);
		// em.merge(configuracion);
		em.remove(configuracion);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Configuracion find(Configuracion configuracion) {
		return find_configuracion(configuracion);
	}

	public Configuracion find_configuracion(Configuracion configuracion) {
		Configuracion configuracion2 = null;
		String str = "select object(o) from Configuracion as o where o.codigo="
				+ configuracion.getCodigo();
		Query query = em.createQuery(str.toString());
		List<Configuracion> objetos = query.getResultList();
		for (Configuracion obj : objetos) {
			configuracion2 = obj;
			break;
		}
		return configuracion2;
	}

	public List<Configuracion> find_allConfiguracion() {

		List<Configuracion> configuracion = null;
		String sql = "select object(o) from Configuracion as o  ";
		// if (usuario != null && usuario.getEmpresa() != null
		// &&(!"root".equalsIgnoreCase(usuario.getLogin()))) {
		// sql += " where o.empresa=" + usuario.getEmpresa().getNodo();
		// }

		sql += " order by o.codigo asc";

		Query query = em.createQuery(sql.toString());
		configuracion = query.getResultList();

		return configuracion;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN HISTORICO
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// DIAS HABILES
	// __________________________________________________________________________________________________________________________//
	public void create(DiasHabiles diasHabiles) {
		em.persist(diasHabiles);
	}

	public void edit(DiasHabiles diasHabiles) {
		em.merge(diasHabiles);
	}

	public void destroy(DiasHabiles diasHabiles) {

		diasHabiles = find_DiasHabiles(diasHabiles);
		// em.merge(diasHabiles);
		em.remove(diasHabiles);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public DiasHabiles find(DiasHabiles diasHabiles) {
		return find_DiasHabiles(diasHabiles);
	}

	public DiasHabiles find_DiasHabiles(DiasHabiles diasHabiles) {
		DiasHabiles diasHabiles2 = null;
		String str = "select object(o) from DiasHabiles as o where o.codigo="
				+ diasHabiles.getCodigo();
		Query query = em.createQuery(str.toString());
		List<DiasHabiles> objetos = query.getResultList();
		for (DiasHabiles obj : objetos) {
			diasHabiles2 = obj;
			break;
		}
		return diasHabiles2;
	}

	public DiasHabiles find_DiasHabilesByDia(Usuario usuario, String diaSemana) {
		DiasHabiles diasHabiles2 = null;
		String str = "select object(o) from DiasHabiles as o where o.nombre='"
				+ diaSemana.toString().trim() + "'";
		if (usuario != null && usuario.getEmpresa() != null) {
			str += " and o.empresa.nodo=" + usuario.getEmpresa().getNodo();
		}

		try {
			Query query = em.createQuery(str.toString());
			if (query != null && !query.getResultList().isEmpty()) {
				List<DiasHabiles> objetos = query.getResultList();
				for (DiasHabiles obj : objetos) {
					diasHabiles2 = obj;
					break;
				}
			} else {
			}

		} catch (Exception e) {
		}
		return diasHabiles2;
	}

	public List<DiasHabiles> find_allDiasHabiles(Tree empresas) {

		List<DiasHabiles> diasHabiles = null;
		String sql = "select object(o) from DiasHabiles as o  ";
		sql += " where o.empresa.nodo=" + empresas.getNodo();
		sql += " order by o.codigo asc";
		Query query = em.createQuery(sql.toString());
		diasHabiles = query.getResultList();

		return diasHabiles;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN DIAS HABILES
	// __________________________________________________________________________________________________________________________//

	// ________________________________________________________________________________________________________________________://
	// DIAS FERIADOS
	// __________________________________________________________________________________________________________________________//
	public void create(DiasFeriadosBean diasHabiles) {
		diasHabiles.setStatus(true);
		em.persist(diasHabiles);
	}

	public void edit(DiasFeriadosBean diasHabiles) {
		em.merge(diasHabiles);
	}

	public void destroy(DiasFeriadosBean diasHabiles) {

		diasHabiles = find_DiasFeriados(diasHabiles);
		// em.merge(diasHabiles);
		em.remove(diasHabiles);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public DiasFeriadosBean find(DiasFeriadosBean diasHabiles) {
		return find_DiasFeriados(diasHabiles);
	}

	public DiasFeriadosBean find_DiasFeriados(DiasFeriadosBean diasHabiles) {
		DiasFeriadosBean diasHabiles2 = null;
		String str = "select object(o) from DiasFeriadosBean as o where o.codigo="
				+ diasHabiles.getCodigo();
		Query query = em.createQuery(str.toString());
		List<DiasFeriadosBean> objetos = query.getResultList();
		for (DiasFeriadosBean obj : objetos) {
			diasHabiles2 = obj;
			break;
		}
		return diasHabiles2;
	}

	public List<DiasFeriadosBean> find_allDiasFeriadosBean(Usuario usuario) {

		List<DiasFeriadosBean> diasHabiles = null;
		String sql = "select object(o) from DiasFeriadosBean as o  ";
		if (usuario != null && usuario.getEmpresa() != null) {
			sql += " where o.empresa.nodo=" + usuario.getEmpresa().getNodo();
		}

		sql += " order by o.fechaonly asc";

		Query query = null;
		try {
			query = em.createQuery(sql.toString());
		} catch (Exception e) {
		}
		if (query != null && !query.getResultList().isEmpty()) {
			diasHabiles = query.getResultList();
		} else {
			diasHabiles = null;
		}

		return diasHabiles;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN DIAS FERIADOS
	// __________________________________________________________________________________________________________________________//

	// ________________________________________________________________________________________________________________________://
	// CALCULO DE TIEMPO EN LOS FLUJOS
	// __________________________________________________________________________________________________________________________//
	public void create(FlowControlByUsuarioBean flowControlByUsuarioBean) {
		flowControlByUsuarioBean.setFechaemitido(new Date());
		em.persist(flowControlByUsuarioBean);
	}

	public void edit(FlowControlByUsuarioBean flowControlByUsuarioBean) {
		em.merge(flowControlByUsuarioBean);
	}

	public void destroy(FlowControlByUsuarioBean flowControlByUsuarioBean) {

		flowControlByUsuarioBean = find_FlowControlByUsuarioBean(flowControlByUsuarioBean);
		// em.merge(flowControlByUsuarioBean);
		em.remove(flowControlByUsuarioBean);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public FlowControlByUsuarioBean find(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {
		return find_FlowControlByUsuarioBean(flowControlByUsuarioBean);
	}

	public FlowControlByUsuarioBean find_FlowControlByUsuarioBean(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {
		FlowControlByUsuarioBean diasHabiles2 = null;
		String str = "select object(o) from FlowControlByUsuarioBean as o where o.codigo="
				+ flowControlByUsuarioBean.getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<FlowControlByUsuarioBean> objetos = query.getResultList();
		for (FlowControlByUsuarioBean obj : objetos) {
			diasHabiles2 = obj;
			break;
		}
		return diasHabiles2;
	}

	public FlowControlByUsuarioBean find_FlowControlIsSecuencialGetFechaFirmaUltimo(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {
		FlowControlByUsuarioBean diasHabiles2 = null;
		long ultimoEnFirmar = flowControlByUsuarioBean.getCodigo() - 1;
		String str = "select object(o) from FlowControlByUsuarioBean as o where o.codigo="
				+ ultimoEnFirmar;
		str += " and o.flow.codigo="
				+ flowControlByUsuarioBean.getFlow().getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());

		List<FlowControlByUsuarioBean> objetos = query.getResultList();
		for (FlowControlByUsuarioBean obj : objetos) {
			diasHabiles2 = obj;
			break;
		}
		return diasHabiles2;
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByRoleBean(
			Role role, Flow flow) {

		List<FlowControlByUsuarioBean> diasHabiles = null;
		if (role != null) {
			String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
			sql += " where o.role.codigo=" + role.getCodigo();
			sql += " and o.flow.codigo=" + flow.getCodigo();
			sql += " and o.status=" + Utilidades.isActivo();
			sql += " order by o.codigo asc";
			Query query = em.createQuery(sql.toString());
			diasHabiles = query.getResultList();
		}

		return diasHabiles;
	}

	// flow_Participantes SOLO AGARRA EL FLOW DEL PARTICIPANTES, EXCLUIMOS AL
	// FLOW DEL ROL
	public List<FlowControlByUsuarioBean> find_allFlowControlByFlowBean(
			Flow flow) {

		List<FlowControlByUsuarioBean> diasHabiles = null;
		if (flow != null) {
			String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
			sql += " where o.flow_Participantes.flow.codigo="
					+ flow.getCodigo();

			if (flow.getFlow_P() != null) {
				sql += " or o.flow_Participantes.codigo="
						+ flow.getFlow_P().getCodigo();
			}
			sql += " and o.status=" + Utilidades.isActivo();
			sql += " order by o.codigo asc";
			System.out.println("sql=" + sql);
			Query query = em.createQuery(sql.toString());
			diasHabiles = query.getResultList();
		}

		return diasHabiles;
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipNulos(
			Flow flow) {

		List<FlowControlByUsuarioBean> diasHabiles = null;
		if (flow != null) {
			String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
			sql += " where o.flow.codigo=" + flow.getCodigo();
			sql += " and o.flow_Participantes is null ";
			sql += " and o.status=" + Utilidades.isActivo();
			sql += " order by o.codigo asc";
			Query query = em.createQuery(sql.toString());
			diasHabiles = query.getResultList();
		}

		return diasHabiles;
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndRole(
			Flow flow) {

		List<FlowControlByUsuarioBean> diasHabiles = null;

		String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
		sql += " where o.flow.codigo=" + flow.getCodigo();
		sql += " and o.status=" + Utilidades.isActivo();
		sql += " order by o.codigo asc";
		Query query = em.createQuery(sql.toString());
		diasHabiles = query.getResultList();
		return diasHabiles;
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndFlow(
			Flow_Participantes flow_Participantes) {

		List<FlowControlByUsuarioBean> diasHabiles = null;

		String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
		sql += " where o.flow.codigo="
				+ flow_Participantes.getFlow().getCodigo();
		sql += " and o.flow_Participantes.codigo="
				+ flow_Participantes.getCodigo();
		sql += " and o.status=" + Utilidades.isActivo();
		sql += " order by o.codigo desc";
		Query query = em.createQuery(sql.toString());
		diasHabiles = query
				.setMaxResults(Utilidades.getMaxregistermostrarall())
				.getResultList();

		return diasHabiles;
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByUsuarioBean() {
		List<FlowControlByUsuarioBean> diasHabiles = null;
		try {

			StringBuffer sql = new StringBuffer(
					"select object(o) from FlowControlByUsuarioBean as o  ");
			sql.append(" where o.status=" + Utilidades.isActivo());
			sql.append(" order by o.codigo asc");
			Query query = em.createQuery(sql.toString());
			diasHabiles = query.getResultList();

		} catch (Exception e) {
			System.out
					.println("No pudo agarrar datos de la base de datos find_allFlowControlByUsuarioBean");
		}

		return diasHabiles;
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByHilo() {
		List<FlowControlByUsuarioBean> diasHabiles = null;
		try {

			StringBuffer sql = new StringBuffer(
					"select object(o) from FlowControlByUsuarioBean as o  ");
			sql.append(" where o.status=" + Utilidades.isActivo());
			sql.append(" and o.swStartHilo=" + Utilidades.isActivo());

			sql.append(" order by o.codigo asc");
			Query query = em.createQuery(sql.toString());
			diasHabiles = query.getResultList();

		} catch (Exception e) {
			System.out
					.println("No pudo agarrar datos de la base de datos find_allFlowControlByUsuarioBean");
		}

		return diasHabiles;
	}

	public void borrarFlowControlTime(Flow flow) {
		// eliminamos el flujo de referencia roles boramos borrar
		List<FlowControlByUsuarioBean> f_refs = find_allControlTimeByFlowParticipAndRole(flow);
		for (FlowControlByUsuarioBean f_ref : f_refs) {
			destroy(f_ref);
		}
	}

	// ________________________________________________________________________________________________________________________://
	// FIN DE TIEMPO EN LOS FLUJOS
	// __________________________________________________________________________________________________________________________//
	// ________________________________________________________________________________________________________________________://
	// attachment poarticipantes
	// __________________________________________________________________________________________________________________________//

	public List<FlowControlByUsuarioBean> find_allFlowControlByRoleBean(
			Role role) {
		List<FlowControlByUsuarioBean> lista = null;

		if (role != null) {
			String sql = "select object(o) from FlowControlByUsuarioBean as o  ";
			sql += " where o.role.codigo=" + role.getCodigo();
			sql += " order by o.codigo asc";
			Query query = em.createQuery(sql.toString());
			lista = query.getResultList();
		}

		return lista;
	}

	public void create(Flow_ParticipantesAttachment objeto) {
		em.persist(objeto);
	}

	public void edit(Flow_ParticipantesAttachment objeto) {
		em.merge(objeto);
	}

	public void destroy(Flow_ParticipantesAttachment objeto) {

		objeto = find_Flow_ParticipantesAttachment(objeto);
		// em.merge(objeto);
		em.remove(objeto);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public Flow_ParticipantesAttachment find(Flow_ParticipantesAttachment objeto) {
		return find_Flow_ParticipantesAttachment(objeto);
	}

	public Flow_ParticipantesAttachment find_Flow_ParticipantesAttachment(
			Flow_ParticipantesAttachment objeto) {
		Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;
		String str = "select object(o) from Flow_ParticipantesAttachment as o where o.flowParticipantes.codigo="
				+ objeto.getFlowParticipantes().getCodigo();
		str += " and o.status=" + Utilidades.isActivo();

		Query query = em.createQuery(str.toString());
		List<Flow_ParticipantesAttachment> objetos = query.getResultList();
		for (Flow_ParticipantesAttachment obj : objetos) {

			flow_ParticipantesAttachment = obj;
			break;
		}
		return flow_ParticipantesAttachment;
	}

	// ,ME DICE SI EL PARTICIPANTE ESPECIFICO SUBIO ATTACHMENT POR SVN
	public Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSvnPorParticipante(
			Flow_ParticipantesAttachment objeto) {
		Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;
		String str = "select object(o) from Flow_ParticipantesAttachment as o where o.flowParticipantes.codigo="
				+ objeto.getFlowParticipantes().getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.swSVN=" + Utilidades.isActivo();

		Query query = em.createQuery(str.toString());
		List<Flow_ParticipantesAttachment> objetos = query.getResultList();
		for (Flow_ParticipantesAttachment obj : objetos) {

			flow_ParticipantesAttachment = obj;
			break;
		}
		return flow_ParticipantesAttachment;
	}

	// ESTE ME MUESTRA SI EN FLUJOS PARALELOS .. POR LO MENOS UN PARTICIPNTES
	// DE TODOS LOS FLUJOS SUBIO UN ATTCHMENT CON SVN
	public Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSVN(
			Flow_ParticipantesAttachment objeto) {
		Flow_ParticipantesAttachment flow_ParticipantesAttachment = null;
		String str = "select object(o) from Flow_ParticipantesAttachment as o where o.flowParticipantes.flow.flowParalelo.codigo="
				+ objeto.getFlowParticipantes().getFlow().getFlowParalelo()
						.getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		str += " and o.swSVN=" + Utilidades.isActivo();

		Query query = em.createQuery(str.toString());
		List<Flow_ParticipantesAttachment> objetos = query.getResultList();
		for (Flow_ParticipantesAttachment obj : objetos) {
			System.out.println("hay data svn atatchment");
			flow_ParticipantesAttachment = obj;
			break;
		}
		return flow_ParticipantesAttachment;
	}

	// __________________________________________________________________________________________________________________________//

	// ________________________________________________________________________________________________________________________://
	// EXTENSIONMES DE ARCHIVOS MAESTRO
	// __________________________________________________________________________________________________________________________//
	public void create(ExtensionFile extensionFile) {
		extensionFile.setStatus(true);
		em.persist(extensionFile);
	}

	public void edit(ExtensionFile extensionFile) {
		em.merge(extensionFile);
	}

	public void destroy(ExtensionFile extensionFile) {

		extensionFile = find_ExtensionFile(extensionFile);
		// em.merge(diasHabiles);
		em.remove(extensionFile);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public ExtensionFile find(ExtensionFile extensionFile) {
		return find_ExtensionFile(extensionFile);
	}

	private ExtensionFile find_ExtensionFile(ExtensionFile extensionFile) {
		ExtensionFile objeto = null;
		String str = "select object(o) from ExtensionFile as o where o.codigo="
				+ extensionFile.getCodigo();
		Query query = em.createQuery(str.toString());
		List<ExtensionFile> objetos = query.getResultList();
		for (ExtensionFile obj : objetos) {
			objeto = obj;
			break;
		}
		return objeto;
	}

	private ExtensionFile find_ExtensionFileExiste(ExtensionFile extensionFile) {
		ExtensionFile objeto = null;
		String str = "select object(o) from ExtensionFile as o where lower(o.extension)='"
				+ extensionFile.getExtension().toLowerCase() + "'";
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<ExtensionFile> objetos = query.getResultList();
		for (ExtensionFile obj : objetos) {
			objeto = obj;
			break;
		}
		return objeto;
	}

	public List find_allExtensionFile(String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from ExtensionFile as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.extension) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			sql.append(" order by lower(o.extension)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ExtensionFile> find_allExtensionFile() {

		List<ExtensionFile> objetos = null;
		String sql = "select object(o) from ExtensionFile as o  ";
		sql += " order by o.extension asc";

		Query query = null;
		try {
			query = em.createQuery(sql.toString());
		} catch (Exception e) {
		}
		if (query != null && !query.getResultList().isEmpty()) {
			objetos = query.getResultList();
		} else {
			objetos = null;
		}
		return objetos;
	}

	public List<ExtensionFile> find_ExtensionFileByExtAndMime(
			ExtensionFile extensionFile) {

		List<ExtensionFile> objetos = null;
		String sql = "select object(o) from ExtensionFile as o  ";
		sql += " where lower(o.extension)='";
		sql += extensionFile.getExtension().toLowerCase().trim() + "'";
		sql += " and ";
		sql += "  lower(o.mimeType)='";
		sql += extensionFile.getMimeType().toLowerCase().trim() + "'";
		sql += " order by o.extension asc";

		Query query = null;
		try {
			query = em.createQuery(sql.toString());
		} catch (Exception e) {
		}
		if (query != null && !query.getResultList().isEmpty()) {
			objetos = query.getResultList();
		} else {
			objetos = null;
		}
		return objetos;
	}

	public ExtensionFile tipoContextType(String ext) {
		ExtensionFile objeto = null;
		if (ext == null) {
			return objeto;

		}
		String sql = "select object(o) from ExtensionFile as o  ";
		sql += " where lower(o.extension)='" + ext.toLowerCase() + "'";
		Query query = em.createQuery(sql.toString());
		// System.out.println(sql);
		List<ExtensionFile> objetos = query.getResultList();
		for (ExtensionFile obj : objetos) {
			objeto = obj;
			break;
		}
		return objeto;

	}

	// ________________________________________________________________________________________________________________________://
	// FIN EXTENSIONMES DE ARCHIVOS MAESTRO
	// __________________________________________________________________________________________________________________________//

	// ________________________________________________________________________________________________________________________://
	// EXTENSIONMES DE ARCHIVOS HIJOS
	// __________________________________________________________________________________________________________________________//
	public void create(ExtensionFileHijos extensionFile) {

		if (find_ExtensionFileByExtension(extensionFile) != null) {
			// System.out.println("Extension existe...");
		} else {
			extensionFile.setStatus(true);
			em.persist(extensionFile);
		}

	}

	public void edit(ExtensionFileHijos extensionFile) {
		em.merge(extensionFile);
	}

	public void destroy(ExtensionFileHijos extensionFile) {

		extensionFile = find_ExtensionFile(extensionFile);
		// em.merge(diasHabiles);
		em.remove(extensionFile);

	}

	/*
	 * public Pais findDoc_pais(Pais pais) { return (Pais) em.find(Pais.class,
	 * pais.getCodigo()); }
	 */
	public ExtensionFileHijos find(ExtensionFileHijos extensionFile) {
		return find_ExtensionFile(extensionFile);
	}

	private ExtensionFileHijos find_ExtensionFile(
			ExtensionFileHijos extensionFile) {
		ExtensionFileHijos objeto = null;
		String str = "select object(o) from ExtensionFileHijos as o where o.codigo="
				+ extensionFile.getCodigo();
		Query query = em.createQuery(str.toString());
		List<ExtensionFileHijos> objetos = query.getResultList();
		for (ExtensionFileHijos obj : objetos) {
			objeto = obj;
			break;
		}
		return objeto;
	}

	public ExtensionFileHijos find_ExtensionFileByExtension(
			ExtensionFileHijos extensionFile) {
		ExtensionFileHijos objeto = null;
		String str = "select object(o) from ExtensionFileHijos as o where lower(o.extension)='"
				+ extensionFile.getExtension().toLowerCase() + "'";
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		List<ExtensionFileHijos> objetos = query.getResultList();
		for (ExtensionFileHijos obj : objetos) {
			objeto = obj;
			break;
		}
		return objeto;
	}

	public List<ExtensionFileHijos> find_allExtensionFile(
			ExtensionFile extensionFile) {
		if (extensionFile == null || extensionFile.getCodigo() == null) {
			return null;
		}

		List<ExtensionFileHijos> objetos = null;
		String sql = "select object(o) from ExtensionFileHijos as o  ";
		sql += " where o.extensionFile.codigo=" + extensionFile.getCodigo();
		sql += " and o.status=" + Utilidades.isActivo();
		sql += " order by o.extension asc";

		Query query = null;
		try {
			query = em.createQuery(sql.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		if (query != null && !query.getResultList().isEmpty()) {
			objetos = query.getResultList();
		} else {
			objetos = null;
		}
		return objetos;
	}

	public List<ExtensionFileHijos> find_allExtensionFileHijos() {

		List<ExtensionFileHijos> objetos = null;
		String sql = "select object(o) from ExtensionFileHijos as o  ";
		sql += " order by o.extension asc";

		Query query = null;
		try {
			query = em.createQuery(sql.toString());
		} catch (Exception e) {
		}
		if (query != null && !query.getResultList().isEmpty()) {
			objetos = query.getResultList();
		} else {
			objetos = null;
		}
		return objetos;
	}

	public String findAllExtensionWithHijos() {

		StringBuffer extensiones = new StringBuffer("");
		StringBuffer extensionesPrimeFaces = new StringBuffer("");
		// richfaces
		// *.jpg;*.png;*.gif;*.pdf;
		// primefaces
		// /(\.|\/)(gif|jpe?g|png)$/
		List<ExtensionFile> exts = find_allExtensionFile();
		boolean swPrimeraVez = true;
		for (ExtensionFile ext : exts) {
			// if (swPrimeraVez) {
			extensiones.append("*.").append(ext.getExtension()).append(";");
			extensionesPrimeFaces.append(ext.getExtension()).append("|");
			swPrimeraVez = false;

		}
		if (!swPrimeraVez) {
			// quitamos el ultyimo pipe
			extensionesPrimeFaces.substring(0,
					extensionesPrimeFaces.length() - 1);
		}

		List<ExtensionFileHijos> extsH = find_allExtensionFileHijos();
		if (extsH != null) {
			swPrimeraVez = true;
			for (ExtensionFileHijos ext : extsH) {
				// if (swPrimeraVez) {
				extensiones.append("*.").append(ext.getExtension()).append(";");
				extensionesPrimeFaces.append(ext.getExtension()).append("|");
				swPrimeraVez = false;

			}
			if (!swPrimeraVez) {
				// quitamos el ultyimo pipe
				extensionesPrimeFaces.substring(0,
						extensionesPrimeFaces.length() - 1);
			}
		}

		StringBuffer extensionesPrimeFacesFin = new StringBuffer("");
		// /(\.|\/)(gif|jpe?g|png)$/
		extensionesPrimeFacesFin.append("/(\\.|\\/)(")
				.append(extensionesPrimeFaces.toString()).append(")$/");

		return extensionesPrimeFacesFin.toString();

	}

	// ________________________________________________________________________________________________________________________://
	// FIN EXTENSIONMES DE ARCHIVOS MAESTRO
	// __________________________________________________________________________________________________________________________//

	/* sub version */
	public void createSubVersionUsuario(SubVersionUsuario subVersionUsuario)
			throws ExceptionSubVersion {
		try {
			if (isEmptyOrNull(subVersionUsuario.getUsuariosubversion())) {
				subVersionUsuario.setUsuariosubversion("Anonymous");
			}
			if (isEmptyOrNull(subVersionUsuario.getPasswordsubversion())) {
				subVersionUsuario.setPasswordsubversion("Anonymous");
			}

			if (isEmptyOrNull(subVersionUsuario.getPasswordsubversion())
					|| isEmptyOrNull(subVersionUsuario.getUsuariosubversion())
					|| isEmptyOrNull(subVersionUsuario.getUrlsubversion())
					|| isEmptyOrNull(subVersionUsuario.getUsuario().getLogin())) {

				throw new ExceptionSubVersion("falta_data");

				/*
				 * ExceptionSubVersion( messages.getString("falta_data"));
				 */

			}
			SubVersionUsuario s = find(subVersionUsuario);
			if (s != null) {
				edit(subVersionUsuario);
			} else {
				create(subVersionUsuario);
			}

		} catch (ExceptionSubVersion e) {
			System.out.println("error controlado ...l");
			e.printStackTrace();
			throw new ExceptionSubVersion(messages.getString("falta_data"));
		}

	}

	public void create(SubVersionUsuario subVersionUsuario) {
		subVersionUsuario.setStatus(true);
		em.persist(subVersionUsuario);
	}

	public void edit(SubVersionUsuario subVersionUsuario) {
		em.merge(subVersionUsuario);
	}

	public void destroy(SubVersionUsuario subVersionUsuario) {

		subVersionUsuario = find(subVersionUsuario);
		// em.merge(diasHabiles);
		em.remove(subVersionUsuario);

	}

	public SubVersionUsuario find(SubVersionUsuario subVersionUsuario) {
		SubVersionUsuario objeto = null;
		try {
			if (subVersionUsuario != null
					&& subVersionUsuario.getUsuario() != null
					&& subVersionUsuario.getUsuario().getId() != null) {
				String str = "select object(o) from SubVersionUsuario as o where o.usuario.id="
						+ subVersionUsuario.getUsuario().getId();
				Query query = em.createQuery(str.toString());
				List<SubVersionUsuario> objetos = query.getResultList();
				for (SubVersionUsuario obj : objetos) {
					objeto = obj;
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return objeto;
	}

	/* fin subversion */

	/* inicio SvnUrlBase */

	public void create(SvnUrlBase objeto) {
		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnUrlBase objeto) {
		em.merge(objeto);
	}

	public void destroy(SvnUrlBase objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public SvnUrlBase find(SvnUrlBase objeto) {
		SvnUrlBase objeto1 = null;
		try {
			return (SvnUrlBase) em.find(SvnUrlBase.class, objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public List findAllSvnUrlBase(Usuario usuario, String strBuscar) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnUrlBase as o ");
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" where lower(o.nombre) like '")
					.append(strBuscar.toLowerCase()).append("%'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
		}
		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
		}

		sql.append(" order by lower(nombre) ");
		return em.createQuery(sql.toString()).getResultList();
	}

	/* fin SvnUrlBase */

	/* inicio tipo ambiente */

	public void create(SvnTipoAmbiente objeto) {
		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnTipoAmbiente objeto) {
		em.merge(objeto);
	}

	public void destroy(SvnTipoAmbiente objeto) {

		objeto = find(objeto);
		em.remove(objeto);

	}

	public SvnTipoAmbiente find(SvnTipoAmbiente objeto) {
		SvnNombreAplicacion objeto1 = null;
		try {
			return (SvnTipoAmbiente) em.find(SvnTipoAmbiente.class,
					objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public List findAllSvnTipoAmbiente(Usuario usuario, String strBuscar) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnTipoAmbiente as o ");
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" where lower(o.nombre) like '")
					.append(strBuscar.toLowerCase()).append("%'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append("  and o.svnnombreaplicacion.status=").append(
					Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
			sql.append("  and o.svnnombreaplicacion.status=").append(
					Utilidades.isActivo());
		}
		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.svnnombreaplicacion.svnUrlBase.empresa.nodo=")
					.append(usuario.getEmpresa().getNodo());
		}

		sql.append(" order by lower(o.nombre) ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllSvnTipoAmbiente(SvnNombreAplicacion svnNombreAplicacion) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnTipoAmbiente as o ");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.svnnombreaplicacion.codigo=").append(
				svnNombreAplicacion.getCodigo());
		sql.append("  and o.svnnombreaplicacion.status=").append(
				Utilidades.isActivo());

		sql.append(" order by lower(o.nombre) ");
		return em.createQuery(sql.toString()).getResultList();
	}

	/* fin tipo ambiente */

	/* inicio SvnNombreAplicacion */

	public void create(SvnNombreAplicacion objeto) {
		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnNombreAplicacion objeto) {
		em.merge(objeto);
	}

	public void destroy(SvnNombreAplicacion objeto) {

		objeto = find(objeto);
		em.remove(objeto);

	}

	public SvnNombreAplicacion find(SvnNombreAplicacion objeto) {
		SvnNombreAplicacion objeto1 = null;
		try {
			return (SvnNombreAplicacion) em.find(SvnNombreAplicacion.class,
					objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public List findAllSvnNombreAplicacion(Usuario usuario, String strBuscar) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnNombreAplicacion as o ");
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" where lower(o.nombre) like '")
					.append(strBuscar.toLowerCase()).append("%'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			sql.append("  and o.svnUrlBase.status=").append(
					Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
			sql.append("  and o.svnUrlBase.status=").append(
					Utilidades.isActivo());
		}

		if (usuario != null && usuario.getEmpresa() != null) {
			sql.append("  and o.svnUrlBase.empresa.nodo=").append(
					usuario.getEmpresa().getNodo());
		}

		sql.append(" order by lower(o.nombre) ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllSvnNombreAplicacion(SvnUrlBase objeto) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnNombreAplicacion as o ");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append(" and o.svnUrlBase.codigo=").append(objeto.getCodigo());
		sql.append("  and o.svnUrlBase.status=").append(Utilidades.isActivo());

		sql.append(" order by lower(o.nombre) ");

		return em.createQuery(sql.toString()).getResultList();
	}

	/* fin SvnNombreAplicacion */

	/* inicio SvnModulo */

	public void create(SvnModulo objeto) {
		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnModulo objeto) {
		em.merge(objeto);
	}

	public void destroy(SvnModulo objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public SvnModulo find(SvnModulo objeto) {
		SvnModulo objeto1 = null;
		try {
			return (SvnModulo) em.find(SvnModulo.class, objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return objeto1;
	}

	public List findAllSvnModulo(SvnTipoAmbiente svnTipoAmbiente) {
		StringBuffer sql = new StringBuffer();
		if (svnTipoAmbiente == null) {
			return null;
		}
		sql.append("select object(o) from SvnModulo as o ");

		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append("  and o.svnTipoAmbiente.status=").append(
				Utilidades.isActivo());
		sql.append(" and o.svnTipoAmbiente.codigo=").append(
				svnTipoAmbiente.getCodigo());

		sql.append(" order by o.codigo ");
		return em.createQuery(sql.toString()).getResultList();
	}

	/* fin SvnModulo */

	/* inicio SvnExtension */

	public void create(SvnExtension objeto) {
		ExtensionFile extensionFile = new ExtensionFile();
		extensionFile.setExtension(objeto.getExt());
		extensionFile = find_ExtensionFileExiste(extensionFile);
		if (extensionFile == null) {
			extensionFile = new ExtensionFile();
			extensionFile.setExtension(objeto.getExt());
			extensionFile.setMimeType("text/plain");
			extensionFile.setStatus(true);
			em.persist(extensionFile);
		}

		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnExtension objeto) {
		ExtensionFile extensionFile = new ExtensionFile();
		extensionFile.setExtension(objeto.getExt());
		extensionFile = find_ExtensionFileExiste(extensionFile);

		if (extensionFile == null) {
			extensionFile = new ExtensionFile();
			extensionFile.setExtension(objeto.getExt());
			extensionFile.setMimeType("text/plain");
			extensionFile.setStatus(true);
			em.persist(extensionFile);
		}
		em.merge(objeto);
	}

	public void destroy(SvnExtension objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public SvnExtension find(SvnExtension objeto) {
		return (SvnExtension) em.find(SvnExtension.class, objeto.getCodigo());

	}

	public List findAllSvnExtension(String strBuscar) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnExtension as o ");
		if (strBuscar != null && strBuscar.length() > 0) {
			sql.append(" where lower(ext) = '").append(strBuscar.toLowerCase())
					.append("'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
		}

		sql.append(" order by lower(ext) ");
		System.out.println(sql.toString());
		return em.createQuery(sql.toString()).getResultList();
	}

	/* fin SvnExtension */

	/* inicio svnrutasrelativasfiles */

	public void create(SvnRutasRelativasFiles objeto) {

		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SvnRutasRelativasFiles objeto) {

		em.merge(objeto);
	}

	public void destroy(SvnRutasRelativasFiles objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public SvnRutasRelativasFiles find(SvnRutasRelativasFiles objeto) {
		return (SvnRutasRelativasFiles) em.find(SvnRutasRelativasFiles.class,
				objeto.getCodigo());

	}

	public SvnRutasRelativasFiles findSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment,
			String archivo) {
		SvnRutasRelativasFiles svnRutasRelativasFiles = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnRutasRelativasFiles as o ");
		if (flow_ParticipantesAttachment != null) {
			sql.append(" where o.flow_ParticipantesAttachment.codigo = ")
					.append(flow_ParticipantesAttachment.getCodigo())
					.append("");
			sql.append("  and o.archivo='").append(archivo).append("'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		}
		List<SvnRutasRelativasFiles> lista = em.createQuery(sql.toString())
				.getResultList();

		if (lista != null && !lista.isEmpty() && lista.size() > 0) {
			svnRutasRelativasFiles = lista.get(0);
			return svnRutasRelativasFiles;

		}
		return null;
	}

	public SvnRutasRelativasFiles findSvnRutasRelativasFiles(
			Doc_maestro doc_maestro, String archivo) {
		SvnRutasRelativasFiles svnRutasRelativasFiles = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnRutasRelativasFiles as o ");
		if (doc_maestro != null) {
			sql.append(" where o.doc_maestro.codigo = ")
					.append(doc_maestro.getCodigo()).append("");
			sql.append("  and o.archivo='").append(archivo).append("'");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		}

		List<SvnRutasRelativasFiles> lista = em.createQuery(sql.toString())
				.getResultList();
		if (lista != null && !lista.isEmpty() && lista.size() > 0) {
			svnRutasRelativasFiles = lista.get(0);
			return svnRutasRelativasFiles;

		}
		return null;
	}

	public List findAllSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnRutasRelativasFiles as o ");
		if (flow_ParticipantesAttachment != null) {
			sql.append(" where o.flow_ParticipantesAttachment.codigo = ")
					.append(flow_ParticipantesAttachment.getCodigo())
					.append("");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
		}
		return em.createQuery(sql.toString()).getResultList();
	}

	public List findAllSvnRutasRelativasFiles(Doc_maestro doc_maestro) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from SvnRutasRelativasFiles as o ");
		if (doc_maestro != null) {
			sql.append(" where o.doc_maestro.codigo = ")
					.append(doc_maestro.getCodigo()).append("");
			sql.append("  and o.status=").append(Utilidades.isActivo());
		} else {
			sql.append("  where o.status=").append(Utilidades.isActivo());
		}
		return em.createQuery(sql.toString()).getResultList();
	}

	private static boolean isNumeric(String cadena) {

		try {

			Integer.parseInt(cadena);

			return true;

		} catch (NumberFormatException nfe) {

			return false;

		}

	}

	/* fin svnrutasrelativasfiles */

	/* inicio Solicitudimpresion */

	public void create(Solicitudimpresion objeto) {

		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(Solicitudimpresion objeto) {

		em.merge(objeto);
	}

	public void destroy(Solicitudimpresion objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public Solicitudimpresion find(Solicitudimpresion objeto) {
		return (Solicitudimpresion) em.find(Solicitudimpresion.class,
				objeto.getCodigo());
	}

	public Solicitudimpresion findSolicitudimpresionByFlow(
			Solicitudimpresion solicitudimpresion) {
		Solicitudimpresion objeto = null;
		String str = "select object(o) from Solicitudimpresion as o where o.flow.codigo="
				+ solicitudimpresion.getFlow().getCodigo() + "";
		str += " and o.status=" + Utilidades.isActivo();
		str += " order by o.codigo desc ";
		Query query = em.createQuery(str.toString());
		List<Solicitudimpresion> objetos = query.getResultList();
		for (Solicitudimpresion obj : objetos) {
			objeto = obj;
			// CHEQUEAMOS SI YA TODOS IMPRIMIERON.. SI ES ASI COLOCAMOS
			// SOLICITUD IMPRESIONB OBSOLETO
			List<SolicitudImpPart> solicitudImpPartsLst = findAllsolicitudImpPartsSinImprimir(objeto);
			if (solicitudImpPartsLst == null || solicitudImpPartsLst.isEmpty()) {
				// cancelamos la solicitusd de impresion
				Doc_estado doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getObsoleto());
				doc_estado = findDocEstado(doc_estado);
				objeto.setEstado(doc_estado);
				edit(objeto);
			}
			break;
		}
		return objeto;
	}

	public Solicitudimpresion findSolicitudimpresionByFlowDelete(
			Solicitudimpresion solicitudimpresion) {
		Solicitudimpresion objeto = null;
		String str = "select object(o) from Solicitudimpresion as o where o.flow.codigo="
				+ solicitudimpresion.getFlow().getCodigo() + "";
		str += " order by o.codigo desc ";
		Query query = em.createQuery(str.toString());
		List<Solicitudimpresion> objetos = query.getResultList();
		for (Solicitudimpresion obj : objetos) {
			objeto = obj;
			// CHEQUEAMOS SI YA TODOS IMPRIMIERON.. SI ES ASI COLOCAMOS
			// SOLICITUD IMPRESIONB OBSOLETO
			List<SolicitudImpPart> solicitudImpPartsLst = findAllsolicitudImpPartsSinImprimir(objeto);
			if (solicitudImpPartsLst == null || solicitudImpPartsLst.isEmpty()) {
				// cancelamos la solicitusd de impresion
				Doc_estado doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getObsoleto());
				doc_estado = findDocEstado(doc_estado);
				objeto.setEstado(doc_estado);
				edit(objeto);
			}
			break;
		}
		return objeto;
	}

	public Solicitudimpresion findSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion) {
		Solicitudimpresion objeto = null;
		if (solicitudimpresion != null
				&& solicitudimpresion.getDoc_detalle() != null) {
			String str = "select object(o) from Solicitudimpresion as o where o.doc_detalle.codigo="
					+ solicitudimpresion.getDoc_detalle().getCodigo() + "";
			str += " and o.status=" + Utilidades.isActivo();
			if (solicitudimpresion.isSwMostrarAll()) {
				// NO FILTRAMOS
			} else {
				str += " and o.estado.codigo <>" + Utilidades.getObsoleto();
			}

			str += " order by o.codigo desc ";
			// System.out.println("sql="+str);
			Query query = em.createQuery(str.toString());
			List<Solicitudimpresion> objetos = query.getResultList();
			for (Solicitudimpresion obj : objetos) {
				objeto = obj;
				// CHEQUEAMOS SI YA TODOS IMPRIMIERON.. SI ES ASI COLOCAMOS
				// SOLICITUD IMPRESIONB OBSOLETO
				List<SolicitudImpPart> solicitudImpPartsLst = findAllsolicitudImpPartsSinImprimir(objeto);
				if (solicitudImpPartsLst == null
						|| solicitudImpPartsLst.isEmpty()) {
					// cancelamos la solicitusd de impresion
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getObsoleto());
					doc_estado = findDocEstado(doc_estado);
					objeto.setEstado(doc_estado);
					edit(objeto);
				}

				objeto.setsolicitudImpParts(findAllsolicitudImpParts(objeto));
				break;
			}
		}

		return objeto;
	}

	public List<Solicitudimpresion> findAllSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion) {
		Solicitudimpresion objeto = null;
		List<Solicitudimpresion> objetos = null;
		List<Solicitudimpresion> objetoSalir = new ArrayList<Solicitudimpresion>();
		if (solicitudimpresion != null
				&& solicitudimpresion.getDoc_detalle() != null) {
			StringBuffer str = new StringBuffer(
					"select object(o) from Solicitudimpresion as o where ");
			if (solicitudimpresion.getDoc_detalle() != null) {
				str.append(" o.doc_detalle.codigo= ");
				str.append(solicitudimpresion.getDoc_detalle().getCodigo());
			}

			if (solicitudimpresion.getSolicitante() != null) {
				str.append(" o.solicitante.empresa.nodo= ");
				str.append(solicitudimpresion.getSolicitante().getEmpresa()
						.getNodo());
			}

			str.append(" and o.status=" + Utilidades.isActivo());
			str.append(" order by o.codigo desc ");
			// System.out.println("sql="+str);
			Query query = em.createQuery(str.toString());
			objetos = query.getResultList();
			for (Solicitudimpresion obj : objetos) {
				objeto = obj;
				// CHEQUEAMOS SI YA TODOS IMPRIMIERON.. SI ES ASI COLOCAMOS
				// SOLICITUD IMPRESIONB OBSOLETO
				List<SolicitudImpPart> solicitudImpPartsLst = findAllsolicitudImpPartsSinImprimir(objeto);
				if (solicitudImpPartsLst == null
						|| solicitudImpPartsLst.isEmpty()) {
					// cancelamos la solicitusd de impresion
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getObsoleto());
					doc_estado = findDocEstado(doc_estado);
					objeto.setEstado(doc_estado);
					edit(objeto);
				}

				objeto.setsolicitudImpParts(findAllsolicitudImpParts(objeto));
				objetoSalir.add(objeto);

			}
		}

		return objetoSalir;
	}

	/* fin Solicitudimpresion */

	/* inicio SolicitudImpPart */

	public void create(SolicitudImpPart objeto) {

		objeto.setStatus(true);
		em.persist(objeto);
	}

	public void edit(SolicitudImpPart objeto) {

		em.merge(objeto);
	}

	public void destroy(SolicitudImpPart objeto) {

		objeto = find(objeto);
		// em.merge(diasHabiles);
		em.remove(objeto);

	}

	public SolicitudImpPart find(SolicitudImpPart objeto) {
		return (SolicitudImpPart) em.find(SolicitudImpPart.class,
				objeto.getCodigo());

	}

	/*
	 * 
	 * solicitudimpresionparticipantes usuario=id estado = 16 status = true
	 * 
	 * solicitudimpresion sttaus=true estado=aprobado doc_detalle
	 */

	public List<SolicitudImpPart> findsolicitudImpPartsToSendToImprimir(
			SolicitudImpPart solicitudImpPart) {
		List<SolicitudImpPart> objetos = null;
		StringBuffer str = new StringBuffer(
				"select object(o) from SolicitudImpPart as o where o.solicitudimpresion.doc_detalle.doc_maestro.codigo=");
		str.append(solicitudImpPart.getSolicitudimpresion().getDoc_detalle()
				.getDoc_maestro().getCodigo());
		str.append(" and  o.solicitudimpresion.estado.codigo=").append(
				Utilidades.getAprobado());
		str.append(" and o.solicitudimpresion.status=" + Utilidades.isActivo());
		str.append(" and o.estado.codigo=").append(Utilidades.getImprimirsin());
		str.append(" and o.usuario.id=").append(
				solicitudImpPart.getUsuario().getId());
		str.append(" and o.status=" + Utilidades.isActivo());
		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();
		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsEmpresa(
			SolicitudImpPart solicitudImpPart) {
		List<SolicitudImpPart> objetos = null;
		StringBuffer str = new StringBuffer(
				"select object(o) from SolicitudImpPart as o where ");
		if (solicitudImpPart.getSolicitudimpresion().getDoc_detalle() != null) {
			str.append("  o.solicitudimpresion.doc_detalle.codigo=");
			str.append(solicitudImpPart.getSolicitudimpresion()
					.getDoc_detalle().getCodigo());
			str.append(" and ");
		}
		str.append(" ( ");
		str.append("  o.solicitudimpresion.estado.codigo=").append(
				Utilidades.getEnAprobacion());

		str.append(" and  o.solicitudimpresion.flow.estado.codigo=").append(
				Utilidades.getEnAprobacion());
		str.append(" ) ");
		str.append(" or ");
		str.append(" ( ");
		str.append("  o.solicitudimpresion.estado.codigo=").append(
				Utilidades.getAprobado());

		str.append(" and  o.solicitudimpresion.flow.estado.codigo=").append(
				Utilidades.getAprobado());
		str.append(" ) ");

		str.append(" and o.estado.codigo=").append(Utilidades.getImprimirsin());
		str.append(" and o.usuario.empresa.nodo=").append(
				solicitudImpPart.getUsuario().getEmpresa().getNodo());
		str.append(" and o.status=" + Utilidades.isActivo());
		str.append(" and o.solicitudimpresion.status=" + Utilidades.isActivo());
		str.append(" order by codigo desc");

		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();

		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpParts(
			SolicitudImpPart solicitudImpPart) {
		List<SolicitudImpPart> objetos = null;
		StringBuffer str = new StringBuffer(
				"select object(o) from SolicitudImpPart as o where ");
		if (solicitudImpPart.getSolicitudimpresion().getDoc_detalle() != null) {
			str.append("  o.solicitudimpresion.doc_detalle.doc_maestro.codigo=");
			str.append(solicitudImpPart.getSolicitudimpresion()
					.getDoc_detalle().getDoc_maestro().getCodigo());
			str.append(" and ");
		}

		str.append(" ( o.solicitudimpresion.estado.codigo=").append(
				Utilidades.getAprobado());
		str.append(" or o.solicitudimpresion.estado.codigo=").append(
				Utilidades.getEnAprobacion());
		str.append(" ) ");

		str.append(" and ( o.solicitudimpresion.flow.estado.codigo=").append(
				Utilidades.getAprobado());
		str.append(" or o.solicitudimpresion.flow.estado.codigo=").append(
				Utilidades.getEnAprobacion());
		str.append(" ) ");

		str.append(" and o.estado.codigo=").append(Utilidades.getImprimirsin());
		str.append(" and o.usuario.id=").append(
				solicitudImpPart.getUsuario().getId());
		str.append(" and o.status=" + Utilidades.isActivo());
		str.append(" and o.solicitudimpresion.status=" + Utilidades.isActivo());

		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();

		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpParts(
			Solicitudimpresion solicitudimpresion) {
		List<SolicitudImpPart> objetos = null;
		String str = "select object(o) from SolicitudImpPart as o where o.solicitudimpresion.codigo="
				+ solicitudimpresion.getCodigo() + "";
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();

		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsDocDetalle(
			SolicitudImpPart solicitudImpPart) {
		List<SolicitudImpPart> objetos = null;
		if (solicitudImpPart != null) {
			StringBuffer str = new StringBuffer(
					"select object(o) from SolicitudImpPart as o where o.solicitudimpresion.doc_detalle.codigo=");
			str.append(solicitudImpPart.getSolicitudimpresion()
					.getDoc_detalle().getCodigo());
			str.append(" and o.status=").append(Utilidades.isActivo());
			str.append(" and o.solicitudimpresion.status=").append(
					Utilidades.isActivo());
			str.append(" order by o.codigo desc");
			Query query = em.createQuery(str.toString());
			objetos = query.getResultList();
		}

		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsDelete(
			Solicitudimpresion solicitudimpresion) {
		List<SolicitudImpPart> objetos = null;
		if (solicitudimpresion != null) {
			String str = "select object(o) from SolicitudImpPart as o where o.solicitudimpresion.codigo="
					+ solicitudimpresion.getCodigo() + "";
			Query query = em.createQuery(str.toString());
			objetos = query.getResultList();
		}

		return objetos;
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsSinImprimir(
			Solicitudimpresion solicitudimpresion) {

		Doc_estado doc_estado = new Doc_estado();
		doc_estado.setCodigo(Utilidades.getImprimirsin());
		doc_estado = findDocEstado(doc_estado);

		List<SolicitudImpPart> objetos = null;
		String str = "select object(o) from SolicitudImpPart as o where o.solicitudimpresion.codigo="
				+ solicitudimpresion.getCodigo() + "";
		str += " and o.estado.codigo=" + doc_estado.getCodigo();
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();

		return objetos;
	}

	public List<SolicitudImpPart> solicitudImpPartsCancelar(
			SolicitudImpPart objeto) {
		Doc_estado doc_estado = null;
		List<SolicitudImpPart> objetos = null;
		String str = "select object(o) from SolicitudImpPart as o where o.codigo="
				+ objeto.getCodigo() + "";
		str += " and o.status=" + Utilidades.isActivo();
		Query query = em.createQuery(str.toString());
		objetos = query.getResultList();

		if (objeto.getSolicitudimpresion() != null) {
			if (objeto.getUsuario() != null) {
				if (objetos != null && !objetos.isEmpty()) {
					SolicitudImpPart solicitudImpParts = objetos.get(0);
					doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getCanceladoByDuenio());
					doc_estado = findDocEstado(doc_estado);
					solicitudImpParts.setEstado(doc_estado);
					edit(solicitudImpParts);
					// CHEQUEAMOS SI YA TODOS IMPRIMIERON.. SI ES ASI COLOCAMOS
					// SOLICITUD IMPRESIONB OBSOLETO
					List<SolicitudImpPart> solicitudImpPartsLst = findAllsolicitudImpPartsSinImprimir(objeto
							.getSolicitudimpresion());
					if (solicitudImpPartsLst == null
							|| solicitudImpPartsLst.isEmpty()) {
						// obsoleta la solicitusd de impresion
						doc_estado = new Doc_estado();
						doc_estado.setCodigo(Utilidades.getObsoleto());
						doc_estado = findDocEstado(doc_estado);
						objeto.getSolicitudimpresion().setEstado(doc_estado);
						edit(objeto.getSolicitudimpresion());
					}
				}
			} else {
				// SI NO HAY USUARIOS.. QUIERE DECIR QUE SE CANCELAN TODOS
				for (SolicitudImpPart sipCancelar : objetos) {
					doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getCanceladoByDuenio());
					doc_estado = findDocEstado(doc_estado);
					sipCancelar.setEstado(doc_estado);
					edit(sipCancelar);
				}
				// obsoleta la solicitusd de impresion
				doc_estado = new Doc_estado();
				doc_estado.setCodigo(Utilidades.getObsoleto());
				doc_estado = findDocEstado(doc_estado);
				objeto.getSolicitudimpresion().setEstado(doc_estado);
				edit(objeto.getSolicitudimpresion());
			}

		}
		return objetos;
	}

	public void putPublicoDocumento(Doc_detalle doc_detalle,
			List<Role> roleSeleccionados, Usuario publicador)
			throws EcologicaExcepciones {

		List<Usuario> usuariosTotales = new ArrayList<Usuario>();

		doc_detalle.getDoc_maestro().setPublico(
				doc_detalle.getDoc_maestro().isPublico());
		doc_detalle.setPublicador(publicador);

		// SI ES PUBLICO COLOCAMOS LAS NUEVAS FECHAS DE EXPIRADOS PUBLIXCACION
		if (doc_detalle.getDoc_maestro().isPublico()) {
			doc_detalle.setFechapubli(new Date());
		} else {
			// SI NO ES PUBLICO BORRAMOS LAS FECHAS DE EXPIRADOS PUBLIXCACION
			doc_detalle.setFechapubli(null);
			doc_detalle.setFechaexpirapubli(null);
		}

		// borramos todos los que esten aqui en roles ..
		List<RolesOnlyViewDocPublicados> rolesOnlyViewDocPublicadosLst = findAllRolesOnlyViewDocPublicados(doc_detalle);
		if (rolesOnlyViewDocPublicadosLst != null) {
			for (RolesOnlyViewDocPublicados r : rolesOnlyViewDocPublicadosLst) {
				destroy(r);
			}
		}
		// fin borramos todos los que esten aqui en roles ..

		RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados = null;
		if (roleSeleccionados != null && roleSeleccionados.size() > 0) {
			for (Role r : roleSeleccionados) {
				rolesOnlyViewDocPublicados = new RolesOnlyViewDocPublicados();
				rolesOnlyViewDocPublicados.setRole(r);
				rolesOnlyViewDocPublicados.setDoc_detalle(doc_detalle);
				create(rolesOnlyViewDocPublicados);

				// MANDAR MAILS AUSUARIOS DE GRUPOS
				if (doc_detalle.getDoc_maestro().isPublico()) {
					List<Usuario> usuarios = findRoles_Usuario(r);
					usuariosTotales.addAll(usuarios);

				}

			}

			doc_detalle.setPublitogrupo(true);

		} else {

			doc_detalle.setPublitogrupo(false);
		}
		edit(doc_detalle.getDoc_maestro());
		edit(doc_detalle);

		if (doc_detalle.getDoc_maestro().isPublico()) {
			try {
				// PARA TOD EL MUNDO
				if (!doc_detalle.isPublitogrupo()) {
					usuariosTotales = findAll(doc_detalle.getPublicador(), null);
				}
				// MANDAR MAILS A TODOS LOS USUARIOS DE GRUPOS

				// ENCONTRAMOS EL AREA DEL DOCUMENTO
				Tree area = null;
				area = findEnNivelArribaTreeDeAcuerdoSuTipo(doc_detalle
						.getDoc_maestro().getTree(), Utilidades.getNodoArea());

				StringBuffer msg = new StringBuffer("");
				msg.append(messages.getString("documentopublicado") + ":"
						+ doc_detalle.getDoc_maestro().getNombre() + "\n"
						+ messages.getString("usuario_area") + ":" + area != null ? area
						.getNombre() : "" + "\n"
						+ messages.getString("doc_consecutivotab") + ":"
						+ doc_detalle.getDoc_maestro().getConsecutivo() + "\n"
						+ messages.getString("publicador") + ":"
						+ doc_detalle.getPublicador() + "\n"
						+ messages.getString("fecha_publicado") + ":"
						+ doc_detalle.getFechapubliStr() + "\n"
						+ messages.getString("usuario_fechab") + ":"
						+ doc_detalle.getFechaexpirapubliStr() + "\n\n\n");

				MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
						mandarMailsUsuariosDocPublico(usuariosTotales,
								doc_detalle, msg.toString()),
						find_allConfiguracion(), null);
				myHiloEnvioMails.start();
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

	}

	public void notificarPorMailPublicoExpiro(Doc_detalle doc_detalle) {

		try {
			List<Usuario> usuariosTotales = new ArrayList<Usuario>();
			// usuariosTotales.add(doc_detalle.getDuenio());
			usuariosTotales.add(doc_detalle.getPublicador());
			// ENCONTRAMOS EL AREA DEL DOCUMENTO
			Tree area = null;
			area = findEnNivelArribaTreeDeAcuerdoSuTipo(doc_detalle
					.getDoc_maestro().getTree(), Utilidades.getNodoArea());

			StringBuffer msg = new StringBuffer("");
			String emp = "";
			try {
				emp = "["
						+ doc_detalle.getPublicador().getEmpresa().getNombre()
						+ "]";
			} catch (Exception e) {
				// TODO: handle exception
			}
			msg.append(messages.getString("documentoexpirado") + "\n\n"
					+ messages.getString("documentopublicado") + ":"
					+ doc_detalle.getDoc_maestro().getNombre() + "\n"
					+ messages.getString("usuario_area") + ":" + area != null ? area
					.getNombre() : "" + "\n"
					+ messages.getString("doc_consecutivotab") + ":"
					+ doc_detalle.getDoc_maestro().getConsecutivo() + "\n"
					+ messages.getString("publicador") + emp + ":"
					+ doc_detalle.getPublicador() + "\n"
					+ messages.getString("fecha_publicado") + ":"
					+ doc_detalle.getFechapubliStr() + "\n"
					+ messages.getString("usuario_fechab") + ":"
					+ doc_detalle.getFechaexpirapubliStr() + "\n\n\n");

			MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
					mandarMailsUsuariosDocPublico(usuariosTotales, doc_detalle,
							msg.toString()), find_allConfiguracion(), null);
			myHiloEnvioMails.start();
			doc_detalle.setPubliMailexpiro(true);
			edit(doc_detalle);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/* fin SolicitudImpPart */

	/* inicio tipo RolesOnlyViewDocPublicados */

	public void create(RolesOnlyViewDocPublicados objeto) {
		em.persist(objeto);
	}

	public void edit(RolesOnlyViewDocPublicados objeto) {
		em.merge(objeto);
	}

	public void destroy(RolesOnlyViewDocPublicados objeto) {

		objeto = find(objeto);
		em.remove(objeto);

	}

	public RolesOnlyViewDocPublicados find(RolesOnlyViewDocPublicados objeto) {
		RolesOnlyViewDocPublicados objeto1 = null;
		try {
			return (RolesOnlyViewDocPublicados) em.find(
					RolesOnlyViewDocPublicados.class, objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public List findAllRolesOnlyViewDocPublicados(Doc_detalle doc_detalle) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from RolesOnlyViewDocPublicados as o ");

		if (doc_detalle != null && doc_detalle.getCodigo() != null) {
			sql.append("  where o.doc_detalle.codigo=").append(
					doc_detalle.getCodigo());
		}
		sql.append(" ");
		return em.createQuery(sql.toString()).getResultList();
	}

	public RolesOnlyViewDocPublicados findAllRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from RolesOnlyViewDocPublicados as o ");
		boolean swAnd = false;
		if (rolesOnlyViewDocPublicados != null
				&& rolesOnlyViewDocPublicados.getRole() != null) {
			if (rolesOnlyViewDocPublicados.getRole() != null) {
				sql.append("  where o.role.codigo=").append(
						rolesOnlyViewDocPublicados.getRole().getCodigo());
				swAnd = true;
			} else {

			}
		}
		if (rolesOnlyViewDocPublicados != null
				&& rolesOnlyViewDocPublicados.getDoc_detalle() != null) {
			if (swAnd) {
				sql.append(" and ");
			} else {
				sql.append(" where ");
			}
			sql.append("   o.doc_detalle.codigo=").append(
					rolesOnlyViewDocPublicados.getDoc_detalle().getCodigo());
		}
		sql.append(" ");
		List<RolesOnlyViewDocPublicados> objList = em.createQuery(
				sql.toString()).getResultList();
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	public List<RolesOnlyViewDocPublicados> findAllRolesOnlyViewDocPublicadosLst(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from RolesOnlyViewDocPublicados as o ");
		boolean swAnd = false;
		if (rolesOnlyViewDocPublicados != null
				&& rolesOnlyViewDocPublicados.getRole() != null) {
			if (rolesOnlyViewDocPublicados.getRole() != null) {
				sql.append("  where o.role.codigo=").append(
						rolesOnlyViewDocPublicados.getRole().getCodigo());
				swAnd = true;
			} else {

			}
		}
		if (rolesOnlyViewDocPublicados != null
				&& rolesOnlyViewDocPublicados.getDoc_detalle() != null) {
			if (swAnd) {
				sql.append(" and ");
			} else {
				sql.append(" where ");
			}
			sql.append("   o.doc_detalle.codigo=").append(
					rolesOnlyViewDocPublicados.getDoc_detalle().getCodigo());
		}
		sql.append(" ");
		List<RolesOnlyViewDocPublicados> objList = em.createQuery(
				sql.toString()).getResultList();

		return objList;

	}

	public Boolean usuarioRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados) {
		// buscamos los roles que tiene el documento doc detalle
		List<RolesOnlyViewDocPublicados> rolesOnlyViewDocPublicadosLst = (List<RolesOnlyViewDocPublicados>) findAllRolesOnlyViewDocPublicadosLst(rolesOnlyViewDocPublicados);
		// buscamos por cada rol. el rol_usuario..
		// y comparamos el rol_usuario.usuario al usuario logueado que viene de
		// rolesOnlyViewDocPublicados
		for (RolesOnlyViewDocPublicados r : rolesOnlyViewDocPublicadosLst) {
			List<Usuario> usuarios = findRoles_Usuario(r.getRole());
			for (Usuario usu : usuarios) {
				if (usu.getId()
						- rolesOnlyViewDocPublicados.getUsuario().getId() == 0) {
					return true;
				}
			}

		}
		return false;
	}

	/* fin RolesOnlyViewDocPublicados */

	/* inicio tipo PublicadosUsuComent */

	public void create(PublicadosUsuComent objeto) {
		em.persist(objeto);
	}

	public void edit(PublicadosUsuComent objeto) {
		em.merge(objeto);
	}

	public void destroy(PublicadosUsuComent objeto) {

		objeto = find(objeto);
		em.remove(objeto);

	}

	public PublicadosUsuComent find(PublicadosUsuComent objeto) {
		PublicadosUsuComent objeto1 = null;
		try {
			return (PublicadosUsuComent) em.find(PublicadosUsuComent.class,
					objeto.getCodigo());

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public PublicadosUsuComent findOnePublicadosUsuComent(
			PublicadosUsuComent publicadosUsuComent) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from PublicadosUsuComent as o ");
		boolean swAnd = false;
		if (publicadosUsuComent != null) {
			if (publicadosUsuComent.getUsuario() != null) {
				sql.append("  where o.usuario.id=").append(
						publicadosUsuComent.getUsuario().getId());
				swAnd = true;
			} else {

			}
		}
		if (publicadosUsuComent != null
				&& publicadosUsuComent.getDoc_detalle() != null) {
			if (swAnd) {
				sql.append(" and ");
			} else {
				sql.append(" where ");
			}
			sql.append("   o.doc_detalle.codigo=").append(
					publicadosUsuComent.getDoc_detalle().getCodigo());
		}
		sql.append(" ");

		List<PublicadosUsuComent> objList = em.createQuery(sql.toString())
				.getResultList();
		if (objList != null && objList.size() > 0) {
			System.out.println("objList.get(0)==" + objList.get(0).getCodigo());
			return objList.get(0);
		} else {
			return null;
		}
	}

	public List<PublicadosUsuComent> findAllPublicadosUsuComentLst(
			PublicadosUsuComent publicadosUsuComent) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from PublicadosUsuComent as o ");
		boolean swAnd = false;
		if (publicadosUsuComent != null) {
			if (publicadosUsuComent.getUsuario() != null) {
				sql.append("  where o.usuario.id=").append(
						publicadosUsuComent.getUsuario().getId());
				swAnd = true;
			} else {

			}
		}
		if (publicadosUsuComent != null
				&& publicadosUsuComent.getDoc_detalle() != null) {
			if (swAnd) {
				sql.append(" and ");
			} else {
				sql.append(" where ");
			}
			sql.append("   o.doc_detalle.codigo=").append(
					publicadosUsuComent.getDoc_detalle().getCodigo());
		}
		sql.append(" ");

		System.out.println("sql.toString()==" + sql.toString());
		List<PublicadosUsuComent> objList = em.createQuery(sql.toString())
				.getResultList();

		return objList;

	}

	public List<PublicadosUsuComent> findAllPublicadosVersionesUsuComentLst(
			PublicadosUsuComent publicadosUsuComent) {
		StringBuffer sql = new StringBuffer();
		sql.append("select object(o) from PublicadosUsuComent as o ");
		boolean swAnd = false;
		if (publicadosUsuComent != null) {
			if (publicadosUsuComent.getUsuario() != null) {
				sql.append("  where o.usuario.id=").append(
						publicadosUsuComent.getUsuario().getId());
				swAnd = true;
			} else {

			}
		}
		if (publicadosUsuComent != null
				&& publicadosUsuComent.getDoc_detalle() != null) {
			if (swAnd) {
				sql.append(" and ");
			} else {
				sql.append(" where ");
			}
			sql.append("   o.doc_detalle.doc_maestro.codigo=").append(
					publicadosUsuComent.getDoc_detalle().getDoc_maestro()
							.getCodigo());
		}
		sql.append(" order by o.doc_detalle.codigo desc");

		List<PublicadosUsuComent> objList = em.createQuery(sql.toString())
				.getResultList();

		return objList;

	}

	// ________________________________________________________________________________________________________________________://
	// AreaDocumentos
	// __________________________________________________________________________________________________________________________//
	public void create(AreaDocumentos objeto) {
		try {
			objeto.setStatus(true);
			em.persist(objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(AreaDocumentos objeto) {
		try {
			em.merge(objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy(AreaDocumentos objeto) {
		try {
			// em.merge(profesion);
			objeto = (AreaDocumentos) em.find(AreaDocumentos.class,
					objeto.getCodigo());
			em.remove(objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AreaDocumentos find(AreaDocumentos pk) {

		try {
			return (AreaDocumentos) em.find(AreaDocumentos.class,
					pk.getCodigo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// cambiado Encontrar
	public AreaDocumentos findAreaDocumentos(String id) {
		try {
			// Profesion prof= em.find(Profesion.class, id);
			AreaDocumentos obj = new AreaDocumentos();
			StringBuffer sql = new StringBuffer(
					"select object(o) from AreaDocumentos as o where o.codigo=:codigo ");
			sql.append("  and o.status=").append(Utilidades.isActivo());
			Query query = em.createQuery(sql.toString());
			query.setParameter("codigo", new Long(id));
			List lst = query.getResultList();
			Iterator it = lst.iterator();
			if (it.hasNext()) {
				obj = (AreaDocumentos) it.next();
				return obj;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// cambiado findAll
	public List findAllAreaDocumentos(Usuario usuario, String search) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from AreaDocumentos as o");
			if (search != null && search.length() > 0) {
				sql.append(" where lower(o.nombre) like '%")
						.append(search.toLowerCase()).append("%'");
				sql.append("  and o.status=").append(Utilidades.isActivo());
			} else {
				sql.append("  where o.status=").append(Utilidades.isActivo());
			}
			if (usuario != null && usuario.getEmpresa() != null) {
				sql.append("  and o.empresa.nodo=").append(
						usuario.getEmpresa().getNodo());
			}

			sql.append(" order by lower(o.nombre)");
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean findExisteAreaDocumentosDocDetallesFlowParalelo(
			AreaDocumentos areaDocumentos) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select object(o) from Doc_detalle as o");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append(
				" and o.areaDocumentos is not null  and o.areaDocumentos.codigo=")
				.append(areaDocumentos.getCodigo());

		if (em.createQuery(sql.toString()).getResultList() != null
				&& em.createQuery(sql.toString()).getResultList().size() > 0) {
			return true;
		}

		sql = new StringBuffer("");
		sql.append("select object(o) from FlowParalelo as o");
		sql.append("  where o.status=").append(Utilidades.isActivo());
		sql.append(
				"  and o.areaDocumentos is not null  and o.areaDocumentos.codigo=")
				.append(areaDocumentos.getCodigo());

		if (em.createQuery(sql.toString()).getResultList() != null
				&& em.createQuery(sql.toString()).getResultList().size() > 0) {
			return true;
		}
		em.clear();

		return false;
	}

	public List findAllAreaDocumentos(Usuario usuario) {
		List lista = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from AreaDocumentos as o");
			sql.append("  where o.status=").append(Utilidades.isActivo());
			if (usuario != null && usuario.getEmpresa() != null) {
				sql.append("  and o.empresa.nodo=").append(
						usuario.getEmpresa().getNodo());
			}
			sql.append(" order by lower(o.nombre)");
			lista = em.createQuery(sql.toString()).getResultList();
			if (lista == null || lista.isEmpty()) {
				AreaDocumentos area = new AreaDocumentos();
				area.setNombre("Area");
				area.setDescripcion("Area");
				area.setStatus(true);
				area.setEmpresa(usuario.getEmpresa());
				create(area);
				lista = new ArrayList();
				lista.add(area);
			}

			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ________________________________________________________________________________________________________________________://
	// FIN AreaDocumentos
	// __

	/* fin PublicadosUsuComent */

	public List findAllScannerDoc(Usuario usuario) {
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select object(o) from ScannerDoc as o");

			if (usuario != null) {
				sql.append("  where o.usuario.id=").append(usuario.getId());
			}
			return em.createQuery(sql.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void destroy(ScannerDoc objeto) {
		try {
			// em.merge(profesion);
			objeto = (ScannerDoc) em.find(ScannerDoc.class, objeto.getCodigo());
			em.remove(objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// *****************************************
	// *****************************************
	// *****************************************
	// ARBOL-------------------
	// *****************************************
	// *****************************************
	// *****************************************

	public Seguridad getSeguridadTree(Map user_seguridad, Tree tree,
			Seguridad seguridadTree) {

		// si el usuario es root, tiene permiso para todo
		if (user_seguridad != null && user_seguridad.containsKey(tree)) {
			seguridadTree = user_seguridad.get(tree) != null ? (Seguridad) user_seguridad
					.get(tree) : new Seguridad();
		} else {

			seguridadTree = new Seguridad();
		}

		return seguridadTree;
	}

	public String seguridadArbol(Usuario user_logueado,
			Seguridad seguridadTree, Map user_seguridad) {

		StringBuffer cad = new StringBuffer();
		try {

			// listamos todas las raices padres
			List arbolPadresLst = null;

			arbolPadresLst = findAllTreePadres();

			Iterator arbolPadresIt = arbolPadresLst.iterator();
			char objArbol = 'e';
			while (arbolPadresIt.hasNext()) {

				Tree treePadre = (Tree) arbolPadresIt.next();

				seguridadTree = getSeguridadTree(user_seguridad, treePadre,
						seguridadTree);
				if (seguridadTree == null) {
					seguridadTree = new Seguridad();
				}

				if (user_logueado != null
						&& user_logueado.getLogin().equalsIgnoreCase(
								Utilidades.getRoot())
						|| seguridadTree.isToView()) {
					cad.append(objArbol).append("= new dTree('")
							.append(objArbol).append("');");
					cad.append("\n\r");
					cad.append("\n\r");
					// dTree.prototype.add = function(id, pid, name, url, title,
					// target, icon, iconOpen, open) {
					cad.append(objArbol)
							.append(".add(")
							.append(treePadre.getNodopadre())
							.append(",-1,'")
							.append(treePadre.getNombre() != null ? treePadre
									.getNombre().trim() : "Vacio").append("'");
					cad.append(",'./puente.jsf?");
					cad.append("idroot=").append(treePadre.getNodopadre());
					cad.append("'");

					cad.append(",'','','")
							.append(messagesConf.getString("img_raiz"))
							.append("');   ");
					// String nodos =
					// delegado.getNodosHijos(treePadre.getNodopadre(),
					// objArbol);
					String nodos = seguridadHijos(treePadre.getNodopadre(),
							objArbol, user_logueado, seguridadTree,
							user_seguridad);
					if ((nodos != null) && (nodos.length() > 0)) {
						cad.append(nodos);
					}
					cad.append("\n\r");
					cad.append(" document.write(").append(objArbol)
							.append(");");
					cad.append("\n\r");
					++objArbol;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cad.toString();
	}

	private String seguridadHijos(long nodoPadre, char objArbol,
			Usuario user_logueado, Seguridad seguridadTree, Map user_seguridad) {
		StringBuffer cad2 = new StringBuffer("");

		List nodosHijosLst = null;
		Tree estaInSeguridadTree = obtenerNodo(nodoPadre);

		seguridadTree = getSeguridadTree(user_seguridad, estaInSeguridadTree,
				seguridadTree);

		// /seguridadTree = getSeguridadTree(estaInSeguridadTree);
		// chequeamos la seguridad, si es root o tiene permiso de ver, se pinta
		// el arbol
		if (user_logueado != null
				&& user_logueado.getLogin().equalsIgnoreCase(
						Utilidades.getRoot())
				|| (seguridadTree != null && seguridadTree.isToView())) {

			nodosHijosLst = findAllTreeHijos(nodoPadre);

			if (nodosHijosLst != null) {
				Iterator nodosHijosIt = nodosHijosLst.iterator();
				Usuario user_cargo;
				while (nodosHijosIt.hasNext()) {
					Tree treeHijo = (Tree) nodosHijosIt.next();
					seguridadTree = getSeguridadTree(user_seguridad, treeHijo,
							seguridadTree);

					user_cargo = null;
					String nombre = null;
					String apellido = null;

					// chequeamos la seguridad, si es root o tiene permiso de
					// ver, se pinta el arbol
					if (user_logueado != null
							&& user_logueado.getLogin().equalsIgnoreCase(
									Utilidades.getRoot())
							|| (seguridadTree != null && seguridadTree
									.isToView())) {

						/*********************** Buscando Cargo *******************************************************************/
						if (treeHijo.getTiponodo() == Utilidades.getNodoCargo()) {
							user_cargo = new Usuario();
							user_cargo.setCargo(treeHijo);
							List staCargoUsu = findExisteCargoInUsuario(user_cargo);
							Iterator it = staCargoUsu.iterator();
							if (it.hasNext()) {

								user_cargo = (Usuario) it.next();
								nombre = Utilidades.getRemplazoCaracter(
										user_cargo.getNombre(),
										Utilidades.getCommilla(), '_');
								apellido = Utilidades.getRemplazoCaracter(
										user_cargo.getApellido(),
										Utilidades.getCommilla(), '_');
							}
						}
						/******************** Fin***Buscando Cargo *******************************************************************/
						cad2.append("\n\r");
						cad2.append(objArbol).append(".add(")
								.append(treeHijo.getNodo()).append(",")
								.append(treeHijo.getNodopadre()).append(",'");
						if (user_cargo != null && nombre != null) {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("[")
									.append(nombre != null ? nombre.trim() : "")
									.append(" ")
									.append(apellido != null ? apellido.trim()
											: "Vacio").append("]").append("'");
						} else {
							cad2.append(
									treeHijo.getNombre() != null ? treeHijo
											.getNombre().trim() : "Vacio")
									.append("'");
						}

						// ___________________________________________________________________________________________
						// primera pag con parametros
						cad2.append(",'./puente.jsf?");
						cad2.append("idroot=").append(treeHijo.getNodo());
						cad2.append("'");

						// ___________________________________________________________________________________________
						if (treeHijo.getTiponodo() == Utilidades
								.getNodoPrincipal()) {
							cad2.append(",'Titulo','','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("','")
									.append(getImg_principal()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoArea()) {
							cad2.append(",'Titulo','','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("','").append(getImg_area())
									.append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCargo()) {
							if (user_cargo != null) {
								cad2.append(",'")
										.append(messages
												.getString("usuario_mail"))
										.append(":")
										.append(user_cargo.getMail_principal() != null ? user_cargo
												.getMail_principal().trim()
												: "");
								cad2.append("  ")
										.append(messages
												.getString("usuario_fechac"))
										.append(":")
										.append(user_cargo.getFecha_creado());
								cad2.append("','','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("','").append(getImg_cargo())
										.append("');");
							} else {

								cad2.append(",'Titulo','','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("','")
										.append(getImg_cargo()).append("');");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoProceso()) {

							cad2.append(",'Titulo','','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("','")
									.append(getImg_proceso()).append("');");
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoCarpeta()) {
							// buscamos si tiene documentos el nodo
							List<Doc_maestro> lista = findAllDoc_maestrosCarpetas(treeHijo);
							if (lista != null && !lista.isEmpty()) {

								cad2.append(",'','','")
										.append(confmessages
												.getString("img.carpetallena"))
										.append("');   ");
							} else {
								cad2.append(",'','','")
										.append(getImg_carpeta())
										.append("');   ");
							}
						} else if (treeHijo.getTiponodo() == Utilidades
								.getNodoDocumento()) {

							// icono= obtenIconoDoc(doc_detalle.getNameFile());
							//
							cad2.append(",'Titulo','','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("','").append(getImg_doc())
									.append("');");
						} else {
							cad2.append(",'Versiones ...','','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("','")
									.append(getImg_doc()).append("');");
						}
						cad2.append("\n\r");
						String nodos = null;
						if (treeHijo != null) {
							nodos = seguridadHijos(treeHijo.getNodo(),
									objArbol, user_logueado, seguridadTree,
									user_seguridad);
						}

						if ((nodos != null) && (nodos.length() > 0)) {
							cad2.append(nodos);
						}
					}
				}
			}
		}
		return cad2.toString();
	}

	// HEREDAMOS PERMISO

	public void asignamosPermiso(Tree registro, Usuario user_logueado,
			Tree treeNodoActual) {
		// __________________________________________________________________
		// damos seguridad hasta abajo del nuevo nodo que se
		// crean

		boolean swEliminar = false;
		// cargamos la operacion de la base de datos
		// Seguridad_Role_Lineal
		Seguridad_Role_Lineal seguridad_AuxUser_Lineal = new Seguridad_Role_Lineal();
		// EL TIPO DE NODO DE UN REGISTRO ES
		// DOCUMENTO,MOMENTANMEMANET COLOCAMOS NODO DOCUMENTO
		registro.setTiponodo(Utilidades.getNodoDocumento());
		List<Operaciones> operacionViewInBD = findAll_operacionesArbol(registro);
		for (Operaciones op : operacionViewInBD) {
			llenarSeguridadLineal(op, seguridad_AuxUser_Lineal);
		}
		// volvemos a dejar el nodo como tipo carpetra para que
		// no c
		// altere nada en el registro y logica del sistema
		registro.setTiponodo(Utilidades.getNodoCarpeta());
		// truco de convertirlo
		Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal(
				seguridad_AuxUser_Lineal);
		seguridad_User_Lineal.setUsuario(user_logueado);
		seguridad_User_Lineal.setTree(registro);
		try {
			heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
					seguridad_User_Lineal, swEliminar);
		} catch (EcologicaExcepciones e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ----------------role
		// seguridad---------------------------------------------

		// ClienteSeguridadRole clienteSeguridadRole = new ClienteSeguridadRole(
		// "vacio constructor");
		swEliminar = true;
		Seguridad_Role_Lineal seguridad_Role_Lineal = new Seguridad_Role_Lineal();

		// AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE

		List<Seguridad_Role_Lineal> lista = findAllSeguridad_Role_Lineal(treeNodoActual);
		for (Seguridad_Role_Lineal s_R_Lineal : lista) {
			Role role = s_R_Lineal.getRole();
			swEliminar = false;
			seguridad_Role_Lineal = new Seguridad_Role_Lineal();
			seguridad_Role_Lineal.setRole(role);
			seguridad_Role_Lineal.setTree(registro);
			// EN CASO YA ESTE GRABADA POR EL ROL EN GRUPOS,
			// BUSCAMOS SU SEGURIDA
			List<Seguridad_Role_Lineal> seguridad_Role_LinealList = findAllSeguridad_Role_Identificador(role);
			if (seguridad_Role_LinealList != null
					&& !seguridad_Role_LinealList.isEmpty()) {
				seguridad_Role_Lineal = seguridad_Role_LinealList.get(0);
				seguridad_Role_Lineal.setRole(role);
				seguridad_Role_Lineal.setTree(registro);
			}
			// para que sea totalmente nuevo y no traiga el
			// primary
			// key viejo
			Seguridad_Role_Lineal seguridad_Role_Lineal2 = new Seguridad_Role_Lineal(
					seguridad_Role_Lineal);
			create(seguridad_Role_Lineal2);
			try {
				heredarRolePermiso(seguridad_Role_Lineal, swEliminar,
						registro.getTiponodo());
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			givePermparaverToGroup(seguridad_Role_Lineal2);
			// FIN AHORA GUARDAMOS SEGURIDAD DEL ROLE EN TREE
		}

	}

	public void heredarOperacionTreeUsuarioPermisoSoloParaAbajo(
			Seguridad_User_Lineal seguridad_User_Lineal, boolean swElimina)
			throws EcologicaExcepciones {

		if (swElimina) {
			destroy(seguridad_User_Lineal);
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

	public void grabarOperacionesInConfiguracionSoloParaAbajo(
			Seguridad_User_Lineal seguridad_User_Lineal) {
		grabarOperaciones(seguridad_User_Lineal);
		// colocamos la nueva seguridad en session

	}

	public List<Tree> llegarHastaHijosTodos(Tree nodoPadre) {
		if (nodoPadre != null && nodoPadre.getNodo() != null) {
			List<Tree> nodosHijosLst = findAllHeredaTreeHijos(nodoPadre
					.getNodo());
			if (nodosHijosLst != null) {
				return nodosHijosLst;
			} else {
				return null;
			}
		}
		return null;
	}

	public void heredarOperacionTreeUsuarioPermiso(
			Seguridad_User_Lineal seguridad_User_Lineal, boolean swElimina)
			throws EcologicaExcepciones {

		if (swElimina) {
			destroy(seguridad_User_Lineal);
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

	// FIN HEREDAMOS PERMISO

	public Tree getTreePadreTipoHereda() {
		return treePadreTipoHereda;
	}

	public void setTreePadreTipoHereda(Tree treePadreTipoHereda) {
		this.treePadreTipoHereda = treePadreTipoHereda;
	}

	// FIN ARBOL-------------------

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// INICIO PARA FIRMAR FLOWS
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	public void firmarFlows(Flow_Participantes flow_Participante,
			Flow_ParticipantesAttachment flow_ParticipantesAttachment)
			throws EcologicaExcepciones {

		if (flow_ParticipantesAttachment != null) {
			create(flow_ParticipantesAttachment);
		}
		// aqui ya firmamois el flujo------------------------------
		edit(flow_Participante);
		// -----------------------------------------
		// EL CONTROL DE TIEMPO LO PARAMOS
		// ME DEVUELVE UN SOLO PARTICIPANTE ESTE FOR
		List<FlowControlByUsuarioBean> flowControlByUsuarioBean = find_allControlTimeByFlowParticipAndFlow(flow_Participante);
		for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
			controlTime.setSwStartHilo(false);
			edit(controlTime);
		}
		// si es secuencial, activamos el tiempo al siguiente
		// participante
		if (flow_Participante.getFlow().isSecuencial()) {
			List<Flow_Participantes> listaAuxParticipantes = findByFlowParticipantes(flow_Participante
					.getFlow());
			// Hago un ciclo hasta encontrar quien le toca firmar
			// para llevar su tiempoo
			boolean swSalte = false;
			for (Flow_Participantes flow_Part : listaAuxParticipantes) {
				if (flow_Part.getFirma().getCodigo() == Utilidades
						.getSinFirmar()) {
					// BUSCAMOS EL CONTROL DE TIEMPO DE ESE
					// PARTICIPANTE Y LO ACTIVAMOS
					flowControlByUsuarioBean = find_allControlTimeByFlowParticipAndFlow(flow_Part);
					for (FlowControlByUsuarioBean controlTime : flowControlByUsuarioBean) {
						// activamos sus hilo para empieze a contar
						// su tiempo
						controlTime.setSwStartHilo(true);
						edit(controlTime);
						break;// primer for , salimos con un solo
						// registro encontrado
					}
					swSalte = true;// segundo for

				}
				if (swSalte) {
					break;
				}

			}
		}

		// BUSCAMOS TODOS LOS PARTICIPANTES DEL FLOW PARA MANDARLES
		// UN MAIL
		if (flow_Participante.getFlow().isNotificacionMail()) {
			MyHiloEnvioMails myHiloEnvioMails = new MyHiloEnvioMails(
					mandarMailsUsuarios(flow_Participante.getFlow(),
							flow_Participante), find_allConfiguracion(),
					flow_Participante.getFlow());
			myHiloEnvioMails.start();
			//
		}

		// ahora chequeamos si terminamos o no el flujo con la
		// anterior firma
		// chequeamos si es condicionbal, secuencial y elk tipo de
		// firma, para ver si altera
		// las demas firmas del flow
		// AQUI SE ALTERA EL DOCUMENTO DETALLE
		checkFlowToContinuar(flow_Participante);
		// si no es una solicitud de impresion, alteramos el
		// documento
		Flow flow = null;
		if (flow_Participante != null) {
			flow = flow_Participante.getFlow();
			Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
			solicitudimpresion.setFlow(flow);
			solicitudimpresion = findSolicitudimpresionByFlow(solicitudimpresion);
			if (solicitudimpresion == null) {
				// AQUI SE ALTERA EL DOCUMENTO DETALLE
				if (flow_Participante.getFlow().getOrigen() == Utilidades
						.getOrigenDocumentoFlow()) {
					chequearForOldVersionVigente(flow_Participante);
				}
				// SOLO PARA SOLICITUD DE IMPRESION
			} else {

				// REVISAMOS SI EL FLUJO FUE APROBADO O REVISADO PARA ALETARRA
				// LA ORDEN DE IMPRESION
				flow = findFlow(flow);
				if (Utilidades.getAprobado() == flow.getEstado().getCodigo()) {
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getAprobado());
					doc_estado = findDocEstado(doc_estado);
					solicitudimpresion.setEstado(doc_estado);
					edit(solicitudimpresion);

					// si el flujo es de revision-------------------
				} else if (Utilidades.getRechazado() == flow.getEstado()
						.getCodigo()) {
					Doc_estado doc_estado = new Doc_estado();
					doc_estado.setCodigo(Utilidades.getRechazado());
					doc_estado = findDocEstado(doc_estado);
					solicitudimpresion.setEstado(doc_estado);
					edit(solicitudimpresion);

				}

			}
		}

	}

	public void checkFlowToContinuar(Flow_Participantes flow_Participante) {
		Flow flow = null;
		try {
			Doc_detalle doc_d = null;
			boolean swCondRechAprob = false;

			if (flow_Participante != null) {
				flow = flow_Participante.getFlow();
				// si es condicional, y es un flujo de aprobacion
				// y ha sido rechazado, se cancela los demas flujos pendientes
				if (flow.isCondicional()
						&& (Long.parseLong(flow.getTipoFlujo()) == Utilidades
								.getEnAprobacion())) {
					if (flow_Participante.getFirma().getCodigo() == Utilidades
							.getRechazado()) {

						FlowParalelo flowParalelo = new FlowParalelo();
						flowParalelo = flow.getFlowParalelo();
						List<Flow> subFlujos = (List<Flow>) findByFlowParaleloAllFlows(flowParalelo);
						boolean swFinFlow = true;
						for (Flow subFlow : subFlujos) {
							colocarFlujoRechazado(subFlow, flow_Participante);
						}

						// si no es una solicitud de impresion, alteramos el
						// documento
						Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
						solicitudimpresion.setFlow(flow);
						if (findSolicitudimpresionByFlow(solicitudimpresion) == null) {
							// AQUI ALTERAMOS EL DOCUMENTO----------------
							// esti es paraalterar el documento, siempre que se
							// a de origen documento flow
							if ((flow_Participante.getFlow().getOrigen() == Utilidades
									.getOrigenDocumentoFlow())) {
								Doc_estado rechazado = new Doc_estado();
								rechazado.setCodigo(Utilidades.getRechazado());
								rechazado = findDocEstado(rechazado);
								doc_d = flow.getFlowParalelo().getFlow()
										.getDoc_detalle();
								doc_d = findDocDetalle(doc_d);
								doc_d.setDoc_estado(rechazado);
								edit(doc_d);
							}

						}

					}
				}
			}
			// ________________________________________________________
			// SI ES FIN DEL FLUJO
			// si no fue condicional de aprbacion
			if (!swCondRechAprob && flowIsFinFlowParticipantes(flow)) {
				// BUSCAMOS SI HA SIDO RECHAZADO
				Doc_estado estadoFinDoc = new Doc_estado();
				Doc_estado estadoFinFlow = new Doc_estado();
				boolean swRechazarAllFlowParalelos = false;
				if (flowIsFinEdoRechazadoFlowParticipantes(flow)) {
					// firmamos el flujo como rechazado
					estadoFinDoc.setCodigo(Utilidades.getRechazado());
					estadoFinDoc = findDocEstado(estadoFinDoc);
					estadoFinFlow = estadoFinDoc;
					flow.setEstado(estadoFinFlow);
					swRechazarAllFlowParalelos = true;
				} else {
					if (Long.parseLong(flow.getTipoFlujo()) == Utilidades
							.getEnAprobacion()) {
						// firmamos el flujo como aprobado
						estadoFinFlow.setCodigo(Utilidades.getAprobado());
						estadoFinFlow = findDocEstado(estadoFinFlow);
						flow.setEstado(estadoFinFlow);

						estadoFinDoc.setCodigo(Utilidades.getVigente());
						estadoFinDoc = findDocEstado(estadoFinDoc);
						swFueAprobado = true;
					} else {
						// firmamos el flujo como revisado
						estadoFinFlow.setCodigo(Utilidades.getRevisado());
						estadoFinFlow = findDocEstado(estadoFinFlow);
						estadoFinDoc = estadoFinFlow;
						flow.setEstado(estadoFinFlow);
					}

				}
				// finalizamos la operacion colocando eñl edo a la nueva
				// version
				// y al flujo
				edit(flow);

				if (swRechazarAllFlowParalelos) {
					// SI CUALQUIERA DE LOS FLUJOS FUE RECHAZADO , ENTONCES
					// TODOS LOS FLKUJOS PARALELOS SONB
					// RECHAZADOS
					FlowParalelo flowParalelo = new FlowParalelo();
					flowParalelo = flow.getFlowParalelo();
					List<Flow> subFlujos = (List<Flow>) findByFlowParaleloAllFlows(flowParalelo);
					boolean swFinFlow = true;
					for (Flow subFlow : subFlujos) {
						colocarFlujoRechazado(subFlow, flow_Participante);
					}
				}

				// si no es una solicitud de impresion, alteramos el documento
				Solicitudimpresion solicitudimpresion = new Solicitudimpresion();
				solicitudimpresion.setFlow(flow);
				if (findSolicitudimpresionByFlow(solicitudimpresion) == null) {
					// AQUI ALTERAMOS EL DOCUMENTO DETALLE
					// esti es paraalterar el documento, siempre que se a de
					// origen documento flow
					if ((flow_Participante.getFlow().getOrigen() == Utilidades
							.getOrigenDocumentoFlow())) {
						if (finFlowParaleloForAlterarDocdetalle(flow)) {
							// REVISAMOS SUBIR VERSIONES DE UN DOCUMENTO SI EL
							// FLUJO
							// NACE
							// DE UN WORKFLOW
							if (flow.getFlow_Participantes() == null) {
								doc_d = flow.getFlowParalelo().getFlow()
										.getDoc_detalle();// flow.getDoc_detalle();
								doc_d = findDocDetalle(doc_d);
								// si es aprobado y es el ultmo flowparalelo,
								// aumentamos
								// la version y se coloca vigente
								if (flow_Participante.getFlow().getOrigen() == Utilidades
										.getOrigenDocumentoFlow()) {
									if (Utilidades.getVigente()
											- estadoFinDoc.getCodigo() == 0) {
										doc_d.setMinorVer("0");
										doc_d.setMayorVer(incInUnoString(doc_d
												.getMayorVer()));
									}
								}
								doc_d.setDoc_estado(estadoFinDoc);
								edit(doc_d);
							}
						}
					}

				}

				// ________________________________________________________

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String incInUnoString(String string) {
		// int i = Integer.valueOf(num);
		StringBuffer numNew = new StringBuffer("");
		boolean Sw = false;
		int valor = 0;
		StringBuffer retorna = new StringBuffer();
		if (string == null) {
			string = "0";
		}
		char[] arreglo = string.toCharArray();
		if (arreglo.length > 0) {
			char car = arreglo[arreglo.length - 1];
			if ((car != '9') && ((car != 'z') || (car != 'Z'))) {
				char oldcar = car;
				car = movementChar(car);
				arreglo[arreglo.length - 1] = car;
				for (int h = 0; h < arreglo.length; h++) {
					retorna.append(arreglo[h]);
				}

			}
			if (car == '9') {
				retorna.append(string).append("0");
			}

			if ((car == 'z') || (car == 'Z')) {
				if (car == 'Z') {
					retorna.append(string).append("A");
				} else {
					retorna.append(string).append("a");
				}

			}
		}

		return retorna.toString();
	}

	public char movementChar(char car) {
		int asciiValue = car;
		asciiValue++;
		return (char) asciiValue;
	}

	public void colocarFlujoRechazado(Flow flow,
			Flow_Participantes flow_Participante) {
		boolean swCondRechAprob = true;
		List lst = findByFlowParticipantes(flow);
		Iterator it = lst.iterator();
		int i = 0;

		while (it.hasNext()) {

			Flow_Participantes flujo_participantes = (Flow_Participantes) it
					.next();

			if (flujo_participantes.getFirma().getCodigo() == Utilidades
					.getSinFirmar()) {

				Doc_estado firma = new Doc_estado();
				firma.setCodigo(Utilidades.getRechazadoAutomatico());
				flujo_participantes.setFirma(firma);
				flujo_participantes.setComentario(flow_Participante
						.getComentario());
				flujo_participantes.setFecha_firma(flow_Participante
						.getFecha_firma());
				try {
					edit(flujo_participantes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// firmamos el flujo como rechazado
		Doc_estado rechazado = new Doc_estado();
		rechazado.setCodigo(Utilidades.getRechazado());
		rechazado = findDocEstado(rechazado);
		flow.setEstado(rechazado);
		try {
			edit(flow);
		} catch (EcologicaExcepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chequearForOldVersionVigente(
			Flow_Participantes flow_Participante) {

		configuraciones = find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {
			conf = configuraciones.get(0);
			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}
		if (swConfiguracionHayData) {
			carpeta_compartida = conf.getCarpetaCompartida().trim();
		} else {
			carpeta_compartida = confmessages.getString("carpeta_compartida");
		}

		if (flow_Participante != null) {
			Flow flow = flow_Participante.getFlow();

			// chequeamos si todos los flujos paralelos fueron firmados...

			// fin chequeamos si todos los flujos paralelos fueron firmados...
			if (finFlowParaleloForAlterarDocdetalle(flow)) {
				Doc_detalle old_version = chequearForOldVersionVigente(flow
						.getFlowParalelo().getFlow().getDoc_detalle());
				if (old_version != null) {
					old_version = find(old_version);
					try {
						if ((swFueAprobado)
								&& (Long.parseLong(flow.getTipoFlujo()) == Utilidades
										.getEnAprobacion())) {
							// colocamos el estado del documento vigente
							// anterior en oboleto
							Doc_estado colocar_obsoleto = new Doc_estado();
							colocar_obsoleto.setCodigo(com.util.Utilidades
									.getObsoleto());
							colocar_obsoleto = findDocEstado(colocar_obsoleto);
							old_version.setDoc_estado(colocar_obsoleto);
							old_version.setDoc_checkout(false);
							edit(old_version);

						}

					} catch (EcologicaExcepciones ex) {
						ex.toString();
						// Logger.getLogger("global").log(Level.SEVERE, null,
						// ex);
					}
				}

			}
		}

	}
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// FIN PARA FIRMAR FLOWS
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

}
