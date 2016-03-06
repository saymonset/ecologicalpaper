/*
 * ServicioDelegado.java
 *
 * Created on August 21, 2007, 8:27 AM  
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.delegados;

import java.io.File;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
import com.ecological.paper.delegado.DelegadoEJBLocal;
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
import com.software.eujovans.clientes.Factura;
import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import com.util.ValidaVencimiento;

/**
 * @author simon
 */
public class ServicioDelegado implements Serializable {
	// @EJB
	// private DelegadoEJBLocal delegado;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DelegadoEJBLocal delegado;

	private static DelegadoEJBLocal instance = null;

	
	private DelegadoEJBLocal devolverInstancia(){
		if (instance==null){
			Context ctx;
			try {
				ctx = new InitialContext();

				/*
				 * Enumeration ce = ctx.list(""); while (ce.hasMoreElements()) {
				 * NameClassPair c = (NameClassPair) ce.nextElement();
				 * System.out.print("Class name=" + c.getClassName());
				 * System.out.print("n.getName()=" + c.getName()); if
				 * ("ecologicalpaper_v5".equalsIgnoreCase(c.getName())) {
				 * System.out.println("LO ENCONTRO");
				 * 
				 * } }
				 */
				System.out
				.println("CREA PRINCIPAL CONEXION A BASE DE DATOS ECOLOGICAL");
				instance= (DelegadoEJBLocal) ctx
						.lookup("ecologicalpaper_v5/DelegadoEJBBean/local");
				if (instance== null) {
					throw new EcologicaExcepciones(
							"no se conecta con base de datos");

				}
				// System.out.println("CONTINUA INSTANCIA DE BASE DE DATOS");

			} catch (EcologicaExcepciones ex) {
				System.out.println("---0-----------");
				System.out.println("BASE DE DATOS->" + ex.getMessage());

			} catch (NamingException ex) {
				System.out.println("---1-----------");
				System.out
						.println("NamingException unitName = ecological_v5_PU BASE DE DATOS->"
								+ ex.getMessage());
			} catch (Exception ex) {
				System.out.println("---2-----------");
				System.out.println("BASE DE DATOS->" + ex.getMessage());

			}
	
		}else{
//			System.out.println("SE USA LA MISMA INSTANCIA BD");
		}
		
		return instance;
	}
	
	/** Creates a new instance of ServicioDocumentos */
	public ServicioDelegado() {
		delegado=devolverInstancia();
			}

	/*
	 * public static synchronized ServicioDelegado getInstance() { if (instance
	 * == null) {
	 * System.out.println("CREA UNA NNUEVA CONEXION A BASE DE DATOS"); instance
	 * = new ServicioDelegado();
	 * System.out.println("SATISFACTORIO .. FINISH..."); return instance; } else
	 * { // System.out.println("CONTINUA INSTANCIA DE BASE DE DATOS"); return
	 * instance;
	 * 
	 * } }
	 */

	public void createDetalle(Doc_detalle doc) throws EcologicaExcepciones {
		delegado.create(doc);
	}

	public void edit(Doc_detalle doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void editDetalle(Doc_detalle doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void destroy(Doc_detalle doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public void destroyDetalle(Doc_detalle doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public com.ecological.paper.documentos.Doc_detalle findDetalle(
			Doc_detalle doc) {
		return delegado.findDocDetalle(doc);
	}

	public com.ecological.paper.documentos.Doc_detalle findDocDetalle(
			Doc_detalle doc) {
		return delegado.findDocDetalle(doc);
	}

	public java.util.List findAllDoc_Detalles() {
		return delegado.findAllDoc_Detalles();
	}

	public Doc_detalle findUltimolDoc_Detalles(Doc_maestro doc_maestro) {
		return delegado.findUltimolDoc_Detalles(doc_maestro);
	}

	public Doc_detalle findUltimolDoc_Detalles(Doc_maestro doc_maestro,
			Doc_estado doc_estado) {
		return delegado.findUltimolDoc_Detalles(doc_maestro, doc_estado);
	}

	public java.util.List findAllDoc_Detalles(Doc_maestro doc_maestro) {
		return delegado.findAllDoc_Detalles(doc_maestro);
	}

	public List<Doc_maestro> consecutivoseRepite(Doc_maestro doc_maestro) {
		return delegado.consecutivoseRepite(doc_maestro);
	}

	public Doc_maestro nombreDocseRepite(Doc_maestro doc_maestro) {
		return delegado.nombreDocseRepite(doc_maestro);
	}

	public List<Doc_detalle> encontrarDetalles(long codigo, String parametro) {
		return delegado.encontrarDetalles(codigo, parametro);
	}

	public List queryFecha_Doc_maestro(String str, Date fechaparametro) {
		return delegado.queryFecha_Doc_maestro(str, fechaparametro);
	}

	public List<Tree> hayRegistros(Tree t, Tree nuevo) {
		return delegado.hayRegistros(t, nuevo);
	}

	public List<Doc_detalle> findDetallesListarDocumewntos(Doc_maestro doc,
			String doc_estadoId, String usuarioId, String parametro) {
		return delegado.findDetallesListarDocumewntos(doc, doc_estadoId,
				usuarioId, parametro);
	}

	public void createMaestro(Doc_maestro doc) throws EcologicaExcepciones {
		doc.setEmpresa(doc.getUsuario_creador().getEmpresa());
		delegado.create(doc);
	}

	public void editMaestro(Doc_maestro doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void destroyMaestro(Doc_maestro doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public com.ecological.paper.documentos.Doc_maestro findMaestro(
			Doc_maestro doc) {
		return delegado.findDocMaestro(doc);
	}

	public java.util.List findAllDoc_maestros() {
		return delegado.findAllDoc_maestros();
	}

	public java.util.List findAllDoc_maestros(Tree tree) {
		return delegado.findAllDoc_maestros(tree);
	}

	public List<Tree> find(Tree tree, Doc_maestro doc) {
		return delegado.find(tree, doc);
	}

	public List findAllDoc_maestrosCarpetas(Tree tree) {
		return delegado.findAllDoc_maestrosCarpetas(tree);
	}

	public List findAllDoc_maestros(String str, char c) {
		return delegado.findAllDoc_maestros(str, c);
	}

	public List findAllDoc_maestros(Usuario usuario, Doc_tipo doc_tipo,
			Doc_estado doc_estado) {
		return delegado.findAllDoc_maestros(usuario, doc_tipo, doc_estado);
	}

	public String queryDoc_maestrosPublicos() {
		return delegado.queryDoc_maestrosPublicos();
	}

	public String queryDetallesVigentePublico(Doc_maestro doc) {
		return delegado.queryDetallesVigentePublico(doc);
	}

	public List<Doc_maestro> findDoc_Maestr_Doc_Tipo(Doc_tipo doc_tipo) {
		return delegado.findDoc_Maestr_Doc_Tipo(doc_tipo);
	}

	public void createDocEstado(Doc_estado doc) throws EcologicaExcepciones {
		delegado.create(doc);
	}

	public void editDocEstado(Doc_estado doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void destroyDocEstado(Doc_estado doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public com.ecological.paper.documentos.Doc_estado findDocEstado(
			Doc_estado doc) {
		return delegado.findDocEstado(doc);
	}

	public java.util.List findAllDoc_estados() {
		return delegado.findAllDoc_estados();
	}

	public List findAllDoc_estados(String strBuscar) {
		return delegado.findAllDoc_estados(strBuscar);
	}

	public void createTipoDoc(Doc_tipo doc) throws EcologicaExcepciones {
		delegado.create(doc);
	}

	public void editTipoDoc(Doc_tipo doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void destroyTipoDoc(Doc_tipo doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public com.ecological.paper.documentos.Doc_tipo findTipoDoc(Doc_tipo doc) {
		return delegado.findDocTipo(doc);
	}

	public com.ecological.paper.documentos.Doc_tipo findDocTipo(Doc_tipo doc) {
		return delegado.findDocTipo(doc);
	}

	public Doc_tipo findDocTipo(Doc_tipo pk, Usuario usuario) {
		return delegado.findDocTipo(pk, usuario);
	}

	public java.util.List findAllDoc_tipos(Usuario usuario) {
		return delegado.findAllDoc_tipos(usuario);
	}

	public java.util.List findAllDoc_tipos(Usuario usuario, String buscar) {
		return delegado.findAllDoc_tipos(usuario, buscar);
	}

	public void createDocRel(Doc_relacionados doc) throws EcologicaExcepciones {
		delegado.create(doc);
	}

	public void editDocRel(Doc_relacionados doc) throws EcologicaExcepciones {
		delegado.edit(doc);
	}

	public void destroy(Doc_relacionados doc) throws EcologicaExcepciones {
		delegado.destroy(doc);
	}

	public com.ecological.paper.documentos.Doc_relacionados findRelDoc(
			Doc_relacionados doc) {
		return delegado.findDocRel(doc);
	}

	public java.util.List findAll_Doc_Relacionados(Doc_maestro doc) {
		return delegado.findAll_Doc_Relacionados(doc);
	}

	public java.util.List findAll_Doc_Relacionados(Doc_maestro doc,
			String buscar) {
		return delegado.findAll_Doc_Relacionados(doc, buscar);
	}

	public java.util.List findAllDoc_relacionados() {
		return delegado.findAllDoc_relacionados();
	}

	public List findAllTipoDeFirma() {
		return delegado.findAllTipoDeFirma();
	}

	public java.util.List findAllTipoDeFirma(Flow flow) {
		return delegado.findAllTipoDeFirma(flow);

	}

	public java.util.List findDocEstadoName(Doc_estado estado) {
		return delegado.findDocEstadoName(estado);
	}

	public void findFlowBorrarObsoleto(Usuario duenio) {
		delegado.findFlowBorrarObsoleto(duenio);
	}

	public Boolean flowIsFinFlowParticipantes(Flow flow) {
		return delegado.flowIsFinFlowParticipantes(flow);
	}

	public Boolean flowIsFinEdoRechazadoFlowParticipantes(Flow flow) {
		return delegado.flowIsFinEdoRechazadoFlowParticipantes(flow);
	}

	public Doc_detalle chequearForOldVersionVigente(Doc_detalle doc_detalle) {
		return delegado.chequearForOldVersionVigente(doc_detalle);
	}

	public List findHistoricooDetalleInFlow(Flow flow) {
		return delegado.findHistoricooDetalleInFlow(flow);
	}

	public List findAll_FlowsHistorico(Doc_maestro doc) {
		return delegado.findAll_FlowsHistorico(doc);
	}

	public List findAll_FlowsHistoricoDoc_detalle(Doc_detalle doc) {
		return delegado.findAll_FlowsHistoricoDoc_detalle(doc);
	}

	public void create(FlowsHistorico flowHist) {
		delegado.create(flowHist);
	}

	public List findAll_FlowsHistorico() {
		return delegado.findAll_FlowsHistorico();
	}

	public void edit(FlowsHistorico flowhist) throws EcologicaExcepciones {
		delegado.edit(flowhist);
	}

	public void destroy(FlowsHistorico flowhist) throws EcologicaExcepciones {
		delegado.destroy(flowhist);
	}

	public List findAllFlowDelParticipanteFirmados(Usuario participante) {
		return delegado.findAllFlowDelParticipanteFirmados(participante);
	}

	public List findDetalleTreeHistoricoPersonalInFlow(Doc_detalle doc) {
		return delegado.findDetalleTreeHistoricoPersonalInFlow(doc);
	}

	public Flow_Participantes flowFlowOfParticipantes(Flow flow, Usuario usuario) {
		return delegado.flowFlowOfParticipantes(flow, usuario);
	}

	// _________________________________________________________________________________________________________________________//
	// FLUJOS
	// __________________________________________________________________________________________________________________________//
	public void create(Flow flow) throws EcologicaExcepciones {
		delegado.create(flow);
	}

	public void edit(Flow flow) throws EcologicaExcepciones {
		delegado.edit(flow);
	}

	public void destroy(Flow flow) throws EcologicaExcepciones {
		delegado.destroy(flow);
	}

	public com.ecological.paper.flows.Flow findFlow(Flow pk) {
		return delegado.findFlow(pk);
	}

	//
	public java.util.List findAllFlow() {
		return delegado.findAllFlow();
	}

	public List findByFlow(Doc_detalle doc) {
		return delegado.findByFlow(doc);
	}

	public List findDocumentoDetalleInFlow(Doc_detalle doc) {
		return delegado.findDocumentoDetalleInFlow(doc);
	}

	public Doc_detalle findDocDetalle_TipoEdo(Doc_detalle doc_detalle) {
		return delegado.findDocDetalle_TipoEdo(doc_detalle);
	}

	public void create(Flow_Participantes flow) throws EcologicaExcepciones {
		delegado.create(flow);
	}

	public void edit(Flow_Participantes flow) throws EcologicaExcepciones {
		delegado.edit(flow);
	}

	public void destroy(Flow_Participantes flow) throws EcologicaExcepciones {
		delegado.destroy(flow);
	}

	public com.ecological.paper.flows.Flow_Participantes findFlow(
			Flow_Participantes pk) {
		return delegado.findFlow(pk);
	}

	public java.util.List findAllFlow_Participantes() {
		return delegado.findAllFlow_Participantes();
	}

	public java.util.List findByFlowParticipantes(Flow flow) {
		return delegado.findByFlowParticipantes(flow);
	}

	public List findByDeleteFlowParticipantes(Flow flow) {
		return delegado.findByDeleteFlowParticipantes(flow);
	}

	public List findAllFlow_delete_role_ByFlow(Flow flow) {
		return delegado.findAllFlow_delete_role_ByFlow(flow);
	}

	public java.util.List findByFlowParticipantes(Doc_detalle doc) {
		return delegado.findByFlowParticipantes(doc);
	}

	public List findByFlowParticipantesRoleIsNull(Doc_detalle doc,
			boolean swRoleIsNull) {
		return delegado.findByFlowParticipantesRoleIsNull(doc, swRoleIsNull);
	}

	public List findByFlowBad(Doc_detalle doc) {
		return delegado.findByFlowBad(doc);

	}

	public void create(Flow_referencia_role flow) throws EcologicaExcepciones {
		delegado.create(flow);
	}

	public void edit(Flow_referencia_role flow) throws EcologicaExcepciones {
		delegado.edit(flow);
	}

	public void destroy(Flow_referencia_role flow) throws EcologicaExcepciones {
		delegado.destroy(flow);
	}

	public com.ecological.paper.flows.Flow_referencia_role findFlow(
			Flow_referencia_role pk) {
		return delegado.findFlow(pk);
	}

	public java.util.List findAllFlow_referencia_role() {
		return delegado.findAllFlow_referencia_role();

	}

	public List findAllFlow_referencia_role_ByFlow(Flow flow) {
		return delegado.findAllFlow_referencia_role_ByFlow(flow);
	}

	public java.util.List findByFlow_referencia_role(Flow flow) {
		return delegado.findByFlow_referencia_role(flow);
	}
	
	public List findByFlowOnlyNotificacionRole(Flow flow){
		return delegado.findByFlowOnlyNotificacionRole(flow);
	}

	public List findAllFlowParticipantesPendientes(Usuario participante) {
		return delegado.findAllFlowParticipantesPendientes(participante);

	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// FLOWS___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________OPERACIONES___________________________________________________________________________________________________________//
	public void create(Operaciones role) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado {
		delegado.create(role);

	}

	public void edit(Operaciones role) throws EcologicaExcepciones {
		delegado.edit(role);
	}

	public void destroy(Operaciones role) throws EcologicaExcepciones {
		delegado.destroy(role);
	}

	public Operaciones getOperacion(Operaciones operaciones)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		return delegado.getOperacion(operaciones);
	}

	public List findAll_Historico() {
		return delegado.findAll_Historico();
	}

	public Operaciones Encontrar_operaciones(String id) {
		try {
			if (id != null && id.length() > 0) {
				Operaciones prof = delegado.Encontrar_operaciones(id);
				return prof;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List findAll_operaciones() {
		return delegado.findAll_operaciones();
	}

	public List buscarTodosLasOperacionesRol(
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		return delegado.buscarTodosLasOperacionesRol(seguridad_Role_Lineal);
	}

	public java.util.List findOperacionName(Operaciones op) {
		return delegado.findOperacionName(op);
	}

	public Operaciones findOperacion(Operaciones op) {
		return delegado.findOperacion(op);
	}

	public List findAll_operacionesMenu() {
		return delegado.findAll_operacionesMenu();

	}

	public List<Operaciones> findAll_operacionesArbol(Tree tree) {
		return delegado.findAll_operacionesArbol(tree);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// OPERACIONES___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________PROFESIONES___________________________________________________________________________________________________________//
	public void addProfesion(Profesion profesion) {
		try {
			delegado.create(profesion);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(Profesion profesion) {
		try {
			delegado.edit(profesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// void destroy(Profesion profesion);
	public void destroy(Profesion profesion) {
		try {
			delegado.destroy(profesion);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Profesion Encontrar_Profesion(String id) {
		try {
			if (id != null && id.length() > 0) {
				Profesion prof = delegado.Encontrar_Profesion(id);
				return prof;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// find(Object pk);
	public Profesion find(Long pk) {
		Profesion prof = null;
		try {
			prof = delegado.find(pk);

		} catch (Exception e) {
			prof = null;
			e.printStackTrace();
		}
		return prof;
	}

	// java.util.List findAll();
	public List findAll_Profesion(Usuario usuario) {
		List list = null;
		try {
			list = delegado.findAll_Profesion(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List findAll_Profesion(Usuario usuario, String search) {
		return delegado.findAll_Profesion(usuario, search);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// PROFESIONES___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________ROLE___________________________________________________________________________________________________________//
	public void create(Role role) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado {
		delegado.create(role);

	}

	public void edit(Role role) throws EcologicaExcepciones {
		delegado.edit(role);
	}

	public void destroy(Role role) throws EcologicaExcepciones {
		delegado.destroy(role);
	}

	public Role findRole(Role role) throws RoleMultiples, RoleNoEncontrado {
		return delegado.findRole(role);
	}

	public Role getRole(Role role) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado {
		return delegado.getRole(role);
	}

	public List findAll_Roles(Usuario usuario) {
		return delegado.findAll_Roles(usuario);
	}

	public List findAll_Roles(Usuario usuario, String search) {
		return delegado.findAll_Roles(usuario, search);
	}

	public List findRoles_Usuario(Usuario usuario) {
		return delegado.findRoles_Usuario(usuario);
	}

	public List findRoles_Usuario(Role role) {
		return delegado.findRoles_Usuario(role);
	}

	public Role Encontrar_roles(String id) {
		return delegado.Encontrar_roles(id);
	}

	public void create(Role_Operaciones operaciones) {
		delegado.create(operaciones);
	}

	public void destroy(Role_Operaciones operaciones)
			throws EcologicaExcepciones {
		delegado.destroy(operaciones);
	}

	public void destroy_Rol(Role rol) throws EcologicaExcepciones {
		delegado.destroy_Rol(rol);
	}

	public void create(Roles_Usuarios operaciones) {
		delegado.create(operaciones);
	}

	public void destroy(Roles_Usuarios operaciones) throws EcologicaExcepciones {
		delegado.destroy(operaciones);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// ROLE___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________SEGURIDAD___________________________________________________________________________________________________________//
	public void create(Seguridad_User seguridad_User)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		delegado.create(seguridad_User);
	}

	public List buscarTodosLasOperacionesTreeUsuario(Tree tree, Usuario usuario) {
		return delegado.buscarTodosLasOperacionesTreeUsuario(tree, usuario);
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTree(Tree tree) {
		return delegado.buscarTodosLosRolesTree(tree);
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTree(Tree tree,
			Role role) {
		return delegado.buscarTodosLosRolesTree(tree, role);
	}

	public List<Seguridad_Role_Lineal> buscarTodosLosRolesTreeCompleto(Role role) {
		return delegado.buscarTodosLosRolesTreeCompleto(role);
	}

	public void create(Seguridad_Role seguridadRole)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado {
		delegado.create(seguridadRole);
	}

	public void destroy(Seguridad_Role_Lineal seguridadRole) {
		try {
			delegado.destroy(seguridadRole);
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
		}
	}

	public com.ecological.paper.permisologia.seguridad.Seguridad_User findTreeOperUser(
			Seguridad_User seg) {
		return delegado.findTreeOperUser(seg);
	}

	public List llenarUsuariosInOperacionesVisibles(Tree tree) {
		return delegado.llenarUsuariosInOperacionesVisibles(tree);
	}

	public List<Seguridad_Role_Lineal> findSeguridad_Role_RoleAndTree(
			Seguridad_Role_Lineal seguridadRole) {
		List<Seguridad_Role_Lineal> lst = null;
		try {
			return delegado.findSeguridad_Role_RoleAndTree(seguridadRole);
		} catch (EcologicaExcepciones ex) {
			ex.printStackTrace();
		}
		return lst;
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// SEGURIDAD___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________TREE___________________________________________________________________________________________________________//
	/** Creates a new instance of ServicioTree */
	public String construirArbol() {
		String str = "";
		try {
			str = delegado.construirArbol();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String construirArbol(boolean swSession) {
		String str = "";
		try {
			str = delegado.construirArbol(swSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String docTaskPendiente(boolean swSession) {
		String str = "";
		try {
			str = delegado.docTaskPendiente(swSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public void crearNuevoTree(Tree tree, Usuario usuario) {
		try {
			delegado.crearNuevoTree(tree, usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearTreeEmpresa(Tree tree) {
		delegado.crearTreeEmpresa(tree);
	}

	public List finTreeByName(Tree tree) {
		return delegado.finTreeByName(tree);
	}

	public List finTreeByNameAndTipo(Tree tree) {
		return delegado.finTreeByNameAndTipo(tree);
	}

	public java.util.List findAllTreeHijos(long nodoPadre) {
		return delegado.findAllTreeHijos(nodoPadre);
	}

	public List findAllTreeHijos(long nodo, String tipoNodo) {
		return delegado.findAllTreeHijos(nodo, tipoNodo);
	}

	public List findAllHeredaTreeHijos(long nodo) {
		return delegado.findAllHeredaTreeHijos(nodo);
	}

	public java.util.List findAllTreePadresAbuelos(Tree tree) {
		return delegado.findAllTreePadresAbuelos(tree);
	}

	public List findAll_DeQueTipo_Tree(Long queTipo) {
		return delegado.findAll_DeQueTipo_Tree(queTipo);
	}

	public List findAll_DeQueTipo_Tree(Long queTipo, String search) {
		return delegado.findAll_DeQueTipo_Tree(queTipo, search);
	}

	public List findAllEmpresas() {
		return delegado.findAllEmpresas();
	}

	public void edit(Tree tree) {
		delegado.edit(tree);
	}

	public List<Tree> findTreeUsuarioNull(Tree tree) {
		return delegado.findTreeUsuarioNull(tree);
	}

	public void destroy(Tree tree) {
		delegado.destroy(tree);
	}

	public void actualizaNodoPadreNull() {
		try {
			delegado.actualizaNodoPadreNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tree obtenerNodo(Long idRoot) {
		Tree tree = null;
		try {
			tree = delegado.obtenerNodo(idRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}

	public String getImg_principal() {
		try {
			return delegado.getImg_principal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_area() {
		try {
			return delegado.getImg_area();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_cargo() {
		try {
			return delegado.getImg_cargo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_proceso() {
		try {
			return delegado.getImg_proceso();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_carpeta() {
		try {
			return delegado.getImg_carpeta();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_doc() {
		try {
			return delegado.getImg_doc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_raiz() {
		try {
			return delegado.getImg_raiz();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_logo() {
		try {
			return delegado.getImg_logo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getImg_userLogueado() {
		return delegado.getImg_userLogueado();
	}

	public List findAllCargos() {
		List lista = null;
		try {
			lista = delegado.findAllCargos();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findAllCargos(Tree area) {
		List lista = null;
		try {
			lista = delegado.findAllCargos(area);
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findAllAreas() {
		List lista = null;
		try {
			lista = delegado.findAllAreas();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findAllAreas(Tree empresa) {
		return delegado.findAllAreas(empresa);
	}

	public java.util.List findAllTreePadres() {
		return delegado.findAllTreePadres();
	}

	public java.lang.String getNodosHijos(long nodoPadre, char objArbol) {
		return delegado.getNodosHijos(nodoPadre, objArbol);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// TREE___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________USUARIO___________________________________________________________________________________________________________//
	public void create(Usuario usuario) throws EcologicaExcepciones,
			NoSuchAlgorithmException {

		delegado.create(usuario);
	}

	public List findAll_Usuario(Usuario usuario) {
		List usu = null;
		try {
			usu = delegado.findAll_Usuario(usuario);
			if (usu == null) {
				new Exception("unitName = ecological_v5_PU");
			}

		} catch (Exception e) {
			System.out.println("BASE DE DATOS->" + e.getMessage());

		}
		return usu;
	}

	public List findAll(Usuario usuario, String buscar) {
		return delegado.findAll(usuario, buscar);
	}

	public void create(Usuario usuario, Date fecha_conecto, String maquina,
			Usuario usuario_loBorro, Date Fecha_baja)
			throws EcologicaExcepciones, NoSuchAlgorithmException {
		Blob blob = null;
		// usuario.setFoto(blob);

		delegado.create(usuario);
		Hist_usuarios hist_usuario = new Hist_usuarios(usuario, fecha_conecto,
				maquina, usuario_loBorro, Fecha_baja);
		hist_usuario.setStatus(true);
		createHist(hist_usuario);
	}

	public Usuario getUsuario(Usuario usuario) throws EcologicaExcepciones {
		return delegado.getUsuario(usuario);
	}

	public void edit(Usuario usuario) throws EcologicaExcepciones {
		delegado.edit(usuario);
	}

	public void destroy(Usuario usuario) throws EcologicaExcepciones {
		delegado.destroy(usuario);
	}

	public void create(Menus_Usuarios menus_Usuarios) {
		delegado.create(menus_Usuarios);
	}

	public void destroy_Menu(Usuario usuario) {
		try {
			delegado.destroy_Menu(usuario);
		} catch (EcologicaExcepciones ex) {
			System.out.println("ex.toString()=" + ex.toString());
			ex.printStackTrace();
		}
	}

	public void createHist(Hist_usuarios hist_usuarios) {
		delegado.create(hist_usuarios);
	}

	public Usuario find_Usuario(Long pk) {
		return delegado.find_Usuario(pk);
	}

	public Usuario findUsuarioLogin(Usuario usuario)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		return delegado.findUsuarioLogin(usuario);
	}
	public Usuario findUsuarioByMail(Usuario usuario)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		return delegado.findUsuarioByMail(usuario);
	}
	public Usuario Encontrar_Usuario(String id) {
		return delegado.Encontrar_Usuario(id);

	}

	public List findExisteCargoInUsuario(Usuario usuario) {
		return delegado.findExisteCargoInUsuario(usuario);
	}

	public java.lang.String findLoginOrMailExista(Usuario usuario) {
		return delegado.findLoginOrMailExista(usuario);
	}

	public java.util.List findAll(Usuario usuario, String str, char c) {
		return delegado.findAll(usuario, str, c);
	}

	public List queryExecute(String query) {
		return delegado.queryExecute(query);
	}

	public List queryExecute(String str, Date fechaparametro) {
		return delegado.queryExecute(str, fechaparametro);
	}

	public List<Usuario> findUsuario_Profesion(Profesion profesion) {
		return delegado.findUsuario_Profesion(profesion);
	}

	public void destroyAlldRoles_UsuarioByRole(Role role) {
		delegado.destroyAlldRoles_UsuarioByRole(role);
	}

	public void destroy_MenuByRole(Role role) throws EcologicaExcepciones {
		delegado.destroy_MenuByRole(role);
	}

	public void destroyAllFlow_referencia_roleByRole(Role role) {
		delegado.destroyAllFlow_referencia_roleByRole(role);
	}

	public void deletellFlow_ParticipantesByRole(Role role) {
		delegado.deletellFlow_ParticipantesByRole(role);
	}

	public void deleteSeguridadIndividual2(Usuario usuario, Usuario remplazo) {
		delegado.deleteSeguridadIndividual2(usuario, remplazo);
	}

	public void sustituirFlowParticipantesOldByOtro(Usuario viejo, Usuario nuevo) {
		delegado.sustituirFlowParticipantesOldByOtro(viejo, nuevo);
	}

	public void destroy_Menu(Usuario usuario, Usuario remplazo)
			throws EcologicaExcepciones {
		delegado.destroy_Menu(usuario, remplazo);
	}

	public void deleteTreenewDuenio(Usuario old, Usuario nuevo) {
		delegado.deleteTreenewDuenio(old, nuevo);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________FIN
	// USUARIO___________________________________________________________________________________________________________//
	// _________________________________________________________________________________________________________________________//
	// ______________HISTORICO___________________________________________________________________________________________________________//
	public void create(Historico hist) {
		delegado.create(hist);
	}

	public void edit(Historico hist) {
		delegado.edit(hist);
	}

	public void destroy(Historico hist) {
		delegado.destroy(hist);
	}

	public Historico findHistorico(Historico hist) {
		return delegado.findHistorico(hist);
	}

	public List<Historico> findAllHistorico(Historico hist) {
		return delegado.findAllHistorico(hist);
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo) {
		return delegado.findAllHistoricoUsuarios(usuario, str, inactivo);
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str, char c,
			boolean inactivo) {
		return delegado.findAllHistoricoUsuarios(usuario, str, c, inactivo);
	}

	public List<Historico> findAllHistorico(Historico hist, Usuario usuario) {
		return delegado.findAllHistorico(hist, usuario);
	}

	public List findAllHeredaTreeHijosDeleted(long nodo, boolean inactivo) {
		return delegado.findAllHeredaTreeHijosDeleted(nodo, inactivo);
	}

	public List<Historico> findAllHistoricoTree(Historico hist, Tree tree) {
		return delegado.findAllHistoricoTree(hist, tree);
	}

	public List<Historico> findAllHistoricoTreeEliminados(Tree tree) {
		return delegado.findAllHistoricoTreeEliminados(tree);
	}

	public List<Historico> findAllHistoricoTreeMoverNodo(Tree tree) {
		return delegado.findAllHistoricoTreeMoverNodo(tree);
	}

	public Doc_detalle findDetalleDocumewntoBusqueda(Doc_maestro doc,
			String doc_estadoId, String usuarioId, String parametro) {
		return delegado.findDetalleDocumewntoBusqueda(doc, doc_estadoId,
				usuarioId, parametro);
	}

	public List findAllHeredaTreeHijos(long nodo, long tiponodo) {
		return delegado.findAllHeredaTreeHijos(nodo, tiponodo);
	}

	public List findAllTreeHijos(long nodo, Usuario usuario) {
		return delegado.findAllTreeHijos(nodo, usuario);
	}

	public List<Seguridad> soloMenu(Usuario usuario) {
		return delegado.soloMenu(usuario);
	}

	public void create(Seguridad_Role_Lineal hist) {
		delegado.create(hist);
	}

	public void edit(Seguridad_Role_Lineal hist) {
		delegado.edit(hist);
	}

	public Seguridad_Role_Lineal findSeguridad_Role_Lineal(
			Seguridad_Role_Lineal hist) {
		return delegado.findSeguridad_Role_Lineal(hist);
	}

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(Role role) {
		return delegado.findAllSeguridad_Role_Lineal(role);
	}

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(Tree tree) {
		return delegado.findAllSeguridad_Role_Lineal(tree);
	}

	public void llenarSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		delegado.llenarSeguridadLineal(seg_user, seguridad_Role_Lineal);
	}

	public void create(Seguridad_User_Lineal hist) {
		delegado.create(hist);
	}

	public void edit(Seguridad_User_Lineal hist) {
		delegado.edit(hist);
	}

	public void destroy(Seguridad_User_Lineal hist) {
		delegado.destroy(hist);
	}

	public Seguridad_User_Lineal findSeguridad_User_Lineal(
			Seguridad_User_Lineal hist) {
		return delegado.findSeguridad_User_Lineal(hist);
	}

	public List<Seguridad_User_Lineal> findAllSeguridad_User_Lineal(Tree tree) {
		return delegado.findAllSeguridad_User_Lineal(tree);
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadIndividual(Usuario usuario) {

		return delegado.buscarSeguridadIndividual(usuario);
	}

	public List findAllHeredaTreeHijosMenosDocumentos(long nodo) {
		return delegado.findAllHeredaTreeHijosMenosDocumentos(nodo);
	}

	public List<Seguridad_User_Lineal> findAllSeguridad_User_Lineal(Tree tree,
			Usuario usuario) {
		return delegado.findAllSeguridad_User_Lineal(tree, usuario);
	}

	public List findAllHeredaTreeHijosSoloPrincipal(long nodo) {
		return delegado.findAllHeredaTreeHijosSoloPrincipal(nodo);
	}

	public boolean existeOperacionSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		return delegado.existeOperacionSeguridadLineal(seg_user,
				seguridad_Role_Lineal);
	}

	public List<Seguridad_Role_Lineal> findAllSeguridad_Role_Identificador(
			Role role) {
		return delegado.findAllSeguridad_Role_Identificador(role);
	}

	public void destroy_RolEditando(Role rol) throws EcologicaExcepciones {
		delegado.destroy_RolEditando(rol);
	}

	public List<Seguridad_Role_Lineal> buscarSeguridadIndividual(
			Usuario usuario, Tree tree) {
		return delegado.buscarSeguridadIndividual(usuario, tree);
	}

	public List<Seguridad_Role_Lineal> buscarTodosLasOperacionesRolSoloMenu(
			Seguridad_Role_Lineal seguridad_Role_Lineal) {
		return delegado
				.buscarTodosLasOperacionesRolSoloMenu(seguridad_Role_Lineal);
	}

	public List<Roles_Usuarios> findRoles_AND_Usuario(Role role) {
		return delegado.findRoles_AND_Usuario(role);
	}

	public List findExisteNombreRaiz(Tree raiz) {
		return delegado.findExisteNombreRaiz(raiz);
	}

	public List findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(long nodo) {
		return delegado.findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(nodo);
	}

	public List queryExecute(String query, int mayor, int menor) {
		return delegado.queryExecute(query, mayor, menor);
	}

	public List queryExecute(String query, int mayor, int menor,
			List cuantosLista) {
		return delegado.queryExecute(query, mayor, menor, cuantosLista);
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str, char c,
			boolean inactivo, int maximo, int minimo) {
		return delegado.findAllHistoricoUsuarios(usuario, str, c, inactivo,
				maximo, minimo);
	}

	public List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo, int maximo, int minimo) {
		return delegado.findAllHistoricoUsuarios(usuario, str, inactivo,
				maximo, minimo);
	}

	public List findAllDoc_maestrosCarpetas(Tree tree, Doc_detalle doc) {
		return delegado.findAllDoc_maestrosCarpetas(tree, doc);
	}

	public List<Flow_Participantes> findAllFlowParticipantesByFlow(Flow flow) {
		return delegado.findAllFlowParticipantesByFlow(flow);
	}

	public Doc_detalle findDetalleDocumewntoPublico(Doc_maestro doc) {
		return delegado.findDetalleDocumewntoPublico(doc);
	}

	public List<Doc_maestro> consecutivoseRepite(Doc_maestro doc_maestro,
			Tree tree) {
		return delegado.consecutivoseRepite(doc_maestro, tree);
	}

	public Usuario findUsuarioByCargo(Tree cargo) {
		return delegado.findUsuarioByCargo(cargo);
	}

	public void create(Doc_dataPostgres datapostgres) {
		delegado.create(datapostgres);
	}

	public void edit(Doc_dataPostgres datapostgres) {
		delegado.edit(datapostgres);
	}

	public void destroy(Doc_dataPostgres datapostgres) {
		delegado.destroy(datapostgres);
	}

	public com.ecological.paper.documentos.Doc_dataPostgres findDoc_dataPostgres(
			Doc_dataPostgres datapostgres) {
		return delegado.findDoc_dataPostgres(datapostgres);
	}

	public com.ecological.paper.documentos.Doc_dataPostgres findDoc_dataPostgres(
			Doc_detalle doc_detalle) {
		return delegado.findDoc_dataPostgres(doc_detalle);
	}

	public List findAllDoc_maestrosNoRelacionados(Tree tree, Doc_detalle doc) {
		return delegado.findAllDoc_maestrosNoRelacionados(tree, doc);
	}

	public List findByFlowDocDetalle(Doc_detalle doc) {
		return delegado.findByFlowDocDetalle(doc);
	}

	public Doc_detalle findDoc_detalleVigente(Doc_detalle doc_detalle) {
		return delegado.findDoc_detalleVigente(doc_detalle);
	}

	public void create(Doc_consecutivo doc_consecutivo) {
		delegado.create(doc_consecutivo);
	}

	public List findAllDoc_estadosInSearch() {
		return delegado.findAllDoc_estadosInSearch();
	}

	public void destroy(TablaAuxiliar tablaAuxiliar) {
		delegado.destroy(tablaAuxiliar);
	}

	public com.ecological.paper.documentos.TablaAuxiliar findTablaAuxiliar(
			TablaAuxiliar tablaAuxiliar) {
		return delegado.findTablaAuxiliar(tablaAuxiliar);
	}

	public void create(TablaAuxiliar tablaAuxiliar) {
		delegado.create(tablaAuxiliar);
	}

	public void create(Hist_usuarios hist_usuarios) {
		delegado.create(hist_usuarios);
	}

	public java.util.List findAll_HistoricoByUsuario(Usuario usuario) {
		return delegado.findAll_HistoricoByUsuario(usuario);
	}

	public com.ecological.paper.documentos.Doc_historicoActivoMaestro findDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		return delegado
				.findDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
	}

	public void create(Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		delegado.create(doc_historicoActivoMaestro);
	}

	public void create(Doc_historicoActivoDetalle doc_historicoActivoDetalle) {
		delegado.create(doc_historicoActivoDetalle);
	}

	public java.util.List<com.ecological.paper.documentos.Doc_historicoActivoDetalle> findAllDoc_historicoActivoDetalle(
			Doc_historicoActivoDetalle doc_historicoActivoDetalle) {
		return delegado
				.findAllDoc_historicoActivoDetalle(doc_historicoActivoDetalle);
	}

	public List<Doc_historicoActivoMaestro> findAllDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro) {
		return delegado
				.findAllDoc_historicoActivoMaestro(doc_historicoActivoMaestro);
	}

	public void arreglarCamposNullInTablas() {
		delegado.arreglarCamposNullInTablas();
	}

	public com.software.zonas.Ciudad find_ciudad(Ciudad ciudad) {
		return delegado.find_ciudad(ciudad);
	}

	public void destroy(Ciudad ciudad) {
		delegado.destroy(ciudad);
	}

	public void edit(Ciudad ciudad) {
		delegado.edit(ciudad);
	}

	public void create(Ciudad ciudad) {
		delegado.create(ciudad);
	}

	public com.software.zonas.Estado find_estado(Estado estado) {
		return delegado.find_estado(estado);
	}

	public void destroy(Estado estado) {
		delegado.destroy(estado);
	}

	public void edit(Estado estado) {
		delegado.edit(estado);
	}

	public void create(Estado estado) {
		delegado.create(estado);
	}

	public com.software.zonas.Pais find_pais(Pais pais) {
		return delegado.find_pais(pais);
	}

	public void destroy(Pais pais) {
		delegado.destroy(pais);
	}

	public void edit(Pais pais) {
		delegado.edit(pais);
	}

	public void create(Pais pais) {
		delegado.create(pais);
	}

	public com.software.zonas.Ciudad find(Ciudad ciudad) {
		return delegado.find(ciudad);
	}

	public com.software.zonas.Estado find(Estado estado) {
		return delegado.find(estado);

	}

	public com.software.zonas.Pais find(Pais pais) {
		return delegado.find(pais);

	}

	public java.util.List<com.software.zonas.Pais> find_allPaises() {
		return delegado.find_allPaises();
	}

	public java.util.List findAll_Paises(String search) {
		return delegado.findAll_Paises(search);
	}

	public java.util.List<com.software.zonas.Estado> find_allEstados() {
		return delegado.find_allEstados();
	}

	public java.util.List findAll_Estado(String search) {
		return delegado.findAll_Estado(search);
	}

	public java.util.List<com.software.zonas.Ciudad> find_allCiudad() {
		return delegado.find_allCiudad();
	}

	public java.util.List findAll_Ciudad(String search) {
		return delegado.findAll_Ciudad(search);
	}

	public com.software.zonas.Pais findPais_InEstado(Pais pais) {
		return delegado.findPais_InEstado(pais);
	}

	public com.software.zonas.Estado findEstado_InCiudad(Estado estado) {
		return delegado.findEstado_InCiudad(estado);
	}

	public java.util.List<com.software.zonas.Estado> findAll_EstadoByPais(
			Pais pais) {
		return delegado.findAll_EstadoByPais(pais);

	}

	public java.util.List<com.software.zonas.Ciudad> findAll_CiudadByEstado(
			Estado estado) {
		return delegado.findAll_CiudadByEstado(estado);
	}

	// CLIENTE EUJ-------------------------------------------
	public java.util.List<com.software.eujovans.clientes.Cliente_EUJ> findAll_CiudadByClienteEUJ(
			Ciudad ciudad, Estado estado, Pais pais, String buscar) {
		return delegado
				.findAll_CiudadByClienteEUJ(ciudad, estado, pais, buscar);
	}

	public void create(Cliente_EUJ cliente_EUJ) {
		delegado.create(cliente_EUJ);
	}

	public void edit(Cliente_EUJ cliente_EUJ) {
		delegado.edit(cliente_EUJ);
	}

	public void destroy(Cliente_EUJ cliente_EUJ) {
		delegado.destroy(cliente_EUJ);
	}

	public com.software.eujovans.clientes.Cliente_EUJ find(
			Cliente_EUJ cliente_EUJ) {
		return delegado.find(cliente_EUJ);
	}

	public com.software.eujovans.clientes.Cliente_EUJ find_cliente_EUJ(
			Cliente_EUJ cliente_EUJ) {
		return delegado.find_cliente_EUJ(cliente_EUJ);
	}

	public java.util.List<com.software.eujovans.clientes.Cliente_EUJ> find_allCliente_EUJ() {
		return delegado.find_allCliente_EUJ();
	}

	public void create(Factura factura) {
		delegado.create(factura);
	}

	public void edit(Factura factura) {
		delegado.edit(factura);
	}

	public void destroy(Factura factura) {
		delegado.destroy(factura);
	}

	public DelegadoEJBLocal getDelegado() {
		return delegado;
	}

	public com.software.eujovans.clientes.Factura find(Factura factura) {
		return delegado.find(factura);
	}

	public com.software.eujovans.clientes.Factura find_Factura(Factura factura) {
		return delegado.find_Factura(factura);
	}

	public void create(ValidaVencimiento validaVencimiento) {
		delegado.create(validaVencimiento);
	}

	public void edit(ValidaVencimiento validaVencimiento) {
		delegado.edit(validaVencimiento);
	}

	public ValidaVencimiento find_ValidaVencimiento(
			ValidaVencimiento validaVencimiento) {
		return delegado.find_ValidaVencimiento(validaVencimiento);
	}

	public List<ValidaVencimiento> find_allValidaVencimiento() {
		return delegado.find_allValidaVencimiento();
	}

	public List<Factura> find_allFactura(Factura factura) {
		return delegado.find_allFactura(factura);
	}

	public Factura find_FacturaNumEntrega(Factura factura) {

		return delegado.find_FacturaNumEntrega(factura);
	}

	public Ciudad findCiudad_InFacturaEUJOVANS(Ciudad ciudad) {
		return delegado.findCiudad_InFacturaEUJOVANS(ciudad);
	}

	public Factura findClienteEUJOVANS_InFactura(Cliente_EUJ destinatario,
			Cliente_EUJ remitente) {
		return delegado.findClienteEUJOVANS_InFactura(destinatario, remitente);
	}

	public Cliente_EUJ find_cliente_EUJ_BYNombre(Cliente_EUJ cliente_EUJ) {
		return delegado.find_cliente_EUJ_BYNombre(cliente_EUJ);
	}

	public Configuracion find_configuracion(Configuracion configuracion) {
		return delegado.find_configuracion(configuracion);
	}

	public void destroy(Configuracion configuracion) {
		delegado.destroy(configuracion);
	}

	public void edit(Configuracion configuracion) {
		delegado.edit(configuracion);
	}

	public void create(Configuracion configuracion) {
		delegado.create(configuracion);
	}

	public List<Configuracion> find_allConfiguracion() {
		return delegado.find_allConfiguracion();
	}

	public void create(DiasHabiles diasHabiles) {
		delegado.create(diasHabiles);
	}

	public void edit(DiasHabiles diasHabiles) {
		delegado.edit(diasHabiles);
	}

	void destroy(DiasHabiles diasHabiles) {
		delegado.destroy(diasHabiles);
	}

	public com.ecological.dias.habiles.DiasHabiles find(DiasHabiles diasHabiles) {
		return delegado.find(diasHabiles);
	}

	public com.ecological.dias.habiles.DiasHabiles find_DiasHabiles(
			DiasHabiles diasHabiles) {
		return delegado.find_DiasHabiles(diasHabiles);
	}

	public java.util.List<com.ecological.dias.habiles.DiasHabiles> find_allDiasHabiles(
			Tree empresas) {
		return delegado.find_allDiasHabiles(empresas);
	}

	public void create(DiasFeriadosBean diasHabiles) {
		delegado.create(diasHabiles);
	}

	public void edit(DiasFeriadosBean diasHabiles) {
		delegado.edit(diasHabiles);
	}

	public void destroy(DiasFeriadosBean diasHabiles) {
		delegado.destroy(diasHabiles);
	}

	public com.ecological.dias.feriados.DiasFeriadosBean find(
			DiasFeriadosBean diasHabiles) {
		return delegado.find(diasHabiles);
	}

	public java.util.List<com.ecological.dias.feriados.DiasFeriadosBean> find_allDiasFeriadosBean(
			Usuario usuario) {
		return delegado.find_allDiasFeriadosBean(usuario);
	}

	public com.ecological.dias.feriados.DiasFeriadosBean find_DiasFeriados(
			DiasFeriadosBean diasHabiles) {
		return delegado.find_DiasFeriados(diasHabiles);
	}

	public void create(FlowControlByUsuarioBean obj) {
		delegado.create(obj);
	}

	public void edit(FlowControlByUsuarioBean obj) {
		delegado.edit(obj);

	}

	public void destroy(FlowControlByUsuarioBean obj) {
		delegado.destroy(obj);

	}

	public com.ecological.control.timeflow.FlowControlByUsuarioBean find(
			FlowControlByUsuarioBean obj) {
		return delegado.find(obj);

	}

	public com.ecological.control.timeflow.FlowControlByUsuarioBean find_FlowControlByUsuarioBean(
			FlowControlByUsuarioBean obj) {
		return delegado.find_FlowControlByUsuarioBean(obj);
	}

	public java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allFlowControlByUsuarioBean() {
		return delegado.find_allFlowControlByUsuarioBean();
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByFlowBean(
			Flow flow) {
		return delegado.find_allFlowControlByFlowBean(flow);
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByRoleBean(
			Role role, Flow flow) {
		return delegado.find_allFlowControlByRoleBean(role, flow);
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndRole(
			Flow flow) {
		return delegado.find_allControlTimeByFlowParticipAndRole(flow);
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipNulos(
			Flow flow) {
		return delegado.find_allControlTimeByFlowParticipNulos(flow);
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByHilo() {
		return delegado.find_allFlowControlByHilo();
	}

	public DiasHabiles find_DiasHabilesByDia(Usuario usuario, String diaSemana) {
		return delegado.find_DiasHabilesByDia(usuario, diaSemana);
	}

	public List<FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndFlow(
			Flow_Participantes flow_Participantes) {
		return delegado
				.find_allControlTimeByFlowParticipAndFlow(flow_Participantes);
	}

	// _________________________________________________________________________________________________________________________//
	// ______________________________________________________________________________________________________________________//

	// PARA SACAR ESTADISTICAS DE LOS FLUJOS
	public List findAllFlow_ParticipantesByUsuaurio(Usuario usuario) {
		return delegado.findAllFlow_ParticipantesByUsuaurio(usuario);
	}

	public List findAllFlowOfParticipantes(Flow_Participantes f_p) {
		return delegado.findAllFlowOfParticipantes(f_p);
	}

	public List findAllFlowControlOfParticipantes(Flow_Participantes f_p) {
		return delegado.findAllFlowControlOfParticipantes(f_p);
	}

	// FIN PARA SACAR ESTADISTICAS DE LOS FLUJOS
	public List<Flow> findAllFlowEnviadosByUsuario(Flow f) {
		return delegado.findAllFlowEnviadosByUsuario(f);
	}

	public void create(Flow_ParticipantesAttachment objeto) {
		delegado.create(objeto);
	}

	public void edit(Flow_ParticipantesAttachment objeto) {
		delegado.edit(objeto);
	}

	public void destroy(Flow_ParticipantesAttachment objeto) {
		delegado.destroy(objeto);
	}

	public com.ecological.paper.flows.Flow_ParticipantesAttachment find(
			Flow_ParticipantesAttachment objeto) {
		return delegado.find(objeto);
	}

	public com.ecological.paper.flows.Flow_ParticipantesAttachment find_Flow_ParticipantesAttachment(
			Flow_ParticipantesAttachment objeto) {
		return delegado.find_Flow_ParticipantesAttachment(objeto);
	}

	public FlowControlByUsuarioBean find_FlowControlIsSecuencialGetFechaFirmaUltimo(
			FlowControlByUsuarioBean flowControlByUsuarioBean) {
		return delegado
				.find_FlowControlIsSecuencialGetFechaFirmaUltimo(flowControlByUsuarioBean);
	}

	public List queryExecute(String str, Date fechaDesde, Date fechaHasta) {
		return delegado.queryExecute(str, fechaDesde, fechaHasta);
	}

	public List findAll_FlowsHistoricoUser(Usuario user) {
		return delegado.findAll_FlowsHistoricoUser(user);
	}

	public List findAll_FlowsHistoricoUserCreados(Usuario user) {
		return delegado.findAll_FlowsHistoricoUserCreados(user);
	}

	public Usuario findLoginOrMailExistaLdap(Usuario usuario) {
		return delegado.findLoginOrMailExistaLdap(usuario);
	}

	public List<FlowControlByUsuarioBean> find_allFlowControlByRoleBean(
			Role role) {
		return delegado.find_allFlowControlByRoleBean(role);
	}

	/*
	 * public void create(Flow_ParticipantesAttachment objeto) {
	 * delegado.create(objeto); }
	 */

	public void create(ExtensionFile objeto) {
		delegado.create(objeto);
	}

	public void edit(ExtensionFile extensionFile) {
		delegado.edit(extensionFile);
	}

	public void destroy(ExtensionFile extensionFile) {
		delegado.destroy(extensionFile);
	}

	public ExtensionFile find(ExtensionFile extensionFile) {
		return delegado.find(extensionFile);

	}

	public List<ExtensionFile> find_allExtensionFile() {
		return delegado.find_allExtensionFile();
	}

	public List find_allExtensionFile(String search) {
		return delegado.find_allExtensionFile(search);
	}

	public void create(ExtensionFileHijos extensionFile) {
		delegado.create(extensionFile);
	}

	public void edit(ExtensionFileHijos extensionFile) {
		delegado.edit(extensionFile);
	}

	public void destroy(ExtensionFileHijos extensionFile) {
		delegado.destroy(extensionFile);
	}

	public ExtensionFileHijos find(ExtensionFileHijos extensionFile) {
		return delegado.find(extensionFile);
	}

	public List<ExtensionFileHijos> find_allExtensionFile(
			ExtensionFile extensionFile) {
		return delegado.find_allExtensionFile(extensionFile);

	}

	public ExtensionFile tipoContextType(String ext) {
		return delegado.tipoContextType(ext);
	}

	public ExtensionFileHijos find_ExtensionFileByExtension(
			ExtensionFileHijos extensionFile) {
		return delegado.find_ExtensionFileByExtension(extensionFile);
	}

	public List findAllEmpresas(Usuario usuario) {
		return delegado.findAllEmpresas(usuario);

	}

	public SubVersionUsuario find(SubVersionUsuario subVersionUsuario) {
		return delegado.find(subVersionUsuario);
	}

	public void destroy(SubVersionUsuario subVersionUsuario) {
		delegado.destroy(subVersionUsuario);
	}

	public void create(SubVersionUsuario subVersionUsuario) {
		delegado.create(subVersionUsuario);
	}

	public void edit(SubVersionUsuario subVersionUsuario) {
		delegado.edit(subVersionUsuario);
	}

	public void createSubVersionUsuario(SubVersionUsuario subVersionUsuario)
			throws ExceptionSubVersion {
		delegado.createSubVersionUsuario(subVersionUsuario);

	}

	public List findAll_Roles(Usuario usuario, String search, boolean workFlow) {
		return delegado.findAll_Roles(usuario, search, workFlow);
	}

	public List findAll_Roles(Usuario usuario, boolean workFlow) {
		return delegado.findAll_Roles(usuario, workFlow);
	}

	public void create(FlowParalelo objeto) throws EcologicaExcepciones {
		delegado.create(objeto);
	}

	public Tree findTree(Long objeto) {
		return delegado.findTree(objeto);
	}

	public List findAllFlowParalelos(FlowParalelo objeto) {
		return delegado.findAllFlowParalelos(objeto);
	}

	public List calculandoPrecendenciaFlowParalelos(FlowParalelo objeto) {
		return delegado.calculandoPrecendenciaFlowParalelos(objeto);
	}

	public List findByFlowParaleloAllFlows(FlowParalelo objeto) {
		return delegado.findByFlowParaleloAllFlows(objeto);
	}

	public Flow_Participantes flowUltimoParticipante(Flow objeto) {
		return delegado.flowUltimoParticipante(objeto);
	}

	public void delete(FlowParalelo objeto) throws EcologicaExcepciones {
		delegado.delete(objeto);
	}

	public List findParalelos(Usuario usuario) {
		return delegado.findParalelos(usuario);
	}

	public void edit(FlowParalelo objeto) throws EcologicaExcepciones {
		delegado.edit(objeto);
	}

	public List findByFlowParaleloPlantilla(FlowParalelo flowParalelo) {
		return delegado.findByFlowParaleloPlantilla(flowParalelo);
	}

	public FlowParalelo find(FlowParalelo objeto) throws EcologicaExcepciones {
		return delegado.find(objeto);
	}

	public List findByFlowParticipantesPlantilla(Doc_detalle doc) {
		return delegado.findByFlowParticipantesPlantilla(doc);
	}

	public List activarFlowParaleloAllFlows(FlowParalelo flowParalelo) {
		return delegado.activarFlowParaleloAllFlows(flowParalelo);
	}

	public List findByFlowParticipantesFlowIsActivo(Flow flow) {
		return delegado.findByFlowParticipantesFlowIsActivo(flow);
	}

	public Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSVN(
			Flow_ParticipantesAttachment objeto) {
		return delegado.find_Flow_ParticipantesAttachmentSVN(objeto);
	}

	public Doc_tipo findTipoDoc(Usuario usuario, Doc_tipo doc_tipo) {
		return delegado.findTipoDoc(usuario, doc_tipo);
	}

	public boolean finFlowParaleloForAlterarDocdetalle(Flow flow) {
		return delegado.finFlowParaleloForAlterarDocdetalle(flow);
	}

	public List<SvnUpload> listUploadAttachmentSvn(Flow flow) {
		return delegado.listUploadAttachmentSvn(flow);
	}

	public File fileAttachmentDocDetalle(Doc_detalle doc_detalle)
			throws SQLException {
		return delegado.fileAttachmentDocDetalle(doc_detalle);
	}

	public File filetFlowAttachment(Flow_ParticipantesAttachment doc_detalle)
			throws SQLException {
		return delegado.filetFlowAttachment(doc_detalle);
	}

	public Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSvnPorParticipante(
			Flow_ParticipantesAttachment objeto) {
		return delegado
				.find_Flow_ParticipantesAttachmentSvnPorParticipante(objeto);

	}

	public void create(SvnUrlBase objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnUrlBase objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnUrlBase objeto) {
		delegado.destroy(objeto);
	}

	public SvnUrlBase find(SvnUrlBase objeto) {
		return delegado.find(objeto);
	}

	public void create(SvnNombreAplicacion objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnNombreAplicacion objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnNombreAplicacion objeto) {
		delegado.destroy(objeto);
	}

	public SvnNombreAplicacion find(SvnNombreAplicacion objeto) {
		return delegado.find(objeto);
	}

	public void create(SvnModulo objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnModulo objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnModulo objeto) {
		delegado.destroy(objeto);
	}

	public SvnModulo find(SvnModulo objeto) {
		return delegado.find(objeto);
	}

	public void create(SvnExtension objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnExtension objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnExtension objeto) {
		delegado.destroy(objeto);
	}

	public SvnExtension find(SvnExtension objeto) {
		return delegado.find(objeto);
	}

	public List findAllSvnUrlBase(Usuario usuario, String strBuscar) {
		return delegado.findAllSvnUrlBase(usuario, strBuscar);
	}

	public List findAllSvnNombreAplicacion(Usuario usuario, String strBuscar) {
		return delegado.findAllSvnNombreAplicacion(usuario, strBuscar);
	}

	public List findAllSvnNombreAplicacion(SvnUrlBase objeto) {
		return delegado.findAllSvnNombreAplicacion(objeto);
	}

	public List findAllSvnModulo(SvnTipoAmbiente objeto) {
		return delegado.findAllSvnModulo(objeto);
	}

	public List findAllSvnExtension(String strBuscar) {
		return delegado.findAllSvnExtension(strBuscar);
	}

	public void create(SvnTipoAmbiente objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnTipoAmbiente objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnTipoAmbiente objeto) {
		delegado.destroy(objeto);
	}

	public SvnTipoAmbiente find(SvnTipoAmbiente objeto) {
		return delegado.find(objeto);
	}

	public List findAllSvnTipoAmbiente(Usuario usuario, String strBuscar) {
		return delegado.findAllSvnTipoAmbiente(usuario, strBuscar);
	}

	public List findAllSvnTipoAmbiente(SvnNombreAplicacion objeto) {
		return delegado.findAllSvnTipoAmbiente(objeto);
	}

	public void sustituirFlowParticipantesOldByOtroFlows(
			Flow_Participantes flow_Participante, Usuario nuevo,
			String comentario) {
		delegado.sustituirFlowParticipantesOldByOtroFlows(flow_Participante,
				nuevo, comentario);
	}

	public List findAll_FlowParaleloUser(Usuario user) {
		return delegado.findAll_FlowParaleloUser(user);
	}

	public List findAll_FlowParaleloUserCreados(Usuario user) {
		return delegado.findAll_FlowParaleloUserCreados(user);
	}

	public List findAllTipoDeFirmaWithParticipanteAnterior(Flow flow) {
		return delegado.findAllTipoDeFirmaWithParticipanteAnterior(flow);
	}

	public Flow_Participantes devolucionFirmaFlow(
			Flow_Participantes flow_Participante) {
		return delegado.devolucionFirmaFlow(flow_Participante);
	}

	public void findFlowParticipantesSinFirmar(Flow flow) {
		delegado.findFlowParticipantesSinFirmar(flow);
	}

	public void create(SvnRutasRelativasFiles objeto) {
		delegado.create(objeto);
	}

	public void edit(SvnRutasRelativasFiles objeto) {
		delegado.edit(objeto);
	}

	public void destroy(SvnRutasRelativasFiles objeto) {
		delegado.destroy(objeto);
	}

	public SvnRutasRelativasFiles find(SvnRutasRelativasFiles objeto) {
		return delegado.find(objeto);
	}

	public List findAllSvnRutasRelativasFiles(Doc_maestro objeto) {
		return delegado.findAllSvnRutasRelativasFiles(objeto);

	}

	public List findAllSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment) {
		return delegado
				.findAllSvnRutasRelativasFiles(flow_ParticipantesAttachment);
	}

	public SvnRutasRelativasFiles findSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment,
			String archivo) {
		return delegado.findSvnRutasRelativasFiles(
				flow_ParticipantesAttachment, archivo);
	}

	public SvnRutasRelativasFiles findSvnRutasRelativasFiles(
			Doc_maestro doc_maestro, String archivo) {
		return delegado.findSvnRutasRelativasFiles(doc_maestro, archivo);
	}

	public List findByFlowParticipantesSinRolForEditar(Flow flow) {
		return delegado.findByFlowParticipantesSinRolForEditar(flow);
	}

	public List findByFlowParaleloAllFlowsForPadreFlow(FlowParalelo flowParalelo) {
		return delegado.findByFlowParaleloAllFlowsForPadreFlow(flowParalelo);
	}

	public List findAllFlowParalelos(Flow flow) {
		return delegado.findAllFlowParalelos(flow);
	}

	public void findFlowParticipantesFirmadosAndFlowIspendiente(Flow flow) {
		delegado.findFlowParticipantesFirmadosAndFlowIspendiente(flow);
	}

	public Flow_Participantes devolucionFirmaFlowEnParalelo(
			Flow_Participantes flow_Participante) {
		return delegado.devolucionFirmaFlowEnParalelo(flow_Participante);
	}

	public void devolvemos(Flow_Participantes flow_Participante,
			Flow_Participantes devolver) {
		delegado.devolvemos(flow_Participante, devolver);
	}

	public FlowParalelo findPlantillaDoc_DetallePlantillaInFlowParalelo(
			FlowParalelo objeto) {
		return delegado.findPlantillaDoc_DetallePlantillaInFlowParalelo(objeto);
	}

	public Tree findEnNivelArribaTreeDeAcuerdoSuTipo(Tree tree, long tiponodo) {
		return delegado.findEnNivelArribaTreeDeAcuerdoSuTipo(tree, tiponodo);
	}

	public Solicitudimpresion findSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion) {
		return delegado.findSolicitudimpresionByDocDetalle(solicitudimpresion);
	}

	public void edit(Solicitudimpresion objeto) {
		delegado.edit(objeto);
	} // _________________________________________________________________________________________________________________________//

	public List<SolicitudImpPart> solicitudImpPartsCancelar(
			SolicitudImpPart objeto) {
		return delegado.solicitudImpPartsCancelar(objeto);

	}

	public Solicitudimpresion find(Solicitudimpresion objeto) {
		return delegado.find(objeto);
	}

	public List<Solicitudimpresion> findAllSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion) {
		return delegado
				.findAllSolicitudimpresionByDocDetalle(solicitudimpresion);
	}

	public List<SolicitudImpPart> findAllsolicitudImpParts(
			Solicitudimpresion solicitudimpresion) {
		return delegado.findAllsolicitudImpParts(solicitudimpresion);
	}

	public Solicitudimpresion findSolicitudimpresionByFlowDelete(
			Solicitudimpresion solicitudimpresion) {
		return delegado.findSolicitudimpresionByFlowDelete(solicitudimpresion);
	}

	public void destroy(Solicitudimpresion objeto) {
		delegado.destroy(objeto);
	}

	public void putPublicoDocumento(Doc_detalle doc_detalle,
			List<Role> roleSeleccionados, Usuario publicador)
			throws EcologicaExcepciones {
		delegado.putPublicoDocumento(doc_detalle, roleSeleccionados, publicador);
	}

	public List findAllRolesOnlyViewDocPublicados(Doc_detalle doc_detalle) {
		return delegado.findAllRolesOnlyViewDocPublicados(doc_detalle);
	}

	public RolesOnlyViewDocPublicados findAllRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados) {
		return delegado
				.findAllRolesOnlyViewDocPublicados(rolesOnlyViewDocPublicados);
	}

	public Boolean usuarioRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados) {

		return delegado
				.usuarioRolesOnlyViewDocPublicados(rolesOnlyViewDocPublicados);

	}

	public void notificarPorMailPublicoExpiro(Doc_detalle doc_detalle) {
		delegado.notificarPorMailPublicoExpiro(doc_detalle);
	}

	public void create(PublicadosUsuComent objeto) {
		delegado.create(objeto);
	}

	public PublicadosUsuComent findOnePublicadosUsuComent(
			PublicadosUsuComent publicadosUsuComent) {
		return delegado.findOnePublicadosUsuComent(publicadosUsuComent);
	}

	public void edit(PublicadosUsuComent objeto) {
		delegado.edit(objeto);
	}

	public List<PublicadosUsuComent> findAllPublicadosUsuComentLst(
			PublicadosUsuComent publicadosUsuComent) {
		return delegado.findAllPublicadosUsuComentLst(publicadosUsuComent);

	}

	public List<PublicadosUsuComent> findAllPublicadosVersionesUsuComentLst(
			PublicadosUsuComent publicadosUsuComent) {

		return delegado
				.findAllPublicadosVersionesUsuComentLst(publicadosUsuComent);
	}

	public void create(AreaDocumentos objeto) {
		delegado.create(objeto);
	}

	public void edit(AreaDocumentos objeto) {
		delegado.edit(objeto);
	}

	public void destroy(AreaDocumentos objeto) {
		delegado.destroy(objeto);
	}

	public AreaDocumentos find(AreaDocumentos pk) {
		return delegado.find(pk);
	}

	public AreaDocumentos findAreaDocumentos(String id) {
		return delegado.findAreaDocumentos(id);
	}

	public List findAllAreaDocumentos(Usuario usuario, String search) {
		return delegado.findAllAreaDocumentos(usuario, search);
	}

	public List findAllAreaDocumentos(Usuario usuario) {
		return delegado.findAllAreaDocumentos(usuario);
	}

	public Boolean findExisteAreaDocumentosDocDetallesFlowParalelo(
			AreaDocumentos areaDocumentos) {
		return delegado
				.findExisteAreaDocumentosDocDetallesFlowParalelo(areaDocumentos);
	}

	public List findByFlowParticipantesOrigenFlow(Flow flow) {
		return delegado.findByFlowParticipantesOrigenFlow(flow);
	}

	public List findByFlowByOrigen(Doc_detalle doc) {
		return delegado.findByFlowByOrigen(doc);
	}

	public Boolean findExistePlantillaImpresionFlow(FlowParalelo objeto)
			throws EcologicaExcepciones {
		return delegado.findExistePlantillaImpresionFlow(objeto);
	}

	public List findByFlowInFlow(Doc_detalle doc) {
		return delegado.findByFlowInFlow(doc);
	}

	public List<SolicitudImpPart> findAllsolicitudImpParts(
			SolicitudImpPart solicitudImpPart) {

		return delegado.findAllsolicitudImpParts(solicitudImpPart);
	}

	public List<SolicitudImpPart> findsolicitudImpPartsToSendToImprimir(
			SolicitudImpPart solicitudImpPart) {
		return delegado.findsolicitudImpPartsToSendToImprimir(solicitudImpPart);
	}

	public FlowParalelo findExistePlantillaImpresionFlowObjeto(
			FlowParalelo objeto) throws EcologicaExcepciones {
		return delegado.findExistePlantillaImpresionFlowObjeto(objeto);
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsDocDetalle(
			SolicitudImpPart solicitudImpPart) {
		return delegado.findAllsolicitudImpPartsDocDetalle(solicitudImpPart);
	}

	public SolicitudImpPart find(SolicitudImpPart objeto) {
		return delegado.find(objeto);
	}

	public List<SolicitudImpPart> findAllsolicitudImpPartsEmpresa(
			SolicitudImpPart solicitudImpPart) {
		return delegado.findAllsolicitudImpPartsEmpresa(solicitudImpPart);
	}

	public List findByFlowParticipantes(Flow_Participantes flow_Participantes) {
		return delegado.findByFlowParticipantes(flow_Participantes);
	}

	public List findAllFlowParalelosDelSistema(FlowParalelo flowParalelo) {
		return delegado.findAllFlowParalelosDelSistema(flowParalelo);
	}

	public String findAllExtensionWithHijos() {
		return delegado.findAllExtensionWithHijos();
	}

	public void create(RolesOnlyViewDocPublicados objeto) {
		delegado.create(objeto);
	}

	public List findAllScannerDoc(Usuario usuario) {
		return delegado.findAllScannerDoc(usuario);
	}

	public void destroy(ScannerDoc objeto) {
		delegado.destroy(objeto);
	}

	public Map soloMenuOptimizado(Usuario usuario,
			Map seguridadIndividualPertenesco) {
		return delegado.soloMenuOptimizado(usuario,
				seguridadIndividualPertenesco);
	}

	public Map buscarSeguridadIndividualOptimizado(Usuario usuario,
			Map seguridadIndividualPertenesco) {
		return delegado.buscarSeguridadIndividualOptimizado(usuario,
				seguridadIndividualPertenesco);
	}

	public Map buscarSeguridadIndividualOptimizado(Usuario usuario, Tree tree,
			Map user_seguridad) {
		return delegado.buscarSeguridadIndividualOptimizado(usuario, tree,
				user_seguridad);
	}

	public void heredarRolePermiso(Seguridad_Role_Lineal seguridad_Role_Lineal,
			boolean swElimina, long tipoNodo) throws EcologicaExcepciones {
		delegado.heredarRolePermiso(seguridad_Role_Lineal, swElimina, tipoNodo);
	}

	public void seguridadForRolesByTree(List<Role> roleLst, Tree tree,
			boolean heredaSeguridad) {
		delegado.seguridadForRolesByTree(roleLst, tree, heredaSeguridad);
	}

	public String seguridadArbol(Usuario user_logueado,
			Seguridad seguridadTree, Map user_seguridad) {
		return delegado.seguridadArbol(user_logueado, seguridadTree,
				user_seguridad);

	}

	public void llenarDatosRole(Role role, List<Operaciones> visibleItems) {
		delegado.llenarDatosRole(role, visibleItems);
	}

	public void asignamosPermiso(Tree registro, Usuario user_logueado,
			Tree treeNodoActual) {
		delegado.asignamosPermiso(registro, user_logueado, treeNodoActual);
	}

	public void firmarFlows(Flow_Participantes flow_Participante,
			Flow_ParticipantesAttachment flow_ParticipantesAttachment)
			throws EcologicaExcepciones {
		delegado.firmarFlows(flow_Participante, flow_ParticipantesAttachment);
	}

	public Usuario findUsuarioLoginEditar(String login, Usuario usucreador)
			throws UsuarioNoEncontrado, UsuarioMultiples {
		return delegado.findUsuarioLoginEditar(login, usucreador);
	}
	public List findByFlowParticipantesByRevOrAprob(Doc_detalle doc,Doc_estado doc_estado) {
		return delegado.findByFlowParticipantesByRevOrAprob( doc, doc_estado);
	}
	public List findAllFlowParticipantesPendientesDoc_Detalle(Usuario participante,Doc_detalle doc_detalle) {
		return delegado.findAllFlowParticipantesPendientesDoc_Detalle(participante, doc_detalle);
	}
	public List<ExtensionFile> find_ExtensionFileByExtAndMime(ExtensionFile extensionFile) {
		return delegado.find_ExtensionFileByExtAndMime( extensionFile);
	}
	public Role findRoleADefault(Role role) throws RoleMultiples, RoleNoEncontrado {
		return delegado.findRoleADefault(role);
	}
	public void destroy(FlowOnlyNotificacionRole flow) throws EcologicaExcepciones {
		delegado.destroy(flow);
	}
	public void create(FlowOnlyNotificacionRole flow) throws EcologicaExcepciones {
		delegado.create(flow);		
	}
	public List findAllFlowOnlyNotificacionRoleByFlow(Flow flow) {
		return delegado.findAllFlowOnlyNotificacionRoleByFlow(flow);
	}
	// ______________________________________________________________________________________________________________________//
}