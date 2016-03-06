package com.ecological.paper.documentos;

import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity class Solicitudimpresion
 *
 * @author simon 2011 03 27
 */
@Entity
@Table(name = "Solicitudimpresion")
public class Solicitudimpresion implements Serializable {

    @TableGenerator(name = "Solicitudimpresion_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "Solicitudimpresion_ID", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Solicitudimpresion_Gen")
    private Long codigo;
   
    @ManyToOne
    @JoinColumn(name = "solicitante")
    private Usuario solicitante;
    
    @Column(name="fechasolicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaSolicitud;
    
    @Transient
    private String fechaSolicitudtxt;
    
    
    
    @ManyToOne
    @JoinColumn(name="flow")
    private  Flow flow;
    
    @Column(name="fechadesdeimprimir")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechadesdeimprimir;

    @Transient
    private String fechadesdeimprimirtxt;
    
    @Column(name="fechahastaimprimir")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechahastaimprimir;
    @Transient
    private String fechahastaimprimirtxt;
    
    @ManyToOne
	@JoinColumn(name = "estado")
	private Doc_estado estado;
    
    @ManyToOne
	@JoinColumn(name = "DOC_DETALLE")
	private Doc_detalle doc_detalle;
    
    @OneToMany(mappedBy = "solicitudimpresion")
    private Collection<SolicitudImpPart> solicitudImpParts = new ArrayList<SolicitudImpPart>();
    
    @Transient
    private boolean swMostrarAll;
 
    
    private long numcopias=1;
    private String comentarios;
    private boolean status;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Usuario getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
	public java.util.Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(java.util.Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	public java.util.Date getFechadesdeimprimir() {
		return fechadesdeimprimir;
	}
	public void setFechadesdeimprimir(java.util.Date fechadesdeimprimir) {
		this.fechadesdeimprimir = fechadesdeimprimir;
	}
	public java.util.Date getFechahastaimprimir() {
		return fechahastaimprimir;
	}
	public void setFechahastaimprimir(java.util.Date fechahastaimprimir) {
		this.fechahastaimprimir = fechahastaimprimir;
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
	public long getNumcopias() {
		return numcopias;
	}
	public void setNumcopias(long numcopias) {
		this.numcopias = numcopias;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Collection<SolicitudImpPart> getsolicitudImpParts() {
		return solicitudImpParts;
	}
	public void setsolicitudImpParts(
			Collection<SolicitudImpPart> solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}
	public boolean isSwMostrarAll() {
		return swMostrarAll;
	}
	public void setSwMostrarAll(boolean swMostrarAll) {
		this.swMostrarAll = swMostrarAll;
	}
	public String getFechaSolicitudtxt() {
		
		if (this.fechaSolicitud!=null){
			fechaSolicitudtxt=Utilidades.sdfShow
			.format(this.fechaSolicitud);
		}

		
		return fechaSolicitudtxt;
	}
	public void setFechaSolicitudtxt(String fechaSolicitudtxt) {
		this.fechaSolicitudtxt = fechaSolicitudtxt;
	}
	public String getFechadesdeimprimirtxt() {
		if (this.fechadesdeimprimir!=null){
			fechadesdeimprimirtxt=Utilidades.sdfShow
			.format(this.fechadesdeimprimir);
		}

		return fechadesdeimprimirtxt;
	}
	public void setFechadesdeimprimirtxt(String fechadesdeimprimirtxt) {
		this.fechadesdeimprimirtxt = fechadesdeimprimirtxt;
	}
	public String getFechahastaimprimirtxt() {
		
		if (this.fechahastaimprimir!=null){
			fechahastaimprimirtxt=Utilidades.sdfShow
			.format(this.fechahastaimprimir);
		}

		
		return fechahastaimprimirtxt;
	}
	public void setFechahastaimprimirtxt(String fechahastaimprimirtxt) {
		this.fechahastaimprimirtxt = fechahastaimprimirtxt;
	}
	public Collection<SolicitudImpPart> getSolicitudImpParts() {
		return solicitudImpParts;
	}
	public void setSolicitudImpParts(Collection<SolicitudImpPart> solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}
   
   
  
    
}
