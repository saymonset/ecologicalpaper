/*
 * Doc_tipo.java
 *
 * Created on July 25, 2007, 8:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import com.ecological.paper.tree.Tree;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
 

/**
 * Entity class Doc_tipo
 *
 * @author simon
 */
@Entity
@Table(name="DOC_TIPO")
public class Doc_tipo implements Serializable {
    
    
    @TableGenerator(
    name="Doc_tipo_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DOC_TIPO_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_tipo_Gen")
    private Long codigo;
    
    private String nombre;
    private boolean status;
    private Long formatoTipoDoc;
    @Column(name = "abreviatura")
    private String abreviatura;
    @Transient
    private boolean  delete;
   
    @Transient
	private boolean swTipoFormato;
    
     @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;
    
    @Column(name="DESCRIPCION", length=4000)
    private String descripcion;
    
    @OneToMany(mappedBy="doc_tipo")
    private Collection<Doc_maestro> doc_maestro = new ArrayList<Doc_maestro>();
 
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Doc_tipo)  {
            Doc_tipo p =(Doc_tipo)objeto;
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
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Collection<Doc_maestro> getDoc_maestro() {
        return doc_maestro;
    }
    
    public void setDoc_maestro(Collection<Doc_maestro> doc_maestro) {
        this.doc_maestro = doc_maestro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
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

	public Long getFormatoTipoDoc() {
		return formatoTipoDoc;
	}

	public void setFormatoTipoDoc(Long formatoTipoDoc) {
		this.formatoTipoDoc = formatoTipoDoc;
	}

	public boolean isSwTipoFormato() {
		return swTipoFormato;
	}

	public void setSwTipoFormato(boolean swTipoFormato) {
		this.swTipoFormato = swTipoFormato;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String toString(){
		return this.nombre;
	}
 

 

	 

   
    
    
}
