/*
 * DiasHabiles.java
 *
 * Created on August 12, 2008, 9:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.dias.habiles;

import com.ecological.paper.tree.Tree;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * Entity class DiasHabiles
 *
 * @author simon
 */
@Entity
@Table(name="DIASHABILES")
public class DiasHabiles implements Serializable {
    @TableGenerator(
    name="diashabiles_Id_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DIASHABILES_ID",
            allocationSize=1)
            
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="diashabiles_Id_Gen")
    private Long codigo;
    
    private boolean status;
    private String nombre;
    @Transient
    private String nombreStr;
    private String h_InicialAM;
    private String h_FinalAM;
    private String h_InicialPM;
    private String h_FinalPM;
      @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;

    public String getH_InicialAM() {
        return h_InicialAM;
    }

    public void setH_InicialAM(String H_InicialAM) {
        this.h_InicialAM = H_InicialAM;
    }

    public String getH_FinalAM() {
        return h_FinalAM;
    }

    public void setH_FinalAM(String H_FinalAM) {
        this.h_FinalAM = H_FinalAM;
    }

    public String getH_InicialPM() {
        return h_InicialPM;
    }

    public void setH_InicialPM(String H_InicialPM) {
        this.h_InicialPM = H_InicialPM;
    }

    public String getH_FinalPM() {
        return h_FinalPM;
    }

    public void setH_FinalPM(String H_FinalPM) {
        this.h_FinalPM = H_FinalPM;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombreStr() {
        return nombreStr;
    }

    public void setNombreStr(String nombreStr) {
        this.nombreStr = nombreStr;
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
