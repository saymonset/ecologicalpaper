/*
 * ClienteRole_OperacionesPopup.java
 *
 * Created on August 19, 2007, 5:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.role;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author simon
 */
public class ClienteRole_OperacionesPopup  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long  codigo;
    private String nombre;
    private String descripcion;
    private String lista;
    private String usuarios;
    
    /** Creates a new instance of ClienteRole_OperacionesPopup */
    public ClienteRole_OperacionesPopup() {
    }
    
    
    public ClienteRole_OperacionesPopup(long codigo, String nombre, String descripcion,String operaciones,String usuarios) {
        this.codigo= codigo;
        this.nombre= nombre;
        this.descripcion=descripcion;
        this.usuarios=usuarios;
        if (operaciones!=null){
            lista = operaciones;
        }else{
            lista = "";
        }
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
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
   

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getLista() {
        return lista;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

  
    
}
