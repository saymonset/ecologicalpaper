/*
 * ConsecutivoEUJ.java
 *
 * Created on 28 de febrero de 2008, 07:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.software.eujovans.clientes;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * Entity class ConsecutivoEUJ
 * 
 * @author simon
 */
@Entity
public class ConsecutivoEUJ implements Serializable {

    @TableGenerator(
    name="Consecutivo_EUJ",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="consecutivoeuj_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Consecutivo_EUJ")
    private Long  codigo;
    
    private long consecutivo;
    private long consecutivonousado;
    private boolean status;
    
   
    
    
    /** Creates a new instance of ConsecutivoEUJ */
    public ConsecutivoEUJ() {
    }

    /**
     * Gets the id of this ConsecutivoEUJ.
     * @return the id
     */

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public long getConsecutivonousado() {
        return consecutivonousado;
    }

    public void setConsecutivonousado(long consecutivonousado) {
        this.consecutivonousado = consecutivonousado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

  
   
    
}
