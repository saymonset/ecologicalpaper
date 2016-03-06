/*
 * Configuracion.java
 *
 * Created on July 20, 2008, 2:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.configuracion;

import com.ecological.paper.tree.Tree;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Configuracion
 * 
 * @author simon
 */
/*
 * smtpHost=mail.cantv.net smtpPort=25 bdpostgres=1 server=saymon1
 * serverip=127.0.0.1 comprado=0 public String fechaCaduca="2008-09-10"; public
 * String numero_usuarios= "10";
 */
@Entity
@Table(name = "CONFIGURACION")
public class Configuracion implements Serializable {
	@TableGenerator(name = "Configfuracion_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "CONFIGURACION_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Configfuracion_Gen")
	private Long codigo;
	private String endPoint = "http://localhost:8180/axis2/services/StatisticsService"; // mi
																						// primer
																						// llamada
																						// a
																						// webservices
																						// desde
																						// ecological
	private String smtpHost;// encriptado
	private String puerto;// encriptado
	private String smtpUsuario;// encriptado
	private String smtpClave;// encriptado
	@Transient
	private String smtpClaveHidden;
	private boolean bdpostgres;// encriptado
	private String server;// encrptado
	private String serverIp;// encriptado
	private String serverPuerto;
	private String comprado;// encriptado
	private String fechaExpira;
	@Transient
	private java.util.Date fechaCaduca;
	@Transient
	private String adminLdap;
	@Transient
	private String passwordLdap;
	private String numeroUsuarios;// encriptado;
	private String carpetaCompartida;
	private String NombreCliente;
	private String mailCliente;
	private String idiomaBundle;
	private String paisBundle;
	private String ldapactivedirectoryhost;
	private String ldapdominiodc;
	private String ldaporganizacion;
	private String ldapUsuarioAdmin;
	private String ldapPasswordAdmin;
	private boolean swWindows = true;
	@Transient
	private String ldapPasswordAdminHidden;
	@Transient
	private String passwordOcultaSincambio;

	@ManyToOne
	@JoinColumn(name = "empresa")
	private Tree empresa;

	/** Creates a new instance of Configuracion */
	public Configuracion() {
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getComprado() {
		return comprado;
	}

	public void setComprado(String comprado) {
		this.comprado = comprado;
	}

	public String getNumeroUsuarios() {
		return numeroUsuarios;
	}

	public void setNumeroUsuarios(String numeroUsuarios) {
		this.numeroUsuarios = numeroUsuarios;
	}

	public String getCarpetaCompartida() {
		return carpetaCompartida;
	}

	public void setCarpetaCompartida(String carpetaCompartida) {
		this.carpetaCompartida = carpetaCompartida;
	}

	public String getNombreCliente() {
		return NombreCliente;
	}

	public void setNombreCliente(String NombreCliente) {
		this.NombreCliente = NombreCliente;
	}

	public java.util.Date getFechaCaduca() {
		return fechaCaduca;
	}

	public void setFechaCaduca(java.util.Date fechaCaduca) {
		this.fechaCaduca = fechaCaduca;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getFechaExpira() {
		return fechaExpira;
	}

	public void setFechaExpira(String fechaExpira) {
		this.fechaExpira = fechaExpira;
	}

	public boolean isBdpostgres() {
		return bdpostgres;
	}

	public void setBdpostgres(boolean bdpostgres) {
		this.bdpostgres = bdpostgres;
	}

	public String getMailCliente() {
		return mailCliente;
	}

	public void setMailCliente(String mailCliente) {
		this.mailCliente = mailCliente;
	}

	public String getIdiomaBundle() {
		return idiomaBundle;
	}

	public void setIdiomaBundle(String idiomaBundle) {
		this.idiomaBundle = idiomaBundle;
	}

	public String getPaisBundle() {
		return paisBundle;
	}

	public void setPaisBundle(String paisBundle) {
		this.paisBundle = paisBundle;
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

	public String getLdapactivedirectoryhost() {
		return ldapactivedirectoryhost;
	}

	public void setLdapactivedirectoryhost(String ldapactivedirectoryhost) {
		this.ldapactivedirectoryhost = ldapactivedirectoryhost;
	}

	public String getLdapdominiodc() {
		return ldapdominiodc;
	}

	public void setLdapdominiodc(String ldapdominiodc) {
		this.ldapdominiodc = ldapdominiodc;
	}

	public String getAdminLdap() {
		return adminLdap;
	}

	public void setAdminLdap(String adminLdap) {
		this.adminLdap = adminLdap;
	}

	public String getPasswordLdap() {
		return passwordLdap;
	}

	public void setPasswordLdap(String passwordLdap) {
		this.passwordLdap = passwordLdap;
	}

	public String getLdapUsuarioAdmin() {
		return ldapUsuarioAdmin;
	}

	public void setLdapUsuarioAdmin(String ldapUsuarioAdmin) {
		this.ldapUsuarioAdmin = ldapUsuarioAdmin;
	}

	public String getLdapPasswordAdmin() {
		return ldapPasswordAdmin;
	}

	public void setLdapPasswordAdmin(String ldapPasswordAdmin) {
		this.ldapPasswordAdmin = ldapPasswordAdmin;
	}

	public String getLdapPasswordAdminHidden() {
		return ldapPasswordAdminHidden;
	}

	public void setLdapPasswordAdminHidden(String ldapPasswordAdminHidden) {
		this.ldapPasswordAdminHidden = ldapPasswordAdminHidden;
	}

	public String getLdaporganizacion() {
		return ldaporganizacion;
	}

	public void setLdaporganizacion(String ldaporganizacion) {
		this.ldaporganizacion = ldaporganizacion;
	}

	public boolean isSwWindows() {
		return swWindows;
	}

	public void setSwWindows(boolean swWindows) {
		this.swWindows = swWindows;
	}

	public String getPasswordOcultaSincambio() {
		return passwordOcultaSincambio;
	}

	public void setPasswordOcultaSincambio(String passwordOcultaSincambio) {
		this.passwordOcultaSincambio = passwordOcultaSincambio;
	}

	public String getServerPuerto() {
		return serverPuerto;
	}

	public void setServerPuerto(String serverPuerto) {
		this.serverPuerto = serverPuerto;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getSmtpUsuario() {
		return smtpUsuario;
	}

	public void setSmtpUsuario(String smtpUsuario) {
		this.smtpUsuario = smtpUsuario;
	}

	public String getSmtpClave() {
		return smtpClave;
	}

	public void setSmtpClave(String smtpClave) {
		this.smtpClave = smtpClave;
	}

	 
	public String getSmtpClaveHidden() {
		return smtpClaveHidden=smtpClave;
	}

	public void setSmtpClaveHidden(String smtpClaveHidden) {
		this.smtpClaveHidden = smtpClaveHidden;
	}

}
