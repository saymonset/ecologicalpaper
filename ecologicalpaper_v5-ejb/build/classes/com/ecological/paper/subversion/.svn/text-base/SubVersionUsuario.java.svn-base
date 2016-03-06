package com.ecological.paper.subversion;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

@Entity
@Table(name = "subversionusuario")
public class SubVersionUsuario implements Serializable {
	@TableGenerator(name = "UsuarioSubversion_Id_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "UsuarioSubversion_Id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UsuarioSubversion_Id_Gen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	@Column(name = "urlsubversion", length = 4000)
	private String urlsubversion;
	private String urlsubversionUpload;
	@Transient
	private String urlsubversionUploadAutomatico;
	private String usuariosubversion;
	private String passwordsubversion;
	@Transient
	private String filePath;
	@Transient
	private String comentario;
	@Transient
	private String dir;
	@Transient
	private List<String> filePaths;
	@Transient
	private List<Repositorio> repositorioLst;

	private boolean status = true;
	@Transient
	private String extensionFiltrar = "";
	@Transient
	private File file;
	@Transient
	private String nombreZip;
	@Transient
	private String busquedakeys;

	// esto no c guarda en bd, por eso siempre lo inicializamos
	@Transient
	private long mostrarLogSVN = Utilidades.getMinRegisterMostrar();
	@Transient
	private boolean swBusquedaRecursiva;

	@Transient
	private List<File> files;

	@Transient
	private String mensaje;

	@Transient
	private long version = -1; // -1 en subversion , obtiene la ultima
								// version...
	@Transient
	private long startRevision = 0; // -1 en subversion , obtiene la ultima
									// version...
	@Transient
	private Flow_ParticipantesAttachment flow_ParticipantesAttachment;
	@Transient
	private Doc_maestro doc_maestro;
	@Transient
	private String urlarchivorelativoUpload;
	
	@Transient
	private String numeroRevision;

	@Transient
	boolean swChequeaRevision=false;
	@Transient
	private boolean swExiste = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUrlsubversion() {
		return urlsubversion;
	}

	public void setUrlsubversion(String urlsubversion) {
		this.urlsubversion = urlsubversion;
	}

	public String getUsuariosubversion() {
		return usuariosubversion;
	}

	public void setUsuariosubversion(String usuariosubversion) {
		this.usuariosubversion = usuariosubversion;
	}

	public String getPasswordsubversion() {
		return passwordsubversion;
	}

	public void setPasswordsubversion(String passwordsubversion) {
		this.passwordsubversion = passwordsubversion;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isSwExiste() {
		return swExiste;
	}

	public void setSwExiste(boolean swExiste) {
		this.swExiste = swExiste;
	}

	public long getVersion() {
		return version;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getExtensionFiltrar() {
		return extensionFiltrar;
	}

	public void setExtensionFiltrar(String extensionFiltrar) {
		this.extensionFiltrar = extensionFiltrar;
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public String getUrlsubversionUpload() {
		return urlsubversionUpload;
	}

	public void setUrlsubversionUpload(String urlsubversionUpload) {
		this.urlsubversionUpload = urlsubversionUpload;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUrlsubversionUploadAutomatico() {
		return urlsubversionUploadAutomatico;
	}

	public void setUrlsubversionUploadAutomatico(
			String urlsubversionUploadAutomatico) {
		this.urlsubversionUploadAutomatico = urlsubversionUploadAutomatico;
	}

	public long getMostrarLogSVN() {
		return mostrarLogSVN;
	}

	public void setMostrarLogSVN(long mostrarLogSVN) {
		this.mostrarLogSVN = mostrarLogSVN;
	}

	public boolean isSwBusquedaRecursiva() {
		return swBusquedaRecursiva;
	}

	public void setSwBusquedaRecursiva(boolean swBusquedaRecursiva) {
		this.swBusquedaRecursiva = swBusquedaRecursiva;
	}

	public long getStartRevision() {
		return startRevision;
	}

	public void setStartRevision(long startRevision) {
		this.startRevision = startRevision;
	}

	public List<Repositorio> getRepositorioLst() {
		return repositorioLst;
	}

	public void setRepositorioLst(List<Repositorio> repositorioLst) {
		this.repositorioLst = repositorioLst;
	}

	public Flow_ParticipantesAttachment getFlow_ParticipantesAttachment() {
		return flow_ParticipantesAttachment;
	}

	public void setFlow_ParticipantesAttachment(
			Flow_ParticipantesAttachment flow_ParticipantesAttachment) {
		this.flow_ParticipantesAttachment = flow_ParticipantesAttachment;
	}

	public Doc_maestro getDoc_maestro() {
		return doc_maestro;
	}

	public void setDoc_maestro(Doc_maestro doc_maestro) {
		this.doc_maestro = doc_maestro;
	}

	public String getUrlarchivorelativoUpload() {
		return urlarchivorelativoUpload;
	}

	public void setUrlarchivorelativoUpload(String urlarchivorelativoUpload) {
		this.urlarchivorelativoUpload = urlarchivorelativoUpload;
	}

	public String getNombreZip() {
		return nombreZip;
	}

	public void setNombreZip(String nombreZip) {
		this.nombreZip = nombreZip;
	}

	public String getBusquedakeys() {
		return busquedakeys;
	}

	public void setBusquedakeys(String busquedakeys) {
		this.busquedakeys = busquedakeys;
	}

	public String getNumeroRevision() {
		return numeroRevision;
	}

	public void setNumeroRevision(String numeroRevision) {
		this.numeroRevision = numeroRevision;
	}

	public boolean isSwChequeaRevision() {
		return swChequeaRevision;
	}

	public void setSwChequeaRevision(boolean swChequeaRevision) {
		this.swChequeaRevision = swChequeaRevision;
	}

}
