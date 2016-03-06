package com.ecological.spotlight;

import java.io.Serializable;

 

public class SpotLightOpcion implements Serializable {

	private static final long serialVersionUID = -6612493193779344736L;
	private String canal;
	private String aplicacion;
	private long id_mmoOpcion;
	private long id_mmoCanalAtencion;
	private long id_aplicacion;
	private long id_opcion;
	private String texto;
	private String PALABRAS_CLAVE;
	private String descripcionSpotLight;

	/**
	 * El ID en bd del spotLight
	 */
	private long id;
	/**
	 * El URL spotLight
	 */
	private String url;

	/**
	 * El etiqueta spotLight
	 */
	private String etiqueta;

	/**
	 * El claves que identifican al registro de un spotLight
	 */
	private String claves;
	/**
	 * Descripcion de un registro del spotLight
	 */
	private String descripcion;

	/**
	 * Titulos de un registro del spotLight
	 */
	private String titulo;
	/**
	 * imagen de un spotLight
	 */
	private ImagenSpotLight imagenSpotLight;
	/**
	 * Imagen ya fomada apuntando al servlet
	 * */
	private String imgSrcServlet;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClaves() {
		return claves;
	}

	public void setClaves(String claves) {
		this.claves = claves;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ImagenSpotLight getImagenSpotLight() {
		return imagenSpotLight;
	}

	public void setImagenSpotLight(ImagenSpotLight imagenSpotLight) {
		this.imagenSpotLight = imagenSpotLight;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public long getId_mmoOpcion() {
		return id_mmoOpcion;
	}

	public void setId_mmoOpcion(long id_mmoOpcion) {
		this.id_mmoOpcion = id_mmoOpcion;
	}

	public long getId_mmoCanalAtencion() {
		return id_mmoCanalAtencion;
	}

	public void setId_mmoCanalAtencion(long id_mmoCanalAtencion) {
		this.id_mmoCanalAtencion = id_mmoCanalAtencion;
	}

	public long getId_aplicacion() {
		return id_aplicacion;
	}

	public void setId_aplicacion(long id_aplicacion) {
		this.id_aplicacion = id_aplicacion;
	}

	public long getId_opcion() {
		return id_opcion;
	}

	public void setId_opcion(long id_opcion) {
		this.id_opcion = id_opcion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPALABRAS_CLAVE() {
		return PALABRAS_CLAVE;
	}

	public void setPALABRAS_CLAVE(String pALABRAS_CLAVE) {
		PALABRAS_CLAVE = pALABRAS_CLAVE;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getImgSrcServlet() {
		return imgSrcServlet;
	}

	public void setImgSrcServlet(String imgSrcServlet) {
		this.imgSrcServlet = imgSrcServlet;
	}

	public String getDescripcionSpotLight() {
		return descripcionSpotLight;
	}

	public void setDescripcionSpotLight(String descripcionSpotLight) {
		this.descripcionSpotLight = descripcionSpotLight;
	}

}
