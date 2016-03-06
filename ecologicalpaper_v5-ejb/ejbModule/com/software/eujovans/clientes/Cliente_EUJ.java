/*
 * Cliente_EUJ.java
 *
 * Created on 18 de febrero de 2008, 08:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.software.eujovans.clientes;

import com.software.zonas.Ciudad;
import com.software.zonas.Estado;
import com.software.zonas.Pais;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Cliente_EUJ
 *
 * @author simon
 */
@Entity
public class Cliente_EUJ implements Serializable {
    @TableGenerator(
    name="Cliente_EUJ",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="clienteuj_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Cliente_EUJ")
    private Long  codigo;
    
   
    
    
    private String nombre;
    @ManyToOne
    @JoinColumn(name="PAIS")
    private Pais pais;
    @ManyToOne
    @JoinColumn(name="ESTADO")
    private Estado estado;
    @ManyToOne
    @JoinColumn(name="CIUDAD")
    private Ciudad ciudad;
    
    private String telefono;
    @Column(name="DIRECCION", length=4000)
    private String direccion;
    @Transient
    private boolean delete;
    private boolean eujovans;
    @Transient
    private String eujovansStr;
    private boolean status;
    
    @OneToMany(mappedBy="destinatario")
    private Collection<Factura> destinatarios= new ArrayList<Factura>();
    
    @OneToMany(mappedBy="remitente")
    private Collection<Factura> remitentes= new ArrayList<Factura>();
    
    /** Creates a new instance of Cliente_EUJ */
    public Cliente_EUJ() {
    }
    
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Cliente_EUJ)  {
            Cliente_EUJ p =(Cliente_EUJ)objeto;
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
    
    public boolean isDelete() {
        return delete;
    }
    
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Ciudad getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Collection<Factura> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(Collection<Factura> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Collection<Factura> getRemitentes() {
        return remitentes;
    }

    public void setRemitentes(Collection<Factura> remitentes) {
        this.remitentes = remitentes;
    }

    public boolean isEujovans() {
        return eujovans;
    }

    public void setEujovans(boolean eujovans) {
        this.eujovans = eujovans;
    }

    public String getEujovansStr() {
        return eujovansStr;
    }

    public void setEujovansStr(String eujovansStr) {
        this.eujovansStr = eujovansStr;
    }

    
    
}
