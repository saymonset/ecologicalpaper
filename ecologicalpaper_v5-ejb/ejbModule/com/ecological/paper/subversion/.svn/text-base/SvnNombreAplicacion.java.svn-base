package com.ecological.paper.subversion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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

import com.ecological.paper.profesion.Profesion;

@Entity
@Table(name="svnnombreaplicacion")
public class SvnNombreAplicacion implements Serializable {
    @TableGenerator(
    	    name="nombreaplicacionsvn_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="nombreaplicacionsvn_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="nombreaplicacionsvn_id_gen")
    	    private Long codigo;
            private boolean status;
            
            private String nombre;
            private String descripcion;
            @Transient
            private boolean delete;
            
        	
            @ManyToOne
            @JoinColumn(name="svnUrlBase")
            private SvnUrlBase svnUrlBase;
            
            @OneToMany(mappedBy="svnnombreaplicacion")
            private Collection<SvnTipoAmbiente> svnTipoAmbientes = new ArrayList<SvnTipoAmbiente>();
    
			 
			/**
			 * @return the codigo
			 */
			public Long getCodigo() {
				return codigo;
			}
			/**
			 * @return the status
			 */
			public boolean isStatus() {
				return status;
			}
			/**
			 * @return the nombre
			 */
			public String getNombre() {
				return nombre;
			}
			/**
			 * @return the descripcion
			 */
			public String getDescripcion() {
				return descripcion;
			}
			/**
			 * @param codigo the codigo to set
			 */
			public void setCodigo(Long codigo) {
				this.codigo = codigo;
			}
			/**
			 * @param status the status to set
			 */
			public void setStatus(boolean status) {
				this.status = status;
			}
			/**
			 * @param nombre the nombre to set
			 */
			public void setNombre(String nombre) {
				this.nombre = nombre;
			}
			/**
			 * @param descripcion the descripcion to set
			 */
			public void setDescripcion(String descripcion) {
				this.descripcion = descripcion;
			}
			 
			 
			public boolean isDelete() {
				return delete;
			}
			public void setDelete(boolean delete) {
				this.delete = delete;
			}

			  public boolean equals(Object objeto) {
			        if  (objeto!=null && objeto instanceof SvnNombreAplicacion)  {
			        	SvnNombreAplicacion p =(SvnNombreAplicacion)objeto;
			            /*String k1 =p.getCodigo()+"";
			            String k2=this.codigo+"";*/
			            //if (k1.trim().equalsIgnoreCase(k2.trim())){
			            if ( p!=null && (p.getCodigo()-this.codigo)==0){
			                return true;
			            }else
			                return false;
			            
			        }else
			            return false;
			        
			    }
			  
			  public String toString(){
			        String str =  this.nombre;
			        return str.toString();
			    }
			 
			public SvnUrlBase getSvnUrlBase() {
				return svnUrlBase;
			}
			public void setSvnUrlBase(SvnUrlBase svnUrlBase) {
				this.svnUrlBase = svnUrlBase;
			}
			public Collection<SvnTipoAmbiente> getSvnTipoAmbientes() {
				return svnTipoAmbientes;
			}
			public void setSvnTipoAmbientes(Collection<SvnTipoAmbiente> svnTipoAmbientes) {
				this.svnTipoAmbientes = svnTipoAmbientes;
			}
}
