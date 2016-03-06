/*
 * Role_Operaciones.java
 *
 * Created on July 13, 2007, 5:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.role_operaciones;

import com.ecological.paper.permisologia.operaciones.Operaciones;
import com.ecological.paper.permisologia.role.Role;
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
 * Entity class Role_Operaciones
 *
 * @author simon
 */
@Entity
public class Role_Operaciones implements Serializable {
    
    @TableGenerator(
    name="RoleOper",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="RoleOper_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RoleOper")
    private Long codigo;
    private boolean status;
    @ManyToOne
    @JoinColumn(name="role")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="operaciones")
    private Operaciones operaciones;
    
     
    
    
   

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

    public Operaciones getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(Operaciones operaciones) {
        this.operaciones = operaciones;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

     

   }
