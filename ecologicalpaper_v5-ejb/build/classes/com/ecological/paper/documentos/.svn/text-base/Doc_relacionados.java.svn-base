/*
 * Doc_relacionados.java
 *
 * Created on July 25, 2007, 8:20 AM
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
 * Entity class Doc_relacionados
 *
 * @author simon
 */
@Entity
public class Doc_relacionados implements Serializable {
    
    @TableGenerator(
    name="Doc_relacionados_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DOC_RELACIONADO_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_relacionados_Gen")
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name="DOC_REL1")
    private Doc_maestro doc_rel1;
    
    @ManyToOne
    @JoinColumn(name="DOC_REL2")
    private  Doc_maestro doc_rel2;
    
   
    
    /** Creates a new instance of Doc_relacionados */
    public Doc_relacionados() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Doc_maestro getDoc_rel1() {
        return doc_rel1;
    }

    public void setDoc_rel1(Doc_maestro doc_rel1) {
        this.doc_rel1 = doc_rel1;
    }

    public Doc_maestro getDoc_rel2() {
        return doc_rel2;
    }

    public void setDoc_rel2(Doc_maestro doc_rel2) {
        this.doc_rel2 = doc_rel2;
    }

   
    
}
