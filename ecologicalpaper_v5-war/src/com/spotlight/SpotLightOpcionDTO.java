package com.spotlight;

import java.io.Serializable;
import java.util.Arrays;

public class SpotLightOpcionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id_mmoOpcion de la tabla MMO_OPCION
	 */
	private long id_mmoOpcion;
	/**
	 * id_mmoCanalAtencion de la tabla MMO_OPCION
	 */
	private long id_mmoCanalAtencion;
	/**
	 * id_aplicacion de la tabla MMO_OPCION
	 */
	private long id_aplicacion;
	/**
	 * id_opcion de la tabla MMO_OPCION
	 */
	private long id_opcion;
	/**
	 * texto de la tabla MMO_OPCION
	 */
	private String texto;
	/**
	 * PALABRAS_CLAVE de la tabla MMO_OPCION
	 */
	private String PALABRAS_CLAVE;

	/**
	 * Trae el archivo en flujo de bytes
	 */
	private byte[] IMAGEN_byte;
	
	/**
	 * Trae el archivo en flujo de bytes
	 */
	private String MIME_TYPE_IMAGEN;
	
	
	/**
	 * Descripcion Larga
	 */
	private String descripcion;
	/**
	 * Descripcion corta de un registro del spotLight
	 */
	private String descripcionSpotLight;
 
	/**
	 * El URL spotLight
	 */
	private String url;
	
	/**
	 * El etiqueta spotLight
	 */
	private String etiqueta;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
 

	public String getMIME_TYPE_IMAGEN() {
		return MIME_TYPE_IMAGEN;
	}

	public void setMIME_TYPE_IMAGEN(String mIME_TYPE_IMAGEN) {
		MIME_TYPE_IMAGEN = mIME_TYPE_IMAGEN;
	}

	public byte[] getIMAGEN_byte() {
		return IMAGEN_byte;
	}

	public void setIMAGEN_byte(byte[] iMAGEN_byte) {
		IMAGEN_byte = iMAGEN_byte;
	}

	@Override
	public String toString() {
		return "SpotLightOpcionDTO [id_mmoOpcion=" + id_mmoOpcion
				+ ", id_mmoCanalAtencion=" + id_mmoCanalAtencion
				+ ", id_aplicacion=" + id_aplicacion + ", id_opcion="
				+ id_opcion + ", texto=" + texto + ", PALABRAS_CLAVE="
				+ PALABRAS_CLAVE + ", IMAGEN_byte="
				+ Arrays.toString(IMAGEN_byte) + ", MIME_TYPE_IMAGEN="
				+ MIME_TYPE_IMAGEN + ", descripcion=" + descripcion + ", url="
				+ url + ", etiqueta=" + etiqueta + "]";
	}

	public String getDescripcionSpotLight() {
		return descripcionSpotLight;
	}

	public void setDescripcionSpotLight(String descripcionSpotLight) {
		this.descripcionSpotLight = descripcionSpotLight;
	}
 
 
  
}
