package com.ecological.paper.cliente.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean(name = "file1")
@SessionScoped
public class File1 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long Id;
	private String Name;
	private String nombre;
	private long longitud;
	private String mime;
	private long length;
	private byte[] data;

	
	
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
		int extDot = name.lastIndexOf('.');
		if(extDot > 0){
			String extension = name.substring(extDot +1);
			if("bmp".equals(extension)){
				mime="image/bmp";
			} else if("jpg".equals(extension)){
				mime="image/jpeg";
			} else if("gif".equals(extension)){
				mime="image/gif";
			} else if("png".equals(extension)){
				mime="image/png";
			} else {
				mime = "image/unknown";
			}
		}
	}
	
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	
	public String getMime(){
		return mime;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getLongitud() {
		return longitud;
	}
	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}

     
	 
}
 