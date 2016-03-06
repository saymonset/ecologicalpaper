/*
 * Flow_Participantes.java
 *
 * Created on August 1, 2007, 9:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.permisologia.role.Role;
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
import javax.persistence.Version;

/**
 * Entity class Flow_Participantes
 *
 * @author simon
 */
@Entity
public class Flow_Participantes implements Serializable {
    
    @TableGenerator(
    name="Flow_Part_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="Flow_Part_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Flow_Part_Gen")
    private Long codigo;
    private boolean status;
    @ManyToOne
    @JoinColumn(name="FLOW")
    private  Flow flow;
    
    @ManyToOne
    @JoinColumn(name="PARTICIPANTE")
    private  Usuario participante;
    
    
    @ManyToOne
    @JoinColumn(name="FIRMA")
    private Doc_estado firma;
    @Transient
    private Integer tipoDeCambioParticipante=0;
    @Transient
    private
            boolean swCanRealizarFlow;
    
    @Transient
    private
            boolean toModFlow;
    
    @Transient
    private
            boolean swPuedeRealizarFlowPlantilla;
    
    @Transient
    private
            boolean swAgregarDocumentosvnUpload;
    
    @Transient
    private
            String firmaStr;
    
     
    @OneToMany(mappedBy="flow_Participantes")
    private Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean = new ArrayList<FlowControlByUsuarioBean>();
    
    @OneToMany(mappedBy="flow_Participantes")
    private Collection<Flow> flows = new ArrayList<Flow>();
 
    
  
    
    
    
    private boolean auxdeFirma=false;
    
    @Column(name="FECHA_FIRMA")
    @Temporal(TemporalType.TIMESTAMP)
    private  Date fecha_firma;
   
    
    @Column(name="COMENTARIO", length=4000)
    private  String comentario;
    
    @Transient
    private boolean swComentario;
    
    @Transient
    private boolean swRole;
    
    @Transient
    private Integer horasAsignadas;
    @Transient
    private Integer minutosAsignados;
    @Transient
    private Integer horasAcumuladas;
    @Transient
    private Integer minutosAcumulados;
    @Transient
    private Integer horasRestantes;
    @Transient
    private Integer minutosRestantes;
  
     @Transient
    private boolean swFlow_ParticipantesAttachment;
 
    //en caso que seleccion tambien por role, estas se desintegra en operaciones y se guardan en flow_participantes,
    //pero tienen qcomo referencia, de que tipo de role llegan
    @ManyToOne
    @JoinColumn(name="ROLE")
    private Role role;
    
    private boolean activo;
    
    @OneToMany(mappedBy="flowParticipantes")
    private Collection<Flow_ParticipantesAttachment> flow_ParticipantesAttachment = new ArrayList<Flow_ParticipantesAttachment>();
   
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Flow_Participantes)  {
            Flow_Participantes p =(Flow_Participantes)objeto;
            if (p.getCodigo()-this.getCodigo()==0){
                return true;
            }else
                return false;
            
        }else
            return false;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    
    public Usuario getParticipante() {
        return participante;
    }
    
    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }
    
    
    public Date getFecha_firma() {
        return fecha_firma;
    }
    
    public void setFecha_firma(Date fecha_firma) {
        this.fecha_firma = fecha_firma;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
    
    public void setFirma(Doc_estado firma) {
        this.firma = firma;
    }
    
    public Doc_estado getFirma() {
        return firma;
    }
    
    public boolean isAuxdeFirma() {
        return auxdeFirma;
    }
    
    public void setAuxdeFirma(boolean auxdeFirma) {
        this.auxdeFirma = auxdeFirma;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getFirmaStr() {
    	if (getFecha_firma()!=null){
    		firmaStr=	Utilidades.sdfShow
    		.format(getFecha_firma());
    	}
    	
    	
        return firmaStr;
    }
    
    public void setFirmaStr(String firmaStr) {
        this.firmaStr = firmaStr;
    }
    
     
    
    public Collection<FlowControlByUsuarioBean> getFlowControlByUsuarioBean() {
        return flowControlByUsuarioBean;
    }
    
    public void setFlowControlByUsuarioBean(Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean) {
        this.flowControlByUsuarioBean = flowControlByUsuarioBean;
    }
    
    public boolean isSwRole() {
        return swRole;
    }
    
    public void setSwRole(boolean swRole) {
        this.swRole = swRole;
    }

    public Integer getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(Integer horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public Integer getMinutosAsignados() {
        return minutosAsignados;
    }

    public void setMinutosAsignados(Integer minutosAsignados) {
        this.minutosAsignados = minutosAsignados;
    }

    public Integer getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(Integer horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public Integer getMinutosAcumulados() {
        return minutosAcumulados;
    }

    public void setMinutosAcumulados(Integer minutosAcumulados) {
        this.minutosAcumulados = minutosAcumulados;
    }

    public Integer getHorasRestantes() {
        return horasRestantes;
    }

    public void setHorasRestantes(Integer horasRestantes) {
        this.horasRestantes = horasRestantes;
    }

    public Integer getMinutosRestantes() {
        return minutosRestantes;
    }

    public void setMinutosRestantes(Integer minutosRestantes) {
        this.minutosRestantes = minutosRestantes;
    }

    public Collection<Flow_ParticipantesAttachment> getFlow_ParticipantesAttachment() {
        return flow_ParticipantesAttachment;
    }

    public void setFlow_ParticipantesAttachment(Collection<Flow_ParticipantesAttachment> flow_ParticipantesAttachment) {
        this.flow_ParticipantesAttachment = flow_ParticipantesAttachment;
    }

    public boolean isSwFlow_ParticipantesAttachment() {
        return swFlow_ParticipantesAttachment;
    }

    public void setSwFlow_ParticipantesAttachment(boolean swFlow_ParticipantesAttachment) {
        this.swFlow_ParticipantesAttachment = swFlow_ParticipantesAttachment;
    }

	public boolean isSwComentario() {
		return swComentario;
	}

	public void setSwComentario(boolean swComentario) {
		this.swComentario = swComentario;
	}

	public Collection<Flow> getFlows() {
		return flows;
	}

	public void setFlows(Collection<Flow> flows) {
		this.flows = flows;
	}

	public boolean isSwCanRealizarFlow() {
		return swCanRealizarFlow;
	}

	public void setSwCanRealizarFlow(boolean swCanRealizarFlow) {
		this.swCanRealizarFlow = swCanRealizarFlow;
	}

	public boolean isSwPuedeRealizarFlowPlantilla() {
		return swPuedeRealizarFlowPlantilla;
	}

	public void setSwPuedeRealizarFlowPlantilla(boolean swPuedeRealizarFlowPlantilla) {
		this.swPuedeRealizarFlowPlantilla = swPuedeRealizarFlowPlantilla;
	}

	public boolean isSwAgregarDocumentosvnUpload() {
		return swAgregarDocumentosvnUpload;
	}

	public void setSwAgregarDocumentosvnUpload(boolean swAgregarDocumentosvnUpload) {
		this.swAgregarDocumentosvnUpload = swAgregarDocumentosvnUpload;
	}

	public boolean isToModFlow() {
		return toModFlow;
	}

	public void setToModFlow(boolean toModFlow) {
		this.toModFlow = toModFlow;
	}

	public Integer getTipoDeCambioParticipante() {
		return tipoDeCambioParticipante;
	}

	public void setTipoDeCambioParticipante(Integer tipoDeCambioParticipante) {
		this.tipoDeCambioParticipante = tipoDeCambioParticipante;
	}

	 
  
    
    
}
