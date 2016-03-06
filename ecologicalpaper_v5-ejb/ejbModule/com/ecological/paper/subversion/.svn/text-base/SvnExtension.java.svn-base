package com.ecological.paper.subversion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name="svnextension")
public class SvnExtension implements Serializable {
    @TableGenerator(
    	    name="extensionsvn_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="extensionsvn_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="extensionsvn_id_gen")
    	    private Long codigo;
            private boolean status;
            private String descripcion;
            private String anidacion0;
            @Transient
            private boolean delete;
            private String anidacion1;
            private String ext;
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
			public String getDescripcion() {
				return descripcion;
			}
			public void setDescripcion(String descripcion) {
				this.descripcion = descripcion;
			}
			public String getAnidacion0() {
				return anidacion0;
			}
			public void setAnidacion0(String anidacion0) {
				this.anidacion0 = anidacion0;
			}
			public String getAnidacion1() {
				return anidacion1;
			}
			public void setAnidacion1(String anidacion1) {
				this.anidacion1 = anidacion1;
			}
			public String getExt() {
				return ext;
			}
			public void setExt(String ext) {
				this.ext = ext;
			}
			public boolean isDelete() {
				return delete;
			}
			public void setDelete(boolean delete) {
				this.delete = delete;
			}
			
			 public String toString(){
				  StringBuffer strbf= new StringBuffer("");
				  if (this.anidacion0!=null && !"".equalsIgnoreCase(this.anidacion0)){
					  strbf.append(this.anidacion0).append("/");
				  }
				  if (this.anidacion1!=null && !"".equalsIgnoreCase(this.anidacion1)){
					  strbf.append(this.anidacion1).append("/");
				  }
			        return strbf.toString();
			    }

}

