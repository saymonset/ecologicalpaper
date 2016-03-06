/*
 * Usuario.java
 *
 * Created on July 2, 2007, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.usuario;

import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.PublicadosUsuComent;
import com.ecological.paper.documentos.SolicitudImpPart;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.historico.Hist_usuarios;
import com.ecological.paper.historico.Historico;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.tree.Tree;
import com.software.eujovans.clientes.Factura;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Usuario
 * 
 * @author simon
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
	@TableGenerator(name = "Usuario_Id_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "USUARIO_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Usuario_Id_Gen")
	private Long id;
	@Transient
	private SistemaExpiraNumLicencias sistemaExpiraNumLicencias;
	@Transient
	String idsession;
	@Transient
	String idsessionObsoleta;
	@Transient
	boolean swMostrar;
	
	@Transient
	private String strBuscar;

	@Transient
	private boolean cancelar;
	@Transient
	private SolicitudImpPart solicitudImpParts;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	private boolean status;

	@Column(name = "APELLIDO")
	private String apellido;

	@Column(name = "LOGIN")
	private String login;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "MAIL_PRINCIPAL")
	private String mail_principal;

	@Column(name = "ULTIMACONEXION")
	private String ultimaConexion;

	@Column(name = "FECHA_CREADO")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha_creado;

	@Transient
	private String fecha_creadotxt;

	@Transient
	private boolean swSiFirmaron;

	@Transient
	private String subFlow;
	
	@Transient
	private Boolean swEstaInFlow;

	@Transient
	private String comentarioMailFlujo;
	// ESTE doc_detall ES PARA OBTENER EL TIPO DE DOCUMENTO DEL FLUJOPARALELO
	@Transient
	private Doc_detalle doc_detall;

	@Column(name = "FECHA_CADUCA")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha_caduca;

	@Transient
	private boolean delete;

	@Transient
	private Tree treeNodoActualCache;
	
	private String anchoLeftTree;

	

	// si es eliminado logicamente, hitorico es true
	@Transient
	private boolean historico;

	@ManyToOne
	@JoinColumn(name = "PROFESION")
	private Profesion profesion;

	// @Column(nullable = false, length = 50, unique = true
	@Column(name = "DIRECCION", length = 4000)
	private String direccion;

	@Column(name = "TELEFONO_OFIC")
	private String telefono_ofic;

	@Column(name = "TELEFONO_CASA")
	private String telefono_casa;

	@Column(name = "TELEFONO_CEL")
	private String telefono_cel;

	@ManyToOne
	@JoinColumn(name = "CARGO")
	private Tree cargo;

	@ManyToOne
	@JoinColumn(name = "AREA")
	private Tree area;

	@ManyToOne
	@JoinColumn(name = "PRINCIPAL")
	private Tree principal;

	@ManyToOne
	@JoinColumn(name = "EMPRESA")
	private Tree empresa;
	@Transient
	private File imgEmpresas;

	@OneToMany(mappedBy = "chofer")
	private Collection<Factura> facturas = new ArrayList<Factura>();

	@OneToMany(mappedBy = "usuario")
	private Collection<Roles_Usuarios> role_user = new ArrayList<Roles_Usuarios>();

	// para historicos
	@OneToMany(mappedBy = "histusuario")
	private Collection<Hist_usuarios> historicos = new ArrayList<Hist_usuarios>();

	@OneToMany(mappedBy = "usuario")
	private Collection<Seguridad_User> seguridad_User = new ArrayList<Seguridad_User>();

	@OneToMany(mappedBy = "duenio")
	private Collection<Doc_detalle> doc_detalle = new ArrayList<Doc_detalle>();

	@OneToMany(mappedBy = "modificadoPor")
	private Collection<Doc_detalle> modificadoPor = new ArrayList<Doc_detalle>();

	@OneToMany(mappedBy = "usuario_creador")
	private Collection<Doc_maestro> usuariosdoc_creador = new ArrayList<Doc_maestro>();

	@OneToMany(mappedBy = "publicador")
	private Collection<Doc_detalle> publicador = new ArrayList<Doc_detalle>();

	@OneToMany(mappedBy = "duenio")
	private Collection<Flow> dueniosFlow = new ArrayList<Flow>();

	@OneToMany(mappedBy = "participante")
	private Collection<Flow_Participantes> participantesFlow = new ArrayList<Flow_Participantes>();

	@OneToMany(mappedBy = "usuario")
	private Collection<Menus_Usuarios> menu_usuario = new ArrayList<Menus_Usuarios>();

	@OneToMany(mappedBy = "usuario_accion")
	private Collection<Historico> usuario_accion = new ArrayList<Historico>();

	@OneToMany(mappedBy = "usuario_anterior")
	private Collection<Historico> usuario_anterior = new ArrayList<Historico>();

	@OneToMany(mappedBy = "usuario_new")
	private Collection<Historico> usuario_new = new ArrayList<Historico>();

	@OneToMany(mappedBy = "usuario")
	private Collection<Seguridad_User_Lineal> seguridad_User_Lineal = new ArrayList<Seguridad_User_Lineal>();

	@OneToMany(mappedBy = "usuario")
	private Collection<Doc_historicoActivoMaestro> doc_historicoActivoMaestros = new ArrayList<Doc_historicoActivoMaestro>();

	@OneToMany(mappedBy = "usuario_creador")
	private Collection<Tree> usuario_creadors = new ArrayList<Tree>();

	@OneToMany(mappedBy = "usuario")
	private Collection<SubVersionUsuario> usuarioSubversion = new ArrayList<SubVersionUsuario>();

	@OneToMany(mappedBy = "usuario")
	private Collection<FlowParalelo> flowParalelos = new ArrayList<FlowParalelo>();

	@OneToMany(mappedBy = "solicitante")
	private Collection<Solicitudimpresion> solicitantes = new ArrayList<Solicitudimpresion>();

	@OneToMany(mappedBy = "usuario")
	private Collection<SolicitudImpPart> solicitudImpPartsLst = new ArrayList<SolicitudImpPart>();

	@OneToMany(mappedBy = "usuario")
	private Collection<PublicadosUsuComent> publicadosUsuComentLst = new ArrayList<PublicadosUsuComent>();

	public Usuario(Long codigo, String nombre, String apellido,
			String password, Date fecha_creado, Date fecha_caduca,
			boolean status, Profesion profesion, String direccion,
			String telefono_ofic, String telefono_casa, String telefono_cel,
			Tree cargo, Blob foto) {
		this.id = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.fecha_creado = fecha_creado;
		this.fecha_caduca = fecha_caduca;
		this.status = status;
		this.profesion = profesion;
		this.direccion = direccion;
		this.telefono_ofic = telefono_ofic;
		this.telefono_casa = telefono_casa;
		this.telefono_cel = telefono_cel;
		this.cargo = cargo;
		// this.foto=foto;

	}

	/** Creates a new instance of Usuario */
	public Usuario() {
	}

	public String toString() {
		String str ="";
		try {
			str = this.apellido + "  " + this.nombre;
			if (this.cargo != null) {
				str += " [" + cargo.getNombre() + "] (" + empresa.getNombre() + ")";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return str.toString();
	}

	/**
	 * Gets the id of this Usuario.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id of this Usuario to the specified value.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns a hash code value for the object. This implementation computes a
	 * hash code value based on the id fields in this object.
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.getId() != null ? this.getId().hashCode() : 0);
		return hash;
	}

	/**
	 * Determines whether another object is equal to this Usuario. The result is
	 * <code>true</code> if and only if the argument is not null and is a
	 * Usuario object that has the same id field values as this object.
	 * 
	 * @param object
	 *            the reference object with which to compare
	 * @return <code>true</code> if this object is the same as the argument;
	 *         <code>false</code> otherwise.
	 */

	/**
	 * Returns a string representation of the object. This implementation
	 * constructs that representation based on the id fields.
	 * 
	 * @return a string representation of the object.
	 */

	public String getNombre() {

		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.util.Date getFecha_creado() {
		return fecha_creado;
	}

	public void setFecha_creado(java.util.Date fecha_creado) {
		this.fecha_creado = fecha_creado;
	}

	public java.util.Date getFecha_caduca() {
		return fecha_caduca;
	}

	public void setFecha_caduca(java.util.Date fecha_caduca) {
		this.fecha_caduca = fecha_caduca;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono_ofic() {
		return telefono_ofic;
	}

	public void setTelefono_ofic(String telefono_ofic) {
		this.telefono_ofic = telefono_ofic;
	}

	public String getTelefono_casa() {
		return telefono_casa;
	}

	public void setTelefono_casa(String telefono_casa) {
		this.telefono_casa = telefono_casa;
	}

	public String getTelefono_cel() {
		return telefono_cel;
	}

	public void setTelefono_cel(String telefono_cel) {
		this.telefono_cel = telefono_cel;
	}

	public Tree getCargo() {
		return cargo;
	}

	public void setCargo(Tree cargo) {
		this.cargo = cargo;
	}

	public Profesion getProfesion() {
		return profesion;
	}

	public void setProfesion(Profesion profesion) {
		this.profesion = profesion;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/*
	 * public Blob getFoto() { return foto; }
	 * 
	 * public void setFoto(Blob foto) { this.foto = foto; }
	 */

	public Collection<Roles_Usuarios> getRole_user() {
		return role_user;
	}

	public void setRole_user(Collection<Roles_Usuarios> role_user) {
		this.role_user = role_user;
	}

	public boolean equals(Object objeto) {
		try {
			if (objeto != null && objeto instanceof Usuario) {
				Usuario p = (Usuario) objeto;
				/*
				 * String k1 =p.getCodigo()+""; String k2=this.codigo+"";
				 */
				// if (k1.trim().equalsIgnoreCase(k2.trim())){
				if (p != null && (p.getId() - this.id) == 0) {
					return true;
				} else
					return false;

			} else
				return false;

		} catch (Exception e) {
			return false;
		}

	}

	public Collection<Seguridad_User> getSeguridad_User() {
		return seguridad_User;
	}

	public void setSeguridad_User(Collection<Seguridad_User> seguridad_User) {
		this.seguridad_User = seguridad_User;
	}

	public Collection<Doc_detalle> getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Collection<Doc_detalle> doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

	public Collection<Doc_maestro> getUsuariosdoc_creador() {
		return usuariosdoc_creador;
	}

	public void setUsuariosdoc_creador(
			Collection<Doc_maestro> usuariosdoc_creador) {
		this.usuariosdoc_creador = usuariosdoc_creador;
	}

	public Collection<Flow> getDueniosFlow() {
		return dueniosFlow;
	}

	public void setDueniosFlow(Collection<Flow> dueniosFlow) {
		this.dueniosFlow = dueniosFlow;
	}

	public Collection<Flow_Participantes> getParticipantesFlow() {
		return participantesFlow;
	}

	public void setParticipantesFlow(
			Collection<Flow_Participantes> participantesFlow) {
		this.participantesFlow = participantesFlow;
	}

	public String getUltimaConexion() {
		return ultimaConexion;
	}

	public void setUltimaConexion(String ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public String getMail_principal() {
		return mail_principal;
	}

	public void setMail_principal(String mail_principal) {
		this.mail_principal = mail_principal;
	}

	public Collection<Menus_Usuarios> getMenu_usuario() {
		return menu_usuario;
	}

	public void setMenu_usuario(Collection<Menus_Usuarios> menu_usuario) {
		this.menu_usuario = menu_usuario;
	}

	public Collection<Doc_detalle> getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Collection<Doc_detalle> modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Tree getArea() {
		return area;
	}

	public void setArea(Tree area) {
		this.area = area;
	}

	public Tree getPrincipal() {
		return principal;
	}

	public void setPrincipal(Tree principal) {
		this.principal = principal;
	}

	public Tree getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Tree empresa) {
		this.empresa = empresa;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	public Collection<Historico> getUsuario_accion() {
		return usuario_accion;
	}

	public void setUsuario_accion(Collection<Historico> usuario_accion) {
		this.usuario_accion = usuario_accion;
	}

	public Collection<Historico> getUsuario_anterior() {
		return usuario_anterior;
	}

	public void setUsuario_anterior(Collection<Historico> usuario_anterior) {
		this.usuario_anterior = usuario_anterior;
	}

	public Collection<Historico> getUsuario_new() {
		return usuario_new;
	}

	public void setUsuario_new(Collection<Historico> usuario_new) {
		this.usuario_new = usuario_new;
	}

	public String getFecha_creadotxt() {
		return fecha_creadotxt;
	}

	public void setFecha_creadotxt(String fecha_creadotxt) {
		this.fecha_creadotxt = fecha_creadotxt;
	}

	public String getIdsession() {
		return idsession;
	}

	public void setIdsession(String idsession) {
		this.idsession = idsession;
	}

	public String getIdsessionObsoleta() {
		return idsessionObsoleta;
	}

	public void setIdsessionObsoleta(String idsessionObsoleta) {
		this.idsessionObsoleta = idsessionObsoleta;
	}

	public String getComentarioMailFlujo() {
		return comentarioMailFlujo;
	}

	public void setComentarioMailFlujo(String comentarioMailFlujo) {
		this.comentarioMailFlujo = comentarioMailFlujo;
	}

	public Collection<Doc_historicoActivoMaestro> getDoc_historicoActivoMaestros() {
		return doc_historicoActivoMaestros;
	}

	public void setDoc_historicoActivoMaestros(
			Collection<Doc_historicoActivoMaestro> doc_historicoActivoMaestros) {
		this.doc_historicoActivoMaestros = doc_historicoActivoMaestros;
	}

	public Collection<Tree> getUsuario_creadors() {
		return usuario_creadors;
	}

	public void setUsuario_creadors(Collection<Tree> usuario_creadors) {
		this.usuario_creadors = usuario_creadors;
	}

	public Collection<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(Collection<Factura> facturas) {
		this.facturas = facturas;
	}

	void setSistemaExpiraNumLicencias(
			SistemaExpiraNumLicencias sistemaExpiraNumLicencias) {
		this.sistemaExpiraNumLicencias = sistemaExpiraNumLicencias;
	}

	public Collection<Seguridad_User_Lineal> getSeguridad_User_Lineal() {
		return seguridad_User_Lineal;
	}

	public void setSeguridad_User_Lineal(
			Collection<Seguridad_User_Lineal> seguridad_User_Lineal) {
		this.seguridad_User_Lineal = seguridad_User_Lineal;
	}

	public Collection<SubVersionUsuario> getUsuarioSubversion() {
		return usuarioSubversion;
	}

	public void setUsuarioSubversion(
			Collection<SubVersionUsuario> usuarioSubversion) {
		this.usuarioSubversion = usuarioSubversion;
	}

	SistemaExpiraNumLicencias getSistemaExpiraNumLicencias() {
		return sistemaExpiraNumLicencias;
	}

	public Collection<FlowParalelo> getFlowParalelos() {
		return flowParalelos;
	}

	public void setFlowParalelos(Collection<FlowParalelo> flowParalelos) {
		this.flowParalelos = flowParalelos;
	}

	public String getSubFlow() {
		return subFlow;
	}

	public void setSubFlow(String subFlow) {
		this.subFlow = subFlow;
	}

	public Doc_detalle getDoc_detall() {
		return doc_detall;
	}

	public void setDoc_detall(Doc_detalle doc_detall) {
		this.doc_detall = doc_detall;
	}

	public Collection<Hist_usuarios> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(Collection<Hist_usuarios> historicos) {
		this.historicos = historicos;
	}

	public Collection<Solicitudimpresion> getSolicitantes() {
		return solicitantes;
	}

	public void setSolicitantes(Collection<Solicitudimpresion> solicitantes) {
		this.solicitantes = solicitantes;
	}

	public boolean isCancelar() {
		return cancelar;
	}

	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}

	public Collection<SolicitudImpPart> getsolicitudImpPartsLst() {
		return solicitudImpPartsLst;
	}

	public void setsolicitudImpPartsLst(
			Collection<SolicitudImpPart> solicitudImpPartsLst) {
		this.solicitudImpPartsLst = solicitudImpPartsLst;
	}

	public SolicitudImpPart getsolicitudImpParts() {
		return solicitudImpParts;
	}

	public void setsolicitudImpParts(SolicitudImpPart solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}

	public boolean isSwMostrar() {
		return swMostrar;
	}

	public void setSwMostrar(boolean swMostrar) {
		this.swMostrar = swMostrar;
	}

	public Collection<Doc_detalle> getPublicador() {
		return publicador;
	}

	public void setPublicador(Collection<Doc_detalle> publicador) {
		this.publicador = publicador;
	}

	public SolicitudImpPart getSolicitudImpParts() {
		return solicitudImpParts;
	}

	public void setSolicitudImpParts(SolicitudImpPart solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}

	public Collection<SolicitudImpPart> getSolicitudImpPartsLst() {
		return solicitudImpPartsLst;
	}

	public void setSolicitudImpPartsLst(
			Collection<SolicitudImpPart> solicitudImpPartsLst) {
		this.solicitudImpPartsLst = solicitudImpPartsLst;
	}

	public Collection<PublicadosUsuComent> getPublicadosUsuComentLst() {
		return publicadosUsuComentLst;
	}

	public void setPublicadosUsuComentLst(
			Collection<PublicadosUsuComent> publicadosUsuComentLst) {
		this.publicadosUsuComentLst = publicadosUsuComentLst;
	}

	public boolean isSwSiFirmaron() {
		return swSiFirmaron;
	}

	public void setSwSiFirmaron(boolean swSiFirmaron) {
		this.swSiFirmaron = swSiFirmaron;
	}

	public String getStrBuscar() {
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		this.strBuscar = strBuscar;
	}

	public Tree getTreeNodoActualCache() {
		return treeNodoActualCache;
	}

	public void setTreeNodoActualCache(Tree treeNodoActualCache) {
		this.treeNodoActualCache = treeNodoActualCache;
	}

	public String getAnchoLeftTree() {
		return anchoLeftTree;
	}

	public void setAnchoLeftTree(String anchoLeftTree) {
		this.anchoLeftTree = anchoLeftTree;
	}

	public File getImgEmpresas() {
		return imgEmpresas;
	}

	public void setImgEmpresas(File imgEmpresas) {
		this.imgEmpresas = imgEmpresas;
	}

	public Boolean getSwEstaInFlow() {
		return swEstaInFlow;
	}

	public void setSwEstaInFlow(Boolean swEstaInFlow) {
		this.swEstaInFlow = swEstaInFlow;
	}

}
