package com.ecological.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReporteDocumentos {
	private int id;
	private String nombre;
	private String areadocumento;
	private String consecutivo;
	private String fecha_mostrar;
	private String doc_estado;
	private String duenio;
	private List<ReporteDocumentos> objetos = new ArrayList<ReporteDocumentos>();

	public ReporteDocumentos(int id, String nombre, String areadocumento,
			String consecutivo, String fecha_mostrar, String doc_estado, String duenio) {
		super();
		this.nombre = nombre;
		this.areadocumento = areadocumento;
		
		this.consecutivo = consecutivo;
		this.fecha_mostrar = fecha_mostrar;
		this.doc_estado = doc_estado;
		this.duenio = duenio;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAreadocumento() {
		return areadocumento;
	}

	public void setAreadocumento(String areadocumento) {
		this.areadocumento = areadocumento;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getFecha_mostrar() {
		return fecha_mostrar;
	}

	public void setFecha_mostrar(String fecha_mostrar) {
		this.fecha_mostrar = fecha_mostrar;
	}

	public String getDoc_estado() {
		return doc_estado;
	}

	public void setDoc_estado(String doc_estado) {
		this.doc_estado = doc_estado;
	}

	public String getDuenio() {
		return duenio;
	}

	public void setDuenio(String duenio) {
		this.duenio = duenio;
	}

	public List<ReporteDocumentos> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<ReporteDocumentos> objetos) {
		this.objetos = objetos;
	}

 

}
