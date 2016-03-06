/*
 * ValidaVencimiento.java
 *
 * Created on 25 de febrero de 2008, 06:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.util;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class ValidaVencimiento
 *
 * @author simon
 */
@Entity
public class ValidaVencimiento implements Serializable {
    
    @TableGenerator(
    name="validafecha_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="FECHA_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="validafecha_Gen")
    private Long codigo;
    
    @Column(name="FECHA")
    @Temporal(TemporalType.DATE)
    private java.util.Date fecha;
    
    
    private String fecha2;
     
    private boolean status;
    /** Creates a new instance of ValidaVencimiento */
    public ValidaVencimiento() {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }
    
    
    
    
}
