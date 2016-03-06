package com.ecological.paper.delegado;

import com.ecological.configuracion.Configuracion;
import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.extensionesfile.ExtensionFile;
import com.ecological.extensionesfile.ExtensionFileHijos;
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
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
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
import com.util.ValidaVencimiento;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * This is the business interface for DelegadoEJB enterprise bean.
 */
@Remote
public interface DelegadoEJBRemote {
	List findAll_Roles(Usuario usuario, String search, boolean workFlow);

	List findAll_Roles(Usuario usuario, boolean workFlow);

	SubVersionUsuario find(SubVersionUsuario subVersionUsuario);

	void destroy(SubVersionUsuario subVersionUsuario);

	void create(SubVersionUsuario subVersionUsuario);

	void edit(SubVersionUsuario subVersionUsuario);

	public void createSubVersionUsuario(SubVersionUsuario subVersionUsuario)
			throws ExceptionSubVersion;

	void create(Doc_detalle doc) throws EcologicaExcepciones;

	void edit(Doc_detalle doc) throws EcologicaExcepciones;

	void destroy(Doc_detalle doc) throws EcologicaExcepciones;

	com.ecological.paper.documentos.Doc_detalle findDocDetalle(Doc_detalle pk);

	java.util.List findAllDoc_Detalles(Doc_maestro doc_maestro);

	java.util.List findAllDoc_Detalles();

	void create(Doc_maestro doc) throws EcologicaExcepciones;

	void edit(Doc_maestro doc) throws EcologicaExcepciones;

	void destroy(Doc_maestro doc) throws EcologicaExcepciones;

	com.ecological.paper.documentos.Doc_maestro findDocMaestro(Doc_maestro pk);

	java.util.List findAllDoc_maestros();

	java.util.List findAllDoc_maestros(Tree tree);

	void create(Doc_estado doc) throws EcologicaExcepciones;

	void edit(Doc_estado doc) throws EcologicaExcepciones;

	void destroy(Doc_estado doc) throws EcologicaExcepciones;

	com.ecological.paper.documentos.Doc_estado findDocEstado(Doc_estado pk);

	java.util.List findDocEstadoName(Doc_estado estado);

	java.util.List findAllDoc_estados();

	java.util.List findAllTipoDeFirma();

	java.util.List findAllTipoDeFirma(Flow flow);

	void create(Doc_tipo doc) throws EcologicaExcepciones;

	void edit(Doc_tipo doc) throws EcologicaExcepciones;

	void destroy(Doc_tipo doc) throws EcologicaExcepciones;

	com.ecological.paper.documentos.Doc_tipo findDocTipo(Doc_tipo pk);

	public Doc_tipo findDocTipo(Doc_tipo pk, Usuario usuario);

	java.util.List findAllDoc_tipos(Usuario usuario);

	void create(Doc_relacionados doc) throws EcologicaExcepciones;

	void edit(Doc_relacionados doc) throws EcologicaExcepciones;

	void destroy(Doc_relacionados doc) throws EcologicaExcepciones;

	com.ecological.paper.documentos.Doc_relacionados findDocRel(
			Doc_relacionados pk);

	void create(Flow flow) throws EcologicaExcepciones;

	void edit(Flow flow) throws EcologicaExcepciones;

	void destroy(Flow flow) throws EcologicaExcepciones;

	com.ecological.paper.flows.Flow findFlow(Flow pk);

	java.util.List findAllFlow();

	java.util.List findByFlow(Doc_detalle doc);

	java.util.List findDocumentoDetalleInFlow(Doc_detalle doc);

	void create(Flow_Participantes flow) throws EcologicaExcepciones;

	void edit(Flow_Participantes flow) throws EcologicaExcepciones;

	void destroy(Flow_Participantes flow) throws EcologicaExcepciones;

	com.ecological.paper.flows.Flow_Participantes findFlow(Flow_Participantes pk);

	java.util.List findAllFlow_Participantes();

	java.util.List findByFlowParticipantes(Flow flow);

	java.util.List findByFlowParticipantes(Doc_detalle doc);

	java.util.List findAllFlowParticipantesPendientes(Usuario participante);

	void create(Flow_referencia_role flow) throws EcologicaExcepciones;

	void edit(Flow_referencia_role flow) throws EcologicaExcepciones;

	void destroy(Flow_referencia_role flow) throws EcologicaExcepciones;

	com.ecological.paper.flows.Flow_referencia_role findFlow(
			Flow_referencia_role pk);

	java.util.List findAllFlow_referencia_role();

	java.util.List findByFlow_referencia_role(Flow flow);

	void create(Hist_usuarios hist_usuarios);

	void edit(Hist_usuarios hist_usuarios);

	void destroy(Hist_usuarios hist_usuarios);

	com.ecological.paper.historico.Hist_usuarios find(Object pk);

	void edit(Operaciones operaciones) throws EcologicaExcepciones;

	void destroy(Operaciones operaciones) throws EcologicaExcepciones;

	void create(Operaciones operaciones) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	com.ecological.paper.permisologia.operaciones.Operaciones getOperacion(
			Operaciones operacion) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado;

	java.util.List findAll_operaciones();

	com.ecological.paper.permisologia.operaciones.Operaciones Encontrar_operaciones(
			String id);

	java.util.List buscarTodosLasOperacionesRol(Seguridad_Role_Lineal rol);

	java.util.List findOperacionName(Operaciones op);

	void create(Profesion profesion);

	void edit(Profesion profesion);

	void destroy(Profesion profesion);

	com.ecological.paper.profesion.Profesion find(Long pk);

	com.ecological.paper.profesion.Profesion Encontrar_Profesion(String id);

	java.util.List findAll_Profesion(Usuario usuario);

	void create(Role role) throws EcologicaExcepciones, RoleMultiples,
			RoleNoEncontrado;

	void edit(Role role) throws EcologicaExcepciones;

	void destroy(Role role) throws EcologicaExcepciones;

	com.ecological.paper.permisologia.role.Role findRole(Role role)
			throws RoleMultiples, RoleNoEncontrado;

	com.ecological.paper.permisologia.role.Role getRole(Role role)
			throws EcologicaExcepciones, RoleMultiples, RoleNoEncontrado;

	java.util.List findAll_Roles(Usuario usuario);

	java.util.List findRoles_Usuario(Usuario usuario);

	java.util.List findRoles_Usuario(Role role);

	com.ecological.paper.permisologia.role.Role Encontrar_roles(String id);

	void edit(Roles_Usuarios operaciones) throws EcologicaExcepciones;

	void destroy(Roles_Usuarios rol_user) throws EcologicaExcepciones;

	void destroy(Usuario usuario) throws EcologicaExcepciones;

	void create(Roles_Usuarios rol_user);

	// void actualizamosSeguridadUserINUsuarioWithNewRol(Usuario usuario);

	com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios getRole_User(
			Roles_Usuarios role_user) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	java.util.List findAll_Role_User();

	/**
	 * Creates a new instance of Role_OperacionesEJBBean
	 */
	void edit(Role_Operaciones operaciones) throws EcologicaExcepciones;

	void destroy(Role_Operaciones operaciones) throws EcologicaExcepciones;

	void destroy_Rol(Role rol) throws EcologicaExcepciones;

	void create(Role_Operaciones role_operaciones);

	com.ecological.paper.permisologia.role_operaciones.Role_Operaciones getRole_Operaciones(
			Role_Operaciones role_user) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	java.util.List findAll_Role_Operaciones();

	void creat(Menus_Usuarios operaciones_Usuarios);

	void edit(Menus_Usuarios operaciones_Usuarios);

	void destroy(Menus_Usuarios operaciones_Usuarios);

	java.util.List findAllPermMenu(Usuario user);

	void create(Seguridad_User seguridad_User) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	void create(Seguridad_Role seguridad_Role) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	void edit(Seguridad_Role seguridadRole);

	void edit(Seguridad_User seguridad_User) throws EcologicaExcepciones;

	void destroy(Seguridad_Role_Lineal seguridadRole)
			throws EcologicaExcepciones;

	void destroy2(Seguridad_Role_Lineal seguridadRole);

	com.ecological.paper.permisologia.seguridad.Seguridad_User getObjeto(
			Seguridad_User seguridad_User) throws EcologicaExcepciones,
			RoleMultiples, RoleNoEncontrado;

	// com.ecological.paper.permisologia.seguridad.Seguridad_User
	// findTreeOperUserWithRole(Seguridad_User seg);

	com.ecological.paper.permisologia.seguridad.Seguridad_User findTreeOperUser(
			Seguridad_User seg);

	java.util.List findAll_Seguridad_User();

	java.util.List findAllRole();

	com.ecological.paper.permisologia.seguridad.Seguridad_User Encontrar(
			String id);

	java.util.List llenarUsuariosInOperacionesVisibles(Tree tree);

	java.util.List buscarTodosLasOperacionesTreeUsuario(Tree tree,
			Usuario usuario);

	java.util.List buscarTodosLosRolesTree(Tree tree, Role role);

	java.util.List buscarTodosLosRolesTree(Tree tree);

	java.util.List findAllCargos();

	java.util.List findAllCargos(Tree area);

	java.util.List findAllAreas();

	java.util.List findAllTreePadres();

	com.ecological.paper.tree.Tree obtenerNodo(Long idRoot);

	java.util.List findAllTreePadresAbuelos(Tree tree);

	java.util.List findAllTreeHijos(long nodo);

	java.util.List finTreeByName(Tree tree);

	List finTreeByNameAndTipo(Tree tree);

	void crearNuevoTree(Tree tree,Usuario usuario);
	public List<Tree> findTreeUsuarioNull(Tree tree);
	void edit(Tree tree);
	public void crearTreeEmpresa(Tree tree);

	void destroy(Tree tree);

	void actualizaNodoPadreNull();

	java.lang.String construirArbol();

	java.lang.String construirArbol(boolean swSession);

	java.lang.String getNodosHijos(long nodoPadre, char objArbol);

	java.lang.String docTaskPendiente(boolean swSession);

	java.lang.String getImg_principal();

	void setImg_principal(String img_principal);

	java.lang.String getImg_area();

	void setImg_area(String img_area);

	java.lang.String getImg_cargo();

	void setImg_cargo(String img_cargo);

	java.lang.String getImg_proceso();

	void setImg_proceso(String img_proceso);

	java.lang.String getImg_carpeta();

	void setImg_carpeta(String img_carpeta);

	java.lang.String getImg_doc();

	void setImg_doc(String img_doc);

	java.lang.String getImg_raiz();

	void setImg_raiz(String img_raiz);

	java.lang.String getImg_logo();

	void setImg_logo(String img_logo);

	java.lang.String getImg_userLogueado();

	void setImg_userLogueado(String img_userLogueado);

	void create(Usuario usuario) throws EcologicaExcepciones,
			NoSuchAlgorithmException;

	void edit(Usuario usuario) throws EcologicaExcepciones;

	void destroy_Usuario(Usuario usuario) throws EcologicaExcepciones;

	com.ecological.paper.usuario.Usuario find_Usuario(Object pk);

	java.lang.String findLoginOrMailExista(Usuario usuario);

	com.ecological.paper.usuario.Usuario findUsuarioLogin(Usuario usuario)
			throws UsuarioNoEncontrado, UsuarioMultiples;

	com.ecological.paper.usuario.Usuario getUsuario(Usuario usuario)
			throws EcologicaExcepciones;

	java.util.List findAll_Usuario(Usuario usuario);

	java.util.List findExisteCargoInUsuario(Usuario usuario);

	java.util.List findAll(Usuario usuario, String str);

	java.util.List findAll(Usuario usuario, String str, char c);

	com.ecological.paper.usuario.Usuario Encontrar_Usuario(String id);

	// java.util.List buscarSeguridadGrupoPertenece(Usuario usuario);

	// java.util.List buscarSeguridadIndividual(Usuario usuario);

	java.util.List findAll_Historico();

	Doc_detalle findDocDetalle_TipoEdo(Doc_detalle doc_detalle);

	void findFlowBorrarObsoleto(Usuario duenio);

	Boolean flowIsFinFlowParticipantes(Flow flow);

	Boolean flowIsFinEdoRechazadoFlowParticipantes(Flow flow);

	Doc_detalle chequearForOldVersionVigente(Doc_detalle doc_detalle);

	void create(FlowsHistorico flowHist);

	List findAll_FlowsHistorico();

	List findHistoricooDetalleInFlow(Flow flow);

	List findAll_FlowsHistorico(Doc_maestro doc);

	List findAll_Doc_Relacionados(Doc_maestro doc);

	List findAll_DeQueTipo_Tree(Long queTipo);

	java.util.List findAllDoc_relacionados();

	java.util.List findAll_Roles(Usuario usuario, String search);

	java.util.List findAll_Profesion(Usuario usuario, String search);

	java.util.List findAllDoc_estados(String strBuscar);

	java.util.List findAllDoc_tipos(Usuario usuario, String strBuscar);

	java.util.List findAll_Doc_Relacionados(Doc_maestro doc, String strBuscar);

	java.util.List findAll_DeQueTipo_Tree(Long queTipo, String search);

	java.util.List findAllFlowDelParticipanteFirmados(Usuario participante);

	java.util.List findDetalleTreeHistoricoPersonalInFlow(Doc_detalle doc);

	com.ecological.paper.flows.Flow_Participantes flowFlowOfParticipantes(
			Flow flow, Usuario usuario);

	com.ecological.paper.documentos.Doc_detalle findUltimolDoc_Detalles(
			Doc_maestro doc_maestro);

	java.util.List findAllDoc_maestros(String str, char c);

	java.util.List findAllDoc_maestros(Usuario usuario, Doc_tipo doc_tipo,
			Doc_estado doc_estado);

	com.ecological.paper.documentos.Doc_detalle findUltimolDoc_Detalles(
			Doc_maestro doc_maestro, Doc_estado doc_estado);

	java.util.List queryExecute(String query);

	java.util.List queryExecute(String str, Date fechaparametro);

	java.util.List findAllEmpresas();

	java.util.List findAllAreas(Tree empresa);

	java.util.List findAllTreeHijos(long nodo, String tipoNodo);

	java.util.List findAll_FlowsHistoricoDoc_detalle(Doc_detalle doc);

	void edit(FlowsHistorico flowhist) throws EcologicaExcepciones;

	void destroy(FlowsHistorico flowhist) throws EcologicaExcepciones;

	com.ecological.paper.permisologia.operaciones.Operaciones findOperacion(
			Operaciones op);

	java.util.List findAll_operacionesMenu();

	java.util.List<com.ecological.paper.permisologia.operaciones.Operaciones> findAll_operacionesArbol(
			Tree tree);

	java.util.List<com.ecological.paper.tree.Tree> find(Tree tree,
			Doc_maestro doc);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> findSeguridad_Role_RoleAndTree(
			Seguridad_Role_Lineal seguridadRole) throws EcologicaExcepciones;

	java.lang.String queryDoc_maestrosPublicos();

	java.lang.String queryDetallesVigentePublico(Doc_maestro doc);

	java.util.List findByFlowParticipantesRoleIsNull(Doc_detalle doc,
			boolean swRoleIsNull);

	java.util.List findAllFlow_referencia_role_ByFlow(Flow flow);

	java.util.List findByFlowBad(Doc_detalle doc);

	java.util.List findAllDoc_maestrosCarpetas(Tree tree);

	java.util.List findAllPrincipal();

	void destroy_Menu(Usuario usuario) throws EcologicaExcepciones;

	void create(Menus_Usuarios menus_Usuarios);

	java.util.List<com.ecological.paper.documentos.Doc_maestro> consecutivoseRepite(
			Doc_maestro doc_maestro);

	com.ecological.paper.documentos.Doc_maestro nombreDocseRepite(
			Doc_maestro doc_maestro);

	java.util.List<com.ecological.paper.documentos.Doc_detalle> encontrarDetalles(
			long codigo, String parametro);

	java.util.List queryFecha_Doc_maestro(String str, Date fechaparametro);

	java.util.List<com.ecological.paper.tree.Tree> hayRegistros(Tree t,
			Tree nuevo);

	java.util.List<com.ecological.paper.documentos.Doc_detalle> findDetallesListarDocumewntos(
			Doc_maestro doc, String doc_estadoId, String usuarioId,
			String parametro);

	java.util.List findAllFlow_delete_role_ByFlow(Flow flow);

	java.util.List findByDeleteFlowParticipantes(Flow flow);

	java.util.List findAllHeredaTreeHijos(long nodo);

	java.util.List<com.ecological.paper.documentos.Doc_maestro> findDoc_Maestr_Doc_Tipo(
			Doc_tipo doc_tipo);

	java.util.List<com.ecological.paper.usuario.Usuario> findUsuario_Profesion(
			Profesion profesion);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> buscarTodosLosRolesTreeCompleto(
			Role role);

	void destroyAlldRoles_UsuarioByRole(Role role);

	void destroy_MenuByRole(Role role) throws EcologicaExcepciones;

	void destroyAllFlow_referencia_roleByRole(Role role);

	void deletellFlow_ParticipantesByRole(Role role);

	void deleteSeguridadIndividual2(Usuario usuario, Usuario remplazo);

	void sustituirFlowParticipantesOldByOtro(Usuario viejo, Usuario nuevo);

	void destroy_Menu(Usuario usuario, Usuario remplazo)
			throws EcologicaExcepciones;

	void deleteTreenewDuenio(Usuario old, Usuario nuevo);

	void create(Historico hist);

	void edit(Historico hist);

	void destroy(Historico hist);

	com.ecological.paper.historico.Historico findHistorico(Historico hist);

	java.util.List<com.ecological.paper.historico.Historico> findAllHistorico(
			Historico hist);

	java.util.List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo);

	java.util.List findAllHistoricoUsuarios(Usuario usuario, String str,
			char c, boolean inactivo);

	java.util.List<com.ecological.paper.historico.Historico> findAllHistorico(
			Historico hist, Usuario usuario);

	java.util.List findAllHeredaTreeHijosDeleted(long nodo, boolean inactivo);

	java.util.List<com.ecological.paper.historico.Historico> findAllHistoricoTree(
			Historico hist, Tree tree);

	java.util.List<com.ecological.paper.historico.Historico> findAllHistoricoTreeEliminados(
			Tree tree);

	java.util.List<com.ecological.paper.historico.Historico> findAllHistoricoTreeMoverNodo(
			Tree tree);

	com.ecological.paper.documentos.Doc_detalle findDetalleDocumewntoBusqueda(
			Doc_maestro doc, String doc_estadoId, String usuarioId,
			String parametro);

	java.util.List findAllHeredaTreeHijos(long nodo, long tiponodo);

	java.util.List findAllTreeHijos(long nodo, Usuario usuario);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad> soloMenu(
			Usuario usuario);

	void create(Seguridad_Role_Lineal seg);

	void edit(Seguridad_Role_Lineal seg);

	com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal findSeguridad_Role_Lineal(
			Seguridad_Role_Lineal seg);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(
			Tree tree);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> findAllSeguridad_Role_Lineal(
			Role role);

	void llenarSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal);

	void create(Seguridad_User_Lineal seg);

	void edit(Seguridad_User_Lineal seg);

	void destroy(Seguridad_User_Lineal seg);

	com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal findSeguridad_User_Lineal(
			Seguridad_User_Lineal seg);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal> findAllSeguridad_User_Lineal(
			Tree tree);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> buscarSeguridadIndividual(
			Usuario usuario);

	java.util.List findAllHeredaTreeHijosMenosDocumentos(long nodo);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal> findAllSeguridad_User_Lineal(
			Tree tree, Usuario usuario);

	java.util.List findAllHeredaTreeHijosSoloPrincipal(long nodo);

	boolean existeOperacionSeguridadLineal(Operaciones seg_user,
			Seguridad_Role_Lineal seguridad_Role_Lineal);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> findAllSeguridad_Role_Identificador(
			Role role);

	void destroy_RolEditando(Role rol) throws EcologicaExcepciones;

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> buscarSeguridadIndividual(
			Usuario usuario, Tree tree);

	java.util.List<com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal> buscarTodosLasOperacionesRolSoloMenu(
			Seguridad_Role_Lineal seguridad_Role_Lineal);

	java.util.List<com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios> findRoles_AND_Usuario(
			Role role);

	java.util.List findExisteNombreRaiz(Tree raiz);

	java.util.List findAllHeredaTreeSoloRaizPrinciAreaCargoProceso(long nodo);

	java.util.List queryExecute(String query, int mayor, int menor);

	java.util.List queryExecute(String query, int mayor, int menor,
			List cuantosLista);

	java.util.List findAllHistoricoUsuarios(Usuario usuario, String str,
			char c, boolean inactivo, int maximo, int minimo);

	java.util.List findAllHistoricoUsuarios(Usuario usuario, String str,
			boolean inactivo, int maximo, int minimo);

	java.util.List findAllDoc_maestrosCarpetas(Tree tree, Doc_detalle doc);

	java.util.List<com.ecological.paper.flows.Flow_Participantes> findAllFlowParticipantesByFlow(
			Flow flow);

	com.ecological.paper.documentos.Doc_detalle findDetalleDocumewntoPublico(
			Doc_maestro doc);

	java.util.List<com.ecological.paper.documentos.Doc_maestro> consecutivoseRepite(
			Doc_maestro doc_maestro, Tree tree);

	com.ecological.paper.usuario.Usuario findUsuarioByCargo(Tree cargo);

	void create(Doc_dataPostgres datapostgres);

	void edit(Doc_dataPostgres datapostgres);

	void destroy(Doc_dataPostgres datapostgres);

	com.ecological.paper.documentos.Doc_dataPostgres findDoc_dataPostgres(
			Doc_dataPostgres datapostgres);

	com.ecological.paper.documentos.Doc_dataPostgres findDoc_dataPostgres(
			Doc_detalle doc_detalle);

	List findAllDoc_maestrosNoRelacionados(Tree tree, Doc_detalle doc);

	List findByFlowDocDetalle(Doc_detalle doc);

	Doc_detalle findDoc_detalleVigente(Doc_detalle doc_detalle);

	void create(Doc_consecutivo doc_consecutivo);

	java.util.List findAllDoc_estadosInSearch();

	void destroy(TablaAuxiliar tablaAuxiliar);

	com.ecological.paper.documentos.TablaAuxiliar findTablaAuxiliar(
			TablaAuxiliar tablaAuxiliar);

	void create(TablaAuxiliar tablaAuxiliar);

	java.util.List findAll_HistoricoByUsuario(Usuario usuario);

	com.ecological.paper.documentos.Doc_historicoActivoMaestro findDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro);

	void create(Doc_historicoActivoMaestro doc_historicoActivoMaestro);

	void create(Doc_historicoActivoDetalle doc_historicoActivoDetalle);

	java.util.List<com.ecological.paper.documentos.Doc_historicoActivoDetalle> findAllDoc_historicoActivoDetalle(
			Doc_historicoActivoDetalle doc_historicoActivoDetalle);

	java.util.List<com.ecological.paper.documentos.Doc_historicoActivoMaestro> findAllDoc_historicoActivoMaestro(
			Doc_historicoActivoMaestro doc_historicoActivoMaestro);

	com.software.zonas.Ciudad find_ciudad(Ciudad ciudad);

	void destroy(Ciudad ciudad);

	void edit(Ciudad ciudad);

	void create(Ciudad ciudad);

	com.software.zonas.Estado find_estado(Estado estado);

	void destroy(Estado estado);

	void edit(Estado estado);

	void create(Estado estado);

	com.software.zonas.Pais find_pais(Pais pais);

	void destroy(Pais pais);

	void edit(Pais pais);

	void create(Pais pais);

	com.software.zonas.Ciudad find(Ciudad ciudad);

	com.software.zonas.Estado find(Estado estado);

	com.software.zonas.Pais find(Pais pais);

	java.util.List<com.software.zonas.Pais> find_allPaises();

	java.util.List findAll_Paises(String search);

	java.util.List<com.software.zonas.Estado> find_allEstados();

	java.util.List findAll_Estado(String search);

	java.util.List<com.software.zonas.Ciudad> find_allCiudad();

	java.util.List findAll_Ciudad(String search);

	com.software.zonas.Pais findPais_InEstado(Pais pais);

	com.software.zonas.Estado findEstado_InCiudad(Estado estado);

	java.util.List<com.software.zonas.Estado> findAll_EstadoByPais(Pais pais);

	java.util.List<com.software.zonas.Ciudad> findAll_CiudadByEstado(
			Estado estado);

	java.util.List<com.software.eujovans.clientes.Cliente_EUJ> findAll_CiudadByClienteEUJ(
			Ciudad ciudad, Estado estado, Pais pais, String buscar);

	void create(Cliente_EUJ cliente_EUJ);

	void edit(Cliente_EUJ cliente_EUJ);

	void destroy(Cliente_EUJ cliente_EUJ);

	com.software.eujovans.clientes.Cliente_EUJ find(Cliente_EUJ cliente_EUJ);

	com.software.eujovans.clientes.Cliente_EUJ find_cliente_EUJ(
			Cliente_EUJ cliente_EUJ);

	java.util.List<com.software.eujovans.clientes.Cliente_EUJ> find_allCliente_EUJ();

	void create(Factura factura);

	void edit(Factura factura);

	void destroy(Factura factura);

	com.software.eujovans.clientes.Factura find(Factura factura);

	com.software.eujovans.clientes.Factura find_Factura(Factura factura);

	void create(ValidaVencimiento validaVencimiento);

	void edit(ValidaVencimiento validaVencimiento);

	com.util.ValidaVencimiento find_ValidaVencimiento(
			ValidaVencimiento validaVencimiento);

	java.util.List<com.util.ValidaVencimiento> find_allValidaVencimiento();

	java.util.List<com.software.eujovans.clientes.Factura> find_allFactura(
			Factura factura);

	void create(ConsecutivoEUJ consecutivoEUJ);

	void edit(ConsecutivoEUJ consecutivoEUJ);

	void destroy(ConsecutivoEUJ consecutivoEUJ);

	com.software.eujovans.clientes.ConsecutivoEUJ find(
			ConsecutivoEUJ consecutivoEUJ);

	com.software.eujovans.clientes.ConsecutivoEUJ find_ConsecutivoEUJ(
			ConsecutivoEUJ consecutivoEUJ);

	java.util.List<com.software.eujovans.clientes.ConsecutivoEUJ> find_allConsecutivoEUJ(
			boolean status);

	com.software.eujovans.clientes.Factura find_FacturaNumEntrega(
			Factura factura);

	com.software.zonas.Ciudad findCiudad_InFacturaEUJOVANS(Ciudad ciudad);

	com.software.eujovans.clientes.Factura findClienteEUJOVANS_InFactura(
			Cliente_EUJ destinatario, Cliente_EUJ remitente);

	com.software.eujovans.clientes.Cliente_EUJ find_cliente_EUJ_BYNombre(
			Cliente_EUJ cliente_EUJ);

	void create(Configuracion configuracion);

	void edit(Configuracion configuracion);

	void destroy(Configuracion configuracion);

	com.ecological.configuracion.Configuracion find(Configuracion configuracion);

	com.ecological.configuracion.Configuracion find_configuracion(
			Configuracion configuracion);

	java.util.List<com.ecological.configuracion.Configuracion> find_allConfiguracion();

	void create(DiasHabiles diasHabiles);

	void edit(DiasHabiles diasHabiles);

	void destroy(DiasHabiles diasHabiles);

	com.ecological.dias.habiles.DiasHabiles find(DiasHabiles diasHabiles);

	com.ecological.dias.habiles.DiasHabiles find_DiasHabiles(
			DiasHabiles diasHabiles);

	java.util.List<com.ecological.dias.habiles.DiasHabiles> find_allDiasHabiles(
			Tree empresas);

	void create(DiasFeriadosBean diasHabiles);

	void edit(DiasFeriadosBean diasHabiles);

	void destroy(DiasFeriadosBean diasHabiles);

	com.ecological.dias.feriados.DiasFeriadosBean find(
			DiasFeriadosBean diasHabiles);

	java.util.List<com.ecological.dias.feriados.DiasFeriadosBean> find_allDiasFeriadosBean(
			Usuario usuario);

	com.ecological.dias.feriados.DiasFeriadosBean find_DiasFeriados(
			DiasFeriadosBean diasHabiles);

	void create(FlowControlByUsuarioBean flowControlByUsuarioBean);

	void edit(FlowControlByUsuarioBean flowControlByUsuarioBean);

	void destroy(FlowControlByUsuarioBean flowControlByUsuarioBean);

	com.ecological.control.timeflow.FlowControlByUsuarioBean find(
			FlowControlByUsuarioBean flowControlByUsuarioBean);

	com.ecological.control.timeflow.FlowControlByUsuarioBean find_FlowControlByUsuarioBean(
			FlowControlByUsuarioBean flowControlByUsuarioBean);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allFlowControlByUsuarioBean();

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allFlowControlByFlowBean(
			Flow flow);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allFlowControlByRoleBean(
			Role role, Flow flow);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndRole(
			Flow flow);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allControlTimeByFlowParticipNulos(
			Flow flow);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allFlowControlByHilo();

	com.ecological.dias.habiles.DiasHabiles find_DiasHabilesByDia(
			Usuario usuario, String diaSemana);

	java.util.List<com.ecological.control.timeflow.FlowControlByUsuarioBean> find_allControlTimeByFlowParticipAndFlow(
			Flow_Participantes flow_Participantes);

	java.util.List findAllFlow_ParticipantesByUsuaurio(Usuario usuario);

	java.util.List findAllFlowOfParticipantes(Flow_Participantes f_p);

	java.util.List findAllFlowControlOfParticipantes(Flow_Participantes f_p);

	java.util.List findAllFlowEnviadosByUsuario(Flow f);

	void create(Flow_ParticipantesAttachment objeto);

	void edit(Flow_ParticipantesAttachment objeto);

	void destroy(Flow_ParticipantesAttachment objeto);

	com.ecological.paper.flows.Flow_ParticipantesAttachment find(
			Flow_ParticipantesAttachment objeto);

	com.ecological.paper.flows.Flow_ParticipantesAttachment find_Flow_ParticipantesAttachment(
			Flow_ParticipantesAttachment objeto);

	com.ecological.control.timeflow.FlowControlByUsuarioBean find_FlowControlIsSecuencialGetFechaFirmaUltimo(
			FlowControlByUsuarioBean flowControlByUsuarioBean);

	java.util.List queryExecute(String str, Date fechaDesde, Date fechaHasta);

	java.util.List findAll_FlowsHistoricoUser(Usuario user);

	java.util.List findAll_FlowsHistoricoUserCreados(Usuario user);

	Usuario findLoginOrMailExistaLdap(Usuario usuario);

	List<FlowControlByUsuarioBean> find_allFlowControlByRoleBean(Role role);

	void create(ExtensionFile extensionFile);

	void edit(ExtensionFile extensionFile);

	void destroy(ExtensionFile extensionFile);

	ExtensionFile find(ExtensionFile extensionFile);

	List<ExtensionFile> find_allExtensionFile();

	ExtensionFile tipoContextType(String ext);

	List find_allExtensionFile(String search);

	void create(ExtensionFileHijos extensionFile);

	void edit(ExtensionFileHijos extensionFile);

	void destroy(ExtensionFileHijos extensionFile);

	ExtensionFileHijos find(ExtensionFileHijos extensionFile);

	List<ExtensionFileHijos> find_allExtensionFile(ExtensionFile extensionFile);

	ExtensionFileHijos find_ExtensionFileByExtension(
			ExtensionFileHijos extensionFile);

	Flow createFlow(Flow flow) throws EcologicaExcepciones;

	List findAllEmpresas(Usuario usuario);

	void create(FlowParalelo objeto) throws EcologicaExcepciones;

	Tree findTree(Long idRoot);

	List findAllFlowParalelos(FlowParalelo flowParalelo);

	List calculandoPrecendenciaFlowParalelos(FlowParalelo flowParalelo);

	List findByFlowParaleloAllFlows(FlowParalelo flowParalelo);

	Flow_Participantes flowUltimoParticipante(Flow flow);

	void delete(FlowParalelo objeto) throws EcologicaExcepciones;

	List findParalelos(Usuario usuario);

	void edit(FlowParalelo objeto) throws EcologicaExcepciones;

	List findByFlowParaleloPlantilla(FlowParalelo flowParalelo);

	FlowParalelo find(FlowParalelo objeto) throws EcologicaExcepciones;

	List findByFlowParticipantesPlantilla(Doc_detalle doc);

	List activarFlowParaleloAllFlows(FlowParalelo flowParalelo);

	List findByFlowParticipantesFlowIsActivo(Flow flow);

	Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSVN(
			Flow_ParticipantesAttachment objeto);

	Doc_tipo findTipoDoc(Usuario usuario, Doc_tipo doc_tipo);

	boolean finFlowParaleloForAlterarDocdetalle(Flow flow);

	List<SvnUpload> listUploadAttachmentSvn(Flow flow);

	File fileAttachmentDocDetalle(Doc_detalle doc_detalle) throws SQLException;

	File filetFlowAttachment(Flow_ParticipantesAttachment doc_detalle)
			throws SQLException;

	Flow_ParticipantesAttachment find_Flow_ParticipantesAttachmentSvnPorParticipante(
			Flow_ParticipantesAttachment objeto);

	void create(SvnUrlBase objeto);

	void edit(SvnUrlBase objeto);

	void destroy(SvnUrlBase objeto);

	SvnUrlBase find(SvnUrlBase objeto);

	void create(SvnNombreAplicacion objeto);

	void edit(SvnNombreAplicacion objeto);

	void destroy(SvnNombreAplicacion objeto);

	SvnNombreAplicacion find(SvnNombreAplicacion objeto);

	void create(SvnModulo objeto);

	void edit(SvnModulo objeto);

	void destroy(SvnModulo objeto);

	SvnModulo find(SvnModulo objeto);

	void create(SvnExtension objeto);

	void edit(SvnExtension objeto);

	void destroy(SvnExtension objeto);

	SvnExtension find(SvnExtension objeto);

	List findAllSvnUrlBase(Usuario usuario, String strBuscar);

	List findAllSvnNombreAplicacion(Usuario usuario, String strBuscar);

	List findAllSvnNombreAplicacion(SvnUrlBase objeto);

	List findAllSvnModulo(SvnTipoAmbiente svnTipoAmbiente);

	List findAllSvnExtension(String strBuscar);

	void create(SvnTipoAmbiente objeto);

	void edit(SvnTipoAmbiente objeto);

	void destroy(SvnTipoAmbiente objeto);

	SvnTipoAmbiente find(SvnTipoAmbiente objeto);

	List findAllSvnTipoAmbiente(Usuario usuario, String strBuscar);

	List findAllSvnTipoAmbiente(SvnNombreAplicacion svnNombreAplicacion);

	void sustituirFlowParticipantesOldByOtroFlows(
			Flow_Participantes flow_Participante, Usuario nuevo,
			String comentario);

	List findAll_FlowParaleloUser(Usuario user);

	List findAll_FlowParaleloUserCreados(Usuario user);

	List findAllTipoDeFirmaWithParticipanteAnterior(Flow flow);

	public Flow_Participantes devolucionFirmaFlow(
			Flow_Participantes flow_Participante);

	void findFlowParticipantesSinFirmar(Flow flow);

	void create(SvnRutasRelativasFiles objeto);

	void edit(SvnRutasRelativasFiles objeto);

	void destroy(SvnRutasRelativasFiles objeto);

	SvnRutasRelativasFiles find(SvnRutasRelativasFiles objeto);

	List findAllSvnRutasRelativasFiles(Doc_maestro doc_maestro);

	List findAllSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment);

	SvnRutasRelativasFiles findSvnRutasRelativasFiles(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment,
			String archivo);

	SvnRutasRelativasFiles findSvnRutasRelativasFiles(Doc_maestro doc_maestro,
			String archivo);

	List findByFlowParticipantesSinRolForEditar(Flow flow);

	List findByFlowParaleloAllFlowsForPadreFlow(FlowParalelo flowParalelo);

	List findAllFlowParalelos(Flow flow);

	void findFlowParticipantesFirmadosAndFlowIspendiente(Flow flow);

	Flow_Participantes devolucionFirmaFlowEnParalelo(
			Flow_Participantes flow_Participante);

	void devolvemos(Flow_Participantes flow_Participante,
			Flow_Participantes devolver);

	List<Usuario> mandarMailsUsuarios(Flow flow,
			Flow_Participantes flow_Participante);

	List<Usuario> mandarMailsUsuarios(Flow flow);

	Flow flowQuePrecede(Flow_Participantes flow_Participante);

	Flow_Participantes precederSimple(Flow_Participantes flow_Participante);

	FlowParalelo findPlantillaDoc_DetallePlantillaInFlowParalelo(
			FlowParalelo objeto);

	Tree findEnNivelArribaTreeDeAcuerdoSuTipo(Tree tree, long tiponodo);

	void create(Solicitudimpresion objeto);

	void edit(Solicitudimpresion objeto);

	void destroy(Solicitudimpresion objeto);

	Solicitudimpresion find(Solicitudimpresion objeto);

	void create(SolicitudImpPart objeto);

	void edit(SolicitudImpPart objeto);

	void destroy(SolicitudImpPart objeto);

	SolicitudImpPart find(SolicitudImpPart objeto);

	Solicitudimpresion findSolicitudimpresionByFlow(
			Solicitudimpresion solicitudimpresion);

	Solicitudimpresion findSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion);

	List<SolicitudImpPart> solicitudImpPartsCancelar(SolicitudImpPart objeto);

	List<Solicitudimpresion> findAllSolicitudimpresionByDocDetalle(
			Solicitudimpresion solicitudimpresion);

	List<SolicitudImpPart> findAllsolicitudImpParts(
			Solicitudimpresion solicitudimpresion);

	Solicitudimpresion findSolicitudimpresionByFlowDelete(
			Solicitudimpresion solicitudimpresion);

	void putPublicoDocumento(Doc_detalle doc_detalle,
			List<Role> roleSeleccionados, Usuario publicador)
			throws EcologicaExcepciones;

	List findAllRolesOnlyViewDocPublicados(Doc_detalle doc_detalle);

	RolesOnlyViewDocPublicados findAllRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados);

	Boolean usuarioRolesOnlyViewDocPublicados(
			RolesOnlyViewDocPublicados rolesOnlyViewDocPublicados);

	void notificarPorMailPublicoExpiro(Doc_detalle doc_detalle);

	void create(PublicadosUsuComent objeto);

	PublicadosUsuComent findOnePublicadosUsuComent(
			PublicadosUsuComent publicadosUsuComent);

	void edit(PublicadosUsuComent objeto);

	List<PublicadosUsuComent> findAllPublicadosUsuComentLst(
			PublicadosUsuComent publicadosUsuComent);

	List<PublicadosUsuComent> findAllPublicadosVersionesUsuComentLst(
			PublicadosUsuComent publicadosUsuComent);

	void create(AreaDocumentos objeto);

	void edit(AreaDocumentos objeto);

	void destroy(AreaDocumentos objeto);

	AreaDocumentos find(AreaDocumentos pk);

	AreaDocumentos findAreaDocumentos(String id);

	List findAllAreaDocumentos(Usuario usuario, String search);

	List findAllAreaDocumentos(Usuario usuario);

	Boolean findExisteAreaDocumentosDocDetallesFlowParalelo(
			AreaDocumentos areaDocumentos);
	
	List findByFlowParticipantesOrigenFlow(Flow flow);
	
	List findByFlowByOrigen(Doc_detalle doc);
	Boolean findExistePlantillaImpresionFlow(FlowParalelo objeto)
	throws EcologicaExcepciones;
	List findByFlowInFlow(Doc_detalle doc);
	List<SolicitudImpPart> findAllsolicitudImpParts(
			SolicitudImpPart solicitudImpPart);
	List<SolicitudImpPart> findsolicitudImpPartsToSendToImprimir(
			SolicitudImpPart solicitudImpPart);
	FlowParalelo findExistePlantillaImpresionFlowObjeto(FlowParalelo objeto)
	throws EcologicaExcepciones;
	 List<SolicitudImpPart> findAllsolicitudImpPartsDocDetalle(
				SolicitudImpPart solicitudImpPart) ;
	 List<SolicitudImpPart> findAllsolicitudImpPartsEmpresa(
				SolicitudImpPart solicitudImpPart);
	 List findByFlowParticipantes(Flow_Participantes flow_Participantes) ;
	 List findAllFlowParalelosDelSistema(FlowParalelo flowParalelo);
	 String findAllExtensionWithHijos();
	 void create(RolesOnlyViewDocPublicados objeto);
		List findAllScannerDoc(Usuario usuario);
		void destroy(ScannerDoc objeto);
		Map soloMenuOptimizado(Usuario usuario,Map seguridadIndividualPertenesco);
		Map buscarSeguridadIndividualOptimizado(Usuario usuario,
				Map seguridadIndividualPertenesco);
		Map buscarSeguridadIndividualOptimizado(
				Usuario usuario, Tree tree,Map user_seguridad);
		void  heredarRolePermiso(Seguridad_Role_Lineal seguridad_Role_Lineal,boolean swElimina,long tipoNodo) throws EcologicaExcepciones;
		void seguridadForRolesByTree(List<Role> roleLst, Tree tree, boolean heredaSeguridad) ;
		String seguridadArbol(Usuario user_logueado,
				Seguridad seguridadTree,Map user_seguridad );
		public void llenarDatosRole(Role role,List<Operaciones> visibleItems);
		void asignamosPermiso(Tree registro,Usuario user_logueado,Tree treeNodoActual);
		void firmarFlows(Flow_Participantes flow_Participante,
				Flow_ParticipantesAttachment flow_ParticipantesAttachment)throws EcologicaExcepciones;
		public Usuario findUsuarioLoginEditar(String login, Usuario usucreador)
		throws UsuarioNoEncontrado, UsuarioMultiples ;
		public Usuario findUsuarioByMail(Usuario usuario)
				throws UsuarioNoEncontrado, UsuarioMultiples ;
		public List findByFlowParticipantesByRevOrAprob(Doc_detalle doc,Doc_estado doc_estado) ;
		public List findAllFlowParticipantesPendientesDoc_Detalle(Usuario participante,Doc_detalle doc_detalle) ;
		public List<ExtensionFile> find_ExtensionFileByExtAndMime(ExtensionFile extensionFile) ;
		public Role findRoleADefault(Role role) throws RoleMultiples, RoleNoEncontrado ;
		public List findByFlowOnlyNotificacionRole(Flow flow);
		public void destroy(FlowOnlyNotificacionRole flow) throws EcologicaExcepciones ;
		public void create(FlowOnlyNotificacionRole flow) throws EcologicaExcepciones ;
		public List findAllFlowOnlyNotificacionRoleByFlow(Flow flow) ;
}
