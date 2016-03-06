/*
 * Seguridad_Role.java
 *
 * Created on July 21, 2007, 9:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.seguridad;

import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * Entity class Seguridad_Role
 * 
 * @author simon
 */
@Entity
public class Seguridad_Role implements Serializable {
    
    @TableGenerator(
    name="SeguridadRole",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="SEGURIDADROLE_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SeguridadRole")
    private Long codigo;

    @ManyToOne
    @JoinColumn(name="ROLE")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="TREE")
    private Tree tree;
    
   
    
    /**
     * Creates a new instance of Seguridad_Role
     */
    public Seguridad_Role() {
    }
    
    /**
     * Gets the id of this Seguridad_Role.
     * 
     * @return the id
     */
    
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
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }

   
    
}
