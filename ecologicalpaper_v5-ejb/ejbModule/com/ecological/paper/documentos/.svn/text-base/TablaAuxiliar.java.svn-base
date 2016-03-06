/*
 * TablaAuxiliar.java
 *
 * Created on December 20, 2007, 5:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class TablaAuxiliar
 * 
 * @author simon
 */
@Entity
public class TablaAuxiliar implements Serializable {
@TableGenerator(
    name="TablaAuxiliar_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="AUXILIAR_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="TablaAuxiliar_Gen")
    private Long codigo;
  //FECHAS AUXILIARES PARA TRABAJAR CON FECHAS EN LAS DEMAS TABLAS
    @Column(name="FECHA1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  fecha1;
    
    @Column(name="FECHA2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  fecha2;

   
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }
    
}
