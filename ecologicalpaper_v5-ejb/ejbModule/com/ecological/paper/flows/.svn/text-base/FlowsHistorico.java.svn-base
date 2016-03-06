/*
 * FlowsHistorico.java
 *
 * Created on August 27, 2007, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.flows;

import com.ecological.paper.documentos.Doc_detalle;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class FlowsHistorico
 *
 * @author simon
 */
@Entity
public class FlowsHistorico implements Serializable {
    @TableGenerator(
    name="FlowHist_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="FLOWHIST_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="FlowHist_Gen")
    private Long codigo;
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name="FLOW")
    private Flow flow;

    @ManyToOne
    @JoinColumn(name="DOC_DETALLE")
    private Doc_detalle doc_detalle;
    
    @Transient
    private String fechaCreado;
    
    @Transient
    private String tipoFlujo;
    
     @Transient
    private String statusEnQedo;

   
    /** Creates a new instance of FlowsHistorico */
    public FlowsHistorico() {
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

    public Doc_detalle getDoc_detalle() {
        return doc_detalle;
    }

    public void setDoc_detalle(Doc_detalle doc_detalle) {
        this.doc_detalle = doc_detalle;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getStatusEnQedo() {
        return statusEnQedo;
    }

    public void setStatusEnQedo(String statusEnQedo) {
        this.statusEnQedo = statusEnQedo;
    }

   
    
}
