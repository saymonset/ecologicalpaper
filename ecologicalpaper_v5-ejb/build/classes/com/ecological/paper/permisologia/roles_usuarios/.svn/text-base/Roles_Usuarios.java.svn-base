/*
 * Roles_Usuarios.java
 *
 * Created on July 13, 2007, 5:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.roles_usuarios;

import com.ecological.paper.permisologia.role.Role;
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
import javax.persistence.Version;

/**
 * Entity class Roles_Usuarios
 * 
 * 
 * @author simon
 */
@Entity
public class Roles_Usuarios implements Serializable {
    @TableGenerator(
    name="RolOperUser",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="RoleOperUser_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RolOperUser")
    
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name="Role")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="USUARIO")
    private Usuario usuario;

    
   
    
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
