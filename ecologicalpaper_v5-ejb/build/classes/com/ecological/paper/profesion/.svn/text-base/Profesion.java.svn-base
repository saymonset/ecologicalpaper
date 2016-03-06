/*
 * Profesion.java
 *
 * Created on July 9, 2007, 4:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.profesion;

import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
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
 * Entity class Profesion
 *
 * @author simon
 */
@Entity
public class Profesion implements Serializable {
    
    @TableGenerator(
    name="Profesion_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="PROFESION_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Profesion_Gen")
    private Long codigo;
    
    private boolean status;
    
    @Column(name="NOMBRE")
    private  String nombre;

    @Column(name="DESCRIPCION", length=4000)
    private String Descripcion;
    
    @Transient 
    private boolean  delete;
    
     

      @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;
    
    @OneToMany(mappedBy="profesion")
    private Collection<Usuario> usuarios = new ArrayList<Usuario>();
    /** Creates a new instance of Profesion */
    public Profesion() {
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
    
    public String getDescripcion() {
        return Descripcion;
    }
    
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
       /*public boolean equals(Object o) {
                Departement d = (Departement)o;
                return d.getCode().equals(getCode());
        }*/
    public boolean equals(Object profesion) {
        if  (profesion!=null && profesion instanceof Profesion)  {
            Profesion p =(Profesion)profesion;
            String k1 =p.getCodigo()+"";
            String k2=this.codigo+"";
               
            if (k1.trim().equalsIgnoreCase(k2.trim())){
                return true;
            }else
                return false;
            
        }else
            return false;
        
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

    

    /**
     * @return the empresa
     */
    public Tree getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Tree empresa) {
        this.empresa = empresa;
    }
    
    
}
