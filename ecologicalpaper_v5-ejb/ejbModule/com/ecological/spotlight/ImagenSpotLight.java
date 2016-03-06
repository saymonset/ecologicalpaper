package com.ecological.spotlight;

import java.io.Serializable;
import java.sql.Blob;

 

public class ImagenSpotLight implements Serializable {
	private static final long serialVersionUID = 8893587296622779001L;
	/**
	 * El Tipo MIME del archivo de la tarjeta
	 */
	private String tipoImagen;
	private String nombre;
	private byte[] imagen;
	/**
	 * imagen a descargar
	 */
	private Blob imagenBlob;

	public String getTipoImagen() {
		return tipoImagen;
	}

	public void setTipoImagen(String tipoImagen) {
		this.tipoImagen = tipoImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Blob getImagenBlob() {
		return imagenBlob;
	}

	public void setImagenBlob(Blob imagenBlob) {
		this.imagenBlob = imagenBlob;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}