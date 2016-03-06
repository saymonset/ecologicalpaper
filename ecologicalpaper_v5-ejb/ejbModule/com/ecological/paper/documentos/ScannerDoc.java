package com.ecological.paper.documentos;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@Entity
public class ScannerDoc implements Serializable {
	@TableGenerator(name = "scannerDoc", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "scannerDoc_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "scannerDoc")
	private Long codigo;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "tree")
	private Tree tree;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private java.util.Date fecha;
	@Column(name = "comentario", length = 4000)
	private String comentario;
	@Transient
	private String fechaStr;
	private Blob data_doc;
	private byte[] data_doc_postgres;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public java.util.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public Blob getData_doc() {
		return data_doc;
	}

	public void setData_doc(Blob data_doc) {
		this.data_doc = data_doc;
	}

	public byte[] getData_doc_postgres() {
		return data_doc_postgres;
	}

	public void setData_doc_postgres(byte[] data_doc_postgres) {
		this.data_doc_postgres = data_doc_postgres;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFechaStr() {
		fechaStr = "";
		if (getFecha() != null) {
			fechaStr = Utilidades.date1.format(getFecha());
		}

		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

}
