/*
 * Doc_historicoActivoMaestro.java
 *
 * Created on December 21, 2007, 4:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
 

/**
 * Entity class Doc_historicoActivoMaestro
 * 
 * @author simon
 */
@Entity
public class Doc_historicoActivoMaestro implements Serializable {
    @TableGenerator(
    name="Doc_historicoActivoMaestro_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="Doc_historicoActivoMaestro_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_historicoActivoMaestro_Gen")
    private Long codigo;
    
    
    
    @ManyToOne
    @JoinColumn(name = "DOC_MAESTRO")
    private Doc_maestro doc_maestro;
    
    @ManyToOne
    @JoinColumn(name = "USUARIO")
    private Usuario usuario;
    
    private boolean status;
      
    @OneToMany(mappedBy="doc_histActMaestro")
    private Collection<Doc_historicoActivoDetalle> doc_histActDetalles = new ArrayList<Doc_historicoActivoDetalle>();
     
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Doc_historicoActivoMaestro)  {
            Doc_historicoActivoMaestro p =(Doc_historicoActivoMaestro)objeto;
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

    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    

    public Collection<Doc_historicoActivoDetalle> getDoc_histActDetalles() {
        return doc_histActDetalles;
    }

    public void setDoc_histActDetalles(Collection<Doc_historicoActivoDetalle> doc_histActDetalles) {
        this.doc_histActDetalles = doc_histActDetalles;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Doc_maestro getDoc_maestro() {
        return doc_maestro;
    }

    public void setDoc_maestro(Doc_maestro doc_maestro) {
        this.doc_maestro = doc_maestro;
    }

   

   
    
}
