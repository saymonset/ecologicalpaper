/*
 * FlowControlByUsuarioBeanII.java
 *
 * Created on 2 de diciembre de 2008, 06:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.control.timeflow;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class FlowControlByUsuarioBeanII
 * 
 * @author simon
 */
@Entity
public class FlowControlByUsuarioBeanII implements Serializable {

   @TableGenerator(
    name="controlflow_Id_GenII",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="CONTROLFLOW_IDII",
            allocationSize=1)
            
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="controlflow_Id_GenII")
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name="flowControlByUsuarioBean")
    private FlowControlByUsuarioBean flowControlByUsuarioBean;
   
    @Column(name="FECHACREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaCreado;
    
    @Column(name="FECHASECUENCIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaSecuencial;
    private boolean status;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public FlowControlByUsuarioBean getFlowControlByUsuarioBean() {
        return flowControlByUsuarioBean;
    }

    public void setFlowControlByUsuarioBean(FlowControlByUsuarioBean flowControlByUsuarioBean) {
        this.flowControlByUsuarioBean = flowControlByUsuarioBean;
    }

    public java.util.Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(java.util.Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public java.util.Date getFechaSecuencial() {
        return fechaSecuencial;
    }

    public void setFechaSecuencial(java.util.Date fechaSecuencial) {
        this.fechaSecuencial = fechaSecuencial;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
