package com.ecological.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SubFlowReporte {
	private int id;
	private String flujo;
	private String nombre;
	private String apellido;
	private String cargo;
	private String comentario;
	private String firma;
	private List<SubFlowReporte> subFlows = new ArrayList<SubFlowReporte>();

	public SubFlowReporte(int id, String flujo, String nombre, String apellido,
			String cargo, String comentario,String firma) {
		super();
		this.flujo = flujo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cargo = cargo;
		this.comentario = comentario;
		this.firma = firma;
		this.id = id;
	}

	public String getFlujo() {
		return flujo;
	}

	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SubFlowReporte> getSubFlows() {
		return subFlows;
	}

	public void setSubFlows(List<SubFlowReporte> subFlows) {
		this.subFlows = subFlows;
	}

	public void addSubFlows(SubFlowReporte subFlow) {
		this.subFlows.add(subFlow);
	}

	public JRDataSource getSubFlowsDS() {
		return new JRBeanCollectionDataSource(subFlows);
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

}
