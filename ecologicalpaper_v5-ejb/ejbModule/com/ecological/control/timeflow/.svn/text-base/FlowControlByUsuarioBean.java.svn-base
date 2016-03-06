/*
 * FlowControlByUsuarioBean.java
 *
 * Created on August 16, 2008, 10:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.control.timeflow;

import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.permisologia.role.Role;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;/**
 * Entity class DiasFeriadosBean
 *
 * @author simon
 */
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class FlowControlByUsuarioBean
 *
 * @author simon
 */
@Entity
public class FlowControlByUsuarioBean implements Serializable {
    
    @TableGenerator(
    name="controlflow_Id_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="CONTROLFLOW_ID",
            allocationSize=1)
            
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="controlflow_Id_Gen")
    private Long codigo;
     
    private boolean status;
    private boolean swEsRole;
    private boolean swStartHilo;
    private Integer horasAsignadas;
    private Integer minutosAsignados;
    private Integer horasAcumuladas;
    private Integer minutosAcumulados;
    private boolean envMailRemember;//redcordatorio
    private Integer hrsAntesToEnvMailRemember;
    private Integer contEnvMailRemember;//redcordatorio
    @Column(name="FECHAONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechaMailRemember;
    
    @Column(name="FECHAEMITIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaemitido;
    @ManyToOne
    @JoinColumn(name="flow_Participantes")
    private Flow_Participantes flow_Participantes;
    
    @ManyToOne
    @JoinColumn(name="flow")
    private Flow flow;
    
    @ManyToOne
    @JoinColumn(name="role")
    private Role role;
    
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
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
    
    public boolean isEnvMailRemember() {
        return envMailRemember;
    }
    
    public void setEnvMailRemember(boolean envMailRemember) {
        this.envMailRemember = envMailRemember;
    }
    
    
    
    public Integer getContEnvMailRemember() {
        return contEnvMailRemember;
    }
    
    public void setContEnvMailRemember(Integer contEnvMailRemember) {
        this.contEnvMailRemember = contEnvMailRemember;
    }
    
    public java.util.Date getFechaMailRemember() {
        return fechaMailRemember;
    }
    
    public void setFechaMailRemember(java.util.Date fechaMailRemember) {
        this.fechaMailRemember = fechaMailRemember;
    }
    
    
    
    
    public Flow_Participantes getFlow_Participantes() {
        return flow_Participantes;
    }
    
    public void setFlow_Participantes(Flow_Participantes flow_Participantes) {
        this.flow_Participantes = flow_Participantes;
    }
    
    public Integer getHrsAntesToEnvMailRemember() {
        return hrsAntesToEnvMailRemember;
    }
    
    public void setHrsAntesToEnvMailRemember(Integer hrsAntesToEnvMailRemember) {
        this.hrsAntesToEnvMailRemember = hrsAntesToEnvMailRemember;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean isSwEsRole() {
        return swEsRole;
    }
    
    public void setSwEsRole(boolean swEsRole) {
        this.swEsRole = swEsRole;
    }
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    
    public FlowControlByUsuarioBean() {
    }
    public FlowControlByUsuarioBean(FlowControlByUsuarioBean t){
        this.setContEnvMailRemember(t.getContEnvMailRemember());
        this.setEnvMailRemember(t.isEnvMailRemember());
        this.setFechaMailRemember(t.getFechaMailRemember());
        this.setFlow(t.getFlow());
        this.setFlow_Participantes(flow_Participantes);
        this.setHorasAsignadas(t.getHorasAsignadas());
        this.setHorasAcumuladas(t.getHorasAcumuladas());
        this.setHrsAntesToEnvMailRemember(t.getHrsAntesToEnvMailRemember());
        this.setMinutosAcumulados(t.getMinutosAcumulados());
        this.setMinutosAsignados(t.getMinutosAsignados());
        this.setRole(t.getRole());
        this.setStatus(t.isStatus());
        this.setSwEsRole(t.isSwEsRole());
        
        /*
        
         */
    }

    public boolean isSwStartHilo() {
        return swStartHilo;
    }

    public void setSwStartHilo(boolean swStartHilo) {
        this.swStartHilo = swStartHilo;
    }
    public java.util.Date getFechaemitido() {
        return fechaemitido;
    }

    public void setFechaemitido(java.util.Date fechaemitido) {
        this.fechaemitido = fechaemitido;
    }
    
    
    
}
