/*
 * Flow_referencia_role.java
 *
 * Created on August 2, 2007, 9:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.flows;

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
 * Entity class Flow_referencia_role
 *
 * @author simon
 */
@Entity
public class Flow_referencia_role implements Serializable {
    @TableGenerator(
    name="FlowRefRole_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="FLOWREFROLE_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="FlowRefRole_Gen")
    private Long codigo;
    private boolean status;
    @ManyToOne
    @JoinColumn(name="ROLE")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="FLOW")
    private Flow flow;
    
    
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Flow_referencia_role)  {
            Flow_referencia_role p =(Flow_referencia_role)objeto;
            if (p.getCodigo()-this.codigo==0){
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
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
 
}


