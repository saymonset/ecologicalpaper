/*
 * Cliente.java
 *
 * Created on June 22, 2007, 9:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class Cliente
 * 
 * @author simon
 */
@Entity
@Table(name="CLIENTE")
public class Cliente implements Serializable {

  
    private Long id;
    private String nombre;
    private String apellidos;
    private String direccion;
    
     public Cliente(Long id,String n,String d) {
         this.id=id;
         this.nombre=n;
         this.direccion=d;
    }
    /** Creates a new instance of Cliente */
    public Cliente() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="NOMBRE")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name="APELLIDOS")
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Column(name="DIRECCION")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
