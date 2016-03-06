/*
 * Doc_dataPostgres.java
 *
 * Created on 28 de noviembre de 2007, 05:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * Clase Entidad Doc_dataPostgres
 *
 * @author simon
 */
@Entity
public class Doc_dataPostgres implements Serializable {
    @TableGenerator(
    name="Doc_dataPostgres_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DOC_DATAPOSTGRES_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_dataPostgres_Gen")
    private Long codigo;
    private byte[] data_doc_postgres;
   
    @ManyToOne
    @JoinColumn(name = "DOC_DETALLE")
    private Doc_detalle doc_detalle;
    
    private boolean status;
    
    public byte[] getData_doc_postgres() {
        return data_doc_postgres;
    }
    
    public void setData_doc_postgres(byte[] data_doc_postgres) {
        this.data_doc_postgres = data_doc_postgres;
    }

    public Doc_detalle getDoc_detalle() {
        return doc_detalle;
    }

    public void setDoc_detalle(Doc_detalle doc_detalle) {
        this.doc_detalle = doc_detalle;
    }

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

   
    
}
