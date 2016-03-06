package com.ecological.paper.subversion;

import java.io.File;
import java.io.Serializable;

public class SvnUpload implements Serializable{
	private boolean swFlowParticipante;
	private long codigo;
	private long codigoParticipante;
	private File file;
	private String nameFile;
	private String  comentarios;
	private String autor;
	private String icono;
	//si se subio el file svn pŕincipal
	private boolean swSVNUpload;
	 private boolean swSVN;
	 private String urlsubversionUpload;
	
	public SvnUpload(){}

	public SvnUpload(boolean swFlowParticipante, long codigo,long codigoParticipante, File file,
			String comentarios, String autor,boolean swSVNUpload,String icono,boolean swSVN,
			String urlsubversionUpload,String nameFile) {
		super();
		this.swFlowParticipante = swFlowParticipante;
		
		this.codigo = codigo;
		this.codigoParticipante=codigoParticipante;
		this.file = file;
		this.comentarios = comentarios;
		this.autor = autor;
		this.swSVNUpload=swSVNUpload;
		this.icono=icono;
		this.swSVN=swSVN;
		this.urlsubversionUpload=urlsubversionUpload;
		this.nameFile=nameFile;
	}
	public boolean isSwFlowParticipante() {
		return swFlowParticipante;
	}
	public void setSwFlowParticipante(boolean swFlowParticipante) {
		this.swFlowParticipante = swFlowParticipante;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public boolean isSwSVNUpload() {
		return swSVNUpload;
	}
	public void setSwSVNUpload(boolean swSVNUpload) {
		this.swSVNUpload = swSVNUpload;
	}

	 
	public long getCodigoParticipante() {
		return codigoParticipante;
	}

	public void setCodigoParticipante(long codigoParticipante) {
		this.codigoParticipante = codigoParticipante;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public boolean isSwSVN() {
		return swSVN;
	}

	public void setSwSVN(boolean swSVN) {
		this.swSVN = swSVN;
	}

	public String getUrlsubversionUpload() {
		return urlsubversionUpload;
	}

	public void setUrlsubversionUpload(String urlsubversionUpload) {
		this.urlsubversionUpload = urlsubversionUpload;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

}
