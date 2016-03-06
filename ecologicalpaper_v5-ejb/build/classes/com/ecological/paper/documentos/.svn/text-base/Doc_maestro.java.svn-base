/*
 * Doc_maestro.java
 *
 * Created on July 25, 2007, 8:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.documentos;

import com.ecological.paper.subversion.SvnRutasRelativasFiles;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Entity class Doc_maestro
 * 
 * @author simon
 */
@Entity
@Table(name = "DOC_MAESTRO")
public class Doc_maestro implements Serializable {
	@TableGenerator(name = "Doc_maestro_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "DOC_MAESTRO_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Doc_maestro_Gen")
	private Long codigo;

	private String nombre;
	private boolean status;
	@Transient
	private String prefijo;

	@ManyToOne
	@JoinColumn(name = "USUARIO_CREADOR")
	private Usuario usuario_creador;

	@ManyToOne
	@JoinColumn(name = "empresa")
	private Tree empresa;

	private String consecutivo;

	private String busquedakeys;

	private long numRegistrosHechos;

	@Column(name = "FECHA_CREADO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_creado;

	@Transient
	private String fecha_mostrar;

	@Column(name = "FECHA_CREADO_ONLY")
	@Temporal(TemporalType.DATE)
	private java.util.Date fechaCreadoOnly;

	@ManyToOne
	@JoinColumn(name = "DOC_TIPO")
	private Doc_tipo doc_tipo;

	// aqui sabemos si el documento viene de un svn
	private boolean swSVN;
	@Transient
	private String tipoDocumento;
	@Transient
	private String area;

	@OneToMany(mappedBy = "doc_maestro")
	private Collection<Doc_detalle> doc_detalles = new ArrayList<Doc_detalle>();

	@OneToMany(mappedBy = "doc_rel1")
	private Collection<Doc_relacionados> doc_rel1 = new ArrayList<Doc_relacionados>();

	@OneToMany(mappedBy = "doc_rel2")
	private Collection<Doc_relacionados> doc_rel2 = new ArrayList<Doc_relacionados>();

	@OneToMany(mappedBy = "doc_maestro")
	private Collection<Doc_historicoActivoMaestro> doc_historicoActivoMaestros = new ArrayList<Doc_historicoActivoMaestro>();

	@ManyToOne
	@JoinColumn(name = "TREE")
	private Tree tree;

	@OneToMany(mappedBy = "doc_maestro")
	private Collection<SvnRutasRelativasFiles> svnRutasRelativasFiles = new ArrayList<SvnRutasRelativasFiles>();

	private boolean publico;

	@Transient
	private String pruebaCampoNoSale;

	@Transient
	private Doc_detalle doc_detalle;

	/** Creates a new instance of Doc_maestro */
	public Doc_maestro() {
	}

	public Doc_maestro(Doc_maestro d) {
		this.busquedakeys = d.getBusquedakeys();
		this.consecutivo = d.getConsecutivo();
		this.doc_detalles = d.getDoc_detalles();
		this.doc_rel1 = d.getDoc_rel1();
		this.doc_rel2 = d.getDoc_rel2();
		this.doc_tipo = d.getDoc_tipo();
		this.fecha_creado = d.getFecha_creado();
		this.nombre = d.getNombre();
		this.pruebaCampoNoSale = d.getPruebaCampoNoSale();
		this.publico = d.isPublico();
		this.tree = d.getTree();
		this.usuario_creador = d.getUsuario_creador();
	}

	public Doc_maestro(Doc_maestro d, String vacio) {
		this.numRegistrosHechos = d.getNumRegistrosHechos();
		this.busquedakeys = d.getBusquedakeys();
		this.consecutivo = d.getConsecutivo();
		this.doc_detalles = d.getDoc_detalles();
		this.doc_tipo = d.getDoc_tipo();
		this.fecha_creado = d.getFecha_creado();
		this.nombre = d.getNombre();
		this.pruebaCampoNoSale = d.getPruebaCampoNoSale();
		this.publico = d.isPublico();
		// this.tree=d.getTree();
		this.usuario_creador = d.getUsuario_creador();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getBusquedakeys() {
		return busquedakeys;
	}

	public void setBusquedakeys(String busquedakeys) {
		this.busquedakeys = busquedakeys;
	}

	public Date getFecha_creado() {
		return fecha_creado;
	}

	public void setFecha_creado(Date fecha_creado) {
		this.fecha_creado = fecha_creado;
	}

	public boolean equals(Object objeto) {
		if (objeto != null && objeto instanceof Doc_maestro) {
			Doc_maestro p = (Doc_maestro) objeto;
			if (p.getCodigo() - this.getCodigo() == 0) {
				return true;
			} else
				return false;

		} else
			return false;

	}

	public Collection<Doc_relacionados> getDoc_rel1() {
		return doc_rel1;
	}

	public void setDoc_rel1(Collection<Doc_relacionados> doc_rel1) {
		this.doc_rel1 = doc_rel1;
	}

	public Collection<Doc_relacionados> getDoc_rel2() {
		return doc_rel2;
	}

	public void setDoc_rel2(Collection<Doc_relacionados> doc_rel2) {
		this.doc_rel2 = doc_rel2;
	}

	public Collection<Doc_detalle> getDoc_detalles() {
		return doc_detalles;
	}

	public void setDoc_detalles(Collection<Doc_detalle> doc_detalles) {
		this.doc_detalles = doc_detalles;
	}

	public Usuario getUsuario_creador() {
		return usuario_creador;
	}

	public void setUsuario_creador(Usuario usuario_creador) {
		this.usuario_creador = usuario_creador;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public String getPruebaCampoNoSale() {
		return pruebaCampoNoSale;
	}

	public void setPruebaCampoNoSale(String pruebaCampoNoSale) {
		this.pruebaCampoNoSale = pruebaCampoNoSale;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Doc_detalle getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public long getNumRegistrosHechos() {
		return numRegistrosHechos;
	}

	public void setNumRegistrosHechos(long numRegistrosHechos) {
		this.numRegistrosHechos = numRegistrosHechos;
	}

	public String getFecha_mostrar() {

		if (fecha_creado != null) {
			fecha_mostrar = Utilidades.sdfShow.format(fecha_creado);
		}

		return fecha_mostrar;

	}

	public void setFecha_mostrar(String fecha_mostrar) {
		this.fecha_mostrar = fecha_mostrar;
	}

	public Collection<Doc_historicoActivoMaestro> getDoc_historicoActivoMaestros() {
		return doc_historicoActivoMaestros;
	}

	public void setDoc_historicoActivoMaestros(
			Collection<Doc_historicoActivoMaestro> doc_historicoActivoMaestros) {
		this.doc_historicoActivoMaestros = doc_historicoActivoMaestros;
	}

	public java.util.Date getFechaCreadoOnly() {
		return fechaCreadoOnly;
	}

	public void setFechaCreadoOnly(java.util.Date fechaCreadoOnly) {
		this.fechaCreadoOnly = fechaCreadoOnly;
	}

	/**
	 * @return the empresa
	 */
	public Tree getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Tree empresa) {
		this.empresa = empresa;
	}

	public boolean isSwSVN() {
		return swSVN;
	}

	public void setSwSVN(boolean swSVN) {
		this.swSVN = swSVN;
	}

	public Collection<SvnRutasRelativasFiles> getSvnRutasRelativasFiles() {
		return svnRutasRelativasFiles;
	}

	public void setSvnRutasRelativasFiles(
			Collection<SvnRutasRelativasFiles> svnRutasRelativasFiles) {
		this.svnRutasRelativasFiles = svnRutasRelativasFiles;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String toString() {

		String st = "";
		if (this.getConsecutivo() != null) {
			st += this.getConsecutivo();
		}

		if (this.getNombre() != null) {
			st += "->" + this.getNombre();
		}
		return st;

	}

}
