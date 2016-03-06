/*
 * Estado.java
 *
 * Created on 18 de febrero de 2008, 08:38 PM
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Estado
 *
 * @author simon
 */
@Entity
public class Estado implements Serializable {
    @TableGenerator(
    name="estado",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="estado_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="estado")
    private Long  codigo;
    @ManyToOne
    @JoinColumn(name="PAIS")
    private Pais pais;
    @Transient
    private boolean delete;
    private String nombre;
   @Column(name="DESCRIPCION", length=4000)
    private String Descripcion;
    @OneToMany(mappedBy="estado")
    private Collection<Ciudad> ciudades= new ArrayList<Ciudad>();
    @OneToMany(mappedBy="estado")
    private Collection<Cliente_EUJ> cliente_eujestado = new ArrayList<Cliente_EUJ>();

    
    
    private boolean status;
    /** Creates a new instance of Estado */
    public Estado() {
    }
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Estado)  {
            Estado p =(Estado)objeto;
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
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    

    public Collection<Cliente_EUJ> getCliente_eujestado() {
        return cliente_eujestado;
    }

    public void setCliente_eujestado(Collection<Cliente_EUJ> cliente_eujestado) {
        this.cliente_eujestado = cliente_eujestado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

   
    
}
