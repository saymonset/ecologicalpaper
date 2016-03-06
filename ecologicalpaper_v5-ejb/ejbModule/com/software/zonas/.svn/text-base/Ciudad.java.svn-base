/*
 * Ciudad.java
 *
 * Created on 18 de febrero de 2008, 08:45 PM
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
 * Entity class Ciudad
 *
 * @author simon
 */
@Entity
public class Ciudad implements Serializable {
    
    @TableGenerator(
    name="ciudad",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="ciudad_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ciudad")
    private Long  codigo;
     
    @ManyToOne
    @JoinColumn(name="ESTADO")
   private Estado estado;
   private String nombre;
   @Transient
   private boolean delete;
   private boolean status;    
   @Column(name="DESCRIPCION", length=4000)
    private String Descripcion;
   @OneToMany(mappedBy="ciudad")
    private Collection<Cliente_EUJ> cliente_eujciudad= new ArrayList<Cliente_EUJ>();

  
    
    /** Creates a new instance of Ciudad */
    public Ciudad() {
    }
    
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Ciudad)  {
            Ciudad p =(Ciudad)objeto;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

   
    public Collection<Cliente_EUJ> getCliente_eujciudad() {
        return cliente_eujciudad;
    }

    public void setCliente_eujciudad(Collection<Cliente_EUJ> cliente_eujciudad) {
        this.cliente_eujciudad = cliente_eujciudad;
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
