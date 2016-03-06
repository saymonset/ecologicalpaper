/*
 * Factura.java
 *
 * Created on 18 de febrero de 2008, 08:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.software.eujovans.clientes;

import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Factura
 *
 * @author simon
 */
@Entity
public class Factura implements Serializable {
    
    @TableGenerator(
    name="factura_EUJ",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="factura_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_EUJ")
    private Long  codigo;
    
    @ManyToOne
    @JoinColumn(name="DESTINATARIO")
    private Cliente_EUJ destinatario;
    
    @ManyToOne
    @JoinColumn(name="REMITENTE")
    private Cliente_EUJ remitente;
    
   
    
    
    private Long  numero_entrega;
    
    @Column(name="FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha;
    
    @Transient
    private java.util.Date fechaHasta;
    
    @Column(name="FECHAONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechaonly;
    
    @Column(name="FECHAEMITIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaemitido;
    
    @Transient
    private java.util.Date fechaemitidoHasta;
    
    @Column(name="FECHAEMITIDOONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechaemitidoonly;
    @Transient
    private String fechaemitidoStr;
    
    @Column(name="FECHACONFIRMAENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaconfirmaentrega;
    
    @Transient
    private java.util.Date fechaconfirmaentregaHasta;
    
    
    
    @Column(name="FECHACONFIRMAENTREGAONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechaconfirmaentregaonly;
    
    
    @Column(name="FECHAPAGADO")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechapagado;
    
    @Transient
    private java.util.Date fechapagadoHasta;
    
    @Column(name="FECHAPAGADOONLY")
    @Temporal(TemporalType.DATE)
    private java.util.Date fechapagadoonly;
    
    private String fechapagadoStr;
    
    private String fechaconfirmaentregaStr;
    
    
    @Transient
    private String fechaStr;
    
    @Transient 
    private boolean reporte;
    
    private String factura;
    private long cant_bultos;
    private double valor_decl;
    
    private double flete_kg_vol;
    private double porct_flete_kg_vol;
    private boolean sw_flete_kg_vol;
    
    private double seguro;
    private double porct_seguro;
    private boolean sw_seguro;
    
    private double iva;
    private double porct_iva;
    private boolean sw_iva;
    
    private double total_fletes;
    
    
    private boolean status;
    private int estado;
    
    @Transient
    private String estadoStr;
    
    
    
    
    @ManyToOne
    @JoinColumn(name="USUARIO")
    private Usuario chofer;
    
    @Transient
    private boolean delete;
    private boolean credito;
    @Transient
    private String creditoStr;
    
    
    
    /** Creates a new instance of Factura */
    public Factura() {
    }
    
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Factura)  {
            Factura p =(Factura)objeto;
            if (p.getCodigo()-this.getCodigo()==0){
                return true;
            }else
                return false;
            
        }else
            return false;
    }
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    
    
    public Long getNumero_entrega() {
        return numero_entrega;
    }
    
    public void setNumero_entrega(Long numero_entrega) {
        this.numero_entrega = numero_entrega;
    }
    
    public java.util.Date getFecha() {
        return fecha;
    }
    
    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }
    
    public String getFactura() {
        return factura;
    }
    
    public void setFactura(String factura) {
        this.factura = factura;
    }
    
    public long getCant_bultos() {
        return cant_bultos;
    }
    
    public void setCant_bultos(long cant_bultos) {
        this.cant_bultos = cant_bultos;
    }
    
    public double getValor_decl() {
        return valor_decl;
    }
    
    public void setValor_decl(double valor_decl) {
        this.valor_decl = valor_decl;
    }
    
    
    
    
    public double getIva() {
        return iva;
    }
    
    public void setIva(double iva) {
        this.iva = iva;
    }
    
    
    
    public Cliente_EUJ getDestinatario() {
        return destinatario;
    }
    
    public void setDestinatario(Cliente_EUJ destinatario) {
        this.destinatario = destinatario;
    }
    
    public Cliente_EUJ getRemitente() {
        return remitente;
    }
    
    public void setRemitente(Cliente_EUJ remitente) {
        this.remitente = remitente;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public boolean isDelete() {
        return delete;
    }
    
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    
    public boolean isCredito() {
        return credito;
    }
    
    public void setCredito(boolean credito) {
        this.credito = credito;
    }
    
    public String getFechaStr() {
        return fechaStr;
    }
    
    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }
    
    public int getEstado() {
        return estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstadoStr() {
        return estadoStr;
    }
    
    public void setEstadoStr(String estadoStr) {
        this.estadoStr = estadoStr;
    }
    
    public Usuario getChofer() {
        return chofer;
    }
    
    public void setChofer(Usuario chofer) {
        this.chofer = chofer;
    }
    
    
    public String getCreditoStr() {
        return creditoStr;
    }
    
    public void setCreditoStr(String creditoStr) {
        this.creditoStr = creditoStr;
    }
    
    public java.util.Date getFechaemitido() {
        return fechaemitido;
    }
    
    public void setFechaemitido(java.util.Date fechaemitido) {
        this.fechaemitido = fechaemitido;
    }
    
    public String getFechaemitidoStr() {
        return fechaemitidoStr;
    }
    
    public void setFechaemitidoStr(String fechaemitidoStr) {
        this.fechaemitidoStr = fechaemitidoStr;
    }
    
    public java.util.Date getFechaconfirmaentrega() {
        return fechaconfirmaentrega;
    }
    
    public void setFechaconfirmaentrega(java.util.Date fechaconfirmaentrega) {
        this.fechaconfirmaentrega = fechaconfirmaentrega;
    }
    
    public String getFechaconfirmaentregaStr() {
        return fechaconfirmaentregaStr;
    }
    
    public void setFechaconfirmaentregaStr(String fechaconfirmaentregaStr) {
        this.fechaconfirmaentregaStr = fechaconfirmaentregaStr;
    }
    
    public java.util.Date getFechapagado() {
        return fechapagado;
    }
    
    public void setFechapagado(java.util.Date fechapagado) {
        this.fechapagado = fechapagado;
    }
    
    public String getFechapagadoStr() {
        return fechapagadoStr;
    }
    
    public void setFechapagadoStr(String fechapagadoStr) {
        this.fechapagadoStr = fechapagadoStr;
    }
    
    public java.util.Date getFechaonly() {
        
        return fechaonly;
    }
    
    public void setFechaonly(java.util.Date fechaonly) {
        
        this.fechaonly = fechaonly;
    }
    
    public java.util.Date getFechaemitidoonly() {
        return fechaemitidoonly;
    }
    
    public void setFechaemitidoonly(java.util.Date fechaemitidoonly) {
        this.fechaemitidoonly = fechaemitidoonly;
    }
    
    public java.util.Date getFechaconfirmaentregaonly() {
        return fechaconfirmaentregaonly;
    }
    
    public void setFechaconfirmaentregaonly(java.util.Date fechaconfirmaentregaonly) {
        this.fechaconfirmaentregaonly = fechaconfirmaentregaonly;
    }
    
    public java.util.Date getFechapagadoonly() {
        return fechapagadoonly;
    }
    
    public void setFechapagadoonly(java.util.Date fechapagadoonly) {
        this.fechapagadoonly = fechapagadoonly;
    }
    public double getTotal_fletes    (){
        return total_fletes;
    }

    public void setTotal_fletes(double total_fletes){
        this.total_fletes=total_fletes;
    }

    public double getFlete_kg_vol() {
        return flete_kg_vol;
    }

    public void setFlete_kg_vol(double flete_kg_vol) {
        this.flete_kg_vol = flete_kg_vol;
    }

    public double getPorct_flete_kg_vol() {
        
        return porct_flete_kg_vol;
    }

    public void setPorct_flete_kg_vol(double porct_flete_kg_vol) {
        this.porct_flete_kg_vol = porct_flete_kg_vol;
    }

    public boolean isSw_flete_kg_vol() {
        return sw_flete_kg_vol;
    }

    public void setSw_flete_kg_vol(boolean sw_flete_kg_vol) {
        this.sw_flete_kg_vol = sw_flete_kg_vol;
    }

   

    public double getPorct_seguro() {
        return porct_seguro;
    }

    public void setPorct_seguro(double porct_seguro) {
        this.porct_seguro = porct_seguro;
    }

    public boolean isSw_seguro() {
        return sw_seguro;
    }

    public void setSw_seguro(boolean sw_seguro) {
        this.sw_seguro = sw_seguro;
    }

    public double getPorct_iva() {
        return porct_iva;
    }

    public void setPorct_iva(double porct_iva) {
        this.porct_iva = porct_iva;
    }

    public boolean isSw_iva() {
        return sw_iva;
    }

    public void setSw_iva(boolean sw_iva) {
        this.sw_iva = sw_iva;
    }

    public double getSeguro() {
        return seguro;
    }

    public void setSeguro(double seguro) {
        this.seguro = seguro;
    }

    public java.util.Date getFechaconfirmaentregaHasta() {
        return fechaconfirmaentregaHasta;
    }

    public void setFechaconfirmaentregaHasta(java.util.Date fechaconfirmaentregaHasta) {
        this.fechaconfirmaentregaHasta = fechaconfirmaentregaHasta;
    }

    public java.util.Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(java.util.Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public java.util.Date getFechaemitidoHasta() {
        return fechaemitidoHasta;
    }

    public void setFechaemitidoHasta(java.util.Date fechaemitidoHasta) {
        this.fechaemitidoHasta = fechaemitidoHasta;
    }

    public java.util.Date getFechapagadoHasta() {
        return fechapagadoHasta;
    }

    public void setFechapagadoHasta(java.util.Date fechapagadoHasta) {
        this.fechapagadoHasta = fechapagadoHasta;
    }

    public boolean isReporte() {
        return reporte;
    }

    public void setReporte(boolean reporte) {
        this.reporte = reporte;
    }

   

    
}
