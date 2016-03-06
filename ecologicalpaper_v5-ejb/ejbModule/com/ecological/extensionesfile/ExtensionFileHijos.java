package com.ecological.extensionesfile;

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

import com.ecological.paper.profesion.Profesion;
@Entity
@Table(name="extensionfilehijos")
public class ExtensionFileHijos implements Serializable {
	@TableGenerator(name = "extensioneshijos_Id_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "extensioneshijos_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "extensioneshijos_Id_Gen")
	private Long codigo;

	private boolean status;
	private String extension;

	@ManyToOne
	@JoinColumn(name = "extensionFile")
	private ExtensionFile extensionFile;
	@Transient
	private boolean delete;

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

	public ExtensionFile getExtensionFile() {
		return extensionFile;
	}

	public void setExtensionFile(ExtensionFile extensionFile) {
		this.extensionFile = extensionFile;
	}

 

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
