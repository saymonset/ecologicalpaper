/*
 * DiasFeriadosBean.java
 *
 * Created on August 14, 2008, 10:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.dias.feriados;

import com.ecological.paper.tree.Tree;
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
@Entity
public class DiasFeriadosBean implements Serializable {
    @TableGenerator(
    name="diasferiados_Id_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DIASFERIADOS_ID",
            allocationSize=1)
            
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="diasferiados_Id_Gen")
    private Long codigo;
    
    private boolean status;
    private String descripcion;
       @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;

    @Column(name="FECHAONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechaonly;
   @Transient
    private boolean delete;
   
      @Transient
    private String fechaStr;


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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.util.Date getFechaonly() {
        return fechaonly;
    }

    public void setFechaonly(java.util.Date fechaonly) {
        this.fechaonly = fechaonly;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }

    /**
     * @return the empresa
     */
    public Tree getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Tree empresa) {
        this.empresa = empresa;
    }

    
    
     
    
  
}
