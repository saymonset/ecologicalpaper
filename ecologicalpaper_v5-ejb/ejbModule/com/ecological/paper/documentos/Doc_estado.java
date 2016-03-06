/*
 * Doc_estado.java
 *
 * Created on July 25, 2007, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.Flow_Participantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * Entity class Doc_estado
 *
 * @author simon
 */
@Entity
@Table(name="DOC_ESTADO")
public class Doc_estado implements Serializable {
    @TableGenerator(
    name="Doc_estado_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="DOC_ESTADO_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Doc_estado_Gen")
    private Long codigo;
   
    
    private String nombre;
    private boolean status;
    @Column(name="DESCRIPCION", length=4000)
    private String descripcion;
    
    @OneToMany(mappedBy="doc_estado")
    private Collection<Doc_detalle> doc_detalle = new ArrayList<Doc_detalle>();
    
    @OneToMany(mappedBy="estado")
    private Collection<Flow>  tipo_estados =  new ArrayList<Flow>();
    
    @OneToMany(mappedBy="firma")
    private Collection<Flow_Participantes>  firmasParticipante =  new ArrayList<Flow_Participantes>();
    
    @OneToMany(mappedBy="estado")
    private Collection<Solicitudimpresion>  solicitudimpresiones =  new ArrayList<Solicitudimpresion>();
    
    @OneToMany(mappedBy="estado")
    private Collection<SolicitudImpPart>  solicitudImpParts =  new ArrayList<SolicitudImpPart>();
    
    /** Creates a new instance of Doc_estado */
    public Doc_estado() {
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
    
    public Collection<Doc_detalle> getDoc_detalle() {
        return doc_detalle;
    }
    
    public void setDoc_detalle(Collection<Doc_detalle> doc_detalle) {
        this.doc_detalle = doc_detalle;
    }
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Doc_estado)  {
            Doc_estado p =(Doc_estado)objeto;
            if (p.getCodigo()-this.codigo==0){
                return true;
            }else
                return false;
            
        }else
            return false;
        
    }
    
    public Collection<Flow> getTipo_estados() {
        return tipo_estados;
    }
    
    public void setTipo_estados(Collection<Flow> tipo_estados) {
        this.tipo_estados = tipo_estados;
    }

    public Collection<Flow_Participantes> getFirmasParticipante() {
        return firmasParticipante;
    }

    public void setFirmasParticipante(Collection<Flow_Participantes> firmasParticipante) {
        this.firmasParticipante = firmasParticipante;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public Collection<Solicitudimpresion> getSolicitudimpresiones() {
		return solicitudimpresiones;
	}

	public void setSolicitudimpresiones(
			Collection<Solicitudimpresion> solicitudimpresiones) {
		this.solicitudimpresiones = solicitudimpresiones;
	}

	public Collection<SolicitudImpPart> getsolicitudImpParts() {
		return solicitudImpParts;
	}

	public void setsolicitudImpParts(
			Collection<SolicitudImpPart> solicitudImpParts) {
		this.solicitudImpParts = solicitudImpParts;
	}

 

  
  
    
}
