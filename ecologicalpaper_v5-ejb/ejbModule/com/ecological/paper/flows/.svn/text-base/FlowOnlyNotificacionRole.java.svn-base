package com.ecological.paper.flows;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import com.ecological.paper.permisologia.role.Role;

@Entity
public class FlowOnlyNotificacionRole implements Serializable {
    @TableGenerator(
    name="FlowOnlyNotificacionRole_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="FlowOnlyNotificacionRole_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="FlowOnlyNotificacionRole_Gen")
    private Long codigo;
    private boolean status;
    @ManyToOne
    @JoinColumn(name="ROLE")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="FLOW")
    private Flow flow;
    
    
    
    
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof FlowOnlyNotificacionRole)  {
            FlowOnlyNotificacionRole p =(FlowOnlyNotificacionRole)objeto;
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
