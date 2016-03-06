package com.ecological.paper.subversion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name="svnmodulo")
public class SvnModulo implements Serializable {
    @TableGenerator(
    	    name="moduloSVN_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="ModuloSVN_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="moduloSVN_id_gen")
    	    private Long codigo;
            private boolean status;
            @ManyToOne
            @JoinColumn(name="svnTipoAmbiente")
            private SvnTipoAmbiente svnTipoAmbiente;
            @Transient
            private boolean delete;
            private String pathCorto1;
            private String pathCorto2;
            private String pathCorto3;
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
			 * @return the pathCorto1
			 */
			public String getPathCorto1() {
				return pathCorto1;
			}
			/**
			 * @return the pathCorto2
			 */
			public String getPathCorto2() {
				return pathCorto2;
			}
			/**
			 * @return the pathCorto3
			 */
			public String getPathCorto3() {
				return pathCorto3;
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
			 * @param pathCorto1 the pathCorto1 to set
			 */
			public void setPathCorto1(String pathCorto1) {
				this.pathCorto1 = pathCorto1;
			}
			/**
			 * @param pathCorto2 the pathCorto2 to set
			 */
			public void setPathCorto2(String pathCorto2) {
				this.pathCorto2 = pathCorto2;
			}
			/**
			 * @param pathCorto3 the pathCorto3 to set
			 */
			public void setPathCorto3(String pathCorto3) {
				this.pathCorto3 = pathCorto3;
			}
			
			public boolean isDelete() {
				return delete;
			}
			public void setDelete(boolean delete) {
				this.delete = delete;
			}
			
			  public boolean equals(Object objeto) {
			        if  (objeto!=null && objeto instanceof SvnModulo)  {
			        	SvnModulo p =(SvnModulo)objeto;
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
				  StringBuffer strbf= new StringBuffer("");
				  if (this.pathCorto1!=null && !"".equalsIgnoreCase(this.pathCorto1)){
					  strbf.append(this.pathCorto1).append("/");
				  }
				  if (this.pathCorto2!=null && !"".equalsIgnoreCase(this.pathCorto2)){
					  strbf.append(this.pathCorto2).append("/");
				  }
				  if (this.pathCorto3!=null && !"".equalsIgnoreCase(this.pathCorto3)){
					  strbf.append(this.pathCorto3).append("/");
				  }
			        return strbf.toString();
			    }
			public SvnTipoAmbiente getSvnTipoAmbiente() {
				return svnTipoAmbiente;
			}
			public void setSvnTipoAmbiente(SvnTipoAmbiente svnTipoAmbiente) {
				this.svnTipoAmbiente = svnTipoAmbiente;
			}
}
