/*
 * Seguridad_User.java
 *
 * Created on July 21, 2007, 9:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.seguridad;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
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
 * Entity class Seguridad_User
 *
 * @author simon
 */
@Entity
public class Seguridad_User implements Serializable {
    @TableGenerator(
    name="SeguridadGen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="SEGURIDAD_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SeguridadGen")
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name="USUARIO")
    private Usuario usuario;
   
    
    @ManyToOne
    @JoinColumn(name="OPERACIONES")
    private Operaciones operaciones;
    
    @ManyToOne
    @JoinColumn(name="TREE")
    private Tree tree;
    
    
    /** Creates a new instance of Seguridad_User */
    public Seguridad_User() {
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Operaciones getOperaciones() {
        return operaciones;
    }
    
    public void setOperaciones(Operaciones operaciones) {
        this.operaciones = operaciones;
    }
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Seguridad_User)  {
            Seguridad_User p =(Seguridad_User)objeto;
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

   

   
    
}
