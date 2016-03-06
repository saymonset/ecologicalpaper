/*
 * Doc_consecutivo.java
 *
 * Created on December 13, 2007, 4:31 AM
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
import javax.persistence.Version;


/**
 * Entity class Doc_consecutivo
 *
 * @author simon
 */
@Entity
public class Doc_consecutivo implements Serializable {
    @TableGenerator(
    name="Doc_numconsecutivo_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DOC_NUMCONSECUTIVO_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_numconsecutivo_Gen")
    private Long codigo;
    
    
    
    
    
    /** Creates a new instance of Doc_consecutivo */
    public Doc_consecutivo() {
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
  
    
    
    
    
}
