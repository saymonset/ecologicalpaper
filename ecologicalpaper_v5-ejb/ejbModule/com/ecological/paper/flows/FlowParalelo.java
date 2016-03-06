package com.ecological.paper.flows;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class FlowParalelo implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableGenerator(name = "flowParalelo_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "flowParalelo_Gen_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "flowParalelo_Gen")
	private Long codigo;
	private boolean status;
	private String nombre;

	@Transient
	private boolean swAprobacion;

	@Transient
	private boolean swNoStaInAprobacion;

	@Transient
	private boolean swTipoPlantilladocumento;

	@Transient
	private boolean swTipoPlantilladocumentoDelete;

	@ManyToOne
	@JoinColumn(name = "doc_tipo")
	private Doc_tipo doc_tipo;

	@ManyToOne
	@JoinColumn(name = "areaDocumentos")
	private AreaDocumentos areaDocumentos;
	
	private boolean solicituImpresion;
	
	

	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "flow")
	private Flow flow;

	@Transient
	private String fechaCreado;

	@Transient
	private String tipoFlujo;

	@Transient
	private String statusEnQedo;

	@OneToMany(mappedBy = "flowParalelo")
	private Collection<Flow> flows = new ArrayList<Flow>();

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Collection<Flow> getFlows() {
		return flows;
	}

	public void setFlows(Collection<Flow> flows) {
		this.flows = flows;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public boolean isSwAprobacion() {
		return swAprobacion;
	}

	public void setSwAprobacion(boolean swAprobacion) {
		this.swAprobacion = swAprobacion;
	}

	public boolean isSwNoStaInAprobacion() {
		return swNoStaInAprobacion;
	}

	public void setSwNoStaInAprobacion(boolean swNoStaInAprobacion) {
		this.swNoStaInAprobacion = swNoStaInAprobacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(String fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	public String getTipoFlujo() {
		return tipoFlujo;
	}

	public void setTipoFlujo(String tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}

	public String getStatusEnQedo() {
		return statusEnQedo;
	}

	public void setStatusEnQedo(String statusEnQedo) {
		this.statusEnQedo = statusEnQedo;
	}

	public boolean isSwTipoPlantilladocumento() {
		return swTipoPlantilladocumento;
	}

	public void setSwTipoPlantilladocumento(boolean swTipoPlantilladocumento) {
		this.swTipoPlantilladocumento = swTipoPlantilladocumento;
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public boolean isSwTipoPlantilladocumentoDelete() {
		return swTipoPlantilladocumentoDelete;
	}

	public void setSwTipoPlantilladocumentoDelete(
			boolean swTipoPlantilladocumentoDelete) {
		this.swTipoPlantilladocumentoDelete = swTipoPlantilladocumentoDelete;
	}

	public AreaDocumentos getAreaDocumentos() {
		return areaDocumentos;
	}

	public void setAreaDocumentos(AreaDocumentos areaDocumentos) {
		this.areaDocumentos = areaDocumentos;
	}

	public boolean isSolicituImpresion() {
		return solicituImpresion;
	}

	public void setSolicituImpresion(boolean solicituImpresion) {
		this.solicituImpresion = solicituImpresion;
	}

 
}
