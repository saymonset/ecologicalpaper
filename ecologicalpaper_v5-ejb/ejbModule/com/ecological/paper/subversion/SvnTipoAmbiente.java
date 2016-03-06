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
@Entity
@Table(name="svnTipoAmbiente")
public class SvnTipoAmbiente implements Serializable {
    @TableGenerator(
    	    name="svnTipoAmbiente_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="tipoAmbiente_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="svnTipoAmbiente_id_gen")
    	    private Long codigo;
            private boolean status;
            private String nombre;
            private String descripcion;
            
            @ManyToOne
            @JoinColumn(name="svnnombreaplicacion")
            private SvnNombreAplicacion svnnombreaplicacion;
            
            @OneToMany(mappedBy="svnTipoAmbiente")
            private Collection<SvnModulo> moduloSVN = new ArrayList<SvnModulo>();
	     
    		
            @Transient
            private boolean  delete;
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
			public boolean isDelete() {
				return delete;
			}
			public void setDelete(boolean delete) {
				this.delete = delete;
			}
		
		 
			
			  public boolean equals(Object objeto) {
			        if  (objeto!=null && objeto instanceof SvnTipoAmbiente)  {
			        	SvnTipoAmbiente p =(SvnTipoAmbiente)objeto;
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
			public SvnNombreAplicacion getSvnnombreaplicacion() {
				return svnnombreaplicacion;
			}
			public void setSvnnombreaplicacion(SvnNombreAplicacion svnnombreaplicacion) {
				this.svnnombreaplicacion = svnnombreaplicacion;
			}
			public Collection<SvnModulo> getModuloSVN() {
				return moduloSVN;
			}
			public void setModuloSVN(Collection<SvnModulo> moduloSVN) {
				this.moduloSVN = moduloSVN;
			}
}
