/*
 * Flow.java
 *
 * Created on August 1, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.historico.Historico;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Flow
 * 
 * @author simon
 */
@Entity
public class Flow implements Serializable {
	@TableGenerator(name = "Flow_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "FLOW_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Flow_Gen")
	private Long codigo;
	private boolean status;
	private Long origen;

	@Column(name = "COMENTARIOS", length = 4000)
	private String comentarios;

	private boolean lectores;

	private boolean secuencial;

	private boolean condicional;
	// si es plantilla o noi para realizar flujos..
	private boolean plantilla;

	// si es plantilla o noi para realizar flujos..
	// private boolean noAlteraVersion;

	// si se subio el file svn pŕincipal
	private boolean swSVNUpload;

	private boolean notificacionMail = true;

	@Transient
	private boolean swEdit;
	@Transient
	private List<Usuario>participantesPlantila;
	@Transient
	private List<Flow>participantesGruposPlantila;
	@Transient
	private List<Flow>participantesGruposPlantilaNotificacion;
	
	@Transient
	private Role grupo;
	@Transient
	private Usuario participante;
	@Transient
	private String permiso;

	@Transient
	private String firmaPrincipalStr;
	@Transient
	private Flow_Participantes flow_P;

	private String tipoFlujo;

	@Transient
	private String tipoFlujoAux;

	@ManyToOne
	@JoinColumn(name = "ESTADO")
	private Doc_estado estado;

	@ManyToOne
	@JoinColumn(name = "DOC_DETALLE")
	private Doc_detalle doc_detalle;

	@ManyToOne
	@JoinColumn(name = "DUENIO")
	private Usuario duenio;

	@Column(name = "FECHA_CREADO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_creado;

	@Transient
	private String fecha_creadoStr;

	@Column(name = "FECHA_CREADO_ONLY")
	@Temporal(TemporalType.DATE)
	private java.util.Date fechaCreadoOnly;

	@ManyToOne
	@JoinColumn(name = "flowParalelo")
	private FlowParalelo flowParalelo;

	private String nombredelflujo;
	@Transient
	private boolean swPrecedencia;
	@Transient
	private String usuariosParticipantes;
	@Transient
	private String gruposParticipantes;
	private String precedencia;
	@Transient
	private List<Flow_Participantes> participantesFlowsDetalle;
	// private boolean swRespAutomatica;
	@ManyToOne
	@JoinColumn(name = "flow_Participantes")
	private Flow_Participantes flow_Participantes;

	@OneToMany(mappedBy = "flow")
	private Collection<FlowParalelo> flowParalelos = new ArrayList<FlowParalelo>();

	@OneToMany(mappedBy = "flow")
	private Collection<Flow_Participantes> participantesFlows = new ArrayList<Flow_Participantes>();

	@OneToMany(mappedBy = "flow")
	private Collection<Flow_referencia_role> roleFlows = new ArrayList<Flow_referencia_role>();

	@OneToMany(mappedBy = "flow")
	private Collection<FlowsHistorico> historicoFlows = new ArrayList<FlowsHistorico>();

	@OneToMany(mappedBy = "flow")
	private Collection<Historico> flowUsuario = new ArrayList<Historico>();

	@OneToMany(mappedBy = "flow")
	private Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean = new ArrayList<FlowControlByUsuarioBean>();

	@OneToMany(mappedBy = "flow")
	private Collection<Solicitudimpresion> solicitudimpresions = new ArrayList<Solicitudimpresion>();

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getOrigen() {
		return origen;
	}

	public void setOrigen(Long origen) {
		this.origen = origen;
	}

	public Doc_estado getEstado() {
		return estado;
	}

	public void setEstado(Doc_estado estado) {
		this.estado = estado;
	}

	public Doc_detalle getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

	public Usuario getDuenio() {
		return duenio;
	}

	public void setDuenio(Usuario duenio) {
		this.duenio = duenio;
	}

	public Date getFecha_creado() {
		return fecha_creado;
	}

	public void setFecha_creado(Date fecha_creado) {
		this.fecha_creado = fecha_creado;
	}

	public boolean equals(Object objeto) {
		if (objeto != null && objeto instanceof Flow) {
			Flow p = (Flow) objeto;
			if (p.getCodigo() - this.codigo == 0) {
				return true;
			} else
				return false;

		} else
			return false;

	}

	public Collection<Flow_Participantes> getParticipantesFlows() {
		return participantesFlows;
	}

	public void setParticipantesFlows(
			Collection<Flow_Participantes> participantesFlows) {
		this.participantesFlows = participantesFlows;
	}

	public Collection<Flow_referencia_role> getRoleFlows() {
		return roleFlows;
	}

	public void setRoleFlows(Collection<Flow_referencia_role> roleFlows) {
		this.roleFlows = roleFlows;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public boolean isSecuencial() {
		return secuencial;
	}

	public void setSecuencial(boolean secuencial) {
		this.secuencial = secuencial;
	}

	public boolean isCondicional() {
		return condicional;
	}

	public void setCondicional(boolean condicional) {
		this.condicional = condicional;
	}

	public boolean isNotificacionMail() {
		return notificacionMail;
	}

	public void setNotificacionMail(boolean notificacionMail) {
		this.notificacionMail = notificacionMail;
	}

	public String getTipoFlujo() {
		return tipoFlujo;
	}

	public void setTipoFlujo(String tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}

	public Collection<FlowsHistorico> getHistoricoFlows() {
		return historicoFlows;
	}

	public void setHistoricoFlows(Collection<FlowsHistorico> historicoFlows) {
		this.historicoFlows = historicoFlows;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Collection<Historico> getFlowUsuario() {
		return flowUsuario;
	}

	public void setFlowUsuario(Collection<Historico> flowUsuario) {
		this.flowUsuario = flowUsuario;
	}

	public String getFirmaPrincipalStr() {
		return firmaPrincipalStr;
	}

	public void setFirmaPrincipalStr(String firmaPrincipalStr) {
		this.firmaPrincipalStr = firmaPrincipalStr;
	}

	public Collection<FlowControlByUsuarioBean> getFlowControlByUsuarioBean() {
		return flowControlByUsuarioBean;
	}

	public void setFlowControlByUsuarioBean(
			Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean) {
		this.flowControlByUsuarioBean = flowControlByUsuarioBean;
	}

	public java.util.Date getFechaCreadoOnly() {
		return fechaCreadoOnly;
	}

	public void setFechaCreadoOnly(java.util.Date fechaCreadoOnly) {
		this.fechaCreadoOnly = fechaCreadoOnly;
	}

	public FlowParalelo getFlowParalelo() {
		return flowParalelo;
	}

	public void setFlowParalelo(FlowParalelo flowParalelo) {
		this.flowParalelo = flowParalelo;
	}

	public Collection<FlowParalelo> getFlowParalelos() {
		return flowParalelos;
	}

	public void setFlowParalelos(Collection<FlowParalelo> flowParalelos) {
		this.flowParalelos = flowParalelos;
	}

	public String getNombredelflujo() {
		return nombredelflujo;
	}

	public void setNombredelflujo(String nombredelflujo) {
		this.nombredelflujo = nombredelflujo;
	}

	public String getPrecedencia() {
		return precedencia;
	}

	public void setPrecedencia(String precedencia) {
		this.precedencia = precedencia;
	}

	public Flow_Participantes getFlow_Participantes() {
		return flow_Participantes;
	}

	public void setFlow_Participantes(Flow_Participantes flow_Participantes) {
		this.flow_Participantes = flow_Participantes;
	}

	public boolean isSwPrecedencia() {
		return swPrecedencia;
	}

	public void setSwPrecedencia(boolean swPrecedencia) {
		this.swPrecedencia = swPrecedencia;
	}

	public boolean isPlantilla() {
		return plantilla;
	}

	public void setPlantilla(boolean plantilla) {
		this.plantilla = plantilla;
	}

	public boolean isSwSVNUpload() {
		return swSVNUpload;
	}

	public void setSwSVNUpload(boolean swSVNUpload) {
		this.swSVNUpload = swSVNUpload;
	}

	public String getUsuariosParticipantes() {
		return usuariosParticipantes;
	}

	public void setUsuariosParticipantes(String usuariosParticipantes) {
		this.usuariosParticipantes = usuariosParticipantes;
	}

	public String getGruposParticipantes() {
		return gruposParticipantes;
	}

	public void setGruposParticipantes(String gruposParticipantes) {
		this.gruposParticipantes = gruposParticipantes;
	}

	public Flow_Participantes getFlow_P() {
		return flow_P;
	}

	public void setFlow_P(Flow_Participantes flow_P) {
		this.flow_P = flow_P;
	}

	public boolean isLectores() {
		return lectores;
	}

	public void setLectores(boolean lectores) {
		this.lectores = lectores;
	}

	public Collection<Solicitudimpresion> getSolicitudimpresions() {
		return solicitudimpresions;
	}

	public void setSolicitudimpresions(
			Collection<Solicitudimpresion> solicitudimpresions) {
		this.solicitudimpresions = solicitudimpresions;
	}

	public boolean isSwEdit() {
		return swEdit;
	}

	public void setSwEdit(boolean swEdit) {
		this.swEdit = swEdit;
	}

	public String getTipoFlujoAux() {
		return tipoFlujoAux;
	}

	public void setTipoFlujoAux(String tipoFlujoAux) {
		this.tipoFlujoAux = tipoFlujoAux;
	}

	public List<Flow_Participantes> getParticipantesFlowsDetalle() {
		return participantesFlowsDetalle;
	}

	public void setParticipantesFlowsDetalle(
			List<Flow_Participantes> participantesFlowsDetalle) {
		this.participantesFlowsDetalle = participantesFlowsDetalle;
	}

	public void setFecha_creadoStr(String fecha_creadoStr) {
		this.fecha_creadoStr = fecha_creadoStr;
	}

	public String getFecha_creadoStr() {
		if (fecha_creado != null) {
			fecha_creadoStr = Utilidades.sdfShow.format(fecha_creado
					.getTime());
		}
		return fecha_creadoStr;
	}

	 

 

	public Role getGrupo() {
		return grupo;
	}

	public void setGrupo(Role grupo) {
		this.grupo = grupo;
	}

	public Usuario getParticipante() {
		return participante;
	}

	public void setParticipante(Usuario participante) {
		this.participante = participante;
	}

	public List<Flow> getParticipantesGruposPlantila() {
		return participantesGruposPlantila;
	}

	public void setParticipantesGruposPlantila(
			List<Flow> participantesGruposPlantila) {
		this.participantesGruposPlantila = participantesGruposPlantila;
	}

	public void setParticipantesPlantila(List<Usuario> participantesPlantila) {
		this.participantesPlantila = participantesPlantila;
	}

	public List<Usuario> getParticipantesPlantila() {
		return participantesPlantila;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public List<Flow> getParticipantesGruposPlantilaNotificacion() {
		return participantesGruposPlantilaNotificacion;
	}

	public void setParticipantesGruposPlantilaNotificacion(
			List<Flow> participantesGruposPlantilaNotificacion) {
		this.participantesGruposPlantilaNotificacion = participantesGruposPlantilaNotificacion;
	}

}
