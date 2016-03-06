/*

 * Doc_detalle.java
 *
 * Created on July 25, 2007, 8:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.documentos;

import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

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

/**
 * Entity class Doc_detalle
 * 
 * @author simon
 */
@Entity
@Table(name = "DOC_DETALLE")
public class Doc_detalle implements Serializable {

	@TableGenerator(name = "Doc_detalle_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "DOC_DETALLE_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Doc_detalle_Gen")
	private Long codigo;
	@ManyToOne
	@JoinColumn(name = "DOC_MAESTRO")
	private Doc_maestro doc_maestro;
	@ManyToOne
	@JoinColumn(name = "DOC_ESTADO")
	private Doc_estado doc_estado;
	private String mayorVer;
	private String minorVer;
	@Transient
	private boolean mover;

	@Transient
	private Flow flowEditar;
	@Transient
	private boolean swAttachment;

	@Transient
	private boolean swHayFlow;
	@Transient
	private boolean swHayHistoricoFlow;


	@Transient
	private Long origen;
	@Transient
	private boolean toViewComentPublic;

	@Transient
	private String icono;

	@Transient
	private boolean swVigente;

	@Transient
	private boolean swCanDoPublicoIsVigente;

	@Transient
	private boolean swFechaPublicoExpiro;
	@Transient
	private boolean swIsPublicador;

	@Column(name = "DATECAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datecambio;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;
	private Blob data_doc;
	@Transient
	private Blob data;
	private double size_doc;
	private String nameFile;
	private boolean doc_checkout;
	private boolean status;
	@Transient
	private boolean swEstaInFlowToImprimir;
	@Transient
	private boolean swHabilitadoImprimir;
	@Transient
	private String datecambioStr;

	@ManyToOne
	@JoinColumn(name = "DUENIO")
	private Usuario duenio;
	@ManyToOne
	@JoinColumn(name = "MODIFICADOPOR")
	private Usuario modificadoPor;
	@OneToMany(mappedBy = "doc_detalle")
	private Collection<Flow> flujos = new ArrayList<Flow>();
	@OneToMany(mappedBy = "doc_detalle")
	private Collection<FlowsHistorico> historicoFlows = new ArrayList<FlowsHistorico>();

	@OneToMany(mappedBy = "doc_detalle")
	private Collection<Doc_dataPostgres> doc_dataPostgres = new ArrayList<Doc_dataPostgres>();
//
//	@OneToMany(mappedBy = "doc_tipo")
//	private Collection<FlowParalelo> doc_tipos = new ArrayList<FlowParalelo>();

	@OneToMany(mappedBy = "doc_detalle")
	private Collection<Solicitudimpresion> solicitudimpresiones = new ArrayList<Solicitudimpresion>();
	private String contextType;

	@OneToMany(mappedBy = "doc_detalle")
	private Collection<RolesOnlyViewDocPublicados> doc_detalles = new ArrayList<RolesOnlyViewDocPublicados>();

	@OneToMany(mappedBy = "doc_detalle")
	private Collection<PublicadosUsuComent> publicadosUsuComentLst = new ArrayList<PublicadosUsuComent>();

	// para publicar documento
	private boolean publitogrupo;
	private boolean publiMailexpiro;
	@ManyToOne
	@JoinColumn(name = "publicador")
	private Usuario publicador;

	@Column(name = "fechapubli")
	@Temporal(TemporalType.DATE)
	private java.util.Date fechapubli;
	@Transient
	private String fechapubliStr;

	@Column(name = "fechaexpirapubli")
	@Temporal(TemporalType.DATE)
	private java.util.Date fechaexpirapubli;

	@Transient
	private String fechaexpirapubliStr;

	@ManyToOne
	@JoinColumn(name = "areaDocumentos")
	private AreaDocumentos areaDocumentos;

	// fin para publicar documento

	@Transient
	private boolean swHistDoc;
	@Transient
	private boolean swHistFlow;
	@Transient
	private boolean swDiferenciaEntreVersiones;
	@Transient
	private boolean swImprimir;
	@Transient
	private boolean swFirstBorrador;
	@Transient
	private boolean swPuedeRealizarFlujo;
	@Transient
	private boolean swIsObsoleto;
	@Transient
	private boolean swActualizar;
	@Transient
	private boolean swCheckOut;
	@Transient
	private boolean swCopy;
	@Transient
	private boolean swPage;
	@Transient
	private boolean swCute;
	@Transient
	private boolean swUnlocked;
	@Transient
	private boolean swLocked;
	@Transient
	private boolean swLockedwithkey;
	@Transient
	private boolean swFlujoActivo;
	@Transient
	private boolean swVincularDocumento;
	@Transient
	private boolean swPublico;
	@Transient
	private boolean swMover;
	@Transient
	private boolean swRegistro;
	@Transient
	private String usuariosInFlowStr;
	@Transient
	private String publicoStr;
	@Transient
	private String tipoDocumento;
	@Transient
	private Boolean swViewDoc;

	public Doc_detalle() {
	}

	public Doc_detalle(Doc_detalle doc_d, boolean swPuedeRealizarFlujo,
			boolean swIsObsoleto, boolean swActualizar, boolean swCheckOut,
			boolean copy, boolean page, boolean cute, boolean swLocked,
			boolean swLockedwithkey, boolean swUnlocked, boolean swFlujoActivo,
			boolean swVincularDocumento, boolean swPublico, boolean swMover,
			boolean swRegistro, String icono, String usuariosInFlowStr,
			boolean swHistDoc, boolean swHistFlow, String publicoStr) {

		this.areaDocumentos = doc_d.getAreaDocumentos();
		this.swHistDoc = swHistDoc;
		this.swHistFlow = swHistFlow;
		this.codigo = doc_d.getCodigo();
		this.doc_maestro = doc_d.getDoc_maestro();
		this.doc_estado = doc_d.getDoc_estado();

		this.mayorVer = doc_d.getMayorVer();
		this.minorVer = doc_d.getMinorVer();

		this.datecambio = doc_d.getDatecambio();
		this.datecambioStr = Utilidades.sdfShow.format(datecambio);

		this.nameFile = doc_d.getNameFile();
		this.descripcion = doc_d.getDescripcion();
		this.data = doc_d.getData();
		this.size_doc = doc_d.getSize_doc();
		this.doc_checkout = doc_d.isDoc_checkout();
		this.duenio = doc_d.getDuenio();
		this.flujos = doc_d.getFlujos();
		this.contextType = doc_d.getContextType();
		this.modificadoPor = doc_d.getModificadoPor();
		this.icono = icono;

		this.swCopy = copy;
		this.swPage = page;
		this.swCute = cute;
		this.swPuedeRealizarFlujo = swPuedeRealizarFlujo;
		this.swIsObsoleto = swIsObsoleto;
		this.swActualizar = swActualizar;
		this.swCheckOut = swCheckOut;
		this.swLocked = swLocked;
		this.swLockedwithkey = swLockedwithkey;
		this.swUnlocked = swUnlocked;
		this.setSwFlujoActivo(swFlujoActivo);
		this.setSwVincularDocumento(swVincularDocumento);
		this.swPublico = swPublico;
		this.swMover = swMover;
		this.swRegistro = swRegistro;
		this.usuariosInFlowStr = usuariosInFlowStr;
		this.publicoStr = publicoStr;
	}

	public Doc_detalle(Doc_detalle doc_detalle) {
		this.contextType = doc_detalle.getContextType();
		this.data_doc = doc_detalle.getData();
		this.datecambio = doc_detalle.getDatecambio();
		this.descripcion = doc_detalle.getDescripcion();
		this.doc_checkout = doc_detalle.isDoc_checkout();
		this.doc_estado = doc_detalle.getDoc_estado();
		this.doc_maestro = doc_detalle.getDoc_maestro();
		this.duenio = doc_detalle.getDuenio();
		this.flujos = doc_detalle.getFlujos();
		this.historicoFlows = doc_detalle.getHistoricoFlows();
		this.mayorVer = doc_detalle.getMayorVer();
		this.modificadoPor = doc_detalle.getModificadoPor();
		this.minorVer = doc_detalle.getMinorVer();
		this.nameFile = doc_detalle.getNameFile();
		this.size_doc = doc_detalle.getSize_doc();

	}

	public Doc_detalle(Doc_detalle doc_detalle, String vacio) {
		this.contextType = doc_detalle.getContextType();
		this.data_doc = doc_detalle.getData();
		this.datecambio = doc_detalle.getDatecambio();
		this.descripcion = doc_detalle.getDescripcion();

		this.doc_estado = doc_detalle.getDoc_estado();
		this.doc_maestro = doc_detalle.getDoc_maestro();
		this.duenio = doc_detalle.getDuenio();
		this.mayorVer = "1";// doc_detalle.getMayorVer();

		this.minorVer = "0";// doc_detalle.getMinorVer();
		this.nameFile = doc_detalle.getNameFile();
		this.size_doc = doc_detalle.getSize_doc();
		this.areaDocumentos = doc_detalle.getAreaDocumentos();

	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Doc_maestro getDoc_maestro() {
		return doc_maestro;
	}

	public void setDoc_maestro(Doc_maestro doc_maestro) {
		this.doc_maestro = doc_maestro;
	}

	public Doc_estado getDoc_estado() {
		return doc_estado;
	}

	public void setDoc_estado(Doc_estado doc_estado) {
		this.doc_estado = doc_estado;
	}

	public String getMayorVer() {
		return mayorVer;
	}

	public void setMayorVer(String mayorVer) {
		this.mayorVer = mayorVer;
	}

	public String getMinorVer() {
		return minorVer;
	}

	public void setMinorVer(String minorVer) {
		this.minorVer = minorVer;
	}

	public Date getDatecambio() {

		return datecambio;
	}

	public void setDatecambio(Date datecambio) {
		this.datecambio = datecambio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Blob getData() {
		return getData_doc();
	}

	public void setData(Blob data) {
		this.setData_doc(data);
	}

	public Usuario getDuenio() {
		return duenio;
	}

	public void setDuenio(Usuario duenio) {
		this.duenio = duenio;
	}

	public String getContextType() {
		return contextType;
	}

	public void setContextType(String contextType) {
		this.contextType = contextType;
	}

	public boolean equals(Object objeto) {
		if (objeto != null && objeto instanceof Doc_maestro) {
			Doc_detalle p = (Doc_detalle) objeto;
			if (p.getCodigo() - this.codigo == 0) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public Collection<Flow> getFlujos() {
		return flujos;
	}

	public void setFlujos(Collection<Flow> flujos) {
		this.flujos = flujos;
	}

	public double getSize_doc() {
		return size_doc;
	}

	public void setSize_doc(double size_doc) {
		this.size_doc = size_doc;
	}

	public boolean isDoc_checkout() {
		return doc_checkout;
	}

	public void setDoc_checkout(boolean doc_checkout) {
		this.doc_checkout = doc_checkout;
	}

	public Usuario getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Usuario modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Collection<FlowsHistorico> getHistoricoFlows() {
		return historicoFlows;
	}

	public void setHistoricoFlows(Collection<FlowsHistorico> historicoFlows) {
		this.historicoFlows = historicoFlows;
	}

	public boolean isMover() {
		return mover;
	}

	public void setMover(boolean mover) {
		this.mover = mover;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Blob getData_doc() {
		return data_doc;
	}

	public void setData_doc(Blob data_doc) {
		this.data_doc = data_doc;
	}

	public Collection<Doc_dataPostgres> getDoc_dataPostgres() {
		return doc_dataPostgres;
	}

	public void setDoc_dataPostgres(
			Collection<Doc_dataPostgres> doc_dataPostgres) {
		this.doc_dataPostgres = doc_dataPostgres;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}
																																																																																																											
	public Flow getFlowEditar() {
		return flowEditar;
	}

	public void setFlowEditar(Flow flowEditar) {
		this.flowEditar = flowEditar;
	}

//	public Collection<FlowParalelo> getDoc_tipos() {
//		return doc_tipos;
//	}
//
//	public void setDoc_tipos(Collection<FlowParalelo> doc_tipos) {
//		this.doc_tipos = doc_tipos;
//	}

	public Collection<Solicitudimpresion> getSolicitudimpresiones() {
		return solicitudimpresiones;
	}

	public void setSolicitudimpresiones(
			Collection<Solicitudimpresion> solicitudimpresiones) {
		this.solicitudimpresiones = solicitudimpresiones;
	}

	public boolean isPublitogrupo() {
		return publitogrupo;
	}

	public void setPublitogrupo(boolean publitogrupo) {
		this.publitogrupo = publitogrupo;
	}

	public Usuario getPublicador() {
		return publicador;
	}

	public void setPublicador(Usuario publicador) {
		this.publicador = publicador;
	}

	public java.util.Date getFechapubli() {
		return fechapubli;
	}

	public void setFechapubli(java.util.Date fechapubli) {
		this.fechapubli = fechapubli;
	}

	public java.util.Date getFechaexpirapubli() {
		return fechaexpirapubli;
	}

	public void setFechaexpirapubli(java.util.Date fechaexpirapubli) {
		this.fechaexpirapubli = fechaexpirapubli;
	}

	public Collection<RolesOnlyViewDocPublicados> getDoc_detalles() {
		return doc_detalles;
	}

	public void setDoc_detalles(
			Collection<RolesOnlyViewDocPublicados> doc_detalles) {
		this.doc_detalles = doc_detalles;
	}

	public String getFechapubliStr() {
		fechapubliStr = "";
		if (getFechapubli() != null) {
			fechapubliStr = Utilidades.date1.format(getFechapubli());
		}

		return fechapubliStr;
	}

	public void setFechapubliStr(String fechapubliStr) {
		this.fechapubliStr = fechapubliStr;
	}

	public String getFechaexpirapubliStr() {
		fechaexpirapubliStr = "";
		if (getFechaexpirapubli() != null) {
			fechaexpirapubliStr = Utilidades.date1
					.format(getFechaexpirapubli());
		}

		return fechaexpirapubliStr;
	}

	public void setFechaexpirapubliStr(String fechaexpirapubliStr) {
		this.fechaexpirapubliStr = fechaexpirapubliStr;
	}

	public boolean isSwFechaPublicoExpiro() {
		String fecha = Utilidades.date1.format(new Date());
		if (!"".equalsIgnoreCase(getFechaexpirapubliStr())) {
			swFechaPublicoExpiro = getFechaexpirapubliStr().compareTo(fecha) <= 0;
		}
		return swFechaPublicoExpiro;
	}

	public void setSwFechaPublicoExpiro(boolean swFechaPublicoExpiro) {
		this.swFechaPublicoExpiro = swFechaPublicoExpiro;
	}

	public boolean isSwIsPublicador() {

		return swIsPublicador;
	}

	public void setSwIsPublicador(boolean swIsPublicador) {
		this.swIsPublicador = swIsPublicador;
	}

	public boolean isPubliMailexpiro() {
		return publiMailexpiro;
	}

	public void setPubliMailexpiro(boolean publiMailexpiro) {
		this.publiMailexpiro = publiMailexpiro;
	}

	public Collection<PublicadosUsuComent> getPublicadosUsuComentLst() {
		return publicadosUsuComentLst;
	}

	public void setPublicadosUsuComentLst(
			Collection<PublicadosUsuComent> publicadosUsuComentLst) {
		this.publicadosUsuComentLst = publicadosUsuComentLst;
	}

	public AreaDocumentos getAreaDocumentos() {
		return areaDocumentos;
	}

	public void setAreaDocumentos(AreaDocumentos areaDocumentos) {
		this.areaDocumentos = areaDocumentos;
	}

	public Long getOrigen() {
		return origen;
	}

	public void setOrigen(Long origen) {
		this.origen = origen;
	}

	public boolean isSwEstaInFlowToImprimir() {
		return swEstaInFlowToImprimir;
	}

	public void setSwEstaInFlowToImprimir(boolean swEstaInFlowToImprimir) {
		this.swEstaInFlowToImprimir = swEstaInFlowToImprimir;
	}

	public boolean isSwHabilitadoImprimir() {
		return swHabilitadoImprimir;
	}

	public void setSwHabilitadoImprimir(boolean swHabilitadoImprimir) {
		this.swHabilitadoImprimir = swHabilitadoImprimir;
	}

	public boolean isToViewComentPublic() {
		return toViewComentPublic;
	}

	public void setToViewComentPublic(boolean toViewComentPublic) {
		this.toViewComentPublic = toViewComentPublic;
	}

	public String getDatecambioStr() {
		if (datecambio != null) {
			datecambioStr = Utilidades.sdfShow.format(getDatecambio());
		} else if (doc_maestro.getFecha_creado() != null) {
			datecambioStr = Utilidades.sdfShow.format(doc_maestro
					.getFecha_creado());
		}

		return datecambioStr;
	}

	public void setDatecambioStr(String datecambioStr) {
		this.datecambioStr = datecambioStr;
	}

	public boolean isSwHistDoc() {
		return swHistDoc;
	}

	public void setSwHistDoc(boolean swHistDoc) {
		this.swHistDoc = swHistDoc;
	}

	public boolean isSwHistFlow() {
		return swHistFlow;
	}

	public void setSwHistFlow(boolean swHistFlow) {
		this.swHistFlow = swHistFlow;
	}

	public boolean isSwDiferenciaEntreVersiones() {
		return swDiferenciaEntreVersiones;
	}

	public void setSwDiferenciaEntreVersiones(boolean swDiferenciaEntreVersiones) {
		this.swDiferenciaEntreVersiones = swDiferenciaEntreVersiones;
	}

	public boolean isSwImprimir() {
		return swImprimir;
	}

	public void setSwImprimir(boolean swImprimir) {
		this.swImprimir = swImprimir;
	}

	public boolean isSwFirstBorrador() {
		return swFirstBorrador;
	}

	public void setSwFirstBorrador(boolean swFirstBorrador) {
		this.swFirstBorrador = swFirstBorrador;
	}

	public boolean isSwPuedeRealizarFlujo() {
		return swPuedeRealizarFlujo;
	}

	public void setSwPuedeRealizarFlujo(boolean swPuedeRealizarFlujo) {
		this.swPuedeRealizarFlujo = swPuedeRealizarFlujo;
	}

	public boolean isSwIsObsoleto() {
		return swIsObsoleto;
	}

	public void setSwIsObsoleto(boolean swIsObsoleto) {
		this.swIsObsoleto = swIsObsoleto;
	}

	public boolean isSwActualizar() {
		return swActualizar;
	}

	public void setSwActualizar(boolean swActualizar) {
		this.swActualizar = swActualizar;
	}

	public boolean isSwCheckOut() {
		return swCheckOut;
	}

	public void setSwCheckOut(boolean swCheckOut) {
		this.swCheckOut = swCheckOut;
	}

	public boolean isSwCopy() {
		return swCopy;
	}

	public void setSwCopy(boolean swCopy) {
		this.swCopy = swCopy;
	}

	public boolean isSwPage() {
		return swPage;
	}

	public void setSwPage(boolean swPage) {
		this.swPage = swPage;
	}

	public boolean isSwCute() {
		return swCute;
	}

	public void setSwCute(boolean swCute) {
		this.swCute = swCute;
	}

	public boolean isSwUnlocked() {
		return swUnlocked;
	}

	public void setSwUnlocked(boolean swUnlocked) {
		this.swUnlocked = swUnlocked;
	}

	public boolean isSwLocked() {
		return swLocked;
	}

	public void setSwLocked(boolean swLocked) {
		this.swLocked = swLocked;
	}

	public boolean isSwLockedwithkey() {
		return swLockedwithkey;
	}

	public void setSwLockedwithkey(boolean swLockedwithkey) {
		this.swLockedwithkey = swLockedwithkey;
	}

	public boolean isSwFlujoActivo() {
		return swFlujoActivo;
	}

	public void setSwFlujoActivo(boolean swFlujoActivo) {
		this.swFlujoActivo = swFlujoActivo;
	}

	public boolean isSwVincularDocumento() {
		return swVincularDocumento;
	}

	public void setSwVincularDocumento(boolean swVincularDocumento) {
		this.swVincularDocumento = swVincularDocumento;
	}

	public boolean isSwPublico() {
		return swPublico;
	}

	public void setSwPublico(boolean swPublico) {
		this.swPublico = swPublico;
	}

	public boolean isSwMover() {
		return swMover;
	}

	public void setSwMover(boolean swMover) {
		this.swMover = swMover;
	}

	public boolean isSwRegistro() {
		return swRegistro;
	}

	public void setSwRegistro(boolean swRegistro) {
		this.swRegistro = swRegistro;
	}

	public String getUsuariosInFlowStr() {
		return usuariosInFlowStr;
	}

	public void setUsuariosInFlowStr(String usuariosInFlowStr) {
		this.usuariosInFlowStr = usuariosInFlowStr;
	}

	public String getPublicoStr() {
		return publicoStr;
	}

	public void setPublicoStr(String publicoStr) {
		this.publicoStr = publicoStr;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isSwVigente() {
		return swVigente;
	}

	public void setSwVigente(boolean swVigente) {
		this.swVigente = swVigente;
	}

	public boolean isSwCanDoPublicoIsVigente() {
		return swCanDoPublicoIsVigente;
	}

	public void setSwCanDoPublicoIsVigente(boolean swCanDoPublicoIsVigente) {
		this.swCanDoPublicoIsVigente = swCanDoPublicoIsVigente;
	}

	public Boolean getSwViewDoc() {
		return swViewDoc;
	}

	public void setSwViewDoc(Boolean swViewDoc) {
		this.swViewDoc = swViewDoc;
	}

	public boolean isSwHayFlow() {
		return swHayFlow;
	}

	public void setSwHayFlow(boolean swHayFlow) {
		this.swHayFlow = swHayFlow;
	}

	public boolean isSwAttachment() {
		if (icono != null && !"".equalsIgnoreCase(icono)) {
			if (Utilidades.obtenerXdelIcono(icono)) {
				swAttachment = true;
			}

		}
		return swAttachment;
	}

	public void setSwAttachment(boolean swAttachment) {
		this.swAttachment = swAttachment;
	}

	public boolean isSwHayHistoricoFlow() {
		return swHayHistoricoFlow;
	}

	public void setSwHayHistoricoFlow(boolean swHayHistoricoFlow) {
		this.swHayHistoricoFlow = swHayHistoricoFlow;
	}

}
