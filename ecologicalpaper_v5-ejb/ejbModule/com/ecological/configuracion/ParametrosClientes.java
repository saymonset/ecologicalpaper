package com.ecological.configuracion;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ecological.paper.tree.Tree;
@Entity
@Table(name="parametrosclientes")
public class ParametrosClientes implements Serializable {
	@TableGenerator(name = "ConfiguracionCliente_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "ConfiguracionCliente_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ConfiguracionCliente_Gen")
	private Long codigo;
	
	
	 @ManyToOne
	    @JoinColumn(name="empresa")
	    private Tree empresa;
	
	private byte[] imagen;
	private String nombre;
	@Column(name = "FECHACREADO")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaCreado;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	 
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public java.util.Date getFechaCreado() {
		return fechaCreado;
	}
	public void setFechaCreado(java.util.Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}
	public Tree getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Tree empresa) {
		this.empresa = empresa;
	}

}
