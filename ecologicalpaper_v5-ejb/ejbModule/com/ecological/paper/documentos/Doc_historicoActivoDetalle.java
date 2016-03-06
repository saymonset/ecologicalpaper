/*
 * Doc_historicoActivoDetalle.java
 *
 * Created on December 21, 2007, 4:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

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
import javax.persistence.Version;
 

/**
 * Entity class Doc_historicoActivoDetalle
 *
 * @author simon
 */
@Entity
public class Doc_historicoActivoDetalle implements Serializable {
    
    @TableGenerator(
    name="Doc_historicoActivoDetalle_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="Doc_historicoActivoDetalle_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_historicoActivoDetalle_Gen")
    private Long codigo;
    
    
    @Column(name="FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha;
    
    @ManyToOne
    @JoinColumn(name = "doc_histActMaestro")
    private Doc_historicoActivoMaestro doc_histActMaestro;
     
    private String comentario;
    private boolean actuBorrador;  
    private boolean darPublico;
    private boolean verDetalles;
    private boolean verVinculados;
    private boolean verSometerFlow;
    private boolean nuevaVerVig;
    private boolean deshNuevaVer;
    private boolean genReg;
    private boolean status;
     
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Doc_historicoActivoDetalle)  {
            Doc_historicoActivoDetalle p =(Doc_historicoActivoDetalle)objeto;
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
    
   
   
    
    public java.util.Date getFecha() {
        return fecha;
    }
    
    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    

    public Doc_historicoActivoMaestro getDoc_histActMaestro() {
        return doc_histActMaestro;
    }

    public void setDoc_histActMaestro(Doc_historicoActivoMaestro doc_histActMaestro) {
        this.doc_histActMaestro = doc_histActMaestro;
    }

    public boolean isActuBorrador() {
        return actuBorrador;
    }

    public void setActuBorrador(boolean actuBorrador) {
        this.actuBorrador = actuBorrador;
    }

    public boolean isDarPublico() {
        return darPublico;
    }

    public void setDarPublico(boolean darPublico) {
        this.darPublico = darPublico;
    }

   

    public boolean isVerDetalles() {
        return verDetalles;
    }

    public void setVerDetalles(boolean verDetalles) {
        this.verDetalles = verDetalles;
    }

    public boolean isVerVinculados() {
        return verVinculados;
    }

    public void setVerVinculados(boolean verVinculados) {
        this.verVinculados = verVinculados;
    }

    public boolean isVerSometerFlow() {
        return verSometerFlow;
    }

    public void setVerSometerFlow(boolean verSometerFlow) {
        this.verSometerFlow = verSometerFlow;
    }

    public boolean isNuevaVerVig() {
        return nuevaVerVig;
    }

    public void setNuevaVerVig(boolean nuevaVerVig) {
        this.nuevaVerVig = nuevaVerVig;
    }

    public boolean isDeshNuevaVer() {
        return deshNuevaVer;
    }

    public void setDeshNuevaVer(boolean deshNuevaVer) {
        this.deshNuevaVer = deshNuevaVer;
    }

    public boolean isGenReg() {
        return genReg;
    }

    public void setGenReg(boolean genReg) {
        this.genReg = genReg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

     
    
}
