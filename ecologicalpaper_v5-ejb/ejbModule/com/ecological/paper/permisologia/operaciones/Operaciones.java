/*
 * Operaciones.java
 *
 * Created on July 13, 2007, 5:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.operaciones;

import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
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
import javax.persistence.Version;

/**
 * Entity class Operaciones
 *
 * @author simon
 */
@Entity
public class Operaciones implements Serializable {
    
    @TableGenerator(
    name="OperacionGen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="OPERACIONES_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="OperacionGen")
    private Long  codigo;
    
    private boolean status;
    @Column(name="OPERACION")
    private  String operacion;
    
    
    @Column(name="DESCRIPCION", length=4000)
    private  String descripcion;
    
    @Column(name="PERTENCE_ARBOL")
    private  Long pertenece_Arbol;
    
    
    @OneToMany(mappedBy="operaciones")
    private Collection<Role_Operaciones> role_oper = new ArrayList<Role_Operaciones>();
    
    @OneToMany(mappedBy="operaciones")
    private Collection<Seguridad_User> seguridad_user = new ArrayList<Seguridad_User>();
    
    
   
    
    /** Creates a new instance of Operaciones */
    public Operaciones() {
    }
    
    
    public String getOperacion() {
        return operacion;
    }
    
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public Collection<Role_Operaciones> getRole_oper() {
        return role_oper;
    }
    
    public void setRole_oper(Collection<Role_Operaciones> role_oper) {
        this.role_oper = role_oper;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Operaciones)  {
            Operaciones p =(Operaciones)objeto;
            /*String k1 =p.getCodigo()+"";
            String k2=this.codigo+"";*/
            //if (k1.trim().equalsIgnoreCase(k2.trim())){
            if (p.getCodigo()-this.codigo==0){
                return true;
            }else
                return false;
            
        }else
            return false;
        
    }
    
    public Collection<Seguridad_User> getSeguridad_user() {
        return seguridad_user;
    }
    
    public void setSeguridad_user(Collection<Seguridad_User> seguridad_user) {
        this.seguridad_user = seguridad_user;
    }
    
   

    public Long getPertenece_Arbol() {
        return pertenece_Arbol;
    }

    public void setPertenece_Arbol(Long pertenece_Arbol) {
        this.pertenece_Arbol = pertenece_Arbol;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   

   
    
}
