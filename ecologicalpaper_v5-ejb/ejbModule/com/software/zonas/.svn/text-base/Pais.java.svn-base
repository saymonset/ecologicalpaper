/*
 * Pais.java
 *
 * Created on 18 de febrero de 2008, 08:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.software.zonas;

import com.software.eujovans.clientes.Cliente_EUJ;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Pais
 *
 * @author simon
 */
@Entity
public class Pais implements Serializable {
    @TableGenerator(
    name="pais",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="pais_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="pais")
    private Long  codigo;
    private String nombre;
    @OneToMany(mappedBy="pais")
    private Collection<Estado> estados = new ArrayList<Estado>();
    @OneToMany(mappedBy="pais")
    private Collection<Cliente_EUJ> cliente_eujpais = new ArrayList<Cliente_EUJ>();
    @Transient
    private boolean  delete;
    private boolean status;
    @Column(name="DESCRIPCION", length=4000)
    private String Descripcion;
    
    
    
    /** Creates a new instance of Pais */
    public Pais() {
    }
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Pais)  {
            Pais p =(Pais)objeto;
            if (p!=null && (p.getCodigo()-this.codigo==0)){
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
    
    
    public Collection<Cliente_EUJ> getCliente_eujpais() {
        return cliente_eujpais;
    }
    
    public void setCliente_eujpais(Collection<Cliente_EUJ> cliente_eujpais) {
        this.cliente_eujpais = cliente_eujpais;
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
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

 
    
    
    
    
}
