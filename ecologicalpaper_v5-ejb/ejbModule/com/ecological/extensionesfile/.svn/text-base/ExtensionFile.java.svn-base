package com.ecological.extensionesfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.ecological.paper.usuario.Usuario;

@Entity
@Table(name = "extensionfile")
public class ExtensionFile implements Serializable {
	@TableGenerator(name = "extensiones_Id_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "extensiones_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "extensiones_Id_Gen")
	private Long codigo;

	private boolean status;
	private String extension;
	private String mimeType;
	@Transient
	private boolean delete;

	@OneToMany(mappedBy = "extensionFile")
	private Collection<ExtensionFileHijos> extensionFileHijos = new ArrayList<ExtensionFileHijos>();

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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Collection<ExtensionFileHijos> getExtensionFileHijos() {
		return extensionFileHijos;
	}

	public void setExtensionFileHijos(
			Collection<ExtensionFileHijos> extensionFileHijos) {
		this.extensionFileHijos = extensionFileHijos;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean equals(Object objeto) {
		try {
			 if  (objeto!=null && objeto instanceof ExtensionFile)  {
		        	ExtensionFile p =(ExtensionFile)objeto;
		            if (p!=null && (p.getCodigo()-this.codigo==0)){
		                return true;
		            }else
		                return false;
		            
		        }else
		            return false;
		} catch (Exception e) {
			 return false;
		}

	}
}
