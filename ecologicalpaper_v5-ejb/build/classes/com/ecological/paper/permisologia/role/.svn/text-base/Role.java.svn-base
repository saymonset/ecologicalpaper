/*
 * Role.java
 *
 * Created on July 13, 2007, 5:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.role;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.paper.documentos.RolesOnlyViewDocPublicados;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_referencia_role;
import com.ecological.paper.permisologia.operaciones_usuarios.Menus_Usuarios;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.role_operaciones.Role_Operaciones;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.tree.Tree;
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
 * Entity class Role
 *
 * @author simon
 */
@Entity
public class Role implements Serializable {
    
    @TableGenerator(
    name="RoleGen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="Role_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RoleGen")
    private Long  codigo;
    
    @Column(name="NOMBRE")
    private String nombre;
    
    private boolean status;
    private boolean usadoParaCrearFlujo=false;
    
    @Column(name="DESCRIPCION", length=4000)
    private String descripcion;
    
    @Transient
    private boolean delete;
    
    
     

      @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;

    @OneToMany(mappedBy="role")
    private Collection<Roles_Usuarios> role_user = new ArrayList<Roles_Usuarios>();
    
    @OneToMany(mappedBy="role")
    private Collection<RolesOnlyViewDocPublicados> roles_Publicados = new ArrayList<RolesOnlyViewDocPublicados>();
   
    
    @OneToMany(mappedBy="role")
    private Collection<Seguridad_Role_Lineal> seguridad_Role_Lineal = new ArrayList<Seguridad_Role_Lineal>();
    
    
    @OneToMany(mappedBy="role")
    private Collection<Role_Operaciones> role_oper = new ArrayList<Role_Operaciones>();
    
    @OneToMany(mappedBy="role")
    private Collection<Seguridad_Role> role_tree = new ArrayList<Seguridad_Role>();
    
    @OneToMany(mappedBy="role")
    private Collection<Flow_referencia_role> roleFlows = new ArrayList<Flow_referencia_role>();
    
    @OneToMany(mappedBy="role")
    private Collection<Flow_Participantes> roleFlow_Participantes= new ArrayList<Flow_Participantes>();
    
    @OneToMany(mappedBy="role")
    private Collection<Menus_Usuarios> menu_roles = new ArrayList<Menus_Usuarios>();
    
    @OneToMany(mappedBy="role")
    private Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean = new ArrayList<FlowControlByUsuarioBean>();
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Collection<Roles_Usuarios> getRole_user() {
        return role_user;
    }
    
    public void setRole_user(Collection<Roles_Usuarios> role_user) {
        this.role_user = role_user;
    }
    
    public Collection<Role_Operaciones> getRole_oper() {
        return role_oper;
    }
    
    public void setRole_oper(Collection<Role_Operaciones> role_oper) {
        this.role_oper = role_oper;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Role)  {
            Role p =(Role)objeto;
            if ((p!=null && p.getCodigo()!=null && this.codigo!=null) && p.getCodigo()-this.codigo==0){
                return true;
            }else
                return false;
            
        }else
            return false;
        
    }
    
    public Collection<Seguridad_Role> getRole_tree() {
        return role_tree;
    }
    
    public void setRole_tree(Collection<Seguridad_Role> role_tree) {
        this.role_tree = role_tree;
    }
    
    public Collection<Flow_referencia_role> getRoleFlows() {
        return roleFlows;
    }
    
    public void setRoleFlows(Collection<Flow_referencia_role> roleFlows) {
        this.roleFlows = roleFlows;
    }
    
    public Collection<Flow_Participantes> getRoleFlow_Participantes() {
        return roleFlow_Participantes;
    }
    
    public void setRoleFlow_Participantes(Collection<Flow_Participantes> roleFlow_Participantes) {
        this.roleFlow_Participantes = roleFlow_Participantes;
    }
    
    public Collection<Menus_Usuarios> getMenu_roles() {
        return menu_roles;
    }
    
    public void setMenu_roles(Collection<Menus_Usuarios> menu_roles) {
        this.menu_roles = menu_roles;
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
    
    public Collection<Seguridad_Role_Lineal> getSeguridad_Role_Lineal() {
        return seguridad_Role_Lineal;
    }
    
    public void setSeguridad_Role_Lineal(Collection<Seguridad_Role_Lineal> seguridad_Role_Lineal) {
        this.seguridad_Role_Lineal = seguridad_Role_Lineal;
    }
    
   

    public Collection<FlowControlByUsuarioBean> getFlowControlByUsuarioBean() {
        return flowControlByUsuarioBean;
    }

    public void setFlowControlByUsuarioBean(Collection<FlowControlByUsuarioBean> flowControlByUsuarioBean) {
        this.flowControlByUsuarioBean = flowControlByUsuarioBean;
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

	public boolean isUsadoParaCrearFlujo() {
		return usadoParaCrearFlujo;
	}

	public void setUsadoParaCrearFlujo(boolean usadoParaCrearFlujo) {
		this.usadoParaCrearFlujo = usadoParaCrearFlujo;
	}

	public Collection<RolesOnlyViewDocPublicados> getRoles_Publicados() {
		return roles_Publicados;
	}

	public void setRoles_Publicados(Collection<RolesOnlyViewDocPublicados> roles_Publicados) {
		this.roles_Publicados = roles_Publicados;
	}

 
    
}
