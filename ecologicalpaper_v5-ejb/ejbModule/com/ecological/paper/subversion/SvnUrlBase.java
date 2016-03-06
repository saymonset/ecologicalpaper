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

import com.ecological.paper.tree.Tree;


@Entity
@Table(name="svnurlbase")
public class SvnUrlBase implements Serializable {
    @TableGenerator(
    	    name="urlbasesvn_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="urlbasesvn_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="urlbasesvn_id_gen")
    	    private Long codigo;
            private boolean status;
            @ManyToOne
            @JoinColumn(name="empresa")
            private Tree empresa;
            private String nombre;
        
            @Transient
            private boolean  delete;
            
            
            @OneToMany(mappedBy="svnUrlBase")
            private Collection<SvnNombreAplicacion> svnNombreAplicacion = new ArrayList<SvnNombreAplicacion>();
        	/**
			 * @return the codigo
			 */
			public Long getCodigo() {
				return codigo;
			}
			/**
			 * @param codigo the codigo to set
			 */
			public void setCodigo(Long codigo) {
				this.codigo = codigo;
			}
			/**
			 * @return the status
			 */
			public boolean isStatus() {
				return status;
			}
			/**
			 * @param status the status to set
			 */
			public void setStatus(boolean status) {
				this.status = status;
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
		 
		
		 
			public boolean isDelete() {
				return delete;
			}
			public void setDelete(boolean delete) {
				this.delete = delete;
			}
			public String getNombre() {
				return nombre;
			}
			public void setNombre(String nombre) {
				this.nombre = nombre;
			}
			
			  public boolean equals(Object objeto) {
			        if  (objeto!=null && objeto instanceof SvnUrlBase)  {
			        	SvnUrlBase p =(SvnUrlBase)objeto;
			            /*String k1 =p.getCodigo()+"";
			            String k2=this.codigo+"";*/
			            //if (k1.trim().equalsIgnoreCase(k2.trim())){
			            if ( p!=null){
			              if ((p.getCodigo()!=null && this.codigo!=null) && (p.getCodigo()-this.codigo)==0){
				                return true;
				          }else
				                return false;
			            }else
			                return false;
			            
			        }else
			            return false;
			        
			    }
			  
			  public String toString(){
				  StringBuffer str = new StringBuffer(this.nombre);
			        return str.toString();
			    }
			public Collection<SvnNombreAplicacion> getSvnNombreAplicacion() {
				return svnNombreAplicacion;
			}
			public void setSvnNombreAplicacion(
					Collection<SvnNombreAplicacion> svnNombreAplicacion) {
				this.svnNombreAplicacion = svnNombreAplicacion;
			}
			
}
